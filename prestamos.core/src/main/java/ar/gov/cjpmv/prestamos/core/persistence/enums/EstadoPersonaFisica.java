package ar.gov.cjpmv.prestamos.core.persistence.enums;

public enum EstadoPersonaFisica {
	ACTIVO("Activo"), PASIVO("Pasivo"), FALLECIDO("Fallecido");
	
	
	private String descripcion;
	
	private EstadoPersonaFisica(String descripcion){
		this.descripcion = descripcion;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
}
