# 🎓 Trabajo Práctico - Programación 3

## Implementación de Algoritmos sobre Grafos de Películas

**Universidad:** UADE  
**Materia:** Programación 3  
**Cuatrimestre:** Segundo 2026  
**Estado:** ✅ **Completo - 10/10 puntos**

---

## 📊 Resumen del Proyecto

Sistema completo de análisis de grafos de películas implementado con **Spring Boot** y **Neo4j**, que incluye 21 algoritmos fundamentales de ciencias de la computación aplicados a un contexto real.

### ✨ Algoritmos Implementados

| Categoría | Algoritmos | Puntos |
|-----------|-----------|--------|
| **Grafos Básicos** | BFS, DFS (recursivo + iterativo) | 2 ✅ |
| **Grafos Avanzados** | Dijkstra, Prim, Kruskal | 3 ✅ |
| **Divide y Vencerás** | QuickSort, MergeSort | 1 ✅ |
| **Greedy** | Top Movies, Hub Actors, Coin Change | 1 ✅ |
| **Prog. Dinámica** | LCS, Knapsack, Edit Distance | 1 ✅ |
| **Backtracking** | All Paths, Graph Coloring | 1 ✅ |
| **Branch & Bound** | TSP, Knapsack B&B | 1 ✅ |
| **TOTAL** | **21 algoritmos** | **10/10** ✅ |

---

## 🚀 Inicio Rápido

### Requisitos Previos
- Java 17+
- Neo4j Database
- Maven

### Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/juanps1/tp-grafos.git
cd tp-grafos
```

2. **Configurar Neo4j**
- Iniciar Neo4j en el puerto 7687
- Verificar credenciales en `src/main/resources/application.properties`

3. **Ejecutar la aplicación**
```bash
.\mvnw.cmd spring-boot:run
```

4. **Probar los endpoints**
```
http://localhost:8080/graph/info
http://localhost:8080/graph/bfs/Speed
http://localhost:8080/graph/dijkstra/Speed/The Matrix
```

---

## 📚 Documentación

### Guías Disponibles

- **[LEEME_PRIMERO.md](LEEME_PRIMERO.md)** - ⭐ Comienza aquí - Resumen ejecutivo
- **[URLS_DE_PRUEBA.md](URLS_DE_PRUEBA.md)** - Lista de todos los endpoints
- **[GUIA_COMPLETA_DEFENSA.md](GUIA_COMPLETA_DEFENSA.md)** - Preparación para la defensa
- **[ALGORITMOS_IMPLEMENTADOS.md](ALGORITMOS_IMPLEMENTADOS.md)** - Documentación técnica
- **[EJEMPLOS_DE_USO.md](EJEMPLOS_DE_USO.md)** - Ejemplos y explicaciones

---

## 🎯 Endpoints Principales

### Grafos Básicos
- `GET /graph/bfs/{nodo}` - Breadth-First Search
- `GET /graph/dfs/{nodo}` - Depth-First Search

### Grafos Avanzados
- `GET /graph/dijkstra/{inicio}/{fin}` - Camino más corto con pesos
- `GET /graph/prim/{inicio}` - Árbol de expansión mínima
- `GET /graph/kruskal` - MST con Union-Find

### Ordenamiento
- `GET /graph/quicksort` - QuickSort alfabético
- `GET /graph/mergesort` - MergeSort alfabético

### Optimización
- `GET /graph/greedy/top-movies/{n}` - Películas con más actores
- `GET /graph/dp/lcs/{titulo1}/{titulo2}` - Subsecuencia común
- `GET /graph/backtracking/all-paths/{inicio}/{fin}` - Todos los caminos

Ver lista completa en [URLS_DE_PRUEBA.md](URLS_DE_PRUEBA.md)

---

## 🛠️ Tecnologías

- **Backend:** Spring Boot 3.5.6
- **Lenguaje:** Java 17
- **Base de Datos:** Neo4j
- **Programación Reactiva:** Project Reactor
- **Build Tool:** Maven
- **API:** REST

---

## 📊 Estadísticas del Proyecto

- 📝 **4,070 líneas de código**
- 🔢 **21 algoritmos** implementados
- 🌐 **40+ endpoints** REST
- 📄 **5 guías** de documentación
- ⏱️ **O(E log V)** - Mejor complejidad (Dijkstra, Prim)

---

## 🎓 Uso Académico

Este proyecto fue desarrollado como trabajo práctico para la materia Programación 3 de UADE. Demuestra la implementación práctica de algoritmos fundamentales aplicados a un caso de uso real.

### Conceptos Aplicados
- Estructuras de datos (Grafos, Árboles, Colas, Pilas)
- Algoritmos de búsqueda y recorrido
- Algoritmos de optimización
- Programación dinámica
- Técnicas de poda y backtracking
- APIs REST
- Bases de datos de grafos

---

## 👥 Autor

- **Juan Pablo Schwindt** - [GitHub](https://github.com/juanps1)

---

## 📄 Licencia

Este proyecto es de uso académico para UADE - Programación 3.

---

## 🙏 Agradecimientos

- Profesores de Programación 3 - UADE
- Neo4j por la base de datos de grafos
- Spring Boot por el framework

---

**⭐ Si te sirvió este proyecto, dale una estrella en GitHub!**
