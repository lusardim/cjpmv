package ar.gov.cjpmv.prestamos.gui.contable.modelos;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;

public class PlanCuentaTreeModel extends DefaultTreeModel {

	private static final long serialVersionUID = -6863615870155003972L;
	private boolean cambiado;
	
	public PlanCuentaTreeModel(PlanCuenta pPlanCuenta) {
		super(new PlanCuentaTreeNodeModel(pPlanCuenta));
	}
	
	@Override
	public PlanCuentaTreeNodeModel getRoot() {
		return (PlanCuentaTreeNodeModel)super.getRoot();
	}
	
	public void refrescar(PlanCuenta pPlanCuenta) {
		this.setRoot(new PlanCuentaTreeNodeModel(pPlanCuenta));
	}
	
	public void refrescar() {
		PlanCuentaTreeNodeModel nodo = (PlanCuentaTreeNodeModel) this.getRoot();
		PlanCuenta plan = nodo.getUserObject();
		this.setRoot(new PlanCuentaTreeNodeModel(plan));
	}

	public boolean isCambiado() {
		return cambiado;
	}

	public void setCambiado(boolean cambiado) {
		this.cambiado = cambiado;
	}

	@Override
	public void removeNodeFromParent(MutableTreeNode node) {
		// TODO Auto-generated method stub
		super.removeNodeFromParent(node);
	}
	/*@Override
	public void removeNodeFromParent(MutableTreeNode node) {
		super.removeNodeFromParent(node);
	//	node.removeFromParent();
		/*if (node instanceof CuentaTreeNodeModel) {
			CuentaTreeNodeModel cuentaModel = (CuentaTreeNodeModel)node;
			Cuenta cuenta = cuentaModel.getUserObject();
			cuenta.getPadre().getCuentasHijas().remove(cuenta);
		}
		else if (node instanceof PlanCuentaTreeModel)
	}*/
}
