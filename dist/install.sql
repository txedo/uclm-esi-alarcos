-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.56-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema desglosadb
--

CREATE DATABASE IF NOT EXISTS desglosadb;
USE desglosadb;

--
-- Definition of table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
CREATE TABLE `addresses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `addresses`
--

/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` (`id`,`address`,`city`,`country`,`postal_code`,`province`) VALUES 
 (4,'R. Anhangabaú, 62','Sao Paulo','Brazil','01007-040',''),
 (5,'R. da Assunção, 12','Lisbon','Portugal','1100',''),
 (6,'Av Juan XXIII, 2502-2600','Lomas de Zamora','Argentina','','Lomas de Zamora Partido'),
 (7,'Viaducto Presidente Miguel Alemán, 196','Mexico City','Mexico','',''),
 (9,'Janpath Ln, undefined','New Delhi','India','','Delhi (district)'),
 (10,'Paseo de la Universidad, 4','Ciudad Real','Spain','13003','Ciudad Real'),
 (11,'Sungang East Rd, 2008','Shenzhen','China','',''),
 (12,'Karl-Marx Allee, 32','Berlin','Germany','10178','Berlin'),
 (13,'Market St, 1601-1645','San Francisco','United States','94103','San Francisco'),
 (14,'Via dei Gracchi, 260','Rome','Italy','00192','Rome'),
 (16,'E 14th St, 120','New York','United States','10003','New York'),
 (17,'Obeysekarapura Rd, undefined','Sri Jayawardenepura Kotte','Sri Lanka','undefined','Colombo'),
 (19,'E 14th St, 120','New York','United States','10003','New York'),
 (20,'E 14th St, 120','New York','United States','10003','New York'),
 (21,'Janpath Ln, undefined','New Delhi','India','','Delhi (district)');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;


--
-- Definition of table `companies`
--

DROP TABLE IF EXISTS `companies`;
CREATE TABLE `companies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `information` varchar(255) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `director_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FKABC9E1DB61F237AD` (`director_id`),
  CONSTRAINT `FKABC9E1DB61F237AD` FOREIGN KEY (`director_id`) REFERENCES `directors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `companies`
--

/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` (`id`,`information`,`name`,`director_id`) VALUES 
 (3,'Compañía de ejemplo número 1.','Compañía 1',6),
 (4,'Compañía de ejemplo número 2.','Compañía 2',7),
 (5,'Compañía de ejemplo número 3.','Compañía 3',8);
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;


--
-- Definition of table `directors`
--

DROP TABLE IF EXISTS `directors`;
CREATE TABLE `directors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_path` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `directors`
--

/*!40000 ALTER TABLE `directors` DISABLE KEYS */;
INSERT INTO `directors` (`id`,`image_path`,`last_name`,`name`) VALUES 
 (6,'images/anonymous.gif','Verdugo Cardona','Rivi'),
 (7,'images/anonymous.gif','Cordero Nevarez','Restituto'),
 (8,'images/anonymous.gif','Osborne','Peter'),
 (9,'images/anonymous.gif','Forster','Danielle'),
 (11,'images/anonymous.gif','Schott','Jade'),
 (12,'images/anonymous.gif','Abbott','Lucas'),
 (13,'images/anonymous.gif','Davidson','Lucas'),
 (14,'images/anonymous.gif','Robinson','Summer'),
 (16,'images/anonymous.gif','Morton','Adam'),
 (17,'images/anonymous.gif','López López','Jose Domingo'),
 (18,'images/anonymous.gif','Hartley','Reece'),
 (19,'images/anonymous.gif','Foster','Shannon'),
 (20,'images/anonymous.gif','Russell','Courtney'),
 (21,'images/anonymous.gif','Hughes','Ewan'),
 (23,'images/anonymous.gif','Barlow','Madison'),
 (24,'images/anonymous.gif','Henry','Ellis'),
 (26,'images/anonymous.gif','Barlow','Madison'),
 (27,'images/anonymous.gif','Barlow','Madison'),
 (28,'images/anonymous.gif','Morton','Adam');
/*!40000 ALTER TABLE `directors` ENABLE KEYS */;


--
-- Definition of table `factories`
--

DROP TABLE IF EXISTS `factories`;
CREATE TABLE `factories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_email` varchar(255) DEFAULT NULL,
  `employees` int(11) DEFAULT NULL,
  `information` varchar(255) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `location_id` int(11) DEFAULT NULL,
  `director_id` int(11) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FKB0E2E728353B48C7` (`company_id`),
  KEY `FKB0E2E72846715B0D` (`location_id`),
  KEY `FKB0E2E7283AA10167` (`address_id`),
  KEY `FKB0E2E72861F237AD` (`director_id`),
  CONSTRAINT `FKB0E2E72861F237AD` FOREIGN KEY (`director_id`) REFERENCES `directors` (`id`),
  CONSTRAINT `FKB0E2E728353B48C7` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `FKB0E2E7283AA10167` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`),
  CONSTRAINT `FKB0E2E72846715B0D` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `factories`
--

/*!40000 ALTER TABLE `factories` DISABLE KEYS */;
INSERT INTO `factories` (`id`,`contact_email`,`employees`,`information`,`name`,`location_id`,`director_id`,`address_id`,`company_id`) VALUES 
 (1,'factory1@company1.com',10,'Factoría de ejemplo 1.1','Factoría 1.1',4,11,4,3),
 (2,'factory2@company1.com',30,'Factoría de ejemplo 1.2','Factoría 1.2',5,12,5,3),
 (3,'factory3@company1.com',95,'Factoría de ejemplo 1.3','Factoría 1.3',6,13,6,3),
 (4,'factory4@company1.com',63,'Factoría de ejemplo 1.4','Factoría 1.4',7,14,7,3),
 (5,'factory1@company2.com',57,'Factoría de ejemplo 2.1','Factoría 2.1',20,27,20,4),
 (6,'factory2@company2.com',44,'Factoría de ejemplo 2.2','Factoría 2.2',21,28,21,4),
 (7,'factory3@company2.com',32,'Factoría de ejemplo 2.3','Factoría 2.3',10,17,10,4),
 (8,'factory4@company2.com',115,'Factoría de ejemplo 2.4','Factoría 2.4',11,18,11,4),
 (9,'factory1@company3.com',7,'Factoría de ejemplo 3.1','Factoría 3.1',12,19,12,5),
 (10,'factory2@company3.com',21,'Factoría de ejemplo 3.2','Factoría 3.2',13,20,13,5),
 (11,'factory3@company3.com',43,'Factoría de ejemplo 3,3','Factoría 3.3',14,21,14,5);
/*!40000 ALTER TABLE `factories` ENABLE KEYS */;


--
-- Definition of table `groups`
--

DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groups`
--

