package ar.gov.cjpmv.prestamos.gui.creditos.viaCobro;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlDerechaViaCobro extends PnlDchaABMView{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3785496883028015601L;
	private PnlViaCobro pnlViaCobro;

	@Override
	protected void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Administración: Via de Cobro");
		this.pnlViaCobro = new PnlViaCobro();
		this.getTbpABM().add("Edición de Vía de Cobro",this.pnlViaCobro);
	}

	public PnlViaCobro getPnlViaCobro() {
		return pnlViaCobro;
	}


	public void setPnlViaCobro(
			PnlViaCobro pnlViaCobro) {
		this.pnlViaCobro = pnlViaCobro;
	}
	

}
