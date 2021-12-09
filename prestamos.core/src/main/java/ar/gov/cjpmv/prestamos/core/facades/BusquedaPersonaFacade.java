package ar.gov.cjpmv.prestamos.core.facades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.gov.cjpmv.prestamos.core.business.FiltroBusquedaPersonas;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.TipoDocumentoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;


/**
 * Fachada para búsqueda de personas
 * @author pulpol
 *
 */
public class BusquedaPersonaFacade {
	private PersonaDAO personaDao;
	private TipoDocumentoDAO tipoDocumentoDAO;
	
	public BusquedaPersonaFacade() {
		this.personaDao = new PersonaDAO();
		this.tipoDocumentoDAO = new TipoDocumentoDAO();
	}

	public List<Persona> findListaPersonas(FiltroBusquedaPersonas pFiltro){
		return this.personaDao.findListaPersonas(pFiltro);
	}
	
	public List<Persona> findListaPersonas(Long pLegajo, Long pCuil, TipoDocumento pTipoDocumento, 
			Integer pNumeroDocumento, String pApellido, String pEstado){
		List<Persona> locListaResultante = new ArrayList<Persona>();
	
		if ((pEstado==null)||(!pEstado.equals("Inactivo"))){
			List<PersonaFisica> locListaPersonasFisicas = this.getListaPersonasFisicas(pLegajo, pCuil, pTipoDocumento, pNumeroDocumento, pApellido, pEstado);
			if (locListaPersonasFisicas!=null){
				locListaResultante.addAll(locListaPersonasFisicas);
			}
		
		}
		
	
		if ((pCuil==null)&&(pTipoDocumento==null)&&(pNumeroDocumento==null)&&(pLegajo==null)){	
			List<PersonaJuridica> locListaPersonasJuridicas = this.getListaPersonasJuridicas(pCuil,pApellido, pEstado);
			if (locListaPersonasJuridicas!=null){
				locListaResultante.addAll(locListaPersonasJuridicas);
			}
		}
		
		return locListaResultante;
	} 
	
	public List<Persona> findListaPersonasConCuentaCorriente(Long pLegajo, Long pCuil, TipoDocumento pTipoDocumento, 
			Integer pNumeroDocumento, String pApellido, String pEstado){
		return personaDao.findListaPersonasConCuenta(pLegajo, pCuil, pTipoDocumento, pNumeroDocumento, pApellido, pEstado);
	} 
	
	/**
	 * Obtiene el listado de personas juridicas 
	 * @param pCuil
	 * @param pRazonSocial
	 * @param pEstado
	 * @return
	 */
	private List<PersonaJuridica> getListaPersonasJuridicas(Long pCuil,
			String pRazonSocial, String pEstado) {
		EstadoPersonaJuridica locEstadoPersonaJuridica = null;
		if (pEstado!=null){
			locEstadoPersonaJuridica = this.personaDao.getEstadoPersonaJuridicaFromDescripcion(pEstado);
			if (locEstadoPersonaJuridica!=null){
				return this.personaDao.findListaPersonasJuridicas(pCuil, pRazonSocial, locEstadoPersonaJuridica);
			}
		}
		else{
			return this.personaDao.findListaPersonasJuridicas(pCuil, pRazonSocial, locEstadoPersonaJuridica); 
		}
		return null;
	}

	/**
	 * Obtiene la lista de personas físicas con los parametros, 
	 * @param pLegajo
	 * @param pCuil
	 * @param pTipoDocumento
	 * @param pNumeroDocumento
	 * @param pApellido
	 * @param pEstado
	 * @return
	 */
	private List<PersonaFisica> getListaPersonasFisicas(Long pLegajo, Long pCuil, TipoDocumento pTipoDocumento, Integer pNumeroDocumento, String pApellido, String pEstado) {
		List<PersonaFisica> locListaPersonasFisicas=null;
		if (pEstado!=null){
			EstadoPersonaFisica locEstadoPersonaFisica = this.personaDao.getEstadoPersonaFisicaFromDescripcion(pEstado);
			if (locEstadoPersonaFisica!=null){
				locListaPersonasFisicas = this.personaDao.findListaPersonasFisicas(pLegajo, pCuil, pTipoDocumento, pNumeroDocumento, pApellido, locEstadoPersonaFisica);
			}
		}
		else{
			locListaPersonasFisicas = this.personaDao.findListaPersonasFisicas(pLegajo, pCuil, pTipoDocumento, pNumeroDocumento, pApellido, null);
		}
		return locListaPersonasFisicas;
		
	}

	/**
	 * Se encarga de devolver un conjunto de los posibles valores que pueden tomar 
	 * los estados de las personas
	 * @return
	 */
	public Set<String> getListaEstados(){
		EstadoPersonaFisica[] listaEstados = EstadoPersonaFisica.values();
		EstadoPersonaJuridica[] listaEstadosPersonaJuridica = EstadoPersonaJuridica.values();
		
		Set<String> locResultado = new HashSet<String>();
		for (EstadoPersonaFisica cadaEstadoPersonaFisica : listaEstados){
			locResultado.add(cadaEstadoPersonaFisica.getDescripcion());
		}
		
		for (EstadoPersonaJuridica cadaJuridica : listaEstadosPersonaJuridica){
			locResultado.add(cadaJuridica.getDescripcion());
		}
		
		return locResultado;
	}

	
	/**
	 * Obtiene la lista de tipos de documentos
	 * @return
	 */
	public List<TipoDocumento> getListaTiposDocumentos() {
		return this.tipoDocumentoDAO.findListaTipoDocumento(null);
	}

	
	public void eliminar(Persona pPersona) throws LogicaException{
		this.personaDao.eliminar(pPersona);
	}
	
}
