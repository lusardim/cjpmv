package ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.ChequeDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.gui.comunes.impresion.ImpresionChequeCommand;

public class ReemplazarChequeDialogController {

	private ChequeDAO chequeDao = new ChequeDAO();
	
	private PnlChequeController pnlChequeController;
	private ChequeDialog vista;
	
	private Cheque chequeViejo;
	private Cheque chequeNuevo;
	
	public ReemplazarChequeDialogController(JDialog pPadre,Cheque pCheque) {
		chequeViejo = pCheque;
		crearChequeNuevo();
		vista = new ChequeDialog(pPadre, true);
		pnlChequeController = new PnlChequeController(this.chequeNuevo,vista.getPnlCheque());
		
		inicializarVista();
		inicializarEventos();
	}
	
	private void inicializarEventos() {
		vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vista.dispose();
			}
		});
		
		vista.getBtnAceptar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aceptar();
			}
		});
	}

	private void aceptar() {
		try{
			pnlChequeController.actualizarModelo();
			chequeDao.reemplazarCheque(chequeViejo,chequeNuevo);
			this.vista.dispose();
			if (pnlChequeController.getModelo().isImprimir()){
				ImpresionChequeCommand impresionCheque = new ImpresionChequeCommand(chequeNuevo);
				impresionCheque.imprimir();
				chequeDao.modificar(chequeNuevo);
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(vista, 
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void crearChequeNuevo() {
		chequeNuevo = chequeViejo.clone();
		chequeNuevo.setNumero(chequeDao.getProximoNumeroCheque());
	}

	private void inicializarVista() {
		PnlCheque pnlCheque = vista.getPnlCheque();
		pnlCheque.getTxtConcepto().setEditable(false);
		pnlCheque.getTxtNombreApellido().setEditable(false);
		pnlCheque.getBtnBuscarPersona().setVisible(false);
	}

	public void setVisible(boolean visible){
		vista.setVisible(visible);
	}

	public Cheque getChequeViejo() {
		return chequeViejo;
	}

	public Cheque getChequeNuevo() {
		return chequeNuevo;
	}

}
