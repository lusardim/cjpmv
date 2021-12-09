package ar.gov.cjpmv.prestamos.actualizador;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Representa una lista de cuentas corrientes y cobros para una liquidación particular
 *
 */
public class ModeloCobrosLiquidacion {
	
	private Long idLiquidacion;
	private Map<Long, BigDecimal> montoPorCuenta = new HashMap<Long, BigDecimal>();
	private Map<Long, BigDecimal> sobrantePorCuenta = new HashMap<Long, BigDecimal>();
	
	public ModeloCobrosLiquidacion(Long id)
	{
		this.idLiquidacion = id;
	}
	
	/**
	 * <code>Map&lt;idCuenta, monto&gt;</code> donde idCuenta es el id de la cuenta corriente 
	 */
	public Map<Long, BigDecimal> getMontoPorCuenta() 
	{
		return montoPorCuenta;
	}

	public void put(Long cuentaId, Long monto)
	{
		this.put(cuentaId, monto, null);
	}
	
	public void put(Long cuentaId, Long monto, BigDecimal sobrante)
	{
		BigDecimal valor = new BigDecimal(monto);
		valor = valor.setScale(2);
		montoPorCuenta.put(cuentaId, valor);
		BigDecimal sobranteCuenta = sobrantePorCuenta.get(cuentaId);
		if (sobranteCuenta != null && sobrante != null) {
			throw new RuntimeException("El sobrante para la cuenta " + 
					cuentaId + " ya está seteado en " + sobranteCuenta);
		}
		if (sobrante != null) {
			sobrantePorCuenta.put(cuentaId, sobrante);
		}
	}
	
	public Long getIdLiquidacion() 
	{
		return idLiquidacion;
	}
	
	/**
	 * Convierte la lista de cuentas de contribuyentes en un string formateado 
	 * para usarlo en las consultas
	 */
	public String getCuentasComoString() 
	{
		Iterator<Long> iterador = montoPorCuenta.keySet().iterator();
		String cuentas = "(";
		while (iterador.hasNext()) {
			cuentas += iterador.next();
			if (iterador.hasNext()) {
				cuentas += ",";
			}
		}
		cuentas += ")";
		return cuentas;
	}
	
	@Override
	public String toString() 
	{
		return "liquidacion = " + idLiquidacion + " cobros = " + getMontoPorCuenta().toString();
	}
 
	public BigDecimal getSobrante(Long id) 
	{
		BigDecimal sobrante = sobrantePorCuenta.get(id);
		if (sobrante == null) {
			return new BigDecimal("0.00");
		}
		return sobrante;
	}
} 
