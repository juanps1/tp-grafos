package com.uade.progra3.tpgrafos.repositories;

import com.uade.progra3.tpgrafos.models.PersonEntity;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveNeo4jRepository<PersonEntity, String> {
    Mono<PersonEntity> findByName(String name);
}
