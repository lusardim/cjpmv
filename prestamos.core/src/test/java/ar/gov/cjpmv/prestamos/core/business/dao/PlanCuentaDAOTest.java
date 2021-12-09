package ar.gov.cjpmv.prestamos.core.business.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.enums.TipoCuentaContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.enums.TipoSaldo;
import ar.gov.cjpmv.prestamos.core.utiles.UtilFecha;

public class PlanCuentaDAOTest {
	
	private PlanCuentaDAO planCuentaDAO;
	private EjercicioContableDAO ejercicioContableDAO;
	
	public PlanCuentaDAOTest() {
		planCuentaDAO = DAOFactory.getDefecto().getPlanCuentaDAO();
		ejercicioContableDAO = DAOFactory.getDefecto().getEjercicioContableDAO();
	}

	@BeforeClass
	public static void crearPlanCuentaPrueba() {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			Calendar calendario = Calendar.getInstance();
			calendario.set(2011, Calendar.JANUARY, 1);
			EjercicioContable ejercicioContable = new EjercicioContable();
			ejercicioContable.setFechaInicio(calendario.getTime());
			ejercicioContable.setActivo(true);
			ejercicioContable.setearDefecto();
			entityManager.persist(ejercicioContable);			
			
			PlanCuenta planCuentaPrueba = new PlanCuenta();
			planCuentaPrueba.setCerrado(false);
			planCuentaPrueba.setPeriodo(ejercicioContable);
			planCuentaPrueba.add(crearCuentas(planCuentaPrueba,((Cuenta)null),3));
			
			entityManager.persist(planCuentaPrueba);
			tx.commit();
		}
		catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			entityManager.close();
		}
	}
	
	private static Cuenta crearCuentas(
			PlanCuenta planCuenta, Cuenta cuentaPadre, int niveles) {
		if (niveles > 0) {
			Cuenta cuenta = new Cuenta();
			cuenta.setCodigo("0");
			cuenta.setNombre("Cuenta "+niveles);
			cuenta.setPlanCuenta(planCuenta);
			cuenta.setPadre(cuentaPadre);
			cuenta.setTipoCuenta(TipoCuentaContable.ACTIVO);
			cuenta.setTipoSaldo(TipoSaldo.ACREEDOR);
			cuenta.getCuentasHijas().add(crearCuentas(planCuenta, cuenta, --niveles));
			return cuenta;
		}
		return null;
	}

	@AfterClass
	public static void eliminarPlanCuenta() {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			PlanCuenta planCuentaPrueba = (PlanCuenta)entityManager
						.createQuery("select p from PlanCuenta p " +
									 "where year(p.periodo.fechaInicio) = 2011")
						.getSingleResult();
			entityManager.remove(planCuentaPrueba);
			entityManager.remove(planCuentaPrueba.getPeriodo());
			tx.commit();
		}
		catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
		finally {
			entityManager.close();
		}	
	}
	
	@Test
	public void testGetPlanCuentaAnio() {
		int anio = 2011;
		Calendar calendario = Calendar.getInstance();
		calendario.set(anio,Calendar.JANUARY,1);
		PlanCuenta planCuenta = planCuentaDAO.getPlanCuenta(anio);
		assertNotNull(planCuenta);
		EjercicioContable ejercicio = planCuenta.getPeriodo();
		assertTrue(UtilFecha.comparaDia(calendario.getTime(), ejercicio.getFechaInicio()));
		Cuenta cuenta = planCuenta.getListaCuenta().get(0);
		assertNotNull(cuenta);
		assertFalse(cuenta.getCuentasHijas().isEmpty());
	}
	
	@Test
	public void testAgregarPlanCuenta() throws Exception {
		int anio = 2000;
		Calendar calendario = Calendar.getInstance();
		calendario.set(anio, Calendar.JANUARY,1);

		EjercicioContable ejercicioContable = new EjercicioContable();
		ejercicioContable.setActivo(true);
		ejercicioContable.setFechaInicio(calendario.getTime());
		
		ejercicioContableDAO.agregar(ejercicioContable);
		ejercicioContable = ejercicioContableDAO.getEjercicioContablePorAnio(2000);
		assertNotNull(ejercicioContable);
		
		PlanCuenta planCuenta = planCuentaDAO.getPlanCuenta(anio);
		assertNull(planCuenta);
		
		planCuenta = new PlanCuenta();
		planCuenta.setPeriodo(ejercicioContable);
		Cuenta cuenta1 = crearCuentas(planCuenta, null, 4);
		cuenta1.setNombre("1");
		Cuenta cuenta2 = crearCuentas(planCuenta, null, 2);
		cuenta2.setNombre("2");
		planCuenta.add(cuenta1);
		planCuenta.add(cuenta2);
		
		planCuentaDAO.agregar(planCuenta);
		planCuenta = planCuentaDAO.getPlanCuenta(anio);
		assertNotNull(planCuenta);
		assertNotNull(planCuenta.getPeriodo());
		assertEquals(planCuenta.getPeriodo(), ejercicioContable);
		assertNotNull(planCuenta.getListaCuenta());
		assertFalse(planCuenta.getListaCuenta().isEmpty());
		assertEquals(2, planCuenta.getListaCuenta().size());
	}
}
