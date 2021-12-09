package ar.gov.cjpmv.prestamos.core.helpers;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.Domicilio;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCivil;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.Sexo;

public class PersonaHelper {
	private static String[] nombres = {"juan","daiana","mariano","beto","fernando","nombre", "otro nombre","Lalalalla"};
	private static String[] apellidos = {"ernst","janapempo","lusardi","otro apellido","apellido loco"};
	
	private List<TipoDocumento> listaTiposDocumentos;
	private Random random;
	
	public PersonaHelper() {
		random = new Random();
		this.leerTiposDocumentos();
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoDocumento> leerTiposDocumentos() {
		EntityManager locEntityManager = GestorPersitencia.getInstance().getEntityManager();
		try{
			this.listaTiposDocumentos = locEntityManager.createQuery("select t from TipoDocumento t").getResultList();
			return this.listaTiposDocumentos;
		}
		finally{
			locEntityManager.close();
		}
	}
	

	/**
	 * Este método genera una lista de personas donde cada persona tiene uno de los domicilios 
	 * pasados por parámetro. La cantidad de personas generadas es directamente proporcional 
	 * a la cantida de domicilios. 
	 * @param pListaDomicilios
	 * @return
	 * @throws Exception 
	 */
	public PersonaFisica[] generarPersonasFisicas(Domicilio[] pListaDomicilios) throws Exception{
		PersonaFisica[] locListaPersonasFisicas = new PersonaFisica[pListaDomicilios.length];
		for (int i=0; i<locListaPersonasFisicas.length;i++){
			Domicilio cadaDomicilio = pListaDomicilios[i];
			PersonaFisica locPersonaFisica = this.getNuevaPersonaFisica();
			locPersonaFisica.setDomicilio(cadaDomicilio);
			locListaPersonasFisicas[i]=locPersonaFisica;
		}
		return locListaPersonasFisicas;
	}
	
	
	public PersonaFisica getNuevaPersonaFisica() throws Exception{
		PersonaFisica locPersona = new PersonaFisica();
		
		locPersona.setCui(this.getCuiAlAzar());
		locPersona.setNombre(this.getNombreAlAzar());
		locPersona.setApellido(this.getApellidoAlAzar());
		//locPersona.setLegajo(new Long(this.random.nextInt(1000)));
		locPersona.setLegajo(0l);
		locPersona.setEstado(this.getEstadoPersona());
		if (random.nextBoolean()){
			Calendar locCalendar = Calendar.getInstance();
			locCalendar.add(Calendar.DAY_OF_YEAR, this.random.nextInt(50)-20);
			locPersona.setFechaDefuncion(locCalendar.getTime());
			
		}
		locPersona.setNumeroDocumento(this.random.nextInt());
		locPersona.setEstadoCivil(this.getEstadoCivilAlAzar());
		locPersona.setNumeroDocumento(this.random.nextInt(60000000));
		locPersona.setTipoDocumento(this.getTipoDocumentoAlAzar());


		locPersona.setSexo(this.getSexoAlAzar());
		return locPersona;
	}
	
	
	private Long getCuiAlAzar() {
		int numero = random.nextInt(60000000);
		Long retorno = Long.parseLong("20"+numero+"4");
		return retorno;
	}

	public TipoDocumento getTipoDocumentoAlAzar() throws Exception {
		if ((this.listaTiposDocumentos==null)||(this.listaTiposDocumentos.isEmpty())){
			this.leerTiposDocumentos();
		}
		int numeroAlAzar = this.random.nextInt(this.listaTiposDocumentos.size());
		return listaTiposDocumentos.get(numeroAlAzar);
	}

	private Sexo getSexoAlAzar() {
		Sexo[] locSexo = Sexo.values();
		return locSexo[this.random.nextInt(locSexo.length)];
	}

	private EstadoPersonaFisica getEstadoPersona() {
		EstadoPersonaFisica[] locListaEstadosPersonaFisica = EstadoPersonaFisica.values();
		return locListaEstadosPersonaFisica[this.random.nextInt(locListaEstadosPersonaFisica.length)];
	}

	private EstadoCivil getEstadoCivilAlAzar() {
		EstadoCivil[] locListaEstadosCiviles = EstadoCivil.values();
		return locListaEstadosCiviles[this.random.nextInt(locListaEstadosCiviles.length)];
	}

	private String getApellidoAlAzar() {
		return (String)this.getRandomString(apellidos);
	}

	private String getNombreAlAzar() {
		return (String)this.getRandomString(nombres);
	}

	private Object getRandomString(Object[] pListaNombres) {
		int indice = this.random.nextInt(pListaNombres.length);
		return pListaNombres[indice];
	}
	
}
