/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlAdminEscalaCategoria.java
 *
 * Created on 22/02/2012, 15:42:45
 */
package ar.gov.cjpmv.prestamos.gui.sueldos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LiquidacionHaberes;
import ar.gov.cjpmv.prestamos.gui.AdministracionFactory;
import ar.gov.cjpmv.prestamos.gui.usuarios.model.TblModulosPermitidos;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

/**
 *
 * @author daiana
 */
public class PnlAdminEscalaCategoria extends javax.swing.JPanel {

	private static final long serialVersionUID = -6777004320199429154L;
	
	/** Creates new form PnlAdminEscalaCategoria */
	private AdminSueldosView adminSueldo;
	private AdministracionFactory adminFactory;
	private EstadoPersonaFisica estadoPersonaFisica;
	private BigDecimal valorBeneficioJubilacion;
	private BigDecimal valorBeneficioPension;
    
    public PnlAdminEscalaCategoria(AdminSueldosView adminSueldosView) {
        initComponents();
        this.adminSueldo= adminSueldosView;
       	this.adminFactory= AdministracionFactory.getInstance();
        this.inicializarEventos(); 
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
        lblTituloPnlDcha = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblTituloClase = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEscalaSalarial = new javax.swing.JTable();
        lblValorJubilacion = new javax.swing.JLabel();
        lblValorPension = new javax.swing.JLabel();
        txtValorJubilacion = new javax.swing.JTextField();
        txtValorPension = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlAdminEscalaCategoria.class);
        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        lblTituloPnlDcha.setFont(resourceMap.getFont("lblTituloPnlDcha.font")); // NOI18N
        lblTituloPnlDcha.setForeground(resourceMap.getColor("lblTituloPnlDcha.foreground")); // NOI18N
        lblTituloPnlDcha.setIcon(resourceMap.getIcon("lblTituloPnlDcha.icon")); // NOI18N
        lblTituloPnlDcha.setText(resourceMap.getString("lblTituloPnlDcha.text")); // NOI18N
        lblTituloPnlDcha.setName("lblTituloPnlDcha"); // NOI18N

