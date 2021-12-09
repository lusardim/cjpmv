package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;

@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = -1676973692608157048L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer numero;
	private String nombre;
	
	@Enumerated(EnumType.STRING)
	private EstadoPersonaFisica tipoPersona;
	
	@Column(columnDefinition="decimal(3,2)")
	private BigDecimal porcentaje;
	//o tiene un monto, o un porcentaje sobre otra categoria.
	@Column(columnDefinition="decimal(13,2)")
	private BigDecimal monto;
	@ManyToOne
	private Categoria porcentajeSobre;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(BigDecimal porcentaje) {
		if (porcentaje != null) {
			this.monto = null;
		}
		this.porcentaje = porcentaje;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		if (monto != null) {
			porcentaje = null;
		}
		this.monto = monto;
	}
	public Categoria getPorcentajeSobre() {
		return porcentajeSobre;
	}
	public void setPorcentajeSobre(Categoria porcentajeSobre) {
		this.porcentajeSobre = porcentajeSobre;
	}
	
	@Transient
	public BigDecimal getTotal() {
		if (monto != null) {
			return monto;
		}
		else {
			//  (1+porcentaje)*total 
			return porcentaje.add(new BigDecimal(1))
					.multiply(porcentajeSobre.getTotal())
					.setScale(2, RoundingMode.HALF_UP);
		}
	}
	
	@Override
	public String toString() {
		return getNombre()+" "+getNumero();
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
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public EstadoPersonaFisica getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(EstadoPersonaFisica tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	
}
