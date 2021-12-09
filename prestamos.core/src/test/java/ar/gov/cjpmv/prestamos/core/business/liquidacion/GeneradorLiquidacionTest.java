package ar.gov.cjpmv.prestamos.core.business.liquidacion;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.dao.MockCuentaCorrienteDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.MockDAOFactory;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;


public class GeneradorLiquidacionTest {
	
	private GeneradorLiquidacion generadorLiquidacion;
	private MockDAOFactory mockDAOFactory; 
	
	@Before
	public void setup() {
		mockDAOFactory = new MockDAOFactory();
		generadorLiquidacion = new GeneradorLiquidacion();
		generadorLiquidacion.setDaoFactory(mockDAOFactory);
	}
	
	@Test
	public void testLiquidarCuentaCorriente() {
		/*
		 * Este test verifica que se pueda liquidar 1 cuenta corriente con 1
		 * credito con 2 cuotas, deberia tomar solo la cuota más antigua
		 * CuentaCorriente
		 * 	-> Credito 1
		 * 			- Cuota 1 cap = 100, intereses = 10, vencimiento este mes
		 * 			- Cuota 2 cap = 100, intereses = 10, vencimiento dentro de 1 mes
		 */
		ViaCobro viaCobro = new ViaCobro();
		viaCobro.setId(1l);
		viaCobro.setLiquidable(true);
		viaCobro.setNombre("via cobro para test");
		Credito credito1 = getCredito1(viaCobro);
		Credito credito2 = getCredito2(viaCobro);
		
		//Primer caso liquido solo una cuenta corriente con 1 credito
		CuentaCorriente cuentaCorriente = new CuentaCorriente();
		cuentaCorriente.setSobrante(new BigDecimal(0));
		cuentaCorriente.addCredito(credito1);
		
		this.generadorLiquidacion.setViaCobro(viaCobro);
		this.generadorLiquidacion.setFechaLiquidar(Calendar.getInstance().getTime());
		
		DetalleLiquidacion liquidado = this.generadorLiquidacion.liquidar(cuentaCorriente);
		assertNotNull(liquidado);
		BigDecimal esperado = new BigDecimal(110).setScale(2);
		assertEquals(esperado,liquidado.getMonto());
		assertEquals(1, liquidado.getListaCuotas().size());
		Cuota cuota2credito1 = credito1.getListaCuotas().get(1);
		assertFalse(liquidado.getListaCuotas().contains(cuota2credito1));
	
		//Agrego 1 credito
		cuentaCorriente.addCredito(credito2);
		
		liquidado = this.generadorLiquidacion.liquidar(cuentaCorriente);
		assertNotNull(liquidado);
		esperado = new BigDecimal("330.33").setScale(2);
		assertEquals(esperado,liquidado.getMonto());
		assertEquals(2, liquidado.getListaCuotas().size());
		Cuota cuota2credito2 = credito2.getListaCuotas().get(1);
		assertFalse(liquidado.getListaCuotas().contains(cuota2credito1));
		assertFalse(liquidado.getListaCuotas().contains(cuota2credito2));
		
		
		//FIXME acá va a fallar porque ahora solo se descuenta de la ultima cuota
		cuentaCorriente.setSobrante(new BigDecimal("30.33"));
		liquidado = this.generadorLiquidacion.liquidar(cuentaCorriente);
		esperado = new BigDecimal("300.00");
		assertEquals(esperado,liquidado.getMonto());
	}

