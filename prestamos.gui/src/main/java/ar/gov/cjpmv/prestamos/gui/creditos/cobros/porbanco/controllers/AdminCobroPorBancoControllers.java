package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.CuentaBancariaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroPorBancoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroPorBanco;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.PnlCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.models.TblDetalleCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.reportes.CobroPorBancoImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class AdminCobroPorBancoControllers {
	
	private PnlCobro vista;
	private AdminCredito adminCredito;

	private JDialog vistaPadre;
	private TblDetalleCobro tablaModel;
	private List<Cuota> listaCuotasACobrar;
	private CuentaBancariaDAO cuentaBancariaDAO;
	private ComboBoxModel modeloCuentaBancaria;
	private BigDecimal montoTotalCuota; 
	private BigDecimal saldoAcreedor; 
	private BigDecimal totalAPagar; 
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private CuentaCorriente cuentaCorriente;
	private CuentaBancaria cuentaBancaria;
	private Date fechaCobro;
	private Integer numeroBoleta;
	private boolean isCuotaConSeguro;
	
	
	
	public AdminCobroPorBancoControllers(AdminCredito pAdminCredito) {
		this.adminCredito= pAdminCredito;
		this.vistaPadre= pAdminCredito.getVista();
		this.vista= new PnlCobro();
		this.inicializarEventos();
		this.inicializarModelo();
		this.actualizarVista();
		this.vista.getLblTituloPnlDcha().setText("Cobro: Depósito Bancario");
		this.cuentaCorriente= new CuentaCorriente();
	
	}

	private void inicializarModelo() {
		this.isCuotaConSeguro= false;
		this.listaCuotasACobrar= new ArrayList<Cuota>();
		this.tablaModel= new TblDetalleCobro(listaCuotasACobrar, this.isCuotaConSeguro, this.fechaCobro);
		cuentaBancariaDAO = new CuentaBancariaDAO();
		if(this.cuentaBancariaDAO.findCuentasBancarias()!=null){
			this.modeloCuentaBancaria = new DefaultComboBoxModel(this.cuentaBancariaDAO.findCuentasBancarias().toArray());
		}
		else{
			List<CuentaBancaria> locCuenta= new ArrayList<CuentaBancaria>();
			this.modeloCuentaBancaria = new DefaultComboBoxModel(locCuenta.toArray());
		}
		BigDecimal cero = new BigDecimal(0);
		this.montoTotalCuota = cero;  
		this.saldoAcreedor = cero;
		this.totalAPagar = cero;
	}
	
	private void actualizarVista() {
		this.vista.getPnlCobroPorBanco().getCkbxImprimirRecibo().setVisible(false);
		this.vista.getPnlCobroPorBanco().getTblDetalleCobro().setModel(tablaModel);
		this.vista.getPnlCobroPorBanco().getCbxCuentaBancaria().setModel(this.modeloCuentaBancaria);
		this.cambioBanco();
		this.vista.getPnlCobroPorBanco().getTxtTotal().setText(this.formateadorMonetario.format(this.montoTotalCuota));
		this.vista.getPnlCobroPorBanco().getTxtSaldoAcreedor().setText(this.formateadorMonetario.format(this.saldoAcreedor));
		this.vista.getPnlCobroPorBanco().getTxtTotalAPagar().setText(this.formateadorMonetario.format(this.totalAPagar));
		if(this.fechaCobro!=null){
			this.vista.getPnlCobroPorBanco().getDtcFechaCobro().setDate(this.fechaCobro);
		}
	}


	
	
	private void inicializarEventos() {
		this.vista.getPnlCobroPorBanco().getBtnAgregarCuotas().addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				agregarCuotas();
				
			}

		});
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarCobro();				
			}
		});
		
		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelarCobro();
			}
		});
		
		
		this.vista.getBtnImprimir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imprimirCobroPorBanco(Reportes.COBRO_POR_BANCO);
				
			}
		});
		
		
		
		this.vista.getPnlCobroPorBanco().getDtcFechaCobro().addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if(isFechaDiferente()){
					recalcularTabla();
				}
			}
		});
			
	
		
	
		
		this.vista.getPnlCobroPorBanco().getCbxCuentaBancaria().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cambioBanco();				
			}
		});
		
	}
	
	private boolean isFechaDiferente() {
		if((this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate()==null)||(this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate()!=this.fechaCobro)){
			return true;
		}
		return false;
	}
	
	
	

	private void recalcularTabla() {
		if(this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate()!=null){
			if((!this.listaCuotasACobrar.isEmpty())){
				this.fechaCobro= this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate();
				this.tablaModel= new TblDetalleCobro(this.listaCuotasACobrar, this.isCuotaConSeguro, this.fechaCobro);
				this.montoTotalCuota= this.tablaModel.getMontoTotalCuotas();
				this.totalAPagar = this.montoTotalCuota.subtract(this.saldoAcreedor);
				this.actualizarVista();
			}
		}
		else{
			this.listaCuotasACobrar.clear();
			this.actualizarVista();
		}
	}
	
	private void guardarCobro() {
		
		
		try{
			this.validarDatosCuentaBancaria();
			
			
			CobroPorBanco locCobroPorBanco= new CobroPorBanco();
			locCobroPorBanco.setCuenta(this.cuentaBancaria);
			locCobroPorBanco.setFecha(this.fechaCobro);
			locCobroPorBanco.setConSeguro(this.isCuotaConSeguro);
			locCobroPorBanco.setNumeroBoleta(this.numeroBoleta);
			
			locCobroPorBanco.setCuentaCorriente(this.cuentaCorriente);
			locCobroPorBanco.setPagador(this.cuentaCorriente.getPersona());
		
			locCobroPorBanco.setMonto(this.totalAPagar);
			locCobroPorBanco.setSobranteAlDia(this.saldoAcreedor);
			
			
			
			
			CobroPorBancoDAO locCobroPorBancoDAO= new CobroPorBancoDAO();
			locCobroPorBancoDAO.cobroPorBanco(locCobroPorBanco, this.listaCuotasACobrar, this.isCuotaConSeguro, this.fechaCobro);
			
			String pMensaje="El cobro se ha realizado con éxito.";
			String pTitulo="Operación exitosa";
			JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.INFORMATION_MESSAGE);
			this.adminCredito.mostrarPnlEstadoCuenta();	
		}
		catch(LogicaException e){
			
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		
			
		}
		
	}



	private void validarDatosCuentaBancaria() throws LogicaException {
		if(this.vista.getPnlCobroPorBanco().getCbxCuentaBancaria().getSelectedItem()!=null){
			this.cuentaBancaria= new CuentaBancaria();
			this.cuentaBancaria=(CuentaBancaria) this.vista.getPnlCobroPorBanco().getCbxCuentaBancaria().getSelectedItem();
			this.validarDatosFecha();
		}
		else{
			int codigo=2;
			String campo="cuenta bancaria";
			throw new LogicaException(codigo, campo);
		}
		
	}
	



	private void validarDatosFecha() throws LogicaException {
		
		if(this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate()!=null){
			this.fechaCobro= this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate();
			this.validarDatosBoleta();
		}
		else{
			int codigo=2;
			String campo="fecha de cobro";
			throw new LogicaException(codigo, campo);
		}		
	}
	
	private void validarDatosBoleta() throws LogicaException{
		if(Utiles.nuloSiVacio(this.vista.getPnlCobroPorBanco().getTxtNumeroBoleta().getText().trim())!=null){
			this.numeroBoleta=Integer.valueOf(this.vista.getPnlCobroPorBanco().getTxtNumeroBoleta().getText().trim());
			this.validarDatosCuentaCorriente();
		}
		else{
			int codigo=2;
			String campo="número de boleta";
			throw new LogicaException(codigo, campo);
		}		
	}



	private void validarDatosCuentaCorriente() throws LogicaException {
		if(this.cuentaCorriente!=null){
			this.validarAgregarCuotas();
		}
		else{
			int codigo=59;
			String campo="";
			throw new LogicaException(codigo, campo);
		}		
		
	}



	private void validarAgregarCuotas()throws LogicaException {
		if(this.listaCuotasACobrar.isEmpty()){
			int codigo=59;
			String campo="";
			throw new LogicaException(codigo, campo);
		}	
	}
	
	
	private void imprimirCobroPorBanco(Reportes locReportes) {
		try{
			this.validarAgregarCuotas();
			List<CobroPorBancoImpresion> listaDetalleImpresion = this.formatearReporte();
					GestorImpresion.imprimirCollectionDataSource(this.vistaPadre, locReportes, this.setearParametros(), listaDetalleImpresion);
		}
		catch(LogicaException e){
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el reporte.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	
	}

	
	private List<CobroPorBancoImpresion> formatearReporte() {
		List<CobroPorBancoImpresion> locDetalleCuotas = new ArrayList(); 
		for(Cuota cadaCuota: this.listaCuotasACobrar){
		 String locNumeroCredito=cadaCuota.getCredito().getNumeroCredito().toString();
		 String locSolicitante= cadaCuota.getCredito().getCuentaCorriente().getPersona().getNombreYApellido();
		 String locNumeroCuota= cadaCuota.getNumeroCuota().toString();
		 String locVencimiento= this.formateadorFecha.format(cadaCuota.getVencimiento());
		 String locValorTotal= this.formateadorMonetario.format(cadaCuota.getTotalSegunVencimiento(this.isCuotaConSeguro, this.fechaCobro));
		 locDetalleCuotas.add(new CobroPorBancoImpresion(locNumeroCredito, locSolicitante, locNumeroCuota, locVencimiento, locValorTotal));
		}
		return  locDetalleCuotas;
	}


	public Map<String, String> setearParametros(){
		Map<String, String> parametros= new HashMap<String, String>();
		if(this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate()!=null){
			this.fechaCobro= this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate();
			parametros.put("fechaCobro", this.formateadorFecha.format(this.fechaCobro));
		}
		else{
			parametros.put("fechaCobro", null);
		}
		
		if(Utiles.nuloSiVacio(this.vista.getPnlCobroPorBanco().getTxtNumeroBoleta().getText().trim())!=null){
			this.numeroBoleta=Integer.valueOf(this.vista.getPnlCobroPorBanco().getTxtNumeroBoleta().getText().trim());
			parametros.put("numeroBoleta", this.numeroBoleta.toString());
		}
		else{
			parametros.put("numeroBoleta", null);
		}
		if(this.vista.getPnlCobroPorBanco().getCbxCuentaBancaria().getSelectedItem()!=null){
			this.cuentaBancaria= new CuentaBancaria();
			this.cuentaBancaria=(CuentaBancaria) this.vista.getPnlCobroPorBanco().getCbxCuentaBancaria().getSelectedItem();
			parametros.put("numeroCuentaBancaria", this.cuentaBancaria.getNumero());
			parametros.put("entidadBancaria", this.cuentaBancaria.getBanco().getNombre());
		}
		else{
			parametros.put("numeroCuentaBancaria", null);
			parametros.put("entidadBancaria", null);
		}
		parametros.put("montoTotal", this.formateadorMonetario.format(this.montoTotalCuota));
		parametros.put("saldoAcreedor", this.formateadorMonetario.format(this.saldoAcreedor));
		parametros.put("totalAPagar", this.formateadorMonetario.format(this.totalAPagar));
		return parametros;	
	}


	private void cancelarCobro() {
		String pMensaje= "¿Realmente desea cancelar la operación?";
		String pTitulo= "Confirmación";
		int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.adminCredito.mostrarPnlEstadoCuenta();	
		}
		
	}
	
	
	
	private void cambioBanco() {
		CuentaBancaria locCuentaBancaria= new CuentaBancaria();
		locCuentaBancaria= (CuentaBancaria) this.vista.getPnlCobroPorBanco().getCbxCuentaBancaria().getSelectedItem();
		this.vista.getPnlCobroPorBanco().getTxtEntidadBancaria().setText(locCuentaBancaria.getBanco().getNombre());
		
	}
		
	
	

	private void agregarCuotas() {
		try {
			this.validarFecha();
			ABMCuotasACobrarControllers locControlador= new ABMCuotasACobrarControllers(this.vistaPadre, this.fechaCobro);
			locControlador.setVisible(true);
			this.listaCuotasACobrar= locControlador.getCuotasACobrar();
			this.isCuotaConSeguro= locControlador.isConSeguro();
			this.tablaModel= new TblDetalleCobro(this.listaCuotasACobrar, this.isCuotaConSeguro, this.fechaCobro);
			this.montoTotalCuota= this.tablaModel.getMontoTotalCuotas();
			this.cuentaCorriente= locControlador.getCuentaCorriente();
			this.saldoAcreedor= this.cuentaCorriente.getSobrante();
			this.fechaCobro= locControlador.getFechaCobro();
			this.totalAPagar = this.montoTotalCuota.subtract(this.saldoAcreedor);
			this.actualizarVista();
		} 
		catch (LogicaException e) {
			String pMensaje=e.getMessage();
			String pTitulo=e.getTitulo();
			JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}	
	}

	private void validarFecha() throws LogicaException {
		if(this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate()!=null){
			this.fechaCobro= this.vista.getPnlCobroPorBanco().getDtcFechaCobro().getDate();
		}
		else{
			int codigo=107;
			String campo="";
			throw new LogicaException(codigo, campo);
		}
	}

	
	public PnlCobro getVista(){
		return this.vista;
	}
	
	public static void main(String[]args){
		JFrame framePrueba= new JFrame();
		framePrueba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame locJFrame= new JFrame();
		AdminCredito locAdminCredito = new AdminCredito(locJFrame);
		AdminCobroPorBancoControllers controlador= new AdminCobroPorBancoControllers(locAdminCredito);
		locAdminCredito.getVista().add(controlador.getVista());
		locAdminCredito.getVista().setVisible(true);
	}
	
}
