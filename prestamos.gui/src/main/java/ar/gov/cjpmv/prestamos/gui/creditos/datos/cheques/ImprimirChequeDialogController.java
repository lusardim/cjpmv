package ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.ChequeDAO;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.gui.comunes.impresion.ImpresionChequeCommand;

public class ImprimirChequeDialogController {

	private ChequeDAO chequeDao = new ChequeDAO();
	
	private PnlChequeController pnlChequeController;
	private ChequeDialog vista;
	
	private Cheque cheque;
	
	public ImprimirChequeDialogController(JDialog pPadre,Cheque pCheque) {
		this.cheque = pCheque;
		vista = new ChequeDialog(pPadre, true);
		pnlChequeController = new PnlChequeController(cheque,vista.getPnlCheque());
		pnlChequeController.getModelo().setImprimir(true);
		
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
		EstadoCheque estadoAnterior = cheque.getEstadoCheque();
		try{
			pnlChequeController.actualizarModelo();
			if (pnlChequeController.getModelo().isImprimir()){
				this.cheque.setEstadoCheque(EstadoCheque.IMPRESO);
				chequeDao.validarModificacion(cheque);
				cheque.setEstadoCheque(estadoAnterior);
				ImpresionChequeCommand impresionCheque = new ImpresionChequeCommand(cheque);
				impresionCheque.imprimir();
			}
			chequeDao.modificar(this.cheque);
			this.vista.dispose();
		}
		catch(Exception e){
			cheque.setEstadoCheque(estadoAnterior);
			JOptionPane.showMessageDialog(vista, 
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
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

}
