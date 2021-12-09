package ar.gov.cjpmv.prestamos.gui.configuracion.models;

import ar.gov.cjpmv.prestamos.core.business.dao.EntidadBancariaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.TipoDocumentoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Banco;
import ar.gov.cjpmv.prestamos.gui.comunes.interfaces.AMModel;

public class AMBancoModel implements AMModel {

	
	private Banco banco;
	private EntidadBancariaDAO bancoDAO;
	
	
	
	public AMBancoModel(EntidadBancariaDAO pBancoDAO){
		this(pBancoDAO, new Banco());
	}
	

	public AMBancoModel(EntidadBancariaDAO pBancoDAO, Banco pBanco){
		bancoDAO= pBancoDAO;
		banco= pBanco;
	}


	
	
	
	@Override
	public String getDescripcion() {
		return this.banco.getNombre();
	}

	@Override
	public void guardar() throws LogicaException {
		if(this.banco.getId()==null){
			this.bancoDAO.agregar(banco);
		}
		else{
			this.bancoDAO.modificar(banco);
		}		
	}

	@Override
	public void setDescripcion(String pDescripcion) {
		this.banco.setNombre(pDescripcion);
	}
	
	

}

