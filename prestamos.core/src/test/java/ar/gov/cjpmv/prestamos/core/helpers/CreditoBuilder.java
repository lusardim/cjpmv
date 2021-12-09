package ar.gov.cjpmv.prestamos.core.helpers;

import java.math.BigDecimal;
import java.util.Date;

import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema.TipoSistema;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;



/**
 * Esta clase es para construir un conjunto de créditos a partir de ciertos 
 * parámetros.
 * @author pulpol
 *
 */
public class CreditoBuilder {

	public Credito crearCredito(
			boolean acumulativo,
			int cantidadCuotas,
			boolean cobrarAGarante,
			EstadoCredito estadoCredito,
			Date fechaFin,
			Date fechaInicio,
			BigDecimal tasa,
			BigDecimal montoEntrega,
			BigDecimal montoTotal,
			int numeroCredito,
			BigDecimal sueldoAlDia,
			TipoSistema tipoSistema,
			ViaCobro viaCobro
		)
	{
		
		Credito credito = new Credito();
		credito.setAcumulativo(acumulativo);
		credito.setCantidadCuotas(cantidadCuotas);
		credito.setCobrarAGarante(cobrarAGarante);
		credito.setEstado(estadoCredito);
		credito.setFechaFin(fechaFin);
		credito.setFechaInicio(fechaInicio);
		credito.setTasa(tasa);
		credito.setMontoEntrega(montoEntrega);
		credito.setMontoTotal(montoTotal);
		credito.setNumeroCredito(numeroCredito);
		credito.setSueldoAlDia(sueldoAlDia);
		credito.setTipoSistema(tipoSistema);
		credito.setViaCobro(viaCobro);
		return credito;
	}
}
