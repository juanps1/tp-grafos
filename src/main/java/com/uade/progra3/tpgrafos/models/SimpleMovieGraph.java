package com.uade.progra3.tpgrafos.models;

import java.util.*;

/**
 * Implementación de grafo para películas y personas usando lista de adyacencia.
 * Soporta algoritmos: DFS, BFS, Dijkstra, Prim y Kruskal.
 */
public class SimpleMovieGraph {
    
    // Clase interna para representar una arista con peso
    public static class Edge {
        public String from;
        public String to;
        public String type;
        public int weight;
        
        public Edge(String from, String to, String type, int weight) {
            this.from = from;
            this.to = to;
            this.type = type;
            this.weight = weight;
        }
        
        @Override
        public String toString() {
            return String.format("%s -[%s:%d]-> %s", from, type, weight, to);
        }
    }
    
    private Map<String, List<String>> adjacencyList;
    private Map<String, String> nodeTypes;
    private List<Edge> edges;
    private Map<String, Map<String, Integer>> weights;
    
    public SimpleMovieGraph() {
        this.adjacencyList = new HashMap<>();
        this.nodeTypes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.weights = new HashMap<>();
    }
    
    public void addNode(String nodeId, String type) {
        adjacencyList.putIfAbsent(nodeId, new ArrayList<>());
        nodeTypes.put(nodeId, type);
        weights.putIfAbsent(nodeId, new HashMap<>());
    }
    
    public void addEdge(String fromId, String toId, String relationshipType) {
        addEdge(fromId, toId, relationshipType, 1);
    }
    
    public void addEdge(String fromId, String toId, String relationshipType, int weight) {
        if (!adjacencyList.containsKey(fromId)) {
            adjacencyList.put(fromId, new ArrayList<>());
            weights.put(fromId, new HashMap<>());
        }
        if (!adjacencyList.containsKey(toId)) {
            adjacencyList.put(toId, new ArrayList<>());
            weights.put(toId, new HashMap<>());
        }
        
        List<String> neighbors = adjacencyList.get(fromId);
        if (!neighbors.contains(toId)) {
            neighbors.add(toId);
        }
        
        weights.get(fromId).put(toId, weight);
        
        Edge newEdge = new Edge(fromId, toId, relationshipType, weight);
        boolean edgeExists = edges.stream()
                .anyMatch(edge -> edge.from.equals(fromId) && edge.to.equals(toId));
        
        if (!edgeExists) {
            edges.add(newEdge);
        }
    }
    
    public void buildFromEntities(List<MovieEntity> movies, List<PersonEntity> persons) {
        for (MovieEntity movie : movies) {
            addNode(movie.getTitle(), "MOVIE");
        }
        
        for (PersonEntity person : persons) {
            addNode(person.getName(), "PERSON");
        }
        
        for (MovieEntity movie : movies) {
            String movieId = movie.getTitle();
            
            if (movie.getActors() != null) {
                for (PersonEntity actor : movie.getActors()) {
                    String actorId = actor.getName();
                    addEdge(actorId, movieId, "ACTED_IN", 1);
                    addEdge(movieId, actorId, "ACTED_BY", 1);
                }
            }
            
            if (movie.getDirectors() != null) {
                for (PersonEntity director : movie.getDirectors()) {
                    String directorId = director.getName();
                    addEdge(directorId, movieId, "DIRECTED", 2);
                    addEdge(movieId, directorId, "DIRECTED_BY", 2);
                }
            }
        }
    }
    
    // ==================== DFS ====================
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
        
