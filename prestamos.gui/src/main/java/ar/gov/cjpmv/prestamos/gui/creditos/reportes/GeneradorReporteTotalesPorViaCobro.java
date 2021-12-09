package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.RepartoCobranzasPorPeriodo;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class GeneradorReporteTotalesPorViaCobro {
	
	private RepartoCobranzasPorPeriodo cobranzas;
	
	public GeneradorReporteTotalesPorViaCobro(
			ViaCobro viaCobro, Date pFechaDesde, Date pFechaHasta) throws LogicaException 
	{
		cobranzas = new RepartoCobranzasPorPeriodo(pFechaDesde, pFechaHasta, viaCobro);
	}

	public Map<String,BigDecimal> getListaTotalesPorTipoCredito() {
		Map<TipoCredito,BigDecimal> totales = cobranzas.getTotalCapitalPorTipoCredito();
		Map<String,BigDecimal> retorno = new HashMap<String,BigDecimal>();
		for (TipoCredito tipo : totales.keySet()) {
			retorno.put(tipo.getNombre(), totales.get(tipo));
		}
		return retorno;
	}
	
	public BigDecimal getSubTotal() {
		BigDecimal total = new BigDecimal("0.00");
		for (BigDecimal cadaValor : cobranzas.getTotalCapitalPorTipoCredito().values()) {
			total = total.add(cadaValor);
		}
		return total;
	}
	
	public Map<String, BigDecimal> getListaTotalesConceptos() {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		map.put("Interes",cobranzas.getInteres());
		map.put("Retenciones Seguro",cobranzas.getOtrosConceptos());
		if (cobranzas.getSobrante() != null) {
			map.put("Sobrante cuenta corriente", cobranzas.getSobrante());
		}
		return map;
	}
	
	public BigDecimal getTotal() {
		BigDecimal totalFinal = getSubTotal();
		for (BigDecimal cadaValor : getListaTotalesConceptos().values()) {
			totalFinal = totalFinal.add(cadaValor);
		}
		return totalFinal;
	}
}
 