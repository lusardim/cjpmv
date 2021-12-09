package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Telefono;
import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoTelefono;
import ar.gov.cjpmv.prestamos.gui.personas.AMTelefonoView;
import ar.gov.cjpmv.prestamos.gui.personas.model.CbxTipoTelefonoRenderer;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class AMTelefonoController {

	private AMTelefonoView vista;
	private Telefono modelo;
	
	public AMTelefonoController(JDialog pPadre){
		this.vista = new AMTelefonoView(pPadre, true);
		this.vista.getCbxTipoTelefono().setRenderer(new CbxTipoTelefonoRenderer());
		this.vista.getCbxTipoTelefono().setModel(new DefaultComboBoxModel(TipoTelefono.values()));
		this.inicializarEventos();
	}
	
	private void inicializarEventos() {
		this.vista.getBtnGuardar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
		});
		
		this.vista.getBtnCancelar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		
		for (Component componentes : this.vista.getComponentesAceptanKeyListeners()){
			if ((componentes instanceof JTextComponent)||(componentes instanceof JComboBox)){
				componentes.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER){
							guardar();
						}
						else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
							cancelar();
						}
					}
				});
			}
		}
	}
	

	public void guardar(){
		try{
			this.validar();
			if (this.modelo == null){
				this.modelo = new Telefono();
			}
			this.actualizarModelo();
			this.vista.dispose();
		}
		catch(LogicaException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void validar() throws LogicaException{
		if (this.vista.getCbxTipoTelefono().getSelectedItem()==null){
			throw new LogicaException(5,"tipo de teléfono");
		}
		if (this.vista.getTxtNumero().getText().isEmpty()){
			throw new LogicaException(2, "número de teléfono");
		}
	}

	private void actualizarModelo() {
		this.modelo.setNumero(Utiles.nuloSiVacio(this.vista.getTxtNumero().getText()));
		this.modelo.setTipo((TipoTelefono)this.vista.getCbxTipoTelefono().getSelectedItem());
		this.modelo.setCaracteristica(Utiles.nuloSiVacio(this.vista.getTxtCaracteristica().getText()));
	}

	public void cancelar(){
		this.vista.dispose();
	}
	
	public Telefono getModelo() {
		return modelo;
	}

	public void setModelo(Telefono modelo) {
		this.modelo = modelo;
		this.actualizarVista();
	}

	
	private void actualizarVista() {
		if (this.modelo == null){
			this.vista.getCbxTipoTelefono().setSelectedItem(null);
			this.vista.getTxtCaracteristica().setText("");
			this.vista.getTxtNumero().setText("");
		}
		else{
			this.vista.getCbxTipoTelefono().setSelectedItem(this.modelo.getTipo());
			this.vista.getTxtCaracteristica().setText(this.modelo.getCaracteristica());
			this.vista.getTxtNumero().setText(this.modelo.getNumero());
		}
	}
	
	public void setVisible(boolean aFlag) {
		vista.setVisible(aFlag);
	}

	public void setTitulo(String pTitulo) {
		this.vista.setTitle(pTitulo);
	}

}
