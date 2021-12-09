package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.util.Date;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;

public class BusquedaPersonaJuridicaModel{

	private Long cuil;
	private String razonSocial;
	private EstadoPersonaJuridica estado;
	private Date fechaInicioActividades;
	private Date fechaFinActividades;

	public Long getCuil() {
		return cuil;
	}
	public void setCuil(Long cuil) {
		this.cuil = cuil;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public EstadoPersonaJuridica getEstado() {
		return estado;
	}
	public void setEstado(EstadoPersonaJuridica estado) {
		this.estado = estado;
	}
	public Date getFechaInicioActividades() {
		return fechaInicioActividades;
	}
	public void setFechaInicioActividades(Date fechaInicioActividades) {
		this.fechaInicioActividades = fechaInicioActividades;
	}
	public Date getFechaFinActividades() {
		return fechaFinActividades;
	}
	public void setFechaFinActividades(Date fechaFinActividades) {
		this.fechaFinActividades = fechaFinActividades;
	}
	
}
