package ar.gov.cjpmv.prestamos.core.persistence.enums;

public enum SituacionCuota {
	CANCELADA("Cancelada"), NO_VENCIDA("Adeudada no vencida"), VENCIDA("Adeudada vencida");
	
	private String cadena;
	
	private SituacionCuota(String cadena) {
		this.cadena = cadena;
	}

	public String getCadena(){
		return cadena;
	}
	
}
