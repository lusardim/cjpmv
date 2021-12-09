package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.persistence.Persona;

@Entity
public class CuentaCorriente implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -7639108515178661679L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String codigo;
	
	@Basic(optional=false)
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal sobrante = new BigDecimal(0);

	@ManyToOne(optional=false)
	private Persona persona;
	
	@OneToMany(cascade=CascadeType.MERGE, mappedBy="cuentaCorriente")
	private List<Credito> listaCredito=new ArrayList<Credito>();
	
	@OneToMany(mappedBy="cuentaCorriente")
	private List<Cobro> listaCobro=new ArrayList<Cobro>();
	
	@OneToMany(mappedBy="afectarA")
	private List<Garantia> garantiasEnCurso=new ArrayList<Garantia>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * El sobrante es el monto que queda cuando se realizó un pago parcial 
	 * (no alcanzó a saldar ninguna cuota)
	 * @return
	 */
	public BigDecimal getSobrante() {
		return sobrante;
	}

	public void setSobrante(BigDecimal sobrante) {
		this.sobrante = sobrante;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @param listaCredito the listaCredito to set
	 */
	public void setListaCredito(List<Credito> listaCredito) {
		this.listaCredito = listaCredito;
	}

	/**
	 * @return the listaCredito
	 */
	public List<Credito> getListaCredito() {
		return listaCredito;
	}

	public void setListaCobro(List<Cobro> listaCobro) {
		this.listaCobro = listaCobro;
	}

	public List<Cobro> getListaCobro() {
		return listaCobro;
	}

	public List<Garantia> getGarantiasEnCurso() {
		return garantiasEnCurso;
	}

	public void setGarantiasEnCurso(List<Garantia> garantiasEnCurso) {
		this.garantiasEnCurso = garantiasEnCurso;
	}

	@Override
	public String toString() {
		return "["+this.getId()+"]"+this.getPersona().getNombre();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentaCorriente other = (CuentaCorriente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addCredito(Credito credito) {
		this.getListaCredito().add(credito);
	}
}
