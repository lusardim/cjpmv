package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.sueldos.ServicioCalculoConcepto;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;

public class ConceptoPorcentualTest {
	
	@Test
	public void testConceptoPorcentual() {
		ConceptoPorcentual concepto = new ConceptoPorcentual();
		ReciboSueldo recibo = crearRecibo();
		
		//Todos los valores se reducen al 10%
		concepto.setValor(new BigDecimal("0.10"));
		concepto.setSobreTotalTipo(TipoCodigo.REMUNERATIVO);
		BigDecimal monto = concepto.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("10.00"),monto);
		
		concepto.setSobreCategoria(crearCategoria());
		monto = concepto.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("500.00"),monto);
	}
	
	@Test
	public void testGetMontoConEquivalenciaCategoria() {
		ConceptoPorcentual concepto = new ConceptoPorcentual();
		concepto.setSobreCategoria(crearCategoria());
		ReciboSueldo recibo = crearRecibo();
		//Todos los valores se reducen al 10%
		concepto.setValor(new BigDecimal("0.10"));
		
		Categoria categoria = new Categoria();
		categoria.setMonto(new BigDecimal("2000"));
		categoria.setTipoPersona(EstadoPersonaFisica.PASIVO);
	
		ServicioCalculoConcepto servicio = mock(ServicioCalculoConcepto.class);
		when(servicio.getEquivalenciaCategoria(eq(concepto.getSobreCategoria()), any(TipoLiquidacion.class)))
			.thenReturn(categoria);
		recibo.getLiquidacion().setTipo(TipoLiquidacion.JUBILACION_NORMAL);
		concepto.setServicio(servicio);
		
		BigDecimal monto = concepto.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("200.00"), monto);
	}
	
	@Test
	public void testGetMontoConCategoriaTipoNulos() {
		ConceptoPorcentual concepto = new ConceptoPorcentual();
		ReciboSueldo recibo = crearRecibo();
		ServicioCalculoConcepto servicio = mock(ServicioCalculoConcepto.class);
		concepto.setServicio(servicio);
		
		Categoria categoriaRevista = new Categoria();
		categoriaRevista.setMonto(new BigDecimal("111.111121"));
		categoriaRevista.setTipoPersona(EstadoPersonaFisica.ACTIVO);
		
		Categoria categoriaPension = new Categoria();
		categoriaPension.setMonto(new BigDecimal("777.77"));
		categoriaPension.setTipoPersona(EstadoPersonaFisica.ACTIVO);
		
		when(servicio.getCategoria(eq(recibo.getPersona()), any(TipoLiquidacion.class)))
			.thenReturn(categoriaRevista);
		
		PersonaFisica causantePension = mock(PersonaFisica.class);
		when(causantePension.getEstado()).thenReturn(EstadoPersonaFisica.FALLECIDO);
	
		Pension pension = new Pension();
		pension.setCausante(causantePension);

		when(servicio.getPension(any(PersonaFisica.class)))
			.thenReturn(pension);
		
		//En caso que busque la categoria del causante de la pensión en vez de la de la persona
		when(servicio.getCategoria(eq(causantePension), any(TipoLiquidacion.class)))
			.thenReturn(categoriaPension);
		
		concepto.setSobreCategoria(null);
		concepto.setSobreTotalTipo(null);
		
		BigDecimal monto = concepto.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("111.11"), monto);
		
		//Lo mismo pero para pension, ahora debería traer la categoria del causante y no la de la persona
		recibo.getLiquidacion().setTipo(TipoLiquidacion.PENSION_NORMAL);
		monto = concepto.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("777.77"), monto);

	}
	
	private Categoria crearCategoria() {
		Categoria categoria = new Categoria();
		categoria.setMonto(new BigDecimal("5000"));
		categoria.setTipoPersona(EstadoPersonaFisica.ACTIVO);
		return categoria;
	}
	
	private ReciboSueldo crearRecibo() {
		LiquidacionHaberes liquidacion = new LiquidacionHaberes();
		liquidacion.setAnio(2000);
		liquidacion.setMes(1);
		liquidacion.setTipo(TipoLiquidacion.ACTIVO_NORMAL);
		
		ReciboSueldo recibo = new ReciboSueldo();
		recibo.setLiquidacion(liquidacion);
		liquidacion.add(recibo);
		recibo.setPersona(new PersonaFisica());
		recibo.setLineasRecibo(crearLineasRecibo());
		
		return recibo;
	}

	private Set<LineaRecibo> crearLineasRecibo() {
		Set<LineaRecibo> lineas = new HashSet<LineaRecibo>();
		
		ConceptoHaberes conceptoRemunerativo = new ConceptoFijo();
		conceptoRemunerativo.setTipoCodigo(TipoCodigo.REMUNERATIVO);
		
		ConceptoHaberes conceptoNoRemunerativo = new ConceptoFijo();
		conceptoNoRemunerativo.setTipoCodigo(TipoCodigo.NO_REMUNERATIVO);
		
		LineaRecibo lineaRemunerativo = new LineaRecibo();
		lineaRemunerativo.setCantidad(1);
		lineaRemunerativo.setMonto(new BigDecimal(100));
		lineaRemunerativo.setConcepto(conceptoRemunerativo);
		lineas.add(lineaRemunerativo);
		
		LineaRecibo lineaNoRemunerativo = new LineaRecibo();
		lineaNoRemunerativo.setCantidad(1);
		lineaNoRemunerativo.setMonto(new BigDecimal(200));
		lineaNoRemunerativo.setConcepto(conceptoNoRemunerativo);
		lineas.add(lineaNoRemunerativo);
		
		return lineas;
	}
}
