package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.dao.GenericDAOImpl;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Plantilla;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class PlantillaDAO extends GenericDAOImpl<Plantilla> {

	@Override
	public Plantilla getObjetoPorId(Long clave) {
		return (Plantilla)this.getEntityManager()
				.find(Plantilla.class, clave);
	}

	@SuppressWarnings("unchecked")
	public List<Plantilla> getPlantillas() {
		try{
			getEntityManager().clear();
			return this.getEntityManager()
					.createQuery("from Plantilla")
					.getResultList();
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	public Plantilla getPlantilla(TipoLiquidacion tipo) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			Query query = entityManager
					.createQuery("from Plantilla p where p.tipoLiquidacion = :tipo");
			query.setParameter("tipo", tipo);
			
			Plantilla plantilla = (Plantilla)query.getSingleResult();
			entityManager.refresh(plantilla);
			//fuerzo el fecth
			for (ConceptoHaberes cadaConcepto : plantilla.getListaConceptos()) {
				entityManager.refresh(cadaConcepto);
			}
			return plantilla;
		}
		catch(NoResultException e) {
			return null;
		}
		finally {
			entityManager.close();
		}
	}
}
