package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.time.DateUtils;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;

public class ChequeDAO extends GenericDAOImpl<Cheque>{
	
	@Override
	public Cheque getObjetoPorId(Long clave) {
		return this.entityManager.getReference(Cheque.class, clave);
	}

	@SuppressWarnings("unchecked")
	public List<Cheque> findListaCheques(String pApellido, Integer pNumeroCheque) {
		Map<String, Object> listaParametros = new HashMap<String,Object>();
		String consulta = "select distinct c from Cheque c";
		boolean primero = true;
		
		if (pApellido != null){
			primero = false;
			consulta += " , PersonaFisica p " +
					"where " +
					" ((c.emitidoA is null) and upper(c.nombrePersona) like upper(:apellido)) or " +
					" (c.emitidoA = p and upper(p.apellido) like upper(:apellido))  ";
			listaParametros.put("apellido", pApellido+"%");
		}
		
		if (pNumeroCheque != null){
			consulta += (primero)?" where ":" and ";
			primero = false;
			consulta += " c.numero = :numero";
			listaParametros.put("numero", pNumeroCheque);
		}
		
		Query locQuery = this.entityManager.createQuery(consulta);
		for (Entry<String, Object> cadaElemento : listaParametros.entrySet()){
			locQuery.setParameter(cadaElemento.getKey(), cadaElemento.getValue());
		}
		return locQuery.getResultList();
	}

	public void cancelar(Cheque cheque) throws LogicaException{
		if (cheque==null){
			throw new LogicaException(70, null);
		}
		if (cheque.getEstadoCheque().equals(EstadoCheque.PENDIENTE_IMPRESION)){
			this.eliminar(cheque);
		}
		else{
			cheque.setEstadoCheque(EstadoCheque.CANCELADO);
			this.modificar(cheque);
		}
	}
	
	@Override
	public void eliminar(Cheque cheque) throws LogicaException {
		EntityTransaction tx = this.entityManager.getTransaction();
		try{
			tx.begin();
			this.entityManager.flush();
			this.validarEliminar(cheque);
			this.entityManager.refresh(cheque);
			Cheque chequeViejo = getViejoCanceladoPor(cheque);
			if (chequeViejo!= null){
				chequeViejo.setCanceladoPor(null);
				this.entityManager.merge(chequeViejo);
			}
			Credito credito = getCreditoPorCheque(cheque);
			credito.getListaCheques().remove(cheque);
			this.entityManager.merge(credito);
			this.entityManager.remove(cheque);
			tx.commit();
		}
		catch(LogicaException e){
			e.printStackTrace();
			if (tx!= null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
		catch (Exception e){
			e.printStackTrace();
			if (tx!= null && tx.isActive()) {
				tx.rollback();
			}
			throw new LogicaException(76,"");
		}
	}
	
	private Credito getCreditoPorCheque(Cheque cheque) {
		Query consulta = this.entityManager.createQuery("select distinct c " +
				" from Credito c inner join c.listaCheques cheq" +
				" where cheq = :cheque");
		consulta.setParameter("cheque", cheque);
		return (Credito)consulta.getSingleResult();
	}

	private Cheque getViejoCanceladoPor(Cheque cheque) {
		try{
			Query query = this.entityManager.createQuery("select c from Cheque c where c.canceladoPor = :cheque");
			query.setParameter("cheque", cheque);
			Cheque chequeViejo = (Cheque) query.getSingleResult();
			return chequeViejo;
		}
		catch(NoResultException e){
			return null;
		}
	}

	public Integer getProximoNumeroCheque(){
		try{
			Integer numero = (Integer)this.entityManager
										  .createQuery("select max(c.numero) " +
										  			   "from Cheque c")
										  .getSingleResult();
			return ++numero;
		} catch (EntityNotFoundException e){
			return 0;
		}
	}

	public void reemplazarCheque(Cheque chequeViejo, Cheque chequeNuevo) throws LogicaException {
		EntityTransaction tx = this.entityManager.getTransaction();
		try{
			tx.begin();
			this.validarAlta(chequeNuevo);
			this.validarModificacion(chequeViejo);
			if (chequeViejo.getCanceladoPor()!= null){
				throw new LogicaException(77, chequeNuevo.getCanceladoPor().toString()); 
			}
			Credito credito = getCreditoPorCheque(chequeViejo);
			credito.getListaCheques().add(chequeNuevo);
			chequeViejo.setCanceladoPor(chequeNuevo);
			this.entityManager.persist(chequeNuevo);
			this.entityManager.merge(credito);
			this.entityManager.flush();
			tx.commit();
		}
		catch(LogicaException e){
			e.printStackTrace();
			if (tx!= null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
		catch (Exception e){
			e.printStackTrace();
			if (tx!= null && tx.isActive()) {
				tx.rollback();
			}
			throw new LogicaException(76,"");
		}
	}
	
	@Override
	public void validarModificacion(Cheque pCheque) throws LogicaException {
		if (pCheque.getCanceladoPor() != null){
			if (pCheque.getCanceladoPor().equals(pCheque)){
				throw new LogicaException(43,"cheque de cancelación");
			}
			pCheque.setEstadoCheque(EstadoCheque.CANCELADO);
		}

		if (pCheque.getMonto().floatValue() <= 0){
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

}
