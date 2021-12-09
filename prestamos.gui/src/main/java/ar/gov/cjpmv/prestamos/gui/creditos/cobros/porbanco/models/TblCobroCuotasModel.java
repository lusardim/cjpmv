package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.models;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionCuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.ITblCuotaModel;


public class TblCobroCuotasModel extends AbstractTableModel implements ITblCuotaModel {
	
	private static final long serialVersionUID = 8649811918063114714L;
	
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();

	private List<RegistroCobroCuotas> listaCuota;
	private boolean isCuotaConSeguro;
	private Date fecha;	

	public TblCobroCuotasModel(List<Cuota> pListaCuota, boolean pIsCuotaConSeguro, Date pFecha) {
		super();
		this.isCuotaConSeguro= pIsCuotaConSeguro;
		this.formateadorDecimal.setMinimumFractionDigits(2);
		this.formateadorDecimal.setMaximumFractionDigits(2);
		this.listaCuota= this.convertirCuotas(pListaCuota);
		this.fecha= pFecha;
	}
	
	private List<RegistroCobroCuotas> convertirCuotas(List<Cuota> pListaCuota) {
		List<RegistroCobroCuotas> listaRetorno= new ArrayList<RegistroCobroCuotas>(pListaCuota.size());
		for(Cuota cadaCuota: pListaCuota){
			listaRetorno.add(new RegistroCobroCuotas(cadaCuota, false));
		}
		return listaRetorno;
	}

	@Override
	public int getColumnCount() {
		return 8;
	}
	
	@Override
	public int getRowCount() {
		if (this.listaCuota==null){
			return 0;
		}
		return this.listaCuota.size();
	}

	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Cobrar: SI/NO";break;
			case 1: nombre = "Nº de Crédito"; break;
			case 2: nombre = "Nº de Cuota"; break;
			case 3: nombre = "Vencimiento"; break;
			case 4: nombre = "Otros Conceptos"; break;
			case 5: nombre = "Interés"; break;
			case 6: nombre = "Capital"; break;
			case 7: nombre = "Valor Total"; break;
		}
		return nombre;
	}
	
	@Override
	public Class<?> getColumnClass(int column) {
		switch(column) {
			case 0: return Boolean.class;
			case 1: return Object.class;
			case 2: return Object.class;
			case 3: return String.class;
			case 4: return Object.class;
			case 5: return Object.class;
			case 6: return Object.class;
			case 7: return Object.class;
		}
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(columnIndex==0){
			this.listaCuota.get(rowIndex).setCobrar((Boolean)aValue);
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		RegistroCobroCuotas locRegistro= this.listaCuota.get(row);
		Cuota locCuota = locRegistro.getCuota();
		
		switch(column){
			case 0: return locRegistro.isCobrar();
			case 1: return locCuota.getCredito().getNumeroCredito();
			case 2: return locCuota.getNumeroCuota();
			case 3: return this.formateadorFecha.format(locCuota.getVencimiento());
			case 4: return this.formateadorMonetario.format(locCuota.getOtrosConceptos());
			case 5: return this.formateadorDecimal.format(locCuota.getInteres());
			case 6: return this.formateadorMonetario.format(locCuota.getCapital());
			case 7: return this.formateadorMonetario.format(locCuota.getTotalSegunVencimiento(isCuotaConSeguro, this.fecha));
		}
		return null;
	}

	@Override
	public Cuota getCuota(int fila) {
		return this.listaCuota.get(fila).getCuota();
	}
	
	public List<Cuota> getListaCuotasACobrar(){
		List<Cuota> cuotasCobrar= new ArrayList<Cuota>();
		for(RegistroCobroCuotas locCuota: listaCuota){
			if(locCuota.isCobrar()){
				cuotasCobrar.add(locCuota.getCuota());
			}
		}
		return cuotasCobrar;
	}
}
