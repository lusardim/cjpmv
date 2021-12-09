package ar.gov.cjpmv.prestamos.core.business.prestamos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cancelacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cobro;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroDetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.tareas.CuotasComparator;

public class CobroDAO {
	
	private Logger logger = Logger.getLogger(CobroDAO.class.getName());
	
	private EntityManager entityManager;
	private Cobro cobro;
	private Date fechaCobro;
	private CreditoDAO creditoDAO;
	
	public CobroDAO() {
		entityManager = GestorPersitencia.getInstance().getEntityManager();
		creditoDAO = DAOFactory.getDefecto().getCreditoDAO();
	}

	
	/**
	 * Cobra una lista de cuotas ordenadas por algún criterio
	 * @param pCobro
	 * @param listaCuotas
	 */
	public void cobrar(Cobro pCobro, SortedSet<Cuota> listaCuotas) throws LogicaException {
		//Check
		this.validar(pCobro);
		EntityTransaction tx = this.entityManager.getTransaction();
		try {
			tx.begin();
			this.cobro = pCobro;
			this.cobro.setSobranteAlDia(pCobro.getCuentaCorriente().getSobrante());
			this.entityManager.persist(cobro);
			pagarListaCuotas(listaCuotas);
			tx.commit();
		}
		catch(Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			logger.log(Level.SEVERE,
					  "No se ha podido realizar el cobro de las cuotas "+listaCuotas,
					  e);
			throw new LogicaException(102,null);
		}
	}
	
	/**
	 * Cobra una lista de cuotas sin importar el orden, pasa eso la ordena a la lista 
	 * @param pCobro
	 * @param pListaCuotas
	 * @throws LogicaException cuando no se ha podido cobrar la cuota
	 */
	public void cobrar(Cobro pCobro, List<Cuota> pListaCuotas) throws LogicaException {
		SortedSet<Cuota> locListaCuotas = new TreeSet<Cuota>(new CuotasComparator());
		locListaCuotas.addAll(pListaCuotas);
		cobrar(pCobro,locListaCuotas);
	}
	
	private void validar(Cobro pCobro){
		if (pCobro.getCuentaCorriente() == null){
			throw new NullPointerException("La cuenta corriente no puede ser nula");
		}
	}

	private void procesarCobro() {
		if (cobro.getFecha()==null){
			cobro.setFecha(Calendar.getInstance().getTime());
		}
		this.cobro.setSobranteAlDia(this.cobro.getCuentaCorriente().getSobrante());
		//TODO en caso de la liquidación, podrían tomarse el valor de las cuotas enviadas
		Set<Cuota> locListaCuotas = creditoDAO.getListaCuotasImpagas(null,
				cobro.getFecha(),
				cobro.getCuentaCorriente());
		
		this.pagarListaCuotas(locListaCuotas);
	}

	private void pagarListaCuotas(Set<Cuota> pListaCuotas) {
		BigDecimal valorAcumulado = this.cobro.getSobranteAlDia().add(this.cobro.getMonto());
		
		/* Recorro todas las cuotas porque si hay una cuota que no se puede pagar, porque el valor 
		 * supera el monto que queda, pero hay una cuota posterior de inferior valor, debería 
		 * ir cancelándolas hasta que no pueda seguir cancelando ninguna.    
		*/ 
		//TODO HACER QUE TERMINE CUANDO SE TERMINÓ EL MONTO!
		for (Cuota cadaCuota : pListaCuotas) {
			cadaCuota = this.entityManager.getReference(Cuota.class, cadaCuota.getId());
			if (cadaCuota.getTotal().floatValue() <= valorAcumulado.floatValue()) {
				Cancelacion locCancelacion = new Cancelacion();
				locCancelacion.setCobro(cobro);
				locCancelacion.setMonto(cadaCuota.getTotal());
				cadaCuota.setCancelacion(locCancelacion);
				this.entityManager.persist(locCancelacion);
				this.entityManager.merge(cadaCuota);
				this.entityManager.merge(cadaCuota.getCredito());
				valorAcumulado = valorAcumulado.subtract(cadaCuota.getTotal());
			}
		}
		this.cobro.getCuentaCorriente().setSobrante(valorAcumulado);
		this.entityManager.merge(cobro.getCuentaCorriente());
	}

