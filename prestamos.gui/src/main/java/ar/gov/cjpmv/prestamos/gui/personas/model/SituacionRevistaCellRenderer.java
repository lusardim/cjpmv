package ar.gov.cjpmv.prestamos.gui.personas.model;

import javax.swing.table.DefaultTableCellRenderer;

import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionRevista;

public class SituacionRevistaCellRenderer extends DefaultTableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3024974533373917391L;


	@Override
	protected void setValue(Object value) {
		if (value instanceof SituacionRevista){
			SituacionRevista locSituacionRevista = (SituacionRevista)value;
			switch(locSituacionRevista){
				case CONTRATO_OBRA: super.setValue("Contrato de Obra"); break;
				case CONTRATO_SERVICIO: super.setValue("Contrado de Servicio"); break;
				case OTRO: super.setValue("Otro"); break;
				case PLANTA_PERMANENTE: super.setValue("Planta permanente"); break;
				case JORNALIZADO: super.setValue("Temporario"); break;
			}
		}
		else{
			super.setValue(value);
		}
	}
}
