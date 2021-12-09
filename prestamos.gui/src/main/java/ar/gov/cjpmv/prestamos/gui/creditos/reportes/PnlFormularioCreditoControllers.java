package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.creditos.model.TblCreditoReporteModel;
import ar.gov.cjpmv.prestamos.gui.reportes.ConceptoNoAplicadoCuotasOtorgamiento;
import ar.gov.cjpmv.prestamos.gui.reportes.CuotaOtorgamiento;
import ar.gov.cjpmv.prestamos.gui.reportes.FormularioRecibo;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlFormularioCreditoControllers {
	
	
	private AdminCredito adminCredito;
	private PnlDchaFormularioCredito vista;
	private List<Credito> listaCredito;
	private Integer numeroCredito;
	private Long legajo;
	private TblCreditoReporteModel modeloTblCreditoReporteModel;
	
	private boolean imprimirFormulario1;
	private boolean imprimirFormulario2;
	private boolean imprimirRecibo;
	private boolean imprimirOtorgamiento;
	private boolean imprimirConceptos;
	private Credito creditoSeleccionado;

	
	
	public PnlFormularioCreditoControllers(AdminCredito adminCredito) {
		super();
		this.adminCredito = adminCredito;
		this.vista= new PnlDchaFormularioCredito();
		this.listaCredito= new ArrayList<Credito>();
		this.inicializarEventos();
		this.inicializarVista();
	}
	
	private void inicializarVista() {
		this.vista.getBtnCancelar().setVisible(false);
		this.vista.getBtnImprimir().setVisible(false);
		this.vista.getBtnAceptarGuardar().setText("Finalizar");
		this.modeloTblCreditoReporteModel= new TblCreditoReporteModel(this.listaCredito);
		this.vista.getPnlFormulariosCredito().getTblResultado().setModel(this.modeloTblCreditoReporteModel);
	
	}
	
	private void inicializarEventos() {
		this.vista.getPnlFormulariosCredito().getBtBusqueda().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarCreditos();				
			}
		});
		
		this.vista.getPnlFormulariosCredito().getBtnImprimir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imprimirReportes();
			}
		});
		
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finalizar();
			}
		});
	}
	
	public PnlDchaFormularioCredito getVista(){
		return this.vista;
	}

	private void imprimirReportes() {
		try{
			this.verificarImpresion();
			if(this.imprimirFormulario1){
				this.generarFormulario(Reportes.FORMULARIO_F01);
			}
			if(this.imprimirFormulario2){
				this.generarFormulario(Reportes.FORMULARIO_F02);
			}
			if(this.imprimirRecibo){
				this.generarRecibo();
			}
			if(this.imprimirOtorgamiento){
				this.generarReporteOtorgamiento();
			}
			if(this.imprimirConceptos){
				this.generarReporteConcepto();
			}
		
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this.vista, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		
		}
	}

	private void verificarImpresion() throws LogicaException {
		this.imprimirFormulario1= this.vista.getPnlFormulariosCredito().getCbxFormulario1().isSelected();
		this.imprimirFormulario2= this.vista.getPnlFormulariosCredito().getCbxFormulario2().isSelected();
		this.imprimirRecibo= this.vista.getPnlFormulariosCredito().getCbxRecibo().isSelected();
		this.imprimirOtorgamiento= this.vista.getPnlFormulariosCredito().getCbxOtorgamiento().isSelected();
		this.imprimirConceptos= this.vista.getPnlFormulariosCredito().getCbxConcepto().isSelected();
		int seleccionado = this.getVista().getPnlFormulariosCredito().getTblResultado().getSelectedRow();
		if(seleccionado!=-1){
			this.creditoSeleccionado=this.modeloTblCreditoReporteModel.getCredito(seleccionado);
			if((!this.imprimirFormulario1)&&(!this.imprimirFormulario2)&&(!this.imprimirRecibo)&&(!this.imprimirOtorgamiento)&&(!this.imprimirConceptos)){
				int pCodigo=97;
				String pMensaje="";
				throw new LogicaException(pCodigo, pMensaje);
			}
		}
		else{
			int pCodigo=96;
			String pMensaje="";
			throw new LogicaException(pCodigo, pMensaje);
		}
	}

	private void buscarCreditos() {
		try{
			this.actualizarModelo();
			CreditoDAOImpl locCreditoDAO= new CreditoDAOImpl();
			if(locCreditoDAO.findCreditosPorNumeroYLegajo(this.legajo, this.numeroCredito)!=null){
				this.listaCredito=locCreditoDAO.findCreditosPorNumeroYLegajo(this.legajo, this.numeroCredito);
			}
			else{
				String mensaje="No se hallaron créditos relacionados a los datos ingresados";
				JOptionPane.showMessageDialog(this.vista, mensaje, "Error", JOptionPane.INFORMATION_MESSAGE);
				this.listaCredito.clear();
			}
			this.actualizarVista();
		}
		catch (LogicaException e) {
			JOptionPane.showMessageDialog(this.vista, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void actualizarVista() {
		this.vista.getBtnCancelar().setVisible(false);
		this.vista.getBtnImprimir().setVisible(false);
		this.vista.getBtnAceptarGuardar().setText("Finalizar");
		this.modeloTblCreditoReporteModel= new TblCreditoReporteModel(this.listaCredito);
		this.vista.getPnlFormulariosCredito().getTblResultado().setModel(this.modeloTblCreditoReporteModel);
	}

	private void actualizarModelo() throws LogicaException {
		String cadenaLegajo= Utiles.nuloSiVacio(this.vista.getPnlFormulariosCredito().getTxtLegajo().getText().trim());
		String cadenaNroCredito= Utiles.nuloSiVacio(this.vista.getPnlFormulariosCredito().getTxtNroCredito().getText().trim());

		
		if((cadenaLegajo==null)&&(cadenaNroCredito==null)){
			int codigo=81;
			throw new LogicaException(codigo, "");
		}
		else{
			this.legajo=null;
			this.numeroCredito= null;
			if(cadenaLegajo!=null){
				try{
					this.legajo= Long.parseLong(cadenaLegajo);
				}
				catch (NumberFormatException e) {
					String campo="Legajo";
					int codigo=17;
					throw new LogicaException(codigo, campo);
				}
			}
			if(cadenaNroCredito!=null){
				try{
					this.numeroCredito= Integer.parseInt(cadenaNroCredito);
				}
				catch (NumberFormatException e) {
					String campo="N° de Crédito";
					int codigo=17;
					throw new LogicaException(codigo, campo);
				}
			}
		}			
	}
	
	private void finalizar() {
		this.adminCredito.mostrarPnlEstadoCuenta();	
	}
	
	private void generarFormulario(Reportes pFormulario) {
		try{
			FormulariosReciboCreditoReporte locRecibo= new FormulariosReciboCreditoReporte(this.creditoSeleccionado);
			List<FormularioRecibo> listaFormularioCredito = locRecibo.formatearRecibo();
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), pFormulario, null, listaFormularioCredito);
		}
		catch(LogicaException m){
			JOptionPane.showMessageDialog(this.vista, m.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.adminCredito.getVista(), "No se ha podido generar el reporte.", "Error",JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private void generarReporteOtorgamiento() {
		try{
			OtorgamientoCreditoReporte locReporte= new OtorgamientoCreditoReporte(this.creditoSeleccionado);
			List<CuotaOtorgamiento> listaCuotasOtorgamiento = locReporte.formatearCuotasOrganismo();
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), Reportes.OTORGAMIENTO_CREDITO, locReporte.setearParametros(), listaCuotasOtorgamiento);			
			
		}
		catch(LogicaException m){
			JOptionPane.showMessageDialog(this.vista, m.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void generarReporteConcepto() {
		try{
			OtorgamientoCreditoReporte locReporte= new OtorgamientoCreditoReporte(this.creditoSeleccionado);
			List<ConceptoNoAplicadoCuotasOtorgamiento> listaConceptosNoAplicadosACuotas= locReporte.formatearConceptosNoAplicadosACuotas();
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), Reportes.CONCEPTO_NO_APLIC_CUOTAS, locReporte.setearParametros(), listaConceptosNoAplicadosACuotas);	
		}
		catch(LogicaException m){
			JOptionPane.showMessageDialog(this.vista, m.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void generarRecibo() {
		try{
			FormulariosReciboCreditoReporte locRecibo= new FormulariosReciboCreditoReporte(this.creditoSeleccionado);
			List<FormularioRecibo> listaFormularioCredito = locRecibo.formatearRecibo();
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), Reportes.RECIBO_CREDITO, null, listaFormularioCredito);
		}
		catch(LogicaException m){
			JOptionPane.showMessageDialog(this.vista, m.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.adminCredito.getVista(), "No se ha podido generar el reporte.", "Error",JOptionPane.ERROR_MESSAGE);
		}		
	}
	
}

