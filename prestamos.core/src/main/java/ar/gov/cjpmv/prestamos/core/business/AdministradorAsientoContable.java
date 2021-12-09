package ar.gov.cjpmv.prestamos.core.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.dao.AsientoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.AsientoModeloDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.EjercicioContableDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.LibroDiarioDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Asiento;
import ar.gov.cjpmv.prestamos.core.persistence.contable.AsientoModelo;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.LibroDiario;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Movimiento;

public class AdministradorAsientoContable {

	private AsientoDAO asientoDAO;
	private LibroDiarioDAO libroDiarioDAO;
	private AsientoModeloDAO asientoModeloDAO;
	private EjercicioContableDAO ejercicioContableDAO;
	
	public AdministradorAsientoContable() {
		asientoDAO = DAOFactory.getDefecto().getAsientoDAO();
		libroDiarioDAO = DAOFactory.getDefecto().getLibroDiarioDAO();
		asientoModeloDAO = DAOFactory.getDefecto().getAsientoModeloDAO();
		ejercicioContableDAO = new EjercicioContableDAO();
	}
	
	public List<Asiento> findListaAsientoContable(Date fechaDesde,
			Date fechaHasta, String descripcion, Integer numeroAsiento) {
		return asientoDAO.getListaAsientoContable(fechaDesde, fechaHasta, descripcion, numeroAsiento);
	}

	public void eliminarAsiento(Asiento locAsiento) throws LogicaException {
		Long idLibroDiario= locAsiento.getLibroDiario().getId();
		LibroDiario locLibro= libroDiarioDAO.getObjetoPorId(idLibroDiario);
		Long idAsiento= locAsiento.getId();
		Asiento asiento= asientoDAO.getObjetoPorId(idAsiento);
		asientoDAO.eliminar(asiento);
		if(locLibro.getListaAsiento().size()==1) {
			libroDiarioDAO.eliminar(locLibro);
		}
	}

	public List<AsientoModelo> getListaAsientosModelo() {
		return asientoModeloDAO.getListaAsientosModelo();
	}

	public Integer getNumeroSiguienteAsiento() {
		int valor = asientoDAO.getNumeroAsientoSiguiente();
		return ++valor;
	}

	public void agregarAsiento(Asiento asientoContable) throws LogicaException {
		if(asientoContable.getNumero()==null){
			int codigo= 126;
			throw new LogicaException(codigo);
		}
		else if(asientoDAO.isExisteNumeroAsiento(asientoContable.getNumero(), asientoContable.getId())){
			int codigo= 127;
			throw new LogicaException(codigo);
		}
		else if(asientoContable.getFecha()==null){
			int codigo= 132;
			String campo=" fecha";
			throw new LogicaException(codigo, campo);
		}
		else if (asientoContable.getDescripcion()==null){
			int codigo= 132;
			String campo=" descripción";
			throw new LogicaException(codigo, campo);
		}
		else if(asientoContable.getListaMovimiento().isEmpty() || asientoContable.getListaMovimiento().size()==1){
			int codigo= 128;
			throw new LogicaException(codigo);
		}
		else {
			BigDecimal totalDebe= new BigDecimal("0.00");
			BigDecimal totalHaber= new BigDecimal("0.00");
			for(Movimiento cadaMov: asientoContable.getListaMovimiento()){
				if(cadaMov.getMontoDebe()!=null){
					totalDebe= totalDebe.add(cadaMov.getMontoDebe());
				}
				if(cadaMov.getMontoHaber()!=null){
					totalHaber= totalHaber.add(cadaMov.getMontoHaber());
				}
			}
			if(!totalDebe.equals(totalHaber)){
				int codigo= 129;
				throw new LogicaException(codigo);
			}
		}
		asientoContable.setLibroDiario(getLibroDiarioActivo());
		asientoDAO.agregar(asientoContable);
	}

	public LibroDiario getLibroDiarioActivo() throws LogicaException {
		List<EjercicioContable> ejerciciosActivos = ejercicioContableDAO.findListaEjerciciosActivos();
		if (ejerciciosActivos.size() > 1) {
			throw new LogicaException(124);
		}
		
		if (!ejerciciosActivos.isEmpty()) {
			EjercicioContable ejercicioContable =  ejerciciosActivos.get(0);
			LibroDiario libroDiario = libroDiarioDAO
					.getLibroDiarioPorPeriodoContable(ejercicioContable);
			if (libroDiario == null) {
				libroDiario = new LibroDiario();
				libroDiario.setPeriodoContable(ejercicioContable);
				libroDiarioDAO.agregar(libroDiario);
			}
			return libroDiario;
		}
		return null;
	}

}
