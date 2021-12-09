package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

public class ConceptoCobranzas {

	private String concepto;
	private String monto;
	
	public ConceptoCobranzas(String concepto, String monto) {
		super();
		this.concepto = concepto;
		this.monto = monto;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}
	
	

}
