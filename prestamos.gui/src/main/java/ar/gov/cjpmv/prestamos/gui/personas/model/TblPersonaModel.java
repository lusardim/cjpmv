/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

/**
 *
 * @author pulpol
 */
public class TblPersonaModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7924509521556364780L;
	private List<? extends Persona> listaPersonas;
	
	public TblPersonaModel() {
		super();
		//Comienza con una lista vacía
		this.listaPersonas = new ArrayList<Persona>();
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return this.listaPersonas.size();
	}

	@Override
	public String getColumnName(int column) {
		switch(column){
			case 0: return "Tipo";
			case 1: return "Legajo";
			case 2: return "CUIT/CUIL";
			case 3: return "Apellido y Nombres";
			case 4: return "Estado";
		}
		return super.getColumnName(column);
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Persona locPersona = listaPersonas.get(rowIndex);
		if (locPersona instanceof PersonaFisica){
			return this.getValueAt((PersonaFisica)locPersona, columnIndex);
		}
		else{
			return this.getValueAt((PersonaJuridica)locPersona, columnIndex);
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 1: return Integer.class;
		}
		return super.getColumnClass(columnIndex);
	}
	
	private Object getValueAt(PersonaJuridica locPersona, int columnIndex) {
		switch(columnIndex){
			case 0: return "Jurídica";
			case 1: return null;
			case 2: return Utiles.getCuiFormateado(locPersona.getCui());
			case 3: return locPersona.getNombreYApellido();
			case 4: return locPersona.getEstado();
		}
		return null;
	}

	private Object getValueAt(PersonaFisica locPersona, int columnIndex) {
		switch(columnIndex){
			case 0: return "Física";
			case 1: return locPersona.getLegajo();
			case 2: return Utiles.getCuiFormateado(locPersona.getCui());
			case 3: return locPersona.getNombreYApellido();
			case 4: return locPersona.getEstado();
		}
		return null;
	}


	public List<? extends Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
		this.fireTableDataChanged();
	}

	public Persona getPersona(int pFila){
		return this.listaPersonas.get(pFila);
	}
}
