SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

SET AUTOCOMMIT=0;
start transaction;
begin;
ALTER TABLE `cjpmv`.`beneficio` DROP FOREIGN KEY `FKA290D80CF7FA9AD3` ;

ALTER TABLE `cjpmv`.`empleo` DROP FOREIGN KEY `FK7BF2F06E38A30203` ;

ALTER TABLE `cjpmv`.`personafisica` DROP FOREIGN KEY `FK11F84B2335F9F1AF` ;

ALTER TABLE `cjpmv`.`beneficio` 
	ADD COLUMN `persona_id` BIGINT(20),
   ADD COLUMN `clase` VARCHAR(30) NOT NULL  AFTER `ordenanza` , 
  ADD COLUMN `tipoBeneficio` VARCHAR(255) NULL DEFAULT NULL  AFTER `clase` , 
  ADD COLUMN `valor` DECIMAL(16,6) NULL DEFAULT NULL  AFTER `tipoBeneficio` , 
  ADD COLUMN `causante_id` BIGINT(20) AFTER `valor` , 
  ADD CONSTRAINT `fk_beneficio_persona`
  FOREIGN KEY (`persona_id` )
  REFERENCES `cjpmv`.`personafisica` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  
 ADD CONSTRAINT `fk_beneficio_causante`
  FOREIGN KEY (`causante_id` )
  REFERENCES `cjpmv`.`personafisica` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_beneficio_persona` (`persona_id` ASC) 
, ADD INDEX `fk_beneficio_causante` (`causante_id` ASC)
, DROP INDEX `FKA290D80CF7FA9AD3` ;



UPDATE `cjpmv`.`beneficio` SET `clase` = 'JUBILACION',
`tipoBeneficio` = 'JUBILACION_COMUN' WHERE `beneficio`.`tipo_id` =1;

UPDATE `cjpmv`.`beneficio` SET `clase` = 'JUBILACION',
`tipoBeneficio` = 'JUBILACION_INVALIDEZ' WHERE `beneficio`.`tipo_id` =2;

UPDATE `cjpmv`.`beneficio` SET `clase` = 'JUBILACION',
`tipoBeneficio` = 'JUBILACION_EDAD' WHERE `beneficio`.`tipo_id` =3;

UPDATE `cjpmv`.`beneficio` SET `clase` = 'PENSION',
`tipoBeneficio` = 'PENSION' WHERE `beneficio`.`tipo_id` =4;

UPDATE `cjpmv`.`beneficio` SET `clase` = 'JUBILACION',
`tipoBeneficio` = 'JUBILACION_ANTICIPADA' WHERE `beneficio`.`tipo_id` =5;

ALTER TABLE `cjpmv`.`beneficio` DROP COLUMN `tipo_id`;

ALTER TABLE `cjpmv`.`categoria` 
	ADD COLUMN `numero` INT(11) NOT NULL  AFTER `nombre` , 
	ADD COLUMN `monto` DECIMAL(12,2) AFTER `numero` , 
	ADD COLUMN `tipoPersona` VARCHAR(45) NOT NULL DEFAULT 'ACTIVO',
	ADD COLUMN `porcentaje` DECIMAL(8,4) NULL DEFAULT NULL  AFTER `monto` ,
	ADD COLUMN `porcentajesobre_id` BIGINT(20) AFTER `porcentaje` , 
	CHANGE COLUMN `descripcion` `nombre` VARCHAR(255) NOT NULL  , 
  ADD CONSTRAINT `fk_categoria_categoria1`
  FOREIGN KEY (`porcentajesobre_id` )
  REFERENCES `cjpmv`.`categoria` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_categoria_categoria1` (`porcentajesobre_id` ASC) ;

ALTER TABLE `cjpmv`.`credito` DROP COLUMN `diaVencimieto` ;

delete from empleo where empleo.empleado_id 
in (select id from personafisica 
    where estado = 'PASIVO');

