package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.business.dao.DepartamentoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion;
import ar.gov.cjpmv.prestamos.gui.configuracion.models.AMTipoDocumentoModel;
import ar.gov.cjpmv.prestamos.gui.personas.model.ProvinciaListCellRenderer;

public class AdminDepartamento {
	public PnlDchaConfiguracion vista;
	
	public String descripcion;
	public List<Departamento> departamento;
	public DepartamentoDAO departamentoDAO;
	
	public AdminDepartamento(){
		this.departamentoDAO= new DepartamentoDAO();
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
		this.vista.getLblTitulo().setText("Departamentos");
		this.vista.getTxtTitulo().setText("Configuración: Departamento");
		this.vista.getTxtDescripcion().setText(this.descripcion);
		this.vista.getLLista().setListData(this.departamento.toArray());
		this.vista.getLLista().setCellRenderer(new ProvinciaListCellRenderer());
		this.validarBtnModificarEliminar();
	}

	private void actualizarModelo() {
		this.descripcion= this.vista.getTxtDescripcion().getText();
		String locDescripcion= this.vista.getTxtDescripcion().getText().trim();
		this.descripcion= (locDescripcion.isEmpty())?null:locDescripcion;
		this.departamento= departamentoDAO.findListaDepartamento(this.descripcion);
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
		DepartamentoDAO locDepartamentoDAO=this.departamentoDAO;
		ABMConfiguracionDepartamento locABMConfiguracionDepartamento= new ABMConfiguracionDepartamento(null, locDepartamentoDAO);
		locABMConfiguracionDepartamento.setTitulo("Agregar Departamento");
		locABMConfiguracionDepartamento.setLocationRelativeTo(this.vista);
		locABMConfiguracionDepartamento.setVisible(true);
		buscar();
	}
	
	private void modificar() {
		Departamento locDepartamento=(Departamento)this.vista.getLLista().getSelectedValue();
		DepartamentoDAO locDepartamentoDAO=this.departamentoDAO;
		ABMConfiguracionDepartamento locABMConfiguracionDepartamento= new ABMConfiguracionDepartamento(null, locDepartamentoDAO, locDepartamento);
		locABMConfiguracionDepartamento.setTitulo("Editar Departamento "+locDepartamento.getNombre());
		locABMConfiguracionDepartamento.setLocationRelativeTo(this.vista);
		locABMConfiguracionDepartamento.setVisible(true);
		buscar();
	}
	
	private void eliminar() {
		try{
			Departamento locDepartamento= (Departamento) this.vista.getLLista().getSelectedValue();
			String pMensaje= "¿Realmente desea eliminar el departamento "+locDepartamento+"?";
			String pTitulo= "Eliminar Departamento";
			int locConfirm= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
			if(locConfirm==JOptionPane.YES_OPTION){
				this.departamentoDAO.eliminar(locDepartamento);
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
		AdminDepartamento controlador= new AdminDepartamento();
		locJDialog.add(controlador.getVista());
		locJDialog.setVisible(true);
	}
	
	
	
	
	
	

}
