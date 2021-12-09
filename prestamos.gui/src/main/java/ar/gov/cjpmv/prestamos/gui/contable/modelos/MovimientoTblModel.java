package ar.gov.cjpmv.prestamos.gui.contable.modelos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Movimiento;
import ar.gov.cjpmv.prestamos.core.persistence.contable.enums.TipoSaldo;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class MovimientoTblModel extends AbstractTableModel {

	private static final long serialVersionUID = -6722635780528810364L;
	private List<Movimiento> listaMovimiento = new ArrayList<Movimiento>();
	
	public void setListaMovimiento(List<Movimiento> pLista){
		this.listaMovimiento = pLista;
		fireTableDataChanged();
	}
	

	
	@Override
	public boolean isCellEditable(int row, int column) {
		Movimiento locMovimiento = this.listaMovimiento.get(row);
		/*
		if(column == 2 && locMovimiento.getCuenta().getTipoSaldo().equals(TipoSaldo.DEUDOR)){
			return true;
		}
		else if(column == 3 && locMovimiento.getCuenta().getTipoSaldo().equals(TipoSaldo.ACREEDOR)) {
			return true;
		}
		*/
		
		if(column == 2 || column == 3){
			return true;
		}
		return false;
	}
	
	@Override
	public int getRowCount() {
		return listaMovimiento.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int column) {
		switch(column) {
			case 0: return "Código";
			case 1: return "Nombre";
			case 2: return "Debe";
			case 3: return "Haber";
		}
		return null;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Movimiento locMovimiento = this.listaMovimiento.get(row);
		switch (column) {
			case 0: return locMovimiento.getCuenta().getCodigoCompleto();
			case 1: return locMovimiento.getCuenta().getNombre();
			case 2: return (locMovimiento.getMontoDebe()!=null?locMovimiento.getMontoDebe().toString():"");
			case 3: return (locMovimiento.getMontoHaber()!=null?locMovimiento.getMontoHaber().toString():"");
		}
		return null;
	}
	
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Movimiento locMovimiento= this.getMovimiento(rowIndex);
		switch(columnIndex){
			case 2: {
				if (aValue != null  ){
					BigDecimal valor = new BigDecimal(aValue.toString())
								.setScale(2,RoundingMode.HALF_UP);
					if(valor.floatValue() >= 0){
						locMovimiento.setMontoDebe(valor);
					}
				}
				break;
			}
			case 3:{
				if (aValue != null){
					BigDecimal valor = new BigDecimal(aValue.toString())
								.setScale(2,RoundingMode.HALF_UP);
					if (valor.floatValue() >= 0) {
						locMovimiento.setMontoHaber(valor); 
					}
				}
				break;
			}
		}
		super.setValueAt(aValue, rowIndex, columnIndex);

	}
	
	
	
	
	
	public Movimiento getMovimiento(int pSeleccionado){
		return this.listaMovimiento.get(pSeleccionado);
	}

	public void agregar(Movimiento movimiento) throws LogicaException{
		for (Movimiento cadaMovimiento : this.listaMovimiento) {
			if (cadaMovimiento.getCuenta().equals(movimiento.getCuenta())) {
				throw new LogicaException(123, movimiento.getCuenta().toString());
			}
		}
		listaMovimiento.add(movimiento);
		fireTableRowsInserted(listaMovimiento.size(), listaMovimiento.size());
	}

	public void eliminarFila(int fila) {
		this.listaMovimiento.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}
	
	public List<Movimiento> getListaMovimiento() {
		return listaMovimiento;
	}


}
