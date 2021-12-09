/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlCreditosFinalizacion.java
 *
 * Created on 13/09/2010, 21:37:35
 */

package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos;

import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author pulpol
 */
public class PnlCreditosFinalizacion extends javax.swing.JPanel {

    /** Creates new form PnlCreditosFinalizacion */
    public PnlCreditosFinalizacion() {
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

        pnlPrincipal = new javax.swing.JPanel();
        scListaCreditos = new javax.swing.JScrollPane();
        tblListaCreditos = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblLabelTotal = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlCreditosFinalizacion.class);
        setBackground(resourceMap.getColor("FondoBlanco")); // NOI18N
        setName("Form"); // NOI18N

        pnlPrincipal.setBackground(resourceMap.getColor("FondoGris")); // NOI18N
        pnlPrincipal.setName("pnlPrincipal"); // NOI18N

        scListaCreditos.setName("scListaCreditos"); // NOI18N

        tblListaCreditos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblListaCreditos.setName("tblListaCreditos"); // NOI18N
        scListaCreditos.setViewportView(tblListaCreditos);

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scListaCreditos, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scListaCreditos, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblTitulo.setFont(resourceMap.getFont("tituloBarrita")); // NOI18N
        lblTitulo.setForeground(resourceMap.getColor("ColorTituloBarrita")); // NOI18N
        lblTitulo.setText(resourceMap.getString("lblTitulo.text")); // NOI18N
        lblTitulo.setName("lblTitulo"); // NOI18N

        lblTotal.setFont(resourceMap.getFont("TituloBarrita")); // NOI18N
        lblTotal.setForeground(resourceMap.getColor("colorTituloBarrita")); // NOI18N
        lblTotal.setText(resourceMap.getString("lblTotal.text")); // NOI18N
        lblTotal.setName("lblTotal"); // NOI18N

        lblLabelTotal.setFont(resourceMap.getFont("TituloBarrita")); // NOI18N
        lblLabelTotal.setForeground(resourceMap.getColor("colorTituloBarrita")); // NOI18N
        lblLabelTotal.setText(resourceMap.getString("lblLabelTotal.text")); // NOI18N
        lblLabelTotal.setName("lblLabelTotal"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblLabelTotal)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotal))
                    .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLabelTotal)
                    .addComponent(lblTotal))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblLabelTotal;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JScrollPane scListaCreditos;
    private javax.swing.JTable tblListaCreditos;
    // End of variables declaration//GEN-END:variables

    public JTable getTblListaCreditos() {
        return tblListaCreditos;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }
    
}