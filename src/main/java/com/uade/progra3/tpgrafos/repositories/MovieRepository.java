package com.uade.progra3.tpgrafos.repositories;

import com.uade.progra3.tpgrafos.models.MovieEntity;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MovieRepository extends ReactiveNeo4jRepository<MovieEntity, String> {
    
    Mono<MovieEntity> findByTitle(String title);
    
    @Query("MATCH (m:Movie) RETURN m")
    Flux<MovieEntity> findAllMovies();
}
