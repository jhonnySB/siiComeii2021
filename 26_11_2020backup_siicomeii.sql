-- MariaDB dump 10.18  Distrib 10.5.8-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: siiComeii
-- ------------------------------------------------------
-- Server version	10.5.8-MariaDB

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
  `correo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gradoEstudio` bigint(20) DEFAULT NULL,
  `institucion` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pais` bigint(20) NOT NULL,
  `sexo` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`),
  KEY `FK_agremiado_gradoEstudio` (`gradoEstudio`),
  KEY `FK_agremiado_pais` (`pais`),
  CONSTRAINT `FK_agremiado_gradoEstudio` FOREIGN KEY (`gradoEstudio`) REFERENCES `gradoEstudio` (`id`),
  CONSTRAINT `FK_agremiado_pais` FOREIGN KEY (`pais`) REFERENCES `pais` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agremiado`
--

LOCK TABLES `agremiado` WRITE;
/*!40000 ALTER TABLE `agremiado` DISABLE KEYS */;
INSERT INTO `agremiado` VALUES (5,'sbjo161021@upemor.edu.mx',2,'UPEMOR','Jonh',2,'H'),(6,'tegchome123@gmail.com',1,'UPEMOR','Jonh 2',1,'H');
/*!40000 ALTER TABLE `agremiado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asistenciaWebinar`
--

DROP TABLE IF EXISTS `asistenciaWebinar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asistenciaWebinar` (
  `id` bigint(20) NOT NULL,
  `agremiado` bigint(20) DEFAULT NULL,
  `usuario` bigint(20) DEFAULT NULL,
  `webinar` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_asistenciaWebinar_webinar` (`webinar`),
  KEY `FK_asistenciaWebinar_usuario` (`usuario`),
  KEY `FK_asistenciaWebinar_agremiado` (`agremiado`),
  CONSTRAINT `FK_asistenciaWebinar_agremiado` FOREIGN KEY (`agremiado`) REFERENCES `agremiado` (`id`),
  CONSTRAINT `FK_asistenciaWebinar_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK_asistenciaWebinar_webinar` FOREIGN KEY (`webinar`) REFERENCES `webinarRealizado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistenciaWebinar`
--

LOCK TABLES `asistenciaWebinar` WRITE;
/*!40000 ALTER TABLE `asistenciaWebinar` DISABLE KEYS */;
/*!40000 ALTER TABLE `asistenciaWebinar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gradoEstudio`
--

DROP TABLE IF EXISTS `gradoEstudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gradoEstudio` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradoEstudio`
--

LOCK TABLES `gradoEstudio` WRITE;
/*!40000 ALTER TABLE `gradoEstudio` DISABLE KEYS */;
INSERT INTO `gradoEstudio` VALUES (1,'Doctorado2'),(2,'Maestría');
/*!40000 ALTER TABLE `gradoEstudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pais` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,'México'),(2,'Canada'),(3,'Argentina');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proximoEvento`
--

DROP TABLE IF EXISTS `proximoEvento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proximoEvento` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha` datetime NOT NULL,
  `imagen` longtext COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `titulo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usuario` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_proximoEvento_usuario` (`usuario`),
  CONSTRAINT `FK_proximoEvento_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proximoEvento`
--

LOCK TABLES `proximoEvento` WRITE;
/*!40000 ALTER TABLE `proximoEvento` DISABLE KEYS */;
/*!40000 ALTER TABLE `proximoEvento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proximoWebinar`
--

DROP TABLE IF EXISTS `proximoWebinar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proximoWebinar` (
  `id` bigint(20) NOT NULL,
  `fecha` datetime NOT NULL,
  `imagen` longtext COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `institucion` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ponente` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `titulo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usuario` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_proximoWebinar_usuario` (`usuario`),
  CONSTRAINT `FK_proximoWebinar_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proximoWebinar`
--

LOCK TABLES `proximoWebinar` WRITE;
/*!40000 ALTER TABLE `proximoWebinar` DISABLE KEYS */;
/*!40000 ALTER TABLE `proximoWebinar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial`
--

DROP TABLE IF EXISTS `tutorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial` (
  `id` bigint(20) NOT NULL,
  `institucion` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tutor` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usuario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tutorial_usuario` (`usuario`),
  CONSTRAINT `FK_tutorial_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial`
--

LOCK TABLES `tutorial` WRITE;
/*!40000 ALTER TABLE `tutorial` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorialSesion`
--

DROP TABLE IF EXISTS `tutorialSesion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorialSesion` (
  `id` bigint(20) NOT NULL,
  `institucion` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tutor` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tutorial` bigint(20) DEFAULT NULL,
  `urlYoutube` longtext COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usuario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tutorialSesion_usuario` (`usuario`),
  KEY `FK_tutorialSesion_tutorial` (`tutorial`),
  CONSTRAINT `FK_tutorialSesion_tutorial` FOREIGN KEY (`tutorial`) REFERENCES `tutorial` (`id`),
  CONSTRAINT `FK_tutorialSesion_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorialSesion`
--

LOCK TABLES `tutorialSesion` WRITE;
/*!40000 ALTER TABLE `tutorialSesion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorialSesion` ENABLE KEYS */;
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
  `cambiarPassword` tinyint(1) NOT NULL DEFAULT 0,
  `correo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usuarioGrupo` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`),
  KEY `FK_usuario_usuarioGrupo` (`usuarioGrupo`),
  CONSTRAINT `FK_usuario_usuarioGrupo` FOREIGN KEY (`usuarioGrupo`) REFERENCES `usuarioGrupo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,0,'fred@gmail.com','Fredy De la cruz Téllez','123',1),(2,1,0,'delacruzfredy133@gmail.com','Fred De la cruz Téllez','123',1),(3,1,0,'dtfo170113@upemor.edu.mx','Prueba','123',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarioGrupo`
--

DROP TABLE IF EXISTS `usuarioGrupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarioGrupo` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarioGrupo`
--

LOCK TABLES `usuarioGrupo` WRITE;
/*!40000 ALTER TABLE `usuarioGrupo` DISABLE KEYS */;
INSERT INTO `usuarioGrupo` VALUES (1,'Administrador');
/*!40000 ALTER TABLE `usuarioGrupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webinarRealizado`
--

DROP TABLE IF EXISTS `webinarRealizado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webinarRealizado` (
  `id` bigint(20) NOT NULL,
  `fecha` datetime NOT NULL,
  `institucion` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ponente` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `presentacion` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `urlYoutube` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webinarRealizado`
--

LOCK TABLES `webinarRealizado` WRITE;
/*!40000 ALTER TABLE `webinarRealizado` DISABLE KEYS */;
INSERT INTO `webinarRealizado` VALUES (1,'2020-11-27 17:00:00','q','q','q','q','q');
/*!40000 ALTER TABLE `webinarRealizado` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-26 16:33:53
