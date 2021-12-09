package ar.gov.cjpmv.prestamos.core.business.sueldos;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Antiguedad;

public class AntiguedadDAOTest {
	
	private AntiguedadDAO dao;
	
	public AntiguedadDAOTest() {
		dao = new AntiguedadDAO();
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
	public void verificarAntiguedadesTest() {
		List<Antiguedad> antiguedades = dao.getListaAntiguedad();
		assertNotNull(antiguedades);
		assertEquals(6, antiguedades.size());
		Antiguedad[] antiguedadesEquivalentes = getAntiguedades();
		assertArrayEquals(antiguedadesEquivalentes, antiguedades.toArray());
	}

	private Antiguedad[] getAntiguedades() {
		Antiguedad[] antiguedad = new Antiguedad[6]; 
		antiguedad[0] = crearAntiguedad(1, 0, 5, 0.10);
		antiguedad[1] = crearAntiguedad(2, 5, 10, 0.22);
		antiguedad[2] = crearAntiguedad(3, 10, 15, 0.42);
		antiguedad[3] = crearAntiguedad(4, 15, 20, 0.72);
		antiguedad[4] = crearAntiguedad(7, 20, 25, 0.90);
		antiguedad[5] = crearAntiguedad(8, 25, 0, 1.00);
		return antiguedad;
	}

	private Antiguedad crearAntiguedad(long id, int minimo, int maximo, double porcentaje) {
		Antiguedad antiguedad = new Antiguedad();
		antiguedad.setId(id);
		antiguedad.setMinimo(minimo);
		antiguedad.setMaximo(maximo);
		antiguedad.setPorcentaje(new BigDecimal(porcentaje));
		return antiguedad;
	}
}
