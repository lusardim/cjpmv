package ar.gov.cjpmv.prestamos.core.facades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.dao.ConfiguracionSistemaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.TipoCreditoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.ConfiguracionSistema;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroPorCancelacionCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class CreditoFacade {
	private TipoCreditoDAO tipoCreditoDao;
	private CreditoDAOImpl creditoDao;
	private ConfiguracionSistemaDAO configuracionDAO;
	
	public CreditoFacade() {
		this.tipoCreditoDao = new TipoCreditoDAO();
		this.creditoDao = new CreditoDAOImpl();
		this.configuracionDAO = new ConfiguracionSistemaDAO();
	}
	
	public List<TipoCredito> getListaTiposCreditos(){
		return this.tipoCreditoDao.findListaTipoCredito(null);
	}
	
	public List<ViaCobro> getListaViasCobro(){
		return this.creditoDao.getListaViasCobro();
	}

	public CreditoDAOImpl getCreditoDao() {
		return creditoDao;
	}

	public CuentaCorriente getCuentaCorriente(Persona personaSeleccionada) throws LogicaException {
		if (personaSeleccionada == null){
			throw new LogicaException(34, null);
		}
		try{
			return this.creditoDao.getCuentaCorriente(personaSeleccionada);
		}
		catch(LogicaException e){
			if (e.getCodigoMensaje() != 29){
				throw e;
			}
			return this.creditoDao.generarCuentaCorriente(personaSeleccionada);
		}
	}

	/**
	 * la configuración del sistema 
	 * @return
	 * @throws LogicaException cuando no hay una configuración cargada
	 */
	public ConfiguracionSistema getConfiguracionSistema()
			throws LogicaException {
		return configuracionDAO.getConfiguracionSistema();
	}
	
	/**
	 * Genera el listado de cheques
	 * @param pCredito
	 * @return
	 */
	public List<Cheque> generarCheques(Credito pCredito){
		List<Cheque> locListaCheques = new ArrayList<Cheque>();
		int numeroCheque = this.getUltimoNumeroCheque();
		Date fechaEmision = Calendar.getInstance().getTime();
		
		Cheque locChequePago = this.getChequePago(pCredito);
		numeroCheque++;
		locChequePago.setNumero(numeroCheque);
		locListaCheques.add(locChequePago);
		
		for (DetalleCredito cadaDetalle : pCredito.getDetalleCredito()){
			if (cadaDetalle.getEmiteCheque()){
				numeroCheque++;
				Cheque locCheque = new Cheque();
				locCheque.setMonto(cadaDetalle.getValor());
				locCheque.setNumero(numeroCheque);
				locCheque.setFechaEmision(fechaEmision);
				locCheque.setConcepto(cadaDetalle.getNombre());
				locListaCheques.add(locCheque);
			}
		}
		return locListaCheques;
	}

	private Cheque getChequePago(Credito pCredito) {
		Cheque locCheque =  new Cheque();
		locCheque.setFechaEmision(Calendar.getInstance().getTime());
		locCheque.setMonto(pCredito.getMontoEntrega());
		locCheque.setEmitidoA(pCredito.getCuentaCorriente().getPersona());
		locCheque.setConcepto("Pago del crédito "+pCredito.getNumeroCredito());
		return locCheque;
	}

	private int getUltimoNumeroCheque() {
		EntityManager locEntityManager = GestorPersitencia.getInstance().getEntityManager();
		try{
			Integer locNumero = (Integer)locEntityManager.createQuery("select max(c.numero) from Cheque c")
												   .getSingleResult(); 
			
			return (locNumero==null)?0:locNumero.intValue();
		}
		finally{
			if (locEntityManager!=null && locEntityManager.isOpen()){
				locEntityManager.close();
			}
		}
	}
	
	public void validarCredito(Credito pCredito) throws LogicaException{
		this.creditoDao.validar(pCredito);
	}
	
	public Integer getUltimoNumeroCredito(){
		EntityManager locEntityManager = GestorPersitencia.getInstance().getEntityManager();
		try{
			Integer locNumero = (Integer)locEntityManager.createQuery("select max(c.numeroCredito) from Credito c")
												   .getSingleResult(); 
			
			return (locNumero==null)?0:locNumero.intValue();
		}
		finally{
			if (locEntityManager!=null && locEntityManager.isOpen()){
				locEntityManager.close();
			}
		}
	}

	public void validarCheques(Credito pCredito) throws LogicaException {
		for (Cheque cadaCheque : pCredito.getListaCheques()){
			this.creditoDao.validarCheque(cadaCheque);
		}
	}
	
	public void guardarCreditoPorFinalizacion(Credito pCredito,
			CuentaCorriente pCuentaCorriente, 
			List<Credito> pListaCreditos, 
			DetalleCredito pDetalleCredito) throws LogicaException
	{
		if (pCredito == null){
			throw new NullPointerException("El crédito no puede ser nulo");
		}
		creditoDao.guardarCreditoPorFinalizacion(pCredito, pCuentaCorriente, pListaCreditos, pDetalleCredito);
	}

	public void guardar(Credito credito) throws LogicaException {
		if (credito == null){
			throw new NullPointerException("El crédito no puede ser nulo");
		}
		
		if (credito.getId() == null){
			this.creditoDao.agregar(credito);
		}
		else{
			this.creditoDao.modificar(credito);
		}
	}

}
