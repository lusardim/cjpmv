package ar.gov.cjpmv.prestamos.gui.utiles;

import java.awt.Component;
import java.awt.Container;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.text.MaskFormatter;

public class Utiles {
	
	private static Utiles instance;
	
	public static final Utiles getInstance() {
		if (instance == null) {
			instance = new Utiles();
		}
		return instance;
	}
	
	public static String nuloSiVacio(String pCadena){
		pCadena = pCadena.trim();
		return (pCadena.isEmpty())?null:pCadena.trim();
	}

	public static String cadenaVaciaSiNulo(Object pNumero) {
		if (pNumero==null){
			return "";
		}
		return pNumero.toString();
	}
	
	public static String nuloSiVacioConCammelCase(String pCadena){
		String cadena = nuloSiVacio(pCadena);
		if (cadena!=null){
			String[] listaCadenas = cadena.split(" ");
			String resultado = "";
			for (String cadaCadena : listaCadenas){
				resultado+=cadaCadena.substring(0, 1).toUpperCase()+cadaCadena.substring(1).toLowerCase()+" ";
			}
			return resultado.trim();
		}
		return cadena;
	}

	public static String vacioSiNulo(String cadena) {
		return (cadena==null)?"":cadena.trim();
	}
	
	public static String vacioSiNulo(BigDecimal numero) {
		return (numero==null)?"":numero.toString();
	}
	
	public static MaskFormatter getFormateadorCuit(){
		MaskFormatter formateadorCuil = null;
        try {
			formateadorCuil = new MaskFormatter("##-*#######-#");
			formateadorCuil.setValueContainsLiteralCharacters(false);
			formateadorCuil.setPlaceholderCharacter('_');
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formateadorCuil;
	}

	public static MaskFormatter getFormateadorDocumento() throws ParseException {
		MaskFormatter documentoFormat = new MaskFormatter("*#.###.###");
		documentoFormat.setValueContainsLiteralCharacters(false);
		
		return documentoFormat;
	}

	public static String getCuiFormateado(Long cui) {
		if (cui!=null){
			String locString = cui.toString();
			locString = Utiles.rellenar(locString,'0',11);
			return locString.substring(0, 2)+"-"+locString.substring(2, 10)+"-"+locString.substring(10);
		}
		return null;

	}
	
	/**
	 * Rellena una cadena con el caracter de relleno hasta cierta cantidad
	 * @param cadena
	 * @param relleno
	 * @param cantidad
	 * @return
	 */
	public static String rellenar(String cadena, char relleno, int cantidad) {
		StringBuilder builder = new StringBuilder();
		int tamanio = cadena.length();
		for (int i=0;i<cantidad-tamanio ;i++) {
			builder.append(relleno);
		}
		builder.append(cadena);
		return builder.toString();
	}

	/**
	 * Convierte un valor de n cantidad de cifras en String a un valor
	 * de 2 cifras
	 * @param pParametro
	 * @return
	 */
	public static Float redondearADosCifras(Float valor) {
		valor *=100;
		valor = (float)Math.round(valor);
		return valor/=100;
	}

	public static boolean isCadenaVacia(String nombre) {
		if (nombre==null){
			return true;
		}
		else if (nombre.trim().isEmpty()){
				return true;
		}
		return false;
	}

	public static Number ceroSiNulo(Number pNumber) {
		if (pNumber == null){
			return 0;
		}
		else{
			return pNumber;
		}
	}

	/**
	 * Compara 2 fechas hasta el día y devuelve
	 * <ul> 
	 *  <li>0 son iguales
	 *  <li>1 si fecha 1 es mayor que fecha 2
	 *  <li>-1 si fecha 1 es menor que fecha 2
	 *  </ul> 
	 * @param fecha1
	 * @param fecha2
	 * @return
	 */
	public int compararFechas(Date fecha1, Date fecha2) 
	{
		Calendar calendario1 = Calendar.getInstance();
		calendario1.setTime(fecha1);
		
		Calendar calendario2 = Calendar.getInstance();
		calendario2.setTime(fecha2);
		
		limpiarCalendario(calendario1);
		limpiarCalendario(calendario2);
		return calendario1.compareTo(calendario2);
	}
	
	private void limpiarCalendario(Calendar calendario) {
		calendario.set(Calendar.HOUR_OF_DAY, 0);
	    calendario.set(Calendar.MINUTE, 0);
	    calendario.set(Calendar.SECOND, 0);
	    calendario.set(Calendar.MILLISECOND, 0);
	}
	
	public static boolean comparaFecha(Calendar calendario1, Calendar calendario2){
		if (calendario1.get(Calendar.YEAR) == calendario2.get(Calendar.YEAR)
			&& calendario1.get(Calendar.MONTH) == calendario2.get(Calendar.MONTH) 
			&& calendario1.get(Calendar.DATE) == calendario2.get(Calendar.DATE)){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Habilita o deshabilita cada hijo de un componente
	 * @param pPanel
	 * @param enable
	 */
	public static void setEnableRecursivo(Container pPanel, boolean enable){
		for (Component cadaComponente : pPanel.getComponents()){
			cadaComponente.setEnabled(enable);
			if (cadaComponente instanceof Container){
				setEnableRecursivo((Container)cadaComponente,enable);
			}
		}
	}

	public static JDialog getJDialogPadre(Component vista) {
		if (vista.getParent() != null){
			if (vista.getParent() instanceof JDialog){
				return (JDialog)vista.getParent();
			}
			else{
				return getJDialogPadre(vista.getParent());
			}
		}
		return null;
	}

	public static boolean isLong(String string) {
		try{
			Long.parseLong(string);
			return true;
		}
		catch(NumberFormatException e){
			return false;	
		}
	}
	
	public static boolean fechaDesdeMayorQueHasta(Date fechaDesde, Date fechaHasta){
		boolean valor= fechaDesde.after(fechaHasta);
		return valor;
	}

	public static boolean isFloat(String string) {
		try{
			Float.parseFloat(string);
			return true;
		}
		catch(NumberFormatException e){
			return false;	
		}
	}
	
	public static boolean isBigDecimal(String monto){
		try{
			new BigDecimal(monto);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	

}
