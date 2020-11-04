package com.falabella.logistic.network.service;


import com.falabella.logistic.network.dto.ItineraryDTO;
import com.falabella.logistic.network.dto.ItineraryLeg;
import com.falabella.logistic.network.dto.NodeDTO;
import com.falabella.logistic.network.model.ItineraryResponse;
import com.falabella.logistic.network.model.Leg;
import com.falabella.logistic.network.model.LegRestrictions;
import com.falabella.logistic.network.model.Node;
import com.falabella.logistic.network.model.NodeType;
import com.falabella.logistic.network.model.Service;
import com.falabella.logistic.network.model.ServiceCategory;
import com.falabella.logistic.network.model.ServiceRestrictions;
import com.falabella.logistic.network.model.ServiceType;
import com.falabella.logistic.network.repository.NodeRepository;
import com.falabella.logistic.network.repository.ServiceRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@org.springframework.stereotype.Service
@Slf4j
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ItineraryDTO> findItinerary(List<Long> zoneIds) {
        log.info("ZoneServiceImpl::>> findItinerary start");
        log.info("ZoneIds::>>" + zoneIds);
        List<ItineraryResponse> result = nodeRepository.findFacilitiesForZones(zoneIds);
        log.info("result::size {} >>>>" + result.size());
        // db response to API Response conversion
        log.info("result::{} >>>>" + result);
        List<ItineraryDTO> dtoResult = result.stream().map(response -> {
            ItineraryDTO itr = new ItineraryDTO();
            NodeDTO originNode = new NodeDTO();
            NodeDTO destinationNode = new NodeDTO();
            modelMapper.map(response.getSourceNode(), originNode);
            modelMapper.map(response.getDestinationNode(), destinationNode);
            itr.setOriginNode(originNode);
            itr.setDestinationNode(destinationNode);
            AtomicInteger runCount = new AtomicInteger(0);
            List<ItineraryLeg> itrLegResponse = response.getRels().stream().map(rels -> {
                ItineraryLeg itrLeg = new ItineraryLeg();
                itrLeg.setLegId(rels.getLegId());
                NodeDTO legOriginNode = new NodeDTO();
                NodeDTO legDestinationNode = new NodeDTO();
                modelMapper.map(rels.getOriginNode(), legOriginNode);
                modelMapper.map(rels.getTargetNode(), legDestinationNode);
                itrLeg.setSourceNode(legOriginNode);
                itrLeg.setTargetNode(legDestinationNode);
                itrLeg.setSequence(runCount.incrementAndGet());
                return itrLeg;
            }).collect(toList());
            itr.setLegs(itrLegResponse);
            return itr;
        }).collect(toList());
        log.info("ZoneServiceImpl::>> findItinerary end");
        return dtoResult;
    }

    @Override
    public List<ItineraryResponse> findItinerariesForAZone(List<Long> zoneIds) {
        Result allPathsForAZone = nodeRepository.findAllFacilitiesForZonesBasedOnService(zoneIds);
        List<ItineraryResponse> itineraryResponses = new ArrayList<>();
        allPathsForAZone.iterator().forEachRemaining(stringObjectMap -> {
            List<Leg> relationships = (List<Leg>) stringObjectMap.get("relationships");
            List<Node> nodes = (List<Node>) stringObjectMap.get("nodes");
            Map<String, Object>[] nodeServices = (Map<String, Object>[]) stringObjectMap.get("nodeServices");

            Map<Long, List<Service>> nodeServiceMap = preapreNodeServiceMap(nodeServices);
            List<ServiceCategory> legsServiceCategories = allowedLegsServiceCategories(relationships);
            boolean skip = legsServiceCategories.isEmpty();

            double allowedWeight = relationships.stream().map(Leg::getLegRestrictions).mapToDouble(LegRestrictions::getMaxWeightInKg).min().orElseThrow(RuntimeException::new);
            double allowedVolume = relationships.stream().map(Leg::getLegRestrictions).mapToDouble(LegRestrictions::getMaxVolumeInDm3).min().orElseThrow(RuntimeException::new);
            double allowedDimensions = relationships.stream().map(Leg::getLegRestrictions).mapToDouble(LegRestrictions::getMaxDimensionInCm).min().orElseThrow(RuntimeException::new);

            Set<String> excludedProductTypes = new HashSet<>(relationships.stream().map(Leg::getExcludedProductTypes).flatMap(Collection::stream).collect(Collectors.toSet()));
            List<List<String>> serviceTypes = new ArrayList<>();
            if (!skip) {
                for (Node node : nodes) {
                    if(node.getType() == NodeType.ZONE)
                        continue;
                    List<Service> servicesByNodeId = nodeServiceMap.get(node.getNodeId());
                    List<Service> filteredNodeServices = null;
                    if(servicesByNodeId != null) {
                         filteredNodeServices = servicesByNodeId.stream().filter(service -> legsServiceCategories.contains(service.getServiceCategory())).collect(toList());
                         serviceTypes.add(filteredNodeServices.stream().map(Service::getName).collect(toList()));
                    }
                    if (filteredNodeServices == null || filteredNodeServices.isEmpty()) {
                        skip = true;
                    } else {
                        ServiceRestrictions serviceRestrictions = filteredNodeServices.get(0).getServiceRestrictions();
                        if(serviceRestrictions.getProductTypes() != null) {
                            excludedProductTypes.addAll(serviceRestrictions.getProductTypes());
                        }
                        if (allowedWeight > serviceRestrictions.getMaxWeight()) {
                            allowedWeight = serviceRestrictions.getMaxWeight();
                        }
                        if (allowedVolume > serviceRestrictions.getMaxVolume()) {
                            allowedVolume = serviceRestrictions.getMaxVolume();
                        }
                        node.setServices(filteredNodeServices);
                    }
                }
            }
            Set<String> allowedServices = new HashSet<>();
           if(!skip && serviceTypes.size() > 1) {
               List<String> allowedServiceAtNode = serviceTypes.get(0);
               for(int i = 1; i< serviceTypes.size(); i++) {
                   serviceTypes.get(i).retainAll(allowedServiceAtNode);
                   if(serviceTypes.get(i).size() ==0) {
                       skip = true;
                       break;
                   }
                   allowedServices.addAll(serviceTypes.get(i));
               }
           } else if(!skip){
               allowedServices.addAll(serviceTypes.get(0));
           }
            if (!skip) {
                itineraryResponses.add(ItineraryResponse.builder()
                        .nodes(nodes)
                        .rels(relationships)
                        .sourceNode(relationships.get(0).getOriginNode())
                        .destinationNode(relationships.get(relationships.size() - 1).getTargetNode())
                        .noOfEdges(relationships.size())
                        .excludedProductTypes(excludedProductTypes)
                        .serviceCategories(allowedServices)
                        .restrictions(ItineraryResponse.Restrictions.builder().maxWeightInKg(allowedWeight).maxVolumeInDm3(allowedVolume).maxDimensionInCm(allowedDimensions).build())
                        .build());
            }
        });
        return itineraryResponses;
    }

    private Map<Long, List<Service>> preapreNodeServiceMap(Map<String, Object>[] nodeServices) {
        Map<Long, List<Service>> nodeServiceMap = new HashMap<>();
        for (Map<String, Object> nodeService : nodeServices) {
            Long nodeId = (Long) nodeService.get("node");
            InternalNode internalNodes = (InternalNode) nodeService.get("services");
            Service service = Service.builder()
                    .id(internalNodes.id())
                    .offeredDaysAhead(internalNodes.get("offeredDaysAhead").asInt())
                    .enabled(internalNodes.get("enabled").asBoolean())
                    .serviceType(ServiceType.valueOf(internalNodes.get("serviceType").asString()))
                    .name(internalNodes.get("name").asString())
                    .serviceRestrictions(new Gson().fromJson(internalNodes.get("serviceRestrictions").asString(), ServiceRestrictions.class))
                    .serviceCategory(ServiceCategory.valueOf(internalNodes.get("serviceCategory").asString()))
                    .build();
            if (nodeServiceMap.containsKey(nodeId)) {
                List<Service> services = nodeServiceMap.get(nodeId);
                services.add(service);
                nodeServiceMap.put(nodeId, services);
            } else {
                ArrayList<Service> list = new ArrayList<>();
                list.add(service);
                nodeServiceMap.put(nodeId, list);
            }
        }
        return nodeServiceMap;
    }

    private List<ServiceCategory> allowedLegsServiceCategories(List<Leg> relationships) {
        List<ServiceCategory> allowed = new ArrayList<>();
        List<Set<ServiceCategory>> collect = relationships.stream().map(Leg::getServiceCategory).collect(toList());
        Set<ServiceCategory> serviceCategories = collect.get(0);
        serviceCategories.forEach(serviceCategory -> {
            boolean add = true;
            for (int i = 1; i < collect.size(); i++) {
                if (!collect.get(i).contains(serviceCategory)) {
                    add = false;
                    break;
                }
            }
            if (add) {
                allowed.add(serviceCategory);
            }
        });
        return allowed;
    }


}
