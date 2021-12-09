package ar.gov.cjpmv.prestamos.core.business.prestamos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cancelacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroPorBanco;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;

public class CobroPorBancoDAO {
	
	private EntityManager entityManager;
	private CobroPorBanco cobroPorBanco;
	private List<Cuota> listaCuotas;
	

	
	public void cobroPorBanco(CobroPorBanco pCobroPorBanco, List<Cuota> pListaCuotas, boolean pIsConSeguro, Date pFecha){
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction tx = this.entityManager.getTransaction();
		try{
			tx.begin();
			this.procesarCobro(pCobroPorBanco, pListaCuotas, pIsConSeguro, pFecha);
			tx.commit();

		}
		catch(Exception e){
			e.printStackTrace();
			if (tx!=null & tx.isActive()){
				tx.rollback();
			}
		}
		finally{
			this.entityManager.close();
		}
	}
	

	private void procesarCobro(CobroPorBanco pCobroPorBanco, List<Cuota> pListaCuota, boolean pIsConSeguro, Date pFecha) {
		Set<Credito> locCredito= new HashSet<Credito>();
		this.cobroPorBanco = pCobroPorBanco;
		this.listaCuotas= pListaCuota;
		this.entityManager.persist(this.cobroPorBanco);
		BigDecimal acumulador= new BigDecimal("0.00");
	
		for(Cuota cadaCuota: this.listaCuotas){
			locCredito.add(cadaCuota.getCredito());
			Cancelacion locCancelacion = new Cancelacion();
			locCancelacion.setCobro(cobroPorBanco);
			locCancelacion.setMonto(cadaCuota.getTotalSegunVencimiento(pIsConSeguro,pFecha));
			acumulador= acumulador.add(cadaCuota.getTotalSegunVencimiento(pIsConSeguro, pFecha));
			cadaCuota.setCancelacion(locCancelacion);
			this.entityManager.persist(locCancelacion);
			this.entityManager.merge(cadaCuota);
			this.entityManager.merge(cadaCuota.getCredito());
		}
		
		if(this.cobroPorBanco.getSobranteAlDia().floatValue()>0){
			BigDecimal diferencia= this.cobroPorBanco.getSobranteAlDia().subtract(acumulador);
			if(diferencia.floatValue()>=0F){
				CuentaCorriente locCuentaCorriente=this.cobroPorBanco.getCuentaCorriente();
				locCuentaCorriente.setSobrante(diferencia);
				this.entityManager.merge(locCuentaCorriente);
			}
			else{
				CuentaCorriente locCuentaCorriente=this.cobroPorBanco.getCuentaCorriente();
				locCuentaCorriente.setSobrante(new BigDecimal(0));
				this.entityManager.merge(locCuentaCorriente);
				
			}
		}	
		
		this.entityManager.flush();
	}
	
	
	public List<Cuota> getListaCuotasACobrar(Integer pNumeroCredito) throws LogicaException{
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		String consulta="select c from Cuota c where c.credito.numeroCredito =:locNumeroCredito";	
		consulta+=" and c.cancelacion is null order by c.vencimiento asc";
		Query locQuery = this.entityManager.createQuery(consulta);
		if(pNumeroCredito!=null){
			locQuery.setParameter("locNumeroCredito", pNumeroCredito);
		}
		if (locQuery.getResultList().isEmpty()){
			int codigo=55;
			String campo="";
			throw new LogicaException(codigo, campo);
		}
		else{
			return locQuery.getResultList();
		}
	}
	
	

	public List<Cuota> getListaCuotasACobrar(Long pCuip, Long pLegajo, EstadoPersonaFisica pEstado) throws LogicaException {
	
		List<Cuota> listaCuotas= new ArrayList<Cuota>(); 
		
		//Busqueda de cuotas como solicitante
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		String consulta="select c from Cuota c ";	
		Persona locPersona=null;
		PersonaFisica locPersonaFisica = null;
		if(pCuip!=null){
				locPersona= this.buscarPersona(pCuip);
		}
		else{
			if(pLegajo!=null){
				locPersonaFisica= this.buscarPersonaFisica(pLegajo, pEstado);
					
			}
		}
		if((locPersona!=null)||(locPersonaFisica!=null)){
			consulta+="where c.credito.cuentaCorriente.persona= :pPersona ";
		}
		consulta+=" and c.cancelacion is null order by c.vencimiento asc";
		Query locQuery = this.entityManager.createQuery(consulta);
		if(locPersona!=null){
			locQuery.setParameter("pPersona", locPersona);
		}
		else{
			if(locPersonaFisica!=null){
				locQuery.setParameter("pPersona", locPersonaFisica);
			}
		}
		if (!locQuery.getResultList().isEmpty()){
			listaCuotas.addAll(locQuery.getResultList());
		}
		
		//Busqueda de cuotas como garante
		
		
		//Busco todas las garantiasPersonal donde garante sea= a personaFisica
		List<GarantiaPersonal> listaGarantias= new ArrayList<GarantiaPersonal>();
		
		if(locPersona instanceof PersonaFisica){
			listaGarantias=this.buscarGarante((PersonaFisica)locPersona);
		}
		else{
			if(locPersonaFisica!=null){
			listaGarantias=this.buscarGarante(locPersonaFisica);
			}
		}
		
		if(!listaGarantias.isEmpty()){
			for(GarantiaPersonal locGarantia: listaGarantias){
				listaCuotas.addAll(this.getListaCuotas(locGarantia.getCredito().getNumeroCredito()));				
			}
		}
		if(listaCuotas.isEmpty()){
			int codigo=55;
			String campo="";
			throw new LogicaException(codigo, campo);
		}
		else{
			return listaCuotas;
		}
	}
	
	
	private List<GarantiaPersonal> buscarGarante(PersonaFisica locPersona) {
		String consulta= "select g from GarantiaPersonal g where g.garante= :personaFisica and afectar=:locAfectar";
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		Query locQuery = this.entityManager.createQuery(consulta);
		locQuery.setParameter("personaFisica", locPersona);
		locQuery.setParameter("locAfectar", true);
		return locQuery.getResultList();
	}
	

