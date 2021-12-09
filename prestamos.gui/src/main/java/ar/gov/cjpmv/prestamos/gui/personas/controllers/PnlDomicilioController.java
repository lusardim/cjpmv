package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.MutableComboBoxModel;

import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Domicilio;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDomicilioView;
import ar.gov.cjpmv.prestamos.gui.configuracion.controllers.AdminConfiguracion;
import ar.gov.cjpmv.prestamos.gui.personas.model.DomicilioModel;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlDomicilioController {
	private PnlDomicilioView vista;
	private DomicilioModel modelo;
	
	public PnlDomicilioController(PnlDomicilioView vista){
		this.vista = vista;
		this.inicializarModelos();
		this.inicializarEventos();
		this.actualizarVista();
	}
	
	public PnlDomicilioController(){
		this.vista = new PnlDomicilioView();
		 
		this.inicializarModelos();
		this.inicializarEventos();
	}

	private void inicializarEventos() {
		this.vista.getCbxProvincia().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cambioProvincia();				
			}
		});
		
		this.vista.getCbxDepartamento().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cambioDepartamento();
			}
		});
		
		this.vista.getBtnLocalidad().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				configuracionLocalidad();			
			}
		});
		
		this.vista.getBtnDepartamento().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				configuracionDepartamento();
			}
		});
		
		this.vista.getBtnProvincia().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				configuracionProvincia();
			}
		});
	}
	
	
	private void configuracionLocalidad() {
		// TODO Auto-generated method stub
		AdminConfiguracion locAdminConfiguracion= new AdminConfiguracion(null);
		locAdminConfiguracion.mostrarPnlLocalidad();
		locAdminConfiguracion.setLocationRelativeTo(this.vista);
		locAdminConfiguracion.show();
		this.reInicializarModelos();
	}

	private void configuracionDepartamento() {
		// TODO Auto-generated method stub
		AdminConfiguracion locAdminConfiguracion= new AdminConfiguracion(null);
		locAdminConfiguracion.mostrarPnlDepartamento();
		locAdminConfiguracion.setLocationRelativeTo(this.vista);
		locAdminConfiguracion.show();
		this.reInicializarModelos();
	}
	
	private void configuracionProvincia() {
		// TODO Auto-generated method stub
		AdminConfiguracion locAdminConfiguracion= new AdminConfiguracion(null);
		locAdminConfiguracion.mostrarPnlProvincia();
		locAdminConfiguracion.setLocationRelativeTo(this.vista);
		locAdminConfiguracion.show();
		this.reInicializarModelos();
	}
	
	
	private void cambioDepartamento() {
		Departamento locDepartamento = (Departamento)this.modelo.getModeloDepartamentos().getSelectedItem();
		List<Localidad> locListaLocalidades = new ArrayList<Localidad>();
		if (locDepartamento!=null){
			locListaLocalidades = locDepartamento.getListaLocalidad();
		}
		MutableComboBoxModel locNuevoModelo = new DefaultComboBoxModel(locListaLocalidades.toArray());
		this.vista.getCbxLocalidad().setModel(locNuevoModelo);
		this.modelo.setModeloLocalidades(locNuevoModelo);
	}
	
	private void cambioProvincia() {
		Provincia locProvincia = (Provincia)this.modelo.getModeloProvincias().getSelectedItem();
		List<Departamento> locLista;
		if (locProvincia == null){
			locLista = new ArrayList<Departamento>();
		}
		else{
		 locLista =  locProvincia.getListaDepartamento();
		}
		MutableComboBoxModel locModeloDepartamento = new DefaultComboBoxModel(locLista.toArray());
		this.modelo.setModeloDepartamentos(locModeloDepartamento);
		this.vista.getCbxDepartamento().setModel(locModeloDepartamento);
		if (locLista.isEmpty()){
			this.vista.getCbxLocalidad().removeAllItems();
		}
		else{
			this.cambioDepartamento();
		}
		
	}

	private void inicializarModelos() {
		this.modelo = new DomicilioModel();
		this.vista.getCbxProvincia().setModel(this.modelo.getModeloProvincias());
		this.vista.getCbxDepartamento().setModel(this.modelo.getModeloDepartamentos());
		this.vista.getCbxLocalidad().setModel(this.modelo.getModeloLocalidades());
	}
	
	private void reInicializarModelos() {
		this.vista.getCbxProvincia().setModel(this.modelo.getModeloProvincias());
		this.vista.getCbxDepartamento().setModel(this.modelo.getModeloDepartamentos());
		this.vista.getCbxLocalidad().setModel(this.modelo.getModeloLocalidades());
	}

	public PnlDomicilioView getVista() {
		return vista;
	}
	
	
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	JDialog locDialogo = new JDialog();
                PnlDomicilioController locDomicilioController = new PnlDomicilioController();
                locDialogo.add(locDomicilioController.getVista());
                locDialogo.pack();
                locDialogo.setVisible(true);
            }
        });
    }

    
    public void actualizarVista(){
    	Domicilio locDomicilio = this.getDomicilio();
    	this.vista.getTxtCalle().setText(locDomicilio.getCalle());
    	this.vista.getTxtNumero().setText(Utiles.cadenaVaciaSiNulo(locDomicilio.getNumero()));
    	this.vista.getTxaObservaciones().setText(locDomicilio.getObservacion());
    	this.vista.getCbxDepartamento().setSelectedItem(locDomicilio.getDepartamento());
    	this.vista.getCbxLocalidad().setSelectedItem(locDomicilio.getLocalidad());
    	this.vista.getCbxProvincia().setSelectedItem(locDomicilio.getProvincia());
    }
    
    public void actualizarModelo(){
    	Domicilio locDomicilio = this.getDomicilio();
    	locDomicilio.setCalle(Utiles.nuloSiVacio(this.vista.getTxtCalle().getText()));
    	Long numero =(Long) this.vista.getTxtNumero().getValue();
    	
    	Integer locNumero = (numero!=null)?numero.intValue():null; 
    	locDomicilio.setNumero(locNumero);
    	
    	locDomicilio.setObservacion(Utiles.nuloSiVacio(this.vista.getTxaObservaciones().getText()));
    	
    	Localidad locLocalidad = (Localidad)this.modelo.getModeloLocalidades().getSelectedItem();
		Departamento locDepartamento = (Departamento)this.modelo.getModeloDepartamentos().getSelectedItem();
   		
		if (locLocalidad!=null){
			locDomicilio.setLocalidad(locLocalidad);
		}
		else{
			locDomicilio.setDepartamento(locDepartamento);
		}
   		
    }
    
	public Domicilio getDomicilio() {
		return modelo.getDomicilio();
	}
	
	public void setDomicilio(Domicilio pDomicilio){
		this.modelo.setDomicilio(pDomicilio);
		this.actualizarVista();
	}
}

