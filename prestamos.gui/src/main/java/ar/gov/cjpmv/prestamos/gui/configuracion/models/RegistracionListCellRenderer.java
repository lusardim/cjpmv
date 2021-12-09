package ar.gov.cjpmv.prestamos.gui.configuracion.models;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import ar.gov.cjpmv.prestamos.core.persistence.Departamento;

public class RegistracionListCellRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2637835264349693837L;


	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		RegistracionModel locRegistracionModel = (RegistracionModel)value; 
		
		String nuevoValor = locRegistracionModel.toString();
		String locValorCadena= locRegistracionModel.toString();
		int locResultado=locValorCadena.indexOf("[REGISTRACIÓN ACTIVA]");
		if(locResultado!=-1){
		
			nuevoValor=nuevoValor.toUpperCase();
		}
	
		return super.getListCellRendererComponent(list, nuevoValor, index, isSelected,
				cellHasFocus);
	}


}
