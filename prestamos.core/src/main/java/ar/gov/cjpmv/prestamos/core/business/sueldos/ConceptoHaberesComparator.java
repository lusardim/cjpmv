package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.util.Comparator;

import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;

public class ConceptoHaberesComparator implements Comparator<ConceptoHaberes> {

	@Override
	public int compare(ConceptoHaberes concepto1, ConceptoHaberes concepto2) {
		int comparacion = concepto1.getTipoCodigo().compareTo(concepto2.getTipoCodigo());
		if (comparacion == 0) {
			comparacion = concepto1.getId().compareTo(concepto2.getId());
		}
		return comparacion;
	}

}
