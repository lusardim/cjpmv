package ar.gov.cjpmv.prestamos.core.business.sueldos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.dao.CategoriaDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.PersonaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LineaRecibo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LiquidacionHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Plantilla;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PorcentajeTipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ReciboSueldo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class AdministracionLiquidacionHaberes {
	
	private PlantillaDAO plantillaDAO;
	private PersonaDAO personaDAO;
	private LiquidacionHaberesDAO liquidacionHaberesDAO;
	private LiquidacionHaberes liquidacion;
	private ServicioCalculoConcepto servicioCalculoConcepto;
	private PorcentajeTipoBeneficioDAO porcentajeTipoBeneficioDAO;
	private CategoriaDAO categoriaDAO;
	 
	public LiquidacionHaberes generarLiquidacion(int mes, int anio, TipoLiquidacion tipo) throws LogicaException {
		liquidacion = getLiquidacionHaberesDAO().getLiquidacion(mes, anio, tipo);
		if (liquidacion != null) {
			throw new LogicaException(145);
		}

		Plantilla plantilla = getPlantillaDAO().getPlantilla(tipo);
		if ((plantilla == null) && (!tipo.isSAC())) {
			throw new LogicaException(146, tipo.getCadena());
		}
		liquidacion = new LiquidacionHaberes();
		liquidacion.setAnio(anio);
		liquidacion.setMes(mes);
		liquidacion.setPlantilla(plantilla);
		liquidacion.setTipo(tipo);
		
		List<PersonaFisica> personas = getPersonaDAO().findListaPersonasPorTipoLiquidacion(tipo);
		for (PersonaFisica persona : personas) {
			ReciboSueldo recibo = generarRecibo(persona);
			liquidacion.add(recibo);
		}
		return liquidacion;
	}

	private ReciboSueldo generarRecibo(PersonaFisica persona) {
		ReciboSueldo recibo = new ReciboSueldo();
		recibo.setLiquidacion(liquidacion);
		recibo.setPersona(persona);
		recibo.setServicio(getServicioCalculoConcepto());
		if (liquidacion.getPlantilla() != null) {
			for (ConceptoHaberes concepto : getConceptos(persona)) {
				concepto.setServicio(getServicioCalculoConcepto());
				
				LineaRecibo linea = new LineaRecibo();
				linea.setConcepto(concepto);
				linea.setMonto(concepto.getMonto(recibo));
				if (linea.getMonto() != null) {
					linea.setCantidad(concepto.getCantidad(recibo));
					recibo.add(linea);
				}
			}
		}
		return recibo;
	}

	private Set<ConceptoHaberes> getConceptos(PersonaFisica persona) {
		int mes = liquidacion.getMes();
		int anio = liquidacion.getAnio();
		
		Set<ConceptoHaberes> conceptos = new TreeSet<ConceptoHaberes>(new ConceptoHaberesComparator());
		List<ConceptoHaberes> conceptosLiquidacionAnterior = liquidacionHaberesDAO
				.getConceptosLiquidacionAnterior(mes, anio, persona, liquidacion.getTipo());
		if (conceptosLiquidacionAnterior != null) {
			conceptos.addAll(conceptosLiquidacionAnterior);
		}
		conceptos.addAll(liquidacion.getPlantilla().getListaConceptos());
		return conceptos;
	}

	public LiquidacionHaberes getLiquidacion(int mes, int anio,
			TipoLiquidacion tipo) {
		return getLiquidacionHaberesDAO().getLiquidacion(mes, anio, tipo);
	}

	public List<LiquidacionHaberes> findLiquidacion(Integer mes, Integer anio,
			TipoLiquidacion tipo) {
		return getLiquidacionHaberesDAO().findLiquidacion(mes, anio, tipo);
	}

	public PlantillaDAO getPlantillaDAO() {
		if (plantillaDAO == null) {
			plantillaDAO = DAOFactory.getDefecto().getPlantillaDAO();
		}
		return plantillaDAO;
	}

	public void setPlantillaDAO(PlantillaDAO plantillaDAO) {
		this.plantillaDAO = plantillaDAO;
	}

	public PersonaDAO getPersonaDAO() {
		if (personaDAO == null) {
			personaDAO = DAOFactory.getDefecto().getPersonaDAO();
		}
		return personaDAO;
	}

	public void setPersonaDAO(PersonaDAO personaDAO) {
		this.personaDAO = personaDAO;
	}

	public LiquidacionHaberesDAO getLiquidacionHaberesDAO() {
		if (liquidacionHaberesDAO == null) {
			liquidacionHaberesDAO = DAOFactory.getDefecto().getLiquidacionHaberesDAO();
		}
		return liquidacionHaberesDAO;
	}

	public void setLiquidacionHaberesDAO(LiquidacionHaberesDAO liquidacionHaberesDAO) {
		this.liquidacionHaberesDAO = liquidacionHaberesDAO;
	}

	public ServicioCalculoConcepto getServicioCalculoConcepto() {
		if (servicioCalculoConcepto == null) {
			servicioCalculoConcepto = new ServicioCalculoConcepto();
		}
		return servicioCalculoConcepto;
	}
	
	public void eliminar(LiquidacionHaberes liquidacion) throws LogicaException {
		getLiquidacionHaberesDAO().eliminar(liquidacion);
	}
	
	public void guardar(LiquidacionHaberes liquidacion) throws LogicaException {
		if (liquidacion.getId() == null) {
			this.liquidacionHaberesDAO.agregar(liquidacion);
		}
		else { 
			liquidacionHaberesDAO.modificar(liquidacion);
		}
	}

	public PorcentajeTipoBeneficioDAO getPorcentajeTipoBeneficioDAO() {
		if (porcentajeTipoBeneficioDAO == null) {
			porcentajeTipoBeneficioDAO = new PorcentajeTipoBeneficioDAO();
		}
		return porcentajeTipoBeneficioDAO;
	}
	
	public void setServicioCalculoConcepto(
			ServicioCalculoConcepto servicioCalculoConcepto) {
		this.servicioCalculoConcepto = servicioCalculoConcepto;
	}

	public List<ReciboSueldo> getRecibos(LiquidacionHaberes liquidacionHaberes) {
		return liquidacionHaberesDAO.getRecibos(liquidacionHaberes);
	}

	public boolean isEmpleoLiquidado(Empleo pEmpleo) {
		return liquidacionHaberesDAO.isEmpleoLiquidado(pEmpleo);
	}

	public boolean isBeneficioLiquidado(Beneficio beneficio) {
		return getLiquidacionHaberesDAO().isBeneficioLiquidado(beneficio);
	}
	
	public void setPorcentajeJubilacion(BigDecimal porcentaje) throws LogicaException {
		if (porcentaje == null || porcentaje.floatValue() <= 0) {
			throw new LogicaException(148);
		}
		
		PorcentajeTipoBeneficio objeto = this.getPorcentajeTipoBeneficioDAO()
				.getObjetoPorId(1l);
		objeto.setPorcentaje(porcentaje);
		getPorcentajeTipoBeneficioDAO().modificar(objeto);
	}
	
	public BigDecimal getPorcentajeJubilacion() {
		return this.getServicioCalculoConcepto().getPorcentajeJubilacion();
	}
	
	public void setPorcentajePension(BigDecimal porcentaje) throws LogicaException {
		if (porcentaje == null || porcentaje.floatValue() <= 0) {
			throw new LogicaException(148);
		}

		PorcentajeTipoBeneficio objeto = this.getPorcentajeTipoBeneficioDAO()
				.getObjetoPorId(2l);
		objeto.setPorcentaje(porcentaje);
		getPorcentajeTipoBeneficioDAO().modificar(objeto);
	}
	
	public BigDecimal getPorcentajePension() {
		return this.getServicioCalculoConcepto().getPorcentajePension();
	}

	public CategoriaDAO getCategoriaDAO() {
		if (categoriaDAO == null) {
			categoriaDAO = DAOFactory.getDefecto().getCategoriaDAO();
		}
		return categoriaDAO;
	}
	public List<Categoria> getListaCategorias(EstadoPersonaFisica estado) {
		return getCategoriaDAO().findCategorias(estado);
	}

	
	public void guardar(List<Categoria> categorias) throws LogicaException {
		for (Categoria categoria : categorias) {
			if (categoria.getTotal().floatValue() <= 0) {
				throw new LogicaException(149, categoria.toString());
			}
		}
		this.categoriaDAO.guardarCategorias(categorias);
	}
}