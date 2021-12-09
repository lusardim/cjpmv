package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.persistence.Persona;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Cobro implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7193963873888148574L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	@Basic(optional=false)
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal monto;
	
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal sobranteAlDia = new BigDecimal(0);
	
	@Basic(optional=false)
	@OneToMany(mappedBy="cobro", cascade=CascadeType.ALL)
	private List<Cancelacion> listaCancelaciones;
	
	@ManyToOne
	private CuentaCorriente cuentaCorriente;
	
	@ManyToOne(optional=false)
	private Persona pagador;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getSobranteAlDia() {
		if (this.sobranteAlDia == null){
			sobranteAlDia = new BigDecimal("0")
							.setScale(2);
		}
		return sobranteAlDia;
	}

	public void setSobranteAlDia(BigDecimal sobranteAlDia) {
		this.sobranteAlDia = sobranteAlDia;
	}

	public List<Cancelacion> getListaCancelaciones() {
		return listaCancelaciones;
	}

	public void setListaCancelaciones(List<Cancelacion> listaCancelaciones) {
		this.listaCancelaciones = listaCancelaciones;
	}

	public CuentaCorriente getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public Persona getPagador() {
		return pagador;
	}

	public void setPagador(Persona pagador) {
		this.pagador = pagador;
	}
	
}
