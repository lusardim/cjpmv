package ar.gov.cjpmv.prestamos.gui.sueldos;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class TipoLiquidacionCellRenderer extends BasicComboBoxRenderer  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8721767523841508047L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (value != null && value instanceof TipoLiquidacion) {
			switch((TipoLiquidacion)value) {
				case ACTIVO_NORMAL: value = "Activo Normal"; break; 
				case ACTIVO_SAC: value = "Activo SAC"; break;
				case JUBILACION_NORMAL: value = "Jubilación Normal"; break;
				case JUBILACION_SAC: value = "Jubilación SAC"; break;
				case PENSION_NORMAL: value = "Pensión Normal"; break;
				case PENSION_SAC: value = "Pensión SAC"; break;
			}
		}
		return super.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);
	}
}