	public Date getFechaCobro() {
		if (fechaCobro == null){
			fechaCobro = Calendar.getInstance().getTime();
		}
		return fechaCobro;
	}

	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	/**
	 * Cobra una lista de liquidaciones es la que se usa en el panel de liquidaci{on
	 * @param liquidacion
	 * @param montos
	 * @throws LogicaException
	 */
	public void cobrar(Liquidacion liquidacion, Map<CuentaCorriente,BigDecimal> montos) throws LogicaException {
		if (this.entityManager == null ){
			this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		}
		EntityTransaction tx = this.entityManager.getTransaction();
		try{
			tx.begin();
			this.fechaCobro = liquidacion.getFechaLiquidacion();
			CobroLiquidacion cobroLiquidacion = new CobroLiquidacion();
			cobroLiquidacion.setFechaIngreso(Calendar.getInstance().getTime());
			cobroLiquidacion.setLiquidacion(liquidacion);
			entityManager.persist(cobroLiquidacion);
			procesarCobro(cobroLiquidacion, montos);
			tx.commit();
		}
		catch(Exception e){
			if (tx!=null && tx.isActive()){
				tx.rollback();
			}
			e.printStackTrace();
			throw new LogicaException(106, null);
		}
	}
	
	private void procesarCobro(CobroLiquidacion cobroLiquidacion,
			Map<CuentaCorriente, BigDecimal> pagos) {
		Liquidacion liquidacion = cobroLiquidacion.getLiquidacion();
		for (Entry<CuentaCorriente, BigDecimal> cadaCuenta : pagos.entrySet()) {
			CuentaCorriente ctacte = cadaCuenta.getKey();
			
			CobroDetalleLiquidacion cobroDetalle = new CobroDetalleLiquidacion();
			cobroDetalle.setCobroLiquidacion(cobroLiquidacion);
			cobroDetalle.setCuentaCorriente(ctacte);
			cobroDetalle.setPagador(ctacte.getPersona());
			cobroDetalle.setMonto(cadaCuenta.getValue());
			cobroDetalle.setFecha(fechaCobro);
			
			//significa que es un cobro que no tiene su detalle liquidación, o sea que no se mandó
			DetalleLiquidacion detalle = getDetalleLiquidacionPorLiquidacion(liquidacion,ctacte);
			cobroDetalle.add(detalle);
			
			this.cobro = cobroDetalle;
			this.entityManager.persist(cobro);
			procesarCobro();
		}
	}


	private DetalleLiquidacion getDetalleLiquidacionPorLiquidacion(
			Liquidacion liquidacion, CuentaCorriente cuentaCorriente) 
	{
		EntityManager entity = GestorPersitencia.getInstance().getEntityManager();
		try {
			Long idLiquidacion = liquidacion.getId();
			Long idCuenta = cuentaCorriente.getId();
			Query consulta = entity.createQuery(
					"select d from DetalleLiquidacion d " +
					"where " +
					"	 d.liquidacion.id = :liquidacion " +
					"and d.cuentaCorrienteAfectada.id = :ctacte");
			consulta.setParameter("liquidacion", idLiquidacion);
			consulta.setParameter("ctacte", idCuenta);
			DetalleLiquidacion liq = (DetalleLiquidacion) consulta.getSingleResult();
			return liq;
		}
		catch (EntityNotFoundException e) {
			return null;
		}
		finally {
			entity.close();
		}
	}

