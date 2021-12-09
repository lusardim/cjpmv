package ar.gov.cjpmv.prestamos.gui.personas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import ar.gov.cjpmv.prestamos.gui.Principal;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDatosContactoView;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlAMPersonaFisicaView extends PnlDchaABMView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4027917621330062000L;
	private PnlDatosPersonalesView pnlDatosPersonalesView;
	private PnlDatosContactoView pnlDatosContactoView;
	private PnlDatosFamiliaresView pnlDatosFamiliares;
	private PnlDatosLaboralesView pnlDatosLaborales;
	private PnlDatosPrevisionalesView pnlDatosPrevisionalesView;
	
	
	public PnlAMPersonaFisicaView() {
		super();
		this.initComponents();
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		ResourceMap locRecursos = Application.getInstance(Principal.class).getContext().getResourceMap(PnlAMPersonaFisicaView.class);
		
		this.pnlDatosPersonalesView = new PnlDatosPersonalesView();
		this.pnlDatosContactoView = new PnlDatosContactoView();
		this.pnlDatosFamiliares = new PnlDatosFamiliaresView();
		this.pnlDatosLaborales = new PnlDatosLaboralesView();
		this.pnlDatosPrevisionalesView = new PnlDatosPrevisionalesView();
		
		this.getTbpABM().add(locRecursos.getString("pnlDatosPersonalesView.titulo"),pnlDatosPersonalesView);
		this.getTbpABM().add(locRecursos.getString("pnlDatosContactoView.titulo"),pnlDatosContactoView);
//FIXME DATOS FAMILIARES COMENTADO
//		this.getTbpABM().add(locRecursos.getString("pnlDatosFamiliaresView.titulo"),this.pnlDatosFamiliares);

		this.getTbpABM().add(locRecursos.getString("pnlDatosLaboralesView.titulo"),this.pnlDatosLaborales);
		this.getTbpABM().add(locRecursos.getString("pnlDatosPrevisionalesView.titulo"),this.pnlDatosPrevisionalesView);
		
		
	}
	
	
	
	public static void main(String[] args){
		JDialog dialogo = new JDialog();
		PnlAMPersonaFisicaView locPnlPersona = new PnlAMPersonaFisicaView();
		dialogo.add(locPnlPersona);
		dialogo.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		dialogo.pack();
		dialogo.setVisible(true);
	}

	public PnlDatosPersonalesView getPnlDatosPersonalesView() {
		return pnlDatosPersonalesView;
	}

	public void setPnlDatosPersonalesView(
			PnlDatosPersonalesView pnlDatosPersonalesView) {
		this.pnlDatosPersonalesView = pnlDatosPersonalesView;
	}

	public PnlDatosContactoView getPnlDatosContactoView() {
		return pnlDatosContactoView;
	}

	public void setPnlDatosContactoView(PnlDatosContactoView pnlDatosContactoView) {
		this.pnlDatosContactoView = pnlDatosContactoView;
	}

	public PnlDatosFamiliaresView getPnlDatosDatosFamiliares() {
		return pnlDatosFamiliares;
	}

	public void setPnlDatosDatosFamiliares(
			PnlDatosFamiliaresView pnlDatosDatosFamiliares) {
		this.pnlDatosFamiliares = pnlDatosDatosFamiliares;
	}

	public PnlDatosPrevisionalesView getPnlDatosPrevisionalesView() {
		return pnlDatosPrevisionalesView;
	}

	public void setPnlDatosPrevisionalesView(
			PnlDatosPrevisionalesView pnlDatosPrevisionales) {
		this.pnlDatosPrevisionalesView = pnlDatosPrevisionales;
	}

	public PnlDatosLaboralesView getPnlDatosLaborales() {
		return pnlDatosLaborales;
	}

	public void setPnlEmpleosView(PnlDatosLaboralesView pnlDatosLaborales) {
		this.pnlDatosLaborales = pnlDatosLaborales;
	}
}
