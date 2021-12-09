package ar.gov.cjpmv.prestamos.core.business.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class CuentaCorrienteDAOTest {

	private EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager(); 
	private CuentaCorrienteDAO cuentaCorrienteDAO = new CuentaCorrienteDAOImpl();
	
	@BeforeClass
	public static void cargarDatosPrueba() throws Exception {
		URL scriptLimpieza = LiquidacionDAOTest.class.getResource("limpieza_liquidacion.sql");
		URL scriptDatosPrueba = LiquidacionDAOTest.class.getResource("lote_prueba_sin_liquidacion.sql");
		/*
		 *El script pueba_liquidacion.sql genera la siguiente estructura
		 *via cobro
		 * 1 y 2
		 * 
		 *persona 1 - cta cte 1
		 *	-credito 1 de 1000 $ 10 cuotas iguales via cobro 1
		 *  -credito 2 de 2000 $ 10 cuotas iguales via cobro 1
		 *persona 2 - cta cte 2
		 *  -credito 3 1000$ 10 cuotas via cobro 1
		 *  -credito 3 1000$ 10 cuotas via cobro 2
		 */
		File archivoScriptLimpieza = new File(scriptLimpieza.toURI());
		File archivoScript = new File(scriptDatosPrueba.toURI());
		DBUtiles.getInstance().cargarScript(archivoScriptLimpieza);
		DBUtiles.getInstance().cargarScript(archivoScript);
	}

	@AfterClass
	public static void limpiarDespues() throws Exception {
		URL scriptLimpieza = LiquidacionDAOTest.class.getResource("limpieza_liquidacion.sql");
		File archivoScriptLimpieza = new File(scriptLimpieza.toURI());
		DBUtiles.getInstance().cargarScript(archivoScriptLimpieza);
	}
	
	
	private void borrarCreditos(Long... ids) throws Exception {
		EntityTransaction tx = this.entityManager.getTransaction();
		try {
			tx.begin();
			for (Long id : ids) {
				Credito credito = entityManager.getReference(Credito.class, id);
				entityManager.remove(credito);
			}
			tx.commit();
		}
		catch(Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
	}
	
	private GarantiaPersonal generarGarantia(
			long id,
			Credito pCredito, 
			PersonaFisica garante,
			ViaCobro viaCobro,
			CuentaCorriente cuentaCorriente) 
	{
		GarantiaPersonal garantiaPersonal = new GarantiaPersonal();
		garantiaPersonal.setId(id);
		garantiaPersonal.setAfectar(true);
		garantiaPersonal.setCredito(pCredito);
		garantiaPersonal.setGarante(garante);
		garantiaPersonal.setPorcentaje(new BigDecimal(100));
		garantiaPersonal.setViaCobro(viaCobro);
		garantiaPersonal.setAfectarA(cuentaCorriente);
		return garantiaPersonal;
	}
	
	@Test
	public void testGetCuentasCorrientesSinGarantias() {
		//Los créditos comienzan con vencimiento 01/02/2011 
		Calendar calendario = Calendar.getInstance();
		calendario.set(2011,4,1);
		Date vencimientoHasta = calendario.getTime();
		ViaCobro viaCobro = this.entityManager.find(ViaCobro.class, 1l);
		
		List<CuentaCorriente> esperado = new ArrayList<CuentaCorriente>();
		esperado.add(this.entityManager.find(CuentaCorriente.class, 1l));
		esperado.add(this.entityManager.find(CuentaCorriente.class, 2l));
		//Que traiga todas las cuentas corrientes
		Set<CuentaCorriente> listaCuentasCorrientes = cuentaCorrienteDAO
			.getCuentasCorrientes(vencimientoHasta, viaCobro);
		assertEquals(2,listaCuentasCorrientes.size());
		assertArrayEquals(esperado.toArray(), listaCuentasCorrientes.toArray());
		
		//busco con otra vía de cobro
		ViaCobro viaCobroCredito4 = this.entityManager.find(ViaCobro.class,2l);
		CuentaCorriente cuentaCorrienteEsperada = this.entityManager.find(CuentaCorriente.class,2l);
		listaCuentasCorrientes = cuentaCorrienteDAO.getCuentasCorrientes(vencimientoHasta, viaCobroCredito4);
		assertEquals(1,listaCuentasCorrientes.size());
		assertEquals(cuentaCorrienteEsperada, listaCuentasCorrientes.iterator().next());

		//pruebo con una fecha que no tenga liquidaciones
		calendario.set(1999,1,1);
		listaCuentasCorrientes = cuentaCorrienteDAO.getCuentasCorrientes(calendario.getTime(), 
					viaCobro);
		assertTrue(listaCuentasCorrientes.isEmpty());	
	}
	
	@Test
	public void testGetCuentasCorrientesConGarantias() throws Exception {
		CuentaCorriente cuentaCorriente = this.entityManager.find(CuentaCorriente.class, 1l);
		Credito credito3 = this.entityManager.find(Credito.class, 3l);
		Credito credito4 = this.entityManager.find(Credito.class, 4l);
		PersonaFisica garante = this.entityManager.find(PersonaFisica.class, 4l);
		ViaCobro viaCobro = this.entityManager.find(ViaCobro.class, 1l);

		//creo 2 garantias y las guardo
		GarantiaPersonal garantia1 = generarGarantia(1l, credito3,
					garante, viaCobro, cuentaCorriente);
		GarantiaPersonal garantia2 = generarGarantia(2l, credito4,
				garante, viaCobro, cuentaCorriente);
		guardar(garantia1,garantia2);
		
		//Ahora tengo que hacer que los 2 créditos sean afectados
		credito3.setCobrarAGarante(true);
		credito3.setListaGarantias(new ArrayList<Garantia>());
		credito3.getListaGarantias().add(garantia1);
		credito4.setCobrarAGarante(true);
		credito4.setListaGarantias(new ArrayList<Garantia>());
		credito4.getListaGarantias().add(garantia2);
		guardar(credito3,credito4);
		
		//Ahora solo debería tener 1 cuenta corriente
		Calendar calendario = Calendar.getInstance();
		calendario.set(2011,4,1);
		Date pVencimientoHasta = calendario.getTime();
		Set<CuentaCorriente> listaCuentasCorrientes = this.cuentaCorrienteDAO
				.getCuentasCorrientes(pVencimientoHasta, viaCobro);
		assertEquals(1,listaCuentasCorrientes.size());
		assertEquals(cuentaCorriente, listaCuentasCorrientes.iterator().next());
		
		//Verifico que siga trayendo los mismo con las garantías únicamente
		borrarCreditos(1l,2l);
		listaCuentasCorrientes = this.cuentaCorrienteDAO
					.getCuentasCorrientes(pVencimientoHasta, viaCobro);
		assertEquals(1,listaCuentasCorrientes.size());
		assertEquals(cuentaCorriente, listaCuentasCorrientes.iterator().next());	
	}

	private void guardar(Object... objetos) throws Exception {
		EntityTransaction tx = this.entityManager.getTransaction();
		try {
			tx.begin();
			for (Object cadaObj : objetos) {
				entityManager.merge(cadaObj);
			}
			tx.commit();
		}
		catch(Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
	}
}
