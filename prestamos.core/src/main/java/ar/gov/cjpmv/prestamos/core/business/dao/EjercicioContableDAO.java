package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;

public class EjercicioContableDAO extends GenericDAOImpl<EjercicioContable> {

	private static final String CONSULTA_EJERCICIO_POR_ANIO = 
					"select ejer from EjercicioContable ejer " +
					"where year(ejer.fechaInicio) = :anio";
	
	private static final String CONSULTA_EJERCICIO_ACTIVO = 
					"select e from EjercicioContable e " +
					"where e.activo is true";
	
	@Override
	public EjercicioContable getObjetoPorId(Long clave) {
		return getEntityManager().find(EjercicioContable.class, clave);
	}

	@SuppressWarnings("unchecked")
	public List<EjercicioContable> getListaEjercicios(String nombre) {
		List<EjercicioContable> resultado = entityManager.createQuery("from EjercicioContable e")
														 .getResultList();
		if (resultado.isEmpty()) {
			return null;
		}
		
		if (nombre != null) {
			List<EjercicioContable> filtrado = new ArrayList<EjercicioContable>();
			for (EjercicioContable cadaEjercicio : resultado) {
				String anio = cadaEjercicio.getAnio().toString();
				if (anio.startsWith(nombre.trim())) {
					filtrado.add(cadaEjercicio);
				}
			}
			return filtrado;
		}
		return resultado;
	}
	
	public EjercicioContable getEjercicioContablePorAnio(int anio) {
		try {
			Map<String,Object> parametros = new HashMap<String, Object>();
			parametros.put("anio", anio);
			return (EjercicioContable)this.crearQuery(CONSULTA_EJERCICIO_POR_ANIO, parametros)
										  .getSingleResult();
		}
		catch(NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<EjercicioContable> findListaEjerciciosActivos() {
		return this.crearQuery(CONSULTA_EJERCICIO_ACTIVO, null)
				.getResultList();
	}

	@Override
	public void agregar(EjercicioContable pObjeto) throws LogicaException {
		pObjeto.setearDefecto();
		super.agregar(pObjeto);
	}

	@Override
	public void modificar(EjercicioContable pObjeto) throws LogicaException {
		pObjeto.setearDefecto();
		super.modificar(pObjeto);
	}

}
