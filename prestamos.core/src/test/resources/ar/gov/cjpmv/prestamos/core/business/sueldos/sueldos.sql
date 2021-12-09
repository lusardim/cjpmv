INSERT INTO porcentajetipobeneficio(id, porcentaje, tipo)
VALUES (1, 0.82, 'JUBILACION_COMUN'),
       (2, 0.65, 'PENSION');
       
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
	   
--escala salarial 2011
INSERT INTO categoria(id, numero, nombre, porcentaje, monto, porcentajesobre_id, tipoPersona)
VALUES (1, 1, 'Categoria', null, 2535.75, null, 'ACTIVO'),
	   (2, 2, 'Categoria', 0.10, null, 1, 'ACTIVO'),
	   (3, 3, 'Categoria', 0.20, null, 1, 'ACTIVO'),
	   (4, 4, 'Categoria', 0.30, null, 1, 'ACTIVO'),
	   (5, 5, 'Categoria', 0.45, null, 1, 'ACTIVO'),
	   (6, 6, 'Categoria', 0.55, null, 1, 'ACTIVO'),
	   (7, 7, 'Categoria', 0.70, null, 1, 'ACTIVO'),
	   (8, 8, 'Categoria', 0.80, null, 1, 'ACTIVO'),
	   (9, 9, 'Categoria', 1.05, null, 1, 'ACTIVO'),
	   (10, 10, 'Categoria', 1.40, null, 1, 'ACTIVO'),
	   (11, 11, 'Categoria', 1.80, null, 1, 'ACTIVO'),
	   (12, 12, 'Categoria', 2.10, null, 1, 'ACTIVO');
	   
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
		(19, 3,5,114.11), 
		(20, 4,5,177.50), 
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
	(19,4,1,329.65),
	(20,4,2,725.23),
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
	
	
-- empleado prueba (id=4) que trabaja en la caja (id = 1) tiene 5-10 años de antiguedad (id = 2)
-- ha permanecido en la categoría por 4-6 años (id=2)y tiene categoria 3 (id=3)
INSERT INTO empleo(id, fechaInicio, situacionRevista, categoria_id, permanencia_id, antiguedad_id, empleado_id, empresa_id )
	VALUES (1, '1980-01-01', 'PLANTA_PERMANENTE', 3, 2, 2, 4, 1);

	
-- concepto fijo para pruebas
INSERT INTO conceptohaberes	(id, codigo, descripcion, tipocodigo)
	VALUES (1, 1, 'Concepto fijo prueba', 'REMUNERATIVO'), 
		   (99, 2, 'concepto no asociado a nada',  'REMUNERATIVO' );
	
INSERT INTO conceptofijo (id,  sobreescribirValor) 
	VALUES (1, 0), 
	       (99, 0);

-- agregamos una plantilla	
INSERT INTO plantilla(id, tipoLiquidacion)	
	VALUES (1, 'ACTIVO_NORMAL');
	
INSERT INTO conceptohaberes_plantilla(conceptohaberes_id, plantilla_id)
	VALUES (1,1);
-- una liquidación de prueba con un valor fijo
INSERT INTO liquidacionhaberes(id, mes, anio, plantilla_id, tipo)
	VALUES (1, 1, 1900, 1, 'ACTIVO_NORMAL');

INSERT INTO recibosueldo(id, liquidacion_id, persona_id)
	VALUES (1,1,4);
	
INSERT INTO linearecibo (id, cantidad, monto, concepto_id, recibosueldo_id)
	VALUES (1,1,50.50, 1,1);

--otra liquidación más vieja	
INSERT INTO liquidacionhaberes(id, mes, anio, plantilla_id, tipo)
	VALUES (2, 1, 1800, 1, 'ACTIVO_NORMAL');

INSERT INTO recibosueldo(id, liquidacion_id, persona_id)
	VALUES (2,2,4);
	
INSERT INTO linearecibo (id, cantidad, monto, concepto_id, recibosueldo_id)
	VALUES (2,27,70.77, 1,2);