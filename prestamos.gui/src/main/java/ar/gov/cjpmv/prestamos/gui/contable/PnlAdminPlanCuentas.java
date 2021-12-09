/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlAdminPlanCuentas.java
 *
 * Created on 11/07/2011, 22:10:10
 */
package ar.gov.cjpmv.prestamos.gui.contable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import ar.gov.cjpmv.prestamos.core.business.AdministradorPlanCuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;
import ar.gov.cjpmv.prestamos.gui.AdministracionFactory;
import ar.gov.cjpmv.prestamos.gui.contable.modelos.CuentaTreeNodeModel;
import ar.gov.cjpmv.prestamos.gui.contable.modelos.PlanCuentaTreeModel;

/**
 *
 * @author daiana
 */
public class PnlAdminPlanCuentas extends javax.swing.JPanel {

	private static final long serialVersionUID = -8055548325791461088L;
	private AdministradorPlanCuenta adminPlanCuenta;
	/** Creates new form PnlAdminPlanCuentas */
    public PnlAdminPlanCuentas() {
    	this.adminPlanCuenta = AdministracionFactory.getInstance().getAdministradorPlanCuenta();
        initComponents();
        inicializarModelos();
        inicializarEventos();
    }

    public void leerAnio() {
    	final int anio = (Integer)pnlPlanIzda.getSpAnio().getValue();
    	SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				pnlPlanIzda.getPanelFiltradoPlanCuenta().setEnabled(false);
				try {
					PlanCuenta planCuenta = adminPlanCuenta.getPlanCuentaPorAnio(anio);
					pnlPlanIzda.getPanelFiltradoPlanCuenta().cargarPlanCuenta(planCuenta);	
					btnAceptarTodo.setEnabled(false);
					btnCancelarTodo.setEnabled(false);
				}	
				catch (Exception e) {
					JOptionPane.showMessageDialog(PnlAdminPlanCuentas.this,
							"Ha ocurrido un error al intentar recuperar el período",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				return null;
			}
    	};
    	worker.execute();
    }
    
