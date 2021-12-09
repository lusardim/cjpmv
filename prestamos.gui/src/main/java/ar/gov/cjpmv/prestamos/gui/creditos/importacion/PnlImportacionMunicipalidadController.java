package ar.gov.cjpmv.prestamos.gui.creditos.importacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroDAO;
import ar.gov.cjpmv.prestamos.core.facades.ServicioLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionImportacionArchivo;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.utiles.ServiceLocator;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class PnlImportacionMunicipalidadController {

	private static final int POSICION_CUIL = 4;
	private static final int POSICION_MONTO = 12;

	private PersonaDAO personaDAO = DAOFactory.getDefecto().getPersonaDAO();

	private PnlDerechaImportacionMunicipalidad vista;
	private AdminCredito adminCredito;
	private JFileChooser filechooser = new JFileChooser();
	private File archivo;
	private String rutaNombreArchivo;
	private Integer locAnio;
	private Integer locMes;
	private ServicioLiquidacion servicioLiquidacion;
	private Liquidacion liquidacion;
	private List<DetalleLiquidacion> listaDetalleLiquidacion;
	private List<ArchivoDetalleLiquidacionModel> listaModeloDetalleLiquidacion;
	private TblArchivoDetalleLiquidacion tblArchivoDetalle;

	public PnlImportacionMunicipalidadController(AdminCredito adminCredito) {
		super();
		this.adminCredito = adminCredito;
		vista= new PnlDerechaImportacionMunicipalidad();
		this.servicioLiquidacion= ServiceLocator.getInstance().getServicioLiquidacion();
		this.listaModeloDetalleLiquidacion= new ArrayList<ArchivoDetalleLiquidacionModel>();
		this.tblArchivoDetalle= new TblArchivoDetalleLiquidacion(listaModeloDetalleLiquidacion);
		this.inicializarEventos();
		this.inicializarVista();
	}

	private void inicializarVista() {
		this.vista.getPnlImportacionMunicipalidad().getBtnProcesar().setEnabled(false);
		this.vista.getBtnAceptarGuardar().setEnabled(false);
		this.vista.getBtnAceptarGuardar().setText("Cobrar");
		this.vista.getPnlImportacionMunicipalidad().getTblLiquidacionMunicipalidad().setModel(tblArchivoDetalle);
		this.vista.getPnlImportacionMunicipalidad().getTblLiquidacionMunicipalidad().setDefaultRenderer(Object.class, new FilaDetalleLiquidacionCellRenderer());
	}

	private void inicializarEventos() {
		this.vista.getPnlImportacionMunicipalidad().getBtnExaminar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listaModeloDetalleLiquidacion= new ArrayList<ArchivoDetalleLiquidacionModel>();
				tblArchivoDetalle= new TblArchivoDetalleLiquidacion(listaModeloDetalleLiquidacion);
				seleccionarArchivo();	
				actualizarVista();
			}
		});

		this.vista.getPnlImportacionMunicipalidad().getBtnProcesar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					procesarArchivo();
				} catch (LogicaException exp) {
					JOptionPane.showMessageDialog(vista, exp.getMessage(), exp.getTitulo(), JOptionPane.ERROR_MESSAGE);
					limpiarTabla();
				}
			}			
		});

		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				aceptar();
			}			
		});

		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
	}

	private void actualizarVista() {
		this.vista.getPnlImportacionMunicipalidad().getTxtRuta().setText(this.rutaNombreArchivo);

		if(this.rutaNombreArchivo==null){
			this.vista.getPnlImportacionMunicipalidad().getBtnProcesar().setEnabled(false);
			this.vista.getBtnAceptarGuardar().setEnabled(false);			
		}
		else{
			if(this.listaModeloDetalleLiquidacion.isEmpty()){
				this.vista.getBtnAceptarGuardar().setEnabled(false);
			}
			else{
				this.vista.getBtnAceptarGuardar().setEnabled(true);
			}
			this.vista.getPnlImportacionMunicipalidad().getBtnProcesar().setEnabled(true);
		}

		this.vista.getPnlImportacionMunicipalidad().getTblLiquidacionMunicipalidad().setModel(tblArchivoDetalle);
	}	

	private void cancelar() {
		String pMensaje= "¿Realmente desea cancelar la operación?";
		String pTitulo= "Confirmación";
		int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.adminCredito.mostrarPnlEstadoCuenta();	
		}
	}

	private void aceptar() {
		try{
			int seleccion = JOptionPane.showConfirmDialog(this.vista,
					"¿Está seguro que desea cobrar esta liquidación?",
					"Cobrar Liquidación",
					JOptionPane.YES_NO_OPTION);
			if (seleccion == JOptionPane.YES_OPTION){
				realizarCobro();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void realizarCobro() {
		SwingWorker<Void,Void> cobro = new SwingWorker<Void, Void>() {

			private void setBotonesHabilitados(boolean habilitado) {
				vista.getBtnAceptarGuardar().setEnabled(habilitado);
				vista.getBtnCancelar().setEnabled(habilitado);
				vista.getPnlImportacionMunicipalidad().getBtnExaminar().setEnabled(habilitado);
				vista.getPnlImportacionMunicipalidad().getBtnProcesar().setEnabled(habilitado);
			}
			
			@Override
			protected Void doInBackground() throws Exception {
				setBotonesHabilitados(false);
				JProgressBar bar = vista.getPnlImportacionMunicipalidad()
					 					.getPbarProcesar();
				bar.setIndeterminate(true);
				bar.setStringPainted(false); 
				
				CobroDAO cobroDAO = DAOFactory.getDefecto().getCobroDAO();
				Map<CuentaCorriente, BigDecimal> cobros = new HashMap<CuentaCorriente, BigDecimal>();
				for(ArchivoDetalleLiquidacionModel cadaLinea: listaModeloDetalleLiquidacion) {
					if(cadaLinea.getMontoRecibido().floatValue() != 0F) {
						cobros.put(
								cadaLinea
									.getDetalleLiquidacion()
									.getCuentaCorrienteAfectada(), 
								cadaLinea.getMontoRecibido());
					}
				}
				cobroDAO.cobrar(liquidacion,cobros);
				return null;
			}
			
			protected void done() {
				try {
					get();
					if (this.isDone()) {
						vista.getPnlImportacionMunicipalidad()
							 .getPbarProcesar()
							 .setIndeterminate(false);

						String pMensaje="La liquidación se ha cobrado con éxito.";
						String pTitulo="Operación exitosa";
						JOptionPane.showMessageDialog(vista, pMensaje, pTitulo, JOptionPane.INFORMATION_MESSAGE);
						setBotonesHabilitados(true);
						adminCredito.mostrarPnlEstadoCuenta();					
					}
				}
				catch (ExecutionException e) {
					if (e.getCause() instanceof LogicaException) {
						JOptionPane.showMessageDialog(
								vista,
								e.getCause().getMessage(),
								"Error",
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						mostrarErrorGenerico();
					}
				}
				catch(Exception e) {
					mostrarErrorGenerico();				
				}
			}

			private void mostrarErrorGenerico() {
				JOptionPane.showMessageDialog(
						vista, 
						"Ha ocurrido un error al realizar el cobro",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			};
		};
		cobro.execute();
	}

	private void seleccionarArchivo() {
		int returnVal = filechooser.showOpenDialog(filechooser);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.archivo = filechooser.getSelectedFile();
			rutaNombreArchivo=this.archivo.getAbsolutePath();
			vista.getPnlImportacionMunicipalidad().getBtnProcesar().setEnabled(true);
		} 	
		else{
			vista.getPnlImportacionMunicipalidad().getBtnProcesar().setEnabled(false);
			rutaNombreArchivo=null;
		}
	}


	private void procesarArchivo() throws LogicaException{
		this.listaModeloDetalleLiquidacion.clear();
		this.locAnio= this.vista.getPnlImportacionMunicipalidad().getAnioLiquidacion().getYear();
		this.locMes = this.vista.getPnlImportacionMunicipalidad().getMchMesLiquidacion().getMonth()+1;

		if(this.vista.getPnlImportacionMunicipalidad().getTxtRuta().getText().equals("")) {
			String titulo="Error: dato inexistente";
			String mensaje="No se ha indicado ningún archivo para procesar. Verifique dato.";
			JOptionPane.showMessageDialog(this.vista, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
			this.limpiarTabla();
			this.vista.getBtnAceptarGuardar().setEnabled(false);	
			this.vista.getPnlImportacionMunicipalidad().getBtnProcesar().setEnabled(false);	
		}
		else{
			
			if(this.isExisteLiquidacion()) {
				this.vista.getPnlImportacionMunicipalidad().getPbarProcesar().setIndeterminate(true);
				Thread hilo = new Thread( new Runnable() {
					@Override
					public void run() {
						try{
							abrirArchivo();
							addDetalleLiquidacionNoEnviadoEnArchivo();

							tblArchivoDetalle= new TblArchivoDetalleLiquidacion(listaModeloDetalleLiquidacion);

							vista.getPnlImportacionMunicipalidad().getTblLiquidacionMunicipalidad().setModel(tblArchivoDetalle);

							//vista.getPnlImportacionMunicipalidad().getTblLiquidacionMunicipalidad().setDefaultRenderer(Object.class, new FilaDetalleLiquidacionCellRenderer());

							SwingUtilities.invokeAndWait(new Runnable() {
								@Override
								public void run() {
									finalizarProcesarArchivo();
								}
							}) ;
						}
						catch(LogicaException e){
							JOptionPane.showMessageDialog(vista, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
							finalizarProcesarArchivo();
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}});
				hilo.start();
			}
			else{
				this.rutaNombreArchivo= null;
				int codigo=64;
				String campo= "";
				throw new LogicaException(codigo, campo);

			}
			actualizarVista();
		}

	}



	private void finalizarProcesarArchivo() {
		JProgressBar barra = vista.getPnlImportacionMunicipalidad().getPbarProcesar();
		vista.getPnlImportacionMunicipalidad().setFinalizada(barra);
		vista.getBtnAceptarGuardar().setEnabled(true);
	}


	private void addDetalleLiquidacionNoEnviadoEnArchivo() {
		for(DetalleLiquidacion cadaDetalle: listaDetalleLiquidacion) {
			BigDecimal montoRecibido = new BigDecimal("0.00");
			SituacionImportacionArchivo locSituacionImportacion= SituacionImportacionArchivo.SI_DET_NO_ARCH;
			ArchivoDetalleLiquidacionModel locModelo= new ArchivoDetalleLiquidacionModel(cadaDetalle, montoRecibido, locSituacionImportacion);
			this.listaModeloDetalleLiquidacion.add(locModelo);
		}
	}

	private boolean isExisteLiquidacion() {
		liquidacion = this.servicioLiquidacion.buscarPorPeriodo(this.locMes, this.locAnio, ViaCobro.municipalidad);

		if(liquidacion!=null){
			this.listaDetalleLiquidacion= liquidacion.getListaDetalleLiquidacion();
			if((this.listaDetalleLiquidacion!=null)&&(!this.listaDetalleLiquidacion.isEmpty())){
				return true;
			}

		}
		return false;
	}

	private void abrirArchivo() throws LogicaException{
		FileInputStream archivoEntrada=null;
		try {
			archivoEntrada = new FileInputStream(archivo);
			BufferedReader bufferReader= new BufferedReader(new InputStreamReader(archivoEntrada, "UTF8"));
			int locNumeroLinea=0;
			String cadaLinea = null;
			cadaLinea=bufferReader.readLine();
			while (cadaLinea != null) {
				locNumeroLinea++;
				String[] elementos = cadaLinea.split("[|]");

				if (!Utiles.isLong(elementos[POSICION_CUIL])){
					throw new LogicaException(65, "cuil, en la linea número "+locNumeroLinea);
				}
				String montoString = elementos[POSICION_MONTO].trim().replace(",", ".");

				if (!Utiles.isBigDecimal(montoString)){
					throw new LogicaException(65, "monto, en la linea número "+locNumeroLinea);
				}

				long cuilArchivo= Long.parseLong(elementos[POSICION_CUIL]);
				BigDecimal montoRecibido = new BigDecimal(montoString);

				DetalleLiquidacion detalleLiquidacion = this.buscarPorCuilEnListaDetalleLiquidacion(cuilArchivo);


				SituacionImportacionArchivo locSituacion=null;
				//Si el cuil del archivo esta en listaDetalleLiquidacion
				if(detalleLiquidacion!=null){

					List <Cuota> listaCuotas= detalleLiquidacion.getListaCuotas();
					boolean todasCanceladas= true;
					for(Cuota cadaCuota: listaCuotas){
						if(cadaCuota.getCancelacion()==null){
							todasCanceladas= false;
						}
					}

					if(todasCanceladas) {
						//todas las cuotas de ese detalle estan canceladas
						locSituacion= SituacionImportacionArchivo.SI_DET_SI_ARCH_CANCELADA;

					}
					else{
						//algunas cuotas de ese detalle no estan canceladas
						locSituacion= SituacionImportacionArchivo.SI_DET_SI_ARCH_PENDIENTE;
					}


					//lo remuevo de la lista - al finalizar solo quedarna los 
					// cuil que no estan en el archivo pero si en listaDetalleLiquidacion
					this.listaDetalleLiquidacion.remove(detalleLiquidacion);
				}
				//Si el cuil del archivo no esta en listaDetalleLiquidacion
				else{
					CuentaCorriente locCuentaCorriente = personaDAO.getCuentaCorrientePorCuil(cuilArchivo);
					if(locCuentaCorriente==null){
						throw new LogicaException(66, "con cuil "+cuilArchivo+" que no posee créditos registrados en el sistema.");
					}
					detalleLiquidacion= new DetalleLiquidacion();
					detalleLiquidacion.setCuentaCorrienteAfectada(locCuentaCorriente);
					detalleLiquidacion.setLiquidacion(this.liquidacion);
					detalleLiquidacion.setMonto(new BigDecimal("0.00"));
					detalleLiquidacion.setListaCuotas(null);	
					locSituacion= SituacionImportacionArchivo.NO_DET_SI_ARCH;
				}
				ArchivoDetalleLiquidacionModel locModelo= new ArchivoDetalleLiquidacionModel(detalleLiquidacion, montoRecibido, locSituacion);
				this.listaModeloDetalleLiquidacion.add(locModelo);
				cadaLinea=bufferReader.readLine();
			}
		} 
		catch (LogicaException e){
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LogicaException(67, "");
		}
		finally{
			try {
				if (archivoEntrada != null){
					archivoEntrada.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}




	private DetalleLiquidacion buscarPorCuilEnListaDetalleLiquidacion(Long cuilArchivo) {

		for(DetalleLiquidacion cadaDetalle: listaDetalleLiquidacion){
			Persona persona = cadaDetalle.getPersona();
			if (persona.getCui()!=null){
				if(cadaDetalle.getPersona().getCui().equals(cuilArchivo)){
					return cadaDetalle;
				}
			}
		}

		return null;
	}




	private void limpiarTabla() {
		this.listaModeloDetalleLiquidacion.clear();
		this.tblArchivoDetalle= new TblArchivoDetalleLiquidacion(listaModeloDetalleLiquidacion);
		this.vista.getPnlImportacionMunicipalidad().getTblLiquidacionMunicipalidad().setModel(tblArchivoDetalle);

	}

	/**
	 * Main para testing. eliminar 
	 */
	public static void main(String[] args){
		JFrame locFrame = new JFrame();
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		PnlImportacionMunicipalidadController controller = new PnlImportacionMunicipalidadController(null);
		locFrame.add(controller.vista);
		locFrame.pack();
		locFrame.setVisible(true);
	}




	public PnlDerechaImportacionMunicipalidad getVista(){
		return this.vista;
	}



}

