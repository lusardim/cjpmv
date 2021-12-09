package ar.gov.cjpmv.prestamos.gui.usuarios.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hibernate.loader.custom.Return;

import ar.gov.cjpmv.prestamos.core.business.dao.UsuarioDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.Usuario;
import ar.gov.cjpmv.prestamos.gui.usuarios.AdminUsuariosView;
import ar.gov.cjpmv.prestamos.gui.usuarios.PnlBusquedaUsuarios;
import ar.gov.cjpmv.prestamos.gui.usuarios.model.TblUsuariosModel;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class BusquedaUsuarios {
	
	private ABMUsuario abmUsuario;
	private PnlBusquedaUsuarios vista;
	private String nombreUsuario;
	private TblUsuariosModel modeloUsuario;
	private UsuarioDAO usuarioDAO;
	private List<Usuario> listaUsuario;
	private Usuario usuario;
	private AdminUsuariosView vistaPadre;
	private String usuarioLogueado;
	
	public BusquedaUsuarios(AdminUsuariosView pVistaPadre, String usuarioLogueado) {
		this.usuarioLogueado= usuarioLogueado;
		this.vistaPadre= pVistaPadre;
		this.vista= new PnlBusquedaUsuarios();
		this.inicializarEventos();
		this.inicializarVista();
	}

	private void inicializarVista() {
		this.vista.getLblEditar().setEnabled(false);
		this.vista.getLblEliminar().setEnabled(false);
		this.buscar();
	}
	
	public void actualizarModelo(){

		this.nombreUsuario=Utiles.nuloSiVacio(this.vista.getTxtUsuario().getText().trim());
		this.usuarioDAO= new UsuarioDAO();
		this.listaUsuario= this.usuarioDAO.getListaUsuarios(this.nombreUsuario);
		this.modeloUsuario= new TblUsuariosModel(this.listaUsuario);
	}
	
	



	
	
	public void actualizarVista(){
		
		
		this.vista.getTblResultadoBusqueda().setModel(this.modeloUsuario);
		this.vista.getLblImprimir().setVisible(false);
		this.vista.getLblEditar().setEnabled(false);
		this.vista.getLblEliminar().setEnabled(false);
		//this.vista.getTxtUsuario().setText(this.nombreUsuario);
		
	}
	

	private void inicializarEventos() {
		
		
		this.vista.getTblResultadoBusqueda().getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(isSelectedRow()){
					enableBtnEditarEliminar();	
				}
			}

		});

		this.vista.getBtnBuscar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();	
			}
		});
		
		this.vista.getTblResultadoBusqueda().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				actualizarVista();				
			}			
		});
		/*
		this.vista.getLblEditar().addMouseListener(new MouseListener() {
			
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
				editar();
				
			}
		});
		*/
		this.vista.getLblEliminar().addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				eliminar();
				
			}


			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		/*
		this.vista.getTblResultadoBusqueda().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (isSelectedRow() && e.getClickCount()==2){
					editar();
				}
			}
		});
		
		this.vista.getTblResultadoBusqueda().addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(isSelectedRow() && e.getKeyCode() == KeyEvent.VK_ENTER){
					editar();
				}
			}
		});*/
	
	}
	
	private boolean isSelectedRow() {
		return this.vista.getTblResultadoBusqueda().getSelectedRow()!=-1;
	}


	private void buscar() {
		this.actualizarModelo();
		this.actualizarVista();		
		this.disableBtnEditarEliminar();
	}
	
	public Usuario getSeleccionado(int seleccionado){
		Usuario locUsuario= new Usuario();
		locUsuario= this.modeloUsuario.getUsuario(seleccionado);
		return locUsuario;
	}
	
	

	/*
	
	
	private void editar() {
		int locSeleccionado= this.vista.getTblResultadoBusqueda().getSelectedRow();
		Usuario locUsuario= new Usuario();
		locUsuario= this.modeloUsuario.getUsuario(locSeleccionado);
		
		this.abmUsuario= new ABMUsuario(this.vistaPadre, this.usuarioLogueado, locUsuario);
		this.vistaPadre.setPanel(this.abmUsuario.getVista());
		this.vistaPadre.getLblTituloPnlDcha1().setText("Administración: Edición de Usuario");
		this.vistaPadre.getBtnGuardar().setVisible(true);
		this.vistaPadre.getBtnCancelar().setVisible(true);
		
		
	
	
	}

*/
	
	
	
	
	
	private void enableBtnEditarEliminar() {
		this.vista.getLblEditar().setEnabled(true);
		this.vista.getLblEliminar().setEnabled(true);
		
	}


	private void disableBtnEditarEliminar() {
		this.vista.getLblEditar().setEnabled(false);
		this.vista.getLblEliminar().setEnabled(false);
		
	}
	

	private void eliminar() {
		int locUsuarioSeleccionado= this.vista.getTblResultadoBusqueda().getSelectedRow();
		if(locUsuarioSeleccionado!=-1){
			this.usuario= new Usuario();
			this.usuario= this.modeloUsuario.getUsuario(locUsuarioSeleccionado);
			try{
				String pMensaje="¿Realmente desea eliminar el usuario "+this.usuario.getNombre()+"?";
				String pTitulo="Eliminar Usuario";
				int confirmacion= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
				if(confirmacion==JOptionPane.YES_OPTION){
					if(this.usuario.getNombre().equals("root")){
						String campo="";
						int codigo=50;
						throw new LogicaException(codigo, campo);
					}
					this.usuarioDAO.eliminar(this.usuario);
					this.buscar();
				}
			}
			catch(LogicaException e){
				String pMensaje=e.getMessage();
				String pTitulo=e.getTitulo();
				JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
				//FIXME SOLUCION REMOVING ENTITY DATACHED
				this.actualizarModelo();
			}
		}
	
		
	}
	
	public PnlBusquedaUsuarios getVista() {
		return vista;
	}
	
	
	public void show() {
        this.vista.setVisible(true);
	}
	
	
	

}
