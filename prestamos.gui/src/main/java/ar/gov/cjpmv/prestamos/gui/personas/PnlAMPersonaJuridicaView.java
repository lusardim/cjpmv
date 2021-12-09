package ar.gov.cjpmv.prestamos.gui.personas;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import ar.gov.cjpmv.prestamos.gui.Principal;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDatosContactoView;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlAMPersonaJuridicaView extends PnlDchaABMView{

	private PnlDatosInstitucionalesResponsables pnlDatosInstitucionales;
	private PnlDatosContactoView pnlDatosContacto;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1468038831438552368L;

	
	public PnlAMPersonaJuridicaView() {
		super();
		this.initComponents();
	}
	
	 @Override
	protected void initComponents() {
		super.initComponents();
		
		ResourceMap locResourceMap = Application.getInstance(Principal.class).getContext().getResourceMap(PnlAMPersonaJuridicaView.class);
		this.pnlDatosContacto = new PnlDatosContactoView();
		this.pnlDatosInstitucionales = new PnlDatosInstitucionalesResponsables();
		
		this.getTbpABM().add(locResourceMap.getString("pnlDatosInstitucionales.titulo"),pnlDatosInstitucionales);
		this.getTbpABM().add(locResourceMap.getString("pnlDatosContacto.titulo"),pnlDatosContacto);

	}

	public PnlDatosInstitucionalesResponsables getPnlDatosInstitucionales() {
		return pnlDatosInstitucionales;
	}

	public void setPnlDatosInstitucionales(
			PnlDatosInstitucionalesResponsables pnlDatosInstitucionales) {
		this.pnlDatosInstitucionales = pnlDatosInstitucionales;
	}

	public PnlDatosContactoView getPnlDatosContacto() {
		return pnlDatosContacto;
	}

	public void setPnlDatosContacto(PnlDatosContactoView pnlDatosContacto) {
		this.pnlDatosContacto = pnlDatosContacto;
	}
	 
	 
	 
}
