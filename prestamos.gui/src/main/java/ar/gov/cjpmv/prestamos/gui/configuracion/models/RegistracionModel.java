package ar.gov.cjpmv.prestamos.gui.configuracion.models;

import ar.gov.cjpmv.prestamos.core.business.dao.ConfiguracionSistemaDAO;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;

public class RegistracionModel {
	
	private PersonaJuridica personaJuridica;
	private boolean valor;
	
	
	public RegistracionModel(PersonaJuridica personaJuridica, boolean valor) {
		super();
		this.setPersonaJuridica(personaJuridica);
		this.setValor(valor);
	}


	public void setPersonaJuridica(PersonaJuridica personaJuridica) {
		this.personaJuridica = personaJuridica;
	}


	public PersonaJuridica getPersonaJuridica() {
		return personaJuridica;
	}


	public void setValor(boolean valor) {
		this.valor = valor;
	}


	public boolean isValor() {
		return valor;
	}
	
	
	public String toString(){
		if(valor){
			return this.personaJuridica.toString()+"  [REGISTRACIÓN ACTIVA]";
		}
		else{
			return this.personaJuridica.toString();
		}
	}
	
	


}
