package ar.gov.cjpmv.prestamos.core.business;

import java.util.Comparator;
import java.util.Date;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;

public class CuotaComparator implements Comparator<Cuota> {
	@Override
	public int compare(Cuota cuota1, Cuota cuota2) {
		Date vencimientoCuota1 = cuota1.getVencimiento();
		Date vencimientoCuota2 = cuota2.getVencimiento();
		int resultado = vencimientoCuota1.compareTo(vencimientoCuota2);
		if (resultado==0) {
			resultado = cuota1.getId().compareTo(cuota2.getId());
		}
		return resultado;
	}
}
