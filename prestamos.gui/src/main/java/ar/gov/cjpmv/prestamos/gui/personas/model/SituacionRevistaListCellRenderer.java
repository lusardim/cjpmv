package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionRevista;

public class SituacionRevistaListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3790278048553794013L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		
		if (value!=null){
			if (value instanceof SituacionRevista){
				SituacionRevista cadaSituacionRevista = (SituacionRevista)value;
				switch(cadaSituacionRevista){
					case CONTRATO_OBRA: value ="Contrato de obra"; break;
					case CONTRATO_SERVICIO: value = "Contrato de servicio"; break;
					case OTRO: value = "Otro"; break;
					case PLANTA_PERMANENTE: value = "Planta permanente"; break;
					case JORNALIZADO: value = "Temporario"; break;
				}
			}
		}
		else{
			value = " ";
		}
			
		
		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
	
}
