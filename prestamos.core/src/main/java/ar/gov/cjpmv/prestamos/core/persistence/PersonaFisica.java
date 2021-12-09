package ar.gov.cjpmv.prestamos.core.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCivil;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.Sexo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;


@Entity
public class PersonaFisica extends Persona{
	/**
	 * 
	 */
	private static final long serialVersionUID = 367652290837514595L;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private String apellido;
	
	private Long legajo;
	
	@Temporal(TemporalType.DATE)
	private Date fechaDefuncion;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private Integer numeroDocumento;
	
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;
	
	@Enumerated(EnumType.STRING)
	private EstadoPersonaFisica estado=EstadoPersonaFisica.ACTIVO;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@ManyToOne(optional=false)
	private TipoDocumento tipoDocumento;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="empleado")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Empleo> listaEmpleos = new ArrayList<Empleo>();

	@OneToMany(mappedBy="persona", cascade=CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Beneficio> listaBeneficios = new ArrayList<Beneficio>();
	
	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}
	
	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getApellido() {
		return apellido;
	}
	public void setNumeroDocumento(int numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}
	
	public EstadoPersonaFisica getEstado() {
		return estado;
	}

	public void setEstado(EstadoPersonaFisica estado) {
		this.estado = estado;
	}
		
	public void setLegajo(Long legajo) {
		this.legajo = legajo;
	}
	public void setNumeroDocumento(Integer numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setListaEmpleos(List<Empleo> listaEmpleos) {
		this.listaEmpleos = listaEmpleos;
	}
	public List<Empleo> getListaEmpleos() {
		return listaEmpleos;
	}
	
	public Long getLegajo() {
		return legajo;
	}
	
	@Override
	public String getNombreYApellido() {
		return ((this.apellido==null)?"":(this.apellido+", "))
				+
			   ((this.getNombre()==null)?"":this.getNombre());
	}
	
	
	@Override
	public String toString() {
		StringBuilder locResultado = new StringBuilder();
		locResultado.append((this.getTipoDocumento()!=null)?this.getTipoDocumento():"");
		locResultado.append(":");
		locResultado.append((this.getNumeroDocumento()!=null)?this.getNumeroDocumento():"");
		locResultado.append(" - ");
		locResultado.append(this.getNombreYApellido());
		return locResultado.toString();
	}

	public List<Beneficio> getListaBeneficios() {
		return listaBeneficios;
	}

	public void setListaBeneficios(List<Beneficio> listaBeneficios) {
		this.listaBeneficios = listaBeneficios;
	}
	
}
