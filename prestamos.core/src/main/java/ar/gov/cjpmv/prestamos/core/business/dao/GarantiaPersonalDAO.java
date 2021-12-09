package ar.gov.cjpmv.prestamos.core.business.dao;


import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;

public class GarantiaPersonalDAO extends GenericDAOImpl<GarantiaPersonal> {

	@Override
	public GarantiaPersonal getObjetoPorId(Long clave) {
		return this.entityManager.find(GarantiaPersonal.class, clave);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Credito> findListaCreditosGarantia(PersonaFisica pPersonaFisica){
		String locConsulta="select g.credito from GarantiaPersonal g where g.garante=:locPersonaFisica and g.credito.estado!= :locEstado";
		Query locQuery = entityManager.createQuery(locConsulta);
		locQuery.setParameter("locPersonaFisica", pPersonaFisica);
		locQuery.setParameter("locEstado", EstadoCredito.FINALIZADO);
		return locQuery.getResultList();	
	}
	
	
	public GarantiaPersonal respondiendoComoGarante(PersonaFisica pPersonaFisica,Credito pCredito){
		String locConsulta="select t from GarantiaPersonal t where t.garante= :locPersonaFisica and t.credito= :locCredito";
		Query locQuery = entityManager.createQuery(locConsulta);
		locQuery.setParameter("locPersonaFisica", pPersonaFisica);
		locQuery.setParameter("locCredito", pCredito);
		return (GarantiaPersonal)locQuery.getSingleResult();
		
		 
	}
	
	

	
	
	
	
}
	
	
