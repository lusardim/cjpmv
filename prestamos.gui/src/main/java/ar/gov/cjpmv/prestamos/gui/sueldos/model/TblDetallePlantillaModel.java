package ar.gov.cjpmv.prestamos.gui.sueldos.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPorcentual;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;

public class TblDetallePlantillaModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1483542222606620904L;
	private List<ConceptoHaberes> detallePlantilla;
	private List<ConceptoHaberes> listaDetalleSeleccionada;
	
	
	
	
	
	public TblDetallePlantillaModel(List<ConceptoHaberes> detallePlantilla) {
		super();
		this.detallePlantilla = detallePlantilla;
		this.listaDetalleSeleccionada= new ArrayList<ConceptoHaberes>();
		
	}

	@Override
	public int getRowCount() {
		int valor= (this.detallePlantilla!=null && !this.detallePlantilla.isEmpty())?this.detallePlantilla.size():0;
		return valor;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Código";break;
			case 1: nombre = "Descripción"; break;
			case 2: nombre = "Tipo"; break;
			case 3: nombre = "Forma de Cálculo"; break;
			case 4: nombre = "Valor"; break;
			case 5: nombre = "Criterio de Aplicación"; break;
	
		}
		return nombre;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		ConceptoHaberes locConceptoHaberes= this.detallePlantilla.get(row);
		
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
			case 4:{
				if (locConceptoHaberes instanceof ConceptoPorcentual) {	
					return ((ConceptoPorcentual) locConceptoHaberes).getValor();
				}
				else if(locConceptoHaberes instanceof ConceptoFijo){
					return ((ConceptoFijo) locConceptoHaberes).getValor();
				}
				else if(locConceptoHaberes instanceof ConceptoPrefijado){
					return "según categoría/antigüedad/permanencia";
				}
			}
			case 5:{
				String dato="";
				if (locConceptoHaberes instanceof ConceptoPorcentual) {
					if(((ConceptoPorcentual) locConceptoHaberes).getSobreCategoria()!=null){
						dato= ((ConceptoPorcentual) locConceptoHaberes).getSobreCategoria().toString();
					}
					else if(((ConceptoPorcentual) locConceptoHaberes).getSobreTotalTipo()!=null){
						dato= ((ConceptoPorcentual) locConceptoHaberes).getSobreTotalTipo().toString();
					}
				}
				return dato;
			}
	
		}
		return null;
	}
	
	
	public ConceptoHaberes getConcepto(int fila) {
		return this.detallePlantilla.get(fila);
	}

	public List<ConceptoHaberes> getListaDetalle(int[] lineasSeleccionadas) {
		for(int cadaLinea: lineasSeleccionadas){
			this.listaDetalleSeleccionada.add(this.detallePlantilla.get(cadaLinea));
		}
		return listaDetalleSeleccionada;
	}

	public void setListaConceptos(List<ConceptoHaberes> listaDetallePlantilla) {
		this.detallePlantilla = listaDetallePlantilla;
		listaDetalleSeleccionada.clear();
		fireTableDataChanged();
	}

}
