package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MontoAntiguedadPorCategoria implements Serializable {
	
	private static final long serialVersionUID = 1196997230567745457L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="antiguedad_id", nullable=false)
	private Antiguedad antiguedad;
	@ManyToOne(optional=false)
	@JoinColumn(name="categoria_id", nullable=false)
	private Categoria categoria;

	@Column(nullable=false, columnDefinition="decimal(13,2)")
	private BigDecimal monto;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Antiguedad getAntiguedad() {
		return antiguedad;
	}
	
	public void setAntiguedad(Antiguedad antiguedad) {
		this.antiguedad = antiguedad;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	
	
}
