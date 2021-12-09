package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Banco;

public class EntidadBancariaDAO extends GenericDAOImpl<Banco> {

	@Override
	public Banco getObjetoPorId(Long clave) {
		return this.entityManager.find(Banco.class, clave);
	}
	
	@SuppressWarnings("unchecked")
	public List<Banco> findListaBanco(String pNombre){
		String locConsulta="select t from Banco t";
		String locConsultaFin=" order by t.nombre asc";
		Query locQuery;
		if(pNombre==null){
			locQuery= entityManager.createQuery(locConsulta+locConsultaFin);
		}
		else{
			locConsulta+=" where upper(t.nombre) like :nombre "+locConsultaFin;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("nombre", pNombre.toUpperCase().trim()+"%");
		}
		return locQuery.getResultList();
	}
	

	
	@Override
	protected void validarAlta(Banco pObjeto) throws LogicaException {
		if(pObjeto.getNombre()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="nombre de entidad bancaria";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsulta="select t from Banco t where upper(t.nombre) = :nombre";
			Query locQuery;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
			if (!locQuery.getResultList().isEmpty()){
				int locCodigoMensaje=1;
				String locCampoMensaje="La entidad bancaria";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}
	
	@Override
	protected void validarModificacion(Banco pObjeto)throws LogicaException {
		if(pObjeto.getNombre()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="nombre de entidad bancaria";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsulta="select t from Banco t where upper(t.nombre) = :nombre and t.id != :id";
			Query locQuery;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
			locQuery.setParameter("id",pObjeto.getId());
			if (!locQuery.getResultList().isEmpty()){
				int locCodigoMensaje=1;
				String locCampoMensaje="La entidad bancaria";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}
	
	@Override
	protected void validarEliminar(Banco pObjeto)throws LogicaException {
	
		String locConsulta="select t from CuentaBancaria t where t.banco= :banco";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("banco",pObjeto);
		if (!locQuery.getResultList().isEmpty()){
			int locCodigoMensaje=18;
			String locCampoMensaje="La entidad bancaria";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Banco> getListaBancos() {
		return this.entityManager.createQuery("select p from Banco p").getResultList();
	}
	


}
