package ar.gov.cjpmv.prestamos.core.persistence.contable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

@Entity
public class LibroDiario implements Serializable {

	private static final long serialVersionUID = 337092963125197219L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="libroDiario")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Asiento> listaAsiento=new ArrayList<Asiento>();

	@OneToOne
	private EjercicioContable periodoContable;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public List<Asiento> getListaAsiento() {
		return listaAsiento;
	}

	public void setListaAsiento(List<Asiento> listaAsiento) {
		this.listaAsiento = listaAsiento;
	}

	public EjercicioContable getPeriodoContable() {
		return periodoContable;
	}

	public void setPeriodoContable(EjercicioContable periodoContable) {
		this.periodoContable = periodoContable;
	}
}
