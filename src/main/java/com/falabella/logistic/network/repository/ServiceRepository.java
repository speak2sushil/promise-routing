package com.falabella.logistic.network.repository;

import com.falabella.logistic.network.model.Leg;
import com.falabella.logistic.network.model.Service;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRepository extends Neo4jRepository<Service, Long> {
}
