package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

public class ReporteSaldoDetallado {
	private String numeroCredito;
	private String solicitante;
	private String montoTotal;
	private String cantidadCuotas;
	private String cuotasAdeudadas;
	private String saldoAdeudado;
	private String capitalAdeudado;
	private String interesAdeudado;
	private String otrosConceptosAdeudado;
	private String legajo;
	
	

	public String getCapitalAdeudado() {
		return capitalAdeudado;
	}

	public void setCapitalAdeudado(String capitalAdeudado) {
		this.capitalAdeudado = capitalAdeudado;
	}

	public String getInteresAdeudado() {
		return interesAdeudado;
	}

	public void setInteresAdeudado(String interesAdeudado) {
		this.interesAdeudado = interesAdeudado;
	}

	public String getOtrosConceptosAdeudado() {
		return otrosConceptosAdeudado;
	}

	public void setOtrosConceptosAdeudado(String otrosConceptosAdeudado) {
		this.otrosConceptosAdeudado = otrosConceptosAdeudado;
	}

	ReporteSaldoDetallado(String numeroCredito, String solicitante,
			String montoTotal, String cantidadCuotas, String cuotasAdeudadas,
			String saldoAdeudado, String capitalAdeudado, String interesAdeudado, String otrosConceptosAdeudado, String legajo) {
		this.numeroCredito = numeroCredito;
		this.solicitante = solicitante;
		this.montoTotal = montoTotal;
		this.cantidadCuotas = cantidadCuotas;
		this.cuotasAdeudadas = cuotasAdeudadas;
		this.saldoAdeudado = saldoAdeudado;
		this.capitalAdeudado= capitalAdeudado;
		this.interesAdeudado= interesAdeudado;
		this.otrosConceptosAdeudado= otrosConceptosAdeudado;
		this.legajo= legajo;
	}
	
	public String getNumeroCredito() {
		return numeroCredito;
	}


	public void setNumeroCredito(String numeroCredito) {
		this.numeroCredito = numeroCredito;
	}


	public String getSolicitante() {
		return solicitante;
	}


	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}


	public String getMontoTotal() {
		return montoTotal;
	}


	public void setMontoTotal(String montoTotal) {
		this.montoTotal = montoTotal;
	}


	public String getCantidadCuotas() {
		return cantidadCuotas;
	}


	public void setCantidadCuotas(String cantidadCuotas) {
		this.cantidadCuotas = cantidadCuotas;
	}


	public String getCuotasAdeudadas() {
		return cuotasAdeudadas;
	}


	public void setCuotasAdeudadas(String cuotasAdeudadas) {
		this.cuotasAdeudadas = cuotasAdeudadas;
	}


	public String getSaldoAdeudado() {
		return saldoAdeudado;
	}


	public void setSaldoAdeudado(String saldoAdeudado) {
		this.saldoAdeudado = saldoAdeudado;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public String getLegajo() {
		return legajo;
	}




	
	
	
}
