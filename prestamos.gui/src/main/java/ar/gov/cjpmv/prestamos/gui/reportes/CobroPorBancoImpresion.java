package ar.gov.cjpmv.prestamos.gui.reportes;

public class CobroPorBancoImpresion {
	private String numeroCredito;
	private String solicitante;
	private String numeroCuota;
	private String vencimiento;
	private String valorTotal;
	
	
	public CobroPorBancoImpresion(String numeroCredito, String solicitante,
			String numeroCuota, String vencimiento, String valorTotal) {
		super();
		this.numeroCredito = numeroCredito;
		this.solicitante = solicitante;
		this.numeroCuota = numeroCuota;
		this.vencimiento = vencimiento;
		this.valorTotal = valorTotal;
	}
	
	
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}
	public String getVencimiento() {
		return vencimiento;
	}
	public void setNumeroCuota(String numeroCuota) {
		this.numeroCuota = numeroCuota;
	}
	public String getNumeroCuota() {
		return numeroCuota;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setNumeroCredito(String numeroCredito) {
		this.numeroCredito = numeroCredito;
	}
	public String getNumeroCredito() {
		return numeroCredito;
	}
	
}
