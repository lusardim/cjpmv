package ar.gov.cjpmv.prestamos.actualizador;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroDetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class Parche {
	private EntityManager em;
	
	public static void main(String[] args) {
		Parche parche = new Parche();
		parche.parchear();
	}

	private void parchear() {
		System.out.println("Aplicando Parche");
		em = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			System.out.println("Actualizando cobro por finalizacion");
			actualizarCobroLiquidacion();
			System.out.println("Borrando cobro liquidacion de la muni");
			borrarCobrosPorLiquidacionMunicipalidad();
			System.out.println("seteando cuentas corrientes en 0");
			actualizarCuentasCorrientes();
			System.out.println("Actualizando creditos");
			actualizarCreditosCancelados();
			tx.commit();
			System.out.println("parche finalizado");
		}
		catch(Exception e) {
			e.printStackTrace();
			if (tx!=null && tx.isActive()) {
				tx.rollback();
			}
			System.err.println("Hubo un problema con el parche");
		}
		finally {
			em.close();
		}
	}
	
	private void actualizarCuentasCorrientes() {
		@SuppressWarnings("unchecked")
		List<CuentaCorriente> listaCuentasCorrientes = 
			em.createQuery("select c from CuentaCorriente c "+
						   "	inner join c.persona per "+
						   "where c.sobrante>0  "+
						   "	and per.class = PersonaFisica")
			.getResultList();
		for (CuentaCorriente c : listaCuentasCorrientes) {
			PersonaFisica per = (PersonaFisica)c.getPersona();
			if (per.getEstado().equals(EstadoPersonaFisica.ACTIVO)) {
				System.out.println("\t-Actualizando sobrante de "+c);
				c.setSobrante(new BigDecimal("0.00"));
				em.merge(c);
			}
		}
	}

	private void borrarCobrosPorLiquidacionMunicipalidad() {
		Query query = em.createQuery(
				"select c from CobroLiquidacion c " +
				"where c.liquidacion.viaCobro = :viaCobro");
		ViaCobro muni = em.getReference(ViaCobro.class, 1l);
		query.setParameter("viaCobro", muni);

		@SuppressWarnings("unchecked")
		List<CobroLiquidacion> listaCobrosLiq = query.getResultList();
		for (CobroLiquidacion cadaCobro : listaCobrosLiq) {
			for (CobroDetalleLiquidacion cobroDetalle : cadaCobro.getListaCobroPorLiquidacion()) {
				Query consultaCuotas = em.createQuery(
						"select c from Cuota c " +
						"where c.cancelacion.cobro = :cobro");
				consultaCuotas.setParameter("cobro", cobroDetalle);
				
				@SuppressWarnings("unchecked")
				List<Cuota> listaCuotas = consultaCuotas.getResultList();
				for (Cuota cuota : listaCuotas) {
					cuota.setCancelacion(null);
					em.merge(cuota);
				}
			}
			em.remove(cadaCobro);
		}
	}

	@SuppressWarnings("unchecked")
	private void actualizarCreditosCancelados() {
		List<Credito> listaCreditos = em.createQuery(
				"select distinct c from Credito c "+
				"inner join c.listaCuotas cuot "+
				"where cuot.cancelacion is null "+ 
				"and c.estado = 'FINALIZADO' ") 
				.getResultList();
		
		for (Credito cadaCredito : listaCreditos) {
			cadaCredito.setFechaFin(null);
			cadaCredito.setEstado(EstadoCredito.OTORGADO);
			em.merge(cadaCredito);
		}
	}

	private void actualizarCobroLiquidacion() {
		@SuppressWarnings("unchecked")
		List<CobroDetalleLiquidacion> cdl = em.createQuery("from CobroDetalleLiquidacion").getResultList();
		for (CobroDetalleLiquidacion cada : cdl) {
			if (cada.getListaDetallesLiquidacion().isEmpty()) {
				for (DetalleLiquidacion det : cada.getCobroLiquidacion().getLiquidacion().getListaDetalleLiquidacion()) {
					if (cada.getCuentaCorriente() == null) {
						Persona pagador = cada.getPagador();
						Query query = em.createQuery("from CuentaCorriente c where c.persona = :p");
						query.setParameter("p", pagador);
						cada.setCuentaCorriente((CuentaCorriente) query.getSingleResult());
					}
					if (det.getCuentaCorrienteAfectada().equals(cada.getCuentaCorriente())) {
						cada.add(det);
						em.merge(cada);
					}
				}
			}
		}
	}
}

