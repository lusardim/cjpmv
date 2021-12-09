package ar.gov.cjpmv.prestamos.gui.configuracion.models;

import ar.gov.cjpmv.prestamos.core.business.dao.CategoriaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.gui.comunes.interfaces.AMModel;

public class AMCategoriaModel implements AMModel{

	private Categoria categoria;
	private CategoriaDAO categoriaDAO;
	
	
	
	public AMCategoriaModel(CategoriaDAO pCategoriaDAO, Categoria pCategoria){
		this.categoria= pCategoria;
		this.categoriaDAO= pCategoriaDAO;
	}
	
	public AMCategoriaModel(CategoriaDAO pCategoriaDAO){
		this(pCategoriaDAO, new Categoria());
	}
	
	@Override
	public String getDescripcion() {
		return this.categoria.getNombre();
	}

	@Override
	public void guardar() throws LogicaException{
		if(this.categoria.getId()==null){
			this.categoriaDAO.agregar(categoria);
		}
		else{
			this.categoriaDAO.modificar(categoria);
		}
	}
	

	@Override
	public void setDescripcion(String pDescripcion) {
		this.categoria.setNombre(pDescripcion);
	}

}

