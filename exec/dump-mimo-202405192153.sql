-- MySQL dump 10.13  Distrib 8.3.0, for macos14.2 (arm64)
--
-- Host: k10a204.p.ssafy.io    Database: mimo
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `curtain`
--

DROP TABLE IF EXISTS `curtain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curtain` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_registered` bit(1) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `registered_dttm` datetime(6) DEFAULT NULL,
  `unregistered_dttm` datetime(6) DEFAULT NULL,
  `is_accessible` bit(1) NOT NULL,
  `mac_address` varchar(255) DEFAULT NULL,
  `open_degree` int DEFAULT NULL,
  `hub_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrly1rq9hr6d9f62bvmo3h5e9j` (`hub_id`),
  KEY `FKev7lsako8rer03gehqrx57fen` (`user_id`),
  CONSTRAINT `FKev7lsako8rer03gehqrx57fen` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrly1rq9hr6d9f62bvmo3h5e9j` FOREIGN KEY (`hub_id`) REFERENCES `hub` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curtain`
--

LOCK TABLES `curtain` WRITE;
/*!40000 ALTER TABLE `curtain` DISABLE KEYS */;
INSERT INTO `curtain` VALUES (1,_binary '','윤동휘의 커튼','2024-05-19 20:01:24.813094',NULL,_binary '','E4:65:B8:0F:FB:16',100,1,1);
/*!40000 ALTER TABLE `curtain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `house`
--

DROP TABLE IF EXISTS `house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `house` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `registered_dttm` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `unregistered_dttm` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `house`
--

LOCK TABLES `house` WRITE;
/*!40000 ALTER TABLE `house` DISABLE KEYS */;
INSERT INTO `house` VALUES (1,_binary '','2024-05-19 19:50:56',NULL,'서울특별시 관악구 봉천동 1688-127');
/*!40000 ALTER TABLE `house` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hub`
--

DROP TABLE IF EXISTS `hub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hub` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_registered` bit(1) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `registered_dttm` datetime(6) DEFAULT NULL,
  `unregistered_dttm` datetime(6) DEFAULT NULL,
  `serial_number` varchar(255) DEFAULT NULL,
  `house_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlxnerepl1d2ruark659433su` (`house_id`),
  CONSTRAINT `FKlxnerepl1d2ruark659433su` FOREIGN KEY (`house_id`) REFERENCES `house` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hub`
--

