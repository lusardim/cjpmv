package ar.gov.cjpmv.prestamos.core.persistence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoTelefono;

@Entity
public class Telefono implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3930038514894699892L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 
	
	@Basic(optional=false)
	private String numero;
	
	@Basic(optional=false)
	@Enumerated(EnumType.STRING)
	private TipoTelefono tipo;
	
	
	private String caracteristica;
	
	@ManyToOne(optional=false)
	private Persona persona;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoTelefono getTipo() {
		return tipo;
	}

	public void setTipo(TipoTelefono tipo) {
		this.tipo = tipo;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	
	@Override
	public String toString() {
		return this.numero;
	}
}
