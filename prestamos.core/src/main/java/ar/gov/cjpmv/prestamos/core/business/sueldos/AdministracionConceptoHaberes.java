package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.util.ArrayList;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;

public class AdministracionConceptoHaberes {
	
	private ConceptoHaberesDAO conceptoHaberesDAO;
	
	/**
	 * Retorna el concepto con ese código o nulo en caso de no existir
	 * @return
	 */
	public ConceptoHaberes getConceptoPorCodigo(int codigo) {
		return getConceptoHaberesDAO().getConceptoPorCodigo(codigo);
	}
	
	public void eliminar(ConceptoHaberes concepto) throws LogicaException {
		if (concepto instanceof ConceptoPrefijado) {
			throw new LogicaException(133);
		}
		
		if (this.conceptoHaberesDAO.isEnLiquidacion(concepto)) {
			int codigo= 36;
			String campo="Error. No es posible eliminar el concepto de haber ya que se encuentra asociado a una liquidación.";
			throw new LogicaException(codigo, campo);
		}
		
		if (conceptoHaberesDAO.isEnPlantilla(concepto)) {
			int codigo= 36;
			String campo="Error. No es posible eliminar el concepto de haber ya que se encuentra asociado a una plantilla.";
			throw new LogicaException(codigo, campo);
		}
		
		getConceptoHaberesDAO().eliminar(concepto);
	}

	public void modificar(ConceptoHaberes concepto) throws LogicaException {
		if (concepto instanceof ConceptoPrefijado) {
			throw new LogicaException(133);
		}
		validar(concepto);
		getConceptoHaberesDAO().modificar(concepto);
	}

	public void agregar(ConceptoHaberes concepto) throws LogicaException {
		if (concepto instanceof ConceptoPrefijado) {
			throw new LogicaException(133);
		}
		validar(concepto);
		getConceptoHaberesDAO().agregar(concepto);
	}
	
	private void validar(ConceptoHaberes concepto) throws LogicaException {
		List<String> errores = new ArrayList<String>();
		if (concepto.getTipoCodigo() == null) {
			errores.add("- El tipo de código no puede estar vacío");
		}
		if (concepto.getDescripcion() == null || concepto.getDescripcion().trim().isEmpty()) {
			errores.add(" - La descripción no puede estar vacía");
		}
		ConceptoHaberes conceptoAnterior = getConceptoHaberesDAO().getConceptoPorCodigo(concepto.getCodigo());
		if (conceptoAnterior != null && !conceptoAnterior.getId().equals(concepto.getId())) {
			errores.add(" - El código está siendo utilizado en otro concepto.");
		}
		if (ConceptoPrefijado.getConcepto(concepto.getCodigo()) != null) {
			errores.add(" - El concepto no puede tener el código " + 
					concepto.getCodigo() + 
					" ya que se encuentra reservado");
		}
		
		if (!errores.isEmpty()) {
			StringBuilder salida = new StringBuilder();
			for (String error : errores) {
				salida.append("\n" + error);
			}
			throw new LogicaException(134, salida.toString());
		}
	}

	public ConceptoHaberesDAO getConceptoHaberesDAO() {
		if (this.conceptoHaberesDAO == null) {
			conceptoHaberesDAO = DAOFactory.getDefecto().getConceptoHaberesDAO();
		}
		return conceptoHaberesDAO;
	}

	public void setConceptoHaberesDAO(ConceptoHaberesDAO conceptoHaberesDAO) {
		this.conceptoHaberesDAO = conceptoHaberesDAO;
	}

	public List<ConceptoHaberes> getConceptos() {
		return getConceptoHaberesDAO().getConceptos();
	}

	
	public List<ConceptoHaberes> findConceptosHaberes(Integer pCodigo, String pDescripcion) {
		if(pCodigo==null && pDescripcion== null){
			return getConceptoHaberesDAO().getConceptos();
		}
		else{
			return getConceptoHaberesDAO().findConceptosHaberes(pCodigo, pDescripcion);
		}
	}

	public List<ConceptoHaberes> getConceptosPlantilla() {
		return getConceptoHaberesDAO().getConceptosPlantilla();
	}
}