/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` (`id`,`description`,`name`) VALUES 
 (1,'Administrator group','Administrators'),
 (2,'Project managers staff group','Project Managers'),
 (3,'Traditional users group','Users');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;


--
-- Definition of table `groups_roles`
--

DROP TABLE IF EXISTS `groups_roles`;
CREATE TABLE `groups_roles` (
  `group_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`group_id`,`role_id`),
  KEY `FKF3F71C921BC5C8ED` (`role_id`),
  KEY `FKF3F71C928A6718C7` (`group_id`),
  CONSTRAINT `FKF3F71C928A6718C7` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FKF3F71C921BC5C8ED` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groups_roles`
--

/*!40000 ALTER TABLE `groups_roles` DISABLE KEYS */;
INSERT INTO `groups_roles` (`group_id`,`role_id`) VALUES 
 (1,1),
 (2,2),
 (3,3);
/*!40000 ALTER TABLE `groups_roles` ENABLE KEYS */;


--
-- Definition of table `locations`
--

DROP TABLE IF EXISTS `locations`;
CREATE TABLE `locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `locations`
--

/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` (`id`,`latitude`,`longitude`) VALUES 
 (4,'-23.5436916','-46.63582629999996'),
 (5,'38.71204305365397','-9.136768629527978'),
 (6,'-34.767556157743535','-58.43207960364907'),
 (7,'19.403143459944427','-99.1542780899772'),
 (9,'28.623412599207477','77.21783036949546'),
 (10,'38.9903658','-3.9208851999999297'),
 (11,'22.55569206982344','114.11369676353843'),
 (12,'52.520194528975345','13.421396201171888'),
 (13,'37.7749295','-122.41941550000001'),
 (14,'41.90918248875203','12.466472337268897'),
 (16,'40.733737015768966','-73.98872022864907'),
 (17,'6.9196191407302665','79.89128465416343'),
 (19,'40.733737015768966','-73.98872022864907'),
 (20,'40.733737015768966','-73.98872022864907'),
 (21,'28.623412599207477','77.21783036949546');
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;


--
-- Definition of table `markets`
--

