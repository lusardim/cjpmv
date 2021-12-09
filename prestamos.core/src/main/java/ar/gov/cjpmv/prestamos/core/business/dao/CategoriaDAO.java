package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.sueldos.MontoAntiguedadPorCategoriaBuilder;
import ar.gov.cjpmv.prestamos.core.business.sueldos.MontoPermanenciaPorCategoriaBuilder;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Antiguedad;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.MontoAntiguedadPorCategoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.MontoPermanenciaPorCategoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PermanenciaCategoria;

public class CategoriaDAO extends GenericDAOImpl<Categoria> {

	@Override
	public Categoria getObjetoPorId(Long clave) {
		return this.entityManager.find(Categoria.class, clave);
	}
	
	@Override
	public void agregar(Categoria pObjeto) throws LogicaException {
		throw new UnsupportedOperationException("REVISAR DAO CATEGORIA");
	}
	
	@Override
	public void eliminar(Categoria pObjeto) throws LogicaException {
		throw new UnsupportedOperationException("REVISAR DAO CATEGORIA");
	}
	
	@Override
	public void modificar(Categoria pObjeto) throws LogicaException {
		throw new UnsupportedOperationException("REVISAR DAO CATEGORIA");
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> findListaCategoria() {
		String locConsulta="select t from Categoria t where t.tipoPersona = :tipoPersona";
		Query locQuery= this.entityManager.createQuery(locConsulta);
		locQuery.setParameter("tipoPersona", EstadoPersonaFisica.ACTIVO);
		List<Categoria> lista = locQuery.getResultList();
		if(lista.isEmpty()){
			return null;
		}
		return lista;
	}
	
	public Categoria getCategoria(PersonaFisica persona) {
		try {
			Query locQuery = entityManager.createQuery("" +
					"select e.tipoCategoria from " +
					" Empleo e where e.empleado = :persona");
			locQuery.setParameter("persona", persona);
			locQuery.setMaxResults(1);
			return (Categoria) locQuery.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Categoria findEquivalenciaCategoria(Categoria tipoCategoria,
			EstadoPersonaFisica estado) {
		
		String consulta= "select c from Categoria c where c.numero=:pNumero and tipoPersona= :pTipoPersona ";
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("pNumero", tipoCategoria.getNumero());
		locQuery.setParameter("pTipoPersona", estado);
		List<Categoria> lista= locQuery.getResultList();
		if(!lista.isEmpty()){
			return lista.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> findCategorias(EstadoPersonaFisica estado) 
	{
		Query consulta = entityManager.createQuery("from Categoria c where c.tipoPersona = :estado");
		consulta.setParameter("estado", estado);
		List<Categoria> categorias = consulta.getResultList();
		for (Categoria categoria : categorias) {
			entityManager.refresh(categoria);
		}
		return categorias;
	}

	public void guardarCategorias(List<Categoria> categorias) throws LogicaException {
		this.entityManager.clear();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			for (Categoria categoria : categorias) {
				entityManager.merge(categoria);
			}
			entityManager.flush();
			entityManager.clear();

			actualizarAntiguedades(categorias);
			actualizarPermanencias(categorias);
			entityManager.createQuery("delete from MontoPermanenciaPorCategoria");
			
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new LogicaException(150);
		}
		finally {
			entityManager.clear();
		}
	}

	@SuppressWarnings("unchecked")
	private void actualizarPermanencias(List<Categoria> categorias) {
		Query consulta = entityManager.createQuery("from MontoPermanenciaPorCategoria where categoria in (:categorias)");
		consulta.setParameter("categorias", categorias);
		List<MontoPermanenciaPorCategoria> montos = consulta.getResultList();
		for (MontoPermanenciaPorCategoria monto : montos) {
			entityManager.remove(monto);
		}
		entityManager.flush();
		
		List<PermanenciaCategoria> permanencias = entityManager
				.createQuery("from PermanenciaCategoria").getResultList();
		
		MontoPermanenciaPorCategoriaBuilder builder = new MontoPermanenciaPorCategoriaBuilder(categorias, permanencias);
		for (MontoPermanenciaPorCategoria monto : builder.getMontos()) {
			entityManager.persist(monto);
		}
		entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	private void actualizarAntiguedades(List<Categoria> categorias) {
		
		Query consulta = entityManager.createQuery("from MontoAntiguedadPorCategoria where categoria in (:categorias)");
		consulta.setParameter("categorias", categorias);
		List<MontoAntiguedadPorCategoria> montos = consulta.getResultList();
		for (MontoAntiguedadPorCategoria monto : montos) {
			entityManager.remove(monto);
		}
		entityManager.flush();
		
		List<Antiguedad> antiguedades = this.entityManager
				.createQuery("from Antiguedad").getResultList();
		
		MontoAntiguedadPorCategoriaBuilder builder = new MontoAntiguedadPorCategoriaBuilder(categorias, antiguedades);
		for (MontoAntiguedadPorCategoria monto : builder.getMontos()) {
			entityManager.persist(monto);
		}
		entityManager.flush();
	}
	
}
