/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlABMCuenta.java
 *
 * Created on 28/06/2011, 22:10:08
 */

package ar.gov.cjpmv.prestamos.gui.contable;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreeNode;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.enums.TipoCuentaContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.enums.TipoSaldo;
import ar.gov.cjpmv.prestamos.gui.comunes.CamelCaseListCellRenderer;
import ar.gov.cjpmv.prestamos.gui.contable.modelos.CuentaTreeNodeModel;
import ar.gov.cjpmv.prestamos.gui.contable.modelos.PlanCuentaTreeNodeModel;
import ar.gov.cjpmv.prestamos.gui.personas.model.CbxCuentaContableCellRenderer;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

/**
 *
 * @author dernst
 */
public class PnlABMCuenta extends javax.swing.JPanel {

	private static final long serialVersionUID = -3235428025530178971L;

	/** Creates new form PnlABMCuenta */
    public PnlABMCuenta() {
        initComponents();
        inicializarRenderers();
        inicializarListeners();
        setHabilitado(false);
    }

    private void inicializarRenderers() {
    	btnAceptar.setEnabled(false);
    	btnCancelar.setEnabled(false);
    	ListCellRenderer renderer = new CamelCaseListCellRenderer();
    	this.cbxTipoCuenta.setRenderer(renderer);
    	this.cbxSaldo.setRenderer(renderer);
    	this.cbxSumariza.setRenderer(new CbxCuentaContableCellRenderer());
	}
    
	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        xtsTitulo = new org.jdesktop.swingx.JXTitledSeparator();
        pnlDatosCuenta = new javax.swing.JPanel();
        javax.swing.JLabel lblCodigo = new javax.swing.JLabel();
        txtSufijoCodigo = new javax.swing.JTextField();
        lblPrefijoCodigo = new javax.swing.JLabel();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        javax.swing.JLabel lblAbreviatura = new javax.swing.JLabel();
        txtAbreviatura = new javax.swing.JTextField();
        javax.swing.JLabel lblTipo = new javax.swing.JLabel();
        txtSaldoInicial = new javax.swing.JTextField();
        javax.swing.JLabel lblSaldo = new javax.swing.JLabel();
        javax.swing.JLabel lblSaldoInicial = new javax.swing.JLabel();
        cbxTipoCuenta = new javax.swing.JComboBox();
        cbxSaldo = new javax.swing.JComboBox();
        javax.swing.JLabel lblSumarizaEn = new javax.swing.JLabel();
        cbxSumariza = new javax.swing.JComboBox();
        chkRecibeAsientos = new javax.swing.JCheckBox();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlABMCuenta.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N

        xtsTitulo.setForeground(resourceMap.getColor("xtsTitulo.foreground")); // NOI18N
        xtsTitulo.setFont(resourceMap.getFont("xtsTitulo.font")); // NOI18N
        xtsTitulo.setName("xtsTitulo"); // NOI18N
        xtsTitulo.setTitle(resourceMap.getString("xtsTitulo.title")); // NOI18N

        pnlDatosCuenta.setBackground(resourceMap.getColor("pnlDatosCuenta.background")); // NOI18N
        pnlDatosCuenta.setName("pnlDatosCuenta"); // NOI18N

        lblCodigo.setFont(resourceMap.getFont("lblCodigo.font")); // NOI18N
        lblCodigo.setForeground(resourceMap.getColor("lblCodigo.foreground")); // NOI18N
        lblCodigo.setText(resourceMap.getString("lblCodigo.text")); // NOI18N
        lblCodigo.setName("lblCodigo"); // NOI18N

        txtSufijoCodigo.setName("txtSufijoCodigo"); // NOI18N

        lblPrefijoCodigo.setFont(resourceMap.getFont("lblPrefijoCodigo.font")); // NOI18N
        lblPrefijoCodigo.setForeground(resourceMap.getColor("lblPrefijoCodigo.foreground")); // NOI18N
        lblPrefijoCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPrefijoCodigo.setText(resourceMap.getString("lblPrefijoCodigo.text")); // NOI18N
        lblPrefijoCodigo.setName("lblPrefijoCodigo"); // NOI18N

        lblNombre.setFont(resourceMap.getFont("lblNombre.font")); // NOI18N
        lblNombre.setForeground(resourceMap.getColor("lblNombre.foreground")); // NOI18N
        lblNombre.setText(resourceMap.getString("lblNombre.text")); // NOI18N
        lblNombre.setName("lblNombre"); // NOI18N

        txtNombre.setName("txtNombre"); // NOI18N

