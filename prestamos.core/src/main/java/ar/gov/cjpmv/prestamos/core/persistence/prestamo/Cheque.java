package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;
import ar.gov.cjpmv.prestamos.core.utiles.UtilFecha;

@Entity
public class Cheque implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1145676532325657981L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date fechaEmision;
	
	@Temporal(TemporalType.DATE)
	private Date fechaPago;
	
	@Basic(optional=false)
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	private BigDecimal monto;

	@Basic(optional=true)
	@Column(nullable=true)
	private Integer numero;

	private String concepto;

	@Enumerated(EnumType.STRING)
	private EstadoCheque estadoCheque=EstadoCheque.PENDIENTE_IMPRESION;

	@ManyToOne
	private Cheque canceladoPor;
	
	@ManyToOne
	private CuentaBancaria cuenta;
	
	@ManyToOne
	private Persona emitidoA;

	private String nombrePersona;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public EstadoCheque getEstadoCheque() {
		return estadoCheque;
	}

	public void setEstadoCheque(EstadoCheque estadoCheque) {
		if (estadoCheque == null){
			throw new IllegalArgumentException("El cheque no puede tener un estado nulo");
		}
		
		if (this.estadoCheque != null) {
			switch(this.estadoCheque){
				case IMPRESO: 
					//De impreso solo puede cancelarse
					if (!estadoCheque.equals(EstadoCheque.PENDIENTE_IMPRESION)){
						this.estadoCheque = estadoCheque;
					}
					break;
					// De pendiente de impresión puede pasar a cualquier estado
				case PENDIENTE_IMPRESION: 
					this.estadoCheque = estadoCheque; 
					break;
					//Cuando está cancelado, queda cancelado
			}
		}
	}

	public Cheque getCanceladoPor() {
		return canceladoPor;
	}

	public void setCanceladoPor(Cheque canceladoPor) {
		this.canceladoPor = canceladoPor;
		if (canceladoPor!= null){
			setEstadoCheque(EstadoCheque.CANCELADO);
		}
	}

	public CuentaBancaria getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaBancaria cuenta) {
		this.cuenta = cuenta;
	}

	public Persona getEmitidoA() {
		return emitidoA;
	}

	public void setEmitidoA(Persona emitidoA) {
		this.emitidoA = emitidoA;
	}

	public String getNombrePersona() {
		if (this.emitidoA!=null){
			return emitidoA.getNombreYApellido();
		}
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	@Override
	public Cheque clone(){
		try{
			Cheque nuevoCheque;
			nuevoCheque = (Cheque)super.clone();
			nuevoCheque.setId(null);
			nuevoCheque.setNumero(null);
			nuevoCheque.estadoCheque = EstadoCheque.PENDIENTE_IMPRESION;
			return nuevoCheque;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("El cheque tiró un CloneNotSupportedException cosa que no debería pasar. ");
		}
	}
	
	@Override
	public String toString() {
		return +this.numero+"-["+this.getCuenta()+"]";
	}
		
	public boolean isDiferido() {
		if(UtilFecha.comparaDia(fechaEmision, fechaPago)){
			return false;
		}
		else if(fechaEmision.before(fechaPago)){
			return true;
		}
		return false;
	}
}
