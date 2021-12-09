package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;

@Entity
@DiscriminatorValue("JUBILACION")
public class Jubilacion extends Beneficio {

	private static final long serialVersionUID = -293702798845586391L;
	
	@Override
	public void setTipoBeneficio(TipoBeneficio tipo) {
		if (TipoBeneficio.PENSION.equals(tipo)) {
			throw new IllegalArgumentException("Una jubilación no puede tener como tipo de beneficio una pensión");
		}
		super.setTipoBeneficio(tipo);
	}
}
