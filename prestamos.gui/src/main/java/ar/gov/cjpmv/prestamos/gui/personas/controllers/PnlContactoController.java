package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import javax.swing.JDialog;

import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDatosContactoView;

public class PnlContactoController{
	private PnlDatosContactoView vista;
	private JDialog padre;
	
	private PnlDomicilioController domicilioController;
	private PnlTelefonoController telefonoController;
	private PnlEmailsController emailController;
	
	
	public PnlContactoController(PnlDatosContactoView pVista,JDialog pPadre){
		this.padre = pPadre;
		this.vista = pVista;
		this.domicilioController = new PnlDomicilioController(this.vista.getPnlDomicilio());
		this.telefonoController = new PnlTelefonoController(this.vista.getPnlListadoTelefonos(), this.padre);
		this.emailController = new PnlEmailsController(this.vista.getPnlListadoEmails(),this.padre);
	}
	
	public PnlDomicilioController getDomicilioController() {
		return domicilioController;
	}

	public PnlTelefonoController getTelefonoController() {
		return telefonoController;
	}

	public PnlEmailsController getEmailController() {
		return emailController;
	}

	public PnlDatosContactoView getVista() {
		return vista;
	}

		
}
