package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoCuenta;

@Entity	
public class CuentaBancaria implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1730020303003615300L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Basic(optional=false)
	@Column(nullable=false)
	private String numero;
	
	@Basic(optional=true)
	@Column(nullable=true)
	private String cbu;
	
	@Basic(optional=false)
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	private BigDecimal saldo;
	
	@OneToMany (mappedBy="cuenta")
	private List<Cheque> listaCheque;
	
	@ManyToOne
	private Banco banco;
	
	@Basic(optional=false)
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private TipoCuenta tipo=TipoCuenta.CUENTA_CORRIENTE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public List<Cheque> getListaCheque() {
		return listaCheque;
	}

	public void setListaCheque(List<Cheque> listaCheque) {
		this.listaCheque = listaCheque;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public TipoCuenta getTipo() {
		return tipo;
	}

	public void setTipo(TipoCuenta tipo) {
		this.tipo = tipo;
	}
	
	public String toString(){
		return this.getNumero();
	}

	public void setCbu(String cbu) {
		this.cbu = cbu;
	}

	public String getCbu() {
		return cbu;
	}

}
