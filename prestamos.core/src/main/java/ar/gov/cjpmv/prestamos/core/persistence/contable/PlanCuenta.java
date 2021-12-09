package ar.gov.cjpmv.prestamos.core.persistence.contable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

@Entity
public class PlanCuenta  implements Serializable, Cloneable  {
	
	private static final long serialVersionUID = -5841467011773925053L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private boolean cerrado;
	 
	@OneToOne
	private EjercicioContable periodo;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="planCuenta")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Cuenta> listaCuenta = new ArrayList<Cuenta>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isCerrado() {
		return cerrado;
	}

	public void setCerrado(boolean cerrado) {
		this.cerrado = cerrado;
	}

	public List<Cuenta> getListaCuenta() {
		return listaCuenta;
	}

	public void setListaCuenta(List<Cuenta> listaCuenta) {
		this.listaCuenta = listaCuenta;
	}

	public void remove(Cuenta cuenta) {
		if (cuenta.getPadre() == null) {
			this.listaCuenta.remove(cuenta);			
		}
	}

	public void add(Cuenta cuenta) {
		if (cuenta.getPadre() == null){
			this.listaCuenta.add(cuenta);	
		}
	}

	public EjercicioContable getPeriodo() {
		return periodo;
	}

	public void setPeriodo(EjercicioContable periodo) {
		if (periodo != null && !periodo.equals(this.periodo)) {
			periodo.setPlanCuenta(this);
		}
		this.periodo = periodo;
	}

	@Override
	public String toString() {
		return "Período " + periodo.getAnio();
	}
	
	@Override
	public PlanCuenta clone() throws CloneNotSupportedException {
		PlanCuenta planCuenta = (PlanCuenta)super.clone();
		planCuenta.setId(null);
		List<Cuenta> cuentas = new ArrayList<Cuenta>(listaCuenta.size());
		for (Cuenta cuenta : listaCuenta) {
			Cuenta nuevaCuenta = cuenta.clone();
			cuentas.add(nuevaCuenta);
		}
		planCuenta.setListaCuenta(cuentas);
		return planCuenta;
	}
	
	public List<Cuenta> getCuentasHijasComoLista() {
		List<Cuenta> listaCuentas = new ArrayList<Cuenta>(this.getListaCuenta());
		for (Cuenta cadaCuenta : this.getListaCuenta()) {
			listaCuentas.addAll(cadaCuenta.getCuentasHijasRecursivas());;
		}
		return listaCuentas;
	}

	
}
