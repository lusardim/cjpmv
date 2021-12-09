package ar.gov.cjpmv.prestamos.gui.personas.model;

import javax.swing.DefaultListCellRenderer;

import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoTelefono;

public class CbxTipoTelefonoRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8826546735428929606L;

	@Override
	public String getText() {
		String texto = super.getText();
		if (!texto.isEmpty()){
			switch (TipoTelefono.valueOf(texto)){ 
				case FIJO: return "Fijo"; 
				case MOVIL: return "Móvil";
			}
		}
		return super.getText();
	}
}
