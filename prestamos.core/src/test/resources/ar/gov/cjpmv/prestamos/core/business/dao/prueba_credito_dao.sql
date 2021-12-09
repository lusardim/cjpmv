
INSERT INTO domicilio 
	(id,calle,numero,piso,
	numeroDepartamento,observacion,
	departamento_id,localidad_id)
VALUES
 (10,'Laprida',636,NULL,NULL,NULL,1,NULL);
 
INSERT INTO persona 
	(id,cui,nombre,fechaNacimiento,domicilio_id)
VALUES 
 (10,0,'PruebaCreditoDAO',NULL,10);

INSERT INTO personafisica 
	(id,apellido,legajo,fechaDefuncion,numeroDocumento,
	estadoCivil,estado,sexo,
	tipoDocumento_id) 
VALUES  
 (10,'PruebaCreditoDAO',10,NULL,0,'SOLTERO','ACTIVO',NULL,1);
 
INSERT INTO cuentacorriente
	(id,codigo,fechaCreacion,sobrante,persona_id)  
VALUES  
	(10,NULL,'2010-01-01',0,10);
	
/* inserto los créditos*/
INSERT INTO credito 
	(id,cantidadCuotas,montoTotal,tasa,montoEntrega,sueldoAlDia,
	fechaInicio,fechaFin,cobrarAGarante,numeroCredito,estado,tipoCredito_id,
	cuentaCorriente_id,viaCobro_id,tipoSistema,acumulativo)  
VALUES  
	(10,3,45,0,45,NULL,'2011-01-01',NULL,0,10,'PENDIENTE',1,10,1,'DIRECTO',0),	
	(11,3,30,0,30,NULL,'2011-02-01',NULL,0,11,'PENDIENTE',1,10,2,'DIRECTO',0);
	
/*CREDITO 10*/
INSERT INTO cuota 
 (id,vencimiento,interes,otrosConceptos,capital,
  numeroCuota,credito_id,cancelacion_id)
VALUES  
 (100,'2011-01-05',0,0,15,1,10,NULL),
 (101,'2011-02-05',0,0,15,2,10,NULL),
 (102,'2011-03-05',0,0,15,3,10,NULL);
	
/*CREDITO 11*/
INSERT INTO cuota 
 (id,vencimiento,interes,otrosConceptos,capital,
  numeroCuota,credito_id,cancelacion_id)
VALUES  
 (110,'2011-02-06',0,0,10,1,11,NULL),
 (111,'2011-03-06',0,0,10,2,11,NULL),
 (112,'2011-04-06',0,0,10,3,11,NULL);	


