package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.dao.GarantiaPersonalDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;
import ar.gov.cjpmv.prestamos.gui.creditos.EstadoCuenta;
import ar.gov.cjpmv.prestamos.gui.creditos.model.TblCreditosModel;
import ar.gov.cjpmv.prestamos.gui.creditos.model.TblGarantiasModel;
import ar.gov.cjpmv.prestamos.gui.reportes.EstadoCuentaReporte;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

public class EstadoCuentaController {
	
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	
	private EstadoCuenta vista;
	
	private Persona persona;
	private CuentaCorriente cuentaCorriente;
	private CreditoDAOImpl creditosDAO;
	private GarantiaPersonalDAO garantiaPersonalDAO;
	private TblCreditosModel modelCreditos;
	private TblGarantiasModel modelGarantias;
	private List<Credito> listaCreditos;
	private List<Credito> listaGarantias;
	private boolean mostrarVista;
	
	public EstadoCuentaController(Persona pPersona, JDialog pPadre) throws LogicaException{
	
		vista= new EstadoCuenta(pPadre, true);

		this.persona= pPersona;
		this.creditosDAO= new CreditoDAOImpl();
		this.cuentaCorriente= this.creditosDAO.getCuentaCorriente(pPersona);
		this.garantiaPersonalDAO= new GarantiaPersonalDAO();
		modelCreditos = new TblCreditosModel();
		
		this.inicializarEventos();
		this.actualizarModelo();
		this.actualizarVista();
		
	}

