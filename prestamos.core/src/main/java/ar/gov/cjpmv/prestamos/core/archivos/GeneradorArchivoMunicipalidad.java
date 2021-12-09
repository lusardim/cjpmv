package ar.gov.cjpmv.prestamos.core.archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;

public class GeneradorArchivoMunicipalidad extends GeneradorArchivos {

	private Liquidacion liquidacion;
	private DateFormat formatoFechaCabecera = new SimpleDateFormat("yyyy|MM");
	private NumberFormat formateadorNumero;
	
	public GeneradorArchivoMunicipalidad(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
		formateadorNumero = NumberFormat.getNumberInstance();
		formateadorNumero.setMaximumFractionDigits(2);
		formateadorNumero.setMinimumFractionDigits(2);
		formateadorNumero.setGroupingUsed(false);
		formateadorNumero.setMinimumIntegerDigits(1);
	}
	
	@Override
	protected void generarArchivo() throws IOException {
		BufferedWriter locWriter = null;
		try{
			File file = this.getArchivo();
			FileWriter fileWriter = new FileWriter(file);
			//Si queremos reemplazar por ascii
			//locWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "ASCII"));
			locWriter = new BufferedWriter(fileWriter);
			locWriter.write(this.getCabecera());
			for (DetalleLiquidacion cadaDetalle : liquidacion.getListaDetalleLiquidacion()){
				locWriter.write(this.getLinea(cadaDetalle));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if (locWriter!=null){
				locWriter.close();
			}
		}
	}

	private String getLinea(DetalleLiquidacion pDetalle) {
		//"cuil|legajo|apellido|nombres|montoTotal";
		PersonaFisica locPersona = (PersonaFisica)pDetalle.getPersona();
		StringBuilder valor = new StringBuilder();
		if(pDetalle.getPersona().getCui()!=null){
			valor.append(pDetalle.getPersona().getCui());
		}
		else{
			valor.append("           ");
		}
		valor.append("|");
		valor.append(locPersona.getLegajo());
		valor.append("|");
		valor.append(locPersona.getApellido().trim());
		valor.append("|");
		valor.append(locPersona.getNombre());
		valor.append("|");
		valor.append(formateadorNumero.format(pDetalle.getMonto()).replaceAll(",", "."));
		valor.append("\n");
		return valor.toString();
	}

	private String getCabecera() {
		//Liquidacion|yyyy|mm
		return "Liquidacion|"+formatoFechaCabecera.format(this.liquidacion.getFechaLiquidacion())+"\n";	
	}	
	
}
