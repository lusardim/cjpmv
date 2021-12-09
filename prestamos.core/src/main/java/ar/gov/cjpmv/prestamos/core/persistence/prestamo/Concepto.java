package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


/**
 * Esta clase representa un concepto aplicado a un tipo de crédito.
 */
@Entity
public class Concepto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3125538979814415791L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String descripcion;

	@Basic(optional=false)
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	private BigDecimal valor;
	
	@Basic(optional=false)
	@Column(nullable=false)
	private Boolean porcentual=true;
	
	@Basic(optional=false)
	@Column(nullable=false)
	private Boolean aplicadoCuota=false;
	
	@Basic(optional=false)
	@Column(nullable=false)
	private Boolean emiteCheque=false;
	
	@ManyToMany(mappedBy="listaConceptos")
	private List<TipoCredito> listaCreditos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Boolean getPorcentual() {
		return porcentual;
	}

	public void setPorcentual(Boolean porcentual) {
		this.porcentual = porcentual;
	}

	public Boolean getAplicadoCuota() {
		return aplicadoCuota;
	}

	public void setAplicadoCuota(Boolean aplicadoCuota) {
		this.aplicadoCuota = aplicadoCuota;
	}

	public Boolean getEmiteCheque() {
		return emiteCheque;
	}

	public void setEmiteCheque(Boolean emiteCheque) {
		this.emiteCheque = emiteCheque;
	}

	public List<TipoCredito> getListaCreditos() {
		return listaCreditos;
	}

	public void setListaCreditos(List<TipoCredito> listaCreditos) {
		this.listaCreditos = listaCreditos;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Concepto other = (Concepto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String toString(){
		return this.getDescripcion();
	}
	
	
}
