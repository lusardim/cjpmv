package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;

public class TipoDocumentoDAO extends GenericDAOImpl<TipoDocumento>{

	@Override
	public TipoDocumento getObjetoPorId(Long clave) {
		return this.entityManager.find(TipoDocumento.class, clave);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoDocumento> findListaTipoDocumento(String pDescripcion){
		
		String locConsulta="select t from TipoDocumento t";
		String locConsultaFin=" order by t.descripcion asc";
		Query locQuery;
		
		
		
		if(pDescripcion==null){
			locQuery= entityManager.createQuery(locConsulta+locConsultaFin);
		}
		else{
			locConsulta+=" where upper(t.descripcion) like :descripcion "+locConsultaFin;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("descripcion", pDescripcion.toUpperCase().trim()+"%");
		}
		return locQuery.getResultList();
	}
	
	
	
	
	@Override
	protected void validarAlta(TipoDocumento pObjeto) throws LogicaException {
		if(pObjeto.getDescripcion()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="descripcion de tipo de documento";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsulta="select t from TipoDocumento t where upper(t.descripcion) = :descripcion";
			Query locQuery;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("descripcion",pObjeto.getDescripcion().toUpperCase().trim());
			if (!locQuery.getResultList().isEmpty()){
				int locCodigoMensaje=1;
				String locCampoMensaje="El tipo de documento";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}
	
	@Override
	protected void validarModificacion(TipoDocumento pObjeto)throws LogicaException {
		if(pObjeto.getDescripcion()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="descripcion de tipo de documento";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsulta="select t from TipoDocumento t where upper(t.descripcion) = :descripcion and t.id != :id";
			Query locQuery;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("descripcion",pObjeto.getDescripcion().toUpperCase().trim());
			locQuery.setParameter("id",pObjeto.getId());
			if (!locQuery.getResultList().isEmpty()){
				int locCodigoMensaje=1;
				String locCampoMensaje="El tipo de documento";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}
	
	@Override
	protected void validarEliminar(TipoDocumento pObjeto)throws LogicaException {
	
		String locConsulta="select t from PersonaFisica t where t.tipoDocumento= :tipoDocumento";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("tipoDocumento",pObjeto);
		if (!locQuery.getResultList().isEmpty()){
			int locCodigoMensaje=10;
			String locCampoMensaje="El tipo de documento";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		
	}
	
	
	
}
