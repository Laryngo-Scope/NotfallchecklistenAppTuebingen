-- MySQL dump 10.16  Distrib 10.1.48-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: fdb1028.awardspace.net    Database: 4354180_checklisten
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `Harte_Tage`
--

DROP TABLE IF EXISTS `Harte_Tage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Harte_Tage` (
  `Reihenfolge` int NOT NULL,
  `Checkpoint` text NOT NULL,
  KEY `Reihenfolge` (`Reihenfolge`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='RSI und Intubation Checkliste';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Harte_Tage`
--

LOCK TABLES `Harte_Tage` WRITE;
/*!40000 ALTER TABLE `Harte_Tage` DISABLE KEYS */;
INSERT INTO `Harte_Tage` VALUES (1,'Cool Bleiben - Keine Panik!'),(2,'Gemeinsam statt einsam! - Gegenseitige Wertschätzung'),(3,'Du hast schon Schlimmeres überstanden!'),(4,'Du bist nicht daran Schuld!'),(5,'Es wird wieder besser werden!');
/*!40000 ALTER TABLE `Harte_Tage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RSI_und_Intubation`
--

DROP TABLE IF EXISTS `RSI_und_Intubation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RSI_und_Intubation` (
  `Reihenfolge` int NOT NULL,
  `Checkpoint` text NOT NULL,
  KEY `Reihenfolge` (`Reihenfolge`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='RSI und Intubation Checkliste';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RSI_und_Intubation`
--

LOCK TABLES `RSI_und_Intubation` WRITE;
/*!40000 ALTER TABLE `RSI_und_Intubation` DISABLE KEYS */;
INSERT INTO `RSI_und_Intubation` VALUES (1,'Monitoring'),(2,'Laryngoskop, ggf. Videolaryngoskop'),(3,'Tubus'),(4,'Führungsstab'),(5,'Alternative Atemwegssicherung'),(6,'Ketanest-S 100mg'),(7,'Rocuronium 100mg'),(8,'Midazolam 5mg'),(9,'Absaugung läuft'),(10,'Präoxygenierung'),(11,'Magill Zange');
/*!40000 ALTER TABLE `RSI_und_Intubation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database '4354180_checklisten'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-27 16:23:07
