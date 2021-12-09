package ar.gov.cjpmv.prestamos.gui.personas;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlABMTelefono;
import ar.gov.cjpmv.prestamos.gui.configuracion.ABMConfiguracionSetearPnl;

public class AMTelefonoView extends ABMConfiguracionSetearPnl{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5070161271228272212L;
	
	private PnlABMTelefono pnlDatosTelenfono;
	
	public AMTelefonoView(Frame parent, boolean modal) {
		super(parent, modal);
		this.initComponents();
	}
	
	private void initComponents() {
		pnlDatosTelenfono = new PnlABMTelefono();
		this.setPanel(pnlDatosTelenfono);
	}

	public AMTelefonoView(JDialog parent, boolean modal) {
		super(parent, modal);
		this.initComponents();
	}

	public JComboBox getCbxTipoTelefono() {
		return pnlDatosTelenfono.getCbxTipoTelefono();
	}

	public JTextField getTxtCaracteristica() {
		return pnlDatosTelenfono.getTxtCaracteristica();
	}

	public JTextField getTxtNumero() {
		return pnlDatosTelenfono.getTxtNumero();
	}

	
	public Component[] getComponentesAceptanKeyListeners() {
		return this.pnlDatosTelenfono.getComponents();
	}
	
}
