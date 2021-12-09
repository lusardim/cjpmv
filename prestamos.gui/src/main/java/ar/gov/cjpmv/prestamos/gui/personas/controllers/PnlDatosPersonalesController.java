package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.TipoDocumentoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCivil;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.gui.personas.PnlDatosPersonalesView;
import ar.gov.cjpmv.prestamos.gui.personas.model.AMPersonaFisicaModel;
import ar.gov.cjpmv.prestamos.gui.personas.model.EstadoPersonaFisicaCellRenderer;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlDatosPersonalesController {
	
	private JDialog padre;
	private PnlDatosPersonalesView vista;
	private TipoDocumentoDAO tipoDocumentoDAO;
	
	private AMPersonaFisicaModel modelo;
	
	
	public PnlDatosPersonalesController(
			PnlDatosPersonalesView pnlDatosPersonales, JDialog padre,AMPersonaFisicaModel pModelo) throws ParseException {
		 this.vista = pnlDatosPersonales;
		 this.padre = padre;
		 this.modelo = pModelo;
		 //TODO VER SI ESTO VA ACÁS
		 this.tipoDocumentoDAO = new TipoDocumentoDAO();
		 this.vista.getLblFoto().setVisible(false);
	     this.vista.getLblImagenFoto().setVisible(false);
	     this.vista.getBtnExaminar().setVisible(false);
		 this.inicializarModelos();
		 this.inicializarEventos();
		 this.actualizarVista();
	}

    private void inicializarEventos() {
   
    	
    	this.vista.getTxtNombres().addFocusListener(new FocusAdapter() {
    		@Override
    		public void focusLost(FocusEvent e) {
    			if (!vista.getTxtNombres().getText().isEmpty()){
    				vista.getTxtNombres().setText(Utiles.nuloSiVacioConCammelCase(vista.getTxtNombres().getText()));
    			}
    		}
		});
    	  
    	this.vista.getTxtApellido().addFocusListener(new FocusAdapter() {
    		@Override
    		public void focusLost(FocusEvent e) {
    			if (!vista.getTxtApellido().getText().isEmpty()){
    				vista.getTxtApellido().setText(Utiles.nuloSiVacioConCammelCase(vista.getTxtApellido().getText()));
    			}
    		}
		});
    
    }
    
    public void actualizarVista() throws ParseException{
    	this.vista.getTxtLegajo().setText(Utiles.cadenaVaciaSiNulo(this.modelo.getPersona().getLegajo()));
    	if (this.modelo.getPersona().getLegajo()!=null){
    		this.vista.getTxtLegajo().commitEdit();
    		
    	}
      	
    	this.vista.getTxtCuil().setText(Utiles.cadenaVaciaSiNulo(this.modelo.getPersona().getCui()));
    	if (this.modelo.getPersona().getCui()!=null){
    		this.vista.getTxtCuil().commitEdit();
    	}
    	
    	this.vista.getCbxTipoDocumento().setSelectedItem(this.modelo.getPersona().getTipoDocumento());

    	
    	this.vista.getTxtNumeroDocumento().setText(Utiles.cadenaVaciaSiNulo(this.modelo.getPersona().getNumeroDocumento()));
    	if (this.modelo.getPersona().getNumeroDocumento()!=null){
    		this.vista.getTxtNumeroDocumento().commitEdit();
    	}
    	
    	this.vista.getTxtApellido().setText(this.modelo.getPersona().getApellido());
    	this.vista.getTxtNombres().setText(this.modelo.getPersona().getNombre());
    	this.vista.getCbxEstadoCivil().setSelectedItem(this.modelo.getPersona().getEstadoCivil());
    	this.vista.getDcFechaNacimiento().setDate(this.modelo.getPersona().getFechaNacimiento());
    	this.vista.getDcFechaDefuncion().setDate(this.modelo.getPersona().getFechaDefuncion());
    	this.vista.getCbxEstado().setSelectedItem(this.modelo.getPersona().getEstado());
    }
    
    public void actualizarModelo() throws LogicaException{
  
	    	if (this.vista.getTxtLegajo().getText().equals("")){
	    		this.modelo.getPersona().setLegajo(null);
	    	}
	    	else{
	    		this.modelo.getPersona().setLegajo((Long)this.vista.getTxtLegajo().getValue());
	    	}
	    	
	    	if (this.vista.getTxtCuil().getText().equals("")){
	    		this.modelo.getPersona().setCui(null);
	    	}
	    	else{
	    		String cuil = (String)this.vista.getTxtCuil().getValue();
	    		this.modelo.getPersona().setCui((cuil==null)?null:Long.parseLong(cuil));
	    	}
	    	
	    	this.modelo.getPersona().setTipoDocumento((TipoDocumento)this.vista.getCbxTipoDocumento().getSelectedItem());
	
	    	if (this.vista.getTxtNumeroDocumento().getText().equals("")){
	    		this.modelo.getPersona().setNumeroDocumento(null);
	    	}
	    	else{
	    		Long numeroDocumento= null;
	    		try{
	    			numeroDocumento = Long.parseLong(this.vista.getTxtNumeroDocumento().getText());
	    		}
	    		catch(NumberFormatException e){
	    			int codigo= 137;
	    			throw new LogicaException(codigo);
				
	    		}
	    		this.modelo.getPersona().setNumeroDocumento((numeroDocumento==null)?null:numeroDocumento.intValue());
	
	    	}
	    			
	    	this.modelo.getPersona().setApellido(Utiles.nuloSiVacioConCammelCase(this.vista.getTxtApellido().getText()));
	    	this.modelo.getPersona().setNombre(Utiles.nuloSiVacioConCammelCase(this.vista.getTxtNombres().getText()));
	    	this.modelo.getPersona().setEstadoCivil((EstadoCivil)this.vista.getCbxEstadoCivil().getSelectedItem());
	    	this.modelo.getPersona().setFechaNacimiento(this.vista.getDcFechaNacimiento().getDate());
	    	this.modelo.getPersona().setFechaDefuncion(this.vista.getDcFechaDefuncion().getDate());
	    	this.modelo.getPersona().setEstado((EstadoPersonaFisica)this.vista.getCbxEstado().getSelectedItem());
    
    	
    }

	private void inicializarModelos() {
    	this.vista.getCbxEstado().setModel(new DefaultComboBoxModel(EstadoPersonaFisica.values()));
    	this.vista.getCbxEstadoCivil().setModel(new DefaultComboBoxModel(EstadoCivil.values()));
    	this.vista.getCbxTipoDocumento().setModel(new DefaultComboBoxModel(this.tipoDocumentoDAO.findListaTipoDocumento(null).toArray()));
    	this.vista.getCbxEstado().setSelectedItem(EstadoPersonaFisica.ACTIVO);
    	
    	//inicializo los cell renderers
    	this.vista.getCbxEstado().setRenderer(new EstadoPersonaFisicaCellRenderer());
    	this.vista.getCbxEstadoCivil().setRenderer(new EstadoCivilCellRenderer());
    	
	}
	
	

}


