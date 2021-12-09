/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gov.cjpmv.prestamos.gui.personas.controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.gui.Principal;
import ar.gov.cjpmv.prestamos.gui.personas.AdminPersonaView;
import ar.gov.cjpmv.prestamos.gui.personas.PnlAMPersonaFisicaView;
import ar.gov.cjpmv.prestamos.gui.personas.PnlAMPersonaJuridicaView;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

/**
 *
 * @author mal
 */
public class AdminPersona {
    private AdminPersonaView vista;
    private boolean cerrarAlGuardar = false;
    
    //Esto es una cache de las vistas, cosa que sea más rápido recuperarlas
    private AMPersonaFisicaController amPersonaFisicaController;
    private BusquedaPersonas busquedaPersonas;


	private AMPersonaJuridicaController amPersonaJuridicaController;
    
    public AdminPersona(JFrame pPadre){
        this.vista = new AdminPersonaView(pPadre, false);
        this.inicializarVista();
        this.inicializarEventos();
    }

    private void inicializarVista() {
    	BusquedaPersonas locBusquedaPersonas = this.getBusquedaPersonas();
    	this.vista.setPnlDchaABMView1(locBusquedaPersonas.getVista());		
    	
	}

	private void inicializarEventos() {
        this.vista.getPnlBotoneraPersonaView1().getXhNuevaPersonaFisica().addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		mostrarAltaPersonaFisica();
        	}
        });
        this.vista.getPnlBotoneraPersonaView1().getXhEditarPersonaFisica().addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		mostrarBuscarPersona();
        	}
        });
        
        this.vista.getPnlBotoneraPersonaView1().getXhNuevaPersonaJuridica().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarAltaPersonaJuridica();
				
			}
		});
        this.vista.getPnlBotoneraPersonaView1().getXhEditarPersonaJuridica().addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		mostrarBuscarPersona();
        	}
        });
        
        this.vista.getPnlBotoneraPersonaView1().getXhBeneficiarios().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarReporteBeneficiarios();
			}
		});
        
        this.vista.getPnlBotoneraPersonaView1().getXhEmpleados().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarReporteEmpleados();
				
			}
        	
        });
        
        
        this.vista.getPnlBotoneraPersonaView1().getXhBeneficiariosJOC().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarReporteBeneficiariosJOC();
				
			}
        	
        });
        
        this.vista.getPnlBotoneraPersonaView1().getXhBeneficiariosJI().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarReporteBeneficiariosJI();
				
			}
        	
        });
        
        this.vista.getPnlBotoneraPersonaView1().getXhBeneficiariosJEA().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarReporteBeneficiariosJEA();
				
			}
        	
        });
        
        this.vista.getPnlBotoneraPersonaView1().getXhBeneficiariosP().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarReporteBeneficiariosP();
				
			}
        	
        });
        
        this.vista.getPnlBotoneraPersonaView1().getXhBeneficiariosJA().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarReporteBeneficiariosJA();
				
			}
        	
        });
        
    }

	public void show() {
		Dimension pantalla=Toolkit.getDefaultToolkit().getScreenSize();
		pantalla.height-=30;
        this.vista.setPreferredSize(pantalla);
        this.vista.pack();
        this.vista.setVisible(true);
	}
       
	public static void main(String[] args){
		AdminPersona locAdmin = new AdminPersona(new JFrame());
		locAdmin.vista.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
			
				System.exit(0);
			}
		});
		locAdmin.show();
	}

	public void mostrarEditarPersonaJuridica(PersonaJuridica locPersona) {
		try{
			AMPersonaJuridicaController locPersonaJuridicaController = this.getAMPersonaJuridicaController();
			locPersonaJuridicaController.setCerrarAlGuardar(this.cerrarAlGuardar);
			if (!(this.vista.getPnlDchaABMView1() instanceof PnlAMPersonaJuridicaView)){
				this.vista.setPnlDchaABMView1(locPersonaJuridicaController.getVista());
				locPersonaJuridicaController.setModel(locPersona);
			}
			else if (!locPersonaJuridicaController.getModel().equals(locPersona)){
				locPersonaJuridicaController.cancelar();
				locPersonaJuridicaController.setModel(locPersona);
			}
			locPersonaJuridicaController.getVista().getLblTituloPnlDcha().setText("Persona Jurídica: Edición");
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	public void mostrarEditarPersonaFisica(PersonaFisica pPersona) {
		try{
	//	AMPersonaFisicaController locPersonaFisicaController = this.getAmPersonaFisicaController();
			AMPersonaFisicaController locPersonaFisicaController = this.amPersonaFisicaController = new AMPersonaFisicaController(this.vista, pPersona);
			locPersonaFisicaController.setCerrarAlGuardar(this.cerrarAlGuardar);
			if (!(this.vista.getPnlDchaABMView1() instanceof PnlAMPersonaFisicaView)){
				this.vista.setPnlDchaABMView1(locPersonaFisicaController.getVista());
				
	//locPersonaFisicaController.setModel(pPersona);
				locPersonaFisicaController.getVista().getLblTituloPnlDcha().setText("Persona Física: Edición");
				this.vista.repaint();
			}
			else if (!locPersonaFisicaController.getModel().equals(pPersona)){
				locPersonaFisicaController.cancelar();
				locPersonaFisicaController.setModel(pPersona);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	

	public void mostrarAltaPersonaFisica() {
		try{
		//	AMPersonaFisicaController locPersonaFisicaController = this.getAmPersonaFisicaController();
			AMPersonaFisicaController locPersonaFisicaController = this.amPersonaFisicaController = new AMPersonaFisicaController(this.vista);
			locPersonaFisicaController.setCerrarAlGuardar(this.cerrarAlGuardar);
		//	if (!(this.vista.getPnlDchaABMView1() instanceof PnlAMPersonaFisicaView)){
				this.vista.setPnlDchaABMView1(locPersonaFisicaController.getVista());
				this.vista.repaint();
		/*	}
			else{
				locPersonaFisicaController.cancelar();
			}
		*/
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void mostrarAltaPersonaJuridica(){
		try{
			AMPersonaJuridicaController locPersonaJuridicaController = this.getAMPersonaJuridicaController();
			locPersonaJuridicaController.setCerrarAlGuardar(this.cerrarAlGuardar);
			if (!(this.vista.getPnlDchaABMView1() instanceof PnlAMPersonaJuridicaView)){
				this.vista.setPnlDchaABMView1(locPersonaJuridicaController.getVista());
			}
			else{
				locPersonaJuridicaController.cancelar();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private AMPersonaJuridicaController getAMPersonaJuridicaController() throws ParseException {
		if (this.amPersonaJuridicaController == null){
			this.amPersonaJuridicaController = new AMPersonaJuridicaController(this.vista); 
		}
		return this.amPersonaJuridicaController;
	}

	public AMPersonaFisicaController getAmPersonaFisicaController() throws ParseException {
		if (this.amPersonaFisicaController == null){
			this.amPersonaFisicaController = new AMPersonaFisicaController(this.vista);
			
		}
		return amPersonaFisicaController;
	}


	private void mostrarBuscarPersona() {
		try{
			this.busquedaPersonas = this.getBusquedaPersonas();
			this.vista.setPnlDchaABMView1(this.busquedaPersonas.getVista());
			this.vista.repaint();
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public BusquedaPersonas getBusquedaPersonas() {
		if (this.busquedaPersonas == null){
			this.busquedaPersonas = new BusquedaPersonas();
			this.busquedaPersonas.getLblEditar().addMouseListener(new EditarPersonaListener(this));
			this.busquedaPersonas.getPnlResultadoBusqueda().getTblResultadoBusqueda().addMouseListener(new EditarPersonaListenerConDobleClick(this));
		}
		return this.busquedaPersonas;
	}

	public AdminPersonaView getVista() {
		return vista;
	}
	//----------------------------------------------------ç
	/**
	 * @deprecated no FUNCO! 
	 */
	@Deprecated()
	public Map<String, Object> getDatosBasicosReporte(){
		ResourceMap mapaRecursos = Application.getInstance(Principal.class).getContext().getResourceMap(GestorImpresion.class);
		Icon locIcono = mapaRecursos.getIcon("Reportes.logo");
		Map<String, Object> locMapa = new HashMap<String,Object>();
		locMapa.put("logo",locIcono);
		return locMapa;
	}

	public void mostrarReporteBeneficiarios() {
		try{
			GestorImpresion.imprimirConViewer(this.vista, Reportes.PERSONAS_PASIVAS, null);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void mostrarReporteBeneficiariosJOC(){
		try{
			GestorImpresion.imprimirConViewer(this.vista, Reportes.PERSONAS_PASIVASJOC, null);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void mostrarReporteBeneficiariosJI(){
		try{
			GestorImpresion.imprimirConViewer(this.vista, Reportes.PERSONAS_PASIVASJI, null);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void mostrarReporteBeneficiariosJEA(){
		try{
			GestorImpresion.imprimirConViewer(this.vista, Reportes.PERSONAS_PASIVASJEA, null);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void mostrarReporteBeneficiariosP(){
		try{
			GestorImpresion.imprimirConViewer(this.vista, Reportes.PERSONAS_PASIVASP, null);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void mostrarReporteBeneficiariosJA(){
		try{
			GestorImpresion.imprimirConViewer(this.vista, Reportes.PERSONAS_PASIVASJA, null);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void mostrarReporteEmpleados(){
		try{
			GestorImpresion.imprimirConViewer(this.vista, Reportes.PERSONAS_EMPLEADOS, null);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean isCerrarAlGuardar() {
		return cerrarAlGuardar;
	}

	public void setCerrarAlGuardar(boolean cerrarAlGuardar) {
		this.cerrarAlGuardar = cerrarAlGuardar;
	}
		
}



class EditarPersonaListener extends MouseAdapter{
	private AdminPersona adminPersona;
	
	public EditarPersonaListener(AdminPersona adminPersona) {
		this.adminPersona = adminPersona;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (adminPersona.getBusquedaPersonas().getLblEditar().isEnabled()){
			
			int indiceSeleccionado = adminPersona.getBusquedaPersonas().getPnlResultadoBusqueda()
									.getTblResultadoBusqueda().getSelectedRow();
			Persona locPersona = adminPersona.getBusquedaPersonas().getModelo().getPersonaSeleccionada(indiceSeleccionado);
			if (locPersona instanceof PersonaFisica){
				this.adminPersona.mostrarEditarPersonaFisica((PersonaFisica)locPersona);
			}
			else{
				this.adminPersona.mostrarEditarPersonaJuridica((PersonaJuridica)locPersona);
			}
		}
	}


	


}

class EditarPersonaListenerConDobleClick extends EditarPersonaListener{
	
	public EditarPersonaListenerConDobleClick(AdminPersona adminPersona) {
		super(adminPersona);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2){
			super.mouseClicked(e);
		}
	}
}
