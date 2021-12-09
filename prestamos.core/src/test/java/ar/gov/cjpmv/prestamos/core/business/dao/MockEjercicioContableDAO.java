package ar.gov.cjpmv.prestamos.core.business.dao;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;

public class MockEjercicioContableDAO extends EjercicioContableDAO {

	@Override
	public EjercicioContable getObjetoPorId(Long clave) {
		return null; 
	}

	@Override
	public EjercicioContable getEjercicioContablePorAnio(int anio) {
		return null;
	}

	@Override
	public void eliminar(EjercicioContable pObjeto) throws LogicaException {
	}

	@Override
	public void modificar(EjercicioContable pObjeto) throws LogicaException {
	}

	@Override
	public void agregar(EjercicioContable pObjeto) throws LogicaException {
	}
}
