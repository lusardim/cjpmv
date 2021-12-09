package ar.gov.cjpmv.prestamos.gui.creditos.garantias;

import javax.swing.JCheckBox;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;
import ar.gov.cjpmv.prestamos.gui.creditos.exportacion.PnlCuotasExportacionMunicipalidad;
import ar.gov.cjpmv.prestamos.gui.creditos.exportacion.PnlSeleccionCuotasReliquidacion;






public class PnlDerechaAfectarGarantes extends PnlDchaABMView {

	private PnlAfectarDesafectarGarantes pnlAfectarDesafectarGarantes;
	
	
	public void PnlDerechaAfectarGarantes() {
		this.initComponents();
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Administración de Garantías");
		this.pnlAfectarDesafectarGarantes = new PnlAfectarDesafectarGarantes();
		this.getTbpABM().add("Afectar/Desafectar Garantes",this.pnlAfectarDesafectarGarantes);
	}
	
	
	public PnlAfectarDesafectarGarantes getPnlAfectarDesafectarGarantes() {
		return pnlAfectarDesafectarGarantes;
	}


	public void setPnlAfectarDesafectarGarantes(
			PnlAfectarDesafectarGarantes pnlAfectarDesafectarGarantes) {
		this.pnlAfectarDesafectarGarantes = pnlAfectarDesafectarGarantes;
	}


}
