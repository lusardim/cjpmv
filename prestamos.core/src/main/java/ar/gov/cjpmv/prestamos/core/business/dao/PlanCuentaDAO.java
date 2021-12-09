package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;

public class PlanCuentaDAO extends GenericDAOImpl<PlanCuenta> {
	
	private static final String PLAN_CUENTA_POR_ANIO = 
								"select p from PlanCuenta p " +
								"where year(p.periodo.fechaInicio) = :anio";
	private static final String PLAN_CUENTA_ACTIVO = 
								"select p from PlanCuenta p " +
								"where p.periodo.activo = true";
				
	
	@Override
	public PlanCuenta getObjetoPorId(Long clave) {
		return getEntityManager().find(PlanCuenta.class, clave);
	}

	public PlanCuenta getPlanCuenta(int anio) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			entityManager.setFlushMode(FlushModeType.AUTO);
			PlanCuenta planCuenta = (PlanCuenta)entityManager
						.createQuery(PLAN_CUENTA_POR_ANIO)
						.setParameter("anio", anio)
						.getSingleResult();
			planCuenta.getCuentasHijasComoLista();
			return planCuenta;
		}
		catch(NoResultException e) {
			return null;
		}
		finally {
			entityManager.close();
		}
	}
	
	/*
	 * Por alguna razón no anda, por lo tanto le mando un persist en vez de un
	 * merge (que es lo que hace el padre) dejar para arreglas más adelante
	 */
	@Override
	public void agregar(PlanCuenta pObjeto) throws LogicaException {
		// FIXME arreglar este método para que no necesitemos sobreescribirlo
		EntityManager locEntityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction locTransaction = locEntityManager.getTransaction();
		try{
			locTransaction.begin();
			this.validarAlta(pObjeto);
			locEntityManager.persist(pObjeto);
			locTransaction.commit();
		}
		catch(LogicaException e) {
			if (locTransaction != null && locTransaction.isActive()) {
				locTransaction.rollback();
			}
			throw e;
		}
		catch(Exception e) {
			if (locTransaction != null && locTransaction.isActive()) {
				locTransaction.rollback();
			}
			this.lanzarExcepcionDesconocida(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<PlanCuenta> getListaPlanesCuenta() {
		return this.entityManager.createQuery("from PlanCuenta")
				.getResultList();
	}

	public PlanCuenta getPlanCuentaActivo() {
		this.entityManager.clear();
		return (PlanCuenta)this.entityManager.createQuery(PLAN_CUENTA_ACTIVO)
				.getSingleResult();
	}

	public List<Cuenta> getCuentasPlanCuentaComoLista(PlanCuenta planCuenta) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			PlanCuenta planCuentaObtenido = entityManager.find(PlanCuenta.class, planCuenta.getId());
			return planCuentaObtenido.getCuentasHijasComoLista();
		}
		finally {
			entityManager.close();
		}
	}

}
