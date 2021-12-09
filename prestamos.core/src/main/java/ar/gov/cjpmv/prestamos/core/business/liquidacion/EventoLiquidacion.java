package ar.gov.cjpmv.prestamos.core.business.liquidacion;


public enum EventoLiquidacion {
	/**
	 * Cuando incia la liquidación se lanza el evento CANTIDAD, 
	 * en newValue mantiene la cantidad
	 */
	CANTIDAD,
	
	/**
	 * Este evento lo lanza cuando la liquidación ha generado un detalle 
	 * de liquidación más.
	 * En newValue mantiene la cantidad de liquidaciones generadas
	 */
	PROCESADOS,
	
	/**
	 * Terminado es cuando la liquidación ha sido terminada
	 */
	TERMINADO,
	/**
	 * Este estado es cuando la liquidación ha lanzado un error
	 * newValue tiene la excepción
	 */
	ERROR;
}
