package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CuotasCellRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745297984208867424L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component elemento= super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		ITblCuotaModel modeloCuotas= (ITblCuotaModel) table.getModel();
		
		if (isSelected) {
//			Color fuente= new Color(102, 102, 102);
//			Color fuente= new Color(255, 0, 0);
//			elemento.setForeground(fuente);
		}
		
		int fila = getNumeroFila(table,row);
		switch(modeloCuotas.getCuota(fila).getSituacion()){
			case CANCELADA: {
				Color fondo= new Color(232, 248, 237);
				elemento.setBackground(fondo); 
				break;	
			}
			case VENCIDA: {
				Color fondo= new Color(255, 232, 225);
				elemento.setBackground(fondo); 
				break;	
			}
			case NO_VENCIDA: {
				Color fondo= new Color(235, 245, 255);
				elemento.setBackground(fondo); 
				break;	
			}
		}
		return elemento; 
	}

	private int getNumeroFila(JTable table, int row) {
		if (table.getRowSorter() != null) {
			return table.getRowSorter().convertRowIndexToModel(row);
		}
		return row;
	}

}
