package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.FiltroBusquedaPersonas;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;

public class PersonaDAO extends GenericDAOImpl<Persona> {
	
	@Override
	public Persona getObjetoPorId(Long clave) {
		return this.entityManager.find(Persona.class,clave);
	}
	
	@SuppressWarnings("unchecked")
	public List<PersonaFisica> findListaPersonasFisicas(Long pLegajo, Long pCuil, TipoDocumento pTipoDocumento, 
												Integer pNumeroDocumento, String pApellido, EstadoPersonaFisica pEstado) {
		Map<String, Object> locListaParametros = new HashMap<String, Object>();
		String locConsulta = "select p from PersonaFisica p ";
		boolean primero = true;
		
		if (pLegajo!=null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" p.legajo = :legajo";
			locListaParametros.put("legajo", pLegajo);
			primero = false;
		}
		
		if (pCuil!=null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" p.cui = :cuil";
			locListaParametros.put("cuil", pCuil);
			primero = false;
		}

		if (pTipoDocumento!=null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" p.tipoDocumento = :tipoDocumento ";
			locListaParametros.put("tipoDocumento", pTipoDocumento);
			primero = false;
		}
		
		if (pNumeroDocumento!=null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" p.numeroDocumento = :numeroDocumento ";
			locListaParametros.put("numeroDocumento", pNumeroDocumento);
			primero = false;
		}

		if (pApellido!=null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" upper(p.apellido) like :apellido";
			locListaParametros.put("apellido",pApellido.toUpperCase().trim()+"%");
			primero=false;
		}

		if (pEstado!=null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" p.estado = :estado";
			locListaParametros.put("estado", pEstado);
		}
		return (List<PersonaFisica>) this.getLista(locConsulta, locListaParametros);
	}
	
	public Persona getPersonaPorCui(Long pCuip) throws LogicaException {
		
		String locConsulta = "select p from Persona p where p.cui=:pcui";
		Query locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("pcui", pCuip);
		if (!locQuery.getResultList().isEmpty()){
			return (Persona) locQuery.getResultList().get(0);
		}
		else{
			int locCodigo=57;
			String locCampo="";
			throw new LogicaException(locCodigo, locCampo);
		}

	}
	
	public Persona buscarPersonaPorCui(Long pCuip){
		String locConsulta = "select p from Persona p where p.cui=:pcui";
		Query locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("pcui", pCuip);
		try{
			return (Persona) locQuery.getResultList().get(0);
		}
		catch(NoResultException e){
			return null;
		}

	}
	
	public List<PersonaJuridica> findListaPersonasJuridicas(Long pCuil,	String pRazonSocial, EstadoPersonaJuridica pEstado) {
		return this.findListaPersonasJuridicas(pCuil, pRazonSocial, pEstado,null,null);
	}

	@SuppressWarnings("unchecked")
	public List<PersonaJuridica> findListaPersonasJuridicas(Long pCuil,	String pRazonSocial, EstadoPersonaJuridica pEstado, 
								Date pFechaInicioActividades, Date pFechaFin) {
		Map<String, Object> locListaParametros = new HashMap<String, Object>();
		
		String locConsulta = "select p from PersonaJuridica p";
		boolean primero = true;
		
		if (pCuil!=null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" p.cui = :cuil ";
			primero = false;
			locListaParametros.put("cuil", pCuil);
		}
		
		if (pRazonSocial!=null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" upper(p.nombre) like :nombre";
			primero = false;
			locListaParametros.put("nombre", pRazonSocial.toUpperCase().trim()+"%");
		}
		
		if (pEstado !=null ){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" p.estado = :estado ";
			primero = false;
			locListaParametros.put("estado", pEstado);
		}
		
		if (pFechaInicioActividades!=null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" p.fechaNacimiento = :fechaNacimiento ";
			primero = false;
			locListaParametros.put("fechaNacimiento", pFechaInicioActividades);
		}
		if (pFechaFin != null){
			locConsulta+=(primero)?" where ":" and ";
			locConsulta+=" p.fechaCeseActividades = :fechaCeseActividades";
			primero = false;
			locListaParametros.put("fechaCeseActividades", pFechaInicioActividades);
		}
		
		return (List<PersonaJuridica>) this.getLista(locConsulta, locListaParametros);
	}
	
