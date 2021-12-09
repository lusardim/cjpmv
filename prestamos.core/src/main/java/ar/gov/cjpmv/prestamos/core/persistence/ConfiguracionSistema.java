package ar.gov.cjpmv.prestamos.core.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema;

@Entity
public class ConfiguracionSistema implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5114729125553125221L;

	@Id
	private Long id=1l;
	
	@ManyToOne(optional=false)
	private PersonaJuridica persona;

	@Enumerated(EnumType.STRING)
	private Sistema.TipoSistema sistemaPorDefecto=Sistema.TipoSistema.DIRECTO;
	private Integer diaVencimiento = 5;
	
	@Column(columnDefinition="decimal(12,2)")
	private BigDecimal interesPorDefecto = new BigDecimal(2);
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PersonaJuridica getPersona() {
		return persona;
	}
	public void setPersona(PersonaJuridica persona) {
		this.persona = persona;
	}
	public Sistema.TipoSistema getSistemaPorDefecto() {
		return sistemaPorDefecto;
	}
	public void setSistemaPorDefecto(Sistema.TipoSistema sistemaPorDefecto) {
		this.sistemaPorDefecto = sistemaPorDefecto;
	}
	public Integer getDiaVencimiento() {
		return diaVencimiento;
	}
	public void setDiaVencimiento(Integer diaVencimiento) {
		this.diaVencimiento = diaVencimiento;
	}
	public void setInteresPorDefecto(BigDecimal interesPorDefecto) {
		this.interesPorDefecto = interesPorDefecto;
	}
	public BigDecimal getInteresPorDefecto() {
		return interesPorDefecto;
	}
	
	
}
