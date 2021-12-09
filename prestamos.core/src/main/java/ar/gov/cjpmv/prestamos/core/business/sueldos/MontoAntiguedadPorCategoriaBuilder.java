package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Antiguedad;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.MontoAntiguedadPorCategoria;

public class MontoAntiguedadPorCategoriaBuilder {
	
	private List<Categoria> categorias;
	private List<Antiguedad> antiguedades;
	
	public MontoAntiguedadPorCategoriaBuilder(
			List<Categoria> categorias, 
			List<Antiguedad> antiguedades) 
	{
		this.categorias = categorias;
		this.antiguedades = antiguedades;
	}
	
	public List<MontoAntiguedadPorCategoria> getMontos()
	{
		List<MontoAntiguedadPorCategoria> montos = new ArrayList<MontoAntiguedadPorCategoria>();
		
		for (int i = 0 ; i < categorias.size() ; i++) {
			Categoria categoria = categorias.get(i);
			for (Antiguedad antiguedad : antiguedades) {
				BigDecimal total = antiguedad
						.getPorcentaje()
						.multiply(categoria.getTotal())
						.setScale(2, RoundingMode.HALF_UP);
				MontoAntiguedadPorCategoria monto = new MontoAntiguedadPorCategoria();
				monto.setAntiguedad(antiguedad);
				monto.setCategoria(categoria);
				monto.setMonto(total);
				montos.add(monto);
			}
		}
		return montos;
	}
	
}
