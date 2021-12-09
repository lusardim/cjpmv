package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

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
public class LineaRecibo implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -7294765404442423493L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer cantidad;
	@Column(columnDefinition="decimal(10,2)", nullable=false)
	@Basic(optional=false)
	private BigDecimal monto;
	@ManyToOne
	private ConceptoHaberes concepto;
	@ManyToOne
	private ReciboSueldo reciboSueldo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public ConceptoHaberes getConcepto() {
		return concepto;
	}
	public void setConcepto(ConceptoHaberes concepto) {
		this.concepto = concepto;
	}
	public ReciboSueldo getReciboSueldo() {
		return reciboSueldo;
	}
	public void setReciboSueldo(ReciboSueldo reciboSueldo) {
		this.reciboSueldo = reciboSueldo;
	}
	
	public BigDecimal getTotal() {
		return monto.multiply(new BigDecimal(cantidad));
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((concepto == null) ? 0 : concepto.hashCode());
		result = prime * result
				+ ((reciboSueldo == null) ? 0 : reciboSueldo.hashCode());
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
		LineaRecibo other = (LineaRecibo) obj;
		if (concepto == null) {
			if (other.concepto != null)
				return false;
		} else if (!concepto.equals(other.concepto))
			return false;
		if (reciboSueldo == null) {
			if (other.reciboSueldo != null)
				return false;
		} else if (!reciboSueldo.equals(other.reciboSueldo))
			return false;
		return true;
	}

	public LineaRecibo clone() throws CloneNotSupportedException{
		return (LineaRecibo) super.clone();
	}
	
	
}
