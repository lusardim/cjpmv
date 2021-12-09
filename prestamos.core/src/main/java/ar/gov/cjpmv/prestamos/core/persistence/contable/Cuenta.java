package ar.gov.cjpmv.prestamos.core.persistence.contable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import ar.gov.cjpmv.prestamos.core.persistence.contable.enums.TipoCuentaContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.enums.TipoSaldo;

@Entity
public class Cuenta implements Serializable, Cloneable {

	private static final long serialVersionUID = 3899740832569845606L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private String codigo;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private String nombre;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private boolean recibeAsiento;
	
	private String abreviatura;
	@Column(columnDefinition="decimal(12,2)")
	private BigDecimal saldoInicial;
	
	//Relaciones
	@Basic(optional=false)
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private TipoCuentaContable tipoCuenta;
	
	@Basic(optional=false)
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private TipoSaldo tipoSaldo;
	
	@ManyToOne
	private PlanCuenta planCuenta;
	
	@ManyToMany(mappedBy="listaCuenta")
	private List<AsientoModelo> asientoModelo = new ArrayList<AsientoModelo>();
		
	@ManyToOne
	private Cuenta padre;

	@OneToMany (mappedBy="padre",cascade=CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Cuenta> cuentasHijas = new ArrayList<Cuenta>();
	
	@ManyToOne
	private Cuenta sumarizaEn;
		 	
	@OneToMany (mappedBy="sumarizaEn")
	private List<Cuenta> sumarizadas = new ArrayList<Cuenta>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isRecibeAsiento() {
		return recibeAsiento;
	}

	public void setRecibeAsiento(boolean recibeAsiento) {
		this.recibeAsiento = recibeAsiento;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public TipoCuentaContable getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuentaContable tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public TipoSaldo getTipoSaldo() {
		return tipoSaldo;
	}

	public void setTipoSaldo(TipoSaldo tipoSaldo) {
		this.tipoSaldo = tipoSaldo;
	}

	public PlanCuenta getPlanCuenta() {
		if (planCuenta == null && this.getPadre() != null) {
			return getPadre().getPlanCuenta();
		}
		return planCuenta;
	}

	public void setPlanCuenta(PlanCuenta nuevoPlanCuenta) {
		if (this.planCuenta != null) {
			if (nuevoPlanCuenta == null) {
				this.planCuenta.remove(this);
			}
			else if (!this.planCuenta.equals(nuevoPlanCuenta)) {
				this.planCuenta.remove(this);
			}
		}
		this.planCuenta = nuevoPlanCuenta;
		if (this.padre != null && this.planCuenta.equals(padre.getPlanCuenta())) {
			this.planCuenta = null;
		}
		nuevoPlanCuenta.add(this);
	}

	public void setAsientoModelo(List<AsientoModelo> asientoModelo) {
		this.asientoModelo = asientoModelo;
	}
	
	public List<AsientoModelo> getAsientoModelo() {
		return asientoModelo;
	}

	public Cuenta getPadre() {
		return padre;
	}

	public void setPadre(Cuenta padre) {
		if (padre != null) {
			this.planCuenta = null;
		}
		this.padre = padre;
	}

	public List<Cuenta> getCuentasHijas() {
		return cuentasHijas;
	}

	public void setCuentasHijas(List<Cuenta> listaHijo) {
		this.cuentasHijas = listaHijo;
	}

	public Cuenta getSumarizaEn() {
		return sumarizaEn;
	}

	public void setSumarizaEn(Cuenta sumarizaEn) {
		this.sumarizaEn = sumarizaEn;
	}

	public List<Cuenta> getSumarizadas() {
		return sumarizadas;
	}

	public void setSumarizadas(List<Cuenta> sumarizadas) {
		this.sumarizadas = sumarizadas;
	}

	public String getCodigoCompleto() {
		if (padre != null) {
			String codigoCompleto = this.padre.getCodigoCompleto(); 
			if (codigo != null) {
				codigoCompleto += codigo;
			}
			return codigoCompleto;
		}
		return (codigo != null)? codigo : "" ;
	}
	
	@Override
	public Cuenta clone() throws CloneNotSupportedException {
		Cuenta nuevaCuenta = (Cuenta)super.clone();
		nuevaCuenta.setId(null);
		List<Cuenta> cuentasHijas = new ArrayList<Cuenta>(this.cuentasHijas.size());
		for (Cuenta cadaHija : nuevaCuenta.getCuentasHijas()) {
			Cuenta nuevaHija = cadaHija.clone();
			nuevaHija.setPadre(nuevaCuenta);
			cuentasHijas.add(nuevaHija);
		}
		nuevaCuenta.setCuentasHijas(cuentasHijas);
		nuevaCuenta.setAsientoModelo(null);
		nuevaCuenta.setSumarizadas(null);
		nuevaCuenta.setSumarizaEn(null);
		return nuevaCuenta;
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
		Cuenta other = (Cuenta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		if (this.nombre != null) {
			return nombre;
		}
		return "";
	}
	/**
	 * Obtiene todas las cuentas hijas de esta incluso si no dependen directamente
	 * @param cuentasHijas
	 * @return
	 */
	public  List<Cuenta> getCuentasHijasRecursivas() {
		return this.getCuentasHijasRecursivas(this.getCuentasHijas());
	}
	
	private List<Cuenta> getCuentasHijasRecursivas(
			List<Cuenta> cuentasHijas) {
	List<Cuenta> listaCuentas = new ArrayList<Cuenta>();
	for (Cuenta cadaCuenta : cuentasHijas) {
		listaCuentas.add(cadaCuenta);
		listaCuentas.addAll(
				getCuentasHijasRecursivas(
						cadaCuenta.getCuentasHijas()));
	}
	return listaCuentas;
}
}
