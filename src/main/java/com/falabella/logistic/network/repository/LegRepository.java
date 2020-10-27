package com.falabella.logistic.network.repository;

import com.falabella.logistic.network.model.Leg;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LegRepository extends Neo4jRepository<Leg, Long> {
}
