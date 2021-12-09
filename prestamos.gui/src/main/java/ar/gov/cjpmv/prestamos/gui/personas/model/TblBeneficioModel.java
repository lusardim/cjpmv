package ar.gov.cjpmv.prestamos.gui.personas.model;

import javax.swing.table.AbstractTableModel;

import java.text.DateFormat;
import java.util.List;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;

public class TblBeneficioModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5639744881323008345L;
	
	private List<Beneficio> listaBeneficio;
	private DateFormat formateadorFechas;
	
	
	
	public void setListaBeneficio(List<Beneficio> listaBeneficio) {
		this.listaBeneficio = listaBeneficio;
		fireTableDataChanged();
	}

	public TblBeneficioModel(List<Beneficio> listaBeneficio) {
		super();
		this.listaBeneficio = listaBeneficio;
		this.formateadorFechas = DateFormat.getDateInstance(DateFormat.SHORT);
	}
	

	@Override
	public int getRowCount() {
		if (this.listaBeneficio==null){
			return 0;
		}
		return this.listaBeneficio.size();
	}

	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Tipo de Beneficio";break;
			case 1: nombre = "Fecha Otorgamiento"; break;
			case 2: nombre = "Decreto"; break;
			case 3: nombre = "Ordenanza"; break;
	
		}
		return nombre;
	}
	
	@Override
	public int getColumnCount() {
		return 4; 
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Beneficio locBeneficio= this.listaBeneficio.get(row);
		
		switch(column){
			case 0: {
				if (locBeneficio.getTipoBeneficio()==null){
					return "";
				}
				else{
					return locBeneficio.getTipoBeneficio().getDescripcion(); 
				}
			}
			case 1: {
				if (locBeneficio.getFechaOtorgamiento()==null){
					return "";
				}
				else{
					return this.formateadorFechas.format(locBeneficio.getFechaOtorgamiento());
				}
			}
			case 2:{
				if (locBeneficio.getDecreto()==null){
					return "";
				}
				else{
					return locBeneficio.getDecreto();
				}
			}
			case 3:{
				if (locBeneficio.getOrdenanza()==null){
					return "";
				}
				else{
					return locBeneficio.getOrdenanza();
				}
			}
	
		}
		return null;
	}
	
	public Beneficio getBeneficio(int fila) {
		return this.listaBeneficio.get(fila);
	}


}
