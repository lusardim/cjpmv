package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.ConfiguracionSistema;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.Usuario;

public class ConfiguracionSistemaDAO extends GenericDAOImpl<ConfiguracionSistema> {

	@Override
	public ConfiguracionSistema getObjetoPorId(Long clave) {
		return this.entityManager.find(ConfiguracionSistema.class, clave);
	}
	
	public Departamento getDepartamentoSistema(){
		try{
			String consulta = "select e.persona.domicilio.departamento from ConfiguracionSistema e";
			return (Departamento)this.entityManager.createQuery(consulta).getSingleResult();
		}
		catch(NoResultException e){
			String consulta = "select e.persona.domicilio.localidad.departamento from ConfiguracionSistema e";
			return (Departamento)this.entityManager.createQuery(consulta).getSingleResult();
		}
	}
	
	public PersonaJuridica getPersonaJuridicaRegistrada() throws LogicaException{
		try{ 
			Query locQuery;
			String consulta ="select e.persona from ConfiguracionSistema e where e.persona.estado = :estado";
			locQuery= entityManager.createQuery(consulta);
			locQuery.setParameter("estado", EstadoPersonaJuridica.ACTIVO);
			return  (PersonaJuridica) locQuery.getSingleResult();
		}
		catch(NoResultException e){
			int locCodigoMensaje=8;
			String locCampoMensaje="";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
	}
	
	public List<PersonaJuridica> getPersonasJuridicasNoRegistradas(String pNombre){
		Query locQuery;
		String consulta ="select e from PersonaJuridica e , ConfiguracionSistema c where e!=c.persona and e.estado = :estado";
		if(pNombre==null){
			locQuery= entityManager.createQuery(consulta);
			locQuery.setParameter("estado", EstadoPersonaJuridica.ACTIVO);
		}
		else{
			
			consulta+=" and upper(e.nombre) like :nombre ";
			locQuery= entityManager.createQuery(consulta);
			locQuery.setParameter("nombre", pNombre.toUpperCase().trim()+"%");
			locQuery.setParameter("estado", EstadoPersonaJuridica.ACTIVO);
			
		}	
		return locQuery.getResultList();
	}

	
	/**
	 * La configuración del sistema
	 * @return
	 * @throws LogicaException cuando no hay una configuración cargada
	 */
	public ConfiguracionSistema getConfiguracionSistema() throws LogicaException{
		try{
			Query locQuery= entityManager.createQuery("select e from ConfiguracionSistema e");
			return (ConfiguracionSistema) locQuery.getSingleResult();
		}
		catch(NoResultException e){
			int locCodigoMensaje=16;
			String locCampoMensaje="";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		
	}
	
}
