package ar.gov.cjpmv.prestamos.gui.creditos.datos.solicitante.controllers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;
import ar.gov.cjpmv.prestamos.gui.Principal;
import ar.gov.cjpmv.prestamos.gui.comunes.utiles.EstadoPersonaFormatter;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlDatosSolicitanteGarante;
import ar.gov.cjpmv.prestamos.gui.creditos.components.LblEditar;
import ar.gov.cjpmv.prestamos.gui.creditos.components.LblEstadoCuenta;
import ar.gov.cjpmv.prestamos.gui.creditos.components.LblNuevo;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.EstadoCuentaController;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CreditoModel;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.solicitante.PnlDatosSolicitante;
import ar.gov.cjpmv.prestamos.gui.personas.PnlBusquedaPersonaView;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.AdminPersona;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.busqueda.BusquedaPersonaController;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlDatosSolicitanteController {
	private PnlDatosSolicitante vista;
	private BusquedaPersonaController busquedaPersonasController;
	
	private CreditoModel modelo;
	
	private JLabel lblNuevo = new LblNuevo();
	private JLabel lblEditar = new LblEditar();
	private JLabel lblMostrarEstadoCuenta = new LblEstadoCuenta();
	
	public PnlDatosSolicitanteController(
			PnlDatosSolicitante pPnlDatosSolicitante, CreditoModel modelo) {
		this.modelo = modelo;
		this.busquedaPersonasController = new BusquedaPersonaController(pPnlDatosSolicitante.getPnlBusquedaPersonaView());
		this.vista = pPnlDatosSolicitante;
		this.inicializarVista();
		this.actualizarVista();
		this.inicializarEventos();
	}

	public PnlDatosSolicitanteController(){
		this.busquedaPersonasController = new BusquedaPersonaController();
		this.vista = new PnlDatosSolicitante(this.busquedaPersonasController.getVista());
		this.inicializarVista();
		this.actualizarVista();
		this.inicializarEventos();
	}
	
	//TODO mostrarEstadoCuenta está repedido acá y en ConsultaEstadoCuenta
	private void mostrarEstadoCuenta() {
		try{
			int seleccionado = this.busquedaPersonasController.getVista().getTblBusqueda().getSelectedRow();
			if (seleccionado!=-1){
				Persona locPersona = this.busquedaPersonasController.getModelo().getPersonaSeleccionada(seleccionado);
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
	
	private void inicializarEventos() {
		this.vista.getBtnSeleccionar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarModelo();
				actualizarVista();
			}
		});
		this.busquedaPersonasController.getVista().getTblBusqueda().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				actualizarSeleccionTabla();			
			}
		});
		this.busquedaPersonasController.getVista().getTblBusqueda().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2){
					actualizarModelo();
					actualizarVista();
				}
			}
		});
		this.vista.getPnlDatosSolicitanteGarante().getBtnEliminar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarDatos();
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
				int filaSeleccionada = busquedaPersonasController.getVista().getTblBusqueda().getSelectedRow();
				if (filaSeleccionada!=-1){
					Persona locPersona = busquedaPersonasController.getModelo().getPersonaSeleccionada(filaSeleccionada);
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
	
	private void actualizarModelo(){
		int filaSeleccionada = this.busquedaPersonasController.getVista().getTblBusqueda().getSelectedRow();
		if (filaSeleccionada!=-1){
			this.modelo.setPersona(this.busquedaPersonasController.getModelo()
					.getPersonaSeleccionada(filaSeleccionada));
		}
	}


	private void actualizarVista() {
		if (this.modelo.getPersona() !=null){
			PnlDatosSolicitanteGarante locPnl = this.vista.getPnlDatosSolicitanteGarante();
			locPnl.getLblApellidoRazon().setText(Utiles.vacioSiNulo(this.modelo.getPersona().getNombreYApellido()));
			locPnl.getLblCui().setText(Utiles.getCuiFormateado(this.modelo.getPersona().getCui()));
			if (this.modelo.getPersona() instanceof PersonaFisica){
				this.actualizarDatosPersonaFisica();
			}
			else{
				this.actualizarDatosPersonaJuridica();
			}
		}
		else{
			this.limpiarDatos();
		}
		this.actualizarSeleccionTabla();
	}

	private void limpiarDatos(){
		this.modelo.setPersona(null);
		PnlDatosSolicitanteGarante locPnl = this.vista.getPnlDatosSolicitanteGarante();
		locPnl.getLblApellidoRazon().setText("");
		locPnl.getLblCui().setText("  -        - ");
		locPnl.getLblEstado().setText("");
		locPnl.getLblLegajo().setText("");
		locPnl.getLblNumeroDocumento().setText("");
		locPnl.getLblTipoDocumento().setText("");
	}

	private void actualizarDatosPersonaFisica() {
		PnlDatosSolicitanteGarante locPnl = this.vista.getPnlDatosSolicitanteGarante();
		PersonaFisica locPersonaFisica = (PersonaFisica)this.modelo.getPersona();
		locPnl.getLblLegajo().setText(Utiles.cadenaVaciaSiNulo(locPersonaFisica.getLegajo()));
		locPnl.getLblNumeroDocumento().setText(Utiles.cadenaVaciaSiNulo(locPersonaFisica.getNumeroDocumento()));
		locPnl.getLblTipoDocumento().setText(Utiles.vacioSiNulo(locPersonaFisica.getTipoDocumento().getDescripcion()));
		locPnl.getLblEstado().setText(EstadoPersonaFormatter.getInstance(locPersonaFisica.getEstado()).getTexto());
	}

	private void actualizarDatosPersonaJuridica() {
		PnlDatosSolicitanteGarante locPnl = this.vista.getPnlDatosSolicitanteGarante();
		PersonaJuridica locPersonaJuridica = (PersonaJuridica)this.modelo.getPersona();
		//Limpio lo específico de la persona física
		locPnl.getLblLegajo().setText("");
		locPnl.getLblNumeroDocumento().setText("");
		locPnl.getLblTipoDocumento().setText("");
		locPnl.getLblEstado().setText(this.getTexto(locPersonaJuridica.getEstado()));
	}

	private String getTexto(EstadoPersonaJuridica estado) {
		return EstadoPersonaFormatter.getInstance(estado).getTexto();
	}

	private void inicializarVista() {
		this.actualizarIconoEliminar();
		PnlBusquedaPersonaView pnlBusqueda = this.busquedaPersonasController.getVista();
		Box locLayout = Box.createHorizontalBox();
		locLayout.add(this.lblNuevo);
		locLayout.add(Box.createHorizontalStrut(10));
		locLayout.add(this.lblEditar);
		locLayout.add(Box.createHorizontalStrut(10));
		locLayout.add(this.lblMostrarEstadoCuenta);
		pnlBusqueda.getPnlBotonera().removeAll();
		pnlBusqueda.getPnlBotonera().add(locLayout);
		this.vista.getBtnSeleccionar().setEnabled(false);
	}

	
	private void actualizarIconoEliminar() {
		ResourceMap locMap = Application.getInstance(Principal.class).getContext().getResourceMap(this.getClass());
		this.vista.getPnlDatosSolicitanteGarante().getBtnEliminar().setIcon(locMap.getIcon("btnEliminar.icon"));
	}

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		PnlDatosSolicitanteController locBusquedaPersonas = new PnlDatosSolicitanteController();
		
		JFrame locFrame = new JFrame();
		locFrame.add(locBusquedaPersonas.getVista(),BorderLayout.CENTER);
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		locFrame.pack();
		locFrame.setVisible(true);
	}

	public PnlDatosSolicitante getVista() {
		return vista;
	}

	public void setPersonaSeleccionada(Persona personaSeleccionada){
		this.modelo.setPersona(personaSeleccionada);
		this.actualizarVista();
	}
	
	public void actualizarSeleccionTabla(){
		boolean seleccionado = this.busquedaPersonasController.getVista().getTblBusqueda().getSelectedRow() != -1;
		this.lblEditar.setEnabled(seleccionado);
		this.lblMostrarEstadoCuenta.setEnabled(seleccionado);
		this.getVista().getBtnSeleccionar().setEnabled(seleccionado);
	}

	private void agregarPersonaFisica() {
		AdminPersona locAdminPersona = new AdminPersona(null);
		locAdminPersona.setCerrarAlGuardar(true);
		locAdminPersona.mostrarAltaPersonaFisica();
		locAdminPersona.show();
		this.busquedaPersonasController.refrescarBusqueda();
		actualizarVista();
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
			actualizarVista();
		}
	}
}
