package ar.gov.cjpmv.prestamos.core.tareas;

import javax.persistence.EntityManager;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import freemarker.log.Logger;

public class CargarViasCobro implements Runnable {
	private EntityManager entityManager;
	private Logger logger = Logger.getLogger(CargarViasCobro.class.getName());
	
	@Override
	public void run() {
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		try{
			ViaCobro.municipalidad = this.entityManager.find(ViaCobro.class, 1l);
			logger.info("Cargada la via de cobro: "+ViaCobro.municipalidad);
			ViaCobro.caja = this.entityManager.find(ViaCobro.class, 2l);
			logger.info("Cargada la via de cobro: "+ViaCobro.caja);
			ViaCobro.banco = this.entityManager.find(ViaCobro.class, 3l);
			logger.info("Cargada la via de cobro: "+ViaCobro.banco);
			ViaCobro.incobrable = this.entityManager.find(ViaCobro.class, 4l);
		}
		finally{
			this.entityManager.close();
		}
	}

}
