package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="clase")
public abstract class Beneficio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5727853614990665150L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional=false)
	private PersonaFisica persona;
	
	@Temporal(TemporalType.DATE)
	private Date fechaOtorgamiento;
	private String decreto;
	private String ordenanza;
	@Column(columnDefinition="decimal(16,6)")
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	private TipoBeneficio tipoBeneficio;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PersonaFisica getPersona() {
		return persona;
	}
	public void setPersona(PersonaFisica persona) {
		this.persona = persona;
	}
	public Date getFechaOtorgamiento() {
		return fechaOtorgamiento;
	}
	public void setFechaOtorgamiento(Date fechaOtorgamiento) {
		this.fechaOtorgamiento = fechaOtorgamiento;
	}
	public String getDecreto() {
		return decreto;
	}
	public void setDecreto(String decreto) {
		this.decreto = decreto;
	}
	public String getOrdenanza() {
		return ordenanza;
	}
	public void setOrdenanza(String ordenanza) {
		this.ordenanza = ordenanza;
	}
	public TipoBeneficio getTipoBeneficio() {
		return tipoBeneficio;
	}
	
	protected void setTipoBeneficio(TipoBeneficio tipoBeneficio) {
		this.tipoBeneficio = tipoBeneficio;
	}
	
	/**
	 * este valor se utiliza a la hora de calcular cuanto va en concepto de 
	 * jubilación o pensión. 
	 */
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
