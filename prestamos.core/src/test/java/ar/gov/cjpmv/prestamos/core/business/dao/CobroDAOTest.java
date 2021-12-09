package ar.gov.cjpmv.prestamos.core.business.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.DAOFactoryImpl;
import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroDAO;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroDetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;


public class CobroDAOTest {
	
	private CobroDAO cobroDAO;
	private ViaCobro viaCobro;
	private EntityManager entityManager;
	
	public CobroDAOTest() {
		cobroDAO =  DAOFactory.getDefecto().getCobroDAO();	
		entityManager = GestorPersitencia.getInstance().getEntityManager();
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
	public void testCobrarCuentaCorrienteMonto() {
		//FIXME HACER ALGO ACA
		//public void cobrar(CuentaCorriente pCuentaCorriente, BigDecimal montoRecibido) throws LogicaException {	
	}

	/*
	 *FIXME HACER BIEN ESTO 
	 */
	@Test
	@Ignore
	public void testCobrarLiquidacion() throws Exception {
		viaCobro = entityManager.find(ViaCobro.class, 1l);
		Liquidacion liquidacionPrueba = crearLiquidacionPrueba();
		Long id = guardarLiquidacion(liquidacionPrueba);
		liquidacionPrueba.setId(id);
		Liquidacion liq = entityManager.find(Liquidacion.class, id);
		
		this.cobroDAO.cobrar(liq);
		assertNotNull(liq);
		for (DetalleLiquidacion cadaDetalle : liq.getListaDetalleLiquidacion()) {
			for (Cuota cadaCuota : cadaDetalle.getListaCuotas()) {
				assertNotNull("la cuota "+cadaCuota.getId()+" no fué cancelada ",
						cadaCuota.getCancelacion());
			}
		}
	}
	
	@Test
	public void testCobarPorLiquidacion() throws Exception {
		viaCobro = entityManager.find(ViaCobro.class, 1l);
		Liquidacion liquidacionPrueba = crearLiquidacionPrueba();
		Long id = guardarLiquidacion(liquidacionPrueba);
		liquidacionPrueba.setId(id);
		
		Liquidacion obtenida = this.entityManager.find(Liquidacion.class, id);
		assertNotNull(obtenida);
		assertEquals(liquidacionPrueba,obtenida);
		assertEquals(2, obtenida.getListaDetalleLiquidacion().size());
		
		CobroLiquidacion cobro = new CobroLiquidacion();
		Date fechaCobro = Calendar.getInstance().getTime();
		cobro.setFechaIngreso(fechaCobro);
		cobro.setLiquidacion(obtenida);
		
		CuentaCorriente cuentaCorriente1 = this.entityManager.find(CuentaCorriente.class, 1l);
		CobroDetalleLiquidacion cobroDetalle = new CobroDetalleLiquidacion();
		cobroDetalle.setFecha(fechaCobro);
		cobroDetalle.setCobroLiquidacion(cobro);
		cobroDetalle.setMonto(new BigDecimal("300"));
		
		cobroDetalle.addDetalleLiquidacion(this.entityManager.find(DetalleLiquidacion.class, 1l));
		//FIXME TAMPOCO DEBIA IR PORQUE EN REALIDAD VA EL DETALLE DE LA LIQUIDACION
		cobroDetalle.setCuentaCorriente(cuentaCorriente1);
		
		//FIXME pagador no deberìa existir para este tipo de cobro
		cobroDetalle.setPagador(cuentaCorriente1.getPersona());
		cobro.addDetalleCobro(cobroDetalle);
		
		this.cobroDAO.cobrar(cobro);
	}
	
	private Long guardarLiquidacion(Liquidacion liquidacion) throws Exception {
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			Long id = entityManager.merge(liquidacion).getId();
			tx.commit();
			return id;
		}
		catch(Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
	}

	public Liquidacion crearLiquidacionPrueba() {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.MONTH, -1);
		Date fechaLiquidacion = calendario.getTime();
		
		Liquidacion liquidacion = new Liquidacion();
		liquidacion.setFechaGeneracion(fechaLiquidacion);
		liquidacion.setFechaLiquidacion(fechaLiquidacion);
		liquidacion.setViaCobro(viaCobro);
		liquidacion.setId(1l);
		
		//Cuenta corriente 1 
		Cuota cuota1  = this.entityManager.find(Cuota.class, 1l);
		Cuota cuota11 =  this.entityManager.find(Cuota.class, 11l);
		//CuentaCorriente 2
		Cuota cuota21 =  this.entityManager.find(Cuota.class, 21l);
		Cuota cuota31 =  this.entityManager.find(Cuota.class, 31l);
		
		DetalleLiquidacion detalle1 = new DetalleLiquidacion();
		detalle1.setId(1l);
		detalle1.setCuentaCorrienteAfectada(cuota1.getCredito().getCuentaCorriente());
		detalle1.setLiquidacion(liquidacion);
		detalle1.setMonto(new BigDecimal("300.00"));
		detalle1.addCuota(cuota1);
		detalle1.addCuota(cuota11);
		
		DetalleLiquidacion detalle2 = new DetalleLiquidacion();
		detalle2.setId(2l);
		detalle2.setCuentaCorrienteAfectada(cuota1.getCredito().getCuentaCorriente());
		detalle2.setLiquidacion(liquidacion);
		detalle2.setMonto(new BigDecimal("200.00"));
		detalle2.addCuota(cuota21);
		detalle2.addCuota(cuota31);
		
		liquidacion.addDetalle(detalle1);
		liquidacion.addDetalle(detalle2);
		
		return liquidacion;
	}
}
