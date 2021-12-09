package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;

public class TblCuotasModel extends AbstractTableModel implements ITblCuotaModel{
	
	protected DateFormat formateadorFecha;
	private NumberFormat formateadorNumeros;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5347450517987549742L;

	private List<Cuota> listaCuotas;
	
	public TblCuotasModel(List<Cuota> pListaCuotas){
		this.listaCuotas = pListaCuotas;
		this.formateadorFecha = DateFormat.getDateInstance(DateFormat.SHORT);
		this.formateadorNumeros = NumberFormat.getNumberInstance();
		this.formateadorNumeros.setMaximumFractionDigits(2);
		this.formateadorNumeros.setMinimumFractionDigits(2);
		this.formateadorNumeros.setMinimumIntegerDigits(1);	
	}
	
	public TblCuotasModel(){
		this.listaCuotas = new ArrayList<Cuota>();
		this.formateadorFecha = DateFormat.getDateInstance(DateFormat.SHORT);
		this.formateadorNumeros = NumberFormat.getNumberInstance();
		this.formateadorNumeros.setMaximumFractionDigits(2);
		this.formateadorNumeros.setMinimumFractionDigits(2);
		this.formateadorNumeros.setMinimumIntegerDigits(1);
		
	}
	

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return this.listaCuotas.size();
	}


	@Override
	public String getColumnName(int column) {
		switch(column){
			case 0: return "Nº de cuota";
			case 1: return "Vencimiento";
			case 2: return "Otros conceptos";
			case 3: return "Interés";
			case 4: return "Capital";
			case 5: return "Valor Total";
		}
		return super.getColumnName(column);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cuota locCuota = this.getCuota(rowIndex);
		switch(columnIndex){
			case 0:	return locCuota.getNumeroCuota();
			case 1: return this.formateadorFecha.format(locCuota.getVencimiento());
			case 2: return this.formateadorNumeros.format(locCuota.getOtrosConceptos());
			case 3:	return this.formateadorNumeros.format(locCuota.getInteres()); 
			case 4: return this.formateadorNumeros.format(locCuota.getCapital());
			case 5: return this.formateadorNumeros.format(locCuota.getTotal());
		}
		return null;
	}

	
	
	public List<Cuota> getListaCuotas() {
		return listaCuotas;
	}

	public void setListaCuotas(List<Cuota> listaCuotas) {
		this.listaCuotas = listaCuotas;
		this.fireTableDataChanged();
	}

	public Cuota getCuota(int fila){
		return this.listaCuotas.get(fila);
	}
	
}
