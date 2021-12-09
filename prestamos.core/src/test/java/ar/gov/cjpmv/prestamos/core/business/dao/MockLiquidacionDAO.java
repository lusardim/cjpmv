package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class MockLiquidacionDAO implements LiquidacionDAO {

	@Override
	public void agregar(Liquidacion pObjeto) throws LogicaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inicializarDao() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cerrarDao() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Liquidacion getObjetoPorId(Long clave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Liquidacion pObjeto) throws LogicaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificar(Liquidacion pObjeto) throws LogicaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<? extends Liquidacion> getLista(String pConsulta,
			Map<String, Object> pListaParametros, int pDesde, int pCantidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends Liquidacion> getLista(String pConsulta,
			Map<String, Object> pListaParametros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityManager getEntityManager() {
		return new MockEntityManager();
	}

	@Override
	public Liquidacion getLiquidacionPorPeriodo(Integer locMes,
			Integer locAnio, ViaCobro pViaCobro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetalleLiquidacion> getLiquidacionesAnteriores(
			Date pFechaLiquidar, int cantidadMeses) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPeriodoLiquidado(Date pVencimiento, ViaCobro pViaCobro) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long getNumeroUltimaLiquidacion(int pAnio, int pMes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarDetalle(Long id) throws LogicaException {
		// TODO Auto-generated method stub
		
	}

}
