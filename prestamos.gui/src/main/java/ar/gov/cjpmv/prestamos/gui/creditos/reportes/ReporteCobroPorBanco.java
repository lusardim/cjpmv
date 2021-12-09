package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

public class ReporteCobroPorBanco {
	private String numeroBoleta;
	private String numeroCuenta;
	private String banco;
	private String fecha;
	private String monto;
	private String solicitante;
	
	
	public ReporteCobroPorBanco() {
		super();
	}

	public ReporteCobroPorBanco(String numeroBoleta, String numeroCuenta,
			String banco, String fecha, String monto, String solicitante) {
		super();
		this.numeroBoleta = numeroBoleta;
		this.numeroCuenta = numeroCuenta;
		this.banco = banco;
		this.fecha = fecha;
		this.monto = monto;
		this.solicitante = solicitante;
	}
	
	public String getNumeroBoleta() {
		return numeroBoleta;
	}
	public void setNumeroBoleta(String numeroBoleta) {
		this.numeroBoleta = numeroBoleta;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	

}
