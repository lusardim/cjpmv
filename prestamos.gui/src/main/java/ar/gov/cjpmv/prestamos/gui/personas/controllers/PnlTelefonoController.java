package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.persistence.Telefono;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlListadoView;
import ar.gov.cjpmv.prestamos.gui.personas.model.TelefonoListCellRenderer;

public class PnlTelefonoController {
	private PnlListadoView vista;
	
	private JDialog padre;
	private AMTelefonoController ventanaAM;
	
	private List<Telefono> modelo=new ArrayList<Telefono>();
	
	public PnlTelefonoController(PnlListadoView vista,JDialog pPadre){
		this.padre = pPadre;
		this.vista = vista;
		this.vista.getLblTitulo().setText("Teléfonos");
		this.vista.getLLista().setCellRenderer(new TelefonoListCellRenderer());
		this.inicializarEventos();
		vista.getBtnModificar().setEnabled(false);
		vista.getBtnEliminar().setEnabled(false);
		
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
		this.vista.getBtnModificar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificar();
			}
		});
		this.vista.getBtnEliminar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminar();				
			}
		});
		
	}

	
	public void agregar(){
		AMTelefonoController ventana = this.getVentanaAM();
		ventana.setModelo(null);
		ventana.setTitulo("Agregar Teléfono");
		ventana.setVisible(true);
		if (ventana.getModelo()!=null){
			if (!this.getModelo().contains(ventana.getModelo())){
				this.modelo.add(ventana.getModelo());
				this.refrescarLista();
			}
		}
	}
	
	public void modificar(){
		if (this.vista.getLLista().getSelectedValue()!=null){
			Telefono locTelefono = (Telefono)this.vista.getLLista().getSelectedValue();
			AMTelefonoController ventana = this.getVentanaAM();
			ventana.setTitulo("Modificar Teléfono");
			ventana.setModelo(locTelefono);
			ventana.setVisible(true);
			vista.getLLista().repaint();
		}
	}
	
	
	public void eliminar(){
		if (this.vista.getLLista().getSelectedIndex()!=-1){
			Telefono locTelefono = (Telefono)this.vista.getLLista().getSelectedValue();
			
			int valor = JOptionPane.showConfirmDialog(this.vista, "¿Está seguro que desea elimiar el teléfono: "+locTelefono.toString()+"?","Eliminar", 
							JOptionPane.YES_OPTION);
			if (valor == JOptionPane.YES_OPTION){
				this.modelo.remove(locTelefono);
				this.refrescarLista();
			}
		}
	}

	private void refrescarLista() {
		this.vista.getLLista().setListData(this.modelo.toArray());
	}



	public static void main(String[] args){
		JDialog locDialog = new JDialog();
		PnlTelefonoController loc  = new PnlTelefonoController(new PnlListadoView(), locDialog);
		locDialog.add(loc.vista);
		locDialog.pack();
		locDialog.setVisible(true);
		locDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
	}



	public List<Telefono> getModelo() {
		return modelo;
	}

	public void setModelo(List<Telefono> modelo) {
		this.modelo = modelo;
		this.refrescarLista();
	}

	public AMTelefonoController getVentanaAM() {
		if (ventanaAM == null){
			this.ventanaAM = new AMTelefonoController(this.padre);
		}	
		return ventanaAM;
	}



	
}
