/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlPlanIzda.java
 *
 * Created on 11/07/2011, 22:14:47
 */

package ar.gov.cjpmv.prestamos.gui.contable;

import javax.swing.JButton;
import javax.swing.JSpinner;

import org.jdesktop.swingx.JXTree;

import ar.gov.cjpmv.prestamos.core.business.AdministradorPlanCuenta;

/**
 *
 * @author daiana
 */
public class PnlPlanIzda extends javax.swing.JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5708166944619480613L;

	/** Creates new form PnlPlanIzda */
    public PnlPlanIzda() {
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

        pnlCopiaPlanCuentas = new ar.gov.cjpmv.prestamos.gui.contable.PnlCopiaPlanCuentas();
        panelFiltradoPlanCuenta = new ar.gov.cjpmv.prestamos.gui.contable.PnlFiltradoPlanCuenta();
        javax.swing.JSeparator separador = new javax.swing.JSeparator();

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelFiltradoPlanCuenta, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
            .add(pnlCopiaPlanCuentas, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(separador, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(pnlCopiaPlanCuentas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(separador, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelFiltradoPlanCuenta, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ar.gov.cjpmv.prestamos.gui.contable.PnlFiltradoPlanCuenta panelFiltradoPlanCuenta;
    private ar.gov.cjpmv.prestamos.gui.contable.PnlCopiaPlanCuentas pnlCopiaPlanCuentas;
    // End of variables declaration//GEN-END:variables
        
    public PnlFiltradoPlanCuenta getPanelFiltradoPlanCuenta() {
		return panelFiltradoPlanCuenta;
	}
    
	public JXTree gettArbol() {
		return panelFiltradoPlanCuenta.gettArbol();
	}

	public void actualizarModeloPlanCuenta() {
		panelFiltradoPlanCuenta.actualizarModeloPlanCuenta();
	}

	public JButton getBtnEliminar() {
		return panelFiltradoPlanCuenta.getBtnEliminar();
	}

	public JButton getBtnAgregar() {
		return panelFiltradoPlanCuenta.getBtnAgregar();
	}

	public JSpinner getSpAnio() {
		return pnlCopiaPlanCuentas.getSpAnio();
	}

	public ar.gov.cjpmv.prestamos.gui.contable.PnlCopiaPlanCuentas getPnlCopiaPlanCuentas() {
		return pnlCopiaPlanCuentas;
	}
}
