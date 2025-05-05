-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: biblioteca
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cedula` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `rol` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `rol` (`rol`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`rol`) REFERENCES `roles` (`rolId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'123456789','Calle 123 20-20','juan@example.com','ACTIVO','Juan Pérez','123456',NULL,NULL,NULL),(2,'1040749278','Cra 64 #27-41','edtabares@gmail.com','ACTIVO','Eduar Tabares',NULL,NULL,NULL,NULL),(3,'10203040','Carrera 81 #48-48','sofiava@gmail.com','ELIMINADO','Sofia Vanegas',NULL,NULL,NULL,NULL),(4,'10203000','Cra 20 #10-10','Hernando@Chaux.com','ELIMINADO','Hernando Chaux',NULL,NULL,NULL,NULL),(5,'10203040','Cra 64 #20-30','ed123@gmail.com','ELIMINADO','Juan Sierra',NULL,NULL,NULL,NULL),(6,'10203055','Cra 64 45-10','tono@gmail.com','ACTIVO','Antonio Aguilar',NULL,NULL,NULL,NULL),(7,'10060710','Calle nueva 123','sofi_new@gmail.com','Activo','Sofia Actualizada',NULL,NULL,NULL,NULL),(8,'123412343123','ccalle 123123','adsoiflj@aksrfdj.comqwe','ACTIVO','juanito',NULL,NULL,NULL,NULL),(9,'9875654200','calle 84898','juan@juan.com','ACTIVO','juan','1234',NULL,NULL,NULL),(10,'123145121','calle 818','qweqwe@qweqwe.com','ACTIVO','Sebastian','5455',NULL,NULL,NULL),(11,'3333333333','calle 3','3333@qwe333qwe.com','ACTIVO','usuario3','443433','elusuario3',1,NULL),(12,'444444444','calle 4','44444@44444.com','ACTIVO','usuario4','443433','elusuario4',NULL,NULL),(13,'555555','calle 5','55555@5555.com','ACTIVO','user5','55555','user5555',1,NULL),(14,'6666','calle6','6666@666.com','ACTIVO','user6','123652345','user6666',1,NULL),(15,'77777777','calle7','7777@7777.com','ACTIVO','user7','$2a$10$KSQsp/ip/t/730Jh.AXWZ.xhAPg.mLBh9OmrSJoZuqDlf9AM/F0DS','user7',1,NULL),(16,'421341234','sadfwerwqe','aaa@aaa','ACTIVO','sadfsdafasd',NULL,NULL,NULL,NULL),(17,'1234561123','luna st','luna@luna.luna','ACTIVO','luna',NULL,NULL,NULL,NULL),(18,'88888888','8888','77asd77@777wqe7.com','ACTIVO','ocho','$2a$10$FSn9lujjXXvF1BV6i0G8v.mWFHzMP5cVfs0HWiW1pDwwe7W4bTkFO','user8',1,NULL),(19,'wqeqwrqwr','calleq','aaaaa@aa','ACTIVO','intento',NULL,NULL,NULL,NULL),(20,'99999999','nueve','12345@123124.com','ACTIVO','nueve','$2a$10$ONddfUvvhLfjNkbAAFp6v.6BDLjXEJMkml8QK8W2vrHmIFZJGtgy.','user9',1,1),(21,'ultimaprueba','ultima','pruba@ultima.prueba','ACTIVO','ultima prueba',NULL,NULL,NULL,NULL);
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

-- Dump completed on 2025-05-05 11:24:05
