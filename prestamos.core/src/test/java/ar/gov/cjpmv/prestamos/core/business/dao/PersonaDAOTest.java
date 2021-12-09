package ar.gov.cjpmv.prestamos.core.business.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class PersonaDAOTest {
	
	private PersonaDAO personaDAO;
	
	@BeforeClass
	public static void init() throws Exception {
		URL limpiezaSueldos = PersonaDAOTest.class.getResource("../sueldos/limpieza-sueldos.sql");
		DBUtiles.getInstance().cargarScript(new File(limpiezaSueldos.toURI()));

		URL limpiezaPersonas = PersonaDAOTest.class.getResource("limpieza_liquidacion.sql");
		DBUtiles.getInstance().cargarScript(new File(limpiezaPersonas.toURI()));

		URL scriptPersona = PersonaDAOTest.class.getResource("personas_con_empleos.sql");
		DBUtiles.getInstance().cargarScript(new File(scriptPersona.toURI()));
	}
	
	@AfterClass
	public static void finalizar() throws Exception {
		URL limpiezaSueldos = PersonaDAOTest.class.getResource("../sueldos/limpieza-sueldos.sql");
		DBUtiles.getInstance().cargarScript(new File(limpiezaSueldos.toURI()));

		URL limpiezaPersonas = PersonaDAOTest.class.getResource("limpieza_liquidacion.sql");
		DBUtiles.getInstance().cargarScript(new File(limpiezaPersonas.toURI()));
	}
	
	@Test
	public void findListaPersonasPorTipoLiquidacionTest() {
		EntityManager manager = GestorPersitencia.getInstance().getEntityManager();
		try {
			/*
			 * Trae todas las personas activas que tengan empleo en la caja (id=1) y ningun beneficio
			 */
			personaDAO = new PersonaDAO();
			List<PersonaFisica> personas = personaDAO
					.findListaPersonasPorTipoLiquidacion(TipoLiquidacion.ACTIVO_NORMAL);
			List<PersonaFisica> esperado = new ArrayList<PersonaFisica>();
			esperado.add(manager.find(PersonaFisica.class, 11l));
			
			assertNotNull(personas);
			assertEquals(1, personas.size());
			assertArrayEquals(esperado.toArray(), personas.toArray());
			/*
			 * Trae todas las personas que no tengan beneficios (sin contar pensiones) y que tengan 
			 * algún empleo.
			 */
			personas = personaDAO
					.findListaPersonasPorTipoLiquidacion(TipoLiquidacion.JUBILACION_NORMAL);
			esperado.clear();
			esperado.add(manager.find(PersonaFisica.class, 14l));
			esperado.add(manager.find(PersonaFisica.class, 15l));
			assertNotNull(personas);
			assertEquals(2, personas.size());
			assertArrayEquals(esperado.toArray(), personas.toArray());
			
			/*
			 * Todas las personas que tengan un beneficio = pension, y que 
			 * el causante tenga empleo 
			 */
			personas = personaDAO
					.findListaPersonasPorTipoLiquidacion(TipoLiquidacion.PENSION_NORMAL);
			esperado.clear();
			esperado.add(manager.find(PersonaFisica.class, 10l));
			esperado.add(manager.find(PersonaFisica.class, 14l));
			assertNotNull(personas);
			assertEquals(2, personas.size());
			assertArrayEquals(esperado.toArray(), personas.toArray());
		}
		finally {
			manager.close();
		}
	}
}
