package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionCuota;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionPeriodo;
import ar.gov.cjpmv.prestamos.core.utiles.UtilFecha;

@Entity
public class Cuota implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7254695716324306503L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	@Basic(optional=false)
	@Temporal(TemporalType.DATE)
	private Date vencimiento;

	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal interes;
	
	@Column(columnDefinition="decimal(12,2)")
	private BigDecimal otrosConceptos;
	
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal capital;

	@Column(nullable=false)
	@Basic(optional=false)
	private Integer numeroCuota;

	@ManyToOne(optional=false)
	private Credito credito;

	@ManyToOne(optional=true,cascade=CascadeType.ALL)
	private Cancelacion cancelacion;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="cuota")
	private List<DetalleCredito> listaDetallesCreditos=new ArrayList<DetalleCredito>();
	
	@ManyToMany(mappedBy="listaCuotas")
	private List<DetalleLiquidacion> liquidaciones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getOtrosConceptos() {
		if (this.otrosConceptos==null){
			this.otrosConceptos = new BigDecimal("0").setScale(2);
		}
		return otrosConceptos;
	}

	public void setOtrosConceptos(BigDecimal otrosConceptos) {
		this.otrosConceptos = otrosConceptos;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public Integer getNumeroCuota() {
		return numeroCuota;
	}

	public void setNumeroCuota(Integer numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public Credito getCredito() {
		return credito;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public Cancelacion getCancelacion() {
		return cancelacion;
	}

	public void setCancelacion(Cancelacion cancelacion) {
		this.cancelacion = cancelacion;
		if (cancelacion != null) {
			credito.cancelar(cancelacion.getCobro().getFecha());
		}
	}

	
	public void add(DetalleCredito pDetalleCredito){
		if (!this.getListaDetallesCreditos().contains(pDetalleCredito)){
			this.listaDetallesCreditos.add(pDetalleCredito);
			this.calcularOtrosConceptos();
		}
	}
	
	private void calcularOtrosConceptos() {
		BigDecimal acumulador = new BigDecimal("0").setScale(2);
		for (DetalleCredito cadaDetalle : this.getListaDetallesCreditos()){
			acumulador = acumulador.add(cadaDetalle.getValor());
		}
		this.setOtrosConceptos(acumulador);
	}

	public List<DetalleCredito> getListaDetallesCreditos() {
		return listaDetallesCreditos;
	}

	public void setListaDetallesCreditos(List<DetalleCredito> listaDetallesCreditos) {
		this.listaDetallesCreditos = listaDetallesCreditos;
	}
 
	public BigDecimal getTotal(){
		return this.getInteres().add(this.getCapital()).add(this.getOtrosConceptos());
	}

	public BigDecimal getTotalSinInteres(){
		return this.getCapital().add(this.getOtrosConceptos());
	}
	
	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public SituacionCuota getSituacion() {
		if (cancelacion != null) {
			return SituacionCuota.CANCELADA;
		}
		if (isCuotaVencida()) {
			return SituacionCuota.VENCIDA;
		}
		return SituacionCuota.NO_VENCIDA;
	}
 
	/**
	 * Obtiene la situación de la cuota en un día determinado
	 * @param pFecha
	 * @return
	 */
	public SituacionCuota getSituacion(Date pFecha) {
		if (this.cancelacion!=null) {
			Date fechaCobro = cancelacion.getCobro().getFecha();
			if (UtilFecha.comparaDia(fechaCobro, pFecha) || 
				fechaCobro.before(pFecha)) { 
				return SituacionCuota.CANCELADA;
			}
		}
		
		if(isCuotaVencida(pFecha)) {
			return SituacionCuota.VENCIDA;	
		}
		else{
			return SituacionCuota.NO_VENCIDA;
		}
	}

	public boolean isCuotaVencida() {
		Date fechaHoy= Calendar.getInstance().getTime();
		return this.isCuotaVencida(fechaHoy);
	}
	
	/**
	 * Verifica si la cuota está vencida en la fecha dada
	 * @param pFecha
	 * @return
	 */
	public boolean isCuotaVencida(Date pFecha) {
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(pFecha);
		Calendar locFechaVencimiento = Calendar.getInstance();
		locFechaVencimiento.setTime(this.getVencimiento());
		if (UtilFecha.comparaDia(fecha, locFechaVencimiento)) {
			return false;
		}
		else if(this.getVencimiento().before(fecha.getTime())) {
			return true;
		}
		return false;
	}
			
			
	public BigDecimal getCapitalMasInteres(){
		return this.getInteres().add(this.getCapital());
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
		Cuota other = (Cuota) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<DetalleLiquidacion> getLiquidaciones() {
		return liquidaciones;
	}

	public void setLiquidaciones(List<DetalleLiquidacion> liquidaciones) {
		this.liquidaciones = liquidaciones;
	}
	
	
	public SituacionPeriodo getSituacionPeriodo(Date pFechaHasta) {
		if(pFechaHasta!=null){
			if(UtilFecha.comparaDia(this.getVencimiento(), pFechaHasta)){
				return SituacionPeriodo.CAPITAL_CORRIENTE;
			}
			else{
				if(this.getVencimiento().before(pFechaHasta)){
					return SituacionPeriodo.CAPITAL_CORRIENTE;
				}
				else{
					if(this.getVencimiento().after(pFechaHasta)){
						return SituacionPeriodo.CAPITAL_NO_CORRIENTE;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Devuelve el valor de la cuota considerando que, si la cuota está vencida
	 * debe devolver la cuota con intereses, en cambio si la cuota <b>NO</b> está vencida
	 * devuelve la cuota sin interes
	 * @return
	 */
	public BigDecimal getTotalSegunVencimiento(boolean conSeguro, Date pFecha) {
		if (isCuotaVencida(pFecha)) {
			return getTotal();
		}
		else {
			if(conSeguro){
				return getTotalSinInteres();
			}
			else{
				return getCapital();
			}
		}
	}
	
	
}
