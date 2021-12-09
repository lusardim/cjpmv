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

public class Frances extends Sistema {
	private BigDecimal saldoRestante;
	private BigDecimal interesMensual;
	private BigDecimal cantidadCuotas;
	
	public Frances() throws LogicaException {
		super();
	}
	
	@Override
	public List<Cuota> calcularCuotas(Credito pCredito, Date pFechaPrimerVencimiento) throws LogicaException{
		if (pFechaPrimerVencimiento==null){
			setFechaPrimerVencimiento(Calendar.getInstance().getTime());
		}
		else if (pFechaPrimerVencimiento.before(pCredito.getFechaInicio())){
			throw new LogicaException(82, null);
		}
		else{
			setFechaPrimerVencimiento(pFechaPrimerVencimiento);
		}
		List<Cuota> locListaCuotas = new ArrayList<Cuota>(pCredito.getCantidadCuotas());

		//inicializo las variables
		//divisot = 12*100
		BigDecimal divisor = new BigDecimal(1200);
		
		this.saldoRestante = pCredito.getMontoTotal();
		this.interesMensual = getInteresReal(pCredito).divide(divisor,RoundingMode.HALF_UP);
		this.cantidadCuotas = new BigDecimal(pCredito.getCantidadCuotas());
		
		//con esto acumulamos las diferencias de redondeo
		BigDecimal diferenciaInteres = new BigDecimal(0);
		BigDecimal diferenciaCapital = new BigDecimal(0);
		
		//Calculo las cuotas
		for (int numeroCuota = 1; numeroCuota<=pCredito.getCantidadCuotas(); numeroCuota++){
			Cuota locCuota = this.generarCuota(numeroCuota);
			locCuota.setCredito(pCredito);
			locListaCuotas.add(locCuota);
			//Redondeamos y acumulamos diferencias
			diferenciaInteres.add(this.redondearInteres(locCuota));
			diferenciaCapital.add(this.redondearCapital(locCuota));
		}
		System.out.println("Diferencia capital = "+diferenciaCapital);
		System.out.println("Diferencia interes = "+diferenciaInteres);
		
		//Ajusto las diferencias de redondeo en la última cuota
		Cuota locCuota = locListaCuotas.get(locListaCuotas.size()-1);
		locCuota.setInteres(locCuota.getInteres().add(diferenciaInteres));
		locCuota.setCapital(locCuota.getCapital().add(diferenciaCapital));
		
		return locListaCuotas;
	}

	/**
	 * Retorna la diferencia entre el capital que debería pagar (sin redondear) y lo que reálmente pagó (redondeado) para una cuota 
	 * en particular. Si el valor es positivo significa que debería pagar más. En caso contrario significa que pagó de más
	 * @param pCredito
	 * @return diferencia capital que debería pagar-capital que pagó
	 */
	private BigDecimal redondearCapital(Cuota locCuota) {
		BigDecimal capitalActual = locCuota.getCapital();
		BigDecimal capitalRedondeado = locCuota.getCapital().setScale(0);
		locCuota.setCapital(capitalRedondeado);
		return capitalActual.subtract(capitalRedondeado);
	}

	/**
	 * Retorna la diferencia entre el interés que debería pagar (sin redondear) y lo que reálmente pagó (redondeado) de una cuota en particular
	 * Si el valor es positivo significa que debería pagar más. En caso contrario significa que pagó de más
	 * @param pCredito
	 * @return diferencia interés que debería pagar-capital que pagó 
	 */
	private BigDecimal redondearInteres(Cuota locCuota) {
		BigDecimal interesActual = locCuota.getInteres();
		BigDecimal interesRedondeado = locCuota.getInteres().setScale(0,RoundingMode.HALF_UP);
		locCuota.setInteres(interesRedondeado);
		return interesActual.subtract(interesRedondeado);
	}

	private Cuota generarCuota(int numeroCuota) {
		Cuota locCuota = new Cuota();
		locCuota.setNumeroCuota(numeroCuota);
		
		BigDecimal capital = this.calcularCapital(numeroCuota);
		locCuota.setCapital(capital);
		
		locCuota.setInteres(this.saldoRestante.multiply(this.interesMensual));
		locCuota.setVencimiento(this.getFechaVencimiento(numeroCuota));
		
		this.saldoRestante.subtract(locCuota.getCapital());
		return locCuota;
	}

	public BigDecimal calcularCapital(int cuotaActual){
		BigDecimal uno = new BigDecimal(1);
		/*
		 *           saldoInicial*interesMensual
		 *	 ----------------------------------------------------- ======> Capital
		 *   ((1+interesMensual)^(cantidadCuotas-cuotaActual+1))-1
		 */
		BigDecimal dividendo = this.saldoRestante.multiply(this.interesMensual);
		BigDecimal base = this.interesMensual.add(uno);
		int potencia = this.cantidadCuotas.intValue() - cuotaActual + 1;
		
		return dividendo.divide( base.pow(potencia).subtract(uno), RoundingMode.HALF_UP);
	}

	@Override
	public TipoSistema getTipoSistema() {
		return TipoSistema.FRANCES;
	}
	
	@Override
	public String toString() {
		return "Francés";
	}
	

}
