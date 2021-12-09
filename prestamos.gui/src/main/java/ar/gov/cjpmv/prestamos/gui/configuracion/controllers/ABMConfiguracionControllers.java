package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.gui.comunes.interfaces.AMModel;
import ar.gov.cjpmv.prestamos.gui.configuracion.ABMConfiguracion;

public class ABMConfiguracionControllers {
	
	//Atributo de la vista
	private ABMConfiguracion vista;
	private AMModel modelo;
	

	public ABMConfiguracionControllers(JDialog pPadre){
		vista= new ABMConfiguracion(pPadre,true);
		this.inicializarEventos();
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
		this.vista.getTxtDescripcion().addKeyListener(new KeyAdapter() {
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
	
	private void actualizarVista() {
		this.vista.getTxtDescripcion().setText(this.modelo.getDescripcion());
	}
	
	
	private void actualizarModelo() {
		String aux= this.vista.getTxtDescripcion().getText().trim();
		//Si ingresan un string con espacios o el campo es vacio se le asigna el valor null, y luego lo maneja la excepcion
		if(aux.isEmpty()){
			aux=null;
		}
		this.modelo.setDescripcion(aux);
	}
	
	
	private void guardar() {
		try{
			this.actualizarModelo();
			this.modelo.guardar();
			this.vista.dispose();
		}
		catch(LogicaException e){
			String pTitulo=e.getTitulo();
			String pMensaje=e.getMessage();
			
			//En este caso no se instancia un objeto del tipo JOptionPane para el metodo showMessageDialog porque este ultimo es un metodo estatico o de clase
			JOptionPane.showMessageDialog(this.vista , pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cancelar() {
		String pMensaje= "¿Realmente desea cancelar la operación?";
		String pTitulo= "Confirmación";
		//TODO CONVERTIR ESTE METODO EN GENERICO
		int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.vista.dispose();	
		}
		
	}
	
	
	public void setModelo(AMModel pModelo){
		this.modelo= pModelo;
		this.actualizarVista();
	}
	
	public void setVisible(boolean pVisible){
		this.vista.setVisible(pVisible);
	}
	
	public void setLocationRelativeTo(JComponent pPadre) {
		this.vista.setLocationRelativeTo(pPadre);
	}

	public void setTitulo(String pTitulo) {
		this.vista.setTitle(pTitulo);	
	}

	public void setLabel(String pLabel){
		this.vista.setLblDescripcion(pLabel);
	}
}
