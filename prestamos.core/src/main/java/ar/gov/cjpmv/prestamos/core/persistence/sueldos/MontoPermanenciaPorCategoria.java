package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	uniqueConstraints={
		@UniqueConstraint(columnNames={"permanencia_id","categoria_id"})
})
public class MontoPermanenciaPorCategoria implements Serializable {

	private static final long serialVersionUID = -5278166237743562759L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="permanencia_id", nullable=false)
	private PermanenciaCategoria permanencia;
	@ManyToOne(optional=false)
	@JoinColumn(name="categoria_id", nullable=false)
	private Categoria categoria;
	@Basic(optional=false)
	@Column(nullable=false, columnDefinition="decimal(13,2)")
	private BigDecimal monto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PermanenciaCategoria getPermanencia() {
		return permanencia;
	}

	public void setPermanencia(PermanenciaCategoria permanencia) {
		this.permanencia = permanencia;
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