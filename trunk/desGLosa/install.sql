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
  `projects_id` INT NOT NULL ,
  `factories_id` INT NOT NULL ,
  PRIMARY KEY (`projects_id`, `factories_id`) ,
  INDEX `fk_projects_has_factories_factories` (`factories_id` ASC) ,
  INDEX `fk_projects_has_factories_projects` (`projects_id` ASC) ,
  CONSTRAINT `fk_projects_has_factories_projects`
    FOREIGN KEY (`projects_id` )
    REFERENCES `desglosadb`.`projects` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_projects_has_factories_factories`
    FOREIGN KEY (`factories_id` )
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
-- Data for table `desglosadb`.`images`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`images` (`id`, `filename`, `content_type`, `data`) VALUES (1, 'anonymous', 'image/jpg', NULL);
INSERT INTO `desglosadb`.`images` (`id`, `filename`, `content_type`, `data`) VALUES (2, 'world map', 'image/png', NULL);
INSERT INTO `desglosadb`.`images` (`id`, `filename`, `content_type`, `data`) VALUES (3, 'spain', 'image/png', NULL);
INSERT INTO `desglosadb`.`images` (`id`, `filename`, `content_type`, `data`) VALUES (4, 'peru', 'image/jpg', NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`directors`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `second_surname`, `image_id`) VALUES (1, 'director', 'de indra', 'ciudad real', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `second_surname`, `image_id`) VALUES (2, 'director', 'de indra', 'madrid', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `second_surname`, `image_id`) VALUES (3, 'director', 'de iecisa', 'miguelturra', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `second_surname`, `image_id`) VALUES (4, 'director', 'de iecisa', 'mirasierra', 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`factories`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`) VALUES (1, 'Indra Ciudad Real', 'information about Indra Ciudad Real', 'email', 25, 1, 1, 1);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`) VALUES (2, 'Indra Madrid', 'information about Indra Madrid', 'email', 35, 1, 2, 2);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`) VALUES (3, 'IECISA Miguelturra', 'information about IECISA Miguelturra', 'email', 50, 2, 3, 3);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`) VALUES (4, 'IECISA Mirasierra', 'information about IECISA Mirasierra', 'email', 60, 2, 4, 4);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`maps`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`maps` (`id`, `parentId`, `label`, `checksum`, `image_id`) VALUES (1, -1, 'World Map', '-1', 2);
INSERT INTO `desglosadb`.`maps` (`id`, `parentId`, `label`, `checksum`, `image_id`) VALUES (2, 1, 'Mapa España', '-1', 3);
INSERT INTO `desglosadb`.`maps` (`id`, `parentId`, `label`, `checksum`, `image_id`) VALUES (3, 1, 'Peru', '-1', 4);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`locations`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `desglosadb`;
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (1, 2, 1, 3.5191083, 3.630573);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (2, 2, 2, 3.9968152, 4.6019106);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (3, 1, 1, 4.5409017, 3.2303839);
INSERT INTO `desglosadb`.`locations` (`id`, `map_id`, `factory_id`, `xcoord`, `ycoord`) VALUES (4, 1, 2, 4.5575957, 3.338898);

COMMIT;
