package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.dao.GenericDAOImpl;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoFijo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;

public class ConceptoHaberesDAO extends GenericDAOImpl<ConceptoHaberes> {

	@Override
	public ConceptoHaberes getObjetoPorId(Long clave) {
		return this.entityManager.find(ConceptoHaberes.class, clave);
	}

	public ConceptoHaberes getConceptoPorCodigo(int codigo) {
		try {
			Query query = this.entityManager.createQuery(
					"select c from ConceptoHaberes c " +
					" where c.codigo = :codigo" );
			query.setParameter("codigo", codigo);
			return (ConceptoHaberes)query.getSingleResult();
		}
		catch(NoResultException e) {
			return null;
		}
	}
		
	@SuppressWarnings("unchecked")
	public List<ConceptoHaberes> getConceptos() {
		return this.getEntityManager()
				.createQuery("from ConceptoHaberes")
				.getResultList();
	}

	public List<ConceptoHaberes> findConceptosHaberes(Integer locCodigo,
			String pDescripcion) {
		try {
			String consulta= "select c from ConceptoHaberes c where";
				
			boolean txtAnd=false;
			if(locCodigo!=null){
				consulta+= " c.codigo= :codigo";
				txtAnd= true;
			}
			if(pDescripcion!=null){
				if(txtAnd){
					consulta+=" and";
				}
				consulta+=" upper(c.descripcion) like :descripcion";
			}
			
			Query query = this.entityManager.createQuery(consulta);
			if(locCodigo!=null){
				query.setParameter("codigo", locCodigo);
			}
			if(pDescripcion!=null){
				query.setParameter("descripcion", pDescripcion.toUpperCase()+"%");
			}
			List<ConceptoHaberes> lista= (List<ConceptoHaberes>) query.getResultList();
			if(!lista.isEmpty()){
				return lista;
			}
		}
		catch(NoResultException e) {
			return null;
		}
		return null;
	}

	public List<ConceptoHaberes> getConceptosPlantilla() {
		try{
			/*String query= "select c from ConceptoHaberes c, ConceptoFijo cfp " +
							"where c.id not in (cfp.id) " +
							"and cfp.sobreescribirValor is false";
			Query locQuery= this.entityManager.createQuery(query);*/
			Query locQuery = this.entityManager.createQuery("from ConceptoHaberes");
			List<ConceptoHaberes> lista= locQuery.getResultList();
			if(!lista.isEmpty()){
				List<ConceptoHaberes> conceptos = new ArrayList<ConceptoHaberes>();
				for (ConceptoHaberes concepto : lista) {
					if (concepto instanceof ConceptoFijo) { 
						if (((ConceptoFijo)concepto).isSobreescribirValor()) {
							conceptos.add(concepto);
						}
					}
					else {
						conceptos.add(concepto);
					}
					
				}
				return conceptos;
			}
			return null;
		}	
		catch(NoResultException e) {
			return null;
		}
	}

	
	/**
	 * Pregunta si el concepto está asociado a una liquidación
	 * @param conceptoHaberes
	 * @return
	 */
	public boolean isEnLiquidacion(ConceptoHaberes conceptoHaberes) {
		String consulta="select count(l) from LineaRecibo l where " +
				"	l.concepto = :pConcepto ";

		Query locQuery = this.entityManager.createQuery(consulta);
		locQuery.setParameter("pConcepto", conceptoHaberes);
		Long cantidad = (Long) locQuery.getSingleResult();
		if (cantidad > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Pregunta si el concepto está asociado a una plantilla
	 * @return
	 */
	public boolean isEnPlantilla(ConceptoHaberes conceptoHaberes) {
		String consulta="select count(l) from Plantilla p " +
				" inner join p.listaConceptos l " +
				" where " +
				"	l = :pConcepto ";

		Query locQuery = this.entityManager.createQuery(consulta);
		locQuery.setParameter("pConcepto", conceptoHaberes);
		Long cantidad = (Long)locQuery.getSingleResult();
		if (cantidad > 0) {
			return true;
		}
		return false;
	}
}