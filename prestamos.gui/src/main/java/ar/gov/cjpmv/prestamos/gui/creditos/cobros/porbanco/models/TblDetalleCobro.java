package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.models;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionCuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;

public class TblDetalleCobro extends DefaultTableModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4664256109674428632L;
	
	private List<Cuota> listaCuotasACobrar;
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private boolean isCuotaConSeguro;
	private Date fechaCobro;


	public TblDetalleCobro(List<Cuota> cuotasACobrar, boolean pIsCuotaConSeguro, Date pFechaCobro) {
		super();
		this.listaCuotasACobrar= cuotasACobrar;
		this.isCuotaConSeguro= pIsCuotaConSeguro;
		this.fechaCobro= pFechaCobro;
	}

	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Nº de crédito";break;
			case 1: nombre = "Solicitante"; break;
			case 2: nombre = "Nº de cuota"; break;
			case 3: nombre = "Vencimiento"; break;
			case 4: nombre = "Valor Total"; break;
		}
		return nombre;
	}

	@Override
	public int getRowCount() {
		if (this.listaCuotasACobrar==null){
			return 0;
		}
		return this.listaCuotasACobrar.size();
	}
	
	

	@Override
	public Class getColumnClass(int column)
	{
	      if (column == 0) return Object.class;
	      if (column == 1) return Object.class;
	      if (column == 2) return Object.class;
	      if (column == 3) return Object.class;
	      if (column == 4) return Object.class;
	      
	      return Object.class;
	}
	
	
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0){
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Cuota locCuota= this.listaCuotasACobrar.get(row);
	
		
		switch(column){
			case 0: return locCuota.getCredito().getNumeroCredito();
			case 1: return locCuota.getCredito().getCuentaCorriente().getPersona().getNombreYApellido();
			case 2: return locCuota.getNumeroCuota();
			case 3: return this.formateadorFecha.format(locCuota.getVencimiento());
			case 4: return this.formateadorMonetario.format(locCuota.getTotalSegunVencimiento(this.isCuotaConSeguro, this.fechaCobro));			
		}
		return null;
	}
	
	/*
	 * 
	 * TODO VER QUE ES ESTOY
	private Float montoTotal(Cuota pCuota) {
		Float monto= 0F;
		
		if(pCuota.getSituacion().equals(SituacionCuota.NO_VENCIDA)){
			Calendar calendario= Calendar.getInstance();
		
			int anioActual= calendario.get(Calendar.YEAR);
			int mesActual= calendario.get(Calendar.MONTH);
			calendario.setTime(pCuota.getVencimiento());
			int anioCuota= calendario.get(Calendar.YEAR);
			int mesCuota= calendario.get(Calendar.MONTH);
			if((anioActual==anioCuota)&&(mesActual==mesCuota)){ 
		
					monto= pCuota.getTotal();
			}
			else{
					monto= pCuota.getTotalSinInteres();
			}
		}
		else{
			monto= pCuota.getTotal();
		}
		return monto;
	}*/
	
	public BigDecimal getMontoTotalCuotas(){
		BigDecimal montoTotalCuotas = new BigDecimal(0);
		for(Cuota locCuota: listaCuotasACobrar){
			montoTotalCuotas = montoTotalCuotas.add(locCuota.getTotalSegunVencimiento(this.isCuotaConSeguro, this.fechaCobro));
		}
		return montoTotalCuotas;
	}

}
