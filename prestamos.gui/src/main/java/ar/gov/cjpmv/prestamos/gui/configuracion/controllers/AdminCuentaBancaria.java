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

import ar.gov.cjpmv.prestamos.core.business.dao.CuentaBancariaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.DepartamentoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion;
import ar.gov.cjpmv.prestamos.gui.personas.model.CuentaBancariaListCellRenderer;

public class AdminCuentaBancaria {
	private PnlDchaConfiguracion vista;
	private String descripcion;
	
	private List<CuentaBancaria> listaCuentaBancaria;
	private CuentaBancariaDAO cuentaBancariaDAO;
	
	public AdminCuentaBancaria() {
		this.cuentaBancariaDAO= new CuentaBancariaDAO();
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
	private void buscar() {
		this.actualizarModelo();
		this.actualizarVista();
		
	}
	
	
	private void actualizarVista() {
		this.vista.getLblDescripcion().setText("Número de Cuenta");
		this.vista.getLblTitulo().setText("Cuentas Bancarias");
		this.vista.getTxtTitulo().setText("Configuración: Cuenta Bancaria");
		this.vista.getTxtDescripcion().setText(this.descripcion);
		this.vista.getLLista().setListData(this.listaCuentaBancaria.toArray());
		this.vista.getLLista().setCellRenderer(new CuentaBancariaListCellRenderer());
		this.validarBtnModificarEliminar();
	}

	private void actualizarModelo() {
		String locDescripcion = this.vista.getTxtDescripcion().getText().trim(); 
		this.descripcion=(locDescripcion.isEmpty())?null:locDescripcion;
		this.listaCuentaBancaria= cuentaBancariaDAO.findListaCuentaBancaria(this.descripcion);
	}

	private void agregar() {
		CuentaBancariaDAO locCuentaBancariaDAO=this.cuentaBancariaDAO;
		ABMConfiguracionCuentaBancaria locABMConfiguracionCtaBancaria= new ABMConfiguracionCuentaBancaria(null, locCuentaBancariaDAO);
		locABMConfiguracionCtaBancaria.setTitulo("Agregar Cuenta Bancaria");
		locABMConfiguracionCtaBancaria.setLocationRelativeTo(this.vista);
		locABMConfiguracionCtaBancaria.setVisible(true);
		buscar();
	}
	
	private void modificar() {
		CuentaBancaria locCuentaBancaria=(CuentaBancaria)this.vista.getLLista().getSelectedValue();
		CuentaBancariaDAO locCuentaBancariaDAO=this.cuentaBancariaDAO;
		ABMConfiguracionCuentaBancaria locABMConfiguracionCtaBancaria= new ABMConfiguracionCuentaBancaria(null, locCuentaBancariaDAO, locCuentaBancaria);
		locABMConfiguracionCtaBancaria.setTitulo("Editar Cuenta Bancaria "+locCuentaBancaria.getNumero());
		locABMConfiguracionCtaBancaria.setLocationRelativeTo(this.vista);
		locABMConfiguracionCtaBancaria.setVisible(true);
		buscar();
	}
	
	
	private void eliminar() {
		try{
			CuentaBancaria locCuentaBancaria= (CuentaBancaria) this.vista.getLLista().getSelectedValue();
			String pMensaje= "¿Realmente desea eliminar la cuenta bancaria "+locCuentaBancaria+"?";
			String pTitulo= "Eliminar Cuenta Bancaria";
			int locConfirm= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		
			if(locConfirm==JOptionPane.YES_OPTION){
				this.cuentaBancariaDAO.eliminar(locCuentaBancaria);
				this.buscar();
			}
		}
		catch (LogicaException e){
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}	
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
		AdminCuentaBancaria controlador= new AdminCuentaBancaria();
		locJdialog.add(controlador.getVista());
		locJdialog.pack();
		locJdialog.setVisible(true);
	}	
	
	
	











	
	
	

}
