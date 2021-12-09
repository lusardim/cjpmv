package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.sueldos.ServicioCalculoConcepto;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;

public class ConceptoFijoTest {
	
	private ServicioCalculoConcepto servicioCalculoConcepto = mock(ServicioCalculoConcepto.class);
	
	@Test
	public void testConceptoFijo() {
		ConceptoFijo fijo = new ConceptoFijo();
		fijo.setServicio(servicioCalculoConcepto);
		fijo.setValor(new BigDecimal("100.40"));
		fijo.setSobreescribirValor(true);
		
		when(servicioCalculoConcepto
				.getValorFijoParaLiquidacionAnterior(eq(2000), eq(1), eq(fijo), any(PersonaFisica.class))).
			thenReturn(new BigDecimal("200.22"));
			
		ReciboSueldo recibo = crearRecibo();
		BigDecimal monto = fijo.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("100.40"), monto);
		fijo.setSobreescribirValor(false);
		monto = fijo.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("200.22"),monto);
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
		
		return recibo;
	}
	
}
