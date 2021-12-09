package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porHaberesCaja;

import javax.swing.JTable;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlDerechaCobroPorHaberesCaja extends PnlDchaABMView {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5957331001877165431L;
	private PnlCobroPorHaberesCaja pnlCobroPorHaberesCaja;

	public PnlCobroPorHaberesCaja getPnlCobroPorHaberesCaja() {
		return pnlCobroPorHaberesCaja;
	}

	public void setPnlCobroPorHaberesCaja(
			PnlCobroPorHaberesCaja pnlCobroPorHaberesCaja) {
		this.pnlCobroPorHaberesCaja = pnlCobroPorHaberesCaja;
	}

	PnlDerechaCobroPorHaberesCaja() {
		super();
		this.initComponents();
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Cobro: Por Liquidaciòn de Haberes");
		this.pnlCobroPorHaberesCaja = new PnlCobroPorHaberesCaja();
		this.getTbpABM().add("Cobro por Liquidaón de Haberes ",this.pnlCobroPorHaberesCaja);	
	}

	public JTable getTblCobroCaja() {
		return pnlCobroPorHaberesCaja.getTblCobroCaja();
	}
	
	
	
}
