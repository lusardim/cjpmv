/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlGarantiaPersonal.java
 *
 * Created on 12/02/2010, 00:48:15
 */

package ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes;

import javax.swing.BorderFactory;


/**
 *
 * @author daiana
 */
public class PnlGarantiaPersonal extends javax.swing.JPanel {

    /** Creates new form PnlGarantiaPersonal */
    public PnlGarantiaPersonal() {
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

        sllpGarantiaPersonal = new javax.swing.JScrollPane();
        pnlListaGarantias = new javax.swing.JPanel();
        pnlBusquedaPersonaView = new ar.gov.cjpmv.prestamos.gui.personas.PnlBusquedaPersonaView();
        btnSeleccionar = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlGarantiaPersonal.class);
        setBackground(resourceMap.getColor("FondoBlanco")); // NOI18N
        setName("Form"); // NOI18N

        sllpGarantiaPersonal.setBorder(null);
        sllpGarantiaPersonal.setName("sllpGarantiaPersonal"); // NOI18N

        pnlListaGarantias.setBackground(resourceMap.getColor("FondoBlanco")); // NOI18N
        pnlListaGarantias.setBorder(null);
        pnlListaGarantias.setName("pnlListaGarantias"); // NOI18N
        pnlListaGarantias.setLayout(new javax.swing.BoxLayout(pnlListaGarantias, javax.swing.BoxLayout.Y_AXIS));
        sllpGarantiaPersonal.setViewportView(pnlListaGarantias);

        pnlBusquedaPersonaView.setMinimumSize(new java.awt.Dimension(428, 284));
        pnlBusquedaPersonaView.setName("pnlBusquedaPersonaView"); // NOI18N

        btnSeleccionar.setIcon(resourceMap.getIcon("btnSeleccionar.icon")); // NOI18N
        btnSeleccionar.setText(resourceMap.getString("btnSeleccionar.text")); // NOI18N
        btnSeleccionar.setMaximumSize(new java.awt.Dimension(121, 23));
        btnSeleccionar.setName("btnSeleccionar"); // NOI18N
        btnSeleccionar.setPreferredSize(new java.awt.Dimension(101, 23));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBusquedaPersonaView, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(731, Short.MAX_VALUE)
                .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sllpGarantiaPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sllpGarantiaPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addComponent(pnlBusquedaPersonaView, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSeleccionar;
    private ar.gov.cjpmv.prestamos.gui.personas.PnlBusquedaPersonaView pnlBusquedaPersonaView;
    private javax.swing.JPanel pnlListaGarantias;
    private javax.swing.JScrollPane sllpGarantiaPersonal;
    // End of variables declaration//GEN-END:variables
    
    public javax.swing.JButton getBtnSeleccionar() {
		return btnSeleccionar;
	}

	public javax.swing.JPanel getPnlListaGarantias() {
		return pnlListaGarantias;
	}
    
	
	public ar.gov.cjpmv.prestamos.gui.personas.PnlBusquedaPersonaView getPnlBusquedaPersonaView() {
		return pnlBusquedaPersonaView;
	}
}