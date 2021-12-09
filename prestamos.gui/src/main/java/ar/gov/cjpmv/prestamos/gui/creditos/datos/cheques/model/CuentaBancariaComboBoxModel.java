package ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.model;

import javax.swing.DefaultComboBoxModel;

import ar.gov.cjpmv.prestamos.core.business.dao.CuentaBancariaDAO;

public class CuentaBancariaComboBoxModel extends DefaultComboBoxModel{

	private static final long serialVersionUID = -2964325442737113141L;

	//TODO TOTALMENTE INEFICIENTE.. PERO SAFA! (cambiar por algo)
	private static CuentaBancariaDAO dao = new CuentaBancariaDAO();
	
	public CuentaBancariaComboBoxModel() {
		super(dao.findListaCuentaBancaria(null).toArray());
	}
}
