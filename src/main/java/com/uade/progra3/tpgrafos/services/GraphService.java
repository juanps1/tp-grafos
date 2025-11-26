package com.uade.progra3.tpgrafos.services;

import com.uade.progra3.tpgrafos.models.PersonEntity;
import com.uade.progra3.tpgrafos.models.SimpleMovieGraph;
import com.uade.progra3.tpgrafos.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GraphService {
    
    private final MovieRepository movieRepository;
    private SimpleMovieGraph movieGraph;
    
    public GraphService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.movieGraph = new SimpleMovieGraph();
    }
    
    public Mono<SimpleMovieGraph> buildGraph() {
        return movieRepository.findAll()
                .collectList()
                .map(movies -> {
                    List<PersonEntity> allPersons = movies.stream()
                            .flatMap(movie -> {
                                List<PersonEntity> persons = movie.getActors().stream()
                                        .collect(Collectors.toList());
                                persons.addAll(movie.getDirectors());
                                return persons.stream();
                            })
                            .distinct()
                            .collect(Collectors.toList());
                    
                    movieGraph = new SimpleMovieGraph();
                    movieGraph.buildFromEntities(movies, allPersons);
                    return movieGraph;
                });
    }
    
    public SimpleMovieGraph getGraph() {
        return movieGraph;
    }
    
    public Mono<List<String>> performDFS(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.dfs(startNodeId));
    }
    
    public Mono<List<String>> performDFSIterative(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.dfsIterative(startNodeId));
    }
    
    public Mono<List<String>> performBFS(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.bfs(startNodeId));
    }
    
    public Mono<List<String>> findShortestPath(String startNodeId, String endNodeId) {
        return buildGraph()
                .map(graph -> graph.findShortestPath(startNodeId, endNodeId));
    }
    
    public Mono<Map<String, Object>> dijkstra(String startNodeId, String endNodeId) {
        return buildGraph()
                .map(graph -> graph.dijkstra(startNodeId, endNodeId));
    }
    
    public Mono<Map<String, Object>> primMST() {
        return buildGraph()
                .map(SimpleMovieGraph::primMST);
    }
    
    public Mono<Map<String, Object>> kruskalMST() {
        return buildGraph()
                .map(SimpleMovieGraph::kruskalMST);
    }
    
    public Mono<Map<String, Object>> getGraphInfo() {
        return buildGraph()
                .map(graph -> Map.of(
                    "nodeCount", graph.getNodeCount(),
                    "edgeCount", graph.getEdgeCount(),
                    "isEmpty", graph.isEmpty()
                ));
    }
    
    public Mono<List<String>> getAllNodes() {
        return buildGraph()
                .map(graph -> graph.getAllNodes().stream()
                        .collect(Collectors.toList()));
    }
    
    public Mono<List<String>> getNodesByType(String type) {
        return buildGraph()
                .map(graph -> graph.getNodesByType(type));
    }
    
    public Mono<List<String>> getNeighbors(String nodeId) {
        return buildGraph()
                .map(graph -> graph.getNeighbors(nodeId));
    }
    
    public Mono<List<SimpleMovieGraph.Edge>> getAllEdges() {
        return buildGraph()
                .map(SimpleMovieGraph::getAllEdges);
    }
    
    public Mono<Map<String, Object>> getNodeInfo(String nodeId) {
        return buildGraph()
                .map(graph -> graph.getNodeInfo(nodeId));
    }
    
    public Mono<String> getGraphString() {
        return buildGraph()
                .map(SimpleMovieGraph::toString);
    }
}
