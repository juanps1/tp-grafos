# TP Grafos - UADE Programación 3

Trabajo Práctico de **grafos de películas** implementado con **Spring Boot 3.5.7**, **Neo4j Reactive** y **Java 17**.

## 📋 Descripción

Este proyecto implementa un sistema de grafos basado en películas, actores y directores usando Neo4j como base de datos de grafos. Se implementan algoritmos de recorrido (BFS, DFS) y algoritmos avanzados sobre grafos ponderados (Dijkstra, Prim, Kruskal).

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data Neo4j Reactive**
- **Spring WebFlux** (Programación Reactiva)
- **Neo4j Database** (Aura Cloud o Local)
- **Maven**
- **Lombok**
- **Reactor Core**

## 📁 Estructura del Proyecto

```
src/main/java/com/uade/progra3/tpgrafos/
├── config/
│   ├── DataLoader.java          # Carga inicial de películas y personas
│   └── Neo4jConfig.java         # Configuración de transacciones reactivas
├── controllers/
│   ├── MovieController.java     # CRUD de películas (PUT, GET, DELETE)
│   └── GraphController.java     # Algoritmos sobre el grafo de películas
├── models/
│   ├── MovieEntity.java         # Nodo Movie en Neo4j
│   ├── PersonEntity.java        # Nodo Person en Neo4j
│   └── SimpleMovieGraph.java    # Grafo con algoritmos (BFS, DFS, Dijkstra, Prim, Kruskal)
├── repositories/
│   ├── MovieRepository.java     # Repositorio reactivo de películas
│   └── PersonRepository.java    # Repositorio reactivo de personas
└── services/
    └── GraphService.java        # Servicio de operaciones sobre grafos
```

## 📦 Requisitos Previos

