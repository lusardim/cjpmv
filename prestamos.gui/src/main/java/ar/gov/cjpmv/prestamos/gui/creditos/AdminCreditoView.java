/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AdminCreditoView.java
 *
 * Created on 10/02/2010, 00:27:40
 */

package ar.gov.cjpmv.prestamos.gui.creditos;

/**
 *
 * @author pulpol
 */
public class AdminCreditoView extends javax.swing.JDialog {

    /** Creates new form AdminCreditoView */
    public AdminCreditoView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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
        pnlBotoneraCreditosView = new ar.gov.cjpmv.prestamos.gui.creditos.PnlBotoneraCreditosView();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        spPrincipal.setDividerSize(2);
        spPrincipal.setName("spPrincipal"); // NOI18N

        spIzquierda.setMinimumSize(new java.awt.Dimension(270, 717));
        spIzquierda.setName("spIzquierda"); // NOI18N

        pnlBotoneraCreditosView.setName("pnlBotoneraCreditosView"); // NOI18N
        spIzquierda.setViewportView(pnlBotoneraCreditosView);

        spPrincipal.setLeftComponent(spIzquierda);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 717, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AdminCreditoView dialog = new AdminCreditoView(new javax.swing.JFrame(), true);
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
    private ar.gov.cjpmv.prestamos.gui.creditos.PnlBotoneraCreditosView pnlBotoneraCreditosView;
    private javax.swing.JSplitPane spPrincipal;
    // End of variables declaration//GEN-END:variables
	public javax.swing.JSplitPane getSpPrincipal() {
		return spPrincipal;
	}

	
	public void setSpPrincipal(javax.swing.JSplitPane spPrincipal) {
		this.spPrincipal = spPrincipal;
	}

	public ar.gov.cjpmv.prestamos.gui.creditos.PnlBotoneraCreditosView getPnlBotoneraCreditosView() {
		return pnlBotoneraCreditosView;
	}
	
    
    
}
