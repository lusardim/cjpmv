package ar.gov.cjpmv.prestamos.gui.contable.modelos;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;

public class CuentaTreeNodeModel extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -8909868390407198957L;
	
	public CuentaTreeNodeModel(Cuenta cuenta) {
		super(cuenta);
		this.setAllowsChildren(true);
		this.setUserObject(cuenta);
		for (Cuenta cadaCuenta : cuenta.getCuentasHijas()) {
			this.add(new CuentaTreeNodeModel(cadaCuenta));
		}
	}
	
	@Override
	public void setParent(MutableTreeNode pNodo) {
		super.setParent(pNodo);
		if (pNodo instanceof CuentaTreeNodeModel) {
			CuentaTreeNodeModel modelo = (CuentaTreeNodeModel)pNodo;
			getUserObject().setPadre(modelo.getUserObject());
		}
	}
	
	@Override
	public Cuenta getUserObject() {
		return (Cuenta)super.getUserObject();
	}
	
	@Override
	public void remove(int childIndex) {
		TreeNode childAt = this.getChildAt(childIndex);
		if (childAt != null) {
			Cuenta cuenta = ((CuentaTreeNodeModel) childAt).getUserObject();
			this.getUserObject().getCuentasHijas().remove(cuenta);
		}
		super.remove(childIndex);
	}
	
	@Override
	public void removeFromParent() {
		this.getUserObject().getPadre()
			.getCuentasHijas()
			.remove(this.getUserObject());
		this.getUserObject().setPadre(null);
		super.removeFromParent();
	}
	
	@Override
	public String toString() {
		Cuenta cuenta = this.getUserObject();
		return cuenta.getCodigo()+" - "+cuenta.getNombre();
	}
}
