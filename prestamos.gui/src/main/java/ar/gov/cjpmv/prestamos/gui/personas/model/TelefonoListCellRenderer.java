package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.Telefono;

public class TelefonoListCellRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3691384316069999424L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		Telefono locTelefono = (Telefono)value;
		String valor ="";
		if ((locTelefono.getCaracteristica()!=null)&&(!locTelefono.getCaracteristica().trim().isEmpty())){
			valor = "("+locTelefono.getCaracteristica()+") "; 
		}
		valor+=locTelefono.getNumero();
		return super.getListCellRendererComponent(list, valor, index, isSelected,
				cellHasFocus);
	}
}
