package ar.gov.cjpmv.prestamos.gui.configuracion.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.ConceptoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.gui.configuracion.ABMConfiguracionSetearPnl;
import ar.gov.cjpmv.prestamos.gui.configuracion.PnlConcepto;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class ABMConfiguracionConcepto {
	
	private ABMConfiguracionSetearPnl vista;
	private PnlConcepto pnlConcepto;
	
	private Concepto concepto;
	private ConceptoDAO conceptoDAO;
	private NumberFormat formateador;

	
	public ABMConfiguracionConcepto(JDialog pPadre, ConceptoDAO pConceptoDAO){
		this(pPadre, pConceptoDAO, new Concepto());
	}
	
	public ABMConfiguracionConcepto(JDialog pPadre, ConceptoDAO pConceptooDAO, Concepto pConcepto){
		this.concepto= pConcepto;
		this.conceptoDAO= pConceptooDAO;
		vista= new ABMConfiguracionSetearPnl(pPadre,true);
		pnlConcepto= new PnlConcepto();
		this.vista.setPanel(pnlConcepto);
		this.formateador= NumberFormat.getInstance();
		formateador.setMaximumFractionDigits(2);
		this.inicializarEventos();
		this.actualizarVista();
	
	}

	private void actualizarVista() {
		if(this.concepto.getId()==null){
			this.pnlConcepto.getRbtnPorcentual().setSelected(true);
			this.pnlConcepto.getRbtnFijoPesos().setSelected(false);
		}
		else{
			if(this.concepto.getPorcentual()){
				this.pnlConcepto.getRbtnPorcentual().setSelected(true);
				this.pnlConcepto.getRbtnFijoPesos().setSelected(false);
			}
			else{
				this.pnlConcepto.getRbtnPorcentual().setSelected(false);
				this.pnlConcepto.getRbtnFijoPesos().setSelected(true);
			}
		}
		this.pnlConcepto.getTxtDescripcion().setText(this.concepto.getDescripcion());
		this.pnlConcepto.getTxtValor().setText(Utiles.cadenaVaciaSiNulo(this.concepto.getValor()));
		this.pnlConcepto.getCkbEmiteCheque().setSelected(this.concepto.getEmiteCheque());
		this.pnlConcepto.getCkbAplicadoCuotas().setSelected(this.concepto.getAplicadoCuota());
	}
	
	
	private void actualizarModelo() throws LogicaException{
		String aux= this.pnlConcepto.getTxtDescripcion().getText().trim();
		if(aux.isEmpty()){
			aux=null;
		}
		this.concepto.setDescripcion(aux);
		
		String locValor= this.pnlConcepto.getTxtValor().getText();
		locValor= locValor.replace(',','.');
		try{
			this.concepto.setValor(new BigDecimal(locValor));
		}
		catch (NumberFormatException e) {
			int locCodigoMensaje= 17;
			String locCampoMensaje="El campo valor";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		
		this.concepto.setAplicadoCuota(this.pnlConcepto.getCkbAplicadoCuotas().isSelected());
		this.concepto.setEmiteCheque(this.pnlConcepto.getCkbEmiteCheque().isSelected());
		this.concepto.setPorcentual(this.pnlConcepto.getRbtnPorcentual().isSelected());

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
	

	private void guardar() {
		try{
			this.actualizarModelo();
			if(this.concepto.getId()==null){
				this.conceptoDAO.agregar(concepto);
			}
			else{
				this.conceptoDAO.modificar(concepto);
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
