package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;

public class DepartamentoDAO extends GenericDAOImpl<Departamento> {

	@Override
	public Departamento getObjetoPorId(Long clave) {
		return this.entityManager.find(Departamento.class, clave);
	}

	@SuppressWarnings("unchecked")
	public List<Departamento> getListaDepartamentos(){
		return this.entityManager.createQuery("select d from Departamento d").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Departamento> findListaDepartamento(String pNombre){
		
		String locConsulta="select p from Departamento p";
		String locConsultaFin=" order by p.provincia, p.nombre asc";
		
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
	protected void validarAlta(Departamento pObjeto) throws LogicaException {
		if(pObjeto.getProvincia()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="provincia";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			if(pObjeto.getNombre()==null){
				int locCodigoMensaje=2;
				String locCampoMensaje="nombre de departamento";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else{
				String locConsulta="select t from Departamento t where upper(t.nombre) = :nombre and t.provincia = :provincia";
				Query locQuery;
				locQuery= entityManager.createQuery(locConsulta);
				locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
				locQuery.setParameter("provincia",pObjeto.getProvincia());
				if (!locQuery.getResultList().isEmpty()){
					int locCodigoMensaje=1;
					String locCampoMensaje="El departamento";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
			}
		}
	}
	
	@Override
	protected void validarModificacion(Departamento pObjeto)throws LogicaException {
		if(pObjeto.getProvincia()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="provincia";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			if(pObjeto.getNombre()==null){
				int locCodigoMensaje=2;
				String locCampoMensaje="nombre de departamento";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else{
				String locConsulta="select t from Departamento t where upper(t.nombre) = :nombre and t.provincia = :provincia and t.id != :id";
				Query locQuery;
				locQuery= entityManager.createQuery(locConsulta);
				locQuery.setParameter("nombre",pObjeto.getNombre().toUpperCase().trim());
				locQuery.setParameter("provincia",pObjeto.getProvincia());
				locQuery.setParameter("id",pObjeto.getId());
				if (!locQuery.getResultList().isEmpty()){
					int locCodigoMensaje=1;
					String locCampoMensaje="El departamento";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
			}
		}
	}
	
	
	@Override
	protected void validarEliminar(Departamento pObjeto)throws LogicaException {
		
		String locConsulta="select t from Localidad t where t.departamento = :departamento";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("departamento",pObjeto);
		if (!locQuery.getResultList().isEmpty()){
			int locCodigoMensaje=7;
			String locCampoMensaje="El departamento";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsultaDomicilio="select t from Domicilio t where t.departamento = :departamento";
			Query locQueryDomicilio;
			locQueryDomicilio= entityManager.createQuery(locConsultaDomicilio);
			locQueryDomicilio.setParameter("departamento",pObjeto);
			if (!locQueryDomicilio.getResultList().isEmpty()){
				int locCodigoMensaje=6;
				String locCampoMensaje="El departamento";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}

	
}
