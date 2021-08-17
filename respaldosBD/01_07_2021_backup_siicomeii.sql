-- MariaDB dump 10.19  Distrib 10.4.18-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: siiComeii
-- ------------------------------------------------------
-- Server version	10.4.18-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agremiado`
--

DROP TABLE IF EXISTS `agremiado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agremiado` (
  `id` bigint(20) NOT NULL,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `correo` varchar(255) NOT NULL,
  `gradoEstudio` bigint(20) NOT NULL,
  `institucion` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `pais` bigint(20) NOT NULL,
  `sexo` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`),
  KEY `FK_agremiado_gradoEstudio` (`gradoEstudio`),
  KEY `FK_agremiado_pais` (`pais`),
  CONSTRAINT `FK_agremiado_gradoEstudio` FOREIGN KEY (`gradoEstudio`) REFERENCES `gradoestudio` (`id`),
  CONSTRAINT `FK_agremiado_pais` FOREIGN KEY (`pais`) REFERENCES `pais` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agremiado`
--

LOCK TABLES `agremiado` WRITE;
/*!40000 ALTER TABLE `agremiado` DISABLE KEYS */;
INSERT INTO `agremiado` VALUES (1,0,'megareydereyes@gmail.com',2,'1','b',1,'M');
/*!40000 ALTER TABLE `agremiado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asistenciawebinar`
--

DROP TABLE IF EXISTS `asistenciawebinar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asistenciawebinar` (
  `id` bigint(20) NOT NULL,
  `agremiado` bigint(20) DEFAULT NULL,
  `usuario` bigint(20) DEFAULT NULL,
  `webinar` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_asistenciaWebinar_webinar` (`webinar`),
  KEY `FK_asistenciaWebinar_usuario` (`usuario`),
  KEY `FK_asistenciaWebinar_agremiado` (`agremiado`),
  CONSTRAINT `FK_asistenciaWebinar_agremiado` FOREIGN KEY (`agremiado`) REFERENCES `agremiado` (`id`),
  CONSTRAINT `FK_asistenciaWebinar_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK_asistenciaWebinar_webinar` FOREIGN KEY (`webinar`) REFERENCES `webinarrealizado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistenciawebinar`
--

