UPDATE cuota set cuota.cancelacion_id = null;
--UPDATE cobroporcancelacioncredito set detalleCreditoCancelaciones_id = null  

DELETE FROM detallecobroliquidacion_detalleliquidacion;
DELETE FROM detallecobroliquidacion;
DELETE FROM cobroliquidacion;
DELETE FROM cancelacion;
DELETE FROM cobroporcancelacioncredito;
DELETE FROM cobroporbanco;
DELETE FROM cobro;
DELETE FROM detalleliquidacion_cuota;
DELETE FROM detalleliquidacion;
DELETE FROM liquidacion;
DELETE FROM credito_cheque;
DELETE FROM cheque;
DELETE FROM telefono;
DELETE FROM cuentabancaria; 
DELETE FROM banco;
DELETE FROM cuota;
DELETE FROM cancelacion;
DELETE FROM credito_garantia;
DELETE FROM garantia;
DELETE FROM detallecredito;
DELETE FROM credito;
DELETE FROM viacobro;
DELETE FROM tipocredito; 
DELETE FROM personafisica;
DELETE FROM configuracionsistema;
DELETE FROM personajuridica;
DELETE FROM cuentacorriente; 
DELETE FROM persona_emails;
DELETE FROM persona;
DELETE FROM domicilio;
DELETE FROM localidad;
DELETE FROM departamento;
DELETE FROM provincia;
DELETE FROM tipodocumento;