	@Test
	public void testLiquidarCuentaCorrienteConGarantes() {
		/*
		 * Este test tiene que validar que una persona garante de otra
		 * salga en la liquidacion
		 */
		ViaCobro viaCobro = new ViaCobro();
		viaCobro.setId(1l);
		viaCobro.setLiquidable(true);
		viaCobro.setNombre("prueba");
		
		Credito credito1 = getCredito1(viaCobro);
		Credito credito2 = getCredito2(viaCobro);
		
		CuentaCorriente cuentaCorriente = new CuentaCorriente();
		cuentaCorriente.setSobrante(new BigDecimal(0));
		cuentaCorriente.addCredito(credito1);
		cuentaCorriente.addCredito(credito2);
		
		CuentaCorriente garante = new CuentaCorriente();
		garante.setSobrante(new BigDecimal(0));
		
		GarantiaPersonal garantia = new GarantiaPersonal();
		garantia.setAfectar(true);
		garantia.setAfectarA(garante);
		garantia.setPorcentaje(new BigDecimal("100.00"));
		garantia.setViaCobro(viaCobro);
		garantia.setCredito(credito1);
		
		List<Garantia> garantiasEnCurso = new ArrayList<Garantia>();
		garantiasEnCurso.add(garantia);
		garante.setGarantiasEnCurso(garantiasEnCurso);
		
		generadorLiquidacion.setViaCobro(viaCobro);
		generadorLiquidacion.setFechaLiquidar(Calendar.getInstance().getTime());
		DetalleLiquidacion detalle = this.generadorLiquidacion.liquidar(garante);
		assertNotNull(detalle);
		assertEquals(new BigDecimal("110.00"), detalle.getMonto());
		assertEquals(1,detalle.getListaCuotas().size());
		assertTrue(detalle.getListaCuotas().contains(credito1.getListaCuotas().get(0)));
		
		garantia.setPorcentaje(new BigDecimal("50.00"));
		detalle = generadorLiquidacion.liquidar(garante);
		assertEquals(new BigDecimal("55.00"), detalle.getMonto());
		
		GarantiaPersonal garantia2 = new GarantiaPersonal();
		garantia2.setAfectar(true);
		garantia2.setAfectarA(garante);
		garantia2.setPorcentaje(new BigDecimal("100.00"));
		garantia2.setViaCobro(viaCobro);
		garantia2.setCredito(credito2);
		garantiasEnCurso.add(garantia2);
		
		detalle = generadorLiquidacion.liquidar(garante);
		assertEquals(new BigDecimal("275.33"), detalle.getMonto());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testLiquidarConViaCobroNoLiquidable() {
		ViaCobro viaCobro = new ViaCobro();
		viaCobro.setLiquidable(false);
		viaCobro.setNombre("viaCobroFalsa");
		generadorLiquidacion.liquidar(viaCobro);
	}
	
	@Test
	public void testLiquidar() throws LogicaException {
		ViaCobro viaCobro = new ViaCobro();
		viaCobro.setLiquidable(true);
		viaCobro.setNombre("via cobro prueba");
		Calendar calendario = Calendar.getInstance();
		
		CuentaCorriente cuenta1 = new CuentaCorriente();
		cuenta1.setCodigo("1");
		cuenta1.setFechaCreacion(calendario.getTime());
		cuenta1.setId(1l);
		Credito credito1 = getCredito1(viaCobro);
		cuenta1.addCredito(credito1);
		
		CuentaCorriente cuenta2 = new CuentaCorriente();
		cuenta2.setCodigo("2");
		cuenta2.setSobrante(new BigDecimal("33.33"));
		cuenta2.setId(2l);
		cuenta2.setFechaCreacion(calendario.getTime());
		Credito credito2 = getCredito2(viaCobro);
		Credito credito3 = getCredito3(viaCobro);
		
		cuenta2.addCredito(credito2);
		cuenta2.addCredito(credito3);
		
		MockCuentaCorrienteDAO cuentaCorrienteDAO = (MockCuentaCorrienteDAO) this.mockDAOFactory.getCuentaCorrienteDAO();
		cuentaCorrienteDAO.agregar(cuenta1);
		cuentaCorrienteDAO.agregar(cuenta2);
		generadorLiquidacion.liquidar(viaCobro);

		Liquidacion liquidacion = generadorLiquidacion.getLiquidacion();
		assertNotNull(liquidacion);
		List<DetalleLiquidacion> listaDetalles = liquidacion.getListaDetalleLiquidacion();
		//un detalle por cuenta corriente
		assertEquals(2,listaDetalles.size());
		
		//coloco los detalles ordenados por el id de las cuentas corrientes
		SortedSet<DetalleLiquidacion> detallesLiquidacionOrdenados = 
			new TreeSet<DetalleLiquidacion>(new Comparator<DetalleLiquidacion>(){

				@Override
				public int compare(DetalleLiquidacion arg0,
						DetalleLiquidacion arg1) {
					Long id1 = arg0.getCuentaCorrienteAfectada().getId();
					Long id2 = arg1.getCuentaCorrienteAfectada().getId();
					return id1.compareTo(id2);
				}
			
			});
		detallesLiquidacionOrdenados.addAll(liquidacion.getListaDetalleLiquidacion());
		
		Iterator<DetalleLiquidacion> iterador = detallesLiquidacionOrdenados.iterator();
		DetalleLiquidacion detalle1 = iterador.next();
		assertNotNull(detalle1);
		assertEquals(1,detalle1.getListaCuotas().size());
		assertEquals(credito1.getListaCuotas().get(0),detalle1.getListaCuotas().get(0));
		assertEquals(new BigDecimal("110.00"),detalle1.getMonto());
		
		DetalleLiquidacion detalle2 = iterador.next();
		assertNotNull(detalle2);
		assertEquals(2,detalle2.getListaCuotas().size());
		assertEquals(credito2.getListaCuotas().get(0),detalle2.getListaCuotas().get(0));
		assertEquals(credito3.getListaCuotas().get(0),detalle2.getListaCuotas().get(1));
		//El valor es de los credito2 = 200+20.33 + credito3 = 200+20.33 - sobrante. 33.33=  407.33
		assertEquals(new BigDecimal("407.33"),detalle2.getMonto());
	}

	private Credito getCredito3(ViaCobro viaCobro) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.DAY_OF_MONTH, 5);
		Date fechaVencimiento = calendario.getTime();
		
