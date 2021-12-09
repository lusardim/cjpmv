package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.sueldos.AntiguedadDAOTest;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;

public class CategoriaTest {


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
	
	@SuppressWarnings("unchecked")
	@Test
	public void getTotalTest() {
		//Obtiene las categorias cargadas por el sueldos.sql
		List<Categoria> categorias = GestorPersitencia.getInstance()
				.getEntityManager()
				.createQuery("from Categoria where tipoPersona = 'ACTIVO'")
				.getResultList();
		BigDecimal[] resultados = new BigDecimal[]{
				new BigDecimal("2535.75"),
				new BigDecimal("2789.33"),
				new BigDecimal("3042.90"),
				new BigDecimal("3296.48"),
				new BigDecimal("3676.84"),
				new BigDecimal("3930.41"),
				new BigDecimal("4310.78"),
				new BigDecimal("4564.35"),
				new BigDecimal("5198.29"),
				new BigDecimal("6085.80"),
				new BigDecimal("7100.10"),
				new BigDecimal("7860.83")};
		
		assertNotNull(categorias);
		assertFalse(categorias.isEmpty());
		assertEquals(12, categorias.size());
		
		List<BigDecimal> totalesObtenidos = new ArrayList<BigDecimal>();
		for (Categoria categoria : categorias) {
			totalesObtenidos.add(categoria.getTotal());
		}
		
		assertArrayEquals(resultados, totalesObtenidos.toArray());
	}
}
