# Gestión tienda de camisetas

Breve descripcion (Tecnologias y cómo hemos calentado el plato).

*Contenedor docker
*Seleccionar con nvm la version de node
*Crear el proyecto node
*crear el proyecto git
*crear el .gitignore

## Tablas

1. Camisetas
    1. ID
    2. Talla
    3. Color
    4. Marca
    5. Stock
    6. Sexo ( chica, chico , unisex,niño , niña , unisex_infantil )
2. Usuarios
    1. ID
    2. Username
    3. Password (hashed)
    4. Email
    5. Telefono
    6. Direccion
3. Pedidos
    1. Fecha
    2. Procutos
    3. Estado( carrito, pagado , procesando , procesado , enviado , recibido )
    4. Cliente

```sql


-- Adminer 5.4.0 MySQL 9.3.0 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

CREATE DATABASE `camisetas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `camisetas`;

DROP TABLE IF EXISTS `Camiseta`;
CREATE TABLE `Camiseta` (
  `ID_camiseta` int NOT NULL AUTO_INCREMENT,
  `Talla` enum('xxs','xs','s','m','l','xl','xxl') NOT NULL,
  `Sexo` enum('chica','chico','unisex','niño','niña','unisex_infantil') NOT NULL,
  `Color` varchar(25) NOT NULL,
  `Marca` varchar(25) NOT NULL,
  `Stock` int NOT NULL,
  `Precio` double NOT NULL,
  PRIMARY KEY (`ID_camiseta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `Linea_Pedido`;
CREATE TABLE `Linea_Pedido` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Pedido` int NOT NULL,
  `Producto` int NOT NULL,
  `PrecioVenta` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Pedido` (`Pedido`),
  CONSTRAINT `Linea_Pedido_ibfk_1` FOREIGN KEY (`Pedido`) REFERENCES `Pedidos` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `Pedidos`;
CREATE TABLE `Pedidos` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Fecha` date NOT NULL,
  `Procutos` int NOT NULL,
  `Estado` enum('carrito, pagado , procesando , procesado , enviado , recibido') NOT NULL,
  `Cliente` int NOT NULL,
  `Total` DECIMAL(10.2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `Usuario`;
CREATE TABLE `Usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Telefono` int NOT NULL,
  `Direccion` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `activo` boolean,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

## Diseño de endpoints

RUTA                    | VERBO HTTP          | observaciones
-----                   |------------         |--------------
/                       | GET                 | Muestra el inicio de la tienda
/auth/login             | GET                 | Muestra el formulario de login
/auth/login             | POST                | Recibe datos de login y crea sesion
/auth/singin            | GET                 | Muestra el formulario de crear usuario
/auth/singin            | POST                | Recibe datos del usuario y lo crea
/logout                 | GET                 | Cierra la sesion
/logout                 | POST                | Cierra la sesion
/camisetas              | GET                 | Muestra todas las camisetas
/camisetas/{id}         | GET                 | Muestra la camisetas {id}
/camisetas/{id}/del     | GET                 | Muestra el formulario que pregunta si borrar la camiseta con esa {id}
/camisetas/del/{id}     | POST                | Elimina la camisetas {id}
/camisetas/{id}/add     | GET                 | Muestra el formulario que pregunta si añadir la camiseta con esa {id}
/camisetas/{id}/add     | POST                | Añade la camisetas {id}
/camisetas/{id}/update  | GET                 | Muestra el formulario que pregunta si actualizar la camiseta con esa {id}
/camisetas/{id}/update  | POST                | Actualiza la camisetas {id}
/carro                  | GET                 | Mostrar el carro
/carro/add/camiseta/{id}| GET                 | ""
/carro/del/camiseta/{id}| GET                 |""
/carro/del/camiseta/{id}| POST                |""
/carro/update/camiseta/{id}| GET              |""
/pedidos                | GET                 | Muestra **Todos** los pedidos
/pedidos/estado/[Estado]|GET                  | Muestra pedidos filtrados por estado
