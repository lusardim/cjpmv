package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.MutableComboBoxModel;

import ar.gov.cjpmv.prestamos.core.business.dao.ConfiguracionSistemaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.DepartamentoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.LocalidadDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.ProvinciaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Domicilio;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;
import ar.gov.cjpmv.prestamos.gui.configuracion.ABMConfiguracionSetearPnl;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlLocalidad;
import ar.gov.cjpmv.prestamos.gui.personas.model.DomicilioModel;

public class ABMConfiguracionLocalidad {

	
	private ABMConfiguracionSetearPnl vista;
	private PnlLocalidad pnlLocalidad;
	
	private Localidad localidad;
	private LocalidadDAO localidadDAO;
	
	private ProvinciaDAO provinciaDAO;
	private ComboBoxModel modeloProvincias;
	private DepartamentoDAO departamentoDAO;
	private ComboBoxModel modeloDepartamentos;
	private ConfiguracionSistemaDAO configuracionSistemaDAO;
	
	

	//private int posicionDepartamento;

	
	public ABMConfiguracionLocalidad(JDialog pPadre, LocalidadDAO pLocalidadDAO){
		this(pPadre, pLocalidadDAO, new Localidad());
	}
	
	public ABMConfiguracionLocalidad(JDialog pPadre, LocalidadDAO pLocalidadDAO, Localidad pLocalidad){
		this.localidad= pLocalidad;
		this.localidadDAO= pLocalidadDAO;
		vista= new ABMConfiguracionSetearPnl(pPadre,true);
		pnlLocalidad= new PnlLocalidad();
		this.vista.setPanel(pnlLocalidad);
		
		departamentoDAO= new DepartamentoDAO();
		provinciaDAO = new ProvinciaDAO();
		this.modeloProvincias = new DefaultComboBoxModel(this.provinciaDAO.getListaProvincias().toArray());
		this.pnlLocalidad.getCbxProvincia().setModel(this.modeloProvincias);
	
		
		if(this.localidad.getId()==null){
			this.configuracionSistemaDAO=new ConfiguracionSistemaDAO();
			this.modeloDepartamentos= new DefaultComboBoxModel(configuracionSistemaDAO.getDepartamentoSistema().getProvincia().getListaDepartamento().toArray());
		}
		else{
			this.modeloDepartamentos= new DefaultComboBoxModel(this.localidad.getDepartamento().getProvincia().getListaDepartamento().toArray());
		}
		this.pnlLocalidad.getCbxDepartamento().setModel(this.modeloDepartamentos);
		
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
		
		
		this.pnlLocalidad.getCbxProvincia().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cambioProvincia();				
			}
					
		});
		
	
		
	
		
		
	}
	

	private void actualizarModelo() {
		String aux= this.pnlLocalidad.getTxtNombre().getText().trim();
		//Si ingresan un string con espacios o el campo es vacio se le asigna el valor null, y luego lo maneja la excepcion
		if(aux.isEmpty()){
			aux=null;
		}
		this.localidad.setNombre(aux);
		
		if(this.pnlLocalidad.getCbxDepartamento().getSelectedItem()==null){
			this.localidad.setDepartamento(null);	
		}
		else{
			Departamento locDepartamento= (Departamento) this.pnlLocalidad.getCbxDepartamento().getSelectedItem();
			this.localidad.setDepartamento(locDepartamento);
		}
		
		
		
	}


	private void actualizarVista() {
	
		if (this.localidad.getId()==null){
			//this.posicionDepartamento=-1;
			
			this.pnlLocalidad.getCbxProvincia().setSelectedItem(this.configuracionSistemaDAO.getDepartamentoSistema().getProvincia());
		}
		else{
			//Object[] ListaDepartamentos=  localidad.getDepartamento().getProvincia().getListaDepartamento().toArray();
			//Departamento locDepartamento= this.localidad.getDepartamento();
			//this.posicionDepartamento = busquedaObjeto(ListaDepartamentos, locDepartamento);
			this.pnlLocalidad.getCbxProvincia().setSelectedItem(this.localidad.getDepartamento().getProvincia());
		}
	
		this.pnlLocalidad.getTxtNombre().setText(this.localidad.getNombre());
		
		this.pnlLocalidad.getCbxDepartamento().setSelectedItem(this.localidad.getDepartamento());
	}
		
	
	private void guardar(){
		try{
			this.actualizarModelo();
			if(this.localidad.getId()==null){
				this.localidadDAO.agregar(localidad);
			}
			else{
				this.localidadDAO.modificar(localidad);
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
	
	public int busquedaObjeto( Object[] pListaDepartamento, Departamento pDepartamento )
	{
		int locPosicion=-1;
		for ( int contador = 0; contador < pListaDepartamento.length; contador++ ){
			long idElementoLista=((Departamento) pListaDepartamento[contador]).getId();
			long idDepartamento=pDepartamento.getId();
			if (idElementoLista == idDepartamento){
	            locPosicion=contador;
			}
		}
		return locPosicion;	
	} 
	
	

	
	
	private void cambioProvincia() {
		Provincia locProvincia= (Provincia)this.pnlLocalidad.getCbxProvincia().getSelectedItem();
		ComboBoxModel modeloDepartamento= new DefaultComboBoxModel(locProvincia.getListaDepartamento().toArray());
		pnlLocalidad.getCbxDepartamento().setModel(modeloDepartamento);
		
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
