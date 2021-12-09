package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.business.dao.LocalidadDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion;
import ar.gov.cjpmv.prestamos.gui.personas.model.DepartamentoListCellRenderer;
import ar.gov.cjpmv.prestamos.gui.personas.model.ProvinciaListCellRenderer;

public class AdminLocalidad {
	
	public PnlDchaConfiguracion vista;
	public String descripcion;
	public List<Localidad> localidad;
	public LocalidadDAO localidadDAO;
	
	public AdminLocalidad(){
		this.localidadDAO= new LocalidadDAO();
		vista= new PnlDchaConfiguracion();
		this.vista.getLLista().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.inicializarEventos();
		this.inicializarVista();		
	}
	
	private void inicializarVista() {
		this.actualizarModelo();
		this.actualizarVista();
	}
	

	private void inicializarEventos() {
		this.vista.getBtnBuscar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();	
			}
		});
		this.vista.getLLista().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				validarBtnModificarEliminar();	
			}
		});
		
		this.vista.getBtnAgregar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				agregar();
			}
		});
		
		this.vista.getBtnModificar().addActionListener(new ActionListener(){
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

	private void actualizarVista() {
		this.vista.getLblTitulo().setText("Localidades");
		this.vista.getTxtTitulo().setText("Configuración: Localidad");
		this.vista.getTxtDescripcion().setText(this.descripcion);
		this.vista.getLLista().setListData(this.localidad.toArray());
		this.vista.getLLista().setCellRenderer(new DepartamentoListCellRenderer());
		this.validarBtnModificarEliminar();
	}

	private void actualizarModelo() {
		this.descripcion= this.vista.getTxtDescripcion().getText();
		String locDescripcion= this.vista.getTxtDescripcion().getText().trim();
		this.descripcion= (locDescripcion.isEmpty())?null:locDescripcion;
		this.localidad= localidadDAO.findListaLocalidad(this.descripcion);
	}

	

	private void buscar() {
		this.actualizarModelo();
		this.actualizarVista();		
	}
	
	private void validarBtnModificarEliminar() {
		boolean valor= !this.vista.getLLista().isSelectionEmpty();
		this.vista.getBtnModificar().setEnabled(valor);
		this.vista.getBtnEliminar().setEnabled(valor);
	}
	
	private void agregar() {
		LocalidadDAO locLocalidadDAO=this.localidadDAO;
		ABMConfiguracionLocalidad locABMConfiguracionLocaclidad= new ABMConfiguracionLocalidad(null, locLocalidadDAO);
		locABMConfiguracionLocaclidad.setTitulo("Agregar Localidad");
		locABMConfiguracionLocaclidad.setLocationRelativeTo(this.vista);
		locABMConfiguracionLocaclidad.setVisible(true);
		buscar();
	}
	
	private void modificar() {
		Localidad locLocalidad=(Localidad)this.vista.getLLista().getSelectedValue();
		LocalidadDAO locLocalidadDAO=this.localidadDAO;
		ABMConfiguracionLocalidad locABMConfiguracionLocalidad= new ABMConfiguracionLocalidad(null, locLocalidadDAO, locLocalidad);
		locABMConfiguracionLocalidad.setTitulo("Editar Localidad "+locLocalidad.getNombre());
		locABMConfiguracionLocalidad.setLocationRelativeTo(this.vista);
		locABMConfiguracionLocalidad.setVisible(true);
		buscar();
	}
	
	private void eliminar() {
		try{
			Localidad locLocalidad= (Localidad) this.vista.getLLista().getSelectedValue();
			String pMensaje= "¿Realmente desea eliminar la localidad "+locLocalidad+"?";
			String pTitulo= "Eliminar Localidad";
			int locConfirm= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
			if(locConfirm==JOptionPane.YES_OPTION){
				this.localidadDAO.eliminar(locLocalidad);
				this.buscar();
			}
		}
		catch (LogicaException e){
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}	
		
	}	
	
	public PnlDchaConfiguracion getVista(){
		return this.vista;
	}

	
	public static void main(String[]args){
		JFrame framePrueba= new JFrame();
		framePrueba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JDialog locJDialog= new JDialog();
		locJDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		AdminLocalidad controlador= new AdminLocalidad();
		locJDialog.add(controlador.getVista());
		locJDialog.pack();
		locJDialog.setVisible(true);
	}
	
	
	
	
	
	

}

