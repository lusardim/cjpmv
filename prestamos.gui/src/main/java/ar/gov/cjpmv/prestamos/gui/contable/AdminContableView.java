/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AdminContableView.java
 *
 * Created on 29/06/2011, 23:27:38
 */

package ar.gov.cjpmv.prestamos.gui.contable;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.jdesktop.swingx.JXHyperlink;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;

/**
 *
 * @author dernst
 */
public class AdminContableView extends javax.swing.JDialog {

	private static final long serialVersionUID = 2754860574620075297L;

	/** Creates new form AdminContableView */
    public AdminContableView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    	initComponents();
        cargarPanelesSegundoPlano();
    }

	private void cargarPanelesSegundoPlano() {
    	SwingWorker<Void, Void> segundoPlano = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				getXhAdministracionPlanCuenta().setEnabled(false);
				getXhAdministracionEjercicio().setEnabled(false);
				getXhAdministacionAsientoContable().setEnabled(false);
				
				pnlAdminPlanCuentas = new PnlAdminPlanCuentas();
				pnlAdminEjercicioContable= new PnlEjercicioContable(AdminContableView.this);
				pnlABMEjercicioContable= new PnlABMEjercicioContable(AdminContableView.this);
				pnlAdminAsientoContable= new PnlBusquedaAsientoContable(AdminContableView.this);
				pnlABMAsientoContable= new PnlABMAsientoContable(AdminContableView.this);
				inciarEventos();
				mostrarAdminEjercicioContable();
				return null;
			}

			@Override
			protected void done() {
				try {
					get();
				} catch (Exception e) {
					e.printStackTrace();
				}

				getXhAdministracionPlanCuenta().setEnabled(true);
				getXhAdministracionEjercicio().setEnabled(true);
				getXhAdministacionAsientoContable().setEnabled(true);
				inicializarEventos();
			}
    	};
		segundoPlano.execute();
	}

	private void inicializarEventos() {
		getXhAdministracionPlanCuenta().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarAdminPlanCuentas();
			}
		});
		
		getXhAdministracionEjercicio().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarAdminEjercicioContable();
			}
		});
		
		getXhAdministacionAsientoContable().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarAdminAsientoContable();				
			}
		});
	}

	/**
	 * Muestra la administración de planes de cuenta a la derecha
	 */
	public void mostrarAdminPlanCuentas() {
		this.spPrincipal.setRightComponent(this.pnlAdminPlanCuentas);
		this.spPrincipal.repaint();
		this.spPrincipal.getRightComponent().repaint();
	}
	
	public void mostrarAdminEjercicioContable() {
		this.spPrincipal.setRightComponent(this.pnlAdminEjercicioContable);
		this.pnlAdminEjercicioContable.buscar();
		this.spPrincipal.repaint();
		this.spPrincipal.getRightComponent().repaint();
	}
	
	public void mostrarABMEjercicioContable() {
		this.spPrincipal.setRightComponent(this.pnlABMEjercicioContable);
		this.spPrincipal.repaint();
		this.spPrincipal.getRightComponent().repaint();
	
	}
	
	public void actualizarListaAsiento(){
		this.pnlAdminAsientoContable.buscarAsiento();
	}

	public void mostrarAdminAsientoContable() {
		this.spPrincipal.setRightComponent(this.pnlAdminAsientoContable);
		this.spPrincipal.repaint();
		this.spPrincipal.getRightComponent().repaint();		
	}
	
	public void mostrarABMAsientoContable() {
		try {
			pnlABMAsientoContable.actualizarPlanCuenta();
			this.spPrincipal.setRightComponent(this.pnlABMAsientoContable);
			this.spPrincipal.repaint();
			this.spPrincipal.getRightComponent().repaint();
		}
		catch(LogicaException e) {
			JOptionPane.showMessageDialog(
					this,
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	

	
	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spPrincipal = new javax.swing.JSplitPane();
        javax.swing.JScrollPane spIzquierda = new javax.swing.JScrollPane();
        pnlBotoneraContable = new ar.gov.cjpmv.prestamos.gui.contable.PnlBotoneraContable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        spPrincipal.setDividerSize(2);
        spPrincipal.setName("spPrincipal"); // NOI18N

        spIzquierda.setMinimumSize(new java.awt.Dimension(270, 717));
        spIzquierda.setName("spIzquierda"); // NOI18N

        pnlBotoneraContable.setName("pnlBotoneraContable"); // NOI18N
        spIzquierda.setViewportView(pnlBotoneraContable);

        spPrincipal.setLeftComponent(spIzquierda);
        spPrincipal.setRightComponent(new JPanel());
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 569, Short.MAX_VALUE)
        );
        
        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AdminContableView dialog = new AdminContableView(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                Dimension pantalla=Toolkit.getDefaultToolkit().getScreenSize();
        		pantalla.height-=30;
                dialog.setPreferredSize(pantalla);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ar.gov.cjpmv.prestamos.gui.contable.PnlBotoneraContable pnlBotoneraContable;
    private javax.swing.JSplitPane spPrincipal;
    // End of variables declaration//GEN-END:variables
    
    private PnlAdminPlanCuentas pnlAdminPlanCuentas;
    private PnlEjercicioContable pnlAdminEjercicioContable;
    private PnlABMEjercicioContable pnlABMEjercicioContable;
    private PnlBusquedaAsientoContable pnlAdminAsientoContable;
    private PnlABMAsientoContable pnlABMAsientoContable;
    
    public PnlABMEjercicioContable getPnlABMEjercicioContable() {
    	return this.pnlABMEjercicioContable;
    }
    
    public PnlABMAsientoContable getPnlABMAsientoContable() {
    	return this.pnlABMAsientoContable;
    }
    
	public javax.swing.JSplitPane getSpPrincipal() {
		return spPrincipal;
	}
	
	public void setSpPrincipal(javax.swing.JSplitPane spPrincipal) {
		this.spPrincipal = spPrincipal;
	}

	public ar.gov.cjpmv.prestamos.gui.contable.PnlBotoneraContable getPnlBotoneraContableView() {
		return pnlBotoneraContable;
	}
	
	
	public JXHyperlink getXhAdministracionPlanCuenta() {
		return this.getPnlBotoneraContableView().getXhAdministracionPlanCuenta();
	}

	
	public JXHyperlink getXhAdministracionEjercicio(){
		return this.getPnlBotoneraContableView().getXhAdministracionEjercicio();
	}	
	

	public JXHyperlink getXhAdministacionAsientoContable(){
		return this.getPnlBotoneraContableView().getXhAdministracionAsientoContable();
	}
	
	public void mostrar() {
		Dimension pantalla=Toolkit.getDefaultToolkit().getScreenSize();
		pantalla.height -= 30;
        this.setPreferredSize(pantalla);
        this.pack();
        this.setVisible(true);
       
	}

	private void inciarEventos() {
		this.pnlAdminPlanCuentas.getBtnCancelarTodo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlAdminPlanCuentas.cancelar();
				mostrarAdminEjercicioContable();
			}
		});
	}

}