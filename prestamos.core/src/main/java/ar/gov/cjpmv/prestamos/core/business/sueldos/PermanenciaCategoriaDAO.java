package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.dao.GenericDAOImpl;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PermanenciaCategoria;

public class PermanenciaCategoriaDAO extends GenericDAOImpl<PermanenciaCategoria> {

	@Override
	public PermanenciaCategoria getObjetoPorId(Long clave) {
		return this.entityManager.find(PermanenciaCategoria.class, clave);
	}
	
	@SuppressWarnings("unchecked")
	public List<PermanenciaCategoria> getListaPermanencia() {
		List<PermanenciaCategoria> lista = this.entityManager
				.createQuery("select p from PermanenciaCategoria p order by p.minimo")
				.getResultList();
		
		if (lista.isEmpty()) {
			return null;
		}
		return lista;
	}

	public BigDecimal getMontoPermanencia(Empleo empleo) {
		Query query = this.entityManager.createQuery("select m.monto from MontoPermanenciaPorCategoria m where " +
				"m.categoria = :categoria and m.permanencia = :permanencia");
		query.setParameter("categoria", empleo.getTipoCategoria());
		query.setParameter("permanencia", empleo.getPermanencia());
		return (BigDecimal) query.getSingleResult();
	}
	
}
