package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;
import ar.gov.cjpmv.prestamos.gui.creditos.exportacion.PnlSeleccionCuotasReliquidacion;

public class PnlDchaPorPeriodo extends PnlDchaABMView {
	private PnlActivoCorrienteNoCorriente pnlActivoCorrienteNoCorriente;
	
	public void PnlDchaPorPeriodo(){
		this.initComponents();
	}

	public void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Reporte: Saldo por Período");
		this.pnlActivoCorrienteNoCorriente = new PnlActivoCorrienteNoCorriente();
		this.getTbpABM().add("Informe sobre activo corriente y no corriente",this.pnlActivoCorrienteNoCorriente);	
	
		
	}
	
	
	public PnlActivoCorrienteNoCorriente getPnlActivoCorrienteNoCorriente(){
		return pnlActivoCorrienteNoCorriente;
	}
	
	public void setPnlActivoCorrienteNoCorriente(PnlActivoCorrienteNoCorriente pnlActivoCteNoCte){
		this.pnlActivoCorrienteNoCorriente= pnlActivoCteNoCte;
	}
	
	
}


