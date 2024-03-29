/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlABMEjercicioContable.java
 *
 * Created on 19/07/2011, 21:31:36
 */
package ar.gov.cjpmv.prestamos.gui.contable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;
import ar.gov.cjpmv.prestamos.gui.AdministracionFactory;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author daiana
 */
public class PnlABMEjercicioContable extends javax.swing.JPanel {

	private AdminContableView adminContable;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean activo;
	private EjercicioContable ejercicioContable;
	private AdministracionFactory adminFactory;
	
    /** Creates new form PnlABMEjercicioContable 
     * @param adminContableView */
    public PnlABMEjercicioContable(AdminContableView adminContableView) {
        initComponents();
        this.adminContable= adminContableView;
        this.adminFactory= AdministracionFactory.getInstance();
        this.inicializarEventos();
        this.inicializarVista();
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
        pnlParametrosBusqueda = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        lblCodigo1 = new javax.swing.JLabel();
        lblCodigo2 = new javax.swing.JLabel();
        lblCodigo3 = new javax.swing.JLabel();
        dtcFechaInicio = new com.toedter.calendar.JDateChooser();
        dtcFechaFin = new com.toedter.calendar.JDateChooser();
        lblPlanCuenta = new javax.swing.JLabel();
        cbxActivar = new javax.swing.JCheckBox();
        xtsTitulo = new org.jdesktop.swingx.JXTitledSeparator();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblTituloPnlDcha = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlABMEjercicioContable.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        pnlParametrosBusqueda.setBackground(resourceMap.getColor("pnlParametrosBusqueda.background")); // NOI18N
        pnlParametrosBusqueda.setName("pnlParametrosBusqueda"); // NOI18N

        lblCodigo.setFont(resourceMap.getFont("lblCodigo.font")); // NOI18N
        lblCodigo.setForeground(resourceMap.getColor("lblCodigo.foreground")); // NOI18N
        lblCodigo.setText("Año"); // NOI18N
        lblCodigo.setName("lblCodigo");

        lblCodigo1.setFont(resourceMap.getFont("lblCodigo1.font")); // NOI18N
        lblCodigo1.setForeground(resourceMap.getColor("lblCodigo1.foreground")); // NOI18N
        lblCodigo1.setText(resourceMap.getString("lblCodigo1.text")); // NOI18N
        lblCodigo1.setName("lblFechaInicio"); // NOI18N

        lblCodigo2.setFont(resourceMap.getFont("lblCodigo2.font")); // NOI18N
        lblCodigo2.setForeground(resourceMap.getColor("lblCodigo2.foreground")); // NOI18N
        lblCodigo2.setText(resourceMap.getString("lblCodigo2.text")); // NOI18N
        lblCodigo2.setName("lblCodigo2"); // NOI18N

        lblCodigo3.setFont(resourceMap.getFont("lblCodigo3.font")); // NOI18N
        lblCodigo3.setForeground(resourceMap.getColor("lblCodigo3.foreground")); // NOI18N
        lblCodigo3.setText(resourceMap.getString("lblCodigo3.text")); // NOI18N
        lblCodigo3.setName("lblCodigo3"); // NOI18N

        dtcFechaInicio.setForeground(resourceMap.getColor("dtcFechaInicio.foreground")); // NOI18N
        dtcFechaInicio.setFont(resourceMap.getFont("dtcFechaInicio.font")); // NOI18N
        dtcFechaInicio.setName("dtcFechaInicio"); // NOI18N

        dtcFechaFin.setForeground(resourceMap.getColor("dtcFechaFin.foreground")); // NOI18N
        dtcFechaFin.setFont(resourceMap.getFont("dtcFechaFin.font")); // NOI18N
        dtcFechaFin.setName("dtcFechaFin"); // NOI18N

        lblPlanCuenta.setFont(resourceMap.getFont("lblPlanCuenta.font")); // NOI18N
        lblPlanCuenta.setForeground(resourceMap.getColor("lblPlanCuenta.foreground")); // NOI18N
        lblPlanCuenta.setText(resourceMap.getString("lblPlanCuenta.text")); // NOI18N
        lblPlanCuenta.setName("lblPlanCuenta"); // NOI18N

        cbxActivar.setBackground(resourceMap.getColor("cbxActivar.background")); // NOI18N
        cbxActivar.setFont(resourceMap.getFont("cbxActivar.font")); // NOI18N
        cbxActivar.setForeground(resourceMap.getColor("cbxActivar.foreground")); // NOI18N
        cbxActivar.setText(resourceMap.getString("cbxActivar.text")); // NOI18N
        cbxActivar.setName("cbxActivar"); // NOI18N
        
        spAnio = new JSpinner();
        spAnio.setModel(new SpinnerNumberModel(2010, 1900, 2100, 1));

        javax.swing.GroupLayout gl_pnlParametrosBusqueda = new javax.swing.GroupLayout(pnlParametrosBusqueda);
        gl_pnlParametrosBusqueda.setHorizontalGroup(
        	gl_pnlParametrosBusqueda.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlParametrosBusqueda.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnlParametrosBusqueda.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pnlParametrosBusqueda.createSequentialGroup()
        					.addGroup(gl_pnlParametrosBusqueda.createParallelGroup(Alignment.TRAILING, false)
        						.addComponent(lblCodigo, Alignment.LEADING)
        						.addComponent(lblCodigo1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addGroup(gl_pnlParametrosBusqueda.createParallelGroup(Alignment.LEADING)
        						.addGroup(gl_pnlParametrosBusqueda.createSequentialGroup()
        							.addGap(76)
        							.addComponent(lblPlanCuenta, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
        						.addGroup(gl_pnlParametrosBusqueda.createSequentialGroup()
        							.addGap(12)
        							.addGroup(gl_pnlParametrosBusqueda.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(spAnio)
        								.addComponent(dtcFechaInicio, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))))
        					.addGap(38)
        					.addGroup(gl_pnlParametrosBusqueda.createParallelGroup(Alignment.LEADING)
        						.addComponent(cbxActivar, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
        						.addGroup(gl_pnlParametrosBusqueda.createSequentialGroup()
        							.addComponent(lblCodigo2)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(dtcFechaFin, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))))
        				.addComponent(lblCodigo3))
        			.addContainerGap(30, Short.MAX_VALUE))
        );
        gl_pnlParametrosBusqueda.setVerticalGroup(
        	gl_pnlParametrosBusqueda.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlParametrosBusqueda.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnlParametrosBusqueda.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblCodigo)
        				.addComponent(spAnio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pnlParametrosBusqueda.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo1, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        				.addComponent(lblCodigo2, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        				.addComponent(dtcFechaFin, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(dtcFechaInicio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pnlParametrosBusqueda.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblCodigo3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(cbxActivar, GroupLayout.PREFERRED_SIZE, 15, Short.MAX_VALUE)
        				.addComponent(lblPlanCuenta))
        			.addGap(35))
        );
        pnlParametrosBusqueda.setLayout(gl_pnlParametrosBusqueda);

        xtsTitulo.setForeground(resourceMap.getColor("xtsTitulo.foreground")); // NOI18N
        xtsTitulo.setFont(resourceMap.getFont("xtsTitulo.font")); // NOI18N
        xtsTitulo.setName("xtsTitulo"); // NOI18N
        xtsTitulo.setTitle(resourceMap.getString("xtsTitulo.title")); // NOI18N

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

        javax.swing.GroupLayout gl_jPanel1 = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(gl_jPanel1);
        gl_jPanel1.setHorizontalGroup(
            gl_jPanel1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
            .addGroup(gl_jPanel1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(gl_jPanel1.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_jPanel1.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(xtsTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                        .addGroup(gl_jPanel1.createSequentialGroup()
                            .addGap(307, 307, 307)
                            .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(pnlParametrosBusqueda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        gl_jPanel1.setVerticalGroup(
            gl_jPanel1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 343, Short.MAX_VALUE)
            .addGroup(gl_jPanel1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(gl_jPanel1.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(xtsTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(pnlParametrosBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(gl_jPanel1.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(141, Short.MAX_VALUE)))
        );

        lblTituloPnlDcha.setFont(resourceMap.getFont("lblTituloPnlDcha.font")); // NOI18N
        lblTituloPnlDcha.setForeground(resourceMap.getColor("lblTituloPnlDcha.foreground")); // NOI18N
        lblTituloPnlDcha.setIcon(resourceMap.getIcon("lblTituloPnlDcha.icon")); // NOI18N
        lblTituloPnlDcha.setText(resourceMap.getString("lblTituloPnlDcha.text")); // NOI18N
        lblTituloPnlDcha.setName("lblTituloPnlDcha"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTituloPnlDcha, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(12, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblTituloPnlDcha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox cbxActivar;
    private com.toedter.calendar.JDateChooser dtcFechaFin;
    private com.toedter.calendar.JDateChooser dtcFechaInicio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigo1;
    private javax.swing.JLabel lblCodigo2;
    private javax.swing.JLabel lblCodigo3;
    private javax.swing.JLabel lblPlanCuenta;
    private javax.swing.JLabel lblTituloPnlDcha;
    private javax.swing.JPanel pnlParametrosBusqueda;
    private org.jdesktop.swingx.JXTitledSeparator xtsTitulo;
    private JSpinner spAnio;
    // End of variables declaration//GEN-END:variables
    

    private void inicializarVista() {
		if (this.ejercicioContable != null) {
			Integer anio = ejercicioContable.getAnio();
			if (anio == null) {
				anio = Calendar.getInstance().get(Calendar.YEAR);
			}
			this.spAnio.setValue(anio);
			actualizarFechas();
			if (ejercicioContable.getFechaInicio() != null) {
				this.dtcFechaInicio.setDate(this.ejercicioContable.getFechaInicio());
			}
			if (ejercicioContable.getFechaFin() != null) {
				this.dtcFechaFin.setDate(this.ejercicioContable.getFechaFin());
			}
			boolean valor= this.ejercicioContable.isActivo();
			this.cbxActivar.setSelected(valor);
			if(this.ejercicioContable.getPlanCuenta() != null) {
				this.lblPlanCuenta.setText("Período "+ejercicioContable.getAnio());
			}
			else{
				this.lblPlanCuenta.setText("ninguno");
			}
		}
		else {
			int anio = Calendar.getInstance().get(Calendar.YEAR);
			this.spAnio.setValue(anio);
			actualizarFechas();
			this.cbxActivar.setSelected(false);
			this.lblPlanCuenta.setText("ninguno");
		}
		
	}

	private void inicializarEventos() {
		this.btnAceptar.addActionListener(new ActionListener(){
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
		this.spAnio.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				actualizarFechas();
			}
		});
	}
	
	private void actualizarFechas() {
		int anio = (Integer)spAnio.getValue();
		Calendar calendario = Calendar.getInstance();
		calendario.set(anio, Calendar.JANUARY, 1);
		
		dtcFechaInicio.setDate(calendario.getTime());
		calendario.set(Calendar.DAY_OF_YEAR, 
				calendario.getActualMaximum(Calendar.DAY_OF_YEAR));
		dtcFechaFin.setDate(calendario.getTime());
	}
	
	private void guardar() {
		try{
			this.actualizarModelo();
			if(this.ejercicioContable.getId()==null){
				this.adminFactory.getAdmistradorEjercicioContable().agregarEjercicio(this.ejercicioContable);
					String mensaje="Los datos se han guardado correctamente";
					String titulo="Nuevo Ejercicio Contable";
					JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
					this.adminContable.mostrarAdminEjercicioContable();
				
			}
			else{
				this.adminFactory.getAdmistradorEjercicioContable().editarEjercicio(this.ejercicioContable);
				String mensaje="Los datos se han actualizado correctamente";
				String titulo="Editar Ejercicio Contable";
				JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
				this.adminContable.mostrarAdminEjercicioContable();
			}
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void actualizarModelo() throws LogicaException {
		validarAnio();
		this.ejercicioContable.setAnio((Integer)spAnio.getValue());
		
		this.fechaInicio= this.dtcFechaInicio.getDate();
		if(this.fechaInicio==null){
			int codigo= 114;
			String campo= "fecha de inicio";
			throw new LogicaException(codigo, campo);
		}
		this.ejercicioContable.setFechaInicio(this.fechaInicio);
		
		this.fechaFin= this.dtcFechaFin.getDate();
		if(this.fechaFin==null){
			int codigo= 114;
			String campo= "fecha de fin";
			throw new LogicaException(codigo, campo);
		}
		this.ejercicioContable.setFechaFin(this.fechaFin);
		
		Calendar calInicio= Calendar.getInstance();
		calInicio.setTime(this.fechaInicio);
		Calendar calFin= Calendar.getInstance();
		calFin.setTime(this.fechaFin);
	
	
		if(Utiles.comparaFecha(calInicio, calFin)){
			int codigo= 115;
			String campo= "";
			throw new LogicaException(codigo, campo);
		}
		else{
			if(this.fechaInicio.after(this.fechaFin)){
				int codigo= 115;
				String campo= "";
				throw new LogicaException(codigo, campo);
			}
		}
		
		this.activo= this.cbxActivar.isSelected();
		this.ejercicioContable.setActivo(this.activo);      
	}
	

	private void validarAnio() throws LogicaException {
		Integer numPeriodo = (Integer)this.spAnio.getValue();
		if(numPeriodo > 2100) {
			int codigo=110;
			String campo=" menor a 2100.";
			throw new LogicaException(codigo, campo);
		}
		else{
			if(numPeriodo < 1900){
				int codigo=110;
				String campo=" mayor o igual a 1900.";
				throw new LogicaException(codigo, campo);
			}
		}
	}

	private void cancelar() {
		String pMensaje= "¿Realmente desea cancelar la operación?";
		String pTitulo= "Confirmación";
		//TODO CONVERTIR ESTE METODO EN GENERICO
		int locConfirmacion= JOptionPane.showConfirmDialog(this, pMensaje, pTitulo, JOptionPane.YES_NO_OPTION);
		if(locConfirmacion==JOptionPane.YES_OPTION){
			this.adminContable.mostrarAdminEjercicioContable();	
		}
	}

	public void setEjercicio(EjercicioContable pEjercicio) {
		this.ejercicioContable= pEjercicio;
		this.inicializarVista();
		
	}
}
