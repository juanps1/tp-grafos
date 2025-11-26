package com.uade.progra3.tpgrafos.controllers;

import com.uade.progra3.tpgrafos.models.SimpleMovieGraph;
import com.uade.progra3.tpgrafos.services.GraphService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/graph")
public class GraphController {
    
    private final GraphService graphService;
    
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }
    
    @GetMapping("/info")
    public Mono<Map<String, Object>> getGraphInfo() {
        return graphService.getGraphInfo();
    }
    
    @GetMapping("/nodes")
    public Mono<List<String>> getAllNodes() {
        return graphService.getAllNodes();
    }
    
    @GetMapping("/nodes/type/{type}")
    public Mono<List<String>> getNodesByType(@PathVariable String type) {
        return graphService.getNodesByType(type);
    }
    
    @GetMapping("/nodes/{nodeId}/neighbors")
    public Mono<List<String>> getNeighbors(@PathVariable String nodeId) {
        return graphService.getNeighbors(nodeId);
    }
    
    @GetMapping("/edges")
    public Mono<List<SimpleMovieGraph.Edge>> getAllEdges() {
        return graphService.getAllEdges();
    }
    
    @GetMapping("/nodes/{nodeId}/info")
    public Mono<Map<String, Object>> getNodeInfo(@PathVariable String nodeId) {
        return graphService.getNodeInfo(nodeId);
    }
    
    // ===== ALGORITMOS DE BÚSQUEDA =====
    
    @GetMapping("/dfs/{startNodeId}")
    public Mono<List<String>> performDFS(@PathVariable String startNodeId) {
        return graphService.performDFS(startNodeId);
    }
    
    @GetMapping("/dfs-iterative/{startNodeId}")
    public Mono<List<String>> performDFSIterative(@PathVariable String startNodeId) {
        return graphService.performDFSIterative(startNodeId);
    }
    
    @GetMapping("/bfs/{startNodeId}")
    public Mono<List<String>> performBFS(@PathVariable String startNodeId) {
        return graphService.performBFS(startNodeId);
    }
    
    @GetMapping("/shortest-path/{startNodeId}/{endNodeId}")
    public Mono<List<String>> findShortestPath(
            @PathVariable String startNodeId, 
            @PathVariable String endNodeId) {
        return graphService.findShortestPath(startNodeId, endNodeId);
    }
    
    // ===== ALGORITMOS AVANZADOS DE GRAFOS =====
    
    @GetMapping("/dijkstra/{startNodeId}/{endNodeId}")
    public Mono<Map<String, Object>> dijkstra(
            @PathVariable String startNodeId,
            @PathVariable String endNodeId) {
        return graphService.dijkstra(startNodeId, endNodeId);
    }
    
    @GetMapping("/prim")
    public Mono<Map<String, Object>> primMST() {
        return graphService.primMST();
    }
    
    @GetMapping("/kruskal")
    public Mono<Map<String, Object>> kruskalMST() {
        return graphService.kruskalMST();
    }
    
    // ===== UTILIDADES =====
    
    @GetMapping(value = "/string", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> getGraphString() {
        return graphService.getGraphString();
    }
    
    @GetMapping("/compare-traversals/{startNodeId}")
    public Mono<Map<String, Object>> compareTraversals(@PathVariable String startNodeId) {
        return Mono.zip(
                graphService.performDFS(startNodeId),
                graphService.performBFS(startNodeId)
        ).map(tuple -> Map.of(
                "startNode", startNodeId,
                "dfs", tuple.getT1(),
                "bfs", tuple.getT2(),
                "dfsCount", tuple.getT1().size(),
                "bfsCount", tuple.getT2().size()
        ));
    }
}
