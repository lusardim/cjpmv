package ar.gov.cjpmv.prestamos.core.business.exceptions;

public class LogicaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3270840138229590603L;
	private int codigoMensaje;
	private String campoMensaje;
	
	public LogicaException(int codigo) {
		this(codigo, null);
	}
	
	public LogicaException(int pCodigoMensaje, String pCampoMensaje) {
		this.codigoMensaje = pCodigoMensaje;
		if (pCampoMensaje != null) {
			this.campoMensaje = pCampoMensaje;
		}
		else {
			campoMensaje = "";
		}
	}
	
	public String getMessage(){
		String mensaje = "Mensaje";
		switch (this.codigoMensaje){
			case 1:{
				mensaje = campoMensaje+" que desea ingresar ya existe.";
				break;
			}
			case 2:{
				mensaje = "El campo "+campoMensaje+" se encuentra vacío. Ingrese datos.";
				break;
			}
			case 3:{
				mensaje = "Ocurrió un error inesperado. Consulte con el administrador del sistema.";
				break;
			}
			case 4:{
				mensaje = "El texto: ["+campoMensaje+"] no se corresponde a un correo electrónico válido";
				break;
			}
			case 5:{
				mensaje = "Debe seleccionar un "+campoMensaje;
				break;
			}
			case 6:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociado a un domicilio.";
				break;
			}
			case 7:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociado a una localidad.";
				break;
			}
			case 8:{
				mensaje = "El sistema no ha sido registrado. Consulte con el administrador del sistema.";
				break;
			}
			case 9:{
				mensaje = "La "+campoMensaje+" no puede estar vacía";
				break;
			}
			case 10:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociado a una persona física.";
				break;
			}
			case 11:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociado a una persona física.";
				break;
			}
			case 12:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociada a una persona física.";
				break;
			}
			case 13:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociada a un departamento.";
				break;
			}
			case 14:{
				mensaje = "El campo usuario o contraseña se encuentran vacíos. Ingrese datos.";
				break;
			}		
			case 15:{
				mensaje = campoMensaje+" no son correctos. Reingrese datos.";
				break;
			}	
			case 16:{
				mensaje = "La registración o las opciones bàsicas no se han configurado en el sistema";
				break;
			}
			case 17:{
				mensaje = "Dato ingresado no válido. "+campoMensaje+" es númerico.";
				break;
			}
			case 18:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociada a una cuenta bancaria.";
				break;
			}
			case 19:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociado a un cobro por banco.";
				break;
			}
			case 20:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociado a un cheque.";
				break;
			}
			case 21:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociado a un detalle de crédito.";
				break;
			}
			case 22:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociado a un tipo de crédito.";
				break;
			}
			case 23:{
				mensaje = campoMensaje+" no se pudo eliminar porque se encuentra asociado a un crédito.";
				break;
			}
			case 24: {
				mensaje = "La persona que intenta eliminar pertenece a las siguientes instituciones: "+campoMensaje +
						"Debe eliminarla de las sociedades para poder continuar";
				break;
			}
			case 25: {
				mensaje = campoMensaje;
				break;
			}
			case 26:{
				mensaje = "La institución que intenta eliminar tiene los empleados registrados: "+campoMensaje +
							"Debe eliminarla de las sociedades para poder continuar";
				break;
			}
			case 27:{
				mensaje = "Formato de dato inválido para el "+campoMensaje+"."+"\nCompruebe longitud de cadena.";
				break;
			}
			case 28: {
				mensaje = "La persona jurídica que desea eliminar está asociada a la configuración del sistema.";
				break;
			}
			case 29: {
				mensaje = "No se han registrados créditos ni garantías a nombre de esta persona.";
				break;
			}
			case 30:{
				mensaje = "La persona ya tiene una cuenta corriente asociada.";
				break;
			}
			case 31:{
				mensaje = "El crédito no posee una cuenta corriente asociada.";
				break;
			}
			case 32:{
				mensaje = "No se ha podido generar la cuenta corriente";
				break;
			}
			case 33:{
				mensaje = "<html>Los siguientes elementos deberían contener datos válidos: "+campoMensaje+" </html>";
				break;
			}
			case 34:{
				mensaje = "Debe seleccionar una persona para realizar el crédito.";
				break;
			}
			case 35: {
				mensaje = "Hay otro crédito con el mismo número registrado: "+campoMensaje;
				break;
			}
			case 36: {
				mensaje = campoMensaje;
				break;
			}
			case 37: {
				mensaje = "Una persona con "+campoMensaje+" no puede tener el estado ACTIVO.";
				break;
			}
			case 38: {
				mensaje = "Una persona con "+campoMensaje+" no puede tener el estado PASIVO.";
				break;
			}
			case 39: {
				mensaje = "Faltan datos requeridos de la garantía propietaria. Verifique los datos ingresados.";
				break;
			}
			case 40: {
				mensaje = "El "+campoMensaje+" debe contener una cantidad de dinero, verifique el texto ingresado.";
				break;
			}
			case 41: {
				mensaje = "Debe seleccionar la persona a la cual está destinada el crédito"; 
				break;
			}
			case 42:{
				mensaje = "No ha sido seleccionado el destinatario del crédito. Seleccione una persona e intente nuevamente.";
				break;
			}
			case 43: {
				mensaje = "Un cheque no puede autocancelarse, verifique los datos ingresados o comuníquese con el administrador.";
				break;
			}
			case 44:{
				mensaje = "El monto de un cheque debe ser un valor positivo mayor a cero.";
				break;
			}
			case 45:{
				mensaje = "El cheque debe estar emitido a nombre de alguien. Ingrese el nombre de la persona o seleccione una utilizando el botón seleccionar";
				break;
			}
			case 46:{
				mensaje = "Para imprimir un cheque debe tener fecha de emisión y de pago.";
				break;
			}
			case 47:{
				mensaje = "Debe cargar un número de cheque";
				break;
			}
			case 48:{
				mensaje = "La fecha de emisión no puede ser posterior a la fecha de pago.";
				break;
			}
			case 49:{
				mensaje = "Debe seleccionar una cuenta bancaria asociada al cheque número: "+campoMensaje;
				break;
			}
			case 50:{
				mensaje="No es posible eliminar el usuario seleccionado.";
				break;
			}
			case 51:{
				mensaje="El archivo de reporte no se encuentra disponible";
				break;
			}
			case 52:{
				mensaje="Las contraseñas ingresadas no coinciden.";
				break;
			}
			case 53:{
				mensaje="Debe ingresar algún dato específico para realizar la búsquedas de cuotas a cobrar.";
				break;
			}
			case 54:{
				mensaje="No se ha registrado ninguna persona con los datos ingresados. Verifique los datos.";
				break;
			}
			case 55:{
				mensaje="No se han encontrado cuotas a cobrar que se correspondan con los datos ingresados.";
				break;
			}
			case 56:{
				mensaje="Debe indicar el estado (Activo-Pasivo) de la persona para realizar la búsqueda por Nº de Legajo.";
				break;
			}
			case 57:{
				mensaje="No se han registrado personas con los datos ingresados en la búsqueda";
				break;
			}
			case 58:{
				mensaje="Debe buscar y seleccionar al menos una cuota.";
				break;
			}
			case 59:{
				mensaje="No se han agregado cuotas a cobrar. Seleccione cuotas.";
				break;
			}
			case 60:{
				mensaje = "El período seleccionado ya se ha liquidado con antelación.";
				break;
			}
			case 61:{
				mensaje = "Ha ocurrido un error al guardar la liquidación";
				break;
			}
			case 62:{ 
				mensaje = "La liquidación no ha generado cuotas";
				break;
			}
			case 63:{
				mensaje = "El archivo a generar no puede estar vacío";
				break;
			}
			case 64:{
				mensaje = "El período de liquidación que intenta procesar no existe o ya ha sido cobrado.";
				break;
			}
			case 65: {
				mensaje = "Ha ocurrido un error al procesar el archivo. Se ha enviado un dato inválido \n"+
						  "en el campo "+campoMensaje+".";
				break;
			}
			case 66:{
				mensaje="Ha ocurrido un error al leer el archivo. Se ha enviado una persona \n"+campoMensaje;
				break;
			}
			case 67:{
				mensaje="Ha ocurrido un error al leer el archivo. " ;
				break;
			}
			case 68:{
				mensaje="No se ha podido realizar el cobro";
				break;
			}
			case 69:{
				mensaje="No se ha podido liquidar";
				break;
			}
			case 70: {
				mensaje="Debe seleccionar un cheque que se encuentre pendiente de impresión o impreso para cancelarlo";
				break;
			}
			case 71:{
				mensaje="Error. Debe ingresar el número de crédito que desea buscar.";
				break;
			}
			case 72:{
				mensaje="Error. Debe ingresar la cantidad de cuotas adeudadas vencidas para la búsqueda.";
				break;
			}
			case 73:{
				mensaje="Error. Debe seleccionar algún criterio para realizar la búsqueda.";
				break;
			}
			case 74:{
				mensaje="Error. Debe ingresar un número entero en el campo número de crédito.";
				break;
			}
			case 75:{
				mensaje="El cheque no ha podido ser impreso, verifique que la impresora se encuentre encendida y preparada para imprimir.";
				break;
			}
			case 76: {
				mensaje="No se ha podido actualizar el cheque, intente nuevamente más tarde";
				break;
			}
			case 77: {
				mensaje="El cheque ha sido reemplazado por el cheque "+campoMensaje+" debe utilizar dicho cheque o cancelarlo.";
				break;
			}
			case 78:{
				mensaje="Debe ingresar un porcentaje de afectación mayor a cero y una vía de cobro\npara el garante con "+campoMensaje+".";
				break;
			}
			case 79:{
				mensaje="La suma total de los porcentajes para la afectación de garantías no debe ser superior a 100.";
				break;
			}
			case 80:{
				mensaje="Todos los creditos deben pertenecer a la misma persona"; 
				break;
			}
			case 81:{
				mensaje="Debe ingresar algún dato para realizar la búsqueda."; 
				break;
			}
			case 82:{
				mensaje = "La fecha del primer vencimiento no puede ser anterior a la fecha de inicio del crédito";
				break;
			}
			case 90: {
				mensaje = "No se permite eliminar el crédito seleccionado debido a que tiene al menos una cuota cancelada.";
				break;
			}
			case 91: {
				mensaje = "Error al intentar generar el reporte. Debe ingresar los datos solicitados.";
				break;
			}
			case 92: {
				mensaje = "El monto total del crédito debe superar el valor de los descuentos";
				break;
			}
			case 93: {
				mensaje = "Error al intentar generar el reporte. El campo Fecha Hasta es obligatorio.";
				break;
			}
			case 94: {
				mensaje = "Ha ocurrido un error al intentar ingresar al sistema, intente nuevamente más tarde";
				break;
			}
			case 95: {
				mensaje = "No se ha podido reemplazar el detalle de liquidación";
				break;
			}
			case 96: {
				mensaje = "Debe seleccionar el crédito que desea imprimir. ";
				break;
			}
			case 97: {
				mensaje = "Debe seleccionar los reportes que desea imprimir.";
				break;
			}
			case 98: {
				mensaje = "El concepto Impuesto a los sellos ha sido modificado. Consulte con el administrador del sistema.";
				break;
			}
			case 99: {
				mensaje = "El concepto Fondo de Quebranto ha sido modificado. Consulte con el administrador del sistema.";
				break;
			}
			case 100: {
				mensaje = "Error al intentar generar el reporte. Los campos 'Fecha Desde' y 'Fecha Hasta' son obligatorios.";
				break;
			}
			case 101: {
				mensaje = "Error en el período seleccionado. 'Fecha Desde' debe ser menor a 'Fecha Hasta'.";
				break;
			}
			case 102: {
				mensaje = "No se ha podido cobrar la lista de cuotas";
				break;
			}
			case 103: {
				mensaje = "No se ha podido actualizar el sobrante de la cuenta corriente id: "+campoMensaje;
				break;
			}
			case 104: {
				mensaje = "No se ha podido realizar el cobro de la liquidación.";
				break;
			}
			case 105:{
				mensaje="No se han registrado cobro para el período y vía seleccionados.";
				break;
			}
			case 106: {
				mensaje = "No se ha podido realizar el cobro de liquidación.";
				break;
			}
			case 107:{
				mensaje="Debe ingresar una fecha de cobro para el cálculo del monto total de las cuotas.";
				break;
			}
			case 108: {
				mensaje = "No hay liquidaciones a la fecha"; 
				break;
			}
			case 109: {
				mensaje = "Debe ingresar una fecha para visualizar el reporte.";
				break;
			}
			case 110:{
				mensaje= "Error. El campo nombre debe contener un número entero de cuatro dígitos" + campoMensaje;
				break;
			}
			case 111:{
				mensaje= "Error. Debe ingresar Fecha Desde y Fecha Hasta para realizar la consulta de cobros por banco.";
				break;
			}
			case 112:{
				mensaje = "Error. No se encontraron resultados en la búsqueda.";
				break;
			}
			case 113:{
				mensaje = "Error. Fecha Desde debe ser menor a Fecha Hasta.";
				break;
			}
			case 114:{
				mensaje= "Error en el campo " + campoMensaje + ". Este dato es obligatorio.";
				break;
			}
			case 115:{
				mensaje="Error en las fechas ingresadas. La fecha de inicio debe ser menor a la fecha de fin.";
				break;
			}
			case 116:{
				mensaje="Error en los datos ingresados. Ya existe un "+campoMensaje+" con los mismos datos.";
				break;
			}
			case 117:{
				mensaje="Error al intentar eliminar. No es posible eliminar el ejercicio contable porque tiene asociado un libro diario.";
				break;
			}
			case 118: {
				mensaje = "El anio debe estar entre 1800 y 2100";
				break;
			}
			case 119: {
				mensaje = "Error al intentar activar el ejercicio contable. Consulte con el administrador del sistema.";
				break;
			}
			case 120:{
				mensaje = "Error en el campo "+campoMensaje+". Debe ingresar un número entero positivo.";
				break;
			}
			case 121:{
				mensaje = "Error. De ingresar un periodo cerrado para consultar.\nVerique que los campos fecha desde y fecha hasta enten cargados.";
				break;
			}
			case 122: {
				mensaje = "No hay ningún ejercicio contable activo.";
				break;
			}
			case 123: {
				mensaje = "La cuenta "+campoMensaje+" ya se encuentra en el asiento";
				break;
			}
			case 124: {
				mensaje = "No hay ningún ejercicio contable activo";
				break;
			}
			case 125: {
				mensaje = "La cuenta "+campoMensaje+" tiene asientos registrados y no puede ser eliminada";
				break;
			}
			case 126: {
				mensaje = "El número de asiento no puede ser nulo.";
				break;
			}
			case 127: {
				mensaje = "El número de asiento ingresado ya existe.";
				break;
			}
			case 128: {
				mensaje = "El asiento debe contener al menos dos movimientos.";
				break;
			}
			case 129: {
				mensaje = "El asiento debe estar balanceado.";
				break;
			}
			case 130: {
				mensaje = "Faltan datos requeridos "+campoMensaje;
				break;
			}
			case 131: {
				mensaje = "Faltan datos requeridos "+campoMensaje;
				break;
			}
			case 132: {
				mensaje = "Faltan datos requeridos. Por favor complete el campo"+campoMensaje+".";
				break;
			}
			case 133: {
				mensaje = "Los conceptos prefijados no pueden modificarse.";
				break;
			}
			case 134: {
				mensaje = "El concepto tiene los siguientes errores: "+campoMensaje;
				break;
			}
			case 135:{
				mensaje = "Error al intentar guardar. Debe seleccionar una persona juridica en la pestaña datos laborales";
				break;
			}
			case 136:{
				mensaje = "Error al intentar guardar. Debe seleccionar una categoria en la pestaña datos laborales";
				break;
			}
			case 137:{
				mensaje = "Error al intentar guardar. El campo número de documento en la pestaña datos personales no tiene un formato válido ";
				break;
			}
			case 138: {
				mensaje = "El código "+campoMensaje+" está ocupado por un concepto prefijado";
				break;
			}
			case 139: {
				mensaje = "Error al intentar guardar. El campo "+campoMensaje+" es requerido. Ingrese el dato.";;
				break;
			}
			case 140: {
				mensaje = "Error al intentar guardar. El campo "+campoMensaje+" no tiene un valor o formato válido.";
				break;
			}
			case 141: {
				mensaje = "Error al guardar, la plantilla debe ser válida";
				break;
			}
			case 142: {
				mensaje = "La plantilla contiene los siguientes errores: "+campoMensaje;
				break;
			}
			case 143: {
				mensaje = "Error al intentar guardar. La persona ya posee un beneficio de este tipo.";
				break;
			}
			case 144: {
				mensaje = "No es posible eliminar el beneficio ya que este ha sido liquidado.";
				break;
			}
			case 145: {
				mensaje = "Ya existe una liquidación de ese tipo en el mismo período.";
				break;
			}
			case 146: {
				mensaje = "Debe crear una plantilla para generar una liquidación del tipo "+campoMensaje;
				break;
			}
			case 147: {
				mensaje = campoMensaje;
				break;
			}
			case 148: {
				mensaje = "El porcentaje aplicado al tipo de beneficio debe ser un valor mayor a 0";
				break;
			}
			case 149: {
				mensaje = "La categoria "+campoMensaje+" debe tener un valor mayor a cero";
				break;
			}
			case 150: {
				mensaje = "Ha ocurrido un error al intentar guardar las categorias";
				break;
			}

		}
		return mensaje;
	}
	
	public String getTitulo(){
		String titulo="Titulo";
		switch (this.codigoMensaje){
			case 1:{
				titulo="Error: "+campoMensaje+" repetido";
				break;
			}
			case 2:{
				titulo="Error: campo "+campoMensaje+" obligatorio";
				break;
			}
			case 3:{
				titulo="Error: Administración del sistema";
				break;
			}
			case 6:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 7:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 8:{
				titulo="Error en la Registracion del Sitema";
				break;
			}
			case 9:{
				titulo="Error: campo "+campoMensaje+" obligatorio";
				break;
			}
			case 10:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 11:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 12:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 13:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 14:{
				titulo="Error: usuario y contraseña son obligatorios";
				break;
			}
			case 15:{
				titulo="Error: Acceso denegado";
				break;
			}
			case 16:{
				titulo="Error en la registración";
				break;
			}
			case 17:{
				titulo="Error: Formato de dato incorrecto";
			}
			case 18:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 19:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 20:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 21:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 22:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 23:{
				titulo="Error: Eliminación denegada";
				break;
			}
			case 24: {
				titulo = "Error: Eliminación denegada"; 
				break;
			}
			case 25: {
				titulo = "Error: Datos repetidos"; 
				break;
			}
			case 26: {
				titulo = "Error: Eliminación denegada"; 
				break;
			}
			case 27: {
				titulo="Error: Formato de dato incorrecto";
				break;
			}
			case 28: {
				titulo="Error";
				break;
			}
			case 29: {
				titulo="Error";
				break;
			}
			case 30: {
				titulo = "Error";
				break;
			}
			case 36: {
				titulo = "Error";
				break;
			}
			case 37: {
				titulo = "Error: Datos incorrectos";
				break;
			}
			case 38: {
				titulo = "Error: Datos incorrectos";
				break;
			}
			case 50: {
				titulo = "Error: Eliminación denegada";
				break;
			}
			case 51: {
				titulo = "Error: Archivo de Impresión";
				break;
			}
			case 52: {
				titulo = "Error";
				break;
			}
			case 53: {
				titulo = "Error";
				break;
			}
			case 54: {
				titulo = "Error";
				break;
			}
			case 55: {
				titulo = "Error";
				break;
			}
			case 56: {
				titulo = "Error";
				break;
			}
			case 57:{
				titulo ="Error";
				break;
			}
			case 58:{
				titulo="Error";
				break;
			}
			case 59:{
				titulo="Error";
				break;
			}
			case 64:{
				titulo="Error";
				break;
			}
			case 65:{
				titulo="Error";
				break;
			}
			case 66:{
				titulo="Error";
				break;
			}
			case 67:{
				titulo="Error";
				break;
			}
			case 96:{
				titulo="Error";
				break;
			}
			case 97:{
				titulo="Error";
				break;
			}
			case 98:{
				titulo="Error";
				break;
			}
			case 99:{
				titulo="Error";
				break;
			}
			case 100:{
				titulo="Error";
				break;
			}
			case 101:{
				titulo="Error";
				break;
			}
			case 105:{
				titulo="Error";
				break;
			}
			case 107:{
				titulo="Error";
				break;
			}
			case 108:{
				titulo="Error";
				break;
			}
			case 109:{
				titulo="Error";
				break;
			}
			case 110:{
				titulo="Error";
				break;
			}
			case 111:{
				titulo="Error";
				break;
			}
			case 112:{
				titulo="Error";
				break;
			}
			case 113:{
				titulo="Error";
				break;
			}
			case 114:{
				titulo="Error";
				break;
			}
			case 115:{
				titulo="Error";
				break;
			}
			case 116:{
				titulo="Error";
				break;
			}
			case 117:{
				titulo="Error";
				break;
			}
			case 118:{
				titulo="Error";
				break;
			}
			case 119:{
				titulo="Error";
				break;
			}
			case 120:{
				titulo="Error";
				break;
			}
			case 121:{
				titulo="Error";
				break;
			}
			case 132:{
				titulo="Error";
				break;
			}
			case 135:{
				titulo="Error";
				break;
			}
			case 136:{
				titulo="Error";
				break;
			}
			case 137:{
				titulo="Error";
				break;
			}
			case 139:{
				titulo="Error";
				break;
			}
			case 140:{
				titulo="Error";
				break;
			}
			case 141:{
				titulo="Error";
				break;
			}
			case 142:{
				titulo="Error";
				break;
			}
			case 143:{
				titulo="Error";
				break;
			}
			case 144:{
				titulo="Error";
				break;
			}
			
		}
		return titulo;
	}

	public int getCodigoMensaje() {
		return codigoMensaje;
	}

	
	
}
