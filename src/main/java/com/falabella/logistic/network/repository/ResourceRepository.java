package com.falabella.logistic.network.repository;


import com.falabella.logistic.network.model.Resource;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResourceRepository extends Neo4jRepository<Resource, Long> {
}
