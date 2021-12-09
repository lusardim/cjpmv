package ar.gov.cjpmv.prestamos.gui.utiles;

import javax.swing.JTable;
import javax.swing.RowSorter;

public class JTableOrdenable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2359302699186999733L;
	
	public JTableOrdenable() {
		super();
		this.setAutoCreateRowSorter(true);
	}
	
	public int getSelectedRow() {
		int fila = super.getSelectedRow();
		RowSorter<?> sorter = this.getRowSorter();
		if (fila != -1 && sorter != null) {
			fila = sorter.convertRowIndexToModel(fila);
		}
		return fila;
	};

}
