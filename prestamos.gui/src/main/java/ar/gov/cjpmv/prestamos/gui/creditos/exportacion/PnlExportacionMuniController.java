package ar.gov.cjpmv.prestamos.gui.creditos.exportacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileSystemView;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.archivos.GeneradorArchivos;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.liquidacion.GeneradorLiquidacion;
import ar.gov.cjpmv.prestamos.core.facades.ServicioLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.ImpresionPlanillaLiquidacionMuni;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;
import ar.gov.cjpmv.prestamos.gui.utiles.MonedaCellRenderer;
import ar.gov.cjpmv.prestamos.gui.utiles.ServiceLocator;

/**
 * @author pulpol 
 */
public class PnlExportacionMuniController {
	
	private static final int CANTIDAD_MESES_ANTERIORES = 2;
	private static String[] tiposOrdenamiento = {"Legajo","Apellido","Número"};
	private String ordenamiento = tiposOrdenamiento[0];
	
	private PnlDerechaExportacionMunicipalidad vista;
	private TblLiquidacionAnteriorModel modeloTablaAnterior;
	
    private ExportacionMuniModel modelo;
    private AdminCredito adminCredito;
    private String mesSeleccionado;
    
	private ServicioLiquidacion servicioLiquidacion;
	private GeneradorLiquidacion generadorLiquidacion;
	private int progresoBarraTareas;
    
    public PnlExportacionMuniController(AdminCredito adminCredito) {
        vista = new PnlDerechaExportacionMunicipalidad();
        this.adminCredito= adminCredito;
        servicioLiquidacion = ServiceLocator.getInstance().getServicioLiquidacion();
        generadorLiquidacion = servicioLiquidacion.getGeneradorLiquidacion();
        inicializarVista();
        inicializarModelos();
        inicializarEventos();
    }

    private void inicializarVista() {
    	this.vista.getBtnImprimir().setVisible(false);
    	this.vista.getBtnAceptarGuardar().setEnabled(false);
    	this.vista.getPnlExportacionMuni().getRbtLegajo().setSelected(true);
    	this.vista.getPnlExportacionMuni().getTblResultado().setDefaultRenderer(Float.class, new MonedaCellRenderer());
    	this.vista.getPnlSeleccionarCuotas().getTblResultadoBusqueda().setDefaultRenderer(Float.class, new MonedaCellRenderer());
    	this.vista.getPnlExportacionMuni().getTblResultado().getRowSorter().toggleSortOrder(1);
    	this.vista.getPnlSeleccionarCuotas().getTblResultadoBusqueda().getRowSorter().toggleSortOrder(2);
	}

