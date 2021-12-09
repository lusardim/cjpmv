package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Cancelacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3772301271043218019L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Basic(optional=false)
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	private BigDecimal monto;
	
	@ManyToOne(optional=false)
	private Cobro cobro;
	
	public Cancelacion() {
	}
	
	public Cancelacion(BigDecimal pMonto, Cobro pCobro){
		this.monto = pMonto;
		this.cobro = pCobro;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public Cobro getCobro() {
		return cobro;
	}
	public void setCobro(Cobro cobro) {
		this.cobro = cobro;
	}
}
