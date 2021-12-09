package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.business.dao.ConfiguracionSistemaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.ConfiguracionSistema;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracionRegistracion;
import ar.gov.cjpmv.prestamos.gui.configuracion.models.RegistracionListCellRenderer;
import ar.gov.cjpmv.prestamos.gui.configuracion.models.RegistracionModel;

public class AdminRegistracion {
	
	private PnlDchaConfiguracionRegistracion vista;
	
	private String descripcion;
	private ConfiguracionSistema configuracionSistema;
	private List<PersonaJuridica> personaJuridica;
	private ConfiguracionSistemaDAO configuracionSistemaDAO;
	public AdminRegistracion(){
		this.configuracionSistema= new ConfiguracionSistema();
		this.configuracionSistemaDAO= new ConfiguracionSistemaDAO();
		vista= new PnlDchaConfiguracionRegistracion();
		this.vista.getLLista().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.inicializarEventos();
		this.inicializarVista();

	}

	private void inicializarVista() {
		this.actualizarModelo();
		this.actualizarVista();
		
	}

	private void actualizarVista() {
		try{
			this.vista.getLblTitulo().setText("Personas Jurídicas");
			this.vista.getLblMensaje().setVisible(false);
			this.vista.getTxtDescripcion().setText(this.descripcion);
			this.vista.getLLista().setListData(this.getListaPersona().toArray());
			this.vista.getLLista().setCellRenderer(new RegistracionListCellRenderer());
			this.vista.getLLista().setSelectedIndex(-1);
			this.validarRegistrar();
		}
		catch(LogicaException e){
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			this.vista.getLblMensaje().setText(pMensaje);
			this.vista.getLblMensaje().setVisible(true);
		}
	}

	private List<RegistracionModel> getListaPersona() throws LogicaException {
		ArrayList<RegistracionModel> listaPersonas= new ArrayList<RegistracionModel>();
		PersonaJuridica locRegistrada=this.configuracionSistemaDAO.getPersonaJuridicaRegistrada();
		List<PersonaJuridica> locNoRegistradas=this.configuracionSistemaDAO.getPersonasJuridicasNoRegistradas(this.descripcion);
		listaPersonas.add(new RegistracionModel(locRegistrada, true));
		for(PersonaJuridica cadaPersona: locNoRegistradas){
			listaPersonas.add(new RegistracionModel(cadaPersona, false));
		}
		return listaPersonas;
	}

	private void actualizarModelo() {
		String locDescripcion = this.vista.getTxtDescripcion().getText().trim(); 
		this.descripcion=(locDescripcion.isEmpty())?null:locDescripcion;
	
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
				validarRegistrar();				
			}
		});
		this.vista.getBtnRegistrar().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
	}

	private void buscar() {
		this.actualizarModelo();
		this.actualizarVista();
	}
	
	private void validarRegistrar(){
		RegistracionModel locSeleccionado=(RegistracionModel)this.vista.getLLista().getSelectedValue();
		if((locSeleccionado!=null)&&(!locSeleccionado.isValor())){
			this.vista.getBtnRegistrar().setEnabled(true);
		}
		else{
			this.vista.getBtnRegistrar().setEnabled(false);
		}
	}
	
	private void registrar() {
		try{
			RegistracionModel locSeleccionado=(RegistracionModel)this.vista.getLLista().getSelectedValue();
			PersonaJuridica locPersonaJuridica= locSeleccionado.getPersonaJuridica();
			PersonaJuridica locRegistrada=this.configuracionSistemaDAO.getPersonaJuridicaRegistrada();
			String pMensaje= "El sistema se encuentra registrado a nombre de "+locRegistrada+"\n"+"Usted ha solicitado registrarlo a nombre de "+locPersonaJuridica+"\n"+"¿Realmente desea reemplazar la registración del sistema?";
			String pTitulo= "Registración del Sistema";
			int locConfirm= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
			this.configuracionSistema.setPersona(locPersonaJuridica);
			if(locConfirm==JOptionPane.YES_OPTION){
				this.configuracionSistemaDAO.modificar(this.configuracionSistema);
				this.buscar();
			}
		}
		catch (LogicaException e){
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public PnlDchaConfiguracionRegistracion getVista(){
		return this.vista;
	}


	public static void main(String[]args){
		JFrame frame= new JFrame();
		//Se llama a este metodo para que la aplicacion no siga corriendo
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JDialog locJdialog= new JDialog();
		AdminRegistracion controlador= new AdminRegistracion();
		locJdialog.add(controlador.getVista());
		locJdialog.pack();
		locJdialog.setVisible(true);
	}	


}
