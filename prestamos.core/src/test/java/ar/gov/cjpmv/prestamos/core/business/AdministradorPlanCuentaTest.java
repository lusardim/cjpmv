package ar.gov.cjpmv.prestamos.core.business;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.dao.EjercicioContableDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PlanCuentaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.enums.TipoCuentaContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.enums.TipoSaldo;

public class AdministradorPlanCuentaTest {
	
	private AdministradorPlanCuenta administradorPlanCuenta;
	
	private PlanCuentaDAO mockPlanCuentaDAO;
	private EjercicioContableDAO mockEjercicioContableDAO;
	
	@Before
	public void configurar() {
		administradorPlanCuenta = new AdministradorPlanCuenta();
		mockPlanCuentaDAO = mock(PlanCuentaDAO.class);
		mockEjercicioContableDAO = mock(EjercicioContableDAO.class);
		administradorPlanCuenta.setPlanCuentaDAO(mockPlanCuentaDAO);
		administradorPlanCuenta.setEjercicioContableDAO(mockEjercicioContableDAO);
	}
	
	@Test
	public void testGetPlanCuentaPorAnioVacio() throws LogicaException {
		//Este anio no tiene plan de cuentas 
		int anio = 2020;
		PlanCuenta planCuenta = administradorPlanCuenta.getPlanCuentaPorAnio(anio);
		assertNull(planCuenta);
	}
	
	@Test
	public void testGetPlanCuentaConEjercicioContable() throws LogicaException {
		int anio = 2020;
		EjercicioContable periodo = new EjercicioContable();
		periodo.setFechaInicio(new GregorianCalendar(anio, Calendar.JANUARY, 1).getTime());
		periodo.setearDefecto();
		when(mockEjercicioContableDAO.getEjercicioContablePorAnio(anio))
			.thenReturn(periodo);
		
		PlanCuenta planCuenta = administradorPlanCuenta.getPlanCuentaPorAnio(anio);
		assertNotNull(planCuenta);
		
		periodo = planCuenta.getPeriodo();
		assertNotNull(periodo);
		//Controlo que tanto la fecha de inicio como la de fin pertenezcan al mismo anio
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(periodo.getFechaInicio());
		assertEquals(anio, calendario.get(Calendar.YEAR));
		calendario.setTime(periodo.getFechaFin());
		assertEquals(anio, calendario.get(Calendar.YEAR));
	}

	@Test
	public void testAgregarPlanCuenta() throws Exception {
		EjercicioContable periodo = new EjercicioContable();
		periodo.setFechaInicio(new GregorianCalendar(2010, Calendar.JANUARY, 1).getTime());
		periodo.setearDefecto();
		
		PlanCuenta planCuenta = new PlanCuenta();
		Cuenta cuenta1 = new Cuenta();
		cuenta1.setNombre("asdasd");
		cuenta1.setCodigo("00");
		cuenta1.setPadre(null);
		cuenta1.setPlanCuenta(planCuenta);
		cuenta1.setTipoCuenta(TipoCuentaContable.ACTIVO);
		cuenta1.setTipoSaldo(TipoSaldo.ACREEDOR);
		
		planCuenta.setPeriodo(periodo);
		
		Cuenta cuenta11 = new Cuenta();
		cuenta11.setNombre("ssss");
		cuenta11.setCodigo("01");
		cuenta11.setPadre(cuenta1);
		cuenta11.setPlanCuenta(planCuenta);
		cuenta11.setTipoCuenta(TipoCuentaContable.ACTIVO);
		cuenta11.setTipoSaldo(TipoSaldo.ACREEDOR);
		
		Cuenta cuenta12 = new Cuenta();
		cuenta12.setCodigo("02");
		cuenta12.setNombre("ddddd");
		cuenta12.setPadre(cuenta1);
		cuenta12.setPlanCuenta(planCuenta);
		cuenta12.setTipoCuenta(TipoCuentaContable.ACTIVO);
		cuenta12.setTipoSaldo(TipoSaldo.ACREEDOR);
		
		cuenta1.getCuentasHijas().add(cuenta11);
		cuenta1.getCuentasHijas().add(cuenta12);
		
		Cuenta cuenta2 = new Cuenta();
		cuenta2.setCodigo("01");
		cuenta2.setNombre("fffff");
		cuenta2.setPadre(null);
		cuenta2.setPlanCuenta(planCuenta);
		cuenta2.setTipoCuenta(TipoCuentaContable.ACTIVO);
		cuenta2.setTipoSaldo(TipoSaldo.ACREEDOR);
		
		administradorPlanCuenta.guardarPlanCuenta(planCuenta);
		verify(mockPlanCuentaDAO).agregar(planCuenta);
	}
	
	@Test(expected=LogicaException.class)
	public void testGetPlanCuentaPorAnioIvalido() throws LogicaException {
		administradorPlanCuenta.getPlanCuentaPorAnio(0);
	}
	
}