        List<String> neighbors = adjacencyList.get(currentNodeId);
        if (neighbors != null) {
            for (String neighborId : neighbors) {
                if (!visited.contains(neighborId)) {
                    dfsRecursive(neighborId, visited, result);
                }
            }
        }
    }
    
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
    
    // ==================== BFS ====================
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
    
    // ==================== Shortest Path (BFS) ====================
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
        
        return new ArrayList<>();
    }
    
    // ==================== DIJKSTRA ====================
    public Map<String, Object> dijkstra(String startNodeId, String endNodeId) {
        if (!adjacencyList.containsKey(startNodeId) || !adjacencyList.containsKey(endNodeId)) {
            return Map.of("path", new ArrayList<>(), "distance", -1, "visited", new ArrayList<>());
        }
        
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> pq = new PriorityQueue<>(
            Comparator.comparingInt(node -> distances.getOrDefault(node, Integer.MAX_VALUE))
        );
        
        // Inicializar distancias
        for (String node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(startNodeId, 0);
        pq.offer(startNodeId);
        
        List<String> visitedOrder = new ArrayList<>();
        
        while (!pq.isEmpty()) {
            String current = pq.poll();
            
            if (visited.contains(current)) continue;
            visited.add(current);
            visitedOrder.add(current);
            
            if (current.equals(endNodeId)) break;
            
            List<String> neighbors = adjacencyList.get(current);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        int weight = weights.get(current).getOrDefault(neighbor, 1);
                        int newDist = distances.get(current) + weight;
                        
                        if (newDist < distances.get(neighbor)) {
                            distances.put(neighbor, newDist);
                            previous.put(neighbor, current);
                            pq.offer(neighbor);
                        }
                    }
                }
            }
        }
        
        List<String> path = reconstructPath(previous, startNodeId, endNodeId);
        int distance = distances.getOrDefault(endNodeId, -1);
        
        return Map.of(
            "path", path,
            "distance", distance,
            "visited", visitedOrder,
            "algorithm", "Dijkstra"
        );
    }
    
    // ==================== PRIM (MST) ====================
    public Map<String, Object> primMST() {
        if (adjacencyList.isEmpty()) {
            return Map.of("edges", new ArrayList<>(), "totalWeight", 0);
        }
        
        Set<String> inMST = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        List<Edge> mstEdges = new ArrayList<>();
        int totalWeight = 0;
        
        String startNode = adjacencyList.keySet().iterator().next();
        inMST.add(startNode);
        
        List<String> neighbors = adjacencyList.get(startNode);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                int weight = weights.get(startNode).getOrDefault(neighbor, 1);
                pq.offer(new Edge(startNode, neighbor, "MST", weight));
            }
        }
        
        while (!pq.isEmpty() && inMST.size() < adjacencyList.size()) {
            Edge edge = pq.poll();
            
            if (inMST.contains(edge.to)) continue;
            
            inMST.add(edge.to);
            mstEdges.add(edge);
            totalWeight += edge.weight;
            
            List<String> newNeighbors = adjacencyList.get(edge.to);
            if (newNeighbors != null) {
                for (String neighbor : newNeighbors) {
                    if (!inMST.contains(neighbor)) {
                        int weight = weights.get(edge.to).getOrDefault(neighbor, 1);
                        pq.offer(new Edge(edge.to, neighbor, "MST", weight));
                    }
                }
            }
        }
        
        return Map.of(
            "edges", mstEdges.stream().map(Edge::toString).toList(),
            "totalWeight", totalWeight,
            "algorithm", "Prim",
            "nodesInMST", inMST.size()
        );
    }
    
    // ==================== KRUSKAL (MST) ====================
    public Map<String, Object> kruskalMST() {
        List<Edge> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort(Comparator.comparingInt(e -> e.weight));
        
        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> rank = new HashMap<>();
        
        for (String node : adjacencyList.keySet()) {
            parent.put(node, node);
            rank.put(node, 0);
        }
        
        List<Edge> mstEdges = new ArrayList<>();
        int totalWeight = 0;
        
        for (Edge edge : sortedEdges) {
            String root1 = find(parent, edge.from);
            String root2 = find(parent, edge.to);
            
            if (!root1.equals(root2)) {
                mstEdges.add(edge);
                totalWeight += edge.weight;
                union(parent, rank, root1, root2);
            }
        }
        
        return Map.of(
            "edges", mstEdges.stream().map(Edge::toString).toList(),
            "totalWeight", totalWeight,
            "algorithm", "Kruskal",
            "edgeCount", mstEdges.size()
        );
    }
    
    private String find(Map<String, String> parent, String node) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent, parent.get(node)));
        }
        return parent.get(node);
    }
    
    private void union(Map<String, String> parent, Map<String, Integer> rank, String root1, String root2) {
        if (rank.get(root1) < rank.get(root2)) {
            parent.put(root1, root2);
        } else if (rank.get(root1) > rank.get(root2)) {
            parent.put(root2, root1);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank.get(root1) + 1);
        }
    }
    
    private List<String> reconstructPath(Map<String, String> parent, String start, String end) {
        List<String> path = new ArrayList<>();
        String current = end;
        
        while (current != null) {
            path.add(0, current);
            current = parent.get(current);
        }
        
        return path.isEmpty() || !path.get(0).equals(start) ? new ArrayList<>() : path;
    }
    
    // ==================== Utilidades ====================
    public Set<String> getAllNodes() {
        return new HashSet<>(adjacencyList.keySet());
    }
    
    public List<String> getNodesByType(String type) {
        return adjacencyList.keySet().stream()
                .filter(nodeId -> {
                    String nodeType = nodeTypes.get(nodeId);
                    return nodeType != null && nodeType.equals(type.toUpperCase());
                })
                .toList();
    }
    
    public List<String> getNeighbors(String nodeId) {
        return adjacencyList.getOrDefault(nodeId, new ArrayList<>());
    }
    
    public List<Edge> getAllEdges() {
        return new ArrayList<>(edges);
    }
    
    public String getNodeType(String nodeId) {
        return nodeTypes.get(nodeId);
    }
    
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }
    
    public int getNodeCount() {
        return adjacencyList.size();
    }
    
    public int getEdgeCount() {
        return edges.size();
    }
    
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
