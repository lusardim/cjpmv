package ar.gov.cjpmv.prestamos.gui.personas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.facades.BusquedaPersonaFacade;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.gui.configuracion.busquedas.BusquedaModel;

public class BusquedaPersonasModel implements BusquedaModel{
	
	private BusquedaPersonaFacade logica;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7341435878305039380L;
	private TblPersonaModel tblPersonaModel;
	private ComboBoxModel cboTipoDocumentoModel;
	private ComboBoxModel cboEstadoPersonaModel;
	
	private Long legajo;
	private Long Cuip;
	private TipoDocumento tipoDocumento;
	private String apellido;
	private String estado;
	private Integer numeroDocumento;
	
	public BusquedaPersonasModel() {
		this.logica = new BusquedaPersonaFacade();
		this.tblPersonaModel = new TblPersonaModel();
	
		Set<String> locLista =  this.logica.getListaEstados();
		locLista.add(null);
		
		this.cboEstadoPersonaModel = new DefaultComboBoxModel(locLista.toArray());
		
		List<TipoDocumento> locListaTiposDocumentos = new ArrayList<TipoDocumento>();
		locListaTiposDocumentos.add(null);
		locListaTiposDocumentos.addAll(this.logica.getListaTiposDocumentos());
		this.cboTipoDocumentoModel = new DefaultComboBoxModel(locListaTiposDocumentos.toArray());
	}

	public Long getLegajo() {
		return legajo;
	}

	public void setLegajo(Long legajo) {
		this.legajo = legajo;
	}

	public Long getCuip() {
		return Cuip;
	}

	public void setCuip(Long cuip) {
		Cuip = cuip;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(Integer numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	@Override
	public void refrescarBusqueda(){
		List<Persona> listaPersonas = this.logica.findListaPersonas(
					this.legajo, this.Cuip, this.tipoDocumento, this.numeroDocumento,this.apellido,this.estado);
		this.setListaPersonas(listaPersonas);
	}

	protected void setListaPersonas(List<Persona> pLista){
		this.tblPersonaModel.setListaPersonas(pLista);
	}

	@Override
	public TblPersonaModel getModeloTabla() {
		return tblPersonaModel;
	}
	public void setTblPersonaModel(TblPersonaModel tblPersonaModel) {
		this.tblPersonaModel = tblPersonaModel;
		
	}

	public ComboBoxModel getCboTipoDocumentoModel() {
		return cboTipoDocumentoModel;
	}

	public ComboBoxModel getCboEstadoPersonaModel() {
		return cboEstadoPersonaModel;
	}

	public void eliminar(int filaSeleccionada) throws LogicaException{
		Persona locPersona = this.getModeloTabla().getPersona(filaSeleccionada);
		this.logica.eliminar(locPersona);
		this.tblPersonaModel.getListaPersonas().remove(filaSeleccionada);
		this.tblPersonaModel.fireTableRowsDeleted(filaSeleccionada, filaSeleccionada);
	}

	public Persona getPersonaSeleccionada(int pFila){
		return this.getModeloTabla().getPersona(pFila);
	}
	
	protected BusquedaPersonaFacade getLogica() {
		return logica;
	}


}
