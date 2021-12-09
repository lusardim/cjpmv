package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlAMCreditoFinalizacionView;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CreditoFinalizacionModel;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CreditoModel;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CreditoModel.EventoCreditoModel;
import ar.gov.cjpmv.prestamos.gui.creditos.model.TblCreditosSeleccionablesModel;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class AMCreditoControllerPorFinalizacion extends AMCreditoController{
	
	private TblCreditosSeleccionablesModel modeloTabla;
	private CreditoDAOImpl creditoDAO = new CreditoDAOImpl();
	
	public AMCreditoControllerPorFinalizacion(AdminCredito pPadre) {
		super(pPadre,new PnlAMCreditoFinalizacionView());
	}

	@Override
	protected CreditoModel getCreditoModel(Credito pCredito) {
		return new CreditoFinalizacionModel(pCredito);
	}
	
	@Override
	protected void inicializarEventos() {
		super.inicializarEventos();
		getModelo().addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					
					if (evt.getPropertyName().equals(EventoCreditoModel.CAMBIO_CUENTA_CORRIENTE.toString())){
						actualizarTablaCreditosFinalizacion();
						actualizarValorTotal();
					}
					else if (evt.getPropertyName().equals(EventoCreditoModel.LIMPIAR_DATOS.toString())){
						modeloTabla.limpiar();
					}
					actualizarVista();
				}
				catch (LogicaException e) {
					JOptionPane.showMessageDialog(
							getVista(),
							"Error",
							e.getMessage(),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		modeloTabla.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE){
					try {
						getModelo().setListaSeleccionados(modeloTabla.getListaSeleccionados());
						actualizarValorTotal();
						actualizarVista();
					}
					catch (LogicaException ex) {
						JOptionPane.showMessageDialog(
								getVista(),
								"Error",
								ex.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	@Override
	public CreditoFinalizacionModel getModelo() {
		return (CreditoFinalizacionModel)super.getModelo();
	}
	
	private void actualizarTablaCreditosFinalizacion() {
		CuentaCorriente ctacte = getModelo().getCuentaCorriente();
		List<Credito> listaCreditosImpagos = creditoDAO.getListaCreditosImpagos(ctacte);
		modeloTabla.setListaCreditos(listaCreditosImpagos);
	}

	@Override
	public void guardar() {
		super.guardar();
	}
	private void actualizarValorTotal() throws LogicaException {
		String valor = NumberFormat.getCurrencyInstance().format(modeloTabla.getTotal());
		getVista().getPnlCreditosFinalizacion().getLblTotal().setText(valor);
		getModelo().setMontoCancelacion(creditoDAO.getMontoCancelacion(modeloTabla.getListaSeleccionados()));
	}
	
	@Override
	protected void inicializarModelos() {
		modeloTabla = new TblCreditosSeleccionablesModel();
		this.getVista().getPnlCreditosFinalizacion().getTblListaCreditos().setModel(modeloTabla);
	}

	@Override
	protected void actualizarVista() {
		super.actualizarVista();
		Utiles.setEnableRecursivo(getVista().getPnlCreditosFinalizacion(),!inCheque );
		
		BigDecimal montoEntregaRedondeado = this.getModelo().getMontoEntrega();
		montoEntregaRedondeado = montoEntregaRedondeado.setScale(2);
		
		this.getVista()
			.getPnlDatosCredito()
			.getLblMontoEntregado()
			.setText(montoEntregaRedondeado.toString());
	}
	
	@Override
	public PnlAMCreditoFinalizacionView getVista() {
		return (PnlAMCreditoFinalizacionView)super.getVista();
	}
	
	public static void main(String[] args){
		JDialog dialogo = new JDialog();
		dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		AMCreditoControllerPorFinalizacion locPnlAMCredito = new AMCreditoControllerPorFinalizacion(new AdminCredito(new JFrame()));
		dialogo.add(locPnlAMCredito.getVista());
		dialogo.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		dialogo.pack();
		dialogo.setVisible(true);
	}

	@Override
	protected void actualizarModelo() throws LogicaException {
		super.actualizarModelo();
	}
	
}
