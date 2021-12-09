package ar.gov.cjpmv.prestamos.core.persistence.contable;

import java.io.Serializable;
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

import org.hibernate.annotations.Cascade;

@Entity
public class Asiento implements Serializable{
	
	private static final long serialVersionUID = 156658537051263605L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	@Basic(optional=false)
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private Integer numero;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private String descripcion;
	
	@ManyToOne(optional=false)
	private LibroDiario libroDiario;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="asiento")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Movimiento> listaMovimiento=new ArrayList<Movimiento>();
	
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LibroDiario getLibroDiario() {
		return libroDiario;
	}

	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}

	public List<Movimiento> getListaMovimiento() {
		return listaMovimiento;
	}

	public void setListaMovimiento(List<Movimiento> listaMovimiento) {
		this.listaMovimiento = listaMovimiento;
		if (listaMovimiento != null) {
			for (Movimiento cadaMovimiento : this.listaMovimiento) {
				cadaMovimiento.setAsiento(this);
			}
		}
	}
}
