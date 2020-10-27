package com.falabella.logistic.network.service;


import com.falabella.logistic.network.dto.ItineraryDTO;
import com.falabella.logistic.network.dto.ItineraryLeg;
import com.falabella.logistic.network.dto.NodeDTO;
import com.falabella.logistic.network.model.ItineraryResponse;
import com.falabella.logistic.network.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ZoneServiceImpl implements ZoneService{

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ItineraryDTO>  findItinerary(List<Long> zoneIds) {
        log.info("ZoneServiceImpl::>> findItinerary start");
        log.info("ZoneIds::>>"+zoneIds);
        List<ItineraryResponse> result= nodeRepository.findFacilitiesForZones(zoneIds);
        log.info("result::size {} >>>>"+result.size());
        // db response to API Response conversion
        log.info("result::{} >>>>"+result);
        List<ItineraryDTO> dtoResult=result.stream().map(response->{
            ItineraryDTO itr=new ItineraryDTO();
            NodeDTO originNode=new NodeDTO();
            NodeDTO destinationNode=new NodeDTO();
            modelMapper.map(response.getSourceNode(),originNode);
            modelMapper.map(response.getDestinationNode(),destinationNode);
            itr.setOriginNode(originNode);
            itr.setDestinationNode(destinationNode);
            AtomicInteger runCount = new AtomicInteger(0);
            List<ItineraryLeg> itrLegResponse=response.getRels().stream().map(rels->{
                ItineraryLeg itrLeg=new ItineraryLeg();
                itrLeg.setLegId(rels.getLegId());
                NodeDTO legOriginNode=new NodeDTO();
                NodeDTO legDestinationNode=new NodeDTO();
                modelMapper.map(rels.getOriginNode(),legOriginNode);
                modelMapper.map(rels.getTargetNode(),legDestinationNode);
                itrLeg.setSourceNode(legOriginNode);
                itrLeg.setTargetNode(legDestinationNode);
                itrLeg.setSequence(runCount.incrementAndGet());
               return itrLeg;
            }).collect(Collectors.toList());
            itr.setLegs(itrLegResponse);
            return itr;
        }).collect(Collectors.toList());
        log.info("ZoneServiceImpl::>> findItinerary end");
       return dtoResult;
    }
}
