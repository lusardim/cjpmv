package ar.gov.cjpmv.prestamos.gui.sueldos;

import java.util.List;

public class ImpresionReciboHaberes {
	
	private String apellidoNombres;
	private String legajo;
	private List<ImpresionLineaRecibo> listaLineaRecibo;
	private String numeroDocumento;
	private String fechaIngreso;
	private String montoHaberes;
	private String montoDescuento;
	private String montoNeto;
	private String montoNetoLetras;
	
	
	public ImpresionReciboHaberes(String apellidoNombres, String legajo,
			List<ImpresionLineaRecibo> listaLineaRecibo,
			String numeroDocumento, String fechaIngreso, String montoHaberes,
			String montoDescuento, String montoNeto, String montoNetoLetras) {
		super();
		this.apellidoNombres = apellidoNombres;
		this.legajo = legajo;
		this.listaLineaRecibo = listaLineaRecibo;
		this.numeroDocumento = numeroDocumento;
		this.fechaIngreso = fechaIngreso;
		this.montoHaberes = montoHaberes;
		this.montoDescuento = montoDescuento;
		this.montoNeto = montoNeto;
		this.montoNetoLetras = montoNetoLetras;
	}

	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getMontoHaberes() {
		return montoHaberes;
	}

	public void setMontoHaberes(String montoHaberes) {
		this.montoHaberes = montoHaberes;
	}

	public String getMontoDescuento() {
		return montoDescuento;
	}

	public void setMontoDescuento(String montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public String getMontoNeto() {
		return montoNeto;
	}

	public void setMontoNeto(String montoNeto) {
		this.montoNeto = montoNeto;
	}

	public String getMontoNetoLetras() {
		return montoNetoLetras;
	}

	public void setMontoNetoLetras(String montoNetoLetras) {
		this.montoNetoLetras = montoNetoLetras;
	}
	
	public String getApellidoNombres() {
		return apellidoNombres;
	}
	public void setApellidoNombres(String apellidoNombres) {
		this.apellidoNombres = apellidoNombres;
	}
	public String getLegajo() {
		return legajo;
	}
	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	public List<ImpresionLineaRecibo> getListaLineaRecibo() {
		return listaLineaRecibo;
	}
	public void setListaLineaRecibo(List<ImpresionLineaRecibo> listaLineaRecibo) {
		this.listaLineaRecibo = listaLineaRecibo;
	}
	

}
