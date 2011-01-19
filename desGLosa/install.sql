SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `desglosadb` DEFAULT CHARACTER SET latin1 ;
USE `desglosadb` ;

-- -----------------------------------------------------
-- Table `desglosadb`.`companies`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`companies` (
  `idcompanies` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `information` TINYTEXT NULL ,
  PRIMARY KEY (`idcompanies`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`factory_addresses`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`factory_addresses` (
  `idfactory_addresses` INT NOT NULL AUTO_INCREMENT ,
  `street` TINYTEXT NOT NULL ,
  `city` VARCHAR(65) NOT NULL ,
  `state` VARCHAR(65) NOT NULL ,
  `country` VARCHAR(65) NOT NULL ,
  `zip` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idfactory_addresses`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`factories`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`factories` (
  `idfactories` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `information` TINYTEXT NULL ,
  `director` VARCHAR(65) NULL ,
  `contact_email` VARCHAR(65) NULL ,
  `employees` INT NULL ,
  `companies_id` INT NOT NULL ,
  `factory_address_id` INT NOT NULL ,
  PRIMARY KEY (`idfactories`) ,
  INDEX `fk_factories_companies` (`companies_id` ASC) ,
  INDEX `fk_factories_factory_address1` (`factory_address_id` ASC) ,
  CONSTRAINT `fk_factories_companies`
    FOREIGN KEY (`companies_id` )
    REFERENCES `desglosadb`.`companies` (`idcompanies` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_factories_factory_address1`
    FOREIGN KEY (`factory_address_id` )
    REFERENCES `desglosadb`.`factory_addresses` (`idfactory_addresses` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`maps`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`maps` (
  `idmaps` INT NOT NULL AUTO_INCREMENT ,
  `parentId` INT NULL ,
  `label` VARCHAR(45) NULL ,
  `filename` VARCHAR(45) NULL ,
  `hashcode` VARCHAR(45) NULL ,
  PRIMARY KEY (`idmaps`) ,
  INDEX `fk_maps_parentId` (`idmaps` ASC) ,
  CONSTRAINT `fk_maps_parentId`
    FOREIGN KEY (`idmaps` )
    REFERENCES `desglosadb`.`maps` (`idmaps` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`locations`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `desglosadb`.`locations` (
  `maps_idmaps` INT NOT NULL ,
  `factories_id` INT NOT NULL ,
  `xcoord` FLOAT NOT NULL ,
  `ycoord` FLOAT NOT NULL ,
  INDEX `fk_locations_maps1` (`maps_idmaps` ASC) ,
  INDEX `fk_locations_factories1` (`factories_id` ASC) ,
  PRIMARY KEY (`maps_idmaps`, `factories_id`) ,
  CONSTRAINT `fk_locations_maps1`
    FOREIGN KEY (`maps_idmaps` )
    REFERENCES `desglosadb`.`maps` (`idmaps` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_locations_factories1`
    FOREIGN KEY (`factories_id` )
    REFERENCES `desglosadb`.`factories` (`idfactories` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE USER `desglosaadmin` IDENTIFIED BY 'nimdaasolgsed';

grant ALL on TABLE `desglosadb`.`companies` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`factories` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`factory_addresses` to desglosaadmin;
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
INSERT INTO `desglosadb`.`companies` (`idcompanies`, `name`, `information`) VALUES (1, 'Indra', 'information about Indra');
INSERT INTO `desglosadb`.`companies` (`idcompanies`, `name`, `information`) VALUES (2, 'IECISA', 'informatino about IECISA');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`factory_addresses`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`factory_addresses` (`idfactory_addresses`, `street`, `city`, `state`, `country`, `zip`) VALUES (1, 'calle', 'Ciudad Real', 'Ciudad Real', 'España', '13000');
INSERT INTO `desglosadb`.`factory_addresses` (`idfactory_addresses`, `street`, `city`, `state`, `country`, `zip`) VALUES (2, 'calle', 'Madrid', 'Madrid', 'España', '28000');
INSERT INTO `desglosadb`.`factory_addresses` (`idfactory_addresses`, `street`, `city`, `state`, `country`, `zip`) VALUES (3, 'calle', 'Miguelturra', 'Ciudad Real', 'España', '13000');
INSERT INTO `desglosadb`.`factory_addresses` (`idfactory_addresses`, `street`, `city`, `state`, `country`, `zip`) VALUES (4, 'mirasierra', 'Madrid', 'Madrid', 'España', '28000');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`factories`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`factories` (`idfactories`, `name`, `information`, `director`, `contact_email`, `employees`, `companies_id`, `factory_address_id`) VALUES (1, 'Indra Ciudad Real', 'information about Indra Ciudad Real', 'director', 'email', 25, 1, 1);
INSERT INTO `desglosadb`.`factories` (`idfactories`, `name`, `information`, `director`, `contact_email`, `employees`, `companies_id`, `factory_address_id`) VALUES (2, 'Indra Madrid', 'information about Indra Madrid', 'director', 'email', 35, 1, 2);
INSERT INTO `desglosadb`.`factories` (`idfactories`, `name`, `information`, `director`, `contact_email`, `employees`, `companies_id`, `factory_address_id`) VALUES (3, 'IECISA Miguelturra', 'information about IECISA Miguelturra', 'director', 'email', 50, 2, 3);
INSERT INTO `desglosadb`.`factories` (`idfactories`, `name`, `information`, `director`, `contact_email`, `employees`, `companies_id`, `factory_address_id`) VALUES (4, 'IECISA Mirasierra', 'information about IECISA Mirasierra', 'director', 'email', 60, 2, 4);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`maps`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`maps` (`idmaps`, `parentId`, `label`, `filename`, `hashcode`) VALUES (1, 2, 'Mapa España', 'resources/maps/mapa_espana.gif', '-1');
INSERT INTO `desglosadb`.`maps` (`idmaps`, `parentId`, `label`, `filename`, `hashcode`) VALUES (2, 2, 'World Map', 'resources/maps/world-map.png', '-1');
INSERT INTO `desglosadb`.`maps` (`idmaps`, `parentId`, `label`, `filename`, `hashcode`) VALUES (3, 2, 'Peru', 'resources/maps/MAPA-POLITICO-PERU.jpg', '-1');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`locations`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`locations` (`maps_idmaps`, `factories_id`, `xcoord`, `ycoord`) VALUES (1, 1, 3.5384614, 3.6153846);
INSERT INTO `desglosadb`.`locations` (`maps_idmaps`, `factories_id`, `xcoord`, `ycoord`) VALUES (2, 1, 4.5888886, 3.2888887);
INSERT INTO `desglosadb`.`locations` (`maps_idmaps`, `factories_id`, `xcoord`, `ycoord`) VALUES (3, 1, 5.3934193, 3.3762517);

COMMIT;