	public List<Cuota> getListaCuotas(Integer pNumeroCredito){
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		String consulta="select c from Cuota c where c.credito.numeroCredito =:locNumeroCredito";	
		consulta+=" and c.cancelacion is null order by c.vencimiento asc";
		Query locQuery = this.entityManager.createQuery(consulta);
		if(pNumeroCredito!=null){
			locQuery.setParameter("locNumeroCredito", pNumeroCredito);
		}
		return locQuery.getResultList();
	}



	public PersonaFisica buscarPersonaFisica(Long pLegajo, EstadoPersonaFisica pEstado) throws LogicaException {
	
			PersonaDAO locPersonaDAO= new PersonaDAO();
			List<PersonaFisica> listaPersonaFisica= new ArrayList<PersonaFisica>();
			listaPersonaFisica=locPersonaDAO.findListaPersonasFisicas(pLegajo, null, null, null, null, pEstado);
			if (!listaPersonaFisica.isEmpty()){
				return listaPersonaFisica.get(0);
			}
			else{
				int codigoMensaje=54;
				String campoMensaje="";
				throw new LogicaException(codigoMensaje, campoMensaje);
			}
		
		
		
	}
	
	

	public Persona buscarPersona(Long pCuip) throws LogicaException {
			PersonaDAO locPersonaDAO= new PersonaDAO();
			return locPersonaDAO.getPersonaPorCui(pCuip);		
	}



	public Cuota getCuentaCorriente(Long pCuip, Long pLegajo, EstadoPersonaFisica pEstado) throws LogicaException {
	
		//Busqueda de cuotas como solicitante
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		String consulta="select c from Cuota c ";	
		Persona locPersona=null;
		PersonaFisica locPersonaFisica = null;
		if(pCuip!=null){
				locPersona= this.buscarPersona(pCuip);
		}
		else{
			if(pLegajo!=null){
				locPersonaFisica= this.buscarPersonaFisica(pLegajo, pEstado);
					
			}
		}
		if((locPersona!=null)||(locPersonaFisica!=null)){
			consulta+="where c.credito.cuentaCorriente.persona= :pPersona ";
		}
		consulta+=" and c.cancelacion is null order by c.vencimiento asc";
		Query locQuery = this.entityManager.createQuery(consulta);
		if(locPersona!=null){
			locQuery.setParameter("pPersona", locPersona);
		}
		else{
			if(locPersonaFisica!=null){
				locQuery.setParameter("pPersona", locPersonaFisica);
			}
		}
		if (!locQuery.getResultList().isEmpty()){
			return (Cuota) locQuery.getResultList().get(0);
			
		}
		
		//Busqueda de cuotas como garante
		
		
		//Busco todas las garantiasPersonal donde garante sea= a personaFisica
		List<GarantiaPersonal> listaGarantias= new ArrayList<GarantiaPersonal>();
		
		if(locPersona instanceof PersonaFisica){
			listaGarantias=this.buscarGarante((PersonaFisica)locPersona);
		}
		else{
			if(locPersonaFisica!=null){
			listaGarantias=this.buscarGarante(locPersonaFisica);
			}
		}
		
		List<Cuota> listaCuotas= new ArrayList<Cuota>();
		if(!listaGarantias.isEmpty()){
			for(GarantiaPersonal locGarantia: listaGarantias){
				listaCuotas.addAll(this.getListaCuotas(locGarantia.getCredito().getNumeroCredito()));				
			}
			return listaCuotas.get(0);
		}
		return null;
	
	
	}


	public List<CobroPorBanco>  findCobros(Date fechaDesde, Date fechaHasta) {
		String consulta="select cb from CobroPorBanco cb, Cobro c " +
				" where cb.id=c.id and " +
					"(" +
					" (c.fecha = :fechaDesde) or " +
					" (c.fecha = :fechaHasta) or " +
					" ((c.fecha > :fechaDesde) and (c.fecha < :fechaHasta)) " +
					") order by c.fecha asc" ;
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("fechaDesde", fechaDesde);
		locQuery.setParameter("fechaHasta", fechaHasta);
		if(!locQuery.getResultList().isEmpty()){
			return locQuery.getResultList();
		}
		return null;
	}



	

	
	
	

}
