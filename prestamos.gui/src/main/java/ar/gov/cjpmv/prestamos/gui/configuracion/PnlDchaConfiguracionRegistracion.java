/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlDchaConfiguracionRegistracion.java
 *
 * Created on 19/01/2010, 10:47:14
 */

package ar.gov.cjpmv.prestamos.gui.configuracion;

import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author Administrador
 */
public class PnlDchaConfiguracionRegistracion extends javax.swing.JPanel {

    /** Creates new form PnlDchaConfiguracionRegistracion */
    public PnlDchaConfiguracionRegistracion() {
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

        xlblTitulo = new org.jdesktop.swingx.JXLabel();
        pnlBusquedaConfiguracion1 = new ar.gov.cjpmv.prestamos.gui.configuracion.PnlBusquedaConfiguracion();
        pnlListadoViewRegistracion1 = new ar.gov.cjpmv.prestamos.gui.configuracion.PnlListadoViewRegistracion();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlDchaConfiguracionRegistracion.class);
        setBackground(resourceMap.getColor("ColorBlanco")); // NOI18N
        setName("Form"); // NOI18N

        xlblTitulo.setForeground(resourceMap.getColor("xlblTitulo.foreground")); // NOI18N
        xlblTitulo.setIcon(resourceMap.getIcon("xlblTitulo.icon")); // NOI18N
        xlblTitulo.setText(resourceMap.getString("xlblTitulo.text")); // NOI18N
        xlblTitulo.setFont(resourceMap.getFont("TituloBarrita")); // NOI18N
        xlblTitulo.setName("xlblTitulo"); // NOI18N

        pnlBusquedaConfiguracion1.setName("pnlBusquedaConfiguracion1"); // NOI18N

        pnlListadoViewRegistracion1.setName("pnlListadoViewRegistracion1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xlblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .addComponent(pnlBusquedaConfiguracion1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .addComponent(pnlListadoViewRegistracion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xlblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(pnlBusquedaConfiguracion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlListadoViewRegistracion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ar.gov.cjpmv.prestamos.gui.configuracion.PnlBusquedaConfiguracion pnlBusquedaConfiguracion1;
    private ar.gov.cjpmv.prestamos.gui.configuracion.PnlListadoViewRegistracion pnlListadoViewRegistracion1;
    private org.jdesktop.swingx.JXLabel xlblTitulo;
    // End of variables declaration//GEN-END:variables
    
    public JXLabel getXlblTitulo(){
    	return this.xlblTitulo;
    }
    
    public JButton getBtnBuscar(){
    	return this.pnlBusquedaConfiguracion1.getBtnBuscar();
    }
    
    public JTextField getTxtDescripcion(){
    	return this.pnlBusquedaConfiguracion1.getTxtDescripcion();
    }
   

   
    public JLabel getLblTitulo(){
    	return this.pnlListadoViewRegistracion1.getLblTitulo();
    }
    public JList getLLista(){
    	return this.pnlListadoViewRegistracion1.getLLista();
    }
    public JLabel getLblMensaje(){
    	return this.pnlListadoViewRegistracion1.getLblMensaje();
    }
    public JButton getBtnRegistrar(){
    	return this.pnlListadoViewRegistracion1.getBtnRegistrar();
    }
}
