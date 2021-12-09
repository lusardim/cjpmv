package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

public class ReporteSaldoActivoCorrienteNoCorriente {
	
	private String numeroCredito;
	private String legajo;
	private String solicitante;
	private String capitalAdeudadoCorriente;
	private String capitalAdeudadoNoCorriente;
	private String capitalAdeudadoTotal;
	
	
	
	
	
	
	public ReporteSaldoActivoCorrienteNoCorriente(String numeroCredito,
			String legajo, String solicitante,
			String capitalAdeudadoCorriente, String capitalAdeudadoNoCorriente,
			String capitalAdeudadoTotal) {
		super();
		this.numeroCredito = numeroCredito;
		this.legajo = legajo;
		this.solicitante = solicitante;
		this.capitalAdeudadoCorriente = capitalAdeudadoCorriente;
		this.capitalAdeudadoNoCorriente = capitalAdeudadoNoCorriente;
		this.capitalAdeudadoTotal = capitalAdeudadoTotal;
	}
	
	
	public void setNumeroCredito(String numeroCredito) {
		this.numeroCredito = numeroCredito;
	}
	public String getNumeroCredito() {
		return numeroCredito;
	}
	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	public String getLegajo() {
		return legajo;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setCapitalAdeudadoCorriente(String capitalAdeudadoCorriente) {
		this.capitalAdeudadoCorriente = capitalAdeudadoCorriente;
	}
	public String getCapitalAdeudadoCorriente() {
		return capitalAdeudadoCorriente;
	}
	public void setCapitalAdeudadoNoCorriente(String capitalAdeudadoNoCorriente) {
		this.capitalAdeudadoNoCorriente = capitalAdeudadoNoCorriente;
	}
	public String getCapitalAdeudadoNoCorriente() {
		return capitalAdeudadoNoCorriente;
	}
	public void setCapitalAdeudadoTotal(String capitalAdeudadoTotal) {
		this.capitalAdeudadoTotal = capitalAdeudadoTotal;
	}
	public String getCapitalAdeudadoTotal() {
		return capitalAdeudadoTotal;
	}
	
	
	

}
