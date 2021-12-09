package ar.gov.cjpmv.prestamos.gui.creditos;

import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.PnlCreditosFinalizacion;

public class PnlAMCreditoFinalizacionView extends PnlAMCreditoView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3694140402562505628L;
	private PnlCreditosFinalizacion pnlCreditosFinalizacion;
	
	public PnlAMCreditoFinalizacionView() {
		super();
		pnlCreditosFinalizacion = new PnlCreditosFinalizacion();
		this.getTbpABM().add(pnlCreditosFinalizacion,1);
		this.getTbpABM().setTitleAt(1, "Créditos a finalizar");
	}
	
	public PnlCreditosFinalizacion getPnlCreditosFinalizacion() {
		return pnlCreditosFinalizacion;
	}
}
