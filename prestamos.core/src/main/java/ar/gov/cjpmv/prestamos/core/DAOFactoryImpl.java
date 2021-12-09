package ar.gov.cjpmv.prestamos.core;

import ar.gov.cjpmv.prestamos.core.business.dao.AsientoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.AsientoModeloDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CategoriaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.dao.CuentaCorrienteDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CuentaCorrienteDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.dao.EjercicioContableDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.LibroDiarioDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.LiquidacionDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.LiquidacionDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PlanCuentaDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.AntiguedadDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.ConceptoHaberesDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.LiquidacionHaberesDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.PlantillaDAO;


public class DAOFactoryImpl extends DAOFactory {

	private CreditoDAO creditoDAO;
	private LiquidacionDAO liquidacionDAO;
	private CuentaCorrienteDAO cuentaCorrienteDAO;
	private PersonaDAO personaDAO;
	private EjercicioContableDAO ejercicioContableDAO;
	private PlanCuentaDAO planCuentaDAO;
	private AsientoDAO asientoDAO;
	private LibroDiarioDAO libroDiarioDAO;
	private AsientoModeloDAO asientoModeloDAO;
	private ConceptoHaberesDAO conceptoHaberesDAO;
	private PlantillaDAO plantillaDAO;
	private LiquidacionHaberesDAO liquidacionHaberesDAO;
	private CategoriaDAO categoriaDAO;
	private AntiguedadDAO antiguedadDAO;
	
	public DAOFactoryImpl() {
		creditoDAO = new CreditoDAOImpl();
		liquidacionDAO = new LiquidacionDAOImpl();
		cuentaCorrienteDAO = new CuentaCorrienteDAOImpl();
		personaDAO = new PersonaDAO();
		ejercicioContableDAO= new EjercicioContableDAO();
		planCuentaDAO = new PlanCuentaDAO();
		asientoDAO= new AsientoDAO();
		libroDiarioDAO= new LibroDiarioDAO();
		asientoModeloDAO= new AsientoModeloDAO();
		conceptoHaberesDAO = new ConceptoHaberesDAO();
		plantillaDAO = new PlantillaDAO();
		liquidacionHaberesDAO = new LiquidacionHaberesDAO();
		categoriaDAO = new CategoriaDAO();
		antiguedadDAO = new AntiguedadDAO();
	}

	@Override
	public CreditoDAO getCreditoDAO() {
		return creditoDAO;
	}

	@Override
	public LiquidacionDAO getLiquidacionDAO() {
		return liquidacionDAO;
	}

	@Override
	public CuentaCorrienteDAO getCuentaCorrienteDAO() {
		return cuentaCorrienteDAO;
	}

	@Override
	public PersonaDAO getPersonaDAO() {
		return personaDAO;
	}
	
	@Override
	public EjercicioContableDAO getEjercicioContableDAO() {
		return ejercicioContableDAO;
	}

	@Override
	public PlanCuentaDAO getPlanCuentaDAO() {
		return planCuentaDAO;
	}
	
	@Override
	public LiquidacionHaberesDAO getLiquidacionHaberesDAO() {
		return liquidacionHaberesDAO;
	}

	@Override
	public AsientoDAO getAsientoDAO() {
		return asientoDAO;
	}

	@Override
	public LibroDiarioDAO getLibroDiarioDAO() {
		return libroDiarioDAO;
	}
	
	@Override
	public AsientoModeloDAO getAsientoModeloDAO() {
		return asientoModeloDAO;
	}

	@Override
	public ConceptoHaberesDAO getConceptoHaberesDAO() {
		return conceptoHaberesDAO;
	}

	public PlantillaDAO getPlantillaDAO() {
		return plantillaDAO;
	}

	@Override
	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	@Override
	public AntiguedadDAO getAntiguedadDAO() {
		return antiguedadDAO;
	}

}
