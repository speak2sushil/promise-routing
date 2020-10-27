package com.falabella.logistic.network.service;

import com.falabella.logistic.network.dto.NodeDTO;
import com.falabella.logistic.network.model.Node;

import java.util.List;

public interface NodeService {
    public List<Node> saveNodes(List<NodeDTO> nodeList);
}
