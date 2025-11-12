// Script para poblar la base de datos Neo4j con datos de prueba
// Ejecutar este script en Neo4j Browser (http://localhost:7474)

// Limpiar base de datos existente
MATCH (n) DETACH DELETE n;

// Crear películas
CREATE (:Movie {title: "The Matrix", tagline: "Welcome to the Real World", released: 1999})
CREATE (:Movie {title: "The Matrix Reloaded", tagline: "Free your mind", released: 2003})
CREATE (:Movie {title: "The Matrix Revolutions", tagline: "Everything that has a beginning has an end", released: 2003})
CREATE (:Movie {title: "Speed", tagline: "Get ready for rush hour", released: 1994})
CREATE (:Movie {title: "John Wick", tagline: "Don't set him off", released: 2014})
CREATE (:Movie {title: "Constantine", tagline: "Hell wants him. Heaven won't take him", released: 2005})
CREATE (:Movie {title: "The Devil's Advocate", tagline: "Evil has its winning ways", released: 1997})
CREATE (:Movie {title: "Point Break", tagline: "100% Pure Adrenaline", released: 1991});

// Crear actores
CREATE (:Person {name: "Keanu Reeves", born: 1964})
CREATE (:Person {name: "Laurence Fishburne", born: 1961})
CREATE (:Person {name: "Carrie-Anne Moss", born: 1967})
CREATE (:Person {name: "Hugo Weaving", born: 1960})
CREATE (:Person {name: "Sandra Bullock", born: 1964})
CREATE (:Person {name: "Dennis Hopper", born: 1936})
CREATE (:Person {name: "Al Pacino", born: 1940})
CREATE (:Person {name: "Charlize Theron", born: 1975})
CREATE (:Person {name: "Patrick Swayze", born: 1952})
CREATE (:Person {name: "Lori Petty", born: 1963});

// Crear directores
CREATE (:Person {name: "Lana Wachowski", born: 1965})
CREATE (:Person {name: "Lilly Wachowski", born: 1967})
CREATE (:Person {name: "Jan de Bont", born: 1943})
CREATE (:Person {name: "Chad Stahelski", born: 1968})
CREATE (:Person {name: "Francis Lawrence", born: 1971})
CREATE (:Person {name: "Taylor Hackford", born: 1944})
CREATE (:Person {name: "Kathryn Bigelow", born: 1951});

// Crear relaciones ACTED_IN para The Matrix
MATCH (keanu:Person {name: "Keanu Reeves"}), (matrix:Movie {title: "The Matrix"})
CREATE (keanu)-[:ACTED_IN {roles: ["Neo"]}]->(matrix);

MATCH (laurence:Person {name: "Laurence Fishburne"}), (matrix:Movie {title: "The Matrix"})
CREATE (laurence)-[:ACTED_IN {roles: ["Morpheus"]}]->(matrix);

MATCH (carrie:Person {name: "Carrie-Anne Moss"}), (matrix:Movie {title: "The Matrix"})
CREATE (carrie)-[:ACTED_IN {roles: ["Trinity"]}]->(matrix);

MATCH (hugo:Person {name: "Hugo Weaving"}), (matrix:Movie {title: "The Matrix"})
CREATE (hugo)-[:ACTED_IN {roles: ["Agent Smith"]}]->(matrix);

// Crear relaciones ACTED_IN para The Matrix Reloaded
MATCH (keanu:Person {name: "Keanu Reeves"}), (reloaded:Movie {title: "The Matrix Reloaded"})
CREATE (keanu)-[:ACTED_IN {roles: ["Neo"]}]->(reloaded);

MATCH (laurence:Person {name: "Laurence Fishburne"}), (reloaded:Movie {title: "The Matrix Reloaded"})
CREATE (laurence)-[:ACTED_IN {roles: ["Morpheus"]}]->(reloaded);

MATCH (carrie:Person {name: "Carrie-Anne Moss"}), (reloaded:Movie {title: "The Matrix Reloaded"})
CREATE (carrie)-[:ACTED_IN {roles: ["Trinity"]}]->(reloaded);

// Crear relaciones ACTED_IN para The Matrix Revolutions
MATCH (keanu:Person {name: "Keanu Reeves"}), (revolutions:Movie {title: "The Matrix Revolutions"})
CREATE (keanu)-[:ACTED_IN {roles: ["Neo"]}]->(revolutions);

MATCH (laurence:Person {name: "Laurence Fishburne"}), (revolutions:Movie {title: "The Matrix Revolutions"})
CREATE (laurence)-[:ACTED_IN {roles: ["Morpheus"]}]->(revolutions);

MATCH (carrie:Person {name: "Carrie-Anne Moss"}), (revolutions:Movie {title: "The Matrix Revolutions"})
CREATE (carrie)-[:ACTED_IN {roles: ["Trinity"]}]->(revolutions);

