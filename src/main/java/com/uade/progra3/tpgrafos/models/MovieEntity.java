package com.uade.progra3.tpgrafos.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Node("Movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {
    
    @Id
    private String title;
    
    @Property("tagline")
    private String description;
    
    @Property("releaseYear")
    private Integer releaseYear;
    
    @Relationship(type = "ACTED_IN", direction = INCOMING)
    private Set<PersonEntity> actors = new HashSet<>();
    
    @Relationship(type = "DIRECTED", direction = INCOMING)
    private Set<PersonEntity> directors = new HashSet<>();
    
    public MovieEntity(String title, String description, Integer releaseYear) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
    }
    
    public MovieEntity(String title, Integer releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }
}
