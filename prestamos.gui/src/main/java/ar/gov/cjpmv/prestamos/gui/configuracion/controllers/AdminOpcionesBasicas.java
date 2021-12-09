package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import ar.gov.cjpmv.prestamos.core.business.dao.ConfiguracionSistemaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema.TipoSistema;
import ar.gov.cjpmv.prestamos.core.persistence.ConfiguracionSistema;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracionOpcionesBasicas;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class AdminOpcionesBasicas {

	private PnlDchaConfiguracionOpcionesBasicas vista;

	private ConfiguracionSistema configuracionSistema;
	private ConfiguracionSistemaDAO configuracionSistemaDAO;
	private NumberFormat formateador;
	
	public AdminOpcionesBasicas() {
		super();
		this.vista= new PnlDchaConfiguracionOpcionesBasicas();
		this.configuracionSistemaDAO= new ConfiguracionSistemaDAO();
		this.formateador= NumberFormat.getInstance();
		formateador.setMaximumFractionDigits(2);
		this.inicializarEventos();
		this.inicializarVista();
	}

	private void inicializarVista() {
		this.inicializarModelo();
		this.actualizarVista();
	}
	
	private void inicializarModelo() {
		try {
			this.configuracionSistema= configuracionSistemaDAO.getConfiguracionSistema();
		} 
		catch (LogicaException e) {
			String pTitulo=e.getTitulo();
			String pMensaje=e.getMessage();
			JOptionPane.showMessageDialog(this.vista , pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarVista() {
		this.vista.getXlblTitulo().setText("Configuración: Opciones Básicas de Créditos");
		this.vista.getBtnGuardar().setEnabled(true);
		this.vista.getCbxSistemaCreditos().setModel(new DefaultComboBoxModel(TipoSistema.values()));
		this.vista.getCbxSistemaCreditos().setSelectedItem(this.configuracionSistema.getSistemaPorDefecto());
		this.vista.getTxtInteresAnual().setText(Utiles.cadenaVaciaSiNulo(this.configuracionSistema.getInteresPorDefecto()));
		this.vista.getSFDiaVencimiento().setValue(this.configuracionSistema.getDiaVencimiento());
	}

	private void actualizarModelo() throws LogicaException{
		String locInteres= this.vista.getTxtInteresAnual().getText();
		locInteres=locInteres.replace(',','.');
		try {
			this.configuracionSistema.setInteresPorDefecto(new BigDecimal(locInteres).setScale(2,RoundingMode.HALF_UP));	
		} 
		catch (NumberFormatException e) {
			int locCodigoMensaje= 17;
			String locCampoMensaje="El campo interés anual";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		this.configuracionSistema.setSistemaPorDefecto((TipoSistema)this.vista.getCbxSistemaCreditos().getSelectedItem());
		this.configuracionSistema.setDiaVencimiento(this.vista.getSFDiaVencimiento().getValue()); 
	}

	private void inicializarEventos() {
		this.vista.getBtnGuardar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});
		KeyListener enter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					guardar();
				}
			}
		};
		vista.getTxtInteresAnual().addKeyListener(enter);		
		vista.getSFDiaVencimiento().addKeyListener(enter);
	}


	private void guardar() {
		try{
			this.actualizarModelo();
			this.configuracionSistemaDAO.modificar(this.configuracionSistema);
			JOptionPane.showMessageDialog(this.vista, "Los datos se han guardado correctamente.", "Información" ,JOptionPane.INFORMATION_MESSAGE);
		}
		catch(LogicaException e){
			String pTitulo=e.getTitulo();
			String pMensaje=e.getMessage();
			JOptionPane.showMessageDialog(this.vista , pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}
		finally{
			this.actualizarVista();
		}
		
	}
	
	public PnlDchaConfiguracionOpcionesBasicas getVista(){
		return this.vista;
	}
	
	
	public static void main(String[]args){
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		JFrame frame= new JFrame();
		//Se llama a este metodo para que la aplicacion no siga corriendo
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JDialog locJdialog= new JDialog();
		AdminOpcionesBasicas controlador= new AdminOpcionesBasicas();
		locJdialog.add(controlador.getVista());
		locJdialog.pack();
		locJdialog.setVisible(true);
	}	
	
	
	
	
	
	
	

}
