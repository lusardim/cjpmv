/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ABMConfiguracionSetearPnl.java
 *
 * Created on 14/01/2010, 23:24:58
 */

package ar.gov.cjpmv.prestamos.gui.configuracion;

import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

/**
 *
 * @author daiana
 */
public class ABMConfiguracionSetearPnl extends javax.swing.JDialog {

    /** Creates new form ABMConfiguracionSetearPnl */
    public ABMConfiguracionSetearPnl(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
       // initComponents();
    }

    public ABMConfiguracionSetearPnl(JDialog pPadre, boolean modal) {
		super(pPadre, modal);
		//initComponents();
	}
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ABMConfiguracionSetearPnl dialog = new ABMConfiguracionSetearPnl(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel pnlABMConfiguracionSetearPnl;
    // End of variables declaration//GEN-END:variables
    
    
    
    public void setPanel(JComponent pComponente){
    	
        pnlABMConfiguracionSetearPnl = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(ABMConfiguracionSetearPnl.class);
        pnlABMConfiguracionSetearPnl.setBackground(resourceMap.getColor("pnlABMConfiguracionSetearPnl.background")); // NOI18N
        pnlABMConfiguracionSetearPnl.setName("pnlABMConfiguracionSetearPnl"); // NOI18N

        btnGuardar.setFont(resourceMap.getFont("Campo")); // NOI18N
        btnGuardar.setIcon(resourceMap.getIcon("btnGuardar.icon")); // NOI18N
        btnGuardar.setText(resourceMap.getString("btnGuardar.text")); // NOI18N
        btnGuardar.setMaximumSize(new java.awt.Dimension(121, 23));
        btnGuardar.setName("btnGuardar"); // NOI18N
        btnGuardar.setPreferredSize(new java.awt.Dimension(101, 23));

        btnCancelar.setFont(resourceMap.getFont("Campo")); // NOI18N
        btnCancelar.setIcon(resourceMap.getIcon("btnCancelar.icon")); // NOI18N
        btnCancelar.setText(resourceMap.getString("btnCancelar.text")); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N
    	
    	
    	  pComponente.setName("pComponente"); // NOI18N
    	  javax.swing.GroupLayout pnlABMConfiguracionLayout = new javax.swing.GroupLayout(pnlABMConfiguracionSetearPnl);
    	  pnlABMConfiguracionSetearPnl.setLayout(pnlABMConfiguracionLayout);
    	  
    	  
    	  
    	  pnlABMConfiguracionLayout.setHorizontalGroup(
    		        pnlABMConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    		        .addGroup(pnlABMConfiguracionLayout.createSequentialGroup()
    		            .addGap(26, 26, 26)
    		            .addComponent(pComponente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    		            .addContainerGap(53, Short.MAX_VALUE))
    		        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlABMConfiguracionLayout.createSequentialGroup()
    		            .addContainerGap(85, Short.MAX_VALUE)
    		            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    		            .addGap(18, 18, 18)
    		            .addComponent(btnCancelar)
    		            .addGap(53, 53, 53))
    		    );
    	  
    	  pnlABMConfiguracionLayout.setVerticalGroup(
    		        pnlABMConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    		        .addGroup(pnlABMConfiguracionLayout.createSequentialGroup()
    		            .addContainerGap()
    		            .addComponent(pComponente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    		            .addGap(18, 18, 18)
    		            .addGroup(pnlABMConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
    		                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
    		                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
    		            .addContainerGap(22, Short.MAX_VALUE))
    		    );
    	   javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    	    getContentPane().setLayout(layout);
    	    layout.setHorizontalGroup(
    	        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    	        .addComponent(pnlABMConfiguracionSetearPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    	    );
    	    layout.setVerticalGroup(
    	        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    	        .addComponent(pnlABMConfiguracionSetearPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    	    );
    	    pack();
    }
    
    public JButton getBtnGuardar(){
    	return this.btnGuardar;
    }

    public JButton getBtnCancelar(){
    	return this.btnCancelar;
    }
   
    



}
