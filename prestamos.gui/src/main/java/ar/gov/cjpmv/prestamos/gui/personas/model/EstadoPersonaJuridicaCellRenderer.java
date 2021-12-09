package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;

public class EstadoPersonaJuridicaCellRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4794867466416006928L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (value!=null){
			if (value instanceof EstadoPersonaJuridica){
				EstadoPersonaJuridica locEstado = (EstadoPersonaJuridica)value;
				switch(locEstado){
					case ACTIVO: value = "Activo"; break;
					case INACTIVO:value = "Inactivo"; break;
					default: value = null;
				}
			}
		}
		else{
			value = " ";
		}
		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}

}
