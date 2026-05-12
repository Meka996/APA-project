-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: fittrack_db
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `trainer_profiles`
--

DROP TABLE IF EXISTS `trainer_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainer_profiles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bio` text,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `specialization` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKb3ivbiplqbnt1jh8h983xq8la` (`user_id`),
  CONSTRAINT `FKcnouyhrcuky4thfva7wrmgo5v` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer_profiles`
--

LOCK TABLES `trainer_profiles` WRITE;
/*!40000 ALTER TABLE `trainer_profiles` DISABLE KEYS */;
INSERT INTO `trainer_profiles` VALUES (1,NULL,'trainer_test_1778280822@example.com','Test','Trainer',NULL,'trainer_test_1778280822@example.com',6),(2,NULL,'ba@gmail.com','b','a',NULL,'ba@gmail.com',7);
/*!40000 ALTER TABLE `trainer_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainer_workouts`
--

DROP TABLE IF EXISTS `trainer_workouts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainer_workouts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `assigned_date` date DEFAULT NULL,
  `calories_target` int DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `instructions` text,
  `title` varchar(255) DEFAULT NULL,
  `trainee_id` bigint DEFAULT NULL,
  `trainer_id` bigint DEFAULT NULL,
  `actual_calories_burned` int DEFAULT NULL,
  `actual_duration` int DEFAULT NULL,
  `completed_at` date DEFAULT NULL,
  `trainee_notes` text,
  PRIMARY KEY (`id`),
  KEY `FKcr4vp72yliydgq3lw1v7sgmxt` (`trainee_id`),
  KEY `FKkuteygdq0xhu8unk1ssi1jt32` (`trainer_id`),
  CONSTRAINT `FKcr4vp72yliydgq3lw1v7sgmxt` FOREIGN KEY (`trainee_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKkuteygdq0xhu8unk1ssi1jt32` FOREIGN KEY (`trainer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer_workouts`
--

LOCK TABLES `trainer_workouts` WRITE;
/*!40000 ALTER TABLE `trainer_workouts` DISABLE KEYS */;
INSERT INTO `trainer_workouts` VALUES (1,'2026-05-06',1000,120,'push up','push',2,6,1000,60,'2026-05-12','good'),(2,'2026-04-29',500,45,'pull ups','pull day',2,6,500,60,'2026-05-12','nice');
/*!40000 ALTER TABLE `trainer_workouts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `trainer` bit(1) NOT NULL,
  `fitness_goal` varchar(255) DEFAULT NULL,
  `height` double DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `selected_trainer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKe3ntd63n0sdua6fp97k2l2b62` (`selected_trainer_id`),
  CONSTRAINT `FKe3ntd63n0sdua6fp97k2l2b62` FOREIGN KEY (`selected_trainer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'yrsl@maildrop.cc','bass','ass','12345',_binary '\0',NULL,NULL,NULL,NULL),(2,'yete@maildrop.cc','ye','te','12345',_binary '\0',NULL,NULL,NULL,6),(4,'trainee_test_1778280382@example.com','Test','Trainee','pass',_binary '\0',NULL,NULL,NULL,NULL),(5,'trainee_test_1778280391@example.com','Test','Trainee','pass',_binary '\0',NULL,NULL,NULL,NULL),(6,'trainer_test_1778280822@example.com','Test','Trainer','pass',_binary '',NULL,NULL,NULL,NULL),(7,'ba@gmail.com','b','a','12',_binary '',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_sessions`
--

DROP TABLE IF EXISTS `workout_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workout_sessions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `calories_burned` int DEFAULT NULL,
  `completed_at` date DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `workout_title` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfwqciawyjntpphp080wpa37ge` (`user_id`),
  CONSTRAINT `FKfwqciawyjntpphp080wpa37ge` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_sessions`
--

LOCK TABLES `workout_sessions` WRITE;
/*!40000 ALTER TABLE `workout_sessions` DISABLE KEYS */;
INSERT INTO `workout_sessions` VALUES (4,1000,'2026-04-12',120,'hi','lazy',2);
/*!40000 ALTER TABLE `workout_sessions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-09 23:00:40
