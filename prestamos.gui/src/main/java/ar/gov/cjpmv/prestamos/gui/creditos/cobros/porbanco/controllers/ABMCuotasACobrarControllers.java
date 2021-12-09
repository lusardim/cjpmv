package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroPorBancoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.ABMCuotasACobrar;
import ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.models.TblCobroCuotasModel;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CuotasCellRenderer;
import ar.gov.cjpmv.prestamos.gui.personas.model.EstadoPersonaFisicaCellRenderer;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class ABMCuotasACobrarControllers {

	private ABMCuotasACobrar vista;
	private Long legajo;
	private Long cuip;
	private EstadoPersonaFisica estado;
	private Integer numeroCredito;
	private CobroPorBancoDAO cobroPorBancoDAO;
	private TblCobroCuotasModel tablaModel;
	private String locSolicitante;
	private List<Cuota> listaCuotas;
	private List<Cuota> listaCuotaACobrar;
	private CuentaCorriente cuentaCorriente;
	private boolean isCuotaConSeguro;
	private Date fechaCobro;
	
	
	public ABMCuotasACobrarControllers(JDialog pPadre, Date pFechaCobro){
		this.vista= new ABMCuotasACobrar(pPadre, true);
		vista.setLocationRelativeTo(pPadre);
		this.fechaCobro= pFechaCobro;
		this.inicializarVista();
		this.inicializarEventos();
		this.inicializarModelo();
		this.actualizarVista();
		
		this.locSolicitante="";
		this.listaCuotas= new ArrayList<Cuota>();
		this.listaCuotaACobrar= new ArrayList<Cuota>();
		this.cuentaCorriente = new CuentaCorriente();
	
	}

	private void inicializarVista() {
		vista.getTblCuotas().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vista.getTblCuotas().setRowSelectionAllowed(true);
		vista.getTblCuotas().setColumnSelectionAllowed(false);
		vista.getTblCuotas().setDefaultRenderer(Object.class, new CuotasCellRenderer());
		vista.getCbxConSeguro().setSelected(false);
//		vista.getTblCuotas().setAutoCreateRowSorter(true);
//		CuotasCellRenderer renderer = (CuotasCellRenderer)vista.
//											getTblCuotas().
//											getDefaultRenderer(Integer.class);
//		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		
	}

	private void actualizarVista() {
		this.vista.getTxtLegajo().setText(Utiles.cadenaVaciaSiNulo(this.legajo));
		this.vista.getTxtCuilCuit().setText(Utiles.cadenaVaciaSiNulo(this.cuip));
		this.vista.getTxtNumeroCredito().setText(Utiles.cadenaVaciaSiNulo(this.numeroCredito));
		this.vista.getCbxEstado().setSelectedItem(this.estado);
		if(this.tablaModel!=null) {
			this.vista.getTblCuotas().setModel(tablaModel);
		}
		this.vista.getXtsTitulo().setTitle("Cuotas a cobrar: "+this.locSolicitante);
	}

	
	public Date getFechaCobro(){
		return this.fechaCobro;
	}

	private void inicializarModelo() {
		this.isCuotaConSeguro= this.vista.getCbxConSeguro().isSelected();
		DefaultComboBoxModel locComboBoxModel = new DefaultComboBoxModel();
		//locComboBoxModel.addElement(null);
		for (EstadoPersonaFisica cadaEstado : EstadoPersonaFisica.values()){
			if((cadaEstado.equals(EstadoPersonaFisica.ACTIVO))||(cadaEstado.equals(EstadoPersonaFisica.PASIVO))){
				locComboBoxModel.addElement(cadaEstado);
			}
		}
		this.vista.getCbxEstado().setModel(locComboBoxModel);
		this.vista.getCbxEstado().setRenderer(new EstadoPersonaFisicaCellRenderer());
		
		List<Cuota> listaCuotas = new ArrayList<Cuota>();
		this.tablaModel = new TblCobroCuotasModel(listaCuotas, this.isCuotaConSeguro, this.fechaCobro);
		this.locSolicitante="";

	}


	private void inicializarEventos() {
		
		this.vista.getBtnBuscar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarCuotas();
				
			}
		});
		
		
		this.vista.getBtnAceptar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				aceptar();
				
			}
		});
		
		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
				
			}
		});
		
		this.vista.getCbxConSeguro().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				 isCuotaConSeguro= vista.getCbxConSeguro().isSelected();
				 if(!listaCuotas.isEmpty()){
					tablaModel= new TblCobroCuotasModel(listaCuotas, isCuotaConSeguro, fechaCobro);
					actualizarVista();
				 }		
				
			
				
			}
		});
		
		
		
	}
	
	private void buscarCuotas() {
		
		/*
		 Si se ingresa numero de credito se buscan las cuotas que no tienen cancelacion
		 Si no se ingreso numero de credito
		 	--> y si se ingresa cuit o legajo y estado se buscan todas las cuotas de todos
		 		los creditos en donde esa persona esta como solicitante y garante (con afectar=true)
		 		las cuotas recuperadas no tienen cancelacion
		 		
		 		
		 			VER LA PARTE DE LIQUIDACION 
		 			en este caso se recuperan todas sin tener en cuenta si fueron liquidadas
		 */
		
		
		try{
			this.actualizarModelo();
			
			if((this.numeroCredito==null)&&(this.cuip==null)&&(this.legajo!=null)&&(this.estado==null)){
				int codigoMensaje=56;
				String campoMensaje="";
				throw new LogicaException(codigoMensaje, campoMensaje);
			}
			else{
				if((this.legajo==null)&&(this.numeroCredito==null)&&(this.cuip==null)){
					int codigoMensaje=53;
					String campoMensaje="";
					throw new LogicaException(codigoMensaje, campoMensaje);
				
				}
			}
			this.cobroPorBancoDAO= new CobroPorBancoDAO();
		
			
			if (this.numeroCredito!=null){
				listaCuotas= this.cobroPorBancoDAO.getListaCuotasACobrar(this.numeroCredito);
				if(!listaCuotas.isEmpty()){
					
					this.cuentaCorriente= listaCuotas.get(0).getCredito().getCuentaCorriente();
				}
			}
			else{
				listaCuotas= this.cobroPorBancoDAO.getListaCuotasACobrar(this.cuip, this.legajo, this.estado);
				
				if(!listaCuotas.isEmpty()){
					listaCuotas.get(0).getCredito().getCuentaCorriente().getPersona().getCui();
					
					this.cuentaCorriente = this.cobroPorBancoDAO.getCuentaCorriente(this.cuip, this.legajo, this.estado).getCredito().getCuentaCorriente();			
				}
			}
			
		
			
			
			this.tablaModel= new TblCobroCuotasModel(listaCuotas, this.isCuotaConSeguro, this.fechaCobro);
			if(!listaCuotas.isEmpty()){
				this.locSolicitante=listaCuotas.get(0).getCredito().getCuentaCorriente().getPersona().getNombreYApellido();
			}
			else{
				this.locSolicitante="";
			}
			this.actualizarVista();
			
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this.vista, e.getMessage(),e.getTitulo(),JOptionPane.ERROR_MESSAGE);
			
			List<Cuota> listaCuotas= new ArrayList<Cuota>();
			this.tablaModel= new TblCobroCuotasModel(listaCuotas, this.isCuotaConSeguro, this.fechaCobro);
			this.actualizarVista();
		
		}
		
	}
	
	private void actualizarModelo() {
		this.legajo= null;
		if(Utiles.nuloSiVacio(this.vista.getTxtLegajo().getText())!=null){
			this.legajo =  Long.parseLong(this.vista.getTxtLegajo().getText().trim());
		}
		
		this.estado = (EstadoPersonaFisica) this.vista.getCbxEstado().getSelectedItem();
		
		this.cuip= null;
		if(Utiles.nuloSiVacio(this.vista.getTxtCuilCuit().getText())!=null){
			cuip =  Long.parseLong(this.vista.getTxtCuilCuit().getText());
		}
		
		this.numeroCredito= null;
		if(Utiles.nuloSiVacio(this.vista.getTxtNumeroCredito().getText())!=null){
			this.numeroCredito =  Integer.parseInt(this.vista.getTxtNumeroCredito().getText());
		}
		this.isCuotaConSeguro= this.vista.getCbxConSeguro().isSelected();
	}

	private void aceptar() {
		try{
			if(this.listaCuotas==null){
				int codigoMensaje=58;
				String campoMensaje="";
				throw new LogicaException(codigoMensaje, campoMensaje);
			
			}
			else{
				if(this.tablaModel.getListaCuotasACobrar().isEmpty()){
					int codigoMensaje=58;
					String campoMensaje="";
					throw new LogicaException(codigoMensaje, campoMensaje);
				}
				else{
					
					this.listaCuotaACobrar= this.tablaModel.getListaCuotasACobrar();
					this.vista.dispose();
				}
			}
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this.vista, e.getMessage(),e.getTitulo(),JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public List<Cuota> getCuotasACobrar(){
		return this.listaCuotaACobrar;
	}
	
	public boolean isConSeguro(){
		return this.isCuotaConSeguro;
	}
	
	public CuentaCorriente getCuentaCorriente(){
		return this.cuentaCorriente;
	}
	
	public void setVisible(boolean pVisible){
		this.vista.setVisible(pVisible);
	}

	private void cancelar() {
		String pMensaje= "¿Realmente desea cancelar la operación?";
		String pTitulo= "Confirmación";
		int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.listaCuotaACobrar= new ArrayList<Cuota>();
			this.listaCuotaACobrar.clear();
			this.vista.dispose();	
		}
	}
	
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Calendar fecha= Calendar.getInstance();
		Date locFecha= fecha.getTime();
		ABMCuotasACobrarControllers locABMCobroPorBanco = new ABMCuotasACobrarControllers(new JDialog(), locFecha);
		locABMCobroPorBanco.vista.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		locABMCobroPorBanco.vista.pack();
		locABMCobroPorBanco.vista.setVisible(true);	
	}
}
