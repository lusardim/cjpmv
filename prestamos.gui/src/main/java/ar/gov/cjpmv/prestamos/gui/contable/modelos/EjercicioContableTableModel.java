package ar.gov.cjpmv.prestamos.gui.contable.modelos;

import java.text.DateFormat;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;

public class EjercicioContableTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9002894686482195329L;
	private List<EjercicioContable> listaEjercicios;
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	
	
	public void setEjercicios(List<EjercicioContable> ejercicios) {
		this.listaEjercicios = ejercicios;
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column) {
			case 0: return "Activo";
			case 1: return "Año";
			case 2: return "Fecha de Inicio";
			case 3: return "Fecha de Fin";
			case 4: return "Plan de Cuentas";
		}
		return super.getColumnName(column);
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public int getRowCount() {
		return (listaEjercicios == null)?0:listaEjercicios.size();
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		EjercicioContable ejercicio = this.listaEjercicios.get(row);
		switch (column) {
			case 0: return (ejercicio.isActivo())?"Activo": "";
			case 1: return ejercicio.getAnio();
			case 2: return formateadorFecha.format(ejercicio.getFechaInicio());
			case 3: return formateadorFecha.format(ejercicio.getFechaFin());
			case 4: {
				return (ejercicio.getPlanCuenta()!=null)?ejercicio.getPlanCuenta().toString():"";
			}
		}
		return super.getValueAt(row, column);
	}
	
	public EjercicioContable getEjercicio(int pSeleccionado){
		return this.listaEjercicios.get(pSeleccionado);
	}
	
}
