/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlDetalleAsientoContable.java
 *
 * Created on 29/08/2011, 20:46:49
 */
package ar.gov.cjpmv.prestamos.gui.contable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.gov.cjpmv.prestamos.core.business.dao.AsientoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Asiento;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Movimiento;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.AdministracionFactory;
import ar.gov.cjpmv.prestamos.gui.creditos.AdminCreditoView;
import ar.gov.cjpmv.prestamos.gui.creditos.viaCobro.ModificacionViaCobroControllers;
import ar.gov.cjpmv.prestamos.gui.creditos.viaCobro.ModificaconViaCobro;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

/**
 *
 * @author daiana
 */

	


public class PnlDetalleAsientoContable extends javax.swing.JDialog {

	private AdministracionFactory adminFactory;
	private AdminContableView vistaPadre;
	private Movimiento movimiento;
	private String descripcionCuenta;
	

	
    
    /** Creates new form PnlDetalleAsientoContable */
    public PnlDetalleAsientoContable(JDialog pPadre, boolean modal, Movimiento pMovimiento) {
    	super(pPadre, true);
		initComponents();
		this.adminFactory= AdministracionFactory.getInstance();
		this.vistaPadre=  (AdminContableView) pPadre;
		this.movimiento= pMovimiento;
		this.inicializarEventos();
		this.inicializarModelo();
		this.actualizarVista();
			
    }
    
  
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlParametrosBusqueda = new javax.swing.JPanel();
        txtCuenta = new javax.swing.JTextField();
        xtsTitulo = new org.jdesktop.swingx.JXTitledSeparator();
        lblCodigo1 = new javax.swing.JLabel();
        txtMontoDebe = new javax.swing.JTextField();
        lblCodigo2 = new javax.swing.JLabel();
        lblCodigo3 = new javax.swing.JLabel();
        txtMontoHaber = new javax.swing.JTextField();
        txtDescripcionCuenta = new javax.swing.JTextField();
        lblCodigo4 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlDetalleAsientoContable.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N

        pnlParametrosBusqueda.setName("pnlParametrosBusqueda"); // NOI18N

        txtCuenta.setName("txtCuenta"); // NOI18N

        xtsTitulo.setForeground(resourceMap.getColor("xtsTitulo.foreground")); // NOI18N
        xtsTitulo.setFont(resourceMap.getFont("xtsTitulo.font")); // NOI18N
        xtsTitulo.setName("xtsTitulo"); // NOI18N
        xtsTitulo.setTitle(resourceMap.getString("xtsTitulo.title")); // NOI18N

        lblCodigo1.setFont(resourceMap.getFont("lblCodigo1.font")); // NOI18N
        lblCodigo1.setForeground(resourceMap.getColor("lblCodigo1.foreground")); // NOI18N
        lblCodigo1.setText(resourceMap.getString("lblCodigo1.text")); // NOI18N
        lblCodigo1.setName("lblCodigo1"); // NOI18N

        txtMontoDebe.setName("txtMontoDebe"); // NOI18N

        lblCodigo2.setFont(resourceMap.getFont("lblCodigo2.font")); // NOI18N
        lblCodigo2.setForeground(resourceMap.getColor("lblCodigo2.foreground")); // NOI18N
        lblCodigo2.setText(resourceMap.getString("lblCodigo2.text")); // NOI18N
        lblCodigo2.setName("lblCodigo2"); // NOI18N

        lblCodigo3.setFont(resourceMap.getFont("lblCodigo3.font")); // NOI18N
        lblCodigo3.setForeground(resourceMap.getColor("lblCodigo3.foreground")); // NOI18N
        lblCodigo3.setText(resourceMap.getString("lblCodigo3.text")); // NOI18N
        lblCodigo3.setName("lblCodigo3"); // NOI18N

        txtMontoHaber.setName("txtMontoHaber"); // NOI18N

        txtDescripcionCuenta.setName("txtDescripcionCuenta"); // NOI18N

        lblCodigo4.setFont(resourceMap.getFont("lblCodigo4.font")); // NOI18N
        lblCodigo4.setForeground(resourceMap.getColor("lblCodigo4.foreground")); // NOI18N
        lblCodigo4.setText(resourceMap.getString("lblCodigo4.text")); // NOI18N
        lblCodigo4.setName("lblCodigo4"); // NOI18N

