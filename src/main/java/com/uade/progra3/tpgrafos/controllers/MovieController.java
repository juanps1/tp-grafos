package com.uade.progra3.tpgrafos.controllers;

import com.uade.progra3.tpgrafos.models.MovieEntity;
import com.uade.progra3.tpgrafos.repositories.MovieRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
public class MovieController {
    
    private final MovieRepository movieRepository;
    
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    
    @PutMapping
    Mono<MovieEntity> createOrUpdateMovie(@RequestBody MovieEntity newMovie) {
        return movieRepository.save(newMovie);
    }
    
    @GetMapping(value = { "", "/" }, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<MovieEntity> getMovies() {
        return movieRepository.findAll();
    }
    
    @GetMapping("/{title}")
    Mono<MovieEntity> getMovieByTitle(@PathVariable String title) {
        return movieRepository.findByTitle(title);
    }
    
    @DeleteMapping("/{title}")
    Mono<Void> deleteMovie(@PathVariable String title) {
        return movieRepository.findByTitle(title)
                .flatMap(movieRepository::delete);
    }
}
