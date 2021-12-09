package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
/**
 * Esta clase representa el porcentaje fijo que se aplica para 
 * cada tipo de beneficio ej. 82% movil para jub
 * @author pulpol
 *
 */
@Entity
public class PorcentajeTipoBeneficio implements Serializable{
	
	private static final long serialVersionUID = 5156819882918003142L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Basic(optional=false)
	@Column(columnDefinition="decimal(16,6)", nullable=false)
	private BigDecimal porcentaje;
	
	@Basic(optional=false)
	@Enumerated(EnumType.STRING)
	private TipoBeneficio tipo;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}
	public TipoBeneficio getTipo() {
		return tipo;
	}
	public void setTipo(TipoBeneficio tipo) {
		this.tipo = tipo;
	}

}
