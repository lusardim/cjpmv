package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

public enum TipoLiquidacion {
	ACTIVO_NORMAL("Activo Normal"),
	ACTIVO_SAC("Activo SAC"),
	JUBILACION_NORMAL("Jubilación Normal"),
	JUBILACION_SAC("Jubilación SAC"),
	PENSION_NORMAL("Pension Normal"),
	PENSION_SAC("Pension SAC");
	
	private String cadena;
	
	private TipoLiquidacion(String cadena) {
		this.cadena = cadena;
	}

	public String getCadena(){
		return cadena;
	}

	public boolean isSAC() {
		return this == ACTIVO_SAC || 
			   this == JUBILACION_SAC || 
			   this == PENSION_SAC;  
	}

	public boolean isActivo() {
		return this == ACTIVO_NORMAL ||
			   this == ACTIVO_SAC;
	}

	public boolean isPension() {
		return this == PENSION_NORMAL || 
			   this == PENSION_SAC;
	}
	
}
