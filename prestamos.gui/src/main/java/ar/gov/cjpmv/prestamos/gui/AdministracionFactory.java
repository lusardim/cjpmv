package ar.gov.cjpmv.prestamos.gui;

import ar.gov.cjpmv.prestamos.core.business.AdministradorAsientoContable;
import ar.gov.cjpmv.prestamos.core.business.AdministradorEjercicioContable;
import ar.gov.cjpmv.prestamos.core.business.AdministradorPlanCuenta;
import ar.gov.cjpmv.prestamos.core.business.sueldos.AdministracionConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.business.sueldos.AdministracionLiquidacionHaberes;
import ar.gov.cjpmv.prestamos.core.business.sueldos.AdministracionPlantilla;

public class AdministracionFactory {
	
	private static AdministracionFactory instance;
	private AdministradorPlanCuenta administradorPlanCuenta;
	private AdministradorEjercicioContable administradorEjercicioContable;
	private AdministradorAsientoContable administradorAsientoContable;
	private AdministracionConceptoHaberes administracionConceptoHaberes;
	private AdministracionPlantilla administracionPlantilla;
	private AdministracionLiquidacionHaberes administracionLiquidacionHaberes;
	

	
	public static AdministracionFactory getInstance() {
		if (instance == null) {
			instance = new AdministracionFactory();
		}
		return instance;
	}
	
	public AdministradorPlanCuenta getAdministradorPlanCuenta() {
		if (administradorPlanCuenta == null) {
			administradorPlanCuenta = new AdministradorPlanCuenta();
		}
		return administradorPlanCuenta;
	}
	
	public AdministradorEjercicioContable getAdmistradorEjercicioContable() {
		if(administradorEjercicioContable == null){
			administradorEjercicioContable = new AdministradorEjercicioContable();
		}
		return administradorEjercicioContable;
	}
	
	public AdministradorAsientoContable getAdministradorAsientoContable() {
		if(administradorAsientoContable == null){
			administradorAsientoContable= new AdministradorAsientoContable();
		}
		return administradorAsientoContable;
	}
	
	public AdministracionConceptoHaberes getAdministracionConceptoHaberes() {
		if (administracionConceptoHaberes  == null) {
			administracionConceptoHaberes = new AdministracionConceptoHaberes();
		}
		return administracionConceptoHaberes;
	}
	
	public AdministracionPlantilla getAdministracionPlantilla() {
		if (administracionPlantilla == null) {
			administracionPlantilla = new AdministracionPlantilla();
		}
		return administracionPlantilla;
	}

	public AdministracionLiquidacionHaberes getAdministracionLiquidacionHaberes() {
		if (administracionLiquidacionHaberes == null) {
			administracionLiquidacionHaberes = new AdministracionLiquidacionHaberes();
		}
		return administracionLiquidacionHaberes;
	}

	
}
