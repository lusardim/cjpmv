package ar.gov.cjpmv.prestamos.core.business;

import java.util.Collection;

public abstract class ResultadoPaginado<L extends Collection<E>, E> {
	protected static final int CANTIDAD_POR_PAGINA = 20;
	
	protected int pagina=0;
	protected int inicio=0;
	private L lista;
	
	public L siguiente() {
		inicio = pagina*CANTIDAD_POR_PAGINA;
		pagina++;
		actualizarLista();
		return lista;
	}

	public L atras() {
		pagina--;
		if (pagina<0) {
			pagina = 0;
		}
		inicio = pagina*CANTIDAD_POR_PAGINA;
		actualizarLista();
		return lista;
	}
	
	public L getLista() {
		return lista;
	}
	
	protected abstract void actualizarLista();
	
}
