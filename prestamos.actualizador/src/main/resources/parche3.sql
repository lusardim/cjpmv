ALTER TABLE `cjpmv`.`cuota`
 DROP FOREIGN KEY `FK3E7054A67463676`;

ALTER TABLE `cjpmv`.`cuota` ADD CONSTRAINT `fk_cuota_cancelacion` 
   FOREIGN KEY `fk_cuota_cancelacion` (`cancelacion_id`)
    REFERENCES `cancelacion` (`id`)
    ON DELETE SET NULL
    ON UPDATE RESTRICT;