LOCK TABLES `asistenciawebinar` WRITE;
/*!40000 ALTER TABLE `asistenciawebinar` DISABLE KEYS */;
/*!40000 ALTER TABLE `asistenciawebinar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gradoestudio`
--

DROP TABLE IF EXISTS `gradoestudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gradoestudio` (
  `id` bigint(20) NOT NULL,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradoestudio`
--

LOCK TABLES `gradoestudio` WRITE;
/*!40000 ALTER TABLE `gradoestudio` DISABLE KEYS */;
INSERT INTO `gradoestudio` VALUES (2,0,'primariad'),(3,0,'a'),(5,0,'abc ñ');
/*!40000 ALTER TABLE `gradoestudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pais` (
  `id` bigint(20) NOT NULL,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,0,'mxe'),(2,0,'ar');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proximoevento`
--

DROP TABLE IF EXISTS `proximoevento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proximoevento` (
  `id` bigint(20) NOT NULL,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `descripcion` varchar(255) NOT NULL,
  `fecha` datetime NOT NULL,
  `imagen` longtext DEFAULT NULL,
  `titulo` varchar(255) NOT NULL,
  `usuario` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_proximoEvento_usuario` (`usuario`),
  CONSTRAINT `FK_proximoEvento_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proximoevento`
--

LOCK TABLES `proximoevento` WRITE;
/*!40000 ALTER TABLE `proximoevento` DISABLE KEYS */;
INSERT INTO `proximoevento` VALUES (1,0,'d','2021-06-30 00:00:00','d','d',1),(2,0,'e','2021-07-21 16:00:00','e','e',1),(3,0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit.','2021-06-30 21:18:00','img','tit',1);
/*!40000 ALTER TABLE `proximoevento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proximowebinar`
--

DROP TABLE IF EXISTS `proximowebinar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proximowebinar` (
  `id` bigint(20) NOT NULL,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `fecha` datetime NOT NULL,
  `imagen` longtext DEFAULT NULL,
  `institucion` varchar(255) NOT NULL,
  `ponente` varchar(255) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `usuario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_proximoWebinar_usuario` (`usuario`),
  CONSTRAINT `FK_proximoWebinar_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proximowebinar`
--

LOCK TABLES `proximowebinar` WRITE;
/*!40000 ALTER TABLE `proximowebinar` DISABLE KEYS */;
INSERT INTO `proximowebinar` VALUES (1,0,'2021-06-30 22:00:00','a','a','a','a',1);
/*!40000 ALTER TABLE `proximowebinar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial`
--

DROP TABLE IF EXISTS `tutorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial` (
  `id` bigint(20) NOT NULL,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `institucion` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `tutor` varchar(255) NOT NULL,
  `usuario` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tutorial_usuario` (`usuario`),
  CONSTRAINT `FK_tutorial_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial`
--

LOCK TABLES `tutorial` WRITE;
/*!40000 ALTER TABLE `tutorial` DISABLE KEYS */;
INSERT INTO `tutorial` VALUES (1,0,'a','a','1',1),(2,0,'a','b','aa',1);
/*!40000 ALTER TABLE `tutorial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorialsesion`
--

DROP TABLE IF EXISTS `tutorialsesion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorialsesion` (
  `id` bigint(20) NOT NULL,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `institucion` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `tutor` varchar(255) NOT NULL,
  `tutorial` bigint(20) DEFAULT NULL,
  `urlYoutube` longtext DEFAULT NULL,
  `usuario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tutorialSesion_usuario` (`usuario`),
  KEY `FK_tutorialSesion_tutorial` (`tutorial`),
  CONSTRAINT `FK_tutorialSesion_tutorial` FOREIGN KEY (`tutorial`) REFERENCES `tutorial` (`id`),
  CONSTRAINT `FK_tutorialSesion_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorialsesion`
--

LOCK TABLES `tutorialsesion` WRITE;
/*!40000 ALTER TABLE `tutorialsesion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorialsesion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT 0,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `cambiarPassword` tinyint(1) NOT NULL DEFAULT 0,
  `correo` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `usuarioGrupo` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`),
  KEY `FK_usuario_usuarioGrupo` (`usuarioGrupo`),
  CONSTRAINT `FK_usuario_usuarioGrupo` FOREIGN KEY (`usuarioGrupo`) REFERENCES `usuariogrupo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,1,0,'admin','ad','12345678',1),(4,1,0,0,'midibi4372@pigicorn.com','a','12345678',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuariogrupo`
--

DROP TABLE IF EXISTS `usuariogrupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuariogrupo` (
  `id` bigint(20) NOT NULL,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuariogrupo`
--

LOCK TABLES `usuariogrupo` WRITE;
/*!40000 ALTER TABLE `usuariogrupo` DISABLE KEYS */;
INSERT INTO `usuariogrupo` VALUES (1,1,'admin'),(2,0,'a'),(3,0,'b'),(5,0,'aa');
/*!40000 ALTER TABLE `usuariogrupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webinarrealizado`
--

DROP TABLE IF EXISTS `webinarrealizado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webinarrealizado` (
  `id` bigint(20) NOT NULL,
  `bandera` tinyint(1) NOT NULL DEFAULT 0,
  `fecha` datetime NOT NULL,
  `institucion` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `ponente` varchar(255) NOT NULL,
  `presentacion` text DEFAULT NULL,
  `urlYoutube` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webinarrealizado`
--

LOCK TABLES `webinarrealizado` WRITE;
/*!40000 ALTER TABLE `webinarrealizado` DISABLE KEYS */;
INSERT INTO `webinarrealizado` VALUES (1,0,'2021-06-28 16:00:00','a','a','a','a','a');
/*!40000 ALTER TABLE `webinarrealizado` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-01 20:27:38
