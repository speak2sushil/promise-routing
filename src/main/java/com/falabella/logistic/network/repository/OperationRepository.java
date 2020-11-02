package com.falabella.logistic.network.repository;

import com.falabella.logistic.network.model.LegOperation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends Neo4jRepository<LegOperation, Long> {
}
