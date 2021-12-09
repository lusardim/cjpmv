INSERT INTO provincia 
	(id,nombre) 
VALUES  (1,'Entre Ríos');
 
INSERT INTO departamento
	(id,nombre,provincia_id)
VALUES  (1,'Victoria',1);
 
INSERT INTO localidad 
	(id,nombre,departamento_id)
VALUES  (1,'Victoria',1);

INSERT INTO banco 
	(id,nombre) 
VALUES (1,'PRUEBA');

INSERT INTO cuentabancaria
	(id,numero,cbu,saldo,tipo,banco_id)
VALUES  (1,'1','1',0,'CUENTA_CORRIENTE',1);

INSERT INTO viacobro
	(id,nombre,liquidable)
VALUES   
	(1,'prueba',1),
 	(2,'prueba2',1);

/* DATOS DE LAS PERSONAS*/

INSERT INTO tipodocumento  
	(id,descripcion)
VALUES (1,'Documento Nacional de Identidad');
 

INSERT INTO domicilio 
	(id,calle,numero,piso,
	numeroDepartamento,observacion,
	departamento_id,localidad_id)
VALUES
 (1,'Laprida',636,NULL,NULL,NULL,1,NULL),
 (2,'prueba',0,NULL,NULL,NULL,1,NULL),
 (5,'prueba1',1,NULL,NULL,NULL,1,NULL);

INSERT INTO persona 
	(id,cui,nombre,fechaNacimiento,domicilio_id)
VALUES 
 (1,30681122954,'Caja de Jubilaciones y Pensiones Municipal de Victoria',NULL,1),
 (4,0,'Prueba',NULL,2),
 (5,1,'Prueba1',NULL,5);

INSERT INTO personafisica 
	(id,apellido,legajo,fechaDefuncion,numeroDocumento,
	estadoCivil,estado,sexo,
	tipoDocumento_id) 
VALUES  
 (4,'Prueba',1,NULL,0,'SOLTERO','ACTIVO',NULL,1),
 (5,'Prueba 1',2,NULL,1,NULL,'ACTIVO',NULL,1);

INSERT INTO personajuridica 
	(id,fechaCeseActividades,estado) 
VALUES  
	(1,NULL,'ACTIVO');

INSERT INTO configuracionsistema 
(id,sistemaPorDefecto,diaVencimiento,interesPorDefecto,persona_id) 
VALUES 
 (1,'DIRECTO',5,18,1);

INSERT INTO cuentacorriente
(id,codigo,fechaCreacion,sobrante,persona_id)  
VALUES  
 (1,NULL,'2011-01-20',0,4),
 (2,NULL,'2011-01-20',0,5);

/*datos del crédito*/

INSERT INTO tipocredito 
	(id,nombre,tipoFormulario)
VALUES  (1,'prueba','F01'),
	(2,'otroTipo','F02');

/* inserto los créditos*/
INSERT INTO credito 
	(id,cantidadCuotas,montoTotal,tasa,montoEntrega,sueldoAlDia,
	fechaInicio,fechaFin,cobrarAGarante,numeroCredito,estado,tipoCredito_id,
	cuentaCorriente_id,viaCobro_id,tipoSistema,acumulativo)  
VALUES  
	(1,10,1000,0,1000,NULL,'2010-01-01',NULL,0,1,'PENDIENTE',1,1,1,'DIRECTO',0),	
 	(2,10,2000,0,2000,NULL,'2011-01-01',NULL,0,2,'PENDIENTE',1,1,1,'DIRECTO',0),
	(3,10,1000,0,1000,NULL,'2010-01-01',NULL,0,3,'PENDIENTE',1,2,1,'DIRECTO',0),
	(4,10,1000,0,1000,NULL,'2011-01-01',NULL,0,2,'PENDIENTE',1,2,2,'DIRECTO',0);	

/*CREDITO 1*/
INSERT INTO cuota 
 (id,vencimiento,interes,otrosConceptos,capital,
  numeroCuota,credito_id,cancelacion_id)
