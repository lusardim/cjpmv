package ar.gov.cjpmv.prestamos.core.business.prestamos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class RepartoCobranzasPorPeriodo {

	private CreditoDAO creditoDAO;
	
	private Map<TipoCredito,BigDecimal> totalesPorTipoCredito;
	private BigDecimal interes;
	private BigDecimal otrosConceptos;
	private BigDecimal sobrante;
	private List<Liquidacion> liquidacionesAfectadas;	
	
	private BigDecimal totalCobrado;
	private BigDecimal totalCancelado;
	
	
	public RepartoCobranzasPorPeriodo(Date pFechaDesde, 
			Date pFechaHasta, ViaCobro pViaCobro) throws LogicaException
	{
		creditoDAO = DAOFactory.getDefecto().getCreditoDAO();
		this.liquidacionesAfectadas = creditoDAO.getLiquidaciones(pFechaDesde, 
				pFechaHasta, pViaCobro);
		if (liquidacionesAfectadas==null || liquidacionesAfectadas.isEmpty()) {
			throw new LogicaException(108,null);
		}
		calcular();
	}
	
	public void calcular() {
		totalCobrado = creditoDAO.getTotalCobrado(liquidacionesAfectadas);
		totalCancelado = creditoDAO.getTotalCuotasCanceladas(liquidacionesAfectadas);
		
		this.totalesPorTipoCredito = new HashMap<TipoCredito, BigDecimal>();
		switch(totalCobrado.compareTo(totalCancelado)) {
			case 0: {
				calcularSinPorcentajes();
				break;
			}
			case 1: {
				//totalCobrado>totalCuotas
				calcularSinPorcentajes();
				this.sobrante = this.totalCobrado.subtract(this.totalCancelado);
				break;
			}
			case -1: {
				//totalCobrado<totalCuotas
				calcularConPorcentajes();
				break;
			}
		}
	}
	
	private void calcularConPorcentajes() {
		List<TipoCredito> listaTiposcreditos = this.creditoDAO.getListaTiposCreditosAfectados(liquidacionesAfectadas);
		for (TipoCredito cadaTipoCredito : listaTiposcreditos) {
			BigDecimal total = creditoDAO
						.getTotalCapital(liquidacionesAfectadas, cadaTipoCredito);
			total = total.multiply(totalCobrado).divide(totalCancelado,2,RoundingMode.HALF_UP);
			this.totalesPorTipoCredito.put(cadaTipoCredito, total);
		}
		this.interes = creditoDAO.getTotalInteres(liquidacionesAfectadas);
		interes = interes.multiply(totalCobrado).divide(totalCancelado,2,RoundingMode.HALF_UP);
		
		this.otrosConceptos = creditoDAO.getTotalOtrosConceptos(liquidacionesAfectadas);
		this.otrosConceptos = otrosConceptos.multiply(totalCobrado).divide(totalCancelado,2,RoundingMode.HALF_UP);
	}

	private void calcularSinPorcentajes() {
		List<TipoCredito> listaTiposcreditos = this.creditoDAO.getListaTiposCreditosAfectados(liquidacionesAfectadas);
		for (TipoCredito cadaTipoCredito : listaTiposcreditos) {
			BigDecimal capital = creditoDAO
						.getTotalCapital(liquidacionesAfectadas, cadaTipoCredito);
			totalesPorTipoCredito.put(cadaTipoCredito, capital);
		}
		this.interes = creditoDAO.getTotalInteres(liquidacionesAfectadas);
		this.otrosConceptos = creditoDAO.getTotalOtrosConceptos(liquidacionesAfectadas);
	}

	public Map<TipoCredito,BigDecimal> getTotalCapitalPorTipoCredito() {
		return this.totalesPorTipoCredito;
	}
	
	public BigDecimal getInteres() {
		return this.interes;
	}
	
	public BigDecimal getOtrosConceptos() {
		return this.otrosConceptos;
	}
	
	public BigDecimal getSobrante() {
		if (this.sobrante == null) {
			sobrante = new BigDecimal("0.00");
		}
		return this.sobrante;
	}
	
	public BigDecimal getTotalCancelado() {
		return totalCancelado;
	}
	
	public BigDecimal getTotalCobrado() {
		return totalCobrado;
	}
}

