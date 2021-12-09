package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.gui.personas.BusquedaPersonaFisicaDialog;
import ar.gov.cjpmv.prestamos.gui.personas.PnlDatosResponsables;

public class PnlDatosResponsablesController {
	private PnlDatosResponsables vista;
	private BusquedaPersonaFisicaController busquedaPersonasFisicas;
	
	
	private DefaultListModel modeloLista;
	
	public PnlDatosResponsablesController(JDialog pPadre){
		this(new PnlDatosResponsables(),pPadre);
	}
	
	public PnlDatosResponsablesController(PnlDatosResponsables vista, JDialog pPadre){
		this.vista = vista;
		
		this.modeloLista = new DefaultListModel();
		this.busquedaPersonasFisicas = new BusquedaPersonaFisicaController(pPadre);
		this.vista.getLLista().setModel(this.modeloLista);
		this.vista.getLLista().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.vista.getBtnEliminar().setEnabled(false);
		
		this.inicializarEventos();
		
	}
	
	private void inicializarEventos() {
		this.vista.getLLista().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (vista.getLLista().getSelectedIndex()==-1){
					vista.getBtnEliminar().setEnabled(false);
				}
				else{
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
	}
	
	
	public void agregar(){
		this.busquedaPersonasFisicas.setVisible(true);
		PersonaFisica locPersonaFisica = this.busquedaPersonasFisicas.getPersonaSeleccionada();
		if (locPersonaFisica!=null){
			if (!this.modeloLista.contains(locPersonaFisica)){
				this.modeloLista.addElement(locPersonaFisica);
			}
		}
	}
	
	public void eliminar(){
		int indice = this.vista.getLLista().getSelectedIndex();
		if (indice !=-1){
			PersonaFisica locPersonaFisica = (PersonaFisica)this.modeloLista.get(indice);
			int pregunta = JOptionPane.showConfirmDialog(this.vista, 
					"¿Está seguro que desea quitar de la lista de socios a "+locPersonaFisica+"?",
					"Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (pregunta==JOptionPane.YES_OPTION){
				this.modeloLista.remove(indice);
			}
		}
	}

	
	

	public void setModelo(List<PersonaFisica> pModeloPersonaFisica){
		this.modeloLista.clear();
		if (pModeloPersonaFisica!=null){
			for (PersonaFisica cadaPersonaFisica:  pModeloPersonaFisica){
				this.modeloLista.addElement(cadaPersonaFisica);
			}
		}
	}

	public List<PersonaFisica> getModelo(){
		List<PersonaFisica> locListaPersonasFisicas = new ArrayList<PersonaFisica>();
		for (Object cadaObjeto : this.modeloLista.toArray()){
			locListaPersonasFisicas.add((PersonaFisica)cadaObjeto);
		}
		return locListaPersonasFisicas;
	}
	
	public static void main(String[] args){
		JDialog locDialogo = new JDialog();
		locDialogo.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		
		locDialogo.add(new PnlDatosResponsablesController(new PnlDatosResponsables(),locDialogo).vista);
		locDialogo.pack();
		locDialogo.setVisible(true);
	}
}
