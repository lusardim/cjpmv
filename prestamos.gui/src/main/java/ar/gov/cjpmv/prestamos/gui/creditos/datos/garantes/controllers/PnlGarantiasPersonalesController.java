package ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.controllers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlDatosSolicitanteGarante;
import ar.gov.cjpmv.prestamos.gui.creditos.components.LblEditar;
import ar.gov.cjpmv.prestamos.gui.creditos.components.LblEstadoCuenta;
import ar.gov.cjpmv.prestamos.gui.creditos.components.LblNuevo;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.EstadoCuentaController;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.PnlGarantiaPersonal;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.model.GarantiasPersonalesModel;
import ar.gov.cjpmv.prestamos.gui.personas.PnlBusquedaPersonaView;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.AdminPersona;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.busqueda.BusquedaPersonaController;


public class PnlGarantiasPersonalesController{
	private PnlGarantiaPersonal vista;
	private GarantiasPersonalesModel modelo;
	private BusquedaPersonaController busquedaPersonaController;
	
	private Credito credito;
	
	private Map<GarantiaPersonal, PnlDatosSolicitanteGaranteController> listaSeleccionados;
	
	//BOTONERA
	private JLabel lblNuevo = new LblNuevo();
	private JLabel lblEditar = new LblEditar();
	private JLabel lblMostrarEstadoCuenta = new LblEstadoCuenta();

	public PnlGarantiasPersonalesController(Credito pCredito){
		vista = new PnlGarantiaPersonal();
		credito = pCredito;
		
		busquedaPersonaController = new BusquedaPersonaController(vista.getPnlBusquedaPersonaView());
		this.inicializarVista();
		this.inicializarModelo();
		this.inicializarEventos();
	}
	
	private void inicializarVista() {
		PnlBusquedaPersonaView pnlBusqueda = this.busquedaPersonaController.getVista();
		Box locLayout = Box.createHorizontalBox();
		locLayout.add(this.lblNuevo);
		locLayout.add(Box.createHorizontalStrut(10));
		locLayout.add(this.lblEditar);
		locLayout.add(Box.createHorizontalStrut(10));
		locLayout.add(this.lblMostrarEstadoCuenta);
		pnlBusqueda.getPnlBotonera().removeAll();
		pnlBusqueda.getPnlBotonera().add(locLayout);		
		this.lblEditar.setEnabled(false);
		this.lblMostrarEstadoCuenta.setEnabled(false);
	}

	private void inicializarModelo() {
		this.modelo = new GarantiasPersonalesModel(credito);
		this.listaSeleccionados = new HashMap<GarantiaPersonal, PnlDatosSolicitanteGaranteController>();
	}

