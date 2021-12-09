package ar.gov.cjpmv.prestamos.core.persistence.contable;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Movimiento implements Serializable {
	
	private static final long serialVersionUID = 6595886487239567159L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=true,columnDefinition="decimal(12,2)")
	private BigDecimal montoDebe;
	@Column(nullable=true,columnDefinition="decimal(12,2)")
	private BigDecimal montoHaber;
	@ManyToOne(optional=false)
	private Asiento asiento;
	@ManyToOne(optional=false)
	private Cuenta cuenta;
	private String descripcionCuenta;
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setMontoDebe(BigDecimal montoDebe) {
		this.montoDebe = montoDebe;
	}

	public BigDecimal getMontoDebe() {
		return montoDebe;
	}

	public void setMontoHaber(BigDecimal montoHaber) {
		this.montoHaber = montoHaber;
	}

	public BigDecimal getMontoHaber() {
		return montoHaber;
	}

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public String getDescripcionCuenta() {
		return descripcionCuenta;
	}

	public void setDescripcionCuenta(String descripcionCuenta) {
		this.descripcionCuenta = descripcionCuenta;
	}
	

}
