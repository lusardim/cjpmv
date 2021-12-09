package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;

public abstract class GenericDAOImpl<T> implements GenericDAO<T>{
	
	protected EntityManager entityManager;
	
	public GenericDAOImpl() {
		this.inicializarDao();
	}
	
	public void inicializarDao() {
		if (this.entityManager!=null){
			this.cerrarDao();
		}
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
	}

	public void cerrarDao() {
		if (this.entityManager!=null){
			if (this.entityManager.isOpen()){
				this.entityManager.close();
			}
			this.entityManager=null;
		}
	}

	
	public abstract T getObjetoPorId(Long clave);
	/**
	 * Elimina un objeto
	 * @param pObjeto
	 * @throws LogicaException
	 */
	public void eliminar(T pObjeto) throws LogicaException{
		EntityTransaction locTransaction = this.entityManager.getTransaction();
		try{
			locTransaction.begin();
			this.validarEliminar(pObjeto);
			this.entityManager.refresh(pObjeto);
			this.entityManager.remove(pObjeto);
			locTransaction.commit();
		}
		catch(LogicaException e){
			if(locTransaction.isActive()){
				locTransaction.rollback();	
			}
			throw e;
		}
		catch(Exception e){
			if(locTransaction.isActive()){
				locTransaction.rollback();	
			}
			this.lanzarExcepcionDesconocida(e);
		}
	}
	
	/**
	 * Modifica un objeto
	 * @param pObjeto
	 * @throws LogicaException
	 */
	public void modificar(T pObjeto) throws LogicaException{
		EntityManager locEntityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction locTransaction = locEntityManager.getTransaction();
		try{
			locTransaction.begin();
			this.validarModificacion(pObjeto);
			locEntityManager.merge(pObjeto);
			locTransaction.commit();
		}
		catch(LogicaException e){
			e.printStackTrace();
			if (locTransaction.isActive()){
				locTransaction.rollback();
			}
			throw e;
		}
		catch(Exception e){
			e.printStackTrace();
			if (locTransaction.isActive()){
				locTransaction.rollback();
			}
			this.lanzarExcepcionDesconocida(e);
		}
	}
	
	/**
	 * Lanza una LogicaException a partir de un throwable de origen.
	 * @param origen
	 * @throws LogicaException
	 */
	protected void lanzarExcepcionDesconocida(Throwable origen) throws LogicaException{
		origen.printStackTrace();
		int locCodigoMensaje=3;
		String locCampoMensaje="ninguno";
		LogicaException ex = new LogicaException(locCodigoMensaje, locCampoMensaje);
		ex.initCause(origen);
		throw ex;
	}

	/**
	 * Agrega el objeto a la base de datos.
	 * @param pObjeto
	 * @throws LogicaException
	 */
	@Override
	public void agregar(T pObjeto) throws LogicaException{
		EntityManager locEntityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction locTransaction = locEntityManager.getTransaction();
		try{
			locTransaction.begin();
			this.validarAlta(pObjeto);
			locEntityManager.merge(pObjeto);
			locTransaction.commit();
		}
		catch(LogicaException e) {
			if (locTransaction != null && locTransaction.isActive()) {
				locTransaction.rollback();
			}
			throw e;
		}
		catch(Exception e) {
			if (locTransaction != null && locTransaction.isActive()) {
				locTransaction.rollback();
			}
			this.lanzarExcepcionDesconocida(e);
		}
	}
	
	
	/**
	 * Permite validar el alta de un objeto persistente, esta es la implementación por defecto, en caso que se quiera
	 * validar algo, simplemente se sobreescribe este método
	 * @param pObjeto
	 * @throws LogicaException
	 */
	protected void validarAlta(T pObjeto) throws LogicaException{
		
	}
	/**
	 * Permite validar la moficiación de un objeto persistente, esta es la implementación por defecto, en caso que se quiera
	 * validar algo, simplemente se sobreescribe este método
	 * @param pObjeto
	 * @throws LogicaException
	 */

	protected void validarModificacion(T pObjeto) throws LogicaException{
		
	}
	
	/**
	 * Permite validar la eliminaciónde un objeto persistente, esta es la implementación por defecto, en caso que se quiera
	 * validar algo, simplemente se sobreescribe este método
	 * @param pObjeto
	 * @throws LogicaException
	 */
	protected void validarEliminar(T pObjeto) throws LogicaException{
		
	}
	
	
	/**
	 * Recupera el conjunto de datos
	 * @param pConsulta
	 * @param pListaParametros
	 * @param pDesde
	 * @param pCantidad si la cantidad es igual a 0 entonces se considera que no tiene límites
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<? extends T> getLista(String pConsulta, Map<String, Object> pListaParametros, int pDesde, int pCantidad){
		Query locQuery = crearQuery(pConsulta, pListaParametros);
		
		if (pCantidad>0){
			locQuery.setFirstResult(pDesde);
			locQuery.setMaxResults(pCantidad);
		}
		
		return locQuery.getResultList();
	}
	
	/**
	 * Crea una consulta de hibernate y le setea los parámetros
	 * @param consulta
	 * @param pParametros
	 * @return
	 */
	protected Query crearQuery(String consulta, Map<String, Object> pParametros) {
		Query query = this.entityManager.createQuery(consulta);
		if(pParametros!= null){
			for (Entry<String, Object> cadaEntrada : pParametros.entrySet()){
	
				Object valor = cadaEntrada.getValue();
				if (valor instanceof Date){
					query.setParameter(cadaEntrada.getKey(), (Date)cadaEntrada.getValue(),TemporalType.DATE);
				}
				else{
					query.setParameter(cadaEntrada.getKey(), cadaEntrada.getValue());
				}
			}
		}
		return query;
	}
	
	public List<? extends T> getLista(String pConsulta, Map<String, Object> pListaParametros){
		return this.getLista(pConsulta, pListaParametros, 0, 4000);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
