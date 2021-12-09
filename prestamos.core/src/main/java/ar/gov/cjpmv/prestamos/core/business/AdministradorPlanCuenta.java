package ar.gov.cjpmv.prestamos.core.business;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.dao.AsientoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.EjercicioContableDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PlanCuentaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.contable.Cuenta;
import ar.gov.cjpmv.prestamos.core.persistence.contable.EjercicioContable;
import ar.gov.cjpmv.prestamos.core.persistence.contable.PlanCuenta;

public class AdministradorPlanCuenta {
	
	private PlanCuentaDAO planCuentaDAO;
	private EjercicioContableDAO ejercicioContableDAO;
	private AsientoDAO asientoDAO;
	
	public AdministradorPlanCuenta() {
		planCuentaDAO = new PlanCuentaDAO();
		asientoDAO = new AsientoDAO();
	}
	
	public void guardarPlanCuenta(PlanCuenta planCuenta) throws LogicaException {
		if (planCuenta == null) {
			throw new IllegalArgumentException("El plan de cuenta no puede ser nulo");
		}
		if (planCuenta.getPeriodo() == null) {
			throw new IllegalArgumentException("El plan de cuenta debe tener un ejercicio " +
					" contable asociado");
		}
		List<Cuenta> cuentas = planCuenta.getCuentasHijasComoLista();
		
		if (cuentas == null || cuentas.isEmpty()) {
			throw new IllegalArgumentException("El plan de cuentas debe contener al menos una cuenta");
		}
		
		verificarCuentasEliminadas(planCuenta, cuentas);
		validarCuentas(cuentas);
				
		if (planCuenta.getId() == null) {
			planCuentaDAO.agregar(planCuenta);
		}
		else {
			planCuentaDAO.modificar(planCuenta);
		}
	}
	
	private void verificarCuentasEliminadas(PlanCuenta planCuenta, List<Cuenta> listaNueva) throws LogicaException {
		//si el id del plan de cuenta es nulo, entonces es nuevo
		if (planCuenta.getId() != null) {
			List<Cuenta> listaOriginal = planCuentaDAO.getCuentasPlanCuentaComoLista(planCuenta);
			for (Cuenta cadaCuenta : listaOriginal) {
				if (!listaNueva.contains(cadaCuenta)) {
					if (asientoDAO.tieneAsientos(cadaCuenta)) {
						throw new LogicaException(125,cadaCuenta.toString());
					}
				}
			}
		}
	}

	private void validarCuentas(List<Cuenta> cuentas) {
		for (Cuenta cuenta : cuentas) {
			if (cuenta.getNombre() == null) {
				throw new IllegalArgumentException("Una cuenta no tiene nombre");
			}
			if (cuenta.getCodigo() == null) {
				throw new IllegalArgumentException("La cuenta " + 
							cuenta.getNombre() + " no tiene código");
			}
			if (cuenta.getTipoCuenta() == null) {
				throw new IllegalArgumentException("El tipo de cuenta no puede ser nula " +
								cuenta.getCodigoCompleto());
			}
			if (cuenta.getTipoSaldo() == null) {
				throw new IllegalArgumentException("El tipo de saldo para la cuenta " +
						cuenta.getCodigoCompleto()+" no puede ser nulo");
			}
			if (cuenta.getCuentasHijas() != null && !cuenta.getCuentasHijas().isEmpty()) {
				validarCuentas(cuenta.getCuentasHijas());
			}
		}
	}

	/**
	 * Obtiene el plan de cuentas para el año dado, en caso que no exista el plan de cuentas
	 * pero haya un ejercicio contable creado para ese año, entonces crea un plan de cuentas
	 * vacío
	 * @param anio
	 * @return
	 * @throws LogicaException
	 */
	public PlanCuenta getPlanCuentaPorAnio(int anio) throws LogicaException {
		validarPeriodo(anio);
		PlanCuenta planCuenta = planCuentaDAO.getPlanCuenta(anio);
		if (planCuenta == null) {
			EjercicioContable ejercicioContable = getEjercicioContable(anio);
			if (ejercicioContable != null) {
				planCuenta = new PlanCuenta();
				planCuenta.setPeriodo(ejercicioContable);
			}
		}
		return planCuenta;
	}
	
	/**
	 * Obtiene el ejercicio contable para el año dado, nulo en caso que no exista
	 * @param anio
	 * @return
	 * @throws LogicaException
	 */
	public EjercicioContable getEjercicioContable(int anio) throws LogicaException {
		validarPeriodo(anio);
		return getEjercicioContableDAO().getEjercicioContablePorAnio(anio);
	}

	private void validarPeriodo(int anio) throws LogicaException {
		if (anio > 2100 || anio < 1800) {
			throw new LogicaException(118, null);
		}
	}

	public void setPlanCuentaDAO(PlanCuentaDAO planCuentaDAO) {
		this.planCuentaDAO = planCuentaDAO;
	}

	private EjercicioContableDAO getEjercicioContableDAO() {
		if (ejercicioContableDAO == null) {
			ejercicioContableDAO = DAOFactory.getDefecto().getEjercicioContableDAO();
		}
		return ejercicioContableDAO;
	}

	public void setEjercicioContableDAO(EjercicioContableDAO ejercicioContableDAO) {
		this.ejercicioContableDAO = ejercicioContableDAO;
	}

	public List<PlanCuenta> getListaPlanesCuenta() {
		return planCuentaDAO.getListaPlanesCuenta();
	}
	
	public PlanCuenta getPlanCuentaActivo() throws LogicaException {
		try {
			return planCuentaDAO.getPlanCuentaActivo();
		}
		catch(NoResultException e) {
			return null;
		}
	}

	public boolean tieneAsientos(Cuenta cuenta) {
		//si el id es nulo
		if (cuenta.getId() == null) {
			return false;
		}
		return asientoDAO.tieneAsientos(cuenta);
	}
}
