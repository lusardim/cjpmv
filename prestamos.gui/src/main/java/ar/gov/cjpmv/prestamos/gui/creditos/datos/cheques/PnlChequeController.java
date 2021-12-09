package ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.model.ChequeModel;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.model.CuentaBancariaComboBoxModel;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.BusquedaPersonaFisicaController;
import ar.gov.cjpmv.prestamos.gui.personas.model.CuentaBancariaListCellRenderer;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlChequeController {
	private PnlCheque vista;
	private ChequeModel modelo;
	private BusquedaPersonaFisicaController busquedaPersonaFisicaController;
	
	private NumberFormat formateadorNumeros = NumberFormat.getNumberInstance();
	
	public PnlChequeController(Cheque pCheque, PnlCheque pVista){
		formateadorNumeros.setMaximumFractionDigits(2);
		modelo = new ChequeModel(pCheque);
		vista = pVista;
		this.busquedaPersonaFisicaController = new BusquedaPersonaFisicaController(Utiles.getJDialogPadre(vista));
		this.inicializarModelos();
		this.actualizarVista();
		this.inicializarEventos();
		this.vista.getTxtMonto().setEditable(false);
		this.vista.getTxtMonto().setEnabled(false);
	}

	private void inicializarModelos() {
		CuentaBancariaComboBoxModel locCboModel = new CuentaBancariaComboBoxModel();
		this.vista.getCboCuentaBancaria().setModel(locCboModel);
		this.vista.getCboCuentaBancaria().setRenderer(new CuentaBancariaListCellRenderer());
	}

	private void actualizarVista() {
		Cheque locCheque = this.modelo.getCheque();
		vista.getTxtConcepto().setText(Utiles.cadenaVaciaSiNulo(locCheque.getConcepto()));
		vista.getTxtMonto().setText( 
				(locCheque.getMonto()==null)?"":
					formateadorNumeros.format(locCheque.getMonto())
		);
				
		String txtNombreYApellido = Utiles.cadenaVaciaSiNulo(locCheque.getNombrePersona());
		vista.getTxtNombreApellido().setText(txtNombreYApellido);

		vista.getCboCuentaBancaria().setSelectedItem(locCheque.getCuenta());
		vista.getTxtNumeroCheque().setText(Utiles.cadenaVaciaSiNulo(locCheque.getNumero()));
		this.setEstadoCheque();
		vista.getDcFechaEmision().setDate(locCheque.getFechaEmision());
		vista.getDcFechaPago().setDate(locCheque.getFechaPago());
	}
	
	private void setEstadoCheque() {
		Cheque locCheque = this.modelo.getCheque();
		if (locCheque.getCanceladoPor() != null && !locCheque.getEstadoCheque().equals(EstadoCheque.CANCELADO)){
			vista.getXlblEstado().setForeground(Color.RED);
			vista.getXlblEstado().setText("A cancelar");
		}
		else{
			vista.getXlblEstado().setText(this.formatearEstado(this.modelo.getCheque().getEstadoCheque()));
		}
	}

	private String formatearEstado(EstadoCheque pEstadoCheque) {
		switch(pEstadoCheque){
			case CANCELADO: return "Cancelado";
			case IMPRESO: return "Impreso";
			case PENDIENTE_IMPRESION: return "Pendiente de impresión";
		}
		return null;
	}

	public void actualizarModelo() throws LogicaException{
		modelo.setImprimir(vista.getChkImprimir().isSelected());
		
		Cheque locCheque = this.modelo.getCheque();
		
		String numero = Utiles.nuloSiVacio(this.vista.getTxtNumeroCheque().getText());
		if (numero!=null){
			locCheque.setNumero(Integer.parseInt(numero));
		}
		else{
			locCheque.setNumero(null);
		}
		BigDecimal monto = new BigDecimal(0);
		try {
			if (Utiles.nuloSiVacio(vista.getTxtMonto().getText())!=null){
				String valor = vista.getTxtMonto().getText();
				valor= valor.replace(".", "");
				valor= valor.replace(",", ".");
			
				monto = new BigDecimal(valor).setScale(2,RoundingMode.HALF_UP);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new LogicaException(40,"monto");
		}
		locCheque.setMonto(monto);		
		locCheque.setFechaEmision(this.vista.getDcFechaEmision().getDate());
		locCheque.setFechaPago(this.vista.getDcFechaPago().getDate());
		
		if (!this.modelo.isPersonaSeleccionada()){
			locCheque.setNombrePersona(this.vista.getTxtNombreApellido().getText());
		}
		
		this.modelo.getCheque().setCuenta((CuentaBancaria)this.vista.getCboCuentaBancaria().getSelectedItem());
	}

	public PnlChequeController(){
		this(new Cheque(),new PnlCheque());
	}
	
	public ChequeModel getModelo() {
		return modelo;
	}
	
	public static void main(String[] args){
		JFrame locFrame = new JFrame();
		PnlChequeController locController = new PnlChequeController();
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		locFrame.add(locController.vista,BorderLayout.CENTER);
		locFrame.pack();
		locFrame.setVisible(true);
	}
	
	private void seleccionarPersona() {
		this.busquedaPersonaFisicaController.setVisible(true);
		PersonaFisica locPersonaFisica = this.busquedaPersonaFisicaController.getPersonaSeleccionada();
		if (locPersonaFisica!=null){
			this.modelo.setPersonaSeleccionada(true);
			this.modelo.getCheque().setEmitidoA(locPersonaFisica);
			this.vista.getTxtNombreApellido().setText(locPersonaFisica.getNombreYApellido());
		}
	}
	
//------------listeners
	private void inicializarEventos() {
		this.vista.getBtnBuscarPersona().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarPersona();
			}
		});
		this.vista.getTxtNombreApellido().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (modelo.isPersonaSeleccionada()){
					if (!vista.getTxtNombreApellido().getText()
								.equals(modelo.getCheque()
											 .getEmitidoA().getNombreYApellido()))
					{
						int seleccion = JOptionPane.showConfirmDialog(vista,
												   "Si cambia el nombre de la persona se ignorará la persona seleccionada con anterioridad. " +
												   "¿Desea continuar?",
												   "Confirmación",
												   JOptionPane.YES_NO_OPTION);
						if (seleccion == JOptionPane.YES_OPTION){
							modelo.getCheque().setEmitidoA(null);
							modelo.setPersonaSeleccionada(false);
							modelo.getCheque().setNombrePersona(vista.getTxtNombreApellido().getText().trim());
						}
						else{
							vista.getTxtNombreApellido().setText(modelo.getCheque().getEmitidoA().getNombreYApellido());
						}
					}
				}
			}
		});
	}

	public PnlCheque getVista() {
		return vista;
	}

}
