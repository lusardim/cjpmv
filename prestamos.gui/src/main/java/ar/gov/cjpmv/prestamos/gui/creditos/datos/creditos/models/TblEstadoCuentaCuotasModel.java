package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;

public class TblEstadoCuentaCuotasModel extends TblCuotasModel {
 
	private static final long serialVersionUID = 5867026489543953854L;

	@Override
	public int getColumnCount() {
		return 7;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 6) {
			return "Fecha cobro";
		}
		return super.getColumnName(column);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex <= 5) {
			return super.getValueAt(rowIndex, columnIndex);		
		}
		else {
			Cuota locCuota = this.getCuota(rowIndex);
			if (locCuota.getCancelacion() != null) {
				return this.formateadorFecha.format(locCuota.getCancelacion().getCobro().getFecha());
			}
			else {
				return "";
			}
		}
	}
}
