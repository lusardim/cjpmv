package ar.gov.cjpmv.prestamos.gui.creditos.exportacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;

public class TblLiquidacionAnteriorModel extends TblDetalleLiquidacionModel{
	protected static final int MONTO = 5;
	
	private boolean[] seleccionado;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6072231739661471676L;

	public Object getValueAt(int row, int column) {
		switch (column){
			case 0: 
				return seleccionado[row]; 
			case MONTO:
				DetalleLiquidacion detalle = this.getLista().get(row);
				return detalle.getMontoAdeudado();
		}
		return super.getValueAt(row, column-1);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex==0){
			return Boolean.class;
		}
		return super.getColumnClass(columnIndex-1);
	}
	
	public int getColumnCount() {
		return super.getColumnCount()+1;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 0){
			return true;
		}
		return super.isCellEditable(row, column);
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == 0){
			Boolean valor = (Boolean)aValue;
			seleccionado[row] = valor;
			fireTableCellUpdated(row, column);
		}
		else { 
			super.setValueAt(aValue, row, column);
		}
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0){
			return "Liquidar";
		}
		return super.getColumnName(column-1);
	}
	
	@Override
	public void setLista(List<DetalleLiquidacion> pLista) {
		this.seleccionado = new boolean[pLista.size()];
		Arrays.fill(this.seleccionado, false);
		super.setLista(pLista);
	}
	
	public void clear() {
		super.setLista(new ArrayList<DetalleLiquidacion>());
	}

	public boolean isSelected(int row){
		return seleccionado[row];
	}

	public void setSelected(int fila, boolean pSeleccionado) {
		this.seleccionado[fila] = pSeleccionado;
		fireTableCellUpdated(fila, 1);
	}
}
