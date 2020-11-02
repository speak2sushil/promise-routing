package com.falabella.logistic.network.service;

import com.falabella.logistic.network.dto.LegDTO;
import com.falabella.logistic.network.model.*;
import com.falabella.logistic.network.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class LegServiceImpl implements  LegService{

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private LegRepository legRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Leg createLegAndNode(LegDTO legDTO) {
        log.info("LegServiceImpl::>> createLegAndNode start");
        log.info("legDTO>>>"+legDTO);
        System.err.println(legDTO);
        Node originNode=new Node();
        Node targetNode=new Node();
        Operator legOperator=new Operator();
        Leg leg=new Leg();
        modelMapper.map(legDTO.getOriginNode(),originNode);
        modelMapper.map(legDTO.getTargetNode(),targetNode);
        modelMapper.map(legDTO.getOperator(),legOperator);
        modelMapper.map(legDTO,leg);
        List<LegOperation> legOperationList = legDTO.getOperationList()
                .stream()
                .map(operation -> modelMapper.map(operation, LegOperation.class))
                .collect(Collectors.toList());
        originNode.setLabels(List.of(legDTO.getOriginNode().getType()));
        targetNode.setLabels(List.of(legDTO.getTargetNode().getType()));
        originNode=nodeRepository.save(originNode);
        targetNode=nodeRepository.save(targetNode);
        //legOperator=operatorRepository.save(legOperator);
        //Iterable<Operation> operationItr=operationRepository.saveAll(operationList);
        leg.setOriginNode(originNode);
        leg.setTargetNode(targetNode);
        leg.setOperator(legOperator);
        leg.setLegOperationList(legOperationList);
        leg=legRepository.save(leg);
        log.info("LegServiceImpl::>> createLegAndNode end");
        return leg;
    }

    @Override
    public Iterable<Leg> linkNodes(List<LegDTO> legDTOList) {
        log.info("LegServiceImpl::>> linkNodes start");
        List<Leg> legList = legDTOList
                .stream()
                .map(legDTO -> modelMapper.map(legDTO, Leg.class))
                .collect(Collectors.toList());
        legList.forEach(leg->{
            leg.setOriginNode(nodeRepository.findNodeById(leg.getOriginNode().getNodeId()).get());
            leg.setTargetNode(nodeRepository.findNodeById(leg.getTargetNode().getNodeId()).get());
        });
        Iterable<Leg>iterableLegs=legRepository.saveAll(legList);
        log.info("LegServiceImpl::>> linkNodes end");
        return iterableLegs;
    }
}
