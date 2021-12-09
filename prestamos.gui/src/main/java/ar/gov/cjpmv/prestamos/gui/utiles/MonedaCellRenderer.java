package ar.gov.cjpmv.prestamos.gui.utiles;

import java.awt.Component;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MonedaCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7067627315785561954L;
	private NumberFormat formateador = NumberFormat.getCurrencyInstance();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		if (table.getModel().getColumnClass(column).equals(BigDecimal.class)) {
			value = formateador.format((BigDecimal)value);
		}
		
		Component elemento = super.getTableCellRendererComponent(table, 
				value, 
				isSelected, 
				hasFocus, 
				row, 
				column);
		
		return elemento;
	}

}
