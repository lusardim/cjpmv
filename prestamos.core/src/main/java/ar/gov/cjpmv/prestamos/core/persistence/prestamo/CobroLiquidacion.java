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
public class CobroLiquidacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6130080889119860196L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	@Basic(optional=false)
	@Temporal(TemporalType.DATE)
	private Date fechaIngreso;

	@ManyToOne(optional=false)
	private Liquidacion liquidacion;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="cobroLiquidacion")
	private List<CobroDetalleLiquidacion> listaCobroPorLiquidacion;
	
	public Date getFechaLiquidacion() {
		return this.liquidacion.getFechaLiquidacion();
	}
	
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public Liquidacion getLiquidacion() {
		return liquidacion;
	}
	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}
	public List<CobroDetalleLiquidacion> getListaCobroPorLiquidacion() {
		return listaCobroPorLiquidacion;
	}
	public void setListaCobroPorLiquidacion(
			List<CobroDetalleLiquidacion> listaCobrePorLiquidacion) {
		this.listaCobroPorLiquidacion = listaCobrePorLiquidacion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void addDetalleCobro(CobroDetalleLiquidacion cobroDetalle) {
		if (this.listaCobroPorLiquidacion == null) {
			this.listaCobroPorLiquidacion = new ArrayList<CobroDetalleLiquidacion>();
		}
		cobroDetalle.setCobroLiquidacion(this);
		this.listaCobroPorLiquidacion.add(cobroDetalle);
	}

}
