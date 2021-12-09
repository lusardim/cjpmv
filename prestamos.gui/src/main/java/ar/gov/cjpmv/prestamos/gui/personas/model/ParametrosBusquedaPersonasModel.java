package ar.gov.cjpmv.prestamos.gui.personas.model;

import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;

public class ParametrosBusquedaPersonasModel {
	private Long legajo;
	private Long Cuip;
	private TipoDocumento tipoDocumento;
	private String apellido;
	private String estado;
	private Integer numeroDocumento;
	public Long getLegajo() {
		return legajo;
	}
	public void setLegajo(Long legajo) {
		this.legajo = legajo;
	}
	public Long getCuip() {
		return Cuip;
	}
	public void setCuip(Long cuip) {
		Cuip = cuip;
	}
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(Integer numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	
}
