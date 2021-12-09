package ar.gov.cjpmv.prestamos.core.persistence.prestamo;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="detallecobroliquidacion")
public class CobroDetalleLiquidacion extends Cobro {

	/**
	 * 
	 */
	private static final long serialVersionUID = -739581440851990759L;
	

	@ManyToOne(optional=false)
	private CobroLiquidacion cobroLiquidacion;
	
	@OneToMany
	@Basic(optional=false)
	private List<DetalleLiquidacion> listaDetallesLiquidacion;

	public CobroLiquidacion getCobroLiquidacion() {
		return cobroLiquidacion;
	}

	public void setCobroLiquidacion(CobroLiquidacion cobroLiquidacion) {
		this.cobroLiquidacion = cobroLiquidacion;
	}

	public List<DetalleLiquidacion> getListaDetallesLiquidacion() {
		return listaDetallesLiquidacion;
	}

	public void setListaDetallesLiquidacion(
			List<DetalleLiquidacion> listaDetallesLiquidacion) {
		this.listaDetallesLiquidacion = listaDetallesLiquidacion;
	}

	public void addDetalleLiquidacion(DetalleLiquidacion detalleLiquidacion) {
		if (this.listaDetallesLiquidacion == null) {
			this.listaDetallesLiquidacion = new ArrayList<DetalleLiquidacion>();
		}
		listaDetallesLiquidacion.add(detalleLiquidacion);
		
	}

	public void add(DetalleLiquidacion cadaDetalle) {
		if (this.getListaDetallesLiquidacion() == null) {
			this.listaDetallesLiquidacion = new ArrayList<DetalleLiquidacion>();
		}
		if (!this.listaDetallesLiquidacion.contains(cadaDetalle)) {
			this.listaDetallesLiquidacion.add(cadaDetalle);
		}
	}

}
