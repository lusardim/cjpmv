package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
		uniqueConstraints={@UniqueConstraint(columnNames={"mes","anio"})}
)
public class LiquidacionHaberes implements Serializable {

	private static final long serialVersionUID = -232849416072413016L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private int mes;
	@Column(nullable=false)
	private int anio;
	
	@ManyToOne(optional=true)
	private Plantilla plantilla;
	@OneToMany(mappedBy="liquidacion", cascade=CascadeType.ALL)	
	private List<ReciboSueldo> recibos;
	@Enumerated(EnumType.STRING)
	private TipoLiquidacion tipo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public Plantilla getPlantilla() {
		return plantilla;
	}
	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
	}
	public List<ReciboSueldo> getRecibos() {
		return recibos;
	}
	public void setRecibos(List<ReciboSueldo> recibos) {
		this.recibos = recibos;
	}
	public TipoLiquidacion getTipo() {
		return tipo;
	}
	public void setTipo(TipoLiquidacion tipo) {
		this.tipo = tipo;
	}
	
	public void add(ReciboSueldo recibo) {
		if (recibos == null) {
			recibos = new ArrayList<ReciboSueldo>();
		}
		if (recibo != null) {
			recibos.add(recibo);
			recibo.setLiquidacion(this);
		}
		
	}
	
}
