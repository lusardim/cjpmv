package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.text.NumberFormat;

import javax.swing.ComboBoxModel;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlDchaCobranzas extends PnlDchaABMView {

	private PnlCobranzas pnlCobranzas;
	private ViaCobro viaCobro;
	private ComboBoxModel modeloViaCobro;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	
	public void PnlDchaPorPeriodo(){
		this.initComponents();
	}

	public void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Reporte: Cobranzas por periodo y via");
		this.pnlCobranzas = new PnlCobranzas();
		this.getTbpABM().add("Informe sobre cobros por periodo y via",this.pnlCobranzas);	
	
		
	}
	
	
	public PnlCobranzas getPnlCobranzas(){
		return pnlCobranzas;
	}
	
	public void setPnlCobranzas(PnlCobranzas pnlCobranzas){
		this.pnlCobranzas= pnlCobranzas;
	}
	
}
