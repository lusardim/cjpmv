package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCivil;

public class EstadoCivilCellRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4748675030353262461L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		EstadoCivil locEstadoCivil = (EstadoCivil)value;
		if (locEstadoCivil!=null){
			switch(locEstadoCivil){
				case CASADO: value = "Casado"; break;
				case CONVIVIENTE: value = "Conviviente"; break;
				case DIVORCIADO: value = "Divorciado"; break;
				case SEPARADO_DE_HECHO: value = "Separado de hecho"; break;
				case SEPARADO_LEGAL: value = "Separado legal"; break;
				case SOLTERO: value = "Soltero"; break;
				default: value = locEstadoCivil.toString();
			}
		}
		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
}
