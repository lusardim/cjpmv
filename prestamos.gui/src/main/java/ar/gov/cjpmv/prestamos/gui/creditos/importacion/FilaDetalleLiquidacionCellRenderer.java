package ar.gov.cjpmv.prestamos.gui.creditos.importacion;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;



public class FilaDetalleLiquidacionCellRenderer  extends DefaultTableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6954711708130953227L;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	
	public FilaDetalleLiquidacionCellRenderer() {
		super();
		setHorizontalAlignment(SwingConstants.LEFT);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		RowSorter<?> sorter = table.getRowSorter();
		
		
		int filaReal = row;
		
		if (sorter!=null) {
			filaReal = sorter.convertRowIndexToModel(row);
		}
				
		Class<?> clase = table.getModel().getColumnClass(column);
		if (clase.equals(BigDecimal.class)) {
			value = formateadorMonetario.format((BigDecimal)value);
		}
	
	

		Component elemento= super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
	
		
		
		TblArchivoDetalleLiquidacion modelo= (TblArchivoDetalleLiquidacion) table.getModel();
		
		if (isSelected){
			Color fuente= new Color(0, 0, 0);
			elemento.setForeground(fuente);
		}
		
		if((modelo.getArchivoDetalle(filaReal).getMontoRecibido().floatValue()==0F)
				&&(modelo.getArchivoDetalle(filaReal).getDetalleLiquidacion().getMonto().floatValue() != 0)){
			Color fondo= new Color(241, 186, 163);
			elemento.setBackground(fondo); 
		}
		else{
			if((modelo.getArchivoDetalle(filaReal).getMontoRecibido().floatValue()!=0F)
					&&(modelo.getArchivoDetalle(filaReal).getDetalleLiquidacion().getMonto().floatValue()==0)){
				Color fondo= new Color(173, 205, 231);
				elemento.setBackground(fondo); 
			}
			else{
				Color fondo= new Color(255, 255, 255);
				elemento.setBackground(fondo); 
				
			}
		}
		return elemento; 
	}
}
