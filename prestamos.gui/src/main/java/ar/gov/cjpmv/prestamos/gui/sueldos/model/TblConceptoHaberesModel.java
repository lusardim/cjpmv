package ar.gov.cjpmv.prestamos.gui.sueldos.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;


import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPorcentual;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;

public class TblConceptoHaberesModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6362070330712048898L;
	
	private List<ConceptoHaberes> listaConceptoHaberes;
	

	public TblConceptoHaberesModel(List<ConceptoHaberes> listaConceptoHaberes){
		this.listaConceptoHaberes= listaConceptoHaberes;
	}
	
	@Override
	public int getRowCount() {
		int numero= (this.listaConceptoHaberes!=null && !this.listaConceptoHaberes.isEmpty())?this.listaConceptoHaberes.size(): 0;
		return numero;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Código";break;
			case 1: nombre = "Descripción"; break;
			case 2: nombre = "Tipo"; break;
			case 3: nombre = "Forma de Cálculo"; break;
	
		}
		return nombre;
	}
	

	@Override
	public Object getValueAt(int row, int column) {
		ConceptoHaberes locConceptoHaberes= this.listaConceptoHaberes.get(row);
		
		switch(column){
			case 0: {
				Integer codigo= locConceptoHaberes.getCodigo();
				return codigo.toString();
			}
			case 1: {
				return locConceptoHaberes.getDescripcion();
			}
			case 2:{
				return locConceptoHaberes.getTipoCodigo().toString();
			}
			case 3:{
				if (locConceptoHaberes instanceof ConceptoPorcentual) {	
					return "Porcentual";
				}
				else if(locConceptoHaberes instanceof ConceptoFijo){
					ConceptoFijo locConcFijo= (ConceptoFijo) locConceptoHaberes;
					String cadena=(locConcFijo.isSobreescribirValor())?"Importe Fijo":"Importe Particular";
					return cadena;
				}
				else if(locConceptoHaberes instanceof ConceptoPrefijado){
					return "Prefijado";
				}
			
			}
	
		}
		return null;
	}
	
	public ConceptoHaberes getConcepto(int fila) {
		return this.listaConceptoHaberes.get(fila);
	}

}
