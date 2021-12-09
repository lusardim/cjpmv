package ar.gov.cjpmv.prestamos.core.business.sueldos;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;

public class ConceptoHaberesComparatorTest {
	
	@Test
	public void compareTest() {
		ConceptoHaberesComparator comparador = new ConceptoHaberesComparator();
		ConceptoFijo concepto1 = new ConceptoFijo();
		concepto1.setId(1l);
		concepto1.setTipoCodigo(TipoCodigo.ASIGNACION);

		ConceptoFijo concepto2 = new ConceptoFijo();
		concepto2.setId(2l);
		concepto2.setTipoCodigo(TipoCodigo.REMUNERATIVO);
		
		assertTrue(comparador.compare(concepto1, concepto2) > 0);
	}
	
	@Test
	public void ordenTest() {
		ConceptoHaberesComparator comparador = new ConceptoHaberesComparator();
		ConceptoFijo concepto1 = new ConceptoFijo();
		concepto1.setId(1l);
		concepto1.setTipoCodigo(TipoCodigo.ASIGNACION);

		ConceptoFijo concepto2 = new ConceptoFijo();
		concepto2.setId(2l);
		concepto2.setTipoCodigo(TipoCodigo.REMUNERATIVO);

		ConceptoFijo concepto3 = new ConceptoFijo();
		concepto3.setId(3l);
		concepto3.setTipoCodigo(TipoCodigo.ASIGNACION);

		ConceptoFijo concepto4 = new ConceptoFijo();
		concepto4.setId(4l);
		concepto4.setTipoCodigo(TipoCodigo.REMUNERATIVO);

		ConceptoFijo concepto5 = new ConceptoFijo();
		concepto5.setId(0l);
		concepto5.setTipoCodigo(TipoCodigo.NO_REMUNERATIVO);

		Set<ConceptoHaberes> conceptosOrdenados = new TreeSet<ConceptoHaberes>(comparador);
		conceptosOrdenados.add(concepto1);
		conceptosOrdenados.add(concepto2);
		conceptosOrdenados.add(concepto3);
		conceptosOrdenados.add(concepto4);
		conceptosOrdenados.add(concepto5);
		
		ConceptoHaberes[] conceptosEsperados = new ConceptoHaberes[5];
		conceptosEsperados[0] = concepto2;
		conceptosEsperados[1] = concepto4;
		conceptosEsperados[2] = concepto5;
		conceptosEsperados[3] = concepto1;
		conceptosEsperados[4] = concepto3;
		
		assertArrayEquals(conceptosEsperados, conceptosOrdenados.toArray());
		
		ConceptoFijo conceptoExtra = new ConceptoFijo();
		conceptoExtra.setDescripcion("nuevo");
		conceptoExtra.setId(1l);
		conceptoExtra.setTipoCodigo(TipoCodigo.ASIGNACION);
		
		//Según la api de java si los conceptos son iguales no debería 
		//pisar el que ya estaba
		conceptosOrdenados.clear();
		conceptosOrdenados.add(concepto1);
		conceptosOrdenados.add(conceptoExtra);
		
		assertFalse(conceptosOrdenados.isEmpty());
		assertEquals(1, conceptosOrdenados.size());
		assertTrue(concepto1 == conceptosOrdenados.iterator().next());
		assertFalse(conceptoExtra == conceptosOrdenados.iterator().next());
	}
	
}
