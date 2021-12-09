/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gov.cjpmv.prestamos.gui.creditos.cheques;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream.GetField;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import ar.gov.cjpmv.prestamos.core.business.dao.ChequeDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.gui.comunes.MontoCellRenderer;
import ar.gov.cjpmv.prestamos.gui.comunes.impresion.ImpresionChequeCommand;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.ImprimirChequeDialogController;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.ReemplazarChequeDialogController;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

/**
 *
 * @author pulpol
 */
public class PnlAdministracionChequesController {

    private AdministracionChequeTblModel model;
    private PnlAdministracionCheques vista;
    private ChequeDAO chequeDao;
    private JDialog vistaPadre;
    
    public PnlAdministracionChequesController(JDialog vistaPadre){
    	this.vistaPadre = vistaPadre; 
    	chequeDao = new ChequeDAO();
        model = new AdministracionChequeTblModel();
        vista = new PnlAdministracionCheques();
        this.inicializarVista();
    }

    private void inicializarVista() {
    	vista.getTblResultadoBusqueda().setModel(model);
    	//Ordenamiento de la tabla
    	DefaultTableCellRenderer renderer =(DefaultTableCellRenderer)vista.
    									getTblResultadoBusqueda().
    									getDefaultRenderer(Integer.class);
    	renderer.setHorizontalAlignment(SwingConstants.LEFT);

    	vista.getTblResultadoBusqueda().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	vista.getTblResultadoBusqueda().setDefaultRenderer(EstadoCheque.class, new EstadoChequeCellRenderer());
    	vista.getTblResultadoBusqueda().setDefaultRenderer(Float.class, new MontoCellRenderer());
    	habilitarLabeles();

    	vista.getBtnBusqueda().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
    	});
    	
    	vista.getLblCancelarCheque().addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			eliminar();
    		}
		});

    	vista.getLblImprimir().addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			imprimir();
    		}
		});
    	
    	vista.getLblAgregarCheque().addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			int filaSeleccionada = vista.getTblResultadoBusqueda().getSelectedRow();
    			if (filaSeleccionada != -1){
    				Cheque chequeSeleccionado = model.getCheque(filaSeleccionada);
    				agregar(chequeSeleccionado);
    			}
    		}
		});
    	
    	vista.getTblResultadoBusqueda().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				habilitarLabeles();
			}
		});
    	
    	KeyListener btnEnterKeyListener = new KeyAdapter() {
    		@Override
    		public void keyPressed(KeyEvent e) {
    			if (e.getKeyCode() == KeyEvent.VK_ENTER){
    				buscar();
    			}
    		}
		};
    	vista.getTxtApellido().addKeyListener(btnEnterKeyListener);
    	vista.getTxtNumeroCheque().addKeyListener(btnEnterKeyListener);
	}
    	
    private void habilitarLabeles() {
    	int filaSeleccionada = vista.getTblResultadoBusqueda().getSelectedRow();
    	boolean hayChequeSeleccionado = filaSeleccionada!=-1;
   	
    	vista.getLblCancelarCheque().setEnabled(false);
    	vista.getLblAgregarCheque().setEnabled(false);
		vista.getLblImprimir().setEnabled(false);
		
   		if (hayChequeSeleccionado){
   			Cheque cheque = model.getCheque(filaSeleccionada);
   			EstadoCheque estado = cheque.getEstadoCheque();
   			if (estado.equals(EstadoCheque.CANCELADO)){
   				if (cheque.getCanceladoPor()==null){
   					vista.getLblAgregarCheque().setEnabled(true);
   				}
   			}
   			else{
   				vista.getLblImprimir().setEnabled(true);
   				vista.getLblCancelarCheque().setEnabled(true);
   			}
   		}
	}

    private void imprimir() {
    	try {
    		int fila = vista.getTblResultadoBusqueda().getSelectedRow();
    		Cheque cheque = model.getCheque(fila);
    		
    		ImprimirChequeDialogController controller = new ImprimirChequeDialogController(this.vistaPadre, cheque);
    		controller.setVisible(true);
    		
			model.fireTableRowsUpdated(fila,fila);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(vista, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void eliminar() {
		try{
			int fila = vista.getTblResultadoBusqueda().getSelectedRow();
			Cheque cheque = model.getCheque(fila);
			
			int respuesta = confirmarEliminacionCheque(cheque); 
			if (respuesta == JOptionPane.YES_OPTION){
				chequeDao.cancelar(cheque);
				if (cheque.getEstadoCheque().equals(EstadoCheque.PENDIENTE_IMPRESION)){
					model.getCheques().remove(cheque);
					model.fireTableRowsDeleted(fila, fila);
				}
				else{
					model.fireTableRowsUpdated(fila, fila);

					respuesta = confirmarAgregarCheque(cheque);
					if (respuesta == JOptionPane.YES_OPTION){
						agregar(cheque);
					}
				}
			}
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(vista, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(vista, 
					"Ha ocurrido un error al intentar cancelar el cheque",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void agregar(Cheque pCheque) {
		ReemplazarChequeDialogController chequeDialogController = new ReemplazarChequeDialogController(vistaPadre, pCheque);
		chequeDialogController.setVisible(true);
		buscar();
	}

	private int confirmarAgregarCheque(Cheque cheque) {
		return JOptionPane.showConfirmDialog(vista, "¿Desea crear un nuevo cheque en reemplazo de "+cheque+"?", 
				"Confirmación",
				JOptionPane.YES_NO_OPTION);
	}

	private int confirmarEliminacionCheque(Cheque pCheque) {
		String pregunta;
		if (pCheque.getEstadoCheque().equals(EstadoCheque.PENDIENTE_IMPRESION)){
			pregunta = "¿Desea eliminar el cheque seleccionado?";
		}
		else{
			pregunta = "¿Desea cancelar el cheque seleccionado?";	
		}
		return JOptionPane.showConfirmDialog(vista,
						  		pregunta,
						  		"Confirmación",
						  		JOptionPane.YES_NO_OPTION);
	}

	public void buscar(){
        this.actualizarModelo();
        this.model.setCheques(getListaCheques());
        habilitarLabeles();
    }

    private List<Cheque> getListaCheques() {
    	return this.chequeDao.findListaCheques(model.getApellido(), model.getNumeroCheque());
	}

    private void actualizarModelo() {
        String apellido = this.vista.getTxtApellido().getText().trim();
        String numero = this.vista.getTxtNumeroCheque().getText().trim();
        
        this.model.setApellido(Utiles.nuloSiVacio(apellido));
        numero = Utiles.nuloSiVacio(numero);
        
        if ( (numero!=null) && (Utiles.isLong(numero)) ){
                this.model.setNumeroCheque(new Integer(numero));        		
       	}
       	else{
       		this.model.setNumeroCheque(null);
       		this.vista.getTxtNumeroCheque().setText("");
       	}
    }

    public static void main(String[] args){
        JDialog dialogo = new JDialog();
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        PnlAdministracionChequesController controller = new PnlAdministracionChequesController(dialogo);
        dialogo.add(controller.vista);
        dialogo.pack();
        dialogo.setVisible(true);
    }

	public PnlAdministracionCheques getVista() {
		return vista;
	}
}
