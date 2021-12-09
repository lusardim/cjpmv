package ar.gov.cjpmv.prestamos.gui.utiles;

import ar.gov.cjpmv.prestamos.core.persistence.Domicilio;

public class UtilesValidacion {

	public static boolean isValido(Domicilio pDomicilio) {
		boolean domicilioValido = true;
		if(pDomicilio.getLocalidad()==null){
		System.out.println("lcoalida");
			if(pDomicilio.getDepartamento()==null){
			System.out.println("Depto");
				domicilioValido = false;
			}
		}
		if (pDomicilio.getCalle() == null){
		System.out.println("calle");
			domicilioValido = false;
		}
		return domicilioValido;		
	}

}