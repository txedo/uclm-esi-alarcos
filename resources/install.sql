SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `desglosadb` ;
CREATE SCHEMA IF NOT EXISTS `desglosadb` DEFAULT CHARACTER SET utf8 ;
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
  `address` TINYTEXT NOT NULL ,
  `city` VARCHAR(65) NOT NULL ,
  `province` VARCHAR(65) NULL ,
  `country` VARCHAR(65) NOT NULL ,
  `postal_code` VARCHAR(45) NULL ,
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
  `last_surname` VARCHAR(45) NULL ,
  `image_id` INT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_directors_images` (`image_id` ASC) ,
  CONSTRAINT `fk_directors_images`
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
  `latitude` FLOAT NOT NULL ,
  `longitude` FLOAT NOT NULL ,
  PRIMARY KEY (`id`) )
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
  `address_id` INT NULL ,
  `director_id` INT NULL ,
  `location_id` INT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_factories_companies` (`company_id` ASC) ,
  INDEX `fk_factories_addresses` (`address_id` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  INDEX `fk_factories_directors` (`director_id` ASC) ,
  INDEX `fk_factories_locations` (`location_id` ASC) ,
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
  CONSTRAINT `fk_factories_directors`
    FOREIGN KEY (`director_id` )
    REFERENCES `desglosadb`.`directors` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_factories_locations`
    FOREIGN KEY (`location_id` )
    REFERENCES `desglosadb`.`locations` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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


-- -----------------------------------------------------
-- Table `desglosadb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`users` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `enabled` TINYINT(1)  NOT NULL DEFAULT 1 ,
  `account_expired` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `account_locked` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `credentials_expired` TINYINT(1)  NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) )
ENGINE = InnoDB, 
COMMENT = 'http://static.springsource.org/spring-security/site/docs/3.0' /* comment truncated */ ;


