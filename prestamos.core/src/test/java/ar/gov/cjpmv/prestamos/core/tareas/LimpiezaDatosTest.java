package ar.gov.cjpmv.prestamos.core.tareas;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

public class LimpiezaDatosTest {
	
	@Test
	@Ignore
	//TODO hacer un test coherente
	public void limpiarDatosTest() throws SecurityException, IOException{
		LimpiezaSaldos locLimpiezaSaldos = new LimpiezaSaldos();
		locLimpiezaSaldos.run();
	}
	
}
