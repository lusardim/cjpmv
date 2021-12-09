package ar.gov.cjpmv.prestamos.core.business;

import java.util.List;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.dao.EjercicioContableDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.LibroDiarioDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;

public class AdministradorEjercicioContable {

	private EjercicioContableDAO ejercicioContableDAO;
	private LibroDiarioDAO libroDiarioDao;

	public AdministradorEjercicioContable() {
		ejercicioContableDAO = DAOFactory.getDefecto().getEjercicioContableDAO();
		libroDiarioDao = DAOFactory.getDefecto().getLibroDiarioDAO();
	}
	
	private EjercicioContableDAO getEjercicioContableDAO() {
		return ejercicioContableDAO;
	}
	
	public List<EjercicioContable> findListaEjercicioContable(String nombre) {
		return this.getEjercicioContableDAO()
				.getListaEjercicios(nombre);
	}

	public void activarPeriodo(EjercicioContable locEjercicio) throws LogicaException {
		try {
			List<EjercicioContable> lista = this.getEjercicioContableDAO().findListaEjerciciosActivos();
			if(!lista.isEmpty()){
				for(EjercicioContable cadaEjercicio: lista){
					cadaEjercicio.setActivo(false);
					this.getEjercicioContableDAO().modificar(cadaEjercicio);
				}
			}
			locEjercicio.setActivo(true);
			this.getEjercicioContableDAO().modificar(locEjercicio);
		} 
		catch (LogicaException e) {
			int codigo= 10;
			String campo="";
			throw new LogicaException(codigo, campo);
		}
	}
	
	public void setEjercicioContableDAO(EjercicioContableDAO ejercicioContableDAO) {
		this.ejercicioContableDAO = ejercicioContableDAO;
	}

	public void editarEjercicio(EjercicioContable pEjercicio) throws LogicaException {
		EjercicioContable ejercicioContable = getEjercicioContableDAO()
				.getEjercicioContablePorAnio(pEjercicio.getAnio());
		
		if (ejercicioContable.getId() == pEjercicio.getId()) {
			if(pEjercicio.isActivo()){
				List<EjercicioContable> lista = getEjercicioContableDAO().findListaEjerciciosActivos();
				for(EjercicioContable cadaEjercicio: lista) {
					if (!cadaEjercicio.equals(pEjercicio)) {
						cadaEjercicio.setActivo(false);
						getEjercicioContableDAO().modificar(cadaEjercicio);
					}
				}
			}
			this.getEjercicioContableDAO().modificar(pEjercicio);	
		}
		else {
			int codigo=116;
			String campo="un ejercicio contable";
			throw new LogicaException(codigo, campo);
		}
	}

	public void agregarEjercicio(EjercicioContable pEjercicio) throws LogicaException {
		EjercicioContable ejercicio = getEjercicioContableDAO()
				.getEjercicioContablePorAnio(pEjercicio.getAnio());

		if(ejercicio == null) {
			if(pEjercicio.isActivo()) {
				List<EjercicioContable> lista = getEjercicioContableDAO().findListaEjerciciosActivos();
				for(EjercicioContable cadaEjercicio : lista) {
					cadaEjercicio.setActivo(false);
					getEjercicioContableDAO().modificar(cadaEjercicio);
				}
			}
			this.getEjercicioContableDAO().agregar(pEjercicio);	
		}
		else {
			int codigo=116;
			String campo="un ejercicio contable";
			throw new LogicaException(codigo, campo);
		}
	}

	public void eliminarEjercicio(EjercicioContable ejercicio) 
			throws LogicaException 
	{
		//Permitir eliminar si no tiene libro diario asociado y cascadear plan cuenta
		if(libroDiarioDao.getLibroDiarioPorPeriodoContable(ejercicio) != null) {
			int codigo=117;
			String campo= "";
			throw new LogicaException(codigo, campo);
		}
		else{
			ejercicioContableDAO.eliminar(ejercicio);
		}
	}
}
