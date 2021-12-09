package ar.gov.cjpmv.prestamos.gui.creditos.importacion;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;
import ar.gov.cjpmv.prestamos.gui.creditos.exportacion.PnlCuotasExportacionMunicipalidad;
import ar.gov.cjpmv.prestamos.gui.creditos.exportacion.PnlSeleccionCuotasReliquidacion;

public class PnlDerechaImportacionMunicipalidad extends PnlDchaABMView{
	private PnlImportacionMunicipalidad pnlImportacionMunicipalidad;

	public void PnlDerechaImportacionMunicipalidad() {
		this.initComponents();
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Importación de Municipalidad");
		this.pnlImportacionMunicipalidad = new PnlImportacionMunicipalidad();
		this.getTbpABM().add("Importación de Archivo",this.pnlImportacionMunicipalidad);	
	}
	
	public void setPnlImportacionMunicipalidad(PnlImportacionMunicipalidad pnlImportacionMuni) {
		this.pnlImportacionMunicipalidad= pnlImportacionMuni;
	}
	
	
	public PnlImportacionMunicipalidad getPnlImportacionMunicipalidad() {
		return pnlImportacionMunicipalidad;
	}

	
	

}
