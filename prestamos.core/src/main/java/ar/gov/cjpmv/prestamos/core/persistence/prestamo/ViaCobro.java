package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ViaCobro implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4405335072641784232L;
//Vias de cobros fijas
	public static ViaCobro municipalidad;
	public static ViaCobro caja;
	public static ViaCobro banco;
	public static ViaCobro incobrable;
	
	@Id
	private Long id;

	@Column(nullable=false)
	@Basic(optional=false)
	private String nombre;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private Boolean liquidable;
	
	@OneToMany(mappedBy="viaCobro")
	private List<Credito> listaCreditos;

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

	public Boolean getLiquidable() {
		return liquidable;
	}

	public void setLiquidable(Boolean liquidable) {
		this.liquidable = liquidable;
	}

	public List<Credito> getListaCreditos() {
		return listaCreditos;
	}

	public void setListaCreditos(List<Credito> listaCreditos) {
		this.listaCreditos = listaCreditos;
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
		ViaCobro other = (ViaCobro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
