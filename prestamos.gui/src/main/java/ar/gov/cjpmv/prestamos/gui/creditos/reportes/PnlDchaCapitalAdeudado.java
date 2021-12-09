package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.text.NumberFormat;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlDchaCapitalAdeudado extends PnlDchaABMView  {

	private PnlCapitalAdeudado pnlCapital;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	
	public void PnlDchaCapitalAdeudado(){
		this.initComponents();
	}

	public void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Reporte: Saldo de Capital por Tipo de Crédito");
		this.pnlCapital = new PnlCapitalAdeudado();
		this.getTbpABM().add("Informe sobre capital adeudado por tipo de crédito",this.pnlCapital);	
	
		
	}
	
	
	public PnlCapitalAdeudado getPnlCapitalAdeudado(){
		return pnlCapital;
	}
	
	public void setPnlCapitalAdeudado(PnlCapitalAdeudado pnlCapitalAdeudado){
		this.pnlCapital= pnlCapitalAdeudado;
	}
	
}