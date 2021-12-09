package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Plantilla implements Serializable {

	private static final long serialVersionUID = 6564484327291307234L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(unique=true)
	@Enumerated(EnumType.STRING)
	private TipoLiquidacion tipoLiquidacion;
	@ManyToMany
	@JoinTable(name="conceptohaberes_plantilla", 
		inverseJoinColumns = {@JoinColumn(name="conceptohaberes_id")},
		joinColumns={@JoinColumn(name="plantilla_id")}
	)
	private List<ConceptoHaberes> listaConceptos;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoLiquidacion getTipoLiquidacion() {
		return tipoLiquidacion;
	}
	public void setTipoLiquidacion(TipoLiquidacion tipoLiquidacion) {
		this.tipoLiquidacion = tipoLiquidacion;
	}
	public void setListaConceptos(List<ConceptoHaberes> listaConceptos) {
		this.listaConceptos = listaConceptos;
	}
	public List<ConceptoHaberes> getListaConceptos() {
		return listaConceptos;
	}

	public void add(ConceptoHaberes concepto) {
		if (listaConceptos == null) {
			listaConceptos = new ArrayList<ConceptoHaberes>();
			listaConceptos.add(concepto);
		}
	}
}
