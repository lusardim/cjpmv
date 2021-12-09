/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ABMCuotasACobrar.java
 *
 * Created on 25/05/2010, 11:56:22
 */

package ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXTitledSeparator;

/**
 *
 * @author dernst
 */
public class ABMCuotasACobrar extends javax.swing.JDialog {

    /** Creates new form ABMCuotasACobrar */
    public ABMCuotasACobrar(JDialog parent, boolean modal) {
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

        jPanel1 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        txtNumeroLegajo = new javax.swing.JTextField();
        xlblNumeroCredito1 = new org.jdesktop.swingx.JXLabel();
        xlblNumeroCredito2 = new org.jdesktop.swingx.JXLabel();
        txtCuilCuit = new javax.swing.JTextField();
        xlblNumeroCredito3 = new org.jdesktop.swingx.JXLabel();
        xlblNumeroCredito4 = new org.jdesktop.swingx.JXLabel();
        txtNumeroCredito = new javax.swing.JTextField();
        cbxEstado = new javax.swing.JComboBox();
        btnBuscar = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblCuotasACobrar = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        xtsTituloSub2 = new org.jdesktop.swingx.JXTitledSeparator();
        xlblNumeroCredito = new org.jdesktop.swingx.JXLabel();
        cbxConSeguro = new javax.swing.JCheckBox();
        xtsTituloSub1 = new org.jdesktop.swingx.JXTitledSeparator();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(ABMCuotasACobrar.class);
        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jPanel19.setBackground(resourceMap.getColor("jPanel19.background")); // NOI18N
        jPanel19.setName("jPanel19"); // NOI18N

        txtNumeroLegajo.setForeground(resourceMap.getColor("txtNumeroLegajo.foreground")); // NOI18N
        txtNumeroLegajo.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("txtNumeroLegajo.border.lineColor"), 1, true)); // NOI18N
        txtNumeroLegajo.setName("txtNumeroLegajo"); // NOI18N

        xlblNumeroCredito1.setForeground(resourceMap.getColor("xlblNumeroCredito1.foreground")); // NOI18N
        xlblNumeroCredito1.setText(resourceMap.getString("xlblNumeroCredito1.text")); // NOI18N
        xlblNumeroCredito1.setName("xlblNumeroCredito1"); // NOI18N

        xlblNumeroCredito2.setForeground(resourceMap.getColor("xlblNumeroCredito2.foreground")); // NOI18N
        xlblNumeroCredito2.setText(resourceMap.getString("xlblNumeroCredito2.text")); // NOI18N
        xlblNumeroCredito2.setName("xlblNumeroCredito2"); // NOI18N

        txtCuilCuit.setForeground(resourceMap.getColor("txtCuilCuit.foreground")); // NOI18N
        txtCuilCuit.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("txtCuilCuit.border.lineColor"), 1, true)); // NOI18N
        txtCuilCuit.setName("txtCuilCuit"); // NOI18N

        xlblNumeroCredito3.setForeground(resourceMap.getColor("xlblNumeroCredito3.foreground")); // NOI18N
        xlblNumeroCredito3.setText(resourceMap.getString("xlblNumeroCredito3.text")); // NOI18N
        xlblNumeroCredito3.setName("xlblNumeroCredito3"); // NOI18N

        xlblNumeroCredito4.setForeground(resourceMap.getColor("xlblNumeroCredito4.foreground")); // NOI18N
        xlblNumeroCredito4.setText(resourceMap.getString("xlblNumeroCredito4.text")); // NOI18N
        xlblNumeroCredito4.setName("xlblNumeroCredito4"); // NOI18N

        txtNumeroCredito.setForeground(resourceMap.getColor("txtNumeroCredito.foreground")); // NOI18N
        txtNumeroCredito.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("txtNumeroCredito.border.lineColor"), 1, true)); // NOI18N
        txtNumeroCredito.setName("txtNumeroCredito"); // NOI18N

        cbxEstado.setForeground(resourceMap.getColor("cbxEstado.foreground")); // NOI18N
        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxEstado.setName("cbxEstado"); // NOI18N

        btnBuscar.setIcon(resourceMap.getIcon("btnBuscar.icon")); // NOI18N
        btnBuscar.setLabel(resourceMap.getString("btnBuscar.label")); // NOI18N
        btnBuscar.setName("btnBuscar"); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xlblNumeroCredito1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xlblNumeroCredito2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNumeroLegajo, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(txtCuilCuit, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xlblNumeroCredito3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xlblNumeroCredito4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxEstado, 0, 169, Short.MAX_VALUE)
                    .addComponent(txtNumeroCredito, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(btnBuscar)
                        .addContainerGap())
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(xlblNumeroCredito1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(xlblNumeroCredito3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNumeroLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(xlblNumeroCredito2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCuilCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xlblNumeroCredito4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumeroCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(20, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel10.setBackground(new java.awt.Color(247, 247, 247));
        jPanel10.setName("jPanel10"); // NOI18N

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        tblCuotasACobrar.setForeground(resourceMap.getColor("tblCuotasACobrar.foreground")); // NOI18N
        tblCuotasACobrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Cobrar: SI / NO", "Nº de Crédito", "Nº de Cuota", "Vencimiento", "Otros Conceptos", "Interes", "Capital", "Valor Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblCuotasACobrar.setName("tblCuotasACobrar"); // NOI18N
        jScrollPane7.setViewportView(tblCuotasACobrar);

        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        xtsTituloSub2.setForeground(resourceMap.getColor("xtsTituloSub2.foreground")); // NOI18N
        xtsTituloSub2.setFont(resourceMap.getFont("xtsTituloSub2.font")); // NOI18N
        xtsTituloSub2.setName("xtsTituloSub2"); // NOI18N
        xtsTituloSub2.setTitle(resourceMap.getString("xtsTituloSub2.title")); // NOI18N

        xlblNumeroCredito.setForeground(resourceMap.getColor("xlblNumeroCredito.foreground")); // NOI18N
        xlblNumeroCredito.setText(resourceMap.getString("xlblNumeroCredito.text")); // NOI18N
        xlblNumeroCredito.setName("xlblNumeroCredito"); // NOI18N

        cbxConSeguro.setBackground(resourceMap.getColor("cbxConSeguro.background")); // NOI18N
        cbxConSeguro.setFont(resourceMap.getFont("cbxConSeguro.font")); // NOI18N
        cbxConSeguro.setForeground(resourceMap.getColor("cbxConSeguro.foreground")); // NOI18N
        cbxConSeguro.setText(resourceMap.getString("cbxConSeguro.text")); // NOI18N
        cbxConSeguro.setName("cbxConSeguro"); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(xtsTituloSub2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 306, Short.MAX_VALUE)
                        .addComponent(cbxConSeguro))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(xlblNumeroCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)))
                .addContainerGap())
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(xtsTituloSub2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(xlblNumeroCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)))
                    .addComponent(cbxConSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addGap(37, 37, 37)))
        );

        xtsTituloSub1.setForeground(resourceMap.getColor("xtsTituloSub1.foreground")); // NOI18N
        xtsTituloSub1.setFont(resourceMap.getFont("xtsTituloSub1.font")); // NOI18N
        xtsTituloSub1.setName("xtsTituloSub1"); // NOI18N
        xtsTituloSub1.setTitle(resourceMap.getString("xtsTituloSub1.title")); // NOI18N

        btnAceptar.setForeground(resourceMap.getColor("btnAceptar.foreground")); // NOI18N
        btnAceptar.setIcon(resourceMap.getIcon("btnAceptar.icon")); // NOI18N
        btnAceptar.setText(resourceMap.getString("btnAceptar.text")); // NOI18N
        btnAceptar.setMaximumSize(new java.awt.Dimension(121, 23));
        btnAceptar.setName("btnAceptar"); // NOI18N
        btnAceptar.setPreferredSize(new java.awt.Dimension(101, 23));

        btnCancelar.setForeground(resourceMap.getColor("btnCancelar.foreground")); // NOI18N
        btnCancelar.setIcon(resourceMap.getIcon("btnCancelar.icon")); // NOI18N
        btnCancelar.setText(resourceMap.getString("btnCancelar.text")); // NOI18N
        btnCancelar.setMaximumSize(new java.awt.Dimension(121, 23));
        btnCancelar.setName("btnCancelar"); // NOI18N
        btnCancelar.setPreferredSize(new java.awt.Dimension(101, 23));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xtsTituloSub1, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xtsTituloSub1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ABMCuotasACobrar dialog = new ABMCuotasACobrar(new JDialog(), true);
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
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox cbxConSeguro;
    private javax.swing.JComboBox cbxEstado;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable tblCuotasACobrar;
    private javax.swing.JTextField txtCuilCuit;
    private javax.swing.JTextField txtNumeroCredito;
    private javax.swing.JTextField txtNumeroLegajo;
    private org.jdesktop.swingx.JXLabel xlblNumeroCredito;
    private org.jdesktop.swingx.JXLabel xlblNumeroCredito1;
    private org.jdesktop.swingx.JXLabel xlblNumeroCredito2;
    private org.jdesktop.swingx.JXLabel xlblNumeroCredito3;
    private org.jdesktop.swingx.JXLabel xlblNumeroCredito4;
    private org.jdesktop.swingx.JXTitledSeparator xtsTituloSub1;
    private org.jdesktop.swingx.JXTitledSeparator xtsTituloSub2;
    // End of variables declaration//GEN-END:variables
    
    
    
    public JComboBox getCbxEstado(){
    	return this.cbxEstado;
    }
    
    public JTextField getTxtLegajo(){
    	return this.txtNumeroLegajo;
    }
    
    public JTextField getTxtCuilCuit(){
    	return this.txtCuilCuit;
    }
    
    public JTextField getTxtNumeroCredito(){
    	return this.txtNumeroCredito;
    }

    
    public JButton getBtnBuscar(){
    	return this.btnBuscar;
    }
    
    public JTable getTblCuotas(){
    	return this.tblCuotasACobrar;
    }
    
    public JButton getBtnAceptar(){
    	return this.btnAceptar;
    }
    
    public JButton getBtnCancelar(){
    	return this.btnCancelar;
    }
    public  JXTitledSeparator getXtsTitulo(){
    	return this.xtsTituloSub2;
    }
    
    public JCheckBox getCbxConSeguro(){
    	return this.cbxConSeguro;
    }
    
}
