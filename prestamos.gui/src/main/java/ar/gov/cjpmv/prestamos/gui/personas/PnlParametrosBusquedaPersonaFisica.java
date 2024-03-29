/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlParametrosBusquedaPersonaFisica.java
 *
 * Created on 03/01/2010, 19:23:03
 */

package ar.gov.cjpmv.prestamos.gui.personas;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JLabel;

import ar.gov.cjpmv.prestamos.gui.utiles.AceptaNulosFormattedTextField;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

/**
 *
 * @author daiana
 */
public class PnlParametrosBusquedaPersonaFisica extends javax.swing.JPanel {

    /** Creates new form PnlParametrosBusquedaPersonaFisica */
    public PnlParametrosBusquedaPersonaFisica() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * @throws ParseException 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLegajo = new javax.swing.JLabel();
        lblTipoDocumento = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        txtApellidoRazonSocial = new javax.swing.JTextField();
        cbxTipoDocumento = new javax.swing.JComboBox();

        lblCuil = new javax.swing.JLabel();
        lblNumeroDocumento = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox();
        
        try{
        
        	txtNumeroDocumento = new AceptaNulosFormattedTextField(Utiles.getFormateadorDocumento());
        	txtCuilCuit = new AceptaNulosFormattedTextField(Utiles.getFormateadorCuit());
        	NumberFormat locFormatoNumero = NumberFormat.getInstance();
        	locFormatoNumero.setMaximumFractionDigits(0);
        	txtLegajo = new AceptaNulosFormattedTextField(locFormatoNumero);
        }
        catch(ParseException e){
        	e.printStackTrace();
        	
        }
        btnBuscar = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlParametrosBusquedaPersonaFisica.class);
        setBackground(resourceMap.getColor("FondoGris")); // NOI18N
        setName("Form"); // NOI18N

        lblLegajo.setFont(resourceMap.getFont("Campo")); // NOI18N
        lblLegajo.setForeground(resourceMap.getColor("ColorTextoCampo")); // NOI18N
        lblLegajo.setText(resourceMap.getString("lblLegajo.text")); // NOI18N
        lblLegajo.setName("lblLegajo"); // NOI18N

        lblTipoDocumento.setFont(resourceMap.getFont("Campo")); // NOI18N
        lblTipoDocumento.setForeground(resourceMap.getColor("ColorTextoCampo")); // NOI18N
        lblTipoDocumento.setText(resourceMap.getString("lblTipoDocumento.text")); // NOI18N
        lblTipoDocumento.setName("lblTipoDocumento"); // NOI18N

        lblApellido.setFont(resourceMap.getFont("Campo")); // NOI18N
        lblApellido.setForeground(resourceMap.getColor("ColorTextoCampo")); // NOI18N
        lblApellido.setText(resourceMap.getString("lblApellido.text")); // NOI18N
        lblApellido.setName("lblApellido"); // NOI18N

        txtApellidoRazonSocial.setFont(resourceMap.getFont("Campo")); // NOI18N
        txtApellidoRazonSocial.setForeground(resourceMap.getColor("txtApellidoRazonSocial.foreground")); // NOI18N
        txtApellidoRazonSocial.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("txtApellidoRazonSocial.border.lineColor"), 1, true)); // NOI18N
        txtApellidoRazonSocial.setName("txtApellidoRazonSocial"); // NOI18N

        cbxTipoDocumento.setFont(resourceMap.getFont("Campo")); // NOI18N
        cbxTipoDocumento.setForeground(resourceMap.getColor("cbxTipoDocumento.foreground")); // NOI18N
        cbxTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Documento Único", "Libreta de Cívica", "Libreta de Enrolamiento" }));
        cbxTipoDocumento.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("cbxTipoDocumento.border.lineColor"), 1, true)); // NOI18N
        cbxTipoDocumento.setName("cbxTipoDocumento"); // NOI18N

        txtLegajo.setFont(resourceMap.getFont("Campo")); // NOI18N
        txtLegajo.setForeground(resourceMap.getColor("txtLegajo.foreground")); // NOI18N
        txtLegajo.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("txtLegajo.border.lineColor"), 1, true)); // NOI18N
        txtLegajo.setName("txtLegajo"); // NOI18N

        lblCuil.setFont(resourceMap.getFont("Campo")); // NOI18N
        lblCuil.setForeground(resourceMap.getColor("ColorTextoCampo")); // NOI18N
        lblCuil.setText(resourceMap.getString("lblCuil.text")); // NOI18N
        lblCuil.setName("lblCuil"); // NOI18N

        lblNumeroDocumento.setFont(resourceMap.getFont("Campo")); // NOI18N
        lblNumeroDocumento.setForeground(resourceMap.getColor("ColorTextoCampo")); // NOI18N
        lblNumeroDocumento.setText(resourceMap.getString("lblNumeroDocumento.text")); // NOI18N
        lblNumeroDocumento.setName("lblNumeroDocumento"); // NOI18N

        lblEstado.setFont(resourceMap.getFont("Campo")); // NOI18N
        lblEstado.setForeground(resourceMap.getColor("ColorTextoCampo")); // NOI18N
        lblEstado.setText(resourceMap.getString("lblEstado.text")); // NOI18N
        lblEstado.setName("lblEstado"); // NOI18N

        cbxEstado.setFont(resourceMap.getFont("Campo")); // NOI18N
        cbxEstado.setForeground(resourceMap.getColor("cbxEstado.foreground")); // NOI18N
        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Pasivo", "Fallecido" }));
        cbxEstado.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("cbxEstado.border.lineColor"), 1, true)); // NOI18N
        cbxEstado.setName("cbxEstado"); // NOI18N

        txtNumeroDocumento.setFont(resourceMap.getFont("Campo")); // NOI18N
        txtNumeroDocumento.setForeground(resourceMap.getColor("txtNumeroDocumento.foreground")); // NOI18N
        txtNumeroDocumento.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("txtNumeroDocumento.border.lineColor"), 1, true)); // NOI18N
        txtNumeroDocumento.setName("txtNumeroDocumento"); // NOI18N

        txtCuilCuit.setFont(resourceMap.getFont("Campo")); // NOI18N
        txtCuilCuit.setForeground(resourceMap.getColor("txtCuilCuit.foreground")); // NOI18N
        txtCuilCuit.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("txtCuilCuit.border.lineColor"), 1, true)); // NOI18N
        txtCuilCuit.setName("txtCuilCuit"); // NOI18N

        btnBuscar.setIcon(resourceMap.getIcon("btnBuscar.icon")); // NOI18N
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.setName("btnBuscar"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblApellido)
                    .addComponent(lblLegajo)
                    .addComponent(lblTipoDocumento))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtLegajo, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(txtApellidoRazonSocial, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(cbxTipoDocumento, javax.swing.GroupLayout.Alignment.LEADING, 0, 141, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumeroDocumento)
                    .addComponent(lblEstado)
                    .addComponent(lblCuil))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCuilCuit, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(cbxEstado, 0, 138, Short.MAX_VALUE)
                    .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLegajo)
                            .addComponent(lblCuil)
                            .addComponent(txtCuilCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNumeroDocumento)
                            .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTipoDocumento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblApellido)
                                .addComponent(txtApellidoRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEstado))))
                    .addComponent(btnBuscar))
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox cbxEstado;
    private javax.swing.JComboBox cbxTipoDocumento;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCuil;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblLegajo;
    private javax.swing.JLabel lblNumeroDocumento;
    private javax.swing.JLabel lblTipoDocumento;
    private javax.swing.JTextField txtApellidoRazonSocial;
    private javax.swing.JFormattedTextField txtCuilCuit;
    private javax.swing.JFormattedTextField txtLegajo;
    private javax.swing.JFormattedTextField txtNumeroDocumento;
    // End of variables declaration//GEN-END:variables
 
    
    
	public javax.swing.JButton getBtnBuscar() {
		return btnBuscar;
	}

	public javax.swing.JComboBox getCbxEstado() {
		return cbxEstado;
	}

	public javax.swing.JComboBox getCbxTipoDocumento() {
		return cbxTipoDocumento;
	}

	public javax.swing.JTextField getTxtApellidoRazonSocial() {
		return txtApellidoRazonSocial;
	}

	public javax.swing.JFormattedTextField getTxtCuilCuit() {
		return txtCuilCuit;
	}

	public javax.swing.JFormattedTextField getTxtLegajo() {
		return txtLegajo;
	}

	public javax.swing.JFormattedTextField getTxtNumeroDocumento() {
		return txtNumeroDocumento;
	}
	
	public JLabel getLblEstado(){
		return this.lblEstado;
	}

}
