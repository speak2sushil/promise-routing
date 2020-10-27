package com.falabella.logistic.network.dto;


import com.falabella.logistic.network.model.Node;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface NodeMapper {
    NodeMapper INSTANCE = Mappers.getMapper(NodeMapper.class);
    Node nodeDTOtoNode(NodeDTO source);
}
