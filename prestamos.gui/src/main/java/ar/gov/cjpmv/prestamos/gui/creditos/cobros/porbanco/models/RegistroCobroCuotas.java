package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.models;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;

public class RegistroCobroCuotas {
	
	private Cuota cuota;
	private boolean cobrar;
	
	public RegistroCobroCuotas(Cuota cuota, boolean cobrar) {
		super();
		this.cuota = cuota;
		this.cobrar = cobrar;
	}
	
	public Cuota getCuota() {
		return cuota;
	}
	public void setCuota(Cuota cuota) {
		this.cuota = cuota;
	}
	public boolean isCobrar() {
		return cobrar;
	}
	public void setCobrar(boolean cobrar) {
		this.cobrar = cobrar;
	}
}
