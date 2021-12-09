package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ar.gov.cjpmv.prestamos.core.business.dao.ConceptoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPropietaria;
import ar.gov.cjpmv.prestamos.gui.comunes.impresion.ImpresionChequeCommand;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlAMCreditoView;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.model.ChequeModel;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.controllers.PnlDatosCreditoController;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CreditoModel;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.controllers.PnlDatosGarantesController;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.solicitante.controllers.PnlDatosSolicitanteController;
import ar.gov.cjpmv.prestamos.gui.reportes.CuotaOtorgamiento;
import ar.gov.cjpmv.prestamos.gui.reportes.FormularioRecibo;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;


public class AMCreditoController {
	private AdminCredito padre;
	private CreditoModel modelo;
	private PnlAMCreditoView vista;
	
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private DateFormat formateadorFechaLarga;
	
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();
	
	private PnlDatosSolicitanteController pnlDatosSolicitante;
	private PnlDatosCreditoController pnlDatosCredito;
	private PnlDatosGarantesController pnlDatosGaranteController;
	private PnlListadoChequesController pnlListadoChequeController;
	
	protected boolean inCheque = false;
	
	public AMCreditoController(AdminCredito pPadre,PnlAMCreditoView pVista){
		this(pPadre,new Credito(),pVista);
		this.modelo.setValoresDefecto();
		this.actualizarVista();
	}
	public AMCreditoController(AdminCredito pPadre){
		this(pPadre,new PnlAMCreditoView());
	}
	
	public AMCreditoController(AdminCredito pPadre, Credito pCredito){
		this(pPadre,pCredito,new PnlAMCreditoView());
	}
	
	protected CreditoModel getCreditoModel(Credito pCredito){
		return new CreditoModel(pCredito);
	}
	
	protected AMCreditoController(AdminCredito pPadre, Credito pCredito, PnlAMCreditoView pVista){
		this.padre = pPadre;
		this.modelo = getCreditoModel(pCredito);
		this.vista = pVista;
		this.vista.getLblTituloPnlDcha().setText("Nuevo Crédito");
		this.formateadorDecimal.setMinimumFractionDigits(2);
		this.formateadorDecimal.setMaximumFractionDigits(2);
		this.formateadorFechaLarga= new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");
		this.inicializarModelos();
		this.inicializarPaneles();
		this.inicializarEventos();
	}
	
	protected void inicializarModelos(){}
	
