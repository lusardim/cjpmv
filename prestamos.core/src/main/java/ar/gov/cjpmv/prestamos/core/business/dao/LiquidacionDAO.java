package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.Date;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public interface LiquidacionDAO extends GenericDAO<Liquidacion> {

	public Liquidacion getLiquidacionPorPeriodo(Integer locMes, Integer locAnio,ViaCobro pViaCobro);
	/**
	 * <p>Obtiene la lista de DetalleLiquidacion que no han sido cobradas.
	 * Para obtenerlas considera la fecha a liquidar y cuenta la cantidadMeses hacia atrás</p>
	 * <p>Solo trae el DetalleLiquidacion más antiguo por cuenta corriente</p>
	 * @param pFechaLiquidar
	 * @param cantidadMeses
	 * @return
	 */
	public List<DetalleLiquidacion> getLiquidacionesAnteriores(Date pFechaLiquidar, int cantidadMeses);
	public boolean isPeriodoLiquidado(Date pVencimiento, ViaCobro pViaCobro);
	public Long getNumeroUltimaLiquidacion(int pAnio, int pMes);
	public void eliminarDetalle(Long id) throws LogicaException;
	
}
