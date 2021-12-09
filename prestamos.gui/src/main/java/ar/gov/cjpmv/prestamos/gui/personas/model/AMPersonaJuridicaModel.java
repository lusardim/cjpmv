package ar.gov.cjpmv.prestamos.gui.personas.model;

import org.jdesktop.application.AbstractBean;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;

public class AMPersonaJuridicaModel extends AbstractBean {
	
	private PersonaJuridica persona;

	public AMPersonaJuridicaModel(PersonaJuridica persona) {
		super();
		this.persona = persona;
	}
	
	public PersonaJuridica getPersona() {
		return persona;
	}

	public void setPersona(PersonaJuridica persona) {
		PersonaJuridica locPersonaAnterior = this.persona;
		this.persona = persona;
		this.firePropertyChange("persona",locPersonaAnterior,this.persona);
	}
}
