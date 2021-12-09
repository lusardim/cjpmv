package ar.gov.cjpmv.prestamos.gui.personas.model;

import javax.swing.DefaultListCellRenderer;

import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoCuenta;

public class CbxTipoCuentaRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8826546735428929606L;

	@Override
	public String getText() {
		String texto = super.getText();
		if (!texto.isEmpty()){
			switch (TipoCuenta.valueOf(texto)){ 
				case CUENTA_CORRIENTE: return "Cuenta Corriente"; 
				case CAJA_AHORRO: return "Caja de Ahorro";
			}
		}
		return super.getText();
	}
}

