package ar.gov.cjpmv.prestamos.core.business.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.persistence.EntityManager;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class MockCreditoDAO implements CreditoDAO {

	@Override
	public void inicializarDao() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cerrarDao() {
		// TODO Auto-generated method stub

	}

	@Override
	public Credito getObjetoPorId(Long clave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Credito pObjeto) throws LogicaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificar(Credito pObjeto) throws LogicaException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<? extends Credito> getLista(String pConsulta,
			Map<String, Object> pListaParametros, int pDesde, int pCantidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends Credito> getLista(String pConsulta,
			Map<String, Object> pListaParametros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityManager getEntityManager() {
		return new MockEntityManager();
	}


	@Override
	public CuentaCorriente getCuentaCorriente(Persona pPersona)
			throws LogicaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CuentaCorriente getCuentaCorrienteGarantia(Persona pPersona) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Credito> findListaCreditosEnCurso(
			CuentaCorriente pCuentaCorriente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ViaCobro> getListaViasCobro() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CuentaCorriente generarCuentaCorriente(Persona pPersona)
			throws LogicaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validar(Credito pCredito) throws LogicaException {
		// TODO Auto-generated method stub

	}

	@Override
	public SortedSet<Cuota> getListaCuotasImpagas(Date pFechaDesde,
			Date pFechaHasta, CuentaCorriente pCuentaCorriente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Credito findCreditoPorNumero(Integer pNumeroCredito) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Credito> getListaCreditoPorCantidadCtasAdeudadas(
			Integer cantidadCuotasVencidas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Credito> getCreditosNoFinalizados(Long pLegajo, Long pCui) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getMontoCancelacion(List<Credito> pListaCreditos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Credito> getListaCreditosImpagos(CuentaCorriente cuentaCorriente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long guardarCreditoPorFinalizacion(Credito pCredito,
			CuentaCorriente pCuentaCorriente, List<Credito> pListaCreditos,
			DetalleCredito pDetalleCredito) throws LogicaException {
		return 0l;
	}

	@Override
	public List<Credito> getCreditosPorTipoYViaEnCurso(TipoCredito tipoCredito,
			ViaCobro pViaCobro, boolean pSoloSeguros, Date pFechaHasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Credito> getListaCreditosPropiosImpagos(
			CuentaCorriente pCuentaCorriente, ViaCobro pViaCobro) {
		List<Credito> listaCreditos = new ArrayList<Credito>();
		for (Credito credito : pCuentaCorriente.getListaCredito()) {
			if (credito.getViaCobro().equals(pViaCobro)) {
				listaCreditos.add(credito);
			}
		}
		return listaCreditos;
	}

	@Override
	public List<Cuota> getListaCuotasImpagas(Credito credito, Date pFecha) {
		//devuelve todas las cuotas impagas 
		List<Cuota> listaCuotas = new ArrayList<Cuota>();
		for (Cuota cadaCuota : credito.getListaCuotas()) {
			if ( cadaCuota.getCancelacion() == null ) {
				listaCuotas.add(cadaCuota);
			}
		}
		return listaCuotas;
	}

	@Override
	public List<Credito> findCreditosPorNumeroYLegajo(Long legajo,
			Integer numeroCredito) {
		return null;
	}

	@Override
	public List<Credito> findCreditosOtorgados(Date pFechaDesde,
			Date pFechaHasta, TipoCredito pTipoCredito, ViaCobro pViaCobro) {
		return null;
	}

	@Override
	public void agregar(Credito pObjeto) throws LogicaException {
	}

	@Override
	public BigDecimal getTotalCobrado(List<Liquidacion> liquidaciones) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getTotalCuotasCanceladas(List<Liquidacion> liquidaciones) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getTotalCapital(List<Liquidacion> idLiquidaciones,
			TipoCredito pTipoCredito) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getTotalInteres(List<Liquidacion> pLiquidaciones) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getTotalOtrosConceptos(List<Liquidacion> idLiquidaciones) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Liquidacion> getLiquidaciones(Date pFechaDesde,
			Date pFechaHasta, ViaCobro viaCobro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoCredito> getListaTiposCreditosAfectados(
			List<Liquidacion> listaLiquidaciones) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSaldoCapital(TipoCredito pTipo, Date fechaCierre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getCreditoCanceladoEnOtorgamiento(
			DetalleCredito locDetalleCredito) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getMontoCanceladoEnOtorgamiento(
			DetalleCredito locDetalleCredito, Integer numeroCredito) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
