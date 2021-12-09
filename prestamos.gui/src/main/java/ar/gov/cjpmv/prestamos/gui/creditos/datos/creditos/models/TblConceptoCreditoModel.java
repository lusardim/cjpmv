package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;

public class TblConceptoCreditoModel extends AbstractTableModel{

	private List<Concepto> listaConceptos;
	private boolean[] aplicado;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4403906372909340141L;

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		if (this.listaConceptos==null){
			return 0;
		}
		return this.listaConceptos.size();
	}

	@Override
	public String getColumnName(int column) {
		switch(column){
			case 0: return "Aplicado";
			case 1: return "Detalle";
			case 2: return "Valor";
			case 3: return "Imputado a cuotas";
			case 4: return "Emite cheque";
		}
		return null;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex){
			case 0: return Boolean.class;
			case 3: return Boolean.class;
			case 4: return Boolean.class;
		}
		return super.getColumnClass(columnIndex);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex==0){
			this.aplicado[rowIndex] = (Boolean)aValue;
			this.fireTableCellUpdated(rowIndex, columnIndex);
		}
		else{
			super.setValueAt(aValue, rowIndex, columnIndex);
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0){
			return true;
		}
		return false;
	}
	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Concepto locConcepto = this.listaConceptos.get(rowIndex);
		switch(columnIndex){
			case 0: return this.aplicado[rowIndex];
			case 1: return locConcepto.getDescripcion();
			case 2: {
				if (locConcepto.getPorcentual()){
					return locConcepto.getValor()+"%";
				}
				else{
					return locConcepto.getValor()+"$";
				}
			}
			case 3: return locConcepto.getAplicadoCuota();
			case 4: return locConcepto.getEmiteCheque();
		}
		return null;
	}

	public List<Concepto> getListaConceptos() {
		if (this.listaConceptos == null){
			this.listaConceptos = new ArrayList<Concepto>();
		}
		return listaConceptos;
	}
	
	public void setListaConceptos(List<Concepto> listaConceptos) {
		this.listaConceptos = listaConceptos;
		this.aplicado=new boolean[this.listaConceptos.size()];
		Arrays.fill(this.aplicado, true);
		this.fireTableDataChanged();
	}

	/**
	 * Retorna todos los conceptos que están realmente aplicados a los créditos
	 * @return
	 */
	public List<Concepto> getListaConceptosAplicadosCredito() {
		List<Concepto> locListaRetorno = new ArrayList<Concepto>(this.listaConceptos.size());
		for (int i = 0; i<this.listaConceptos.size(); i++){
			if (this.aplicado[i]){
				Concepto locConcepto = this.listaConceptos.get(i);
				if (!locConcepto.getAplicadoCuota()){
					locListaRetorno.add(locConcepto);
				}
			}
		}
		return locListaRetorno;
	}
	
	/**
	 * Retorna todos los conceptos que están realmente aplicados solo a las cuotas
	 * @return
	 */
	public List<Concepto> getListaConceptosAplicadosCuotas() {
		if (this.listaConceptos == null){
			return null;
		}
		List<Concepto> locListaRetorno = new ArrayList<Concepto>(this.listaConceptos.size());
		for (int i = 0; i<this.listaConceptos.size(); i++){
			if (this.aplicado[i]){
				Concepto locConcepto = this.listaConceptos.get(i);
				if (locConcepto.getAplicadoCuota()){
					locListaRetorno.add(locConcepto);
				}
			}
		}
		return locListaRetorno;
	}
}
