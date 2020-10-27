package com.falabella.logistic.network.repository;

import com.falabella.logistic.network.model.Operation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends Neo4jRepository<Operation, Long> {
}
