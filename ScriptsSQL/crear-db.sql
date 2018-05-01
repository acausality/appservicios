-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema app_servicios
-- -----------------------------------------------------
CREATE DATABASE app_servicios;
USE app_servicios;

CREATE USER usuario_app_servicios IDENTIFIED BY 'app6529@Serv129457';

GRANT ALL ON `app_servicios`.* TO 'usuario_app_servicios';
GRANT SELECT ON TABLE `app_servicios`.* TO 'usuario_app_servicios';
GRANT SELECT, INSERT, TRIGGER ON TABLE `app_servicios`.* TO 'usuario_app_servicios';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `app_servicios`.* TO 'usuario_app_servicios';
/*GRANT EXECUTE ON ROUTINE 'app_servicios'.* TO 'usuario_app_servicios';
linea comentada porque tiraba un error, por mas que fue generada por el workbench*/

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
