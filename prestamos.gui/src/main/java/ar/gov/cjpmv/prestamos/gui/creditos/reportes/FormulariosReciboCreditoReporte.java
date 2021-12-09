package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.ConceptoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPropietaria;
import ar.gov.cjpmv.prestamos.gui.reportes.FormularioRecibo;

public class FormulariosReciboCreditoReporte {
	
	private Credito credito;
	//private List<FormularioRecibo> locFormularioReporte;
	
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private DateFormat formateadorFechaLarga;
	
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();

	

	public FormulariosReciboCreditoReporte(Credito creditoSeleccionado) {
		this.credito= creditoSeleccionado;
		this.formateadorFechaLarga= new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");
	}


	public List<FormularioRecibo> formatearRecibo() throws LogicaException {
			List<FormularioRecibo> locFormularioReporte = new ArrayList<FormularioRecibo>(); 
		    PersonaDAO locPersonaDAO= new PersonaDAO();
		    PersonaJuridica locPersonaJuridica= new PersonaJuridica();
		    locPersonaJuridica= locPersonaDAO.findListaPersonasJuridicas(30681122954L, null, null).get(0);
		    String locDomicilioCaja= locPersonaJuridica.getDomicilio().getCalle()+" "+locPersonaJuridica.getDomicilio().getNumero();
		    String locTelefonoCaja= locPersonaJuridica.getListaTelefonos().get(0).getCaracteristica()+"-"+locPersonaJuridica.getListaTelefonos().get(0).getNumero();
			String locDomicilioTelefonoCaja= locDomicilioCaja+" - "+locTelefonoCaja;
			String locFechaOtorgamiento= (this.credito.getFechaInicio()!=null)?this.formateadorFechaLarga.format(this.credito.getFechaInicio()):"";		
			String locMontoCredito= (this.credito.getMontoTotal()!=null)?this.formateadorMonetario.format(this.credito.getMontoTotal())+"-":"";		
			String locMesesCuotas= (this.credito.getCantidadCuotas()!=null)?Integer.toString(this.credito.getCantidadCuotas()):"";
			String locInteres= (this.credito.getTasa()!=null)?this.formateadorDecimal.format(this.credito.getTasa()):"";
			String locSistema= (this.credito.getTipoSistema()!=null)?this.credito.getTipoSistema().toString():"";
			String locValorCuota= (this.credito.getListaCuotas().get(0).getTotal()!=null)?this.formateadorMonetario.format(this.credito.getListaCuotas().get(0).getTotal())+"-":"";
			
			Calendar locCalendario = Calendar.getInstance();
			locCalendario.setTime(this.credito.getListaCuotas().get(0).getVencimiento());		
			String locDiaVencimiento= Integer.toString(locCalendario.get(Calendar.DAY_OF_MONTH));
			
			String locPrimerVencimiento= (this.credito.getListaCuotas().get(0).getVencimiento()!=null)?this.formateadorFecha.format(this.credito.getListaCuotas().get(0).getVencimiento()):"";
			String locPorcentajeImpuestoSellos=null;
			try{
				ConceptoDAO locConceptoDAO= new ConceptoDAO();
				locPorcentajeImpuestoSellos=(locConceptoDAO.getObjetoPorId(1L).getValor()!=null)?this.formateadorDecimal.format(locConceptoDAO.getObjetoPorId(1L).getValor()):"";
			}
			catch (Exception e) {
				Integer locCodigo=98;
				String locMensaje="";				
				throw new LogicaException(locCodigo, locMensaje);
			}		
			String locPorcentajeFondoQuebranto=null;
			try{
				ConceptoDAO locConceptoDAO= new ConceptoDAO();
				locPorcentajeFondoQuebranto=(locConceptoDAO.getObjetoPorId(2L).getValor()!=null)?this.formateadorDecimal.format(locConceptoDAO.getObjetoPorId(2L).getValor()):"";
			}
			catch (Exception e) {
				Integer locCodigo=99;
				String locMensaje="";				
				throw new LogicaException(locCodigo, locMensaje);
			}
		   String locNombreSolicitante= this.credito.getCuentaCorriente().getPersona().getNombreYApellido();
		   String calle= this.credito.getCuentaCorriente().getPersona().getDomicilio().getCalle();
		   String numero= (this.credito.getCuentaCorriente().getPersona().getDomicilio().getNumero()!=null)?Integer.toString(this.credito.getCuentaCorriente().getPersona().getDomicilio().getNumero()):"";
		   String locDomicilioSolicitante= calle+" "+numero;
		   PersonaFisica locPersonaFisica= new PersonaFisica();
		   locPersonaFisica= (PersonaFisica)this.credito.getCuentaCorriente().getPersona();
		   String locLegajoSolicitante= (locPersonaFisica.getLegajo()!=null)?Long.toString(locPersonaFisica.getLegajo()):"";
		   String locTipoDocumentoSolicitante= locPersonaFisica.getTipoDocumento().getDescripcion();
		   String locNumeroDocumentoSolicitante= (locPersonaFisica.getNumeroDocumento()!=null)?Integer.toString(locPersonaFisica.getNumeroDocumento()):null;
		   Integer contadorGarantias=0;
		   String locNombreGarante=" ";
		   String locDomicilioGarante=" ";
		   String locLegajoGarante=" ";
		   String locNumeroDocumentoGarante=" ";
		   String locTipoDocumentoGarante=" ";
		   
		   if(this.credito.getListaGarantias().size()==0){
			   String locNumeroGarante= " ";
			   locFormularioReporte.add(new FormularioRecibo(locDomicilioTelefonoCaja, locFechaOtorgamiento, locMontoCredito, locMesesCuotas, locInteres, locSistema, locValorCuota, locDiaVencimiento, locPrimerVencimiento, locPorcentajeImpuestoSellos, locNombreSolicitante, locDomicilioSolicitante, locLegajoSolicitante, locNumeroGarante, locNombreGarante, locDomicilioGarante, locLegajoGarante, locPorcentajeFondoQuebranto, locNumeroDocumentoSolicitante, locTipoDocumentoSolicitante, locNumeroDocumentoGarante, locTipoDocumentoGarante));
		   }
		   else{
			   for(Garantia listaGarantia: this.credito.getListaGarantias()){
				   contadorGarantias+=1;
				   if(listaGarantia instanceof GarantiaPersonal){
					   locNombreGarante= ((GarantiaPersonal) listaGarantia).getGarante().getNombreYApellido();
					   String calleGarante= ((GarantiaPersonal) listaGarantia).getGarante().getDomicilio().getCalle();
					   String numeroGarante=(((GarantiaPersonal) listaGarantia).getGarante().getDomicilio().getNumero()!=null)?Integer.toString(((GarantiaPersonal) listaGarantia).getGarante().getDomicilio().getNumero()):"";
					   locDomicilioGarante= calleGarante+" "+numeroGarante;
					   locLegajoGarante= (((GarantiaPersonal) listaGarantia).getGarante().getLegajo()!=null)?Long.toString(((GarantiaPersonal) listaGarantia).getGarante().getLegajo()):"";
					   locTipoDocumentoGarante= ((GarantiaPersonal) listaGarantia).getGarante().getTipoDocumento().getDescripcion();
					   locNumeroDocumentoGarante= (((GarantiaPersonal) listaGarantia).getGarante().getNumeroDocumento()!=null)?Integer.toString(((GarantiaPersonal) listaGarantia).getGarante().getNumeroDocumento()):"";
				   }
				   else{
					   if(listaGarantia instanceof GarantiaPropietaria){
						   locNombreGarante="(Garantía Hipotecaria)";
						   String calleGarante= ((GarantiaPropietaria) listaGarantia).getPropiedad().getCalle();
						   String numeroGarante= (((GarantiaPropietaria) listaGarantia).getPropiedad().getNumero()!=null)?Long.toString(((GarantiaPropietaria) listaGarantia).getPropiedad().getNumero()):"";
						   locDomicilioGarante= calleGarante+" "+numeroGarante;
						   locNumeroDocumentoGarante="";
						   locTipoDocumentoGarante="";
					   }
				   }
				   String locNumeroGarante= Integer.toString(contadorGarantias);
				   locFormularioReporte.add(new FormularioRecibo(locDomicilioTelefonoCaja, locFechaOtorgamiento, locMontoCredito, locMesesCuotas, locInteres, locSistema, locValorCuota, locDiaVencimiento, locPrimerVencimiento, locPorcentajeImpuestoSellos, locNombreSolicitante, locDomicilioSolicitante, locLegajoSolicitante, locNumeroGarante, locNombreGarante, locDomicilioGarante, locLegajoGarante, locPorcentajeFondoQuebranto, locNumeroDocumentoSolicitante, locTipoDocumentoSolicitante, locNumeroDocumentoGarante, locTipoDocumentoGarante));
			}
		}
		return locFormularioReporte;
	
	}


	
}
