package com.falabella.logistic.network.repository;


import com.falabella.logistic.network.model.Operator;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OperatorRepository extends Neo4jRepository<Operator, Long> {
}
