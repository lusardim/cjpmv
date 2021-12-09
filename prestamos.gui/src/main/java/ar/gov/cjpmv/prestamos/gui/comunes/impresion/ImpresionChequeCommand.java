package ar.gov.cjpmv.prestamos.gui.comunes.impresion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.model.ChequeModel;
import ar.gov.cjpmv.prestamos.gui.reportes.ConversorNumerosALetras;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.ImpresionCheque;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class ImpresionChequeCommand implements ImpresionCommand{
	private DateFormat formateadorDia;
	private DateFormat formateadorMes;
	private DateFormat formateadorAnio;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();
	
	private Cheque cheque;
	
	public ImpresionChequeCommand(Cheque cheque){
		this();
		this.cheque = cheque;
	}
	
	public ImpresionChequeCommand(){
		formateadorMonetario = NumberFormat.getCurrencyInstance();
		formateadorDecimal= NumberFormat.getNumberInstance();
		formateadorDecimal.setMaximumFractionDigits(2);
		formateadorDecimal.setMinimumFractionDigits(2);
		this.formateadorAnio= new SimpleDateFormat("yyyy");
		this.formateadorMes= new SimpleDateFormat("MMMMM");
		this.formateadorDia= new SimpleDateFormat("dd");
	}
	
	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}
	
	public Cheque getCheque() {
		return cheque;
	}
	
	@Override
	public void imprimir() throws Exception {
		List<ImpresionCheque> parametrosCheque = this.formatearCheque(cheque);
		boolean impreso = false;
		if(cheque.getFechaPago()!=null && cheque.isDiferido()){	
			impreso = GestorImpresion.impresionSinViewer(Reportes.CHEQUE_SANTANDER_RIO, null, parametrosCheque);
		}
		else{
			impreso = GestorImpresion.impresionSinViewer(Reportes.CHEQUE_BERSA, null, parametrosCheque);
		}		

		//Un valor en false significa que el cheque no pudo ser impreso.
		if (impreso) {
			cheque.setEstadoCheque(EstadoCheque.IMPRESO);
		}
		else {
			throw new LogicaException(75, null);
		}
	}
	
	private List<ImpresionCheque> formatearCheque(Cheque cheque) {
	    List<ImpresionCheque> parametros = new ArrayList<ImpresionCheque>();  
	    String locMontoNumero=(cheque.getMonto()!=null)?this.formateadorDecimal.format(cheque.getMonto()):"";
	    String locDia= (cheque.getFechaEmision()!=null)?this.formateadorDia.format(cheque.getFechaEmision()):null;
	    String locMes= (cheque.getFechaEmision()!=null)?Utiles.nuloSiVacioConCammelCase(this.formateadorMes.format(cheque.getFechaEmision())):null;
	    String locAnio= (cheque.getFechaEmision()!=null)?this.formateadorAnio.format(cheque.getFechaEmision()):null;
		String locNombrePersona=null;
		
	    if(cheque.getEmitidoA()!=null){
			locNombrePersona= cheque.getEmitidoA().getNombreYApellido().toUpperCase();
		}
		else{
			locNombrePersona= cheque.getNombrePersona().toUpperCase();
		}
	    
		BigDecimal locMonto=cheque.getMonto();
		BigDecimal locEntero;
		if (locMonto == null) {
			locEntero = new BigDecimal(0);
		} 
		else {
			locEntero = locMonto.setScale(0, RoundingMode.FLOOR);
;
		}
		
		BigDecimal valorAux = locMonto.subtract(locEntero).multiply(new BigDecimal(100));
		
		long valorLong= valorAux.setScale(0).longValue();
		Long locDecimal= (valorAux!=null)?valorLong:0L;
		
		ConversorNumerosALetras locConversor= new ConversorNumerosALetras();
		String  cadenaMil="";
		
		long valorEntero = locEntero.longValue();
		if((valorEntero>999)&&(valorEntero<2000)){
			cadenaMil="un ";
		}
		String locMontoLetra=cadenaMil.toUpperCase();
		locMontoLetra+=(locEntero!=null)?locConversor.toLetras(valorEntero).toUpperCase().trim():"";
		String subMontoLetra="";
		if(locDecimal!=0L){
			subMontoLetra+=" con "+locDecimal+"/100";
		}
		String locMontoLetraContinuacion="".toUpperCase();
		
		locMontoLetra+=subMontoLetra.toUpperCase()+".-";
		
		if(locMontoLetra.length()>65){
			String cadena1= locMontoLetra.substring(0,66);
			int indice= cadena1.lastIndexOf(' ');
			locMontoLetraContinuacion=locMontoLetra.substring(indice+1).toUpperCase();
			locMontoLetra=cadena1.substring(0, indice).toUpperCase();
	
		}
		
		String locBanco= "";
		String locDiaDiferido=(cheque.getFechaPago()!=null)?this.formateadorDia.format(cheque.getFechaPago()):null;
		String locMesDiferido=(cheque.getFechaPago()!=null)?Utiles.nuloSiVacioConCammelCase(this.formateadorMes.format(cheque.getFechaPago())):null;
		String locAnioDiferido=(cheque.getFechaPago()!=null)?this.formateadorAnio.format(cheque.getFechaPago()):null;
	    parametros.add(new ImpresionCheque(locMontoNumero, locDia, locMes, locAnio, locNombrePersona, locMontoLetra, locMontoLetraContinuacion, locDiaDiferido, locMesDiferido, locAnioDiferido, locBanco));
		return parametros;	
	}

}
