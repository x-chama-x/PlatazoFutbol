-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: platazobd
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clasificacion`
--

DROP TABLE IF EXISTS `clasificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clasificacion` (
  `clasificacionId` int NOT NULL AUTO_INCREMENT,
  `usuarioId` int NOT NULL,
  `partidosJugados` int NOT NULL DEFAULT '0',
  `victorias` int NOT NULL DEFAULT '0',
  `derrotas` int NOT NULL DEFAULT '0',
  `empates` int NOT NULL DEFAULT '0',
  `golesFavor` int NOT NULL DEFAULT '0',
  `golesContra` int NOT NULL DEFAULT '0',
  `difGoles` int NOT NULL DEFAULT '0',
  `puntos` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`clasificacionId`,`usuarioId`),
  KEY `fk_clasificacion_equipo1_idx` (`usuarioId`),
  CONSTRAINT `fk_clasificacion_equipo1` FOREIGN KEY (`usuarioId`) REFERENCES `usuario` (`usuarioId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clasificacion`
--

LOCK TABLES `clasificacion` WRITE;
/*!40000 ALTER TABLE `clasificacion` DISABLE KEYS */;
INSERT INTO `clasificacion` VALUES (1,1,2,2,0,0,12,5,7,6),(2,2,2,2,0,0,12,6,6,6),(3,3,2,0,1,1,8,9,-1,1),(5,5,2,1,1,0,9,8,1,3),(7,7,1,0,1,0,3,6,-3,0),(9,9,1,0,1,0,2,6,-4,0),(10,10,1,0,1,0,2,6,-4,0);
/*!40000 ALTER TABLE `clasificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `golesportiempo`
--

DROP TABLE IF EXISTS `golesportiempo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `golesportiempo` (
  `golesPorTiempoId` int NOT NULL AUTO_INCREMENT,
  `tiempo` enum('primer_tiempo','segundo_tiempo','tercer_tiempo','unico_tiempo') NOT NULL,
  `golesLocal` int NOT NULL,
  `golesVisitantes` int NOT NULL,
  `partidoId` int NOT NULL,
  PRIMARY KEY (`golesPorTiempoId`,`partidoId`),
  KEY `fk_golesPorTiempo_partido_idx` (`partidoId`),
  CONSTRAINT `fk_golesPorTiempo_partido` FOREIGN KEY (`partidoId`) REFERENCES `partido` (`partidoId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `golesportiempo`
--

LOCK TABLES `golesportiempo` WRITE;
/*!40000 ALTER TABLE `golesportiempo` DISABLE KEYS */;
INSERT INTO `golesportiempo` VALUES (1,'primer_tiempo',3,1,1),(2,'segundo_tiempo',3,1,1),(3,'primer_tiempo',2,3,2),(4,'segundo_tiempo',3,0,2),(6,'primer_tiempo',2,3,3),(7,'segundo_tiempo',1,3,3),(8,'primer_tiempo',3,0,4),(9,'segundo_tiempo',3,2,4),(12,'primer_tiempo',3,2,5),(13,'segundo_tiempo',3,1,5),(15,'primer_tiempo',2,3,10),(16,'segundo_tiempo',3,0,10),(17,'tercer_tiempo',0,3,10),(18,'primer_tiempo',3,2,11),(19,'segundo_tiempo',3,1,11);
/*!40000 ALTER TABLE `golesportiempo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido`
--

DROP TABLE IF EXISTS `partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partido` (
  `partidoId` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `equipoLocaId` int NOT NULL,
  `equipoVisitanteId` int NOT NULL,
  `resultado` varchar(10) NOT NULL,
  `tipoEvento` enum('liga','mundial','otro') NOT NULL,
  `faseCopa` enum('octavos','cuartos','semifinal','final','otro') DEFAULT NULL,
  `jornada` int DEFAULT NULL,
  `estado` enum('finalizado','suspendido','en_progreso') DEFAULT NULL,
  PRIMARY KEY (`partidoId`,`equipoLocaId`,`equipoVisitanteId`),
  KEY `fk_partido_equipo1_idx` (`equipoLocaId`),
  KEY `fk_equipoVisitantelId_idx` (`equipoVisitanteId`),
  CONSTRAINT `fk_equipoLocalId` FOREIGN KEY (`equipoLocaId`) REFERENCES `usuario` (`usuarioId`),
  CONSTRAINT `fk_equipoVisitantelId` FOREIGN KEY (`equipoVisitanteId`) REFERENCES `usuario` (`usuarioId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido`
--

LOCK TABLES `partido` WRITE;
/*!40000 ALTER TABLE `partido` DISABLE KEYS */;
INSERT INTO `partido` VALUES (1,'2024-11-11',1,10,'6-2','liga',NULL,1,'finalizado'),(2,'2024-11-11',3,4,'DRAW','liga',NULL,1,'finalizado'),(3,'2024-11-11',7,2,'3-6','liga',NULL,1,'finalizado'),(4,'2024-11-20',5,9,'6-2','liga',NULL,1,'finalizado'),(5,'2024-11-25',1,3,'6-3','liga',NULL,2,'finalizado'),(6,'2024-11-11',6,8,'','liga',NULL,1,'suspendido'),(10,'2024-11-01',1,2,'5-6','mundial',NULL,NULL,'finalizado'),(11,'2024-11-25',2,5,'6-3','liga',NULL,2,'finalizado'),(12,'2024-11-25',10,9,' ','liga',NULL,2,NULL);
/*!40000 ALTER TABLE `partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `usuarioId` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `partidosJugados` int NOT NULL,
  `victorias` int NOT NULL,
  `nivel` int NOT NULL,
  `estado` enum('activo','no_activo') NOT NULL,
  PRIMARY KEY (`usuarioId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'x_chama_x',1648,878,50,'activo'),(2,'facubostero',478,255,25,'activo'),(3,'H0m3ro_s1ns0',132,69,14,'activo'),(4,'Papuyol',287,139,21,'no_activo'),(5,'Tevezista_10',22,6,5,'activo'),(6,'aliss.ctet',0,0,0,'no_activo'),(7,'tom_017',312,187,23,'activo'),(8,'Sergiohdp',268,147,20,'no_activo'),(9,'Matixdlol',93,42,12,'activo'),(10,'Moe szyslaksz',103,41,12,'activo');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-03 15:36:34
