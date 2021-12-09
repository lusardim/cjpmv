package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlDchaFormularioCredito extends PnlDchaABMView{
	private PnlFormulariosCredito pnlFormularioCredito;
	
	public void PnlDchaFormularioCredito(){
		this.initComponents();
	}
	
	public void initComponents(){
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Reportes: Formularios de Crédito");
		this.pnlFormularioCredito = new PnlFormulariosCredito();
		this.getTbpABM().add("Impresion de formularios de créditos otorgados", this.pnlFormularioCredito);
	}
	
	public PnlFormulariosCredito getPnlFormulariosCredito(){
		return this.pnlFormularioCredito;
	}
	
	public void setPnlFormulariosCredito(PnlFormulariosCredito pPnlFormulariosCredito){
		this.pnlFormularioCredito= pPnlFormulariosCredito;
	}
	
}
