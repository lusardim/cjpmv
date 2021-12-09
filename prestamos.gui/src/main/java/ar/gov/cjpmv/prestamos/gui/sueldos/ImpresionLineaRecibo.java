package ar.gov.cjpmv.prestamos.gui.sueldos;

public class ImpresionLineaRecibo {

	private String codigoConcepto;
	private String descripcionConcepto;
	private String montoHaberes;
	private String montoDescuento;
	private String montoNeto;
	
	
	

	public ImpresionLineaRecibo(String codigoConcepto,
			String descripcionConcepto, String montoHaberes, String montoDecuento, String montoNeto) {
		super();
		this.codigoConcepto = codigoConcepto;
		this.descripcionConcepto = descripcionConcepto;
		this.montoHaberes = montoHaberes;
		this.montoDescuento= montoDecuento;
		this.montoNeto= montoNeto;
	
	}
	
	public String getCodigoConcepto() {
		return codigoConcepto;
	}
	public void setCodigoConcepto(String codigoConcepto) {
		this.codigoConcepto = codigoConcepto;
	}
	public String getDescripcionConcepto() {
		return descripcionConcepto;
	}
	public void setDescripcionConcepto(String descripcionConcepto) {
		this.descripcionConcepto = descripcionConcepto;
	}
	
	public String getMontoHaberes() {
		return montoHaberes;
	}

	public void setMontoHaberes(String montoHaberes) {
		this.montoHaberes = montoHaberes;
	}

	public String getMontoDescuento() {
		return montoDescuento;
	}

	public void setMontoDescuento(String montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public String getMontoNeto() {
		return montoNeto;
	}

	public void setMontoNeto(String montoNeto) {
		this.montoNeto = montoNeto;
	}
}
