package com.falabella.logistic.network.service;


import com.falabella.logistic.network.dto.NodeDTO;
import com.falabella.logistic.network.dto.ServiceDto;
import com.falabella.logistic.network.model.Node;
import com.falabella.logistic.network.model.NodeType;
import com.falabella.logistic.network.repository.NodeRepository;
import com.falabella.logistic.network.repository.OperatorRepository;
import com.falabella.logistic.network.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class NodeServiceImpl implements NodeService{

    @Autowired
    private NodeRepository  nodeRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<Node> saveNodes(List<NodeDTO> nodeList) {
        log.info("NodeServiceImpl::>> saveNodes start");
        final List<Node> savedNodes=new ArrayList<>();
        nodeList.forEach(node->{
            Node newNode=new Node();
            newNode.setEnabled(node.getEnabled());
            newNode.setName(node.getName());
            newNode.setRanking(node.getRanking());
            newNode.setType(NodeType.valueOf(node.getType().toUpperCase()));
            if(node.getServices() != null) {
                List<com.falabella.logistic.network.model.Service> services = node.getServices().stream().map(this::persistService).collect(toList());
                newNode.setServices(services);
            }
            //            Operator operator=new Operator();
//            operator.setOperatorName(node.getOperator().getOperatorName());
//            operatorRepository.save(operator);
//            newNode.setOperator(operator);
            newNode.setShortName(node.getShortName());
            newNode.setLabels(List.of(node.getType().toUpperCase()));
            savedNodes.add(newNode);
        });
        nodeRepository.saveAll(savedNodes);
        log.info("NodeServiceImpl::>> saveNodes end");
        return savedNodes;
    }

    public com.falabella.logistic.network.model.Service persistService(ServiceDto serviceDto) {
        com.falabella.logistic.network.model.Service service = com.falabella.logistic.network.model.Service.builder()
                .name(serviceDto.getServiceType() + "_" + serviceDto.getServiceCategory())
                .serviceType(serviceDto.getServiceType())
                .enabled(serviceDto.isEnabled())
                .offeredDaysAhead(serviceDto.getOfferedDaysAhead())
                .serviceCategory(serviceDto.getServiceCategory())
                .build();
        return serviceRepository.save(service);
    }

}
