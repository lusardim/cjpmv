package ar.gov.cjpmv.prestamos.core.business;

import java.io.Serializable;

import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;

public class FiltroBusquedaPersonas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2770254205933286288L;
	private Long legajo;
	private Long cuil;
	private TipoDocumento tipoDocumento;
	private Integer numeroDocumento;
	private String apellido;
	private int estadoActual;
	private Boolean personaFisica;
	private EstadoPersonaFisica estadoPersonaFisica;
	private EstadoPersonaJuridica estadoPersonaJuridica;
	
	public static final String[] ESTADOS = {null,"Activo","Pasivo","Fallecido","Inactivo"};
	
	public Long getLegajo() {
		return legajo;
	}
	public void setLegajo(Long pLegajo) {
		if (pLegajo != null) {
			personaFisica = true;
		}
		this.legajo = pLegajo;
	}
	
	public Long getCuil() {
		return cuil;
	}
	public void setCuil(Long pCuil) {
		this.cuil = pCuil;
	}
	
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumento pTipoDocumento) {
		this.tipoDocumento = pTipoDocumento;
	}
	
	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(Integer pNumeroDocumento) {
		this.numeroDocumento = pNumeroDocumento;
	}
	
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String pApellido) {
		this.apellido = pApellido;
	}
	
	public String getEstado() {
		return ESTADOS[estadoActual];
	}
	public void setEstado(String pEstado) {
		if (pEstado == null) {
			this.estadoActual = 0;
		}
		else { 
			int i = 0;
			while (estadoActual == 0 && i<ESTADOS.length) {
				if (ESTADOS[i].equals(pEstado)) {
					estadoActual = i;
				}
				i++;
			}
			if (estadoActual == 0) {
				throw new IllegalArgumentException("El estado debe pertenecer a una persona");
			}
			setEnumEstado();
		}
	}
		
	private void setEnumEstado() {
		//TODO debería tirar exception cuando se mete chanchada
		switch(estadoActual) {
			case 1: {
				personaFisica = null;
				estadoPersonaFisica = EstadoPersonaFisica.ACTIVO;
				estadoPersonaJuridica = EstadoPersonaJuridica.ACTIVO;
				break;
			}
			case 2: {
				personaFisica = true;
				estadoPersonaFisica = EstadoPersonaFisica.PASIVO;
				estadoPersonaJuridica = null;
				break;
			}
			case 3: {
				personaFisica = true;
				estadoPersonaFisica = EstadoPersonaFisica.FALLECIDO;
				estadoPersonaJuridica = null;
				break;
			}
			case 4: {
				personaFisica = false;
				estadoPersonaFisica = null;
				estadoPersonaJuridica = EstadoPersonaJuridica.INACTIVO;
				break;
			}
		}
	}
	
	public Boolean isPersonaFisica() {
		return personaFisica;
	}
	
	public void setPersonaFisica(boolean personaFisica) {
		this.personaFisica = personaFisica;
	}
	
	public EstadoPersonaFisica getEstadoPersonaFisica() {
		return estadoPersonaFisica;
	}
	
	public EstadoPersonaJuridica getEstadoPersonaJuridica() {
		return estadoPersonaJuridica;
	}
	
}
