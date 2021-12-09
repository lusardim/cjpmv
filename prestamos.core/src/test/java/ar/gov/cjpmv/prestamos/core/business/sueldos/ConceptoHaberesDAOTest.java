package ar.gov.cjpmv.prestamos.core.business.sueldos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPorcentual;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;

public class ConceptoHaberesDAOTest {
	
	private ConceptoHaberesDAO dao;
	
	public ConceptoHaberesDAOTest() {
		dao = new ConceptoHaberesDAO();
	}
	
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

	@Test
	public void agregarConceptoFijoTest() throws LogicaException {
		ConceptoFijo concepto = new ConceptoFijo();
		concepto.setCodigo(7070);
		concepto.setDescripcion("mandá supercalifrajilisticoespialidoso al 2020 y ganate una chancleta");
		concepto.setSobreescribirValor(false);
		concepto.setTipoCodigo(TipoCodigo.ASIGNACION);
		concepto.setValor(new BigDecimal("100.12"));
		dao.agregar(concepto);
		
		ConceptoFijo conceptoComparar = (ConceptoFijo)dao.getConceptoPorCodigo(7070);
		assertNotNull(conceptoComparar);
		assertEquals(conceptoComparar, concepto);
	}
	
	@Test
	public void agregarConceptoPorcentualTest() throws LogicaException {
		ConceptoPorcentual concepto = new ConceptoPorcentual();
		concepto.setCodigo(2021);
		concepto.setDescripcion("porcentual");
		concepto.setTipoCodigo(TipoCodigo.ASIGNACION);
		concepto.setValor(new BigDecimal("0.25"));
		concepto.setSobreTotalTipo(TipoCodigo.ASIGNACION);
		dao.agregar(concepto);
		
		ConceptoPorcentual conceptoComparar = (ConceptoPorcentual)dao.getConceptoPorCodigo(2021);
		assertNotNull(conceptoComparar);
		assertEquals(conceptoComparar, concepto);
	}	
	
	@Test
	public void testIsEnPlantilla() {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		ConceptoHaberes concepto = entityManager.find(ConceptoHaberes.class, 1l);
		assertTrue(dao.isEnPlantilla(concepto));
		concepto = entityManager.find(ConceptoHaberes.class, 99l);
		assertFalse(dao.isEnPlantilla(concepto));
	}
	
	@Test
	public void testIsEnLiquidacion() {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		ConceptoHaberes concepto = entityManager.find(ConceptoHaberes.class, 1l);
		assertTrue(dao.isEnLiquidacion(concepto));
		concepto = entityManager.find(ConceptoHaberes.class, 99l);
		assertFalse(dao.isEnLiquidacion(concepto));
	}
}
