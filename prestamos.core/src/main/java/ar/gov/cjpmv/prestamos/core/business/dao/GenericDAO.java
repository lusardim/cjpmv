package ar.gov.cjpmv.prestamos.core.business.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;

public interface GenericDAO<T> {

	public void agregar(T pObjeto) throws LogicaException;
	public void inicializarDao();
	public void cerrarDao();
	public abstract T getObjetoPorId(Long clave);
	public void eliminar(T pObjeto) throws LogicaException;
	public void modificar(T pObjeto) throws LogicaException;
	public List<? extends T> getLista(String pConsulta, Map<String, Object> pParametros, int pDesde, int pCantidad);
	public List<? extends T> getLista(String pConsulta, Map<String, Object> pParametros);
	public EntityManager getEntityManager();
}
