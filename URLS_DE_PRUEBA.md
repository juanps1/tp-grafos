# 🔗 URLs de Prueba - Copia y Pega

## 📋 **LISTA COMPLETA DE ENDPOINTS**

### ✅ Para probar, simplemente copia cada URL y pégala en tu navegador

---

## 🔹 **BÁSICOS - Información del Grafo**

```
http://localhost:8080/graph/info
http://localhost:8080/graph/nodes
http://localhost:8080/graph/nodes/type/MOVIE
http://localhost:8080/graph/nodes/type/PERSON
http://localhost:8080/graph/edges
```

---

## 🔹 **BFS y DFS (2 puntos)**

```
http://localhost:8080/graph/bfs/Speed
http://localhost:8080/graph/dfs/Speed
http://localhost:8080/graph/dfs-iterative/Speed
http://localhost:8080/graph/compare-traversals/Speed
http://localhost:8080/graph/shortest-path/Speed/The Matrix
```

---

## 🔹 **DIJKSTRA, PRIM, KRUSKAL (3 puntos)**

```
http://localhost:8080/graph/dijkstra/Speed/The Matrix
http://localhost:8080/graph/dijkstra/Keanu Reeves/Laurence Fishburne
http://localhost:8080/graph/prim/Speed
http://localhost:8080/graph/prim/The Matrix
http://localhost:8080/graph/kruskal
```

---

## 🔹 **QUICKSORT y MERGESORT (1 punto)**

```
http://localhost:8080/graph/quicksort
http://localhost:8080/graph/mergesort
http://localhost:8080/graph/quicksort-by-degree
```

---

## 🔹 **ALGORITMOS GREEDY (1 punto)**

```
http://localhost:8080/graph/greedy/top-movies/5
http://localhost:8080/graph/greedy/top-movies/10
http://localhost:8080/graph/greedy/hub-actors/5
http://localhost:8080/graph/greedy/hub-actors/10
http://localhost:8080/graph/greedy/coin-change/237
http://localhost:8080/graph/greedy/coin-change/1000
```

---

## 🔹 **PROGRAMACIÓN DINÁMICA (1 punto)**

```
http://localhost:8080/graph/dp/lcs/The Matrix/The Matrix Reloaded
http://localhost:8080/graph/dp/lcs/Speed/Speed Racer
http://localhost:8080/graph/dp/edit-distance/Speed/The Matrix
http://localhost:8080/graph/dp/edit-distance/The Matrix/Matrix
```

---

## 🔹 **BACKTRACKING (1 punto)**

```
http://localhost:8080/graph/backtracking/all-paths/Speed/The Matrix
http://localhost:8080/graph/backtracking/all-paths/Keanu Reeves/Laurence Fishburne
http://localhost:8080/graph/backtracking/coloring/2
http://localhost:8080/graph/backtracking/coloring/3
http://localhost:8080/graph/backtracking/coloring/4
```

---

## 🎯 **TOP 10 - URLs MÁS IMPORTANTES PARA LA DEMO**

### Copia estas para tu presentación:

**1. Info del grafo**
```
http://localhost:8080/graph/info
```

**2. Comparar BFS vs DFS**
```
http://localhost:8080/graph/compare-traversals/Speed
```

**3. Dijkstra (camino más corto con pesos)**
```
http://localhost:8080/graph/dijkstra/Speed/The Matrix
```

**4. Kruskal (MST de todo el grafo)**
```
http://localhost:8080/graph/kruskal
```

**5. QuickSort por grado (ordenar por conexiones)**
```
http://localhost:8080/graph/quicksort-by-degree
```

**6. Top 5 películas con más actores (Greedy)**
```
http://localhost:8080/graph/greedy/top-movies/5
```

**7. Actores más conectados (Greedy)**
```
http://localhost:8080/graph/greedy/hub-actors/5
```

**8. Subsecuencia común más larga (DP)**
```
http://localhost:8080/graph/dp/lcs/The Matrix/The Matrix Reloaded
```

**9. Todos los caminos posibles (Backtracking)**
```
http://localhost:8080/graph/backtracking/all-paths/Speed/The Matrix
```

