package ar.gov.cjpmv.prestamos.core.business.sueldos;

import ar.gov.cjpmv.prestamos.core.business.dao.GenericDAOImpl;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PorcentajeTipoBeneficio;

public class PorcentajeTipoBeneficioDAO extends GenericDAOImpl<PorcentajeTipoBeneficio>{

	@Override
	public PorcentajeTipoBeneficio getObjetoPorId(Long clave) {
		return entityManager.find(PorcentajeTipoBeneficio.class, clave);
	}

}
