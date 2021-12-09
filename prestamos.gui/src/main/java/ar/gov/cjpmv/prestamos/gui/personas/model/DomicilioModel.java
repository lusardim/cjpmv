package ar.gov.cjpmv.prestamos.gui.personas.model;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.MutableComboBoxModel;

import ar.gov.cjpmv.prestamos.core.business.dao.ConfiguracionSistemaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.ProvinciaDAO;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.Domicilio;

public class DomicilioModel {
	private Domicilio domicilio;
	private ComboBoxModel modeloProvincias;
	private MutableComboBoxModel modeloDepartamentos;
	private MutableComboBoxModel modeloLocalidades;
	
	private ProvinciaDAO provinciaDAO;
	private ConfiguracionSistemaDAO configuracionSistemaDAO;
	
	public DomicilioModel(){
		provinciaDAO = new ProvinciaDAO();
		this.configuracionSistemaDAO = new ConfiguracionSistemaDAO();
		this.inicializarModelos();
	}	

	public void inicializarModelos() {
		Departamento locDepartamento = this.configuracionSistemaDAO.getDepartamentoSistema();
		
		this.modeloProvincias = new DefaultComboBoxModel(this.provinciaDAO.getListaProvincias().toArray());
		this.modeloDepartamentos = new DefaultComboBoxModel(locDepartamento.getProvincia().getListaDepartamento().toArray());
		this.modeloLocalidades = new DefaultComboBoxModel(locDepartamento.getListaLocalidad().toArray());
		this.modeloProvincias.setSelectedItem(locDepartamento.getProvincia());
		this.modeloDepartamentos.setSelectedItem(locDepartamento);
	}

	public Domicilio getDomicilio() {
		if (domicilio == null){
			this.domicilio = new Domicilio();
		}
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public ComboBoxModel getModeloProvincias() {
		return modeloProvincias;
	}

	public void setModeloProvincias(ComboBoxModel modeloProvincias) {
		this.modeloProvincias = modeloProvincias;
	}


	public MutableComboBoxModel getModeloDepartamentos() {
		return modeloDepartamentos;
	}


	public void setModeloDepartamentos(MutableComboBoxModel modeloDepartamentos) {
		this.modeloDepartamentos = modeloDepartamentos;
	}


	public MutableComboBoxModel getModeloLocalidades() {
		return modeloLocalidades;
	}


	public void setModeloLocalidades(MutableComboBoxModel modeloLocalidades) {
		this.modeloLocalidades = modeloLocalidades;
	}


	public ConfiguracionSistemaDAO getConfiguracionSistemaDAO() {
		return configuracionSistemaDAO;
	}


	public void setConfiguracionSistemaDAO(
			ConfiguracionSistemaDAO configuracionSistemaDAO) {
		this.configuracionSistemaDAO = configuracionSistemaDAO;
	}



}	
