package ar.gov.cjpmv.prestamos.gui.creditos.exportacion;

import java.io.File;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;

public class ExportacionMuniModel {
	
	private String mes;
	private int anio;
	private File guardarEn;
	private boolean imprimirPlanilla;
	private TblDetalleLiquidacionActualModel modeloTablaActual;
	private String[] listaMeses = new DateFormatSymbols().getMonths();
	private Liquidacion liquidacion;
	private int numeroLiquidacion;

	public ExportacionMuniModel() {
		modeloTablaActual = new TblDetalleLiquidacionActualModel();
	}
	
	public void setMes(String pMes){
		this.mes = pMes;
	}
	
	public String getMes() {
		return mes;
	}
	
	public int getNumeroMes(){
		if (this.mes != null){
			for (int i = 0; i<listaMeses.length; i++){
				if (listaMeses[i].equals(this.mes)){
					return i;
				}
			}
		}
		return 0;
	}
	
	public Date getFechaExportacion(){
		Calendar locCalendar = Calendar.getInstance();
		locCalendar.set(Calendar.YEAR,anio);
		locCalendar.set(Calendar.MONTH,this.getNumeroMes());
		locCalendar.set(Calendar.DAY_OF_MONTH, locCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return locCalendar.getTime();
	}
	
	public int getAnio() {
		return anio;
	}
	
	public File getGuardarEn() {
		return guardarEn;
	}
	
	public boolean isImprimirPlanilla() {
		return imprimirPlanilla;
	}
	
	public TblDetalleLiquidacionActualModel getModeloTablaActual() {
		return modeloTablaActual;
	}
	
	public String getNombreArchivoPorDefecto(){
		DateFormat formato = new SimpleDateFormat("MMyyyy");
		return "liquidacion_"+
			   formato.format(this.getFechaExportacion())+
			   ".txt";
	}

	public int getNumeroLiquidacion() {
		return numeroLiquidacion;
	}

	public void setNumeroLiquidacion(int numeroLiquidacion) {
		this.numeroLiquidacion = numeroLiquidacion;
	}
	
	public String[] getListaMeses() {
		return listaMeses;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public void setGuardarEn(File guardarEn) {
		this.guardarEn = guardarEn;
	}

	public void setImprimirPlanilla(boolean imprimirPlanilla) {
		this.imprimirPlanilla = imprimirPlanilla;
	}

	public void setListaMeses(String[] listaMeses) {
		this.listaMeses = listaMeses;
	}

	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
		if (liquidacion != null) {
			List<DetalleLiquidacion> listaDetalle = this.liquidacion.getListaDetalleLiquidacion();
			this.modeloTablaActual.setLista(listaDetalle);
		}
		else {
			this.modeloTablaActual.setLista(new ArrayList<DetalleLiquidacion>());
		}
	}

	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

}
