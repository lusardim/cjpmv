package ar.gov.cjpmv.prestamos.core;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.business.dao.ProvinciaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.helpers.ProvinciaHelper;
import ar.gov.cjpmv.prestamos.core.persistence.Provincia;

public class ProvinciaDaoTest {
	
	@Ignore
	@Test
	public void agregarProvinciaTest() throws LogicaException{
		ProvinciaHelper locProvinciaHelper = new ProvinciaHelper();
		ProvinciaDAO locProvinciaDAO = new ProvinciaDAO();
		Provincia[] locProvinciasGeneradas = locProvinciaHelper.generarProvincias();
		for (Provincia cadaProvincia : locProvinciasGeneradas){
			locProvinciaDAO.agregar(cadaProvincia);
		}
		
		List<Provincia> listaProvincias = locProvinciaDAO.getListaProvincias();
		Assert.assertTrue("La lista de provincias no son iguales", listaProvincias.containsAll(Arrays.asList(locProvinciasGeneradas)));
		
		
	}

}
