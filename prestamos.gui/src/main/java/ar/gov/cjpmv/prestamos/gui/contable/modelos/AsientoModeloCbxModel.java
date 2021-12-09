package ar.gov.cjpmv.prestamos.gui.contable.modelos;

import javax.swing.DefaultComboBoxModel;

import ar.gov.cjpmv.prestamos.core.business.dao.AsientoModeloDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CuentaBancariaDAO;

public class AsientoModeloCbxModel extends DefaultComboBoxModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8037251618116248685L;


	
	
	public AsientoModeloCbxModel(AsientoModeloDAO pAsientoModeloDAO) {
		super(pAsientoModeloDAO.getListaAsientosModelo().toArray());
	}

	
	
}
