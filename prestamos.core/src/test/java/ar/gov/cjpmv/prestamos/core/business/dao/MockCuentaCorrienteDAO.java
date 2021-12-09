package ar.gov.cjpmv.prestamos.core.business.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class MockCuentaCorrienteDAO implements CuentaCorrienteDAO {
	private Set<CuentaCorriente> cuentas = new HashSet<CuentaCorriente>();
	@Override
	public void agregar(CuentaCorriente pCuenta) throws LogicaException {
		cuentas.add(pCuenta);
		
	}

	@Override
	public void inicializarDao() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cerrarDao() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CuentaCorriente getObjetoPorId(Long clave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(CuentaCorriente pObjeto) throws LogicaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificar(CuentaCorriente pObjeto) throws LogicaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<? extends CuentaCorriente> getLista(String pConsulta,
			Map<String, Object> pListaParametros, int pDesde, int pCantidad) {
		return null;
	}

	@Override
	public List<? extends CuentaCorriente> getLista(String pConsulta,
			Map<String, Object> pListaParametros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityManager getEntityManager() {
		return new MockEntityManager();
	}


	@Override
	public Set<CuentaCorriente> getCuentasCorrientes(Date pVencimientoHasta,
			ViaCobro pViaCobro) {
		return this.cuentas;
	}

	@Override
	public void actualizarSobrante(Long id, BigDecimal bigDecimal)
			throws LogicaException {
		// TODO Auto-generated method stub
		
	}

}
