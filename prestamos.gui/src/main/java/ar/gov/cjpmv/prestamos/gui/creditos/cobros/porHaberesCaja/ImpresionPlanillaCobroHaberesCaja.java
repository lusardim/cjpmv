package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porHaberesCaja;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.gui.reportes.LiquidacionMunicipalidadImpresion;

public class ImpresionPlanillaCobroHaberesCaja {

	private List<DetalleLiquidacion> listaModelo;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private DateFormat formateadorFecha;
	private BigDecimal montoTotalLiquidar;
	private Integer cantidadRegistros;
	private Integer anio;
	private Integer mes;
	
	
	public ImpresionPlanillaCobroHaberesCaja(
			List<DetalleLiquidacion> listaModelo, Integer pMes, Integer pAnio) {
		this.listaModelo = listaModelo;
		this.mes= pMes;
		this.anio= pAnio;
		this.formateadorFecha= new SimpleDateFormat("MMMMM 'de' yyyy");
		this.cantidadRegistros= 0;
		this.montoTotalLiquidar= new BigDecimal(0);
	}
	
	public List<LiquidacionMunicipalidadImpresion> formatearReporte() {
		List<LiquidacionMunicipalidadImpresion> locDetalleLiquidacion = new ArrayList<LiquidacionMunicipalidadImpresion>(); 
		this.cantidadRegistros= this.listaModelo.size();
		for(DetalleLiquidacion cadaCobro: this.listaModelo){
			String locCuil="";
			if(cadaCobro.getCuentaCorrienteAfectada().getPersona().getCui()!=null){
				locCuil= cadaCobro.getCuentaCorrienteAfectada().getPersona().getCui().toString();
			}
			PersonaFisica locPersona= (PersonaFisica) cadaCobro.getCuentaCorrienteAfectada().getPersona();
			String locNumeroLegajo="";
			if(locPersona.getLegajo()!=null){
				locNumeroLegajo=  locPersona.getLegajo().toString();
			}
			String locApellido= locPersona.getApellido();
			String locNombres= locPersona.getNombre();
			String locMontoLiquidar= this.formateadorMonetario.format(cadaCobro.getMonto());
			this.montoTotalLiquidar = montoTotalLiquidar.add(cadaCobro.getMonto());
			locDetalleLiquidacion.add(new LiquidacionMunicipalidadImpresion(locCuil, locNumeroLegajo, locApellido, locNombres, locMontoLiquidar));
		}
		return  locDetalleLiquidacion;
	}

	public Map<String, String> setearParametros(){
		this.formatearReporte();
		Calendar cal= Calendar.getInstance();
		cal.set(this.anio, this.mes, 1);
		Date fechaCobro= cal.getTime();
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("mesAnioLiquidacion", this.formateadorFecha.format(fechaCobro).toUpperCase());
		parametros.put("totalRegistros", this.cantidadRegistros.toString());
		parametros.put("montoTotalLiquidar", this.formateadorMonetario.format(this.montoTotalLiquidar));
		return parametros;		
	}

}
