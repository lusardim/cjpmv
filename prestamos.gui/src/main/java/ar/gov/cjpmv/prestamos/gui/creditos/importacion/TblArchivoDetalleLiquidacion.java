package ar.gov.cjpmv.prestamos.gui.creditos.importacion;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;

public class TblArchivoDetalleLiquidacion extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8170400637331265020L;
	private List<ArchivoDetalleLiquidacionModel> listaArchivoDetalle;

	public TblArchivoDetalleLiquidacion(
			List<ArchivoDetalleLiquidacionModel> listaArchivoDetalle) {
		super();
		this.listaArchivoDetalle = listaArchivoDetalle;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "CUIL";break;
			case 1: nombre = "Nº Legajo"; break;
			case 2: nombre = "Apellido y Nombres"; break;
			case 3: nombre = "Monto Enviado"; break;
			case 4: nombre = "Monto Recibido"; break;
		}
		return nombre;
	}
	
	@Override
	public int getRowCount() {
		if (this.listaArchivoDetalle==null){
			return 0;
		}
		return this.listaArchivoDetalle.size();
	}

	@Override
	public Class getColumnClass(int column)
	{
	      if (column == 0) return Long.class;
	      if (column == 1) return Integer.class;
	      if (column == 2) return Object.class;
	      if (column == 3) return BigDecimal.class;
	      if (column == 4) return BigDecimal.class;
	      return Object.class;
	}

/*	FIXME VER POR QUÉ ESTABA ESTO ACÁ!!!
 * 	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0){
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}
*/
	@Override
	public Object getValueAt(int row, int column) {
		ArchivoDetalleLiquidacionModel locModel= this.listaArchivoDetalle.get(row);	
		switch(column){
			case 0: return locModel.getDetalleLiquidacion().getPersona().getCui();
			case 1: return locModel.getDetalleLiquidacion().getLegajo();
			case 2: return locModel.getDetalleLiquidacion().getNombreApellidoPersona();
			case 3: return locModel.getDetalleLiquidacion().getMonto();
			case 4: return locModel.getMontoRecibido();
		}
		return null;
	}
	
	
	public ArchivoDetalleLiquidacionModel  getArchivoDetalle(int fila){
		return this.listaArchivoDetalle.get(fila);
	}

}
