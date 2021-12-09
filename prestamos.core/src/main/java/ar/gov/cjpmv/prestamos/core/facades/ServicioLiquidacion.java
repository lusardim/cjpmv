package ar.gov.cjpmv.prestamos.core.facades;

import java.util.Date;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.dao.LiquidacionDAO;
import ar.gov.cjpmv.prestamos.core.business.liquidacion.GeneradorLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class ServicioLiquidacion {

	private DAOFactory daoFactory = DAOFactory.getDefecto();
	private LiquidacionDAO liquidacionDAO;
	private GeneradorLiquidacion generadorLiquidacion;
	
	public ServicioLiquidacion() {
		liquidacionDAO = daoFactory.getLiquidacionDAO();
		generadorLiquidacion = new GeneradorLiquidacion();
	}
	
	public Liquidacion buscarPorPeriodo(Integer pMes, Integer pAnio,
			ViaCobro pViaCobro) {
		
		if (pMes == null || pAnio == null || pViaCobro == null) {
			throw new NullPointerException("Para buscar por período se necesitan el mes, el anio " +
					"y una vía de cobro válida");
		}
		return liquidacionDAO.getLiquidacionPorPeriodo(pMes, pAnio, pViaCobro);
	}
	
	public GeneradorLiquidacion getGeneradorLiquidacion() {
		return generadorLiquidacion;
	}

	public Long getNumeroUltimaLiquidacion(int pAnio, int pMes) {
		return this.liquidacionDAO.getNumeroUltimaLiquidacion(pAnio, pMes);
	}

	public Liquidacion liquidar(ViaCobro pViaCobro, Date pFecha) {
		return this.generadorLiquidacion.liquidar(pViaCobro, pFecha);
	}

	public List<DetalleLiquidacion> getLiquidacionesAnteriores(Date pFechaLiquidar, int pCantidadMeses) {
		return this.liquidacionDAO.getLiquidacionesAnteriores(pFechaLiquidar, pCantidadMeses);
	}
	
	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public DAOFactory getDaoFactory() {
		return daoFactory;
	}
}
