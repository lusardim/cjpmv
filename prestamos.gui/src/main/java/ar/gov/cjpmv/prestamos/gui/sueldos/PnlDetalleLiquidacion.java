/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlDetalleLiquidacion.java
 *
 * Created on 26/12/2011, 20:51:29
 */

package ar.gov.cjpmv.prestamos.gui.sueldos;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author daiana
 */
public class PnlDetalleLiquidacion extends javax.swing.JPanel {


	
    /** Creates new form PnlDetalleLiquidacion 
     * @param pVentana 
     * @param pnlAMLiquidacionHaberes */
    public PnlDetalleLiquidacion() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        lblDescripcion2 = new javax.swing.JLabel();
        lblDescripcion1 = new javax.swing.JLabel();
        lblQuitar = new javax.swing.JLabel();
        lblDescripcion3 = new javax.swing.JLabel();
        lblDescripcion4 = new javax.swing.JLabel();
        lblLiqBen = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        lblDescripcion7 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        txtLegajo = new javax.swing.JTextField();
        txtCategoria = new javax.swing.JTextField();
        txtValorLiquidacion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblEjerciciosContables = new javax.swing.JLabel();
        lblEjerciciosContables2 = new javax.swing.JLabel();
        lblEjerciciosContables3 = new javax.swing.JLabel();
        txtTotalHaberes = new javax.swing.JTextField();
        txtTotalDescuentos = new javax.swing.JTextField();
        txtTotalNeto = new javax.swing.JTextField();

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDetalle);

        lblDescripcion2.setFont(new java.awt.Font("Tahoma 11 12 11 11 11 11", 1, 11));
        lblDescripcion2.setForeground(new java.awt.Color(102, 102, 102));
        lblDescripcion2.setText("Recibo de Haberes");

        lblDescripcion1.setFont(new java.awt.Font("Tahoma 11 12 11 11 11", 1, 11));
        lblDescripcion1.setForeground(new java.awt.Color(102, 102, 102));
        lblDescripcion1.setText("Apellido");

        lblQuitar.setFont(new java.awt.Font("Tahoma 11 11", 1, 11));
        lblQuitar.setForeground(new java.awt.Color(102, 102, 102));
        lblQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ar/gov/cjpmv/prestamos/gui/comunes/resources/BtnEliminar.png"))); // NOI18N
        lblQuitar.setText("Quitar");
        lblQuitar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblDescripcion3.setFont(new java.awt.Font("Tahoma 11 12 11 11 11", 1, 11));
        lblDescripcion3.setForeground(new java.awt.Color(102, 102, 102));
        lblDescripcion3.setText("Legajo");

        lblDescripcion4.setFont(new java.awt.Font("Tahoma 11 12 11 11 11", 1, 11));
        lblDescripcion4.setForeground(new java.awt.Color(102, 102, 102));
        lblDescripcion4.setText("Categoría");

        lblLiqBen.setFont(new java.awt.Font("Tahoma 11 12 11 11 11", 1, 11)); // NOI18N
        lblLiqBen.setForeground(new java.awt.Color(102, 102, 102));
        lblLiqBen.setText("Valor de la liq.");

        lblDescripcion7.setFont(new java.awt.Font("Tahoma 11 12 11 11 11", 1, 11));
        lblDescripcion7.setForeground(new java.awt.Color(102, 102, 102));
        lblDescripcion7.setText("Nombres");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 1, true), "Totales"));
        jPanel2.setForeground(new java.awt.Color(102, 102, 102));

        lblEjerciciosContables.setFont(new java.awt.Font("Tahoma 11 11 11", 1, 11));
        lblEjerciciosContables.setForeground(new java.awt.Color(102, 102, 102));
        lblEjerciciosContables.setText("Haberes");

        lblEjerciciosContables2.setFont(new java.awt.Font("Tahoma 11 11 11 11", 1, 11));
        lblEjerciciosContables2.setForeground(new java.awt.Color(102, 102, 102));
        lblEjerciciosContables2.setText("Descuentos");

        lblEjerciciosContables3.setFont(new java.awt.Font("Tahoma 11 11 11 11 11", 1, 11));
        lblEjerciciosContables3.setForeground(new java.awt.Color(102, 102, 102));
        lblEjerciciosContables3.setText("Neto");

        txtTotalHaberes.setForeground(new java.awt.Color(51, 51, 51));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(lblEjerciciosContables3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel2Layout.createSequentialGroup()
                            .add(lblEjerciciosContables2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 66, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(10, 10, 10))
                        .add(jPanel2Layout.createSequentialGroup()
                            .add(lblEjerciciosContables, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))))
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(txtTotalHaberes)
                        .add(txtTotalDescuentos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 146, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(txtTotalNeto, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtTotalHaberes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblEjerciciosContables, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lblEjerciciosContables2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, txtTotalDescuentos))
                .add(8, 8, 8)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(lblEjerciciosContables3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .add(txtTotalNeto))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lblDescripcion1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lblDescripcion3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lblLiqBen))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(layout.createSequentialGroup()
                                .add(txtLegajo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .add(42, 42, 42))
                            .add(layout.createSequentialGroup()
                                .add(txtApellido, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                .add(44, 44, 44))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(txtValorLiquidacion, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .add(42, 42, 42)))
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lblDescripcion7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lblDescripcion4))
                        .add(44, 44, 44)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, txtCategoria, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, txtNombres, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
                        .add(22, 22, 22))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(lblDescripcion2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 149, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 494, Short.MAX_VALUE)
                        .add(lblQuitar))
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblDescripcion1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtApellido, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblDescripcion7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtNombres, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblDescripcion3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtLegajo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblDescripcion4)
                    .add(txtCategoria, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtValorLiquidacion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblLiqBen))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblDescripcion2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblQuitar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescripcion1;
    private javax.swing.JLabel lblDescripcion2;
    private javax.swing.JLabel lblDescripcion3;
    private javax.swing.JLabel lblDescripcion4;
    private javax.swing.JLabel lblDescripcion7;
    private javax.swing.JLabel lblEjerciciosContables;
    private javax.swing.JLabel lblEjerciciosContables2;
    private javax.swing.JLabel lblEjerciciosContables3;
    private javax.swing.JLabel lblLiqBen;
    private javax.swing.JLabel lblQuitar;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtLegajo;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTotalDescuentos;
    private javax.swing.JTextField txtTotalHaberes;
    private javax.swing.JTextField txtTotalNeto;
    private javax.swing.JTextField txtValorLiquidacion;
    // End of variables declaration//GEN-END:variables
    
    
    public JLabel getLblValorLiq(){
    	return this.lblLiqBen;
    }
    
    public JTextField getTxtApellido(){
    	return this.txtApellido;
    }
    
    public JTextField getTxtCategoria(){
    	return this.txtCategoria;
    }
    
    public JTextField getTxtLegajo(){
    	return this.txtLegajo;
    }

    public JTextField getTxtNombres(){
    	return this.txtNombres;
    }
    
    public JTextField getTxtTotalDescuento(){
    	return this.txtTotalDescuentos;
    }
    
    public JTextField getTxtTotalHaberes(){
    	return this.txtTotalHaberes;
    }
    
    public JTextField getTxtTotalNeto(){
    	return this.txtTotalNeto;
    }
    
    public JTextField getTxtValorLiq(){
    	return this.txtValorLiquidacion;
    }
    
    public JLabel getLblLiqBen(){
    	return this.lblLiqBen;
    }
    
    public JLabel getLblQuitar(){
    	return this.lblQuitar;
    }
    
    public JTable getTblDetalle(){
    	return this.tblDetalle;
    }
    

}