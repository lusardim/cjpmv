package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlListadoView;
import ar.gov.cjpmv.prestamos.gui.configuracion.controllers.ABMConfiguracionControllers;
import ar.gov.cjpmv.prestamos.gui.personas.model.AMMailModel;

public class PnlEmailsController {
	
	private JDialog padre;
	private PnlListadoView vista;
	private ABMConfiguracionControllers ventana;
	private DefaultListModel modeloLista;
	
	public PnlEmailsController(PnlListadoView pVista,JDialog pPadre){
		this.padre = pPadre;
		this.vista = pVista;
		this.vista.getLblTitulo().setText("Emails");
		this.modeloLista = new DefaultListModel();
		this.vista.getLLista().setModel(this.modeloLista);
		this.vista.getLLista().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		vista.getBtnModificar().setEnabled(false);
		vista.getBtnEliminar().setEnabled(false);
		
		this.inicializarEventos();
	}
	
	
	private void inicializarEventos() {
		this.vista.getLLista().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (vista.getLLista().getSelectedIndex()==-1){
					vista.getBtnModificar().setEnabled(false);
					vista.getBtnEliminar().setEnabled(false);
				}
				else{
					vista.getBtnModificar().setEnabled(true);
					vista.getBtnEliminar().setEnabled(true);
				}
			}
		});
		this.vista.getBtnAgregar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				agregar();
			}
		});
		this.vista.getBtnEliminar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		this.vista.getBtnModificar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificar();
				
			}
		});

	}

	
	public void agregar(){
		ABMConfiguracionControllers ventana = this.getVentana();
		ventana.setTitulo("Agregar correo electrónico");
		AMMailModel locModelo = new AMMailModel();
		ventana.setModelo(locModelo);
		ventana.setVisible(true);
		if (locModelo.isAceptado()){
			if (!this.modeloLista.contains(locModelo.getDescripcion())){
				this.modeloLista.addElement(locModelo.getDescripcion());
			}
		}
	}
	
	public void modificar(){
		if (this.vista.getLLista().getSelectedValue()!=null){
			String mail = (String)this.vista.getLLista().getSelectedValue();
			ABMConfiguracionControllers ventana = this.getVentana();
			ventana.setTitulo("Modificar correo electrónico");
			AMMailModel locModelo = new AMMailModel(mail);
			ventana.setModelo(locModelo);
			ventana.setVisible(true);
			if (locModelo.isAceptado()){
				this.modeloLista.remove(this.vista.getLLista().getSelectedIndex());
				this.modeloLista.addElement(locModelo.getDescripcion());
			}
		}
	}
	
	
	public void eliminar(){
		if (this.vista.getLLista().getSelectedIndex()!=-1){
			String correoSeleccionado = (String)this.vista.getLLista().getSelectedValue();
			int valor = JOptionPane.showConfirmDialog(this.vista, "¿Está seguro que desea elimiar el correo: "+correoSeleccionado+"?","Eliminar", 
							JOptionPane.YES_OPTION);
			if (valor == JOptionPane.YES_OPTION){
				this.modeloLista.removeElement(correoSeleccionado);
			}
		}
	}
	
	
	public PnlListadoView getVista() {
		return vista;
	}

	public void setVista(PnlListadoView vista) {
		this.vista = vista;
	}


	public ABMConfiguracionControllers getVentana() {
		if (ventana == null){
			ventana = new ABMConfiguracionControllers(this.padre);
		}
		return ventana;
	}
	

	public void setModelo(List<String> pModelo) {
		this.modeloLista.clear();
		for (String cadaDato : pModelo){
			this.modeloLista.addElement(cadaDato);
		}
	}


	public List<String> getModelo() {
		List<String> locLista = new ArrayList<String>();
		for (Object cadaObj: this.modeloLista.toArray()){
			locLista.add(cadaObj.toString());
		}
		
		return locLista;
	}
	
	
}
