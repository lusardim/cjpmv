package ar.gov.cjpmv.prestamos.core.business;

import java.util.List;

import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.facades.BusquedaPersonaFacade;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;

public class ResultadoPaginadoBusquedaPersona extends ResultadoPaginado<List<Persona>, Persona>{

	private FiltroBusquedaPersonas filtro;
	private PersonaDAO personaDAO; 
	
	public ResultadoPaginadoBusquedaPersona(FiltroBusquedaPersonas filtro) {
		this.filtro = filtro;
	}
	@Override
	protected void actualizarLista() {
		
		
	}

}
