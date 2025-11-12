package com.example.demo.controller;

import com.example.demo.service.GraphService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * Obtiene información general del grafo
     */
    @GetMapping("/info")
    public Mono<Map<String, Object>> getGraphInfo() {
        return graphService.getGraphInfo();
    }

    /**
     * Obtiene todos los nodos del grafo
     */
    @GetMapping("/nodes")
    public Mono<List<String>> getAllNodes() {
        return graphService.getAllNodes();
    }

    /**
     * Obtiene nodos por tipo (MOVIE o PERSON)
     */
    @GetMapping("/nodes/type/{type}")
    public Mono<List<String>> getNodesByType(@PathVariable String type) {
        return graphService.getNodesByType(type);
    }

    /**
     * Obtiene los vecinos de un nodo específico
     */
    @GetMapping("/nodes/{nodeId}/neighbors")
    public Mono<List<String>> getNeighbors(@PathVariable String nodeId) {
        return graphService.getNeighbors(nodeId);
    }

    /**
     * Obtiene todas las aristas del grafo
     */
    @GetMapping("/edges")
    public Mono<List<String[]>> getAllEdges() {
        return graphService.getAllEdges();
    }

    /**
     * Obtiene información detallada de un nodo
     */
    @GetMapping("/nodes/{nodeId}/info")
    public Mono<Map<String, Object>> getNodeInfo(@PathVariable String nodeId) {
        return graphService.getNodeInfo(nodeId);
    }

    /**
     * Realiza un recorrido DFS (Depth-First Search) recursivo
     */
    @GetMapping("/dfs/{startNodeId}")
    public Mono<List<String>> performDFS(@PathVariable String startNodeId) {
        return graphService.performDFS(startNodeId);
    }

    /**
     * Realiza un recorrido DFS iterativo
     */
    @GetMapping("/dfs-iterative/{startNodeId}")
    public Mono<List<String>> performDFSIterative(@PathVariable String startNodeId) {
        return graphService.performDFSIterative(startNodeId);
    }

    /**
     * Realiza un recorrido BFS (Breadth-First Search)
     */
    @GetMapping("/bfs/{startNodeId}")
    public Mono<List<String>> performBFS(@PathVariable String startNodeId) {
        return graphService.performBFS(startNodeId);
    }

    /**
     * Encuentra el camino más corto entre dos nodos
     */
    @GetMapping("/shortest-path/{startNodeId}/{endNodeId}")
    public Mono<List<String>> findShortestPath(
            @PathVariable String startNodeId,
            @PathVariable String endNodeId) {
        return graphService.findShortestPath(startNodeId, endNodeId);
    }

    /**
     * Obtiene la representación en string del grafo
     */
    @GetMapping(value = "/string", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> getGraphString() {
        return graphService.getGraphString();
    }

    /**
     * Endpoint para comparar DFS vs BFS desde el mismo nodo
     */
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

    /**
     * Algoritmo de Dijkstra - Encuentra el camino más corto con pesos
     * Ejemplo: /graph/dijkstra/Speed/The Matrix
     */
    @GetMapping("/dijkstra/{startNodeId}/{endNodeId}")
    public Mono<Map<String, Object>> dijkstra(
            @PathVariable String startNodeId,
            @PathVariable String endNodeId) {
        return graphService.dijkstra(startNodeId, endNodeId);
    }

    /**
     * Algoritmo de Prim - Encuentra el árbol de expansión mínima (MST)
     * Ejemplo: /graph/prim/Speed
     */
    @GetMapping("/prim/{startNodeId}")
    public Mono<Map<String, Object>> prim(@PathVariable String startNodeId) {
        return graphService.prim(startNodeId);
    }

    /**
     * Algoritmo de Kruskal - Encuentra el árbol de expansión mínima (MST)
     * Ejemplo: /graph/kruskal
     */
    @GetMapping("/kruskal")
    public Mono<Map<String, Object>> kruskal() {
        return graphService.kruskal();
    }

    // ========================================
    // DIVIDE Y VENCERÁS - QUICKSORT & MERGESORT
    // ========================================

    /**
     * QuickSort - Ordena todos los nodos alfabéticamente
     */
    @GetMapping("/quicksort")
    public Mono<List<String>> quickSort() {
        return graphService.quickSortNodes();
    }

    /**
     * MergeSort - Ordena todos los nodos alfabéticamente
     */
    @GetMapping("/mergesort")
    public Mono<List<String>> mergeSort() {
        return graphService.mergeSortNodes();
    }

    /**
     * QuickSort por grado - Ordena nodos por número de conexiones
     */
    @GetMapping("/quicksort-by-degree")
    public Mono<List<Map<String, Object>>> quickSortByDegree() {
        return graphService.quickSortByDegree();
    }

    // ========================================
    // ALGORITMOS GREEDY
    // ========================================

    /**
     * Greedy - Selecciona las N películas con más actores
     * Ejemplo: /graph/greedy/top-movies/5
     */
    @GetMapping("/greedy/top-movies/{n}")
    public Mono<List<Map<String, Object>>> greedyTopMovies(@PathVariable int n) {
        return graphService.greedySelectMoviesWithMostActors(n);
    }

    /**
     * Greedy - Encuentra los actores más conectados (hub actors)
     * Ejemplo: /graph/greedy/hub-actors/5
     */
    @GetMapping("/greedy/hub-actors/{n}")
    public Mono<List<Map<String, Object>>> greedyHubActors(@PathVariable int n) {
        return graphService.greedyFindHubActors(n);
    }

    /**
     * Greedy - Problema del cambio de monedas
     * Ejemplo: /graph/greedy/coin-change/237
     */
    @GetMapping("/greedy/coin-change/{amount}")
    public Mono<Map<String, Object>> greedyCoinChange(@PathVariable int amount) {
        return graphService.greedyCoinChange(amount);
    }

    // ========================================
    // PROGRAMACIÓN DINÁMICA
    // ========================================

    /**
     * DP - Subsecuencia común más larga entre dos títulos
     * Ejemplo: /graph/dp/lcs/The Matrix/The Matrix Reloaded
     */
    @GetMapping("/dp/lcs/{title1}/{title2}")
    public Mono<Map<String, Object>> longestCommonSubsequence(
            @PathVariable String title1,
            @PathVariable String title2) {
        return graphService.longestCommonSubsequence(title1, title2);
    }

    /**
     * DP - Distancia de edición entre dos títulos
     * Ejemplo: /graph/dp/edit-distance/Speed/The Matrix
     */
    @GetMapping("/dp/edit-distance/{title1}/{title2}")
    public Mono<Map<String, Object>> editDistance(
            @PathVariable String title1,
            @PathVariable String title2) {
        return graphService.editDistance(title1, title2);
    }

    // ========================================
    // BACKTRACKING
    // ========================================

    /**
     * Backtracking - Encuentra TODOS los caminos entre dos nodos
     * Ejemplo: /graph/backtracking/all-paths/Speed/The Matrix
     */
    @GetMapping("/backtracking/all-paths/{start}/{end}")
    public Mono<List<List<String>>> findAllPaths(
            @PathVariable String start,
            @PathVariable String end) {
        return graphService.findAllPaths(start, end);
    }

    /**
     * Backtracking - Coloreo de grafo
     * Ejemplo: /graph/backtracking/coloring/3
     */
    @GetMapping("/backtracking/coloring/{maxColors}")
    public Mono<Map<String, Object>> graphColoring(@PathVariable int maxColors) {
        return graphService.graphColoring(maxColors);
    }
}

