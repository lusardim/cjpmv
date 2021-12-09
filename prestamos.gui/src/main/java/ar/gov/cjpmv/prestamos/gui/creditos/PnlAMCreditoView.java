package ar.gov.cjpmv.prestamos.gui.creditos;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import ar.gov.cjpmv.prestamos.gui.Principal;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.PnlDatosCredito;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.PnlDatosGarantes;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.solicitante.PnlDatosSolicitante;

public class PnlAMCreditoView extends PnlDchaABMView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6128918850055040412L;
	
	private PnlDatosSolicitante pnlDatosSolicitante;
	private PnlDatosCredito pnlDatosCredito;
	private PnlDatosGarantes pnlDatosGarantes;
	private PnlListadoCheques pnlListadoCheques;
	
	public PnlAMCreditoView() {
		super();
		this.initComponents();
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		ResourceMap locRecursos = Application.getInstance(Principal.class).getContext().getResourceMap(PnlAMCreditoView.class);
		
		this.pnlDatosCredito = new PnlDatosCredito();
		this.pnlDatosSolicitante = new PnlDatosSolicitante();
		this.pnlDatosGarantes = new PnlDatosGarantes();
		this.pnlListadoCheques = new PnlListadoCheques();
		
		this.getTbpABM().add(locRecursos.getString("PnlDatosSolicitante.title"), this.pnlDatosSolicitante);
		this.getTbpABM().add(locRecursos.getString("PnlDatosGarantes.title"), this.pnlDatosGarantes);
		this.getTbpABM().add(locRecursos.getString("PnlDatosCredito.title"),this.pnlDatosCredito);
		this.getTbpABM().add(locRecursos.getString("PnlListadoCheques.title"),this.pnlListadoCheques);
	}
	
	public static void main(String[] args){
		JDialog dialogo = new JDialog();
		PnlAMCreditoView locPnlAMCredito = new PnlAMCreditoView();
		dialogo.add(locPnlAMCredito);
		dialogo.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		dialogo.pack();
		dialogo.setVisible(true);
	}


	public PnlDatosSolicitante getPnlDatosSolicitante() {
		return pnlDatosSolicitante;
	}


	public void setPnlDatosSolicitante(PnlDatosSolicitante pnlDatosSolicitante) {
		this.pnlDatosSolicitante = pnlDatosSolicitante;
	}

	public PnlDatosCredito getPnlDatosCredito() {
		return pnlDatosCredito;
	}


	public void setPnlDatosCredito(PnlDatosCredito pnlDatosCredito) {
		this.pnlDatosCredito = pnlDatosCredito;
	}

	public PnlDatosGarantes getPnlDatosGarantes() {
		return pnlDatosGarantes;
	}

	public PnlListadoCheques getPnlListadoCheques() {
		return pnlListadoCheques;
	}
}
