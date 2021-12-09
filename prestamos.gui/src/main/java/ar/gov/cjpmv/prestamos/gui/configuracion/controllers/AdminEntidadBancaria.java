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

import ar.gov.cjpmv.prestamos.core.business.dao.EntidadBancariaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Banco;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion;
import ar.gov.cjpmv.prestamos.gui.configuracion.models.AMBancoModel;

public class AdminEntidadBancaria {
	private PnlDchaConfiguracion vista;
	private String descripcion;
	private List<Banco> listaBancos;
	private EntidadBancariaDAO bancoDAO;
	
	public AdminEntidadBancaria() {
		super();
		this.vista= new PnlDchaConfiguracion();
		this.bancoDAO= new EntidadBancariaDAO();
		this.vista.getLLista().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.inicializarEventos();
		this.inicializarVista();
	}

	private void inicializarVista() {
		this.actualizarModelo();
		this.actualizarVista();
	}

	private void actualizarVista() {
		this.vista.getLblDescripcion().setText("Nombre");
		this.vista.getLblTitulo().setText("Entidades Bancarias");
		this.vista.getTxtTitulo().setText("Configuración: Entidad Bancaria");
		this.vista.getTxtDescripcion().setText(this.descripcion);
		this.vista.getLLista().setListData(this.listaBancos.toArray());
		this.validarBtnModificarEliminar();
	}

	private void actualizarModelo() {
		String locDescripcion = this.vista.getTxtDescripcion().getText().trim(); 
		this.descripcion=(locDescripcion.isEmpty())?null:locDescripcion;
		this.listaBancos= bancoDAO.findListaBanco(this.descripcion);	
	}

	private void inicializarEventos() {
		this.vista.getBtnBuscar().addActionListener(new ActionListener(){
			//Clase interna anonima: ActionListener
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
	

	
	
	private void agregar() {
		ABMConfiguracionControllers controlador= new ABMConfiguracionControllers(null);
		controlador.setModelo(new AMBancoModel(this.bancoDAO));
		controlador.setTitulo("Agregar Entidad Bancaria");
		controlador.setLabel("Nombre");
		controlador.setLocationRelativeTo(this.vista);
		controlador.setVisible(true);
		this.buscar();
	}
	

	
	private void modificar() {
		Banco locBanco= (Banco) this.vista.getLLista().getSelectedValue();
		ABMConfiguracionControllers controlador= new ABMConfiguracionControllers(null);
		controlador.setModelo(new AMBancoModel(bancoDAO, locBanco));
		controlador.setTitulo("Editar Entidad Bancaria: "+locBanco.getNombre());
		controlador.setLabel("Nombre");
		controlador.setLocationRelativeTo(this.vista);
		controlador.setVisible(true);
		this.buscar();
	}
	
	
	private void eliminar() {
		try{
			Banco locBanco= (Banco) this.vista.getLLista().getSelectedValue();
			String pMensaje= "¿Realmente desea eliminar la entidad bancaria "+locBanco+"?";
			String pTitulo= "Eliminar Entidad Bancaria";
			int locConfirm= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		
			if(locConfirm==JOptionPane.YES_OPTION){
				this.bancoDAO.eliminar(locBanco);
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
		AdminEntidadBancaria controlador= new AdminEntidadBancaria();
		locJdialog.add(controlador.getVista());
		locJdialog.pack();
		locJdialog.setVisible(true);
	}	
	
	
	
	

}
