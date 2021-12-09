package ar.gov.cjpmv.prestamos.gui.configuracion.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.hibernate.type.SetType;

import ar.gov.cjpmv.prestamos.core.business.dao.ConceptoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;

public class TblConceptosModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3447947781481867831L;
	
	private Concepto concepto;
	private ConceptoDAO conceptoDAO;
	private TipoCredito tipoCredito;
	private List<Concepto> listaConcepto;
	
	
	
	
	
	public TblConceptosModel(TipoCredito pTipoCredito) {
		super();
		this.tipoCredito= pTipoCredito;
		this.conceptoDAO= new ConceptoDAO();
		this.listaConcepto= this.conceptoDAO.findListaConcepto(null);
		
	}
	
	@Override
	public int getColumnCount() {
		return 2; 
	}
	
	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Conceptos";break;
			case 1: nombre = "Aplicado"; break;
		}
		return nombre;
	}
	
	@Override
	public int getRowCount() {
		if (this.listaConcepto==null){
			return 0;
		}
		return this.listaConcepto.size();
	}
	

 
	
	
	@Override
	public Class getColumnClass(int column)
	{
	      if (column == 0) return Object.class;
	      if (column == 1) return Boolean.class;
	      return Object.class;
	}


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 1){
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}


	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 1){
			Boolean locBoolean = (Boolean)aValue;
			if (locBoolean){
				Concepto locConceptoSeleccionado = this.listaConcepto.get(rowIndex);
				if (!this.tipoCredito.getListaConceptos().contains(locConceptoSeleccionado)){
					this.tipoCredito.getListaConceptos().add(locConceptoSeleccionado);
				}
			}
			else{
				Concepto locConceptoSeleccionado = this.listaConcepto.get(rowIndex);
				if (this.tipoCredito.getListaConceptos().contains(locConceptoSeleccionado)){
					this.tipoCredito.getListaConceptos().remove(locConceptoSeleccionado);
				}
			}
		}
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	
	@Override
	public Object getValueAt(int row, int column) {
		Concepto locConcepto = this.listaConcepto.get(row);
		
		switch(column){
			case 0: return locConcepto.getDescripcion(); 
			case 1: {
				boolean valor= comparacionConceptos(locConcepto); 
				return valor;
			}
		}
		return null;
	}
	
	
	

	private boolean comparacionConceptos(Concepto pConcepto) {
		for(Concepto cadaConcepto: this.tipoCredito.getListaConceptos()){
			long idA= cadaConcepto.getId();
			long idB= pConcepto.getId();
			if(idA==idB){
				return true;	
			}
		} 
		return false;
	}

	
	public Concepto getRowAt(int fila) {
		return this.conceptoDAO.findListaConcepto(null).get(fila);
		
	}

	
}