package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;
import ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.PnlCobroPorBanco;

public class PnlDchaCobroBancario extends PnlDchaABMView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3569267640590456876L;

	private PnlCobrosBancarios pnlCobroBancario;
	
	public PnlDchaCobroBancario(){
		this.initComponents();
	}
	

	public PnlCobrosBancarios getCobrosBancarios() {
		return pnlCobroBancario;
	}

	public void setCobrosBancarios(PnlCobrosBancarios pnlCobroBancario) {
		this.pnlCobroBancario = pnlCobroBancario;
	}
	
	
	@Override
	protected void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Reporte: Cobros por Banco");
		this.pnlCobroBancario = new PnlCobrosBancarios();
		this.getTbpABM().add("Informe sobre cobros por banco",this.pnlCobroBancario);	
	}
	
	
}
