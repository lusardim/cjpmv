package ar.gov.cjpmv.prestamos.gui.contable.modelos;

import java.text.DateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Asiento;


public class AsientoContableTblModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3839592357012043715L;
	private List<Asiento> listaAsiento;
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	
	public void setListaAsiento(List<Asiento> pListaAsiento){
		this.listaAsiento= pListaAsiento;
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column) {
			case 0: return "Fecha";
			case 1: return "N° de Asiento";
			case 2: return "Descripción";
		}
		return null;
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}
	
	@Override
	public int getRowCount() {
		return (listaAsiento.isEmpty()?0:listaAsiento.size());
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Asiento asiento = this.listaAsiento.get(row);
		switch (column) {
			case 0: return formateadorFecha.format(asiento.getFecha());
			case 1: return asiento.getNumero();
			case 2: return asiento.getDescripcion();
		}
		return null;
	}
	
	public Asiento getAsiento(int pSeleccionado){
		return this.listaAsiento.get(pSeleccionado);
	}
	

}
