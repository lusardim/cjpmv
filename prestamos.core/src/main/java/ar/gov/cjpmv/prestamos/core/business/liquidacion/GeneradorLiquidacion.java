package ar.gov.cjpmv.prestamos.core.business.liquidacion;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CuentaCorrienteDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.LiquidacionDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class GeneradorLiquidacion {

	private PropertyChangeSupport support = new PropertyChangeSupport(this);

	private int cantidad;
	private int procesados;

	private ViaCobro viaCobro;
	private Date fechaLiquidar;
	private Liquidacion liquidacion;
	private LiquidacionDAO liquidacionDAO;
	private CuentaCorrienteDAO cuentaCorrienteDAO;
	private CreditoDAO creditoDAO;
	
	private DAOFactory daoFactory;
	
	private Logger logger = Logger.getLogger(GeneradorLiquidacion.class
			.getName());

	private boolean liquidadoAlgo;

	// Cuentas corrientes directas a liquidar
	private Set<CuentaCorriente> listaCuentasCorrientes = new HashSet<CuentaCorriente>();

	public GeneradorLiquidacion() {
		inicializarDAOs();
	}

	private void inicializarDAOs() {
		liquidacionDAO = getDaoFactory().getLiquidacionDAO();
		cuentaCorrienteDAO = getDaoFactory().getCuentaCorrienteDAO();
		creditoDAO = getDaoFactory().getCreditoDAO();
	}

	public Liquidacion liquidar(ViaCobro pViaCobro, Date pFecha) {
		creditoDAO.getEntityManager().clear();
		cuentaCorrienteDAO.getEntityManager().clear();
		liquidacionDAO.getEntityManager().clear();
		
		if (pViaCobro == null || pFecha == null) {
			throw new IllegalArgumentException("la vía de cobro y la fecha no pueden ser nulas");
		}
		if (!pViaCobro.getLiquidable()) {
			throw new IllegalArgumentException(
					"La via de cobro debe ser liquidable");
		}
		this.viaCobro = pViaCobro;
		this.fechaLiquidar = pFecha;
		liquidar();
		return liquidacion;
	}

	private void liquidar() {
		try {
			listaCuentasCorrientes.clear();
			validarLiquidacion();
			cargarCuentasCorrientes();
			// calculo la cantidad
			cantidad = listaCuentasCorrientes.size();
			procesados = 0;
			logger.info("Se van a liquidar " + cantidad);
			// le hago saber a todos los listeners que la cantidad cambió
			support.firePropertyChange(EventoLiquidacion.CANTIDAD.name(), 0,
					cantidad);
			liquidarCuentasCorrientesCargadas();
			if (!liquidadoAlgo) {
				throw new LogicaException(62, null);
			}
		} catch (LogicaException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "No se pudo liquidar", e);
			support.firePropertyChange(EventoLiquidacion.ERROR.name(), null, e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "No se pudo liquidar", e);
		}
	}

	private void validarLiquidacion() throws LogicaException {
		if (this.liquidacionDAO.isPeriodoLiquidado(fechaLiquidar, viaCobro)) {
			throw new LogicaException(60, null);
		}
	}

	public void liquidar(ViaCobro pViaCobro) {
		this.liquidar(pViaCobro, Calendar.getInstance().getTime());
	}

	/**
	 * Obtiene todas las cuentas corrientes que estan asociadas directamente a
	 * créditos a liquidar esto significa que no son garantes, sinó que son los
	 * titulares de los créditos
	 */
	public void cargarCuentasCorrientes() {
		/*
		 * Esta fecha es hasta donde se toman las cuotas. generalmente para este
		 * caso se va a tomar el último día del mes
		 */
		Calendar fechaHastaLiquidacion = Calendar.getInstance();
		fechaHastaLiquidacion.setTime(fechaLiquidar);
		fechaHastaLiquidacion.set(Calendar.DAY_OF_MONTH,
				fechaHastaLiquidacion.getActualMaximum(Calendar.DAY_OF_MONTH));
		fechaHastaLiquidacion.set(Calendar.HOUR_OF_DAY,
				fechaHastaLiquidacion.getActualMaximum(Calendar.HOUR_OF_DAY));

		listaCuentasCorrientes = this.cuentaCorrienteDAO
				.getCuentasCorrientes(fechaHastaLiquidacion.getTime(),
						this.viaCobro);
	}

	/**
	 * Liquida una lista de cuentas corrientes
	 * 
	 * @paranm pListaCuentasCorrientes
	 */
	private void liquidarCuentasCorrientesCargadas() {
		crearLiquidacion();
		boolean logearDebug = logger.isLoggable(Level.FINE);
		
		for (CuentaCorriente cadaCuentaCorriente : listaCuentasCorrientes) {
			//liquido cada cuenta corriente
			DetalleLiquidacion detalle = liquidar(cadaCuentaCorriente);
			if (detalle != null) {
				this.liquidacion.addDetalle(detalle);
			}
			if (logearDebug) {
				logger.log(Level.FINE, "se liquidó la cuenta corriente:"
					+ cadaCuentaCorriente);
			}
			//Notifico que se liquidó una cuenta corriente más
			support.firePropertyChange(EventoLiquidacion.PROCESADOS.name(),
					procesados, ++procesados);
		}
		logger.info("Se liquidaron "
				+ liquidacion.getListaDetalleLiquidacion().size()
				+ " cuentas corrientes");
		//Si no se liquidó nada. entonces limpio el objeto liquidación
		if (liquidacion.getListaDetalleLiquidacion().isEmpty()) {
			this.liquidacion = null;
		}
		//notifico que se terminaron de liquidar todas las cuentas corrientes
		support.firePropertyChange(EventoLiquidacion.TERMINADO.name(), false,
				true);

	}

	/**
	 * inicializa el objeto Liquidacion  
	 */
	private void crearLiquidacion() {
		liquidacion = new Liquidacion();
		liquidacion.setFechaGeneracion(Calendar.getInstance().getTime());
		liquidacion.setFechaLiquidacion(this.fechaLiquidar);
		liquidacion.setViaCobro(this.viaCobro);
	}

	/**
	 * Liquida una cuenta corriente
	 * @param pCuentaCorriente
	 */
	public DetalleLiquidacion liquidar(CuentaCorriente pCuentaCorriente) {
		DetalleLiquidacion detalle = null;
		BigDecimal montoTotal = new BigDecimal(0).setScale(2,RoundingMode.HALF_UP);
		// A este punto el monto a favor debería ser inferior a la cuota más
		// pequeña de la cuenta corriente
		BigDecimal montoFavor = pCuentaCorriente.getSobrante();
		List<Cuota> locListaCuotas = new ArrayList<Cuota>();
		
		// Garantias
		BigDecimal cien = new BigDecimal(100).setScale(2,RoundingMode.HALF_UP);
		for (Garantia cadaGarantia : pCuentaCorriente.getGarantiasEnCurso()) {
			if (cadaGarantia.getAfectar()
					&& cadaGarantia.getViaCobro().equals(viaCobro)) {
				
				Credito credito = cadaGarantia.getCredito();
				List<Cuota> cuotasImpagas = this.creditoDAO
									.getListaCuotasImpagas(credito,
														fechaLiquidar);
				
				if (!cuotasImpagas.isEmpty()) {
					//Tomo la primer cuota de cada crédito
					Cuota agregar = cuotasImpagas.get(0);
					locListaCuotas.add(agregar);
					
					// toma el total, nunca sería adelantado
					BigDecimal subTotal = agregar.getTotal()
											.multiply(cadaGarantia.getPorcentaje())
											.divide(cien, RoundingMode.HALF_UP);
					montoTotal = montoTotal.add(subTotal);
					//El monto a favor siempre tiene que ser menor a la cuota inferior
					//Además esto afecta solo a la última cuota del crédito
					if (montoFavor.floatValue() > 0 
							&& credito.getCantidadCuotas().equals(agregar.getNumeroCuota())) {
						montoTotal = montoTotal.subtract(montoFavor);
						montoFavor = new BigDecimal(0);
					}
				}
			}
		}

		List<Credito> creditosPropiosImpagos = this.creditoDAO
				.getListaCreditosPropiosImpagos(pCuentaCorriente, viaCobro);
		
		for (Credito cadaCredito : creditosPropiosImpagos) {
			List<Cuota> cuotasImpagas = this.creditoDAO.getListaCuotasImpagas(
					cadaCredito, fechaLiquidar);

			if (!cuotasImpagas.isEmpty()) {
				Cuota agregar = cuotasImpagas.get(0);
				locListaCuotas.add(agregar);
				montoTotal = montoTotal.add(agregar.getTotal());
				//Es la última cuota del crédito y tiene sobrante
				if (montoFavor.floatValue() > 0 
						&& cadaCredito.getCantidadCuotas().equals(agregar.getNumeroCuota())) {
					montoTotal = montoTotal.subtract(montoFavor);
					montoFavor = new BigDecimal(0);
				}
			}
		}

		pCuentaCorriente.setSobrante(montoFavor);

		// Solo se liquidan las que tengan cuotas
		if (!locListaCuotas.isEmpty() && montoTotal.floatValue()>0) {
			liquidadoAlgo = true;
			// this.mostrar(locListaCuotas);
			if (logger.isLoggable(Level.FINE)) {
				logger.log(Level.FINE, "La cuenta corriente  "
						+ pCuentaCorriente + " tiene " + locListaCuotas.size()
						+ " cuotas en la liquidación");
			}
			detalle = new DetalleLiquidacion();
			detalle.setLiquidacion(this.liquidacion);
			detalle.setListaCuotas(locListaCuotas);
			detalle.setMonto(montoTotal.setScale(2,RoundingMode.HALF_UP));
			detalle.setCuentaCorrienteAfectada(pCuentaCorriente);
		}
		return detalle;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getProcesados() {
		return procesados;
	}

	public void setProcesados(int procesados) {
		this.procesados = procesados;
	}

	public void addPropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		this.support.addPropertyChangeListener(propertyChangeListener);
	}

	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

	public void guardar() throws LogicaException {
		try {
			this.liquidacionDAO.agregar(this.liquidacion);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LogicaException(61, null);
		}
	}

	public void setFechaLiquidar(Date fechaLiq) {
		this.fechaLiquidar = fechaLiq;
	}

	public void setViaCobro(ViaCobro viaCobro) {
		this.viaCobro = viaCobro;
	}

	public Set<CuentaCorriente> getListaCuentasCorrientes() {
		return listaCuentasCorrientes;
	}

	public void reemplazar(
			Map<DetalleLiquidacion, DetalleLiquidacion> cambiosRealizados)
			throws LogicaException {
		//TODO ver si esto va en el controlador no acá
		try {
			if (liquidacion == null) {
				crearLiquidacion();
			} 
			else {
				Iterator<DetalleLiquidacion> detalleIterator = liquidacion
						.getListaDetalleLiquidacion().iterator();
				
				while (detalleIterator.hasNext()) {
					DetalleLiquidacion cadaDetalle = detalleIterator.next();
					if (cambiosRealizados.get(cadaDetalle) != null) {
						detalleIterator.remove();
					}
				}
			}

			for (DetalleLiquidacion cadaDetalle : cambiosRealizados.values()) {
				if (cadaDetalle.getId() != null) {
					liquidacionDAO.eliminarDetalle(cadaDetalle.getId());
				}
				cadaDetalle = cadaDetalle.clone();
				cadaDetalle.setLiquidacion(liquidacion);
				liquidacion.getListaDetalleLiquidacion().add(cadaDetalle);
			}
		} catch (CloneNotSupportedException e) {
			throw new LogicaException(95, null);
		}
	}

	// Setters y getters de los daos para poner mocks
	public DAOFactory getDaoFactory() {
		if (this.daoFactory == null ){
			daoFactory = DAOFactory.getDefecto();
		}
		return daoFactory;
	}

	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		inicializarDAOs();
	}
}
