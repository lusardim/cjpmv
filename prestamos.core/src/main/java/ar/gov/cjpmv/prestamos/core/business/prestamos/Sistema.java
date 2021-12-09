package ar.gov.cjpmv.prestamos.core.business.prestamos;


 
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;

public abstract class Sistema {
	
	public enum TipoSistema{DIRECTO, FRANCES};
	private Date fechaPrimerVencimiento;
	
	public Sistema() throws LogicaException {
		//TODO SOLUCIONAR ALGÚUUUUUNA VEZ
		//new ConfiguracionSistemaDAO().getConfiguracionSistema().getDiaVencimiento();
	}
	
	public abstract List<Cuota> calcularCuotas(Credito pCredito, Date pFechaPrimerVencimiento) throws LogicaException;
	
	public static Sistema getSistema(TipoSistema pTipoSistema) throws LogicaException{
		switch(pTipoSistema){
			case DIRECTO: return new Directo();
			case FRANCES: return new Frances();
		}
		return null;
	}
	
	/**
	 * Obtiene una fecha de vencimiento de una cuota, tomando como base la fecha de inicio del crédito
	 * @param pFechaInicio
	 * @param pCuota
	 * @return
	 */
	public Date getFechaVencimiento(int pCuota){
		Calendar locCalendar = Calendar.getInstance();
		locCalendar.setTime(fechaPrimerVencimiento);
		locCalendar.add(Calendar.MONTH, pCuota-1);
		return locCalendar.getTime();
	}
	
	public abstract TipoSistema getTipoSistema();

	public Date getFechaPrimerVencimiento() {
		return fechaPrimerVencimiento;
	}

	public void setFechaPrimerVencimiento(Date fechaPrimerVencimiento) {
		this.fechaPrimerVencimiento = fechaPrimerVencimiento;
	}
	
	protected BigDecimal getInteresReal(Credito pCredito) {
		BigDecimal interes = pCredito.getTasa().setScale(2);
		if (pCredito.isAcumulativo()) {
			//calculo interes mensual
			interes = pCredito.getTasa().setScale(2).divide(new BigDecimal(12),RoundingMode.HALF_UP);
			//calculo el interes acumulado para todas las cuotas
			BigDecimal cantidadCuotas = new BigDecimal(pCredito.getCantidadCuotas());
			interes = interes.multiply(cantidadCuotas).setScale(2);
			return interes;
		}
		else {
			return interes;
		}
	}

}
