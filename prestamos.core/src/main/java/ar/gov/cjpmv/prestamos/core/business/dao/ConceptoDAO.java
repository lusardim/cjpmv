package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;

public class ConceptoDAO extends GenericDAOImpl<Concepto> {

	@Override
	public Concepto getObjetoPorId(Long clave) {
		return this.entityManager.find(Concepto.class, clave);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Concepto> findListaConcepto(String pDescripcion){
		String locConsulta="select t from Concepto t";
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
	protected void validarAlta(Concepto pObjeto) throws LogicaException {
		if(pObjeto.getDescripcion()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="descripcion de concepto";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			if(pObjeto.getValor()==null){
				int locCodigoMensaje=2;
				String locCampoMensaje="valor de concepto";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else{
				String locConsulta="select t from Concepto t where upper(t.descripcion) = :descripcion";
				Query locQuery;
				locQuery= entityManager.createQuery(locConsulta);
				locQuery.setParameter("descripcion",pObjeto.getDescripcion().toUpperCase().trim());
				if (!locQuery.getResultList().isEmpty()){
					int locCodigoMensaje=1;
					String locCampoMensaje="El concepto";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
			}
		}
	}
	

	@Override
	protected void validarModificacion(Concepto pObjeto)throws LogicaException {
		if(pObjeto.getDescripcion()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="descripcion de concepto";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			if(pObjeto.getValor()==null){
				int locCodigoMensaje=2;
				String locCampoMensaje="valor de concepto";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else{			
				String locConsulta="select t from Concepto t where upper(t.descripcion) = :descripcion and t.id != :id";
				Query locQuery;
				locQuery= entityManager.createQuery(locConsulta);
				locQuery.setParameter("descripcion",pObjeto.getDescripcion().toUpperCase().trim());
				locQuery.setParameter("id",pObjeto.getId());
				if (!locQuery.getResultList().isEmpty()){
					int locCodigoMensaje=1;
					String locCampoMensaje="El concepto";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
			}
		}
	}
	
	@Override
	protected void validarEliminar(Concepto pObjeto)throws LogicaException {
	
		String locConsulta="select t from DetalleCredito t where t.fuente= :concepto";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("concepto",pObjeto);
		if (!locQuery.getResultList().isEmpty()){
			int locCodigoMensaje=21;
			String locCampoMensaje="El Concepto";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsulta1="select t from TipoCredito t join t.listaConceptos e where e= :concepto";
			Query locQuery1;
			locQuery1= entityManager.createQuery(locConsulta1);
			locQuery1.setParameter("concepto",pObjeto);
			if (!locQuery1.getResultList().isEmpty()){
				int locCodigoMensaje=22;
				String locCampoMensaje="El Concepto";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}

	public Concepto findRetenciones(String pDescripcion) {
		String consulta="select conc from Concepto conc where upper(conc.descripcion)= :pDescripcion";
		Query locQuery= entityManager.createQuery(consulta);
		locQuery.setParameter("pDescripcion", pDescripcion);
		if(!locQuery.getResultList().isEmpty()){
			return (Concepto) locQuery.getResultList().get(0);
		}
		return null;
	}

}
