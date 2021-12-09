package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;

@Entity
public class DetalleLiquidacion implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6477084545588749453L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal monto;
	
	@ManyToOne
	private Liquidacion liquidacion;
	
	@ManyToMany
	private List<Cuota> listaCuotas;
	
	@ManyToOne(optional=false)
	private CuentaCorriente cuentaCorrienteAfectada;

	public BigDecimal getMontoAdeudado(){
		BigDecimal total = new BigDecimal(0).setScale(2);
		if(this.getListaCuotas()!=null){
			for (Cuota cadaCuota : this.getListaCuotas()){
				if (cadaCuota.getCancelacion() == null){
					total = total.add(cadaCuota.getTotal());
				}
			}
		}
		return total;
	}
	
	public BigDecimal getMonto() {
		return monto;
	}
	
	public Persona getPersona(){
		return cuentaCorrienteAfectada.getPersona();
	}
	
	public String getNombreApellidoPersona(){
		return this.getPersona().getNombreYApellido();
	}
	
	public Long getLegajo(){
		if (this.getPersona() instanceof PersonaFisica){
			PersonaFisica locPersonaFisica= (PersonaFisica)this.getPersona();
			return locPersonaFisica.getLegajo();
		}	
		return null;
	}

	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}

	public List<Cuota> getListaCuotas() {
		return listaCuotas;
	}

	public void setListaCuotas(List<Cuota> listaCuotas) {
		this.listaCuotas = listaCuotas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public CuentaCorriente getCuentaCorrienteAfectada() {
		return cuentaCorrienteAfectada;
	}

	public void setCuentaCorrienteAfectada(CuentaCorriente cuentaCorrienteAfectada) {
		this.cuentaCorrienteAfectada = cuentaCorrienteAfectada;
	}
	
	@Override
	public DetalleLiquidacion clone() throws CloneNotSupportedException {
		DetalleLiquidacion detalle = (DetalleLiquidacion)super.clone();
		detalle.setId(null);
		return detalle;
	}
	
	/**
	 * Reemplaza la lista actual de cuotas por esta sola
	 * @param cadaCuota
	 */
	public void setCuota(Cuota cadaCuota) {
		List<Cuota> listaCuotas = new ArrayList<Cuota>();
		listaCuotas.add(cadaCuota);
		this.setListaCuotas(listaCuotas);
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
		DetalleLiquidacion other = (DetalleLiquidacion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addCuota(Cuota cuota) {
		if (this.listaCuotas == null) {
			this.listaCuotas = new ArrayList<Cuota>();
		}
		
		if (!listaCuotas.contains(cuota)) {
			listaCuotas.add(cuota);
		}
	}
}
