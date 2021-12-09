package ar.gov.cjpmv.prestamos.core.persistence;

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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionOfElements;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cobro;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Persona implements Serializable{

	private static final long serialVersionUID = -8669203445918297319L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private Long id;
	
	/**
	 * Clave única de identificación personal 
	 */
	@Column(length=11)
	private Long cui;
	
	
	@Basic(optional=false)
	private String nombre;
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	@CollectionOfElements
	private List<String> emails= new ArrayList<String>();
	
	@OneToOne(cascade=CascadeType.ALL)
	private Domicilio domicilio;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="persona")
	private List<Telefono> listaTelefonos=new ArrayList<Telefono>();
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="emitidoA")
	private List<Cheque> listaChequesOtorgados=new ArrayList<Cheque>();
	
	@OneToMany(mappedBy="pagador")
	private List<Cobro> listaPagosRealizados;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}

	public void setFechaNacimiento(Date fechaNaciemiento) {
		this.fechaNacimiento = fechaNaciemiento;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	public List<String> getEmails() {
		return emails;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public List<Telefono> getListaTelefonos() {
		return listaTelefonos;
	}
	
	public void setListaTelefonos(List<Telefono> listaTelefonos) {
		this.listaTelefonos = listaTelefonos;
		for (Telefono cadaTelefono: this.listaTelefonos){
			cadaTelefono.setPersona(this);
		}
	}
	
	public Long getCui() {
		return cui;
	}
	public void setCui(Long cui) {
		this.cui = cui;
	}
	
	public abstract String getNombreYApellido();
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
		Persona other = (Persona) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public List<Cheque> getListaChequesOtorgados() {
		return listaChequesOtorgados;
	}
	public void setListaChequesOtorgados(List<Cheque> listaChequesOtorgados) {
		this.listaChequesOtorgados = listaChequesOtorgados;
	}
	public List<Cobro> getListaPagosRealizados() {
		return listaPagosRealizados;
	}
	public void setListaPagosRealizados(List<Cobro> listaPagosRealizados) {
		this.listaPagosRealizados = listaPagosRealizados;
	}
	
	

}
