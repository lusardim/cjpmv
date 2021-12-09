package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;

import ar.gov.cjpmv.prestamos.core.business.dao.DepartamentoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.ProvinciaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;
import ar.gov.cjpmv.prestamos.gui.configuracion.ABMConfiguracionSetearPnl;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDepartamento;

public class ABMConfiguracionDepartamento {
	
	private ABMConfiguracionSetearPnl vista;
	private PnlDepartamento pnlDepartamento;
	
	private Departamento departamento;
	private DepartamentoDAO departamentoDAO;
	
	private ProvinciaDAO provinciaDAO;
	private ComboBoxModel modeloProvincias;


	
	public ABMConfiguracionDepartamento(JDialog pPadre, DepartamentoDAO pDepartamentoDAO){
		this(pPadre, pDepartamentoDAO, new Departamento());
	}
	
	public ABMConfiguracionDepartamento(JDialog pPadre, DepartamentoDAO pDepartamentoDAO, Departamento pDepartamento){
		this.departamento= pDepartamento;
		this.departamentoDAO= pDepartamentoDAO;
		vista= new ABMConfiguracionSetearPnl(pPadre,true);
		pnlDepartamento= new PnlDepartamento();
		this.vista.setPanel(pnlDepartamento);
		provinciaDAO = new ProvinciaDAO();
		this.modeloProvincias = new DefaultComboBoxModel(this.provinciaDAO.getListaProvincias().toArray());
		this.inicializarEventos();
		this.actualizarVista();
	
	}
	

	
	private void inicializarEventos() {
		
		this.vista.getBtnGuardar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}

		});
		
		this.vista.getBtnCancelar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});	
		
	}
	

	private void actualizarModelo() {
		String aux= this.pnlDepartamento.getTxtNombre().getText().trim();
		//Si ingresan un string con espacios o el campo es vacio se le asigna el valor null, y luego lo maneja la excepcion
		if(aux.isEmpty()){
			aux=null;
		}
		this.departamento.setNombre(aux);
		if(this.pnlDepartamento.getCbxProvincia().getSelectedItem()==null){
			this.departamento.setProvincia(null);	
		}
		else{
			Provincia locProvincia= (Provincia) this.pnlDepartamento.getCbxProvincia().getSelectedItem();
			this.departamento.setProvincia(locProvincia);
		}
	}
	
	
	private void actualizarVista() {
		this.pnlDepartamento.getTxtNombre().setText(this.departamento.getNombre());
		this.pnlDepartamento.getCbxProvincia().setModel(this.modeloProvincias);
		this.pnlDepartamento.getCbxProvincia().setSelectedItem(this.departamento.getProvincia());
	}



		private void guardar(){
			try{
				this.actualizarModelo();
				if(this.departamento.getId()==null){
					this.departamentoDAO.agregar(departamento);
				}
				else{
					this.departamentoDAO.modificar(departamento);
				}
				this.vista.dispose();
			}
			catch(LogicaException e){
				String pTitulo=e.getTitulo();
				String pMensaje=e.getMessage();
				
				//En este caso no se instancia un objeto del tipo JOptionPane para el metodo showMessageDialog porque este ultimo es un metodo estatico o de clase
				JOptionPane.showMessageDialog(this.vista , pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
	private void cancelar() {
			String pMensaje= "¿Realmente desea cancelar la operación?";
			String pTitulo= "Confirmación";
			//TODO CONVERTIR ESTE METODO EN GENERICO
			int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
			if(locConfirmacion==JOptionPane.YES_OPTION){
				this.vista.dispose();	
			}
			
		}
		
		
		
	
	
	public void setTitulo(String pTitulo) {
		this.vista.setTitle(pTitulo);
	}

	public void setVisible(boolean pVisible){
		this.vista.setVisible(pVisible);
	}
	
	public void setLocationRelativeTo(JComponent pPadre) {
		this.vista.setLocationRelativeTo(pPadre);
	}
	


	

}