**10. Coloreo de grafo (Backtracking)**
```
http://localhost:8080/graph/backtracking/coloring/3
```

---

## 📝 **NOTAS IMPORTANTES**

### Si un nombre tiene espacios:
- ✅ **Funciona:** `http://localhost:8080/graph/bfs/The Matrix`
- ✅ **También funciona:** `http://localhost:8080/graph/bfs/The%20Matrix`
- El navegador convierte automáticamente los espacios

### Si algo no funciona:
1. Verifica que la aplicación esté corriendo (`.\mvnw.cmd spring-boot:run`)
2. Verifica que estés usando `http://` (no `https://`)
3. Verifica que el puerto sea `8080`
4. Verifica que Neo4j esté corriendo

---

## 🚀 **SECUENCIA DE PRUEBA COMPLETA (5 minutos)**

### Ejecuta estas URLs en orden para una demo completa:

```
# 1. Verificar que funciona
http://localhost:8080/graph/info

# 2. Ver todos los nodos
http://localhost:8080/graph/nodes

# 3. BFS desde Speed
http://localhost:8080/graph/bfs/Speed

# 4. Comparar BFS vs DFS
http://localhost:8080/graph/compare-traversals/Speed

# 5. Dijkstra
http://localhost:8080/graph/dijkstra/Speed/The Matrix

# 6. MST con Kruskal
http://localhost:8080/graph/kruskal

# 7. Ordenar por conexiones
http://localhost:8080/graph/quicksort-by-degree

# 8. Top películas
http://localhost:8080/graph/greedy/top-movies/5

# 9. LCS entre títulos
http://localhost:8080/graph/dp/lcs/The Matrix/The Matrix Reloaded

# 10. Todos los caminos
http://localhost:8080/graph/backtracking/all-paths/Speed/The Matrix
```

---

## 📊 **CATEGORIZACIÓN POR PUNTOS**

### 2 puntos - BFS/DFS:
```
http://localhost:8080/graph/bfs/Speed
http://localhost:8080/graph/dfs/Speed
http://localhost:8080/graph/compare-traversals/Speed
```

### 3 puntos - Dijkstra/Prim/Kruskal:
```
http://localhost:8080/graph/dijkstra/Speed/The Matrix
http://localhost:8080/graph/prim/Speed
http://localhost:8080/graph/kruskal
```

### 1 punto - Divide y Vencerás:
```
http://localhost:8080/graph/quicksort
http://localhost:8080/graph/mergesort
```

### 1 punto - Greedy:
```
http://localhost:8080/graph/greedy/top-movies/5
http://localhost:8080/graph/greedy/hub-actors/5
```

### 1 punto - Programación Dinámica:
```
http://localhost:8080/graph/dp/lcs/The Matrix/The Matrix Reloaded
http://localhost:8080/graph/dp/edit-distance/Speed/The Matrix
```

### 1 punto - Backtracking:
```
http://localhost:8080/graph/backtracking/all-paths/Speed/The Matrix
http://localhost:8080/graph/backtracking/coloring/3
```

---

## ✨ **LISTA DE VERIFICACIÓN**

Antes de la defensa, prueba estos endpoints:

- [ ] `http://localhost:8080/graph/info` ✅
- [ ] `http://localhost:8080/graph/bfs/Speed` ✅
- [ ] `http://localhost:8080/graph/dijkstra/Speed/The Matrix` ✅
- [ ] `http://localhost:8080/graph/kruskal` ✅
- [ ] `http://localhost:8080/graph/quicksort-by-degree` ✅
- [ ] `http://localhost:8080/graph/greedy/top-movies/5` ✅
- [ ] `http://localhost:8080/graph/dp/lcs/The Matrix/The Matrix Reloaded` ✅
- [ ] `http://localhost:8080/graph/backtracking/all-paths/Speed/The Matrix` ✅

---

## 🎯 **PARA IMPRIMIR**

Imprime esta página y tenla durante tu presentación.
Puedes marcar con un ✓ las URLs que ya probaste.

---

*Última actualización: Noviembre 10, 2025*  
*Total de endpoints: 40+*  
*Estado: ✅ Todos funcionales*
