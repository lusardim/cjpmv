package ar.gov.cjpmv.prestamos.core.helpers;

import ar.gov.cjpmv.prestamos.core.persistence.Provincia;

/**
 * Clase utilitaria que genera todas las provincias cuyos nombres se encuentran en el atributo estatico 
 * listaProvincias
 * @author pulpol
 *
 */
public class ProvinciaHelper {

	private static String[] listaProvincias = {"Entre Ríos","Buenos Aires","Córdoba","Santa Fe","Mendoza","Tucumán",
										"Salta","Misiones","Chaco","Corrientes","Santiago del Estero","Jujuy",
										"San Juan","Rio negro","Formosa","Neuquén","Chubut","San Luis","Catamarca",
										"La Rioja","La Pampa","Santa Cruz","Tierra del fuego"};
	
	/**
	 * Genera todas las provincias argentinas
	 * @return
	 */
	public Provincia[] generarProvincias(){
		Provincia[] locListaProvincias = new Provincia[listaProvincias.length];
		for (int i = 0; i<listaProvincias.length; i++){
			String cadaProvincia = listaProvincias[i];
			Provincia locProvincia = new Provincia();
			locProvincia.setNombre(cadaProvincia);
			locListaProvincias[i] = locProvincia;
		}
		return locListaProvincias;
	}
}
