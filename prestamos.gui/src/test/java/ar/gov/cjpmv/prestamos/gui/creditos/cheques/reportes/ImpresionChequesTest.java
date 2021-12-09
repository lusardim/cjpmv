package ar.gov.cjpmv.prestamos.gui.creditos.cheques.reportes;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.dao.CuentaBancariaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.model.ChequeModel;
import ar.gov.cjpmv.prestamos.gui.reportes.ConversorNumerosALetras;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.ImpresionCheque;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class ImpresionChequesTest {

	
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private DateFormat formateadorFechaLarga;
	private DateFormat formateadorDia;
	private DateFormat formateadorMes;
	private DateFormat formateadorAnio;
	
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();
	
	private ChequeModel chequeModel;
	private Cheque cheque;
	
	@Ignore
	@Test
	public void testImpresion(){
		try{
			this.formateadorDecimal.setMinimumFractionDigits(2);
			this.formateadorDecimal.setMaximumFractionDigits(2);
			this.formateadorFechaLarga= new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");
			this.formateadorAnio= new SimpleDateFormat("yyyy");
			this.formateadorMes= new SimpleDateFormat("MMMMM");
			this.formateadorDia= new SimpleDateFormat("dd");
			this.impresionCheque();
		}
		catch (Exception e){
			System.out.println("no se pudo generar el reporte de cheque");
		}
	}
	
	
    public void impresionCheque() throws Exception{
    	this.cheque= new Cheque();
    	this.cheque.setConcepto("Pago de Impuestos");
    	CuentaBancariaDAO cuentaBancariaDAO= new CuentaBancariaDAO();
    	CuentaBancaria cuentaBancaria= cuentaBancariaDAO.findCuentasBancarias().get(0);
    	this.cheque.setCuenta(cuentaBancaria);
    	PersonaDAO personaDAO= new PersonaDAO();
    	Persona persona;
		persona = personaDAO.getPersonaPorCui(20312478073L);
    	this.cheque.setEmitidoA(persona);
    	this.cheque.setEstadoCheque(EstadoCheque.PENDIENTE_IMPRESION);
    	Calendar cal= Calendar.getInstance();
    	Date fecha= cal.getTime();
    	this.cheque.setFechaPago(fecha);
    	this.cheque.setFechaEmision(fecha);
    	BigDecimal monto = new BigDecimal(5785);
    	this.cheque.setMonto(monto);
    	this.cheque.setNombrePersona("Eduardo Luis Martinez");
    	this.cheque.setNumero(123);
    	this.chequeModel= new ChequeModel(this.cheque);
    	this.chequeModel.setImprimir(true);
    	this.chequeModel.setPersonaSeleccionada(false);

    	List<ImpresionCheque> listaImpresionCheque = this.formatearListaImpresionCheque(this.chequeModel);
    	GestorImpresion.impresionSinViewer(Reportes.CHEQUE_SANTANDER_RIO, null, listaImpresionCheque);
		//GestorImpresion.impresionSinViewer(new JDialog(), Reportes.CHEQUE_BERSA, null, listaImpresionCheque);
    }
    

    private List<ImpresionCheque> formatearListaImpresionCheque(ChequeModel pChequeModel) {
	    List<ImpresionCheque> locImpresionCheque= new ArrayList<ImpresionCheque>();  
	    String locMontoNumero=(pChequeModel.getCheque().getMonto()!=null)?this.formateadorDecimal.format(pChequeModel.getCheque().getMonto()):"";
	    String locDia= (pChequeModel.getCheque().getFechaEmision()!=null)?this.formateadorDia.format(pChequeModel.getCheque().getFechaEmision()):null;
	    String locMes= (pChequeModel.getCheque().getFechaEmision()!=null)?Utiles.nuloSiVacioConCammelCase(this.formateadorMes.format(pChequeModel.getCheque().getFechaEmision())):null;
	    String locAnio= (pChequeModel.getCheque().getFechaEmision()!=null)?this.formateadorAnio.format(pChequeModel.getCheque().getFechaEmision()):null;
		String locNombrePersona=null;
	    if(pChequeModel.isPersonaSeleccionada()){
			locNombrePersona= pChequeModel.getCheque().getEmitidoA().getNombreYApellido().toUpperCase();
		}
		else{
			locNombrePersona= pChequeModel.getCheque().getNombrePersona().toUpperCase();
		}
		BigDecimal locMonto=pChequeModel.getCheque().getMonto();
		BigDecimal locEntero=(locMonto!=null)?locMonto.setScale(0):new BigDecimal(0);
		
		BigDecimal valorAux = locMonto.subtract(locEntero).multiply(new BigDecimal(100));
		
		Long locDecimal= (valorAux!=null)?valorAux.longValue():0L;
		ConversorNumerosALetras locConversor= new ConversorNumerosALetras();
		String  cadenaMil="";
		if((locEntero.floatValue()>999)&&(locEntero.floatValue()<2000)){
			cadenaMil="un ";
		}
		String locMontoLetra=cadenaMil.toUpperCase();
		locMontoLetra+=(locEntero!=null)?locConversor.toLetras(locEntero.intValue()).toUpperCase().trim():"";
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
		String locDiaDiferido=(pChequeModel.getCheque().getFechaPago()!=null)?this.formateadorDia.format(pChequeModel.getCheque().getFechaPago()):null;
		String locMesDiferido=(pChequeModel.getCheque().getFechaPago()!=null)?Utiles.nuloSiVacioConCammelCase(this.formateadorMes.format(pChequeModel.getCheque().getFechaPago())):null;
		String locAnioDiferido=(pChequeModel.getCheque().getFechaPago()!=null)?this.formateadorAnio.format(pChequeModel.getCheque().getFechaPago()):null;
	    locImpresionCheque.add(new ImpresionCheque(locMontoNumero, locDia, locMes, locAnio, locNombrePersona, locMontoLetra, locMontoLetraContinuacion, locDiaDiferido, locMesDiferido, locAnioDiferido, locBanco));
		return locImpresionCheque;	
	}
    
    
    
}

	

    
    