		BigDecimal capital = new BigDecimal(200);
		BigDecimal interes = new BigDecimal("20.33");
		Credito credito = new Credito();
		credito.setMontoEntrega(new BigDecimal(200));
		credito.setMontoTotal(new BigDecimal(200));
		credito.setId(3l);
		credito.getListaCuotas().addAll(
				generarCuotas(
						10l,
						2,
						capital,
						interes,
						new BigDecimal(0),
						fechaVencimiento)
			);
		credito.setViaCobro(viaCobro);
		return credito;
	}

	@Test
	public void testLiquidarConSobrante() {
		ViaCobro viaCobro = new ViaCobro();
		viaCobro.setId(1l);
		viaCobro.setLiquidable(true);
		viaCobro.setNombre("via cobro para test");
		
		Credito credito1 = getCredito1(viaCobro);
		Credito credito2 = getCredito2(viaCobro);

		CuentaCorriente cuentaCorriente = new CuentaCorriente();
		cuentaCorriente.setSobrante(new BigDecimal(10000));
		cuentaCorriente.addCredito(credito1);
		cuentaCorriente.addCredito(credito2);

		this.generadorLiquidacion.setViaCobro(viaCobro);
		this.generadorLiquidacion.setFechaLiquidar(Calendar.getInstance().getTime());

		DetalleLiquidacion liquidado = this.generadorLiquidacion.liquidar(cuentaCorriente);
		//Si el sobrante es mayor que las cuotas, no debería mandar nada
		assertNull(liquidado);
		cuentaCorriente.setSobrante(new BigDecimal("230.33"));
		//total 	= 110 + 220.33
		//sobrante 	= 230.33
		//total		  100
		liquidado = this.generadorLiquidacion.liquidar(cuentaCorriente);
		assertEquals(new BigDecimal("100.00"), liquidado.getMonto());
	}
	
	/**
	 * Crea un credito
	 * <ul>
	 * <li>Credito 1
	 * <ul> 
	 * <li>Cuota 1 capital = 200 interes 20,33</li>
	 * <li>Cuota 2 capital = 200 interes 20,33</li>
	 * </ul></li>
	 *</ul>
	 * @param viaCobro
	 * @return
	 */
	private Credito getCredito2(ViaCobro viaCobro) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.DAY_OF_MONTH, 5);
		Date fechaVencimiento = calendario.getTime();
		
		BigDecimal capital = new BigDecimal(200);
		BigDecimal interes = new BigDecimal("20.33");
		Credito credito = new Credito();
		credito.setMontoEntrega(new BigDecimal(200));
		credito.setMontoTotal(new BigDecimal(200));
		credito.getListaCuotas().addAll(
				generarCuotas(
						2l,
						2,
						capital,
						interes,
						new BigDecimal(0),
						fechaVencimiento)
			);
		credito.setViaCobro(viaCobro);
		return credito;
	}

	/**
	 * Crea un credito
	 * <ul>
	 * <li>
	 * Credito 1 : 
	 * <ul>
	 * <li>Cuota 1 capital = 100 interes 10</li>
	 * <li>Cuota 2 capital = 100 interes 10</li>
	 *  </li>
	 *  </ul>
	 * @param viaCobro
	 * @return
	 */
	private Credito getCredito1(ViaCobro viaCobro) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.DAY_OF_MONTH, 5);
		Date fechaVencimiento = calendario.getTime();
		
		BigDecimal capital = new BigDecimal(100);
		BigDecimal interes = new BigDecimal(10);
		Credito credito = new Credito();
		credito.setMontoEntrega(new BigDecimal(200));
		credito.setMontoTotal(new BigDecimal(200));
		credito.getListaCuotas().addAll(
				generarCuotas(
						2l,
						2,
						capital,
						interes,
						new BigDecimal(0),
						fechaVencimiento)
			);
		credito.setViaCobro(viaCobro);
		return credito;
	}

	private List<Cuota> generarCuotas(
			long idInicial,
			int cantidad, 
			BigDecimal capital, 
			BigDecimal interes, 
			BigDecimal otrosConceptos,
			Date fechaPrimerVencimiento) {
		List<Cuota> retorno = new ArrayList<Cuota>(cantidad);
		
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaPrimerVencimiento);
		
		for (int i=0 ; i<cantidad; i++) {
			Cuota cuota = new Cuota();
			cuota.setId(idInicial+i);
			cuota.setNumeroCuota(i);
			cuota.setCapital(capital);
			cuota.setInteres(interes);
			cuota.setOtrosConceptos(otrosConceptos);
			cuota.setVencimiento(calendario.getTime());
			calendario.add(Calendar.MONTH, 1);
			retorno.add(cuota);
		}
		
		return retorno;
	}
}
