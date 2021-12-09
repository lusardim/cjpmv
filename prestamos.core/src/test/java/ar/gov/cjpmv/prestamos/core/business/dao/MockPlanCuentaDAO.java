package ar.gov.cjpmv.prestamos.core.business.dao;

import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;

public class MockPlanCuentaDAO extends PlanCuentaDAO {
	
	/**
	 * si el anio es 2010 devuelve un plan de cuentas vacío, sinó devuelve null
	 */
	@Override
	public PlanCuenta getPlanCuenta(int anio) {
		System.out.println(anio);
		if (anio == 2010) {
			PlanCuenta plan = new PlanCuenta();
			return plan;
		}
		return null;
	}
}
