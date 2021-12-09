package ar.gov.cjpmv.prestamos.core.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;


@Entity
public class PersonaJuridica extends Persona{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9092837040913881586L;
	
	
	@Temporal(TemporalType.DATE)
	private Date fechaCeseActividades;
	
	@ManyToMany
	private List<PersonaFisica> listaSocios;
	
	@Enumerated(EnumType.STRING)
	private EstadoPersonaJuridica estado=EstadoPersonaJuridica.ACTIVO;
	
	public void setFechaCeseActividades(Date fechaCeseActividades) {
		this.fechaCeseActividades = fechaCeseActividades;
	}
	public Date getFechaCeseActividades() {
		return fechaCeseActividades;
	}
	
	public String getRazonSocial(){
		return super.getNombre();		
	}
	public void setListaSocios(List<PersonaFisica> listaSocios) {
		this.listaSocios = listaSocios;
	}
	public List<PersonaFisica> getListaSocios() {
		return listaSocios;
	}

	@Override
	public String getNombreYApellido() {
		return (this.getRazonSocial()!=null)?this.getRazonSocial():"";
	}
	public EstadoPersonaJuridica getEstado() {
		return estado;
	}
	public void setEstado(EstadoPersonaJuridica estado) {
		this.estado = estado;
	}
	
	
	@Override
	public String toString() {
		return this.getRazonSocial();
	}
}
