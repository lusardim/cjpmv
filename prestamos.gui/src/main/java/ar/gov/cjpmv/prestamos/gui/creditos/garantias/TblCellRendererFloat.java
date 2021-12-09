package ar.gov.cjpmv.prestamos.gui.creditos.garantias;

import java.awt.Component;
import java.text.NumberFormat;
import javax.swing.table.DefaultTableCellRenderer;



public class TblCellRendererFloat extends DefaultTableCellRenderer {

	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();

	TblCellRendererFloat() {
		super();
		this.formateadorDecimal.setMinimumFractionDigits(2);
    	this.formateadorDecimal.setMaximumFractionDigits(2);
	}

	@Override
	protected void setValue(Object value) {
		super.setValue(formateadorDecimal.format(value)+"%");
	}

}
