package ar.gov.cjpmv.prestamos.core.tareas;

import java.util.Comparator;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.utiles.UtilFecha;

public class CuotasComparator implements Comparator<Cuota>{

	@Override
	public int compare(Cuota pCuota1, Cuota pCuota2) {
		if (UtilFecha.comparaDia(pCuota1.getVencimiento(), pCuota2.getVencimiento())){
			return 0;
		}
		// pCuota1<pCuota2 == -1;
		// pCuota1>pCuota2 == 1;
		return pCuota1.getVencimiento().compareTo(pCuota2.getVencimiento());
	}

}
