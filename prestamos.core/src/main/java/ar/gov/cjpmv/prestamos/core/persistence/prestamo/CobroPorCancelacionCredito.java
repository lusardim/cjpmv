package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class CobroPorCancelacionCredito extends Cobro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6255027935489347250L;
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
	private DetalleCredito detalleCreditoCancelaciones;
	
	
	public DetalleCredito getDetalleCreditoCancelaciones() {
		return detalleCreditoCancelaciones;
	}

	public void setDetalleCreditoCancelaciones(
			DetalleCredito detalleCreditoCancelaciones) {
		this.detalleCreditoCancelaciones = detalleCreditoCancelaciones;
	}
}
