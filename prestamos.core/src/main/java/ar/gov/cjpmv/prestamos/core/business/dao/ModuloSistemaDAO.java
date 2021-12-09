package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.ModuloSistema;

public class ModuloSistemaDAO extends GenericDAOImpl<ModuloSistema>{


	@Override
	public ModuloSistema getObjetoPorId(Long clave) {
		return this.entityManager.find(ModuloSistema.class, clave);
	}

	

	
	@SuppressWarnings("unchecked")
	public List<ModuloSistema> findListaModulos(){
		String locConsulta="select t from ModuloSistema t";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		return locQuery.getResultList();
	}
}
