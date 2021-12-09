package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoCuenta;
import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoFormulario;

@Entity
public class TipoCredito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6348548773788133911L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	@Basic(optional=false)
	private String nombre;
	
	@Enumerated(EnumType.STRING)
	private TipoFormulario tipoFormulario=TipoFormulario.F01;
	
	@ManyToMany
	private List<Concepto> listaConceptos=new ArrayList<Concepto>();
	
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
		TipoCredito other = (TipoCredito) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

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


	public List<Concepto> getListaConceptos() {
		return listaConceptos;
	}

	public void setListaConceptos(List<Concepto> listaConceptos) {
		this.listaConceptos = listaConceptos;
	}
	
	

	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}
	
	public String toString(){
		return this.getNombre();
	}
	
	
}
