CREATE DATABASE IF NOT EXISTS `gestion`;

USE `gestion`;

DROP TABLE IF EXISTS alumno_asignatura;
DROP TABLE IF EXISTS profesor_asignatura;
DROP TABLE IF EXISTS alumno;
DROP TABLE IF EXISTS profesor;
DROP TABLE IF EXISTS asignatura;

-- para los logins 
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);

INSERT INTO `users` (`username`, `password`)
	VALUES ('pepe', 'Secreto_123');

CREATE TABLE alumno (
	id INT AUTO_INCREMENT PRIMARY KEY, 
	nombre VARCHAR(25) NOT NULL , 
	apellido VARCHAR(50) NOT NULL ) ;
	
CREATE TABLE profesor (
	id INT AUTO_INCREMENT PRIMARY KEY, 
	nombre VARCHAR(25) NOT NULL , 
	apellido VARCHAR(50) NOT NULL );

CREATE TABLE asignatura (
	id INT AUTO_INCREMENT PRIMARY KEY, 
	nombre VARCHAR(60) NOT NULL ,
	curso SMALLINT,
	ciclo VARCHAR(50));
	
CREATE TABLE alumno_asignatura (
	alumno	INT NOT NULL,
	asignatura	INT NOT NULL,
	PRIMARY KEY (alumno, asignatura), 
	FOREIGN KEY (alumno) REFERENCES alumno(id) ,
	FOREIGN KEY (asignatura) REFERENCES asignatura(id) 
);

CREATE TABLE profesor_asignatura (
	profesor	INT NOT NULL,
	asignatura	INT NOT NULL,
	PRIMARY KEY (profesor, asignatura),
	FOREIGN KEY (profesor) REFERENCES profesor(id),
	FOREIGN KEY (asignatura) REFERENCES asignatura(id) 
);

