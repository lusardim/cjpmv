package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.util.ArrayList;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Plantilla;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class AdministracionPlantilla {
	
	private PlantillaDAO plantillaDAO;
	private ConceptoHaberesDAO conceptoHaberesDAO;
	
	public AdministracionPlantilla() {
		plantillaDAO = DAOFactory.getDefecto().getPlantillaDAO();
		conceptoHaberesDAO = DAOFactory.getDefecto().getConceptoHaberesDAO();
	}

	public void eliminar(Plantilla plantilla) throws LogicaException {
		plantillaDAO.eliminar(plantilla);
	}

	public void modificar(Plantilla plantilla) throws LogicaException {
		validarPlantilla(plantilla);
		plantillaDAO.modificar(plantilla);
	}

	public void agregar(Plantilla plantilla) throws LogicaException {
		validarPlantilla(plantilla);
		plantillaDAO.agregar(plantilla);
	}

	private void validarPlantilla(Plantilla plantilla) throws LogicaException {
		if (plantilla == null) {
			throw new LogicaException(141);
		}
		
		List<String> errores = new ArrayList<String>();
		if (plantilla.getTipoLiquidacion() == null) {
			errores.add("-Debe seleccionar un tipo de liquidación");
		}
		if (plantilla.getListaConceptos() == null || plantilla.getListaConceptos().isEmpty()) {
			errores.add("-La plantilla debe contener al menos un concepto");
		}
		Plantilla plantillaAnterior= plantillaDAO.getPlantilla(plantilla.getTipoLiquidacion());
		if(plantillaAnterior!=null && !plantillaAnterior.getId().equals(plantilla.getId())){
			errores.add(" - Ya existe una plantilla para este tipo de liquidación");
		}
		
		if (!errores.isEmpty()) {
			StringBuilder cadenaErrores = new StringBuilder();
			for (String error : errores) {
				cadenaErrores.append("\n"+error);
			}
			throw new LogicaException(142, cadenaErrores.toString());
		}
	}

	public Plantilla getPlantilla(TipoLiquidacion tipo) {
		if (tipo == null) {
			throw new NullPointerException("El tipo no puede ser nulo");
		}
		
		Plantilla plantilla = plantillaDAO.getPlantilla(tipo);
		if (plantilla == null) {
			plantilla = new Plantilla();
			plantilla.setTipoLiquidacion(tipo);
		}
		return plantilla;
	}

	public ConceptoHaberes getConceptoPorCodigo(int codigo) {
		return conceptoHaberesDAO.getConceptoPorCodigo(codigo);
	}

	public List<Plantilla> getPlantillas() {
		return plantillaDAO.getPlantillas();
	}

	public List<ConceptoHaberes> getConceptos() {
		return conceptoHaberesDAO.getConceptos();
	}
}
