package ar.gov.cjpmv.prestamos.gui.reportes;

public class LiquidacionMunicipalidadImpresion {
	
	private String cuil;
	private String numeroLegajo;
	private String apellido;
	private String nombres;
	private String montoLiquidar;
	
	
	
	public LiquidacionMunicipalidadImpresion(String cuil, String numeroLegajo,
			String apellido, String nombres, String montoLiquidar) {
		super();
		this.cuil = cuil;
		this.numeroLegajo = numeroLegajo;
		this.apellido = apellido;
		this.nombres = nombres;
		this.montoLiquidar = montoLiquidar;
	}
	
	
	public String getCuil() {
		return cuil;
	}
	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	public String getNumeroLegajo() {
		return numeroLegajo;
	}
	public void setNumeroLegajo(String numeroLegajo) {
		this.numeroLegajo = numeroLegajo;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getMontoLiquidar() {
		return montoLiquidar;
	}
	public void setMontoLiquidar(String montoLiquidar) {
		this.montoLiquidar = montoLiquidar;
	}

}
