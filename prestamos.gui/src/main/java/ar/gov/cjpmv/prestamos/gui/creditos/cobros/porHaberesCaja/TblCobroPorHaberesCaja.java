package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porHaberesCaja;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;

public class TblCobroPorHaberesCaja extends AbstractTableModel {

	private static final long serialVersionUID = 8481206280137277719L;
	
	private List<DetalleLiquidacion> lista;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();

	TblCobroPorHaberesCaja() {
		super();
		this.lista = new ArrayList<DetalleLiquidacion>();
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column){
			case 0: return "CUIL"; 
			case 1: return "Nº Legajo"; 
			case 2: return "Apellido y Nombres"; 
			case 3: return "Monto a cobrar"; 
		}
		return super.getColumnName(column);
	}
	
	@Override
	public int getRowCount() {
		return lista.size();
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case 0: return Long.class; 
			case 1: return Long.class;
			case 2: return String.class;
			case 3: return Float.class;
		}
		return super.getColumnClass(columnIndex);
	}

	@Override
	public Object getValueAt(int row, int column) {
		DetalleLiquidacion locModel= this.lista.get(row);	
		PersonaFisica locPersona = (PersonaFisica)locModel.getPersona();
		switch(column){
			case 0: return locPersona.getCui();
			case 1: return locPersona.getLegajo();
			case 2: return locPersona.getNombreYApellido();
			case 3: return locModel.getMonto();
		}
		return null;
	}
	
	public void setLista(List<DetalleLiquidacion> lista) {
		if (lista == null){
			this.lista = new ArrayList<DetalleLiquidacion>();
		}
		else {
			this.lista = lista;
		}
		fireTableDataChanged();
	}

	public void limpiar() {
		this.lista.clear();
		fireTableDataChanged();
	}

	public void add(DetalleLiquidacion pModelo) {
		int tamanio = this.lista.size();
		lista.add(pModelo);
		fireTableRowsInserted(tamanio, tamanio);
	}
	
	public List<DetalleLiquidacion> getLista() {
		return lista;
	}
}
