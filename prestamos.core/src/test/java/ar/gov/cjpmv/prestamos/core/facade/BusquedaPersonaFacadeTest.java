package ar.gov.cjpmv.prestamos.core.facade;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.facades.BusquedaPersonaFacade;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;

public class BusquedaPersonaFacadeTest {
	private static BusquedaPersonaFacade busquedaPersonaFacade;

	public List<Persona> findListaPersonas(Long pLegajo, Long pCuil, TipoDocumento pTipoDocumento, 
			Integer pNumeroDocumento, String pApellido, EstadoPersonaFisica pEstado){
		//FIXME VER BUSQUEDA!!!
		return null;
	}
	
	@BeforeClass
	public static void init(){
		busquedaPersonaFacade = new BusquedaPersonaFacade();
	}
	
	@Ignore
	@Test
	public void getListaTiposDocumentosTest(){
		List<TipoDocumento> locListaTiposDocumentos = busquedaPersonaFacade.getListaTiposDocumentos();
		assertNotNull("la lista es nula",locListaTiposDocumentos);
		assertFalse("La lista está vacía", locListaTiposDocumentos.isEmpty());
	}
	
	@Test
	public void getListaEstadosTest(){
		EstadoPersonaJuridica[] locListaEstadosPersonasJuridicas = EstadoPersonaJuridica.values();
		EstadoPersonaFisica[] locListaEstadosPersonasFisicas = EstadoPersonaFisica.values();
		
		Set<String> locEstados = busquedaPersonaFacade.getListaEstados();
	
		boolean todos = true;
		for (EstadoPersonaFisica cadaEstadoPersonaFisica: locListaEstadosPersonasFisicas){
			if (!locEstados.contains(cadaEstadoPersonaFisica.getDescripcion())){
				todos = false;
			}
			assertTrue("El estado "+cadaEstadoPersonaFisica+" no se encuentra en el listado",todos);
		}

		for (EstadoPersonaJuridica cadaEstadoPersonaJuridica: locListaEstadosPersonasJuridicas){
			if (!locEstados.contains(cadaEstadoPersonaJuridica.getDescripcion())){
				todos = false;
			}
			assertTrue("El estado "+cadaEstadoPersonaJuridica+" no se encuentra en el listado",todos);
		}
	}
	
}
