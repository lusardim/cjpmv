package ar.gov.cjpmv.prestamos.gui.creditos.garantias;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.dao.GarantiaPersonalDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.AdminCreditoView;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CuotasCellRenderer;

public class AfectarDesafectarGaranteControllers {


	private AfectarDesafectarGarante vista;
	private TblGarantesModel modeloGarantes;
	private Credito locCredito;
	private ResultadoGarantia locResultadoGarantia;
	private NumberFormat formateadorMonetario= NumberFormat.getCurrencyInstance();
	private List<ViaCobro> listaViaCobro;
	

	
	
	public AfectarDesafectarGaranteControllers(AdminCreditoView vistaPadre, ResultadoGarantia pResultadoGarantia) {
		this.vista= new AfectarDesafectarGarante(vistaPadre, true);
		this.locResultadoGarantia= pResultadoGarantia;
		this.locCredito= pResultadoGarantia.getCredito();
		this.inicializarModelo();
		this.inicializarEventos();
		this.inicializarVista();		
	}
	

	private void inicializarModelo() {
		CreditoDAOImpl creditoDAO= new CreditoDAOImpl();
		this.listaViaCobro= creditoDAO.getListaViasCobro();
	}


	private void inicializarEventos() {
		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		
		this.vista.getBtnGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});

		
		
	}


	private void inicializarVista() {
	
		Persona locPersona= this.locCredito.getCuentaCorriente().getPersona();
		PersonaFisica locPersonaFisica= new PersonaFisica();
		PersonaJuridica locPersonaJuridica= new PersonaJuridica();
		if(locPersona instanceof PersonaFisica){
			locPersonaFisica= (PersonaFisica)locPersona;
			if(locPersonaFisica.getLegajo()!=null){
				this.vista.getLblLegajo().setText(locPersonaFisica.getLegajo().toString());
			}
			else{
				this.vista.getLblLegajo().setText("");
			}
			if(locPersona.getCui()!=null){
				this.vista.getLblCuil().setText(locPersona.getCui().toString());
			}
			else{
				this.vista.getLblCuil().setText("");
			}
			if(locPersonaFisica.getApellido()!=null){
				this.vista.getLblApellido().setText(locPersonaFisica.getApellido().toString());
			}
			else{
				this.vista.getLblApellido().setText("");
				
			}
			if(locPersona.getNombre()!=null){
				this.vista.getLblNombres().setText(locPersona.getNombre().toString());
			}
			else{
				this.vista.getLblNombres().setText("");
			}
			
			
			this.vista.getLblNumeroCredito().setText(this.locCredito.getNumeroCredito().toString());
			this.vista.getLblCuotasVencidas().setText(this.locResultadoGarantia.getCantidadCuotasVencidas().toString());
			this.vista.getLblMontoAdeudado().setText(formateadorMonetario.format(this.locResultadoGarantia.getMontoAdeudado()));
			this.vista.getLblMontoTotal().setText(formateadorMonetario.format(this.locCredito.getMontoTotal()));
		}
		else{
			locPersonaJuridica=(PersonaJuridica)locPersona; 
			this.vista.getLblLegajo().setText("");
			this.vista.getLblCuil().setText(locPersona.getCui().toString());
			this.vista.getLblApellido().setText("");
			this.vista.getLblNombres().setText(locPersona.getNombre().toString());
			this.vista.getLblNumeroCredito().setText(this.locCredito.getNumeroCredito().toString());
			this.vista.getLblCuotasVencidas().setText(this.locResultadoGarantia.getCantidadCuotasVencidas().toString());
			this.vista.getLblMontoAdeudado().setText(formateadorMonetario.format(this.locResultadoGarantia.getMontoAdeudado()));
			this.vista.getLblMontoTotal().setText(formateadorMonetario.format(this.locCredito.getMontoTotal()));
		}

		List<GarantiaPersonal> listaGarantiaPersonal= new ArrayList<GarantiaPersonal>();
		for(Garantia cadaGarantia: locCredito.getListaGarantias()){
			listaGarantiaPersonal.add((GarantiaPersonal)cadaGarantia);
		}
		this.modeloGarantes= new TblGarantesModel(listaGarantiaPersonal);
		this.vista.getTblGarantes().setModel(this.modeloGarantes);
		this.vista.getTblGarantes().setDefaultRenderer(BigDecimal.class, new TblCellRendererFloat());
		this.vista.getTblGarantes().setDefaultRenderer(Float.class, new TblCellRendererFloat());
		int vColIndex = 4;
		TableColumn col = this.vista.getTblGarantes().getColumnModel().getColumn(vColIndex);
		col.setCellEditor(new TblCellEditorViaCobro(this.listaViaCobro));
		col.setCellRenderer(new TblCellRendererViaCobro(this.listaViaCobro));
	
	}

	private void guardar() {
		try {
			BigDecimal acumulativoPorcentaje = new BigDecimal(0);
			boolean banAfectarGarante= false;
			List<Garantia> listaGarantia= new ArrayList<Garantia>();
			
			for(GarantiaPersonal cadaGarantia:this.modeloGarantes.getListaGarantiaPersonal()){
				if(cadaGarantia.getAfectar()){
					banAfectarGarante= true;
					acumulativoPorcentaje = acumulativoPorcentaje.add(cadaGarantia.getPorcentaje());
					if((cadaGarantia.getViaCobro()==null)||(cadaGarantia.getPorcentaje().equals(0F))){
						int codigo= 78;
						String campo="cuil: "+cadaGarantia.getGarante().getCui()+" - "+cadaGarantia.getGarante().getNombreYApellido();
						throw new LogicaException(codigo, campo);
					}
					if(cadaGarantia.getAfectarA()==null){
						CreditoDAOImpl locCreditDAO= new CreditoDAOImpl();
						if(locCreditDAO.getCuentaCorrienteGarantia(cadaGarantia.getGarante())==null){
							locCreditDAO.generarCuentaCorriente(cadaGarantia.getGarante());
						}
						CuentaCorriente locCuentaCorriente= locCreditDAO.getCuentaCorriente(cadaGarantia.getGarante());
						cadaGarantia.setAfectarA(locCuentaCorriente);	
					}				
					this.locCredito.getCuentaCorriente().getGarantiasEnCurso().add(cadaGarantia);					
				}	
				else{
					this.locCredito.getCuentaCorriente().getGarantiasEnCurso().remove(cadaGarantia);					
				}
				listaGarantia.add(cadaGarantia);
			}
			if((banAfectarGarante)&&(acumulativoPorcentaje.floatValue()>100F)){
				int codigo= 79;
				String campo="";
				throw new LogicaException(codigo, campo);
			}
			else{
				String mensaje="¿Realmente desea guardar los cambios efectuados?";
				int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
				if(locConfirmacion==JOptionPane.YES_OPTION){
					this.locCredito.setCobrarAGarante(banAfectarGarante);
					this.locCredito.setListaGarantias(listaGarantia);
					CreditoDAOImpl creditoDAO= new CreditoDAOImpl();
					creditoDAO.modificar(this.locCredito);
					this.vista.dispose();
				}
			}
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
	
	
	
	
	private void show() {
		this.vista.pack();
		this.vista.setVisible(true);
	}


	public void setVisible(boolean pVisible){
		this.vista.setVisible(pVisible);
	}

	public void setTitulo(String pTitulo) {
		this.vista.setTitle(pTitulo);
	}

	public void setLocationRelativeTo(AdminCreditoView adminCreditoView) {
		this.vista.setLocationRelativeTo(adminCreditoView);
	}


	
}



