package ar.gov.cjpmv.prestamos.gui.creditos.components;

import javax.swing.JLabel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import ar.gov.cjpmv.prestamos.gui.Principal;

public abstract class AbstracJLabelWithProperties extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8382371669895391919L;
	private String nombre;
	
	public AbstracJLabelWithProperties(String pNombre) {
		super();
		this.nombre = pNombre;
		this.incializarPropiedades();
	}

	protected void incializarPropiedades() {
		ResourceMap resourceMap = Application.getInstance(Principal.class).getContext().getResourceMap(this.getClass());
		this.setForeground(resourceMap.getColor(this.nombre+".foreground")); // NOI18N
        this.setIcon(resourceMap.getIcon(this.nombre+".icon")); // NOI18N
        this.setText(resourceMap.getString(this.nombre+".text")); // NOI18N
        this.setName(this.nombre); // NOI18N
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
	}
	
}
