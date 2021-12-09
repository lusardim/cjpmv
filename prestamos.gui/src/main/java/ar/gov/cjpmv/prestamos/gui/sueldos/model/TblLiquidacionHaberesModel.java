package ar.gov.cjpmv.prestamos.gui.sueldos.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPorcentual;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LiquidacionHaberes;

public class TblLiquidacionHaberesModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1892841720627929527L;
	private List<LiquidacionHaberes> listaLiquidacionHaberes;

	
	
	public TblLiquidacionHaberesModel(
			List<LiquidacionHaberes> listaLiquidacionHaberes) {
		super();
		this.listaLiquidacionHaberes = listaLiquidacionHaberes;
	}
	

	@Override
	public int getRowCount() {
		int filas= (this.listaLiquidacionHaberes!=null && !this.listaLiquidacionHaberes.isEmpty())?this.listaLiquidacionHaberes.size():0;
		return filas;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Mes";break;
			case 1: nombre = "Año"; break;
			case 2: nombre = "Tipo de Liquidacion"; break;	
		}
		return nombre;
	}
	
	
	@Override
	public Object getValueAt(int row, int column) {
		LiquidacionHaberes locLiquidacionHaberes= this.listaLiquidacionHaberes.get(row);
		switch(column){
			case 0: {
				Integer locMes= locLiquidacionHaberes.getMes(); 
				return locMes.toString();
			}
			case 1: {
				Integer locAnio= locLiquidacionHaberes.getAnio();
				return locAnio.toString();
			}
			case 2:{
				return locLiquidacionHaberes.getTipo().getCadena();
			}
		}
		return null;
	}
	
	
	public LiquidacionHaberes getLiquidacion(int fila) {
		return this.listaLiquidacionHaberes.get(fila);
	}


}
