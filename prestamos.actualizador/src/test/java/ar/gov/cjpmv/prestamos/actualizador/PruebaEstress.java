package ar.gov.cjpmv.prestamos.actualizador;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionRevista;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Antiguedad;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Jubilacion;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PermanenciaCategoria;

public class PruebaEstress {
	
	public static void main(String[] args) {
		EntityManager em = GestorPersitencia.getInstance().getEntityManager();
		EntityTransaction tx = em.getTransaction();
		long tiempo = System.currentTimeMillis();

		try {
			tx.begin();
			Categoria cat = em.getReference(Categoria.class, 12l);
			Antiguedad an = em.getReference(Antiguedad.class, 7l);
			PermanenciaCategoria perm = em.getReference(PermanenciaCategoria.class, 1l);
			PersonaJuridica empresa = em.getReference(PersonaJuridica.class, 1l);
			Date fechaInicio = Calendar.getInstance().getTime();
			
			@SuppressWarnings("unchecked")
			List<PersonaFisica> personas = em.createQuery("select p from PersonaFisica p where estado = 'PASIVO'").getResultList();
			System.out.println("cantidad personas : "+personas.size());
			for (PersonaFisica persona : personas) {
				persona.getListaBeneficios().clear();
				persona.getListaEmpleos().clear();
				
				Empleo empleo = new Empleo();
				empleo.setAntiguedad(an);
				empleo.setFechaInicio(fechaInicio);
				empleo.setSituacionRevista(SituacionRevista.PLANTA_PERMANENTE);
				empleo.setTipoCategoria(cat);
				empleo.setPermanencia(perm);
				empleo.setEmpleado(persona);
				empleo.setEmpresa(empresa);
				
				em.persist(empleo);
				
				Jubilacion bene = new Jubilacion();
				bene.setFechaOtorgamiento(fechaInicio);
				bene.setTipoBeneficio(TipoBeneficio.JUBILACION_COMUN);
				bene.setPersona(persona);
					
				em.persist(bene);
			}
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
		
		System.out.println(System.currentTimeMillis()-tiempo+ " ms");
	}
}
