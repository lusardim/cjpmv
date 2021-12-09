package ar.gov.cjpmv.prestamos.gui.creditos.garantias;

import java.math.BigDecimal;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPropietaria;

public class ResultadoGarantia {
	private Credito credito;
	private BigDecimal montoAdeudado;
	private Integer cantidadCuotasVencidas;
	private String garantes;
	private String tipoGarantia;
	
	
	
	
	public Credito getCredito() {
		return credito;
	}
	public void setCredito(Credito credito) {
		this.credito = credito;
	}
	
	
	public BigDecimal getMontoAdeudado() {
		this.montoAdeudado= this.credito.getMontoAdeudadoVencido();
		return montoAdeudado;
	}
	
	
	/*public void setMontoAdeudado() {
		this.montoAdeudado = this.credito.getMontoAdeudadoVencido();
	}*/
	
	public Integer getCantidadCuotasVencidas() {
		this.cantidadCuotasVencidas = this.credito.getCuotasAdeudadasVencidas();
		return cantidadCuotasVencidas;
	}
	
	/*
	public void setCantidadCuotasVencidas() {
		this.cantidadCuotasVencidas = this.credito.getCuotasAdeudadasVencidas();
	}
	*/
	
	public String getGarantes() {
		String cadenaGarantias= "";
		for(Garantia locGarantia: this.credito.getListaGarantias()){
			if(locGarantia instanceof GarantiaPersonal){
				GarantiaPersonal loc= (GarantiaPersonal) locGarantia;
				cadenaGarantias+=loc.getGarante().getNombreYApellido()+". ";
				setTipoGarantia("PERSONAL");
			}
			else{
				if(locGarantia instanceof GarantiaPropietaria){
					GarantiaPropietaria loc= (GarantiaPropietaria)locGarantia;
					cadenaGarantias+= loc.getPropiedad().getCalle()+" Nº "+loc.getPropiedad().getNumero()+". ";
					setTipoGarantia("PROPIETARIA");
				}
			}
		}
		this.garantes= cadenaGarantias;
		return this.garantes;
	}
	public void setTipoGarantia(String tipoGarantia) {
		this.tipoGarantia = tipoGarantia;
	}
	public String getTipoGarantia() {
		return tipoGarantia;
	}
	
	/*
	public void setGarantes() {
		String garante= "";
		for(Garantia cadaGarantia: this.credito.getListaGarantias()){
			if(cadaGarantia instanceof GarantiaPersonal){
				GarantiaPersonal locGarantia= (GarantiaPersonal)cadaGarantia;
				garante= locGarantia.getGarante().getNombreYApellido()+". ";
			}
			else{
				if(cadaGarantia instanceof GarantiaPropietaria){
					GarantiaPropietaria locGarantia= (GarantiaPropietaria)cadaGarantia;
					garante= locGarantia.getPropiedad().getCalle()+" Nº "+locGarantia.getPropiedad().getNumero()+". ";
				}
			}
			this.garantes+= garante;
		}
		
	}
	
	*/
	

}