    private void inicializarEventos() 
    {
    	this.pnlPlanIzda.gettArbol()
    		.addMouseListener(new MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent e) {
    				if (e.getClickCount() == 2) {
    					DefaultMutableTreeNode node = (DefaultMutableTreeNode) pnlPlanIzda.gettArbol() 
    											.getLastSelectedPathComponent();
    					if (node != null && !node.isRoot()) {
    						seleccionarCuenta();
    					}
    				}
    			}
    		});
      	
    	this.pnlPlanIzda.getBtnAgregar().addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				agregarCuenta();
    			}
        	});
      	
      	this.pnlPlanIzda.getBtnEliminar().addActionListener(new ActionListener() {
      		@Override
      		public void actionPerformed(ActionEvent arg0) {
      			eliminarCuenta();      			
      		}
      	});
      	
    	this.pnlABMCuenta.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("cuenta")) {
					pnlPlanIzda.actualizarModeloPlanCuenta();
					if (pnlABMCuenta.getCamino() != null) {
						pnlPlanIzda.gettArbol().setSelectionPath(
								new TreePath(pnlABMCuenta.getCamino()));
					}
					btnAceptarTodo.setEnabled(true);
					btnCancelarTodo.setEnabled(true);
				}
			}
		});
    	
    	pnlPlanIzda.getSpAnio().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				leerAnio();
			}
		});
    	
    	btnAceptarTodo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});
    	
    	pnlPlanIzda.getPnlCopiaPlanCuentas()
    			   .getBtnActualizar()
    			   .addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				copiarPlanCuenta();				
			}
		});
	}

	private void guardar() {
		try {
			PlanCuentaTreeModel modelo = (PlanCuentaTreeModel)pnlPlanIzda.gettArbol().getModel();
			PlanCuenta planCuenta = modelo.getRoot().getUserObject();
			this.adminPlanCuenta.guardarPlanCuenta(planCuenta);
			JOptionPane.showMessageDialog(
						this,
						"El plan de cuenta se a actualizado correctamente", 
						"Plan cuenta "+planCuenta,
						JOptionPane.INFORMATION_MESSAGE);
			this.pnlPlanIzda.getPnlCopiaPlanCuentas().actualizarListaPlanes();
			btnAceptarTodo.setEnabled(false);
			btnCancelarTodo.setEnabled(false);
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

    private void eliminarCuenta() {
    	DefaultMutableTreeNode nodo = getNodoSeleccionado();
    	if (nodo != null && nodo.getUserObject() instanceof Cuenta) {
    		int seleccion = JOptionPane.showConfirmDialog(this, 
    					"¿Esta segudo que desea quitar la cuenta seleccionada?",
    					"Eliminar cuenta",
    					JOptionPane.YES_NO_OPTION);
    		
    		if (seleccion == JOptionPane.YES_OPTION) {
    			Cuenta cuenta = (Cuenta)nodo.getUserObject();
    			if (this.adminPlanCuenta.tieneAsientos(cuenta)) { 
    				JOptionPane.showMessageDialog(this,
    						"La cuenta "+cuenta+" o alguna cuenta dependente tiene asientos registrados. No puede ser eliminada",
    						"Error",
    						JOptionPane.ERROR_MESSAGE
    						);
    			}
    			else {
    				PlanCuentaTreeModel modelo = (PlanCuentaTreeModel)pnlPlanIzda.gettArbol().getModel();
    				modelo.removeNodeFromParent(nodo);
    				pnlPlanIzda.gettArbol().repaint();
    				btnAceptarTodo.setEnabled(true);
    				btnCancelarTodo.setEnabled(true);
    			}
    		}
    	}
	}
    
	private void agregarCuenta() {
		DefaultMutableTreeNode nodo = getNodoSeleccionado();
		if (nodo != null) {
			TreeNode[] camino = nodo.getPath();
			Cuenta cuentaHija = new Cuenta();
			if (nodo instanceof CuentaTreeNodeModel) {
				Cuenta padre = ((CuentaTreeNodeModel)nodo).getUserObject();
				cuentaHija.setPadre(padre);
			}
			pnlABMCuenta.setCuenta(cuentaHija,camino);
		}
	}
    
	private DefaultMutableTreeNode getNodoSeleccionado() {
		return (DefaultMutableTreeNode) pnlPlanIzda.gettArbol()
				.getLastSelectedPathComponent();
	}
	
	private void seleccionarCuenta() {
		DefaultMutableTreeNode nodo = getNodoSeleccionado();
		if (nodo != null) {
			pnlABMCuenta.setCuenta((Cuenta)nodo.getUserObject(),nodo.getPath());
		}
	}

	private void inicializarModelos() {
    	leerAnio();
    	this.pnlPlanIzda.gettArbol()
    		.getSelectionModel()
    		.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	public void cancelar() {
		pnlABMCuenta.cancelar();
		pnlPlanIzda.getPanelFiltradoPlanCuenta().cargarPlanCuenta(null);
		this.inicializarModelos();
	}
	
	public javax.swing.JButton getBtnCancelarTodo() {
		return btnCancelarTodo;
	}
		
	private void copiarPlanCuenta() {
		try {
			PlanCuenta planCuentaOriginal = (PlanCuenta)pnlPlanIzda
						.getPnlCopiaPlanCuentas()
						.getCbxPeriodo().getSelectedItem();
			PlanCuenta planCuentaNuevo =  getPlanCuenta();
			if (planCuentaOriginal != null && !planCuentaOriginal.equals(planCuentaNuevo)) {
				EjercicioContable ejercicio = planCuentaNuevo.getPeriodo();
				planCuentaNuevo = planCuentaOriginal.clone();
				planCuentaNuevo.setPeriodo(ejercicio);
				pnlPlanIzda.gettArbol().setModel(new PlanCuentaTreeModel(planCuentaNuevo));
			}
		}
		catch(CloneNotSupportedException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					this, 
					"No se ha podido duplicar el plan de cuentas", 
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private PlanCuenta getPlanCuenta() {
		PlanCuentaTreeModel modelo = (PlanCuentaTreeModel)pnlPlanIzda.gettArbol().getModel();
		return modelo.getRoot().getUserObject();
	}
	/** This method is called from wi 0 thin the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        javax.swing.JSplitPane spAdminPlan = new javax.swing.JSplitPane();
        lblTituloPnlDcha = new javax.swing.JLabel();
        btnAceptarTodo = new javax.swing.JButton();
        btnCancelarTodo = new javax.swing.JButton();
        btnAceptarTodo.setEnabled(false);
		btnCancelarTodo.setEnabled(false);
        pnlABMCuenta = new ar.gov.cjpmv.prestamos.gui.contable.PnlABMCuenta();
        pnlPlanIzda = new ar.gov.cjpmv.prestamos.gui.contable.PnlPlanIzda();
		
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlAdminPlanCuentas.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N

        spAdminPlan.setBorder(null);
        spAdminPlan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        spAdminPlan.setName("spAdminPlan"); // NOI18N

        pnlABMCuenta.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlABMCuenta.setName("pnlABMCuenta"); // NOI18N
        spAdminPlan.setRightComponent(pnlABMCuenta);

        pnlPlanIzda.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlPlanIzda.setName("pnlPlanIzda"); // NOI18N
        spAdminPlan.setLeftComponent(pnlPlanIzda);

        lblTituloPnlDcha.setFont(resourceMap.getFont("lblTituloPnlDcha.font")); // NOI18N
        lblTituloPnlDcha.setForeground(resourceMap.getColor("lblTituloPnlDcha.foreground")); // NOI18N
        lblTituloPnlDcha.setIcon(resourceMap.getIcon("lblTituloPnlDcha.icon")); // NOI18N
        lblTituloPnlDcha.setText(resourceMap.getString("lblTituloPnlDcha.text")); // NOI18N
        lblTituloPnlDcha.setName("lblTituloPnlDcha"); // NOI18N

        btnAceptarTodo.setFont(resourceMap.getFont("btnAceptarTodo.font")); // NOI18N
        btnAceptarTodo.setForeground(resourceMap.getColor("btnAceptarTodo.foreground")); // NOI18N
        btnAceptarTodo.setIcon(resourceMap.getIcon("btnAceptarTodo.icon")); // NOI18N
        btnAceptarTodo.setText(resourceMap.getString("btnAceptarTodo.text")); // NOI18N
        btnAceptarTodo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptarTodo.setName("btnAceptarTodo"); // NOI18N

        btnCancelarTodo.setFont(resourceMap.getFont("btnCancelarTodo.font")); // NOI18N
        btnCancelarTodo.setForeground(resourceMap.getColor("btnCancelarTodo.foreground")); // NOI18N
        btnCancelarTodo.setIcon(resourceMap.getIcon("btnCancelarTodo.icon")); // NOI18N
        btnCancelarTodo.setText(resourceMap.getString("btnCancelarTodo.text")); // NOI18N
        btnCancelarTodo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelarTodo.setName("btnCancelarTodo"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTituloPnlDcha)
                    .addComponent(spAdminPlan, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAceptarTodo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelarTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPnlDcha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spAdminPlan, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptarTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarTodo;
    private javax.swing.JButton btnCancelarTodo;
    private javax.swing.JLabel lblTituloPnlDcha;
    private ar.gov.cjpmv.prestamos.gui.contable.PnlABMCuenta pnlABMCuenta;
    private ar.gov.cjpmv.prestamos.gui.contable.PnlPlanIzda pnlPlanIzda;
    // End of variables declaration//GEN-END:variables
    
    public static void main(String[] args) {
    	JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.add(new PnlAdminPlanCuentas());
    	frame.pack();
    	frame.setVisible(true);
    }
}
