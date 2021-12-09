package ar.gov.cjpmv.prestamos.core.business.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public interface CreditoDAO extends GenericDAO<Credito>{
	
	public CuentaCorriente getCuentaCorriente(Persona pPersona) throws LogicaException;
	public CuentaCorriente getCuentaCorrienteGarantia(Persona pPersona);
	public List<Credito> findListaCreditosEnCurso(CuentaCorriente pCuentaCorriente);
	public List<ViaCobro> getListaViasCobro();
	public CuentaCorriente generarCuentaCorriente(Persona pPersona) throws LogicaException;
	public void validar(Credito pCredito) throws LogicaException;
	public SortedSet<Cuota> getListaCuotasImpagas(Date pFechaDesde, Date pFechaHasta, CuentaCorriente pCuentaCorriente);
	public Credito findCreditoPorNumero(Integer pNumeroCredito);
	public List<Credito> getListaCreditoPorCantidadCtasAdeudadas(Integer cantidadCuotasVencidas);
	public List<Credito> getCreditosNoFinalizados(Long pLegajo, Long pCui);
	public BigDecimal getMontoCancelacion(List<Credito> pListaCreditos);
	public List<Credito> getListaCreditosImpagos(CuentaCorriente cuentaCorriente);
	
	public long guardarCreditoPorFinalizacion(Credito pCredito,
			CuentaCorriente pCuentaCorriente, 
			List<Credito> pListaCreditos, 
			DetalleCredito pDetalleCredito) throws LogicaException;
	
	public List<Credito> getCreditosPorTipoYViaEnCurso(TipoCredito tipoCredito, 
			ViaCobro pViaCobro, 
			boolean pSoloSeguros, Date pFechaHasta);
	
	public List<Credito> getListaCreditosPropiosImpagos(CuentaCorriente pCuentaCorriente, 
			ViaCobro pViaCobro);
	
	public List<Cuota> getListaCuotasImpagas(Credito credito,Date pFecha);
	public List<Credito> findCreditosPorNumeroYLegajo(Long legajo,
			Integer numeroCredito);
	public List<Credito> findCreditosOtorgados(Date pFechaDesde, 
			Date pFechaHasta, 
			TipoCredito pTipoCredito, 
			ViaCobro pViaCobro);
	
	/**
	 * Obtiene el capital adeudado considerando la situación que existe 
	 * hasta la fecha de cierre
	 * El capital adeudado = suma(capital por cuota) de cuotas impagas 
	 * a la fecha (si pagó después de la fecha de cierre, entonces se considera
	 * como parte del total)
	 * @param pTipo
	 * @param fechaCierre
	 * @return
	 */
	public BigDecimal getSaldoCapital(TipoCredito pTipo, Date fechaCierre);
	
	//------------------------------------TODO REPARTIR ESTO
	/**
	 * Obtiene el total cobrado para todas las liquidaciones
	 * de una via en particular en un período dado
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param viaCobro
	 * @return
	 */
	public BigDecimal getTotalCobrado(List<Liquidacion> liquidaciones);
	
	/**
	 * Obtiene el total cancelado para todas las liquidaciones 
	 * entre ambas fechas para una via de cobro en particular
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param viaCobro
	 * @return
	 */
	public BigDecimal getTotalCuotasCanceladas(List<Liquidacion> liquidaciones);
	
	/**
	 * Obtengo el total por tipo de crédito
	 * @param pTipoCredito
	 * @return
	 */
	public BigDecimal getTotalCapital(List<Liquidacion> idLiquidaciones, TipoCredito pTipoCredito);
		
	/**
	 * Obtiene el total de intereses
	 * @param idLiquidaciones
	 * @param pViaCobro
	 * @return
	 */
	public BigDecimal getTotalInteres(List<Liquidacion> pLiquidaciones);
	
	/**
	 * Obtiene el total de intereses
	 * @param idLiquidaciones
	 * @param pViaCobro
	 * @return
	 */
	public BigDecimal getTotalOtrosConceptos(List<Liquidacion> idLiquidaciones);
	
	public List<Liquidacion> getLiquidaciones(Date pFechaDesde, Date pFechaHasta, ViaCobro viaCobro);
	
	public List<TipoCredito> getListaTiposCreditosAfectados(List<Liquidacion> listaLiquidaciones);
	
	public List<Integer> getCreditoCanceladoEnOtorgamiento(DetalleCredito locDetalleCredito);
	
	public BigDecimal getMontoCanceladoEnOtorgamiento(DetalleCredito locDetalleCredito, Integer numeroCredito);
	
}
