package ar.gov.cjpmv.prestamos.gui.personas.model;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.gui.comunes.interfaces.AMModel;

public class AMMailModel implements AMModel{
	
	private String email;
	private boolean aceptado = false;
	
	public AMMailModel(String pEmail) {
		this();
		this.email = pEmail;
	}

	public AMMailModel() {
		super();
	}
	
	@Override
	public String getDescripcion() {
		return this.email;
	}

	@Override
	public void guardar() throws LogicaException {
		this.email = email.trim();
		if (!this.email.matches(".+@.+\\..+")){
			throw new LogicaException(4,email );
		}
		this.aceptado = true;
	}

	@Override
	public void setDescripcion(String pDescripcion) {
		this.email = pDescripcion;
	}

	public boolean isAceptado() {
		return aceptado;
	}

}
