-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.32-community


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
  `address_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `director_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FKB0E2E728353B48C7` (`company_id`),
  KEY `FKB0E2E72846715B0D` (`location_id`),
  KEY `FKB0E2E72861F237AD` (`director_id`),
  KEY `FKB0E2E7283AA10167` (`address_id`),
  CONSTRAINT `FKB0E2E7283AA10167` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`),
  CONSTRAINT `FKB0E2E728353B48C7` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `FKB0E2E72846715B0D` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `FKB0E2E72861F237AD` FOREIGN KEY (`director_id`) REFERENCES `directors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `factories`
--

/*!40000 ALTER TABLE `factories` DISABLE KEYS */;
INSERT INTO `factories` (`id`,`contact_email`,`employees`,`information`,`name`,`address_id`,`location_id`,`company_id`,`director_id`) VALUES 
 (1,'factory1@company1.com',10,'Factoría de ejemplo 1.1','Factoría 1.1',4,4,3,11),
 (2,'factory2@company1.com',30,'Factoría de ejemplo 1.2','Factoría 1.2',5,5,3,12),
 (3,'factory3@company1.com',95,'Factoría de ejemplo 1.3','Factoría 1.3',6,6,3,13),
 (4,'factory4@company1.com',63,'Factoría de ejemplo 1.4','Factoría 1.4',7,7,3,14),
 (5,'factory1@company2.com',57,'Factoría de ejemplo 2.1','Factoría 2.1',20,20,4,27),
 (6,'factory2@company2.com',44,'Factoría de ejemplo 2.2','Factoría 2.2',21,21,4,28),
 (7,'factory3@company2.com',32,'Factoría de ejemplo 2.3','Factoría 2.3',10,10,4,17),
 (8,'factory4@company2.com',115,'Factoría de ejemplo 2.4','Factoría 2.4',11,11,4,18),
 (9,'factory1@company3.com',7,'Factoría de ejemplo 3.1','Factoría 3.1',12,12,5,19),
 (10,'factory2@company3.com',21,'Factoría de ejemplo 3.2','Factoría 3.2',13,13,5,20),
 (11,'factory3@company3.com',43,'Factoría de ejemplo 3,3','Factoría 3.3',14,14,5,21);
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
  `finished` tinyint(1) DEFAULT '0',
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
INSERT INTO `projects` (`id`,`actividad`,`audited`,`code`,`comentarios`,`delay`,`eficiencia`,`fiabilidad`,`fichaje_codigo`,`fichaje_total`,`finished`,`lineas_de_codigo`,`mantenibilidad`,`name`,`plan`,`portabilidad`,`puntos_funcion`,`repaired_incidences`,`size`,`total_incidences`,`usabilidad`,`mainFactory_id`,`market_id`) VALUES 
 (1,95.4,0,'VTRB',1022,1,89.9,78.1,1600,1500,0,100457,85.6,'Vitrubio','0932',95.8,50.6,400,23,510,87.9,3,1),
 (2,78.8,1,'ALT',150512,0,78.5,67.9,1400,4050,1,1152230,74.5,'Althea','0132',96.7,78.9,300,31,450,64.5,5,3),
 (3,89.5,0,'ARS',1022445,0,85.32,95.78,20000,30000,0,14007875,98.33,'Ares','0142',89.4,80.5,100,45,780,89.78,7,2),
 (4,99.9,1,'ASCP',6654,0,99.7,97.8,5000,16400,1,4592744,96.12,'Asclepios','0421',83.45,30.5,0,60,300,54.9,9,3),
 (5,67.8,1,'EOL',55466,0,81.23,81.45,1000,8790,0,1214520,85.6,'Eolo','0094',74.1,10.2,150,40,200,36.87,7,4),
 (6,45.6,0,'VDLV',123354,1,67.9,72.36,10000,7000,0,422133,68.4,'Vandelvira','0541',86.2,15.6,140,80,150,65.98,5,1);
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
  `audit` varchar(255) DEFAULT NULL,
  `test_cobertura` float DEFAULT '0',
  `comentarios` int(11) DEFAULT '0',
  `test_efectividad` float DEFAULT '0',
  `eficiencia` float DEFAULT '0',
  `errores_detectados` int(11) DEFAULT '0',
  `errores_resueltos` int(11) DEFAULT '0',
  `esfuerzo_mto_estimado` float DEFAULT '0',
  `esfuerzo_mto_real` float DEFAULT '0',
  `fiabilidad` float DEFAULT '0',
  `fichaje_codigo` float DEFAULT '0',
  `fichaje_total` float DEFAULT '0',
  `func_implementadas` int(11) DEFAULT '0',
  `func_totales` int(11) DEFAULT '0',
  `iReportadosEnPlazo` int(11) DEFAULT '0',
  `iReportadosOk` int(11) DEFAULT '0',
  `iReportados` int(11) DEFAULT '0',
  `lcd_modificadas` int(11) DEFAULT '0',
  `lineas_de_codigo` int(11) DEFAULT '0',
  `mantenibilidad` float DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `ncCerradas` int(11) DEFAULT '0',
  `ncEnProceso` int(11) DEFAULT '0',
  `ncGestionadas` int(11) DEFAULT '0',
  `ncNoGestionadas` int(11) DEFAULT '0',
  `commits` int(11) DEFAULT '0',
  `plan` varchar(255) DEFAULT NULL,
  `portabilidad` float DEFAULT '0',
  `puntos_funcion` float DEFAULT '0',
  `testcases_exec` int(11) DEFAULT '0',
  `usabilidad` float DEFAULT '0',
  `project_id` int(11) NOT NULL,
  `factory_id` int(11) NOT NULL,
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
INSERT INTO `subprojects` (`id`,`actividad`,`audit`,`test_cobertura`,`comentarios`,`test_efectividad`,`eficiencia`,`errores_detectados`,`errores_resueltos`,`esfuerzo_mto_estimado`,`esfuerzo_mto_real`,`fiabilidad`,`fichaje_codigo`,`fichaje_total`,`func_implementadas`,`func_totales`,`iReportadosEnPlazo`,`iReportadosOk`,`iReportados`,`lcd_modificadas`,`lineas_de_codigo`,`mantenibilidad`,`name`,`ncCerradas`,`ncEnProceso`,`ncGestionadas`,`ncNoGestionadas`,`commits`,`plan`,`portabilidad`,`puntos_funcion`,`testcases_exec`,`usabilidad`,`project_id`,`factory_id`) VALUES 
 (1,78.8,'Planificada',89.9,1056,78.65,79.8,14,5,130,80,98,600,500,12,23,56,1,80,1000,30000,95.4,'Sub 1 Vitrubio',12,6,5,6,89,'Gestionado',89.4,900,567,87.34,1,3),
 (2,97.5,'Planificada',78.5,4565,87.5,73.9,34,3,150,210,75.5,400,500,8,14,45,6,78,250,50000,85.3,'Sub 2 Vitrubio',14,11,10,7,133,'Gestionado',23.4,1908,754,89.56,1,4),
 (3,67.56,'Aplazada',45.76,5050,56.7,98.6,23,5,137.4,140,14.6,500,500,13,25,12,8,12,300,20000,96.4,'Sub 3 Vitrubio',5,7,3,12,65,'En proceso ',56.3,789,756,84.56,1,8),
 (4,89.78,'Planificada',34.67,1064,76.7,83.3,64,34,320,200,68.7,800,1000,17,20,15,12,34,8000,100000,75.3,'Sub 1 Althea',4,7,8,6,786,'Gestionado',45.5,3980,787,34.78,2,5),
 (5,78.89,'Cancelada',90.04,506,98.8,93.3,10,7,220,200,84.6,1000,1000,5,8,12,45,50,4555,250000,65.1,'Sub 2 Althea',4,12,4,5,577,'Gestionado',87.5,7882.5,7685,84.8,2,8),
 (6,57.687,'Aplazada',76.4,4560,86.7,81.5,21,18,400,350,84.6,700,1000,9,10,15,19,23,6555,300000,89.45,'Sub 3 Althea',12,5,7,7,325,'En proceso ',98.8,8023,6788,74.8,2,6),
 (7,78.9,'Planificada',74.6,1204,78.9,98.3,18,4,320,290,81.3,100,1000,9,12,15,12,16,700,350000,74.3,'Sub 4 Althea',4,5,6,7,745,'No gestionado',78.3,7856,6756,87.5,2,7),
 (8,98.3,'Planificada',89.6,2004,83.76,79.36,15,5,330.5,100,89.4,4000,5000,12,14,90,32,100,500,250000,92.2,'Sub 1 Ares',2,4,6,8,23,'No gestionado',98.1,7509,8656,83.8,3,8),
 (9,75.6,'Aplazada',68.5,3522,93.4,82.5,45,34,132.4,200,74.2,5500,5000,13,15,56,13,79,6700,400000,84.3,'Sub 2 Ares',6,8,6,4,656,'No gestionado',91.3,9786,8768,97.34,3,7),
 (10,67.65,'Realizada',78.6,355,98.7,62.3,30,0,42.3,20,76.3,7000,5000,15,18,65,7,122,345,300000,72.23,'Sub 3 Ares',8,9,3,4,75,'No gestionado',92.7,8366,345,89.3,3,9),
 (11,98.4,'Realizada',54.2,6533,99.9,98.3,23,2,50.4,10,78.5,450,5000,23,34,54,8,12,5120,120000,74.6,'Sub 4 Ares',5,6,6,6,57,'En proceso ',45.5,3098,567,58.6,3,10),
 (12,45.65,'Realizada',56.77,3522,87.7,61.7,14,1,1200.5,200,82.23,5000,5000,7,8,45,9,56,3000,220000,89.14,'Sub 5 Ares',6,5,7,12,574,'No gestionado',65.8,6088,656,78.97,3,9),
 (13,65.6,'Realizada',67.45,6462,67.8,68.3,11,9,1400,150,67.6,4222,7000,30,34,12,9,67,5400,2000000,93.6,'Sub 1 Asclepios',3,5,7,17,56,'En proceso ',89.5,60987,6567,98.23,4,10),
 (14,68.6,'Realizada',86.76,3421,89.76,84.45,32,10,440,300,67.1,5000,7000,29,43,45,15,68,7000,2500000,98.3,'Sub 2 Asclepios',12,13,16,17,9767,'No gestionado',98.5,70998,7658,99.9,4,11),
 (15,89.8,'Realizada',96.45,457,98.76,55.5,12,6,300,320,37.3,1000,4000,10,13,67,16,98,700,300000,68.5,'Sub 1 Eolo',12,14,15,18,677,'En proceso ',91.6,6044,987,65.5,5,9),
 (16,87.9,'Realizada',45.7,3312,67.78,56.9,8,4,240,200,76.1,2400,4000,6,8,45,13,100,560,750000,89.3,'Sub 2 Eolo',12,15,16,17,865,'No gestionado',95.65,15044,235,98.5,5,7),
 (17,78.8,'Realizada',87.67,5633,87.7,46.7,10,7,220,100,54.6,400,1000,5,9,34,45,120,500,50000,87.5,'Sub 1 Vandelvira ',15,18,13,12,76,'No gestionado',96.3,1543,544,89.7,6,5),
 (18,73.35,'Realizada',82.67,4872,87.87,98.8,17,4,430,250,65.3,600,1000,4,7,23,67,34,400,30000,94.4,'Sub 2 Vandelvira',15,17,18,19,345,'No gestionado',97.35,967,765,69.8,6,6),
 (19,72.6,'Aplazada',80.432,3124,89.7,78.8,18,6,231,150,71.3,700,1000,4,8,12,12,76,350,70000,97.6,'Sub 3 Vandelvira',12,0,34,56,65,'En proceso ',67.5,2343,765,98.3,6,7),
 (20,98.5,'Realizada',54.6,5834,76.78,98.8,21,6,123,110,95.1,900,1000,10,12,14,34,52,890,160000,82.3,'Sub 4 Vandelvira',15,23,26,28,869,'No gestionado',9.8,5644,867,65.5,6,3),
 (21,35.5,'Aplazada',87.7,345,73.5,98.5,43,34,423,340,75.01,450,1000,7,13,13,12,54,560,25000,98.2,'Sub 5 Vandelvira',12,17,17,13,98,'No gestionado',98.5,667,656,68.5,6,4),
 (22,76.6,'Realizada',93.2,3425,71.23,88.8,5,2,234,210,42,640,1000,5,15,16,12,43,750,85000,78.5,'Sub 6 Vandelvira',14,15,0,12,767,'No gestionado',51.2,1212,865,55.5,6,3);
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
