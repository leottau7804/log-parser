Parser Project - Access log parser
====================

Access Log Parser is a project created by Sergio Leottau with the purpose to parse web server access logs   

# What is this repository for?

* [Utilization]
  * [Restore database]
  * [Verify properties]
  * [Parse access log]
  * [Search IPs]
------

## Utilization

### Utilization steps
1. Verify folder
2. Restore database 
3. Verify properties
4. Parse access log
5. Search IPs

### Verify folder content

The folder must contain the follow files:

- parser.jar
- parser.properties
- database_backup.sql
- example_access_log 
- Readme.md

### Restore database

To restore the application database you have to execute the follow script:

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

### Verify properties

To execute the application you have to have a properties file with the name "parser.properties" and the 
follow properties:

- database.driver={driver.class}
- database.url=jdbc:mysql://{host}:{port}/
- database.username={username}
- database.password={password}
- parser.date.format={data format} (Ej: dd/MMM/yyyy:HH:mm:ss +SSSS)
- search.date.format={date format} (Ej: yyyy-MM-dd.HH:mm:ss)

### Parser access log

To parse a web server access log file you must execute the follow command:

java -cp parser.jar com.ef.Parser --logFile=/path/filename

- the log file must contain access log with a | as separator
- the access log date must satify parser.date.format property 

### Search IPs

To parse a web server access log file you must execute the follow command:

java -cp parser.jar com.ef.Parser --startDate=yyyy-MM-dd.HH:mm:ss --duration=hourly/daily --threshold=10

- startDate: must satisfy search.date.format property
- duration: must be either "hourly" or "daily"
- threshold: must be a positive number

