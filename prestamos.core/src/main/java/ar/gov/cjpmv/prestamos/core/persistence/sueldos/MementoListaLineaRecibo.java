package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.util.HashSet;
import java.util.Set;



public class MementoListaLineaRecibo {
	private Set<LineaRecibo> listaLineaRecibo;

	public MementoListaLineaRecibo(Set<LineaRecibo> lineasRecibo) {
		try{
			this.listaLineaRecibo = new HashSet<LineaRecibo>();
			for(LineaRecibo cadaLinea: lineasRecibo){
				this.listaLineaRecibo.add(cadaLinea.clone());
			}
		} 
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public Set<LineaRecibo> getSaveState(){
		return this.listaLineaRecibo;
	}
	
}
