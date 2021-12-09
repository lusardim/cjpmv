package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.CuentaBancariaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.EntidadBancariaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoCuenta;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Banco;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;
import ar.gov.cjpmv.prestamos.gui.configuracion.ABMConfiguracionSetearPnl;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlCuentaBancaria;
import ar.gov.cjpmv.prestamos.gui.personas.model.CbxTipoCuentaRenderer;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;


public class ABMConfiguracionCuentaBancaria {

	private ABMConfiguracionSetearPnl vista;
	private PnlCuentaBancaria pnlCuentaBancaria;
	
	private CuentaBancaria cuentaBancaria;
	private CuentaBancariaDAO cuentaBancariaDAO;
	
	private EntidadBancariaDAO bancoDAO;
	private ComboBoxModel modeloBancos;
	
	private NumberFormat formateador;

	
	public ABMConfiguracionCuentaBancaria(JDialog pPadre, CuentaBancariaDAO pCuentaBancariaDAO){
		this(pPadre, pCuentaBancariaDAO, new CuentaBancaria());
	}
	
	public ABMConfiguracionCuentaBancaria(JDialog pPadre, CuentaBancariaDAO pCuentaBancariaDAO, CuentaBancaria pCuentaBancaria){
		this.cuentaBancaria= pCuentaBancaria;
		this.cuentaBancariaDAO= pCuentaBancariaDAO;
		this.formateador= NumberFormat.getInstance();
		formateador.setMaximumFractionDigits(2);
		vista= new ABMConfiguracionSetearPnl(pPadre,true);
		pnlCuentaBancaria= new PnlCuentaBancaria();
		this.vista.setPanel(pnlCuentaBancaria);
		bancoDAO = new EntidadBancariaDAO();
		this.modeloBancos = new DefaultComboBoxModel(this.bancoDAO.getListaBancos().toArray());
		this.inicializarEventos();
		this.actualizarVista();
	}

	private void actualizarVista() {
		this.pnlCuentaBancaria.getTxtSaldoInicial().setText(Utiles.cadenaVaciaSiNulo(this.cuentaBancaria.getSaldo()));
		this.pnlCuentaBancaria.getTxtNumeroCuenta().setText(this.cuentaBancaria.getNumero());
		this.pnlCuentaBancaria.getTxtCBU().setText(this.cuentaBancaria.getCbu());
		this.pnlCuentaBancaria.getCbxEntidadBancaria().setModel(this.modeloBancos);
		this.pnlCuentaBancaria.getCbxEntidadBancaria().setSelectedItem(this.cuentaBancaria.getBanco());
		this.pnlCuentaBancaria.getCbxTipoCuenta().setModel(new DefaultComboBoxModel(TipoCuenta.values()));
		this.pnlCuentaBancaria.getCbxTipoCuenta().setRenderer(new CbxTipoCuentaRenderer());
		this.pnlCuentaBancaria.getCbxTipoCuenta().setSelectedItem(this.cuentaBancaria.getTipo());
	}
	
	private void actualizarModelo() throws LogicaException {
		String aux= this.pnlCuentaBancaria.getTxtNumeroCuenta().getText().trim();
		
		//Si ingresan un string con espacios o el campo es vacio se le asigna el valor null, y luego lo maneja la excepcion
		if(aux.isEmpty()){
			aux=null;
		}
		this.cuentaBancaria.setNumero(aux);
		
		String aux1= this.pnlCuentaBancaria.getTxtCBU().getText().trim();
		
		if(aux1.isEmpty()){
			aux1=null;
		}
		this.cuentaBancaria.setCbu(aux1);
		
		if(this.pnlCuentaBancaria.getCbxEntidadBancaria().getSelectedItem()==null){
			this.cuentaBancaria.setBanco(null);	
		}
		else{
			Banco locBanco= (Banco) this.pnlCuentaBancaria.getCbxEntidadBancaria().getSelectedItem();
			this.cuentaBancaria.setBanco(locBanco);
		}
		
		if(this.pnlCuentaBancaria.getCbxTipoCuenta().getSelectedItem()==null){
			this.cuentaBancaria.setTipo(null);	
		}
		else{
			this.cuentaBancaria.setTipo((TipoCuenta)this.pnlCuentaBancaria.getCbxTipoCuenta().getSelectedItem());
		}

		String locSaldoInicial= this.pnlCuentaBancaria.getTxtSaldoInicial().getText();
		locSaldoInicial= locSaldoInicial.replace(',','.');
		try{
			this.cuentaBancaria.setSaldo(new BigDecimal(locSaldoInicial));
		}
		catch (NumberFormatException e){
			int locCodigoMensaje= 17;
			String locCampoMensaje="El campo saldo inicial";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		
		}
	}


	

	private void inicializarEventos() {
		this.vista.getBtnGuardar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});
		
		this.vista.getBtnCancelar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});			
	}
	
	
	private void guardar(){
		try{
			this.actualizarModelo();
			if(this.cuentaBancaria.getId()==null){
				this.cuentaBancariaDAO.agregar(cuentaBancaria);
			}
			else{
				this.cuentaBancariaDAO.modificar(cuentaBancaria);
			}
			this.vista.dispose();
		}
		catch(LogicaException e){
			String pTitulo=e.getTitulo();
			String pMensaje=e.getMessage();
			JOptionPane.showMessageDialog(this.vista , pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void cancelar() {
		String pMensaje= "¿Realmente desea cancelar la operación?";
		String pTitulo= "Confirmación";
		int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.vista.dispose();	
		}
	}
	
	public void setTitulo(String pTitulo) {
		this.vista.setTitle(pTitulo);
	}

	public void setVisible(boolean pVisible){
		this.vista.setVisible(pVisible);
	}

	public void setLocationRelativeTo(JComponent pPadre) {
		this.vista.setLocationRelativeTo(pPadre);
	}


	
}
