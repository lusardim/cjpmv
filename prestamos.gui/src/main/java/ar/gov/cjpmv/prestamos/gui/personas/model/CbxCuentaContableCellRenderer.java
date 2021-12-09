package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;

public class CbxCuentaContableCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 3820738391645410705L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component component = super.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);
		if (value == null) {
			setText("ninguna");
		}
		if (value instanceof Cuenta) {
			Cuenta cuenta = (Cuenta)value;
			String mostrar = cuenta.getCodigo() + " - " + cuenta.getNombre();
			setText(mostrar);
		}
		return component;
	}
}
