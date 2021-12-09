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

import ar.gov.cjpmv.prestamos.core.business.dao.ConceptoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CuentaBancariaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion;
import ar.gov.cjpmv.prestamos.gui.personas.model.CuentaBancariaListCellRenderer;

public class AdminConcepto {
	
	private PnlDchaConfiguracion vista;
	private String descripcion;
	private List<Concepto> listaConcepto;
	private ConceptoDAO conceptoDAO;
	
	public AdminConcepto() {
		super();
		this.conceptoDAO= new ConceptoDAO();
		vista= new PnlDchaConfiguracion();
		this.vista.getLLista().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.inicializarEventos();
		this.inicializarVista();
	}
	
	private void inicializarVista() {
		this.actualizarModelo();
		this.actualizarVista();
		
	}
	
	
	private void actualizarVista() {
		this.vista.getLblDescripcion().setText("Descripción");
		this.vista.getLblTitulo().setText("Conceptos");
		this.vista.getTxtTitulo().setText("Configuración: Conceptos aplicados a Créditos");
		this.vista.getTxtDescripcion().setText(this.descripcion);
		this.vista.getLLista().setListData(this.listaConcepto.toArray());
		this.validarBtnModificarEliminar();
	}

	private void actualizarModelo() {
		String locDescripcion = this.vista.getTxtDescripcion().getText().trim(); 
		this.descripcion=(locDescripcion.isEmpty())?null:locDescripcion;
		this.listaConcepto= conceptoDAO.findListaConcepto(this.descripcion);
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
			public void valueChanged(ListSelectionEvent e) {
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
	
	
	private void agregar() {
		ConceptoDAO locConceptoDAO=this.conceptoDAO;
		ABMConfiguracionConcepto locABMConcepto= new ABMConfiguracionConcepto(null, locConceptoDAO);
		locABMConcepto.setTitulo("Agregar Concepto");
		locABMConcepto.setLocationRelativeTo(this.vista);
		locABMConcepto.setVisible(true);
		buscar();
	}
	
	private void modificar() {
		Concepto locConcepto=(Concepto)this.vista.getLLista().getSelectedValue();
		ConceptoDAO locConceptoDAO=this.conceptoDAO;
		ABMConfiguracionConcepto locABMConfiguracionConcepto= new ABMConfiguracionConcepto(null, locConceptoDAO, locConcepto);
		locABMConfiguracionConcepto.setTitulo("Editar Concepto "+locConcepto.getDescripcion());
		locABMConfiguracionConcepto.setLocationRelativeTo(this.vista);
		locABMConfiguracionConcepto.setVisible(true);
		buscar();
	}
	
	
	private void eliminar() {
		try{
			Concepto locConcepto= (Concepto) this.vista.getLLista().getSelectedValue();
			String pMensaje= "¿Realmente desea eliminar el concepto "+locConcepto+"?";
			String pTitulo= "Eliminar Concepto";
			int locConfirm= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		
			if(locConfirm==JOptionPane.YES_OPTION){
				this.conceptoDAO.eliminar(locConcepto);
				this.buscar();
			}
		}
		catch (LogicaException e){
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}	
	}

	

	private void buscar() {
		this.actualizarModelo();
		this.inicializarVista();	
	}
	
	private void validarBtnModificarEliminar() {
		boolean haySeleccion = !this.vista.getLLista().isSelectionEmpty();
		this.vista.getBtnModificar().setEnabled(haySeleccion);
		this.vista.getBtnEliminar().setEnabled(haySeleccion);		
	}
	

	public PnlDchaConfiguracion getVista() {
		return vista;
	}
	
	
	public static void main(String[]args){
		JFrame frame= new JFrame();
		//Se llama a este metodo para que la aplicacion no siga corriendo
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JDialog locJdialog= new JDialog();
		AdminConcepto controlador= new AdminConcepto();
		locJdialog.add(controlador.getVista());
		locJdialog.pack();
		locJdialog.setVisible(true);
	}	
	

	

}
