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
INSERT INTO `agremiado` VALUES (1,'sbjo161021@upemor.edu.mx',1,'qwerty','qwerty',1,'H'),(2,'dtfo170113@upemor.edu.mx',1,'12346','1234',2,'H'),(3,'fred@gmail.com',5,'UPEMOR','UPEMOR',1,'H');
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
INSERT INTO `asistenciaWebinar` VALUES (1,1,1,2),(2,2,1,4);
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
INSERT INTO `gradoEstudio` VALUES (1,'Doctorado2'),(2,'Maestría'),(3,'321'),(4,'123'),(5,'Dios');
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
INSERT INTO `pais` VALUES (1,'México'),(2,'Canada'),(3,'Argentina'),(4,'666'),(5,'111'),(6,'Wakanda xd');
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
INSERT INTO `proximoEvento` VALUES (1,'qqqqqq','2020-11-30 17:00:00','qqqqqq','qqqqqq',1),(2,'123','2020-11-29 17:00:00','123','123',1),(3,'wwwwwq','2021-02-10 17:00:00','wwwwwwq','wwwwq',1);
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
INSERT INTO `proximoWebinar` VALUES (1,'2021-02-12 17:00:00','lllllllllllllllllllllllllllllllllllllllp','UTEZ','Jonathan','Webinar UTEZ1',1),(2,'2021-02-12 17:00:00','qqq','qqqq','qq','qq',1);
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
INSERT INTO `tutorial` VALUES (1,'qqqqq','Tutorial1','qqqqqq',1),(2,'qwert','Tutorial2','asdfg',1),(3,'tutorial3','tutorial','fred',1),(4,'xxx','xxx','xxx',1);
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
INSERT INTO `tutorialSesion` VALUES (1,'xd','xd','xd',1,'xd',1),(2,'123','123','123',1,'123',1),(3,'ppp','ppp','ppp',1,'ppp',1),(4,'qqqqq','qqqqq','qqqqq',2,'qqqqq',1),(5,'wwww','wwww','wwww',1,'wwww',1),(6,'1234567890','1234567890','1234567890',2,'1234567890',1),(7,'0987654321','098765432','097654321',2,'0987654321',1),(8,'qwerty','qwerty','qwerty',2,'qwerty',1),(9,'Prueba1','Prueba1','Prueba1',3,'Prueba1',1),(10,'prueba2','prueba2','prueba2',3,'prueba2',1),(11,'prueba3','prueba3','prueba3',3,'prueba3',1),(12,'prueba4','prueba4','prueba4',3,'prueba4',1),(13,'prueba5','prueba5','prueba5',3,'prueba5',1),(14,'prueba6','prueba6','prueba6',3,'prueba6',1),(15,'prueba7','prueba7','prueba7',3,'prueba7',1),(16,'prueba8','prueba8','prueba8',3,'prueba8',1),(17,'prueba9','prueba9','prueba9',3,'prueba9',1),(18,'prueba10','prueba10','prueba10',3,'prueba10',1),(19,'prueba10','prueba10','prueba10',3,'prueba10',1),(20,'prueba10','prueba10','prueba10',3,'prueba10',1),(21,'prueba10','prueba10','prueba10',3,'prueba10',1),(22,'prueba11','prueba11','prueba11',3,'prueba11',1),(23,'prueba12','prueba12','prueba12',3,'prueba12',1),(24,'prueba1','prueba1','prueba1',4,'prueba1',1),(25,'prueba2','prueba2','prueba2',4,'prueba2',1),(26,'prueba3','prueba3','prueba3',4,'prueba3',1),(27,'prueba4','prueba4','prueba4',4,'prueba4',1),(28,'prueba5','prueba5','prueba5',4,'prueba5',1),(29,'prueba6','prueba6','prueba6',4,'prueba6',1),(30,'prueba7','prueba7','prueba7',4,'prueba7',1),(31,'prueba77','prueba77','prueba77',4,'prueba77',1),(32,'prueba88','prueba88','prueba88',4,'prueba88',1),(33,'prueba99','prueba99','prueba99',4,'prueba99',1),(34,'123','123','123',4,'123',1),(35,'111','111','111',4,'111',1);
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
INSERT INTO `usuario` VALUES (1,1,0,'fred@gmail.com','Fredy De la cruz Téllez','123',1),(2,1,0,'dtfo170113@upemor.edu.mx','Jose','12345678',1),(3,1,0,'123@gmail.com','111','111111111',2),(4,1,0,'fredlacruz@outlook.com','xdxd','123456789',1),(5,1,0,'sbjo161021@upemor.edu.mx','Jonny','1234567890',4);
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
INSERT INTO `usuarioGrupo` VALUES (1,'Administrador'),(2,'000'),(3,'111'),(4,'Dios');
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
INSERT INTO `webinarRealizado` VALUES (1,'2020-11-29 17:00:24','UTEX','Matematicas','Jose Alfredo Benitez','qwertyuiop','qwertyuiop'),(2,'2020-11-29 17:00:43','UPEMOR','Programación','Roberto Enrique Lopez Díaz','qwertyuiop1qwe','qwertyuiop1qwe'),(3,'2020-11-01 17:00:00','123','123','123','123','123'),(4,'2021-02-09 17:00:00','qqq','qqqw','qqq','qqq','qqq');
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

-- Dump completed on 2021-02-11 20:58:45
