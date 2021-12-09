package ar.gov.cjpmv.prestamos.core.persistence.seguridad;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;

@Entity
public class ModuloSistema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 453587279875517854L;
	
	@Id
	private int id;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private String descripcion;

	@ManyToMany(mappedBy="listaModulos")
	private List<Usuario> listaUsuarios;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}



	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	@Basic(optional=false)
	@Column(nullable=false)
	private boolean habilitado;
	

	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		ModuloSistema other = (ModuloSistema) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String toString(){
		return this.getDescripcion();
	}
	
}
