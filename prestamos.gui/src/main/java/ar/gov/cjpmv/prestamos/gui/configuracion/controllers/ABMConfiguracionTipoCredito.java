package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.ConceptoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.TipoCreditoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoFormulario;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.gui.configuracion.ABMConfiguracionSetearPnl;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlTipoCredito;
import ar.gov.cjpmv.prestamos.gui.configuracion.models.TblConceptosModel;
import ar.gov.cjpmv.prestamos.gui.personas.model.TblEmpleosModel;

public class ABMConfiguracionTipoCredito {
	
	private ABMConfiguracionSetearPnl vista;
	private PnlTipoCredito pnlTipoCredito;
	
	private TipoCredito tipoCredito;
	private TipoCreditoDAO tipoCreditoDAO;
	
	private ConceptoDAO conceptoDAO;
	
	private TblConceptosModel modeloTablaConcepto;

	
	
	
	public ABMConfiguracionTipoCredito(JDialog pPadre, TipoCreditoDAO pTipoCreditoDAO){
		this(pPadre, pTipoCreditoDAO, new TipoCredito());
	}
	
	public ABMConfiguracionTipoCredito(JDialog pPadre, TipoCreditoDAO pTipoCreditoDAO, TipoCredito pTipoCredito){
		this.tipoCredito= pTipoCredito;
		this.tipoCreditoDAO= pTipoCreditoDAO;
		vista= new ABMConfiguracionSetearPnl(pPadre,true);
		pnlTipoCredito= new PnlTipoCredito();
		this.vista.setPanel(pnlTipoCredito);
		conceptoDAO = new ConceptoDAO();
		this.inicializarEventos();
		this.actualizarVista();
	}

	private void actualizarVista() {
		this.pnlTipoCredito.getTxtNombre().setText(this.tipoCredito.getNombre());
		this.pnlTipoCredito.getCbxFormulario().setModel(new DefaultComboBoxModel(TipoFormulario.values()));
		this.pnlTipoCredito.getCbxFormulario().setSelectedItem(this.tipoCredito.getTipoFormulario());
		
		this.modeloTablaConcepto= new TblConceptosModel(this.tipoCredito);
		this.pnlTipoCredito.getTbConcepto().setModel(this.modeloTablaConcepto);
	}

	
	
	private void actualizarModelo() {
		String aux= this.pnlTipoCredito.getTxtNombre().getText().trim();
		if(aux.isEmpty()){
			aux=null;
		}
		this.tipoCredito.setNombre(aux);
		this.tipoCredito.setTipoFormulario((TipoFormulario)this.pnlTipoCredito.getCbxFormulario().getSelectedItem());
		//this.tipoCredito.setListaConceptos(this.modeloTablaConcepto.getLista)
		
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
		
		
	}

	private void guardar(){
		try{
			this.actualizarModelo();
			if(this.tipoCredito.getId()==null){
				this.tipoCreditoDAO.agregar(tipoCredito);
			}
			else{
				this.tipoCreditoDAO.modificar(tipoCredito);
			}
			this.vista.dispose();
		}
		catch(LogicaException e){
			String pTitulo=e.getTitulo();
			String pMensaje=e.getMessage();
			JOptionPane.showMessageDialog(this.vista , pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE);
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
	
	public void setTitulo(String pTitulo) {
		this.vista.setTitle(pTitulo);
	}

	public void setVisible(boolean pVisible){
		this.vista.setVisible(pVisible);
	}

	public void setLocationRelativeTo(JComponent pPadre) {
		this.vista.setLocationRelativeTo(pPadre);
	}


}