ALTER TABLE `cjpmv`.`empleo` 
DROP COLUMN `diasAntiguedadEmpleo` , 
DROP COLUMN `cargo` , 
DROP COLUMN `codigoLiquidacion` , 
ADD COLUMN `antiguedad_id` BIGINT(20)  AFTER `categoria_id` , 
CHANGE COLUMN `tipoCategoria_id` `categoria_id` BIGINT(20) , 
ADD COLUMN `permanencia_id` BIGINT(20) AFTER `antiguedad_id`, 
  ADD CONSTRAINT `fk_empleo_categoria`
  FOREIGN KEY (`categoria_id` )
  REFERENCES `cjpmv`.`categoria` (`id` ),
  
  ADD CONSTRAINT `fk_permanenciacategoria_empleo`
  FOREIGN KEY (`permanencia_id` )
  REFERENCES `cjpmv`.`permanenciacategoria` (`id` ), 

  ADD CONSTRAINT `fk_empleo_antiguedad1`
  FOREIGN KEY (`antiguedad_id` )
  REFERENCES `cjpmv`.`antiguedad` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_empleo_antiguedad1` (`antiguedad_id` ASC)
, ADD INDEX `fk_permanenciacategoria_empleo` (`permanencia_id` ASC);

CREATE TABLE IF NOT EXISTS cjpmv.permanenciacategoria(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    minimo INT(11) NULL DEFAULT NULL,
    maximo INT(11) NULL DEFAULT NULL,
    porcentaje DECIMAL(12, 2 ) NULL DEFAULT NULL,
    PRIMARY KEY (id)) 
ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.porcentajetipobeneficio(
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	porcentaje DECIMAL(16,6) NOT NULL,
	tipo VARCHAR(45) NOT NULL,
	PRIMARY KEY (id)) 
ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

INSERT INTO cjpmv.porcentajetipobeneficio(id, porcentaje, tipo)
VALUES (1, 0.82, 'JUBILACION_COMUN'),
       (2, 0.65, 'PENSION');


CREATE TABLE IF NOT EXISTS cjpmv.montopermanenciaporcategoria(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    monto DECIMAL(12, 2 ) NOT NULL,
    permanencia_id BIGINT(20) NOT NULL,
    categoria_id BIGINT(20) NOT NULL,
    PRIMARY KEY (id),
    INDEX fk_montopermanenciaporcategoria_permanenciacategoria1 (permanencia_id ASC),
    INDEX fk_montopermanenciaporcategoria_categoria1 (categoria_id ASC),
    CONSTRAINT fk_montopermanenciacategoria_permanenciacategoria1 FOREIGN KEY (permanencia_id)
        REFERENCES cjpmv.permanenciacategoria (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_montopermanenciaporcategoria_categoria1 FOREIGN KEY (categoria_id)
        REFERENCES cjpmv.categoria (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.antiguedad(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    minimo INT(11) NOT NULL,
    maximo INT(11) NOT NULL,
    porcentaje DECIMAL(8, 4 ) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.montoantiguedadporcategoria(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    monto DECIMAL(12, 2 ) NOT NULL,
    antiguedad_id BIGINT(20) NOT NULL,
    categoria_id BIGINT(20) NOT NULL,
    PRIMARY KEY (id),
    INDEX fk_montoantiguedadporcategoria_antiguedad1 (antiguedad_id ASC),
    INDEX fk_montoantiguedadporcategoria_categoria1 (categoria_id ASC),
    CONSTRAINT fk_montoantiguedadporcategoria_antiguedad1 FOREIGN KEY (antiguedad_id)
        REFERENCES cjpmv.antiguedad (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_montoantiguedadporcategoria_categoria1 FOREIGN KEY (categoria_id)
        REFERENCES cjpmv.categoria (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;


CREATE TABLE IF NOT EXISTS cjpmv.conceptohaberes(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    codigo INT(11) NOT NULL,
    descripcion VARCHAR(255) NULL DEFAULT NULL,
    tipocodigo VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (id)
    
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS conceptohaberes_plantilla(
	conceptohaberes_id BIGINT(20) NOT NULL,
	plantilla_id BIGINT(20) NOT NULL,
	PRIMARY KEY (conceptohaberes_id,plantilla_id), 
	INDEX fk_rela_chaberesplantilla_plantilla (plantilla_id ASC),
    CONSTRAINT fk_rela_chaberesplantilla_plantilla FOREIGN KEY (plantilla_id)
        REFERENCES cjpmv.plantilla (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
        
    INDEX fk_rela_chaberesplantilla_conceptohaberes (conceptohaberes_id ASC),
    CONSTRAINT fk_rela_chaberesplantilla_conceptohaberes FOREIGN KEY (conceptohaberes_id)
        REFERENCES cjpmv.conceptohaberes (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.conceptofijo(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    valor DECIMAL(12, 2 ),
    sobreescribirvalor TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT fk_conceptofijo_conceptohaberes1 FOREIGN KEY (id)
        REFERENCES cjpmv.conceptohaberes (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.conceptoporcentual(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    valor DECIMAL(12, 2 ) NOT NULL,
    sobretotaltipo VARCHAR(45) NULL DEFAULT NULL,
    sobrecategoria_id BIGINT(20) NULL DEFAULT NULL,
    PRIMARY KEY (id),
    INDEX fk_conceptoporcentual_categoria1 (sobrecategoria_id ASC),
    CONSTRAINT fk_conceptoporcentual_conceptohaberes1 FOREIGN KEY (id)
        REFERENCES cjpmv.conceptohaberes (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_conceptoporcentual_categoria1 FOREIGN KEY (sobrecategoria_id)
        REFERENCES cjpmv.categoria (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.conceptoprefijado(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    CONSTRAINT fk_conceptoprefijado_conceptohaberes1 FOREIGN KEY (id)
        REFERENCES cjpmv.conceptohaberes (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.plantilla(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    tipoliquidacion VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.liquidacionhaberes(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    mes INT(11) NOT NULL,
    anio INT(11) NOT NULL,
    tipo VARCHAR(45) NULL DEFAULT NULL,
    plantilla_id BIGINT(20) NULL DEFAULT NULL,
    PRIMARY KEY (id),
    INDEX fk_liquidacionhaberes_plantilla1 (plantilla_id ASC),
    CONSTRAINT fk_liquidacionhaberes_plantilla1 FOREIGN KEY (plantilla_id)
        REFERENCES cjpmv.plantilla (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.recibosueldo(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    liquidacion_id BIGINT(20) NOT NULL,
    persona_id BIGINT(20) NOT NULL,
    PRIMARY KEY (id),
    INDEX fk_recibosueldo_liquidacionhaberes1 (liquidacion_id ASC),
    INDEX fk_recibosueldo_personafisica1 (persona_id ASC),
    CONSTRAINT fk_recibosueldo_liquidacionhaberes1 FOREIGN KEY (liquidacion_id)
        REFERENCES cjpmv.liquidacionhaberes (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_recibosueldo_personafisica1 FOREIGN KEY (persona_id)
        REFERENCES cjpmv.personafisica (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS cjpmv.linearecibo(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    cantidad INT(11) NOT NULL DEFAULT 1,
    monto DECIMAL(12, 2 ) NULL DEFAULT NULL,
    recibosueldo_id BIGINT(20) NOT NULL,
    concepto_id BIGINT(20) NOT NULL,
    PRIMARY KEY (id),
    INDEX fk_linearecibo_recibosueldo1 (recibosueldo_id ASC),
    INDEX fk_linearecibo_conceptohaberes1 (concepto_id ASC),
    CONSTRAINT fk_linearecibo_recibosueldo1 FOREIGN KEY (recibosueldo_id)
        REFERENCES cjpmv.recibosueldo (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_linearecibo_conceptohaberes1 FOREIGN KEY (concepto_id)
        REFERENCES cjpmv.conceptohaberes (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET=latin1 COLLATE =latin1_swedish_ci;

DROP TABLE IF EXISTS `cjpmv`.`tipobeneficio` ;

DROP TABLE IF EXISTS `cjpmv`.`antiguedadinicial` ;

INSERT INTO antiguedad (id, minimo, maximo, porcentaje)
VALUES (1, 0, 5, 0.10),
	 (2, 5, 10, 0.22),
	 (3, 10, 15, 0.42),
	 (4, 15, 20, 0.72),
	 (7, 20, 25, 0.90),
	 (8, 25, 0, 1.00);
	 
INSERT INTO permanenciacategoria(id, minimo, maximo, porcentaje)
VALUES (1, 2, 4, 0.10),
	   (2, 4, 6, 0.25),
	   (3, 6, 8, 0.45),
	   (4, 8, 0, 0.70);
	   
	   
update categoria set nombre = 'Categoria';
update categoria set numero = id;
update categoria set monto = 2535.75 where id = 1;
update categoria set porcentaje = 0.10 where id = 2;
update categoria set porcentaje = 0.20 where id = 3;
update categoria set porcentaje = 0.30 where id = 4;
update categoria set porcentaje = 0.45 where id = 5;
update categoria set porcentaje = 0.55 where id = 6;
update categoria set porcentaje = 0.70 where id = 7;
update categoria set porcentaje = 0.80 where id = 8;
update categoria set porcentaje = 1.05 where id = 9;
update categoria set porcentaje = 1.40 where id = 10;
update categoria set porcentajesobre_id = 1 where id <> 1;
update categoria set tipoPersona = 'ACTIVO';

INSERT INTO categoria(id, numero, nombre, porcentaje, monto, tipoPersona, porcentajesobre_id)
values (11, 11, 'Categoria', 1.80, null, 'ACTIVO', 1),
   	   (12, 12, 'Categoria', 2.10, null, 'ACTIVO', 1);

INSERT INTO categoria(id, numero, nombre, porcentaje, monto, tipoPersona, porcentajesobre_id)
values (13, 1, 'Categoria', null, 1811.25, 'PASIVO', null),
   	   (14, 2, 'Categoria', 0.10, null, 'PASIVO', 13),
   	   (15, 3, 'Categoria', 0.20, null, 'PASIVO', 13),
   	   (16, 4, 'Categoria', 0.30, null, 'PASIVO', 13),
   	   (17, 5, 'Categoria', 0.45, null, 'PASIVO', 13),
   	   (18, 6, 'Categoria', 0.55, null, 'PASIVO', 13),
   	   (19, 7, 'Categoria', 0.70, null, 'PASIVO', 13),
   	   (20, 8, 'Categoria', 0.80, null, 'PASIVO', 13),
   	   (21, 9, 'Categoria', 1.05, null, 'PASIVO', 13),
   	   (22,10, 'Categoria', 1.40, null, 'PASIVO', 13),
   	   (23,11, 'Categoria', 1.80, null, 'PASIVO', 13),
   	   (24,12, 'Categoria', 2.10, null, 'PASIVO', 13);
	/*activos*/   
INSERT INTO montopermanenciaporcategoria(id, permanencia_id, categoria_id, monto) 
VALUES (1, 1,1,25.36), 
		(2, 2,1,63.40), 
		(3, 3,1,114.11), 
		(4, 4,1,177.51), 
		(5, 1,2,25.36), 
		(6, 2,2,63.39), 
		(7, 3,2,114.11), 
		(8, 4,2,177.50), 
		(9, 1,3,25.36), 
		(10, 2,3,63.40), 
		(11, 3,3,114.11), 
		(12, 4,3,177.51), 
		(13, 1,4,38.04), 
		(14, 2,4,95.09), 
		(15, 3,4,171.16), 
		(16, 4,4,266.25), 
		(17, 1,5,25.36), 
		(18, 2,5,63.39), 
		(19, 3,5,114.11);
INSERT INTO montopermanenciaporcategoria(id, permanencia_id, categoria_id, monto)
VALUES  (20, 4,5,177.50), 
		(21, 1,6,38.04), 
		(22, 2,6,95.09), 
		(23, 3,6,171.17), 
		(24, 4,6,266.26), 
		(25, 1,7,25.36), 
		(26, 2,7,63.39), 
		(27, 3,7,114.11), 
		(28, 4,7,177.50), 
		(29, 1,8,63.39), 
		(30, 2,8,158.49), 
		(31, 3,8,285.27), 
		(32, 4,8,443.76), 
		(33, 1,9,88.75), 
		(34, 2,9,221.88), 
		(35, 3,9,399.38), 
		(36, 4,9,621.26), 
		(37, 1,10,101.43), 
		(38, 2,10,253.58), 
		(39, 3,10,456.44), 
		(40, 4,10,710.01), 
		(41, 1,11,76.07), 
		(42, 2,11,190.18), 
		(43, 3,11,342.33), 
		(44, 4,11,532.51), 
		(45, 1,12,0.00), 
		(46, 2,12,0.00), 
		(47, 3,12,0.00), 
		(48, 4,12,0.00);
		
INSERT INTO montoantiguedadporcategoria(id, categoria_id, antiguedad_id, monto)
 VALUES (1,1,1,253.58),
	(2,1,2,557.87),
	(3,1,3,1065.02),
	(4,1,4,1825.74),
	(5,1,7,2282.18),
	(6,1,8,2535.75),
	(7,2,1,278.93),
	(8,2,2,613.65),
	(9,2,3,1171.52),
	(10,2,4,2008.32),
	(11,2,7,2510.40),
	(12,2,8,2789.33),
	(13,3,1,304.29),
	(14,3,2,669.44),
	(15,3,3,1278.02),
	(16,3,4,2190.89),
	(17,3,7,2738.61),
	(18,3,8,3042.90),
	(19,4,1,329.65);
INSERT INTO montoantiguedadporcategoria(id, categoria_id, antiguedad_id, monto)
VALUES	(20,4,2,725.23),
	(21,4,3,1384.52),
	(22,4,4,2373.47),
	(23,4,7,2966.83),
	(24,4,8,3296.48),
	(25,5,1,367.68),
	(26,5,2,808.90),
	(27,5,3,1544.27),
	(28,5,4,2647.32),
	(29,5,7,3309.16),
	(30,5,8,3676.84),
	(31,6,1,393.04),
	(32,6,2,864.69),
	(33,6,3,1650.77),
	(34,6,4,2829.90),
	(35,6,7,3537.37),
	(36,6,8,3930.41),
	(37,7,1,431.08),
	(38,7,2,948.37),
	(39,7,3,1810.53),
	(40,7,4,3103.76),
	(41,7,7,3879.70),
	(42,7,8,4310.78),
	(43,8,1,456.44),
	(44,8,2,1004.16),
	(45,8,3,1917.03),
	(46,8,4,3286.33),
	(47,8,7,4107.92),
	(48,8,8,4564.35),
	(49,9,1,519.83),
	(50,9,2,1143.62),
	(51,9,3,2183.28),
	(52,9,4,3742.77),
	(53,9,7,4678.46),
	(54,9,8,5198.29),
	(55,10,1,608.58),
	(56,10,2,1338.88),
	(57,10,3,2556.04),
	(58,10,4,4381.78),
	(59,10,7,5477.22),
	(60,10,8,6085.80),
	(61,11,1,710.01),
	(62,11,2,1562.02),
	(63,11,3,2982.04),
	(64,11,4,5112.07),
	(65,11,7,6390.09),
	(66,11,8,7100.10),
	(67,12,1,786.08),
	(68,12,2,1729.38),
	(69,12,3,3301.55),
	(70,12,4,5659.80),
	(71,12,7,7074.75),
	(72,12,8,7860.83);

/*pasivos*/
	
INSERT INTO  montoantiguedadporcategoria(id, categoria_id, antiguedad_id, monto)
 VALUES (74, 13 , 1,181.13),
(75, 13 , 2,398.48),
(76, 13 , 3,760.73),
(77, 13 , 4,1304.10),
(78, 13 , 7,1630.13),
(79, 13 , 8,1811.25),
(80, 14 , 1,199.24),
(81, 14 , 2,438.32),
(82, 14 , 3,836.80),
(83, 14 , 4,1434.51),
(84, 14 , 7,1793.14),
(85, 14 , 8,1992.38),
(86, 15 , 1,217.35),
(87, 15 , 2,478.17),
(88, 15 , 3,912.87),
(89, 15 , 4,1564.92),
(90, 15 , 7,1956.15),
(91, 15 , 8,2173.50),
(92, 16 , 1,235.46),
(93, 16 , 2,518.02),
(94, 16 , 3,988.94),
(95, 16 , 4,1695.33),
(96, 16 , 7,2119.17),
(97, 16 , 8,2354.63),
(98, 17 , 1,262.63),
(99, 17 , 2,577.79),
(100, 17 , 3,1103.05),
(101, 17 , 4,1890.94),
(102, 17 , 7,2363.68),
(103, 17 , 8,2626.31),
(104, 18 , 1,280.74),
(105, 18 , 2,617.64),
(106, 18 , 3,1179.12),
(107, 18 , 4,2021.36),
(108, 18 , 7,2526.70),
(109, 18 , 8,2807.44),
(110, 19 , 1,307.91),
(111, 19 , 2,677.41),
(112, 19 , 3,1293.23),
(113, 19 , 4,2216.97),
(114, 19 , 7,2771.22),
(115, 19 , 8,3079.13),
(116, 20 , 1,326.03),
(117, 20 , 2,717.26),
(118, 20 , 3,1369.31),
(119, 20 , 4,2347.38),
(120, 20 , 7,2934.23),
(121, 20 , 8,3260.25),
(122, 21 , 1,371.31),
(123, 21 , 2,816.87),
(124, 21 , 3,1559.49),
(125, 21 , 4,2673.40),
(126, 21 , 7,3341.75),
(127, 21 , 8,3713.06),
(128, 22 , 1,434.70),
(129, 22 , 2,956.34),
(130, 22 , 3,1825.74),
(131, 22 , 4,3129.84),
(132, 22 , 7,3912.30),
(133, 22 , 8,4347.00),
(134, 23 , 1,507.15),
(135, 23 , 2,1115.73),
(136, 23 , 3,2130.03),
(137, 23 , 4,3651.48),
(138, 23 , 7,4564.35),
(139, 23 , 8,5071.50),
(140, 24 , 1,561.49),
(141, 24 , 2,1235.27),
(142, 24 , 3,2358.25),
(143, 24 , 4,4042.71),
(144, 24 , 7,5053.39),
(145, 24 , 8,5614.88);


INSERT INTO montopermanenciaporcategoria(id, permanencia_id, categoria_id, monto) 
VALUES  (49,1,13,18.11) ,
 (50,2,13,45.28) ,
 (51,3,13,81.51) ,
 (52,4,13,126.79) ,
 (53,1,14,18.11) ,
 (54,2,14,45.28) ,
 (55,3,14,81.50) ,
 (56,4,14,126.78) ,
 (57,1,15,18.11) ,
 (58,2,15,45.28) ,
 (59,3,15,81.51) ,
 (60,4,15,126.79) ,
 (61,1,16,27.17) ,
 (62,2,16,67.92) ,
 (63,3,16,122.26) ,
 (64,4,16,190.18) ,
 (65,1,17,18.11) ,
 (66,2,17,45.28) ,
 (67,3,17,81.51) ,
 (68,4,17,126.79) ,
 (69,1,18,27.17) ,
 (70,2,18,67.92) ,
 (71,3,18,122.26) ,
 (72,4,18,190.18) ,
 (73,1,19,18.11) ,
 (74,2,19,45.28) ,
 (75,3,19,81.50) ,
 (76,4,19,126.78) ,
 (77,1,20,45.28) ,
 (78,2,20,113.20) ,
 (79,3,20,203.76) ,
 (80,4,20,316.97) ,
 (81,1,21,63.39) ,
 (82,2,21,158.49) ,
 (83,3,21,285.27) ,
 (84,4,21,443.76) ,
 (85,1,22,72.45) ,
 (86,2,22,181.13) ,
 (87,3,22,326.03) ,
 (88,4,22,507.15) ,
 (89,1,23,54.34) ,
 (90,2,23,135.85) ,
 (91,3,23,244.52) ,
 (92,4,23,380.37) ,
 (93,1,24,0.00) ,
 (94,2,24,0.00) ,
 (95,3,24,0.00) ,
 (96,4,24,0.00) ;	
/*********/
	
INSERT INTO conceptoHaberes (id, codigo,descripcion , tipocodigo )
VALUES (1, 1, 'Básico', 'REMUNERATIVO'),
	   (2, 2, 'Permanencia en categoria', 'REMUNERATIVO'),
	   (3, 3, 'Antigüedad en categoria', 'REMUNERATIVO'),
	   (4, 4, 'Nominal - Jubilación', 'REMUNERATIVO'),
	   (5, 5, 'Nominal - Pensión', 'REMUNERATIVO');
	   
INSERT INTO conceptoprefijado (id) VALUES (1),(2),(3),(4),(5);


DELIMITER $$

DROP PROCEDURE IF EXISTS `cjpmv`.`eliminarBeneficioID`$$
CREATE PROCEDURE `cjpmv`.`eliminarBeneficioID` ()
BEGIN
  
DECLARE terminado INT DEFAULT 0;
DECLARE per_id BIGINT(20);
DECLARE bene_id BIGINT(20);

DECLARE cur CURSOR FOR 
  select id, beneficio_id from personafisica where beneficio_id is not null; 

DECLARE CONTINUE HANDLER FOR NOT FOUND SET terminado = 1;
OPEN cur;

REPEAT
	FETCH cur into per_id, bene_id;
 	UPDATE beneficio set persona_id = per_id 
	where beneficio.id = bene_id;
UNTIL terminado = 1 END REPEAT;

CLOSE cur;

END$$

DELIMITER ;

CALL eliminarBeneficioID();

drop procedure eliminarBeneficioID;

ALTER TABLE `cjpmv`.`personafisica` DROP COLUMN `antiguedadInicial_id`,
 DROP COLUMN `beneficio_id`,
 DROP FOREIGN KEY `FK11F84B238CCD4E2F`;


commit;
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;
