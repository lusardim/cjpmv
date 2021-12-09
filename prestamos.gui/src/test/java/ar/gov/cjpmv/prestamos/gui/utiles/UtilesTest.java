package ar.gov.cjpmv.prestamos.gui.utiles;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilesTest {
	
	private Utiles utiles;
	
	@Test
	public void testCompararFecha() {
		utiles = Utiles.getInstance();
		Calendar calendario = Calendar.getInstance();
		calendario.set(2010, Calendar.JANUARY, 10); 
		
		Date fecha1 = calendario.getTime();
		Date fecha2 = calendario.getTime();
		int resultado = utiles.compararFechas(fecha1, fecha2);
		assertEquals(0,resultado);
		
		calendario.set(Calendar.HOUR_OF_DAY, 12);
		fecha1 = calendario.getTime();
		calendario.set(Calendar.HOUR_OF_DAY,10);
		fecha2 = calendario.getTime();

		resultado = utiles.compararFechas(fecha1, fecha2);
		assertEquals(0,resultado);
		
		calendario.set(Calendar.MONTH,Calendar.FEBRUARY);
		fecha1 = calendario.getTime();
		resultado = utiles.compararFechas(fecha1, fecha2);
		assertEquals(1, resultado);
		
		calendario.set(Calendar.YEAR,2011);
		fecha2 = calendario.getTime();
		resultado = utiles.compararFechas(fecha1, fecha2);
		assertEquals(-1, resultado);
	}
}