	public EstadoPersonaFisica getEstadoPersonaFisicaFromDescripcion(String pDescripcion){
		for (EstadoPersonaFisica cadaEstado : EstadoPersonaFisica.values()){
			if (cadaEstado.getDescripcion().equals(pDescripcion)){
				return cadaEstado;
			}
		}
		return null;
	}

	public EstadoPersonaJuridica getEstadoPersonaJuridicaFromDescripcion(String pDescripcion) {
		for (EstadoPersonaJuridica cadaEstado : EstadoPersonaJuridica.values()){
			if (cadaEstado.getDescripcion().equals(pDescripcion)){
				return cadaEstado;
			}
		}
		return null;
	}

	
	@Override
	protected void validarModificacion(Persona pObjeto) throws LogicaException {
	//Las validaciones son las mismas
		this.validarAlta(pObjeto);
	}
	
	@Override
	protected void validarAlta(Persona pObjeto) throws LogicaException {
		super.validarAlta(pObjeto);
		//Valida nombre
		if(pObjeto.getNombre()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="nombre de la persona";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		//valida domicilio
		if(pObjeto.getDomicilio().getCalle()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="calle en datos de contacto";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
	
		//Valida localidad y departamento
		if(pObjeto.getDomicilio().getLocalidad()==null){
			if(pObjeto.getDomicilio().getDepartamento()==null){
				int locCodigoMensaje=2;
				String locCampoMensaje="departamento en datos de contacto";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}

		if (pObjeto instanceof PersonaFisica){
			this.validarPersonaFisica((PersonaFisica)pObjeto);
		}	
	}

	@Override
	protected void validarEliminar(Persona pObjeto) throws LogicaException {
		if (pObjeto instanceof PersonaFisica){
			this.validarEliminar((PersonaFisica)pObjeto);
		}
		else{
			this.validarEliminar((PersonaJuridica)pObjeto);
		}
	}
	
	private void validarPersonaFisica(PersonaFisica persona) throws LogicaException {
		if(persona.getApellido() == null) {
			int locCodigoMensaje=2;
			String locCampoMensaje="apellido de la persona";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else if(persona.getTipoDocumento()==null) {
			int locCodigoMensaje=2;
			String locCampoMensaje="tipo de documento de la persona";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else if(persona.getNumeroDocumento()==null) {
			int locCodigoMensaje=2;
			String locCampoMensaje="número de documento de la persona";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else {

			String consulta="select e from PersonaFisica e where e.tipoDocumento = :tipoDocumento and e.numeroDocumento= :numeroDocumento";

			if (persona.getId()!=null){
				consulta+=" and e.id != "+persona.getId();
			}

			Query locQuery= entityManager.createQuery(consulta);
			locQuery.setParameter("tipoDocumento", persona.getTipoDocumento());
			locQuery.setParameter("numeroDocumento", persona.getNumeroDocumento());
			
			if (!locQuery.getResultList().isEmpty()){
				int locCodigoMensaje=25;
				String locCampoMensaje="Ya existe una persona con el mismo tipo y número de documento";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else{
				String locConsulta="select e from PersonaFisica e where e.legajo = :legajo and e.estado= :estado";

				if (persona.getId()!=null){
					locConsulta+=" and e.id != :idPersona";
				}

				Query lQuery= entityManager.createQuery(locConsulta);
				lQuery.setParameter("legajo", persona.getLegajo());
				lQuery.setParameter("estado", persona.getEstado());
				if (persona.getId()!=null){
					lQuery.setParameter("idPersona", persona.getId());
				}
				if (!lQuery.getResultList().isEmpty()){
					int locCodigoMensaje=25;
					String locCampoMensaje="Ya existe una persona con el mismo legajo y estado";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
				else{
					switch (persona.getEstado()) {
						case ACTIVO: {
							if(persona.getFechaDefuncion()!=null){
								int codigo=37;
								String campo="fecha de defunción";
								throw new LogicaException(codigo, campo);
							}
							break;
						}
						case PASIVO: {
							if(persona.getFechaDefuncion()!=null){
								int codigo=38;
								String campo="fecha de defunción";
								throw new LogicaException(codigo, campo);
							}
							break;
						}
						case FALLECIDO: {
							if(persona.getFechaDefuncion()==null){
								int locCodigoMensaje=2;
								String locCampoMensaje="fecha de defunción de la persona";
								throw new LogicaException(locCodigoMensaje, locCampoMensaje);
							}
							break;
						}
					}
				}
			}
		}
		
		//FIXME SUPER OINK OINK AL RESCATE!!!!
		for (Empleo empleo : persona.getListaEmpleos()) {
			empleo.setEmpleado(persona);
			if (empleo.getTipoCategoria() != null && !empleo.getTipoCategoria().getTipoPersona().equals(persona.getEstado())) {
				empleo.setTipoCategoria(getCategoriaEquivalente(empleo.getTipoCategoria(), persona.getEstado()));
			}
		}
		
		for (Beneficio beneficio : persona.getListaBeneficios()) {
			beneficio.setPersona(persona);
		}
	}

	//SUPER ULTRA OINK OINK, superando los límites oinkoinkseros desde 1972
	private Categoria getCategoriaEquivalente(Categoria tipoCategoria,
			EstadoPersonaFisica estado) {
		try {
			Query query = this.entityManager.createQuery("select c from Categoria c where c.numero = :numero" +
					" and c.tipoPersona = :estado");
			query.setParameter("numero", tipoCategoria.getNumero());
			query.setParameter("estado", estado);
			return (Categoria) query.getSingleResult();
		}
		catch (NoResultException e) {
			return tipoCategoria;
		}
	}

	protected void validarEliminar(PersonaFisica pObjeto) throws LogicaException {
		//Recupera todas las personas de las cuales es socio.
		Query locConsulta = this.entityManager.createQuery("select p.nombre from PersonaJuridica p " +
				"join p.listaSocios s where s = :persona");
		locConsulta.setParameter("persona", pObjeto);

		@SuppressWarnings("unchecked")
		List<String> locListaPersonasJuridicas = locConsulta.getResultList();
		if (!locListaPersonasJuridicas.isEmpty()) {
			StringBuilder locStringBuilder = new StringBuilder();
	
			for (String cadaPersonaJuridica: locListaPersonasJuridicas){
				locStringBuilder.append("\n\t\t- "+cadaPersonaJuridica);
			}
			locStringBuilder.append("\n");
			throw new LogicaException(24, locStringBuilder.toString());
		}
	}

	protected void validarEliminar(PersonaJuridica pObjeto) throws LogicaException {
		//Validar que no haya empleo cuyo organismo apunten acá
		this.validarConfiguracion(pObjeto);
		this.validarEmpleo(pObjeto);
	}

	private void validarConfiguracion(PersonaJuridica pObjeto) throws LogicaException{
		Query locConsulta = this.entityManager.createQuery("select c from ConfiguracionSistema c where c.persona = :persona");
		locConsulta.setParameter("persona", pObjeto);
		if (!locConsulta.getResultList().isEmpty()){
			throw new LogicaException(28, pObjeto.toString());
		}
	}

	/**
	 * Valida que ninguna persona esté empleada por esta persona jurídica al momento de ser eliminada
	 * @param pObjeto
	 * @throws LogicaException
	 */
	@SuppressWarnings("unchecked")
	private void validarEmpleo(PersonaJuridica pObjeto) throws LogicaException{
		Query locConsulta = this.entityManager.createQuery("select e from PersonaFisica e " +
				"												join e.listaEmpleos empleos"  +
				"												join empleos.empresa emp " +
				"										    where emp = :empresa " +
														   "    and empleos.fechaFin is null");
		locConsulta.setParameter("empresa", pObjeto);
		
		List<PersonaFisica> listaPersonas = locConsulta.getResultList();
		
		String resultado="";
		for (PersonaFisica cadaPersona : listaPersonas){
			resultado+="\n\t\t- "+cadaPersona;
		}
		throw new LogicaException(26, resultado);
	}

	
	public CuentaCorriente getCuentaCorriente(Persona locPersona) {
		String consulta="select c from CuentaCorriente c where c.persona= :pPersona";
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("pPersona", locPersona);
		try{
			return (CuentaCorriente)locQuery.getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
	}

	public CuentaCorriente getCuentaCorrientePorCuil(long cui) {
		String consulta="select c from CuentaCorriente c where c.persona.cui = :pCui";
		Query locQuery= this.entityManager.createQuery(consulta);
		locQuery.setParameter("pCui", cui);
		try{
			return (CuentaCorriente)locQuery.getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Persona> findListaPersonasConCuenta(Long pLegajo, Long pCuil,
			TipoDocumento pTipoDocumento, Integer pNumeroDocumento,
			String pApellido, String pEstado) {
		String consulta = "select distinct p from CuentaCorriente c inner join c.persona p, PersonaFisica pf, PersonaJuridica pj" +
				" where (pf = p or pj = p)";
		
		Map<String,Object> listaParametros = new HashMap<String, Object>();
		if (pLegajo != null){
			consulta+=" and pf.legajo = :legajo and p.class = PersonaFisica";
			listaParametros.put("legajo", pLegajo);
		}
		
		if (pCuil != null){
			consulta+=" and p.cui = :cui ";
			listaParametros.put("cui", pCuil);
		}
		
		if ((pTipoDocumento!=null) &&  (pNumeroDocumento!= null)){
			consulta +=" and pf.tipoDocumento = :tipoDocumento " + 
					   " and pf.numeroDocumento = :numeroDocumento " +
					   " and p.class = PersonaFisica";
			listaParametros.put("numeroDocumento",pNumeroDocumento);
			listaParametros.put("tipoDocumento",pTipoDocumento);
			
		}
		
		if (pApellido!=null){
			consulta+=" and (" +
					"(upper(pf.apellido) like :apellido and p.class = PersonaFisica)" +
					" or " +
					"(upper(pj.nombre) like :apellido and p.class = PersonaJuridica)" +
					")";
			listaParametros.put("apellido", pApellido.toUpperCase()+"%");
		}
		
		if (pEstado!=null){
			EstadoPersonaFisica estadoPersonaFisica =getEstadoPersonaFisicaFromDescripcion(pEstado);
			EstadoPersonaJuridica estadoPersonaJuridica = getEstadoPersonaJuridicaFromDescripcion(pEstado);
			
			if (estadoPersonaFisica !=null ){
				consulta+=" and pf.estado = :estadoPF ";
				listaParametros.put("estadoPF", estadoPersonaFisica);
			}
			else{
				consulta+=" and p.class = PersonaJuridica ";
			}
			
			if (estadoPersonaJuridica!=null){
				listaParametros.put("estadoPJ",estadoPersonaJuridica);
				consulta += "and pj.estado = :estadoPJ"; 
			}
			else{
				consulta+=" and p.class = PersonaFisica ";
			}
		}
		
		Query resultado = this.entityManager.createQuery(consulta);
		for (Entry<String, Object> cadaParametro : listaParametros.entrySet()){
			resultado.setParameter(cadaParametro.getKey(), cadaParametro.getValue());
		}
		
		return resultado.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Persona> findListaPersonas(FiltroBusquedaPersonas pFiltro) {
		
		String consulta = "select p from Persona p, PersonaFisica pf, PersonaJuridica pj " +
				" where (p = pf or p = pj)";
		
		Map<String,Object> listaParametros = new HashMap<String, Object>();
		if (pFiltro.getLegajo() != null){
			consulta+=" and pf.legajo = :legajo";
			listaParametros.put("legajo", pFiltro.getLegajo());
		}
		
		if (pFiltro.getCuil() != null){
			consulta+=" and p.cui = :cui ";
			listaParametros.put("cui", pFiltro.getCuil());
		}
		
		if ((pFiltro.getTipoDocumento() != null) 
				&&  
			(pFiltro.getNumeroDocumento() != null))
		{
			consulta +=" and pf.tipoDocumento = :tipoDocumento " + 
					   " and pf.numeroDocumento = :numeroDocumento ";
			listaParametros.put("numeroDocumento",pFiltro.getNumeroDocumento());
			listaParametros.put("tipoDocumento",pFiltro.getTipoDocumento());
		}
		
		if (pFiltro.getApellido() != null){
			consulta+=" and (" +
					"(upper(pf.apellido) like :apellido and p.class = PersonaFisica)" +
					" or " +
					"(upper(pj.nombre) like :apellido and p.class = PersonaJuridica)" +
					")";
			listaParametros.put("apellido", pFiltro.getApellido().toUpperCase()+"%");
		}
		
		if (pFiltro.isPersonaFisica() != null) {
			if (pFiltro.isPersonaFisica()) {
				consulta += " and p.class = PersonaFisica";
			}
			else {
				consulta += " and p.class = PersonaJuridica";
			}
		}
		
		if (pFiltro.getEstado() != null){
			EstadoPersonaFisica estadoPersonaFisica = pFiltro.getEstadoPersonaFisica();
			EstadoPersonaJuridica estadoPersonaJuridica = pFiltro.getEstadoPersonaJuridica();
			
			if (estadoPersonaFisica !=null ){
				consulta+=" and pf.estado = :estadoPF ";
				listaParametros.put("estadoPF", estadoPersonaFisica);
			}
			
			if (estadoPersonaJuridica!=null){
				listaParametros.put("estadoPJ",estadoPersonaJuridica);
				consulta += "and pj.estado = :estadoPJ"; 
			}
		}
		Query query = this.entityManager.createQuery(consulta);
		for (Entry<String,Object> cadaParametro : listaParametros.entrySet()) {
			query.setParameter(cadaParametro.getKey(), cadaParametro.getValue());
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PersonaFisica> findListaPersonasPorTipoLiquidacion(TipoLiquidacion tipo) 
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		String query = null;
		switch (tipo) {
			case ACTIVO_NORMAL:
			case ACTIVO_SAC:
				//TODO si es activo y tiene pensión, habría que considerarlo (verlo más adelante)
				query = "select p from PersonaFisica p inner join p.listaEmpleos em " +
						"where p.listaBeneficios is empty and " +
						" p.estado = :estado and em.empresa.id = 1";
				parametros.put("estado", EstadoPersonaFisica.ACTIVO);
				break;
			case JUBILACION_NORMAL:
			case JUBILACION_SAC: 
				query = "select p from PersonaFisica p " +
						" inner join p.listaBeneficios bene " +
						"where bene.tipoBeneficio != :tipo and " +
						" p.listaEmpleos is not empty and " +
						" p.estado = :estado";
				parametros.put("estado", EstadoPersonaFisica.PASIVO);
				parametros.put("tipo", TipoBeneficio.PENSION);
				break;

			case PENSION_NORMAL:
			case PENSION_SAC:
				query = "select p from PersonaFisica p" +
						" inner join p.listaBeneficios bene " +
						"where bene.tipoBeneficio = :tipo " +
						"	and p.estado != :estado " +
						"	and bene.causante.listaEmpleos is not empty ";
				parametros.put("tipo", TipoBeneficio.PENSION);
				parametros.put("estado", EstadoPersonaFisica.FALLECIDO);
				break;
		}
		return this.crearQuery(query, parametros).getResultList();
	}

}