	private void inicializarEventos() {
		this.vista.getBtnSeleccionar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionar();
			}
		});
		this.vista.getPnlBusquedaPersonaView().getTblBusqueda().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					seleccionar();
				}
			}
		});
		this.busquedaPersonaController.getVista().getTblBusqueda().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				actualizarSeleccionTabla();			
			}
		});
		this.lblNuevo.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				agregarPersonaFisica();
			}
		});
		this.lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaSeleccionada = busquedaPersonaController.getVista().getTblBusqueda().getSelectedRow();
				if (filaSeleccionada!=-1){
					Persona locPersona = busquedaPersonaController.getModelo().getPersonaSeleccionada(filaSeleccionada);
					editarPersona(locPersona);
				}
				
			}
		});
		this.lblMostrarEstadoCuenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarEstadoCuenta();
			}
		});
	}

	public PnlGarantiaPersonal getVista() {
		return vista;
	}

	private void seleccionar() {
		PersonaFisica persona = this.getPersonaSeleccionada();
		if (persona != null){
			this.agregarGarantia(persona);
		}
	}
	
	/**
	 * Agrega un panel con una garantía
	 * @param persona
	 */
	private void agregarGarantia(PersonaFisica persona) {
		final GarantiaPersonal locGarantiaPersonal =  this.modelo.addGarantia(persona);
		if (locGarantiaPersonal != null ){
			final PnlDatosSolicitanteGaranteController locPnlDatosGaranteController  = new PnlDatosSolicitanteGaranteController(locGarantiaPersonal);
			final Component locStrut = Box.createVerticalStrut(10);
			
			this.vista.getPnlListaGarantias().add(locPnlDatosGaranteController.getVista());
			this.vista.getPnlListaGarantias().add(locStrut);
			
			locPnlDatosGaranteController.getVista().getBtnEliminar().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modelo.removeGarantia(locGarantiaPersonal);
					vista.getPnlListaGarantias().remove(locPnlDatosGaranteController.getVista());
					vista.getPnlListaGarantias().remove(locStrut);
					vista.validate();
					vista.repaint();
				}
			});
			
			listaSeleccionados.put(locGarantiaPersonal, locPnlDatosGaranteController);
		}
		this.vista.validate();
	}

	private PersonaFisica getPersonaSeleccionada() {
		int row = this.vista.getPnlBusquedaPersonaView().getTblBusqueda().getSelectedRow();
		if (row!=-1){
			Persona locPersona = this.busquedaPersonaController.getModelo().getModeloTabla().getPersona(row);
			if (locPersona instanceof PersonaFisica){
				return (PersonaFisica)locPersona;
			}
		}
		return null;
	}
	
	private void agregarPersonaFisica() {
		AdminPersona locAdminPersona = new AdminPersona(null);
		locAdminPersona.setCerrarAlGuardar(true);
		locAdminPersona.mostrarAltaPersonaFisica();
		locAdminPersona.show();
		this.busquedaPersonaController.refrescarBusqueda();
	}
	
	private void editarPersona(Persona pPersona) {
		if (pPersona!=null){
			AdminPersona locAdminPersona = new AdminPersona(null);
			locAdminPersona.setCerrarAlGuardar(true);
			if (pPersona instanceof PersonaFisica){
				locAdminPersona.mostrarEditarPersonaFisica((PersonaFisica)pPersona);
			}
			else{
				locAdminPersona.mostrarEditarPersonaJuridica((PersonaJuridica)pPersona);
			}
			locAdminPersona.show();
			locAdminPersona.getVista().addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					actualizarGarante();	
				}
			});
		}
	}
	
	/**
	 * Si una persona ya está registrada como garante, entonces es actualizada
	 * @param pPersona
	 */
	private void actualizarGarante() {
		for (Component cadaComponente : this.getVista().getPnlListaGarantias().getComponents()){
			if (cadaComponente instanceof PnlDatosSolicitanteGarante){
				PnlDatosSolicitanteGarante vista = (PnlDatosSolicitanteGarante)cadaComponente;
				if (vista.getControlador()!=null){
					vista.getControlador().actualizarVista();
				}
			}
		}
	}

	//TODO mostrarEstadoCuenta está repedido acá y en ConsultaEstadoCuenta
	private void mostrarEstadoCuenta() {
		try{
			int seleccionado = this.busquedaPersonaController.getVista().getTblBusqueda().getSelectedRow();
			if (seleccionado!=-1){
				Persona locPersona = this.busquedaPersonaController.getModelo().getPersonaSeleccionada(seleccionado);
				EstadoCuentaController controlador= new EstadoCuentaController(locPersona, null);
				controlador.getVista().setTitle("Estado de Cuenta de "+locPersona.getNombreYApellido());
				controlador.setLocationRelativeTo(this.getVista());
				controlador.setVisible(true);

			}
		}
		catch( LogicaException e) {
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.getVista(), pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actualizarSeleccionTabla(){
		boolean seleccionado = this.busquedaPersonaController.getVista().getTblBusqueda().getSelectedRow() != -1;
		this.lblEditar.setEnabled(seleccionado);
		this.lblMostrarEstadoCuenta.setEnabled(seleccionado);
		this.getVista().getBtnSeleccionar().setEnabled(seleccionado);
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public static void main(String[] args){
		GestorPersitencia.getInstance();
		JFrame locFrame = new JFrame();
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		locFrame.add(new PnlGarantiasPersonalesController(new Credito()).getVista(), BorderLayout.CENTER);
		locFrame.pack();
		locFrame.setVisible(true);
	}

	public GarantiasPersonalesModel getModelo() {
		return modelo;
	}

	public Credito getCredito() {
		return credito;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public void removeGarantia(GarantiaPersonal garantiaPersonal) {
		PnlDatosSolicitanteGaranteController locPnl = listaSeleccionados.get(garantiaPersonal);
		ActionListener[] locLista = locPnl.getVista().getBtnEliminar().getActionListeners();
		for (ActionListener cadaEvento : locLista){
			cadaEvento.actionPerformed(new ActionEvent(this, 1, "click"));
		}
	}

}
