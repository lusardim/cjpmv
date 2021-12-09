package ar.gov.cjpmv.prestamos.gui.controllers;

import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.dao.UsuarioDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.Usuario;
import ar.gov.cjpmv.prestamos.gui.LoginView;

public class Login {
	
	private LoginView vista;
	
	private String nombreUsuario;
	private String password;
	private UsuarioDAO usuarioDAO;
	private boolean loginOk;
	private Usuario usuario;
	

	public Login(JFrame pPadre) {
		super();
		vista= new LoginView(pPadre, true);
		this.inicializarEventos();
		this.inicializarVista();
		cargarPersistencia();
	}

	private void cargarPersistencia() {
		final String textoBotonIngresar = vista.getBtnIngresar().getText();
		vista.getBtnIngresar().setEnabled(false);
		vista.getBtnIngresar().setText("Cargando...");
		SwingWorker<Void,Void> swingWorker = new SwingWorker<Void,Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				usuarioDAO = new UsuarioDAO();
				return null;
			}
			
			@Override
			protected void done() {
				vista.getBtnIngresar().setText(textoBotonIngresar);
				vista.getBtnIngresar().setEnabled(true);
				super.done();
			}
		};
		swingWorker.execute();
	}

	private void inicializarEventos() {
		this.vista.getBtnIngresar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				validarUsuarioContrasenia();
			}
		});
		KeyListener locEnter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (usuarioDAO != null) {
						validarUsuarioContrasenia();
					}
					else {
						JOptionPane.showMessageDialog(vista,
								"Por favor espere unos segundos",
								"Espera", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		};

		this.vista.getTxtUsuario().addKeyListener(locEnter);
		this.vista.getPasContrasenia().addKeyListener(locEnter);
	}

	private void inicializarVista() {
		this.actualizarModelo();
		this.actualizarVista();		
	}


	private void actualizarVista() {
		this.vista.getLblMensaje().setText(null);
		this.vista.getTxtUsuario().setText(this.nombreUsuario);
		this.vista.getPasContrasenia().setText(null);	
	}

	private void actualizarModelo() {
		String locNombreUsuario= this.vista.getTxtUsuario().getText().trim();
		this.nombreUsuario=(locNombreUsuario.isEmpty())?null:locNombreUsuario;
		char [] passwordChar = this.vista.getPasContrasenia().getPassword();
		String passwordCompleto = String.valueOf(passwordChar);
		this.password= (passwordCompleto.isEmpty())?null:passwordCompleto;
	}

	public void validarUsuarioContrasenia(){
		try{
			this.actualizarModelo();
			this.usuario=this.usuarioDAO.validarUsuarioContrasenia(this.nombreUsuario,this.password);
			this.vista.dispose();
			this.loginOk=true;
		}
		catch(LogicaException e){
			String pMensaje=e.getMessage();
			this.actualizarVista();
			this.vista.getLblMensaje().setText(pMensaje);
		}
		catch(Exception e){
			String locTitulo="Error";
			String locMensaje="El sistema no ha podido establecer conexión con la base de datos.\nConsulte con el administrador del sistema.";
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,locMensaje,locTitulo, JOptionPane.ERROR_MESSAGE);	
		}
	}
	
	
	


	public Usuario getUsuario() {
		return usuario;
	}



	public boolean isLoginOk() {
		return loginOk;
	}



	public void setLoginOk(boolean loginOk) {
		this.loginOk = loginOk;
	}



	public LoginView getVista(){
		return this.vista;
	}

	public void setVisible(boolean pValor){
		this.vista.setVisible(pValor);
	}
	
	
	public void setLocationRelativeTo(JComponent pPadre){
		this.vista.setLocationRelativeTo(pPadre);
	}

	public static void main(String[]args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		JFrame frame= new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login controlador= new Login(frame);
		controlador.vista.setLocationRelativeTo(frame);
		controlador.vista.setVisible(true);	
	}


}
