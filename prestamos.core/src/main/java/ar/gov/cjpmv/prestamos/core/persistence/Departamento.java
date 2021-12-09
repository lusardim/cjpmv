package ar.gov.cjpmv.prestamos.core.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Departamento implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 326709523020554994L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private String nombre;
	
	@OneToMany(mappedBy="departamento")
	private List<Localidad> listaLocalidad;

	@ManyToOne
	private Provincia provincia;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setListaLocalidad(List<Localidad> listaLocalidad) {
		this.listaLocalidad = listaLocalidad;
	}

	public List<Localidad> getListaLocalidad() {
		if (this.listaLocalidad==null){
			this.listaLocalidad = new ArrayList<Localidad>();
		}
		return listaLocalidad;
	}


	public void setProvincia(Provincia provincia) {
		if (this.provincia != provincia){
			if (this.provincia!=null){
				this.provincia.getListaDepartamento().remove(this);
			}
			
			if ((provincia!=null)&&(provincia.getListaDepartamento().contains(this))){
				provincia.getListaDepartamento().add(this);
			}
		}
		this.provincia = provincia;
	}



	public Provincia getProvincia() {
		return provincia;
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
		Departamento other = (Departamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
