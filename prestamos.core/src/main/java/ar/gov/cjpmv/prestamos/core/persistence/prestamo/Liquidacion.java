package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

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




@Entity
public class Liquidacion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5304613383981610283L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Basic(optional=false)
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaLiquidacion;
	
	@Basic(optional=false)
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaGeneracion;
	
	@ManyToOne
	private Liquidacion liquidacionAnterior;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="liquidacion")
	private List<DetalleLiquidacion> listaDetalleLiquidacion=new ArrayList<DetalleLiquidacion>();
	
	@OneToMany(mappedBy="liquidacion")
	private List<CobroLiquidacion> listaCobroLiquidacion;

	@ManyToOne(optional=false)
	private ViaCobro viaCobro;
	/**
	 * Obtiene la fecha que se utilizó como vencimiento
	 * @return
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * Obtiene la fecha donde fué generada la liquidación
	 * @return
	 */
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	/**
	 * Setea la fecha donde la liquidación fué generada 
	 * @param fechaGeneracion
	 */
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public Liquidacion getLiquidacionAnterior() {
		return liquidacionAnterior;
	}

	public void setLiquidacionAnterior(Liquidacion liquidacionAnterior) {
		this.liquidacionAnterior = liquidacionAnterior;
	}

	public List<DetalleLiquidacion> getListaDetalleLiquidacion() {
		return listaDetalleLiquidacion;
	}

	public void setListaDetalleLiquidacion(
			List<DetalleLiquidacion> listaDetalleLiquidacion) {
		this.listaDetalleLiquidacion = listaDetalleLiquidacion;
	}

	public List<CobroLiquidacion> getListaCobroLiquidacion() {
		return listaCobroLiquidacion;
	}

	public void setListaCobroLiquidacion(
			List<CobroLiquidacion> listaCobroLiquidacion) {
		this.listaCobroLiquidacion = listaCobroLiquidacion;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ViaCobro getViaCobro() {
		return viaCobro;
	}

	public void setViaCobro(ViaCobro viaCobro) {
		this.viaCobro = viaCobro;
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
		Liquidacion other = (Liquidacion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addDetalle(DetalleLiquidacion detalle) {
		this.getListaDetalleLiquidacion().add(detalle);
	}
}
