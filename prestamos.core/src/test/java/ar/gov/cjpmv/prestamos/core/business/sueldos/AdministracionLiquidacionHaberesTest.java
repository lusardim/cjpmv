package ar.gov.cjpmv.prestamos.core.business.sueldos;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LiquidacionHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Plantilla;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ReciboSueldo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;

public class AdministracionLiquidacionHaberesTest {
	
	private AdministracionLiquidacionHaberes adminLiquidacion;
	
	//Servicios mockeados
	private PlantillaDAO plantillaDAO = mock(PlantillaDAO.class);
	private PersonaDAO personaDAO = mock(PersonaDAO.class);
	private LiquidacionHaberesDAO liquidacionHaberesDAO = mock(LiquidacionHaberesDAO.class);
	private ServicioCalculoConcepto servicioCalculoConcepto = mock(ServicioCalculoConcepto.class);
	
	public AdministracionLiquidacionHaberesTest() {
		adminLiquidacion = new AdministracionLiquidacionHaberes();
		adminLiquidacion.setPlantillaDAO(plantillaDAO);
		adminLiquidacion.setPersonaDAO(personaDAO);
		adminLiquidacion.setLiquidacionHaberesDAO(liquidacionHaberesDAO);
		adminLiquidacion.setServicioCalculoConcepto(servicioCalculoConcepto);
	}

	@Test
	public void generarLiquidacionTest() throws LogicaException {
		cargarPlantilla();
		cargarPersonas();
		LiquidacionHaberes liquidacion = adminLiquidacion.generarLiquidacion(1, 200, TipoLiquidacion.ACTIVO_NORMAL);
		assertNotNull(liquidacion);
		assertEquals(3, liquidacion.getRecibos().size());
		ReciboSueldo recibo = liquidacion.getRecibos().get(0);
		assertNotNull(recibo.getLineasRecibo());
		assertEquals(1, recibo.getLineasRecibo().size());
		//Agrego 2 conceptos para la persona 1 
		
		ConceptoFijo concepto = new ConceptoFijo();
		concepto.setCodigo(19);
		concepto.setTipoCodigo(TipoCodigo.ASIGNACION);
		concepto.setValor(new BigDecimal(100));
		List<ConceptoHaberes> conceptos = new ArrayList<ConceptoHaberes>();
		conceptos.add(concepto);
		
		when(liquidacionHaberesDAO.getConceptosLiquidacionAnterior(
				any(Integer.class), 
				any(Integer.class), 
				eq(recibo.getPersona()),
				any(TipoLiquidacion.class)))
			.thenReturn(conceptos);
	
		liquidacion = adminLiquidacion.generarLiquidacion(1, 200, TipoLiquidacion.ACTIVO_NORMAL);
		assertNotNull(liquidacion);
		assertEquals(3, liquidacion.getRecibos().size());
		recibo = liquidacion.getRecibos().get(0);
		assertNotNull(recibo.getLineasRecibo());
		assertEquals(2, recibo.getLineasRecibo().size());
		//Pero el 1 todavía tiene 1 concepto por lo tanto tiene 1 linea
		recibo = liquidacion.getRecibos().get(1);
		assertNotNull(recibo.getLineasRecibo());
		assertEquals(1, recibo.getLineasRecibo().size());
		
	}

	private void cargarPersonas() {
		PersonaFisica persona1 = new PersonaFisica();
		persona1.setNombre("Persona1");
		persona1.setId(1l);
		PersonaFisica persona2 = new PersonaFisica();
		persona1.setNombre("Persona2");
		persona2.setId(2l);
		PersonaFisica persona3 = new PersonaFisica();
		persona1.setNombre("Persona2");
		persona3.setId(3l);
		
		List<PersonaFisica> personas = new ArrayList<PersonaFisica>();
		personas.add(persona1);
		personas.add(persona2);
		personas.add(persona3);
		
		when(personaDAO.findListaPersonasPorTipoLiquidacion(any(TipoLiquidacion.class)))
			.thenReturn(personas);
	}

	private void cargarPlantilla() {
		Plantilla plantilla = new Plantilla();
		plantilla.setTipoLiquidacion(TipoLiquidacion.ACTIVO_NORMAL);
		
		ConceptoHaberes concepto = mock(ConceptoHaberes.class);
		when(concepto.getTipoCodigo()).thenReturn(TipoCodigo.DESCUENTO);
		when(concepto.getCantidad(any(ReciboSueldo.class)))
			.thenReturn(1);
		when(concepto.getMonto(any(ReciboSueldo.class)))
			.thenReturn(new BigDecimal(100.20));
		plantilla.add(concepto);
		when(plantillaDAO.getPlantilla(any(TipoLiquidacion.class)))
			.thenReturn(plantilla);
	}
	
}
