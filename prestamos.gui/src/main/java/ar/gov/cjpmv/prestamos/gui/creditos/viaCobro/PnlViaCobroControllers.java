package ar.gov.cjpmv.prestamos.gui.creditos.viaCobro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlViaCobroControllers {
	
	private PnlDerechaViaCobro vista;
	private AdminCredito adminCredito;
	private Long cui;
	private Long legajo;
	private List<Credito> listaCredito;
	private TblResultadoViaCobro modeloTblViaCobrante;

	public PnlViaCobroControllers(AdminCredito adminCredito) {
		super();
		vista = new PnlDerechaViaCobro();
		this.adminCredito= adminCredito;
		this.inicializarEventos();
		this.listaCredito= new ArrayList<Credito>();
		this.actualizarVista();
	}

	private void actualizarVista() {
		this.disableBtnEditar();
		this.vista.getBtnCancelar().setVisible(false);
		this.vista.getBtnAceptarGuardar().setText("Finalizar");	
		this.modeloTblViaCobrante= new TblResultadoViaCobro(this.listaCredito);
		this.vista.getPnlViaCobro().getTblResultado().setModel(this.modeloTblViaCobrante);		
	}

	private void inicializarEventos() {
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finalizar();
			}
		});
		
		this.vista.getPnlViaCobro().getBtnBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});

		this.vista.getPnlViaCobro().getLblxEditarViaCobro().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (isSelectedRow() && e.getClickCount()==1){
					modificarViaCobro();
				}
			}

		});

		this.vista.getPnlViaCobro().getTblResultado().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(isSelectedRow()){
					enableBtnEditar();	
				}
			}
		});

		this.vista.getPnlViaCobro().getTblResultado().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (isSelectedRow() && e.getClickCount()==2){
					modificarViaCobro();
				}
			}
		});

		this.vista.getPnlViaCobro().getTblResultado().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				if(isSelectedRow() && e.getKeyCode() == KeyEvent.VK_ENTER){
					modificarViaCobro();
				}
			}
		});
	}

	private void enableBtnEditar() {
		this.vista.getPnlViaCobro().getLblxEditarViaCobro().setEnabled(true);
	}

	private void finalizar() {
		this.adminCredito.mostrarPnlEstadoCuenta();	
	}

	private void disableBtnEditar() {
		this.vista.getPnlViaCobro().getLblxEditarViaCobro().setEnabled(false);
	}

	private void modificarViaCobro() {
		int locSeleccionado= this.vista.getPnlViaCobro().getTblResultado().getSelectedRow();
		Credito creditoSeleccionado= this.modeloTblViaCobrante.getCredito(locSeleccionado);
		ModificacionViaCobroControllers controlador= new ModificacionViaCobroControllers(adminCredito.getVista(), creditoSeleccionado);
		controlador.setTitulo("Editar Vía de Cobro - Crédito Nº "+creditoSeleccionado.getNumeroCredito().toString());
		controlador.setLocationRelativeTo(this.adminCredito.getVista());
		controlador.setVisible(true);		
		this.buscar();
	}

	private boolean isSelectedRow() {
		return this.vista.getPnlViaCobro().getTblResultado().getSelectedRow()!=-1;
	}

	private void buscar()  {
		try{
			this.actualizarModelo();
			CreditoDAOImpl locCreditoDAO= new CreditoDAOImpl();
			if(locCreditoDAO.getCreditosNoFinalizados(this.legajo, this.cui)!=null){
				this.listaCredito= locCreditoDAO.getCreditosNoFinalizados(this.legajo, this.cui);
			}
			else{
				String mensaje="No se hallaron créditos en curso correspondiente a los datos ingresados";
				JOptionPane.showMessageDialog(this.vista, mensaje, "Error", JOptionPane.INFORMATION_MESSAGE);
			}
			this.actualizarVista();
			
		}
		catch (LogicaException e) {
			JOptionPane.showMessageDialog(this.vista, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void actualizarModelo() throws LogicaException {
		String cadenaLegajo= Utiles.nuloSiVacio(this.vista.getPnlViaCobro().getTxtLegajo().getText().trim());
		String cadenaCuil= Utiles.nuloSiVacio(this.vista.getPnlViaCobro().getTxtCuil().getText().trim());
		
		if((cadenaLegajo==null)&&(cadenaCuil==null)){
			int codigo=81;
			throw new LogicaException(codigo, "");
		}
		else{
			this.legajo=null;
			this.cui= null;
			if(cadenaLegajo!=null){
				try{
					this.legajo= Long.parseLong(cadenaLegajo);
				}
				catch (NumberFormatException e) {
					String campo="Legajo";
					int codigo=17;
					throw new LogicaException(codigo, campo);
				}
			}
			if(cadenaCuil!=null){
				try{
					this.cui= Long.parseLong(cadenaCuil);
				}
				catch (NumberFormatException e) {
					String campo="CUIL/CUIT";
					int codigo=17;
					throw new LogicaException(codigo, campo);
				}
			}
		}
	}

	public PnlDerechaViaCobro getVista(){
		return this.vista;
	}
}
