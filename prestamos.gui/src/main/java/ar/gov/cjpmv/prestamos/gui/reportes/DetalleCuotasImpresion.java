package ar.gov.cjpmv.prestamos.gui.reportes;

public class DetalleCuotasImpresion {
	private String numeroCuota;
	private String vencimiento;
	private String otrosConceptos;
	private String interes;
	private String capital;
	private String valorTotal;
	private String estado;
	
	
	public String getNumeroCuota() {
		return numeroCuota;
	}


	public void setNumeroCuota(String numeroCuota) {
		this.numeroCuota = numeroCuota;
	}


	public String getVencimiento() {
		return vencimiento;
	}


	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
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


	public String getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public DetalleCuotasImpresion(String numeroCuota, String vencimiento,
			String otrosConceptos, String interes, String capital,
			String valorTotal, String estado) {
		super();
		this.numeroCuota = numeroCuota;
		this.vencimiento = vencimiento;
		this.otrosConceptos = otrosConceptos;
		this.interes = interes;
		this.capital = capital;
		this.valorTotal = valorTotal;
		this.estado = estado;
	}
	
	
	

}
