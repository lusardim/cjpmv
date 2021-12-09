package ar.gov.cjpmv.prestamos.gui.usuarios.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.UsuarioDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.Usuario;
import ar.gov.cjpmv.prestamos.gui.configuracion.models.TblConceptosModel;
import ar.gov.cjpmv.prestamos.gui.usuarios.AdminUsuariosView;
import ar.gov.cjpmv.prestamos.gui.usuarios.PnlABMUsuarios;
import ar.gov.cjpmv.prestamos.gui.usuarios.PnlBusquedaUsuarios;
import ar.gov.cjpmv.prestamos.gui.usuarios.model.TblModulosPermitidos;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class ABMUsuario {
	private PnlABMUsuarios vista;
	private AdminUsuariosView vistaPadre;
	private Usuario usuario;
	private String usuarioLogueado;
	private String repetirContrasenia;
	private BusquedaUsuarios busquedaUsuario;
	private UsuarioDAO usuarioDAO;
	private TblModulosPermitidos modeloPermisos;

	
	public ABMUsuario(AdminUsuariosView pVistaPadre, String usuarioLogueado) {
		this(pVistaPadre, usuarioLogueado, new Usuario());
	}
	
	public ABMUsuario(AdminUsuariosView pVistaPadre, String usuarioLogueado, Usuario pUsuario) {
		this.vista= new PnlABMUsuarios();
		this.vistaPadre= pVistaPadre;
		this.usuario= pUsuario;
		this.usuarioLogueado= usuarioLogueado;
		this.actualizarVista();
		this.actualizarModelo();
		
	}
	
	public void actualizarModelo() {
		this.usuario.setNombre(Utiles.nuloSiVacio(this.vista.getTxtUsuario().getText()));
		char [] passwordChar = this.vista.getTxtPasContrasenia().getPassword();
		String locPassword = String.valueOf(passwordChar);
		this.usuario.setContrasenia((locPassword.isEmpty())?null:locPassword);
		char [] rePasswordChar = this.vista.getTxtPasRepetirContrasenia().getPassword();
		String locRePassword = String.valueOf(rePasswordChar);
		this.repetirContrasenia=(locRePassword.isEmpty())?null:locRePassword;
	}

	private void actualizarVista() {
		
	
		this.vista.getTxtUsuario().setText(Utiles.vacioSiNulo(this.usuario.getNombre()));
		if(!this.usuarioLogueado.equals("root")){
			this.vista.getTxtUsuario().setEditable(false);
			this.vista.getTblModulosPermisos().setEnabled(false);
		}
		else{
			if((this.usuario.getId()!=null)&&(this.usuario.getNombre().equals("root"))){
				this.vista.getTxtUsuario().setEditable(false);
				this.vista.getTblModulosPermisos().setEnabled(false);
			}
			
		}

		
	
		
		this.vista.getTxtPasContrasenia().setText(Utiles.vacioSiNulo(this.usuario.getContrasenia()));
		this.vista.getTxtPasRepetirContrasenia().setText(Utiles.vacioSiNulo(this.usuario.getContrasenia()));
	
		this.modeloPermisos= new TblModulosPermitidos(this.usuario);
		this.vista.getTblModulosPermisos().setModel(this.modeloPermisos);
		
	}
	
	public void validarUsuario() throws LogicaException{
		
			this.actualizarModelo();
				
			
				if((this.usuario.getContrasenia()==null)||(this.repetirContrasenia==null)){
					int codigo=2;
					String campo="contraseña";
					throw new LogicaException(codigo, campo);
				}
				if(!this.usuario.getContrasenia().equals(this.repetirContrasenia)){
					int locCodigo=52;
					String locCampo="";
					throw new LogicaException(locCodigo, locCampo);
				}
				this.usuarioDAO= new UsuarioDAO();
				if(this.usuario.getId()==null){
					this.usuarioDAO.agregar(this.usuario);
				}
				else{
					this.usuarioDAO.modificar(this.usuario);
				}
		
			
		
	}
	

	

	
	

	
	
	public void show(){
		this.vista.setVisible(true);
	}
	
	
	public PnlABMUsuarios getVista(){
		return this.vista;
	}
	

}
