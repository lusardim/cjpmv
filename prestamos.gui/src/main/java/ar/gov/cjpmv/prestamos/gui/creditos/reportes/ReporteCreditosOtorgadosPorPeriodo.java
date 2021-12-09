package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

public class ReporteCreditosOtorgadosPorPeriodo {

	private String numeroCredito;
	private String legajo;
	private String solicitante;
	private String fechaOtorgamiento;
	private String capitalOtorgado;
	private String montoSellado;
	private String montoFondoQuebranto;
	private String montoOtrosConceptos;

	
	
	
	
	public ReporteCreditosOtorgadosPorPeriodo(String numeroCredito,
			String legajo, String solicitante,
			String fechaOtorgamiento, String capitalOtorgado, 
			String montoSellado, String montoFondoQuebranto, String montoOtrosConceptos) {
		super();
		this.numeroCredito = numeroCredito;
		this.legajo = legajo;
		this.solicitante = solicitante;
		this.fechaOtorgamiento = fechaOtorgamiento;
		this.capitalOtorgado = capitalOtorgado;
		this.montoSellado = montoSellado;
		this.montoFondoQuebranto = montoFondoQuebranto;
		this.montoOtrosConceptos= montoOtrosConceptos;
	
	}
	
	public String getNumeroCredito() {
		return numeroCredito;
	}
	public void setNumeroCredito(String numeroCredito) {
		this.numeroCredito = numeroCredito;
	}
	public String getLegajo() {
		return legajo;
	}
	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getFechaOtorgamiento() {
		return fechaOtorgamiento;
	}
	public void setFechaOtorgamiento(String fechaOtorgamiento) {
		this.fechaOtorgamiento = fechaOtorgamiento;
	}
	public String getCapitalOtorgado() {
		return capitalOtorgado;
	}
	public void setCapitalOtorgado(String capitalOtorgado) {
		this.capitalOtorgado = capitalOtorgado;
	}
	public String getMontoSellado() {
		return montoSellado;
	}
	public void setMontoSellado(String montoSellado) {
		this.montoSellado = montoSellado;
	}
	public String getMontoFondoQuebranto() {
		return montoFondoQuebranto;
	}
	public void setMontoFondoQuebranto(String montoFondoQuebranto) {
		this.montoFondoQuebranto = montoFondoQuebranto;
	}



	public void setMontoOtrosConceptos(String montoOtrosConceptos) {
		this.montoOtrosConceptos = montoOtrosConceptos;
	}



	public String getMontoOtrosConceptos() {
		return montoOtrosConceptos;
	}

}