	private void inicializarEventos() {
    	this.vista.getPnlExportacionMuni().getBtnExaminar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleccionarArchivo();
			}
		});
    	this.vista.getPnlSeleccionarCuotas().getBtnGenerarLiquidacion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarLiquidacion();				
			}
		});
    	
    	this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
    	
    	this.vista.getPnlSeleccionarCuotas().getCboSeleccionMes().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String mesActual= (String)vista.getPnlSeleccionarCuotas().getCboSeleccionMes().getSelectedItem();
				if(!mesActual.equals(mesSeleccionado)){
					cargarVista();
				}
			}
		});
    	
    	this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarAction();
			}
		});
    	
    	this.modeloTablaAnterior.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() == 0){
					int fila = e.getFirstRow();
					reemplazarFila(fila);
				}
			}
		});
    	generadorLiquidacion.addPropertyChangeListener(new EventoActualizacionBarraProgreso(this));
	}
	
	private void cargarVista() {
		this.modeloTablaAnterior.limpiar();
		this.modelo.getModeloTablaActual().limpiar();
		this.vista.getBtnAceptarGuardar().setEnabled(false);
	}
	
	private void inicializarModelos() {
    	modelo = new ExportacionMuniModel();
    	this.modeloTablaAnterior = new TblLiquidacionAnteriorModel();
    	vista.getPnlSeleccionarCuotas().getTblResultadoBusqueda().setModel(modeloTablaAnterior);
    	
    	DateFormatSymbols symbols = new DateFormatSymbols();
    	this.vista.getPnlSeleccionarCuotas().getCboSeleccionMes().setModel(new DefaultComboBoxModel(symbols.getMonths()));
    	vista.getPnlExportacionMuni().getTblResultado().setModel(modelo.getModeloTablaActual());
    	
    	//Seteo el número de liquidacion
    	int numeroLiquidacion = this.servicioLiquidacion.getNumeroUltimaLiquidacion(
    			modelo.getAnio(),
    			modelo.getNumeroMes()).intValue();
    	numeroLiquidacion++;
    	modelo.setNumeroLiquidacion(numeroLiquidacion);
    }
    
	private void seleccionarArchivo() {
		JFileChooser fileChooser =  new JFileChooser();
		fileChooser.setSelectedFile(new File(this.modelo.getNombreArchivoPorDefecto()));
		fileChooser.showSaveDialog(this.vista);
		if (fileChooser.getSelectedFile()!=null){
			File archivo = fileChooser.getSelectedFile();
			this.modelo.setGuardarEn(archivo); 
			this.vista.getPnlExportacionMuni().getTxtArchivoGenerar().setText(archivo.getPath());
		}
	}
	
	private void generarLiquidacion() {
		SwingWorker<Void,Void> tareaEnSegundoPlano = new SwingWorker<Void, Void>() {
			private Liquidacion liquidacionActual;
			private List<DetalleLiquidacion> listaAnteriores;
			
			@Override
			protected Void doInBackground() throws Exception {
					getVista().getPnlSeleccionarCuotas().getBtnGenerarLiquidacion().setEnabled(false);
					getVista().getBtnAceptarGuardar().setEnabled(false);

					JProgressBar progress = getVista().getPnlSeleccionarCuotas().getPbGenerado();
					progress.setValue(0);
					actualizarModelo();
  
					progresoBarraTareas = 40;
					modelo.getModeloTablaActual().limpiar();
					modeloTablaAnterior.clear();
					
					liquidacionActual = generadorLiquidacion.liquidar(
							ViaCobro.municipalidad, 
							modelo.getFechaExportacion());
					listaAnteriores = 
						servicioLiquidacion.getLiquidacionesAnteriores(
											modelo.getFechaExportacion(),
											CANTIDAD_MESES_ANTERIORES
											);
					return null;
			}
			
			@Override
			protected void done() {
				try{
					get();
					modelo.setLiquidacion(liquidacionActual);
					modeloTablaAnterior.setLista(listaAnteriores);
					getVista().getBtnAceptarGuardar().setEnabled(true);
				}
				catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(vista,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
				finally {
					getVista().getPnlSeleccionarCuotas().getBtnGenerarLiquidacion().setEnabled(true);
				}
			}
		};
		tareaEnSegundoPlano.execute();
	}

	private void actualizarModelo() {
		String mes = (String)this.vista.getPnlSeleccionarCuotas().getCboSeleccionMes().getSelectedItem();
		this.mesSeleccionado= mes;
		if (mes == null || mes.isEmpty()){
			throw new NullPointerException("Debe seleccionar el mes de la liquidación");
		}
		int anio = this.vista.getPnlSeleccionarCuotas().getYcSeleccionAnio().getYear();
		//boolean imprimir = this.vista.getPnlExportacionMuni().getChkImprimirPlanilla().isSelected();
		
		this.modelo.setMes(mes);
		this.modelo.setAnio(anio);
		this.modelo.setImprimirPlanilla(true);
	}
	
	private void guardarAction() {
		try{
			int seleccion = JOptionPane.showConfirmDialog(this.vista,
								"¿Está seguro que desea guardar esta liquidación?",
								"Guardar",
								JOptionPane.YES_NO_OPTION);
			if (seleccion == JOptionPane.YES_OPTION){
				guardar();
				this.generarArchivoLiquidacion();
				//if (this.vista.getChkImprimirPlanilla().isSelected()){
					imprimir(Reportes.PLANILLA_LIQUIDACION_MUNICIPALIDAD);
			//	}
				
				String pMensaje="La liquidación se ha realizado con éxito.";
				String pTitulo="Operación exitosa";
				JOptionPane.showMessageDialog(this.vista, pMensaje, pTitulo, JOptionPane.INFORMATION_MESSAGE);
				this.adminCredito.mostrarPnlEstadoCuenta();	
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this.vista,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void guardar() throws LogicaException {
		try{
			this.generadorLiquidacion.reemplazar(modelo.getModeloTablaActual().getCambiosRealizados());
			this.generadorLiquidacion.guardar();
		}
		catch(LogicaException e){
			e.printStackTrace();
			throw e;
		}
	}


	private void imprimir(Reportes locReportes) {		
		try{
			Liquidacion locLiquidacion= this.modelo.getLiquidacion();
			List<DetalleLiquidacion> locListaDetalle= locLiquidacion.getListaDetalleLiquidacion();
			this.ordenarLista(locListaDetalle);
			ImpresionPlanillaLiquidacionMuni locImpresion= new ImpresionPlanillaLiquidacionMuni(locListaDetalle, locLiquidacion);
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), 
														 locReportes, 
														 locImpresion.setearParametros(), 
														 locImpresion.formatearReporte());
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.adminCredito.getVista(), "No se ha podido generar el reporte.", "Error",JOptionPane.ERROR_MESSAGE);
		}	
	}

	
	private void ordenarLista(List<DetalleLiquidacion> listaDetalle) {
		if(this.vista.getRbtLegajo().isSelected()){
			this.ordenamiento = tiposOrdenamiento[0];
		}
		else if(this.vista.getRbtApellidoNombre().isSelected()){
				this.ordenamiento = tiposOrdenamiento[1];
		}
		ListaDetalleCreditosComparatorFactory factory = new ListaDetalleCreditosComparatorFactory();
		Collections.sort(listaDetalle,factory.create(this.ordenamiento));
	}
	
	private void generarArchivoLiquidacion() throws LogicaException, IOException {
		Liquidacion locLiquidacion = modelo.getLiquidacion();
		GeneradorArchivos generadorArchivos = GeneradorArchivos.getGeneradorLiquidacionMuni(locLiquidacion);
		if (this.modelo.getGuardarEn()==null){
			File archivo = new File(FileSystemView
					.getFileSystemView()
					.getDefaultDirectory()+
					"/"+
					this.modelo.getNombreArchivoPorDefecto());
			generadorArchivos.setArchivo(archivo);
		}
		else{
			generadorArchivos.setArchivo(this.modelo.getGuardarEn());	
		}
		generadorArchivos.generar();
	}

	private void cancelar() {
		String pMensaje= "¿Realmente desea cancelar la operación?";
		String pTitulo= "Confirmación";
		int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.adminCredito.mostrarPnlEstadoCuenta();	
		}
	}

	public PnlDerechaExportacionMunicipalidad getVista(){
		return this.vista;
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
	
	private void reemplazarFila(int fila) {
		TblDetalleLiquidacionActualModel modeloActual = modelo.getModeloTablaActual();
		
		if (modeloTablaAnterior.isSelected(fila)){
			DetalleLiquidacion detalleNuevo = modeloTablaAnterior.getDetalleLiquidacion(fila);
			DetalleLiquidacion detalleAnterior = modeloActual
													 .getDetalleLiquidacionPorCuentaCorriente(
															 detalleNuevo.getCuentaCorrienteAfectada()
													 );
			if (detalleAnterior == null) {
				int seleccion = JOptionPane.showConfirmDialog(vista,
										"La persona seleccionada no será liquidada en este período. " +
										"¿Desea incluirla dentro de la liquidación?", 
										"Confirmacion",JOptionPane.YES_NO_OPTION);
				if (seleccion == JOptionPane.YES_OPTION){
					modeloActual.agregarDetalleLiquidacion(detalleNuevo);
					vista.getBtnAceptarGuardar().setEnabled(true);
				}
				else {
					modeloTablaAnterior.setSelected(fila, false);
				}
			}
			else {
				int seleccion = JOptionPane.showConfirmDialog(vista,
						"La persona seleccionada ha generado una liquidación actual en este período. " +
						"¿Desea reemplazar la liquidacion anterior con la seleccionada?", 
						"Confirmacion",JOptionPane.YES_NO_OPTION);
				if (seleccion == JOptionPane.YES_OPTION){
					modeloActual.pisarDetalleLiquidacion(detalleAnterior,detalleNuevo);
					vista.getBtnAceptarGuardar().setEnabled(true);
				}
				else {
					modeloTablaAnterior.setSelected(fila, false);
				}
			}
		}
		else{
			DetalleLiquidacion detalle = modeloTablaAnterior.getDetalleLiquidacion(fila);
			modeloActual.eliminarORestaurarDetalle(detalle);
			if(modeloActual.getLista().isEmpty()){
				vista.getBtnAceptarGuardar().setEnabled(false);
			}
		}
	}
	
//	private void actualizarLiquidacion(PropertyChangeEvent evt) {
//		String nombre = evt.getPropertyName();
//		switch(EventoLiquidacion.valueOf(nombre)){
//			case CANTIDAD: 
//				modelo.getModeloTablaActual().limpiar(); 
//				break;
//			case TERMINADO: 
//				Liquidacion liquidacion = generadorLiquidacion.getLiquidacion();
//				List<DetalleLiquidacion> detalle = liquidacion.getListaDetalleLiquidacion();
//				modelo.getModeloTablaActual().setLista(detalle);
//				break;
//		}
//	}

/**
 * Main para testing. eliminar 
 */
	public static void main(String[] args){
        JFrame locFrame = new JFrame();
        locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EntityManager entityManager = GestorPersitencia.getInstance().getEntityManager();
        
        
        
    	PnlExportacionMuniController controller = new PnlExportacionMuniController(null);
    	locFrame.add(controller.vista);
    	locFrame.pack();
    	locFrame.setVisible(true);
    }

	public Integer getProgreso() {
		return progresoBarraTareas;
	}

	public void setProgreso(int otroProgreso) {
		progresoBarraTareas = otroProgreso;
	}
}
