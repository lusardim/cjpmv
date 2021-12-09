package ar.gov.cjpmv.prestamos.gui.contable.modelos;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.contable.AsientoModelo;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCivil;

public class CbxAsientoModeloRenderer extends DefaultListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		Component component = super.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);
		
		if (value instanceof AsientoModelo) {
			AsientoModelo asiento = (AsientoModelo)value;
			String mostrar = asiento.getDescripcion();
			setText(mostrar);
		}
		else{
			setText(" ");
		}
	
		return component;
	}
	
	
	
	
	
	
}