VALUES  
 (1,'2011-02-01',0,0,100,1,1,NULL),
 (2,'2011-03-01',0,0,100,2,1,NULL),
 (3,'2011-04-01',0,0,100,3,1,NULL),
 (4,'2011-05-01',0,0,100,4,1,NULL),
 (5,'2011-06-01',0,0,100,5,1,NULL),
 (6,'2011-07-01',0,0,100,6,1,NULL),
 (7,'2011-08-01',0,0,100,7,1,NULL),
 (8,'2011-09-01',0,0,100,8,1,NULL),
 (9,'2011-10-01',0,0,100,9,1,NULL),
 (10,'2011-11-01',0,0,100,10,1,NULL);

/*CREDITO 2*/
INSERT INTO cuota 
 (id,vencimiento,interes,otrosConceptos,capital,
  numeroCuota,credito_id,cancelacion_id)
VALUES
 (11,'2011-02-01',0,0,200,1,2,NULL),
 (12,'2011-03-01',0,0,200,2,2,NULL),
 (13,'2011-04-01',0,0,200,3,2,NULL),
 (14,'2011-05-01',0,0,200,4,2,NULL),
 (15,'2011-06-01',0,0,200,5,2,NULL),
 (16,'2011-07-01',0,0,200,6,2,NULL),
 (17,'2011-08-01',0,0,200,7,2,NULL),
 (18,'2011-09-01',0,0,200,8,2,NULL),
 (19,'2011-10-01',0,0,200,9,2,NULL),
 (20,'2011-11-01',0,0,200,10,2,NULL);

/*CREDITO 3*/
INSERT INTO cuota 
 (id,vencimiento,interes,otrosConceptos,capital,
  numeroCuota,credito_id,cancelacion_id)
VALUES
 (21,'2011-02-01',0,0,100,1,3,NULL),
 (22,'2011-03-01',0,0,100,2,3,NULL),
 (23,'2011-04-01',0,0,100,3,3,NULL),
 (24,'2011-05-01',0,0,100,4,3,NULL),
 (25,'2011-06-01',0,0,100,5,3,NULL),
 (26,'2011-07-01',0,0,100,6,3,NULL),
 (27,'2011-08-01',0,0,100,7,3,NULL),
 (28,'2011-09-01',0,0,100,8,3,NULL),
 (29,'2011-10-01',0,0,100,9,3,NULL),
 (30,'2011-11-01',0,0,100,10,3,NULL);

/*CREDITO 4*/
INSERT INTO cuota 
 (id,vencimiento,interes,otrosConceptos,capital,
  numeroCuota,credito_id,cancelacion_id)
VALUES
 (31,'2011-02-01',0,0,100,1,4,NULL),
 (32,'2011-03-01',0,0,100,2,4,NULL),
 (33,'2011-04-01',0,0,100,3,4,NULL),
 (34,'2011-05-01',0,0,100,4,4,NULL),
 (35,'2011-06-01',0,0,100,5,4,NULL),
 (36,'2011-07-01',0,0,100,6,4,NULL),
 (37,'2011-08-01',0,0,100,7,4,NULL),
 (38,'2011-09-01',0,0,100,8,4,NULL),
 (39,'2011-10-01',0,0,100,9,4,NULL),
 (40,'2011-11-01',0,0,100,10,4,NULL);

INSERT INTO cheque 
	(id,fechaEmision,fechaPago,monto,numero,concepto,
	estadoCheque,nombrePersona,cuenta_id,
	canceladoPor_id,emitidoA_id)
VALUES  
(1,'2011-01-01','2011-01-02',1000,1,'Pago del crédito 1','PENDIENTE_IMPRESION',NULL,1,NULL,4),
(2,'2011-01-01','2011-01-01',2000,2,'Pago del crédito 2','PENDIENTE_IMPRESION',NULL,1,NULL,4),
(3,'2011-01-01','2011-01-08',1000,3,'Pago del crédito 3','PENDIENTE_IMPRESION',NULL,1,NULL,5),
(4,'2011-01-01','2011-01-08',1000,4,'Pago del crédito 4','PENDIENTE_IMPRESION',NULL,1,NULL,5);

INSERT INTO credito_cheque 
 (Credito_id,listaCheques_id) 
VALUES  (1,1),
 (2,2),
 (3,3);

INSERT INTO telefono VALUES  (1,'424641','FIJO','03436',1);

