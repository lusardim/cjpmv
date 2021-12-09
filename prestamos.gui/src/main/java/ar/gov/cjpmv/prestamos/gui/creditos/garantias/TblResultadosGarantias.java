package ar.gov.cjpmv.prestamos.gui.creditos.garantias;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;

public class TblResultadosGarantias extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NumberFormat formateadorMonetario= NumberFormat.getCurrencyInstance();
	
	private List<ResultadoGarantia> listaResultadoGarantia;
	

	public TblResultadosGarantias(
			List<ResultadoGarantia> listaResultadoGarantia) {
		super();
		this.listaResultadoGarantia = listaResultadoGarantia;
	}
	
	

	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public int getRowCount() {
		if(this.listaResultadoGarantia==null){
			return 0;
		}
		return this.listaResultadoGarantia.size();
	}
	

	@Override
	public String getColumnName(int column){
		String nombre=null;
		switch(column){
			case 0: nombre = "Crédito Nº"; break;
			case 1: nombre = "Solicitante"; break;
			case 2: nombre = "Garantes"; break;
			case 3: nombre = "Cuotas Adeudadas Vencidas"; break;
			case 4: nombre = "Saldo Adeudado Vencido"; break;
		}
		return nombre;
	}
	
	

	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	
	@Override
	public Object getValueAt(int row, int column) {
		ResultadoGarantia locRdo= this.listaResultadoGarantia.get(row); 
		switch(column){
			case 0: return locRdo.getCredito().getNumeroCredito();
			case 1: return locRdo.getCredito().getCuentaCorriente().getPersona().getNombreYApellido();
			case 2: return locRdo.getGarantes();
			case 3: return locRdo.getCantidadCuotasVencidas();
			case 4: return this.formateadorMonetario.format(locRdo.getMontoAdeudado());
		}
		return null;
	}
	
	
	public ResultadoGarantia getResultadoGarantia(int pSeleccionado){
		return this.listaResultadoGarantia.get(pSeleccionado);
	}
	
}
