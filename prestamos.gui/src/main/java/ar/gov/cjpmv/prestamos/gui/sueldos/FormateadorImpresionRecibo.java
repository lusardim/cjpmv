package ar.gov.cjpmv.prestamos.gui.sueldos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LineaRecibo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ReciboSueldo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;
import ar.gov.cjpmv.prestamos.core.utiles.NumeroALetras;

public class FormateadorImpresionRecibo {
	
	private List<ReciboSueldo> listaRecibo;
	private DateFormat formatedorFecha;
	private NumberFormat formateadorDecimal;
	private DecimalFormat formateadorDocumento; 


	public FormateadorImpresionRecibo(List<ReciboSueldo> listaRecibo) {
		super();
		this.formatedorFecha = new SimpleDateFormat("dd/MM/yyyy"); 
		formateadorDecimal= NumberFormat.getNumberInstance();
		formateadorDecimal.setMaximumFractionDigits(2);
		formateadorDecimal.setMinimumFractionDigits(2);
		formateadorDocumento= new DecimalFormat("###,###.### ");
		this.listaRecibo = listaRecibo;
	}
	
	public Set<ImpresionReciboHaberes> getListaImpresionRecibo() {
		Set<ImpresionReciboHaberes> listaImpresionRecibo= new TreeSet<ImpresionReciboHaberes>(new Comparator<ImpresionReciboHaberes>() {
			@Override
			public int compare(ImpresionReciboHaberes o1,
					ImpresionReciboHaberes o2) {
				return o1.getApellidoNombres().compareTo(o2.getApellidoNombres()); 				
			}
		}) ;
		
	
		
		for(ReciboSueldo cadaRecibo: this.listaRecibo){
			BigDecimal totalHaberes= new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
			BigDecimal totalDescuento= new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
			BigDecimal totalNeto= new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
			
			List<ImpresionLineaRecibo> listaLinea= new ArrayList<ImpresionLineaRecibo>();
		
			for(LineaRecibo cadaLinea: cadaRecibo.getLineasRecibo()){
				
				BigDecimal montoHaberes= null;
				BigDecimal montoDescuento= null;
				Integer codigo= cadaLinea.getConcepto().getCodigo();
				String txtCodigo= codigo.toString();
				String txtDescripcion= cadaLinea.getConcepto().getDescripcion().toUpperCase();
				
				
				if(cadaLinea.getConcepto() instanceof ConceptoPrefijado && cadaLinea.getConcepto().equals(ConceptoPrefijado.SUELDO_BASICO) && cadaRecibo.getPersona().getListaEmpleos().get(0).getTipoCategoria()!=null){
					txtDescripcion= cadaRecibo.getPersona().getListaEmpleos().get(0).getTipoCategoria().toString().toUpperCase();
				}
				
		
				if(!cadaLinea.getConcepto().getTipoCodigo().equals(TipoCodigo.DESCUENTO)){
					totalHaberes= totalHaberes.add(cadaLinea.getTotal());
					montoHaberes= cadaLinea.getTotal();
				}
				else{
					totalDescuento= totalDescuento.add(cadaLinea.getTotal());
					montoDescuento= cadaLinea.getTotal();
				}
				
				String txtMontoHaber= (montoHaberes!=null)?this.formateadorDecimal.format(montoHaberes):null;
				String txtMontoDescuento= (montoDescuento!=null)?this.formateadorDecimal.format(montoDescuento):null;

				listaLinea.add(new ImpresionLineaRecibo(txtCodigo, txtDescripcion, txtMontoHaber, txtMontoDescuento, null));
			}
		
			totalNeto= totalNeto.add(totalHaberes);
			totalNeto= totalNeto.subtract(totalDescuento);
			NumeroALetras conversorNumerosLetras = new NumeroALetras();
		 	String montoLetras=conversorNumerosLetras.convertir(totalNeto.toString(), true);
		 	String txtHaberes= this.formateadorDecimal.format(totalHaberes);
		 	String txtDescuento= this.formateadorDecimal.format(totalDescuento);
		 	String txtNeto= this.formateadorDecimal.format(totalNeto);
		 	

			listaLinea.add(new ImpresionLineaRecibo(null, "TOTAL NETO A COBRAR", null, null, txtNeto));

		 	String txtApellidoNombres= cadaRecibo.getPersona().getApellido()+", "+cadaRecibo.getPersona().getNombre();
		 	String txtLegajo= formateadorDocumento.format(cadaRecibo.getPersona().getLegajo()).toString();
		 	String txtDocumento= formateadorDocumento.format(cadaRecibo.getPersona().getNumeroDocumento()).toString();
		 	String txtFechaIngreso= null;
		 	
			List<Beneficio> listaBeneficios= cadaRecibo.getPersona().getListaBeneficios();
		 	
		 	if(cadaRecibo.getLiquidacion().getTipo().equals(TipoLiquidacion.ACTIVO_NORMAL) || cadaRecibo.getLiquidacion().getTipo().equals(TipoLiquidacion.ACTIVO_SAC)){
		 		txtFechaIngreso=(cadaRecibo.getPersona().getListaEmpleos().get(0).getFechaInicio()!=null)?this.formatedorFecha.format(cadaRecibo.getPersona().getListaEmpleos().get(0).getFechaInicio()):null;
		 	}
		 	else if(cadaRecibo.getLiquidacion().getTipo().equals(TipoLiquidacion.JUBILACION_NORMAL) || cadaRecibo.getLiquidacion().getTipo().equals(TipoLiquidacion.JUBILACION_SAC)){
		 		if(listaBeneficios!=null && !listaBeneficios.isEmpty()){
		 			for(Beneficio cadaBeneficio: listaBeneficios){
		 				if(!cadaBeneficio.getTipoBeneficio().equals(TipoBeneficio.PENSION)){
		 					txtFechaIngreso=(cadaBeneficio.getFechaOtorgamiento()!=null)?this.formatedorFecha.format(cadaBeneficio.getFechaOtorgamiento()):null;
		 				}
		 			}
		 		}
		 	}
			else if(cadaRecibo.getLiquidacion().getTipo().equals(TipoLiquidacion.PENSION_NORMAL) || cadaRecibo.getLiquidacion().getTipo().equals(TipoLiquidacion.PENSION_SAC)){
		 		if(listaBeneficios!=null && !listaBeneficios.isEmpty()){
		 			for(Beneficio cadaBeneficio: listaBeneficios){
		 				if(cadaBeneficio.getTipoBeneficio().equals(TipoBeneficio.PENSION)){
		 					txtFechaIngreso= (cadaBeneficio.getFechaOtorgamiento()!=null)?this.formatedorFecha.format(cadaBeneficio.getFechaOtorgamiento()):null;
		 				}
		 			}
		 		}
		 	}
		 	
		
			listaImpresionRecibo.add(new ImpresionReciboHaberes(
													txtApellidoNombres, 
													txtLegajo, 
													listaLinea,
													txtDocumento,
													txtFechaIngreso,
													txtHaberes,
													txtDescuento,
													txtNeto,
													montoLetras
											)
										);
		}
		
		return listaImpresionRecibo;
	}
	
	
	
	
	
	
		
	public Map<String, String> setearParametros(Date fechaPago, String mesAnioLiquidacion){
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("SUBREPORT_DIR", "reportes/");
		parametros.put("fechaPago", formatedorFecha.format(fechaPago));
		parametros.put("periodoLiquidacion", "Liq. correspondiente a "+mesAnioLiquidacion);
		return parametros;
	}
	
	
}