-- -----------------------------------------------------
-- Table `desglosadb`.`groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`groups` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`groups` (
  `id` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`users_groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`users_groups` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`users_groups` (
  `user_id` INT NOT NULL ,
  `group_id` INT NOT NULL ,
  PRIMARY KEY (`user_id`, `group_id`) ,
  INDEX `fk_users_groups_users` (`user_id` ASC) ,
  INDEX `fk_users_groups_groups` (`group_id` ASC) ,
  CONSTRAINT `fk_users_groups_users`
    FOREIGN KEY (`user_id` )
    REFERENCES `desglosadb`.`users` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_groups_groups`
    FOREIGN KEY (`group_id` )
    REFERENCES `desglosadb`.`groups` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`roles` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`roles` (
  `id` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `desglosadb`.`groups_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `desglosadb`.`groups_roles` ;

CREATE  TABLE IF NOT EXISTS `desglosadb`.`groups_roles` (
  `group_id` INT NOT NULL ,
  `role_id` INT NOT NULL ,
  PRIMARY KEY (`group_id`, `role_id`) ,
  INDEX `fk_groups_roles_groups` (`group_id` ASC) ,
  INDEX `fk_groups_roles_roles` (`role_id` ASC) ,
  CONSTRAINT `fk_groups_roles_groups`
    FOREIGN KEY (`group_id` )
    REFERENCES `desglosadb`.`groups` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_groups_roles_roles`
    FOREIGN KEY (`role_id` )
    REFERENCES `desglosadb`.`roles` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


grant ALL on TABLE `desglosadb`.`companies` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`factories` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`addresses` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`locations` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`directors` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`images` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`projects` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`projects_has_factories` to desglosaadmin;
grant ALL on TABLE `desglosadb`.`users` to desglosaadmin;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`companies`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`companies` (`id`, `name`, `information`) VALUES (1, 'Indra', 'information about Indra');
INSERT INTO `desglosadb`.`companies` (`id`, `name`, `information`) VALUES (2, 'IECISA', 'información sobre IECISA');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`addresses`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`addresses` (`id`, `address`, `city`, `province`, `country`, `postal_code`) VALUES (1, 'calle', 'Ciudad Real', 'Ciudad Real', 'España', '13000');
INSERT INTO `desglosadb`.`addresses` (`id`, `address`, `city`, `province`, `country`, `postal_code`) VALUES (2, 'calle', 'Madrid', 'Madrid', 'España', '28000');
INSERT INTO `desglosadb`.`addresses` (`id`, `address`, `city`, `province`, `country`, `postal_code`) VALUES (3, 'calle', 'Miguelturra', 'Ciudad Real', 'España', '13000');
INSERT INTO `desglosadb`.`addresses` (`id`, `address`, `city`, `province`, `country`, `postal_code`) VALUES (4, 'mirasierra', 'Madrid', 'Madrid', 'España', '28000');
INSERT INTO `desglosadb`.`addresses` (`id`, `address`, `city`, `province`, `country`, `postal_code`) VALUES (5, 'liverpool street', 'London', 'England', 'England', 'GBH8Z');

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
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `last_surname`, `image_id`) VALUES (1, 'director', 'de indra', 'ciudad real', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `last_surname`, `image_id`) VALUES (2, 'director', 'de indra', 'madrid', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `last_surname`, `image_id`) VALUES (3, 'director', 'de compañia de ejemplo 1', 'miguelturra', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `last_surname`, `image_id`) VALUES (4, 'director', 'de test company 2', 'mirasierra', 1);
INSERT INTO `desglosadb`.`directors` (`id`, `name`, `first_surname`, `last_surname`, `image_id`) VALUES (5, 'director', 'of uk company', 'london', 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`locations`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`locations` (`id`, `latitude`, `longitude`) VALUES (1, -3.92495155334473, 38.9931718028834);
INSERT INTO `desglosadb`.`locations` (`id`, `latitude`, `longitude`) VALUES (2, -3.69878768920898, 40.4503434970018);
INSERT INTO `desglosadb`.`locations` (`id`, `latitude`, `longitude`) VALUES (3, -3.88306617736816, 38.9583409248377);
INSERT INTO `desglosadb`.`locations` (`id`, `latitude`, `longitude`) VALUES (4, -3.65896224975586, 40.4910873563272);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`factories`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`, `location_id`) VALUES (1, 'Indra Ciudad Real', 'information about Indra Ciudad Real', 'indra@ciudadreal.es', 25, 1, 1, 1, 1);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`, `location_id`) VALUES (2, 'Indra Madrid', 'information about Indra Madrid', 'indra@madrid.es', 35, 1, 2, 2, 2);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`, `location_id`) VALUES (3, 'Factoría en Miguelturra', 'information about a factory in Miguelturra', 'factory@miguelturra.es', 50, 2, 3, 3, 3);
INSERT INTO `desglosadb`.`factories` (`id`, `name`, `information`, `contact_email`, `employees`, `company_id`, `address_id`, `director_id`, `location_id`) VALUES (4, 'Factoría en Mirasierra', 'information about a factory in Mirasierra', 'factory@mirasierra.es', 60, 2, 4, 4, 4);

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
INSERT INTO `desglosadb`.`projects_has_factories` (`project_id`, `factory_id`) VALUES (2, 2);
INSERT INTO `desglosadb`.`projects_has_factories` (`project_id`, `factory_id`) VALUES (2, 4);
INSERT INTO `desglosadb`.`projects_has_factories` (`project_id`, `factory_id`) VALUES (1, 3);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`users` (`id`, `username`, `password`, `enabled`, `account_expired`, `account_locked`, `credentials_expired`) VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 1, 0, 0, 0);
INSERT INTO `desglosadb`.`users` (`id`, `username`, `password`, `enabled`, `account_expired`, `account_locked`, `credentials_expired`) VALUES (2, 'executive', '3250d1e21c4281d3cd9479f5685770b6', 1, 0, 0, 0);
INSERT INTO `desglosadb`.`users` (`id`, `username`, `password`, `enabled`, `account_expired`, `account_locked`, `credentials_expired`) VALUES (3, 'manager', '1d0258c2440a8d19e716292b231e3190', 1, 0, 1, 0);
INSERT INTO `desglosadb`.`users` (`id`, `username`, `password`, `enabled`, `account_expired`, `account_locked`, `credentials_expired`) VALUES (4, 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 1, 0, 0, 0);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`groups`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`groups` (`id`, `name`, `description`) VALUES (1, 'administradores', 'grupo de admins');
INSERT INTO `desglosadb`.`groups` (`id`, `name`, `description`) VALUES (2, 'ejecutivos', 'grupo de execs');
INSERT INTO `desglosadb`.`groups` (`id`, `name`, `description`) VALUES (3, 'jefes de proyecto', 'grupo de managers');
INSERT INTO `desglosadb`.`groups` (`id`, `name`, `description`) VALUES (4, 'usuarios', 'grupo de users');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`users_groups`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`users_groups` (`user_id`, `group_id`) VALUES (1, 1);
INSERT INTO `desglosadb`.`users_groups` (`user_id`, `group_id`) VALUES (2, 2);
INSERT INTO `desglosadb`.`users_groups` (`user_id`, `group_id`) VALUES (3, 3);
INSERT INTO `desglosadb`.`users_groups` (`user_id`, `group_id`) VALUES (4, 4);

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`roles` (`id`, `name`, `description`) VALUES (1, 'ROLE_ADMIN', 'rol de admin');
INSERT INTO `desglosadb`.`roles` (`id`, `name`, `description`) VALUES (2, 'ROLE_EXECUTIVE', 'rol de executive');
INSERT INTO `desglosadb`.`roles` (`id`, `name`, `description`) VALUES (3, 'ROLE_MANAGER', 'rol de manager');
INSERT INTO `desglosadb`.`roles` (`id`, `name`, `description`) VALUES (4, 'ROLE_USER', 'rol de user');

COMMIT;

-- -----------------------------------------------------
-- Data for table `desglosadb`.`groups_roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `desglosadb`;
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (1, 1);
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (1, 2);
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (1, 3);
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (1, 4);
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (2, 2);
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (2, 3);
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (2, 4);
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (3, 3);
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (3, 4);
INSERT INTO `desglosadb`.`groups_roles` (`group_id`, `role_id`) VALUES (4, 4);

COMMIT;