        lblAbreviatura.setFont(resourceMap.getFont("lblAbreviatura.font")); // NOI18N
        lblAbreviatura.setForeground(resourceMap.getColor("lblAbreviatura.foreground")); // NOI18N
        lblAbreviatura.setText(resourceMap.getString("lblAbreviatura.text")); // NOI18N
        lblAbreviatura.setName("lblAbreviatura"); // NOI18N

        txtAbreviatura.setName("txtAbreviatura"); // NOI18N

        lblTipo.setFont(resourceMap.getFont("lblTipo.font")); // NOI18N
        lblTipo.setForeground(resourceMap.getColor("lblTipo.foreground")); // NOI18N
        lblTipo.setText(resourceMap.getString("lblTipo.text")); // NOI18N
        lblTipo.setName("lblTipo"); // NOI18N

        txtSaldoInicial.setName("txtSaldoInicial"); // NOI18N

        lblSaldo.setFont(resourceMap.getFont("lblSaldo.font")); // NOI18N
        lblSaldo.setForeground(resourceMap.getColor("lblSaldo.foreground")); // NOI18N
        lblSaldo.setText(resourceMap.getString("lblSaldo.text")); // NOI18N
        lblSaldo.setName("lblSaldo"); // NOI18N

        lblSaldoInicial.setFont(resourceMap.getFont("lblSaldoInicial.font")); // NOI18N
        lblSaldoInicial.setForeground(resourceMap.getColor("lblSaldoInicial.foreground")); // NOI18N
        lblSaldoInicial.setText(resourceMap.getString("lblSaldoInicial.text")); // NOI18N
        lblSaldoInicial.setName("lblSaldoInicial"); // NOI18N

        cbxTipoCuenta.setModel(new DefaultComboBoxModel(TipoCuentaContable.values()));
        cbxTipoCuenta.setName("cbxTipoCuenta"); // NOI18N

        cbxSaldo.setModel(new DefaultComboBoxModel(TipoSaldo.values()));
        cbxSaldo.setName("cbxSaldo"); // NOI18N

        lblSumarizaEn.setFont(resourceMap.getFont("lblSumarizaEn.font")); // NOI18N
        lblSumarizaEn.setForeground(resourceMap.getColor("lblSumarizaEn.foreground")); // NOI18N
        lblSumarizaEn.setText(resourceMap.getString("lblSumarizaEn.text")); // NOI18N
        lblSumarizaEn.setName("lblSumarizaEn"); // NOI18N

        cbxSumariza.setName("cbxSumariza"); // NOI18N

        chkRecibeAsientos.setBackground(resourceMap.getColor("chkRecibeAsientos.background")); // NOI18N
        chkRecibeAsientos.setFont(resourceMap.getFont("chkRecibeAsientos.font")); // NOI18N
        chkRecibeAsientos.setForeground(resourceMap.getColor("chkRecibeAsientos.foreground")); // NOI18N
        chkRecibeAsientos.setText(resourceMap.getString("chkRecibeAsientos.text")); // NOI18N
        chkRecibeAsientos.setName("chkRecibeAsientos"); // NOI18N

