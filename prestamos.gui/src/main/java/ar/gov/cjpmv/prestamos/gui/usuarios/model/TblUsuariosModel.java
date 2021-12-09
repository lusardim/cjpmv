package ar.gov.cjpmv.prestamos.gui.usuarios.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.business.dao.UsuarioDAO;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.ModuloSistema;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.Usuario;

public class TblUsuariosModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6744589590025353417L;

	private List<Usuario> listaUsuario;
	
	public TblUsuariosModel(List<Usuario> pListaUsuario) {
		this.listaUsuario= pListaUsuario;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getRowCount() {
		if(this.listaUsuario==null){
			return 0;
		}
		else{
			return this.listaUsuario.size();
		}
	}
	
	
	@Override
	public Class getColumnClass(int column)
	{
	      if (column == 0) return Object.class;
	      if (column == 1) return Object.class;
	      return Object.class;
	}

	public String getColumnName(int column){
		String nombre=null;
		switch(column){
			case 0: nombre = "Usuario"; break;
			case 1: nombre = "Módulos Permitidos"; break;
		}
		return nombre;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Usuario locUsuario= this.listaUsuario.get(row);
		switch(column){
			case 0: return locUsuario.getNombre(); 
			case 1: {
				String listaModulosSistema=" ";
				boolean primero=true;
				for (ModuloSistema cadaModulo:locUsuario.getListaModulos()){
					if(primero){
						listaModulosSistema+=cadaModulo.getDescripcion();
					}
					else{
						listaModulosSistema+=" - "+cadaModulo.getDescripcion();
					}
					primero= false;
		
				}
				return listaModulosSistema; 
			}
		}
		return null;
	}
	
	public Usuario getUsuario(int pSeleccionado){
		return this.listaUsuario.get(pSeleccionado);
	}
	

	

}
