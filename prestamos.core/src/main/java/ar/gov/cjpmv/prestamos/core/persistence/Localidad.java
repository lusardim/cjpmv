package ar.gov.cjpmv.prestamos.core.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Localidad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 65196653755762346L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nombre;
	
	@OneToMany(mappedBy="localidad")
	private List<Domicilio> listaDomicilio;
	
	@ManyToOne(optional=false)
	private Departamento departamento;
	

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setListaDomicilio(List<Domicilio> listaDomicilio) {
		this.listaDomicilio = listaDomicilio;
	}
	public List<Domicilio> getListaDomicilio() {
		return listaDomicilio;
	}
	
	@Override
	public String toString() {
		return this.getNombre();
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
		Localidad other = (Localidad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
