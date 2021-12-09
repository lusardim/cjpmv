package ar.gov.cjpmv.prestamos.core.business.dao;

import java.util.List;

import javax.persistence.Query;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.Departamento;
import ar.gov.cjpmv.prestamos.core.persistence.TipoDocumento;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Banco;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaBancaria;

public class CuentaBancariaDAO extends GenericDAOImpl<CuentaBancaria>{

	@Override
	public CuentaBancaria getObjetoPorId(Long clave) {
		return this.entityManager.find(CuentaBancaria.class,clave);
	}
	
	@SuppressWarnings("unchecked")
	public List<CuentaBancaria> findListaCuentaBancaria(String pNumero){
		String locConsulta="select t from CuentaBancaria t";
		String locConsultaFin=" order by t.banco, t.numero asc";
		Query locQuery;
		if(pNumero==null){
			locQuery= entityManager.createQuery(locConsulta+locConsultaFin);
		}
		else{
			locConsulta+=" where upper(t.numero) like :numero "+locConsultaFin;
			locQuery= entityManager.createQuery(locConsulta);
			locQuery.setParameter("numero", pNumero.toUpperCase().trim()+"%");
		}
		return locQuery.getResultList();
	}
	

	@SuppressWarnings("unchecked")
	public List<CuentaBancaria> findCuentasBancarias(){
		String locConsulta="select t from CuentaBancaria t order by t.banco, t.numero asc";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		return locQuery.getResultList();
	}
	
	
	

	@Override
	protected void validarAlta(CuentaBancaria pObjeto) throws LogicaException {
		if(pObjeto.getBanco()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="entidad bancaria";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			if(pObjeto.getTipo()==null){
				int locCodigoMensaje=2;
				String locCampoMensaje="tipo de cuenta bancaria";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else{
				if(pObjeto.getNumero()==null){
					int locCodigoMensaje=2;
					String locCampoMensaje="número de cuenta bancaria";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
				else{
					String locConsulta="select t from CuentaBancaria t where upper(t.numero) = :numero and t.banco = :banco and t.tipo = :tipo";
					Query locQuery;
					locQuery= entityManager.createQuery(locConsulta);
					locQuery.setParameter("numero",pObjeto.getNumero().toUpperCase().trim());
					locQuery.setParameter("banco",pObjeto.getBanco());
					locQuery.setParameter("tipo",pObjeto.getTipo());
					if (!locQuery.getResultList().isEmpty()){
						int locCodigoMensaje=1;
						String locCampoMensaje="La cuenta bancaria";
						throw new LogicaException(locCodigoMensaje, locCampoMensaje);
					}
					else{
						
						if(pObjeto.getCbu()!=null){
							System.out.println("paso");
							
							String locConsultaCBU="select t from CuentaBancaria t where upper(t.cbu) = :cbu";
							Query locQuery1;
							locQuery1= entityManager.createQuery(locConsultaCBU);
							locQuery1.setParameter("cbu", pObjeto.getCbu());
							if(!locQuery1.getResultList().isEmpty()){
								int locCodigoMensaje1=1;
								String locCampoMensaje1="El CBU";
								throw new LogicaException(locCodigoMensaje1, locCampoMensaje1);
							}
						}
						else{
							if(pObjeto.getSaldo()==null){
								int locCodigoMensaje=2;
								String locCampoMensaje="saldo inicial de cuenta bancaria";
								throw new LogicaException(locCodigoMensaje, locCampoMensaje);
							}
						}
					}
				}
			}
		}
	}
	
	protected void validarModificacion(CuentaBancaria pObjeto)throws LogicaException {
		if(pObjeto.getBanco()==null){
			int locCodigoMensaje=2;
			String locCampoMensaje="entidad bancaria";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			if(pObjeto.getTipo()==null){
				int locCodigoMensaje=2;
				String locCampoMensaje="tipo de cuenta bancaria";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
			else{
				if(pObjeto.getNumero()==null){
					int locCodigoMensaje=2;
					String locCampoMensaje="numero de cuenta bancaria";
					throw new LogicaException(locCodigoMensaje, locCampoMensaje);
				}
				else{
					String locConsulta="select t from CuentaBancaria t where upper(t.numero) = :numero and t.banco = :banco and t.tipo = :tipo and t.id != :id";
					Query locQuery;
					locQuery= entityManager.createQuery(locConsulta);
					locQuery.setParameter("numero",pObjeto.getNumero().toUpperCase().trim());
					locQuery.setParameter("banco",pObjeto.getBanco());
					locQuery.setParameter("id",pObjeto.getId());
					locQuery.setParameter("tipo",pObjeto.getTipo());
					if (!locQuery.getResultList().isEmpty()){
						int locCodigoMensaje=1;
						String locCampoMensaje="La cuenta bancaria";
						throw new LogicaException(locCodigoMensaje, locCampoMensaje);
					}
					else{
						String locConsulta1="select t from CuentaBancaria t where upper(t.cbu)= :cbu and t.id != :id";
						Query locQuery1;
						locQuery1= entityManager.createQuery(locConsulta1);
						locQuery1.setParameter("cbu", pObjeto.getCbu());
						locQuery1.setParameter("id", pObjeto.getId());
						if(!locQuery1.getResultList().isEmpty()){
							int locCodigoMensaje=1;
							String locCampoMensaje="El CBU";
							throw new LogicaException(locCodigoMensaje, locCampoMensaje);
						}
						else{
							if(pObjeto.getSaldo()==null){
								int locCodigoMensaje=2;
								String locCampoMensaje="saldo inicial de cuenta bancaria";
								throw new LogicaException(locCodigoMensaje, locCampoMensaje);
							}
						}
					}
				}
			}
		}
	}
	

	
	@Override
	protected void validarEliminar(CuentaBancaria pObjeto)throws LogicaException {
		//asociado con cheque y con cobroPorBanco
		String locConsulta="select t from CobroPorBanco t where t.cuenta = :cuenta";
		Query locQuery;
		locQuery= entityManager.createQuery(locConsulta);
		locQuery.setParameter("cuenta",pObjeto);
		if (!locQuery.getResultList().isEmpty()){
			int locCodigoMensaje=19;
			String locCampoMensaje="La cuenta bancaria";
			throw new LogicaException(locCodigoMensaje, locCampoMensaje);
		}
		else{
			String locConsultaDomicilio="select t from Cheque t where t.cuenta = :cuenta";
			Query locQueryDomicilio;
			locQueryDomicilio= entityManager.createQuery(locConsultaDomicilio);
			locQueryDomicilio.setParameter("cuenta",pObjeto);
			if (!locQueryDomicilio.getResultList().isEmpty()){
				int locCodigoMensaje=6;
				String locCampoMensaje="La cuenta bancaria";
				throw new LogicaException(locCodigoMensaje, locCampoMensaje);
			}
		}
	}


	
	
	


}
