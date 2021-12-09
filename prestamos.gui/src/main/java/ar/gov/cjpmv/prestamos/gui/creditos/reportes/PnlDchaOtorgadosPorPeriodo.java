package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlDchaOtorgadosPorPeriodo extends PnlDchaABMView {
	
	private PnlCreditosOtorgados pnlCreditosOtorgados;
	
	public void PnlCreditosOtorgados(){
		this.initComponents();
	}

	public void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Reporte: Créditos Otorgados por Período");
		this.pnlCreditosOtorgados = new PnlCreditosOtorgados();
		this.getTbpABM().add("Informe sobre créditos otorgados por período",this.pnlCreditosOtorgados);	
	
		
	}
	
	
	public PnlCreditosOtorgados getPnlCreditosOtorgados(){
		return pnlCreditosOtorgados;
	}
	
	public void setPnlCreditosOtorgados(PnlCreditosOtorgados pPnlCreditosOtorgados){
		this.pnlCreditosOtorgados= pPnlCreditosOtorgados;
	}
	
	
}