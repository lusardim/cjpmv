package ar.gov.cjpmv.prestamos.gui.creditos.garantias;

import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class TblCellRendererViaCobro extends JComboBox implements TableCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	TblCellRendererViaCobro(List<ViaCobro> listaViaCobro) {
		super(listaViaCobro.toArray());
		// TODO Auto-generated constructor stub
	}


	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub

		if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        // Select the current value
        setSelectedItem(value);
        return this;
    }
	

}


