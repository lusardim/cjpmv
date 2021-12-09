package ar.gov.cjpmv.prestamos.gui.creditos.model;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;

public class TblCreditosModel extends AbstractTableModel {
	/**
	 * 
	 */
	
	private NumberFormat formateadorMonetario= NumberFormat.getCurrencyInstance();
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private static final long serialVersionUID = 1L;
	private List<Credito> listaCreditos;
	
	public TblCreditosModel(List<Credito> pListaCreditos) {
		this.listaCreditos= pListaCreditos;
	}

	public TblCreditosModel() {
		listaCreditos = new ArrayList<Credito>();
	}
	
	@Override
	public int getColumnCount() {
		return 7;
	}
	
	@Override
	public String getColumnName(int column){
		String nombre=null;
		switch(column){
			case 0: nombre = "Número"; break;
			case 1: nombre = "Tipo"; break;
			case 2: nombre = "Fecha Inicio"; break;
			case 3: nombre = "Monto Total"; break;
			case 4: nombre = "Cantidad de Cuotas"; break;
			case 5: nombre = "Cuotas Adeudadas"; break;
			case 6: nombre = "Saldo Adeudado"; break;
		}
		return nombre;
	}
	
	@Override
	public int getRowCount() {
		if(this.listaCreditos==null){
			return 0;
		}
		return this.listaCreditos.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Credito locCredito= this.listaCreditos.get(row); 
		switch(column){
			case 0: return locCredito.getNumeroCredito();
			case 1: return locCredito.getTipoCredito().getNombre();
			case 2: return this.formateadorFecha.format(locCredito.getFechaInicio());
			case 3: return this.formateadorMonetario.format(locCredito.getMontoTotal());
			case 4: return locCredito.getCantidadCuotas();
			case 5: return locCredito.getCuotasAdeudadas();
			case 6: return this.formateadorMonetario.format(locCredito.getSaldoAdeudado());
		}
		return null;
	}
	
	public Credito getCredito(int pSeleccionado){
		return this.listaCreditos.get(pSeleccionado);
	}
	
	public void setListaCreditos(List<Credito> listaCreditos) {
		this.listaCreditos = listaCreditos;
		fireTableDataChanged();
	}
	
	protected List<Credito> getListaCreditos() {
		return listaCreditos;
	}

}
