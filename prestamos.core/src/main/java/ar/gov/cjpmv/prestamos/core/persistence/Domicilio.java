package ar.gov.cjpmv.prestamos.core.persistence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;



@Entity
public class Domicilio implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1460698111764532688L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Basic(optional=false)
	private String calle;
	
	private Integer numero;

	private Integer piso;
	
	private String numeroDepartamento;
	
	private String observacion;
	
	@OneToOne(mappedBy="domicilio")
	private Persona persona;
	
	@ManyToOne
	private Localidad localidad;
	
	@ManyToOne
	private Departamento departamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public String getNumeroDepartamento() {
		return numeroDepartamento;
	}

	public void setNumeroDepartamento(String numeroDepartamento) {
		this.numeroDepartamento = numeroDepartamento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Provincia getProvincia() {
		if (this.departamento==null){
			if (this.localidad == null){
				return null;
			}
			return this.localidad.getDepartamento().getProvincia();
		}
		else{
			return this.departamento.getProvincia();
		}
	}
	
	
}
