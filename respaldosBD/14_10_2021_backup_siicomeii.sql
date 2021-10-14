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
  `correo` varchar(255) NOT NULL,
  `created_at` date DEFAULT NULL,
  `gradoEstudio` bigint(20) DEFAULT NULL,
  `institucion` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `pais` bigint(20) DEFAULT NULL,
  `sexo` char(1) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `urlIcon` tinyint(1) DEFAULT 0,
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
INSERT INTO `agremiado` VALUES (1,'a','2021-08-21',1,'A','nam',1,'M','2021-09-12 02:00:22',0),(2,'non@interdumliberodui.edu','2021-04-08',1,'COMEII','Paul Blair',2,'M','2021-10-10 21:00:32',NULL),(3,'congue.turpis.In@pede.org','2022-01-24',1,'IIM','Keaton Reed',3,'H','2021-08-30 00:35:49',NULL),(4,'non.massa@imperdieteratnonummy.com','2021-07-01',1,'IMTA','Dane Mckee',2,'M','2021-08-30 00:35:52',NULL),(5,'auctor@metusvitaevelit.com','2021-02-24',1,'IIM','Donovan Bright',1,'M','2021-09-08 17:15:50',NULL),(6,'molestie.tellus.Aenean@Proin.edu','2022-07-20',1,'CEMAC','Basil Harrell',2,'H','2021-08-30 00:35:55',NULL),(7,'ut@sedpede.org','2020-10-14',1,'COMEII','Austin Douglas',3,'H','2021-09-08 17:15:41',NULL),(8,'sed.pede.nec@vehicula.net','2021-09-02',1,'COMEII','Trevor Avila',1,'H','2021-08-07 23:15:05',NULL),(9,'sapien@tortor.ca','2021-07-19',1,'IIM','Raymond Coffey',1,'M','2021-08-07 23:15:19',NULL),(10,'sem.elit@commodo.edu','2021-08-31',1,'C','Armand Charles',1,'M','2021-09-12 19:01:45',NULL),(11,'fermentum@lobortis.ca','2021-04-28',1,'IMTA','Davis Rivers',1,'M','2021-08-07 23:15:32',NULL);
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
  `agremiado` bigint(20) NOT NULL,
  `usuario` bigint(20) NOT NULL,
  `webinar` bigint(20) NOT NULL,
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
INSERT INTO `asistenciawebinar` VALUES (1,4,1,6),(2,4,1,6),(3,10,1,2),(4,6,1,7),(5,5,1,3),(6,8,1,3),(7,11,1,2),(8,6,1,2),(9,2,1,3),(10,3,1,3),(11,6,1,4),(12,5,1,3);
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
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradoestudio`
--

LOCK TABLES `gradoestudio` WRITE;
/*!40000 ALTER TABLE `gradoestudio` DISABLE KEYS */;
INSERT INTO `gradoestudio` VALUES (1,'primaria');
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
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,'México'),(2,'Venezuela'),(3,'Perú'),(4,'San Salvador');
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
  `descripcion` varchar(300) NOT NULL,
  `fecha` datetime NOT NULL,
  `imagen` longtext DEFAULT NULL,
  `titulo` varchar(255) NOT NULL,
  `usuario` bigint(20) DEFAULT NULL,
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
INSERT INTO `proximowebinar` VALUES (1,'2021-04-30 22:59:00','urlImg','IMTA','Kareem Brennan','hendrerit',1),(2,'2021-03-24 10:59:00','urlImg','ESSEC','Hayden Webb','Morbi',1),(3,'2020-10-29 16:08:00','urlImg','ESSEC','Nathan Landry','tincidunt,',1),(4,'2021-01-07 21:29:00','urlImg','IMAC','Amery Dean','Pellentesque',1),(5,'2021-02-16 15:57:00','urlImg','IMAC','Chadwick Stanley','non',1),(6,'2021-02-17 07:48:00','urlImg','ESSEC','Matthew Gallegos','Sed',1),(7,'2021-06-23 08:07:00','urlImg','ESSEC','Ronan Barber','purus',1),(8,'2021-12-30 06:39:00','urlImg','IMAC','Ivan Mcbride','ligula.',1),(9,'2021-11-21 15:33:00','urlImg','ESSEC','Jackson Reed','enim,',1),(10,'2021-11-02 21:54:00','urlImg','IMTA','Hyatt Kinney','Phasellus',1),(11,'2015-10-15 21:16:36',NULL,'IIRG','Juán Rodríguez','Riego II',1),(12,'2021-07-07 14:50:19','img','CONAGUA','Pedro Suárez','Micro Aspersión',1);
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
INSERT INTO `usuario` VALUES (1,1,0,'admin','ad','12345678',1);
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
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuariogrupo`
--

LOCK TABLES `usuariogrupo` WRITE;
/*!40000 ALTER TABLE `usuariogrupo` DISABLE KEYS */;
INSERT INTO `usuariogrupo` VALUES (1,'ad');
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
  `asistentes` smallint(6) NOT NULL,
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
INSERT INTO `webinarrealizado` VALUES (1,3,'2021-09-02 22:59:00','IMTA','hendrerit','Kareem Brennan','a','a'),(2,7,'2021-03-18 07:59:00','ESSEC','Morbi','Hayden Webb','b','b'),(3,2,'2020-10-29 16:08:00','ESSEC','tincidunt,','Nathan Landry','c','c'),(4,2,'2021-01-07 21:29:00','IMAC','Pellentesque','Amery Dean','c','c'),(5,5,'2015-10-15 21:16:36','IIRG','Riego II','Juán Rodríguez','uri','uri'),(6,5,'2021-07-07 14:50:19','CONAGUA','Micro Aspersión','Pedro Suárez','ur','ur'),(7,4,'2021-02-17 07:48:00','ESSEC','Sed','Matthew Gallegos','ur','ur');
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

-- Dump completed on 2021-10-14 16:13:21