LOCK TABLES `hub` WRITE;
/*!40000 ALTER TABLE `hub` DISABLE KEYS */;
INSERT INTO `hub` VALUES (1,_binary '',NULL,'2024-05-19 19:50:55.813978',NULL,'10000000e63286b1',1);
/*!40000 ALTER TABLE `hub` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lamp`
--

DROP TABLE IF EXISTS `lamp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lamp` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_registered` bit(1) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `registered_dttm` datetime(6) DEFAULT NULL,
  `unregistered_dttm` datetime(6) DEFAULT NULL,
  `is_accessible` bit(1) NOT NULL,
  `mac_address` varchar(255) DEFAULT NULL,
  `cur_color` varchar(255) DEFAULT NULL,
  `wakeup_color` varchar(255) DEFAULT NULL,
  `hub_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7xs0kfw93e0pi794c05mhntgd` (`hub_id`),
  KEY `FKj7iowqkrr9tdtvraipxyjvbc7` (`user_id`),
  CONSTRAINT `FK7xs0kfw93e0pi794c05mhntgd` FOREIGN KEY (`hub_id`) REFERENCES `hub` (`id`),
  CONSTRAINT `FKj7iowqkrr9tdtvraipxyjvbc7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lamp`
--

LOCK TABLES `lamp` WRITE;
/*!40000 ALTER TABLE `lamp` DISABLE KEYS */;
INSERT INTO `lamp` VALUES (1,_binary '','윤동휘의 무드등','2024-05-19 20:00:47.229677',NULL,_binary '','D4:8a:FC:a7:7B:76','FFFFFF','FFFFFF',1,1);
/*!40000 ALTER TABLE `lamp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `light`
--

DROP TABLE IF EXISTS `light`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `light` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_registered` bit(1) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `registered_dttm` datetime(6) DEFAULT NULL,
  `unregistered_dttm` datetime(6) DEFAULT NULL,
  `is_accessible` bit(1) NOT NULL,
  `mac_address` varchar(255) DEFAULT NULL,
  `cur_color` varchar(255) DEFAULT NULL,
  `wakeup_color` varchar(255) DEFAULT NULL,
  `hub_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK28ava0c0hguwk7aibj01gsjr8` (`hub_id`),
  KEY `FKafxejm3bj0ih9mu4aklnb9t9r` (`user_id`),
  CONSTRAINT `FK28ava0c0hguwk7aibj01gsjr8` FOREIGN KEY (`hub_id`) REFERENCES `hub` (`id`),
  CONSTRAINT `FKafxejm3bj0ih9mu4aklnb9t9r` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `light`
--

LOCK TABLES `light` WRITE;
/*!40000 ALTER TABLE `light` DISABLE KEYS */;
INSERT INTO `light` VALUES (1,_binary '','윤동휘의 조명','2024-05-19 20:00:59.032097',NULL,_binary '','A8:42:E3:B8:13:8A','FFFFFF','FFFFFF',1,1);
/*!40000 ALTER TABLE `light` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sleep_data`
--

DROP TABLE IF EXISTS `sleep_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sleep_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_dttm` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sleep_level` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr2kvrg999jw9vqvb8jvxpech1` (`user_id`),
  CONSTRAINT `FKr2kvrg999jw9vqvb8jvxpech1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sleep_data`
--

LOCK TABLES `sleep_data` WRITE;
/*!40000 ALTER TABLE `sleep_data` DISABLE KEYS */;
INSERT INTO `sleep_data` VALUES (1,'2024-05-19 20:06:28',6,1),(2,'2024-05-19 20:07:19',4,1),(3,'2024-05-19 20:07:44',6,1),(4,'2024-05-19 20:11:58',6,1),(5,'2024-05-19 20:12:27',6,1),(6,'2024-05-19 20:12:46',-1,1),(7,'2024-05-19 20:14:07',4,1),(8,'2024-05-19 20:19:57',6,1),(9,'2024-05-19 20:19:58',6,1),(10,'2024-05-19 20:19:58',6,1),(11,'2024-05-19 20:20:00',6,1),(12,'2024-05-19 20:49:07',4,1),(13,'2024-05-19 20:49:52',6,1),(14,'2024-05-19 20:49:53',6,1),(15,'2024-05-19 20:49:53',6,1),(16,'2024-05-19 20:50:54',6,1),(17,'2024-05-19 20:50:54',6,1),(18,'2024-05-19 20:52:15',6,1),(19,'2024-05-19 20:53:10',-1,1),(20,'2024-05-19 20:55:18',6,1),(21,'2024-05-19 20:55:45',-1,1),(22,'2024-05-19 21:01:13',4,1),(23,'2024-05-19 21:02:04',6,1),(24,'2024-05-19 21:02:04',6,1),(25,'2024-05-19 21:02:05',6,1),(26,'2024-05-19 21:02:05',6,1),(27,'2024-05-19 21:02:05',6,1),(28,'2024-05-19 21:05:10',-1,1),(29,'2024-05-19 21:14:43',4,1),(30,'2024-05-19 21:15:29',6,1),(31,'2024-05-19 21:15:29',6,1),(32,'2024-05-19 21:16:27',-1,1),(33,'2024-05-19 21:32:16',4,1),(34,'2024-05-19 21:32:59',6,1),(35,'2024-05-19 21:33:00',6,1),(36,'2024-05-19 21:33:54',-1,1);
/*!40000 ALTER TABLE `sleep_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sliding_window`
--

DROP TABLE IF EXISTS `sliding_window`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sliding_window` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_registered` bit(1) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `registered_dttm` datetime(6) DEFAULT NULL,
  `unregistered_dttm` datetime(6) DEFAULT NULL,
  `is_accessible` bit(1) NOT NULL,
  `mac_address` varchar(255) DEFAULT NULL,
  `open_degree` int DEFAULT NULL,
  `hub_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9nhxqxsh57yx43eprqkpmgduf` (`hub_id`),
  KEY `FKomm6vsdlqqoxydf0t03v4hmh7` (`user_id`),
  CONSTRAINT `FK9nhxqxsh57yx43eprqkpmgduf` FOREIGN KEY (`hub_id`) REFERENCES `hub` (`id`),
  CONSTRAINT `FKomm6vsdlqqoxydf0t03v4hmh7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sliding_window`
--

LOCK TABLES `sliding_window` WRITE;
/*!40000 ALTER TABLE `sliding_window` DISABLE KEYS */;
INSERT INTO `sliding_window` VALUES (1,_binary '','윤동휘의 창문','2024-05-19 20:01:12.324410',NULL,_binary '','30:AE:A4:EB:24:2A',100,1,1);
/*!40000 ALTER TABLE `sliding_window` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `registered_dttm` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `unregistered_dttm` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_super_user` bit(1) DEFAULT NULL,
  `key_code` varchar(100) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `wakeup_time` time(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r6schwinmggn491kgiv4um4ed` (`key_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary '','2024-05-19 19:50:43',NULL,'xorms1119@naver.com',_binary '\0','3459742565','윤동휘',NULL),(2,_binary '','2024-05-19 20:04:29',NULL,'ysy1644@naver.com',_binary '\0','','용상윤',NULL),(3,_binary '','2024-05-19 21:30:26',NULL,'ysy1644@naver.com',_binary '\0','3474827898','용상윤',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_house`
--

DROP TABLE IF EXISTS `user_house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_house` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `registered_dttm` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `unregistered_dttm` datetime(6) DEFAULT NULL,
  `is_home` bit(1) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `house_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK60fh3naj3uah5ja1os8ebc3gv` (`house_id`),
  KEY `FKpcc3drvusk68rpn2brnlx4wbt` (`user_id`),
  CONSTRAINT `FK60fh3naj3uah5ja1os8ebc3gv` FOREIGN KEY (`house_id`) REFERENCES `house` (`id`),
  CONSTRAINT `FKpcc3drvusk68rpn2brnlx4wbt` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_house`
--

LOCK TABLES `user_house` WRITE;
/*!40000 ALTER TABLE `user_house` DISABLE KEYS */;
INSERT INTO `user_house` VALUES (1,_binary '','2024-05-19 19:50:56',NULL,_binary '','우리집',1,1),(2,_binary '','2024-05-19 20:47:26',NULL,_binary '','나의 집',1,2),(3,_binary '','2024-05-19 21:30:50',NULL,_binary '','나의 집',1,3);
/*!40000 ALTER TABLE `user_house` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'mimo'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-19 21:53:25
