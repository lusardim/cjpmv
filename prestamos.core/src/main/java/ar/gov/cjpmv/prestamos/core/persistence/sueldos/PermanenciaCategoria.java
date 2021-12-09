package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PermanenciaCategoria implements Serializable {

	private static final long serialVersionUID = -1714241032575479546L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private int minimo;
	@Column(nullable=false)
	private int maximo;
	@Column(columnDefinition="decimal(3,2)")
	private BigDecimal porcentaje;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="permanencia")
	private List<MontoPermanenciaPorCategoria> montosPorPermanencia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMinimo() {
		return minimo;
	}

	public void setMinimo(int minimo) {
		this.minimo = minimo;
	}

	public int getMaximo() {
		return maximo;
	}

	public void setMaximo(int maximo) {
		this.maximo = maximo;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public List<MontoPermanenciaPorCategoria> getMontosPorPermanencia() {
		return montosPorPermanencia;
	}

	public void setMontosPorPermanencia(
			List<MontoPermanenciaPorCategoria> montosPorPermanencia) {
		this.montosPorPermanencia = montosPorPermanencia;
	}
	
	@Override
	public String toString() {
		if (minimo == 0) {
			return "Hasta " + maximo + " años";
		}
		else if (maximo == 0) {
			return "más de " + minimo + " años";
		}
		return "de " + minimo + " a " + maximo + " años";
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
		PermanenciaCategoria other = (PermanenciaCategoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
