package ar.gov.cjpmv.prestamos.gui.personas.controllers.busqueda;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.gui.configuracion.busquedas.AbstractBusquedaController;
import ar.gov.cjpmv.prestamos.gui.personas.PnlBusquedaPersonaView;
import ar.gov.cjpmv.prestamos.gui.personas.PnlParametrosBusquedaPersonaView;
import ar.gov.cjpmv.prestamos.gui.personas.model.BusquedaPersonasModel;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class BusquedaPersonaController extends AbstractBusquedaController<BusquedaPersonasModel, PnlBusquedaPersonaView>{

	
	public BusquedaPersonaController(PnlBusquedaPersonaView pVista){
		this(new BusquedaPersonasModel(),pVista);
	}
	
	public BusquedaPersonaController(){
		this(new BusquedaPersonasModel());
	}
	
	public BusquedaPersonaController(BusquedaPersonasModel pBusquedaPersonasModel){
		super(pBusquedaPersonasModel,new PnlBusquedaPersonaView());
	}
	
	public BusquedaPersonaController(BusquedaPersonasModel pBusquedaPersonaModel, PnlBusquedaPersonaView pVista){
		super(pBusquedaPersonaModel, pVista);
	}
	
	@Override
	protected void inicializarModelo() {
		PnlParametrosBusquedaPersonaView locPnlParametros = this.getVista().getPnlParametrosBusqueda();
		locPnlParametros.getCbxEstado().setModel(this.getModelo().getCboEstadoPersonaModel());
		locPnlParametros.getCbxEstado().setSelectedItem(null);
		
		locPnlParametros.getCbxTipoDocumento().setModel(this.getModelo().getCboTipoDocumentoModel());
		locPnlParametros.getCbxTipoDocumento().setSelectedItem(null);
		this.getVista().getTblBusqueda().setModel(this.getModelo().getModeloTabla());
		this.actualizarModelo();
	}

	
	public void actualizarVista(){
		PnlParametrosBusquedaPersonaView locPnl = this.getVista().getPnlParametrosBusqueda();
		
		locPnl.getTxtApellidoRazonSocial().setText(this.getModelo().getApellido());
		locPnl.getTxtCuilCuit().setText(Utiles.cadenaVaciaSiNulo(this.getModelo().getCuip()));
		locPnl.getTxtLegajo().setText(Utiles.cadenaVaciaSiNulo(this.getModelo().getLegajo()));
		locPnl.getTxtNumeroDocumento().setText(Utiles.cadenaVaciaSiNulo(this.getModelo().getNumeroDocumento()));
		locPnl.getCbxEstado().setSelectedItem(this.getModelo().getEstado());
		locPnl.getCbxTipoDocumento().setSelectedItem(this.getModelo().getTipoDocumento());		
	}
	
	
	public void actualizarModelo(){
		PnlParametrosBusquedaPersonaView locPnl = getVista().getPnlParametrosBusqueda(); 
		
		String apellido = locPnl.getTxtApellidoRazonSocial().getText();
		
		
		String cadenaCuip = (String)locPnl.getTxtCuilCuit().getValue();
		
		Long cuip = (cadenaCuip==null)?null:Long.parseLong(cadenaCuip);
		
		Long legajo = (Long)locPnl.getTxtLegajo().getValue();
		
		String cadenaNumeroDocumento = (String)locPnl.getTxtNumeroDocumento().getValue();
		Integer numeroDocumento = (cadenaNumeroDocumento==null)?null:Integer.parseInt(cadenaNumeroDocumento);
				
		String estado = (String)locPnl.getCbxEstado().getSelectedItem();
		TipoDocumento tipoDocumento = (TipoDocumento) locPnl.getCbxTipoDocumento().getSelectedItem();
		
		this.getModelo().setApellido(Utiles.nuloSiVacio(apellido));
		this.getModelo().setCuip(cuip);
		this.getModelo().setLegajo(legajo);
		this.getModelo().setNumeroDocumento(numeroDocumento);
		this.getModelo().setEstado(estado);
		this.getModelo().setTipoDocumento(tipoDocumento);
	}
	
	
	
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		BusquedaPersonaController locBusquedaPersonas = new BusquedaPersonaController();
		
		JFrame locFrame = new JFrame();
		locFrame.add(locBusquedaPersonas.getVista(),BorderLayout.CENTER);
		locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		locFrame.pack();
		locFrame.setVisible(true);
	}

	
}
