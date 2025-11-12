// SCRIPT SIMPLIFICADO PARA POBLAR LA BASE DE DATOS
// Copia y pega este código en Neo4j Browser (http://localhost:7474)

// Limpiar todo
MATCH (n) DETACH DELETE n;

// Crear películas y actores en una sola consulta
CREATE 
  // Películas
  (matrix:Movie {title: "The Matrix", tagline: "Welcome to the Real World", released: 1999}),
  (speed:Movie {title: "Speed", tagline: "Get ready for rush hour", released: 1994}),
  (wick:Movie {title: "John Wick", tagline: "Don't set him off", released: 2014}),
  (constantine:Movie {title: "Constantine", tagline: "Hell wants him", released: 2005}),
  
  // Actores
  (keanu:Person {name: "Keanu Reeves", born: 1964}),
  (laurence:Person {name: "Laurence Fishburne", born: 1961}),
  (carrie:Person {name: "Carrie-Anne Moss", born: 1967}),
  (sandra:Person {name: "Sandra Bullock", born: 1964}),
  (hugo:Person {name: "Hugo Weaving", born: 1960}),
  (dennis:Person {name: "Dennis Hopper", born: 1936}),
  
  // Directores
  (lana:Person {name: "Lana Wachowski", born: 1965}),
  (jan:Person {name: "Jan de Bont", born: 1943}),
  (chad:Person {name: "Chad Stahelski", born: 1968}),
  (francis:Person {name: "Francis Lawrence", born: 1971}),
  
  // Relaciones ACTED_IN
  (keanu)-[:ACTED_IN {roles: ["Neo"]}]->(matrix),
  (laurence)-[:ACTED_IN {roles: ["Morpheus"]}]->(matrix),
  (carrie)-[:ACTED_IN {roles: ["Trinity"]}]->(matrix),
  (hugo)-[:ACTED_IN {roles: ["Agent Smith"]}]->(matrix),
  
  (keanu)-[:ACTED_IN {roles: ["Jack Traven"]}]->(speed),
  (sandra)-[:ACTED_IN {roles: ["Annie Porter"]}]->(speed),
  (dennis)-[:ACTED_IN {roles: ["Howard Payne"]}]->(speed),
  
  (keanu)-[:ACTED_IN {roles: ["John Wick"]}]->(wick),
  (keanu)-[:ACTED_IN {roles: ["John Constantine"]}]->(constantine),
  
  // Relaciones DIRECTED
  (lana)-[:DIRECTED]->(matrix),
  (jan)-[:DIRECTED]->(speed),
  (chad)-[:DIRECTED]->(wick),
  (francis)-[:DIRECTED]->(constantine);