        javax.swing.GroupLayout pnlParametrosBusquedaLayout = new javax.swing.GroupLayout(pnlParametrosBusqueda);
        pnlParametrosBusqueda.setLayout(pnlParametrosBusquedaLayout);
        pnlParametrosBusquedaLayout.setHorizontalGroup(
            pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametrosBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodigo4, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .addComponent(xtsTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlParametrosBusquedaLayout.createSequentialGroup()
                        .addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo2)
                            .addComponent(lblCodigo1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlParametrosBusquedaLayout.createSequentialGroup()
                                .addComponent(txtMontoDebe, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblCodigo3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMontoHaber, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)))
                    .addComponent(txtDescripcionCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlParametrosBusquedaLayout.setVerticalGroup(
            pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametrosBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xtsTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlParametrosBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCodigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCodigo3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMontoHaber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMontoDebe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCodigo4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txtDescripcionCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAceptar.setFont(resourceMap.getFont("btnAceptar.font")); // NOI18N
        btnAceptar.setForeground(resourceMap.getColor("btnAceptar.foreground")); // NOI18N
        btnAceptar.setIcon(null);
        btnAceptar.setText(resourceMap.getString("btnAceptar.text")); // NOI18N
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.setName("btnAceptar"); // NOI18N

        btnCancelar.setFont(resourceMap.getFont("btnCancelar.font")); // NOI18N
        btnCancelar.setForeground(resourceMap.getColor("btnCancelar.foreground")); // NOI18N
        btnCancelar.setIcon(null);
        btnCancelar.setText(resourceMap.getString("btnCancelar.text")); // NOI18N
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setName("btnCancelar"); // NOI18N
        
        JPanel pnlPrincipal = new JPanel();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlParametrosBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlParametrosBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        this.add(pnlPrincipal,BorderLayout.CENTER);
        this.pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblCodigo1;
    private javax.swing.JLabel lblCodigo2;
    private javax.swing.JLabel lblCodigo3;
    private javax.swing.JLabel lblCodigo4;
    private javax.swing.JPanel pnlParametrosBusqueda;
    private javax.swing.JTextField txtCuenta;
    private javax.swing.JTextField txtDescripcionCuenta;
    private javax.swing.JTextField txtMontoDebe;
    private javax.swing.JTextField txtMontoHaber;
    private org.jdesktop.swingx.JXTitledSeparator xtsTitulo;
    // End of variables declaration//GEN-END:variables
    
    
   
    
    
  

    
    private void actualizarVista() {
    	if(this.movimiento.getCuenta()!=null){
    		this.txtCuenta.setText(this.movimiento.getCuenta().getCodigoCompleto().toString()+" - "+this.movimiento.getCuenta().getNombre());
    	}
    	if(this.movimiento.getMontoDebe()!=null){
    		this.txtMontoDebe.setText(this.movimiento.getMontoDebe().toString());
    	}
    	if(this.movimiento.getMontoHaber()!=null){
    		this.txtMontoHaber.setText(this.movimiento.getMontoHaber().toString());
    	}
    	this.txtDescripcionCuenta.setText(this.descripcionCuenta);
    	this.txtCuenta.setEnabled(false);
    	this.txtMontoDebe.setEnabled(false);
    	this.txtMontoHaber.setEnabled(false);
	}

	private void inicializarModelo() {
		this.descripcionCuenta= Utiles.vacioSiNulo(this.movimiento.getDescripcionCuenta());
	}
	
	private void actualizarModelo(){
		this.descripcionCuenta= Utiles.vacioSiNulo(this.txtDescripcionCuenta.getText().trim());
	}

	private void inicializarEventos() {
		this.btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});
		
		this.btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		
	}
	
	private void guardar() {
		String mensaje="¿Realmente desea guardar los cambios efectuados?";
		int locConfirmacion= JOptionPane.showConfirmDialog(this, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.actualizarModelo();
			this.movimiento.setDescripcionCuenta(this.descripcionCuenta);
			this.dispose();
		}
	}
    


	private void cancelar() {
		String pMensaje= "¿Realmente desea cancelar la operación?";
		String pTitulo= "Confirmación";
		int locConfirmacion= JOptionPane.showConfirmDialog(this, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.dispose();	
		}
		
	}
	
    
}
