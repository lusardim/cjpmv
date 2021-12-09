package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.gui.personas.PnlAMPersonaJuridicaView;
import ar.gov.cjpmv.prestamos.gui.personas.model.AMPersonaJuridicaModel;

public class AMPersonaJuridicaController {
	private boolean cerrarAlGuardar = false;
	private JDialog padre;
	private PnlAMPersonaJuridicaView vista;
	private AMPersonaJuridicaModel modelo;
	
	private PnlContactoController datosContactoController;
	private PnlDatosInstitucionalesController datosInstitucionalesController;
	
	private PersonaDAO personaDAO;

	public AMPersonaJuridicaController(JDialog pPadre, PersonaJuridica pModelo) throws ParseException{
		this.personaDAO = new PersonaDAO();
		this.padre = pPadre;
		this.vista = new PnlAMPersonaJuridicaView();
		this.vista.getLblTituloPnlDcha().setText("Persona Jurídica: Edición");
		this.modelo = new AMPersonaJuridicaModel(pModelo);	
		this.inicializarPaneles();
		this.actualizarVista();
		this.inicializarEventos();
		this.vista.getBtnImprimir().setVisible(false);
	}

	public AMPersonaJuridicaController(JDialog pPadre) throws ParseException{
		this(pPadre,new PersonaJuridica());
		this.vista.getLblTituloPnlDcha().setText("Persona Jurídica: Nuevo");
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

	private void actualizarModelo(){
		this.datosInstitucionalesController.actualizarModelo();
		this.datosContactoController.getDomicilioController().actualizarModelo();
		this.modelo.getPersona().setDomicilio(this.datosContactoController.getDomicilioController().getDomicilio());
		this.modelo.getPersona().setEmails(this.datosContactoController.getEmailController().getModelo());
		this.modelo.getPersona().setListaTelefonos(this.datosContactoController.getTelefonoController().getModelo());
	}
	
	private void actualizarVista()  throws ParseException{
		this.datosInstitucionalesController.setModelo(this.modelo.getPersona());
		this.datosInstitucionalesController.actualizarVista();
		this.datosContactoController.getDomicilioController().setDomicilio(this.modelo.getPersona().getDomicilio());
		this.datosContactoController.getEmailController().setModelo(this.modelo.getPersona().getEmails());
		this.datosContactoController.getTelefonoController().setModelo(this.modelo.getPersona().getListaTelefonos());
	}

	private void inicializarPaneles() throws ParseException {
		this.datosContactoController = new PnlContactoController(this.vista.getPnlDatosContacto(), this.padre);
		this.datosInstitucionalesController = new PnlDatosInstitucionalesController(this.vista.getPnlDatosInstitucionales(), this.padre,this.modelo.getPersona());
		
	}
	
	private void guardar() {
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
				this.vista.getTbpABM().setSelectedComponent(this.vista.getPnlDatosInstitucionales());	
			}
		}
		catch(LogicaException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		} catch (ParseException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "Ha ocurrido un error al actualizar los datos de la interfaz, " +
					"intente nuevamente más tarde","Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void limpiarDatos() throws ParseException {
		this.modelo.setPersona(new PersonaJuridica());
	}

	public void cancelar() {
		try{
			this.limpiarDatos();
		}
		catch(ParseException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "Ha ocurrido un error al actualizar los datos de la interfaz, " +
					"intente nuevamente más tarde","Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}

	
	
public static void main(String[] args) throws ParseException{
		
		EntityManager locEM = GestorPersitencia.getInstance().getEntityManager();
		
		PersonaJuridica locPersonaJuridica = (PersonaJuridica)new PersonaDAO().getObjetoPorId(6l);
		
		if (locPersonaJuridica == null){
			locEM.close();
			System.exit(2);
		}
	
		JDialog locDialog = new JDialog();  
		//locDialog.add(new AMPersonaFisicaController(locDialog,locPersonaFisica).getVista());
		locDialog.add(new AMPersonaJuridicaController(locDialog,locPersonaJuridica).vista);
		locDialog.addWindowListener(new WindowAdapter(){public void windowClosed(WindowEvent arg0) {System.exit(0);}});
		locDialog.pack();
		
		locDialog.setVisible(true);
//		locEM.close();
		
	}

	public PnlAMPersonaJuridicaView getVista() {
		return this.vista;
	}

	public void setModel(PersonaJuridica pPersona) {
		this.modelo.setPersona(pPersona);
		
	}

	public PersonaJuridica getModel() {
		return this.modelo.getPersona();
	}

	public boolean isCerrarAlGuardar() {
		return cerrarAlGuardar;
	}

	public void setCerrarAlGuardar(boolean cerrarAlGuardar) {
		this.cerrarAlGuardar = cerrarAlGuardar;
	}


}
