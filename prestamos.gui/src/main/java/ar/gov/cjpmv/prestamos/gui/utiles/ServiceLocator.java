package ar.gov.cjpmv.prestamos.gui.utiles;

import ar.gov.cjpmv.prestamos.core.facades.ServicioLiquidacion;

public class ServiceLocator {
	
	private static ServiceLocator serviceLocator;
	
	private ServicioLiquidacion servicioLiquidacion;
	
	private ServiceLocator() {
		
	}
	
	public static ServiceLocator getInstance() {
		if (serviceLocator == null) {
			serviceLocator = new ServiceLocator();
		}
		return serviceLocator;
	}

	public ServicioLiquidacion getServicioLiquidacion() {
		if (servicioLiquidacion == null) {
			servicioLiquidacion = new ServicioLiquidacion();
		}
		return servicioLiquidacion;
	}
}
