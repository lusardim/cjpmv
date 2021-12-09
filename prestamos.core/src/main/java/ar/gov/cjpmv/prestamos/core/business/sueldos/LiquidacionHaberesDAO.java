package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.dao.GenericDAOImpl;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LiquidacionHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ReciboSueldo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class LiquidacionHaberesDAO extends GenericDAOImpl<LiquidacionHaberes> {

	@Override
	public LiquidacionHaberes getObjetoPorId(Long clave) {
		return entityManager.find(LiquidacionHaberes.class, clave);
	}

	/**
	 * Retorna la liquidacion correspondiente a los parámetros
	 * @param mes
	 * @param anio
	 * @param tipo
	 * @return
	 */
	public LiquidacionHaberes getLiquidacion(int mes, int anio,
			TipoLiquidacion tipo) {
		try {
			String consulta = "from LiquidacionHaberes liq "
					+ "where liq.anio = :anio and liq.mes = :mes and liq.tipo = :tipo";
			Query query = this.entityManager.createQuery(consulta);
			query.setParameter("mes", mes);
			query.setParameter("anio", anio);
			query.setParameter("tipo", tipo);
			return (LiquidacionHaberes) query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Retorna la lista de liquidaciones que cumplen con los parametros de filtrado, si son todos nulos
	 * retorna la lista completa
	 * @param mes
	 * @param anio
	 * @param tipo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LiquidacionHaberes> findLiquidacion(Integer mes, Integer anio, TipoLiquidacion tipo) {
		String consulta = "from LiquidacionHaberes liq ";
		boolean primero = true;
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if (mes != null) {
			consulta += (primero)?" where ":" and ";
			primero = false;
			consulta += " liq.mes = :mes";
			parametros.put("mes", mes);
		}
		
		if (anio!= null) {
			consulta += (primero)?" where ":" and ";
			primero = false;
			consulta += " liq.anio = :anio ";
			parametros.put("anio", anio);
		}
		
		if (tipo != null) {
			consulta += (primero)?" where ":" and ";
			primero = false;
			consulta += " liq.tipo = :tipo";
			parametros.put("tipo", tipo);
		}
		
		return this.crearQuery(consulta, parametros).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ConceptoHaberes> getConceptosLiquidacionAnterior(
			int mes, 
			int anio, 
			PersonaFisica persona, TipoLiquidacion tipoLiquidacion) 
	{
			Query consulta = entityManager.createQuery("select l.concepto from LineaRecibo l " +
					"	inner join l.reciboSueldo.liquidacion liq " +
					" where " +
					"   liq.tipo = :tipo and " +
					"	l.reciboSueldo.persona = :persona " +
					" and " +
					"  ( liq.anio < :anio or " +
					"	 (liq.anio = :anio and liq.mes < :mes)" +
					"  )");
			consulta.setParameter("tipo", tipoLiquidacion);
			consulta.setParameter("anio", anio);
			consulta.setParameter("mes", mes);
			consulta.setParameter("persona", persona);
			List<ConceptoHaberes> conceptos = consulta.getResultList();
			if (conceptos.isEmpty()) {
				return null;
			}
			else {
				for (ConceptoHaberes concepto : conceptos) {
					entityManager.refresh(concepto);
				}
			}
			return conceptos;
	}

	public List<ReciboSueldo> getRecibos(LiquidacionHaberes liquidacionHaberes) {
		this.entityManager.refresh(liquidacionHaberes);
		return liquidacionHaberes.getRecibos();
	}

	public boolean isEmpleoLiquidado(Empleo pEmpleo) {
		//Jubilados y activos
		String consulta="select count(r) from ReciboSueldo r where r.persona= :persona ";
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("persona", pEmpleo.getEmpleado());
		Long cont= (Long) locQuery.getSingleResult();
		if (cont>0){
			return true;
		}
		
		//Pensionados
		String consultaP="select count(r) from Pension p, ReciboSueldo r " +
				" inner join r.lineasRecibo l " +
				" where p.causante=:persona and r.persona=p.persona " +
				" and l.concepto.id=5";
		locQuery= this.entityManager.createQuery(consultaP);
		locQuery.setParameter("persona", pEmpleo.getEmpleado());
		cont= (Long) locQuery.getSingleResult();
		if (cont>0){
			return true;
		}
		
		
		return false;
	}

	public boolean isBeneficioLiquidado(Beneficio beneficio) {
		Long idBeneficio= 4L;
		if(beneficio.getTipoBeneficio().equals(TipoBeneficio.PENSION)){
			idBeneficio=5L;
		}
		
		String consulta= "select count(r) from ReciboSueldo r" +
				" inner join r.lineasRecibo l " +
				" where l.concepto.id= :idBenef "+
				" and r.persona= :persona";
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("idBenef", idBeneficio);
		locQuery.setParameter("persona", beneficio.getPersona());
		Long cont= (Long) locQuery.getSingleResult();
		if (cont>0){
			return true;
		}
			
		return false;
		
	}

}
