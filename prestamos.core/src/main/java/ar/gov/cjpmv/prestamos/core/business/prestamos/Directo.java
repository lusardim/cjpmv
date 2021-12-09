package ar.gov.cjpmv.prestamos.core.business.prestamos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;




public class Directo extends Sistema{
	
	private BigDecimal tasaInteres;
	private BigDecimal capital;
	private Credito credito;
	private BigDecimal acumuladorInteres; 
	private BigDecimal capitalRedondeado;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7018160757960998306L;
	

	public Directo() throws LogicaException{
		super();
	}

	@Override
	public List<Cuota> calcularCuotas(Credito pCredito, Date pFechaPrimerVencimiento) throws LogicaException {
		if (pFechaPrimerVencimiento==null){
			setFechaPrimerVencimiento(Calendar.getInstance().getTime());
		}
		else if (pFechaPrimerVencimiento.before(pCredito.getFechaInicio())){
			throw new LogicaException(82, null);
		}
		else{
			setFechaPrimerVencimiento(pFechaPrimerVencimiento);
		}
		credito = pCredito;
		
		//preparo el capital
		BigDecimal cantidadCuotas = new BigDecimal(pCredito.getCantidadCuotas());
		capital = credito.getMontoTotal().divide(cantidadCuotas,2,RoundingMode.HALF_UP);
		capitalRedondeado = capital.setScale(0,RoundingMode.HALF_UP);
		
		//preparo el interes
		acumuladorInteres = new BigDecimal(0).setScale(2);
		BigDecimal cien = new BigDecimal(100);
		this.tasaInteres = getInteresReal(credito);
		this.tasaInteres = tasaInteres.divide(cien);
		this.tasaInteres.setScale(2,RoundingMode.HALF_UP);
		
		//Obtengo las cuotas
		List<Cuota> locRetorno = this.generarCuotas(pCredito);
		
		Cuota locCuota = locRetorno.get(locRetorno.size()-1);
		this.acumularCapitalUltimaCuota(locCuota);
		
		return locRetorno;
	}

	/**
	 * Le suma a la cuota pasada por parámetro el interes y el capital 
	 * @param locCuota
	 * @param pInteres
	 * @param pCapital
	 */
	private void acumularCapitalUltimaCuota(Cuota locCuota) {
		BigDecimal cantidadCuotas = new BigDecimal(credito.getCantidadCuotas());
		BigDecimal diferenciaCapital = capitalRedondeado.multiply(cantidadCuotas)
														.subtract(credito.getMontoTotal())
														.setScale(2);
		
		BigDecimal capitalUltimaCuota = locCuota.getCapital()
												.subtract(diferenciaCapital)
												.setScale(2,RoundingMode.HALF_UP); 
		BigDecimal interesUltimaCuota = locCuota.getInteres()
												.subtract(this.acumuladorInteres)
												.setScale(2,RoundingMode.HALF_UP);
		
		locCuota.setCapital(capitalUltimaCuota);
		locCuota.setInteres(interesUltimaCuota);
	}
	/**
	 * Genera la lista de cuotas
	 * @param pCredito
	 * @return
	 */
	private List<Cuota> generarCuotas(Credito pCredito) {
		List<Cuota> retorno = new ArrayList<Cuota>(pCredito.getCantidadCuotas());
		//calculo cuota por cuota
		for (int numeroCuota = 1; numeroCuota<=pCredito.getCantidadCuotas();numeroCuota++){
			Cuota cadaCuota = this.generarCuota(numeroCuota);
			cadaCuota.setVencimiento(this.getFechaVencimiento(numeroCuota));
			cadaCuota.setCredito(pCredito);
			retorno.add(cadaCuota);
		}
		return retorno;
	}

	/**
	 * Genera una cuota
	 * @param numeroCuota número de cuota a generar
	 * @return
	 */
	private Cuota generarCuota(int numeroCuota) {
		BigDecimal interesCuota = this.tasaInteres.multiply(capital);
		BigDecimal intereRedondeado = interesCuota.setScale(0,RoundingMode.HALF_UP); 
		acumuladorInteres = acumuladorInteres.add(intereRedondeado.subtract(interesCuota));
		
		Cuota locCuota = new Cuota();
		locCuota.setNumeroCuota(numeroCuota);
		locCuota.setInteres(intereRedondeado);
		locCuota.setCapital(this.capitalRedondeado);
		return locCuota;
	}
	
	@Override
	public TipoSistema getTipoSistema() {
		return TipoSistema.DIRECTO;
	}
	
	
	@Override
	public String toString() {
		return "Directo";
	}
}
