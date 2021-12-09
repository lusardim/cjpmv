package ar.gov.cjpmv.prestamos.gui.creditos.viaCobro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javassist.bytecode.LineNumberAttribute.Pc;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.dom4j.tree.DefaultAttribute;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.AdminCreditoView;
import ar.gov.cjpmv.prestamos.gui.creditos.garantias.AfectarDesafectarGarante;
import ar.gov.cjpmv.prestamos.gui.creditos.garantias.ResultadoGarantia;

public class ModificacionViaCobroControllers {
	private ModificaconViaCobro vista;
	private AdminCreditoView vistaPadre;
	private Credito locCredito;
	private ComboBoxModel modeloViaCobro;
	private CreditoDAOImpl creditoDAO;
	
	public ModificacionViaCobroControllers(AdminCreditoView vistaPadre, Credito creditoSeleccionado){
		this.vista= new ModificaconViaCobro(vistaPadre, true);
		this.vistaPadre= vistaPadre;
		this.locCredito= creditoSeleccionado;
		this.creditoDAO= new CreditoDAOImpl();
		this.inicializarModelo();
		this.inicializarVista();
		this.inicializarEventos();
	}



	private void inicializarEventos() {
		this.vista.getBtnAceptar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
		});
		
		this.vista.getBtnACancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cancelar();
			}
		});
		
	}

	private void inicializarVista() {
		this.vista.getCbxViaCobro().setModel(this.modeloViaCobro);
		this.vista.getCbxViaCobro().setSelectedItem(this.locCredito.getViaCobro());
		
	}

	private void inicializarModelo() {
		this.creditoDAO.getListaViasCobro();
		this.modeloViaCobro= new DefaultComboBoxModel(this.creditoDAO.getListaViasCobro().toArray());
	
		
	}
	
	private void guardar() {
		try{
			String mensaje="¿Realmente desea guardar los cambios efectuados?";
			int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
			if(locConfirmacion==JOptionPane.YES_OPTION){
				this.locCredito.setViaCobro((ViaCobro) this.vista.getCbxViaCobro().getSelectedItem());
				this.creditoDAO.modificar(this.locCredito);
				this.vista.dispose();
			}
		}
		catch(LogicaException e){
		
			String pMensaje="Ha ocurrido un error al intentar editar la vía de cobro.\nConsulte con el administrador del sistema.";
			JOptionPane.showMessageDialog(this.vista , pMensaje, "Error", JOptionPane.ERROR_MESSAGE);
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
	
	
	
	
	private void show() {
		this.vista.pack();
		this.vista.setVisible(true);
	}


	public void setVisible(boolean pVisible){
		this.vista.setVisible(pVisible);
	}



	public void setLocationRelativeTo(AdminCreditoView adminCreditoView) {
		this.vista.setLocationRelativeTo(adminCreditoView);
	}



	public void setTitulo(String pTitulo) {
		this.vista.setTitle(pTitulo);
		
	}

	
	
	
	
	
	
}
