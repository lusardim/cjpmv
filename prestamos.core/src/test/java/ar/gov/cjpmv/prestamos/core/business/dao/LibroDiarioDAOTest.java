package ar.gov.cjpmv.prestamos.core.business.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.net.URL;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.LibroDiario;

public class LibroDiarioDAOTest {
	
	private EjercicioContableDAO ejercicioContableDAO;
	private LibroDiarioDAO libroDiarioDAO;
	
	public LibroDiarioDAOTest() {
		ejercicioContableDAO = DAOFactory.getDefecto().getEjercicioContableDAO();
		libroDiarioDAO = DAOFactory.getDefecto().getLibroDiarioDAO();
	}
	
	@BeforeClass
	public static void cargarDatosPrueba() throws Exception {
		URL scriptLimpieza = CobroDAOTest.class.getResource("limpieza_liquidacion.sql");
		URL scriptDatosPrueba = CobroDAOTest.class.getResource("lote_prueba_sin_liquidacion.sql");
		URL scriptDatosLibroDiario = CobroDAOTest.class.getResource("libro_diario_con_periodo.sql");
		File archivoScriptLimpieza = new File(scriptLimpieza.toURI());
		File archivoScript = new File(scriptDatosPrueba.toURI());
		File archivoDatosPrueba = new File(scriptDatosLibroDiario.toURI());
		DBUtiles.getInstance().cargarScript(archivoScriptLimpieza);
		DBUtiles.getInstance().cargarScript(archivoScript);
		DBUtiles.getInstance().cargarScript(archivoDatosPrueba);
	}
	
	@AfterClass
	public static void limpiar() throws Exception {
		URL scriptLimpieza = CobroDAOTest.class.getResource("limpieza_liquidacion.sql");
		File archivoScriptLimpieza = new File(scriptLimpieza.toURI());
		DBUtiles.getInstance().cargarScript(archivoScriptLimpieza);
	}
	
	@Test
	public void testGetLibroDiarioPorPeriodoContable() {
		EjercicioContable ejercicioContable = ejercicioContableDAO.getEjercicioContablePorAnio(2011);
		LibroDiario libroDiario = libroDiarioDAO.getLibroDiarioPorPeriodoContable(ejercicioContable);
		assertNull(libroDiario);
		
		crearLibroDiario(ejercicioContable);
		libroDiario = libroDiarioDAO.getLibroDiarioPorPeriodoContable(ejercicioContable);
		assertNotNull(libroDiario);
		assertEquals(libroDiario.getPeriodoContable(), ejercicioContable);
	}

	private LibroDiario crearLibroDiario(EjercicioContable ejercicioContable) {
		EntityManager manager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		try {
			tx.begin();
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setPeriodoContable(ejercicioContable);
			manager.persist(libroDiario);
			tx.commit();
		}
		catch(Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		finally {
			manager.close();
		}
		return null;
	}
}