DROP TABLE IF EXISTS `markets`;
CREATE TABLE `markets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `markets`
--

/*!40000 ALTER TABLE `markets` DISABLE KEYS */;
INSERT INTO `markets` (`id`,`color`,`name`) VALUES 
 (1,'00649a','Arquitectura'),
 (2,'298400','Defensa'),
 (3,'ff8c00','Sanitario'),
 (4,'ffda47','Aéreo'),
 (5,'ff47af','Transporte'),
 (6,'8a47ff','Gestión documental'),
 (7,'7d7676','No definido');
/*!40000 ALTER TABLE `markets` ENABLE KEYS */;


--
-- Definition of table `projects`
--

DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actividad` float DEFAULT '0',
  `audited` tinyint(1) DEFAULT '0',
  `code` varchar(255) DEFAULT NULL,
  `comentarios` int(11) DEFAULT '0',
  `delay` tinyint(1) DEFAULT '0',
  `eficiencia` float DEFAULT '0',
  `fiabilidad` float DEFAULT '0',
  `fichaje_codigo` float DEFAULT '0',
  `fichaje_total` float DEFAULT '0',
  `lineas_de_codigo` int(11) DEFAULT '0',
  `mantenibilidad` float DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `plan` varchar(255) DEFAULT NULL,
  `portabilidad` float DEFAULT '0',
  `puntos_funcion` float DEFAULT '0',
  `repaired_incidences` int(11) DEFAULT '0',
  `size` int(11) DEFAULT '0',
  `total_incidences` int(11) DEFAULT '0',
  `usabilidad` float DEFAULT '0',
  `mainFactory_id` int(11) NOT NULL,
  `market_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC479187AEC81FA6D` (`market_id`),
  KEY `FKC479187A6BCD420` (`mainFactory_id`),
  CONSTRAINT `FKC479187A6BCD420` FOREIGN KEY (`mainFactory_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `FKC479187AEC81FA6D` FOREIGN KEY (`market_id`) REFERENCES `markets` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `projects`
--

/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` (`id`,`actividad`,`audited`,`code`,`comentarios`,`delay`,`eficiencia`,`fiabilidad`,`fichaje_codigo`,`fichaje_total`,`lineas_de_codigo`,`mantenibilidad`,`name`,`plan`,`portabilidad`,`puntos_funcion`,`repaired_incidences`,`size`,`total_incidences`,`usabilidad`,`mainFactory_id`,`market_id`) VALUES 
 (1,95.4,0,'VTRB',1022,1,89.9,78.1,1600,1500,100457,85.6,'Vitrubio','0932',95.8,50.6,400,23,510,87.9,3,1),
 (2,78.8,1,'ALT',150512,0,78.5,67.9,1400,4050,1152230,74.5,'Althea','0132',96.7,78.9,300,31,450,64.5,5,3),
 (3,89.5,0,'ARS',1022445,0,85.32,95.78,20000,30000,14007875,98.33,'Ares','0142',89.4,80.5,100,45,780,89.78,7,2),
 (4,99.9,1,'ASCP',6654,0,99.7,97.8,5000,16400,4592744,96.12,'Asclepios','0421',83.45,30.5,0,60,300,54.9,9,3),
 (5,67.8,1,'EOL',55466,0,81.23,81.45,1000,8790,1214520,85.6,'Eolo','0094',74.1,10.2,150,40,200,36.87,7,4),
 (6,45.6,0,'VDLV',123354,1,67.9,72.36,10000,7000,422133,68.4,'Vandelvira','0541',86.2,15.6,140,80,150,65.98,5,1);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;


--
-- Definition of table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles`
--

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`,`description`,`name`) VALUES 
 (1,'Administrator','ROLE_ADMIN'),
 (2,'Project manager','ROLE_MANAGER'),
 (3,'Traditional user','ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;


--
-- Definition of table `subprojects`
--

DROP TABLE IF EXISTS `subprojects`;
CREATE TABLE `subprojects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actividad` float DEFAULT '0',
  `comentarios` int(11) DEFAULT '0',
  `eficiencia` float DEFAULT '0',
  `fiabilidad` float DEFAULT '0',
  `fichaje_codigo` float DEFAULT '0',
  `fichaje_total` float DEFAULT '0',
  `lineas_de_codigo` int(11) DEFAULT '0',
  `mantenibilidad` float DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `portabilidad` float DEFAULT '0',
  `puntos_funcion` float DEFAULT '0',
  `usabilidad` float DEFAULT '0',
  `factory_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKEF896BAA73F7347` (`project_id`),
  KEY `FKEF896BA5986E1A7` (`factory_id`),
  CONSTRAINT `FKEF896BA5986E1A7` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `FKEF896BAA73F7347` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `subprojects`
--

/*!40000 ALTER TABLE `subprojects` DISABLE KEYS */;
INSERT INTO `subprojects` (`id`,`actividad`,`comentarios`,`eficiencia`,`fiabilidad`,`fichaje_codigo`,`fichaje_total`,`lineas_de_codigo`,`mantenibilidad`,`name`,`portabilidad`,`puntos_funcion`,`usabilidad`,`factory_id`,`project_id`) VALUES 
 (1,78.8,1056,79.8,98,600,500,30000,95.4,'Sub 1 Vitrubio',89.4,35.5,87.34,3,1),
 (2,97.5,4565,73.9,75.5,400,500,50000,85.3,'Sub 2 Vitrubio',23.4,68.2,89.56,4,1),
 (3,67.56,5050,98.6,14.6,500,500,20000,96.4,'Sub 3 Vitrubio',56.3,45.5,84.56,8,1),
 (4,89.78,1064,83.3,68.7,800,1000,100000,75.3,'Sub 1 Althea',45.5,67.8,34.78,5,2),
 (5,78.89,506,93.3,84.6,1000,1000,250000,65.1,'Sub 2 Althea',87.5,82.5,84.8,8,2),
 (6,57.687,4560,81.5,84.6,700,1000,300000,89.45,'Sub 3 Althea',98.8,34.5,74.8,6,2),
 (7,78.9,1204,98.3,81.3,100,1000,350000,74.3,'Sub 4 Althea',78.3,76.5,87.5,7,2),
 (8,98.3,2004,79.36,89.4,4000,5000,250000,92.2,'Sub 1 Ares',98.1,64.2,83.8,8,3),
 (9,75.6,3522,82.5,74.2,5500,5000,400000,84.3,'Sub 2 Ares',91.3,64.5,97.34,7,3),
 (10,67.65,355,62.3,76.3,7000,5000,300000,72.23,'Sub 3 Ares',92.7,56.5,89.3,9,3),
 (11,98.4,6533,98.3,78.5,450,5000,120000,74.6,'Sub 4 Ares',45.5,30.8,58.6,10,3),
 (12,45.65,3522,61.7,82.23,5000,5000,220000,89.14,'Sub 5 Ares',65.8,56.2,78.97,9,3),
 (13,65.6,6462,68.3,67.6,4222,7000,2000000,93.6,'Sub 1 Asclepios',89.5,78.54,98.23,10,4),
 (14,68.6,3421,84.45,67.1,5000,7000,2500000,98.3,'Sub 2 Asclepios',98.5,65.5,99.9,11,4),
 (15,89.8,457,55.5,37.3,1000,4000,300000,68.5,'Sub 1 Eolo',91.6,45.2,65.5,9,5),
 (16,87.9,3312,56.9,76.1,2400,4000,750000,89.3,'Sub 2 Eolo',95.65,61.2,98.5,7,5),
 (17,78.8,5633,46.7,54.6,400,1000,50000,87.5,'Sub 1 Vandelvira ',96.3,12.2,89.7,5,6),
 (18,73.35,4872,98.8,65.3,600,1000,30000,94.4,'Sub 2 Vandelvira',97.35,32.5,69.8,6,6),
 (19,72.6,3124,78.8,71.3,700,1000,70000,97.6,'Sub 3 Vandelvira',67.5,42.24,98.3,7,6),
 (20,98.5,5834,98.8,95.1,900,1000,160000,82.3,'Sub 4 Vandelvira',9.8,25.2,65.5,3,6),
 (21,35.5,345,98.5,75.01,450,1000,25000,98.2,'Sub 5 Vandelvira',98.5,56,68.5,4,6),
 (22,76.6,3425,88.8,42,640,1000,85000,78.5,'Sub 6 Vandelvira',51.2,45,55.5,3,6);
/*!40000 ALTER TABLE `subprojects` ENABLE KEYS */;


--
-- Definition of table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`,`account_expired`,`account_locked`,`credentials_expired`,`enabled`,`password`,`username`) VALUES 
 (1,0x00,0x00,0x00,0x01,'21232f297a57a5a743894a0e4a801fc3','admin'),
 (2,0x00,0x00,0x00,0x01,'1d0258c2440a8d19e716292b231e3190','manager'),
 (3,0x00,0x00,0x00,0x01,'ee11cbb19052e40b07aac0ca060c23ee','user'),
 (4,0x00,0x00,0x00,0x00,'075ae3d2fc31640504f814f60e5ef713','disabled'),
 (5,0x01,0x00,0x00,0x01,'c4bfb2a0bab0e91bc7dcfbe3bbec246e','expired'),
 (6,0x00,0x01,0x00,0x01,'4cef2f30ac7d33419d00c1d93a090095','locked'),
 (7,0x00,0x00,0x01,0x01,'9e9198d2bc527fbb46d417f02ecd6439','cexpired');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;


--
-- Definition of table `users_groups`
--

DROP TABLE IF EXISTS `users_groups`;
CREATE TABLE `users_groups` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`),
  KEY `FKD034EFEBC0F08CCD` (`user_id`),
  KEY `FKD034EFEB8A6718C7` (`group_id`),
  CONSTRAINT `FKD034EFEB8A6718C7` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FKD034EFEBC0F08CCD` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users_groups`
--

/*!40000 ALTER TABLE `users_groups` DISABLE KEYS */;
INSERT INTO `users_groups` (`user_id`,`group_id`) VALUES 
 (1,1),
 (2,2),
 (3,3);
/*!40000 ALTER TABLE `users_groups` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
