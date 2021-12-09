package ar.gov.cjpmv.prestamos.core.business.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public interface CuentaCorrienteDAO extends GenericDAO<CuentaCorriente> {

	public Set<CuentaCorriente> getCuentasCorrientes(Date pVencimientoHasta, 
			ViaCobro pViaCobro);
	public void actualizarSobrante(Long id, BigDecimal bigDecimal) throws LogicaException;
}
