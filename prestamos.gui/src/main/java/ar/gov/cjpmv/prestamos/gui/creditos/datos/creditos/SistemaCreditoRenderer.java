package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos;

import javax.swing.DefaultListCellRenderer;

import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema.TipoSistema;

public class SistemaCreditoRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6380890792844350438L;


	@Override
	public String getText() {
		String texto = super.getText();
		if (!texto.isEmpty()){
			switch(TipoSistema.valueOf(texto)){
				case DIRECTO: return "Directo";
				case FRANCES: return "Francés";
			}
		}
		return texto;
	}
}
