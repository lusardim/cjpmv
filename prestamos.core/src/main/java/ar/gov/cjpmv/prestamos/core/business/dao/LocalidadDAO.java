package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;

public class LocalidadDAO extends GenericDAOImpl<Localidad>{

	@Override
	public Localidad getObjetoPorId(Long clave) {
		return this.entityManager.find(Localidad.class, clave);
	}
	
	@SuppressWarnings("unchecked")
	public List<Localidad> getListaLocalidades(){
		return this.entityManager.createQuery("select l from Localidad l").getResultList();
	}

	public List<Localidad> findListaLocalidad(String pNombre){
			
		String locConsulta="select p from Localidad p";
		String locConsultaFin=" order by p.departamento, p.nombre asc";
			
		Query locQuery;
		
		if(pNombre==null){
			locQuery= entityManager.createQuery(locConsulta+locConsultaFin);
		}
		else{
			locConsulta+=" where upper(p.nombre) like :nombre "+locConsultaFin;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("nombre", pNombre.toUpperCase().trim()+"%");
		}
		return locQuery.getResultList();
	}
	
	
	


	@Override
	protected void validarAlta(Localidad pObjeto) throws LogicaException {
		if(pObjeto.getDepartamento()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="departamento";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			if(pObjeto.getNombre()==null){
				int locCodigoMensaje=2;
				String locCampoMensaje="nombre de localidad";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else{
				String locConsulta="select t from Localidad t where upper(t.nombre) = :nombre and t.departamento = :departamento";
				Query locQuery;
				locQuery= entityManager.createQuery(locConsulta);
				locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
				locQuery.setParameter("departamento",pObjeto.getDepartamento());
				if (!locQuery.getResultList().isEmpty()){
					int locCodigoMensaje=1;
					String locCampoMensaje="La localidad";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
			}
		}
	}
	
	@Override
	protected void validarModificacion(Localidad pObjeto)throws LogicaException {
		if(pObjeto.getDepartamento()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="departamento";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			if(pObjeto.getNombre()==null){
				int locCodigoMensaje=2;
				String locCampoMensaje="nombre de localidad";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else{
				String locConsulta="select t from Localidad t where upper(t.nombre) = :nombre and t.departamento = :departamento and t.id != :id";
				Query locQuery;
				locQuery= entityManager.createQuery(locConsulta);
				locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
				locQuery.setParameter("departamento",pObjeto.getDepartamento());
				locQuery.setParameter("id",pObjeto.getId());
				if (!locQuery.getResultList().isEmpty()){
					int locCodigoMensaje=1;
					String locCampoMensaje="La localidad";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
			}
		}
	}
	
	
	@Override
	protected void validarEliminar(Localidad pObjeto)throws LogicaException {
	
		String locConsulta="select t from Domicilio t where t.localidad = :localidad";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("localidad",pObjeto);
		if (!locQuery.getResultList().isEmpty()){
			int locCodigoMensaje=6;
			String locCampoMensaje="La localidad";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		
	}


	
	
	
}
