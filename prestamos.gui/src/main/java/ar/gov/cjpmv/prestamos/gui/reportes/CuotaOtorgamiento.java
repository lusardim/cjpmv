package ar.gov.cjpmv.prestamos.gui.reportes;

public class CuotaOtorgamiento{

	private String numeroCuota;
	private String vencimientoCuota;
	private String otrosConceptos;
	private String interes;
	private String capital;
	private String valor;
	

	public CuotaOtorgamiento(String numeroCuota, String vencimiento,
			String otrosConceptos, String interes, String capital,
			String valor) {
		super();
		this.numeroCuota = numeroCuota;
		this.vencimientoCuota = vencimiento;
		this.otrosConceptos = otrosConceptos;
		this.interes = interes;
		this.capital = capital;
		this.valor = valor;
	}
	
	
	
	public String getNumeroCuota() {
		return numeroCuota;
	}
	public void setNumeroCuota(String numeroCuota) {
		this.numeroCuota = numeroCuota;
	}
	public String getVencimientoCuota() {
		return vencimientoCuota;
	}
	public void setVencimientoCuota(String vencimiento) {
		this.vencimientoCuota = vencimiento;
	}
	public String getOtrosConceptos() {
		return otrosConceptos;
	}
	public void setOtrosConceptos(String otrosConceptos) {
		this.otrosConceptos = otrosConceptos;
	}
	public String getInteres() {
		return interes;
	}
	public void setInteres(String interes) {
		this.interes = interes;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valorTotal) {
		this.valor = valorTotal;
	}
	
	
	
}
