package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import ar.gov.cjpmv.prestamos.gui.Principal;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlAMCreditoView;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlListadoCheques;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.PnlDatosCredito;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.PnlDatosGarantes;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.solicitante.PnlDatosSolicitante;

public class PnlCobro extends PnlDchaABMView{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7366198546962837080L;
	private PnlCobroPorBanco pnlCobroPorBanco;
	
	
	public PnlCobro() {
		super();
		this.initComponents();
		
	}
	
	
	@Override
	protected void initComponents() {
		super.initComponents();
		ResourceMap locRecursos = Application.getInstance(Principal.class).getContext().getResourceMap(PnlCobroPorBanco.class);
		this.pnlCobroPorBanco = new PnlCobroPorBanco();
		this.getTbpABM().add("Cobro por Depósito Bancario", this.pnlCobroPorBanco);	
	}


	public PnlCobroPorBanco getPnlCobroPorBanco() {
		return pnlCobroPorBanco;
	}


	public void setPnlCobroPorBanco(PnlCobroPorBanco pnlCobroPorBanco) {
		this.pnlCobroPorBanco = pnlCobroPorBanco;
	}
	
}
