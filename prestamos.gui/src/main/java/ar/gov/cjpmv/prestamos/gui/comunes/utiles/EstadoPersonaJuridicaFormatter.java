package ar.gov.cjpmv.prestamos.gui.comunes.utiles;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;

public class EstadoPersonaJuridicaFormatter extends EstadoPersonaFormatter{

	private EstadoPersonaJuridica estado;

	public EstadoPersonaJuridicaFormatter(EstadoPersonaJuridica pEstado) {
		this.estado = pEstado;
	}

	@Override
	public String getTexto() {
		switch(this.estado){
			case ACTIVO: return "Activo";
			case INACTIVO: return "Inactivo";
		}
		return null;
	}
	
}
