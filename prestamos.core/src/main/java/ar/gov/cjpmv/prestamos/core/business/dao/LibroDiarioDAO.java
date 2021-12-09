package ar.gov.cjpmv.prestamos.core.business.dao;

import javax.persistence.NoResultException;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.LibroDiario;

public class LibroDiarioDAO extends GenericDAOImpl<LibroDiario>{
	
	private static final String LIBRO_DIARIO_POR_PERIODO =
			"select ld from LibroDiario ld " +
			"where ld.periodoContable = :periodo";
	
	@Override
	public LibroDiario getObjetoPorId(Long clave) {
		return this.entityManager.find(LibroDiario.class, clave);
	}

	public LibroDiario getLibroDiarioPorPeriodoContable(EjercicioContable ejercicio) {
		try {
			return (LibroDiario)this.entityManager.createQuery(LIBRO_DIARIO_POR_PERIODO)
							  		.setParameter("periodo", ejercicio)
							  		.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public void agregar(LibroDiario pObjeto) throws LogicaException {
		super.agregar(pObjeto);
		this.entityManager.refresh(pObjeto);
	}
	
}
