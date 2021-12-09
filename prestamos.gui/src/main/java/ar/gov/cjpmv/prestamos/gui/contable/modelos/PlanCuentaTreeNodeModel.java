package ar.gov.cjpmv.prestamos.gui.contable.modelos;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;

public class PlanCuentaTreeNodeModel extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -6403645726803153025L;
	
	public PlanCuentaTreeNodeModel(PlanCuenta planCuenta) {
		super(planCuenta);
		if (planCuenta == null) {
			throw new IllegalArgumentException("El plan de cuenta no puede ser nulo");
		}
		this.setAllowsChildren(true);
		for (Cuenta cadaCuenta : getUserObject().getListaCuenta()) {
			this.add(new CuentaTreeNodeModel(cadaCuenta));
		}
	}
	
	@Override
	public String toString() {
		return "Plan Cuenta "+getUserObject().getPeriodo();
	}
	
	@Override
	public PlanCuenta getUserObject() {
		return (PlanCuenta)super.getUserObject();
	}
	
	@Override
	public void remove(int childIndex) {
		TreeNode childAt = this.getChildAt(childIndex);
		if (childAt != null) {
			Cuenta cuenta = ((CuentaTreeNodeModel) childAt).getUserObject();
			this.getUserObject().remove(cuenta);
		}
		super.remove(childIndex);
	}
}
