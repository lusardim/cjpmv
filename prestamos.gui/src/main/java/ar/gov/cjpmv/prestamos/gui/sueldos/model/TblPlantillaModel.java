package ar.gov.cjpmv.prestamos.gui.sueldos.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPorcentual;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Plantilla;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class TblPlantillaModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6175298897131903555L;
	private List<Plantilla> listaPlantilla;
	
	

	public TblPlantillaModel(List<Plantilla> listaPlantilla) {
		super();
		this.listaPlantilla = listaPlantilla;
	}

	
	@Override
	public int getRowCount() {
		int filas= (this.listaPlantilla!=null && !this.listaPlantilla.isEmpty())?this.listaPlantilla.size(): 0;
		return filas;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}
	
	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Tipo de Liquidación"; break;	
		}
		return nombre;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Plantilla locPlantilla= this.listaPlantilla.get(row);
			return locPlantilla.getTipoLiquidacion().getCadena();
	}
	
	public Plantilla getPlantilla(int fila) {
		return this.listaPlantilla.get(fila);
	}


}
