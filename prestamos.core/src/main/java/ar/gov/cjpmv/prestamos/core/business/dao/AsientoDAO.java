package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Asiento;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Movimiento;

public class AsientoDAO extends GenericDAOImpl<Asiento>{

	
	
		
	@Override
	public Asiento getObjetoPorId(Long clave) {
		return this.entityManager.find(Asiento.class, clave);
	}

	@SuppressWarnings("unchecked")
	public List<Asiento> getListaAsientoContable(Date fechaDesde,
			Date fechaHasta, String descripcion, Integer numeroAsiento) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			String consulta="select a from Asiento a ";
			Map<String, Object> parametros= new HashMap<String, Object>();
			boolean inicio= true;
			if(fechaDesde!=null && fechaHasta!=null){
				inicio= false;
				consulta+=" where a.fecha between :fechaDesde and :fechaHasta";	
				parametros.put("fechaDesde", fechaDesde);
				parametros.put("fechaHasta", fechaHasta);
			}
			
			if(descripcion!=null){
				if(inicio){
					consulta+=" where upper(a.descripcion) like :descripcion";
					inicio= false;
				}
				else{
					consulta+=" and upper(a.descripcion) like :descripcion";
				}
				parametros.put("descripcion", descripcion.toUpperCase()+"%");
			}
			
			if(numeroAsiento!=null){
				if(inicio){
					consulta+=" where a.numero= :numero";
				}
				else{
					consulta+=" and a.numero= :numero";
				}
				parametros.put("numero", numeroAsiento);
			}
					
			Query resultado = entityManager.createQuery(consulta);
			for (Entry<String, Object> cadaParametro : parametros.entrySet()){
				resultado.setParameter(cadaParametro.getKey(), cadaParametro.getValue());
			}
			if(!resultado.getResultList().isEmpty()){
				List<Asiento> listaResultado = resultado.getResultList();
				for (Asiento cadaAsiento : listaResultado) {
					cadaAsiento.getListaMovimiento().toString();
				}
				return listaResultado;
			}
			return null;
		}
		finally {
			entityManager.close();
		}
		
	}

	public Integer getNumeroAsientoSiguiente() {
		try{
			Integer valor=(Integer) entityManager
					.createQuery("select max(a.numero) from Asiento a ")
					.getSingleResult();
			if(valor!=null){
				return valor;
			}
			else {
				return 0;
			}
		}
		catch(Exception e) {
			return 0;
		}
	}
	
	public boolean tieneAsientos(Cuenta cuenta) {
		List<Cuenta> listaCuentas = new ArrayList<Cuenta>(cuenta.getCuentasHijasRecursivas());
		listaCuentas.add(cuenta);
		return 	!this.entityManager.createQuery("select m from Movimiento m where m.cuenta in (:cuentas)")
				.setParameter("cuentas", listaCuentas)
				.getResultList()
				.isEmpty();
	}
	
	public boolean isExisteNumeroAsiento(Integer numero, Long idAsiento) {
		String consulta="select a from Asiento a where a.numero= :pNumero";
		if(idAsiento!=null){
			consulta+=" and a.id != :pIdAsiento";
		}
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("pNumero", numero);
		if(idAsiento!=null){
			locQuery.setParameter("pIdAsiento", idAsiento);
		}
		try{
			locQuery.getSingleResult();
			return true;
		}
		catch(NoResultException e) {
			return false;
		}
	}
}
