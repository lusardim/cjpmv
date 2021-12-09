package ar.gov.cjpmv.prestamos.core.business.sueldos;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PermanenciaCategoria;

public class PermanenciaCategoriaDAOTest {
	
	private PermanenciaCategoriaDAO dao;
	
	public PermanenciaCategoriaDAOTest() {
		dao = new PermanenciaCategoriaDAO();
	}
	
	@BeforeClass
	public static void init() throws Exception {
		URL script = AntiguedadDAOTest.class.getResource("limpieza-sueldos.sql");
		DBUtiles.getInstance().cargarScript(new File(script.toURI()));
		script = AntiguedadDAOTest.class.getResource("../dao/limpieza_liquidacion.sql");
		DBUtiles.getInstance().cargarScript(new File(script.toURI()));
		
		URL scriptPersona = AntiguedadDAOTest.class.getResource("../dao/lote_prueba_sin_liquidacion.sql");
		DBUtiles.getInstance().cargarScript(new File(scriptPersona.toURI()));
		script = AntiguedadDAOTest.class.getResource("sueldos.sql");
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
	public void verficarPermanenciasTest() {
		List<PermanenciaCategoria> lista = dao.getListaPermanencia();
		assertNotNull(lista);
		assertEquals(4, lista.size());
		PermanenciaCategoria[] arrayVerificar = getPermanencias();
		PermanenciaCategoria[] arrayObtenido = lista.toArray(new PermanenciaCategoria[0]);
		assertArrayEquals(arrayVerificar, arrayObtenido);
	}
	
	private PermanenciaCategoria[] getPermanencias() {
		PermanenciaCategoria[] permanencias = new PermanenciaCategoria[4];
		permanencias[0] = crearPermanencia(1, 2, 4, 0.10);
		permanencias[1] = crearPermanencia(2, 4, 6, 0.25);
		permanencias[2] = crearPermanencia(3, 6, 8, 0.45);
		permanencias[3] = crearPermanencia(4, 8, 0, 0.70);
		return permanencias;
	}

	private PermanenciaCategoria crearPermanencia(long id, int minimo, int maximo, double porcentaje) {
		PermanenciaCategoria permanencia = new PermanenciaCategoria();
		permanencia.setId(id);
		permanencia.setMinimo(minimo);
		permanencia.setMaximo(maximo);
		permanencia.setPorcentaje(new BigDecimal(porcentaje));
		return permanencia;
	}

}
