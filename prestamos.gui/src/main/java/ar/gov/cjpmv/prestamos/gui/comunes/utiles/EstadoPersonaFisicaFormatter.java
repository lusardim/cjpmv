package ar.gov.cjpmv.prestamos.gui.comunes.utiles;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;

public class EstadoPersonaFisicaFormatter extends EstadoPersonaFormatter{
	
	private EstadoPersonaFisica estado;

	public EstadoPersonaFisicaFormatter(EstadoPersonaFisica pEstado) {
		this.estado = pEstado;
	}

	@Override
	public String getTexto() {
		switch(this.estado){
			case ACTIVO: return "Activo";
			case FALLECIDO: return "Fallecido";
			case PASIVO: return "Pasivo";
		}
		return null;
	}

	
}
