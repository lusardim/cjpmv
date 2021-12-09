package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.util.Comparator;

import ar.gov.cjpmv.prestamos.core.business.sueldos.ConceptoHaberesComparator;

public class LineaReciboComparator implements Comparator<LineaRecibo> {
	
	private ConceptoHaberesComparator comparator;
	
	public LineaReciboComparator() {
		this.comparator = new ConceptoHaberesComparator();
	}
	
	@Override
	public int compare(LineaRecibo linea1, LineaRecibo linea2) {
		int comparacion = comparator.compare(linea1.getConcepto(), linea2.getConcepto());
		return comparacion;
	}

}
