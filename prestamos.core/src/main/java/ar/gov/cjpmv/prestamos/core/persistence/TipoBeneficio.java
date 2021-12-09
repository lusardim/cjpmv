package ar.gov.cjpmv.prestamos.core.persistence;

import java.io.Serializable;

public enum TipoBeneficio implements Serializable {
	
	JUBILACION_COMUN("Jubilación ordinaria común"), 
	JUBILACION_INVALIDEZ("Jubilación por invalidez"),
	JUBILACION_EDAD("Jubilación por edad avanzada"),
	PENSION("Pension"),
	JUBILACION_ANTICIPADA("Jubilación anticipada");
	
	private String descripcion;
	
	private TipoBeneficio(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
}
