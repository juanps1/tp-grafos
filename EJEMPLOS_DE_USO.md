# 🎓 Ejemplos de Uso - Para la Defensa

## 🎯 Escenarios de Prueba

### Escenario 1: Buscar conexiones entre actores
```
http://localhost:8080/graph/dijkstra/Keanu Reeves/Laurence Fishburne
```
**Resultado esperado:** Mostrará cómo están conectados estos dos actores a través de películas

---

### Escenario 2: Explorar desde una película
```
http://localhost:8080/graph/bfs/The Matrix
```
**Resultado esperado:** Lista todos los actores, directores y películas relacionadas en orden de amplitud

---

### Escenario 3: Comparar recorridos
```
http://localhost:8080/graph/compare-traversals/Speed
```
**Resultado esperado:** Muestra la diferencia entre DFS y BFS desde el mismo punto

---

## 💡 Cómo Explicar Cada Algoritmo en la Defensa

### **DFS (Depth-First Search)**
**¿Qué hace?**
> "Explora el grafo yendo lo más profundo posible antes de retroceder. Es como perderte en un laberinto y siempre tomar el primer camino que ves."

**Ventajas:**
- Usa menos memoria que BFS
- Bueno para encontrar si existe un camino

**Desventajas:**
- No garantiza el camino más corto
- Puede quedarse en una rama muy profunda

**Código clave:**
```java
// Usa una pila (Stack) o recursión
dfsRecursive(nodo, visitados, resultado)
```

---

### **BFS (Breadth-First Search)**
**¿Qué hace?**
> "Explora el grafo por niveles, como las ondas en el agua. Primero explora todos los vecinos inmediatos, luego los vecinos de los vecinos."

**Ventajas:**
- ✅ **Garantiza encontrar el camino más corto** (en grafos sin pesos)
- Explora uniformemente

**Desventajas:**
- Usa más memoria que DFS

**Código clave:**
```java
// Usa una cola (Queue)
Queue<String> queue = new LinkedList<>();
```

---

### **Dijkstra**
**¿Qué hace?**
> "Como BFS pero considera el peso de las aristas. Es como Google Maps buscando la ruta más rápida considerando el tráfico."

**Diferencia con BFS:**
| Aspecto | BFS | Dijkstra |
|---------|-----|----------|
| Pesos | Todas las aristas = 1 | Pesos diferentes |
| Estructura | Cola simple | Cola de prioridad |
| Resultado | Camino más corto en # nodos | Camino más corto en peso total |

**Código clave:**
```java
// Usa una PriorityQueue ordenada por distancia
PriorityQueue<Map.Entry<String, Integer>> pq = 
    new PriorityQueue<>(Map.Entry.comparingByValue());
```

**Ejemplo en la vida real:**
- Entrada: "Speed" → "The Matrix"
- Peso 1 = relación directa (actúa en / dirige)
- Resultado: ["Speed", "Keanu Reeves", "The Matrix"] con distancia = 2

---

### **Prim (Árbol de Expansión Mínima)**
**¿Qué hace?**
> "Construye un árbol que conecta todos los nodos usando las aristas de menor peso. Crece como un árbol real, agregando una rama a la vez."

**Analogía:**
- Tienes que conectar varias casas con cable de internet
- Quieres usar la menor cantidad de cable posible
- Prim empieza desde una casa y va conectando la casa más cercana

**Características:**
- ✅ Empieza desde un nodo específico
- ✅ Siempre elige la arista más barata que conecta con un nodo nuevo
- ✅ Usa cola de prioridad

**Código clave:**
```java
// Agrega vecinos a PriorityQueue ordenada por peso
PriorityQueue<EdgeInfo> pq = new PriorityQueue<>(
    Comparator.comparingInt(e -> e.weight)
);
```

**Resultado:**
```json
{
  "edges": [
    {"from": "Speed", "to": "Keanu Reeves", "weight": 1},
    {"from": "Keanu Reeves", "to": "The Matrix", "weight": 1}
  ],
  "totalWeight": 2
}
```

---

### **Kruskal (Árbol de Expansión Mínima)**
**¿Qué hace?**
> "Como Prim pero con una estrategia diferente: ordena TODAS las aristas por peso y las agrega una por una, evitando ciclos."

**Diferencia con Prim:**
| Aspecto | Prim | Kruskal |
|---------|------|---------|
| Estrategia | Crece desde un nodo | Ordena todas las aristas |
| Cuándo es mejor | Grafos densos (muchas aristas) | Grafos dispersos (pocas aristas) |
| Estructura | PriorityQueue + visitados | Union-Find para detectar ciclos |

