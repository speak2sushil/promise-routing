package com.falabella.logistic.network.repository;

import com.falabella.logistic.network.model.ItineraryResponse;
import com.falabella.logistic.network.model.Node;
import com.falabella.logistic.network.model.Service;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface NodeRepository extends Neo4jRepository<Node, Long> {

    @Query("match (n:Node) where n[$0]=$1 return n")
    Node findNodeByProperty(String propertyType, Object propertyValue);

    @Query("match (n:Node) where id(n)=$0 return n")
    Optional<Node> findNodeById(Long id);

    @Query("match (s:Service)<-[:`HAS-SERVICE`]-(n:Node) where id(n)=$0 return s")
    List<Service> findServicesByNodeId(Long id);

    @Query("match p=(n:Node)-[*]->(m:ZONE) \n" +
            "where\n" +
            "n.type in[\"FULFILMENT_CENTER\",\"STORE\"]\n" +
            "and n.enabled=true\n" +
            "and id(m) in $zoneIds \n" +
//            "return nodes(p) AS nodes, relationships(p)" )
            "return head(nodes(p)) as sourceNode , last(nodes(p)) as destinationNode \n" +
            ",reduce(output = [], n IN nodes(p) | CASE WHEN n:Node THEN  output + n.name ELSE output END) as legs\n" +
            ",nodes(p) AS nodes\n" +
            ",relationships(p) as rels\n" +
            ",length(p) as noOfEdges\n" +
            "ORDER BY sourceNode.ranking,noOfEdges ASC ")
    List<ItineraryResponse> findFacilitiesForZones(@Param("zoneIds") List<Long> zoneIds);

    /*@Query("match p=(s:Service)<-[:`HAS-SERVICE`]-(n:Node)-[*..10]->(z:Node) " +
            "where s.name in $serviceList \n" +
            "and id(z) in $zoneIds \n" +
            "with collect(distinct n) as sourceNodes,collect(distinct z) as destinationNodes\n" +
            "with sourceNodes+destinationNodes as allNodes\n" +
            "match (start:Node) , (end:Node:ZONE)\n" +
            "CALL apoc.algo.dijkstra(start, end, 'LEG', 'cost') YIELD path, weight\n" +
            "where all(node in nodes(path) where node in allNodes)\n" +
            "and  start.type IN ['FULFILMENT_CENTER','STORE']\n" +
            "return nodes(path) AS nodes, relationships(path) as relationships\n" +
            "ORDER BY weight ")
    Result findAllFacilitiesForZonesBasedOnService(@Param("serviceList") List<String> serviceList ,@Param("zoneIds") List<Long> zoneIds );*/

    @Query("match p=(s:Service)<-[:`HAS-SERVICE`]-(n:Node)-[*..10]->(z:Node) where  z.name in [$zone]\n" +
            "with collect(distinct n) as sourceNodes,collect(distinct z) as destinationNodes\n" +
            "with sourceNodes+destinationNodes as allNodes\n" +
            "match (start:Node) , (end:Node:ZONE)\n" +
            "CALL apoc.algo.dijkstra(start, end, 'LEG', 'cost') YIELD path, weight\n" +
            "where all(node in nodes(path) where node in allNodes)\n" +
            "and  start.type IN ['FULFILMENT_CENTER','STORE']\n" +
            "return nodes(path) AS nodes, relationships(path) as relationships\n" +
            "ORDER BY weight")
    Result findAllPathsForAZone(String zone);

    @Query("match p=(s:Service)<-[:`HAS-SERVICE`]-(n:Node)-[:LEG*..5]->(z:Node)  where\n" +
            "id(z) in $zonedIds \n" +
            "with collect(distinct n) as sourceNodes,collect(distinct z) as destinationNodes\n" +
            "with sourceNodes+destinationNodes as allNodes,sourceNodes\n" +
            "match (start:Node),(end:Node:ZONE)\n" +
            "CALL apoc.algo.dijkstra(start, end, 'LEG>', 'cost') YIELD path, weight\n" +
            "where all(node in nodes(path) where node in allNodes)\n" +
            "and  start.type IN ['FULFILMENT_CENTER','STORE']\n" +
            "with  nodes(path) AS nodes, relationships(path) as relationships\n" +
            ",sourceNodes\n" +
            "UNWIND sourceNodes as node\n" +
            "OPTIONAL match (s:Service)<-[:`HAS-SERVICE`]-(node)\n" +
            "return nodes,relationships\n" +
            ",COLLECT({node: id(node), services: s}) AS nodeServices")
    Result findAllFacilitiesForZonesBasedOnService(List<Long> zonedIds);

}
