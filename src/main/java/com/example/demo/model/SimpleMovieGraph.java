package com.example.demo.model;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleMovieGraph {

    private Map<String, List<String>> adjacencyList;

    // Mapa para saber el tipo de cada nodo: "MOVIE" o "PERSON"
    private Map<String, String> nodeTypes;

    // Lista de aristas con información de relación: [from, to, relationshipType]
    private List<String[]> edges;
    
    // Mapa para almacenar pesos de las aristas: "from->to" -> peso
    private Map<String, Integer> edgeWeights;

    public SimpleMovieGraph() {
        this.adjacencyList = new HashMap<>();
        this.nodeTypes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.edgeWeights = new HashMap<>();
    }

    public void addNode(String nodeId, String type) {
        adjacencyList.putIfAbsent(nodeId, new ArrayList<>());
        nodeTypes.put(nodeId, type);
    }

    public void addEdge(String fromId, String toId, String relationshipType) {
        addEdge(fromId, toId, relationshipType, 1); // Peso por defecto = 1
    }

    public void addEdge(String fromId, String toId, String relationshipType, int weight) {
        // Asegurar que ambos nodos existan
        if (!adjacencyList.containsKey(fromId)) {
            adjacencyList.put(fromId, new ArrayList<>());
        }
        if (!adjacencyList.containsKey(toId)) {
            adjacencyList.put(toId, new ArrayList<>());
        }

        // Agregar la arista a la lista de adyacencia solo si no existe
        List<String> neighbors = adjacencyList.get(fromId);
        if (!neighbors.contains(toId)) {
            neighbors.add(toId);
        }

        // Agregar la arista a la lista de aristas solo si no existe
        String[] newEdge = new String[]{fromId, toId, relationshipType};
        boolean edgeExists = edges.stream()
                .anyMatch(edge -> edge[0].equals(fromId) && edge[1].equals(toId) && edge[2].equals(relationshipType));

        if (!edgeExists) {
            edges.add(newEdge);
        }
        
        // Guardar el peso de la arista
        String edgeKey = fromId + "->" + toId;
        edgeWeights.put(edgeKey, weight);
    }

    /**
     * Construye el grafo a partir de entidades MovieEntity y PersonEntity
     */
    public void buildFromEntities(List<MovieEntity> movies, List<PersonEntity> persons) {
        // Agregar todos los nodos de películas
        for (MovieEntity movie : movies) {
            addNode(movie.getTitle(), "MOVIE");
        }

        // Agregar todos los nodos de personas
        for (PersonEntity person : persons) {
            addNode(person.getName(), "PERSON");
        }

        // Agregar las relaciones (BIDIRECCIONALES)
        for (MovieEntity movie : movies) {
            String movieId = movie.getTitle();

            // Relaciones ACTED_IN (Person <-> Movie) - BIDIRECCIONAL
            for (PersonEntity actor : movie.getActors()) {
                String actorId = actor.getName();
                addEdge(actorId, movieId, "ACTED_IN");
                addEdge(movieId, actorId, "ACTED_BY"); // Relación inversa
            }

            // Relaciones DIRECTED (Person <-> Movie) - BIDIRECCIONAL
            for (PersonEntity director : movie.getDirectors()) {
                String directorId = director.getName();
                addEdge(directorId, movieId, "DIRECTED");
                addEdge(movieId, directorId, "DIRECTED_BY"); // Relación inversa
            }
        }
    }

    /**
     * Algoritmo DFS (Depth-First Search) - Recursivo
     */
    public List<String> dfs(String startNodeId) {
        if (!adjacencyList.containsKey(startNodeId)) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        dfsRecursive(startNodeId, visited, result);
        return result;
    }

    private void dfsRecursive(String currentNodeId, Set<String> visited, List<String> result) {
        visited.add(currentNodeId);
        result.add(currentNodeId);

        // Visitar todos los nodos adyacentes no visitados
        List<String> neighbors = adjacencyList.get(currentNodeId);
        if (neighbors != null) {
            for (String neighborId : neighbors) {
                if (!visited.contains(neighborId)) {
                    dfsRecursive(neighborId, visited, result);
                }
            }
        }
    }

    /**
     * Algoritmo DFS iterativo usando pila
     */
    public List<String> dfsIterative(String startNodeId) {
        if (!adjacencyList.containsKey(startNodeId)) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();

        stack.push(startNodeId);

        while (!stack.isEmpty()) {
            String currentNodeId = stack.pop();

            if (!visited.contains(currentNodeId)) {
                visited.add(currentNodeId);
                result.add(currentNodeId);

                // Agregar vecinos no visitados a la pila
                List<String> neighbors = adjacencyList.get(currentNodeId);
                if (neighbors != null) {
                    for (String neighborId : neighbors) {
                        if (!visited.contains(neighborId)) {
                            stack.push(neighborId);
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Algoritmo BFS (Breadth-First Search)
     */
    public List<String> bfs(String startNodeId) {
        if (!adjacencyList.containsKey(startNodeId)) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.offer(startNodeId);
        visited.add(startNodeId);

        while (!queue.isEmpty()) {
            String currentNodeId = queue.poll();
            result.add(currentNodeId);

            // Agregar todos los vecinos no visitados a la cola
            List<String> neighbors = adjacencyList.get(currentNodeId);
            if (neighbors != null) {
                for (String neighborId : neighbors) {
                    if (!visited.contains(neighborId)) {
                        visited.add(neighborId);
                        queue.offer(neighborId);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Encuentra el camino más corto entre dos nodos usando BFS
     */
    public List<String> findShortestPath(String startNodeId, String endNodeId) {
        if (!adjacencyList.containsKey(startNodeId) || !adjacencyList.containsKey(endNodeId)) {
            return new ArrayList<>();
        }

        Map<String, String> parent = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(startNodeId);
        visited.add(startNodeId);
        parent.put(startNodeId, null);

        while (!queue.isEmpty()) {
            String currentNodeId = queue.poll();

            if (currentNodeId.equals(endNodeId)) {
                // Reconstruir el camino
                return reconstructPath(parent, startNodeId, endNodeId);
            }

            List<String> neighbors = adjacencyList.get(currentNodeId);
            if (neighbors != null) {
                for (String neighborId : neighbors) {
                    if (!visited.contains(neighborId)) {
                        visited.add(neighborId);
                        parent.put(neighborId, currentNodeId);
                        queue.offer(neighborId);
                    }
                }
            }
        }

        return new ArrayList<>(); // No hay camino
    }

    private List<String> reconstructPath(Map<String, String> parent, String start, String end) {
        List<String> path = new ArrayList<>();
        String current = end;

        while (current != null) {
            path.add(0, current); // Agregar al inicio
            current = parent.get(current);
        }

        return path;
    }

    /**
     * Algoritmo de Dijkstra - Encuentra el camino más corto considerando pesos
     * Retorna un mapa con: "path" (lista de nodos), "distance" (distancia total), "distances" (distancias a todos los nodos)
     */
    public Map<String, Object> dijkstra(String startNodeId, String endNodeId) {
        Map<String, Object> result = new HashMap<>();
        
        if (!adjacencyList.containsKey(startNodeId) || !adjacencyList.containsKey(endNodeId)) {
            result.put("path", new ArrayList<String>());
            result.put("distance", Integer.MAX_VALUE);
            result.put("distances", new HashMap<String, Integer>());
            return result;
        }

        // Distancias mínimas desde el nodo inicial
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();
        
        // Inicializar todas las distancias como infinito
        for (String node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(startNodeId, 0);

        // Cola de prioridad: [nodo, distancia]
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
            Map.Entry.comparingByValue()
        );
        pq.offer(new AbstractMap.SimpleEntry<>(startNodeId, 0));

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> current = pq.poll();
            String currentNode = current.getKey();
            int currentDistance = current.getValue();

            if (visited.contains(currentNode)) {
                continue;
            }
            
            visited.add(currentNode);

            // Si llegamos al nodo destino, podemos terminar
            if (currentNode.equals(endNodeId)) {
                break;
            }

            // Explorar vecinos
            List<String> neighbors = adjacencyList.get(currentNode);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        String edgeKey = currentNode + "->" + neighbor;
                        int weight = edgeWeights.getOrDefault(edgeKey, 1);
                        int newDistance = currentDistance + weight;

                        if (newDistance < distances.get(neighbor)) {
                            distances.put(neighbor, newDistance);
                            previous.put(neighbor, currentNode);
                            pq.offer(new AbstractMap.SimpleEntry<>(neighbor, newDistance));
                        }
                    }
                }
            }
        }

        // Reconstruir el camino
        List<String> path = new ArrayList<>();
        String current = endNodeId;
        
        if (previous.containsKey(endNodeId) || endNodeId.equals(startNodeId)) {
            while (current != null) {
                path.add(0, current);
                current = previous.get(current);
            }
        }

        result.put("path", path);
        result.put("distance", distances.get(endNodeId));
        result.put("distances", distances);
        return result;
    }

    /**
     * Algoritmo de Prim - Encuentra el árbol de expansión mínima (MST)
     * Retorna una lista de aristas que forman el MST
     */
    public Map<String, Object> prim(String startNodeId) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> mstEdges = new ArrayList<>();
        int totalWeight = 0;

        if (!adjacencyList.containsKey(startNodeId)) {
            result.put("edges", mstEdges);
            result.put("totalWeight", totalWeight);
            return result;
        }

        Set<String> visited = new HashSet<>();
        PriorityQueue<EdgeInfo> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        // Comenzar desde el nodo inicial
        visited.add(startNodeId);
        addEdgesToPQ(startNodeId, pq, visited);

        while (!pq.isEmpty() && visited.size() < adjacencyList.size()) {
            EdgeInfo edge = pq.poll();

            if (visited.contains(edge.to)) {
                continue;
            }

            // Agregar la arista al MST
            visited.add(edge.to);
            Map<String, Object> edgeInfo = new HashMap<>();
            edgeInfo.put("from", edge.from);
            edgeInfo.put("to", edge.to);
            edgeInfo.put("weight", edge.weight);
            mstEdges.add(edgeInfo);
            totalWeight += edge.weight;

            // Agregar nuevas aristas al PQ
            addEdgesToPQ(edge.to, pq, visited);
        }

        result.put("edges", mstEdges);
        result.put("totalWeight", totalWeight);
        result.put("nodeCount", visited.size());
        return result;
    }

    private void addEdgesToPQ(String node, PriorityQueue<EdgeInfo> pq, Set<String> visited) {
        List<String> neighbors = adjacencyList.get(node);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    String edgeKey = node + "->" + neighbor;
                    int weight = edgeWeights.getOrDefault(edgeKey, 1);
                    pq.offer(new EdgeInfo(node, neighbor, weight));
                }
            }
        }
    }

    /**
     * Algoritmo de Kruskal - Encuentra el árbol de expansión mínima (MST)
     * Retorna una lista de aristas que forman el MST
     */
    public Map<String, Object> kruskal() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> mstEdges = new ArrayList<>();
        int totalWeight = 0;

        // Crear lista de todas las aristas con sus pesos
        List<EdgeInfo> allEdges = new ArrayList<>();
        for (String[] edge : edges) {
            String from = edge[0];
            String to = edge[1];
            String edgeKey = from + "->" + to;
            int weight = edgeWeights.getOrDefault(edgeKey, 1);
            allEdges.add(new EdgeInfo(from, to, weight));
        }

        // Ordenar aristas por peso
        allEdges.sort(Comparator.comparingInt(e -> e.weight));

        // Union-Find para detectar ciclos
        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> rank = new HashMap<>();

        // Inicializar Union-Find
        for (String node : adjacencyList.keySet()) {
            parent.put(node, node);
            rank.put(node, 0);
        }

        // Procesar aristas en orden de peso
        for (EdgeInfo edge : allEdges) {
            String root1 = find(parent, edge.from);
            String root2 = find(parent, edge.to);

            // Si no forman ciclo, agregar al MST
            if (!root1.equals(root2)) {
                Map<String, Object> edgeInfo = new HashMap<>();
                edgeInfo.put("from", edge.from);
                edgeInfo.put("to", edge.to);
                edgeInfo.put("weight", edge.weight);
                mstEdges.add(edgeInfo);
                totalWeight += edge.weight;

                // Unir los conjuntos
                union(parent, rank, root1, root2);
            }
        }

        result.put("edges", mstEdges);
        result.put("totalWeight", totalWeight);
        result.put("edgeCount", mstEdges.size());
        return result;
    }

    // Union-Find: Find con compresión de camino
    private String find(Map<String, String> parent, String node) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent, parent.get(node)));
        }
        return parent.get(node);
    }

    // Union-Find: Union por rango
    private void union(Map<String, String> parent, Map<String, Integer> rank, String root1, String root2) {
        if (rank.get(root1) > rank.get(root2)) {
            parent.put(root2, root1);
        } else if (rank.get(root1) < rank.get(root2)) {
            parent.put(root1, root2);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank.get(root1) + 1);
        }
    }

    // Clase auxiliar para representar aristas con peso
    private static class EdgeInfo {
        String from;
        String to;
        int weight;

        EdgeInfo(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    /**
     * Obtiene el peso de una arista
     */
    public int getEdgeWeight(String from, String to) {
        String edgeKey = from + "->" + to;
        return edgeWeights.getOrDefault(edgeKey, 1);
    }

    // ========================================
    // ALGORITMOS DE DIVIDE Y VENCERÁS
    // ========================================

    /**
     * QUICKSORT - Ordena una lista de nodos alfabéticamente
     * Divide y vencerás usando un pivote
     */
    public List<String> quickSort(List<String> nodes) {
        List<String> copy = new ArrayList<>(nodes);
        quickSortHelper(copy, 0, copy.size() - 1);
        return copy;
    }

    private void quickSortHelper(List<String> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSortHelper(list, low, pivotIndex - 1);
            quickSortHelper(list, pivotIndex + 1, high);
        }
    }

    private int partition(List<String> list, int low, int high) {
        String pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).compareToIgnoreCase(pivot) <= 0) {
                i++;
                String temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        String temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    /**
     * MERGESORT - Ordena una lista de nodos alfabéticamente
     * Divide y vencerás dividiendo en mitades
     */
    public List<String> mergeSort(List<String> nodes) {
        if (nodes.size() <= 1) {
            return new ArrayList<>(nodes);
        }

        int mid = nodes.size() / 2;
        List<String> left = mergeSort(nodes.subList(0, mid));
        List<String> right = mergeSort(nodes.subList(mid, nodes.size()));

        return merge(left, right);
    }

    private List<String> merge(List<String> left, List<String> right) {
        List<String> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareToIgnoreCase(right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }

        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }

    /**
     * Ordena nodos por número de conexiones (grado) usando QuickSort
     */
    public List<Map<String, Object>> quickSortByDegree(List<String> nodes) {
        List<Map<String, Object>> nodeInfo = nodes.stream()
                .map(node -> {
                    Map<String, Object> info = new HashMap<>();
                    info.put("node", node);
                    info.put("degree", getNeighbors(node).size());
                    info.put("type", getNodeType(node));
                    return info;
                })
                .collect(Collectors.toList());

        quickSortByDegreeHelper(nodeInfo, 0, nodeInfo.size() - 1);
        return nodeInfo;
    }

    private void quickSortByDegreeHelper(List<Map<String, Object>> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionByDegree(list, low, high);
            quickSortByDegreeHelper(list, low, pivotIndex - 1);
            quickSortByDegreeHelper(list, pivotIndex + 1, high);
        }
    }

    private int partitionByDegree(List<Map<String, Object>> list, int low, int high) {
        int pivot = (int) list.get(high).get("degree");
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if ((int) list.get(j).get("degree") >= pivot) { // Orden descendente
                i++;
                Map<String, Object> temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        Map<String, Object> temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    // ========================================
    // ALGORITMOS GREEDY (CODICIOSOS)
    // ========================================

    /**
     * GREEDY - Selecciona las N películas con más actores
     */
    public List<Map<String, Object>> greedySelectMoviesWithMostActors(int n) {
        List<String> movies = getNodesByType("MOVIE");
        List<Map<String, Object>> movieInfo = new ArrayList<>();

        for (String movie : movies) {
            int actorCount = (int) getNeighbors(movie).stream()
                    .filter(neighbor -> "PERSON".equals(getNodeType(neighbor)))
                    .count();

            Map<String, Object> info = new HashMap<>();
            info.put("movie", movie);
            info.put("actorCount", actorCount);
            movieInfo.add(info);
        }

        // Ordenar por número de actores (descendente)
        movieInfo.sort((a, b) -> Integer.compare((int) b.get("actorCount"), (int) a.get("actorCount")));

        return movieInfo.stream().limit(n).collect(Collectors.toList());
    }

    /**
     * GREEDY - Encuentra los actores más conectados (hub actors)
     */
    public List<Map<String, Object>> greedyFindHubActors(int n) {
        List<String> persons = getNodesByType("PERSON");
        List<Map<String, Object>> personInfo = new ArrayList<>();

        for (String person : persons) {
            int movieCount = (int) getNeighbors(person).stream()
                    .filter(neighbor -> "MOVIE".equals(getNodeType(neighbor)))
                    .count();

            Map<String, Object> info = new HashMap<>();
            info.put("actor", person);
            info.put("movieCount", movieCount);
            info.put("totalConnections", getNeighbors(person).size());
            personInfo.add(info);
        }

        // Ordenar por número de películas (descendente)
        personInfo.sort((a, b) -> Integer.compare((int) b.get("movieCount"), (int) a.get("movieCount")));

        return personInfo.stream().limit(n).collect(Collectors.toList());
    }

    /**
     * GREEDY - Problema del cambio de monedas aplicado a "presupuestos de películas"
     * Simula distribuir un presupuesto usando la menor cantidad de "billetes"
     */
    public Map<String, Object> greedyCoinChange(int amount) {
        int[] denominations = {100, 50, 20, 10, 5, 1}; // Millones de dólares
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Integer>> coins = new ArrayList<>();
        int remaining = amount;

        for (int denom : denominations) {
            if (remaining >= denom) {
                int count = remaining / denom;
                remaining = remaining % denom;

                Map<String, Integer> coin = new HashMap<>();
                coin.put("denomination", denom);
                coin.put("count", count);
                coins.add(coin);
            }
        }

        result.put("amount", amount);
        result.put("coins", coins);
        result.put("totalCoins", coins.stream().mapToInt(c -> c.get("count")).sum());
        return result;
    }

    // ========================================
    // PROGRAMACIÓN DINÁMICA
    // ========================================

    /**
     * PROGRAMACIÓN DINÁMICA - Subsecuencia común más larga (LCS) entre dos títulos
     */
    public Map<String, Object> longestCommonSubsequence(String title1, String title2) {
        int m = title1.length();
        int n = title2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Llenar la tabla DP
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (title1.charAt(i - 1) == title2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Reconstruir la LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (title1.charAt(i - 1) == title2.charAt(j - 1)) {
                lcs.insert(0, title1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("title1", title1);
        result.put("title2", title2);
        result.put("lcs", lcs.toString());
        result.put("length", dp[m][n]);
        result.put("similarity", (double) dp[m][n] / Math.max(m, n));
        return result;
    }

    /**
     * PROGRAMACIÓN DINÁMICA - Problema de la mochila 0/1 aplicado a selección de películas
     * Maximiza el valor total sin exceder el tiempo disponible
     */
    public Map<String, Object> knapsackMovieSelection(List<Map<String, Object>> movies, int maxTime) {
        int n = movies.size();
        int[][] dp = new int[n + 1][maxTime + 1];

        // Llenar la tabla DP
        for (int i = 1; i <= n; i++) {
            Map<String, Object> movie = movies.get(i - 1);
            int time = (int) movie.get("duration");
            int value = (int) movie.get("rating");

            for (int w = 0; w <= maxTime; w++) {
                if (time <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - time] + value);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Reconstruir las películas seleccionadas
        List<String> selectedMovies = new ArrayList<>();
        int w = maxTime;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                Map<String, Object> movie = movies.get(i - 1);
                selectedMovies.add((String) movie.get("title"));
                w -= (int) movie.get("duration");
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("maxTime", maxTime);
        result.put("selectedMovies", selectedMovies);
        result.put("totalValue", dp[n][maxTime]);
        result.put("movieCount", selectedMovies.size());
        return result;
    }

    /**
     * PROGRAMACIÓN DINÁMICA - Distancia de edición (Levenshtein) entre títulos
     */
    public Map<String, Object> editDistance(String title1, String title2) {
        int m = title1.length();
        int n = title2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Inicializar
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        // Llenar la tabla DP
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (title1.charAt(i - 1) == title2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("title1", title1);
        result.put("title2", title2);
        result.put("distance", dp[m][n]);
        result.put("similarity", 1.0 - ((double) dp[m][n] / Math.max(m, n)));
        return result;
    }

    // ========================================
    // BACKTRACKING
    // ========================================

    /**
     * BACKTRACKING - Encuentra TODOS los caminos posibles entre dos nodos
     */
    public List<List<String>> findAllPaths(String start, String end) {
        List<List<String>> allPaths = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        currentPath.add(start);
        visited.add(start);

        findAllPathsHelper(start, end, visited, currentPath, allPaths);

        return allPaths;
    }

    private void findAllPathsHelper(String current, String end, Set<String> visited,
                                    List<String> currentPath, List<List<String>> allPaths) {
        if (current.equals(end)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        List<String> neighbors = getNeighbors(current);
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                currentPath.add(neighbor);

                findAllPathsHelper(neighbor, end, visited, currentPath, allPaths);

                // Backtrack
                currentPath.remove(currentPath.size() - 1);
                visited.remove(neighbor);
            }
        }
    }

    /**
     * BACKTRACKING - Coloreo de grafo (Graph Coloring)
     * Asigna colores a nodos de manera que nodos adyacentes tengan colores diferentes
     */
    public Map<String, Object> graphColoring(int maxColors) {
        List<String> nodes = new ArrayList<>(adjacencyList.keySet());
        Map<String, Integer> nodeColors = new HashMap<>();

        boolean result = graphColoringHelper(nodes, 0, maxColors, nodeColors);

        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        response.put("colorsUsed", maxColors);
        response.put("nodeColors", nodeColors);
        response.put("colorGroups", groupNodesByColor(nodeColors));
        return response;
    }

    private boolean graphColoringHelper(List<String> nodes, int nodeIndex, int maxColors,
                                       Map<String, Integer> nodeColors) {
        if (nodeIndex == nodes.size()) {
            return true; // Todos los nodos están coloreados
        }

        String currentNode = nodes.get(nodeIndex);

        for (int color = 1; color <= maxColors; color++) {
            if (isSafeColor(currentNode, color, nodeColors)) {
                nodeColors.put(currentNode, color);

                if (graphColoringHelper(nodes, nodeIndex + 1, maxColors, nodeColors)) {
                    return true;
                }

                // Backtrack
                nodeColors.remove(currentNode);
            }
        }

        return false;
    }

    private boolean isSafeColor(String node, int color, Map<String, Integer> nodeColors) {
        List<String> neighbors = getNeighbors(node);
        for (String neighbor : neighbors) {
            if (nodeColors.containsKey(neighbor) && nodeColors.get(neighbor) == color) {
                return false;
            }
        }
        return true;
    }

    private Map<Integer, List<String>> groupNodesByColor(Map<String, Integer> nodeColors) {
        Map<Integer, List<String>> groups = new HashMap<>();
        for (Map.Entry<String, Integer> entry : nodeColors.entrySet()) {
            groups.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
        }
        return groups;
    }

    // ========================================
    // BRANCH & BOUND
    // ========================================

    /**
     * BRANCH & BOUND - Problema del viajante (TSP) aplicado a visitar películas
     * Encuentra la ruta más corta para visitar todas las películas especificadas
     */
    public Map<String, Object> travelingSalesmanProblem(List<String> nodesToVisit) {
        if (nodesToVisit.isEmpty()) {
            return Map.of("path", new ArrayList<>(), "cost", 0);
        }

        int n = nodesToVisit.size();
        int[][] costMatrix = buildCostMatrix(nodesToVisit);

        // Estado inicial
        TSPState initialState = new TSPState();
        initialState.visited = new boolean[n];
        initialState.visited[0] = true;
        initialState.currentNode = 0;
        initialState.cost = 0;
        initialState.level = 1;
        initialState.path = new ArrayList<>();
        initialState.path.add(nodesToVisit.get(0));

        // Variables para el mejor resultado
        TSPState bestState = new TSPState();
        bestState.cost = Integer.MAX_VALUE;

        // Branch & Bound
        PriorityQueue<TSPState> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
        pq.offer(initialState);

        while (!pq.isEmpty()) {
            TSPState current = pq.poll();

            // Si ya tenemos una mejor solución, podar
            if (current.cost >= bestState.cost) {
                continue;
            }

            // Si visitamos todos los nodos
            if (current.level == n) {
                if (current.cost < bestState.cost) {
                    bestState = current;
                }
                continue;
            }

            // Explorar vecinos
            for (int i = 0; i < n; i++) {
                if (!current.visited[i]) {
                    TSPState newState = new TSPState();
                    newState.visited = current.visited.clone();
                    newState.visited[i] = true;
                    newState.currentNode = i;
                    newState.cost = current.cost + costMatrix[current.currentNode][i];
                    newState.level = current.level + 1;
                    newState.path = new ArrayList<>(current.path);
                    newState.path.add(nodesToVisit.get(i));

                    pq.offer(newState);
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("path", bestState.path != null ? bestState.path : new ArrayList<>());
        result.put("cost", bestState.cost != Integer.MAX_VALUE ? bestState.cost : 0);
        result.put("nodeCount", nodesToVisit.size());
        return result;
    }

    private int[][] buildCostMatrix(List<String> nodes) {
        int n = nodes.size();
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    // Calcular distancia usando Dijkstra o BFS
                    List<String> path = findShortestPath(nodes.get(i), nodes.get(j));
                    matrix[i][j] = path.isEmpty() ? 9999 : path.size() - 1;
                }
            }
        }

        return matrix;
    }

    // Clase auxiliar para TSP
    private static class TSPState {
        boolean[] visited;
        int currentNode;
        int cost;
        int level;
        List<String> path;
    }

    /**
     * BRANCH & BOUND - Mochila 0/1 optimizado
     */
    public Map<String, Object> knapsackBranchAndBound(List<Map<String, Object>> items, int capacity) {
        // Ordenar por ratio valor/peso (descendente)
        List<Map<String, Object>> sortedItems = new ArrayList<>(items);
        sortedItems.sort((a, b) -> {
            double ratioA = (double) (int) a.get("value") / (int) a.get("weight");
            double ratioB = (double) (int) b.get("value") / (int) b.get("weight");
            return Double.compare(ratioB, ratioA);
        });

        KnapsackState initialState = new KnapsackState();
        initialState.level = 0;
        initialState.value = 0;
        initialState.weight = 0;
        initialState.bound = calculateBound(initialState, sortedItems, capacity);
        initialState.selected = new ArrayList<>();

        PriorityQueue<KnapsackState> pq = new PriorityQueue<>((a, b) -> Double.compare(b.bound, a.bound));
        pq.offer(initialState);

        KnapsackState bestState = new KnapsackState();
        bestState.value = 0;

        while (!pq.isEmpty()) {
            KnapsackState current = pq.poll();

            if (current.bound <= bestState.value) {
                continue;
            }

            if (current.level == sortedItems.size()) {
                if (current.value > bestState.value) {
                    bestState = current;
                }
                continue;
            }

            Map<String, Object> item = sortedItems.get(current.level);
            int itemWeight = (int) item.get("weight");
            int itemValue = (int) item.get("value");

            // Incluir el item
            if (current.weight + itemWeight <= capacity) {
                KnapsackState includeState = new KnapsackState();
                includeState.level = current.level + 1;
                includeState.value = current.value + itemValue;
                includeState.weight = current.weight + itemWeight;
                includeState.selected = new ArrayList<>(current.selected);
                includeState.selected.add((String) item.get("name"));
                includeState.bound = calculateBound(includeState, sortedItems, capacity);

                if (includeState.value > bestState.value) {
                    bestState = includeState;
                }

                pq.offer(includeState);
            }

            // No incluir el item
            KnapsackState excludeState = new KnapsackState();
            excludeState.level = current.level + 1;
            excludeState.value = current.value;
            excludeState.weight = current.weight;
            excludeState.selected = new ArrayList<>(current.selected);
            excludeState.bound = calculateBound(excludeState, sortedItems, capacity);

            pq.offer(excludeState);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("selectedItems", bestState.selected);
        result.put("totalValue", bestState.value);
        result.put("totalWeight", bestState.weight);
        result.put("capacity", capacity);
        return result;
    }

    private double calculateBound(KnapsackState state, List<Map<String, Object>> items, int capacity) {
        if (state.weight >= capacity) {
            return 0;
        }

        double bound = state.value;
        int remainingCapacity = capacity - state.weight;

        for (int i = state.level; i < items.size() && remainingCapacity > 0; i++) {
            Map<String, Object> item = items.get(i);
            int weight = (int) item.get("weight");
            int value = (int) item.get("value");

            if (weight <= remainingCapacity) {
                bound += value;
                remainingCapacity -= weight;
            } else {
                bound += value * ((double) remainingCapacity / weight);
                break;
            }
        }

        return bound;
    }

    // Clase auxiliar para Knapsack
    private static class KnapsackState {
        int level;
        int value;
        int weight;
        double bound;
        List<String> selected;
    }

    /**
     * Obtiene todos los nodos del grafo
     */
    public Set<String> getAllNodes() {
        return new HashSet<>(adjacencyList.keySet());
    }

    /**
     * Obtiene todos los nodos de un tipo específico
     */
    public List<String> getNodesByType(String type) {
        return adjacencyList.keySet().stream()
                .filter(nodeId -> nodeTypes.get(nodeId).equals(type.toUpperCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Obtiene los vecinos de un nodo específico
     */
    public List<String> getNeighbors(String nodeId) {
        return adjacencyList.getOrDefault(nodeId, new ArrayList<>());
    }

    /**
     * Obtiene todas las aristas del grafo
     */
    public List<String[]> getAllEdges() {
        return new ArrayList<>(edges);
    }

    /**
     * Obtiene el tipo de un nodo
     */
    public String getNodeType(String nodeId) {
        return nodeTypes.get(nodeId);
    }

    /**
     * Verifica si el grafo está vacío
     */
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    /**
     * Obtiene el número de nodos
     */
    public int getNodeCount() {
        return adjacencyList.size();
    }

    /**
     * Obtiene el número de aristas
     */
    public int getEdgeCount() {
        return edges.size();
    }

    /**
     * Obtiene información detallada de un nodo
     */
    public Map<String, Object> getNodeInfo(String nodeId) {
        Map<String, Object> info = new HashMap<>();
        info.put("id", nodeId);
        info.put("type", nodeTypes.get(nodeId));
        info.put("neighbors", getNeighbors(nodeId));
        info.put("neighborCount", getNeighbors(nodeId).size());
        return info;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SimpleMovieGraph {\n");
        sb.append("  Nodos: ").append(getNodeCount()).append("\n");
        sb.append("  Aristas: ").append(getEdgeCount()).append("\n");

        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            sb.append("  ").append(entry.getKey())
                    .append(" (").append(nodeTypes.get(entry.getKey())).append(") -> ");
            for (String neighbor : entry.getValue()) {
                sb.append(neighbor).append(" ");
            }
            sb.append("\n");
        }

        sb.append("}");
        return sb.toString();
    }
}
