package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import ar.gov.cjpmv.prestamos.gui.configuracion.AdminConfiguracionView;

public class AdminConfiguracion {
	
	private AdminConfiguracionView vista;
			
	public AdminConfiguracion(JFrame pPadre){
		this.vista=new AdminConfiguracionView(pPadre, false);
		this.mostrarPnlRegistracion();
		this.inicializarEventos();
	}

	private void inicializarEventos() {
		this.vista.getTreeAdminConfiguracion().addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e) {
				TreePath paths = e.getNewLeadSelectionPath();
				mostrarPanelDerecha(paths);	
			}
		});
	}
	
	
	private void mostrarPanelDerecha(TreePath pPaths) {
		String clave = pPaths.getPath()[pPaths.getPath().length-1].toString();

		if(clave.equals("Tipo de Documento")){
			this.mostrarPnlTipoDocumento();
		}
		else if(clave.equals("Provincia")) {
			mostrarPnlProvincia();
		}	
		else if(clave.equals("Categoría")){
			//FIXME SACADO PANEL DE CATEGORIA
	//		mostrarPnlCategoria();
		}
		else if (clave.equals("Departamento")){
			mostrarPnlDepartamento();
		}
		else if(clave.equals("Localidad")) {
			mostrarPnlLocalidad();
		}
		else if(clave.equals("Entidad Bancaria")){
			mostrarPnlEntidadBancaria();
		}	
		else if(clave.equals("Cuenta Bancaria")){
			mostrarPnlCuentaBancaria();
		}
		else if(clave.equals("Registración")){
			this.mostrarPnlRegistracion();	
		}
		else if(clave.equals("Opciones Básicas")){
			this.mostrarPnlOpcionesBasicas();	
		}
		else if(clave.equals("Tipo de Crédito")){
			this.mostrarPnlTipoCredito();	
		}		
		else if(clave.equals("Concepto")){
			this.mostrarPnlConcepto();	
		}	
	}	


	private void mostrarPnlConcepto() {
		AdminConcepto controlador=new AdminConcepto();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}

	private void mostrarPnlTipoCredito() {
		AdminTipoCredito controlador=new AdminTipoCredito();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
		
	}

	private void mostrarPnlOpcionesBasicas() {
		AdminOpcionesBasicas controlador=new AdminOpcionesBasicas();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}

	private void mostrarPnlCuentaBancaria() {
		AdminCuentaBancaria controlador= new AdminCuentaBancaria();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}

	private void mostrarPnlEntidadBancaria() {
		AdminEntidadBancaria controlador= new AdminEntidadBancaria();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}

	public void mostrarPnlRegistracion() {
		AdminRegistracion controlador= new AdminRegistracion();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}

	public void mostrarPnlLocalidad() {
		AdminLocalidad controlador= new AdminLocalidad();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}


	public void mostrarPnlDepartamento() {
		AdminDepartamento controlador= new AdminDepartamento();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}

/*	
 * FIXME SACADO PANEL DE CATEGORIA DEL MAPA
 * public void mostrarPnlCategoria() {
		AdminCategoria controlador= new AdminCategoria();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}
*/
	public void mostrarPnlProvincia() {
		AdminProvincia controlador= new AdminProvincia();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}


	public void mostrarPnlTipoDocumento() {
		AdminTipoDocumento controlador= new AdminTipoDocumento();
		this.vista.getSllpDchaAdminConfiguracion().setViewportView(controlador.getVista());
		this.vista.pack();
	}

	public void show() {
		Dimension pantalla=Toolkit.getDefaultToolkit().getScreenSize();
		pantalla.height-=30;
        this.vista.setPreferredSize(pantalla);
        this.vista.pack();
        this.vista.setVisible(true);
	}
	

	
	
	public void setLocationRelativeTo(JComponent pPadre){
		this.vista.setLocationRelativeTo(pPadre);
	}
	
	public static void main(String[] args){
		AdminConfiguracion locAdmin = new AdminConfiguracion(new JFrame());
		locAdmin.vista.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
			
				System.exit(0);
			}
		});
		locAdmin.show();
	}
	
	



}
