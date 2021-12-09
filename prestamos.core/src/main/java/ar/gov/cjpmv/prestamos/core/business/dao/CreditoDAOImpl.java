package ar.gov.cjpmv.prestamos.core.business.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.ejb.HibernateQuery;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.CuotaComparator;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionCuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cancelacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cobro;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroPorCancelacionCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class CreditoDAOImpl extends GenericDAOImpl<Credito> implements CreditoDAO {

	@Override
	public Credito getObjetoPorId(Long clave) {
		return this.entityManager.find(Credito.class, clave);
	}

	public CuentaCorriente getCuentaCorriente(Persona pPersona) throws LogicaException {
		try {
			Query locQuery = this.entityManager.createNamedQuery("getCuentaCorriente");
			locQuery.setParameter("persona", pPersona);
			return (CuentaCorriente) locQuery.getSingleResult();
		} catch (Exception e) {
			int locCodigoMensaje = 29;
			String locCampoMensaje = "";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
	}
	
	
	public CuentaCorriente getCuentaCorrienteGarantia(Persona pPersona) {
		try {
			Query locQuery = this.entityManager.createNamedQuery("getCuentaCorriente");
			locQuery.setParameter("persona", pPersona);
			return (CuentaCorriente) locQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Credito> findListaCreditosEnCurso(
			CuentaCorriente pCuentaCorriente) {
		String locConsulta = "select g from Credito g where g.cuentaCorriente=:locCuentaCorriente and g.estado!= :locEstado";
		Query locQuery = entityManager.createQuery(locConsulta);
		locQuery.setParameter("locCuentaCorriente", pCuentaCorriente);
		locQuery.setParameter("locEstado", EstadoCredito.FINALIZADO);
		return locQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ViaCobro> getListaViasCobro() {
		return this.entityManager.createQuery("select v from ViaCobro v")
				.getResultList();
	}
	
	public List<ViaCobro> getListaViasCobroReporteCobranzas() {
		String consulta= "select v from ViaCobro v where upper(v.nombre)!= :pNombre";
		Query locQuery= this.entityManager.createQuery(consulta);
		String viaRemovida= "incobrable";
		locQuery.setParameter("pNombre", viaRemovida.toUpperCase());
		return locQuery.getResultList();
	}

	public CuentaCorriente generarCuentaCorriente(Persona pPersona) throws LogicaException {
		Query locQuery = this.entityManager.createNamedQuery("getCuentaCorriente");
		locQuery.setParameter("persona", pPersona);
		if (!locQuery.getResultList().isEmpty()) {
			throw new LogicaException(30, "Persona");
		}
		CuentaCorriente locCuentaCorriente = new CuentaCorriente();
		locCuentaCorriente.setFechaCreacion(Calendar.getInstance().getTime());
		locCuentaCorriente.setSobrante(new BigDecimal(0));
		locCuentaCorriente.setPersona(pPersona);
		
		
		EntityTransaction locTransaction = this.entityManager.getTransaction();
		try{
			locTransaction.begin();
			this.entityManager.persist(locCuentaCorriente);
			locTransaction.commit();
			this.entityManager.refresh(locCuentaCorriente);
		}
		catch(Exception e){
			if ((locTransaction != null) &&(locTransaction.isActive())){
				locTransaction.rollback();
			}
			e.printStackTrace();
			throw new LogicaException(32,null);
		}
		return locCuentaCorriente;
	}
	
	@Override
	public void validarAlta(Credito pObjeto) throws LogicaException {
		this.validar(pObjeto);
		this.validarCheques(pObjeto);
		this.generarCuentasCorrientesGarantes(pObjeto);
	}
	
	/**
	 * Genera las cuentas corrientes de los garantesque están faltando en el crédito 
	 * @param pCredito
	 */
	private void generarCuentasCorrientesGarantes(Credito pCredito) throws LogicaException{
		for (Garantia cadaGarantia : pCredito.getListaGarantias()){
			if (cadaGarantia instanceof GarantiaPersonal){
				GarantiaPersonal garantia = (GarantiaPersonal)cadaGarantia;
				CuentaCorriente locCuentaCorriente = null;
				try {
					locCuentaCorriente = this.getCuentaCorriente(garantia.getGarante());
				} catch (LogicaException e) {
					locCuentaCorriente = this.generarCuentaCorriente(garantia.getGarante());
				}
			}
		}
	}

	/**
	 * Valida todos los cheques de un crédito
	 * @param pObjeto
	 * @throws LogicaException
	 */
	private void validarCheques(Credito pObjeto) throws LogicaException{
		for (Cheque cadaCheque : pObjeto.getListaCheques()){
			this.validarCheque(cadaCheque);
		}
	}

	/**
	 * Valida un cheque
	 * @param pCheque
	 */
	public void validarCheque(Cheque pCheque) throws LogicaException{
		if (pCheque.getCanceladoPor() != null){
			if (pCheque.getCanceladoPor().equals(pCheque)){
				throw new LogicaException(43,"cheque de cancelación");
			}
			pCheque.setEstadoCheque(EstadoCheque.CANCELADO);
		}
		
		if (pCheque.getMonto().floatValue()<=0){
			throw new LogicaException(44, "monto");
		}
		
		if (pCheque.getEmitidoA() == null && pCheque.getNombrePersona() == null){
			throw new LogicaException(45, "cheque de cancelación");
		}
		else if (pCheque.getEmitidoA() != null){
			pCheque.setNombrePersona(null);
		}
		else if (pCheque.getNombrePersona().trim().isEmpty()){
			throw new LogicaException(45, "cheque de cancelación");
		}
		
		EstadoCheque estado = pCheque.getEstadoCheque();
		
		if (estado.equals(EstadoCheque.IMPRESO)){
			if (pCheque.getFechaEmision()==null || pCheque.getFechaPago() == null){
				throw new LogicaException(46, "fechas");
			}
		}

		if (estado.equals(EstadoCheque.IMPRESO)|| estado.equals(EstadoCheque.CANCELADO)) {
			if (pCheque.getNumero() == null) {
				throw new LogicaException(47, "numero de cheque");				
			}
		}
		
		if (pCheque.getFechaEmision()!=null && pCheque.getFechaPago() != null){
			if (!DateUtils.isSameDay(pCheque.getFechaEmision(), pCheque.getFechaPago())){
				if (pCheque.getFechaEmision().after(pCheque.getFechaPago())){
					throw new LogicaException(48, "fechas");
				}		
			}
		}
		
		if (pCheque.getCuenta() == null){
			String numeroCheque = pCheque.getNumero()!=null?pCheque.getNumero().toString():"Sin número";
			throw new LogicaException(49,numeroCheque);
		}
	}

	private void validarGarantias(Credito pObjeto) {
		Iterator<Garantia> iteradorGarantia = pObjeto.getListaGarantias().iterator();
		while(iteradorGarantia.hasNext()){
			Garantia cadaGarantia = iteradorGarantia.next();
			if (cadaGarantia instanceof GarantiaPersonal){
				Persona locPersona = ((GarantiaPersonal)cadaGarantia).getGarante();
				if (locPersona.equals(pObjeto.getCuentaCorriente().getPersona())){
					iteradorGarantia.remove();
				}
			}
		}
	}

	private void validarNumeroCredito(Integer numeroCredito) throws LogicaException {
		Query locQuery = this.entityManager.createQuery("select e from Credito e where e.numeroCredito = :numero");
		locQuery.setParameter("numero", numeroCredito);
		
		@SuppressWarnings("unchecked")
		List<Credito> locRetorno = locQuery.getResultList();
		if (!locRetorno.isEmpty()){
			throw new LogicaException(35,locRetorno.get(0).toString());
		}
	}

	private Integer getUltimoNumeroCredito() {
		return (Integer)this.entityManager.createQuery("select max(e.numeroCredito) from Credito e").getSingleResult();
	}

	private void validarAtributos(Credito pObjeto) throws LogicaException {
		boolean error = false;
		Map<String,String> listaAtributosNulos = new HashMap<String,String>();
		
		if (pObjeto.getNumeroCredito() == null || pObjeto.getNumeroCredito()<1){
			error = true;
			listaAtributosNulos.put("Número de Crédito", "debe ser un número mayor que cero");
		}
		if (pObjeto.getMontoTotal() == null || pObjeto.getMontoTotal().floatValue() <= 0){
			error=true;
			listaAtributosNulos.put("Monto total", "debe ser mayor o igual a cero");
		}
		if (pObjeto.getTasa() == null || pObjeto.getTasa().floatValue() <0){
			error =true;
			listaAtributosNulos.put("Interés anual", "debe ser mayor o igual a cero");
		}
		if (pObjeto.getMontoEntrega() == null || pObjeto.getMontoEntrega().floatValue() < 0){
			error=true;
			listaAtributosNulos.put("Monto de entrega", "debe ser mayor o igual a cero");
		}
		if (pObjeto.getFechaInicio() == null){
			error = true;
			listaAtributosNulos.put("Fecha de Inicio","no puede estar vacía");
		}
		if (pObjeto.getTipoCredito() == null){
			error = true;
			listaAtributosNulos.put("Tipo de crédito", "no puede estar vacío");
		}
		
		if (pObjeto.getCantidadCuotas() == null || pObjeto.getCantidadCuotas()<=0 
				|| pObjeto.getListaCuotas() == null || pObjeto.getListaCuotas().isEmpty() 
				|| pObjeto.getListaCuotas().size() != pObjeto.getCantidadCuotas())
		{
			error = true;
			listaAtributosNulos.put("Cantidad de cuotas", "No puede estar vacía<br/>Pulse el botón Generar Cuotas para actualizar el listado");
		}
		
		if (pObjeto.getViaCobro() == null){
			error = true;
			listaAtributosNulos.put("Vía de cobro", "no puede estar vacía");
		}
		
//		if (pObjeto.getDiaVencimieto() == null || pObjeto.getDiaVencimieto()<=0 || pObjeto.getDiaVencimieto()>31 ) {
//			error = true;
//			listaAtributosNulos.put("Día de vencimiento", "No puede estar vacío ni contener dias inválidos");
//		}
//		
		if (error){
			String locCadena = "";
			for (Entry<String,String> cadaEntrada : listaAtributosNulos.entrySet()){
				locCadena+= "<br/><b>"+cadaEntrada.getKey()+"</b> "+cadaEntrada.getValue();
			}
			locCadena+="<br>Verifique los datos ingresados";
			throw new LogicaException(33, locCadena);
		}
		
	}

	/**
	 * Valida el crédito, pero no valida los cheques asociados
	 * @param pCredito
	 * @throws LogicaException
	 */
	public void validar(Credito pCredito) throws LogicaException {
		super.validarAlta(pCredito);
		if (pCredito.getCuentaCorriente() == null){
			throw new LogicaException(42, "cuenta corriente");
		}
		this.validarAtributos(pCredito);
		if (pCredito.getNumeroCredito()==null){
			pCredito.setNumeroCredito(this.getUltimoNumeroCredito()+1);
		}
		else{
			this.validarNumeroCredito(pCredito.getNumeroCredito());
		}
		this.validarGarantias(pCredito);
	}
	
	/**
	 * Obtiene todas las cuotas impagas de una cuenta corriente que se encuentre entre las fechas dadas
	 * @param pFechaDesde
	 * @param pFechaHasta
	 * @param pCuentaCorriente
	 */
	public SortedSet<Cuota> getListaCuotasImpagas(
			Date pFechaDesde, 
			Date pFechaHasta, 
			CuentaCorriente pCuentaCorriente) {
		if (pCuentaCorriente == null) {
			throw new NullPointerException("La cuenta corriente no puede ser nula");
		}
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager(); 
		try {
				
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("ctacte", pCuentaCorriente);
			
			String consulta = "select c from Cuota c " +
							  "		left join c.credito.listaGarantias g" +
							  "  where" +
							  "	 (	 (" +
							  "			c.credito.cuentaCorriente = :ctacte" +
							  "         		and" +
							  "	     	 c.credito.cobrarAGarante = false" +
							  "		 )" +
							  "    or" +
							  "       (" +
							  "			 c.credito.cobrarAGarante = true" +
							  "				and" +
							  "		     g.afectarA = :ctacte" +
							  "   	  )" +
							  "  )" +
							  "	  and c.cancelacion is null";
			
			if (pFechaDesde != null) {
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(pFechaDesde);
				Integer mesDesde = calendario.get(Calendar.MONTH) + 1;
				Integer anioDesde = calendario.get(Calendar.YEAR);
				parametros.put("mesDesde", mesDesde);
				parametros.put("anioDesde", anioDesde);
				consulta += " and ( year(c.vencimiento) = :anioDesde " +
							"		and" +
							"	 	month(c.vencimiento) >= :mesDesde" +
							"	  )" +
							" 	  or " +
							"	  year(c.vencimiento) < :anioDesde)";
			}
			
			if (pFechaHasta != null) {
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(pFechaHasta);
				Integer mesHasta = calendario.get(Calendar.MONTH) + 1;
				Integer anioHasta = calendario.get(Calendar.YEAR);
				parametros.put("mesHasta", mesHasta);
				parametros.put("anioHasta", anioHasta);
				consulta += " and( " +
							"	  ( year(c.vencimiento) = :anioHasta " +
							"		and" +
							"	 	month(c.vencimiento) <= :mesHasta" +
							"	  )" +
							" 	  or " +
							"	  year(c.vencimiento) < :anioHasta" +
							")";
			}
			consulta += " order by c.vencimiento asc";
	
			Query query = entityManager.createQuery(consulta);
			for (Entry<String,Object> cadaParametro : parametros.entrySet()) {
				String clave = cadaParametro.getKey();
				Object valor = cadaParametro.getValue();
				query.setParameter(clave, valor);
			}
			
			@SuppressWarnings("unchecked")
			List<Cuota> resultado = query.getResultList();
			SortedSet<Cuota> set = new TreeSet<Cuota>(new CuotaComparator());
			set.addAll(resultado);
			return set;
		}
		finally {
			entityManager.close();
		}
	}
	
	public Credito findCreditoPorNumero(Integer pNumeroCredito) {
		String consulta="select c from Credito c where c.numeroCredito= :pNumero and c.estado!= :pEstado";
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("pNumero", pNumeroCredito);
		locQuery.setParameter("pEstado", EstadoCredito.FINALIZADO);
		try{
			return (Credito) locQuery.getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
		catch(NonUniqueResultException e){
			return null;
		}
	}

	//TODO MEJORAR ESTA CONSULTA PARA QUE VAYA MÁS RAPIDO
	public List<Credito> getListaCreditoPorCantidadCtasAdeudadas(
			Integer cantidadCuotasVencidas) {
		String consulta="select c from Credito c where c.estado!= :pEstado";
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("pEstado", EstadoCredito.FINALIZADO);
		@SuppressWarnings("unchecked")
		List<Credito> resultado = locQuery.getResultList();

		List<Credito> listaCreditosConCuotasVencidas= new ArrayList<Credito>();
		if(!resultado.isEmpty()) {
			for(Credito cadaCredito: resultado ){
				if(!cadaCredito.getListaGarantias().isEmpty()){
					if(cadaCredito.getCuotasAdeudadasVencidas()==cantidadCuotasVencidas){
						listaCreditosConCuotasVencidas.add(cadaCredito);
					}
				}
			}
			if(!listaCreditosConCuotasVencidas.isEmpty()){
				return listaCreditosConCuotasVencidas;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Credito> getCreditosNoFinalizados(Long pLegajo, Long pCui) {
		String consulta = "select distinct cred from Credito cred inner join cred.cuentaCorriente c inner join c.persona p, PersonaFisica pf, PersonaJuridica pj" +
		" where (pf = p or pj = p) and cred.estado!= :pEstado";
		if (pLegajo != null){
			consulta+=" and pf.legajo = :pLegajo and p.class = PersonaFisica";
		}
		if (pCui != null){
			consulta+=" and p.cui = :pCui ";
		}
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("pEstado", EstadoCredito.FINALIZADO);
		if (pLegajo != null){
			locQuery.setParameter("pLegajo", pLegajo);
		}
		if (pCui != null){
			locQuery.setParameter("pCui", pCui);
		}
		if(!locQuery.getResultList().isEmpty()){
			return  locQuery.getResultList();
		}
		return null;
		
	}

	private CobroPorCancelacionCredito getCobroPorCancelacionCredito(
			Date pFechaCancelacion,
			CuentaCorriente pCuentaCorriente,
			List<Credito> pListaCreditos) throws LogicaException{
		
		if (pFechaCancelacion == null ) {
			throw new NullPointerException("la fecha de inicio del crédito otorgado no puede estar nula");
		}
		
		BigDecimal total = new BigDecimal(0);
		List<Cancelacion> listaCancelacionesCreditos = new ArrayList<Cancelacion>();
		
		CobroPorCancelacionCredito cobro = new CobroPorCancelacionCredito();
		cobro.setCuentaCorriente(pCuentaCorriente);
		cobro.setPagador(pCuentaCorriente.getPersona());
		cobro.setSobranteAlDia(pCuentaCorriente.getSobrante());
		cobro.setFecha(pFechaCancelacion);
		cobro.setListaCancelaciones(listaCancelacionesCreditos);
		
		for (Credito cadaCredito : pListaCreditos){
			total = total.add(cadaCredito.getSaldoAdeudado());
			listaCancelacionesCreditos.addAll(getCancelaciones(cobro, cadaCredito));
			cadaCredito.setEstado(EstadoCredito.FINALIZADO);
			cadaCredito.setFechaFin(Calendar.getInstance().getTime());
		}
		cobro.setMonto(total.subtract(pCuentaCorriente.getSobrante()));		
		return cobro;
	}

	public BigDecimal getMontoCancelacion(List<Credito> pListaCreditos) {
		BigDecimal total = new BigDecimal(0);
		for (Credito cadaCredito : pListaCreditos){
			total = total.add(cadaCredito.getSaldoAdeudado());
		}
		return total;
	}
	
	private List<Cancelacion> getCancelaciones(Cobro pCobro, Credito cadaCredito) {
		List<Cancelacion> locListaCancelaciones = new ArrayList<Cancelacion>();
		for (Cuota cadaCuota : cadaCredito.getListaCuotas()){
			SituacionCuota situacion = cadaCuota.getSituacion();
			if (!situacion.equals(SituacionCuota.CANCELADA)){
				Cancelacion cancelacion = new Cancelacion();
				cancelacion.setCobro(pCobro);
				if (situacion.equals(SituacionCuota.VENCIDA)){
					cancelacion.setMonto(cadaCuota.getTotal());
				}
				else{
					cancelacion.setMonto(cadaCuota.getTotalSinInteres());
				}
				cadaCuota.setCancelacion(cancelacion);
				locListaCancelaciones.add(cancelacion);
			}
		}
		return locListaCancelaciones;
	}

	@SuppressWarnings("unchecked")
	public List<Credito> getListaCreditosImpagos(CuentaCorriente cuentaCorriente) {
		Query consulta = this.entityManager.createQuery("select distinct c " +
				" from Credito c inner join c.listaCuotas cuota " +
				" where cuota.cancelacion = null and c.cuentaCorriente = :ctacte");
		consulta.setParameter("ctacte", cuentaCorriente);
		return consulta.getResultList();
	}

	/**
	 * Guarda un crédito por cancelación
	 * @param pCredito crédito a guardar
	 * @param pListaCreditos lista créditos a cancelar
	 * @param pDetalleCredito 
	 *
	 */
	//FIXME reemplazar esto por algo mejor
	public long guardarCreditoPorFinalizacion(Credito pCredito,
			CuentaCorriente pCuentaCorriente, 
			List<Credito> pListaCreditos, 
			DetalleCredito pDetalleCredito) throws LogicaException
	{
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try{
			tx.begin();
			Date fechaCancelacion = pCredito.getFechaInicio();
			CobroPorCancelacionCredito cancelacion = getCobroPorCancelacionCredito(
											fechaCancelacion,
											pCuentaCorriente, 
											pListaCreditos);
			pCredito.getDetalleCredito().add(pDetalleCredito);
			cancelacion.setDetalleCreditoCancelaciones(pDetalleCredito);
			CuentaCorriente cuenta = pCredito.getCuentaCorriente();
			cuenta.getListaCredito().add(pCredito);
			
			entityManager.persist(pCredito);
			entityManager.flush();
			entityManager.persist(cancelacion);
			entityManager.flush();
			entityManager.merge(pCredito.getCuentaCorriente());
			entityManager.flush();
			for (Credito credito : pListaCreditos) {
				entityManager.merge(credito);
				entityManager.flush();
			}
			
			tx.commit();
			entityManager.refresh(pCredito);
			return pCredito.getId();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx!= null & tx.isActive()){
				tx.rollback();
			}
			throw new LogicaException(22,null);
		}
		finally {
			entityManager.close();
		}
		
	}
	

	@Override
	public void eliminar(Credito pCredito) throws LogicaException {
		EntityTransaction locTransaction = this.entityManager.getTransaction();
		try {
			locTransaction.begin();
			this.validarEliminar(pCredito);
			this.entityManager.merge(pCredito);
			eliminarCancelacionesDeCreditoFinalizacion(pCredito);
		
			//Elimina incluso las liquidaciones
			@SuppressWarnings("unchecked")
			List<DetalleLiquidacion> listaDetalles = this.entityManager
							.createQuery(
								"select d from DetalleLiquidacion d " +
								"inner join d.listaCuotas cuot " +
								" where cuot.credito = :credito")
							.setParameter("credito", pCredito)
							.getResultList();
			
			for (DetalleLiquidacion cadaDetalle : listaDetalles) {
				cadaDetalle.getListaCuotas().removeAll(pCredito.getListaCuotas());
				this.entityManager.merge(cadaDetalle);
			}
			this.entityManager.remove(pCredito);
			locTransaction.commit();
		}
		catch(LogicaException e){
			if(locTransaction.isActive()){
				locTransaction.rollback();	
			}
			throw e;
		}
		catch(Exception e){
			if(locTransaction.isActive()){
				locTransaction.rollback();	
			}
			this.lanzarExcepcionDesconocida(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void eliminarCancelacionesDeCreditoFinalizacion(Credito pCredito) {
		Query query = this.entityManager.createQuery("select cobro " +
				"from CobroPorCancelacionCredito cobro " +
				"where cobro." +
				"	   detalleCreditoCancelaciones." +
				"	   credito = :credito");
		query.setParameter("credito", pCredito);
		List<CobroPorCancelacionCredito> listaCobro = query.getResultList();
		
		
		for (CobroPorCancelacionCredito cadaCobro : listaCobro) {
			List<Cancelacion> cancelaciones = cadaCobro.getListaCancelaciones();
			if (cancelaciones != null && !cancelaciones.isEmpty()) {
				HibernateQuery queryCancelaciones = (HibernateQuery)this.entityManager
						.createQuery("select c " +
								"from Cuota c " +
								"where c.cancelacion in (:cancelaciones)");
				org.hibernate.Query queryHibernate = queryCancelaciones.getHibernateQuery();
				queryHibernate.setParameterList("cancelaciones", cancelaciones);
			
				List<Cuota> creditosConCancelaciones = queryHibernate.list();
				for (Cuota cadaCuota : creditosConCancelaciones) {
					cadaCuota.setCancelacion(null);
					cadaCuota.getCredito().setEstado(EstadoCredito.OTORGADO);
					this.entityManager.merge(cadaCuota.getCredito());
				}
			}
			this.entityManager.remove(cadaCobro);
		}
	}

//METODO CORREGIDO
	@Override
	protected void validarEliminar(Credito pObjeto) throws LogicaException {
		super.validarEliminar(pObjeto);
		Long cantidad = (Long)this.entityManager.createQuery("select count(cuot) from Credito c " +
									   " inner join c.listaCuotas cuot" +
									   " where c= :credito and cuot.cancelacion is not null ")
									   .setParameter("credito", pObjeto)
									   .getSingleResult();
		if (cantidad>0){
			throw new LogicaException(90,null);
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<Credito> getCreditosPorTipoYViaEnCurso(
			TipoCredito tipoCredito, ViaCobro pViaCobro, boolean pSoloSeguros, Date pFechaHasta) 
	{
		Concepto pConceptoSeguro=null;
		
		String consulta = "select distinct c from Credito c";
	
		if(pSoloSeguros){
			consulta+=" inner join c.listaCuotas ctas"+
					  " inner join ctas.listaDetallesCreditos det"+
					  " where det.fuente= :pConcepto and" ;
			
			ConceptoDAO locConceptoDAO= new ConceptoDAO();
			if(locConceptoDAO.findListaConcepto("Seguro de Vida")!=null){
				pConceptoSeguro=(Concepto) locConceptoDAO.findListaConcepto("Seguro de Vida").get(0);;
			}
		}
		else{
			consulta+="	where";
		}

		consulta+= " (c.fechaInicio <= :pFechaHasta)";
		consulta+= " and (c.fechaFin is null or c.fechaFin>:pFechaHasta)";

		if(tipoCredito != null){
			consulta+=" and c.tipoCredito= :pTipoCredito";
		}
		
		if(pViaCobro != null){
			consulta+=" and c.viaCobro= :pViaCobro";
		}
		
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("pFechaHasta", pFechaHasta, TemporalType.DATE);
		
		if(pViaCobro != null){
			locQuery.setParameter("pViaCobro", pViaCobro);
		}
		
		if(tipoCredito != null){
			locQuery.setParameter("pTipoCredito", tipoCredito);
		}
		
		
		if(pSoloSeguros){
			locQuery.setParameter("pConcepto", pConceptoSeguro);
		}
		
		if(!locQuery.getResultList().isEmpty()){
			return  locQuery.getResultList();
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Credito> getListaCreditosPropiosImpagos(CuentaCorriente pCuentaCorriente, 
			ViaCobro pViaCobro) 
	{
		Query consulta = this.entityManager.createQuery(
									"select c from Credito c " +
									"	where " +
									"			c.cuentaCorriente = :cuentaCorriente" +
									"		and c.fechaFin is null" +
									" 		and c.cobrarAGarante = false" +
									"		and c.viaCobro = :viaCobro"
								);
		consulta.setParameter("cuentaCorriente", pCuentaCorriente);
		consulta.setParameter("viaCobro",pViaCobro);
		return consulta.getResultList();
	}
	
	
	/**
	 * Obtiene la lista de cuotas impagas de un crédito hasta el mes de vencimiento y la retorna ordenada
	 * @param credito
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Cuota> getListaCuotasImpagas(Credito credito,Date pFecha) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager(); 
		try {
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(pFecha);
			int anio = calendario.get(Calendar.YEAR);
			//En Calendar los meses van de 0 a 11
			int mes = calendario.get(Calendar.MONTH) + 1;
			Query consulta = entityManager
					.createQuery("select c from Cuota c left join c.liquidaciones liq" +
									"	where " +
									"		 c.credito = :credito" +
									"    and c.cancelacion is null" +
									"	 and ( " +
									"			year(c.vencimiento) < :anio or " +
									"			(" +
									"				year(c.vencimiento) = :anio and" +
									"				month(c.vencimiento) <= :mes" +
									"			)" +
									"		)" +
									" order by c.vencimiento asc");
			consulta.setParameter("credito", credito);
			consulta.setParameter("anio", anio);
			consulta.setParameter("mes", mes);
			return consulta.getResultList();
		}
		finally{
			entityManager.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Credito> findCreditosPorNumeroYLegajo(Long legajo,
			Integer numeroCredito) {
		
		String consulta="select c from Credito c";
	    List<Persona> listaPersonas= new ArrayList<Persona>();
		boolean isLegajo=false;
		boolean isNumeroCredito=false;
		if(legajo!=null){
			String consultaPersona="select p from PersonaFisica p where p.legajo= :pLegajo";
			Query locQueryPersona= this.entityManager.createQuery(consultaPersona);
			locQueryPersona.setParameter("pLegajo", legajo);
			if(!locQueryPersona.getResultList().isEmpty()){
				listaPersonas= (List<Persona>)locQueryPersona.getResultList();
				isLegajo= true;
				consulta+=" where c.cuentaCorriente.persona in (:pListaPersonas)";
			}
		}
		if(numeroCredito!=null){
			if(isLegajo){
				consulta+=" and";
			}
			else{
				consulta+=" where";
			}
			isNumeroCredito= true;
			consulta+=" c.numeroCredito= :pNumeroCredito";
		}		
		Query locQuery= this.entityManager.createQuery(consulta);
		if(isLegajo){
			locQuery.setParameter("pListaPersonas", listaPersonas);
		}
		if(isNumeroCredito){
			locQuery.setParameter("pNumeroCredito", numeroCredito);
		}
		
		if(!locQuery.getResultList().isEmpty()){
			return locQuery.getResultList();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Credito> findCreditosOtorgados(Date pFechaDesde, Date pFechaHasta, TipoCredito pTipoCredito, ViaCobro pViaCobro) {
		String consulta="select c from Credito c"+
						" where ((c.fechaInicio = :fechaDesde)" +
						" or (c.fechaInicio = :fechaHasta)" +
						" or ((c.fechaInicio > :fechaDesde) and (c.fechaInicio < :fechaHasta)))";
			
		
		if(pTipoCredito!=null){
			consulta+=" and c.tipoCredito= :tipoCredito ";
		}
		
		if(pViaCobro!=null){
			consulta+=" and c.viaCobro= :viaCobro ";
		}
		consulta+=" order by c.fechaInicio asc";
		
		
		
		Query locQuery= entityManager.createQuery(consulta);
		locQuery.setParameter("fechaDesde", pFechaDesde, TemporalType.DATE);
		locQuery.setParameter("fechaHasta", pFechaHasta, TemporalType.DATE);
		if(pTipoCredito!=null){
			locQuery.setParameter("tipoCredito", pTipoCredito);
		}
		if(pViaCobro!=null){
			locQuery.setParameter("viaCobro", pViaCobro);
		}		
		
		if(!locQuery.getResultList().isEmpty()){
			return locQuery.getResultList();
		}
		return null;
	}

	
	
	//-----------------------------------TODO VER COMO SE REPARTE
	
	@Override
	public BigDecimal getTotalCobrado(List<Liquidacion> pLiquidaciones) {
		if (pLiquidaciones == null || pLiquidaciones.isEmpty()) {
			throw new NullPointerException("las liquidaciones no pueden ser nulas");
		}
		
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			HibernateQuery consulta = (HibernateQuery)entityManager.createQuery(
					"select sum(cobroDetalle.monto) " +
					"from CobroDetalleLiquidacion cobroDetalle " +
					"	inner join cobroDetalle.cobroLiquidacion cobroLiq " +
					"where cobroLiq.liquidacion in (:liquidaciones) ");
			
			org.hibernate.Query query = consulta.getHibernateQuery();
			query.setParameterList("liquidaciones", pLiquidaciones);
			return (BigDecimal)query.uniqueResult();
		}
		finally {
			entityManager.close();
		}
	}

	@Override
	public BigDecimal getTotalCuotasCanceladas(List<Liquidacion> pLiquidaciones) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			HibernateQuery consulta = (HibernateQuery)entityManager.createQuery(
					"select sum(c.capital)+sum(c.interes)+sum(c.otrosConceptos) " +
					"from CobroDetalleLiquidacion cobroDetalle, Cuota c " +
					"inner join cobroDetalle.listaCancelaciones can " +
					"inner join cobroDetalle.cobroLiquidacion cobLiq " +
					"where cobLiq.liquidacion in (:liquidaciones) " +
					"and can = c.cancelacion");
			
			org.hibernate.Query query = consulta.getHibernateQuery();
			query.setParameterList("liquidaciones", pLiquidaciones);
			return (BigDecimal)query.uniqueResult();
		}
		finally {
			entityManager.close();
		}
	}

	@Override
	public BigDecimal getTotalCapital(List<Liquidacion> pLiquidaciones,
			TipoCredito pTipoCredito) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			HibernateQuery consulta = (HibernateQuery)entityManager.createQuery(
					"select sum(c.capital) " +
					"from CobroDetalleLiquidacion cobroDetalle, Cuota c " +
					"inner join cobroDetalle.listaCancelaciones can " +
					"inner join cobroDetalle.cobroLiquidacion cobLiq " +
					"where cobLiq.liquidacion in (:liquidaciones) " +
					"and can = c.cancelacion "+
					"and c.credito.tipoCredito = :tipo");
			
			org.hibernate.Query query = consulta.getHibernateQuery();
			query.setParameterList("liquidaciones", pLiquidaciones);
			query.setParameter("tipo", pTipoCredito);
			return (BigDecimal)query.uniqueResult();
		}
		finally {
			entityManager.close();
		}
	}

	@Override
	public BigDecimal getTotalInteres(List<Liquidacion> pLiquidaciones) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			HibernateQuery consulta = (HibernateQuery)entityManager.createQuery(
					"select sum(c.interes) " +
					"from CobroDetalleLiquidacion cobroDetalle, Cuota c " +
					"inner join cobroDetalle.listaCancelaciones can " +
					"inner join cobroDetalle.cobroLiquidacion cobLiq " +
					"where cobLiq.liquidacion in (:liquidaciones)" +
					"and can = c.cancelacion");
			
			org.hibernate.Query query = consulta.getHibernateQuery();
			query.setParameterList("liquidaciones", pLiquidaciones);
			return (BigDecimal)query.uniqueResult();
		}
		finally {
			entityManager.close();
		}
	}

	@Override
	public BigDecimal getTotalOtrosConceptos(List<Liquidacion> pLiquidaciones) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			HibernateQuery consulta = (HibernateQuery)entityManager.createQuery(
					"select sum(c.otrosConceptos) " +
					"from CobroDetalleLiquidacion cobroDetalle, Cuota c " +
					"inner join cobroDetalle.listaCancelaciones can " +
					"inner join cobroDetalle.cobroLiquidacion cobLiq " +
					"where cobLiq.liquidacion in (:liquidaciones)" +
					"and can = c.cancelacion");
			
			org.hibernate.Query query = consulta.getHibernateQuery();
			query.setParameterList("liquidaciones", pLiquidaciones);
			return (BigDecimal)query.uniqueResult();
		}
		finally {
			entityManager.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Liquidacion> getLiquidaciones(Date pFechaDesde,
			Date pFechaHasta, ViaCobro viaCobro) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Query consulta = entityManager.createQuery("select l from Liquidacion l" +
					" where l.fechaLiquidacion between :fechaDesde and :fechaHasta " +
					"		and l.viaCobro = :viaCobro");
			consulta.setParameter("fechaDesde", pFechaDesde,TemporalType.DATE);
			consulta.setParameter("fechaHasta", pFechaHasta,TemporalType.DATE);
			consulta.setParameter("viaCobro", viaCobro);
			return consulta.getResultList();
		}
		finally {
			entityManager.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoCredito> getListaTiposCreditosAfectados(
			List<Liquidacion> pLiquidaciones) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			HibernateQuery consulta = (HibernateQuery)entityManager.createQuery(
					"select distinct tipo from " +
					"DetalleLiquidacion det " +
					"	inner join det.listaCuotas cuota " +
					"	inner join cuota.credito cred " +
					"	inner join cred.tipoCredito tipo " +
					"where det.liquidacion in (:liquidaciones)");
			
			org.hibernate.Query query = consulta.getHibernateQuery(); 
			query.setParameterList("liquidaciones", pLiquidaciones);
			return query.list();
		}
		finally {
			entityManager.close();
		}
	}

	@Override
	public BigDecimal getSaldoCapital(TipoCredito pTipo, Date fechaCierre) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Query consulta = entityManager.createQuery(
					"select sum(c.capital) from Cuota c "+
					"	inner join c.credito cred " +
					"	left join c.cancelacion.cobro cobro " +
					"where " +
					"( c.cancelacion is null or cobro > :fechaCierre ) "+
					"and "+ 
					"cred.fechaInicio <= :fechaCierre "+
					"and " +
					"( cred.fechaFin is null or cred.fechaFin > :fechaCierre ) "+
					"and " +
					"cred.tipoCredito = :tipo");
			
			consulta.setParameter("tipo", pTipo);
			consulta.setParameter("fechaCierre", fechaCierre,TemporalType.DATE);
			return (BigDecimal)consulta.getSingleResult();
		}
		finally {
			entityManager.close();
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Integer> getCreditoCanceladoEnOtorgamiento(DetalleCredito locDetalleCredito){
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			String consulta= "SELECT creditoCancelado.numeroCredito "+
								"FROM `cobro` AS cobro "+
								"INNER JOIN cobroporcancelacioncredito AS cobroCancelacionCredito ON cobro.id = cobroCancelacionCredito.id "+
								"INNER JOIN detalleCredito AS detalleCredito ON detalleCreditoCancelaciones_id = detalleCredito.id "+
								"INNER JOIN cancelacion AS cancelacion ON cobro.id = cancelacion.cobro_id "+
								"INNER JOIN cuota AS cuota ON cuota.cancelacion_id = cancelacion.id "+
								"INNER JOIN credito AS creditoCancelado ON cuota.credito_id = creditoCancelado.id "+
								"WHERE detalleCredito.id = :idDetalleCredito "+
								"GROUP BY creditoCancelado.numeroCredito";
		
			Query locQuery= entityManager.createNativeQuery(consulta);
			locQuery.setParameter("idDetalleCredito", locDetalleCredito.getId());
			List<Integer> lista=locQuery.getResultList();
			return lista;
		}
		finally {
			entityManager.close();
		}
			
	}
	
	public BigDecimal getMontoCanceladoEnOtorgamiento(DetalleCredito locDetalleCredito, Integer numeroCredito){
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			String consulta= "SELECT SUM(cancelacion.monto) "+
					"FROM `cobro` AS cobro "+
					"INNER JOIN cobroporcancelacioncredito AS cobroCancelacionCredito ON cobro.id = cobroCancelacionCredito.id "+
					"INNER JOIN detalleCredito AS detalleCredito ON detalleCreditoCancelaciones_id = detalleCredito.id "+
					"INNER JOIN cancelacion AS cancelacion ON cobro.id = cancelacion.cobro_id "+
					"INNER JOIN cuota AS cuota ON cuota.cancelacion_id = cancelacion.id "+
					"INNER JOIN credito AS creditoCancelado ON cuota.credito_id = creditoCancelado.id "+
					"WHERE detalleCredito.id = :idDetalleCredito "+
					"AND creditoCancelado.numeroCredito= :numeroCredito";
			Query locQuery= entityManager.createNativeQuery(consulta);
			locQuery.setParameter("idDetalleCredito", locDetalleCredito.getId());
			locQuery.setParameter("numeroCredito", numeroCredito);
			return (BigDecimal) locQuery.getResultList().get(0);
		}
		finally {
			entityManager.close();
		}
		
		
		
		
		
		
	}
	
	
	/*
	 * FIXME ELIMINAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(2010, Calendar.DECEMBER,31); 
		Date fecha = calendario.getTime();
		CreditoDAO dao = DAOFactory.getDefecto().getCreditoDAO();
		List<TipoCredito> listaTipos = GestorPersitencia.getInstance().getEntityManager().createQuery("from TipoCredito").getResultList();
		BigDecimal total = new BigDecimal("0.00"); 
		for (TipoCredito cadaTipo : listaTipos) {
			BigDecimal capital = dao.getSaldoCapital(cadaTipo, fecha);
			System.out.println(cadaTipo+" : "+capital);
			if (capital != null) {
				total = total.add(capital);
			}
		}
		System.out.println("Total: "+total);
	}
}
