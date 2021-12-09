package ar.gov.cjpmv.prestamos.gui.creditos.exportacion;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.liquidacion.EventoLiquidacion;

public class EventoActualizacionBarraProgreso implements PropertyChangeListener{
	
	private PnlExportacionMuniController controller;
	private JProgressBar progress;
	public EventoActualizacionBarraProgreso(PnlExportacionMuniController pController) {
		controller = pController;
		progress = controller.getVista().getPnlSeleccionarCuotas().getPbGenerado();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String nombre = evt.getPropertyName();
		
		
		switch (EventoLiquidacion.valueOf(nombre)){
			case CANTIDAD:
				int cantidad = (Integer)evt.getNewValue();
				int otroProgreso = cantidad/4;
				controller.setProgreso(otroProgreso);
				progress.setMaximum(cantidad+otroProgreso);
				progress.setValue(otroProgreso/2);
				controller.getVista().getPnlSeleccionarCuotas().getLblPorcentaje().setText(getTextoProgreso());
				break;
			case PROCESADOS:
				progress.setValue((Integer)evt.getNewValue());
				controller.getVista().getPnlSeleccionarCuotas().getLblPorcentaje().setText(getTextoProgreso());
				break;
				
			case TERMINADO:
				progress.setValue(progress.getMaximum());
				controller.getVista().getPnlSeleccionarCuotas().getBtnGenerarLiquidacion().setEnabled(true);
				controller.getVista().getBtnAceptarGuardar().setEnabled(true);
				controller.getVista().getPnlSeleccionarCuotas().getLblPorcentaje().setText(getTextoProgreso());
				break;
				
			case ERROR:
				controller.getVista().getBtnAceptarGuardar().setEnabled(false);
				controller.getVista().getPnlSeleccionarCuotas().getBtnGenerarLiquidacion().setEnabled(true);
				controller.getVista().getPnlSeleccionarCuotas().getLblPorcentaje().setText(getTextoProgreso());
				JOptionPane.showMessageDialog(controller.getVista(),
						((Exception)evt.getNewValue()).getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE);
				if (((LogicaException)evt.getNewValue()).getCodigoMensaje()==60){
					TblDetalleLiquidacionModel modelo = (TblDetalleLiquidacionModel)controller.getVista()
																.getPnlSeleccionarCuotas()
																.getTblResultadoBusqueda()
																.getModel();
					modelo.limpiar();
				}
				break;
		}
	}

	private String getTextoProgreso() {
		float maximo = (float)progress.getMaximum();
		float actual = (float)progress.getValue();
		int porcentaje = (int)(actual/maximo*100);
		return porcentaje+"%";
	}
}		