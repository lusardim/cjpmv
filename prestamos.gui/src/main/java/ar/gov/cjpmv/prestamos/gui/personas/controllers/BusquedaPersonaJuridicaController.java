package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;
import ar.gov.cjpmv.prestamos.gui.personas.BusquedaPersonaJuridicaDialog;
import ar.gov.cjpmv.prestamos.gui.personas.PnlParametrosBusquedaPersonaJuridica;
import ar.gov.cjpmv.prestamos.gui.personas.model.BusquedaPersonaJuridicaModel;
import ar.gov.cjpmv.prestamos.gui.personas.model.EstadoPersonaJuridicaCellRenderer;
import ar.gov.cjpmv.prestamos.gui.personas.model.TblPersonaJuridicaModel;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class BusquedaPersonaJuridicaController {
	private BusquedaPersonaJuridicaDialog vista;
	private TblPersonaJuridicaModel modeloTabla;
	private BusquedaPersonaJuridicaModel modeloBusqueda;
	
	private PersonaJuridica personaSeleccionada;
	
	public BusquedaPersonaJuridicaController(JDialog pPadre) {
		this.vista = new BusquedaPersonaJuridicaDialog(pPadre, true);
		this.vista.setTitle("Búsqueda de Persona Jurídica");
		this.modeloBusqueda = new BusquedaPersonaJuridicaModel();
		this.modeloTabla = new TblPersonaJuridicaModel(this.modeloBusqueda);
		
		this.inicializarVista();
		this.inicializarModelos();
		this.actualizarVista();
		this.inicializarEventos();
		this.vista.getBtnSeleccionar().setEnabled(false);
	}


	private void inicializarVista() {
		this.vista.getPnlResultadoBusqueda().getBtnModificar().setVisible(false);
		this.vista.getPnlResultadoBusqueda().getBtnEliminar().setVisible(false);
		this.vista.getPnlResultadoBusqueda().getBtnAgregar().setVisible(false);
		
	}


	private void inicializarEventos() {
		this.vista.getPnlParametrosBusquedaPersonaJuridica().getBtnBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vista.dispose();
			}
		});
		this.vista.getBtnSeleccionar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				seleccionar();
			}
		});
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isSelectedRow() && e.getClickCount()==2){
					seleccionar();
				}
			}
		});
		
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (isSelectedRow() && e.getKeyCode() == KeyEvent.VK_ENTER){
					seleccionar();
				}
			}
		});
		
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (isSelectedRow()){
					vista.getBtnSeleccionar().setEnabled(true);
				}
				else{
					vista.getBtnSeleccionar().setEnabled(false);
				}
			}
		});
	}

	private boolean isSelectedRow(){
		return vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().getSelectedRow()!=-1;
	}

	private void seleccionar() {
		int locSeleccionado = this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().getSelectedRow();
		if (locSeleccionado  !=-1 ){
			this.personaSeleccionada = this.modeloTabla.getPersonaJuridica(locSeleccionado);
			this.vista.dispose();
		}
	}


	private void buscar(){
		this.actualizarModelo();
		this.modeloTabla.actualizarBusqueda();
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().requestFocus();
	}
	
	private void actualizarVista() {
		PnlParametrosBusquedaPersonaJuridica locPanel = this.vista.getPnlParametrosBusquedaPersonaJuridica();
		locPanel.getTxtCuit().setText(Utiles.cadenaVaciaSiNulo(this.modeloBusqueda.getCuil()));
		locPanel.getTxtRazonSocial().setText(Utiles.vacioSiNulo(this.modeloBusqueda.getRazonSocial()));
		locPanel.getDtcFinActividades().setDate(this.modeloBusqueda.getFechaFinActividades());
		locPanel.getDtcInicioActividades().setDate(this.modeloBusqueda.getFechaFinActividades());
		locPanel.getCbxEstado().setSelectedItem(this.modeloBusqueda.getEstado());
	}

	private void actualizarModelo(){
		PnlParametrosBusquedaPersonaJuridica locPanel = this.vista.getPnlParametrosBusquedaPersonaJuridica();
		Long locValor;
	
		AbstractFormatter locFormater = locPanel.getTxtCuit().getFormatter();
		try {
			locValor = Long.parseLong(locFormater.stringToValue(locPanel.getTxtCuit().getText()).toString());
		} catch (ParseException e) {
			locValor = null; 
		}
		this.modeloBusqueda.setCuil(locValor);
		this.modeloBusqueda.setRazonSocial(Utiles.nuloSiVacio(locPanel.getTxtRazonSocial().getText()));
		this.modeloBusqueda.setEstado((EstadoPersonaJuridica)locPanel.getCbxEstado().getSelectedItem());
		this.modeloBusqueda.setFechaInicioActividades(locPanel.getDtcInicioActividades().getDate());
		this.modeloBusqueda.setFechaFinActividades(locPanel.getDtcFinActividades().getDate());
		
	}
	
	private void inicializarModelos() {
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().setModel(this.modeloTabla);
		
		DefaultComboBoxModel locComboBoxModel = new DefaultComboBoxModel();
		locComboBoxModel.addElement(null);
		for (EstadoPersonaJuridica cadaEstado : EstadoPersonaJuridica.values()){
			locComboBoxModel.addElement(cadaEstado);
		}
		this.vista.getPnlParametrosBusquedaPersonaJuridica().getCbxEstado().setModel(locComboBoxModel);
		this.vista.getPnlParametrosBusquedaPersonaJuridica().getCbxEstado().setRenderer(new EstadoPersonaJuridicaCellRenderer());
	
	}
	
	
	public static void main(String[] args){
		
		BusquedaPersonaJuridicaController locController = new BusquedaPersonaJuridicaController(new JDialog());
		locController.vista.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		locController.setVisible(true);
		System.out.println(locController.getPersonaSeleccionada());
	}

	public void setVisible(boolean b) {
		this.vista.pack();
		this.vista.setVisible(true);
	}


	public PersonaJuridica getPersonaSeleccionada() {
		return personaSeleccionada;
	}
	
}
