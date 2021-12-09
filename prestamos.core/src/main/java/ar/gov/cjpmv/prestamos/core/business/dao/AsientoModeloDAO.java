package ar.gov.cjpmv.prestamos.core.business.dao;


import java.util.List;

import ar.gov.cjpmv.prestamos.core.persistence.contable.AsientoModelo;

public class AsientoModeloDAO extends  GenericDAOImpl<AsientoModelo>{

	@Override
	public AsientoModelo getObjetoPorId(Long clave) {
		return this.entityManager.find(AsientoModelo.class, clave);
	}
	
	@SuppressWarnings("unchecked")
	public List<AsientoModelo> getListaAsientosModelo() {
		return this.entityManager
					.createQuery("from AsientoModelo")
					.getResultList();
	}

}
