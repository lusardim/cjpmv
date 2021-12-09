package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import static ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.sueldos.ServicioCalculoConcepto;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;

public class ConceptoPrefijadoTest {
	
	private ServicioCalculoConcepto servicioCalculoConcepto = mock(ServicioCalculoConcepto.class);
	
	@Before
	public void setearServicio() {
		ANTIGUEDAD.setServicio(servicioCalculoConcepto);
		PERMANENCIA.setServicio(servicioCalculoConcepto);
		SUELDO_BASICO.setServicio(servicioCalculoConcepto);
		JUBILACION.setServicio(servicioCalculoConcepto);
		PENSION.setServicio(servicioCalculoConcepto);
	}
	
	@Test
	public void testConceptoPrefijado() {
		when(servicioCalculoConcepto.getBasico(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("100"));
		when(servicioCalculoConcepto.getPermanencia(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("70"));	
		when(servicioCalculoConcepto.getAntiguedad(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("55"));
	
		ReciboSueldo recibo = mock(ReciboSueldo.class);
		when(recibo.getPersona()).thenReturn(new PersonaFisica());
		when(recibo.getLiquidacion()).thenReturn(mock(LiquidacionHaberes.class));
		when(recibo.getLiquidacion().getTipo()).thenReturn(TipoLiquidacion.ACTIVO_NORMAL);
		
		BigDecimal monto = SUELDO_BASICO.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("100.00"),monto);
		
		monto = PERMANENCIA.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("70.00"),monto);
		
		monto = ANTIGUEDAD.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("55.00"),monto);
	}
	
	@Test
	public void testJubilacion() {
		//Ejemplos tomado de beneficiarios noviembre 2011
		//legajo 88
		when(servicioCalculoConcepto.getBasico(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("3260.25"));
		when(servicioCalculoConcepto.getPermanencia(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("3260.25"));	
		when(servicioCalculoConcepto.getAntiguedad(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("316.97"));
		when(servicioCalculoConcepto.getPorcentajeJubilacion())
			.thenReturn(new BigDecimal("0.82"));
	
		ReciboSueldo recibo = mock(ReciboSueldo.class);
		when(recibo.getPersona()).thenReturn(new PersonaFisica());
		when(recibo.getLiquidacion()).thenReturn(mock(LiquidacionHaberes.class));
		when(recibo.getLiquidacion().getTipo()).thenReturn(TipoLiquidacion.ACTIVO_NORMAL);
		
		
		BigDecimal monto = JUBILACION.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("5606.73"), monto);
		
		//Legajo 199
		when(servicioCalculoConcepto.getBasico(any(PersonaFisica.class),any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("1811.25"));
		when(servicioCalculoConcepto.getPermanencia(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(null);	
		when(servicioCalculoConcepto.getAntiguedad(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(null);
		when(servicioCalculoConcepto.getPorcentajeJubilacionSobreLiquidacion(any(PersonaFisica.class)))
			.thenReturn(new BigDecimal("0.9553"));

		monto = JUBILACION.getMonto(recibo);
		assertNotNull(monto);
		assertEquals(new BigDecimal("1418.84"), monto);
		
		when(servicioCalculoConcepto.getBasico(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(null);
		monto = JUBILACION.getMonto(recibo);
		assertNull(monto);
	}
	
	@Test
	public void testPension() {
		PersonaFisica personaMock = mock(PersonaFisica.class);
		when(personaMock.getEstado()).thenReturn(EstadoPersonaFisica.FALLECIDO);
		
		Pension pension = new Pension();
		pension.setPersona(new PersonaFisica());
		pension.setCausante(personaMock);

		ReciboSueldo recibo = mock(ReciboSueldo.class);
		when(recibo.getPersona()).thenReturn(new PersonaFisica());
		when(recibo.getLiquidacion()).thenReturn(mock(LiquidacionHaberes.class));
		when(recibo.getLiquidacion().getTipo()).thenReturn(TipoLiquidacion.ACTIVO_NORMAL);
		
		//ejemplo tomado de pension noviembre 2011 ACOSTA, Inoez
		when(servicioCalculoConcepto.getBasico(eq(personaMock), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("1992.38"));
		when(servicioCalculoConcepto.getPermanencia(eq(personaMock), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("126.79"));	
		when(servicioCalculoConcepto.getAntiguedad(eq(personaMock), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("1434.51"));
		when(servicioCalculoConcepto.getPension(any(PersonaFisica.class)))
			.thenReturn(pension);
		when(servicioCalculoConcepto.getPorcentajeJubilacion())
			.thenReturn(new BigDecimal("0.82"));
		when(servicioCalculoConcepto.getPorcentajePension())
			.thenReturn(new BigDecimal("0.65"));
		BigDecimal valor = PENSION.getMonto(recibo);
		assertNotNull(valor);
		assertEquals(new BigDecimal("1894.11"), valor);
		
		//Escobar, Ester mismo año
		when(servicioCalculoConcepto.getBasico(eq(personaMock),any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("2173.50"));
		when(servicioCalculoConcepto.getPermanencia(eq(personaMock), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("18.11"));	
		when(servicioCalculoConcepto.getAntiguedad(eq(personaMock), any(TipoLiquidacion.class)))
			.thenReturn(new BigDecimal("1956.15"));
		when(servicioCalculoConcepto.getPension(any(PersonaFisica.class)))
			.thenReturn(pension);
		//El resultado va al 50%
		pension.setValor(new BigDecimal("0.50"));

		valor = PENSION.getMonto(recibo);
		assertNotNull(valor);
		assertEquals(new BigDecimal("1105.38"), valor);
		
		when(servicioCalculoConcepto.getBasico(any(PersonaFisica.class), any(TipoLiquidacion.class)))
			.thenReturn(null);
		valor = PENSION.getMonto(recibo);
		assertNull(valor);
	}
}
