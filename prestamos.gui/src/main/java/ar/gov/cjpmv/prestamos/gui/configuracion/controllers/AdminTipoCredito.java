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
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.TipoCreditoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion;

public class AdminTipoCredito {
	private PnlDchaConfiguracion vista;
	private String descripcion;
	private List<TipoCredito> listaCredito;
	private TipoCreditoDAO tipoCreditoDAO;
	
	public AdminTipoCredito() {
		super();
		this.tipoCreditoDAO= new TipoCreditoDAO();
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
		this.vista.getLblTitulo().setText("Tipos de Créditos");
		this.vista.getTxtTitulo().setText("Configuración: Tipo de Crédito");
		this.vista.getTxtDescripcion().setText(this.descripcion);
		this.vista.getLLista().setListData(this.listaCredito.toArray());
		this.validarBtnModificarEliminar();
	}

	private void actualizarModelo() {
		String locDescripcion = this.vista.getTxtDescripcion().getText().trim(); 
		this.descripcion=(locDescripcion.isEmpty())?null:locDescripcion;
		this.listaCredito= tipoCreditoDAO.findListaTipoCredito(this.descripcion);	
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
		TipoCreditoDAO locTipoCreditoDAO=this.tipoCreditoDAO;
		ABMConfiguracionTipoCredito locABMConfiguracionTipoCredito= new ABMConfiguracionTipoCredito(null, locTipoCreditoDAO);
		locABMConfiguracionTipoCredito.setTitulo("Agregar Tipo de Crédito");
		locABMConfiguracionTipoCredito.setLocationRelativeTo(this.vista);
		locABMConfiguracionTipoCredito.setVisible(true);
		buscar();
	}
	
	private void modificar() {
		TipoCredito locTipoCredito=(TipoCredito)this.vista.getLLista().getSelectedValue();
		TipoCreditoDAO locTipoCreditoDAO=this.tipoCreditoDAO;
		ABMConfiguracionTipoCredito locABMConfiguracionTipoCredito= new ABMConfiguracionTipoCredito(null, locTipoCreditoDAO, locTipoCredito);
		locABMConfiguracionTipoCredito.setTitulo("Editar Tipo de Crédito "+locTipoCredito.getNombre());
		locABMConfiguracionTipoCredito.setLocationRelativeTo(this.vista);
		locABMConfiguracionTipoCredito.setVisible(true);
		buscar();
	}
	
	private void eliminar() {
		try{
			TipoCredito locTipoCredito= (TipoCredito) this.vista.getLLista().getSelectedValue();
			String pMensaje= "¿Realmente desea eliminar la cuenta bancaria "+locTipoCredito+"?";
			String pTitulo= "Eliminar Tipo de Crédito";
			int locConfirm= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		
			if(locConfirm==JOptionPane.YES_OPTION){
				this.tipoCreditoDAO.eliminar(locTipoCredito);
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
		this.actualizarVista();
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JDialog locJdialog= new JDialog();
		AdminTipoCredito controlador= new AdminTipoCredito();
		locJdialog.add(controlador.getVista());
		locJdialog.pack();
		locJdialog.setVisible(true);
	}	
	


}
