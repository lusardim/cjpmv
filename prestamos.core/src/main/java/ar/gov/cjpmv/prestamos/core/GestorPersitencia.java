package ar.gov.cjpmv.prestamos.core;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.ejb.EntityManagerImpl;

import ar.gov.cjpmv.prestamos.core.tareas.CargarViasCobro;
import ar.gov.cjpmv.prestamos.core.tareas.LimpiezaSaldos;

public class GestorPersitencia {
	
	private EntityManagerFactory factoria;
	
	private static GestorPersitencia instance;
	
	private List<Runnable> listaTareas;
	
	public synchronized static GestorPersitencia getInstance(){
		try{
			if (instance==null){
				instance = new GestorPersitencia();
			}
			return instance;
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}
	
	private GestorPersitencia() {
		try{
			this.factoria = Persistence.createEntityManagerFactory("cjpmv");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public void ejecutarLimpiezaInicio() throws SecurityException, IOException {
		getInstance().inicializarTareasDeInicio();
		getInstance().ejecutarTareasInicio();
	}
	
	/**
	 * Acá se inicia la lista de tareas, si se necesita agregar alguna nueva, hacerlo acá
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	private void inicializarTareasDeInicio() throws SecurityException, IOException {
		this.listaTareas = new ArrayList<Runnable>();
		this.listaTareas.add(new CargarViasCobro());
		listaTareas.add(new LimpiezaSaldos());
	}


	/**
	 * Dispara todas las tareas de inicio en segundo plano
	 */
	public void ejecutarTareasInicio() {
		for (Runnable cadaTarea : this.listaTareas){
			Thread locThread = new Thread(cadaTarea);
			locThread.start();
		}
	}

	public EntityManager getEntityManager(){
		return this.factoria.createEntityManager();
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.factoria.close();
		super.finalize();
	}

	
	public Connection getConnection(){
		if (this.factoria.isOpen()){
			return ((EntityManagerImpl)this.factoria.createEntityManager()).getSession().connection();
		}
		return null;
	}
}
