package ar.gov.cjpmv.prestamos.gui.configuracion.busquedas;

import javax.swing.table.TableModel;


public interface BusquedaModel{

	
	/**
	 *  actualiza la búsqueda, en caso de la tabla debe lanzar el evento 
	 */
	public void refrescarBusqueda();
	
	/**
	 * Obtiene el modelo de la tabla
	 * @return
	 */
	public TableModel getModeloTabla();
	
}