        jPanel2.setBackground(resourceMap.getColor("jPanel2.background")); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        lblTituloClase.setFont(resourceMap.getFont("lblTituloClase.font")); // NOI18N
        lblTituloClase.setForeground(resourceMap.getColor("lblTituloClase.foreground")); // NOI18N
        lblTituloClase.setText(resourceMap.getString("lblTituloClase.text")); // NOI18N
        lblTituloClase.setName("lblTituloClase"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblEscalaSalarial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Categoría", "Porcentaje sobre Categoría 1", "Monto"
            }
        ));
        tblEscalaSalarial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblEscalaSalarial.setName("tblEscalaSalarial"); // NOI18N
        jScrollPane1.setViewportView(tblEscalaSalarial);

        lblValorJubilacion.setFont(resourceMap.getFont("lblValorJubilacion.font")); // NOI18N
        lblValorJubilacion.setForeground(resourceMap.getColor("lblValorJubilacion.foreground")); // NOI18N
        lblValorJubilacion.setText(resourceMap.getString("lblValorJubilacion.text")); // NOI18N
        lblValorJubilacion.setName("lblValorJubilacion"); // NOI18N

        lblValorPension.setFont(resourceMap.getFont("lblValorPension.font")); // NOI18N
        lblValorPension.setForeground(resourceMap.getColor("lblValorPension.foreground")); // NOI18N
        lblValorPension.setText(resourceMap.getString("lblValorPension.text")); // NOI18N
        lblValorPension.setName("lblValorPension"); // NOI18N

        txtValorJubilacion.setText(resourceMap.getString("txtValorJubilacion.text")); // NOI18N
        txtValorJubilacion.setName("txtValorJubilacion"); // NOI18N

        txtValorPension.setText(resourceMap.getString("txtValorPension.text")); // NOI18N
        txtValorPension.setName("txtValorPension"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblValorPension)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtValorPension, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblValorJubilacion)
                                .addGap(18, 18, 18)
                                .addComponent(txtValorJubilacion, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblTituloClase, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloClase, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValorJubilacion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorJubilacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValorPension, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(txtValorPension, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTituloPnlDcha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPnlDcha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAceptar.setFont(resourceMap.getFont("btnAceptar.font")); // NOI18N
        btnAceptar.setForeground(resourceMap.getColor("btnAceptar.foreground")); // NOI18N
        btnAceptar.setIcon(resourceMap.getIcon("btnAceptar.icon")); // NOI18N
        btnAceptar.setText(resourceMap.getString("btnAceptar.text")); // NOI18N
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.setName("btnAceptar"); // NOI18N

        btnCancelar.setFont(resourceMap.getFont("btnCancelar.font")); // NOI18N
        btnCancelar.setForeground(resourceMap.getColor("btnCancelar.foreground")); // NOI18N
        btnCancelar.setIcon(resourceMap.getIcon("btnCancelar.icon")); // NOI18N
        btnCancelar.setText(resourceMap.getString("btnCancelar.text")); // NOI18N
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setName("btnCancelar"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(487, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(367, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(61, 61, 61)))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTituloClase;
    private javax.swing.JLabel lblTituloPnlDcha;
    private javax.swing.JLabel lblValorJubilacion;
    private javax.swing.JLabel lblValorPension;
    private javax.swing.JTable tblEscalaSalarial;
    private javax.swing.JTextField txtValorJubilacion;
    private javax.swing.JTextField txtValorPension;

	private CategoriaTableModel modeloTblEscalaSalarial;
    // End of variables declaration//GEN-END:variables

    public void inicializarVista() {
    	if(this.estadoPersonaFisica.equals(EstadoPersonaFisica.ACTIVO)){
    		this.lblTituloClase.setText("Ingrese los datos para la clase Activa: Categoría y Escala Salarial");
    		this.lblValorJubilacion.setVisible(false);
    		this.lblValorPension.setVisible(false);
    		this.txtValorJubilacion.setVisible(false);
    		this.txtValorPension.setVisible(false);
    	}
    	else{
    		this.lblTituloClase.setText("Ingrese los datos para la clase Pasiva: Categoría y Escala Salarial");
    		this.lblValorJubilacion.setVisible(true);
    		this.lblValorPension.setVisible(true);
    		this.txtValorJubilacion.setVisible(true);
    		this.txtValorPension.setVisible(true);
    		this.txtValorJubilacion.setText(this.valorBeneficioJubilacion.toString());
    		this.txtValorPension.setText(this.valorBeneficioPension.toString());
    	}
	}

	public void inicializarModelo(EstadoPersonaFisica pEstado) {
		this.estadoPersonaFisica= pEstado;
		if(pEstado.equals(EstadoPersonaFisica.PASIVO)){
			this.valorBeneficioJubilacion= this.adminFactory.getAdministracionLiquidacionHaberes().getPorcentajeJubilacion();
			this.valorBeneficioPension= this.adminFactory.getAdministracionLiquidacionHaberes().getPorcentajePension();
		}
		if (pEstado != null) {
			this.modeloTblEscalaSalarial = new CategoriaTableModel(estadoPersonaFisica);
			this.tblEscalaSalarial.setModel(modeloTblEscalaSalarial);
		}
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
		try{
			this.actualizarModelo();
			if(this.estadoPersonaFisica.equals(EstadoPersonaFisica.PASIVO)){
				this.adminFactory.getAdministracionLiquidacionHaberes().setPorcentajeJubilacion(this.valorBeneficioJubilacion);
				this.adminFactory.getAdministracionLiquidacionHaberes().setPorcentajePension(this.valorBeneficioPension);
			}
			this.modeloTblEscalaSalarial.guardar();
			String mensaje="Los datos se han modificado correctamente";
			String titulo="Configuración de Escala Salarial";
			JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
			this.adminSueldo.mostrarAdminLiquidacionHaberes();	
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void actualizarModelo() throws LogicaException {
		if(this.estadoPersonaFisica.equals(EstadoPersonaFisica.PASIVO)){
			String cadenaValorJubilacion= Utiles.nuloSiVacio(this.txtValorJubilacion.getText().trim());
			String cadenaValorPension= Utiles.nuloSiVacio(this.txtValorPension.getText().trim());
		
			if(cadenaValorJubilacion==null || cadenaValorPension==null){
				int codigo= 36;
				String campo="Error. Debe ingresar un valor para cada beneficio.";
				throw new LogicaException(codigo,campo);
			}
			else{
				try{
					this.valorBeneficioJubilacion= new BigDecimal(cadenaValorJubilacion);
					this.valorBeneficioPension= new BigDecimal(cadenaValorPension);
				}
				catch(NumberFormatException e){
					int codigo= 36;
					String campo="Error. El dato ingresado para el valor de los beneficios no tiene un formato válido.";
					throw new LogicaException(codigo,campo);
				}
			}
		}
	}


	private void cancelar() {
		String locMensaje = "¿Está seguro que desea cancelar la operación?";
		int valor = JOptionPane.showConfirmDialog(this,locMensaje,"Cancelar", JOptionPane.YES_NO_OPTION);
		if (valor == JOptionPane.YES_OPTION) {
			this.adminSueldo.mostrarAdminLiquidacionHaberes();
		}	
		
	}
	
	
}