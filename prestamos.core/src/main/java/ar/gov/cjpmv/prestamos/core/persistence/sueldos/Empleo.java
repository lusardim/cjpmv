package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionRevista;

@Entity
public class Empleo implements Serializable {

	private static final long serialVersionUID = 1015247641122080002L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	@Temporal(TemporalType.DATE)
	private Date fechaFin;
	private String funcion;
	@Enumerated(EnumType.STRING)
	private SituacionRevista situacionRevista;
	
	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria tipoCategoria;
	@ManyToOne
	private PermanenciaCategoria permanencia;
	@ManyToOne
	private Antiguedad antiguedad;
	
	@ManyToOne(optional=false)
	private PersonaFisica empleado;
	@ManyToOne(optional=false)
	private PersonaJuridica empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public SituacionRevista getSituacionRevista() {
		return situacionRevista;
	}

	public void setSituacionRevista(SituacionRevista situacionRevista) {
		this.situacionRevista = situacionRevista;
	}

	public PersonaFisica getEmpleado() {
		return empleado;
	}

	public void setEmpleado(PersonaFisica empleado) {
		this.empleado = empleado;
	}

	public PersonaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PersonaJuridica empresa) {
		this.empresa = empresa;
	}

	public void setTipoCategoria(Categoria tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}

	public Categoria getTipoCategoria() {
		return tipoCategoria;
	}

	public String toString(){
		return " con lugar: "+this.empresa+" y fecha de inicio:"+this.fechaInicio;
	}

	public PermanenciaCategoria getPermanencia() {
		return permanencia;
	}

	public void setPermanencia(PermanenciaCategoria permanencia) {
		this.permanencia = permanencia;
	}

	public Antiguedad getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(Antiguedad antiguedad) {
		this.antiguedad = antiguedad;
	}
	
}