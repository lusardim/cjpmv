package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;

public class EstadoPersonaFisicaCellRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4949908907278493788L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		EstadoPersonaFisica locEstado = (EstadoPersonaFisica)value;
		if (locEstado!=null){
			switch(locEstado){
				case ACTIVO: value = "Activo"; break;
				case FALLECIDO: value = "Fallecido"; break;
				case PASIVO: value = "Pasivo"; break;
				default: value = locEstado.toString();
			}
		}
		return super.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);
	}
}
