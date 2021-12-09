package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPropietaria;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CreditoModel;
import ar.gov.cjpmv.prestamos.gui.reportes.CuotaOtorgamiento;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class ImpresionOtorgamientoCredito {
	
	

	private List<CuotaOtorgamiento> listaCuotaOtorgamiento;
	private CreditoModel modelo;
	private String montoTotalAPagar;
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();

	
	
	
	public ImpresionOtorgamientoCredito(CreditoModel modelo) {
		super();
		this.modelo = modelo;
		this.formateadorDecimal.setMinimumFractionDigits(2);
		this.formateadorDecimal.setMaximumFractionDigits(2);
	}

	

	public Map<String, String> setearParametros(){
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("numeroCredito", this.modelo.getCredito().getNumeroCredito().toString());
		parametros.put("montoTotal", this.formateadorMonetario.format(this.modelo.getCredito().getMontoTotal()).toString());
		parametros.put("interes", this.formateadorDecimal.format(this.modelo.getCredito().getTasa()).toString()+"%");
		if(this.modelo.getCredito().getViaCobro()!=null){
			parametros.put("viaCobro", this.modelo.getCredito().getViaCobro().toString());
		}
		else{
			parametros.put("viaCobro", null);
		}
		parametros.put("tipoCredito", this.modelo.getCredito().getTipoCredito().toString());
		parametros.put("cantidadCuotas", this.modelo.getCredito().getCantidadCuotas().toString());
		parametros.put("fechaInicio", this.formateadorFecha.format(this.modelo.getCredito().getFechaInicio()));
		parametros.put("sistema", this.modelo.getSistema().toString());
		parametros.put("montoEntrega", this.formateadorMonetario.format(this.modelo.getCredito().getMontoEntrega()));
		parametros.put("montoAPagar", this.montoTotalAPagar);
		if(this.modelo.getPersona() instanceof PersonaFisica){
			PersonaFisica locPersonaFisica= (PersonaFisica)this.modelo.getPersona();
			parametros.put("legajo", locPersonaFisica.getLegajo().toString());
			parametros.put("estado", locPersonaFisica.getEstado().toString());
			parametros.put("numeroDocumento", locPersonaFisica.getNumeroDocumento().toString());
			parametros.put("tipoDocumento", locPersonaFisica.getTipoDocumento().getDescripcion());
		}
		else{
			parametros.put("legajo", null);
			parametros.put("estado", null);
			parametros.put("numeroDocumento", null);
			parametros.put("tipoDocumento", null);
		}
		if(this.modelo.getPersona()!=null){
			parametros.put("nombreApellido", this.modelo.getPersona().getNombreYApellido());
		}
		else{
			parametros.put("nombreApellido", null);
		}
		return parametros;	
	}


	public List<CuotaOtorgamiento> formatearCuotasOrganismo() {
		this.listaCuotaOtorgamiento= new ArrayList<CuotaOtorgamiento>();
		String locNumeroCuota= " ";
		String locVencimiento= " ";
		String locOtrosConceptos= " ";
		String locInteres= " ";
		String locCapital= " ";
		String locValorTotal= " ";
		BigDecimal acumulador = new BigDecimal(0);
	
		for(Cuota cadaCuota: this.modelo.getCredito().getListaCuotas()){
			locNumeroCuota=(cadaCuota.getNumeroCuota()!=null)?cadaCuota.getNumeroCuota().toString():" ";
			locVencimiento= (cadaCuota.getVencimiento()!=null)?this.formateadorFecha.format(cadaCuota.getVencimiento()):" ";
			locOtrosConceptos= (cadaCuota.getOtrosConceptos()!=null)?this.formateadorMonetario.format(cadaCuota.getOtrosConceptos()): " ";
			locInteres= (cadaCuota.getInteres()!=null)?this.formateadorMonetario.format(cadaCuota.getInteres()):" ";
			locCapital= (cadaCuota.getCapital()!=null)?this.formateadorMonetario.format(cadaCuota.getCapital()):" ";
			locValorTotal= (cadaCuota.getTotal()!=null)?this.formateadorMonetario.format(cadaCuota.getTotal()): " ";
			this.listaCuotaOtorgamiento.add(new CuotaOtorgamiento(locNumeroCuota, locVencimiento, locOtrosConceptos, locInteres, locCapital, locValorTotal));
			acumulador = acumulador.add(cadaCuota.getTotal());
		}
		
		this.montoTotalAPagar=this.formateadorMonetario.format(acumulador);
		return this.listaCuotaOtorgamiento;
	}
	
	
}
