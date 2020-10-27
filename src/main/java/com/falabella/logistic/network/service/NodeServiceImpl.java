package com.falabella.logistic.network.service;


import com.falabella.logistic.network.dto.NodeDTO;
import com.falabella.logistic.network.model.Node;
import com.falabella.logistic.network.model.NodeType;
import com.falabella.logistic.network.repository.NodeRepository;
import com.falabella.logistic.network.repository.OperatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NodeServiceImpl implements NodeService{

    @Autowired
    private NodeRepository  nodeRepository;

    @Autowired
    private OperatorRepository operatorRepository;


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
}
