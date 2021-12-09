package ar.gov.cjpmv.prestamos.core.persistence.enums;

public enum EstadoPersonaJuridica  {
	ACTIVO("Activo"), INACTIVO("Inactivo");

	private String descripcion;
	
	private EstadoPersonaJuridica(String descripcion){
		this.descripcion = descripcion;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
}
