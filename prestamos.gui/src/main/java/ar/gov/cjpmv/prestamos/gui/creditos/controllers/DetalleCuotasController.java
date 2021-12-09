package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.gui.creditos.DetalleCuotas;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.CuotasCellRenderer;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.TblCuotasModel;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models.TblEstadoCuentaCuotasModel;
import ar.gov.cjpmv.prestamos.gui.reportes.DetalleCuotasImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;


public class DetalleCuotasController {
	private DetalleCuotas vista;
	private Credito credito;
	private TblCuotasModel modelCuotas;
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();
	
	
	public DetalleCuotasController(Credito pCredito, JDialog pJDialog){
		this.vista= new DetalleCuotas(pJDialog, true);
		this.credito= pCredito;
		this.inicializarModelo();
		this.inicializarVista();
		this.inicializarEventos();
		this.formateadorDecimal.setMinimumFractionDigits(2);
		this.formateadorDecimal.setMaximumFractionDigits(2);
		actualizarModelo();
	}
	
	private void inicializarModelo() {
		this.modelCuotas = new TblEstadoCuentaCuotasModel();
	}

	private void inicializarEventos() {
		this.vista.getTblCuotas().getSelectionModel().addListSelectionListener(new ListSelectionListener() { 
		  
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				vista.getTblCuotas().repaint(); 
				
			} 
		}); 
		
		this.vista.getBtnImprimir().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				imprimirDetalleCuota(Reportes.DETALLE_CUOTAS);
			}
		});
	}


	private void imprimirDetalleCuota(Reportes locReportes) {
		try{
			List<DetalleCuotasImpresion> listaDetalleCuotasImpresion = this.formatearReporte();
				GestorImpresion.imprimirCollectionDataSource(this.vista, locReportes, this.setearParametros(), listaDetalleCuotasImpresion);
			}
			catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el reporte.", "Error",JOptionPane.ERROR_MESSAGE);
			}
		
	}
	
	private List<DetalleCuotasImpresion> formatearReporte() {
		List<DetalleCuotasImpresion> locDetalleCuotas = new ArrayList(); 
		for(Cuota cadaCuota: this.credito.getListaCuotas()){
		 String locNumeroCuota=cadaCuota.getNumeroCuota().toString();
		 String locVencimiento= this.formateadorFecha.format(cadaCuota.getVencimiento());
		 String locOtrosConceptos= this.formateadorMonetario.format(cadaCuota.getOtrosConceptos());
		 String locInteres= this.formateadorMonetario.format(cadaCuota.getInteres());
		 String locCapital= this.formateadorMonetario.format(cadaCuota.getCapital());
		 String locValorTotal= this.formateadorMonetario.format(cadaCuota.getTotal());
		 String locEstado= cadaCuota.getSituacion().getCadena();
		 locDetalleCuotas.add(new DetalleCuotasImpresion(locNumeroCuota, locVencimiento, locOtrosConceptos, locInteres, locCapital, locValorTotal, locEstado));
		}
		return  locDetalleCuotas;
	}
	
	public Map<String, String> setearParametros(){
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("numeroCredito", this.credito.getNumeroCredito().toString());
		parametros.put("fechaInicio", this.formateadorFecha.format(this.credito.getFechaInicio()));
		parametros.put("solicitante", this.credito.getCuentaCorriente().getPersona().getNombreYApellido());
		parametros.put("cantidadCuotas", this.credito.getCantidadCuotas().toString());
		return parametros;	
	}
	
	private void inicializarVista() {
		this.vista.getTblCuotas().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.vista.getTblCuotas().setDefaultRenderer(Object.class, new CuotasCellRenderer());
		this.vista.getTblCuotas().setModel(modelCuotas);
	}

	private void actualizarModelo() {
		this.modelCuotas.setListaCuotas(this.credito.getListaCuotas());
	}

	public DetalleCuotas getVista(){
		return this.vista;
	}

	public void setVisible(boolean valor) {
		this.vista.setVisible(valor);
	}
	
	public void setLocationRelativeTo(Component pVista){
		this.vista.setLocationRelativeTo(pVista);
	}


}

