package ar.gov.cjpmv.prestamos.gui.configuracion.models;

import ar.gov.cjpmv.prestamos.core.business.dao.TipoDocumentoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.gui.comunes.interfaces.AMModel;

public class AMTipoDocumentoModel implements AMModel {

	
	private TipoDocumento tipoDocumento;
	private TipoDocumentoDAO tipoDocumentoDAO;
	
	
	
	public AMTipoDocumentoModel(TipoDocumentoDAO pTipoDocumentoDAO){
		this(pTipoDocumentoDAO, new TipoDocumento());
	}
	

	public AMTipoDocumentoModel(TipoDocumentoDAO pTipoDocumentoDAO, TipoDocumento pTipoDocumento){
		tipoDocumentoDAO= pTipoDocumentoDAO;
		tipoDocumento= pTipoDocumento;
	}


	
	
	
	@Override
	public String getDescripcion() {
		return this.tipoDocumento.getDescripcion();
	}

	@Override
	public void guardar() throws LogicaException {
		if(this.tipoDocumento.getId()==null){
			this.tipoDocumentoDAO.agregar(tipoDocumento);
		}
		else{
			this.tipoDocumentoDAO.modificar(tipoDocumento);
		}		
	}

	@Override
	public void setDescripcion(String pDescripcion) {
		this.tipoDocumento.setDescripcion(pDescripcion);
	}
	
	

}
