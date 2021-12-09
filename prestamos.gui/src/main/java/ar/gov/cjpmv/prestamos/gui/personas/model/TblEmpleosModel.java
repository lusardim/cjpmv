package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionRevista;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;

public class TblEmpleosModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4723341564908670508L;
	
	
	private PersonaFisica empleado;
	
	private DateFormat formateadorFechas;
	
	public TblEmpleosModel(PersonaFisica pEmpleado) {
		super();
		this.empleado = pEmpleado;
		this.formateadorFechas = DateFormat.getDateInstance(DateFormat.SHORT);
	}
	
	@Override
	public int getColumnCount() {
		return 6; 
	}
	
	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Organismo";break;
			case 1: nombre = "Fecha Inicio"; break;
			case 2: nombre = "Fecha Fin"; break;
			case 3: nombre = "Situacion de Revista"; break;
			case 4: nombre = "Categoria"; break;
			case 5: nombre = "Función"; break;
		}
		return nombre;
	}
	
	@Override
	public int getRowCount() {
		if (this.empleado.getListaEmpleos()==null){
			return 0;
		}
		return this.empleado.getListaEmpleos().size();
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Empleo locEmpleo = this.empleado.getListaEmpleos().get(row);
		
		switch(column){
			case 0: return locEmpleo.getEmpresa(); 
			case 1: {
				if (locEmpleo.getFechaInicio()==null){
					return null;
				}
				else{
					return this.formateadorFechas.format(locEmpleo.getFechaInicio());
				}
			}
			case 2:{
				if (locEmpleo.getFechaFin()==null){
					return null;
				}
				else{
					return this.formateadorFechas.format(locEmpleo.getFechaFin());
				}
			}
			case 3: return this.getSituacionRevista(locEmpleo.getSituacionRevista());
			case 4: return locEmpleo.getTipoCategoria();
			case 5: return locEmpleo.getFuncion();
	
		}
		return null;
	}


	/**
	 * Obtiene la cadena de la situación de revista como algo legible 
	 * @param pSituacionRevista
	 * @return
	 */
	private String getSituacionRevista(SituacionRevista pSituacionRevista) {
		String valor = null;
		if (pSituacionRevista!=null){
			switch(pSituacionRevista){
				case CONTRATO_OBRA: valor="Contrato de Obra"; break;
				case CONTRATO_SERVICIO: valor="Contrado de Servicio"; break;
				case OTRO: valor="Otro"; break;
				case PLANTA_PERMANENTE: valor="Planta permanente"; break;
				case JORNALIZADO: valor="Temporario"; break;
			}
		}
		return valor;
	}

	public void add(Empleo modelo) {
		List<Empleo> locLista = this.getListaEmpleos();
		locLista.add(modelo);
		modelo.setEmpleado(this.empleado);
		this.fireTableRowsInserted(locLista.size()-1, locLista.size());
	}

	private List<Empleo> getListaEmpleos() {
		if (this.empleado.getListaEmpleos()==null){
			this.empleado.setListaEmpleos(new ArrayList<Empleo>());
		}
		return this.empleado.getListaEmpleos();
	}

	public Empleo getRowAt(int fila) {
		return this.empleado.getListaEmpleos().get(fila);
		
	}

	public void remove(int fila) {
		this.empleado.getListaEmpleos().remove(fila);
		this.fireTableRowsDeleted(fila-1, fila);
		
	}
	
}
