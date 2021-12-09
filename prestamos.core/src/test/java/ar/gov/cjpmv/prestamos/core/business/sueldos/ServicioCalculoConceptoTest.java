package ar.gov.cjpmv.prestamos.core.business.sueldos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Antiguedad;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Jubilacion;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LineaRecibo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LiquidacionHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Pension;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PermanenciaCategoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ReciboSueldo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class ServicioCalculoConceptoTest {
	
	private ServicioCalculoConcepto servicio;
	private EntityManager entityManager;
	
	@BeforeClass
	public static void init() throws Exception {
		URL scriptServicioCalculo = AntiguedadDAOTest.class.getResource("sueldos.sql");
		URL scriptPersona = AntiguedadDAOTest.class.getResource("../dao/lote_prueba_sin_liquidacion.sql");
		DBUtiles.getInstance().cargarScript(new File(scriptPersona.toURI()));
		DBUtiles.getInstance().cargarScript(new File(scriptServicioCalculo.toURI()));
	}
	
	@AfterClass
	public static void destroy() throws Exception {
		URL script = AntiguedadDAOTest.class.getResource("limpieza-sueldos.sql");
		DBUtiles.getInstance().cargarScript(new File(script.toURI()));
	}
	
	public ServicioCalculoConceptoTest() {
		servicio = new ServicioCalculoConcepto();
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Ignore("esto es para mostrar la tabla de montos por antiguedad.")
	public void mostrarTablaAntiguedadPorCategoria() {
		System.out.println("\n\nMONTO ANTIGUEDADES POR CATEGORIA\n");
		EntityManager manager = GestorPersitencia.getInstance().getEntityManager();
		List<Categoria> categorias = manager.createQuery("from Categoria").getResultList();
		List<Antiguedad> antiguedades = manager.createQuery("from Antiguedad").getResultList();
		
		System.out.print("categoria\ttotal\t\t");
		for (Antiguedad permanencia : antiguedades) {
			System.out.print(permanencia.getPorcentaje() +"%\t");
		}
		
	
		for (int i = 0 ; i < categorias.size() ; i++) {
			Categoria categoria = categorias.get(i);
			System.out.print(categoria.getTipoPersona()+"-"+categoria+"\t"+categoria.getTotal()+"\t\t");			

			for (Antiguedad permanencia : antiguedades) {
				BigDecimal total = permanencia
						.getPorcentaje()
						.multiply(categoria.getTotal())
						.setScale(2, RoundingMode.HALF_UP);
				System.out.print(total+"\t");
			}
			System.out.println();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Ignore("esto es para dejar registrados los cálculos que van a ir en categoria")
	public void mostrarTablaPermanenciaPorCategoria() {
		System.out.println("\n\nMONTO PERMANENCIA POR CATEGORIA\n");
		EntityManager manager = GestorPersitencia.getInstance().getEntityManager();
		List<Categoria> categorias = manager.createQuery("from Categoria").getResultList();
		List<PermanenciaCategoria> permanencias = manager.createQuery("from PermanenciaCategoria").getResultList();
		
		System.out.print("categoria\ttotal\t\tdiferencia\t");
		for (PermanenciaCategoria permanencia : permanencias) {
			System.out.print(permanencia.getPorcentaje() +"%\t");
		}
		
		System.out.println();
		for (int i = 0 ; i < categorias.size() ; i++) {
			Categoria categoria = categorias.get(i);
			BigDecimal diferenciaCategoriaSuperior;
			if ( i < categorias.size() - 1) {
				diferenciaCategoriaSuperior = categorias.get(i+1).getTotal().subtract(categoria.getTotal());
			}
			else {
				diferenciaCategoriaSuperior = new BigDecimal(0);
			}
			
			System.out.print(categoria+"\t"+categoria.getTotal()+"\t\t"+diferenciaCategoriaSuperior+"\t\t");			

			for (PermanenciaCategoria permanencia : permanencias) {
				BigDecimal total = permanencia
						.getPorcentaje()
						.multiply(diferenciaCategoriaSuperior)
						.setScale(2, RoundingMode.HALF_UP);
				System.out.print(total+"\t");
			}
			System.out.println();
		}
	}
	
	@Test
	public void testGetBasico() {
		/* Para la prueba usamos este empleado 
		 * empleado prueba (id=4) que trabaja en la caja (id = 1) tiene 5-10 años de antiguedad (id = 2)
		 * ha permanecido en la categoría por 4-6 años y tiene categoria 3*/
		PersonaFisica persona = this.entityManager.find(PersonaFisica.class, 4l);
		Categoria categoria = this.entityManager.find(Categoria.class, 3l);
		BigDecimal basico = servicio.getBasico(persona, TipoLiquidacion.ACTIVO_NORMAL);
		assertNotNull(basico);
		assertEquals(categoria.getTotal(), basico);
		persona = this.entityManager.find(PersonaFisica.class, 5l);
		//como esta persona no tiene categoria debería tirar una excepción
		basico = servicio.getBasico(persona, TipoLiquidacion.ACTIVO_NORMAL);
		assertNull(basico);
	}
	
	@Test
	public void testGetPermanencia() {
		/* Para la prueba usamos este empleado 
		 * empleado prueba (id=4) que trabaja en la caja (id = 1) tiene 5-10 años de antiguedad (id = 2)
		 * ha permanecido en la categoría por 4-6 años y tiene categoria 3*/
		Categoria categoriaSuperior = entityManager.find(Categoria.class, 4l);
		Categoria categoria = entityManager.find(Categoria.class, 3l);
		PermanenciaCategoria permanencia = entityManager.find(PermanenciaCategoria.class, 2l);
		BigDecimal montoEsperado = permanencia
				.getPorcentaje()
				.multiply(
						categoriaSuperior.getTotal()
						.subtract(categoria.getTotal())
						)
				.setScale(2, RoundingMode.HALF_UP);
		
		PersonaFisica persona = this.entityManager.find(PersonaFisica.class, 4l);
		BigDecimal monto = servicio.getPermanencia(persona, TipoLiquidacion.ACTIVO_NORMAL);
		assertNotNull(monto);
		assertEquals(montoEsperado, monto);
	}
	
	
	@Test
	public void testGetAntiguedad() {
		/* Para la prueba usamos este empleado 
		 * empleado prueba (id=4) que trabaja en la caja (id = 1) tiene 5-10 años de antiguedad (id = 2)
		 * ha permanecido en la categoría por 4-6 años y tiene categoria 3*/
		Categoria categoria = entityManager.find(Categoria.class, 3l);
		Antiguedad antiguedad = entityManager.find(Antiguedad.class, 2l);
		BigDecimal montoEsperado = antiguedad
				.getPorcentaje()
				.multiply( categoria.getTotal())
				.setScale(2, RoundingMode.HALF_UP);
		
		PersonaFisica persona = this.entityManager.find(PersonaFisica.class, 4l);
		BigDecimal monto = servicio.getAntiguedad(persona, TipoLiquidacion.ACTIVO_NORMAL);
		assertNotNull(monto);
		assertEquals(montoEsperado, monto);
	}
	
	@Test
	public void testGetValorFijoParaLiquidacionAnterior() {
		PersonaFisica persona = this.entityManager.find(PersonaFisica.class, 4l);
		ConceptoFijo concepto = this.entityManager.find(ConceptoFijo.class, 1l);
		assertNotNull(persona);
		assertNotNull(concepto);
		BigDecimal valor = servicio.getValorFijoParaLiquidacionAnterior(1900, 2, concepto, persona);
		assertNotNull(valor);
		BigDecimal esperado = new BigDecimal("50.50");
		assertEquals(esperado, valor);
		//no hay liquidación anterior
		valor = servicio.getValorFijoParaLiquidacionAnterior(1800, 1, concepto, persona);
		assertNull(valor);
	}
	
	@Test
	public void testGetCantidad() {
		PersonaFisica persona = this.entityManager.find(PersonaFisica.class, 4l);
		ConceptoFijo concepto = this.entityManager.find(ConceptoFijo.class, 1l);
		ReciboSueldo recibo = crearRecibo(persona, concepto);
		Integer valor = servicio.getCantidad(concepto, recibo);
		assertNotNull(valor);
		assertEquals(27, valor.intValue());
		recibo.getLiquidacion().setAnio(1000);
		valor = servicio.getCantidad(concepto, recibo);
		assertNull(valor);
	}

	@Test
	public void testGetPorcentajeJubilacionSobreLiquidacion() throws Exception {
		BigDecimal valorEsperado = new BigDecimal("1.985500");
		PersonaFisica persona = this.entityManager.find(PersonaFisica.class, 4l);
		Jubilacion jub = new Jubilacion();
		jub.setValor(valorEsperado);
		jub.setPersona(persona);
		jub.setTipoBeneficio(TipoBeneficio.JUBILACION_COMUN);
		jub.setFechaOtorgamiento(Calendar.getInstance().getTime());
		guardar(jub);
		
		BigDecimal valor = servicio.getPorcentajeJubilacionSobreLiquidacion(persona);
		assertNotNull(valor);
		assertEquals(valorEsperado, valor);
		eliminar(jub);
	}
	
	@Test
	public void testGetPension() throws Exception {
		PersonaFisica persona = this.entityManager.find(PersonaFisica.class, 4l);
		PersonaFisica causante = this.entityManager.find(PersonaFisica.class, 5l);
		causante.setEstado(EstadoPersonaFisica.FALLECIDO);
		
		Pension pension = new Pension();
		pension.setPersona(persona);
		pension.setCausante(causante);
		pension.setFechaOtorgamiento(Calendar.getInstance().getTime());
		pension.setValor(new BigDecimal("0.0012"));
		guardar(pension);
		
		Pension obtenida = servicio.getPension(persona);
		assertNotNull(obtenida);
		assertEquals(new BigDecimal("0.001200"), obtenida.getValor());
		
		eliminar(pension);
	}

	@Test
	public void testGetEquivalenciaCategoria() throws Exception {
		Categoria categoriaOriginal = entityManager.find(Categoria.class, 1l);
		Categoria categoriaEsperada = entityManager.find(Categoria.class, 13l);
		
		Categoria categoriaObtenida = servicio.getEquivalenciaCategoria(categoriaOriginal, 
				TipoLiquidacion.JUBILACION_NORMAL);
		
		assertNotNull(categoriaObtenida);
		assertEquals(categoriaEsperada, categoriaObtenida);
	}
	
	@Test
	public void testGetCategoria() throws Exception {
		Categoria categoriaOriginal = entityManager.find(Categoria.class, 3l);
		PersonaFisica persona = entityManager.find(PersonaFisica.class, 4l);
		
		Categoria categoria = servicio.getCategoria(persona, TipoLiquidacion.ACTIVO_NORMAL);
		assertNotNull(categoria);
		assertEquals(categoriaOriginal, categoria);
		
		Categoria categoriaEquivalente = entityManager.find(Categoria.class, 15l);		 
		categoria = servicio.getCategoria(persona, TipoLiquidacion.JUBILACION_NORMAL);
		assertNotNull(categoria);
		assertEquals(categoriaEquivalente, categoria);
	}
	
	private void eliminar(Object jub) throws Exception {
		EntityTransaction tx = this.entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.remove(jub);
			tx.commit();
		}
		catch (Exception e) {
			tx.rollback();
			throw e;
		}
	}

	private void guardar(Object jub) throws Exception {
		EntityTransaction tx = this.entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.persist(jub);
			tx.commit();
			entityManager.refresh(jub);
		}
		catch (Exception e) {
			tx.rollback();
			throw e;
		}
		
	}

	private ReciboSueldo crearRecibo(PersonaFisica persona, ConceptoHaberes concepto) {
		LiquidacionHaberes liq = new LiquidacionHaberes();
		liq.setAnio(1900);
		liq.setMes(1);
		liq.setTipo(TipoLiquidacion.ACTIVO_NORMAL);
		
		ReciboSueldo recibo = new ReciboSueldo();
		recibo.setLiquidacion(liq);
		recibo.setPersona(persona);

		LineaRecibo linea = new LineaRecibo();
		linea.setCantidad(1);
		linea.setConcepto(concepto);
		linea.setMonto(new BigDecimal("10.33"));
		recibo.add(linea);
		
		return recibo;
	}
	
	@Test
	public void testGetPorcentajeJubilacion() {
		BigDecimal valor = servicio.getPorcentajeJubilacion();
		assertNotNull(valor);
		assertEquals(new BigDecimal("0.820000"), valor);
	}
	
	@Test
	public void testGetPorcentajePension() {
		BigDecimal valor = servicio.getPorcentajePension();
		assertNotNull(valor);
		assertEquals(new BigDecimal("0.650000"), valor);
	}
}
