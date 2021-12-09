package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;
import ar.gov.cjpmv.prestamos.gui.personas.PnlDatosInstitucionales;
import ar.gov.cjpmv.prestamos.gui.personas.PnlDatosInstitucionalesResponsables;
import ar.gov.cjpmv.prestamos.gui.personas.model.EstadoPersonaFisicaCellRenderer;
import ar.gov.cjpmv.prestamos.gui.personas.model.EstadoPersonaJuridicaCellRenderer;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlDatosInstitucionalesController {
	
	private PnlDatosInstitucionalesResponsables vista;
	private JDialog padre;
	
	private PnlDatosResponsablesController sociosController;
	
	private PersonaJuridica modelo;
	
	public PnlDatosInstitucionalesController(
			PnlDatosInstitucionalesResponsables pnlDatosInstitucionales,
			JDialog padre, PersonaJuridica pPersona) throws ParseException {
		this.modelo = pPersona;
		this.vista = pnlDatosInstitucionales;
		this.padre = padre;
		this.sociosController = new PnlDatosResponsablesController(this.vista.getPnlDatosResponsables(),this.padre);
		this.inicializarModelos();
		this.actualizarVista();
		this.inicializarEventos();
	}

	
	private void inicializarModelos() {
		DefaultComboBoxModel locComBoxModel = new DefaultComboBoxModel();
		locComBoxModel.addElement(null);
		for (EstadoPersonaJuridica cadaEstado : EstadoPersonaJuridica.values()){
			locComBoxModel.addElement(cadaEstado);
		}
		this.vista.getPnlDatosInstitucionales().getCbxEstado().setModel(locComBoxModel);
		this.vista.getPnlDatosInstitucionales().getCbxEstado().setRenderer(new EstadoPersonaJuridicaCellRenderer());
	}

	private void inicializarEventos() {
		this.vista.getPnlDatosInstitucionales().getTxtRazonSocial().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String locValorActual = vista.getPnlDatosInstitucionales().getTxtRazonSocial().getText();
				if (!locValorActual.isEmpty()){
					vista.getPnlDatosInstitucionales().getTxtRazonSocial()
						 .setText(Utiles.nuloSiVacioConCammelCase(locValorActual));
				}
			}
		});
		
	}

	
	
	public void actualizarModelo(){	
		this.modelo.setListaSocios(this.sociosController.getModelo());
		PnlDatosInstitucionales locPnlVista = this.vista.getPnlDatosInstitucionales();
		
		String locCui = (String)locPnlVista.getTxtCuit().getValue();
		this.modelo.setCui((locCui!=null)?Long.parseLong(locCui):null);
		
		this.modelo.setNombre(Utiles.nuloSiVacio(locPnlVista.getTxtRazonSocial().getText()));
		this.modelo.setFechaNacimiento(locPnlVista.getDtcInicioActividades().getDate());
		this.modelo.setFechaCeseActividades(locPnlVista.getDtcFinActividades().getDate());
		this.modelo.setEstado((EstadoPersonaJuridica)locPnlVista.getCbxEstado().getSelectedItem());
	}
	
	public void actualizarVista() throws ParseException{
		this.sociosController.setModelo(this.modelo.getListaSocios());
		PnlDatosInstitucionales locVista = this.vista.getPnlDatosInstitucionales();
		
		locVista.getTxtCuit().setText(Utiles.cadenaVaciaSiNulo(this.modelo.getCui()));
    	if (this.modelo.getCui()!=null){
    		locVista.getTxtCuit().commitEdit();
    	}
		locVista.getTxtRazonSocial().setText(this.modelo.getNombre());
		locVista.getDtcInicioActividades().setDate(this.modelo.getFechaNacimiento());
		locVista.getDtcFinActividades().setDate(this.modelo.getFechaCeseActividades());
		locVista.getCbxEstado().setSelectedItem(this.modelo.getEstado());
	}
	
	
	
	public static void main(String[] args) throws ParseException{
		JDialog locDialogo = new JDialog();
		locDialogo.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		
		locDialogo.add(new PnlDatosInstitucionalesController(new PnlDatosInstitucionalesResponsables(),locDialogo,new PersonaJuridica()).vista);
		locDialogo.pack();
		locDialogo.setVisible(true);
	}


	public PersonaJuridica getModelo() {
		return modelo;
	}


	public void setModelo(PersonaJuridica modelo) {
		this.modelo = modelo;
	}
}
