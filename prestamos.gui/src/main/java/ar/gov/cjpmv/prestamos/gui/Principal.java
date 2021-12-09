/*
 * Principal.java
 */

package ar.gov.cjpmv.prestamos.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import ar.gov.cjpmv.prestamos.gui.configuracion.controllers.AdminConfiguracion;
import ar.gov.cjpmv.prestamos.gui.contable.AdminContableView;
import ar.gov.cjpmv.prestamos.gui.controllers.Login;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.AdminPersona;
import ar.gov.cjpmv.prestamos.gui.sueldos.AdminSueldosView;
import ar.gov.cjpmv.prestamos.gui.usuarios.controllers.AdminUsuario;

/**
 * The main class of the application.
 */
public class Principal extends SingleFrameApplication {
    private PrincipalView vista;
    private String nombreUsuario;

    @Override
    protected void initialize(String[] args) {                                          
       	super.initialize(args);
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override 
    protected void startup() {
        this.vista = new PrincipalView();
        this.inicializarEventos();
        this.vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        Login locLogin= new Login(this.vista);
        locLogin.getVista();
        locLogin.setVisible(true);
        if (locLogin.isLoginOk()){
        	String nombreUsuario = "USUARIO: "+locLogin.getUsuario().getNombre();
        	this.vista.getLblNombreUsuario().setText(nombreUsuario);
        	this.nombreUsuario= locLogin.getUsuario().getNombre();;
        
        	this.setMainFrame(vista);
        	this.vista.setVisible(true);
        }
        else{
        	System.exit(0);
        }
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of Principal
     */
    public static Principal getApplication() {
        return Application.getInstance(Principal.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(Principal.class, args);
    }

    private void mostrarCreditos(){
    	AdminCredito locAdminCredito = new AdminCredito(vista);
    	locAdminCredito.show();
    }
    
    private void mostrarContable(){
    	AdminContableView locAdminContable= new AdminContableView(vista, false);
    	locAdminContable.mostrar();
    }

    private void mostrarPersonas() {
    	AdminPersona locAdminPersona = new AdminPersona(vista);
    	locAdminPersona.show();
    }
    
    private void mostrarLiquidacionHaberes(){
    	AdminSueldosView locAdminSueldosView = new AdminSueldosView(vista, false);
    	locAdminSueldosView.mostrar();
    }

	private void mostrarConfiguracion() {
		AdminConfiguracion locAdminConfiguracion= new AdminConfiguracion(vista); 
		locAdminConfiguracion.show();
		
	}
	
	private void mostrarUsuarios() {
		AdminUsuario locAdministracionUsuario= new AdminUsuario(vista, this.nombreUsuario);
		locAdministracionUsuario.show();
	}
    
    
    
    private void inicializarEventos() {
    	
    	this.vista.getXhSalir().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				cerrarAplicación();
			}
    	});
    
    	this.vista.addWindowListener(new WindowAdapter(){
    		@Override
    		public void windowClosing(WindowEvent e) {
    			cerrarAplicación();
    		}
    		
    	});
    	
        //Inicializa el click de lblCreditos
    	this.vista.addCreditosListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		mostrarCreditos();
        	}
		});
    	
    	this.vista.addPersonasListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                mostrarPersonas();
            }
        });
        
        this.vista.addConfiguracionListener(new MouseAdapter(){
        	
            @Override
            public void mouseClicked(MouseEvent arg0) {
                mostrarConfiguracion();
            }
        });
        
        //Inicializa el click de lblCreditos
    	this.vista.addUsuariosListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		mostrarUsuarios();
        	}
		});
    	
    	this.vista.addContableListener(new MouseAdapter(){
    	  	@Override
        	public void mouseClicked(MouseEvent e) {
        		mostrarContable();
        	}

		});
    	
    	this.vista.addSueldosListerner(new MouseAdapter(){
    	  	@Override
        	public void mouseClicked(MouseEvent e) {
        		mostrarLiquidacionHaberes();
        	}

		});
    	
        
    }
    
	private void cerrarAplicación() {
		String pMensaje= "¿Realmente desea salir de la aplicación?";
		String pTitulo= "Confirmación";
		int locConfirm= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirm==JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
}

