package ar.gov.cjpmv.prestamos.gui.creditos.garantias;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.gui.configuracion.controllers.ABMConfiguracionControllers;
import ar.gov.cjpmv.prestamos.gui.configuracion.models.AMProvinciaModel;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;

public class PnlAfectarGarantesControllers {
	private PnlDerechaAfectarGarantes vista;
	private AdminCredito adminCredito;
	private Integer numeroCredito;
	private Integer cantidadCuotasVencidas;
	private boolean checkCantidadCtas;
	private boolean checkNumeroCredito; 
	private List<ResultadoGarantia> listaResultado;
	private TblResultadosGarantias modeloTabla;
	

	public PnlAfectarGarantesControllers(AdminCredito adminCredito) {
		super();
		vista = new PnlDerechaAfectarGarantes();
		this.adminCredito= adminCredito;
		this.listaResultado= new ArrayList<ResultadoGarantia>();
	
		this.inicializarVista();
		this.inicializarEventos();
		


	}

	
	
	
	private void inicializarEventos() {
		
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finalizar();
			}

		});
		
		this.vista.getPnlAfectarDesafectarGarantes().getBtnBuscar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();
				disableBtnEditarEliminar();
			}

		});
		
		
		this.vista.getPnlAfectarDesafectarGarantes().getTblResultado().getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(isSelectedRow()){
					enableBtnEditarEliminar();	
				}
			}


			
		});
		
		
		this.vista.getPnlAfectarDesafectarGarantes().getTblResultado().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (isSelectedRow() && e.getClickCount()==2){
					try {
						afectarDesafectar();
					} catch (LogicaException e1) {
						JOptionPane.showMessageDialog(vista, e1.getMessage(), e1.getTitulo(), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		this.vista.getPnlAfectarDesafectarGarantes().getTblResultado().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				if(isSelectedRow() && e.getKeyCode() == KeyEvent.VK_ENTER){
					try {
						afectarDesafectar();
					} catch (LogicaException e1) {
						JOptionPane.showMessageDialog(vista, e1.getMessage(), e1.getTitulo(), JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
		
	
		this.vista.getPnlAfectarDesafectarGarantes().getLblAfectarDesafectar().addMouseListener(new MouseAdapter() {
		
			public void mouseClicked(MouseEvent e) {
				if (isSelectedRow() && e.getClickCount()==1){
					try {
						afectarDesafectar();
					} catch (LogicaException e1) {
						JOptionPane.showMessageDialog(vista, e1.getMessage(), e1.getTitulo(), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
	
	}

	
	private boolean isSelectedRow() {
		return this.vista.getPnlAfectarDesafectarGarantes().getTblResultado().getSelectedRow()!=-1;
	}
	

	private void finalizar() {
		this.adminCredito.mostrarPnlEstadoCuenta();	
	}
	

	private void afectarDesafectar() throws LogicaException {
		int locSeleccionado= this.vista.getPnlAfectarDesafectarGarantes().getTblResultado().getSelectedRow();
		Credito creditoSeleccionado= this.modeloTabla.getResultadoGarantia(locSeleccionado).getCredito();
		ResultadoGarantia locResultadoGarantia= this.modeloTabla.getResultadoGarantia(locSeleccionado);
		CreditoDAOImpl locCreditoDAO= new CreditoDAOImpl();
		if(this.modeloTabla.getResultadoGarantia(locSeleccionado).getTipoGarantia().equals("PERSONAL")){
			AfectarDesafectarGaranteControllers controlador= new AfectarDesafectarGaranteControllers(this.adminCredito.getVista(), locResultadoGarantia);
			controlador.setTitulo("Afectar/Desafectar Garantías Personales - Crédito Nº "+creditoSeleccionado.getNumeroCredito().toString());
			controlador.setLocationRelativeTo(this.adminCredito.getVista());
			controlador.setVisible(true);		
		}
		else{
			//Afectar garantia propietaria
			if(this.modeloTabla.getResultadoGarantia(locSeleccionado).getTipoGarantia().equals("PROPIETARIA")){
				if(!creditoSeleccionado.getCobrarAGarante()){
					String mensaje="¿Realmente desea afectar la garantía propietaria del crédito Nº "+creditoSeleccionado.getNumeroCredito()+"?";
					int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
					if(locConfirmacion==JOptionPane.YES_OPTION){
						creditoSeleccionado.getListaGarantias().get(0).setAfectar(true);
						creditoSeleccionado.setCobrarAGarante(true);
						locCreditoDAO.modificar(creditoSeleccionado);
					}
				}
				else{
					String mensaje="¿Realmente desea desafectar la garantía propietaria del crédito Nº "+creditoSeleccionado.getNumeroCredito()+"?";
					int locConfirmacion= JOptionPane.showConfirmDialog(this.vista, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
					if(locConfirmacion==JOptionPane.YES_OPTION){
						creditoSeleccionado.getListaGarantias().get(0).setAfectar(false);
						creditoSeleccionado.setCobrarAGarante(false);
						locCreditoDAO.modificar(creditoSeleccionado);
					}	
				}
			}
		}
		this.buscar();
	}
	

	private void enableBtnEditarEliminar() {
		this.vista.getPnlAfectarDesafectarGarantes().getLblAfectarDesafectar().setEnabled(true);
	}


	private void disableBtnEditarEliminar() {
		this.vista.getPnlAfectarDesafectarGarantes().getLblAfectarDesafectar().setEnabled(false);
	}

	private void inicializarVista() {
		this.vista.getBtnCancelar().setVisible(false);
		this.vista.getBtnAceptarGuardar().setText("Finalizar");	
		this.vista.getPnlAfectarDesafectarGarantes().getLblAfectarDesafectar().setEnabled(false);
		this.modeloTabla= new TblResultadosGarantias(this.listaResultado);
		this.vista.getPnlAfectarDesafectarGarantes().getTblResultado().setModel(this.modeloTabla);
	}
	
	
	
	

	private void buscar() {
		try{
			this.checkCantidadCtas= this.vista.getPnlAfectarDesafectarGarantes().getRbtnCantidadCuotas().isSelected();
			this.checkNumeroCredito= this.vista.getPnlAfectarDesafectarGarantes().getRbtnNumeroCredito().isSelected();
		
		
			this.actualizarModelo();
		
			if(checkNumeroCredito){
				if(this.numeroCredito!=null){
					this.buscarPorNumeroCredito();
				}
				else{
					int codigo= 71;
					String campo="";
					limpiar();
					throw new LogicaException(codigo, campo);
				}
			}
			else{
				if(checkCantidadCtas){
					if(this.cantidadCuotasVencidas!=null){
						this.buscarPorCuotasAdeudadasVencidas();
					}
					else{
						int codigo= 72;
						String campo="";
						limpiar();
						throw new LogicaException(codigo, campo);
						
					}
				}
				else{
					int codigo= 73;
					String campo="";
					limpiar();
					throw new LogicaException(codigo, campo);
					
				}
			}
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this.vista, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			this.listaResultado.clear();
			this.inicializarVista();
		}
			
	}
	
	
	private void buscarPorCuotasAdeudadasVencidas() {
		this.listaResultado.clear();
		CreditoDAOImpl creditoDAO= new CreditoDAOImpl();
		List<Credito> listaCredito= creditoDAO.getListaCreditoPorCantidadCtasAdeudadas(this.cantidadCuotasVencidas);
		if(listaCredito!=null){
			for(Credito cadaCredito: listaCredito){
				ResultadoGarantia locResultado= new ResultadoGarantia();
				locResultado.setCredito(cadaCredito);
				this.listaResultado.add(locResultado);
			}
			this.actualizarVista();	
		}
		else{
			String mensaje="No se encontro ningún crédito en curso con "+this.cantidadCuotasVencidas+" cuotas adeudadas vencidas.";
			JOptionPane.showMessageDialog(this.vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
			this.listaResultado.clear();
			this.inicializarVista();
		}
		
	}


	private void buscarPorNumeroCredito() {
		this.listaResultado.clear();
		CreditoDAOImpl creditoDAO= new CreditoDAOImpl();
		Credito locCredito= creditoDAO.findCreditoPorNumero(this.numeroCredito);
		if(locCredito!=null){
			if(!locCredito.getListaGarantias().isEmpty()){
				ResultadoGarantia locResultado= new ResultadoGarantia();
				locResultado.setCredito(locCredito);
				this.listaResultado.add(locResultado);
				this.actualizarVista();
			}
			else{
				String mensaje="El credito que intenta buscar no posee garantías.";
				JOptionPane.showMessageDialog(this.vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
				this.listaResultado.clear();
				this.inicializarVista();
			}
		}
		else{
			String mensaje="No se encontro ningún crédito en curso con el número indicado.";
			JOptionPane.showMessageDialog(this.vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
			this.listaResultado.clear();
			this.inicializarVista();
		}
	}




	private void actualizarVista() {
		this.vista.getPnlAfectarDesafectarGarantes().getLblAfectarDesafectar().setEnabled(false);
		this.modeloTabla= new TblResultadosGarantias(this.listaResultado);
		this.vista.getPnlAfectarDesafectarGarantes().getTblResultado().setModel(this.modeloTabla);
	
		
	}




	private void actualizarModelo() throws LogicaException {
		this.cantidadCuotasVencidas= null;
		this.cantidadCuotasVencidas= null;
		if(this.checkCantidadCtas){
			if(this.vista.getPnlAfectarDesafectarGarantes().getSpfCantidadCuotasVencidas().getValue()>0){
				this.cantidadCuotasVencidas= this.vista.getPnlAfectarDesafectarGarantes().getSpfCantidadCuotasVencidas().getValue();
			}
		}
		if(this.checkNumeroCredito){
			if(!this.vista.getPnlAfectarDesafectarGarantes().getTxtNumeroCredito().getText().equals("")){
				try{
					this.numeroCredito= Integer.parseInt(this.vista.getPnlAfectarDesafectarGarantes().getTxtNumeroCredito().getText().trim());
			
				}
				catch(NumberFormatException e){
					int codigo= 74;
					String campo="";
					limpiar();
					throw new LogicaException(codigo, campo);
					
				}
			}
		}
	}




	private void limpiar() {
		this.vista.getPnlAfectarDesafectarGarantes().getRbtnCantidadCuotas().setSelected(false);
		this.vista.getPnlAfectarDesafectarGarantes().getRbtnNumeroCredito().setSelected(false);
		
	}




	public static void main(String[] args){
        JFrame locFrame = new JFrame();
        locFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        PnlAfectarGarantesControllers controller = new PnlAfectarGarantesControllers(null);
    	locFrame.add(controller.vista);
    	locFrame.pack();
    	locFrame.setVisible(true);
    }
	
	public PnlDerechaAfectarGarantes getVista(){
		return this.vista;
	}

	
	
	
	

}
