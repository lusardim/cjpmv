package ar.gov.cjpmv.prestamos.core;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.dao.ConfiguracionSistemaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.ProvinciaDAO;
import ar.gov.cjpmv.prestamos.core.persistence.ConfiguracionSistema;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Domicilio;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;
import ar.gov.cjpmv.prestamos.core.persistence.Telefono;
import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoTelefono;

public class ConfiguracionSistemaDAOTest {
	
	private static PersonaDAO personaDAO;
	private EntityManager entityManager;
	private static ConfiguracionSistemaDAO configuracionSistemaDAO;
	
	private ProvinciaDAO locProvinciaDAO = new ProvinciaDAO();
	
	@BeforeClass
	public static void init(){
		configuracionSistemaDAO = new ConfiguracionSistemaDAO();
		personaDAO = new PersonaDAO();
	}
	
	private void persistirPersonaJuridica(PersonaJuridica locPersonaJuridica) throws Exception{
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction locTx = this.entityManager.getTransaction();
		try{
			locTx.begin();			
			this.entityManager.persist(locPersonaJuridica.getDomicilio().getLocalidad().getDepartamento().getProvincia());
			this.entityManager.persist(locPersonaJuridica.getDomicilio().getLocalidad().getDepartamento());
			this.entityManager.persist(locPersonaJuridica.getDomicilio().getLocalidad());
			//El domicilio se guarda en cascada
			this.entityManager.persist(locPersonaJuridica);
			
			locTx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			locTx.rollback();
			throw e;
		}
	}
	
	
	private PersonaJuridica crearPersonaJuridica(){
		PersonaJuridica locPersonaJuridica = new PersonaJuridica();
		Domicilio locDomicilio = new Domicilio();
		/*Departamento locDepatamento = new Departamento();
		Provincia locProvincia = new Provincia();
		Localidad locLocalidad = new Localidad();
		*/
		
		Provincia locVictoria = locProvinciaDAO.findListaPronvincia("Victoria").get(0);
		
		locPersonaJuridica.setNombre("Caja de Jubilaciones y Pensiones Municipal de Victoria");
		locPersonaJuridica.setCui(30681122954l);
		locPersonaJuridica.setDomicilio(locDomicilio);
		
		locDomicilio.setCalle("Laprida");
		locDomicilio.setNumero(636);
				
		
	
		//locLocalidad.setNombre("Victoria");
		//locLocalidad.setDepartamento(locDepatamento);
		
		//locDepatamento.setNombre("Victoria");
		//locDepatamento.setProvincia(locProvincia);
		
		//locProvincia.setNombre("Entre Ríos");
		
		Telefono locTelefono = new Telefono();
		locTelefono.setCaracteristica("03436");
		locTelefono.setNumero("424641");
		locTelefono.setTipo(TipoTelefono.FIJO);
		locTelefono.setPersona(locPersonaJuridica);
		
		locPersonaJuridica.getListaTelefonos().add(locTelefono);
		locPersonaJuridica.getEmails().add("cajajubvictoria@ciudad.com.ar");
		
		return locPersonaJuridica;
	}
	
	@Ignore
	@Test
	public void getDepartamentoSistemaTest() throws Exception{
		PersonaJuridica locPersonaJuridica = this.crearPersonaJuridica();
		this.persistirPersonaJuridica(locPersonaJuridica);

		ConfiguracionSistema locConfiguracionSistema = new ConfiguracionSistema();
		locConfiguracionSistema.setPersona(locPersonaJuridica);

		configuracionSistemaDAO.agregar(locConfiguracionSistema);
		
		
		Departamento locDepartamento = configuracionSistemaDAO.getDepartamentoSistema();
		
	//	assertTrue("El departamento y el del sistema no son los mimos",locDepartamento.getId().equals(locPersonaJuridica.getDomicilio().getDepartamento().getId()));
		
	//	configuracionSistemaDAO.eliminar(locConfiguracionSistema);
//		this.borrarPersonaJuridica(locPersonaJuridica);
		this.entityManager.close();
	}
	
		
	
	private void borrarPersonaJuridica(PersonaJuridica pPersonaJuridica) throws Exception {
		this.entityManager = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction locTx = this.entityManager.getTransaction();
		try{
			locTx.begin();
			PersonaJuridica locPersonaJuridica = this.entityManager.find(PersonaJuridica.class,pPersonaJuridica.getId());
			
			this.entityManager.merge(locPersonaJuridica.getDomicilio().getDepartamento().getProvincia());
			this.entityManager.remove(locPersonaJuridica.getDomicilio().getDepartamento().getProvincia());
			
			
			this.entityManager.remove(locPersonaJuridica.getDomicilio().getDepartamento());
			//El domicilio se guarda en cascada
			this.entityManager.remove(locPersonaJuridica);
			
			
			locTx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			locTx.rollback();
			throw e;
		}
		
	}



	
}
