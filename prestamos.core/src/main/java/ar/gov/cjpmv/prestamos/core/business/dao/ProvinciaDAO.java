package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;


public class ProvinciaDAO extends GenericDAOImpl<Provincia>{

	@Override
	public Provincia getObjetoPorId(Long clave) {
		return this.entityManager.find(Provincia.class, clave);
	}

	@SuppressWarnings("unchecked")
	public List<Provincia> getListaProvincias(){
		return this.entityManager.createQuery("select p from Provincia p").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Provincia> findListaPronvincia(String pNombre){
		
		String locConsulta="select p from Provincia p";
		String locConsultaFin=" order by p.nombre asc";
		
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
	protected void validarAlta(Provincia pObjeto) throws LogicaException {
		if(pObjeto.getNombre()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="nombre de provincia";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsulta="select t from Provincia t where upper(t.nombre) = :nombre";
			Query locQuery;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
			if (!locQuery.getResultList().isEmpty()){
				int locCodigoMensaje=1;
				String locCampoMensaje="La provincia";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}
	
	@Override
	protected void validarModificacion(Provincia pObjeto)throws LogicaException {
		if(pObjeto.getNombre()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="nombre de provincia";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsulta="select t from Provincia t where upper(t.nombre) = :nombre and t.id != :id";
			Query locQuery;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
			locQuery.setParameter("id",pObjeto.getId());
			if (!locQuery.getResultList().isEmpty()){
				int locCodigoMensaje=1;
				String locCampoMensaje="La provincia";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}
	
	@Override
	protected void validarEliminar(Provincia pObjeto)throws LogicaException {
	
		String locConsulta="select t from Departamento t where t.provincia = :provincia";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("provincia",pObjeto);
		if (!locQuery.getResultList().isEmpty()){
			int locCodigoMensaje=13;
			String locCampoMensaje="La provincia";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		
	}
	

}
