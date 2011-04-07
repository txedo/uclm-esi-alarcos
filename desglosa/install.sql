SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `desglosadb` ;
CREATE SCHEMA IF NOT EXISTS `desglosadb` DEFAULT CHARACTER SET latin1 ;
USE `desglosadb` ;

-- -----------------------------------------------------
-- Table `desglosadb`.`companies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`companies` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`companies` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `information` TINYTEXT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`addresses` ;

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
-- Table `desglosadb`.`images`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`images` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`images` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `filename` VARCHAR(45) NULL ,
  `content_type` VARCHAR(45) NULL ,
  `data` LONGBLOB NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`directors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`directors` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`directors` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `first_surname` VARCHAR(45) NOT NULL ,
  `second_surname` VARCHAR(45) NULL ,
  `image_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_directors_images` (`image_id` ASC) ,
  CONSTRAINT `fk_directors_images`
    FOREIGN KEY (`image_id` )
    REFERENCES `desglosadb`.`images` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`factories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`factories` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`factories` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `information` TINYTEXT NULL ,
  `contact_email` VARCHAR(65) NULL ,
  `employees` INT NULL ,
  `company_id` INT NOT NULL ,
  `address_id` INT NOT NULL ,
  `director_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_factories_companies` (`company_id` ASC) ,
  INDEX `fk_factories_addresses` (`address_id` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  INDEX `fk_factories_directors` (`director_id` ASC) ,
  CONSTRAINT `fk_factories_companies`
    FOREIGN KEY (`company_id` )
    REFERENCES `desglosadb`.`companies` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_factories_addresses`
    FOREIGN KEY (`address_id` )
    REFERENCES `desglosadb`.`addresses` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_factories_directors1`
    FOREIGN KEY (`director_id` )
    REFERENCES `desglosadb`.`directors` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`maps`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`maps` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`maps` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `parentId` INT NULL ,
  `label` VARCHAR(45) NULL ,
  `checksum` VARCHAR(45) NULL ,
  `image_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `label_UNIQUE` (`label` ASC) ,
  INDEX `fk_maps_images` (`image_id` ASC) ,
  CONSTRAINT `fk_maps_images1`
    FOREIGN KEY (`image_id` )
    REFERENCES `desglosadb`.`images` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`locations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`locations` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`locations` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `map_id` INT NOT NULL ,
  `factory_id` INT NOT NULL ,
  `xcoord` FLOAT NOT NULL ,
  `ycoord` FLOAT NOT NULL ,
  INDEX `fk_locations_maps` (`map_id` ASC) ,
  INDEX `fk_locations_factories` (`factory_id` ASC) ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `fk_maps_factories_id_UNIQUE` (`map_id` ASC, `factory_id` ASC) ,
  CONSTRAINT `fk_locations_maps`
    FOREIGN KEY (`map_id` )
    REFERENCES `desglosadb`.`maps` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_locations_factories`
    FOREIGN KEY (`factory_id` )
    REFERENCES `desglosadb`.`factories` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`projects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`projects` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`projects` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `code` VARCHAR(45) NULL ,
  `planName` VARCHAR(45) NULL ,
  `workingFactory_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_projects_factories` (`workingFactory_id` ASC) ,
  CONSTRAINT `fk_projects_factories`
    FOREIGN KEY (`workingFactory_id` )
    REFERENCES `desglosadb`.`factories` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`projects_has_factories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`projects_has_factories` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`projects_has_factories` (
  `project_id` INT NOT NULL ,
  `factory_id` INT NOT NULL ,
  PRIMARY KEY (`project_id`, `factory_id`) ,
  INDEX `fk_projects_has_factories_factories` (`factory_id` ASC) ,
  INDEX `fk_projects_has_factories_projects` (`project_id` ASC) ,
  CONSTRAINT `fk_projects_has_factories_projects`
    FOREIGN KEY (`project_id` )
    REFERENCES `desglosadb`.`projects` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_projects_has_factories_factories`
    FOREIGN KEY (`factory_id` )
    REFERENCES `desglosadb`.`factories` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


grant ALL on TABLE `desglosadb`.`companies` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`factories` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`addresses` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`locations` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`maps` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`directors` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`images` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`projects` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`projects_has_factories` to desglosaadmin;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`companies`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`companies` (`id`, `name`, `information`) VALUES (1, 'Indra', 'information about Indra');
INSERT INTO `desglosadb`.`companies` (`id`, `name`, `information`) VALUES (2, 'Compañía de Ejemplo 1', 'información sobre una compañía de ejemplo');
INSERT INTO `desglosadb`.`companies` (`id`, `name`, `information`) VALUES (3, 'Test Company 2', 'information about a test company');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`addresses`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`addresses` (`id`, `street`, `city`, `state`, `country`, `zip`) VALUES (1, 'calle', 'Ciudad Real', 'Ciudad Real', 'España', '13000');
INSERT INTO `desglosadb`.`addresses` (`id`, `street`, `city`, `state`, `country`, `zip`) VALUES (2, 'calle', 'Madrid', 'Madrid', 'España', '28000');
INSERT INTO `desglosadb`.`addresses` (`id`, `street`, `city`, `state`, `country`, `zip`) VALUES (3, 'calle', 'Miguelturra', 'Ciudad Real', 'España', '13000');
INSERT INTO `desglosadb`.`addresses` (`id`, `street`, `city`, `state`, `country`, `zip`) VALUES (4, 'mirasierra', 'Madrid', 'Madrid', 'España', '28000');
INSERT INTO `desglosadb`.`addresses` (`id`, `street`, `city`, `state`, `country`, `zip`) VALUES (5, 'liverpool street', 'London', 'England', 'England', 'GBH8Z');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`images`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`images` (`id`, `filename`, `content_type`, `data`) VALUES (1, 'anonymous', 'image/jpg', NULL);
INSERT INTO `desglosadb`.`images` (`id`, `filename`, `content_type`, `data`) VALUES (2, 'world map', 'image/png', NULL);
INSERT INTO `desglosadb`.`images` (`id`, `filename`, `content_type`, `data`) VALUES (3, 'spain', 'image/png', NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`directors`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `second_surname`, `image_id`) VALUES (1, 'director', 'de indra', 'ciudad real', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `second_surname`, `image_id`) VALUES (2, 'director', 'de indra', 'madrid', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `second_surname`, `image_id`) VALUES (3, 'director', 'de compañia de ejemplo 1', 'miguelturra', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `second_surname`, `image_id`) VALUES (4, 'director', 'de test company 2', 'mirasierra', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `second_surname`, `image_id`) VALUES (5, 'director', 'of uk company', 'london', 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`factories`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`) VALUES (1, 'Indra Ciudad Real', 'information about Indra Ciudad Real', 'indra@ciudadreal.es', 25, 1, 1, 1);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`) VALUES (2, 'Indra Madrid', 'information about Indra Madrid', 'indra@madrid.es', 35, 1, 2, 2);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`) VALUES (3, 'Factoría en Miguelturra', 'information about a factory in Miguelturra', 'factory@miguelturra.es', 50, 2, 3, 3);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`) VALUES (4, 'Factoría en Mirasierra', 'information about a factory in Mirasierra', 'factory@mirasierra.es', 60, 2, 4, 4);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`) VALUES (5, 'UK Factory', 'info about an UK factory', 'factory@company.co.uk', 150, 3, 5, 5);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`maps`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`maps` (`id`, `parentId`, `label`, `checksum`, `image_id`) VALUES (1, -1, 'World Map', '-1', 2);
INSERT INTO `desglosadb`.`maps` (`id`, `parentId`, `label`, `checksum`, `image_id`) VALUES (2, 1, 'Mapa España', '-1', 3);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`locations`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (1, 1, 1, 1.27436, 3.76312);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (2, 1, 2, 1.3943, 3.92804);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (3, 1, 3, 1.4093, 3.67316);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (4, 1, 4, 1.55922, 4.01799);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (5, 2, 1, 3.55064, 3.31781);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (6, 2, 2, 3.93319, 4.27802);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (7, 2, 3, 3.88306, 3.13343);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (8, 2, 4, 4.18405, 4.44172);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (9, 1, 5, 7.153374, 4.2944784);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`projects`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`projects` (`id`, `code`, `planName`, `workingFactory_id`) VALUES (1, 'DGL', 'DESGLOSA', 1);
INSERT INTO `desglosadb`.`projects` (`id`, `code`, `planName`, `workingFactory_id`) VALUES (2, 'VLM', 'VILMA', 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`projects_has_factories`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`projects_has_factories` (`project_id`, `factory_id`) VALUES (1, 1);
INSERT INTO `desglosadb`.`projects_has_factories` (`project_id`, `factory_id`) VALUES (1, 5);
INSERT INTO `desglosadb`.`projects_has_factories` (`project_id`, `factory_id`) VALUES (2, 2);
INSERT INTO `desglosadb`.`projects_has_factories` (`project_id`, `factory_id`) VALUES (2, 4);

COMMIT;
