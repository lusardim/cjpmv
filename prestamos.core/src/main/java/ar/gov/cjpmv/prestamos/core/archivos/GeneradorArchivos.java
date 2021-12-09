package ar.gov.cjpmv.prestamos.core.archivos;

import java.io.File;
import java.io.IOException;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Liquidacion;

public abstract class GeneradorArchivos {

	private File archivo;
	
	public static GeneradorArchivoMunicipalidad getGeneradorLiquidacionMuni(Liquidacion liquidacion) {
		return new GeneradorArchivoMunicipalidad(liquidacion);
	}

	public File getArchivo() {
		return archivo;
	}
	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}
	
	public void generar() throws LogicaException, IOException{
		if (this.getArchivo() == null){
			throw new LogicaException(63, "archivo");
		}
		this.generarArchivo();
	}

	protected abstract void generarArchivo() throws IOException;
}
