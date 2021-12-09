package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.swingworker.SwingWorker.StateValue;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaBusquedaView;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlResultadoBusquedaView;
import ar.gov.cjpmv.prestamos.gui.personas.PnlBusquedaPersonaView;
import ar.gov.cjpmv.prestamos.gui.personas.PnlParametrosBusquedaPersonaView;
import ar.gov.cjpmv.prestamos.gui.personas.model.BusquedaPersonasModel;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class BusquedaPersonas {
	private PnlBusquedaPersonaView pnlBusqueda;
	private PnlDchaBusquedaView vista;
	private BusquedaPersonasModel modelo;
	private boolean busquedaHabilitada = true;
	
	
	public BusquedaPersonas(){
		this(new BusquedaPersonasModel());
	}

	public BusquedaPersonas(BusquedaPersonasModel pBusquedaPersonasModel){
		this.modelo = pBusquedaPersonasModel;
		this.vista = new PnlDchaBusquedaView();
		this.pnlBusqueda = new PnlBusquedaPersonaView();
		this.vista.setPnlBusquedaDcha(this.pnlBusqueda);
		this.inicializarModelo();
		this.inicializarEventos();
		this.actualizarVista();
	}
	
	
	private void inicializarEventos() {
		this.getPnlParametrosBusquedaPersonaView().getBtnBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actualizarModelo();
				refrescarBusqueda();
				actualizarVista();
			}
		});
		
		this.getPnlResultadoBusqueda().getLblEliminar().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getComponent().isEnabled()){
					eliminar();
				}
			}
		});
		
		this.getPnlResultadoBusqueda().getTblResultadoBusqueda().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				actualizarVista();
			}
		});
		
		KeyListener locKeyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					if (e.getComponent() instanceof JFormattedTextField){
						JFormattedTextField locFormattedTextField = (JFormattedTextField)e.getComponent();
						try {
							locFormattedTextField.commitEdit();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
					actualizarModelo();
					refrescarBusqueda();
				}
			}
		};
		//Agrego a todos los componentes que pueden recibir foco el listener
		for (Component cadaComponent : this.getPnlParametrosBusquedaPersonaView().getComponents()){
			if (cadaComponent instanceof JTextComponent){
				JTextComponent locTextComponent = (JTextComponent)cadaComponent;
				locTextComponent.addKeyListener(locKeyListener);
			}
		}
	}

	
	private void inicializarModelo() {
		this.getPnlParametrosBusquedaPersonaView().getCbxEstado().setModel(this.modelo.getCboEstadoPersonaModel());
		this.getPnlParametrosBusquedaPersonaView().getCbxEstado().setSelectedItem(null);
		
		this.getPnlParametrosBusquedaPersonaView().getCbxTipoDocumento().setModel(this.modelo.getCboTipoDocumentoModel());
		this.getPnlParametrosBusquedaPersonaView().getCbxTipoDocumento().setSelectedItem(null);
		
		this.getPnlResultadoBusqueda().getTblResultadoBusqueda().setModel(this.modelo.getModeloTabla());
		
		this.actualizarModelo();
	}

	public PnlResultadoBusquedaView getPnlResultadoBusqueda(){
		return this.pnlBusqueda.getPnlResultadoBusquedaView1();
	}

	public PnlDchaBusquedaView getVista() {
		return vista;
	}
	
	private PnlParametrosBusquedaPersonaView getPnlParametrosBusquedaPersonaView(){
		return this.pnlBusqueda.getPnlParametrosBusquedaPersonaView1();
	}
	
	public void actualizarVista(){
		//Saco el boton imprimir por ahora
		this.getPnlResultadoBusqueda().getLblImprimir().setVisible(false);
		
		this.getPnlParametrosBusquedaPersonaView().getBtnBuscar().setEnabled(this.busquedaHabilitada);
		this.getPnlResultadoBusqueda().setEnabled(this.busquedaHabilitada);
		
		this.getPnlParametrosBusquedaPersonaView().getTxtApellidoRazonSocial().setText(this.modelo.getApellido());
		this.getPnlParametrosBusquedaPersonaView().getTxtCuilCuit().setText(Utiles.cadenaVaciaSiNulo(this.modelo.getCuip()));
		this.getPnlParametrosBusquedaPersonaView().getTxtLegajo().setText(Utiles.cadenaVaciaSiNulo(this.modelo.getLegajo()));
		this.getPnlParametrosBusquedaPersonaView().getTxtNumeroDocumento().setText(Utiles.cadenaVaciaSiNulo(this.modelo.getNumeroDocumento()));
		this.getPnlParametrosBusquedaPersonaView().getCbxEstado().setSelectedItem(this.modelo.getEstado());
		this.getPnlParametrosBusquedaPersonaView().getCbxTipoDocumento().setSelectedItem(this.modelo.getTipoDocumento());
		
		int seleccion = this.getPnlResultadoBusqueda().getTblResultadoBusqueda().getSelectedRow();
		if (seleccion==-1){
			this.habilitarEditarEliminar(false);
		}
		else{
			this.habilitarEditarEliminar(true);
		}
		
		this.getPnlResultadoBusqueda().getTblResultadoBusqueda().repaint();
		

	}
	
	
	private void habilitarEditarEliminar(boolean pHabilitado){
		this.getPnlResultadoBusqueda().getLblEditar().setEnabled(pHabilitado);
		this.getPnlResultadoBusqueda().getLblEliminar().setEnabled(pHabilitado);	
	}
	
	public void actualizarModelo(){
		String apellido = this.getPnlParametrosBusquedaPersonaView().getTxtApellidoRazonSocial().getText();
		
		
		String cadenaCuip = (String)this.getPnlParametrosBusquedaPersonaView().getTxtCuilCuit().getValue();
		
		Long cuip = (cadenaCuip==null)?null:Long.parseLong(cadenaCuip);
		
		Long legajo = (Long)this.getPnlParametrosBusquedaPersonaView().getTxtLegajo().getValue();
		
		String cadenaNumeroDocumento = (String)this.getPnlParametrosBusquedaPersonaView().getTxtNumeroDocumento().getValue();
		Integer numeroDocumento = (cadenaNumeroDocumento==null)?null:Integer.parseInt(cadenaNumeroDocumento);
				
		String estado = (String)this.getPnlParametrosBusquedaPersonaView().getCbxEstado().getSelectedItem();
		TipoDocumento tipoDocumento = (TipoDocumento) this.getPnlParametrosBusquedaPersonaView().getCbxTipoDocumento().getSelectedItem();
		
		this.modelo.setApellido(Utiles.nuloSiVacio(apellido));
		this.modelo.setCuip(cuip);
		this.modelo.setLegajo(legajo);
		this.modelo.setNumeroDocumento(numeroDocumento);
		this.modelo.setEstado(estado);
		this.modelo.setTipoDocumento(tipoDocumento);
	}
	
	
	
	/**
	 * Actualiza la tabla de búsqueda
	 */
	private void refrescarBusqueda() {
		if (this.isBusquedaHabilitada()){
			RefrescarTablaTask task = new RefrescarTablaTask(this);
			task.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					if (arg0.getPropertyName().equals("state")){
						StateValue locValor = (StateValue)arg0.getNewValue();
						if (locValor.equals(StateValue.DONE)){
							setBusquedaHabilitada(true);
							actualizarVista();
						}
					}
				}
			});

			this.setBusquedaHabilitada(false);
			task.execute();
		}

	}
	
	public void eliminar(){
		try{
			int filaSeleccionada = this.getPnlResultadoBusqueda().getTblResultadoBusqueda().getSelectedRow();
			if (filaSeleccionada!=-1){
				if (JOptionPane.showConfirmDialog(this.vista,
												  "¿Está seguro que desea eliminar a la persona seleccionada?",
												  "Eliminar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
				{
					this.modelo.eliminar(filaSeleccionada);
					this.getPnlResultadoBusqueda().getTblResultadoBusqueda().clearSelection();
					this.actualizarVista();
				}
			}
		}
		catch(LogicaException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		BusquedaPersonas locBusquedaPersonas = new BusquedaPersonas();
		
		JFrame locFrame = new JFrame();
		locFrame.add(locBusquedaPersonas.getVista(),BorderLayout.CENTER);
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		locFrame.pack();
		locFrame.setVisible(true);
	}

	public BusquedaPersonasModel getModelo() {
		return modelo;
	}

	public boolean isBusquedaHabilitada() {
		return busquedaHabilitada;
	}

	public void setBusquedaHabilitada(boolean busquedaHabilitada) {
		this.busquedaHabilitada = busquedaHabilitada;
	}

	
	public JLabel getLblEditar(){
		return this.getPnlResultadoBusqueda().getLblEditar();
	}
}


class RefrescarTablaTask extends Task<Void, Void>{

	private BusquedaPersonas controlador;
	
	
	public RefrescarTablaTask(BusquedaPersonas controlador) {
		super(Application.getInstance());
		this.controlador = controlador;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		this.controlador.getModelo().refrescarBusqueda();
		return null;
	}
	
	@Override
	protected void finished() {
		super.finished();
		
	}
	
}