package ar.gov.cjpmv.prestamos.gui.sueldos.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPorcentual;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LineaRecibo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ReciboSueldo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;

public class TblDetalleLiquidacionHaberesModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9146690946649488669L;
	
	private List<ReciboSueldo> listaReciboSueldo;
	private BigDecimal haberes;
	private BigDecimal descuento;
	private BigDecimal neto;
	
	private BigDecimal totalHaberes;
	private BigDecimal totalDescuento;
	private BigDecimal totalNeto;
	

	public TblDetalleLiquidacionHaberesModel(List<ReciboSueldo> listaDetalleLiquidacion) {
		super();
		this.listaReciboSueldo = listaDetalleLiquidacion;
	}

	@Override
	public int getRowCount() {
		int valor= (this.listaReciboSueldo!=null && !this.listaReciboSueldo.isEmpty())?this.listaReciboSueldo.size():0;
		return valor;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0: return Integer.class;
		}
		return super.getColumnClass(columnIndex);
	}
	
	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Legajo";break;
			case 1: nombre = "Nombre y Apellido"; break;
			case 2: nombre = "Total Haberes"; break;
			case 3: nombre = "Total Descuento"; break;
			case 4: nombre = "Total Neto"; break;
		}
		return nombre;
	}
	

	@Override
	public Object getValueAt(int row, int column) {
		ReciboSueldo locReciboSueldo= this.listaReciboSueldo.get(row);
		this.calcularTotales(locReciboSueldo);

		switch(column){
			case 0: {
				String locLegajo= (locReciboSueldo.getPersona().getLegajo()!=null)?locReciboSueldo.getPersona().getLegajo().toString():"";
				return locLegajo;
			}
			case 1: {
				String locNombreYApellido= (locReciboSueldo.getPersona().getNombreYApellido()!=null)?locReciboSueldo.getPersona().getNombreYApellido():"";
				return locNombreYApellido;
			}
			case 2:{
				return this.haberes.toString();
			}
			case 3:{
				return this.descuento.toString();
			}
			case 4:{
				return this.neto.toString();
			}
		}
		return null;
	}

	public void calcularTotalesRecibo(){
		this.totalHaberes= new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
		this.totalDescuento= new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
		this.totalNeto= new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
		if(listaReciboSueldo!=null){
			for(ReciboSueldo cadaRecibo: listaReciboSueldo){
				this.haberes= new BigDecimal("0.00").setScale(2,RoundingMode.HALF_UP);
				this.descuento= new BigDecimal("0.00").setScale(2,RoundingMode.HALF_UP);
				this.neto= new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
				for(LineaRecibo cadaLinea:cadaRecibo.getLineasRecibo()){
					if(!cadaLinea.getConcepto().getTipoCodigo().equals(TipoCodigo.DESCUENTO)){
						this.haberes= this.haberes.add(cadaLinea.getTotal());
					}
					else{
						this.descuento= this.descuento.add(cadaLinea.getTotal());

					}
				}
				this.neto= this.neto.add(this.haberes);
				this.neto= this.neto.subtract(this.descuento);
				this.totalHaberes= this.totalHaberes.add(this.haberes);
				this.totalDescuento= this.totalDescuento.add(this.descuento);
				this.totalNeto= this.totalNeto.add(this.neto);
			}
		}
	}
	
	
	public void calcularTotales(ReciboSueldo locReciboSueldo) {
		this.haberes= new BigDecimal("0.00").setScale(2,RoundingMode.HALF_UP);
		this.descuento= new BigDecimal("0.00").setScale(2,RoundingMode.HALF_UP);
		this.neto= new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
		for(LineaRecibo cadaLinea:locReciboSueldo.getLineasRecibo()){
			if(!cadaLinea.getConcepto().getTipoCodigo().equals(TipoCodigo.DESCUENTO)){
				this.haberes= this.haberes.add(cadaLinea.getTotal());
			}
			else{
				this.descuento= this.descuento.add(cadaLinea.getTotal());

			}
		}
		this.neto= this.neto.add(this.haberes);
		this.neto= this.neto.subtract(this.descuento);
	}

	public ReciboSueldo getReciboSueldo(int fila){
		return this.listaReciboSueldo.get(fila);
	}
	
	public BigDecimal getTotalHaberes(){
		return this.totalHaberes;
	}
	
	public BigDecimal getTotalDescuento(){
		return this.totalDescuento;
	}
	
	public BigDecimal getTotalNeto(){
		return this.totalNeto;
	}

	public List<ReciboSueldo> getListaSeleccionada(int[] lineasSeleccionadas) {
		List<ReciboSueldo> lista= new ArrayList<ReciboSueldo>();
		for(int cadaLinea: lineasSeleccionadas){
			lista.add(this.listaReciboSueldo.get(cadaLinea));
		}
		return lista;
	}   

	public List<ReciboSueldo> getListaRecibosSueldo() {
		return this.listaReciboSueldo;
	} 

	public void setRecibos(List<ReciboSueldo> recibos) {
		this.listaReciboSueldo = recibos;
		calcularTotalesRecibo();
		fireTableDataChanged();
	}

}
