package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class LiquidacionDAOImpl extends GenericDAOImpl<Liquidacion> implements LiquidacionDAO {

	@Override
	public Liquidacion getObjetoPorId(Long clave) {
		return this.entityManager.find(Liquidacion.class, clave);
	}
	
	public Liquidacion getLiquidacionPorPeriodo(Integer locMes, Integer locAnio,ViaCobro pViaCobro) {
		String consulta="select p from Liquidacion p where month(p.fechaLiquidacion)= month(:locFecha) "+
						" and year(p.fechaLiquidacion)= year(:locFecha)" +
						"and p.viaCobro = :viaCobro";
		Query locQuery;
		Calendar cal= Calendar.getInstance();
		cal.set(locAnio, locMes-1, 1);
		Date locFecha= cal.getTime();
		locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("locFecha", locFecha);
		locQuery.setParameter("viaCobro", pViaCobro);
		
		try{
			return (Liquidacion) locQuery.getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DetalleLiquidacion> getLiquidacionesAnteriores(
			Date pFechaLiquidar, 
			int cantidadMeses) 
	{
		Query consulta = this.entityManager.createQuery("select d " +
								"	from DetalleLiquidacion d " +
								"			inner join d.liquidacion l " +
								"    		left join d.listaCuotas cuotas" +
								" where " +
								"	year(l.fechaLiquidacion) < year(:fecha) or" +
								"   ((month(l.fechaLiquidacion) <= month(:fecha)) and (year(l.fechaLiquidacion) = year(:fecha))) " +
								"  and cuotas.cancelacion is null" +
								"  and l.fechaLiquidacion = lower(l.fechaLiquidacion)" +
								" group by d.cuentaCorrienteAfectada");
		
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(pFechaLiquidar);
		calendario.add(Calendar.MONTH,-1*cantidadMeses);
		consulta.setParameter("fecha", calendario.getTime(),TemporalType.DATE);
		List<DetalleLiquidacion> resultado = consulta.getResultList();
		return resultado;
	}

	public boolean isPeriodoLiquidado(Date pVencimiento, ViaCobro pViaCobro) {
		Query consulta = this.entityManager.createQuery("select l from Liquidacion l " +
				" where month(l.fechaLiquidacion) = :mes " +
				"		and " +
				"		year(l.fechaLiquidacion) = :anio " +
				"   and l.viaCobro = :viaCobro");
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(pVencimiento);
		int mes = calendario.get(Calendar.MONTH) + 1;
		int anio = calendario.get(Calendar.YEAR);
		
		consulta.setParameter("mes", mes);
		consulta.setParameter("anio", anio);
		consulta.setParameter("viaCobro", pViaCobro);
		consulta.setMaxResults(1);
		boolean hayResultados = consulta.getResultList().isEmpty();
		return !hayResultados;
	}
	

	/**
	 * Obtiene el último número de liquidación del mes y el año dado
	 * @param pAnio
	 * @param pMes
	 * @return
	 */
	public Long getNumeroUltimaLiquidacion(int pAnio, int pMes) {
		try{
			Query query = this.entityManager.createQuery("select count(l) from Liquidacion l where " +
											" year(l.fechaLiquidacion) = :anio and month(l.fechaLiquidacion) = :mes");
			query.setParameter("anio", pAnio);
			query.setParameter("mes", pMes);
			return (Long)query.getSingleResult();
		}
		catch(NoResultException ex){
			ex.printStackTrace();
			//significa que nunca fué liquidado nada para ese mes
			return 0l;
		}
	}

	public void eliminarDetalle(Long id) throws LogicaException {
		EntityTransaction locTransaction = this.entityManager.getTransaction();
		try{
			locTransaction.begin();
			DetalleLiquidacion detalle = this.entityManager.getReference(DetalleLiquidacion.class, id);
			this.entityManager.remove(detalle);
			locTransaction.commit();
		}
		catch(Exception e){
			if(locTransaction.isActive()){
				locTransaction.rollback();	
			}
			this.lanzarExcepcionDesconocida(e);
		}
	}
	
}