//
// EJEMPLOS DE USO - TODOS LOS ENDPOINTS:
//
// === BÁSICOS ===
// http://localhost:8080/graph/info
// http://localhost:8080/graph/nodes
// http://localhost:8080/graph/bfs/Speed
// http://localhost:8080/graph/dfs/Speed
//
// === DIJKSTRA, PRIM, KRUSKAL ===
// http://localhost:8080/graph/dijkstra/Speed/The Matrix
// http://localhost:8080/graph/prim/Speed
// http://localhost:8080/graph/kruskal
//
// === DIVIDE Y VENCERÁS ===
// http://localhost:8080/graph/quicksort
// http://localhost:8080/graph/mergesort
// http://localhost:8080/graph/quicksort-by-degree
//
// === GREEDY ===
// http://localhost:8080/graph/greedy/top-movies/5
// http://localhost:8080/graph/greedy/hub-actors/5
// http://localhost:8080/graph/greedy/coin-change/237
//
// === PROGRAMACIÓN DINÁMICA ===
// http://localhost:8080/graph/dp/lcs/The Matrix/The Matrix Reloaded
// http://localhost:8080/graph/dp/edit-distance/Speed/The Matrix
//
// === BACKTRACKING ===
// http://localhost:8080/graph/backtracking/all-paths/Speed/The Matrix
// http://localhost:8080/graph/backtracking/coloring/3
//