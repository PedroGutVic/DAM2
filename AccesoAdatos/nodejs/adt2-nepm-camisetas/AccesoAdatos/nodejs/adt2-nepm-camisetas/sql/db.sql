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

