package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porHaberesCaja;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import ar.gov.cjpmv.prestamos.core.business.liquidacion.EventoLiquidacion;
import ar.gov.cjpmv.prestamos.core.business.liquidacion.GeneradorLiquidacion;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroDAO;
import ar.gov.cjpmv.prestamos.core.facades.ServicioLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.comunes.LegajoCellRenderer;
import ar.gov.cjpmv.prestamos.gui.comunes.MontoCellRenderer;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

public class PnlCobroPorHaberesCajaControllers {
	

	private static String[] tiposOrdenamiento = {"Legajo","Apellido","Número"};
	private String ordenamiento = tiposOrdenamiento[1];
	
	private PnlDerechaCobroPorHaberesCaja vista;
	private AdminCredito adminCredito;
	private TblCobroPorHaberesCaja modeloTabla;
	private Integer locAnio;
	private Integer locMes;
	
	private GeneradorLiquidacion generadorLiquidacion;
	private CobroDAO cobroDAO;
	
	public PnlCobroPorHaberesCajaControllers(AdminCredito adminCredito) {
		super();
		generadorLiquidacion = new ServicioLiquidacion().getGeneradorLiquidacion();
		this.adminCredito = adminCredito;
		cobroDAO = new CobroDAO();
		this.inicializarVista();
		inicializarModelo();
		this.inicializarEventos();
		setearRendererTabla();
	}

	private void setearRendererTabla() {
		JTable tblCobroCaja = this.vista.getTblCobroCaja();
		tblCobroCaja.setDefaultRenderer(Float.class, new MontoCellRenderer());
		tblCobroCaja.setDefaultRenderer(Long.class, new LegajoCellRenderer());
		tblCobroCaja.getRowSorter().toggleSortOrder(1);
	}

	private void inicializarModelo() {
		modeloTabla= new TblCobroPorHaberesCaja();
		vista.getTblCobroCaja().setModel(modeloTabla);
	}

