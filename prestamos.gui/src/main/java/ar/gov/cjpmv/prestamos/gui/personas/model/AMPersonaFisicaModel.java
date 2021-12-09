package ar.gov.cjpmv.prestamos.gui.personas.model;


import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;

public class AMPersonaFisicaModel extends org.jdesktop.application.AbstractBean {
	
	private PersonaFisica persona;
	
	public AMPersonaFisicaModel(PersonaFisica pPersonaFisica) {
		this.persona = pPersonaFisica;
	}

	public PersonaFisica getPersona() {
		return persona;
	}

	public void setPersona(PersonaFisica persona) {
		PersonaFisica locPersona = this.persona;
		this.persona = persona;
		this.firePropertyChange("persona", locPersona,this.persona);
	}
}
