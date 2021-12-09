package ar.gov.cjpmv.prestamos.gui.reportes;

public class ConceptoNoAplicadoCuotasOtorgamiento {

	private String nombreConcepto;
	private String montoConcepto;
	private String observacionConcepto;
	
	public ConceptoNoAplicadoCuotasOtorgamiento(String nombreConcepto,
			String montoConcepto, String observacionConcepto) {
		super();
		this.nombreConcepto = nombreConcepto;
		this.montoConcepto = montoConcepto;
		this.observacionConcepto = observacionConcepto;
	}
	
	
	public String getNombreConcepto() {
		return nombreConcepto;
	}


	public void setNombreConcepto(String nombreConcepto) {
		this.nombreConcepto = nombreConcepto;
	}


	public String getMontoConcepto() {
		return montoConcepto;
	}


	public void setMontoConcepto(String montoConcepto) {
		this.montoConcepto = montoConcepto;
	}


	public String getObservacionConcepto() {
		return observacionConcepto;
	}


	public void setObservacionConcepto(String observacionConcepto) {
		this.observacionConcepto = observacionConcepto;
	}



	
}
