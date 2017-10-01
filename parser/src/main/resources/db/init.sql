CREATE DATABASE IF NOT EXISTS parser;

CREATE TABLE IF NOT EXISTS `parser`.`access_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ip` VARCHAR(100) NOT NULL,
  `date` DATETIME NOT NULL,
  `log` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `by_ip` (`ip` ASC),
  INDEX `by_data` (`date` ASC),
  INDEX `by_ip_and_date` (`ip` ASC, `date` ASC));

  CREATE TABLE IF NOT EXISTS `parser`.`blocked` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ip` VARCHAR(100) NOT NULL,
  `observations` VARCHAR(500) NULL,
  PRIMARY KEY (`id`));

