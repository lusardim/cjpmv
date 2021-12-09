/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlListadoViewRegistracion.java
 *
 * Created on 18/01/2010, 19:50:25
 */

package ar.gov.cjpmv.prestamos.gui.configuracion;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author daiana
 */
public class PnlListadoViewRegistracion extends javax.swing.JPanel {

    /** Creates new form PnlListadoViewRegistracion */
    public PnlListadoViewRegistracion() {
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

        sllpLista = new javax.swing.JScrollPane();
        lLista = new javax.swing.JList();
        lblTitulo = new javax.swing.JLabel();
        lblMensaje = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlListadoViewRegistracion.class);
        setBackground(resourceMap.getColor("ColorGris")); // NOI18N
        setName("Form"); // NOI18N

        sllpLista.setName("sllpLista"); // NOI18N

        lLista.setFont(resourceMap.getFont("TestoCampo")); // NOI18N
        lLista.setForeground(resourceMap.getColor("lLista.foreground")); // NOI18N
        lLista.setName("lLista"); // NOI18N
        sllpLista.setViewportView(lLista);

        lblTitulo.setForeground(resourceMap.getColor("lblTitulo.foreground")); // NOI18N
        lblTitulo.setText(resourceMap.getString("lblTitulo.text")); // NOI18N
        lblTitulo.setName("lblTitulo"); // NOI18N

        lblMensaje.setForeground(resourceMap.getColor("lblMensaje.foreground")); // NOI18N
        lblMensaje.setText(resourceMap.getString("lblMensaje.text")); // NOI18N
        lblMensaje.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblMensaje.setName("lblMensaje"); // NOI18N

        btnRegistrar.setFont(resourceMap.getFont("Campo")); // NOI18N
        btnRegistrar.setForeground(resourceMap.getColor("ColorTextoCampo")); // NOI18N
        btnRegistrar.setIcon(resourceMap.getIcon("btnRegistrar.icon")); // NOI18N
        btnRegistrar.setText(resourceMap.getString("btnRegistrar.text")); // NOI18N
        btnRegistrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRegistrar.setName("btnRegistrar"); // NOI18N
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sllpLista, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 296, Short.MAX_VALUE)
                                .addComponent(btnRegistrar)))
                        .addGap(2, 2, 2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sllpLista, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMensaje)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_btnRegistrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JList lLista;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane sllpLista;
    // End of variables declaration//GEN-END:variables

	public JLabel getLblTitulo(){
		return this.lblTitulo;
	}
	public JList getLLista(){
		return this.lLista;
	}
	
	public JButton getBtnRegistrar(){
		return this.btnRegistrar;
	}
	public JLabel getLblMensaje(){
		return this.lblMensaje;
	}
}
