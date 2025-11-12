package com.example.demo.service;

import com.example.demo.model.PersonEntity;
import com.example.demo.model.SimpleMovieGraph;
import com.example.demo.repo.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
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

    /**
     * Construye el grafo a partir de los datos en Neo4j
     */
    public Mono<SimpleMovieGraph> buildGraph() {
        return movieRepository.findAll()
                .collectList()
                .map(movies -> {
                    // Extraer todas las personas únicas de las películas
                    List<PersonEntity> allPersons = movies.stream()
                            .flatMap(movie -> {
                                List<PersonEntity> persons = movie.getActors().stream()
                                        .collect(Collectors.toList());
                                persons.addAll(movie.getDirectors());
                                return persons.stream();
                            })
                            .distinct()
                            .collect(Collectors.toList());

                    // Construir el grafo
                    movieGraph.buildFromEntities(movies, allPersons);
                    return movieGraph;
                });
    }

    /**
     * Obtiene el grafo actual
     */
    public SimpleMovieGraph getGraph() {
        return movieGraph;
    }

    /**
     * Realiza un recorrido DFS desde un nodo específico
     */
    public Mono<List<String>> performDFS(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.dfs(startNodeId));
    }

    /**
     * Realiza un recorrido DFS iterativo desde un nodo específico
     */
    public Mono<List<String>> performDFSIterative(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.dfsIterative(startNodeId));
    }

    /**
     * Realiza un recorrido BFS desde un nodo específico
     */
    public Mono<List<String>> performBFS(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.bfs(startNodeId));
    }

    /**
     * Encuentra el camino más corto entre dos nodos
     */
    public Mono<List<String>> findShortestPath(String startNodeId, String endNodeId) {
        return buildGraph()
                .map(graph -> graph.findShortestPath(startNodeId, endNodeId));
    }

    /**
     * Obtiene información del grafo
     */
    public Mono<Map<String, Object>> getGraphInfo() {
        return buildGraph()
                .map(graph -> Map.of(
                        "nodeCount", graph.getNodeCount(),
                        "edgeCount", graph.getEdgeCount(),
                        "isEmpty", graph.isEmpty()
                ));
    }

    /**
     * Obtiene todos los nodos del grafo
     */
    public Mono<List<String>> getAllNodes() {
        return buildGraph()
                .map(graph -> graph.getAllNodes().stream()
                        .collect(Collectors.toList()));
    }

    /**
     * Obtiene todos los nodos de un tipo específico
     */
    public Mono<List<String>> getNodesByType(String type) {
        return buildGraph()
                .map(graph -> graph.getNodesByType(type));
    }

    /**
     * Obtiene los vecinos de un nodo específico
     */
    public Mono<List<String>> getNeighbors(String nodeId) {
        return buildGraph()
                .map(graph -> graph.getNeighbors(nodeId));
    }

    /**
     * Obtiene todas las aristas del grafo
     */
    public Mono<List<String[]>> getAllEdges() {
        return buildGraph()
                .map(SimpleMovieGraph::getAllEdges);
    }

    /**
     * Obtiene información detallada de un nodo
     */
    public Mono<Map<String, Object>> getNodeInfo(String nodeId) {
        return buildGraph()
                .map(graph -> graph.getNodeInfo(nodeId));
    }

    /**
     * Obtiene la representación en string del grafo
     */
    public Mono<String> getGraphString() {
        return buildGraph()
                .map(SimpleMovieGraph::toString);
    }

    /**
     * Ejecuta el algoritmo de Dijkstra para encontrar el camino más corto con pesos
     */
    public Mono<Map<String, Object>> dijkstra(String startNodeId, String endNodeId) {
        return buildGraph()
                .map(graph -> graph.dijkstra(startNodeId, endNodeId));
    }

    /**
     * Ejecuta el algoritmo de Prim para encontrar el árbol de expansión mínima (MST)
     */
    public Mono<Map<String, Object>> prim(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.prim(startNodeId));
    }

    /**
     * Ejecuta el algoritmo de Kruskal para encontrar el árbol de expansión mínima (MST)
     */
    public Mono<Map<String, Object>> kruskal() {
        return buildGraph()
                .map(SimpleMovieGraph::kruskal);
    }

    // ========================================
    // DIVIDE Y VENCERÁS
    // ========================================

    public Mono<List<String>> quickSortNodes() {
        return buildGraph()
                .map(graph -> {
                    List<String> nodes = new ArrayList<>(graph.getAllNodes());
                    return graph.quickSort(nodes);
                });
    }

    public Mono<List<String>> mergeSortNodes() {
        return buildGraph()
                .map(graph -> {
                    List<String> nodes = new ArrayList<>(graph.getAllNodes());
                    return graph.mergeSort(nodes);
                });
    }

    public Mono<List<Map<String, Object>>> quickSortByDegree() {
        return buildGraph()
                .map(graph -> {
                    List<String> nodes = new ArrayList<>(graph.getAllNodes());
                    return graph.quickSortByDegree(nodes);
                });
    }

    // ========================================
    // ALGORITMOS GREEDY
    // ========================================

    public Mono<List<Map<String, Object>>> greedySelectMoviesWithMostActors(int n) {
        return buildGraph()
                .map(graph -> graph.greedySelectMoviesWithMostActors(n));
    }

    public Mono<List<Map<String, Object>>> greedyFindHubActors(int n) {
        return buildGraph()
                .map(graph -> graph.greedyFindHubActors(n));
    }

    public Mono<Map<String, Object>> greedyCoinChange(int amount) {
        return buildGraph()
                .map(graph -> graph.greedyCoinChange(amount));
    }

    // ========================================
    // PROGRAMACIÓN DINÁMICA
    // ========================================

    public Mono<Map<String, Object>> longestCommonSubsequence(String title1, String title2) {
        return buildGraph()
                .map(graph -> graph.longestCommonSubsequence(title1, title2));
    }

    public Mono<Map<String, Object>> knapsackMovieSelection(List<Map<String, Object>> movies, int maxTime) {
        return buildGraph()
                .map(graph -> graph.knapsackMovieSelection(movies, maxTime));
    }

    public Mono<Map<String, Object>> editDistance(String title1, String title2) {
        return buildGraph()
                .map(graph -> graph.editDistance(title1, title2));
    }

    // ========================================
    // BACKTRACKING
    // ========================================

    public Mono<List<List<String>>> findAllPaths(String start, String end) {
        return buildGraph()
                .map(graph -> graph.findAllPaths(start, end));
    }

    public Mono<Map<String, Object>> graphColoring(int maxColors) {
        return buildGraph()
                .map(graph -> graph.graphColoring(maxColors));
    }

    // ========================================
    // BRANCH & BOUND
    // ========================================

    public Mono<Map<String, Object>> travelingSalesmanProblem(List<String> nodesToVisit) {
        return buildGraph()
                .map(graph -> graph.travelingSalesmanProblem(nodesToVisit));
    }

    public Mono<Map<String, Object>> knapsackBranchAndBound(List<Map<String, Object>> items, int capacity) {
        return buildGraph()
                .map(graph -> graph.knapsackBranchAndBound(items, capacity));
    }
}
