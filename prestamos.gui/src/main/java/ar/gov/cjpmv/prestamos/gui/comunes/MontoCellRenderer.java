package ar.gov.cjpmv.prestamos.gui.comunes;

import java.awt.Component;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MontoCellRenderer extends DefaultTableCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4692253937969356073L;
	
	private NumberFormat formateador = NumberFormat.getCurrencyInstance();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		BigDecimal valor = (BigDecimal)value;
		if (valor == null){
			valor = new BigDecimal("0.00");
		}
		value = formateador.format(valor);
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
	}
}
