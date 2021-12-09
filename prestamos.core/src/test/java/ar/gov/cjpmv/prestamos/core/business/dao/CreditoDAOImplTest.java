package ar.gov.cjpmv.prestamos.core.business.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.CuotaComparator;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroDAO;
import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema;
import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema.TipoSistema;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cancelacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroPorBanco;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class CreditoDAOImplTest {

	private CreditoDAO creditoDAO;
	private EntityManager entityManager;
	private CuentaBancaria cuenta;
	
	public CreditoDAOImplTest() {
		creditoDAO = new CreditoDAOImpl();
		entityManager = GestorPersitencia.getInstance().getEntityManager();
	}
	
	@Before
	public void cargarDatosPrueba() throws Exception {
		URL scriptLimpieza = LiquidacionDAOTest.class.getResource("limpieza_liquidacion.sql");
		URL scriptLimpiezaSueldos = LiquidacionDAOTest.class.getResource("../sueldos/limpieza-sueldos.sql");
		URL scriptDatosPrueba = LiquidacionDAOTest.class.getResource("lote_prueba_sin_liquidacion.sql");
		URL scriptDatosAdicionalesPrueba = LiquidacionDAOTest.class.getResource("prueba_credito_dao.sql");
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
		 *  
		 */
		File archivoScriptLimpieza = new File(scriptLimpieza.toURI());
		File archivoScript = new File(scriptDatosPrueba.toURI());
		File archivoDatosAdicionales = new File(scriptDatosAdicionalesPrueba.toURI());
		DBUtiles.getInstance().cargarScript(archivoScriptLimpieza);
		DBUtiles.getInstance().cargarScript(new File(scriptLimpiezaSueldos.toURI()));
		DBUtiles.getInstance().cargarScript(archivoScript);
		DBUtiles.getInstance().cargarScript(archivoDatosAdicionales);
		cuenta = entityManager.find(CuentaBancaria.class, 1l);
	}

	@After
	public void limpiarDespues() throws Exception {
		URL scriptLimpieza = LiquidacionDAOTest.class.getResource("limpieza_liquidacion.sql");
		URL scriptLimpiezaSueldos = LiquidacionDAOTest.class.getResource("../sueldos/limpieza-sueldos.sql");
		DBUtiles.getInstance().cargarScript(new File(scriptLimpieza.toURI()));
		DBUtiles.getInstance().cargarScript(new File(scriptLimpiezaSueldos.toURI()));
	}
	
	@Test
	public void testGetListaCuotasImpagas() throws Exception {
		//FIXME HAY QUE LIMPIAR DESPUÉS DEL MÉTODO
		Calendar calendario = Calendar.getInstance();
		calendario.set(2011,Calendar.DECEMBER, 01);
		Credito credito = this.entityManager.find(Credito.class,1l);

		//Me aseguro que estén ordenadas
		Set<Cuota> esperadas = new TreeSet<Cuota>(new CuotaComparator());
		
		esperadas.addAll(credito.getListaCuotas());
		Date fecha = calendario.getTime();
		
		List<Cuota> obtenidas = creditoDAO.getListaCuotasImpagas(credito, fecha);
		assertArrayEquals(esperadas.toArray(),obtenidas.toArray());
		
		//Verifico que las cuotas vengan ordenadas por fecha
		Cuota primerCuota = obtenidas.get(0);
		Cuota ultimaCuota = obtenidas.get(obtenidas.size()-1);
		assertFalse(primerCuota.equals(ultimaCuota));
		assertTrue(primerCuota.getVencimiento().before(ultimaCuota.getVencimiento()));	

		//verifico que filtre por fecha (solo tiene que traer las cuotas hasta junio) 
		calendario.set(2011,Calendar.JUNE,01);
		fecha = calendario.getTime();
		
		obtenidas = creditoDAO.getListaCuotasImpagas(credito, fecha);
		assertEquals(5, obtenidas.size());
		Date fechaUltimaCuota = obtenidas.get(obtenidas.size()-1).getVencimiento();
		calendario.setTime(fechaUltimaCuota);
		assertTrue(calendario.get(Calendar.MONTH) == Calendar.JUNE);
		assertTrue(calendario.get(Calendar.YEAR) == 2011);
		
		//Pago la primer cuota y la 4ta
		CuentaCorriente ctacte = this.entityManager.find(CuentaCorriente.class, 1l);
		cargarPago(ctacte,obtenidas.get(0));
		cargarPago(ctacte,obtenidas.get(3));
		esperadas.remove(obtenidas.get(0));
		esperadas.remove(obtenidas.get(3));
		
		calendario.set(2011,Calendar.DECEMBER, 01);
		fecha = calendario.getTime();
		
		obtenidas = creditoDAO.getListaCuotasImpagas(credito, fecha);
		assertEquals(obtenidas.size(), 8);
		assertArrayEquals(esperadas.toArray(), obtenidas.toArray());
	}

	@Test
	public void testGetListaCuotasImpagasCobro() throws Exception {
		//************************************************************************
		//CtaCte: 10 - FechaHasta: 09/02/2011
		//Return (idCtas) en este orden: 100-101-110 
		
		CuentaCorriente cuentaCorriente = this.entityManager
											  .find(CuentaCorriente.class, 10l);
		Date fechaDesde=null;
		
		Calendar calFechaHasta=Calendar.getInstance();
		//Calendar Mes(0-11) - Date Mes(1-12)
		calFechaHasta.set(2011, 1, 9);
		Date fechaHasta= calFechaHasta.getTime();
		
		Set<Cuota> obtenidas = this.creditoDAO
				.getListaCuotasImpagas(fechaDesde, fechaHasta, cuentaCorriente);
		
		Set<Cuota> esperadas = new TreeSet<Cuota>(new CuotaComparator());
		Cuota cuota1Credito10 = entityManager.find(Cuota.class, 100l);
		Cuota cuota2Credito10 = entityManager.find(Cuota.class, 101l);
		Cuota cuota1Credito11 = entityManager.find(Cuota.class, 110l);
		esperadas.add(cuota1Credito10);
		esperadas.add(cuota2Credito10);
		esperadas.add(cuota1Credito11);
		
		assertFalse(obtenidas.isEmpty());	
		assertEquals(3, obtenidas.size());
		assertArrayEquals(esperadas.toArray(), obtenidas.toArray());
		
		//************************************************************************
		//CtaCte: 10 - FechaHasta: 31/12/2010
		//Return (idCtas) en este orden: vacio
		calFechaHasta.set(2010, Calendar.DECEMBER, 31);
		fechaHasta= calFechaHasta.getTime();
		obtenidas=this.creditoDAO.getListaCuotasImpagas(fechaDesde, fechaHasta, cuentaCorriente);	
		assertTrue(obtenidas.isEmpty());	
		
		
		//************************************************************************
		// Esta persona es solicitante del credito n° 1 y 2
		// es garante de dos creditos n° 3 y 4 (1 solo esta afectado N° 4)
		//CtaCte: 1 - FechaHasta: 02/02/2011
		//Return (idCtas) en este orden: 1-2-11-12-31-32
		
		
		CuentaCorriente cuentaCorrienteGarantia = this.entityManager.find(CuentaCorriente.class, 1l);
		Credito credito3 = this.entityManager.find(Credito.class, 3l);
		Credito credito4 = this.entityManager.find(Credito.class, 4l);
		PersonaFisica garante = this.entityManager.find(PersonaFisica.class, 4l);
		ViaCobro viaCobro = this.entityManager.find(ViaCobro.class, 1l);
		

		//creo 2 garantias y las guardo
		GarantiaPersonal garantia1 = generarGarantia(1l, credito3,
					garante, viaCobro, cuentaCorrienteGarantia);
		GarantiaPersonal garantia2 = generarGarantia(2l, credito4,
				garante, viaCobro, cuentaCorrienteGarantia);
		guardar(garantia1,garantia2);
		
		//Ahora tengo que hacer que un solo crédito sea afectado
		credito3.setCobrarAGarante(false);
		credito3.setListaGarantias(new ArrayList<Garantia>());
		credito3.getListaGarantias().add(garantia1);
		credito4.setCobrarAGarante(true);
		credito4.setListaGarantias(new ArrayList<Garantia>());
		credito4.getListaGarantias().add(garantia2);
		guardar(credito3, credito4);
	
		//Calendar Mes(0-11) - Date Mes(1-12)
		calFechaHasta.set(2011, Calendar.MARCH, 2);
		fechaHasta= calFechaHasta.getTime();
		
		obtenidas=this.creditoDAO.getListaCuotasImpagas(fechaDesde, fechaHasta, cuentaCorrienteGarantia);
		
		esperadas = new TreeSet<Cuota>(new CuotaComparator());
		Cuota cuota1Credito1 = entityManager.find(Cuota.class, 1l);
		Cuota cuota2Credito1 = entityManager.find(Cuota.class, 2l);
		Cuota cuota1Credito2 = entityManager.find(Cuota.class, 11l);
		Cuota cuota2Credito2 = entityManager.find(Cuota.class, 12l);
		Cuota cuota1Credito4 = entityManager.find(Cuota.class, 31l);
		Cuota cuota2Credito4 = entityManager.find(Cuota.class, 32l);
		
		esperadas.add(cuota1Credito1);
		esperadas.add(cuota2Credito1);
		esperadas.add(cuota1Credito2);
		esperadas.add(cuota2Credito2);
		esperadas.add(cuota1Credito4);
		esperadas.add(cuota2Credito4);
		
		assertFalse(obtenidas.isEmpty());	
		assertEquals(6, obtenidas.size());
		assertArrayEquals(esperadas.toArray(), obtenidas.toArray());
		
		//************************************************************************
		// Esta persona es solicitante del credito n° 1 y 2
		// es garante de dos creditos n° 3 y 4 (1 solo esta afectado N° 4)
		//CtaCte: 1 - FechaHasta: 02/02/2011
		//Return (idCtas) en este orden: 1-2-11-12-31-32
		ViaCobro viaCobroCaja = this.entityManager.find(ViaCobro.class, 2l);
		assertNotNull(viaCobroCaja);
		//creo 2 garantias y las guardo
		garantia1.setViaCobro(viaCobroCaja);
		garantia2.setViaCobro(viaCobroCaja);
		guardar(garantia1,garantia2);
		
		//Ahora tengo que hacer que un solo crédito sea afectado
		credito3.setCobrarAGarante(false);
		credito4.setCobrarAGarante(true);
		guardar(credito3, credito4);
	
		//Calendar Mes(0-11) - Date Mes(1-12)
		calFechaHasta.set(2011, Calendar.MARCH, 2);
		fechaHasta= calFechaHasta.getTime();
		
		obtenidas=this.creditoDAO.getListaCuotasImpagas(fechaDesde, fechaHasta, cuentaCorrienteGarantia);
		
		esperadas = new TreeSet<Cuota>(new CuotaComparator());
		Cuota locCuota1Credito1 = entityManager.find(Cuota.class, 1l);
		Cuota locCuota2Credito1 = entityManager.find(Cuota.class, 2l);
		Cuota locCuota1Credito2 = entityManager.find(Cuota.class, 11l);
		Cuota locCuota2Credito2 = entityManager.find(Cuota.class, 12l);
		Cuota locCuota1Credito4 = entityManager.find(Cuota.class, 31l);
		Cuota locCuota2Credito4 = entityManager.find(Cuota.class, 32l);
		
		esperadas.add(locCuota1Credito1);
		esperadas.add(locCuota2Credito1);
		esperadas.add(locCuota1Credito2);
		esperadas.add(locCuota2Credito2);
		esperadas.add(locCuota1Credito4);
		esperadas.add(locCuota2Credito4);
		
		assertFalse(obtenidas.isEmpty());	
		assertEquals(6, obtenidas.size());
		assertArrayEquals(esperadas.toArray(), obtenidas.toArray());
	}
	
	@Test
	public void testGuardarCreditoPorFinalizacion() throws LogicaException {
		CuentaCorriente ctactePagador = this.entityManager.find(CuentaCorriente.class, 1l);
		assertNotNull(ctactePagador);
		List<Credito> creditosAPagar = ctactePagador.getListaCredito();
		assertEquals(2, creditosAPagar.size());
		
		Credito credito = new Credito();
		credito.setAcumulativo(false);
		credito.setCantidadCuotas(10);
		credito.setCuentaCorriente(ctactePagador);
		Calendar calendario = Calendar.getInstance();
		credito.setFechaInicio(calendario.getTime());
		credito.setMontoTotal(new BigDecimal(100000));
		credito.setMontoEntrega(new BigDecimal(9700));
		credito.setTasa(new BigDecimal(0));
		credito.setNumeroCredito(1000);
		credito.setTipoCredito(this.entityManager.find(TipoCredito.class, 1l));
		credito.setViaCobro(this.entityManager.find(ViaCobro.class,1l));
		
		calendario.add(Calendar.MONTH, 1);
		
		List<Cuota> cuotas = Sistema.getSistema(TipoSistema.DIRECTO)
									.calcularCuotas(credito, calendario.getTime());
		credito.getListaCuotas().addAll(cuotas);
		
		DetalleCredito detalleCancelacion = new DetalleCredito();
		detalleCancelacion.setValor(new BigDecimal(3000));
		detalleCancelacion.setCredito(credito);
		detalleCancelacion.setEmiteCheque(false);
		
		creditoDAO.guardarCreditoPorFinalizacion(
					credito, 
					ctactePagador, 
					creditosAPagar, 
					detalleCancelacion);
		
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		CuentaCorriente ctacte = this.entityManager.find(CuentaCorriente.class, 1l);
		assertNotNull(ctacte);
		List<Credito> listaCreditos = ctacte.getListaCredito();
		assertEquals(3, listaCreditos.size());
		EstadoCredito estadoCredito1 = listaCreditos.get(0).getEstado();
		EstadoCredito estadoCredito2 = listaCreditos.get(1).getEstado();
		EstadoCredito estadoCredito3 = listaCreditos.get(2).getEstado();
		
		assertEquals(EstadoCredito.FINALIZADO, estadoCredito1);
		assertEquals(EstadoCredito.FINALIZADO, estadoCredito2);
		assertEquals(EstadoCredito.PENDIENTE, estadoCredito3);
	}
	
	@Test
	public void testEliminarCredito() {
		//FIXME terminar el test 
		//creditoDAO.eliminar(pObjeto)
	}
	
	private void guardar(Object... objetos) throws Exception {
		EntityTransaction tx = this.entityManager.getTransaction();
		try {
			tx.begin();
			for (Object cadaObj : objetos) {
				entityManager.merge(cadaObj);
			}
			tx.commit();
		}
		catch(Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
	}
	
	private GarantiaPersonal generarGarantia(
			long id,
			Credito pCredito, 
			PersonaFisica garante,
			ViaCobro viaCobro,
			CuentaCorriente cuentaCorriente) 
	{
		GarantiaPersonal garantiaPersonal = new GarantiaPersonal();
		garantiaPersonal.setId(id);
		garantiaPersonal.setAfectar(true);
		garantiaPersonal.setCredito(pCredito);
		garantiaPersonal.setGarante(garante);
		garantiaPersonal.setPorcentaje(new BigDecimal(100));
		garantiaPersonal.setViaCobro(viaCobro);
		garantiaPersonal.setAfectarA(cuentaCorriente);
		return garantiaPersonal;
	}
	
	private void cargarPago(CuentaCorriente ctacte,
			Cuota cuota) throws Exception 
	{
		EntityTransaction tx = this.entityManager.getTransaction();
		try {
			tx.begin();
			cuota = entityManager.getReference(Cuota.class, cuota.getId());
			CobroPorBanco cobro = new CobroPorBanco();
			cobro.setCuentaCorriente(ctacte);
			cobro.setFecha(Calendar.getInstance().getTime());
			cobro.setPagador(ctacte.getPersona());
			cobro.setMonto(cuota.getTotal());
			cobro.setCuenta(cuenta);
			
			Cancelacion cancelacion = new Cancelacion();
			cancelacion.setMonto(cuota.getTotal());
			cancelacion.setCobro(cobro);
			cuota.setCancelacion(cancelacion);
			
			entityManager.persist(cobro);
			entityManager.merge(cuota);
			tx.commit();
		}
		catch(Exception e) {
			if (tx != null &&  tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
	}
	
	/*getTotalSeguroCobradoPorPeriodo(Date, Date, ViaCobro)*/
}