	private void inicializarEventos() {
		this.vista.getTblCreditosEnCurso().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0){
				if(isSelectedRowCreditos()){
					enableBtnDetalleCuotas();
				}
			}
		});
		
		this.vista.getTblGarantiasEnCurso().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0){
				if(isSelectedRowGarantias()){
					enableBtnDetalleGarantias();
				}
			}
		});
		
		this.vista.getBtnImprimir().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				imprimirEstadoCuenta();
				
			}

		});
		
		
		this.vista.getXhDetalleCuotasCreditos().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				detalleCuotasCreditos();
				
			}
		});
		
		this.vista.getXhDetalleCuotasGarantias().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				detalleCuotasGarantias();
				
			}
		});
		
		this.vista.getXhCancelarCredito().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelarSeleccionado();				
			}
		});
		
	}
	
	private void cancelarSeleccionado() {
		try{
			int seleccionado = this.getVista().getTblCreditosEnCurso().getSelectedRow();
			Credito locCredito = this.modelCreditos.getCredito(seleccionado);	
			int respuesta = JOptionPane.showConfirmDialog(vista,
					"¿Está seguro que desea eliminar el crédito: "+
					locCredito+"?", 
					"Confirmacion",
					JOptionPane.YES_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				this.creditosDAO.eliminar(locCredito);
				this.actualizarModelo();
				this.disableBtnDetalleCuotas();
			}
		}
		catch(LogicaException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(vista,
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void disableBtnDetalleCuotas() {
		this.vista.getXhDetalleCuotasCreditos().setEnabled(false);
		this.vista.getXhCancelarCredito().setEnabled(false);
	}

	private void enableBtnDetalleCuotas() {
		this.vista.getXhDetalleCuotasCreditos().setEnabled(true);
		this.vista.getXhCancelarCredito().setEnabled(true);
	}
	private boolean isSelectedRowCreditos(){
		return this.vista.getTblCreditosEnCurso().getSelectedRow()!=-1;
	}
	
	private boolean isSelectedRowGarantias(){
		return this.vista.getTblGarantiasEnCurso().getSelectedRow()!=-1;
	}
	
	private void enableBtnDetalleGarantias() {
		this.vista.getXhDetalleCuotasGarantias().setEnabled(true);
	}
	
	private void actualizarModelo() {
		this.listaCreditos= this.creditosDAO.findListaCreditosEnCurso(this.cuentaCorriente);
		this.modelCreditos.setListaCreditos(this.listaCreditos);
		if(this.persona instanceof PersonaFisica){
			this.listaGarantias= this.garantiaPersonalDAO.findListaCreditosGarantia((PersonaFisica)this.persona);
			this.modelGarantias= new TblGarantiasModel(this.listaGarantias);
		}
		else{
			this.modelGarantias= new TblGarantiasModel(null);
		}
		if((this.listaCreditos.isEmpty()) && (this.listaGarantias.isEmpty())){
			this.mostrarVista=false;
		}
		else{
			this.mostrarVista=true;
		}
	
	}

	private void detalleCuotasCreditos() {
		int seleccionado = this.getVista().getTblCreditosEnCurso().getSelectedRow();
		Credito locCredito = this.modelCreditos.getCredito(seleccionado);	
		DetalleCuotasController controlador= new DetalleCuotasController(locCredito, this.vista);
		controlador.getVista().setTitle("Detalle de Cuotas - Crédito Nº "+locCredito.getNumeroCredito());
		controlador.setLocationRelativeTo(this.vista);
		controlador.setVisible(true);
	}

	
	private void detalleCuotasGarantias() {
		int seleccionado = this.getVista().getTblGarantiasEnCurso().getSelectedRow();
		Credito locCredito = this.modelGarantias.getCredito(seleccionado);	
		DetalleCuotasController controlador= new DetalleCuotasController(locCredito, this.vista);
		controlador.getVista().setTitle("Detalle de Cuotas - Crédito Nº "+locCredito.getNumeroCredito());
		controlador.setLocationRelativeTo(this.vista);
		controlador.setVisible(true);
	}
	

	private void actualizarVista() {
		this.vista.getXhDetalleCuotasCreditos().setEnabled(false);
		this.vista.getXhDetalleCuotasGarantias().setEnabled(false);
		this.vista.getXhCancelarCredito().setEnabled(false);
		this.vista.getTblCreditosEnCurso().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.vista.getTblGarantiasEnCurso().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.vista.getTblCreditosEnCurso().setModel(this.modelCreditos);
		BigDecimal montoAdeudadoCredito = new BigDecimal(0);
		for(Credito cadaCredito: listaCreditos){
			montoAdeudadoCredito = montoAdeudadoCredito.add(cadaCredito.getSaldoAdeudado());
		}		
	
		this.vista.getLblMontoAdeudadoCredito().setText(this.formateadorMonetario.format(montoAdeudadoCredito));
		this.vista.getLblMontoAdeudadoCredito1().setText(this.formateadorMonetario.format(this.cuentaCorriente.getSobrante()));
		
		if(this.persona instanceof PersonaFisica){
			BigDecimal montoAdeudadoGarantia = new BigDecimal(0);
			String respondiendoGarante="NO";
			this.vista.getTblGarantiasEnCurso().setModel(this.modelGarantias);
			for(Credito cadaGarantia: listaGarantias){
				montoAdeudadoGarantia = montoAdeudadoGarantia.add(cadaGarantia.getSaldoAdeudado());
				boolean garantia=cadaGarantia.getCobrarAGarante();
				if (garantia){
					GarantiaPersonal locGarantiaPersonal= new GarantiaPersonal();
					locGarantiaPersonal=this.garantiaPersonalDAO.respondiendoComoGarante((PersonaFisica)this.persona, cadaGarantia);
					if(locGarantiaPersonal.getAfectar()){
						respondiendoGarante="SI";
					}
				}
			}
			
			this.vista.getLblTotalAdeudadoGarantias().setText(this.formateadorMonetario.format(montoAdeudadoGarantia));
			this.vista.getLblRespondiendoComoGarante().setText(respondiendoGarante);
		}
		else{
			this.vista.getXtsGarantiasEnCurso().setVisible(false);
			this.vista.getPnlGarantiasEnCurso().setVisible(false);
			this.vista.pack();
		}
		
		
	
		this.vista.getXhDetalleCuotasCreditos().setVisible(true);
		this.vista.getXhDetalleCuotasGarantias().setVisible(true);
		
	}
	
	public EstadoCuenta getVista() {
		return this.vista;
	}
	
	public boolean getMostrarVista(){
		return this.mostrarVista;
	}
	
	//TODO DETALLE CUOTAS
			
	public void setVisible(boolean pVisible){
		this.vista.setVisible(pVisible);
	}
	
	public void setLocationRelativeTo(JComponent pPadre) {
		this.vista.setLocationRelativeTo(pPadre);
	}
	
	public void imprimirEstadoCuenta(){
		try{
			List<EstadoCuentaReporte> listaCreditosFormateada = this.formatearListaCreditos();
			GestorImpresion.imprimirCollectionDataSource(this.vista, Reportes.ESTADO_CUENTA, this.setearParametros(), listaCreditosFormateada);
		
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Map<String, String> setearParametros(){
		Map<String, String> parametros= new HashMap<String, String>();
		
		if (this.persona instanceof PersonaFisica) {
			PersonaFisica locPersonaFisica= (PersonaFisica) this.persona;
			parametros.put("legajo", (locPersonaFisica.getLegajo()==null)?"":locPersonaFisica.getLegajo().toString());
			parametros.put("estado", locPersonaFisica.getEstado().getDescripcion());
		}
		else{
			PersonaJuridica locPersonaJuridica= (PersonaJuridica) this.persona;
			parametros.put("legajo", "");
			parametros.put("estado", locPersonaJuridica.getEstado().getDescripcion());
		}
	
		parametros.put("apellidoNombres", this.persona.getNombreYApellido());
		parametros.put("cui",(this.persona.getCui()==null)?"":this.persona.getCui().toString());
		return parametros;
	}
	
	private List<EstadoCuentaReporte> formatearListaCreditos() {
	    List<EstadoCuentaReporte> locCreditoReporte = new ArrayList();      
		for(Credito cadaCredito: this.listaCreditos){
			String locNumero=cadaCredito.getNumeroCredito().toString();
			String locFechaInicio=this.formateadorFecha.format(cadaCredito.getFechaInicio());
			String locMontoTotal=this.formateadorMonetario.format(cadaCredito.getMontoTotal());
			String locCantidadCuotas= Integer.toString(cadaCredito.getCantidadCuotas());
			String locCuotasAdeudadas=Integer.toString(cadaCredito.getCuotasAdeudadas());
			String locSaldoAdeudado=this.formateadorMonetario.format(cadaCredito.getSaldoAdeudado());
			String locActor="Solicitante";
			String locTextoSobranteCuentaCorriente="Total Adeudado: "+this.vista.getLblMontoAdeudadoCredito().getText();
			String locTextoDetalleCuentaCorriente="Sobrante cuenta corriente: "+this.vista.getLblMontoAdeudadoCredito1().getText();
			String locTipoCredito=cadaCredito.getTipoCredito().getNombre();
			locCreditoReporte.add(new EstadoCuentaReporte(locNumero, locFechaInicio, locMontoTotal, locCantidadCuotas, locCuotasAdeudadas, locSaldoAdeudado, locActor, locTextoSobranteCuentaCorriente, locTextoDetalleCuentaCorriente, locTipoCredito));
		}
		
		for(Credito cadaCreditoGarantia: this.listaGarantias){
			String locNumero=cadaCreditoGarantia.getNumeroCredito().toString();
			String locFechaInicio=this.formateadorFecha.format(cadaCreditoGarantia.getFechaInicio());
			String locMontoTotal=this.formateadorMonetario.format(cadaCreditoGarantia.getMontoTotal());
			String locCantidadCuotas= Integer.toString(cadaCreditoGarantia.getCantidadCuotas());
			String locCuotasAdeudadas=Integer.toString(cadaCreditoGarantia.getCuotasAdeudadas());
			String locSaldoAdeudado=this.formateadorMonetario.format(cadaCreditoGarantia.getSaldoAdeudado());
			String locActor="Garante";
			String locTextoSobranteCuentaCorriente="Total Adeudado: "+this.vista.getLblTotalAdeudadoGarantias().getText();
			String locTextoDetalleCuentaCorriente="¿Está respondiendo como garante? "+this.vista.getLblRespondiendoComoGarante().getText();
			String locTextoTipoCredito= cadaCreditoGarantia.getTipoCredito().getNombre();
			locCreditoReporte.add(new EstadoCuentaReporte(locNumero, locFechaInicio, locMontoTotal, locCantidadCuotas, locCuotasAdeudadas, locSaldoAdeudado, locActor, locTextoSobranteCuentaCorriente, locTextoDetalleCuentaCorriente, locTextoTipoCredito));
		}
		return locCreditoReporte;
	}
}