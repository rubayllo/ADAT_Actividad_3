-- Creación de la tabla Autor
CREATE TABLE Autor
(
    id     SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

-- Creación de la tabla Libro
CREATE TABLE Libro
(
    id        SERIAL PRIMARY KEY,
    titulo    VARCHAR(255) NOT NULL,
    autor_id INT REFERENCES Autor (id) ON DELETE CASCADE
);