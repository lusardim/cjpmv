package ar.gov.cjpmv.prestamos.gui.comunes.interfaces;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;

public interface AMModel {
	public String getDescripcion();
	public void setDescripcion(String pDescripcion);
	public void guardar() throws LogicaException ;
}
