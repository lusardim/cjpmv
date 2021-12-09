package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.MontoPermanenciaPorCategoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PermanenciaCategoria;

public class MontoPermanenciaPorCategoriaBuilder {
	private List<Categoria> categorias;
	private List<PermanenciaCategoria> permanencias;
	
	public MontoPermanenciaPorCategoriaBuilder(
			List<Categoria> categorias, 
			List<PermanenciaCategoria> permanencias) 
	{
		this.categorias = categorias;
		this.permanencias = permanencias;
	}
	
	public List<MontoPermanenciaPorCategoria> getMontos() {
		List<MontoPermanenciaPorCategoria> montos = new ArrayList<MontoPermanenciaPorCategoria>();
		for (int i = 0 ; i < categorias.size() ; i++) {
			Categoria categoria = categorias.get(i);
			BigDecimal diferenciaCategoriaSuperior;
			if ( i < categorias.size() - 1) {
				diferenciaCategoriaSuperior = categorias.get(i+1).getTotal().subtract(categoria.getTotal());
			}
			else {
				diferenciaCategoriaSuperior = new BigDecimal(0);
			}
			
			for (PermanenciaCategoria permanencia : permanencias) {
				BigDecimal total = permanencia
						.getPorcentaje()
						.multiply(diferenciaCategoriaSuperior)
						.setScale(2, RoundingMode.HALF_UP);
				
				MontoPermanenciaPorCategoria monto = new MontoPermanenciaPorCategoria();
				monto.setCategoria(categoria);
				monto.setPermanencia(permanencia);
				monto.setMonto(total);
				montos.add(monto);
			}
		}
		return montos;
	}
}
