package ar.gov.cjpmv.prestamos.core.business.prestamos;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.dao.GenericDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Banco;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;

public class TipoCreditoDAO extends GenericDAOImpl<TipoCredito>{

	@Override
	public TipoCredito getObjetoPorId(Long clave) {
		return this.entityManager.find(TipoCredito.class, clave);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoCredito> findListaTipoCredito(String pNombre){
		
		String locConsulta="select t from TipoCredito t";
		String locConsultaFin=" order by t.nombre asc";
		Query locQuery;
		
		if(pNombre==null){
			locQuery= entityManager.createQuery(locConsulta+locConsultaFin);
		}
		else{
			locConsulta+=" where upper(t.nombre) like :nombre "+locConsultaFin;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("dnombre", pNombre.toUpperCase().trim()+"%");
		}
		return locQuery.getResultList();
	}

	@Override
	protected void validarAlta(TipoCredito pObjeto) throws LogicaException {
		if(pObjeto.getNombre()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="descripción de tipo de crédito";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsulta="select t from TipoCredito t where upper(t.nombre) = :nombre";
			Query locQuery;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
			if (!locQuery.getResultList().isEmpty()){
				int locCodigoMensaje=1;
				String locCampoMensaje="El tipo de crédito";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}
	
	@Override
	protected void validarModificacion(TipoCredito pObjeto)throws LogicaException {
		if(pObjeto.getNombre()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="descripción de tipo de crédito";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsulta="select t from TipoCredito t where upper(t.nombre) = :nombre and t.id != :id";
			Query locQuery;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
			locQuery.setParameter("id",pObjeto.getId());
			if (!locQuery.getResultList().isEmpty()){
				int locCodigoMensaje=1;
				String locCampoMensaje="El tipo de crédito";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}
	
	@Override
	protected void validarEliminar(TipoCredito pObjeto)throws LogicaException {
	
		String locConsulta="select t from Credito t where t.tipoCredito= :tipoCredito";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("tipoCredito",pObjeto);
		if (!locQuery.getResultList().isEmpty()){
			int locCodigoMensaje=23;
			String locCampoMensaje="El tipo de crédito";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}

	}

}
