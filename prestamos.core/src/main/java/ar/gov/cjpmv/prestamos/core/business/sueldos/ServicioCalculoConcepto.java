package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Pension;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ReciboSueldo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;
 
public class ServicioCalculoConcepto {
	
	private Map<Categoria, BigDecimal> basicos;
	
	public ServicioCalculoConcepto() {
		cargarBasicos();
	}
	
	@SuppressWarnings("unchecked")
	private void cargarBasicos() {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			List<Categoria> categorias = 
					entityManager.createQuery("from Categoria").getResultList();
	
			this.basicos = new HashMap<Categoria, BigDecimal>();
			
			for (Categoria categoria : categorias) {
				basicos.put(categoria, categoria.getTotal());
			}
		}
		finally {
			entityManager.close();
		}
	}

	@SuppressWarnings("unchecked")
	public BigDecimal getValorFijoParaLiquidacionAnterior(int anio, int mes, ConceptoFijo concepto, PersonaFisica persona) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			String consulta = "select l.monto from LineaRecibo l left join l.reciboSueldo.liquidacion liq " +
					" where l.concepto = :concepto and " +
					" ( (liq.anio = :anio and liq.mes < :mes) " +
					"	or " +
					"	(liq.anio < :anio) " +
					") and l.reciboSueldo.persona = :persona" +
					" order by liq.anio desc, liq.mes desc";
			
			Query query = entityManager.createQuery(consulta);
			query.setParameter("concepto", concepto);
			query.setParameter("anio", anio);
			query.setParameter("mes", mes);
			query.setParameter("persona", persona);
			List<BigDecimal> result = query.setMaxResults(1).getResultList();
			if (result.isEmpty()) {
				return null; 
			}
			return result.get(0);
		}
		finally {
			entityManager.close();
		}
	}

	public BigDecimal getBasico(PersonaFisica persona, TipoLiquidacion tipoLiquidacion) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Query query = entityManager
				.createQuery("select c from Empleo e inner join e.tipoCategoria c " +
						" where e.empleado = :persona");
			query.setParameter("persona", persona);

			Categoria categoria = (Categoria) query.getSingleResult();
			categoria = getEquivalenciaCategoria(categoria, tipoLiquidacion);
			
			return basicos.get(categoria);
		}
		catch(NoResultException e) {
			return null;
		}
		finally {
			entityManager.close();
		}
	}

	public BigDecimal getPermanencia(PersonaFisica persona, TipoLiquidacion tipoLiquidacion) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Categoria categoria = getCategoria(persona, tipoLiquidacion);
			Query query = entityManager
					.createQuery("select m.monto " +
							"from MontoPermanenciaPorCategoria m, Empleo e " +
							"inner join m.permanencia p " +
							"where " +
							"e.empleado = :persona " +
							"and e.permanencia = p " +
							"and m.categoria = :categoria");
			query.setParameter("persona", persona);
			query.setParameter("categoria", categoria);
			return (BigDecimal) query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		finally {
			entityManager.close();
		}
	}

	public BigDecimal getAntiguedad(PersonaFisica persona, TipoLiquidacion tipoLiquidacion) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Categoria categoria = getCategoria(persona, tipoLiquidacion);
			
			Query query = entityManager
					.createQuery("select m.monto " +
							"from MontoAntiguedadPorCategoria m, Empleo e " +
							"inner join m.antiguedad a " +
							"where e.antiguedad = a " +
							"and e.empleado = :persona " +
							"and m.categoria = :categoria");
			query.setParameter("persona", persona);
			query.setParameter("categoria", categoria);
			return (BigDecimal) query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		finally {
			entityManager.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Integer getCantidad(ConceptoHaberes conceptoHaberes,
			ReciboSueldo recibo) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Integer anio = recibo.getLiquidacion().getAnio();
			Integer mes = recibo.getLiquidacion().getMes();
			TipoLiquidacion tipo = recibo.getLiquidacion().getTipo();

			Query query = entityManager.createQuery("select linea.cantidad " +
					" from LineaRecibo linea " +
					" left join linea.reciboSueldo recibo " +
					" left join recibo.liquidacion liq" +
					" where " +
					"  recibo.persona = :persona and " +
					"  liq.tipo = :tipo and" +
					"  linea.concepto = :concepto and  " +
					" ( (liq.anio = :anio and liq.mes < :mes) or " +
					"   (liq.anio < :anio) )" +
					"order by liq.anio desc, liq.mes desc");

			query.setParameter("persona", recibo.getPersona());
			query.setParameter("tipo", tipo);
			query.setParameter("concepto", conceptoHaberes);
			query.setParameter("anio", anio);
			query.setParameter("mes", mes);
			query.setMaxResults(1);

			List<Integer> resultados = query.getResultList();
			if (resultados.isEmpty()) {
				return null;
			}
			return resultados.get(0);
		}
		finally {
			entityManager.close();
		}
	}

	/**
	 * Obtiene el valor de la jubilacion para esta persona
	 * @param persona
	 * @return
	 */
	public BigDecimal getPorcentajeJubilacionSobreLiquidacion(PersonaFisica persona) {		
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Query query = entityManager.createQuery("select j.valor " +
					" from Jubilacion j where j.persona = :persona ");
			query.setParameter("persona", persona);			
			return (BigDecimal) query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		finally {
			entityManager.close();
		}
	}
	
	public Pension getPension(PersonaFisica persona) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Query query = entityManager.createQuery(
					"select p from Pension p where p.persona = :persona");
			query.setParameter("persona", persona);
			return (Pension)query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		finally {
			entityManager.close();
		}
	}

	public Categoria getEquivalenciaCategoria(Categoria sobreCategoria,
			TipoLiquidacion tipo) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			EstadoPersonaFisica tipoPersona = getTipoPersonaSegunTipoLiquidacion(tipo);
			
			Query query = entityManager.createQuery(
					"select c from Categoria c where c.numero = :numero" +
					" and c.tipoPersona = :tipoPersona");
			query.setParameter("numero", sobreCategoria.getNumero());
			query.setParameter("tipoPersona", tipoPersona);
			return (Categoria)query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		finally {
			entityManager.close();
		}
	}

	public Categoria getCategoria(PersonaFisica persona, TipoLiquidacion tipo) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Query query = entityManager.createQuery("select c from Categoria c, Empleo p" +
					" where p.empleado = :persona and " +
					"	c.numero = p.tipoCategoria.numero and " +
					"		c.tipoPersona = :tipo");
			EstadoPersonaFisica tipoPersona = getTipoPersonaSegunTipoLiquidacion(tipo);
			query.setParameter("persona", persona);
			query.setParameter("tipo", tipoPersona);
			return (Categoria)query.getSingleResult();
		}
		catch(NoResultException e) {
			return null;
		}
		finally {
			entityManager.close();
		}
	}

	private EstadoPersonaFisica getTipoPersonaSegunTipoLiquidacion(TipoLiquidacion tipo) {
		EstadoPersonaFisica tipoPersona;
		if (tipo.isActivo()) {
			tipoPersona = EstadoPersonaFisica.ACTIVO;
		}
		else {
			tipoPersona = EstadoPersonaFisica.PASIVO;
		}
		return tipoPersona;
	}
	
	public BigDecimal getPorcentajePension() {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Query query = entityManager.createQuery("select p.porcentaje " +
					" from PorcentajeTipoBeneficio p where p.tipo = :tipo");
			query.setParameter("tipo", TipoBeneficio.PENSION);
			return (BigDecimal) query.getSingleResult();
		}
		finally {
			entityManager.close();
		}
	}
	
	public BigDecimal getPorcentajeJubilacion() {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Query query = entityManager.createQuery("select p.porcentaje " +
					" from PorcentajeTipoBeneficio p where p.tipo = :tipo");
			query.setParameter("tipo", TipoBeneficio.JUBILACION_COMUN);
			return (BigDecimal) query.getSingleResult();
		}
		finally {
			entityManager.close();
		}
	}
}