        javax.swing.GroupLayout pnlDatosCuentaLayout = new javax.swing.GroupLayout(pnlDatosCuenta);
        pnlDatosCuenta.setLayout(pnlDatosCuentaLayout);
        pnlDatosCuentaLayout.setHorizontalGroup(
            pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosCuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkRecibeAsientos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                    .addGroup(pnlDatosCuentaLayout.createSequentialGroup()
                        .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(lblCodigo)
                            .addComponent(lblAbreviatura)
                            .addComponent(lblSaldoInicial)
                            .addComponent(lblSaldo)
                            .addComponent(lblTipo)
                            .addComponent(lblSumarizaEn))
                        .addGap(23, 23, 23)
                        .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxSumariza, 0, 297, Short.MAX_VALUE)
                            .addComponent(txtSaldoInicial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                            .addComponent(cbxTipoCuenta, 0, 297, Short.MAX_VALUE)
                            .addComponent(txtAbreviatura, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                            .addGroup(pnlDatosCuentaLayout.createSequentialGroup()
                                .addComponent(lblPrefijoCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSufijoCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                            .addComponent(cbxSaldo, 0, 297, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlDatosCuentaLayout.setVerticalGroup(
            pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosCuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtSufijoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrefijoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAbreviatura, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAbreviatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTipoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSaldoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaldoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSumarizaEn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxSumariza))
                .addGap(18, 18, 18)
                .addComponent(chkRecibeAsientos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAceptar.setFont(resourceMap.getFont("btnAceptar.font")); // NOI18N
        btnAceptar.setForeground(resourceMap.getColor("btnAceptar.foreground")); // NOI18N
        btnAceptar.setIcon(resourceMap.getIcon("btnAceptar.icon")); // NOI18N
        btnAceptar.setText(resourceMap.getString("btnAceptar.text")); // NOI18N
        btnAceptar.setName("btnAceptar"); // NOI18N

        btnCancelar.setFont(resourceMap.getFont("btnCancelar.font")); // NOI18N
        btnCancelar.setForeground(resourceMap.getColor("btnCancelar.foreground")); // NOI18N
        btnCancelar.setIcon(resourceMap.getIcon("btnCancelar.icon")); // NOI18N
        btnCancelar.setText(resourceMap.getString("btnCancelar.text")); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(183, Short.MAX_VALUE)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlDatosCuenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(xtsTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xtsTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDatosCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cbxSaldo;
    private javax.swing.JComboBox cbxSumariza;
    private javax.swing.JComboBox cbxTipoCuenta;
    private javax.swing.JCheckBox chkRecibeAsientos;
    private javax.swing.JLabel lblPrefijoCodigo;
    private javax.swing.JPanel pnlDatosCuenta;
    private javax.swing.JTextField txtAbreviatura;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSaldoInicial;
    private javax.swing.JTextField txtSufijoCodigo;
    private org.jdesktop.swingx.JXTitledSeparator xtsTitulo;
    // End of variables declaration//GEN-END:variables
    private Cuenta cuenta;
    private TreeNode[] camino;
    
    public void setCuenta(Cuenta cuenta, TreeNode[] camino) {
    	this.cuenta = cuenta;
    	this.camino = camino;
		actualizarVista();
		btnCancelar.setEnabled(true);
		setHabilitado(true);
	}

	private void actualizarVista() {
		if (cuenta != null) {
			int indiceFinal = cuenta.getCodigoCompleto().length();
			indiceFinal -= (cuenta.getCodigo() != null)?cuenta.getCodigo().length():0;
			String codigo = cuenta.getCodigoCompleto().substring(0, indiceFinal);
			
			lblPrefijoCodigo.setText(codigo);
			txtSufijoCodigo.setText(cuenta.getCodigo());
			txtAbreviatura.setText(Utiles.vacioSiNulo(cuenta.getAbreviatura()));
			txtNombre.setText(Utiles.vacioSiNulo(cuenta.getNombre()));
			txtSaldoInicial.setText(Utiles.ceroSiNulo(cuenta.getSaldoInicial()).toString());
			cbxTipoCuenta.setSelectedItem(cuenta.getTipoCuenta());
			cbxSaldo.setSelectedItem(cuenta.getTipoSaldo());
			chkRecibeAsientos.setSelected(cuenta.isRecibeAsiento());
			
			if (cuenta.getPadre() == null) {
				cbxSumariza.setModel(new DefaultComboBoxModel());
			}
			else {
				List<Cuenta> listaSumarizaEn = convertirJerarquiaALista(cuenta.getPadre());
				listaSumarizaEn.add(0, null);
				if (!listaSumarizaEn.contains(cuenta.getSumarizaEn())) {
					listaSumarizaEn.add(cuenta.getSumarizaEn());
				}
				cbxSumariza.setModel(new DefaultComboBoxModel(listaSumarizaEn.toArray()));
				cbxSumariza.setSelectedItem(cuenta.getSumarizaEn());
				
				if (cuenta.getTipoCuenta() == null) {
					cbxTipoCuenta.setSelectedItem(cuenta.getPadre().getTipoCuenta());
				}
				
				if (cuenta.getTipoSaldo() == null) {
					cbxSaldo.setSelectedItem(cuenta.getPadre().getTipoSaldo());
				}
			}
		}
		else {
			limpiar();
		}
		btnAceptar.setEnabled(false);
	}

	private void limpiar() {
		btnCancelar.setEnabled(false);
		lblPrefijoCodigo.setText("0");
		chkRecibeAsientos.setSelected(false);
		for (Component cadaComponente : pnlDatosCuenta.getComponents()) {
			if (cadaComponente instanceof JTextField) {
				((JTextField)cadaComponente).setText("");
			}
			else if (cadaComponente instanceof JComboBox) {
				((JComboBox)cadaComponente).setSelectedItem(null);
			}
		}
	}

	private void actualizarModelo() {
		TreeNode padre = camino[camino.length-1];
		if (padre instanceof CuentaTreeNodeModel) {
			CuentaTreeNodeModel modeloCuentaPadre = (CuentaTreeNodeModel)padre;
			//Se agrega si no se está editando
			if (cuenta != modeloCuentaPadre.getUserObject()) {
				cuenta.setPadre(modeloCuentaPadre.getUserObject());
				cuenta.setPlanCuenta(modeloCuentaPadre.getUserObject().getPlanCuenta());
				modeloCuentaPadre.getUserObject().getCuentasHijas().add(cuenta);
			}
		}
		else if (padre instanceof PlanCuentaTreeNodeModel) {
			PlanCuenta planCuenta = ((PlanCuentaTreeNodeModel)padre).getUserObject();
			cuenta.setPlanCuenta(planCuenta);
		}
		cuenta.setCodigo(Utiles.nuloSiVacio(this.txtSufijoCodigo.getText()));
		cuenta.setNombre(Utiles.nuloSiVacio(txtNombre.getText()));
		cuenta.setRecibeAsiento(chkRecibeAsientos.isSelected());
		cuenta.setAbreviatura(Utiles.nuloSiVacio(txtAbreviatura.getText()));
		String cadenaSaldoInicial = txtSaldoInicial.getText();
		cuenta.setSaldoInicial(null);
		if (Utiles.isBigDecimal(cadenaSaldoInicial)) {
			cuenta.setSaldoInicial(new BigDecimal(cadenaSaldoInicial.trim()));
		}
		cuenta.setTipoCuenta((TipoCuentaContable)cbxTipoCuenta.getSelectedItem());
		cuenta.setTipoSaldo((TipoSaldo)cbxSaldo.getSelectedItem());
		cuenta.setSumarizaEn((Cuenta)cbxSumariza.getSelectedItem());
	}
	
	public Cuenta getCuenta() {
		return cuenta;
	}
	
	private List<Cuenta> convertirJerarquiaALista(Cuenta cuenta) {
		List<Cuenta> retorno = new ArrayList<Cuenta>();
		retorno.add(cuenta);
		if (cuenta.getPadre() != null) {
			retorno.addAll(convertirJerarquiaALista(cuenta.getPadre()));
		}
		return retorno;
	}

	private void guardar() {
		try {
			validar();
			if (cuenta == null) {
				cuenta = new Cuenta();
			}
			actualizarModelo();
			firePropertyChange("cuenta", null, cuenta);
			limpiar();
			setHabilitado(false);
		}
		catch(Exception e) {
			mostrarError(e);
		}
	}
	
    private void mostrarError(Exception e) {
    	JOptionPane.showMessageDialog(this, 
    			e.getMessage(), 
    			"Error", 
    			JOptionPane.ERROR_MESSAGE);
	}

	private void validar() throws LogicaException {
		String codigo = Utiles.nuloSiVacio(txtSufijoCodigo.getText());
		String nombre = Utiles.nuloSiVacio(txtNombre.getText());
		
		List<String> errores = new ArrayList<String>();
		if ((codigo == null) || (!codigo.matches("\\d+?"))) {
			errores.add("El código debe ser un número");
		}
		if (nombre == null) {
			errores.add("Debe ingresar un nombre");
		}
		if (cbxTipoCuenta.getSelectedItem() == null) {
			errores.add("Debe seleccionar un tipo de cuenta");
		}
		if (cbxSaldo.getSelectedItem() == null) {
			errores.add("Debe seleccionar el tipo de saldo");
		}
		if (!errores.isEmpty()) {
			StringBuilder mensajeError = new StringBuilder();
			for (String cadaError : errores) {
				mensajeError.append("\n - "+cadaError);
			}
			
			throw new LogicaException(131,mensajeError.toString());
		}
	}

	private void setHabilitado(boolean habilitado) {
    	for (Component component : this.pnlDatosCuenta.getComponents()) {
    		if ((component instanceof JTextComponent) || 
    		    (component instanceof JCheckBox) ||
    			(component instanceof JComboBox)) {
    			component.setEnabled(habilitado);
    		}
    	}
	}

	private void inicializarListeners() {
    	btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cuenta = null;
				actualizarVista();
			}
		});
    	btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});
    	
    	DocumentListener tocoAlgoListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				actualizarBoton();
			}

			private void actualizarBoton() {
				btnAceptar.setEnabled(true);					
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				actualizarBoton();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				actualizarBoton();
			}
		}; 

		ActionListener tocoAlgoActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAceptar.setEnabled(true);
			}
		};
		
    	for (Component cadaComponente : pnlDatosCuenta.getComponents()) {
			if (cadaComponente instanceof JTextField) {
				((JTextField)cadaComponente).getDocument().addDocumentListener(tocoAlgoListener);
			}
			else if (cadaComponente instanceof JComboBox) {
				((JComboBox)cadaComponente).addActionListener(tocoAlgoActionListener);
			}
			else if (cadaComponente instanceof JCheckBox) {
				((JCheckBox)cadaComponente).addActionListener(tocoAlgoActionListener);
			}
		}
	}
    
    public TreeNode[] getCamino() {
		return camino;
	}

	public void cancelar() {
		limpiar();
	}
}
