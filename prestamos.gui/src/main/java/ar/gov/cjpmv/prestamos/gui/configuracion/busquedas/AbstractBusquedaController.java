package ar.gov.cjpmv.prestamos.gui.configuracion.busquedas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingworker.SwingWorker.StateValue;

public abstract class AbstractBusquedaController <M extends BusquedaModel, V extends BusquedaView>{
	
	private M modelo;
	private V vista;
	
	private boolean busquedaHabilitada=true;
	
	public AbstractBusquedaController(M pModelo, V pVista) {
		this.modelo = pModelo;
		this.vista = pVista;
		this.inicializarModelo();
		this.inicializarEventos();
		this.actualizarVista();
	}

	protected void inicializarEventos() {
		this.vista.getBtnBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actualizarModelo();
				refrescarBusqueda();
				actualizarVista();
			}
		});		
		
		this.vista.getTblBusqueda().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				actualizarVista();
			}
		});
		
		BtnEnterKeyListener locBtnEnterKeyListener = new BtnEnterKeyListener(this);

		//Agrego a todos los componentes que pueden recibir foco el listener
		for (Component cadaComponent : this.vista.getPnlParametrosBusqueda().getComponents()){
			if (cadaComponent instanceof JTextComponent){
				JTextComponent locTextComponent = (JTextComponent)cadaComponent;
				locTextComponent.addKeyListener(locBtnEnterKeyListener);
			}
		}
	}

	/**
	 * Inicializa los modelos de búsqueda (o cualquier cosa relacionada con los modelos
	 */
	protected abstract void inicializarModelo();
	
	/**
	 * Actualiza el modelo con los parámetros de búsqueda
	 */
	protected abstract void actualizarModelo();

	/**
	 * Actualiza la vista, este método
	 */
	protected abstract void actualizarVista();

	

	/**
	 * Actualiza la tabla de búsqueda
	 */
	public void refrescarBusqueda() {
		if (this.isBusquedaHabilitada()){
			RefrescarBusquedaTask task = new RefrescarBusquedaTask(this);
			
			
			task.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					if (arg0.getPropertyName().equals("state")){
						StateValue locValor = (StateValue)arg0.getNewValue();
						if (locValor.equals(StateValue.DONE)){
							setBusquedaHabilitada(true);
							//Se asegura que vuelva a dibujarse lo que haya cambiado
							vista.getTblBusqueda().repaint();
						}
					}
				}
			});

			this.setBusquedaHabilitada(false);
			task.execute();
		}
	}
	
	public boolean isBusquedaHabilitada() {
		return busquedaHabilitada;
	}

	public void setBusquedaHabilitada(boolean busquedaHabilitada) {
		this.busquedaHabilitada = busquedaHabilitada;
		this.vista.setBusquedaHabilitada(busquedaHabilitada);
		this.vista.getBtnBuscar().setEnabled(busquedaHabilitada);
	}

	public M getModelo() {
		return this.modelo;
	}

	public V getVista() {
		return vista;
	}

}
