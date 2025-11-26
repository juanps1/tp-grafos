package com.uade.progra3.tpgrafos.config;

import com.uade.progra3.tpgrafos.models.MovieEntity;
import com.uade.progra3.tpgrafos.models.PersonEntity;
import com.uade.progra3.tpgrafos.repositories.MovieRepository;
import com.uade.progra3.tpgrafos.repositories.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.Set;

@Configuration
public class DataLoader {
    
    @Bean
    CommandLineRunner initDatabase(MovieRepository movieRepository, PersonRepository personRepository) {
        return args -> {
            // Limpiar base de datos de forma reactiva
            movieRepository.deleteAll()
                    .then(personRepository.deleteAll())
                    .thenMany(createPersons(personRepository))
                    .collectList()
                    .flatMapMany(persons -> createMovies(movieRepository, persons))
                    .collectList()
                    .doOnSuccess(movies -> {
                        System.out.println("✅ Base de datos inicializada con películas y personas");
                    })
                    .flatMap(movies -> movieRepository.count()
                            .zipWith(personRepository.count())
                            .doOnNext(counts -> {
                                System.out.println("📊 Películas: " + counts.getT1());
                                System.out.println("👥 Personas: " + counts.getT2());
                            })
                    )
                    .block(); // Block para CommandLineRunner
        };
    }
    
    private Flux<PersonEntity> createPersons(PersonRepository personRepository) {
        PersonEntity keanu = new PersonEntity("Keanu Reeves");
        PersonEntity laurence = new PersonEntity("Laurence Fishburne");
        PersonEntity carrieAnne = new PersonEntity("Carrie-Anne Moss");
        PersonEntity lanaWachowski = new PersonEntity("Lana Wachowski");
        PersonEntity lillyWachowski = new PersonEntity("Lilly Wachowski");
        PersonEntity janDeBont = new PersonEntity("Jan de Bont");
        
        PersonEntity tomHanks = new PersonEntity("Tom Hanks");
        PersonEntity megRyan = new PersonEntity("Meg Ryan");
        PersonEntity noraEphron = new PersonEntity("Nora Ephron");
        
        PersonEntity alPacino = new PersonEntity("Al Pacino");
        PersonEntity robertDuvall = new PersonEntity("Robert Duvall");
        PersonEntity marlonBrando = new PersonEntity("Marlon Brando");
        PersonEntity francisFordCoppola = new PersonEntity("Francis Ford Coppola");
        
        return Flux.just(
                keanu, laurence, carrieAnne, lanaWachowski, lillyWachowski, janDeBont,
                tomHanks, megRyan, noraEphron,
                alPacino, robertDuvall, marlonBrando, francisFordCoppola
        ).flatMap(personRepository::save);
    }
    
    private Flux<MovieEntity> createMovies(MovieRepository movieRepository, java.util.List<PersonEntity> allPersons) {
        // Buscar personas por nombre
        PersonEntity keanu = findPerson(allPersons, "Keanu Reeves");
        PersonEntity laurence = findPerson(allPersons, "Laurence Fishburne");
        PersonEntity carrieAnne = findPerson(allPersons, "Carrie-Anne Moss");
        PersonEntity lanaWachowski = findPerson(allPersons, "Lana Wachowski");
        PersonEntity lillyWachowski = findPerson(allPersons, "Lilly Wachowski");
        PersonEntity janDeBont = findPerson(allPersons, "Jan de Bont");
        
        PersonEntity tomHanks = findPerson(allPersons, "Tom Hanks");
        PersonEntity megRyan = findPerson(allPersons, "Meg Ryan");
        PersonEntity noraEphron = findPerson(allPersons, "Nora Ephron");
        
        PersonEntity alPacino = findPerson(allPersons, "Al Pacino");
        PersonEntity robertDuvall = findPerson(allPersons, "Robert Duvall");
        PersonEntity marlonBrando = findPerson(allPersons, "Marlon Brando");
        PersonEntity francisFordCoppola = findPerson(allPersons, "Francis Ford Coppola");
        
        // Crear películas
        MovieEntity matrix = new MovieEntity("The Matrix", 1999);
        matrix.setActors(Set.of(keanu, laurence, carrieAnne));
        matrix.setDirectors(Set.of(lanaWachowski, lillyWachowski));
        
        MovieEntity speed = new MovieEntity("Speed", 1994);
        speed.setActors(Set.of(keanu));
        speed.setDirectors(Set.of(janDeBont));
        
        MovieEntity sleeplessInSeattle = new MovieEntity("Sleepless in Seattle", 1993);
        sleeplessInSeattle.setActors(Set.of(tomHanks, megRyan));
        sleeplessInSeattle.setDirectors(Set.of(noraEphron));
        
        MovieEntity youveGotMail = new MovieEntity("You've Got Mail", 1998);
        youveGotMail.setActors(Set.of(tomHanks, megRyan));
        youveGotMail.setDirectors(Set.of(noraEphron));
        
        MovieEntity godfather = new MovieEntity("The Godfather", 1972);
        godfather.setActors(Set.of(alPacino, robertDuvall, marlonBrando));
        godfather.setDirectors(Set.of(francisFordCoppola));
        
        return Flux.just(matrix, speed, sleeplessInSeattle, youveGotMail, godfather)
                .flatMap(movieRepository::save);
    }
    
    private PersonEntity findPerson(java.util.List<PersonEntity> persons, String name) {
        return persons.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Person not found: " + name));
    }
}
