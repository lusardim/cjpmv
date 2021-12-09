/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AdminConfiguracionView.java
 *
 * Created on 04/01/2010, 23:05:49
 */

package ar.gov.cjpmv.prestamos.gui.configuracion;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;

/**
 *
 * @author daiana
 */
public class AdminConfiguracionView extends javax.swing.JDialog {

    /** Creates new form AdminConfiguracionView */
    public AdminConfiguracionView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setResizable(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stpAdminConfiguracion = new javax.swing.JSplitPane();
        sllpAdminConfiguracion = new javax.swing.JScrollPane();
        treeAdminConfiguracion = new javax.swing.JTree();
        sllpDchaAdminConfiguracion = new javax.swing.JScrollPane();
        pnlDchaConfiguracion1 = new ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(AdminConfiguracionView.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        stpAdminConfiguracion.setBackground(resourceMap.getColor("FondoCeleste")); // NOI18N
        stpAdminConfiguracion.setDividerLocation(210);
        stpAdminConfiguracion.setDividerSize(2);
        stpAdminConfiguracion.setMinimumSize(new java.awt.Dimension(50, 25));
        stpAdminConfiguracion.setName("stpAdminConfiguracion"); // NOI18N
        stpAdminConfiguracion.setPreferredSize(new java.awt.Dimension(450, 324));

        sllpAdminConfiguracion.setName("sllpAdminConfiguracion"); // NOI18N

        treeAdminConfiguracion.setBackground(resourceMap.getColor("ColorBlanco")); // NOI18N
        treeAdminConfiguracion.setFont(resourceMap.getFont("Campo")); // NOI18N
        treeAdminConfiguracion.setForeground(resourceMap.getColor("ColorTextoCampo")); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Configuración");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Sistema");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Registración");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Persona");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Datos Personales");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Tipo de Documento");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Datos de Contacto");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Provincia");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Departamento");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Localidad");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
      //  treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Datos de Empleo");
       // treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Categoría");
       // treeNode3.add(treeNode4);
     //   treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Datos Previsionales");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Créditos");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Opciones Básicas");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Concepto");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Tipo de Crédito");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Banco");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Entidad Bancaria");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Cuenta Bancaria");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeAdminConfiguracion.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeAdminConfiguracion.setName("treeAdminConfiguracion"); // NOI18N
        sllpAdminConfiguracion.setViewportView(treeAdminConfiguracion);

        stpAdminConfiguracion.setLeftComponent(sllpAdminConfiguracion);

        sllpDchaAdminConfiguracion.setName("sllpDchaAdminConfiguracion"); // NOI18N
        sllpDchaAdminConfiguracion.setPreferredSize(new java.awt.Dimension(200, 200));

        pnlDchaConfiguracion1.setMinimumSize(new java.awt.Dimension(100, 70));
        pnlDchaConfiguracion1.setName("pnlDchaConfiguracion1"); // NOI18N
        pnlDchaConfiguracion1.setPreferredSize(new java.awt.Dimension(552, 300));
        sllpDchaAdminConfiguracion.setViewportView(pnlDchaConfiguracion1);

        stpAdminConfiguracion.setRightComponent(sllpDchaAdminConfiguracion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stpAdminConfiguracion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stpAdminConfiguracion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AdminConfiguracionView dialog = new AdminConfiguracionView(new javax.swing.JFrame(), true);
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
    private ar.gov.cjpmv.prestamos.gui.configuracion.PnlDchaConfiguracion pnlDchaConfiguracion1;
    private javax.swing.JScrollPane sllpAdminConfiguracion;
    private javax.swing.JScrollPane sllpDchaAdminConfiguracion;
    private javax.swing.JSplitPane stpAdminConfiguracion;
    private javax.swing.JTree treeAdminConfiguracion;
    // End of variables declaration//GEN-END:variables
    
    public JTree getTreeAdminConfiguracion(){
		return this.treeAdminConfiguracion;
    }
    /*
    public JSplitPane getStpAdminConfiguracion(){
    	return this.stpAdminConfiguracion;
    }
     */
    public JScrollPane getSllpDchaAdminConfiguracion(){
    	return this.sllpDchaAdminConfiguracion;
    }
}