package ar.gov.cjpmv.prestamos.gui.reportes;

public class EstadoCuentaReporte {
	private String numeroCredito;
	private String fechaInicio;
	private String montoTotal;
	private String cantidadCuotas;
	private String cuotasAdeudadas;
	private String saldoAdeudado;
	private String actor;
	private String textoDetalleCuentaCorriente;
	private String textoSobranteCuentaCorriente;
	private String tipoCredito;




	public String getTextoSobranteCuentaCorriente() {
		return textoSobranteCuentaCorriente;
	}


	public void setTextoSobranteCuentaCorriente(String textoSobranteCuentaCorriente) {
		this.textoSobranteCuentaCorriente = textoSobranteCuentaCorriente;
	}


	public String getTextoDetalleCuentaCorriente() {
		return textoDetalleCuentaCorriente;
	}


	public void setTextoDetalleCuentaCorriente(String textoDetalleCuentaCorriente) {
		this.textoDetalleCuentaCorriente = textoDetalleCuentaCorriente;
	}






	public EstadoCuentaReporte(String numeroCredito, String fechaInicio,
			String montoTotal, String cantidadCuotas, String cuotasAdeudadas,
			String saldoAdeudado, String actor, String textoSobranteCuentaCorriente, String textoDetalleCuentaCorriente, String tipoCredito) {
		super();
		this.numeroCredito = numeroCredito;
		this.fechaInicio = fechaInicio;
		this.montoTotal = montoTotal;
		this.cantidadCuotas = cantidadCuotas;
		this.cuotasAdeudadas = cuotasAdeudadas;
		this.saldoAdeudado = saldoAdeudado;
		this.textoSobranteCuentaCorriente= textoSobranteCuentaCorriente;
		this.textoDetalleCuentaCorriente= textoDetalleCuentaCorriente;
		this.actor = actor;
		this.tipoCredito= tipoCredito;
	}
	
	
	public String getNumeroCredito() {
		return numeroCredito;
	}
	public void setNumeroCredito(String numeroCredito) {
		this.numeroCredito = numeroCredito;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
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
	
	
	public String getActor() {
		return actor;
	}


	public void setActor(String actor) {
		this.actor = actor;
	}


	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}


	public String getTipoCredito() {
		return tipoCredito;
	}
	
	
	
	

}
