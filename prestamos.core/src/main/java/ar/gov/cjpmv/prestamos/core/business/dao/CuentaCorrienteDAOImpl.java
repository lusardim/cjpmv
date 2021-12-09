package ar.gov.cjpmv.prestamos.core.business.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class CuentaCorrienteDAOImpl extends GenericDAOImpl<CuentaCorriente> implements CuentaCorrienteDAO {

	@Override
	public CuentaCorriente getObjetoPorId(Long clave) {
		return this.entityManager.find(CuentaCorriente.class, clave);
	}

	/**
	 * Devuelve todas las cuentas corrientes que tengan al menos un vencimiento
	 * para la vía de cobro dada hasta la fecha
	 * @param pVencimientoHasta
	 * @param pViaCobro
	 * @return
	 */
	public Set<CuentaCorriente> getCuentasCorrientes(Date pVencimientoHasta, 
			ViaCobro pViaCobro) 
	{
		Set<CuentaCorriente> cuentasCorrientes = new HashSet<CuentaCorriente>();
		cuentasCorrientes.addAll(getCuentasCorrientesConVencimientoDirectas(pVencimientoHasta,pViaCobro));
		cuentasCorrientes.addAll(getCuentasCorrientesConVencimientoGarantes(pVencimientoHasta,pViaCobro));
		return cuentasCorrientes;
	}

	@SuppressWarnings("unchecked")
	private List<CuentaCorriente> getCuentasCorrientesConVencimientoGarantes(
			Date pVencimientoHasta, ViaCobro pViaCobro) {
		String consulta = 	"select distinct ctacte " +
							" from  CuentaCorriente ctacte " +
							"       		inner join ctacte.listaCredito cred" +
							"       		inner join cred.listaCuotas c" +
							" where cred.cobrarAGarante = false  " +
							"	        and c.cancelacion = null" +
							"            and c.vencimiento <= :vencimiento" +
							"            and cred.viaCobro = :viaCobro ";
		Query locQuery = entityManager.createQuery(consulta);
		locQuery.setParameter("viaCobro", pViaCobro);
		locQuery.setParameter("vencimiento", pVencimientoHasta, TemporalType.DATE);
		return locQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<CuentaCorriente> getCuentasCorrientesConVencimientoDirectas(
			Date pVencimientoHasta, ViaCobro pViaCobro) {
		String consulta = 	" select distinct cuentaC " +
							" from GarantiaPersonal gar, CuentaCorriente cuentaC" +
							"	inner join gar.garante per" +
							"    inner join gar.credito.listaCuotas cuotas" +
							" where cuentaC.persona = per " +
							"	and gar.credito.cobrarAGarante = true " +
							"	and cuotas.cancelacion = null" +
							"	and cuotas.vencimiento <= :vencimiento" + 
							"	and gar.viaCobro = :viaCobro";
		Query locQuery = entityManager.createQuery(consulta);
		locQuery.setParameter("viaCobro", pViaCobro);
		locQuery.setParameter("vencimiento", pVencimientoHasta,TemporalType.DATE);
		return locQuery.getResultList();
	}

	@Override
	public void actualizarSobrante(Long id, BigDecimal sobrante) throws LogicaException {
		EntityTransaction tx = this.entityManager.getTransaction();
		try { 
			tx.begin();
			CuentaCorriente ctacte = this.entityManager.find(CuentaCorriente.class, id);
			ctacte.setSobrante(sobrante);
			entityManager.merge(ctacte);
			tx.commit();
		}
		catch(Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
			throw new LogicaException(103,id.toString());
		}
	}
}
