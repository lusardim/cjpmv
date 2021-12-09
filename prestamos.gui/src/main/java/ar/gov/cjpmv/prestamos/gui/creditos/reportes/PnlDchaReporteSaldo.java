package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlDchaReporteSaldo extends PnlDchaABMView {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2177536991209537585L;
	private PnlReporteSaldoCredito pnlReporteSaldoCredito;
	
	public PnlDchaReporteSaldo() {
		this.initComponents();
	}

	@Override
	protected void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Reporte: Saldo por Tipo de Crédito");
		this.pnlReporteSaldoCredito = new PnlReporteSaldoCredito();
		this.getTbpABM().add("Informe sobre saldos",this.pnlReporteSaldoCredito);	
	}

	public PnlReporteSaldoCredito getPnlReporteSaldoCredito() {
		return pnlReporteSaldoCredito;
	}

	public void setPnlReporteSaldoCredito(
			PnlReporteSaldoCredito pnlReporteSaldoCredito) {
		this.pnlReporteSaldoCredito = pnlReporteSaldoCredito;
	}
}
