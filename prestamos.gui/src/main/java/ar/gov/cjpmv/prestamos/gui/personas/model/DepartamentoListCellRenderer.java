package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;

public class DepartamentoListCellRenderer  extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4897522902596851680L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Localidad locLocalidad = (Localidad)value; 
		String nuevoValor = locLocalidad.getNombre() +" ("+locLocalidad.getDepartamento()+", "+locLocalidad.getDepartamento().getProvincia()+")";
		return super.getListCellRendererComponent(list, nuevoValor, index, isSelected,
				cellHasFocus);
	}
}
