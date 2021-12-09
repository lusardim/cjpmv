package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class TblConceptosParticularesModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7690500985988331527L;

	private List<DetalleCredito> listaDetallesCreditos;
	private NumberFormat formateadorNumeros;
	private Credito credito;
	
	//Algunos conceptos se bloquean explícitamente, o sea no son editables
	private Map<DetalleCredito, Boolean> conceptosBloqueados;
	
	
	public TblConceptosParticularesModel(Credito credito) {
		super();
		this.listaDetallesCreditos=new ArrayList<DetalleCredito>();
		this.conceptosBloqueados=new HashMap<DetalleCredito,Boolean>();
		formateadorNumeros = NumberFormat.getNumberInstance();
		formateadorNumeros.setMinimumFractionDigits(0);
		formateadorNumeros.setMaximumFractionDigits(2);
		this.credito = credito;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column){
			case 0: return "Descripción";
			case 1: return "Monto";
			case 2: return "Emite cheque";
		}
		return super.getColumnName(column);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex){
			case 0: return String.class;
			case 1: return Float.class;
			case 2: return Boolean.class;
		}
		return super.getColumnClass(columnIndex);
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return this.listaDetallesCreditos.size()+1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex<this.listaDetallesCreditos.size()){
			DetalleCredito locDetalle = this.listaDetallesCreditos.get(rowIndex);
			switch(columnIndex){
				case 0: return locDetalle.getNombre();
				case 1:	return locDetalle.getValor();
				case 2: return locDetalle.getEmiteCheque();
			}
		}
		return null;
	}
	
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		DetalleCredito locDetalleCredito = this.getDetalleEditado(rowIndex);
		switch(columnIndex){
			case 0: locDetalleCredito.setNombre(Utiles.nuloSiVacio((String)aValue));break;
			case 1: {
				if (aValue != null){
					BigDecimal valor = new BigDecimal(aValue.toString())
								.setScale(2,RoundingMode.HALF_UP);
					locDetalleCredito.setValor(valor); 
				}
				break;
			}
			case 2: locDetalleCredito.setEmiteCheque((Boolean)aValue); break;
		}
		
		if (!this.listaDetallesCreditos.contains(locDetalleCredito)){
			if (!(Utiles.isCadenaVacia(locDetalleCredito.getNombre()) &&(locDetalleCredito.getValor().floatValue()==0))){
				this.listaDetallesCreditos.add(locDetalleCredito);
				this.fireTableRowsInserted(rowIndex, rowIndex);
			}
		}
		else{
			this.fireTableCellUpdated(rowIndex, rowIndex);
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (rowIndex<listaDetallesCreditos.size()) {
			Boolean bloqueado = conceptosBloqueados.get(
										listaDetallesCreditos
										.get(rowIndex)
									);
			if (bloqueado!=null) {
				return !bloqueado;
			}
		}
		return true;
	}
	
	public void removeRow(int row){
		this.listaDetallesCreditos.remove(row);
		this.fireTableRowsDeleted(row, row);
	}
	
	/**
	 * Obtiene 
	 * @param row
	 * @return
	 */
	public DetalleCredito getDetalleEditado(int row){
		if (this.listaDetallesCreditos.size()>row){
			return this.listaDetallesCreditos.get(row);
		}
		else{
			DetalleCredito locDetalleCredito = new DetalleCredito();
			locDetalleCredito.setCredito(this.credito);
			return locDetalleCredito;
		}
	}

	public List<DetalleCredito> getListaDetallesCreditos() {
		return listaDetallesCreditos;
	}

	public void limpiar() {
		this.listaDetallesCreditos.clear();
		this.conceptosBloqueados.clear();
	}

	public void add(DetalleCredito pDetalle, boolean bloqueado) {
		pDetalle.setCredito(credito);
		this.listaDetallesCreditos.add(pDetalle);
		this.conceptosBloqueados.put(pDetalle, bloqueado);
		int indice = listaDetallesCreditos.indexOf(pDetalle);
		fireTableRowsInserted(indice, indice);
	}

	public void eliminar(DetalleCredito detalleCredito) {
		int indice = this.listaDetallesCreditos.indexOf(detalleCredito);
		listaDetallesCreditos.remove(indice);
		this.conceptosBloqueados.remove(detalleCredito);
		fireTableRowsDeleted(indice, indice);
	}
	
}
