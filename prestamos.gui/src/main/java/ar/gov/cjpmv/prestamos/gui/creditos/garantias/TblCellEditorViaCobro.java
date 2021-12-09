package ar.gov.cjpmv.prestamos.gui.creditos.garantias;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;

public class TblCellEditorViaCobro extends DefaultCellEditor{
	
	public TblCellEditorViaCobro(List<ViaCobro> listaViaCobro) {
        super(new JComboBox(listaViaCobro.toArray()));
    }
}