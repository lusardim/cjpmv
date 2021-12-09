package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;

import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

public class TblPersonaJuridicaModel extends AbstractTableModel{

	private PersonaDAO personaDAO;
	private BusquedaPersonaJuridicaModel parametrosBusqueda;
	
	private MaskFormatter formatoCuil;
	
	private List<PersonaJuridica> listaPersonas;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 223719721403135008L;

	public TblPersonaJuridicaModel(BusquedaPersonaJuridicaModel pParametros) {
		this.personaDAO = new PersonaDAO();
		if (pParametros==null){
			throw new IllegalArgumentException("El modelo de búsqueda no puede ser nulo");
		}
		this.parametrosBusqueda = pParametros;
		this.listaPersonas = new ArrayList<PersonaJuridica>();
	
		formatoCuil = Utiles.getFormateadorCuit();
	}
	
	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return this.listaPersonas.size();
	}

	
	@Override
	public String getColumnName(int column) {
		switch(column){
			case 0: return "CUIT";
			case 1: return "Razón Social"; 
			case 2: return "Estado";
		}
		return null;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		try{
			PersonaJuridica locPersonaJuridica = this.getPersonaJuridica(rowIndex);
			switch(columnIndex){
				case 0: return this.formatoCuil.valueToString(locPersonaJuridica.getCui());
				case 1: return locPersonaJuridica.getRazonSocial();
				case 2: {
					String locEstado = locPersonaJuridica.getEstado().toString();
					return locEstado.substring(0, 1).toUpperCase()+locEstado.substring(1).toLowerCase();
				}
			}
			return null;
		}
		catch(ParseException e){
			e.printStackTrace();
			throw new RuntimeException("Ha ocurrido un error al intentar parsear el cuit", e);
		}
	}

	

	public PersonaJuridica getPersonaJuridica(int rowIndex) {
		return this.listaPersonas.get(rowIndex);
	}


	public void setParametrosBusqueda(
			BusquedaPersonaJuridicaModel parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
		this.actualizarBusqueda();
	}

	public void actualizarBusqueda() {
		this.listaPersonas = this.personaDAO.findListaPersonasJuridicas(this.parametrosBusqueda.getCuil(), 
											this.parametrosBusqueda.getRazonSocial(), 
											this.parametrosBusqueda.getEstado(), 
											this.parametrosBusqueda.getFechaInicioActividades(), 
											this.parametrosBusqueda.getFechaFinActividades());
		this.fireTableDataChanged();
	}

	
	
	
}
