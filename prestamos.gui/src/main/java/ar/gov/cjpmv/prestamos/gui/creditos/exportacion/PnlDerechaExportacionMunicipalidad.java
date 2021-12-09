package ar.gov.cjpmv.prestamos.gui.creditos.exportacion;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaABMView;

public class PnlDerechaExportacionMunicipalidad extends PnlDchaABMView{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2228601177525362776L;
	private PnlCuotasExportacionMunicipalidad pnlExportacionMuni;
	private PnlSeleccionCuotasReliquidacion pnlSeleccionarCuotas;
	
	public PnlDerechaExportacionMunicipalidad() {
		super();
		this.initComponents();
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		this.getLblTituloPnlDcha().setText("Exportación a Municipalidad");
		this.pnlExportacionMuni = new PnlCuotasExportacionMunicipalidad();
		this.pnlSeleccionarCuotas = new PnlSeleccionCuotasReliquidacion();
		this.getTbpABM().add("Liquidaciones Anteriores",this.pnlSeleccionarCuotas);
		this.getTbpABM().add("Liquidación a Municipalidad", this.pnlExportacionMuni);	
	}

	public void setPnlExportacionMuni(PnlCuotasExportacionMunicipalidad pnlExportacionMuni) {
		this.pnlExportacionMuni = pnlExportacionMuni;
	}

	public PnlCuotasExportacionMunicipalidad getPnlExportacionMuni() {
		return pnlExportacionMuni;
	}

	public JCheckBox getChkImprimirPlanilla() {
		return pnlExportacionMuni.getChkImprimirPlanilla();
	}

	public PnlSeleccionCuotasReliquidacion getPnlSeleccionarCuotas() {
		return pnlSeleccionarCuotas;
	}

	public void setPnlSeleccionarCuotas(
			PnlSeleccionCuotasReliquidacion pnlSeleccionarCuotas) {
		this.pnlSeleccionarCuotas = pnlSeleccionarCuotas;
	}

	public void setExportacionMunicipalidadEnabled(boolean pHabilitado) {
		getTbpABM().setEnabledAt(getTbpABM().indexOfComponent(pnlExportacionMuni), pHabilitado);
	}

	public JRadioButton getRbtApellidoNombre() {
		return pnlExportacionMuni.getRbtApellidoNombre();
	}

	public JRadioButton getRbtLegajo() {
		return pnlExportacionMuni.getRbtLegajo();
	}

	
	
}
