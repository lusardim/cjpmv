package ar.gov.cjpmv.prestamos.gui.configuracion.models;

import ar.gov.cjpmv.prestamos.core.business.dao.ProvinciaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;
import ar.gov.cjpmv.prestamos.gui.comunes.interfaces.AMModel;

public class AMProvinciaModel implements AMModel {
	private Provincia provincia;
	private ProvinciaDAO provinciaDAO;
	
	public AMProvinciaModel(ProvinciaDAO pProvinciaDAO, Provincia pProvincia){
		this.provinciaDAO= pProvinciaDAO;
		this.provincia= pProvincia;		
	}
	
	public AMProvinciaModel(ProvinciaDAO pProvinciaDAO){
		this(pProvinciaDAO, new Provincia());
	}
	
	public String getDescripcion(){
		return this.provincia.getNombre();
	}
	
	public void guardar() throws LogicaException {
		if(this.provincia.getId()==null){
			this.provinciaDAO.agregar(provincia);
		}
		else{
			this.provinciaDAO.modificar(provincia);
		}		
	}
	
	public void setDescripcion(String pDescripcion) {
		this.provincia.setNombre(pDescripcion);
	}

}
