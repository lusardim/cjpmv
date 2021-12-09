package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaBusquedaView;

public class PnlConsultaEstadoCuentaController {
	private PnlDchaBusquedaView vista;
	private ConsultaEstadoCuentaController consultaEstadoCuentaController;
	
	public PnlConsultaEstadoCuentaController() {
		this.vista = new PnlDchaBusquedaView();
		this.consultaEstadoCuentaController = new ConsultaEstadoCuentaController();
		this.vista.setPnlBusquedaDcha(consultaEstadoCuentaController.getVista());
		this.vista.getLblTituloPnlDcha().setText("Consulta Estado Cuenta");
		this.vista.getBtnImprimir().setVisible(false);
	}
	
	public PnlDchaBusquedaView getVista() {
		return vista;
	}
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		PnlConsultaEstadoCuentaController locBusquedaPersonas = new PnlConsultaEstadoCuentaController();
		
		JFrame locFrame = new JFrame();
		locFrame.add(locBusquedaPersonas.getVista(),BorderLayout.CENTER);
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		locFrame.pack();
		locFrame.setVisible(true);
	}

}
