package ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Domicilio;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPropietaria;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.PnlDatosGarantes;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.PnlGarantiaHipotecaria;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.PnlDomicilioController;
import ar.gov.cjpmv.prestamos.gui.utiles.UtilesValidacion;

public class PnlDatosGarantesController {
	private PnlDatosGarantes vista;
	private PnlGarantiasPersonalesController pnlGarantiasPersonales;
	private PnlDomicilioController pnlDomicilioController;

	private Credito credito;
	
	private enum TipoPanel{
		GARANTIA_PERSONAL,GARANTIA_HIPOTECARIA 
	}
	
	public PnlDatosGarantesController(Credito pCredito) {
		this(new PnlDatosGarantes(),pCredito);
	}
	
	public PnlDatosGarantesController(PnlDatosGarantes vista, Credito pCredito){
		this.vista = vista;
		this.credito = pCredito;
		pnlGarantiasPersonales = new PnlGarantiasPersonalesController(credito);
		pnlDomicilioController = new PnlDomicilioController();
		this.inicializarVista();
		this.inicializarEventos();
	}
	
	
	private void inicializarEventos() {
		this.vista.getRbtnGarantiaHipotecaria().addActionListener(new SelectPanelActionListener(this, TipoPanel.GARANTIA_HIPOTECARIA));
		this.vista.getRbtnGarantiaPersonal().addActionListener(new SelectPanelActionListener(this, TipoPanel.GARANTIA_PERSONAL));
	}

	private void inicializarVista() {
		PnlGarantiaHipotecaria locPnlDomicilio = new PnlGarantiaHipotecaria(this.pnlDomicilioController.getVista());
		
		this.vista.getCardlayout().add(this.pnlGarantiasPersonales.getVista(),TipoPanel.GARANTIA_PERSONAL.toString());
		this.vista.getCardlayout().add(locPnlDomicilio, TipoPanel.GARANTIA_HIPOTECARIA.toString());
		this.vista.getRbtnGarantiaPersonal().setSelected(true);
	}

	public PnlDatosGarantes getVista() {
		return vista;
	}
	
	public List<Garantia> getListaGarantias() throws LogicaException{
		List<Garantia> locListaGarantias = new ArrayList<Garantia>();
		if (this.getVista().getRbtnGarantiaHipotecaria().isSelected()){
			GarantiaPropietaria locGarantiaPropietaria = this.getGarantiaHipotecaria();
			if (locGarantiaPropietaria!=null){
				locListaGarantias.add(locGarantiaPropietaria);
			}
		}
		else{
			locListaGarantias.addAll(this.pnlGarantiasPersonales.getModelo().getListaGarantias());
		}
		return locListaGarantias;
	}
	
	private GarantiaPropietaria getGarantiaHipotecaria() throws LogicaException {
		this.pnlDomicilioController.actualizarModelo();
		Domicilio locDomicilio = this.pnlDomicilioController.getDomicilio();
		if (UtilesValidacion.isValido(locDomicilio)){
			GarantiaPropietaria locGarantiaPropietaria = new GarantiaPropietaria();
			locGarantiaPropietaria.setCredito(this.credito);
			locGarantiaPropietaria.setPorcentaje(new BigDecimal(100));
			locGarantiaPropietaria.setPropiedad(locDomicilio);
			return locGarantiaPropietaria;
		}
		else{
			int opcion = JOptionPane.showConfirmDialog(this.vista, 
										"Faltan datos requeridos en el domicilio. " +
										"Si continúa el crédito no registrará garantías. " +
										"¿Desea continuar de todas maneras?",
										"Confirmación",JOptionPane.YES_OPTION);
			if (opcion == JOptionPane.NO_OPTION){
				throw new LogicaException(39, null);
			}
		}
		return null;
	}
	/**
	 * Selecciona el panel según lo pasado por parámetro
	 * @author pulpol
	 *
	 */
	class SelectPanelActionListener implements ActionListener{
		
		private TipoPanel panel;
		private PnlDatosGarantesController controller;
		
		public SelectPanelActionListener(PnlDatosGarantesController pControlador, TipoPanel pPanel) {
			this.controller = pControlador;
			this.panel = pPanel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout locLayout = (CardLayout)controller.getVista().getCardlayout().getLayout();
			locLayout.show(controller.getVista().getCardlayout(), panel.toString());
		}
	}

	public void actualizarModelo() throws LogicaException {
		List<Garantia> locListaGarantias = this.getListaGarantias();
		this.credito.getListaGarantias().clear();
		this.credito.setListaGarantias(locListaGarantias);
	}

	public void actualizarVista() {
		this.pnlGarantiasPersonales.actualizarSeleccionTabla();
	}

	public void remove(GarantiaPersonal garantiaPersonal) {
		this.pnlGarantiasPersonales.removeGarantia(garantiaPersonal);
	}

}
