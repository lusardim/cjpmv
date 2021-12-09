package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DetalleCredito implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4313825980230817499L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nombre;

	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal valor=new BigDecimal("0").setScale(2);
	private Boolean emiteCheque=false;
	
	@ManyToOne
	private Credito credito;
	
	@ManyToOne
	private Concepto fuente;
	
	@ManyToOne
	private Cheque cheque;
	
	@ManyToOne
	private Cuota cuota;

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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Boolean getEmiteCheque() {
		return emiteCheque;
	}

	public void setEmiteCheque(Boolean emiteCheque) {
		this.emiteCheque = emiteCheque;
	}

	public Credito getCredito() {
		return credito;
	}

	/**
	 * Asocia el Detalle de crédito con un crédito en particular, 
	 * En caso que esté asociado a una cuota lo desasocia
	 * @param credito
	 */
	public void setCredito(Credito credito) {
		if (credito!=null){
			this.setCuota(null);
		}
		this.credito = credito;
	}

	public Concepto getFuente() {
		return fuente;
	}

	public void setFuente(Concepto fuente) {
		this.fuente = fuente;
	}

	public Cuota getCuota() {
		return cuota;
	}

	
	/**
	 * Asocia el detalle con una cuota en particular, en caso que suceda esto, el detalle deja de estar asociado
	 * con el crédito. por lo tanto lo nulifica.
	 * @param cuota
	 */
	public void setCuota(Cuota cuota) {
		if (cuota!=null){
			this.setCredito(null);
		}
		this.cuota = cuota;
	}
	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}
	public Cheque getCheque() {
		return cheque;
	}
	
	
}
