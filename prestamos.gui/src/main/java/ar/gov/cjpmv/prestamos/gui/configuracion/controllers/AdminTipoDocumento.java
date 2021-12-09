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

import ar.gov.cjpmv.prestamos.core.business.dao.TipoDocumentoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion;
import ar.gov.cjpmv.prestamos.gui.configuracion.models.AMTipoDocumentoModel;


public class AdminTipoDocumento {
	
	//---Atributos de la vista
	private PnlDchaConfiguracion vista;
	
	//---Atributos del modelo
	private String descripcion;
	private List<TipoDocumento> listaTipoDocumento;
	private TipoDocumentoDAO tipoDocumentoDAO;
 
	//---Constructor
	public AdminTipoDocumento() {
		this.tipoDocumentoDAO= new TipoDocumentoDAO();
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
	
	public void actualizarVista(){
		this.vista.getLblTitulo().setText("Tipos de Documentos");
		this.vista.getTxtTitulo().setText("Configuración: Tipo de Documento");
		this.vista.getTxtDescripcion().setText(this.descripcion);
		this.vista.getLLista().setListData(this.listaTipoDocumento.toArray());
		this.validarBtnModificarEliminar();
	}
	
	public void actualizarModelo(){
		String locDescripcion = this.vista.getTxtDescripcion().getText().trim(); 
		this.descripcion=(locDescripcion.isEmpty())?null:locDescripcion;
		this.listaTipoDocumento= tipoDocumentoDAO.findListaTipoDocumento(this.descripcion);
	}
	
	public void buscar(){
		this.actualizarModelo();
		this.actualizarVista();
	}
	

	private void agregar() {
		ABMConfiguracionControllers locABMControllers= new ABMConfiguracionControllers(null);
		locABMControllers.setModelo(new AMTipoDocumentoModel(this.tipoDocumentoDAO));
		locABMControllers.setTitulo("Agregar Tipo de Documento");
		locABMControllers.setLocationRelativeTo(this.vista);
		locABMControllers.setVisible(true);
		this.buscar();
	}
	
	public void modificar(){
		TipoDocumento locTipoDocumento= (TipoDocumento) this.vista.getLLista().getSelectedValue();
		ABMConfiguracionControllers locABMControllers= new ABMConfiguracionControllers(null);
		locABMControllers.setModelo(new AMTipoDocumentoModel(this.tipoDocumentoDAO, locTipoDocumento));
		locABMControllers.setTitulo("Editar Tipo de Documento: "+locTipoDocumento.getDescripcion());
		locABMControllers.setLocationRelativeTo(this.vista);
		locABMControllers.setVisible(true);
		this.buscar();
	}
	
	private void eliminar() {
		try{
			TipoDocumento locTipoDocumento= (TipoDocumento) this.vista.getLLista().getSelectedValue();
			String pMensaje= "¿Realmente desea eliminar el tipo de documento "+locTipoDocumento+"?";
			String pTitulo= "Eliminar Tipo de Documento";
			int locConfirm= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		
			if(locConfirm==JOptionPane.YES_OPTION){
				this.tipoDocumentoDAO.eliminar(locTipoDocumento);
				this.buscar();
			}
		}
		catch (LogicaException e){
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public void validarBtnModificarEliminar(){
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
		AdminTipoDocumento controlador= new AdminTipoDocumento();
		locJdialog.add(controlador.getVista());
		locJdialog.pack();
		locJdialog.setVisible(true);
	}	


}



