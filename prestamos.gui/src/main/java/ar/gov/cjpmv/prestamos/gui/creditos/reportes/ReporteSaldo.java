package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

public class ReporteSaldo {
	private String viaCobro;
	private String cantidadCreditosEnCurso;
	private String saldoTotalAdeudado;
	
	
	public ReporteSaldo(String viaCobro, String cantidadCreditosEnCurso,
			String saldoTotalAdeudado) {
		this.viaCobro = viaCobro;
		this.cantidadCreditosEnCurso = cantidadCreditosEnCurso;
		this.saldoTotalAdeudado = saldoTotalAdeudado;
	}
	

	public String getViaCobro() {
		return viaCobro;
	}
	public void setViaCobro(String viaCobro) {
		this.viaCobro = viaCobro;
	}
	public String getCantidadCreditosEnCurso() {
		return cantidadCreditosEnCurso;
	}
	public void setCantidadCreditosEnCurso(String cantidadCreditosEnCurso) {
		this.cantidadCreditosEnCurso = cantidadCreditosEnCurso;
	}
	public String getSaldoTotalAdeudado() {
		return saldoTotalAdeudado;
	}
	public void setSaldoTotalAdeudado(String saldoTotalAdeudado) {
		this.saldoTotalAdeudado = saldoTotalAdeudado;
	}
	
	


}
