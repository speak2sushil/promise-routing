package com.falabella.logistic.network.dto;

import com.falabella.logistic.network.model.Node;
import com.falabella.logistic.network.model.NodeType;
import com.falabella.logistic.network.model.Operator;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-22T01:25:27+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.8 (Oracle Corporation)"
)
@Component
public class NodeMapperImpl implements NodeMapper {

    @Override
    public Node nodeDTOtoNode(NodeDTO source) {
        if ( source == null ) {
            return null;
        }

        Node node = new Node();

        node.setName( source.getName() );
        node.setShortName( source.getShortName() );
        node.setOperator( operatorDTOToOperator( source.getOperator() ) );
        if ( source.getType() != null ) {
            node.setType( Enum.valueOf( NodeType.class, source.getType() ) );
        }
        if ( source.getEnabled() != null ) {
            node.setEnabled( source.getEnabled() );
        }
        node.setRanking( source.getRanking() );

        return node;
    }

    protected Operator operatorDTOToOperator(OperatorDTO operatorDTO) {
        if ( operatorDTO == null ) {
            return null;
        }

        Operator operator = new Operator();

        operator.setOperatorName( operatorDTO.getOperatorName() );

        return operator;
    }
}
