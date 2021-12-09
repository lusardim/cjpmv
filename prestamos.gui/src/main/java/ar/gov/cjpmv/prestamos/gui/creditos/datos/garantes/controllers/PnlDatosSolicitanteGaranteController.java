package ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.controllers;

import javax.swing.JFrame;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlDatosSolicitanteGarante;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlDatosSolicitanteGaranteController {
	private PnlDatosSolicitanteGarante vista;
	private GarantiaPersonal modelo;
	
	public PnlDatosSolicitanteGaranteController(GarantiaPersonal pGarantiaPersonal) {
		this.modelo = pGarantiaPersonal;
		vista = new PnlDatosSolicitanteGarante();
		vista.setControlador(this);
		this.actualizarVista();
	}
	
	public void actualizarVista() {
		vista.getXtsTitulo().setTitle("Datos de Garantías");
		if (this.modelo == null){
			limpiar();
		}
		else{
			vista.getLblApellidoRazon().setText(Utiles.vacioSiNulo(this.modelo.getGarante().getApellido()));
			vista.getLblCui().setText(Utiles.getCuiFormateado(this.modelo.getGarante().getCui()));
			vista.getLblEstado().setText(this.getString(this.modelo.getGarante().getEstado()));
			vista.getLblLegajo().setText(Utiles.cadenaVaciaSiNulo(this.modelo.getGarante().getLegajo()));
			vista.getLblNumeroDocumento().setText(Utiles.cadenaVaciaSiNulo(this.modelo.getGarante().getNumeroDocumento()));
			vista.getLblTipoDocumento().setText(Utiles.cadenaVaciaSiNulo((this.modelo.getGarante().getTipoDocumento())));
		}
	}
	
	private String getString(EstadoPersonaFisica estado) {
		if (estado == null) {
			return "";
		}
		else{
			switch(estado){
				case ACTIVO: return "Activo";
				case FALLECIDO: return "Fallecido";
				case PASIVO: return "Pasivo";
				default: return "";
			}
		}
	}

	private void limpiar() {
		vista.getLblApellidoRazon().setText("");
		vista.getLblCui().setText("");
		vista.getLblEstado().setText("");
		vista.getLblLegajo().setText("");
		vista.getLblNumeroDocumento().setText("");
		vista.getLblTipoDocumento().setText("");
	}


	public PnlDatosSolicitanteGarante getVista() {
		return vista;
	}


	public GarantiaPersonal getModelo() {
		return modelo;
	}


	public void setModelo(GarantiaPersonal modelo) {
		this.modelo = modelo;
		this.actualizarVista();
	}
	
	
	public static void main(String[] args){
		PersonaFisica locPersona = GestorPersitencia.getInstance().getEntityManager().getReference(PersonaFisica.class, 2l);
		GarantiaPersonal locGarantia = new GarantiaPersonal();
		locGarantia.setGarante(locPersona);

		JFrame locFrame = new JFrame();
		PnlDatosSolicitanteGaranteController controller = new PnlDatosSolicitanteGaranteController(locGarantia);
		locFrame.add(controller.getVista());
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		locFrame.pack();
		locFrame.setVisible(true);
	}
}
