package ar.gov.cjpmv.prestamos.core.business.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;


public class LiquidacionDAOTest {
	
	private LiquidacionDAO liquidacionDAO = new LiquidacionDAOImpl();
	private EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager(); 
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
	
	@Test
	public void testAgregar() throws LogicaException {
		ViaCobro viaPrueba = getViaCobro(1l);
		assertNotNull(viaPrueba);
		
		Calendar calendario = Calendar.getInstance();
		Date fecha = calendario.getTime();
		long id = 1l;
		
		Liquidacion liquidacion = crearLiquidacion(fecha, 
				fecha, viaPrueba);
		liquidacion.setId(id);
		
		liquidacionDAO.agregar(liquidacion);
		Liquidacion obtenida = liquidacionDAO.getObjetoPorId(id);
		
		Assert.assertNotNull(obtenida);
		Assert.assertEquals(liquidacion, obtenida);
	}
		
	@Test
	public void testIsPeriodoLiquidado() throws LogicaException {
		//Coloco la fecha de liquidacion
		Calendar calendario = Calendar.getInstance();
		calendario.set(2011, Calendar.JANUARY, 1);
		Date fechaLiquidacion = calendario.getTime();
		//obtengo la via de cobro
		ViaCobro viaCobro = getViaCobro(1l);
		
		Liquidacion liquidacion = new Liquidacion();
		liquidacion.setFechaGeneracion(fechaLiquidacion);
		liquidacion.setFechaLiquidacion(fechaLiquidacion);
		liquidacion.setViaCobro(viaCobro);
		liquidacion.setId(1l);
		//preparo los detalles de liquidacion
		Cuota cuota1Persona1 = entityManager.getReference(Cuota.class, 1l);
		Cuota cuota2Persona1 = entityManager.getReference(Cuota.class, 11l);
		Cuota cuota1Persona2 = entityManager.getReference(Cuota.class, 21l);
		DetalleLiquidacion detallePersona1 = generarDetalle(liquidacion, cuota1Persona1, cuota2Persona1);
		DetalleLiquidacion detallePersona2 = generarDetalle(liquidacion, cuota1Persona2);
		
		List<DetalleLiquidacion> detalleLiquidacion = new ArrayList<DetalleLiquidacion>();
		detalleLiquidacion.add(detallePersona1);
		detalleLiquidacion.add(detallePersona2);
		liquidacion.setListaDetalleLiquidacion(detalleLiquidacion);
		liquidacionDAO.agregar(liquidacion);
		
		Liquidacion liquidacionReferencia = entityManager.find(Liquidacion.class, 1l);
		assertNotNull(liquidacionReferencia);
		assertEquals(liquidacion, liquidacionReferencia);
	
		//Pruebo que devuelva true cuando corresponde
		assertTrue(liquidacionDAO.isPeriodoLiquidado(fechaLiquidacion, viaCobro));
		
		ViaCobro viaCobroInvalida = getViaCobro(2l);
		calendario.add(Calendar.MONTH,1);
		Date fechaInvalida = calendario.getTime();
		
		assertFalse(liquidacionDAO.isPeriodoLiquidado(fechaInvalida, viaCobro));
		assertFalse(liquidacionDAO.isPeriodoLiquidado(fechaLiquidacion, viaCobroInvalida));
		assertFalse(liquidacionDAO.isPeriodoLiquidado(fechaInvalida, viaCobroInvalida));
	}

	private DetalleLiquidacion generarDetalle(Liquidacion liquidacion, Cuota... cuotas) {
		CuentaCorriente ctacteAfectada = cuotas[0].getCredito().getCuentaCorriente();
		BigDecimal monto = new BigDecimal(0f);
		for (Cuota cuota: cuotas) {
			monto = monto.add(cuota.getTotal());
		}
		DetalleLiquidacion detalle = new DetalleLiquidacion();
		detalle.setCuentaCorrienteAfectada(ctacteAfectada);
		detalle.setLiquidacion(liquidacion);
		detalle.setMonto(monto);
		List<Cuota> listaCuotas = Arrays.asList(cuotas);
		detalle.setListaCuotas(listaCuotas);
		return detalle;
	}

	private ViaCobro getViaCobro(long id) {
		return entityManager.getReference(ViaCobro.class,id);
	}

	private Liquidacion crearLiquidacion(Date pFechaGeneracion, Date pFechaLiquidacion,
			ViaCobro pViaCobro) {
		Liquidacion liquidacion = new Liquidacion();
		liquidacion.setFechaGeneracion(pFechaGeneracion);
		liquidacion.setFechaLiquidacion(pFechaLiquidacion);
		liquidacion.setViaCobro(pViaCobro);
		return liquidacion;
	}
	
	@Test
	public void testGetLiquidacionesAnteriores() throws Exception {
		
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
		ViaCobro viaCobro = getViaCobro(1l);
		Calendar calendario = Calendar.getInstance();
		calendario.set(2010, Calendar.FEBRUARY, 5);
		Date fechaLiquidacion1 = calendario.getTime();
		
		Liquidacion liquidacion1 = crearLiquidacion(
				fechaLiquidacion1, 
				fechaLiquidacion1,
				viaCobro);
		liquidacion1.setId(1l);

		Cuota cuota1Persona1 = entityManager.getReference(Cuota.class, 1l);
		Cuota cuota2Persona1 = entityManager.getReference(Cuota.class, 11l);
		Cuota cuota1Persona2 = entityManager.getReference(Cuota.class, 21l);
		Cuota cuota2Persona2 = entityManager.getReference(Cuota.class, 31l);
		DetalleLiquidacion detalle1 = generarDetalle(liquidacion1,cuota1Persona1,cuota2Persona1);
		DetalleLiquidacion detalle2 = generarDetalle(liquidacion1,cuota1Persona1,cuota2Persona1);
		
		liquidacion1.addDetalle(detalle1);
		liquidacion1.addDetalle(detalle2);
		liquidacionDAO.agregar(liquidacion1);
		
		//liquidacionDAO.getLiquidacionesAnteriores(pFechaLiquidar, cantidadMeses)
		
	}
	
	/*
	getLiquidacionPorPeriodo(Integer, Integer, ViaCobro)
	getLiquidacionesAnteriores(Date, int)
	isPeriodoLiquidado(Date, ViaCobro)
	getNumeroUltimaLiquidacion(int, int)
	eliminarDetalle(Long)*/
	
}
