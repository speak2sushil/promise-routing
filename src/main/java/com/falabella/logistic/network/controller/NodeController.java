package com.falabella.logistic.network.controller;


import com.falabella.logistic.network.dto.NodeDTO;
import com.falabella.logistic.network.model.Node;
import com.falabella.logistic.network.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/v1/nodes")
@Slf4j
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @PostMapping()
    public ResponseEntity<List<Node>> createNodes(
            @RequestBody List<NodeDTO> nodeList) {
        log.info("NodeController  {} createNodes start ");
        List<Node> savedNodeList=nodeService.saveNodes(nodeList);
        log.info("NodeController  {} createNodes end ");
        return new ResponseEntity<List<Node>>(savedNodeList, HttpStatus.CREATED);
    }
}