	protected void inicializarEventos() {
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});
		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		this.vista.getBtnImprimir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				imprimirOtorgamientoCredito();
				
			}
		});
		
		this.vista.getTbpABM().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (vista.getTbpABM().getSelectedComponent() == pnlDatosGaranteController.getVista()){
					validarGarantias();
				}
			}
		});
	}
	
	protected void actualizarVista() {
		//TODO ver para que no pise los estados de las búsquedas
		Utiles.setEnableRecursivo(this.vista.getPnlDatosCredito(),!inCheque);
		Utiles.setEnableRecursivo(this.vista.getPnlDatosGarantes(),!inCheque);
		Utiles.setEnableRecursivo(this.vista.getPnlDatosSolicitante(),!inCheque);
		Utiles.setEnableRecursivo(this.vista.getPnlListadoCheques(),inCheque);

		this.pnlDatosCredito.actualizarVista();
		this.pnlDatosGaranteController.actualizarVista();
		this.pnlDatosSolicitante.actualizarSeleccionTabla();
	}

	private void validarGarantias() {
		try {
			if (pnlDatosGaranteController.getVista()
					.getRbtnGarantiaPersonal().isSelected()){
				for (Garantia cadaGarantia : pnlDatosGaranteController.getListaGarantias()){
					if (cadaGarantia instanceof GarantiaPersonal){
						GarantiaPersonal garantiaPersonal = (GarantiaPersonal)cadaGarantia;
						if (garantiaPersonal.getGarante().equals(this.modelo.getPersona())){
							pnlDatosGaranteController.remove(garantiaPersonal);
						}
					}
				}
			}
		} catch (LogicaException e) {
			e.printStackTrace();
			//Lo que puede tirar una exception es la garantía propietaria, no la personal
			//Por eso esto es ignorado, Arreglarlo para la versión 0.1
		}
	}
	
	private void inicializarPaneles() {
		this.vista.getBtnImprimir().setVisible(true);
		Utiles.setEnableRecursivo(this.getVista().getPnlListadoCheques(),false);
		
		this.pnlDatosSolicitante = new PnlDatosSolicitanteController(this.vista.getPnlDatosSolicitante(),this.modelo);
		this.pnlDatosCredito = new PnlDatosCreditoController(this.vista.getPnlDatosCredito(),this.modelo);
		this.pnlDatosGaranteController = new PnlDatosGarantesController(this.vista.getPnlDatosGarantes(),this.modelo.getCredito());
		this.pnlListadoChequeController = new PnlListadoChequesController(this.vista.getPnlListadoCheques());
	}

	public PnlAMCreditoView getVista() {
		return this.vista;
	}
	
	private void imprimirOtorgamientoCredito() {
		try{
		//	this.modelo.validar();
			ImpresionOtorgamientoCredito locImpresion= new ImpresionOtorgamientoCredito(this.modelo);
			List<CuotaOtorgamiento> listaCuotasOtorgamiento = locImpresion.formatearCuotasOrganismo();
			GestorImpresion.imprimirCollectionDataSource(this.padre.getVista(), Reportes.OTORGAMIENTO_CREDITO, locImpresion.setearParametros(), listaCuotasOtorgamiento);			
		}
		catch(LogicaException m){
			JOptionPane.showMessageDialog(this.vista, m.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void guardar(){
		try{
			this.actualizarModelo();
			if (inCheque){
				this.pnlListadoChequeController.actualizarModelo();
				this.modelo.validar();
				this.imprimirCheques();
				this.modelo.guardar();

				JOptionPane.showMessageDialog(this.padre.getVista(),"El crédito ha sido guardado correctamente.", "Agregar",JOptionPane.INFORMATION_MESSAGE);
				volver();
			}
			else{
				this.modelo.validar();
				this.mostrarPnlCheque();
				this.imprimirFormularioRecibo();
			}
		}
		catch(LogicaException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void volver() {
		padre.mostrarPnlEstadoCuenta();
		padre.setAmCreditoController(null);
		padre.setCreditoFinalizacionController(null);
	}

	private void mostrarPnlCheque() {
		this.inCheque = true;
		this.actualizarVista();
		this.actualizarCheques();
		this.vista.getTbpABM().setSelectedComponent(this.vista.getPnlListadoCheques());
	}
	

	private void imprimirCheques() {
		/*TODO 
		 * 	IMPRIMIR DIRECTO A LA IMPRESORA
		 * CONTAR CARACTERES EN LAS LINEAS DE MONTO LETRA		
		*/
		ImpresionChequeCommand impresionChequeCommand = new ImpresionChequeCommand();
		for(ChequeModel cadaCheque:this.pnlListadoChequeController.getListaModelosCheques()){
			if(cadaCheque.isImprimir()){
				try{
					impresionChequeCommand.setCheque(cadaCheque.getCheque());
					impresionChequeCommand.imprimir();
				}
				catch(Exception e){
					e.printStackTrace();
					String locMensaje="No se ha podido imprimir el cheque nº"+cadaCheque.getCheque().getNumero();
					JOptionPane.showMessageDialog(this.padre.getVista(), locMensaje, "Error",JOptionPane.ERROR_MESSAGE);
				}	
			}
		}
	}
   
	public void imprimirFormularioRecibo() {
		if(this.vista.getPnlDatosCredito().getRbtnImprimirF01().isSelected()) {
			this.imprimirFormulario(Reportes.FORMULARIO_F01);
		}
		else{
			if(this.vista.getPnlDatosCredito().getRbtnImprimirF02().isSelected()) {
				this.imprimirFormulario(Reportes.FORMULARIO_F02);
			}
		}
		if(this.vista.getPnlDatosCredito().getCkbImprimirRecibo().isSelected()) {
			this.imprimirRecibo(Reportes.RECIBO_CREDITO);
		}
		
	}

	public void imprimirFormulario(Reportes locReportes) {
		try{
			List<FormularioRecibo> listaFormularioCredito = this.formatearFormularioRecibo();
				GestorImpresion.imprimirCollectionDataSource(this.padre.getVista(), locReportes, null, listaFormularioCredito);
			}
			catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(this.padre.getVista(), "No se ha podido generar el reporte.", "Error",JOptionPane.ERROR_MESSAGE);
			}
	}
	
	public void imprimirRecibo(Reportes locReportes) {
		try{
			List<FormularioRecibo> listaFormularioCredito = this.formatearFormularioRecibo();
				GestorImpresion.imprimirCollectionDataSource(this.padre.getVista(), locReportes, null, listaFormularioCredito);
			}
			catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(this.padre.getVista(), "No se ha podido generar el reporte.", "Error",JOptionPane.ERROR_MESSAGE);
			}
	}
	
	private List<FormularioRecibo> formatearFormularioRecibo() {
	    List<FormularioRecibo> locFormularioReporte = new ArrayList<FormularioRecibo>();      
	    PersonaDAO locPersonaDAO= new PersonaDAO();
	    PersonaJuridica locPersonaJuridica= new PersonaJuridica();
	    locPersonaJuridica= locPersonaDAO.findListaPersonasJuridicas(30681122954L, null, null).get(0);
	    String locDomicilioCaja= locPersonaJuridica.getDomicilio().getCalle()+" "+locPersonaJuridica.getDomicilio().getNumero();
	    String locTelefonoCaja= locPersonaJuridica.getListaTelefonos().get(0).getCaracteristica()+"-"+locPersonaJuridica.getListaTelefonos().get(0).getNumero();
		String locDomicilioTelefonoCaja= locDomicilioCaja+" - "+locTelefonoCaja;
		String locFechaOtorgamiento= (this.modelo.getCredito().getFechaInicio()!=null)?this.formateadorFechaLarga.format(this.modelo.getCredito().getFechaInicio()):"";		
		String locMontoCredito= (this.modelo.getCredito().getMontoTotal()!=null)?this.formateadorMonetario.format(this.modelo.getCredito().getMontoTotal())+"-":"";		
		String locMesesCuotas= (this.modelo.getCredito().getCantidadCuotas()!=null)?Integer.toString(this.modelo.getCredito().getCantidadCuotas()):"";
		String locInteres= (this.modelo.getCredito().getTasa()!=null)?this.formateadorDecimal.format(this.modelo.getCredito().getTasa()):"";
		String locSistema= (this.modelo.getCredito().getTipoSistema()!=null)?this.modelo.getCredito().getTipoSistema().toString():"";
		String locValorCuota= (this.modelo.getCredito().getListaCuotas().get(0).getTotal()!=null)?this.formateadorMonetario.format(this.modelo.getCredito().getListaCuotas().get(0).getTotal())+"-":"";
		
		Calendar locCalendario = Calendar.getInstance();
		locCalendario.setTime(this.modelo.getFechaPrimerVencimiento());		
		String locDiaVencimiento= Integer.toString(locCalendario.get(Calendar.DAY_OF_MONTH));
		
		String locPrimerVencimiento= (this.modelo.getCredito().getListaCuotas().get(0).getVencimiento()!=null)?this.formateadorFecha.format(this.modelo.getCredito().getListaCuotas().get(0).getVencimiento()):"";
		String locPorcentajeImpuestoSellos=null;
		try{
			ConceptoDAO locConceptoDAO= new ConceptoDAO();
			locPorcentajeImpuestoSellos=(locConceptoDAO.getObjetoPorId(1L).getValor()!=null)?this.formateadorDecimal.format(locConceptoDAO.getObjetoPorId(1L).getValor()):"";
		}
		catch (Exception e) {
			String pTitulo="Error";
			String pMensaje="El concepto Impuesto a los sellos ha sido modificado. Consulte con el administrador del sistema.";
			JOptionPane.showMessageDialog(this.vista , pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
	   }		
		String locPorcentajeFondoQuebranto=null;
		try{
			ConceptoDAO locConceptoDAO= new ConceptoDAO();
			locPorcentajeFondoQuebranto=(locConceptoDAO.getObjetoPorId(2L).getValor()!=null)?this.formateadorDecimal.format(locConceptoDAO.getObjetoPorId(2L).getValor()):"";
		}
		catch (Exception e) {
			String pTitulo="Error";
			String pMensaje="El concepto Fondo de Quebranto ha sido modificado. Consulte con el administrador del sistema.";
			JOptionPane.showMessageDialog(this.vista , pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
	   }
	   String locNombreSolicitante= this.modelo.getCredito().getCuentaCorriente().getPersona().getNombreYApellido();
	   String calle= this.modelo.getCredito().getCuentaCorriente().getPersona().getDomicilio().getCalle();
	   String numero= (this.modelo.getCredito().getCuentaCorriente().getPersona().getDomicilio().getNumero()!=null)?Integer.toString(this.modelo.getCredito().getCuentaCorriente().getPersona().getDomicilio().getNumero()):"";
	   String locDomicilioSolicitante= calle+" "+numero;
	   PersonaFisica locPersonaFisica= new PersonaFisica();
	   locPersonaFisica= (PersonaFisica)this.modelo.getCredito().getCuentaCorriente().getPersona();
	   String locLegajoSolicitante= (locPersonaFisica.getLegajo()!=null)?Long.toString(locPersonaFisica.getLegajo()):"";
	   String locTipoDocumentoSolicitante= locPersonaFisica.getTipoDocumento().getDescripcion();
	   String locNumeroDocumentoSolicitante= (locPersonaFisica.getNumeroDocumento()!=null)?Integer.toString(locPersonaFisica.getNumeroDocumento()):null;
	   Integer contadorGarantias=0;
	   String locNombreGarante=" ";
	   String locDomicilioGarante=" ";
	   String locLegajoGarante=" ";
	   String locNumeroDocumentoGarante=" ";
	   String locTipoDocumentoGarante=" ";
	   
	   if(this.modelo.getCredito().getListaGarantias().size()==0){
		   String locNumeroGarante= " ";
		   locFormularioReporte.add(new FormularioRecibo(locDomicilioTelefonoCaja, locFechaOtorgamiento, locMontoCredito, locMesesCuotas, locInteres, locSistema, locValorCuota, locDiaVencimiento, locPrimerVencimiento, locPorcentajeImpuestoSellos, locNombreSolicitante, locDomicilioSolicitante, locLegajoSolicitante, locNumeroGarante, locNombreGarante, locDomicilioGarante, locLegajoGarante, locPorcentajeFondoQuebranto, locNumeroDocumentoSolicitante, locTipoDocumentoSolicitante, locNumeroDocumentoGarante, locTipoDocumentoGarante));
	   }
	   else{
		   for(Garantia listaGarantia: this.modelo.getCredito().getListaGarantias()){
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
	
	public void cancelar(){
		if (inCheque){
			int confirmacion = JOptionPane.showConfirmDialog(this.vista, 
							"Se perderán los datos de los cheques. ¿Desea continuar de todas maneras?",
							"Confirmación",
							JOptionPane.YES_NO_OPTION+JOptionPane.WARNING_MESSAGE);
			if (confirmacion == JOptionPane.YES_OPTION){
				this.inCheque = false;
				this.limpiarCheques();
				this.actualizarVista();
				this.getVista().getTbpABM().setSelectedComponent(this.pnlDatosCredito.getVista());
			}
		}
		else if (JOptionPane.showConfirmDialog(this.vista, 
						"¿Está seguro que desea cancelar? Perderá los datos cargados.",
						"Confirmación",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			this.volver();
		}
	}
	
	private void limpiarCheques() {
		List<Cheque> locListaCheques = this.pnlListadoChequeController.getListaCheques();;
		this.modelo.getCredito().getListaCheques().removeAll(locListaCheques);
		this.pnlListadoChequeController.limpiarCheques();
	}

	private void limpiarDatos() {
		/*this.limpiarCheques();
		this.modelo.limpiarDatos();
		this.pnlDatosCredito.actualizarVista();
		this.pnlDatosGaranteController.limpiar();
		this.pnlDatosSolicitante.setPersonaSeleccionada(null);
		this.actualizarVista();*/
		//forma facil
		
	}

	protected void actualizarModelo() throws LogicaException {
	//	this.modelo.setPersona(this.pnlDatosSolicitante.getPersonaSeleccionada());
		this.pnlDatosCredito.actualizarModelo();
		this.pnlDatosGaranteController.actualizarModelo();
	}

	private void actualizarCheques(){
		//TODO FIME MENTE!!!! acá debería actualizar el modelo
		List<Cheque> locListaCheques = this.modelo.generarListaCheques();
		this.limpiarCheques();
		for (Cheque cadaCheque : locListaCheques){
			this.pnlListadoChequeController.addCheque(cadaCheque);
		}
	}
	public CreditoModel getModelo() {
		return modelo;
	}		

}
