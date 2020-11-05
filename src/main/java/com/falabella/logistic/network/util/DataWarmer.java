package com.falabella.logistic.network.util;

import com.falabella.logistic.network.model.Leg;
import com.falabella.logistic.network.model.LegRestrictions;
import com.falabella.logistic.network.model.Node;
import com.falabella.logistic.network.model.NodeType;
import com.falabella.logistic.network.model.Service;
import com.falabella.logistic.network.model.ServiceCategory;
import com.falabella.logistic.network.model.ServiceRestrictions;
import com.falabella.logistic.network.model.ServiceType;
import com.falabella.logistic.network.repository.LegRepository;
import com.falabella.logistic.network.repository.NodeRepository;
import com.falabella.logistic.network.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

@Component

public class DataWarmer {
    List<String> excludedProductTypes = List.of("FRAGILE_ITEMS", "LARGE_ITEMS", "SMALL_ITEMS", "CHEMICAL_PRODUCTS", "RDX", "CRACKERS", "LIQUID_ITEMS", "METAL_ITEMS", "POISON", "LIQUOR", "DRUGS");
    List<ServiceCategory> serviceCategories = Arrays.asList(ServiceCategory.values());
    List<ServiceType> serviceTypes = Arrays.asList(ServiceType.values());
    Random random = new Random();
    @Autowired
    LegRepository legRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @PostConstruct
    void init() {
        List<Node> fcs = new ArrayList<>();
        List<Node> stores = new ArrayList<>();
        List<Node> crossDockHubs = new ArrayList<>();
        List<Node> zones = new ArrayList<>();
        //Node preparation
        prepareNodes(fcs, 500, 0);
        prepareNodes(stores, 500, 1);
        prepareNodes(crossDockHubs, 100, 2);
        prepareNodes(zones, 600, 3);

        buildPaths(fcs, stores, crossDockHubs, zones);

    }


    void buildPaths(List<Node> fcs, List<Node> stores, List<Node> crossDockHubs, List<Node> zones) {
        int fcsCounter = 0;
        int storesCounter = 0;
        int crossDocksCounter = 0;
        for (Node destination : zones) {
            if (fcsCounter == fcs.size())
                fcsCounter = 0;
            if (storesCounter == stores.size())
                storesCounter = 0;
            if (crossDocksCounter == crossDockHubs.size())
                crossDocksCounter = 0;
            Node source = random.nextInt(10) >= 5 ? fcs.get(fcsCounter++) : stores.get(storesCounter++);
            if(random.nextInt(10) < 2 ) {
                Node intermediate = random.nextInt(10) >= 5 ? fcs.get(fcsCounter++) : stores.get(storesCounter++);
                legRepository.save(prepareLinks(source, intermediate));
                source = intermediate;
            }
            if (random.nextInt(10) >= 5) {
                Node intermediate = crossDockHubs.get(crossDocksCounter++);
                legRepository.save(prepareLinks(source, intermediate));
                source = intermediate;
            }
            legRepository.save(prepareLinks(source, destination));
        }
        if(fcsCounter < fcs.size()) {
            prepareRemainingSourcePaths(fcs, crossDockHubs,zones, fcsCounter);
        }
        if(storesCounter < stores.size()) {
            prepareRemainingSourcePaths(stores,crossDockHubs, zones, storesCounter);
        }
    }

    private void prepareRemainingSourcePaths(List<Node> fcs, List<Node> crossDockHubs, List<Node> zones, int fcsCounter) {
        for(int i = fcsCounter; i< fcs.size(); i++) {
            Node source = fcs.get(fcsCounter);
            if (random.nextInt(10) >= 5) {
                Node intermediate = crossDockHubs.get(random.nextInt(crossDockHubs.size()));
                legRepository.save(prepareLinks(source, intermediate));
                source = intermediate;
            }
            Node destination = zones.get(random.nextInt(zones.size()));
            legRepository.save(prepareLinks(source, destination));
        }
    }

    void connectNodes(List<Node> nodes, int count) {
        for (int i = 0; i < count; ) {
            Node source = nodes.get(random.nextInt(nodes.size()));
            Node destination = nodes.get(random.nextInt(nodes.size()));
            if (!source.getNodeId().equals(destination.getNodeId())) {
                legRepository.save(prepareLinks(source, destination));
                i++;
            }
        }
    }

    private void preparePaths(List<Node> fcs, List<Node> stores, List<Node> crossDockHubs, List<Node> zones, int edges, int count) {
        for (int i = 0; i < count; i++) {
            Node source = null;
            if (i % 2 == 0) {
                source = fcs.get(random.nextInt(fcs.size()));
            } else {
                source = stores.get(random.nextInt(stores.size()));
            }
            Node zone = zones.get(random.nextInt(zones.size()));
            if (edges > 1) {
                int j = 0;
                while (j < edges) {
                    Node intemediate;
                    int x = random.nextInt(10);
                    if (x <= 3) {
                        intemediate = crossDockHubs.get(random.nextInt(crossDockHubs.size()));
                    } else if (x < 7) {
                        intemediate = fcs.get(random.nextInt(fcs.size()));
                    } else {
                        intemediate = stores.get(random.nextInt(stores.size()));
                    }
                    if (!source.getNodeId().equals(intemediate.getNodeId())) {
                        legRepository.save(prepareLinks(source, intemediate));
                        source = intemediate;
                        j++;
                    }
                }
                legRepository.save(prepareLinks(source, zone));
            }
        }
    }