	// TODO QUEDÉ ACÁ TENGO QUE TERMINAR LOS TEST Y VER QUE ANDE
	/**
	 *
	 * @param pCobroLiquidacion
	 * @throws LogicaException
	 */
	public void cobrar(CobroLiquidacion pCobroLiquidacion) throws LogicaException {
		if (this.entityManager == null || !this.entityManager.isOpen() ){
			this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		}
		EntityTransaction tx = this.entityManager.getTransaction();
		try{
			tx.begin();
			this.entityManager.persist(pCobroLiquidacion);
			for (CobroDetalleLiquidacion detalle :	pCobroLiquidacion
					.getListaCobroPorLiquidacion()) 
			{
				entityManager.persist(detalle);
				this.cobro = detalle;
				procesarCobro();
			}
			this.entityManager.merge(pCobroLiquidacion);
			tx.commit();
		}
		catch(Exception e){
			if (tx!=null && tx.isActive()){
				tx.rollback();
			}
			e.printStackTrace();
			throw new LogicaException(104, null);
		}
	}

	/**
	 * Cobra la liquidación completa, considerando que todas los detalles se pagan en su totalidad
	 * Es usado por ahora solo en la liquidación de haberes
	 * @param liquidacion
	 * @throws Exception
	 */
	public void cobrar(Liquidacion liquidacion) throws Exception {
		entityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			this.entityManager.persist(liquidacion);
			CobroLiquidacion cobroLiquidacion = new CobroLiquidacion();
			cobroLiquidacion.setFechaIngreso(Calendar.getInstance().getTime());
			cobroLiquidacion.setLiquidacion(liquidacion);
			entityManager.persist(cobroLiquidacion);
			
			List<CobroDetalleLiquidacion> listaDetallesCobro = new ArrayList<CobroDetalleLiquidacion>
					(liquidacion.getListaDetalleLiquidacion().size()); 
			
			for (DetalleLiquidacion cadaDetalle : liquidacion.getListaDetalleLiquidacion()) {
				CobroDetalleLiquidacion detalleCobro = new CobroDetalleLiquidacion();
				detalleCobro.setCobroLiquidacion(cobroLiquidacion);
				detalleCobro.setCuentaCorriente(detalleCobro.getCuentaCorriente());
				detalleCobro.setFecha(cobroLiquidacion.getFechaIngreso());
				detalleCobro.setMonto(cadaDetalle.getMonto());
				detalleCobro.setPagador(cadaDetalle.getPersona());		
				detalleCobro.add(cadaDetalle);
				
				entityManager.persist(detalleCobro);
				detalleCobro.setListaCancelaciones(crearCancelaciones(detalleCobro,cadaDetalle));
				entityManager.merge(detalleCobro);
			}
			
			cobroLiquidacion.setListaCobroPorLiquidacion(listaDetallesCobro);
			entityManager.merge(cobroLiquidacion);
			tx.commit();
		}
		catch(Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
	}

	private List<Cancelacion> crearCancelaciones(CobroDetalleLiquidacion detalleCobro, DetalleLiquidacion detalleLiquidacion) {
		List<Cuota> listaCuotas = detalleLiquidacion.getListaCuotas();
		List<Cancelacion> listaCancelaciones = new ArrayList<Cancelacion>(listaCuotas.size());
		for (Cuota cadaCuota : listaCuotas) {
			//TODO ver de qué otra manera se puede solucionar esto
			cadaCuota = entityManager.find(Cuota.class, cadaCuota.getId());
			Cancelacion cadaCancelacion = new Cancelacion();
			cadaCancelacion.setCobro(detalleCobro);
			cadaCancelacion.setMonto(cadaCuota.getTotal());
			cadaCuota.setCancelacion(cadaCancelacion);
			entityManager.persist(cadaCancelacion);
			entityManager.merge(cadaCuota);
			listaCancelaciones.add(cadaCancelacion);
		}
		return listaCancelaciones;
	}

}
