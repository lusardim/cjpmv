/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlCabeceraBotoneraContable.java
 *
 * Created on 29/06/2011, 22:24:06
 */

package ar.gov.cjpmv.prestamos.gui.contable;

/**
 *
 * @author dernst
 */
public class PnlCabeceraBotoneraContable extends javax.swing.JPanel {

    /** Creates new form PnlCabeceraBotoneraContable */
    public PnlCabeceraBotoneraContable() {
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

        lblTituloBotonera = new javax.swing.JLabel();
        lblImagenBotonera = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlCabeceraBotoneraContable.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N

        lblTituloBotonera.setBackground(resourceMap.getColor("lblTituloBotonera.background")); // NOI18N
        lblTituloBotonera.setFont(resourceMap.getFont("lblTituloBotonera.font")); // NOI18N
        lblTituloBotonera.setForeground(resourceMap.getColor("lblTituloBotonera.foreground")); // NOI18N
        lblTituloBotonera.setText(resourceMap.getString("lblTituloBotonera.text")); // NOI18N
        lblTituloBotonera.setName("lblTituloBotonera"); // NOI18N

        lblImagenBotonera.setIcon(resourceMap.getIcon("lblImagenBotonera.icon")); // NOI18N
        lblImagenBotonera.setName("lblImagenBotonera"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImagenBotonera, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTituloBotonera, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagenBotonera, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
            .addComponent(lblTituloBotonera, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblImagenBotonera;
    private javax.swing.JLabel lblTituloBotonera;
    // End of variables declaration//GEN-END:variables

}