    private void prepareDirectPaths(List<Node> source, List<Node> zones, int count) {
        IntStream.range(0, count).forEach(c -> legRepository.save(prepareLinks(source.get(random.nextInt(source.size())), zones.get(random.nextInt(zones.size())))));
    }

    void prepareNodes(List<Node> list, int count, int i) {
        IntStream.range(0, count).forEach(value -> {
            Node node;
            if (i == 0) {
                node = nodeRepository.save(prepareFulFilmentCenter());
            } else if (i == 1) {
                node = nodeRepository.save(prepareStores());
            } else if (i == 2) {
                node = nodeRepository.save(prepareCrossDockHubs());
            } else {
                node = nodeRepository.save(prepareZones());
            }
            list.add(node);
        });
    }


    private Leg prepareLinks(Node startNode, Node endNode) {
        Set<ServiceCategory> serviceCategory = new HashSet<>();
        serviceCategory.add(serviceCategories.get(random.nextInt(3)));
        serviceCategory.add(serviceCategories.get(random.nextInt(3)));
        return Leg.builder()
                .enabled(true)
                .cost(1)
                .excludedProductTypes(List.of(excludedProductTypes.get(random.nextInt(11)), excludedProductTypes.get(random.nextInt(11))))
                .legRestrictions(prepareLegRestrictions())
                .offeredDaysAhead(random.nextInt(10))
                .originNode(startNode)
                .targetNode(endNode)
                .preprocessingInMins(random.nextInt(300))
                .serviceCategory(serviceCategory)
                .ranking(random.nextInt(1000))
                .build();
    }

    private LegRestrictions prepareLegRestrictions() {
        return LegRestrictions.builder()
                .maxWeightInKg((double) random.nextInt(1000))
                .maxVolumeInDm3((double) random.nextInt(1000))
                .maxDimensionInCm((double) random.nextInt(1000))
                .build();
    }

    private Node prepareFulFilmentCenter() {
        return Node.builder()
                .enabled(true)
                .ranking(random.nextInt(1000))
                .services(prepareServices())
                .labels(List.of(NodeType.FULFILMENT_CENTER.name()))
                .name(NodeType.FULFILMENT_CENTER.name())
                .type(NodeType.FULFILMENT_CENTER)
                .build();
    }

    private Node prepareStores() {
        return Node.builder()
                .enabled(true)
                .ranking(random.nextInt(1000))
                .services(prepareServices())
                .labels(List.of(NodeType.STORE.name()))
                .name(NodeType.STORE.name())
                .type(NodeType.STORE)
                .build();
    }

    private Node prepareCrossDockHubs() {
        return Node.builder()
                .enabled(true)
                .ranking(random.nextInt(1000))
                .services(prepareServices())
                .labels(List.of(NodeType.CROSSDOCK_HUB.name()))
                .name(NodeType.CROSSDOCK_HUB.name())
                .type(NodeType.CROSSDOCK_HUB)
                .build();
    }

    private Node prepareZones() {
        return Node.builder()
                .enabled(true)
                .ranking(random.nextInt(1000))
                .labels(List.of(NodeType.ZONE.name()))
                .name(NodeType.ZONE.name())
                .type(NodeType.ZONE)
                .build();
    }


    private List<Service> prepareServices() {
        int serviceCount = random.nextInt(6);
        Service baseService = Service.builder()
                .serviceRestrictions(prepareServiceRestrictions())
                .enabled(true)
                .offeredDaysAhead(random.nextInt(10))
                .build();
        Map<String, Service> services = new HashMap<>();
        IntStream.range(0, serviceCount).forEach(value -> {
            ServiceType serviceType = serviceTypes.get(random.nextInt(2));
            ServiceCategory serviceCategory = serviceCategories.get(random.nextInt(3));
            String name = serviceCategory.name() + "_" + serviceType.name();
            Service service = baseService.toBuilder()
                    .name(name)
                    .serviceType(serviceType)
                    .serviceCategory(serviceCategory)
                    .build();
            services.put(name, service);
        });

        Iterable<Service> services1 = serviceRepository.saveAll(new ArrayList<>(services.values()));
        List<Service> savedServices = new ArrayList<>();
        services1.forEach(savedServices::add);
        return savedServices;
    }


    private ServiceRestrictions prepareServiceRestrictions() {
        return ServiceRestrictions
                .builder()
                .maxWeight(random.nextInt(1000))
                .weightUOM("KG")
                .maxVolume(random.nextInt(1000))
                .volumeUOM("m3")
                .productTypes(List.of(excludedProductTypes.get(random.nextInt(11)), excludedProductTypes.get(random.nextInt(11))))
                .build();
    }

}
