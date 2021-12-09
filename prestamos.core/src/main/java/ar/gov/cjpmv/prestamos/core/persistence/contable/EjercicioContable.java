package ar.gov.cjpmv.prestamos.core.persistence.contable;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class EjercicioContable implements Serializable{

	private static final long serialVersionUID = 7740150160832801636L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable=false, unique=true)
	@Basic(optional=false)
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	private Date fechaFin;
	
	@OneToOne(mappedBy="periodo",cascade=CascadeType.ALL)
	private PlanCuenta planCuenta;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private Integer anio;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private boolean activo;

	public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}

	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaFin() {
		return fechaFin;  
	}
	
	public String toString() {
		if (fechaInicio != null) {
			DateFormat formateador = new SimpleDateFormat("MMMMM 'de' yyyy");
			return formateador.format(this.fechaInicio).toUpperCase() +
					" - "+
				   formateador.format(getFechaFin()).toUpperCase();
		}
		return "";
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	/**
	 * Setea los valores por defecto, necesita que esté seteada la fecha de inicio 
	 */
	public void setearDefecto() {
		if ((this.getFechaInicio() == null) && (anio == null)) {
			throw new IllegalArgumentException("No se pueden setear los valores por defecto sin fecha de inicio o un anio");
		}
		Calendar calendario = Calendar.getInstance();
		if (fechaInicio != null) {
			calendario.setTime(this.fechaInicio);
		}
		else {
			calendario.set(Calendar.YEAR, anio);
			calendario.set(Calendar.DAY_OF_YEAR, calendario.getActualMinimum(Calendar.DAY_OF_YEAR));
			fechaInicio = calendario.getTime();
		}
		calendario.set(Calendar.DAY_OF_YEAR, calendario.getActualMaximum(Calendar.DAY_OF_YEAR));
		
		if (fechaFin == null) {
			setFechaFin(calendario.getTime());
		}

		if (anio == null) {
			anio = calendario.get(Calendar.YEAR);
		}
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
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
		EjercicioContable other = (EjercicioContable) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
