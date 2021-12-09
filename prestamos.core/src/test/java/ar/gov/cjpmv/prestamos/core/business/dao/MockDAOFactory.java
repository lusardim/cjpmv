package ar.gov.cjpmv.prestamos.core.business.dao;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.sueldos.AntiguedadDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.ConceptoHaberesDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.LiquidacionHaberesDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.PlantillaDAO;

public class MockDAOFactory extends DAOFactory{

	private MockCreditoDAO mockCreditoDAO = new MockCreditoDAO();
	private MockLiquidacionDAO mockLiquidacionDAO = new MockLiquidacionDAO();
	private MockCuentaCorrienteDAO mockCuentaCorrienteDAO = new MockCuentaCorrienteDAO();
	
	@Override
	public CreditoDAO getCreditoDAO() {
		return mockCreditoDAO;
	}

	@Override
	public LiquidacionDAO getLiquidacionDAO() {
		return mockLiquidacionDAO;
	}

	@Override
	public CuentaCorrienteDAO getCuentaCorrienteDAO() {
		return mockCuentaCorrienteDAO;
	}

	@Override
	public PersonaDAO getPersonaDAO() {
		throw new IllegalAccessError("todavía no hecho");
	}

	@Override
	public EjercicioContableDAO getEjercicioContableDAO() {
		return new MockEjercicioContableDAO();
	}

	@Override
	public PlanCuentaDAO getPlanCuentaDAO() {
		return new MockPlanCuentaDAO();
	}

	@Override
	public AsientoDAO getAsientoDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LibroDiarioDAO getLibroDiarioDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsientoModeloDAO getAsientoModeloDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConceptoHaberesDAO getConceptoHaberesDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlantillaDAO getPlantillaDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LiquidacionHaberesDAO getLiquidacionHaberesDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoriaDAO getCategoriaDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AntiguedadDAO getAntiguedadDAO() {
		// TODO Auto-generated method stub
		return null;
	}
}
