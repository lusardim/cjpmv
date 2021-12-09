package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.dao.GenericDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Antiguedad;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;


public class AntiguedadDAO extends GenericDAOImpl<Antiguedad>{

	@Override
	public void agregar(Antiguedad pObjeto) throws LogicaException {
		throw new UnsupportedOperationException("Método todavía no implementado");
	}
	
	@Override
	public void eliminar(Antiguedad pObjeto) throws LogicaException {
		throw new UnsupportedOperationException("Método todavía no implementado");
	}
	
	@Override
	public void modificar(Antiguedad pObjeto) throws LogicaException {
		throw new UnsupportedOperationException("Método todavía no implementado");
	}
	
	@Override
	public Antiguedad getObjetoPorId(Long clave) {
		return this.entityManager.find(Antiguedad.class, clave);
	}

	@SuppressWarnings("unchecked")
	public List<Antiguedad> getListaAntiguedad() {
		List<Antiguedad> antiguedades = this.entityManager
				.createQuery("select a from Antiguedad a order by a.minimo")
				.getResultList();
		if (antiguedades.isEmpty()) {
			return null;
		}
		return antiguedades;
	}

	/**
	 * Obtiene el monto de antiguedad para ese empleo en particular
	 * @param empleo
	 * @return
	 */
	public BigDecimal getMontoAntiguedad(Empleo empleo) {
		Query query = this.entityManager.createQuery("select m.monto from MontoAntiguedadPorCategoria m" +
				"where m.antiguedad = :antiguedad and m.categoria = :categoria");
		query.setParameter("antiguedad", empleo.getAntiguedad());
		query.setParameter("categoria", empleo.getTipoCategoria());
		return (BigDecimal) query.getSingleResult();
	}
	
}
