package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;

@Entity
@DiscriminatorValue("PENSION")
public class Pension extends Beneficio {
	
	private static final long serialVersionUID = -5510806768115001775L;
	
	@ManyToOne
	@JoinColumn(name="causante_id")
	private PersonaFisica causante;

	public Pension() {
		this.setTipoBeneficio(TipoBeneficio.PENSION);
	}
	
	public PersonaFisica getCausante() {
		return causante;
	}

	public void setCausante(PersonaFisica causante) {
		if (causante != null && !causante.getEstado().equals(EstadoPersonaFisica.FALLECIDO)) {
			throw new IllegalArgumentException("El causante tiene que ser un fallecido");
		}
		this.causante = causante;
	}
}