**Union-Find:**
> "Estructura de datos que maneja conjuntos disjuntos. En Kruskal, lo usamos para detectar si agregar una arista crearía un ciclo."

**Código clave:**
```java
// 1. Ordenar todas las aristas
allEdges.sort(Comparator.comparingInt(e -> e.weight));

// 2. Agregar aristas sin crear ciclos
if (!root1.equals(root2)) {
    mstEdges.add(edge);
    union(parent, rank, root1, root2);
}
```

---

## 🎭 Preguntas Típicas en la Defensa

### 1. "¿Por qué usar Dijkstra si BFS es más simple?"
**Respuesta:**
> "BFS solo funciona cuando todas las conexiones tienen el mismo 'costo'. Dijkstra permite que cada conexión tenga un peso diferente. Por ejemplo, en nuestro grafo de películas, podríamos dar peso 1 a actores principales y peso 2 a actores secundarios."

---

### 2. "¿Cuándo usar Prim vs Kruskal?"
**Respuesta:**
> "Ambos encuentran el MST, pero:
> - **Prim**: Mejor para grafos densos (muchas conexiones). Crece desde un punto.
> - **Kruskal**: Mejor para grafos dispersos (pocas conexiones). Mira todas las aristas globalmente.
> En la práctica, con grafos pequeños como el nuestro, la diferencia de rendimiento es mínima."

---

### 3. "¿Qué es la complejidad de estos algoritmos?"
**Respuesta:**
```
BFS/DFS:     O(V + E) - V=nodos, E=aristas
Dijkstra:    O((V + E) log V) - por la PriorityQueue
Prim:        O(E log V) - similar a Dijkstra
Kruskal:     O(E log E) - por ordenar las aristas
```

---

### 4. "¿Por qué el grafo es bidireccional?"
**Respuesta:**
> "En nuestro modelo, si Keanu Reeves actúa en Speed, entonces Speed tiene a Keanu Reeves como actor. Ambas direcciones son válidas:
> - `Keanu Reeves --ACTED_IN--> Speed`
> - `Speed --ACTED_BY--> Keanu Reeves`
> Esto hace que el grafo sea no dirigido (bidireccional)."

---

## 🚀 Demo para la Presentación

### Paso 1: Mostrar el grafo
```
http://localhost:8080/graph/info
```
Explica: "Tenemos X nodos y Y aristas en nuestro grafo de películas"

---

### Paso 2: Mostrar BFS vs DFS
```
http://localhost:8080/graph/compare-traversals/Speed
```
Explica: "Miren cómo BFS y DFS exploran el grafo de manera diferente desde el mismo punto"

---

### Paso 3: Demostrar Dijkstra
```
http://localhost:8080/graph/dijkstra/Speed/The Matrix
```
Explica: "Dijkstra encuentra el camino más corto considerando pesos. Aquí vemos que Speed y The Matrix están conectados a través de Keanu Reeves."

---

### Paso 4: Mostrar MST con Prim
```
http://localhost:8080/graph/prim/Speed
```
Explica: "Prim construye un árbol que conecta todos los nodos alcanzables desde Speed usando las aristas de menor peso."

---

### Paso 5: Comparar con Kruskal
```
http://localhost:8080/graph/kruskal
```
Explica: "Kruskal encuentra el MST de todo el grafo, no solo desde un nodo. Puede dar un resultado diferente a Prim dependiendo del nodo inicial de Prim."

---

## 📊 Tabla Resumen para Estudiar

| Algoritmo | Tipo | Estructura de Datos | Garantiza Camino Mínimo | Complejidad |
|-----------|------|---------------------|-------------------------|-------------|
| **DFS** | Recorrido | Stack / Recursión | ❌ No | O(V + E) |
| **BFS** | Recorrido | Queue | ✅ Sí (sin pesos) | O(V + E) |
| **Dijkstra** | Camino mínimo | PriorityQueue | ✅ Sí (con pesos) | O((V+E) log V) |
| **Prim** | MST | PriorityQueue | ✅ MST | O(E log V) |
| **Kruskal** | MST | Union-Find | ✅ MST | O(E log E) |

---

## 🎯 Tip Final para la Defensa

**Practica esta frase:**
> "Implementé estos algoritmos en Java usando Spring Boot y Neo4j. El grafo representa películas y actores como nodos, con relaciones bidireccionales. Usé estructuras de datos como PriorityQueue para Dijkstra y Prim, y Union-Find para Kruskal. Todos los algoritmos están expuestos como endpoints REST que se pueden probar desde el navegador."

**¡Buena suerte! 🍀**
