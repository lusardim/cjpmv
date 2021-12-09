package ar.gov.cjpmv.prestamos.gui.usuarios.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.business.dao.ConceptoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.ModuloSistemaDAO;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.ModuloSistema;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.Usuario;

public class TblModulosPermitidos extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352143931188753993L;

	private Usuario usuario;
	private ModuloSistemaDAO modulosDAO;
	private List<ModuloSistema> listaModulos; 

	
	






	public TblModulosPermitidos(Usuario pUsuario) {
		super();
		this.usuario= pUsuario;
		this.modulosDAO= new ModuloSistemaDAO();
		this.listaModulos= this.modulosDAO.findListaModulos();
	}
	

	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	
	
	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Módulo";break;
			case 1: nombre = "Permiso"; break;
		}
		return nombre;
	}
	
	
	@Override
	public int getRowCount() {
		if (this.listaModulos==null){
			return 0;
		}
		return this.listaModulos.size();
	}
	
	
	
	@Override
	public Class getColumnClass(int column)
	{
	      if (column == 0) return Object.class;
	      if (column == 1) return Boolean.class;
	      return Object.class;
	}


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 1){
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}
	

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 1){
			Boolean locBoolean = (Boolean)aValue;
			if (locBoolean){
				ModuloSistema locModuloSeleccionado = this.listaModulos.get(rowIndex);
				if (!this.usuario.getListaModulos().contains(locModuloSeleccionado)){
					this.usuario.getListaModulos().add(locModuloSeleccionado);
				}
			}
			else{
				
				ModuloSistema locModuloSeleccionado = this.listaModulos.get(rowIndex);
				if (this.usuario.getListaModulos().contains(locModuloSeleccionado)){
					this.usuario.getListaModulos().remove(locModuloSeleccionado);
				}
			}
		}
		super.setValueAt(aValue, rowIndex, columnIndex);
	}
	
	

	@Override
	public Object getValueAt(int row, int column) {
		ModuloSistema locModulo = this.listaModulos.get(row);
		
		switch(column){
			case 0: return locModulo.getDescripcion(); 
			case 1: {
				boolean valor= comparacionModulos(locModulo); 
				return valor;
			}
		}
		return null;
	}
	
	
	

	private boolean comparacionModulos(ModuloSistema pModulo) {
		for(ModuloSistema cadaModulo: this.usuario.getListaModulos()){
			long idA= cadaModulo.getId();
			long idB= pModulo.getId();
			if(idA==idB){
				return true;	
			}
		} 
		return false;
	}


	public ModuloSistema getRowAt(int fila) {
		return this.modulosDAO.findListaModulos().get(fila);
		
	}



	
	
	
	
}
