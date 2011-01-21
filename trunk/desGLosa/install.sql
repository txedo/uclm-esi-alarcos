SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `desglosadb` DEFAULT CHARACTER SET latin1 ;
USE `desglosadb` ;

-- -----------------------------------------------------
-- Table `desglosadb`.`companies`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`companies` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `information` TINYTEXT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`addresses`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`addresses` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `street` TINYTEXT NOT NULL ,
  `city` VARCHAR(65) NOT NULL ,
  `state` VARCHAR(65) NOT NULL ,
  `country` VARCHAR(65) NOT NULL ,
  `zip` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`factories`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`factories` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `information` TINYTEXT NULL ,
  `director` VARCHAR(65) NULL ,
  `contact_email` VARCHAR(65) NULL ,
  `employees` INT NULL ,
  `companies_id` INT NOT NULL ,
  `addresses_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_factories_companies` (`companies_id` ASC) ,
  INDEX `fk_factories_addresses` (`addresses_id` ASC) ,
  CONSTRAINT `fk_factories_companies`
    FOREIGN KEY (`companies_id` )
    REFERENCES `desglosadb`.`companies` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_factories_addresses`
    FOREIGN KEY (`addresses_id` )
    REFERENCES `desglosadb`.`addresses` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`maps`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`maps` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `parentId` INT NULL ,
  `label` VARCHAR(45) NULL ,
  `filename` TEXT NULL ,
  `checksum` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`locations`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`locations` (
  `maps_id` INT NOT NULL ,
  `factories_id` INT NOT NULL ,
  `xcoord` FLOAT NOT NULL ,
  `ycoord` FLOAT NOT NULL ,
  INDEX `fk_locations_maps` (`maps_id` ASC) ,
  INDEX `fk_locations_factories` (`factories_id` ASC) ,
  UNIQUE INDEX `maps_factory_id_UNIQUE` (`maps_id` ASC, `factories_id` ASC) ,
  PRIMARY KEY (`maps_id`, `factories_id`) ,
  CONSTRAINT `fk_locations_maps`
    FOREIGN KEY (`maps_id` )
    REFERENCES `desglosadb`.`maps` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_locations_factories`
    FOREIGN KEY (`factories_id` )
    REFERENCES `desglosadb`.`factories` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


CREATE USER `desglosaadmin` IDENTIFIED BY 'nimdaasolgsed';

grant ALL on TABLE `desglosadb`.`companies` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`factories` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`addresses` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`locations` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`maps` to desglosaadmin;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`companies`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`companies` (`id`, `name`, `information`) VALUES (1, 'Indra', 'information about Indra');
INSERT INTO `desglosadb`.`companies` (`id`, `name`, `information`) VALUES (2, 'IECISA', 'informatino about IECISA');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`addresses`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`addresses` (`id`, `street`, `city`, `state`, `country`, `zip`) VALUES (1, 'calle', 'Ciudad Real', 'Ciudad Real', 'España', '13000');
INSERT INTO `desglosadb`.`addresses` (`id`, `street`, `city`, `state`, `country`, `zip`) VALUES (2, 'calle', 'Madrid', 'Madrid', 'España', '28000');
INSERT INTO `desglosadb`.`addresses` (`id`, `street`, `city`, `state`, `country`, `zip`) VALUES (3, 'calle', 'Miguelturra', 'Ciudad Real', 'España', '13000');
INSERT INTO `desglosadb`.`addresses` (`id`, `street`, `city`, `state`, `country`, `zip`) VALUES (4, 'mirasierra', 'Madrid', 'Madrid', 'España', '28000');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`factories`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `director`, `contact_email`, `employees`, `companies_id`, `addresses_id`) VALUES (1, 'Indra Ciudad Real', 'information about Indra Ciudad Real', 'director', 'email', 25, 1, 1);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `director`, `contact_email`, `employees`, `companies_id`, `addresses_id`) VALUES (2, 'Indra Madrid', 'information about Indra Madrid', 'director', 'email', 35, 1, 2);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `director`, `contact_email`, `employees`, `companies_id`, `addresses_id`) VALUES (3, 'IECISA Miguelturra', 'information about IECISA Miguelturra', 'director', 'email', 50, 2, 3);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `director`, `contact_email`, `employees`, `companies_id`, `addresses_id`) VALUES (4, 'IECISA Mirasierra', 'information about IECISA Mirasierra', 'director', 'email', 60, 2, 4);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`maps`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`maps` (`id`, `parentId`, `label`, `filename`, `checksum`) VALUES (1, -1, 'World Map', 'maps/world-map.png', '-1');
INSERT INTO `desglosadb`.`maps` (`id`, `parentId`, `label`, `filename`, `checksum`) VALUES (2, 1, 'Mapa España', 'maps/mapa_espana.gif', '-1');
INSERT INTO `desglosadb`.`maps` (`id`, `parentId`, `label`, `filename`, `checksum`) VALUES (3, 1, 'Peru', 'maps/MAPA-POLITICO-PERU.jpg', '-1');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`locations`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`locations` (`maps_id`, `factories_id`, `xcoord`, `ycoord`) VALUES (1, 1, 3.5384614, 3.6153846);
INSERT INTO `desglosadb`.`locations` (`maps_id`, `factories_id`, `xcoord`, `ycoord`) VALUES (2, 1, 4.5888886, 3.2888887);
INSERT INTO `desglosadb`.`locations` (`maps_id`, `factories_id`, `xcoord`, `ycoord`) VALUES (3, 1, 5.3934193, 3.3762517);

COMMIT;
