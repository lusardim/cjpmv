package ar.gov.cjpmv.prestamos.core.persistence.prestamo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema.TipoSistema;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCredito;

@Entity
@NamedQuery(name = "getCuentaCorriente", query = "select c from CuentaCorriente c where c.persona=:persona")
public class Credito implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8670763023572771796L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private Integer cantidadCuotas;
	
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal montoTotal;

	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal tasa;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private boolean acumulativo;
	
	@Column(nullable=false,columnDefinition="decimal(12,2)")
	@Basic(optional=false)
	private BigDecimal montoEntrega;
	
	@Column(columnDefinition="decimal(12,2)")
	private BigDecimal sueldoAlDia;
	
	@Column(nullable=false)
	@Basic(optional=false)
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	private Date fechaFin;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private Boolean cobrarAGarante=false;
	
	@Column(nullable=false)
	@Basic(optional=false)
	private Integer numeroCredito;
	
	@Column(nullable=false)
	@Basic(optional=false)
	@Enumerated(EnumType.STRING)
	private EstadoCredito estado=EstadoCredito.PENDIENTE;

	@OneToMany(cascade=CascadeType.ALL,mappedBy="credito")
	private List<DetalleCredito> detalleCredito=new ArrayList<DetalleCredito>();
	
	@ManyToOne(optional=false)
	private TipoCredito tipoCredito;
	
	@OneToMany(mappedBy="credito",cascade=CascadeType.ALL)
	private List<Cuota> listaCuotas;
	
	@ManyToOne(optional=false)
	private ViaCobro viaCobro;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Garantia> listaGarantias = new ArrayList<Garantia>();
	
	@ManyToOne(optional=false)
	private CuentaCorriente cuentaCorriente;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Cheque> listaCheques=new ArrayList<Cheque>();

	@Enumerated(EnumType.STRING)
	private TipoSistema tipoSistema;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidadCuotas() {
		if (cantidadCuotas == null && listaCuotas != null && !listaCuotas.isEmpty()) {
			cantidadCuotas = listaCuotas.size();
		}
		return cantidadCuotas;
	}

	public void setCantidadCuotas(Integer cantidadCuotas) {
		this.cantidadCuotas = cantidadCuotas;
	}

	public BigDecimal getMontoTotal() {
		if (montoTotal == null){
			this.montoTotal = new BigDecimal("0");
		}
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public BigDecimal getMontoEntrega() {
		return montoEntrega;
	}

	public void setMontoEntrega(BigDecimal montoEntrega) {
		this.montoEntrega = montoEntrega;
	}

	public BigDecimal getSueldoAlDia() {
		return sueldoAlDia;
	}

	public void setSueldoAlDia(BigDecimal sueldoAlDia) {
		this.sueldoAlDia = sueldoAlDia;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getCobrarAGarante() {
		return cobrarAGarante;
	}

	public void setCobrarAGarante(Boolean cobrarAGarante) {
		this.cobrarAGarante = cobrarAGarante;
	}

	public Integer getNumeroCredito() {
		return numeroCredito;
	}

	public void setNumeroCredito(Integer numeroCredito) {
		this.numeroCredito = numeroCredito;
	}

	public EstadoCredito getEstado() {
		return estado;
	}

	public void setEstado(EstadoCredito estado) {
		this.estado = estado;
	}

	public List<DetalleCredito> getDetalleCredito() {
		return detalleCredito;
	}

	public void setDetalleCredito(List<DetalleCredito> detalleCredito) {
		this.detalleCredito = detalleCredito;
	}

	public TipoCredito getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(TipoCredito tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public List<Cuota> getListaCuotas() {
		if (this.listaCuotas==null){
			this.listaCuotas = new ArrayList<Cuota>();
		}
		return listaCuotas;
	}

	public void setListaCuotas(List<Cuota> listaCuotas) {
		this.listaCuotas = listaCuotas;
	}

	public ViaCobro getViaCobro() {
		return viaCobro;
	}

	public void setViaCobro(ViaCobro viaCobro) {
		this.viaCobro = viaCobro;
	}

	public List<Garantia> getListaGarantias() {
		return listaGarantias;
	}

	public void setListaGarantias(List<Garantia> listaGarantias) {
		this.listaGarantias = listaGarantias;
	}

	public CuentaCorriente getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public List<Cheque> getListaCheques() {
		return listaCheques;
	}

	public void setListaCheques(List<Cheque> listaCheques) {
		this.listaCheques = listaCheques;
	}

	/**
	 * actualiza el valor del monto a pagar	
	 */
	public void actualizarMontoAPagar(){
		BigDecimal locMontoTotal = this.getMontoTotal();
		for (DetalleCredito cadaDetalle : this.getDetalleCredito()){
			locMontoTotal = locMontoTotal.subtract(cadaDetalle.getValor());
		}
		this.setMontoEntrega(locMontoTotal);
	}
	/**
	 * Obtiene el valor del crédito con todos los intereses aplicados
	 * @return
	 */
	public BigDecimal getTotalConIntereses(){
		BigDecimal total=new BigDecimal(0);
		for (Cuota cadaCuota : this.getListaCuotas()){
			total = total.add(cadaCuota.getTotal());
		}
		return total;
	}

	public int getCuotasAdeudadas() {
		int locCuotasAdeudadas=0;
		for(Cuota cadaCuota:listaCuotas){
			if(cadaCuota.getCancelacion()==null){
				locCuotasAdeudadas=locCuotasAdeudadas+1;
			}
		}
		return locCuotasAdeudadas;
	}
	
	public int getCuotasAdeudadas(Date pFechaHasta){
		int cuotasAdeudadas=0;
		if (pFechaHasta==null){
			cuotasAdeudadas=this.getCuotasAdeudadas();
		}
		else{
			for(Cuota cadaCuota:listaCuotas){
				if(cadaCuota.getCancelacion()==null){
					cuotasAdeudadas=cuotasAdeudadas+1;
				}
				else{
					
					if(cadaCuota.getCancelacion().getCobro()!=null){
						if(cadaCuota.getCancelacion().getCobro().getFecha().after(pFechaHasta)){
							cuotasAdeudadas=cuotasAdeudadas+1;
						}
					}
					else{
						cuotasAdeudadas=cuotasAdeudadas+1;
					}
				}
			}
			
		}
		return cuotasAdeudadas;
	}
	

	public BigDecimal getSaldoAdeudado(){
		BigDecimal locSaldoAdeudado = new BigDecimal(0).setScale(2);
		for(Cuota cadaCuota:listaCuotas){
			switch (cadaCuota.getSituacion()){
				case VENCIDA: {
					locSaldoAdeudado = locSaldoAdeudado.add(cadaCuota.getTotal());
					break;
				}
				case NO_VENCIDA: {
					locSaldoAdeudado = locSaldoAdeudado.add(cadaCuota.getCapital());
					break;
				}
			}	
		}
		return locSaldoAdeudado;
	}
	
	

	public int getCuotasAdeudadasVencidas(){
		int contador=0;
		for(Cuota cadaCuota:listaCuotas){
			switch (cadaCuota.getSituacion()){
				case VENCIDA: {
					contador+=1;
					break;
				}
			}	
		}
		return contador;
	}
	
	
	public BigDecimal getMontoAdeudadoVencido(){
		BigDecimal locMontoAduedado = new BigDecimal(0).setScale(2);
		for(Cuota cadaCuota:listaCuotas){
			switch (cadaCuota.getSituacion()){
				case VENCIDA: {
					locMontoAduedado = locMontoAduedado.add(cadaCuota.getTotal());
					break;
				}
			}	
		}
		return locMontoAduedado;
	}
	
	
	
	@Override
	public String toString() {
		String retorno = "["+this.getNumeroCredito()+"] - ";
		if (this.getCuentaCorriente()!=null){
			retorno += this.getCuentaCorriente().getPersona().getNombreYApellido();
		}
		else{
			retorno+="---";
		}
		retorno += " - crédito "+this.getTipoCredito();
		return retorno;
	}

	public TipoSistema getTipoSistema() {
		return tipoSistema;
	}

	public void setTipoSistema(TipoSistema tipoSistema) {
		this.tipoSistema = tipoSistema;
	}

	public boolean isAcumulativo() {
		return acumulativo;
	}

	public void setAcumulativo(Boolean acumulativo) {
		this.acumulativo = acumulativo;
	}
	

	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	public void cancelar(Date pFecha) {
		int cantidad = 0;
		for (Cuota cadaCuota : this.listaCuotas) {
			if (cadaCuota.getCancelacion() != null) {
				cantidad++;
			}
		}
		if (cantidad == this.cantidadCuotas) {
			this.setFechaFin(pFecha);
			this.setEstado(EstadoCredito.FINALIZADO);
		}
	}
}