1. **Java 17** o superior
2. **Maven 3.8+**
3. **Neo4j Database**:
   - **Opción 1 (Recomendada)**: Neo4j Aura Free - [neo4j.com/cloud/aura](https://neo4j.com/cloud/aura/)
   - **Opción 2**: Neo4j Desktop local - [neo4j.com/download](https://neo4j.com/download/)

## ⚙️ Instalación

### 1. Configurar Neo4j

#### Opción A: Neo4j Aura (Cloud)

1. Ir a [neo4j.com/cloud/aura](https://neo4j.com/cloud/aura/)
2. Crear cuenta gratuita
3. Crear una instancia free
4. **Guardar las credenciales** (usuario, password, URI)

#### Opción B: Neo4j Desktop (Local)

1. Descargar desde [neo4j.com/download](https://neo4j.com/download/)
2. Instalar y crear un nuevo proyecto
3. Crear una base de datos con password
4. Iniciar la base de datos
5. Conectarse en `bolt://localhost:7687`

### 2. Configurar application.properties

Editar `src/main/resources/application.properties`:

**Para Neo4j Aura:**
```properties
spring.neo4j.uri=neo4j+s://xxxxx.databases.neo4j.io
spring.neo4j.authentication.username=neo4j
spring.neo4j.authentication.password=tu_password_de_aura
```

**Para Neo4j Local:**
```properties
spring.neo4j.uri=bolt://localhost:7687
spring.neo4j.authentication.username=neo4j
spring.neo4j.authentication.password=tu_password
```

### 3. Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 🎬 Modelo de Datos

### Nodos

- **Movie**: Películas con título, descripción y año
- **Person**: Personas (actores/directores) con nombre y año de nacimiento

### Relaciones

- **ACTED_IN**: Actor → Movie
- **DIRECTED**: Director → Movie

### Ejemplo de Grafo

```
(Keanu Reeves)-[:ACTED_IN]->(The Matrix)<-[:DIRECTED]-(Lana Wachowski)
                                        <-[:DIRECTED]-(Lilly Wachowski)
(Laurence Fishburne)-[:ACTED_IN]->(The Matrix)
(Carrie-Anne Moss)-[:ACTED_IN]->(The Matrix)

(Keanu Reeves)-[:ACTED_IN]->(Speed)<-[:DIRECTED]-(Jan de Bont)
```

## 🌐 API Endpoints

### Movies - CRUD Básico

#### Crear/Actualizar Película
```http
PUT /movies
Content-Type: application/json

{
  "title": "The Matrix",
  "description": "Welcome to the Real World",
  "releaseYear": 1999,
  "actors": [
    {"name": "Keanu Reeves", "born": 1964},
    {"name": "Laurence Fishburne", "born": 1961},
    {"name": "Carrie-Anne Moss", "born": 1967}
  ],
  "directors": [
    {"name": "Lana Wachowski", "born": 1965},
    {"name": "Lilly Wachowski", "born": 1967}
  ]
}
```

#### Listar Todas las Películas
```http
GET /movies
Accept: text/event-stream
```

#### Obtener Película por Título
```http
GET /movies/The Matrix
```

#### Eliminar Película
```http
DELETE /movies/The Matrix
```

### Graph - Información del Grafo

#### Información General del Grafo
```http
GET /graph/info
```
Retorna: cantidad de nodos, aristas, tipos de nodos, etc.

#### Listar Todos los Nodos
```http
GET /graph/nodes
```

#### Nodos por Tipo
```http
GET /graph/nodes/type/Movie
GET /graph/nodes/type/Person
```

#### Vecinos de un Nodo
```http
GET /graph/nodes/The Matrix/neighbors
```
Retorna: todos los nodos conectados (actores y directores)

#### Todas las Aristas
```http
GET /graph/edges
```

#### Información de un Nodo
```http
GET /graph/nodes/Keanu Reeves/info
```

### Graph - Algoritmos de Recorrido

#### DFS (Depth-First Search) - Recursivo
```http
GET /graph/dfs/The Matrix
```
Recorrido en profundidad desde "The Matrix"

#### DFS Iterativo
```http
GET /graph/dfs-iterative/The Matrix
```

#### BFS (Breadth-First Search)
```http
GET /graph/bfs/The Matrix
```
Recorrido en amplitud desde "The Matrix"

#### Comparar Recorridos
```http
GET /graph/compare-traversals/The Matrix
```
Compara DFS vs BFS desde el mismo nodo

### Graph - Algoritmos de Caminos

#### Camino Más Corto (BFS)
```http
GET /graph/shortest-path/The Matrix/Speed
```
Encuentra el camino más corto entre dos películas

#### Dijkstra (Caminos Mínimos con Pesos)
```http
GET /graph/dijkstra/The Matrix/Speed
```
Respuesta:
```json
{
  "path": ["The Matrix", "Keanu Reeves", "Speed"],
  "distance": 2,
  "visited": ["The Matrix", "Keanu Reeves", "Laurence Fishburne", "Speed"]
}
```

### Graph - Árboles de Expansión Mínima

#### Prim MST
```http
GET /graph/prim
```
Retorna el Árbol de Expansión Mínima usando el algoritmo de Prim

#### Kruskal MST
```http
GET /graph/kruskal
```
Retorna el Árbol de Expansión Mínima usando el algoritmo de Kruskal

### Graph - Utilidades

#### Visualizar Grafo como String
```http
GET /graph/string
```
Retorna representación textual del grafo completo

## 📝 Ejemplos de Uso

### Ejemplo 1: Crear una Película con cURL

```bash
curl -X PUT "http://localhost:8080/movies" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Aeon Flux",
    "description": "Reactive is the new cool",
    "releaseYear": 2005,
    "actors": [
      {"name": "Charlize Theron", "born": 1975}
    ],
    "directors": [
      {"name": "Karyn Kusama", "born": 1968}
    ]
  }'
```

### Ejemplo 2: Ver Todas las Películas

```bash
curl -X GET "http://localhost:8080/movies" \
  -H "Accept: text/event-stream"
```

### Ejemplo 3: BFS desde The Matrix

```bash
curl -X GET "http://localhost:8080/graph/bfs/The%20Matrix"
```

### Ejemplo 4: Encontrar Camino entre Películas

```bash
curl -X GET "http://localhost:8080/graph/shortest-path/The%20Matrix/The%20Godfather"
```

### Ejemplo 5: Dijkstra entre Películas

```bash
curl -X GET "http://localhost:8080/graph/dijkstra/The%20Matrix/Speed"
```

### Ejemplo 6: Comparar DFS y BFS

```bash
curl -X GET "http://localhost:8080/graph/compare-traversals/The%20Matrix"
```

### Ejemplo 7: Árbol de Expansión Mínima (Prim)

```bash
curl -X GET "http://localhost:8080/graph/prim"
```

## 🎯 Algoritmos Implementados

### 1. Recorridos de Grafos
- **DFS (Depth-First Search)**: Recorrido en profundidad (recursivo e iterativo)
- **BFS (Breadth-First Search)**: Recorrido en amplitud

### 2. Caminos Mínimos
- **Camino más corto (BFS)**: Para grafos no ponderados
- **Dijkstra**: Para grafos ponderados positivos

### 3. Árboles de Expansión Mínima (MST)
- **Prim**: Usando cola de prioridad
- **Kruskal**: Usando Union-Find (Disjoint Set)

## 🗃️ Datos de Prueba

Al iniciar la aplicación, se cargan automáticamente estas películas:

1. **The Matrix** (1999)
   - Actores: Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss
   - Directores: Lana Wachowski, Lilly Wachowski

2. **Speed** (1994)
   - Actores: Keanu Reeves
   - Directores: Jan de Bont

3. **Sleepless in Seattle** (1993)
   - Actores: Tom Hanks, Meg Ryan
   - Directores: Nora Ephron

4. **You've Got Mail** (1998)
   - Actores: Tom Hanks, Meg Ryan
   - Directores: Nora Ephron

5. **The Godfather** (1972)
   - Actores: Al Pacino, Robert Duvall, Marlon Brando
   - Directores: Francis Ford Coppola

## 🔍 Consultas Cypher en Neo4j

Podés ejecutar estas consultas directamente en Neo4j Browser:

### Ver todas las películas
```cypher
MATCH (m:Movie) RETURN m
```

### Ver todas las personas
```cypher
MATCH (p:Person) RETURN p
```

### Ver películas con sus actores
```cypher
MATCH (p:Person)-[:ACTED_IN]->(m:Movie)
RETURN m.title, collect(p.name) as actors
```

### Ver películas de un actor
```cypher
MATCH (p:Person {name: "Keanu Reeves"})-[:ACTED_IN]->(m:Movie)
RETURN m.title, m.releaseYear
```

### Camino entre dos personas
```cypher
MATCH path = shortestPath(
  (a:Person {name: "Keanu Reeves"})-[*]-(b:Person {name: "Tom Hanks"})
)
RETURN path
```

## 🧪 Testing

Para verificar que todo funciona correctamente:

1. Iniciar la aplicación: `mvn spring-boot:run`
2. Verificar que se cargaron los datos: ver logs con "✅ Base de datos inicializada"
3. Probar endpoints:
   - GET `http://localhost:8080/movies` - Ver películas
   - GET `http://localhost:8080/graph/info` - Info del grafo
   - GET `http://localhost:8080/graph/bfs/The Matrix` - BFS

## 📚 Referencias

- [Spring Data Neo4j](https://docs.spring.io/spring-data/neo4j/docs/current/reference/html/)
- [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/)
- [Project Reactor](https://projectreactor.io/docs/core/release/reference/)
- [Neo4j Java Driver](https://neo4j.com/docs/java-manual/current/)

## 👨‍💻 Autor

Trabajo Práctico - UADE Programación 3

## 📄 Licencia

Este proyecto es parte de un trabajo práctico académico de UADE.
