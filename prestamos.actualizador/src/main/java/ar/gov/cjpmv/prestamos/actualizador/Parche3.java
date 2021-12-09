package ar.gov.cjpmv.prestamos.actualizador;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.DBUtiles;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroDAO;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cobro;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;

/**
 * 
 * Este parche elimina los cobros de una lista de cuentas corrientes para 
 * un varias liquidaciones y luego los vuelve a realizar pero usando los 
 * valores que corresponde
 * 
 */
public class Parche3 {

	private DateFormat formateador = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
	private static Logger logger = Logger.getLogger(Parche3.class.getName());
	private static final List<ModeloCobrosLiquidacion> MODELO_COBRO_LIQUIDACION = new ArrayList<ModeloCobrosLiquidacion>();
	{ 
		ModeloCobrosLiquidacion liquidacion48 = new ModeloCobrosLiquidacion(48l);
		liquidacion48.put(164l, 307l, new BigDecimal(0));
		MODELO_COBRO_LIQUIDACION.add(liquidacion48);
		
		ModeloCobrosLiquidacion liquidacion49 = new ModeloCobrosLiquidacion(49l);
		liquidacion49.put(164l, 307l);
		liquidacion49.put(462l, 665l);
		liquidacion49.put(176l, 332l);
		liquidacion49.put(218l, 1507l, new BigDecimal("39.00"));
		liquidacion49.put(417l, 770l);
		liquidacion49.put(238l, 1216l);
		liquidacion49.put(206l, 900l, new BigDecimal("18.00"));
		liquidacion49.put(520l, 369l);
		liquidacion49.put(237l, 1019l);
		liquidacion49.put(403l, 648l);
		liquidacion49.put(171l, 878l);
		liquidacion49.put(192l, 1261l);	
		MODELO_COBRO_LIQUIDACION.add(liquidacion49);
		
		ModeloCobrosLiquidacion liquidacion51 = new ModeloCobrosLiquidacion(51l);
		liquidacion51.put(462l, 723l);
		liquidacion51.put(176l, 332l);
		liquidacion51.put(417l, 197l);
		liquidacion51.put(238l, 1311l);
		liquidacion51.put(206l, 918l, new BigDecimal("18.00"));
		liquidacion51.put(520l, 368l);
		liquidacion51.put(237l, 1019l);
		liquidacion51.put(403l, 647l);
		liquidacion51.put(171l, 878l);
		liquidacion51.put(192l, 1446l);	
		MODELO_COBRO_LIQUIDACION.add(liquidacion51);
	}
	private static final Long ID_COBRO_LIMPIEZA = 14584l; 
	
	private EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
	private CobroDAO cobroDao;
	
	public static void main(String[] args) throws Exception
	{
		Parche3 parche3 = new Parche3();
		logger.info("iniciando parcheador");
		parche3.actualizarBD();
		parche3.eliminarCobros();
		parche3.cobrar();
		logger.info("parcheador finalizado");
	}

	private void actualizarBD() throws Exception 
	{
		File archivoParche = File.createTempFile("parche", "sql");
		URL script = getClass().getResource("/parche3.sql");
		IOUtils.copy(script.openStream(),
	            FileUtils.openOutputStream(archivoParche));
		DBUtiles.getInstance().cargarScript(archivoParche);
	}

	public Parche3() throws Exception
	{
		InputStream stream = this.getClass().getResourceAsStream("/log.properties");
		LogManager.getLogManager().readConfiguration(stream);
		cobroDao = DAOFactory.getDefecto().getCobroDAO();
	}
	
