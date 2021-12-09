package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;

public class ProvinciaListCellRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4897522902596851680L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		Departamento locDepartamento = (Departamento)value; 
		String nuevoValor = locDepartamento.getNombre() +" ("+locDepartamento.getProvincia()+")";
		
		return super.getListCellRendererComponent(list, nuevoValor, index, isSelected,
				cellHasFocus);
	}
}
