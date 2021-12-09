package ar.gov.cjpmv.prestamos.gui.contable;

import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;

public class PlanCuentaListCellRenderer extends DefaultListCellRenderer {
	
	private static final long serialVersionUID = 5703559172661826019L;
	private static DateFormat formateadorFecha = new SimpleDateFormat("MMMMM 'de' yyyy");
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (value instanceof PlanCuenta) {
			PlanCuenta planCuenta = (PlanCuenta)value;
			EjercicioContable periodo = planCuenta.getPeriodo();
			;
			value = "Plan de cuenta " + 
					formateadorFecha.format(periodo.getFechaInicio()) + 
					" a " +
					formateadorFecha.format(periodo.getFechaFin());
		}
		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
}