	private void inicializarEventos() {
		this.vista.getPnlCobroPorHaberesCaja().getBtnCobrar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				generarCobro();
			}
		});
		
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cobrar();
			}
		});
		
		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		this.generadorLiquidacion.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				EventoLiquidacion evento = EventoLiquidacion.valueOf(evt.getPropertyName());
				switch (evento){
					case TERMINADO: cargarTabla(); break;
					case CANTIDAD: break;
					case ERROR: mostrarError(); break;
					case PROCESADOS: break;
				}
			}
		});
	}

	private void cargarTabla() {
		Liquidacion liquidacion = generadorLiquidacion.getLiquidacion();
		if (liquidacion != null) {
			modeloTabla.setLista(liquidacion.getListaDetalleLiquidacion());
		}
		else {
			modeloTabla.setLista(null);
		}
		this.actualizarVista();
	}

	private void generarCobro() {
		modeloTabla.limpiar();
		
		this.locAnio= this.vista.getPnlCobroPorHaberesCaja().getYchAnio().getYear();
		//mes se trabaja desde 0 a 11
		this.locMes = this.vista.getPnlCobroPorHaberesCaja().getMchMes().getMonth();
	
		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.YEAR, locAnio);
		calendario.set(Calendar.MONTH,locMes);		
		generadorLiquidacion.liquidar(ViaCobro.caja, calendario.getTime());
	}

	private void actualizarVista() {
		boolean habilitarAceptar = modeloTabla.getRowCount()>0;
		this.vista.getBtnAceptarGuardar().setEnabled(habilitarAceptar);	
	}

	private void cobrar(){
		try{
			int seleccion = JOptionPane.showConfirmDialog(this.vista,
					"¿Está seguro que desea cobrar este período?",
					"Cobrar Cuotas",
					JOptionPane.YES_NO_OPTION);

			if (seleccion == JOptionPane.YES_OPTION){
				cobroDAO.cobrar(generadorLiquidacion.getLiquidacion());
		
				String pMensaje="El cobro se ha realizado con éxito.";
				String pTitulo="Operación exitosa";
				JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.INFORMATION_MESSAGE);
				this.imprimirReporte(Reportes.PLANILLA_COBRO_HABERES_CAJA);
				
				this.adminCredito.mostrarPnlEstadoCuenta();	
			}
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void imprimirReporte(Reportes locReportes) {		
		try{
			List<DetalleLiquidacion> listaOrdenada= modeloTabla.getLista();
			this.ordenarLista(listaOrdenada);
			ImpresionPlanillaCobroHaberesCaja locImpresion= new ImpresionPlanillaCobroHaberesCaja(listaOrdenada, this.locMes, this.locAnio);
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), locReportes, locImpresion.setearParametros(), locImpresion.formatearReporte());
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.adminCredito.getVista(), "No se ha podido generar el reporte.", "Error",JOptionPane.ERROR_MESSAGE);
		}	
	}

	private void cancelar() {
		String pMensaje= "¿Realmente desea cancelar la operación?";
		String pTitulo= "Confirmación";
		int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.adminCredito.mostrarPnlEstadoCuenta();	
		}
	}

	private void inicializarVista() {
		vista= new PnlDerechaCobroPorHaberesCaja();
		vista.getBtnAceptarGuardar().setText("Cobrar");
		vista.getBtnAceptarGuardar().setEnabled(false);
		vista.getPnlCobroPorHaberesCaja().getRbtnOrdenarPorApellidoNombre().setSelected(true);
		vista.getBtnImprimir().setVisible(false);
	}

	private void mostrarError() {
		String titulo="Error";
		String mensaje="No se han encontrado cuotas para cobrar en el periodo seleccionado";
		JOptionPane.showMessageDialog(this.vista, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
		modeloTabla.limpiar();
		this.vista.getBtnAceptarGuardar().setEnabled(false);	
	}
	



	private void ordenarLista(List<DetalleLiquidacion> listaDetalle) {
			if(this.vista.getPnlCobroPorHaberesCaja().getRbtnOrdenarPorLegajo().isSelected()){
				this.ordenamiento= tiposOrdenamiento[0];
			}
			else{
				if(this.vista.getPnlCobroPorHaberesCaja().getRbtnOrdenarPorApellidoNombre().isSelected()){
					this.ordenamiento= tiposOrdenamiento[1];
				}
			}
			ListaDetalleCreditosComparatorFactory factory = new ListaDetalleCreditosComparatorFactory();
			Collections.sort(listaDetalle,factory.create(this.ordenamiento));
		}



		
		
		//MUYY CHANCHO, PERO ES RÁPIDO DE ARMAR
		private class ListaDetalleCreditosComparatorFactory {

			public Comparator<DetalleLiquidacion> create(String tipoOrden) {
				
				if (tipoOrden.equals(tiposOrdenamiento[1])) {
					return new Comparator<DetalleLiquidacion>() {
						@Override
						public int compare(DetalleLiquidacion o1, DetalleLiquidacion o2) {
							Persona p1 = o1.getPersona();
							Persona p2 = o2.getPersona();
							if (p1 instanceof PersonaFisica && p2 instanceof PersonaFisica) {
								return ((PersonaFisica)p1).getApellido().compareTo(((PersonaFisica)p2).getApellido());
							}
							return 0;
						}
					};
				}
				
				
				//Legajo
				Comparator<DetalleLiquidacion> comparator = new Comparator<DetalleLiquidacion>() {
					@Override
					public int compare(DetalleLiquidacion o1, DetalleLiquidacion o2) {
						Persona p1 = o1.getPersona();
						Persona p2 = o2.getPersona();
						if (p1 instanceof PersonaFisica && p2 instanceof PersonaFisica) {
							return ((PersonaFisica)p1).getLegajo().compareTo(((PersonaFisica)p2).getLegajo());
						}
						return 0;
					}
				};

				return comparator;
			}
		}
		
	
	

	/**
	 * Main para testing. eliminar 
	 */
		public static void main(String[] args){
	        JFrame locFrame = new JFrame();
	        locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	    	PnlCobroPorHaberesCajaControllers controller = new PnlCobroPorHaberesCajaControllers(null);
	    	locFrame.add(controller.vista);
	    	locFrame.pack();
	    	locFrame.setVisible(true);
	    }

		public PnlDerechaCobroPorHaberesCaja getVista(){
			return this.vista;
		}
	
		
}
