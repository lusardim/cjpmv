package ar.gov.cjpmv.prestamos.core.tareas;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.SortedSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CuentaCorrienteDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroDAO;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroLimpiezaSaldos;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;


public class LimpiezaSaldos extends Observable implements Runnable{
	
	private Logger log = Logger.getLogger(this.getClass().getName()); 
	private EntityManager entityManager;
	private int cantidadCuentasCorrientes;
	private int cantidadCuentasProcesadas;

	private Date fechaHasta;
	private Date fechaUltimaLimpieza;
	private int cantidadPaginas;
	private static final int TAMANIO_LOTE = 100;

	private CreditoDAO creditoDao;
	private CobroDAO cobroDao;
	private CuentaCorrienteDAO cuentaCorrienteDAO;
	
	public LimpiezaSaldos() throws SecurityException, IOException{
		FileHandler handler = new FileHandler("%t/archivo.log",true);
		log.addHandler(handler);
		handler.setFormatter(new SimpleFormatter());
		creditoDao = DAOFactory.getDefecto().getCreditoDAO();
		cuentaCorrienteDAO = DAOFactory.getDefecto().getCuentaCorrienteDAO();
	}
	
	@Override
	public void run() {
		entityManager = GestorPersitencia.getInstance().getEntityManager();
		try{
			log.info("Limpieza iniciada");
			procesarLimpieza();
			log.info("Limpieza finalizada");
		}
		catch(Exception e){
			log.log(Level.SEVERE,e.getMessage(),e);
		}
		finally{
			if (entityManager != null && entityManager.isOpen()){
				entityManager.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void procesarLimpieza() throws LogicaException {
		fechaHasta = obtenerFechaFinalizacion();
		
		this.cantidadCuentasCorrientes = ((Long)this.entityManager.createQuery("select count(c) " +
																			   "from CuentaCorriente c " +
																			   "where c.sobrante > 0 ")
																  .getSingleResult())
																  .intValue();
		
		cantidadPaginas = (cantidadCuentasCorrientes/TAMANIO_LOTE)+1;		
		
		for (int i=0; i<cantidadPaginas;i++){
			log.info("procesando página "+i +" de "+cantidadPaginas);
			int inicio = i*TAMANIO_LOTE;
			Query locQuery = this.entityManager.createQuery("select c from CuentaCorriente c where c.sobrante > 0 ");
			locQuery.setFirstResult(inicio);
			locQuery.setMaxResults(TAMANIO_LOTE);
			this.limpiar(locQuery.getResultList());
			this.notifyObservers();
		}
		log.info("fin de proceso, procesadas "+cantidadPaginas+" paginas");
	}
	
	/**
	 * Obtiene la última fecha del mes
	 * @return
	 */
	private Date obtenerFechaFinalizacion() {
		Calendar locCalendario = Calendar.getInstance();
		locCalendario.set(Calendar.DAY_OF_MONTH,locCalendario.getActualMaximum(Calendar.DAY_OF_MONTH));
		locCalendario.set(Calendar.HOUR_OF_DAY,locCalendario.getMaximum(Calendar.HOUR_OF_DAY));
		return locCalendario.getTime();
	}

	private void limpiar(List<CuentaCorriente> resultList) throws LogicaException {
		for (CuentaCorriente cadaCuenta: resultList) {
			SortedSet<Cuota> listaCuotas = this.creditoDao.getListaCuotasImpagas(this.fechaUltimaLimpieza, 
												  fechaHasta, 
												  cadaCuenta);
			this.procesarCuenta(cadaCuenta,listaCuotas);
			this.cantidadCuentasProcesadas++;
		}
	}

	private void procesarCuenta(CuentaCorriente pCuenta, SortedSet<Cuota> listaCuotas) throws LogicaException {
		BigDecimal sobrante = pCuenta.getSobrante();
		boolean cobrar = false;
		Iterator<Cuota> iterador = listaCuotas.iterator();
		while(iterador.hasNext()) {
			Cuota cadaCuota = iterador.next();
			if (cadaCuota.getCredito().getViaCobro().equals(ViaCobro.incobrable)) {
				iterador.remove();
			}
			else {
				if (cadaCuota.getTotal().floatValue() <= sobrante.floatValue()) {
					cobrar = true;
				}
			}
		}
		if (cobrar) {
			cobrar(pCuenta,listaCuotas);
		}
	}

	private void cobrar(CuentaCorriente pCuenta, SortedSet<Cuota> listaCuotas) throws LogicaException {
		BigDecimal sobrante = pCuenta.getSobrante();
		
		CobroLimpiezaSaldos cobro = new CobroLimpiezaSaldos();
		cobro.setFecha(Calendar.getInstance().getTime());
		cobro.setMonto(sobrante);
		cobro.setSobranteAlDia(new BigDecimal(0));
		cobro.setCuentaCorriente(pCuenta);
		cobro.setPagador(pCuenta.getPersona());
		pCuenta.setSobrante(new BigDecimal(0));
		
		this.cuentaCorrienteDAO.actualizarSobrante(pCuenta.getId(),new BigDecimal(0));
		this.getCobroDao().cobrar(cobro, listaCuotas);
	}

	private CobroDAO getCobroDao() {
		if (cobroDao == null){
			cobroDao = DAOFactory.getDefecto().getCobroDAO();
		}
		return cobroDao;
	}

	/**
	 * Obtiene el porcentaje entero del total procesado
	 * @return
	 */
	public int getPorcentajeFinalizacion(){
		return cantidadCuentasProcesadas/cantidadCuentasCorrientes*100;
	}
}
