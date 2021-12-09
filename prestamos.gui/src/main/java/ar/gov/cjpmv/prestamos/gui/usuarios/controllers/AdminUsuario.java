package ar.gov.cjpmv.prestamos.gui.usuarios.controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import ar.gov.cjpmv.prestamos.core.business.dao.UsuarioDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.Usuario;
import ar.gov.cjpmv.prestamos.gui.Principal;
import ar.gov.cjpmv.prestamos.gui.PrincipalView;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.BusquedaPersonas;
import ar.gov.cjpmv.prestamos.gui.usuarios.AdminUsuariosView;



public class AdminUsuario {
	
    private AdminUsuariosView vista;
    private String nombreUsuario;
    private BusquedaUsuarios busquedaUsuario;
    private ABMUsuario abmUsuario;
    
    
 
    
    public AdminUsuario(PrincipalView pPadre, String pNombreUsuario){
        this.vista = new AdminUsuariosView(pPadre, false);
        
        this.nombreUsuario= pNombreUsuario;
        this.inicializarVista();
        this.inicializarEventos();
    }



	private void inicializarEventos() {
		this.vista.getXhEditarUsuario().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		
	
		this.vista.getXhNuevoUsuario().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				nuevo();
			}
		});
		
		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
				
			}
		});
		
		this.vista.getBtnGuardar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guardar();
				
			}
		});
	
		
	}
	
	
	public void refrescarEventos(){

		this.busquedaUsuario.getVista().getLblEditar().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				edicionDirecto();
				
			}
		});
		
	}

	
	
	public void editar(){
		this.inicializarVista();
	}

	
	private void nuevo() {
		this.abmUsuario= new ABMUsuario(this.vista,this.nombreUsuario);
		this.vista.setPanel(this.abmUsuario.getVista());
		this.vista.getBtnGuardar().setVisible(true);
		this.vista.getBtnCancelar().setVisible(true);
		this.vista.getLblTituloPnlDcha1().setText("Administración: Nuevo Usuario");
	}
	
	
	
	private void edicionDirecto(){
		int locSeleccionado= this.busquedaUsuario.getVista().getTblResultadoBusqueda().getSelectedRow();
		Usuario locUsuario= new Usuario();
		locUsuario= this.busquedaUsuario.getSeleccionado(locSeleccionado);
		this.abmUsuario= new ABMUsuario(this.vista, this.nombreUsuario, locUsuario);
		this.vista.setPanel(this.abmUsuario.getVista());
		this.vista.getBtnGuardar().setVisible(true);
		this.vista.getBtnCancelar().setVisible(true);
		this.vista.getLblTituloPnlDcha1().setText("Administración: Edición Usuario");
	}
	

	
	
	private void cancelar(){
		
		String locMensaje="¿Realmente desea cancelar la operación?";
		String locTitulo="Confirmación";
		int confirmacion=JOptionPane.showConfirmDialog(this.vista,locMensaje, locTitulo, JOptionPane.YES_OPTION);
		if(confirmacion==JOptionPane.YES_OPTION){
			inicializarVista();
		}
		
	}
	
	private void guardar(){
		
		try{	
			this.abmUsuario.validarUsuario();
			String titulo="Datos de Usuario";
			String mensaje="Los datos del usuario se han actualizado correctamente.";
			JOptionPane.showMessageDialog(this.vista, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
			this.inicializarVista();
		}
		catch(LogicaException e){
			String mensaje= e.getMessage();
			String titulo= e.getTitulo();
			JOptionPane.showMessageDialog(this.vista, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	
	
	

	private void inicializarVista() {	
		this.vista.getXhCambiarContrasenia().setVisible(false);
		if(this.nombreUsuario.equals("root")){
			this.busquedaUsuario = new BusquedaUsuarios(this.vista, this.nombreUsuario);
			this.vista.setPanel(this.busquedaUsuario.getVista());
			this.vista.getBtnGuardar().setVisible(false);
			this.vista.getBtnCancelar().setVisible(false);
			this.refrescarEventos();
		
		}
		else{
			this.vista.getXhCambiarContrasenia().setVisible(false);
			this.vista.getXhNuevoUsuario().setVisible(false);
			UsuarioDAO locUsuarioDAO = new UsuarioDAO();
			Usuario locUsuario= new Usuario();
			locUsuario= locUsuarioDAO.getUsuario(this.nombreUsuario);
			this.abmUsuario= new ABMUsuario(this.vista, this.nombreUsuario, locUsuario);
			this.vista.setPanel(this.abmUsuario.getVista());
			this.vista.getLblTituloPnlDcha1().setText("Administración: Edición de Usuario");
		}
	}
	
	
	
	
	
	public static void main(String[] args){
		String locUsuario= "usuario";
		AdminUsuario locAdminUsuario = new AdminUsuario(new PrincipalView(), locUsuario);
		locAdminUsuario.vista.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
			
				System.exit(0);
			}
		});
		locAdminUsuario.show();
	}



	public void show() {
		Dimension pantalla=Toolkit.getDefaultToolkit().getScreenSize();
		pantalla.height-=30;
        this.vista.setPreferredSize(pantalla);
        this.vista.pack();
        this.vista.setVisible(true);
	}
	


}
