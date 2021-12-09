package ar.gov.cjpmv.prestamos.core.utiles;

import java.util.Calendar;
import java.util.Date;


public class UtilFecha {

	/**
	 * Verifica si 2 fechas son iguales sin importar el horario
	 * @param calendario1
	 * @param calendario2
	 * @return
	 */
	public static boolean comparaDia(Calendar calendario1, Calendar calendario2) {
		if (calendario1.get(Calendar.YEAR) == calendario2.get(Calendar.YEAR)
				&& calendario1.get(Calendar.MONTH) == calendario2.get(Calendar.MONTH) 
				&& calendario1.get(Calendar.DATE) == calendario2.get(Calendar.DATE)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Verifica que 2 fechas sean iguales tomando solo el día.
	 * @param pFecha1
	 * @param pFecha2
	 * @return
	 */
	public static boolean comparaDia(Date pFecha1,Date pFecha2){
		Calendar fecha1 = Calendar.getInstance();
		Calendar fecha2 = Calendar.getInstance();
		fecha1.setTime(pFecha1);
		fecha2.setTime(pFecha2);
		return comparaDia(fecha1, fecha2);
	}

}
