package ar.gov.cjpmv.prestamos.gui.creditos.importacion;

import java.math.BigDecimal;

import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionImportacionArchivo;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;

public class ArchivoDetalleLiquidacionModel {

	private DetalleLiquidacion detalleLiquidacion;
	private BigDecimal montoRecibido;
	private SituacionImportacionArchivo situacionImportacion;
	
	
	public ArchivoDetalleLiquidacionModel(
			DetalleLiquidacion detalleLiquidacion, BigDecimal montoRecibido, SituacionImportacionArchivo situacion) {
		super();
		this.detalleLiquidacion = detalleLiquidacion;
		this.montoRecibido = montoRecibido;
		this.situacionImportacion= situacion;
	}


	public DetalleLiquidacion getDetalleLiquidacion() {
		return detalleLiquidacion;
	}


	public void setDetalleLiquidacion(DetalleLiquidacion detalleLiquidacion) {
		this.detalleLiquidacion = detalleLiquidacion;
	}


	public BigDecimal getMontoRecibido() {
		return montoRecibido;
	}


	public void setMontoRecibido(BigDecimal montoRecibido) {
		this.montoRecibido = montoRecibido;
	}


	public void setSituacionImportacion(SituacionImportacionArchivo situacionImportacion) {
		this.situacionImportacion = situacionImportacion;
	}


	public SituacionImportacionArchivo getSituacionImportacion() {
		return situacionImportacion;
	}
	
	
	
}
