package ar.gov.cjpmv.prestamos.core.utiles;

import java.util.regex.Pattern;

public class NumeroALetras {
	private final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
	private final String[] UNIDADES1 = {"", "uno ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
	private final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
	        "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ", "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
	private final String[] DECENASVEINTE= {"", "veintiun ", "veintidos ", "veintitres ", "veinticuatro ", "veinticinco ", "veintiseis ", "veintisiete ", "veintiocho ", "veintinueve "};
	private final String[] DECENASVEINTE1= {"", "veintiuno ", "veintidos ", "veintitres ", "veinticuatro ", "veinticinco ", "veintiseis ", "veintisiete ", "veintiocho ", "veintinueve "};
	private final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
	        "setecientos ", "ochocientos ", "novecientos "};
	private final String[] UBICACION = {"", "millon", "miles", "centenas", "decenas", "unidades"};



	public String convertir(String numero, boolean mayusculas) {
	        String literal = "";
	        String parte_decimal;    
	        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
	        numero = numero.replace(".", ",");
	        //si el numero no tiene parte decimal, se le agrega ,00
	        if(numero.indexOf(",")==-1){
	            numero = numero + ",00";
	        }
	        //se valida formato de entrada -> 0,00 y 999 999 999,00
	        if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
	            //se divide el numero 0000000,00 -> entero y decimal
	            String Num[] = numero.split(",");            
	            //de da formato al numero decimal
	            parte_decimal = Num[1] + "/100.";
	            //se convierte el numero a literal
	            if (Integer.parseInt(Num[0]) == 0) {//si el valor es cero
	                literal = "cero ";
	            } else if (Integer.parseInt(Num[0]) > 999999) {//si es millon
	                literal = getMillones(Num[0]);
	            } else if (Integer.parseInt(Num[0]) > 999) {//si es miles
	                literal = getMiles(Num[0],UBICACION[2]);
	            } else if (Integer.parseInt(Num[0]) > 99) {//si es centena
	                literal = getCentenas(Num[0], UBICACION[3]);
	            } else if (Integer.parseInt(Num[0]) > 9) {//si es decena
	                literal = getDecenas(Num[0], UBICACION[4]);
	            } else {//sino unidades -> 9
	                literal = getUnidades(Num[0], UBICACION[5]);
	            }
	            //devuelve el resultado en mayusculas o minusculas
	            if (mayusculas) {
	                return ( literal + "con "+ parte_decimal).toUpperCase();
	            } else {
	                return ( literal + "con "+ parte_decimal);
	            }
	        } else {//error, no se puede convertir
	            return literal = null;
	        }
	  }

	    /* funciones para convertir los numeros a literales */

	 private String getUnidades(String numero, String pUbicacion) {// 1 - 9
		 
		 if(pUbicacion.equals("")){
			 pUbicacion="unidades";
		 }
		 
	        //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
	        String num = numero.substring(numero.length() - 1);
	      
	        if(!pUbicacion.equals("millon")&&!pUbicacion.equals("miles")){
	        	return UNIDADES1[Integer.parseInt(num)];
	        }
	        return UNIDADES[Integer.parseInt(num)];
	 }
	 


	 private String getDecenas(String num, String pUbicacion) {// 99  
		 
		 	if(pUbicacion.equals("")){
		 		pUbicacion= "decenas";
		 	}
		 
	        int n = Integer.parseInt(num);
	 
	        if (n < 10) {//para casos como -> 01 - 09
	            return getUnidades(num, pUbicacion);
	        } else if (n > 19) {//para 20...99
	            String u = getUnidades(num, pUbicacion);
	            if (u.equals("")) { //para 20,30,40,50,60,70,80,90
	                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
	            } else if(n>20 && n<30){
	            	if(pUbicacion.equals("millon")||pUbicacion.equals("miles")){
	            		return DECENASVEINTE[n-20];
	            	}
	            	else{
	            		return DECENASVEINTE1[n-20];
	            	}
	            }else{
	                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
	            }
	        } else {//numeros entre 11 y 19
	            return DECENAS[n - 10];
	        }
	  }

	    private String getCentenas(String num, String pUbicacion) {// 999 o 099
	    	if(pUbicacion.equals("")){
	    		pUbicacion= "centenas";
	    	}
	        if( Integer.parseInt(num)>99 ){//es centena
	            if (Integer.parseInt(num) == 100) {//caso especial
	                return " cien ";
	            } else {
	                 return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1), pUbicacion);
	            } 
	        }else{//por Ej. 099 
	            //se quita el 0 antes de convertir a decenas
	            return getDecenas(Integer.parseInt(num)+"", pUbicacion);            
	        }        
	    }

	    private String getMiles(String numero, String pUbicacion) {// 999 999
	    	if(pUbicacion.equals("")){
	    		pUbicacion="miles";
	    	}
	        //obtiene las centenas
	        String c = numero.substring(numero.length() - 3);
	        //obtiene los miles
	        String m = numero.substring(0, numero.length() - 3);
	        String n="";
	        //se comprueba que miles tenga valor entero
	        if (Integer.parseInt(m) > 0) {
	        	
	            n = getCentenas(m, pUbicacion);    
	   
	            return n + "mil " + getCentenas(c, UBICACION[3]);
	        } else {
	            return "" + getCentenas(c, UBICACION[3]);
	        }

	    }

	    private String getMillones(String numero) { //000 000 000        
	        //se obtiene los miles
	        String miles = numero.substring(numero.length() - 6);
	        //se obtiene los millones
	        String millon = numero.substring(0, numero.length() - 6);
	        String n = "";
	        if(millon.length()>1){
	            n = getCentenas(millon, UBICACION[1]) + "millones ";
	        }else{
	            n = getUnidades(millon, UBICACION[1]) + "millon ";
	        }
	        return n + getMiles(miles,UBICACION[2]);        
	    }
	
}
