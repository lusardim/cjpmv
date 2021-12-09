package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import ar.gov.cjpmv.prestamos.core.business.sueldos.ServicioCalculoConcepto;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;


/**
 * Esta clase es un concepto utilizado en el recibo de haberes.
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class ConceptoHaberes implements Serializable {

	private static final long serialVersionUID = 7190117562006790084L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false, unique=true)
	private int codigo;
	private String descripcion;
	@Enumerated(EnumType.STRING)
	private TipoCodigo tipoCodigo;
	
	@Transient
	private ServicioCalculoConcepto servicio;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public TipoCodigo getTipoCodigo() {
		return tipoCodigo;
	}
	public void setTipoCodigo(TipoCodigo tipoCodigo) {
		this.tipoCodigo = tipoCodigo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
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
		ConceptoHaberes other = (ConceptoHaberes) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	
	public abstract BigDecimal getMonto(ReciboSueldo recibo);
	
	public int getCantidad(ReciboSueldo recibo) {
		Integer cantidadAnterior = servicio.getCantidad(this, recibo);
		if (cantidadAnterior == null) {
			return 1;
		}
		return cantidadAnterior;
	}
	
	@Override
	public String toString() {
		return codigo + " - "+ descripcion + " (" + tipoCodigo + ")";
	}
	
	public void setServicio(ServicioCalculoConcepto servicio) {
		this.servicio = servicio;
	}
	 
	public ServicioCalculoConcepto getServicio() {
		return servicio;
	}
}
