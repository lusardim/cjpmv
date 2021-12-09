insert into ejerciciocontable 
	(id, fechaInicio, fechaFin, activo, anio)
values 
	(1, '2011-01-01', '2011-12-31',0, 2011), 
	(2, '2010-01-01', '2010-12-31',0, 2010);
	
insert into planCuenta
	(id, cerrado, periodo_id)
values
	(1,0, 1);
	
insert into cuenta
 (id, codigo, nombre, recibeAsiento, abreviatura, saldoInicial, tipoCuenta, tipoSaldo, sumarizaEn_id, padre_id, planCuenta_id)
VALUES  
 (1,'00','Cuenta 1',1,'c1',5000.00,'ACTIVO','DEUDOR',NULL,NULL,1),
 (2,'22','Cuenta 2',0,'c2',22.00,'ACTIVO','ACREEDOR',1,1,NULL);
