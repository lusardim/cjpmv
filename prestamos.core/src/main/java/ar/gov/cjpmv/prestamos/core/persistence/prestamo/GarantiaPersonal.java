package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;

@Entity
public class GarantiaPersonal extends Garantia{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4785639202164594795L;
	
	@ManyToOne(optional=false)
	private PersonaFisica garante;

	public PersonaFisica getGarante() {
		return garante;
	}

	public void setGarante(PersonaFisica garante) {
		this.garante = garante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((garante == null) ? 0 : garante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		GarantiaPersonal other = (GarantiaPersonal) obj;
		if (garante == null) {
			if (other.garante != null)
				return false;
		} else{
			if (!garante.equals(other.garante))
				return false;
		}
		return true;
	}
	
}
