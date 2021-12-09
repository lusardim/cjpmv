/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlActivoCorrienteNoCorriente.java
 *
 * Created on 27/12/2010, 23:56:29
 */

package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

/**
 *
 * @author dernst
 */
public class PnlActivoCorrienteNoCorriente extends javax.swing.JPanel {

    /** Creates new form PnlActivoCorrienteNoCorriente */
    public PnlActivoCorrienteNoCorriente() {
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

        jPanel2 = new javax.swing.JPanel();
        lblDescripcion1 = new javax.swing.JLabel();
        cbxTipoCredito = new javax.swing.JComboBox();
        lblDescripcion2 = new javax.swing.JLabel();
        dtcFechaHasta = new com.toedter.calendar.JDateChooser();
        lblViaCobro = new javax.swing.JLabel();
        cbxVíadeCobro = new javax.swing.JComboBox();
        lblDescripcion4 = new javax.swing.JLabel();
        cbxOrden = new javax.swing.JComboBox();
        xtsTituloParametroBusqueda = new org.jdesktop.swingx.JXTitledSeparator();
        btnImprimir = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlActivoCorrienteNoCorriente.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N

        jPanel2.setBackground(resourceMap.getColor("jPanel2.background")); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        lblDescripcion1.setForeground(resourceMap.getColor("lblDescripcion1.foreground")); // NOI18N
        lblDescripcion1.setText(resourceMap.getString("lblDescripcion1.text")); // NOI18N
        lblDescripcion1.setName("lblDescripcion1"); // NOI18N

        cbxTipoCredito.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTipoCredito.setName("cbxTipoCredito"); // NOI18N

        lblDescripcion2.setForeground(resourceMap.getColor("lblDescripcion2.foreground")); // NOI18N
        lblDescripcion2.setText(resourceMap.getString("lblDescripcion2.text")); // NOI18N
        lblDescripcion2.setName("lblDescripcion2"); // NOI18N

        dtcFechaHasta.setForeground(resourceMap.getColor("dtcFechaHasta.foreground")); // NOI18N
        dtcFechaHasta.setFont(resourceMap.getFont("dtcFechaHasta.font")); // NOI18N
        dtcFechaHasta.setName("dtcFechaHasta"); // NOI18N

        lblViaCobro.setForeground(resourceMap.getColor("lblViaCobro.foreground")); // NOI18N
        lblViaCobro.setText(resourceMap.getString("lblViaCobro.text")); // NOI18N
        lblViaCobro.setName("lblViaCobro"); // NOI18N

        cbxVíadeCobro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxVíadeCobro.setName("cbxVíadeCobro"); // NOI18N

        lblDescripcion4.setForeground(resourceMap.getColor("lblDescripcion4.foreground")); // NOI18N
        lblDescripcion4.setText(resourceMap.getString("lblDescripcion4.text")); // NOI18N
        lblDescripcion4.setName("lblDescripcion4"); // NOI18N

        cbxOrden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxOrden.setName("cbxOrden"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblDescripcion1, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblDescripcion4)
                        .addGap(20, 20, 20)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxTipoCredito, 0, 231, Short.MAX_VALUE)
                    .addComponent(cbxOrden, 0, 231, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblDescripcion2)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblViaCobro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxVíadeCobro, 0, 175, Short.MAX_VALUE)
                    .addComponent(dtcFechaHasta, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtcFechaHasta, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDescripcion2)
                        .addComponent(lblDescripcion1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxTipoCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblViaCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxVíadeCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDescripcion4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        xtsTituloParametroBusqueda.setForeground(resourceMap.getColor("xtsTituloParametroBusqueda.foreground")); // NOI18N
        xtsTituloParametroBusqueda.setFont(resourceMap.getFont("xtsTituloParametroBusqueda.font")); // NOI18N
        xtsTituloParametroBusqueda.setName("xtsTituloParametroBusqueda"); // NOI18N
        xtsTituloParametroBusqueda.setTitle(resourceMap.getString("xtsTituloParametroBusqueda.title")); // NOI18N

        btnImprimir.setIcon(resourceMap.getIcon("btnImprimir.icon")); // NOI18N
        btnImprimir.setText(resourceMap.getString("btnImprimir.text")); // NOI18N
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.setMaximumSize(new java.awt.Dimension(121, 23));
        btnImprimir.setName("btnImprimir"); // NOI18N
        btnImprimir.setPreferredSize(new java.awt.Dimension(101, 23));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xtsTituloParametroBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xtsTituloParametroBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox cbxOrden;
    private javax.swing.JComboBox cbxTipoCredito;
    private javax.swing.JComboBox cbxVíadeCobro;
    private com.toedter.calendar.JDateChooser dtcFechaHasta;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDescripcion1;
    private javax.swing.JLabel lblDescripcion2;
    private javax.swing.JLabel lblDescripcion4;
    private javax.swing.JLabel lblViaCobro;
    private org.jdesktop.swingx.JXTitledSeparator xtsTituloParametroBusqueda;
    // End of variables declaration//GEN-END:variables

    
    public JButton getBtnImprimir(){
    	return this.btnImprimir;
    }
   
   public com.toedter.calendar.JDateChooser getDtcFechaHasta(){
	   return this.dtcFechaHasta;
   }
   
   public JComboBox getCbxTipoCredito(){
	   return this.cbxTipoCredito;
   }
   
   public JComboBox getCbxViaCobro(){
	   return this.cbxVíadeCobro;
   }
   
   public JComboBox getCbxOrden(){
	   return this.cbxOrden;
   }
   
   
    
    
}
