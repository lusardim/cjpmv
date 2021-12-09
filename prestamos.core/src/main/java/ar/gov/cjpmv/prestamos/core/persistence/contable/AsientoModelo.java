package ar.gov.cjpmv.prestamos.core.persistence.contable;

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

@Entity
public class AsientoModelo implements Serializable{

	private static final long serialVersionUID = 3364028265546751699L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToMany
	private List<Cuenta> listaCuenta= new ArrayList<Cuenta>();
	
	@Column(nullable=false)
	@Basic(optional=false)
	private String descripcion;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public List<Cuenta> getListaCuenta() {
		return listaCuenta;
	}

	public void setListaCuenta(List<Cuenta> listaCuenta) {
		this.listaCuenta = listaCuenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Movimiento> getListaMovimiento(){
		List<Movimiento> listaMovimiento= new ArrayList<Movimiento>();
		for(Cuenta cadaCuenta: listaCuenta ){
			Movimiento locMovimiento= new Movimiento();
			locMovimiento.setCuenta(cadaCuenta);
			BigDecimal monto= new BigDecimal(0);
			locMovimiento.setMontoDebe(monto);
			locMovimiento.setMontoHaber(monto);
			listaMovimiento.add(locMovimiento);
		}
		return listaMovimiento;
	}
	
}
