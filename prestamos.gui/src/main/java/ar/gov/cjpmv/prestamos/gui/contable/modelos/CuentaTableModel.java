package ar.gov.cjpmv.prestamos.gui.contable.modelos;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;

public class CuentaTableModel extends DefaultTableModel {

	private static final long serialVersionUID = -72438901236973417L;
	private List<Cuenta> cuentas;

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column) {
			case 0: return "Código";
			case 1: return "Nombre";
		}
		return super.getColumnName(column);
	}
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	@Override
	public int getRowCount() {
		return (cuentas == null)?0:cuentas.size();
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Cuenta cuenta = this.cuentas.get(row);
		switch (column) {
			case 0: return cuenta.getCodigoCompleto();
			case 1: return cuenta.getNombre();
		}
		return super.getValueAt(row, column);
	}
}
