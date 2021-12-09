package ar.gov.cjpmv.prestamos.core.persistence.prestamo;



import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CobroPorBanco extends Cobro {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6631518421007140185L;

	@Basic(optional=false)
	@Column(nullable=false)
	private int numeroBoleta;
	
	@ManyToOne(optional=false)
	private CuentaBancaria cuenta;
	
	@Basic(optional=false)
	@Column(nullable=false)
	private Boolean conSeguro=false;

	public int getNumeroBoleta() {
		return numeroBoleta;
	}

	public void setNumeroBoleta(int numeroBoleta) {
		this.numeroBoleta = numeroBoleta;
	}

	public void setCuenta(CuentaBancaria cuenta) {
		this.cuenta = cuenta;
	}

	public CuentaBancaria getCuenta() {
		return cuenta;
	}

	public void setConSeguro(Boolean conSeguro) {
		this.conSeguro = conSeguro;
	}

	public Boolean getConSeguro() {
		return conSeguro;
	}

}
