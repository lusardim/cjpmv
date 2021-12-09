package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ar.gov.cjpmv.prestamos.core.persistence.Domicilio;

@Entity
public class GarantiaPropietaria extends Garantia{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3062705268529341218L;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(nullable=true)
	private Domicilio propiedad;

	public Domicilio getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(Domicilio propiedad) {
		this.propiedad = propiedad;
	}
	
}
