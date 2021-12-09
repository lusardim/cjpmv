package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.seguridad.Usuario;

public class UsuarioDAO extends GenericDAOImpl<Usuario>{

	@Override
	public Usuario getObjetoPorId(Long clave) {
		return this.entityManager.find(Usuario.class, clave);
	}

	public Usuario validarUsuarioContrasenia(String pNombre, String pContrasenia) throws LogicaException{
		try {
			if((pNombre==null)||(pContrasenia==null)){
				int locCodigoMensaje=14;
				String locCampoMensaje="";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else {
				Query locQuery;
				String consulta ="select e from Usuario e where upper(e.nombre) = :nombre and e.contrasenia= :contrasenia";
				locQuery= entityManager.createQuery(consulta);
				locQuery.setParameter("nombre", pNombre.toUpperCase().trim());
				locQuery.setParameter("contrasenia", pContrasenia);

				List<Usuario> listaUsuario= locQuery.getResultList();
				if (listaUsuario.isEmpty()){
					int locCodigoMensaje=15;
					String locCampoMensaje="El usuario o la contraseña";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
				else{
					String locContrasenia=listaUsuario.get(0).getContrasenia();
					if(!locContrasenia.equals(pContrasenia)){
						int locCodigoMensaje=15;
						String locCampoMensaje="El usuario o la contraseña";
						throw new LogicaException(locCodigoMensaje, locCampoMensaje);
					}
				}

				Usuario usuarioLoggeado = listaUsuario.get(0);
				if (usuarioLoggeado != null) {
					//significa que tenemos un login bien.
					GestorPersitencia.getInstance().ejecutarLimpiezaInicio();
				}
				return listaUsuario.get(0);
			}
		}
		catch (LogicaException e) {
			throw e;
		}
		catch(Exception e) {
			throw new LogicaException(94,null);
		}
	}


	public List<Usuario> getListaUsuarios(String pUsuario) {
		
		String locConsulta="select p from Usuario p";
		String locConsultaFin=" order by p.nombre asc";
		Query locQuery;
		if(pUsuario==null){
			locQuery= entityManager.createQuery(locConsulta+locConsultaFin);
		}
		else{
			locConsulta+=" where upper(p.nombre) like :nombre"+locConsultaFin;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("nombre", pUsuario+"%");
		}
		return locQuery.getResultList();
	}
	

	public Usuario getUsuario(String pUsuario) {
		String locConsulta="select p from Usuario p where upper(p.nombre) = :nombre";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("nombre", pUsuario.toUpperCase());
		return (Usuario) locQuery.getSingleResult();
	}



	@Override
	protected void validarAlta(Usuario objeto) throws LogicaException {
		if(objeto.getNombre()==null){
			int locCodigo=2;
			String locCampo="nombre de usuario";
			throw new LogicaException(locCodigo, locCampo);
		}
		else{
			String consulta="select t from Usuario t where t.nombre= :usuario";
			Query locQuery;
			locQuery= entityManager.createQuery(consulta);
			locQuery.setParameter("usuario", objeto.getNombre());
		
			if(!locQuery.getResultList().isEmpty()){
				int locCodigo=1;
				String locCampo="El nombre de usuario";
				throw new LogicaException(locCodigo, locCampo);
			}
		}
	}

	
	
	@Override
	protected void validarModificacion(Usuario objeto) throws LogicaException {
		if(objeto.getNombre()==null){
			int locCodigo=2;
			String locCampo="nombre de usuario";
			throw new LogicaException(locCodigo, locCampo);
		}
		else{
			
			String consulta="select t from Usuario t where t.nombre= :usuario and id != :id";
			Query locQuery;
			locQuery= entityManager.createQuery(consulta);
			locQuery.setParameter("usuario", objeto.getNombre());
			locQuery.setParameter("id", objeto.getId());
			
			if(!locQuery.getResultList().isEmpty()){
				int locCodigo=1;
				String locCampo="El nombre de usuario";
				throw new LogicaException(locCodigo, locCampo);
			}
		};
	}



	
}
