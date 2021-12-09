package ar.gov.cjpmv.prestamos.gui.reportes;

public class FormularioRecibo {
	private String domicilioTelefonoCaja;
	private String fechaOtorgamiento;
	private String montoCredito;
	private String mesesCuotas;
	private String interes;
	private String sistema;
	private String valorCuota;
	private String diaVencimiento;
	private String primerVencimiento;
	private String porcentajeImpuestoSellos;
	private String nombreSolicitante;
	private String domicilioSolicitante;
	private String legajoNumero;
	private String numeroGarante;
	private String nombreGarante;
	private String domicilioGarante;
	private String legajoGarante;
	private String porcentajeFondoQuebranto;
	private String numeroDocumentoSolicitante;
	public String getNumeroDocumentoSolicitante() {
		return numeroDocumentoSolicitante;
	}


	public void setNumeroDocumentoSolicitante(String numeroDocumentoSolicitante) {
		this.numeroDocumentoSolicitante = numeroDocumentoSolicitante;
	}


	public String getTipoDocumentoSolicitante() {
		return tipoDocumentoSolicitante;
	}


	public void setTipoDocumentoSolicitante(String tipoDocumentoSolicitante) {
		this.tipoDocumentoSolicitante = tipoDocumentoSolicitante;
	}


	public String getNumeroDocumentoGarante() {
		return numeroDocumentoGarante;
	}


	public void setNumeroDocumentoGarante(String numeroDocumentoGarante) {
		this.numeroDocumentoGarante = numeroDocumentoGarante;
	}


	public String getTipoDocumentoGarante() {
		return tipoDocumentoGarante;
	}


	public void setTipoDocumentoGarante(String tipoDocumentoGarante) {
		this.tipoDocumentoGarante = tipoDocumentoGarante;
	}


	private String tipoDocumentoSolicitante;
	private String numeroDocumentoGarante;
	private String tipoDocumentoGarante;
	
	
	public FormularioRecibo(String domicilioTelefonoCaja,
			String fechaOtorgamiento, String montoCredito, String mesesCuotas,
			String interes, String sistema, String valorCuota,
			String diaVencimiento, String primerVencimiento,
			String porcentajeImpuestoSellos, String nombreSolicitante,
			String domicilioSolicitante, String legajoNumero, String numeroGarante,
			String nombreGarante, String domicilioGarante, String legajoGarante, 
			String porcentajeFondoQuebranto, String numeroDocumentoSolicitante, 
			String tipoDocumentoSolicitante, String numeroDocumentoGarante, 
			String tipoDocumentoGarante) {
		super();
		this.domicilioTelefonoCaja = domicilioTelefonoCaja;
		this.fechaOtorgamiento = fechaOtorgamiento;
		this.montoCredito = montoCredito;
		this.mesesCuotas = mesesCuotas;
		this.interes = interes;
		this.sistema = sistema;
		this.valorCuota = valorCuota;
		this.diaVencimiento = diaVencimiento;
		this.primerVencimiento = primerVencimiento;
		this.porcentajeImpuestoSellos = porcentajeImpuestoSellos;
		this.nombreSolicitante = nombreSolicitante;
		this.domicilioSolicitante= domicilioSolicitante;
		this.legajoNumero = legajoNumero;
		this.numeroGarante = numeroGarante;
		this.nombreGarante = nombreGarante;
		this.domicilioGarante = domicilioGarante;
		this.legajoGarante = legajoGarante;
		this.porcentajeFondoQuebranto = porcentajeFondoQuebranto;
		this.numeroDocumentoSolicitante= numeroDocumentoSolicitante;
		this.tipoDocumentoSolicitante = tipoDocumentoSolicitante;
		this.numeroDocumentoGarante = numeroDocumentoGarante;
		this.tipoDocumentoGarante = tipoDocumentoGarante;
	
	}
	
	
	public String getFechaOtorgamiento() {
		return fechaOtorgamiento;
	}
	public void setFechaOtorgamiento(String fechaOtorgamiento) {
		this.fechaOtorgamiento = fechaOtorgamiento;
	}
	public String getMontoCredito() {
		return montoCredito;
	}
	public void setMontoCredito(String montoCredito) {
		this.montoCredito = montoCredito;
	}
	public String getMesesCuotas() {
		return mesesCuotas;
	}
	public void setMesesCuotas(String mesesCuotas) {
		this.mesesCuotas = mesesCuotas;
	}
	public String getInteres() {
		return interes;
	}
	public void setInteres(String interes) {
		this.interes = interes;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public String getValorCuota() {
		return valorCuota;
	}
	public void setValorCuota(String valorCuota) {
		this.valorCuota = valorCuota;
	}
	public String getPrimerVencimiento() {
		return primerVencimiento;
	}
	public void setPrimerVencimiento(String primerVencimiento) {
		this.primerVencimiento = primerVencimiento;
	}
	public String getPorcentajeImpuestoSellos() {
		return porcentajeImpuestoSellos;
	}
	public void setPorcentajeImpuestoSellos(String porcentajeImpuestoSellos) {
		this.porcentajeImpuestoSellos = porcentajeImpuestoSellos;
	}
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}
	public String getLegajoNumero() {
		return legajoNumero;
	}
	public void setLegajoNumero(String legajoNumero) {
		this.legajoNumero = legajoNumero;
	}

	public String getNumeroGarante() {
		return numeroGarante;
	}
	public void setNumeroGarante(String numeroGarante) {
		this.numeroGarante = numeroGarante;
	}
	public String getNombreGarante() {
		return nombreGarante;
	}
	public void setNombreGarante(String nombreGarante) {
		this.nombreGarante = nombreGarante;
	}
	public String getLegajoGarante() {
		return legajoGarante;
	}
	public void setLegajoGarante(String legajoGarante) {
		this.legajoGarante = legajoGarante;
	}
	public void setDiaVencimiento(String diaVencimiento) {
		this.diaVencimiento = diaVencimiento;
	}
	public String getDiaVencimiento() {
		return diaVencimiento;
	}
	public void setDomicilioTelefonoCaja(String domicilioTelefonoCaja) {
		this.domicilioTelefonoCaja = domicilioTelefonoCaja;
	}
	public String getDomicilioTelefonoCaja() {
		return domicilioTelefonoCaja;
	}

	public void setDomicilioSolicitante(String domicilioSolicitante) {
		this.domicilioSolicitante = domicilioSolicitante;
	}


	public String getDomicilioSolicitante() {
		return domicilioSolicitante;
	}



	public void setDomicilioGarante(String domicilioGarante) {
		this.domicilioGarante = domicilioGarante;
	}


	public String getDomicilioGarante() {
		return domicilioGarante;
	}

	public void setPorcentajeFondoQuebranto(String porcentajeFondoQuebranto) {
		this.porcentajeFondoQuebranto = porcentajeFondoQuebranto;
	}


	public String getPorcentajeFondoQuebranto() {
		return porcentajeFondoQuebranto;
	}

	

}
