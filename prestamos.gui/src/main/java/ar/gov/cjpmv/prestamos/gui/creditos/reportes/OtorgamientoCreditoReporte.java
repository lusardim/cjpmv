package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.gui.reportes.ConceptoNoAplicadoCuotasOtorgamiento;
import ar.gov.cjpmv.prestamos.gui.reportes.CuotaOtorgamiento;

public class OtorgamientoCreditoReporte {
	
	
	private Credito credito;
	private List<CuotaOtorgamiento> listaCuotaOtorgamiento;
	private List<ConceptoNoAplicadoCuotasOtorgamiento> listaConceptosNoAplicadoACuota;
	private String montoTotalAPagar;
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();

	

	public OtorgamientoCreditoReporte(Credito creditoSeleccionado) {
		this.credito= creditoSeleccionado;
		this.formateadorDecimal.setMinimumFractionDigits(2);
		this.formateadorDecimal.setMaximumFractionDigits(2);
	}

	public Map<String, String> setearParametros() {
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("numeroCredito", this.credito.getNumeroCredito().toString());
		parametros.put("montoTotal", this.formateadorMonetario.format(this.credito.getMontoTotal()).toString());
		parametros.put("interes", this.formateadorDecimal.format(this.credito.getTasa()).toString()+"%");
		if(this.credito.getViaCobro()!=null){
			parametros.put("viaCobro", this.credito.getViaCobro().toString());
		}
		else{
			parametros.put("viaCobro", null);
		}
		parametros.put("tipoCredito", this.credito.getTipoCredito().toString());
		parametros.put("cantidadCuotas", this.credito.getCantidadCuotas().toString());
		parametros.put("fechaInicio", this.formateadorFecha.format(this.credito.getFechaInicio()));
		parametros.put("sistema", this.credito.getTipoSistema().toString());
		parametros.put("montoEntrega", this.formateadorMonetario.format(this.credito.getMontoEntrega()));
		parametros.put("montoAPagar", this.montoTotalAPagar);
		if(this.credito.getCuentaCorriente().getPersona() instanceof PersonaFisica){
			PersonaFisica locPersonaFisica= (PersonaFisica)this.credito.getCuentaCorriente().getPersona();
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
		if(this.credito.getCuentaCorriente().getPersona()!=null){
			parametros.put("nombreApellido", this.credito.getCuentaCorriente().getPersona().getNombreYApellido());
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
		
		for(Cuota cadaCuota: this.credito.getListaCuotas()){
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

	public List<ConceptoNoAplicadoCuotasOtorgamiento> formatearConceptosNoAplicadosACuotas() {
		this.listaConceptosNoAplicadoACuota= new ArrayList<ConceptoNoAplicadoCuotasOtorgamiento>();
		String locNombreConcepto= " ";
		String locMontoConcepto= " ";
		String locObservacionConcepto= " ";
		for(DetalleCredito cadaDetalle: this.credito.getDetalleCredito()){
			if(cadaDetalle.getCuota()==null && !cadaDetalle.getNombre().equals("Redondeo de cuota")){
				locNombreConcepto=" "+cadaDetalle.getNombre();
				locMontoConcepto= (cadaDetalle.getValor()!=null)?this.formateadorMonetario.format(cadaDetalle.getValor()):" ";
				if(cadaDetalle.getNombre().equals("Cancelación de créditos anteriores")){
					CreditoDAOImpl locCreditoDAO= new CreditoDAOImpl();
					List<Integer> listaNumeroCreditos=locCreditoDAO.getCreditoCanceladoEnOtorgamiento(cadaDetalle);
					boolean inicio= true;
					if(listaNumeroCreditos!=null && !listaNumeroCreditos.isEmpty()){
						for(Integer cadaNum: listaNumeroCreditos){
							if(inicio){
								locObservacionConcepto=" N° de Crédito/s Cancelado/s: ";
								inicio= false;
							}
							//BigDecimal locMonto= locCreditoDAO.getMontoCanceladoEnOtorgamiento(cadaDetalle, cadaNum);
							//String cadenaMonto= this.formateadorMonetario.format(locMonto);
							//locObservacionConcepto+= cadaNum.toString()+" ("+cadenaMonto+") - ";
							locObservacionConcepto+= cadaNum.toString()+" - ";
						}
					}
				}
				this.listaConceptosNoAplicadoACuota.add(new ConceptoNoAplicadoCuotasOtorgamiento(locNombreConcepto, locMontoConcepto, locObservacionConcepto));
			}
		}
		return this.listaConceptosNoAplicadoACuota;
	}
	
	
	

}


