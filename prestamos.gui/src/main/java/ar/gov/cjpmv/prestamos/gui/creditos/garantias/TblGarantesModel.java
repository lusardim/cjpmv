package ar.gov.cjpmv.prestamos.gui.creditos.garantias;


import java.math.BigDecimal;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;


public class TblGarantesModel extends AbstractTableModel {
	
	

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<GarantiaPersonal> listaGarantiasPersonal;
	

	public TblGarantesModel(List<GarantiaPersonal> pListaGarantiasPersonal) {
		this.listaGarantiasPersonal= pListaGarantiasPersonal;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public String getColumnName(int column){
		String nombre=null;
		switch(column){
			case 0: nombre = "Cuil"; break;
			case 1: nombre = "Nombre y Apellido"; break;
			case 2: nombre = "Afectar/Desafectar"; break;
			case 3: nombre = "% de Afectación"; break;
			case 4: nombre = "Vía de Cobro"; break;
		}
		return nombre;
	}

	
	@Override
	public int getRowCount() {
		if(this.listaGarantiasPersonal==null){
			return 0;
		}
		return this.listaGarantiasPersonal.size();
	}
	

	

	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if ((columnIndex==2)||(columnIndex==3)||(columnIndex==4)){
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}
	
	
	
	@Override
	public Class getColumnClass(int column)
	{
	      if (column == 0) return Object.class;
	      if (column == 1) return Object.class;
	      if (column == 2) return Boolean.class;
	      if (column == 3) return Float.class;
	      if (column == 4) return ViaCobro.class;
	      return Object.class;
	}

	
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 2){
			Boolean locBoolean = (Boolean)aValue;
			if (locBoolean){
				Garantia locGarantiaSeleccionado = this.listaGarantiasPersonal.get(rowIndex);
				if (!locGarantiaSeleccionado.getAfectar()){
					locGarantiaSeleccionado.setAfectar(true);
				}
			}
			else{
				Garantia locGarantiaSeleccionado = this.listaGarantiasPersonal.get(rowIndex);
				if (locGarantiaSeleccionado.getAfectar()){
					locGarantiaSeleccionado.setAfectar(false);
				}
			}
		}
		else if(columnIndex==3){
			BigDecimal monto= new BigDecimal(aValue.toString());
			this.listaGarantiasPersonal.get(rowIndex).setPorcentaje(monto);
		}
		else if(columnIndex==4){
			ViaCobro viaCobro= (ViaCobro)aValue;
			this.listaGarantiasPersonal.get(rowIndex).setViaCobro(viaCobro);
		}
		else {
		//TODO TE AMOUUU MUCHO MUCHO TODO FIXME!!! :D <3
			super.setValueAt(aValue, rowIndex, columnIndex);
		}
	}


	@Override
	public Object getValueAt(int row, int column) {
		GarantiaPersonal locGarantiaPersonal= this.listaGarantiasPersonal.get(row); 
		switch(column){
			case 0: return locGarantiaPersonal.getGarante().getCui();
			case 1: return locGarantiaPersonal.getGarante().getNombreYApellido();
			case 2: return locGarantiaPersonal.getAfectar();
			case 3: return locGarantiaPersonal.getPorcentaje();
			case 4: {
				if(locGarantiaPersonal.getViaCobro()!=null){
					return locGarantiaPersonal.getViaCobro();
				}
			}
		}
		return null;
	}
	

	public GarantiaPersonal getGarantiaPersonal(int pSeleccionado){
		return this.listaGarantiasPersonal.get(pSeleccionado);
	}
	
	public List<GarantiaPersonal> getListaGarantiaPersonal(){
		return this.listaGarantiasPersonal;
	}
	
	
	
	

	
	
}
