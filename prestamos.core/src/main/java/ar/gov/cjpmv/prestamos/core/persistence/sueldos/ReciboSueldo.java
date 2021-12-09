package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import ar.gov.cjpmv.prestamos.core.business.sueldos.ServicioCalculoConcepto;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;

@Entity
public class ReciboSueldo implements Serializable {
	
	private static final long serialVersionUID = -1938774135776456332L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="liquidacion_id")
	private LiquidacionHaberes liquidacion;
	@OneToMany(mappedBy="reciboSueldo", cascade=CascadeType.ALL)
	@Sort(comparator=LineaReciboComparator.class, type=SortType.COMPARATOR)
	private Set<LineaRecibo> lineasRecibo = new TreeSet<LineaRecibo>(new LineaReciboComparator());
	@ManyToOne(optional=false)
	private PersonaFisica persona;
	
	@Transient
	private ServicioCalculoConcepto servicioCalculoConcepto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LiquidacionHaberes getLiquidacion() {
		return liquidacion;
	}
	public void setLiquidacion(LiquidacionHaberes liquidacion) {
		this.liquidacion = liquidacion;
	}
	public Set<LineaRecibo> getLineasRecibo() {
		return lineasRecibo;
	}
	public void setLineasRecibo(Set<LineaRecibo> lineasRecibo) {
		this.lineasRecibo = lineasRecibo;
	}
	
	public void add(LineaRecibo lineaRecibo) {
		lineaRecibo.setReciboSueldo(this);
		//El monto no puede ser nulo, en caso que lo sea se recalcula usando el concepto
		// si el concepto es nulo, debería tirar un NullPointerException
		if (lineaRecibo.getMonto() == null) {
			ConceptoHaberes concepto = lineaRecibo.getConcepto();
			concepto.setServicio(getServicioCalculoConcepto());
			BigDecimal valor = concepto.getMonto(this);
			if (valor == null) {
				valor = new BigDecimal(0);
			}
			lineaRecibo.setMonto(valor);
		}
		this.lineasRecibo.add(lineaRecibo);
	}
	
	private ServicioCalculoConcepto getServicioCalculoConcepto() {
		if (servicioCalculoConcepto == null) {
			servicioCalculoConcepto = new ServicioCalculoConcepto();
		}
		return servicioCalculoConcepto;
	}
	
	public void remove(LineaRecibo lineaRecibo) {
		if (this.lineasRecibo.contains(lineaRecibo)) {
			this.lineasRecibo.remove(lineaRecibo);
		}
	}
	
	public PersonaFisica getPersona() {
		return persona;
	}
	public void setPersona(PersonaFisica persona) {
		this.persona = persona;
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
		ReciboSueldo other = (ReciboSueldo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void recalcularSegunConceptos() {
		for (LineaRecibo linea : this.getLineasRecibo()) {
			if (linea.getConcepto() instanceof ConceptoPorcentual) {
				ConceptoPorcentual porcentual = (ConceptoPorcentual)linea.getConcepto();
				if (porcentual.getSobreTotalTipo() != null) {
					linea.setMonto(porcentual.getMonto(this));
				}
			}
		}
	}

	public void restoreFromMemento(MementoListaLineaRecibo pMemento){
		this.lineasRecibo= pMemento.getSaveState();
		
	}
	
	public void setServicio(ServicioCalculoConcepto servicioCalculoConcepto) {
		this.servicioCalculoConcepto = servicioCalculoConcepto;
	}
	
}
