package ar.gov.cjpmv.prestamos.gui.configuracion.busquedas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JFormattedTextField;

public class BtnEnterKeyListener extends KeyAdapter{
	
	private AbstractBusquedaController busquedaController;
	
	public BtnEnterKeyListener(AbstractBusquedaController pBusquedaController){
		this.busquedaController = pBusquedaController;
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER){
			if (e.getComponent() instanceof JFormattedTextField){
				JFormattedTextField locFormattedTextField = (JFormattedTextField)e.getComponent();
				try {
					locFormattedTextField.commitEdit();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			this.busquedaController.actualizarModelo();
			this.busquedaController.refrescarBusqueda();
		}
	}
}
