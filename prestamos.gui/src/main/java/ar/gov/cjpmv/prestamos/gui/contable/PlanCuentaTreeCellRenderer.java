package ar.gov.cjpmv.prestamos.gui.contable;

import javax.swing.tree.DefaultTreeCellRenderer;

public class PlanCuentaTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 6687724005931460769L;
	
	public PlanCuentaTreeCellRenderer() {
		super();
		this.setLeafIcon(null);
		setClosedIcon(null);
		setOpenIcon(null);
	}

}
