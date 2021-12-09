package ar.gov.cjpmv.prestamos.core;

import ar.gov.cjpmv.prestamos.core.business.dao.AsientoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.AsientoModeloDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CategoriaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CuentaCorrienteDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.EjercicioContableDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.LibroDiarioDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.LiquidacionDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PlanCuentaDAO;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.AntiguedadDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.ConceptoHaberesDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.LiquidacionHaberesDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.PlantillaDAO;


public abstract class DAOFactory {
	
	private static DAOFactory defecto = new DAOFactoryImpl();

	public static DAOFactory getDefecto() {
		return defecto;
	}

	public static void setDefecto(DAOFactory defecto) {
		DAOFactory.defecto = defecto;
	}
	
	public abstract CreditoDAO getCreditoDAO();
	public abstract LiquidacionDAO getLiquidacionDAO();
	public abstract CuentaCorrienteDAO getCuentaCorrienteDAO();
	public abstract PersonaDAO getPersonaDAO();
	public abstract EjercicioContableDAO getEjercicioContableDAO();
	public abstract PlanCuentaDAO getPlanCuentaDAO();
	public abstract AsientoDAO getAsientoDAO();
	public abstract LibroDiarioDAO getLibroDiarioDAO();
	public abstract AsientoModeloDAO getAsientoModeloDAO();
	public abstract ConceptoHaberesDAO getConceptoHaberesDAO();
	public abstract PlantillaDAO getPlantillaDAO();
	public abstract LiquidacionHaberesDAO getLiquidacionHaberesDAO();
	public abstract CategoriaDAO getCategoriaDAO();
	public abstract AntiguedadDAO getAntiguedadDAO();
	
	public CobroDAO getCobroDAO() {
		return new CobroDAO();
	}
	
}
