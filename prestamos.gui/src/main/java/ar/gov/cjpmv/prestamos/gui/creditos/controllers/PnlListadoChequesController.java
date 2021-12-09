package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JFrame;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlListadoCheques;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.PnlCheque;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.PnlChequeController;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.model.ChequeModel;

public class PnlListadoChequesController {
	
	private List<PnlChequeController> listaCheques;
	private PnlListadoCheques vista;
	
	public PnlListadoChequesController(PnlListadoCheques pVista){
		vista = pVista;
		listaCheques = new ArrayList<PnlChequeController>();
		
		this.inicializarEventos();
	}
	
	private void inicializarEventos() {
		//TODO FIXME
	}

	public void addCheque(Cheque pCheque){
		final PnlChequeController locPnlChequeController = new PnlChequeController(pCheque,new PnlCheque());
		
		if (!this.listaCheques.contains(locPnlChequeController)){
			this.listaCheques.add(locPnlChequeController);
			final Component locStrut = Box.createVerticalStrut(10);
			this.vista.getPnlListaCheques().add(locPnlChequeController.getVista());
			this.vista.getPnlListaCheques().add(locStrut);
			this.vista.validate();
		}
	}
	
	public static void main(String[] args){
		JFrame locFrame = new JFrame();
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		locFrame.add(new PnlListadoChequesController(new PnlListadoCheques()).vista,BorderLayout.CENTER);
		locFrame.pack();
		locFrame.setVisible(true);
	}

	public void limpiarCheques() {
		for (PnlChequeController cadaController: this.listaCheques){
			this.vista.getPnlListaCheques().remove(cadaController.getVista());
		}
		this.listaCheques.clear();
	}

	public void actualizarModelo() throws LogicaException {
		for (PnlChequeController cadaController: this.listaCheques){
			cadaController.actualizarModelo();
		}
	}

	/**
	 * Recupera el listado de cheques
	 * @return
	 */
	public List<ChequeModel> getListaModelosCheques(){
		List<ChequeModel> locListaCheques = new ArrayList<ChequeModel>(this.listaCheques.size());
		for (PnlChequeController cadaController : this.listaCheques){
			locListaCheques.add(cadaController.getModelo());
		}
		return locListaCheques;
	}

	public List<Cheque> getListaCheques() {
		List<Cheque> locListaRetorno = new ArrayList<Cheque>(this.listaCheques.size());
		for (PnlChequeController cadaModelo : this.listaCheques){
			locListaRetorno.add(cadaModelo.getModelo().getCheque());
		}
		return locListaRetorno;
	}
}
