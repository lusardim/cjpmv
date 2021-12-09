package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;

public class TblPersonaFisicaModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3180029816843864770L;
	
	private BusquedaPersonasModel busquedaPersonaModel;
	
	private PersonaDAO personaDAO;
	
	private List<PersonaFisica> listaPersonasFisicas;
	
	public TblPersonaFisicaModel(BusquedaPersonasModel pModelo) {
		this.personaDAO = new PersonaDAO();
		this.busquedaPersonaModel = pModelo;
		this.listaPersonasFisicas = new ArrayList<PersonaFisica>();
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return this.listaPersonasFisicas.size();
	}

	
	@Override
	public String getColumnName(int column) {
		switch(column){
			case 0:	return "Tipo de documento";
			case 1: return "Nº de Documento";
			case 2:	return "Apellido";
			case 3: return "Nombres";
			case 4: return "Estado";
		}
		return null;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		PersonaFisica locPersonaFisica = this.getPersonaFisica(rowIndex);
		switch(columnIndex){
			case 0:	return locPersonaFisica.getTipoDocumento();
			case 1: return locPersonaFisica.getNumeroDocumento();
			case 2:	return locPersonaFisica.getApellido();
			case 3: return locPersonaFisica.getNombre();
			case 4: return locPersonaFisica.getEstado();
		}
		return null;
	}


	public PersonaFisica getPersonaFisica(int locSeleccionado) {
		return this.listaPersonasFisicas.get(locSeleccionado);
	}

	public void refrescarBusqueda() {
		String locEstado = this.busquedaPersonaModel.getEstado();
		EstadoPersonaFisica estado = null;
		if (locEstado!=null && !locEstado.isEmpty()){
			estado = EstadoPersonaFisica.valueOf(locEstado);
		}
		
		this.listaPersonasFisicas = this.personaDAO.findListaPersonasFisicas(
				this.busquedaPersonaModel.getLegajo(), 
				this.busquedaPersonaModel.getCuip(), 
				this.busquedaPersonaModel.getTipoDocumento(), 
				this.busquedaPersonaModel.getNumeroDocumento(), 
				this.busquedaPersonaModel.getApellido(), 
				estado);
		this.fireTableDataChanged();
		
	}
}
