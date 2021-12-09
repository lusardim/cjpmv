package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.gui.reportes.LiquidacionMunicipalidadImpresion;

public class ImpresionPlanillaLiquidacionMuni {
	
	private List<DetalleLiquidacion> listaDetalleLiquidacion;
	private Liquidacion liquidacion;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private DateFormat formateadorFecha;
	private BigDecimal montoTotalLiquidar;
	private Integer cantidadRegistros;
	
	
	public ImpresionPlanillaLiquidacionMuni(
			List<DetalleLiquidacion> listaDetalleLiquidacion,
			Liquidacion liquidacion) {
		this.listaDetalleLiquidacion = listaDetalleLiquidacion;
		this.liquidacion = liquidacion;
		this.formateadorFecha= new SimpleDateFormat("MMMMM 'de' yyyy");
		this.cantidadRegistros= 0;
		this.montoTotalLiquidar= new BigDecimal(0);
	}


	public List<LiquidacionMunicipalidadImpresion> formatearReporte() {
		List<LiquidacionMunicipalidadImpresion> locDetalleLiquidacion = new ArrayList<LiquidacionMunicipalidadImpresion>(); 
		this.cantidadRegistros= this.listaDetalleLiquidacion.size();
		for(DetalleLiquidacion cadaDetalle: this.listaDetalleLiquidacion){
			String locCuil="";
			if(cadaDetalle.getPersona().getCui()!=null){
				locCuil= cadaDetalle.getPersona().getCui().toString();
			}
			PersonaFisica locPersona= (PersonaFisica) cadaDetalle.getPersona();
			String locNumeroLegajo="";
			if(locPersona.getLegajo()!=null){
				locNumeroLegajo=  locPersona.getLegajo().toString();
			}
			String locApellido= locPersona.getApellido();
			String locNombres= locPersona.getNombre();
			String locMontoLiquidar= this.formateadorMonetario.format(cadaDetalle.getMonto());
			this.montoTotalLiquidar = montoTotalLiquidar.add(cadaDetalle.getMonto());
			locDetalleLiquidacion.add(new LiquidacionMunicipalidadImpresion(locCuil, locNumeroLegajo, locApellido, locNombres, locMontoLiquidar));
		}
		return  locDetalleLiquidacion;
	}

	public Map<String, String> setearParametros(){
		this.formatearReporte();
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("mesAnioLiquidacion", this.formateadorFecha.format(this.liquidacion.getFechaLiquidacion()).toUpperCase());
		parametros.put("totalRegistros", this.cantidadRegistros.toString());
		parametros.put("montoTotalLiquidar", this.formateadorMonetario.format(this.montoTotalLiquidar));
		return parametros;		
	}
	

}
