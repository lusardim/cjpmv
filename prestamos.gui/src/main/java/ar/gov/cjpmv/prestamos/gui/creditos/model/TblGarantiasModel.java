package ar.gov.cjpmv.prestamos.gui.creditos.model;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;

public class TblGarantiasModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Credito> listaCreditosGarantias;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	

	
	public TblGarantiasModel(List<Credito> pListaCreditosGarantias) {
		this.listaCreditosGarantias= pListaCreditosGarantias;
		
	}

	@Override
	public int getColumnCount() {
		return 8;
	}
	
	@Override
	public String getColumnName(int column){
		String nombre=null;
		switch(column){
			case 0: nombre = "Número"; break;
			case 1: nombre = "Solicitante"; break;
			case 2: nombre = "Tipo"; break;
			case 3: nombre = "Fecha de Inicio"; break;
			case 4: nombre = "Monto Total"; break;
			case 5: nombre = "Cantidad de Cuotas"; break;
			case 6: nombre = "Cuotas Adeudadas"; break;
			case 7: nombre = "Saldo Adeudado"; break;
		}
		return nombre;
	}
	
	
	@Override
	public int getRowCount() {
		if(this.listaCreditosGarantias==null){
			return 0;
		}
		return this.listaCreditosGarantias.size();
	}
	
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	
	@Override
	public Object getValueAt(int row, int column) {
		Credito locCredito= this.listaCreditosGarantias.get(row); 
		switch(column){
			case 0: return locCredito.getNumeroCredito();
			case 1: return locCredito.getCuentaCorriente().getPersona().getNombreYApellido();
			case 2: return locCredito.getTipoCredito().getNombre();
			case 3: return this.formateadorFecha.format(locCredito.getFechaInicio());
			case 4: return this.formateadorMonetario.format(locCredito.getMontoTotal());
			case 5: return locCredito.getCantidadCuotas();
			case 6: return locCredito.getCuotasAdeudadas();
			case 7: return this.formateadorMonetario.format(locCredito.getSaldoAdeudado());
		}
		return null;
	}
	public Credito getCredito(int pSeleccionado){
		return this.listaCreditosGarantias.get(pSeleccionado);
	}
	


}
