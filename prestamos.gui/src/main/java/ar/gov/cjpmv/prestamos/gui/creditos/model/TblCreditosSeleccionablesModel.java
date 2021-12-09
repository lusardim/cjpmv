package ar.gov.cjpmv.prestamos.gui.creditos.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;

public class TblCreditosSeleccionablesModel extends TblCreditosModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 126827482885796777L;
	private boolean[] seleccionados;
	private BigDecimal total = new BigDecimal(0);
	private static final int COLUMNA_TOTAL = 6;
	
	public TblCreditosSeleccionablesModel() {
		super();
	}

	@Override
	public String getColumnName(int column) {
		if (column==0){
			return "Cancelar";
		}
		else{
			return super.getColumnName(column-1);
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex==0){
			return Boolean.class;
		}
		else{
			return super.getColumnClass(columnIndex+1);
		}
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		if (column==0){
			return seleccionados[row];
		}
		else{
			return super.getValueAt(row, column-1);
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex==0){
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex==0){
			Boolean valor = (Boolean)aValue;
			if (!valor.equals(seleccionados[rowIndex])){
				if (valor){
					total = total.add(getListaCreditos().get(rowIndex).getSaldoAdeudado());
				}
				else{
					total = total.subtract(getListaCreditos().get(rowIndex).getSaldoAdeudado());
				}
			}
			seleccionados[rowIndex] = valor;
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
	}
	
	@Override
	public int getColumnCount() {
		return super.getColumnCount()+1;
	}
	
	@Override
	public void setListaCreditos(List<Credito> listaCreditos) {
		this.getListaCreditos().clear();
		this.getListaCreditos().addAll(listaCreditos);
		seleccionados = new boolean[listaCreditos.size()];
		Arrays.fill(seleccionados, false);
		fireTableDataChanged();
	}
	
	public BigDecimal getTotal() {
		return total;
	}

	public void limpiar() {
		this.getListaCreditos().clear();
		fireTableDataChanged();		
	}

	public List<Credito> getListaSeleccionados() {
		List<Credito> listaSeleccionados = new ArrayList<Credito>(this.getRowCount());
		for (int i =0 ; i<seleccionados.length;i++){
			if (seleccionados[i]){
				listaSeleccionados.add(getListaCreditos().get(i));
			}
		}
		return listaSeleccionados;
	}
	
}
