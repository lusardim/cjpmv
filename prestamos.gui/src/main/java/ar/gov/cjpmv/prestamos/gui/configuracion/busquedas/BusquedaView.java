package ar.gov.cjpmv.prestamos.gui.configuracion.busquedas;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public interface BusquedaView {

	/**
	 * Obtiene el botón encargado de buscar
	 * @return
	 */
	public JButton getBtnBuscar();
	/**
	 * Obtiene la tabla donde se encuentra el resultado de la búsqueda
	 * @return
	 */
	public JTable getTblBusqueda();

	/**
	 * Obtiene un panel de parámetros de búsqueda
	 * @return
	 */
	public JPanel getPnlParametrosBusqueda();
	
	/**
	 * Setea la ventana como en búsqueda (deshabilita lo que sea necesario)
	 * @param pEnBusqueda
	 */
	public void setBusquedaHabilitada(boolean pEnBusqueda);
	
}