// Crear relaciones ACTED_IN para Speed
MATCH (keanu:Person {name: "Keanu Reeves"}), (speed:Movie {title: "Speed"})
CREATE (keanu)-[:ACTED_IN {roles: ["Jack Traven"]}]->(speed);

MATCH (sandra:Person {name: "Sandra Bullock"}), (speed:Movie {title: "Speed"})
CREATE (sandra)-[:ACTED_IN {roles: ["Annie Porter"]}]->(speed);

MATCH (dennis:Person {name: "Dennis Hopper"}), (speed:Movie {title: "Speed"})
CREATE (dennis)-[:ACTED_IN {roles: ["Howard Payne"]}]->(speed);

// Crear relaciones ACTED_IN para John Wick
MATCH (keanu:Person {name: "Keanu Reeves"}), (wick:Movie {title: "John Wick"})
CREATE (keanu)-[:ACTED_IN {roles: ["John Wick"]}]->(wick);

// Crear relaciones ACTED_IN para Constantine
MATCH (keanu:Person {name: "Keanu Reeves"}), (constantine:Movie {title: "Constantine"})
CREATE (keanu)-[:ACTED_IN {roles: ["John Constantine"]}]->(constantine);

// Crear relaciones ACTED_IN para The Devil's Advocate
MATCH (keanu:Person {name: "Keanu Reeves"}), (devils:Movie {title: "The Devil's Advocate"})
CREATE (keanu)-[:ACTED_IN {roles: ["Kevin Lomax"]}]->(devils);

MATCH (al:Person {name: "Al Pacino"}), (devils:Movie {title: "The Devil's Advocate"})
CREATE (al)-[:ACTED_IN {roles: ["John Milton"]}]->(devils);

MATCH (charlize:Person {name: "Charlize Theron"}), (devils:Movie {title: "The Devil's Advocate"})
CREATE (charlize)-[:ACTED_IN {roles: ["Mary Ann Lomax"]}]->(devils);

// Crear relaciones ACTED_IN para Point Break
MATCH (keanu:Person {name: "Keanu Reeves"}), (point:Movie {title: "Point Break"})
CREATE (keanu)-[:ACTED_IN {roles: ["Johnny Utah"]}]->(point);

MATCH (patrick:Person {name: "Patrick Swayze"}), (point:Movie {title: "Point Break"})
CREATE (patrick)-[:ACTED_IN {roles: ["Bodhi"]}]->(point);

MATCH (lori:Person {name: "Lori Petty"}), (point:Movie {title: "Point Break"})
CREATE (lori)-[:ACTED_IN {roles: ["Tyler Endicott"]}]->(point);

// Crear relaciones DIRECTED para las películas
MATCH (lana:Person {name: "Lana Wachowski"}), (matrix:Movie {title: "The Matrix"})
CREATE (lana)-[:DIRECTED]->(matrix);

MATCH (lilly:Person {name: "Lilly Wachowski"}), (matrix:Movie {title: "The Matrix"})
CREATE (lilly)-[:DIRECTED]->(matrix);

MATCH (lana:Person {name: "Lana Wachowski"}), (reloaded:Movie {title: "The Matrix Reloaded"})
CREATE (lana)-[:DIRECTED]->(reloaded);

MATCH (lilly:Person {name: "Lilly Wachowski"}), (reloaded:Movie {title: "The Matrix Reloaded"})
CREATE (lilly)-[:DIRECTED]->(reloaded);

MATCH (lana:Person {name: "Lana Wachowski"}), (revolutions:Movie {title: "The Matrix Revolutions"})
CREATE (lana)-[:DIRECTED]->(revolutions);

MATCH (lilly:Person {name: "Lilly Wachowski"}), (revolutions:Movie {title: "The Matrix Revolutions"})
CREATE (lilly)-[:DIRECTED]->(revolutions);

MATCH (jan:Person {name: "Jan de Bont"}), (speed:Movie {title: "Speed"})
CREATE (jan)-[:DIRECTED]->(speed);

MATCH (chad:Person {name: "Chad Stahelski"}), (wick:Movie {title: "John Wick"})
CREATE (chad)-[:DIRECTED]->(wick);

MATCH (francis:Person {name: "Francis Lawrence"}), (constantine:Movie {title: "Constantine"})
CREATE (francis)-[:DIRECTED]->(constantine);

MATCH (taylor:Person {name: "Taylor Hackford"}), (devils:Movie {title: "The Devil's Advocate"})
CREATE (taylor)-[:DIRECTED]->(devils);

MATCH (kathryn:Person {name: "Kathryn Bigelow"}), (point:Movie {title: "Point Break"})
CREATE (kathryn)-[:DIRECTED]->(point);
