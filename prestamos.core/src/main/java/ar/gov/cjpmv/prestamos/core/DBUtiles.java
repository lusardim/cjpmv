package ar.gov.cjpmv.prestamos.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtiles {

	private static DBUtiles instance;
	private Logger logger = Logger.getLogger(DBUtiles.class.getName());
	
	private Connection conexion;
	
	public static DBUtiles getInstance(){
		if (instance==null){
			instance = new DBUtiles();
		}
		return instance;
	}
	
	private DBUtiles(){}
	
	public void cargarScript(File script) throws Exception {
		conexion = GestorPersitencia.getInstance().getConnection();
		Statement statement = conexion.createStatement();
		String cadaPaso = null;
		try {
			logger.info("Ejecutando el script: "+script.getName());
			if (!script.exists()) {
				throw new IllegalArgumentException("El archivo "+script.getName()+
						" debe existir");
			}

			String stringConsulta = getConsulta(script);
			logger.fine("script:\n"+stringConsulta);
			conexion.setAutoCommit(false);
 
			//Por cada paso del script ejecuto una consulta aparte
			String[] pasos = stringConsulta.split(";");
			for (int i = 0; i < pasos.length ; i++) {
				cadaPaso = pasos[i];
				if (!cadaPaso.trim().isEmpty()) {
					if (logger.isLoggable(Level.FINE)) {
						logger.fine("ejecutando :"+ cadaPaso);						
						logger.fine("_____________");
					}
					statement.execute(cadaPaso);
				}
			}
			conexion.commit();
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Error en script:"+script.getName()+" sql: "+cadaPaso, e);
			conexion.rollback();
			throw e;
		}
		finally {
			statement.close();
			conexion.close();
		}
	}

	private String getConsulta(File script) throws Exception {
		FileReader fileReader = new FileReader(script);
		try {
			BufferedReader reader = new BufferedReader(fileReader);
			StringBuilder builder = new StringBuilder();
			String linea = reader.readLine();
			while (linea != null) {
				builder.append(linea+"\n");
				linea = reader.readLine();
			}
			return builder.toString();
		}
		finally {
			fileReader.close();
		}
	}
}
