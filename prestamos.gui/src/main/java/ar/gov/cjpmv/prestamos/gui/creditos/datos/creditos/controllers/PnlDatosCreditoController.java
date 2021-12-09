package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema.TipoSistema;
import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoFormulario;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.PnlDatosCredito;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.SistemaCreditoRenderer;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CreditoModel;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlDatosCreditoController {	
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	
	private CreditoModel modeloCredito;
	private PnlDatosCredito vista;
	
	public PnlDatosCreditoController(PnlDatosCredito pVista, CreditoModel pModelo){
		this.vista = pVista;	
		this.modeloCredito = pModelo;
		this.inicializarModelos();
		this.inicializarVista();
		this.inicializarEventos();		
	}
	
	private void inicializarModelos() {
		this.vista.getCbxSistema().setModel(this.modeloCredito.getModeloSistema());
		this.vista.getCbxTipoCredito().setModel(this.modeloCredito.getModeloTipoCredito());
		this.vista.getCbxViaCobro().setModel(this.modeloCredito.getModeloViaCobro());
		this.vista.getTblCuotas().setModel(this.modeloCredito.getModeloTablaCuotas());
		this.vista.getTblConceptosTipoCredito().setModel(this.modeloCredito.getModeloConceptosCredito());
		this.vista.getTblConceptosParticulares().setModel(this.modeloCredito.getModeloConceptosParticulares());
	}

	private void inicializarEventos() {
		this.vista.getCbxTipoCredito().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarConceptos();				
			}
		});
		
		this.vista.getTblConceptosTipoCredito().getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				actualizarConceptosActivos();
			}

		});
		
		this.vista.getBtnGenerarCuotas().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generarCuotas();
			}
		});
		
		this.modeloCredito.getModeloConceptosParticulares().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				actualizarConceptosActivos();
			}
		});
		
		this.vista.getCbxTipoCredito().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chequearFormulario();
				actualizarModelo();				
			}

			
		});
		this.vista.getTblConceptosParticulares().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE){
					int filaSeleccionada = vista.getTblConceptosParticulares().getSelectedRow();
					if (filaSeleccionada!=-1){
						if (filaSeleccionada<modeloCredito.getModeloConceptosParticulares().getRowCount()-1){
							modeloCredito.getModeloConceptosParticulares().removeRow(filaSeleccionada);
						}
					}
				}
			}
		});
	}
	
	private void generarCuotas() {
		try{
			this.actualizarModelo();
			//TODO FIXME TERMINAR ACÁ LO DE CRÉDITO
			//No tira excepción porque no vale la pena, solo tiene que mostrarlo.
			if (!this.modeloCredito.isGenerarCuotasHabilitado()){
				JOptionPane.showMessageDialog(this.vista, "No se pueden generar las cuotas porque faltan datos requeridos."
										,"Error",JOptionPane.ERROR_MESSAGE);
			}
			else{
				this.modeloCredito.getCredito().setCantidadCuotas(this.vista.getSpfCantidadCuotas().getValue());
				this.modeloCredito.generarCuotas();
				this.vista.getLblMontoAPagar().setText(this.formateadorMonetario.format(this.modeloCredito.getCredito().getTotalConIntereses()));
				this.actualizarConceptos();
			}
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this.vista,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public void actualizarVista(){
		if(this.modeloCredito.getTipoFormulario()==TipoFormulario.F01){
			this.vista.getRbtnImprimirF01().setSelected(true);
			this.vista.getRbtnImprimirF02().setSelected(false);
		}
		else{
			this.vista.getRbtnImprimirF01().setSelected(false);
			this.vista.getRbtnImprimirF02().setSelected(true);
		}
		
		this.vista.getTxtInteresAnual().setText(this.modeloCredito.getCredito().getTasa().toString());
		this.vista.getCkbInteresAcumulativo().setSelected(this.modeloCredito.getCredito().isAcumulativo());

		this.vista.getTxtMontoTotal().setText(this.modeloCredito.getCredito().getMontoTotal().toString());

		this.vista.getTxtNumeroCredito().setText(Utiles.cadenaVaciaSiNulo(this.modeloCredito.getCredito().getNumeroCredito()));
		this.vista.getTxtSueldoDia().setText(Utiles.cadenaVaciaSiNulo(this.modeloCredito.getCredito().getSueldoAlDia()));
		this.vista.getDtcFechaInicio().setDate(this.modeloCredito.getCredito().getFechaInicio());

		this.vista.getSpfCantidadCuotas().setValue((Integer)Utiles.ceroSiNulo(this.modeloCredito.getCredito().getCantidadCuotas()));
		
		this.vista.getDtcFechaVencimento().setDate(this.modeloCredito.getFechaPrimerVencimiento());
		this.vista.getCbxViaCobro().setSelectedItem(this.modeloCredito.getCredito().getViaCobro());
		this.vista.getCbxSistema().setSelectedItem(this.modeloCredito.getSistema().getTipoSistema());
		
		TipoCredito tipoCredito = this.modeloCredito.getCredito().getTipoCredito();
		TipoCredito tipoCreditoSeleccionado = (TipoCredito)this.vista.getCbxTipoCredito().getSelectedItem();
		if (tipoCredito != null) {
			if (!tipoCredito.equals(tipoCreditoSeleccionado)) {
				this.vista.getCbxTipoCredito().setSelectedItem(this.modeloCredito.getCredito().getTipoCredito());
			}
		}
		else {
			this.vista.getCbxTipoCredito().setSelectedItem(null);
		}
	}
	
	public void actualizarModelo() {
		try{
			//Busco todos los datos de la interfaz
			String locNumeroCredito = Utiles.nuloSiVacio(this.vista.getTxtNumeroCredito().getText());
			String locMontoTotal = Utiles.nuloSiVacio(this.vista.getTxtMontoTotal().getText());
			String locInteresAnual = Utiles.nuloSiVacio(this.vista.getTxtInteresAnual().getText());
			locInteresAnual = locInteresAnual.replace(",", ".");
			locMontoTotal = locMontoTotal.replace(",",".");
			
			Date locFechaInicio = this.vista.getDtcFechaInicio().getDate();
			ViaCobro locViaCobro = (ViaCobro)this.vista.getCbxViaCobro().getSelectedItem();
			
			this.modeloCredito.setTipoFormulario(this.getTipoFormulario());
			
			TipoSistema tipoSistema = (TipoSistema)this.vista.getCbxSistema().getSelectedItem();
			this.modeloCredito.setTipoSistema(tipoSistema);
			Date fechaPrimerVencimiento= this.vista.getDtcFechaVencimento().getDate();
			this.modeloCredito.setFechaPrimerVencimiento(fechaPrimerVencimiento);
			
			TipoCredito locTipoCredito = (TipoCredito)this.vista.getCbxTipoCredito().getSelectedItem();
			int locCantidadCuotas = this.vista.getSpfCantidadCuotas().getValue();
		
            String locSueldoAlDia = Utiles.nuloSiVacio(this.vista.getTxtSueldoDia().getText());
			
			Credito locCredito = this.modeloCredito.getCredito();
			
			locCredito.setNumeroCredito((locNumeroCredito==null)?null:Integer.parseInt(locNumeroCredito));
			locCredito.setMontoTotal((locMontoTotal==null)?null:new BigDecimal(locMontoTotal));

			locCredito.setTasa((locInteresAnual==null)?null:new BigDecimal(locInteresAnual));
			locCredito.setAcumulativo(this.vista.getCkbInteresAcumulativo().isSelected());
			
			locCredito.setFechaInicio(locFechaInicio);
			locCredito.setViaCobro(locViaCobro);
			locCredito.setTipoCredito(locTipoCredito);
			locCredito.setCantidadCuotas(locCantidadCuotas);
		//	locCredito.setDiaVencimieto(locDiaVencimiento);
			locCredito.setSueldoAlDia((locSueldoAlDia==null)?null:new BigDecimal(locSueldoAlDia));
			locCredito.setTipoSistema(tipoSistema);
		}
		catch(LogicaException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private TipoFormulario getTipoFormulario() {
		if(this.vista.getRbtnImprimirF01().isSelected()){
			return TipoFormulario.F01;
		}
		else{
			return TipoFormulario.F02;
		}
	}

	private void inicializarVista() {
		this.vista.getCbxSistema().setRenderer(new SistemaCreditoRenderer());
		this.chequearFormulario();
	}

	public PnlDatosCreditoController(PnlDatosCredito pVista) {
		this(pVista,new CreditoModel(new Credito()));
	}
	
	
	/**
	 * Actualiza la lista de conceptos activos a partir de la tabla de conceptos
	 * 
	 */
	private void actualizarConceptosActivos() {
		this.modeloCredito.aplicarConceptos();
		String locTextMontoEntregado = "";
		BigDecimal montoEntrega = this.modeloCredito.getMontoEntrega(); 
		if (montoEntrega!=null){
			locTextMontoEntregado = this.formateadorMonetario.format(montoEntrega);	
		}
		this.vista.getLblMontoEntregado().setText(locTextMontoEntregado);
		this.vista.getLblMontoAPagar().setText(this.formateadorMonetario.format(this.modeloCredito.getCredito().getTotalConIntereses()));
	}
	/**
	 * Actualiza la lista de conceptos a partir del tipo de crédito tanto de las cuotas como del crédito
	 */
	private void actualizarConceptos() {
		TipoCredito tipoCredito = (TipoCredito) this.vista.getCbxTipoCredito().getSelectedItem();
		this.modeloCredito.getCredito().setTipoCredito(tipoCredito);
		if (tipoCredito == null){
			this.modeloCredito.getModeloConceptosCredito().getListaConceptos().clear();
		}
		else{
			this.modeloCredito.getModeloConceptosCredito().setListaConceptos(tipoCredito.getListaConceptos());
		}
		this.actualizarConceptosActivos();
	}
	
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		PnlDatosCreditoController controller = new PnlDatosCreditoController(new PnlDatosCredito());
		JDialog locDialog = new JDialog();
		locDialog.add(controller.vista);
		
		locDialog.pack();
		locDialog.setVisible(true);
	}
	
	private void chequearFormulario() {
		if(this.modeloCredito.getModeloTipoCredito().getSelectedItem()!=null){
			
			TipoCredito locTipoCredito= (TipoCredito)this.modeloCredito.getModeloTipoCredito().getSelectedItem();
			
			if(locTipoCredito.getTipoFormulario()==TipoFormulario.F01){
				this.vista.getRbtnImprimirF01().setSelected(true);
			}
			if(locTipoCredito.getTipoFormulario()==TipoFormulario.F02){
				this.vista.getRbtnImprimirF02().setSelected(true);
			}
		}
	}

	public PnlDatosCredito getVista() {
		return this.vista;
	}
}
