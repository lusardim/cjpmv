package ar.gov.cjpmv.prestamos.gui.creditos.exportacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;

public abstract class TblDetalleLiquidacionModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5892261398148427476L;
	private static final String[] COLUMNAS = {"CUIL","Nº de Legajo","Apellido","Nombres","Monto a Liquidar"};
	
	private List<DetalleLiquidacion> lista;
	
	public TblDetalleLiquidacionModel() {
		super();
		lista = new ArrayList<DetalleLiquidacion>();
	}
	
	@Override
	public int getColumnCount() {
		return COLUMNAS.length;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		DetalleLiquidacion detalle = this.getLista().get(row);
		return getValueAt(detalle,row,column);
	}

	public java.lang.Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0: return Long.class;
			case 1: return Integer.class;
			case 2: return String.class;
			case 3: return String.class;
			case 4: return BigDecimal.class;
		}
		return super.getColumnClass(columnIndex);
	};
	
	protected Object getValueAt(DetalleLiquidacion detalle,int row,int column) {
		PersonaFisica persona = (PersonaFisica)detalle.getPersona();
		switch(column){
			case 0: return persona.getCui();
			case 1: return persona.getLegajo();
			case 2: return persona.getApellido();
			case 3: return persona.getNombre();
			case 4: return detalle.getMonto();
		}
		return super.getValueAt(row, column);
	}

	@Override
	public int getRowCount() {
		if (this.lista==null){
			return 0;
		}
		return this.lista.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMNAS[column];
	}

	public void limpiar() {
		this.lista.clear();
		this.fireTableDataChanged();
	}

	public void setLista(List<DetalleLiquidacion> pLista) {
		this.lista = pLista;
		this.fireTableDataChanged();
	}
	
	protected List<DetalleLiquidacion> getLista() {
		return lista;
	}
	
	public DetalleLiquidacion getDetalleLiquidacion(int fila){
		return lista.get(fila);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
