package ar.gov.cjpmv.prestamos.core.helpers;

import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;

public class DepartamentoHelper {

	private static String[] listaDepartamentosEntreRios={"Victoria","Colón","Concordia","Diamante","Federación","Federal","Gualeguay",
		"Gualeguaychú","Islas del Ibicuy","La Paz","Nogoyá","Paraná",
		"San José de Feliciano","San Salvador","Tala","Uruguay",
		"Villaguay"}; 

	/**
	 * Genera todos los departamentos de entre ríos, pero no genera la provincia
	 * @return
	 */
	public Departamento[] generarDepartamentosEntreRios(Provincia pEntreRios){
		Departamento[] locListaDepartamentos = new Departamento[listaDepartamentosEntreRios.length];
		for (int i=0;i<listaDepartamentosEntreRios.length;i++){
			String locNombreDepartamento = listaDepartamentosEntreRios[i];
			Departamento locDepartamento = new Departamento();
			locDepartamento.setNombre(locNombreDepartamento);
			locDepartamento.setProvincia(pEntreRios);
			locListaDepartamentos[i] = locDepartamento;
		}
		return locListaDepartamentos;
	}

}
