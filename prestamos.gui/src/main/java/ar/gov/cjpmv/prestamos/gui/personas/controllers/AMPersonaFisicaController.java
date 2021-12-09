package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;
import ar.gov.cjpmv.prestamos.gui.personas.PnlAMPersonaFisicaView;
import ar.gov.cjpmv.prestamos.gui.personas.PnlDatosLaboralesView;
import ar.gov.cjpmv.prestamos.gui.personas.PnlDatosPrevisionalesView;
import ar.gov.cjpmv.prestamos.gui.personas.model.AMPersonaFisicaModel;

public class AMPersonaFisicaController {
	private JDialog padre;
	private boolean cerrarAlGuardar = false;
	
	private PnlAMPersonaFisicaView vista;
	private AMPersonaFisicaModel modelo;
	
	private PnlContactoController contactoController;
	private PnlDatosPersonalesController datosPersonalesController;
	private PnlDatosPrevisionalesView pnlDatosPrevisionales;	
	private PnlDatosLaboralesView datosLaboralesView;
	
	private PersonaDAO personaDAO;
	
	public AMPersonaFisicaController(JDialog pPadre) throws ParseException{
		this(pPadre,new PersonaFisica());
		this.vista.getLblTituloPnlDcha().setText("Persona Física: Nuevo");
		this.vista.getBtnImprimir().setVisible(false);
	}
	
	public AMPersonaFisicaController(JDialog pPadre, PersonaFisica pPersona) throws ParseException{
		this.padre = pPadre;
		this.vista = new PnlAMPersonaFisicaView();
		this.vista.getLblTituloPnlDcha().setText("Persona Física: Edición");
		this.modelo = new AMPersonaFisicaModel(pPersona);
		
		this.inicializarPaneles();
		this.actualizarVista();
		this.inicializarEventos();
		this.personaDAO = new PersonaDAO();
	}
	
	private void inicializarEventos() {
		this.modelo.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					actualizarVista();
				} catch (ParseException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(vista,"Ha ocurrido un error al intentar cargar los datos personales, " +
							"Intente nuevamente más tarde",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
		});
		
		this.vista.getBtnCancelar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();			
			}
		});
	}

	
	public void cancelar(){
		try{
			String locMensaje = "¿Está seguro que desea cancelar la acción? Se perderán los datos ingresados";
			int valor = JOptionPane.showConfirmDialog(this.padre,locMensaje,"Cancelar", JOptionPane.YES_NO_OPTION);
			if (valor == JOptionPane.YES_OPTION){
				this.modelo.setPersona(new PersonaFisica());
			}
			this.vista.getTbpABM().setSelectedComponent(this.vista.getPnlDatosPersonalesView());
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.padre, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void inicializarPaneles() throws ParseException {
		this.contactoController = new PnlContactoController(this.vista.getPnlDatosContactoView(),this.padre);
		this.datosPersonalesController = new PnlDatosPersonalesController(this.vista.getPnlDatosPersonalesView(),this.padre,modelo);
		this.pnlDatosPrevisionales = new PnlDatosPrevisionalesView();
		this.vista.getPnlDatosPrevisionalesView().inicializarPanel(this.padre, modelo);
		this.vista.getPnlDatosLaborales().inicializarPanel(this.padre, modelo);
	
	}
	
	public PnlAMPersonaFisicaView getVista() {
		return vista;
	}
	
	public void actualizarModelo() throws LogicaException {
		this.datosPersonalesController.actualizarModelo();
		this.vista.getPnlDatosLaborales().actualizarModelo();
		this.contactoController.getDomicilioController().actualizarModelo();
		this.modelo.getPersona().setDomicilio(this.contactoController.getDomicilioController().getDomicilio());
		this.modelo.getPersona().setEmails(this.contactoController.getEmailController().getModelo());
		this.modelo.getPersona().setListaTelefonos(this.contactoController.getTelefonoController().getModelo());
	

		List<Empleo> empleos = this.vista.getPnlDatosLaborales().getListaEmpleo();
		if(empleos == null) {
			modelo.getPersona().getListaEmpleos().clear();
		}
		else {
			modelo.getPersona().getListaEmpleos().clear();
			modelo.getPersona().getListaEmpleos().addAll(empleos);
		}
	}
	
	
	public void actualizarVista() throws ParseException{
		this.contactoController.getDomicilioController().setDomicilio(this.modelo.getPersona().getDomicilio());
		this.contactoController.getEmailController().setModelo(this.modelo.getPersona().getEmails());
		this.contactoController.getTelefonoController().setModelo(this.modelo.getPersona().getListaTelefonos());
		this.datosPersonalesController.actualizarVista();
	}
	
	 
	public void guardar(){
		try{
			this.actualizarModelo();
			if (this.modelo.getPersona().getId()==null){
				this.personaDAO.agregar(this.modelo.getPersona());
			}
			else{
				this.personaDAO.modificar(this.modelo.getPersona());
			}
			
			JOptionPane.showMessageDialog(this.padre,"La persona ha sido agregada correctamente.", "Agregar",JOptionPane.INFORMATION_MESSAGE);
			this.limpiarDatos();
			if (this.cerrarAlGuardar){
				this.padre.dispose();
			}
			else{
				this.vista.getTbpABM().setSelectedComponent(this.vista.getPnlDatosPersonalesView());
			}
		}
		catch(LogicaException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void limpiarDatos() {
		this.modelo.setPersona(new PersonaFisica());	
	}

	public void setModel(PersonaFisica pPersonaFisica){
		this.modelo.setPersona(pPersonaFisica);
	}
	
	public static void main(String[] args) throws ParseException{
		
//		EntityManager locEM = GestorPersitencia.getInstance().getEntityManager();
//		
//		PersonaFisica locPersonaFisica = (PersonaFisica)new PersonaDAO().getObjetoPorId(4l);
//		
//		if (locPersonaFisica == null){
//			locEM.close();
//			System.exit(2);
//		}
//		
		JDialog locDialog = new JDialog();  
		//locDialog.add(new AMPersonaFisicaController(locDialog,locPersonaFisica).getVista());
		locDialog.add(new AMPersonaFisicaController(locDialog).getVista());
		locDialog.addWindowListener(new WindowAdapter(){public void windowClosed(WindowEvent arg0) {System.exit(0);}});
		locDialog.pack();
		locDialog.setVisible(true);
//		locEM.close();
		
	}

	public PersonaFisica getModel() {
		return this.modelo.getPersona();
	}

	public boolean isCerrarAlGuardar() {
		return cerrarAlGuardar;
	}

	public void setCerrarAlGuardar(boolean cerrarAlGuardar) {
		this.cerrarAlGuardar = cerrarAlGuardar;
	}
}
