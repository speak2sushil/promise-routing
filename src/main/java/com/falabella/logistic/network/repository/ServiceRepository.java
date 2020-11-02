package com.falabella.logistic.network.repository;

import com.falabella.logistic.network.model.Service;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ServiceRepository extends Neo4jRepository<Service, Long> {
    @Query("MATCH (s:Service)<-[:`HAS-SERVICE`]-(n:Node) WHERE id(n)=$0 RETURN s")
    List<Service> findServicesByNodeId(Long id);
}
