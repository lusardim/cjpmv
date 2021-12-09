package ar.gov.cjpmv.prestamos.gui.creditos.datos.garantes.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.GarantiaPersonal;

public class GarantiasPersonalesModel {
	private Credito credito; 
	private Set<GarantiaPersonal> listaGarantias;
	
	public GarantiasPersonalesModel(Credito pCredito) {
		if (pCredito == null ){
			throw new NullPointerException("El crédito no puede estar vacío");
		}
		this.credito = pCredito;
		this.initGarantias();
	}
	
	private void initGarantias() {
		this.listaGarantias = new HashSet<GarantiaPersonal>();
		for (Garantia cadaGarantia : this.credito.getListaGarantias()){
			if (cadaGarantia instanceof GarantiaPersonal){
				listaGarantias.add((GarantiaPersonal)cadaGarantia);
			}
		}
	}
	
	/**
	 * 
	 * @return lista de garantías registradas
	 */
	public Set<GarantiaPersonal> getListaGarantias(){
		return this.listaGarantias;
	}
	
	/**
	 * Obtiene todas las personas que son garantes del crédito
	 * @return
	 */
	public List<PersonaFisica> getListaPersonas(){
		List<PersonaFisica> listaPersonas = new ArrayList<PersonaFisica>(this.listaGarantias.size());
		for (GarantiaPersonal cadaGarantia : this.listaGarantias){
			listaPersonas.add(cadaGarantia.getGarante());
		}
		return listaPersonas;
	}
	
	/**
	 * Agrega una garantía al crédito
	 * @param pPersona
	 */
	public GarantiaPersonal addGarantia(PersonaFisica pPersona){
		//Preparamos la garantía
		GarantiaPersonal locGarantiaPersonal = new GarantiaPersonal();
		locGarantiaPersonal.setGarante(pPersona);
		locGarantiaPersonal.setCredito(credito);
		
		if (!this.listaGarantias.contains(locGarantiaPersonal)){
			this.listaGarantias.add(locGarantiaPersonal);
			BigDecimal tamanio = new BigDecimal(listaGarantias.size());
			BigDecimal porcentaje = new BigDecimal(100).divide(tamanio,RoundingMode.HALF_UP);
			
			for (Garantia cadaGarantia : this.listaGarantias){
				cadaGarantia.setPorcentaje(porcentaje);
				if (!this.credito.getListaGarantias().contains(cadaGarantia)){
					this.credito.getListaGarantias().add(cadaGarantia);
				}
			}
			return locGarantiaPersonal;
		}
		else{
			return null;
		}
	}

	public void removeGarantia(GarantiaPersonal locGarantiaPersonal) {
		this.getListaGarantias().remove(locGarantiaPersonal);
		this.credito.getListaGarantias().remove(locGarantiaPersonal);
	}
	
	
}