	private void eliminarCobros() 
	{
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
		    this.eliminarCobrosLiquidacion();
		    this.eliminarCobroLimpiezaSaldos();
			tx.commit();
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Error en el parcheador: " + e.getMessage(), e);
			tx.rollback();
			e.printStackTrace();
		}
		finally {
			entityManager.close();
		}
	}
	
	private void eliminarCobroLimpiezaSaldos() 
	{
		logger.info("Eliminado cobros por limpieza saldos");
		Cobro cobro = entityManager.getReference(Cobro.class, ID_COBRO_LIMPIEZA);
		loggearCobros(Collections.singletonList(cobro));
		entityManager.remove(cobro);
	}

	@SuppressWarnings("unchecked")
	private List<CuentaCorriente> getCuentasCorrientes(ModeloCobrosLiquidacion liquidacion) 
	{
		//oink oink pero funziona!!!!
		Query query = entityManager.createQuery(
				"select c from CuentaCorriente c " +
				" where c.id in " +
				liquidacion.getCuentasComoString()
			);
		return query.getResultList();
	}
	
	private void eliminarCobrosLiquidacion() throws Exception
	{
		List<ModeloCobrosLiquidacion> liquidaciones = MODELO_COBRO_LIQUIDACION;
		
		for (ModeloCobrosLiquidacion modelo : liquidaciones) {
			eliminarCobros(modelo);
		}
	}
	
	private void cobrar() throws Exception
	{
		for (ModeloCobrosLiquidacion modelo : MODELO_COBRO_LIQUIDACION) { 
			logger.info("cobrando " + modelo);
			Map<Long, BigDecimal> montosPorCuenta = modelo.getMontoPorCuenta();
			Map<CuentaCorriente, BigDecimal> cobros = new HashMap<CuentaCorriente, BigDecimal>();
			
			for (Entry<Long, BigDecimal> cadaMonto : montosPorCuenta.entrySet()) {
				CuentaCorriente cuentaCorriente = getCuentaCorriente(cadaMonto.getKey());
				cobros.put(cuentaCorriente, cadaMonto.getValue());
			}
			
			Liquidacion liquidacion = getLiquidacion(modelo.getIdLiquidacion());
			cobroDao.cobrar(liquidacion,cobros);
			logger.info("cobro finalizado correctamente... ");
		}
	}

	private Liquidacion getLiquidacion(Long idLiquidacion) 
	{
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			return entityManager.find(Liquidacion.class, idLiquidacion);
		}
		finally {
			entityManager.close();
		}
	}

	private CuentaCorriente getCuentaCorriente(Long key) {
		EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
		try {
			return entityManager.find(CuentaCorriente.class, key);
		}
		finally {
			entityManager.close();
		}
	}

	private void eliminarCobros(ModeloCobrosLiquidacion liquidacion) 
	{
		List<CuentaCorriente> cuentas = getCuentasCorrientes(liquidacion);
		logger.info("LIQUIDACION = " + liquidacion.getIdLiquidacion());
		
		for (CuentaCorriente cuenta : cuentas) {
			loggearCuenta(cuenta);
			
			Date fecha = Calendar.getInstance().getTime();
			
					
			List<Cobro> cobros = getCobros(liquidacion.getIdLiquidacion(), cuenta);
			loggearCobros(cobros);
			for (Cobro cobro : cobros) {
				entityManager.remove(cobro);
			}
			BigDecimal sobranteAlDia = liquidacion.getSobrante(cuenta.getId());
			logger.warning("sobrante al día " + formateador.format(fecha) + " para cuenta :" + 
					cuenta.getId() + "=" + sobranteAlDia);

			cuenta.setSobrante(sobranteAlDia);
			entityManager.merge(cuenta);

			logger.fine("terminada persona: " + cuenta.getPersona().getId());
		}
	}

	private void loggearCuenta(CuentaCorriente cuenta) 
	{
		logger.info("Procesando persona: " + 
				"idCuenta=" + cuenta.getId() + "   " + 
				cuenta.getPersona().getNombreYApellido() + 
				"  persona " + 
				" idPersona =" + cuenta.getPersona().getId());
	}

	private void loggearCobros(List<Cobro> cobros) 
	{
		for (Cobro cobro : cobros) {
			logger.warning("Borrando cobro: (" + 
						formateador.format(cobro.getFecha()) + 
						") monto=" + 
						cobro.getMonto() + " idCobro=" + 
						cobro.getId());
		}
	}

	@SuppressWarnings("unchecked")
	private List<Cobro> getCobros(
			Long liquidacion,
			CuentaCorriente cuenta) 
	{
		Query query = entityManager.createQuery(
				"select c from CobroDetalleLiquidacion c " +
				"where c.cobroLiquidacion.liquidacion.id = :liq " +
				"and c.cuentaCorriente = :cuenta");
		query.setParameter("liq", liquidacion);
		query.setParameter("cuenta", cuenta);
		List<Cobro> cobros = query.getResultList();
		return cobros;
	}

}
