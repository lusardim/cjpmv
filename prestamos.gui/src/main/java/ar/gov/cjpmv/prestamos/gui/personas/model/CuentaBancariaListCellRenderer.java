package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoCuenta;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;

public class CuentaBancariaListCellRenderer extends DefaultListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6000286953591723546L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		if (value instanceof CuentaBancaria){
			CuentaBancaria locCuentaBancaria= (CuentaBancaria)value; 
			if (locCuentaBancaria!=null){
				TipoCuenta enumTipoCuenta= locCuentaBancaria.getTipo();
				String cadenaCuenta=this.getTipoCuenta(enumTipoCuenta);
				String nuevoValor = locCuentaBancaria.getNumero()+" ("+cadenaCuenta+" - "+locCuentaBancaria.getBanco()+")";
				return super.getListCellRendererComponent(list, nuevoValor, index, isSelected,
						cellHasFocus);
			}
		}
		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
	
	private String getTipoCuenta(TipoCuenta pTipoCuenta){
		switch (pTipoCuenta){ 
			case CUENTA_CORRIENTE: return "Cuenta Corriente"; 
			case CAJA_AHORRO: return "Caja de Ahorro";
		}
		return null;
	}
}
