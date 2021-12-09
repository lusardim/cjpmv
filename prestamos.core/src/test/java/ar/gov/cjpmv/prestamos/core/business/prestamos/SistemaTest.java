package ar.gov.cjpmv.prestamos.core.business.prestamos;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema.TipoSistema;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;

public class SistemaTest {

	@Test
	public void testCalcularCapitalSistemaDirecto() throws LogicaException {
		/*	
		 * El credito a probar queda de la siguiente manera:
		 * monto = 1333.33 cuotas = 3
		 * 
		 * cuotas
		 * cuota 1 capital =  444,4433333333 redondeado = 444
		 * cuota 2 capital =  444,4433333333 redondeado = 444
		 * cuota 3 capital =  444,4433333333 redondeado = 444
		 *                    --------------              ---
		 *                  	 1333,33       -          1332 = 1,33
		 *	La cuota 3 debería tener un capital = 444+1.33 = 445.33 
		 *			 
		 */
		Directo sistemaDirecto = (Directo)Sistema.getSistema(TipoSistema.DIRECTO);
		BigDecimal monto = new BigDecimal("1333.33");
		
		Calendar calendario = Calendar.getInstance();
		Date fechaIncioCredito = calendario.getTime();
		Credito credito = crearCredito(monto,fechaIncioCredito);
		//Hago que se venza el mes posterior
		calendario.add(Calendar.MONTH, 1);
		Date fechaVencimiento = calendario.getTime();
		List<Cuota> resultado = sistemaDirecto
							.calcularCuotas(credito, fechaVencimiento);
		assertEquals(3,resultado.size());
		Cuota cuota1 = resultado.get(0);
		Cuota cuota2 = resultado.get(1);
		Cuota cuota3 = resultado.get(2);
		
		BigDecimal capital = new BigDecimal("444");
		assertEquals(cuota1.getCapital(), capital);
		assertEquals(cuota2.getCapital(), capital);
		
		capital = new BigDecimal("445.33");
		assertEquals(capital,cuota3.getCapital());
	}
	
	@Test
	public void testCalcularInteresSistemaDirecto() throws LogicaException {
		BigDecimal monto = new BigDecimal("1500");
		
		Directo sistemaDirecto = (Directo)Sistema.getSistema(TipoSistema.DIRECTO);

		Calendar calendario = Calendar.getInstance();
		Date fechaIncioCredito = calendario.getTime();
		Credito credito = crearCredito(monto,fechaIncioCredito);
		//Hago que se venza el mes posterior
		calendario.add(Calendar.MONTH, 1);
		Date fechaVencimiento = calendario.getTime();
		
		List<Cuota> resultado = sistemaDirecto
					.calcularCuotas(credito, fechaVencimiento);
		assertEquals(3,resultado.size());
		Cuota cuota1 = resultado.get(0);
		Cuota cuota2 = resultado.get(1);
		Cuota cuota3 = resultado.get(2);
		
		//Verifico interes
		BigDecimal interesCuota = new BigDecimal("23");
		assertEquals(interesCuota,cuota1.getInteres());
		assertEquals(interesCuota,cuota2.getInteres());
		/*
		 * El valor se calcula de la siguiente manera: 
		 *  interes cuota 1 = 22,5 redondeado = 23
		 *  interes cuota 2 = 22,5 redondeado = 23
		 *  interes cuota 3 = 22,5 redondeado = 23
		 *					 ------            ----
		 *					  67.5      -       69 			   =  -1.5
		 * resultado = interes cuota 3  +  diferencia redondeo =  23 + (-1.5) = 21.5  
		 */
		interesCuota = new BigDecimal("21.50");
		assertEquals(interesCuota,cuota3.getInteres());
		//Verifico el capital como es un número entero deberían ser iguales
		BigDecimal capital = new BigDecimal("500");
		assertEquals(capital, cuota1.getCapital());
		assertEquals(capital, cuota2.getCapital());
		//el BigDecimal requiere que tenga la misma escala para comparar
		assertEquals(capital.setScale(2), cuota3.getCapital());
	}

	private Credito crearCredito(BigDecimal monto, 
								 Date pFechaIncioCredito) 
	{
		BigDecimal interes = new BigDecimal("18");
		Credito credito = new Credito();
		credito.setCantidadCuotas(3);
		credito.setAcumulativo(true);
		credito.setMontoTotal(monto);
		credito.setTasa(interes);
		credito.setFechaInicio(pFechaIncioCredito);
		return credito;
	}
}
