package ar.gov.cjpmv.prestamos.core.helpers;

import java.util.Random;

import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Domicilio;
import ar.gov.cjpmv.prestamos.core.persistence.Localidad;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;


public class DomicilioHelper {

	private Random random;
	
	private static String[] listaNombresCalles = {"las", "el", "vacio", "San martin", "los", "calandrias"};
	private static String[] listaLetrasDepartamentos = {"A","B","Z"};
	
	
	private static String[] listaDepartamentosEntreRios={"Victoria","Colón","Concordia","Diamante","Federación","Federal","Gualeguay",
													"Gualeguaychú","Islas del Ibicuy","La Paz","Nogoyá","Paraná",
													"San José de Feliciano","San Salvador","Tala","Uruguay",
													"Villaguay"}; 

	
	public DomicilioHelper() {
		this.random = new Random();
	}
	
	/**
	 * Genera al azar un listado de domicilios
	 * @param cantidad
	 * @return
	 */
	public Domicilio[] generarDomicilios(int cantidad){
		Domicilio[] locListaDomicilios = new Domicilio[cantidad];
		for (int i = 0; i<cantidad;i++){
			locListaDomicilios[i] = this.generarDomicilio();
		}
		return locListaDomicilios;
	}
	
	/**
	 * Genera al azar un listado de domicilios con departamentos
	 * @param cantidad
	 * @return
	 */
	public Domicilio[] generarDomicilios(int cantidad,Departamento pDepartamento){
		Domicilio[] locListaDomicilios = new Domicilio[cantidad];
		for (int i = 0; i<cantidad;i++){
			locListaDomicilios[i] = this.generarDomicilio();
			locListaDomicilios[i].setDepartamento(pDepartamento);
		}
		return locListaDomicilios;
	}
	

	/**
	 * Genera al azar un listado de domicilios con localidades
	 * @param cantidad
	 * @return
	 */
	public Domicilio[] generarDomicilios(int cantidad,Localidad pLocalidad){
		Domicilio[] locListaDomicilios = new Domicilio[cantidad];
		for (int i = 0; i<cantidad;i++){
			locListaDomicilios[i] = this.generarDomicilio();
			locListaDomicilios[i].setLocalidad(pLocalidad);
		}
		return locListaDomicilios;
	}
	public Domicilio generarDomicilio() {
		Domicilio locDomicilio = new Domicilio();
		locDomicilio.setCalle(this.getCalleAlAzar());
		locDomicilio.setNumero(this.random.nextInt(10000));
		if (this.random.nextBoolean()){
			locDomicilio.setNumeroDepartamento(listaLetrasDepartamentos[this.random.nextInt(listaLetrasDepartamentos.length)]);
			locDomicilio.setPiso(this.random.nextInt(10));
		}
		return locDomicilio;
	}


	private String getCalleAlAzar() {
		int silabas = this.random.nextInt(3);
		String nombre="";
		for (int i = 0 ; i<silabas; i++){
			nombre+=" "+listaNombresCalles[this.random.nextInt(listaNombresCalles.length)];
		}
		return nombre.trim();
	}


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
