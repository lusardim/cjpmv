package ar.gov.cjpmv.prestamos.core.business.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;

public class EjercicioContableDAOTest {
	
	private EjercicioContableDAO dao = new EjercicioContableDAO();
	
	@AfterClass 
	public static void limpiarDatos() throws Exception {
		URL scriptLimpieza = CobroDAOTest.class.getResource("limpieza_liquidacion.sql");
		File archivoScriptLimpieza = new File(scriptLimpieza.toURI());
		DBUtiles.getInstance().cargarScript(archivoScriptLimpieza);
	}
	
	@BeforeClass
	public static void cargarDatosPrueba() throws Exception {
		URL scriptLimpieza = CobroDAOTest.class.getResource("limpieza_liquidacion.sql");
		URL scriptDatosPrueba = CobroDAOTest.class.getResource("lote_prueba_sin_liquidacion.sql");
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
		DBUtiles.getInstance().cargarScript(new File(CobroDAOTest.class.getResource("../sueldos/limpieza-sueldos.sql").toURI()));
		DBUtiles.getInstance().cargarScript(archivoScriptLimpieza);
		DBUtiles.getInstance().cargarScript(archivoScript);
	}

	@Test
	public void testGetEjercicioContablePorAnio() {
		EjercicioContable ejercicio2011 = crearEjercicioContable(2011);
		EjercicioContable ejercicio = dao.getEjercicioContablePorAnio(1990);
		assertNull(ejercicio);
		ejercicio = dao.getEjercicioContablePorAnio(2011);
		assertNotNull(ejercicio);
		assertEquals(ejercicio2011, ejercicio);
	}
	
	@Test
	public void testFindListaEjerciciosActivos() {
		crearEjercicioContable(2011, true);
		EjercicioContable ejercicio2012 = crearEjercicioContable(2012, false);
		List<EjercicioContable> listaEjercicios = dao.findListaEjerciciosActivos();
		assertNotNull(listaEjercicios);
		assertEquals(1, listaEjercicios.size());
		assertFalse(listaEjercicios.contains(ejercicio2012));
	}
	
	@Test
	public void testGetListaEjercicio() throws LogicaException {
		EjercicioContable ejercicio2011 = crearEjercicioContable(2011);
		EjercicioContable ejercicio2012 = crearEjercicioContable(2012);

		EjercicioContableDAO dao = new EjercicioContableDAO();
		List<EjercicioContable> ejercicios = dao.getListaEjercicios("2011");
		assertNotNull(ejercicios);
		assertEquals(1, ejercicios.size());
		assertEquals(ejercicio2011, ejercicios.get(0));
		
		ejercicios = dao.getListaEjercicios("2012");
		assertNotNull(ejercicios);
		assertEquals(1, ejercicios.size());
		assertEquals(ejercicio2012, ejercicios.get(0));
		
		ejercicios = dao.getListaEjercicios("20");
		assertNotNull(ejercicios);
		assertEquals(2, ejercicios.size());
		assertEquals(ejercicio2011, ejercicios.get(0));
		assertEquals(ejercicio2012, ejercicios.get(1));
		
		ejercicios = dao.getListaEjercicios(null);
		assertNotNull(ejercicios);
		assertEquals(2, ejercicios.size());
		assertEquals(ejercicio2011, ejercicios.get(0));
		assertEquals(ejercicio2012, ejercicios.get(1));
	}

	@After
	public void limpiarEjercicios() {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction transaccion = entityManager.getTransaction();
		try {
			transaccion.begin();
			entityManager.createQuery("delete from EjercicioContable").executeUpdate();
			transaccion.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			if (transaccion.isActive()) {
				transaccion.rollback();
			}
			fail("no se pudo realizar la limpieza");
		}
	}
	
	private void guardar(EjercicioContable ejercicio) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction transaccion = entityManager.getTransaction();
		try {
			transaccion.begin();
			entityManager.persist(ejercicio);
			entityManager.flush();
			entityManager.refresh(ejercicio);
			transaccion.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			if (transaccion.isActive()) {
				transaccion.rollback();
			}
			fail("No se pudo agregar el ejercicio "+ejercicio);
		}
	}

	private EjercicioContable crearEjercicioContable(Integer anio) {
		return this.crearEjercicioContable(anio, false);
	}
	
	private EjercicioContable crearEjercicioContable(Integer anio, boolean activo) {
		EjercicioContable ejercicio = new EjercicioContable();
		ejercicio.setAnio(anio);
		ejercicio.setearDefecto();
		ejercicio.setActivo(activo);
		guardar(ejercicio);
		return ejercicio;
	}
}
