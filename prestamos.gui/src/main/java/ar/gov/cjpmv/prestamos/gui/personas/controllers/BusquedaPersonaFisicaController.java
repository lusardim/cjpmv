package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.swingworker.SwingWorker.StateValue;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.gui.personas.BusquedaPersonaFisicaDialog;
import ar.gov.cjpmv.prestamos.gui.personas.model.BusquedaPersonasModel;
import ar.gov.cjpmv.prestamos.gui.personas.model.EstadoPersonaFisicaCellRenderer;
import ar.gov.cjpmv.prestamos.gui.personas.model.TblPersonaFisicaModel;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class BusquedaPersonaFisicaController {

	private BusquedaPersonaFisicaDialog vista;
	private TblPersonaFisicaModel modeloTabla;
	private BusquedaPersonasModel modeloBusqueda;
	private boolean soloFallecidos;
	private JDialog padre;
	
	private PersonaFisica personaSeleccionada;
	
	private boolean busquedaHabilitada = true;

	
	public BusquedaPersonaFisicaController(JDialog pPadre) {
		this.padre= pPadre;
		this.vista = new BusquedaPersonaFisicaDialog(pPadre, true);
		this.vista.setTitle("Búsqueda de Persona Física");
		this.modeloBusqueda = new BusquedaPersonasModel();
		this.modeloTabla = new TblPersonaFisicaModel(this.modeloBusqueda);
		this.soloFallecidos= false;
		
		this.vista.getBtnSeleccionar().setEnabled(false);
		this.inicializarVista();
		this.inicializarModelos();
		this.inicializarEventos();
	}
	
	public BusquedaPersonaFisicaController(JDialog pPadre, EstadoPersonaFisica estado) {
		this.padre= pPadre;
		this.vista = new BusquedaPersonaFisicaDialog(pPadre, true);
		this.vista.setTitle("Búsqueda de Persona Física Fallecida");
		this.modeloBusqueda = new BusquedaPersonasModel();
		this.modeloTabla = new TblPersonaFisicaModel(this.modeloBusqueda);
		this.soloFallecidos= true;
		
		this.vista.getBtnSeleccionar().setEnabled(false);
		this.inicializarVista();
		this.inicializarModelos();
		this.inicializarEventos();
	}


	private void inicializarEventos() {
		this.vista.getPnlParametrosBusquedaPersonaFisica().getBtnBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vista.dispose();
			}
		});
		this.vista.getBtnSeleccionar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				seleccionar();
			}
		});
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isSelectedRow() && e.getClickCount()==2){
					seleccionar();
				}
			}
		});
		
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (isSelectedRow() && e.getKeyCode() == KeyEvent.VK_ENTER){
					seleccionar();
				}
			}
		});
		
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (isSelectedRow()){
					vista.getBtnSeleccionar().setEnabled(true);
				}
				else{
					vista.getBtnSeleccionar().setEnabled(false);
				}
			}
		});
		
	}

	
	private void actualizarVista(){
		this.vista.getPnlParametrosBusquedaPersonaFisica().getBtnBuscar().setEnabled(this.busquedaHabilitada);
		this.vista.getPnlParametrosBusquedaPersonaFisica().setEnabled(this.busquedaHabilitada);
		
		this.vista.getPnlParametrosBusquedaPersonaFisica().getTxtApellidoRazonSocial().setText(this.modeloBusqueda.getApellido());
		this.vista.getPnlParametrosBusquedaPersonaFisica().getTxtCuilCuit().setText(Utiles.cadenaVaciaSiNulo(this.modeloBusqueda.getCuip()));
		this.vista.getPnlParametrosBusquedaPersonaFisica().getTxtLegajo().setText(Utiles.cadenaVaciaSiNulo(this.modeloBusqueda.getLegajo()));
		this.vista.getPnlParametrosBusquedaPersonaFisica().getTxtNumeroDocumento().setText(Utiles.cadenaVaciaSiNulo(this.modeloBusqueda.getNumeroDocumento()));
		
		if(!soloFallecidos){
			this.vista.getPnlParametrosBusquedaPersonaFisica().getCbxEstado().setSelectedItem(this.modeloBusqueda.getEstado());
		}
		this.vista.getPnlParametrosBusquedaPersonaFisica().getCbxTipoDocumento().setSelectedItem(this.modeloBusqueda.getTipoDocumento());
		
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().repaint();
		
	}
	
	private void buscar(){
		if (this.isBusquedaHabilitada()){
			RefrescarBusquedaPersonaFisicaTask task = new RefrescarBusquedaPersonaFisicaTask(this);
			task.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					if (arg0.getPropertyName().equals("state")){
						StateValue locValor = (StateValue)arg0.getNewValue();
						if (locValor.equals(StateValue.DONE)){
							setBusquedaHabilitada(true);
							actualizarVista();
							vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().requestFocus();
						}
					}
				}
			});

			this.setBusquedaHabilitada(false);
			task.execute();
		}
	}
	
	
	void actualizarModelo() {
		String apellido = this.vista.getPnlParametrosBusquedaPersonaFisica().getTxtApellidoRazonSocial().getText();
		
		
		String cadenaCuip = (String)this.vista.getPnlParametrosBusquedaPersonaFisica().getTxtCuilCuit().getValue();
		
		Long cuip = (cadenaCuip==null)?null:Long.parseLong(cadenaCuip);
		
		Long legajo = (Long)this.vista.getPnlParametrosBusquedaPersonaFisica().getTxtLegajo().getValue();
		
		String cadenaNumeroDocumento = (String)this.vista.getPnlParametrosBusquedaPersonaFisica().getTxtNumeroDocumento().getValue();
		Integer numeroDocumento = (cadenaNumeroDocumento==null)?null:Integer.parseInt(cadenaNumeroDocumento);
				
		if(!soloFallecidos){
			EstadoPersonaFisica estado = (EstadoPersonaFisica)this.vista.getPnlParametrosBusquedaPersonaFisica().getCbxEstado().getSelectedItem();
			this.modeloBusqueda.setEstado((estado!=null)?estado.toString():null);
		}
		else{
			this.modeloBusqueda.setEstado(EstadoPersonaFisica.FALLECIDO.toString());
		}
		TipoDocumento tipoDocumento = (TipoDocumento) this.vista.getPnlParametrosBusquedaPersonaFisica().getCbxTipoDocumento().getSelectedItem();
		
		this.modeloBusqueda.setApellido(Utiles.nuloSiVacio(apellido));
		this.modeloBusqueda.setCuip(cuip);
		this.modeloBusqueda.setLegajo(legajo);
		this.modeloBusqueda.setNumeroDocumento(numeroDocumento);

		this.modeloBusqueda.setTipoDocumento(tipoDocumento);
		
	}

	private void inicializarModelos() {
		this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().setModel(this.modeloTabla);
		
		
		if(!soloFallecidos){
			this.vista.getPnlParametrosBusquedaPersonaFisica().getCbxEstado().setVisible(true);
			this.vista.getPnlParametrosBusquedaPersonaFisica().getLblEstado().setVisible(true);
			DefaultComboBoxModel locComboBoxModel = new DefaultComboBoxModel();
			locComboBoxModel.addElement(null);
			for (EstadoPersonaFisica cadaEstado : EstadoPersonaFisica.values()){
				locComboBoxModel.addElement(cadaEstado);
			}
			this.vista.getPnlParametrosBusquedaPersonaFisica().getCbxEstado().setModel(locComboBoxModel);
			this.vista.getPnlParametrosBusquedaPersonaFisica().getCbxEstado().setRenderer(new EstadoPersonaFisicaCellRenderer());
		}
		else{
			this.vista.getPnlParametrosBusquedaPersonaFisica().getCbxEstado().setVisible(false);
			this.vista.getPnlParametrosBusquedaPersonaFisica().getLblEstado().setVisible(false);
		}
	
		
		this.vista.getPnlParametrosBusquedaPersonaFisica().getCbxTipoDocumento().setModel(this.modeloBusqueda.getCboTipoDocumentoModel());
	}

	private void inicializarVista() {
		this.vista.getPnlResultadoBusqueda().getBtnAgregar().setVisible(false);
		this.vista.getPnlResultadoBusqueda().getBtnModificar().setVisible(false);
		this.vista.getPnlResultadoBusqueda().getBtnEliminar().setVisible(false);
	}

	
	private boolean isSelectedRow(){
		return vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().getSelectedRow()!=-1;
	}
	

	public void setVisible(boolean visible) {
		this.personaSeleccionada = null;
		this.vista.setLocationRelativeTo(this.padre);
		this.vista.setVisible(visible);
	}
	
	private void seleccionar() {
		int locSeleccionado = this.vista.getPnlResultadoBusqueda().getTblFamiliaresEmpleos().getSelectedRow();
		if (locSeleccionado  !=-1 ){
			this.personaSeleccionada = this.modeloTabla.getPersonaFisica(locSeleccionado);
			this.vista.dispose();
		}
	}

	
	public TblPersonaFisicaModel getModeloTabla() {
		return modeloTabla;
	}	
	
	
	public PersonaFisica getPersonaSeleccionada() {
		return personaSeleccionada;
	}
	
	
	
	//TODO ELIMINAR
public static void main(String[] args){
		
		BusquedaPersonaFisicaController locController = new BusquedaPersonaFisicaController(new JDialog());
		locController.setVisible(true);
		System.out.println(locController.getPersonaSeleccionada());
	}

	public boolean isBusquedaHabilitada() {
		return busquedaHabilitada;
	}

	public void setBusquedaHabilitada(boolean busquedaHabilitada) {
		this.busquedaHabilitada = busquedaHabilitada;
	}
}



class RefrescarBusquedaPersonaFisicaTask extends Task<Void, Void>{

	private BusquedaPersonaFisicaController controlador;
	
	
	public RefrescarBusquedaPersonaFisicaTask(BusquedaPersonaFisicaController controlador) {
		super(Application.getInstance());
		this.controlador = controlador;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		this.controlador.actualizarModelo();
		this.controlador.getModeloTabla().refrescarBusqueda();
		return null;
	}
	
	@Override
	protected void finished() {
		super.finished();
		
	}
}
	
