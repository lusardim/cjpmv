package ar.gov.cjpmv.prestamos.gui.comunes.utiles;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;

public abstract class EstadoPersonaFormatter {
	public static EstadoPersonaFormatter getInstance(EstadoPersonaFisica p){
		return new EstadoPersonaFisicaFormatter(p);
	}
	
	public static EstadoPersonaFormatter getInstance(EstadoPersonaJuridica p){
		return new EstadoPersonaJuridicaFormatter(p);
	}
	
	public abstract String getTexto();
}
