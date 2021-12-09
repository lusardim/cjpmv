package ar.gov.cjpmv.prestamos.gui.comunes;

import javax.swing.DefaultListCellRenderer;

public class CamelCaseListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -3866942953853559933L;

	@Override
	public String getText() {
		String texto = super.getText();
		if (!texto.isEmpty()) {
			texto = texto.substring(0,1).toUpperCase() + 
					texto.substring(1,texto.length()).toLowerCase();
		}
		return texto;
	}
}
