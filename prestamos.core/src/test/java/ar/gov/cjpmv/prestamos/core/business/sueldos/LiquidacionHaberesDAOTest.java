package ar.gov.cjpmv.prestamos.core.business.sueldos;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LineaRecibo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LiquidacionHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Plantilla;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ReciboSueldo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class LiquidacionHaberesDAOTest {

	private LiquidacionHaberesDAO liquidacionHaberesDAO;
	private EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
	
	@BeforeClass
	public static void init() throws Exception {
		URL scriptPersona = AntiguedadDAOTest.class.getResource("../dao/lote_prueba_sin_liquidacion.sql");
		DBUtiles.getInstance().cargarScript(new File(scriptPersona.toURI()));
		URL script = AntiguedadDAOTest.class.getResource("sueldos.sql");
		DBUtiles.getInstance().cargarScript(new File(script.toURI()));
	}
	
	@AfterClass
	public static void destroy() throws Exception {
		URL script = AntiguedadDAOTest.class.getResource("limpieza-sueldos.sql");
		DBUtiles.getInstance().cargarScript(new File(script.toURI()));
		script = AntiguedadDAOTest.class.getResource("../dao/limpieza_liquidacion.sql");
		DBUtiles.getInstance().cargarScript(new File(script.toURI()));
	}
	
	@Before
	public void inicializar() {
		this.liquidacionHaberesDAO = new LiquidacionHaberesDAO();
		liquidacionHaberesDAO.getEntityManager().clear();
	}
	
	@Test
	public void getConceptosLiquidacionAnteriorTest() {
		EntityManager em = GestorPersitencia.getInstance().getEntityManager();
		try {
			liquidacionHaberesDAO = new LiquidacionHaberesDAO();
			PersonaFisica persona = em.find(PersonaFisica.class, 4l);
			List<ConceptoHaberes> conceptos = liquidacionHaberesDAO.getConceptosLiquidacionAnterior(1, 2000, persona, TipoLiquidacion.ACTIVO_NORMAL);
			assertNotNull(conceptos);
			assertFalse(conceptos.isEmpty());
			ConceptoHaberes concepto = conceptos.get(0);
			ConceptoHaberes esperado = em.find(ConceptoHaberes.class, 1l);
			assertEquals(esperado, concepto);
			conceptos = liquidacionHaberesDAO.getConceptosLiquidacionAnterior(1, 1700, persona, TipoLiquidacion.ACTIVO_NORMAL);
			assertNull(conceptos);
	
			//Mismos parametros que la primera, diferentes conceptos
			conceptos = liquidacionHaberesDAO.getConceptosLiquidacionAnterior(1, 2000, persona, TipoLiquidacion.ACTIVO_SAC);
			assertNull(conceptos);
		}
		finally {
			em.close();
		}
	}

	@Test
	public void testGuardarLiquidacion() throws LogicaException {
		PersonaFisica persona = entityManager.find(PersonaFisica.class, 4l);
		Plantilla plantilla = entityManager.find(Plantilla.class, 1l);
		LiquidacionHaberes liquidacionHaberes = new LiquidacionHaberes();
		liquidacionHaberes.setMes(1);
		liquidacionHaberes.setAnio(2151);
		liquidacionHaberes.setPlantilla(plantilla);
		liquidacionHaberes.setTipo(TipoLiquidacion.ACTIVO_NORMAL);

		ReciboSueldo recibo = new ReciboSueldo();
		recibo.setPersona(persona);
		liquidacionHaberes.add(recibo);

		LineaRecibo linea1 = new LineaRecibo();
		linea1.setCantidad(1);
		linea1.setConcepto(ConceptoPrefijado.SUELDO_BASICO);
		linea1.setMonto(new BigDecimal("100.00"));
		recibo.add(linea1);

		liquidacionHaberesDAO.agregar(liquidacionHaberes);
		assertNotNull(liquidacionHaberes.getId());

	}
}
