package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Garantia implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1072595015006744046L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String detalle;
	
	@Column(nullable=false,columnDefinition="decimal(5,2)")
	@Basic(optional=false)
	private BigDecimal porcentaje;

	@ManyToOne(optional=false)
	private Credito credito;
	
	@ManyToOne
	private ViaCobro viaCobro;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private Boolean afectar=false;
	
	/**
	 * Si no es nulo, representa la cuenta corriente del garante.
	 */
	@ManyToOne(optional=true)
	private CuentaCorriente afectarA;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Credito getCredito() {
		return credito;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public ViaCobro getViaCobro() {
		return viaCobro;
	}

	public void setViaCobro(ViaCobro viaCobro) {
		this.viaCobro = viaCobro;
	}

	public void setAfectar(Boolean afectar) {
		this.afectar = afectar;
	}

	public Boolean getAfectar() {
		return afectar;
	}

	public CuentaCorriente getAfectarA() {
		return afectarA;
	}

	public void setAfectarA(CuentaCorriente afectarA) {
		this.afectarA = afectarA;
	}

}
