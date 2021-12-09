package ar.gov.cjpmv.prestamos.gui.creditos.cheques;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;

public class EstadoChequeCellRenderer extends DefaultTableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1453499568825205512L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		EstadoCheque estado = (EstadoCheque)value;
		switch(estado){
			case CANCELADO: value = "Cancelado"; break;
			case IMPRESO: value = "Impreso"; break;
			case PENDIENTE_IMPRESION: value = "Pendiente de impresión"; break;
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
	}
}
