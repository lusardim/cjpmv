package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.gui.comunes.LegajoCellRenderer;
import ar.gov.cjpmv.prestamos.gui.creditos.components.LblEstadoCuenta;
import ar.gov.cjpmv.prestamos.gui.personas.PnlBusquedaPersonaView;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.busqueda.BusquedaPersonaController;
import ar.gov.cjpmv.prestamos.gui.personas.model.BusquedaPersonasModel;

public class ConsultaEstadoCuentaController {
	private BusquedaPersonaController busquedaPersona;
	private JLabel lblEstadoCuenta;
	
	public ConsultaEstadoCuentaController(){
		this.busquedaPersona = new BusquedaPersonaController(new BusquedaPersonasModel(){
			@Override
			public void refrescarBusqueda() {
				List<Persona> listaPersonas = this.getLogica().findListaPersonasConCuentaCorriente(
						this.getLegajo(), 
						this.getCuip(), 
						this.getTipoDocumento(), 
						this.getNumeroDocumento(),
						this.getApellido(),
						this.getEstado()
					);
				setListaPersonas(listaPersonas);
			}
		});
		this.inicializarVista();
		this.inicializarEventos();
	}
	
	private void inicializarEventos() {
		this.lblEstadoCuenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarEstadoCuenta();
			}
		});
		
		this.busquedaPersona.getVista().getTblBusqueda().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				actualizarVista();
			}
		});
		
	}

	private void actualizarVista(){
		this.busquedaPersona.actualizarVista();
		if (this.getVista().getTblBusqueda().getSelectedRow()==-1){
			this.lblEstadoCuenta.setEnabled(false);
		}	
		else{
			this.lblEstadoCuenta.setEnabled(true);
		}
	}
	
	private void inicializarVista() {
		Box locLayout = Box.createHorizontalBox();
		locLayout.add(this.getLblEstadoCuenta());
		this.busquedaPersona.getVista().getPnlBotonera().removeAll();
		this.busquedaPersona.getVista().getPnlBotonera().add(locLayout);
		this.lblEstadoCuenta.setEnabled(false);
		getVista().getTblBusqueda().getColumn("Legajo").setCellRenderer(new LegajoCellRenderer());
	}

	private Component getLblEstadoCuenta() {
		if (this.lblEstadoCuenta==null){
			this.lblEstadoCuenta = new LblEstadoCuenta();
		}
		return lblEstadoCuenta;
	}

	
	//TODO mostrarEstadoCuenta está repetido acá y en PnlDatosSolicitanteController :D
	private void mostrarEstadoCuenta() {
		try{
			int seleccionado = this.getVista().getTblBusqueda().getSelectedRow();
			if (seleccionado!=-1){
				Persona locPersona = this.busquedaPersona.getModelo().getPersonaSeleccionada(seleccionado);
				EstadoCuentaController controlador= new EstadoCuentaController(locPersona, null);
				if(controlador.getMostrarVista()){
					controlador.getVista().setTitle("Estado de Cuenta de "+locPersona.getNombreYApellido());
					controlador.setLocationRelativeTo(this.getVista());
					controlador.setVisible(true);
				}
				else{
					String locMensaje="La persona seleccionada no posee créditos ni garantía en curso.";
					String locTitulo="Error";
					JOptionPane.showMessageDialog(this.getVista(),locMensaje, locTitulo, JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		catch( LogicaException e) {
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.getVista(), pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public PnlBusquedaPersonaView getVista() {
		return busquedaPersona.getVista();
	}
	
	
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		ConsultaEstadoCuentaController locBusquedaPersonas = new ConsultaEstadoCuentaController();
		
		JFrame locFrame = new JFrame();
		locFrame.add(locBusquedaPersonas.getVista(),BorderLayout.CENTER);
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		locFrame.pack();
		locFrame.setVisible(true);
	}



}
