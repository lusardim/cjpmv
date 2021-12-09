package ar.gov.cjpmv.prestamos.gui.reportes;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

public class GestorImpresion {
	
	
	public static void imprimirDirecto(Reportes pReporte, Map<String, Object> pParametros) throws Exception{
		Connection locConection = GestorPersitencia.getInstance().getConnection();
		InputStream locStream = null;
		try{
			locStream = pReporte.getStream();
		//	locStream = new FileInputStream(pReporte.getArchivo());
			JasperPrint locPrint = JasperFillManager.fillReport(locStream, 
					pParametros,locConection);
			JasperPrintManager.printReport(locPrint, true);
		}
		finally{
			if (locStream!=null){
				locStream.close();
			}
			locConection.close();
		}
	}
	
	
	public static void imprimirConViewer(Dialog pPadre,Reportes pReporte, Map<String, Object> pParametros) throws Exception{
		Connection locConection = GestorPersitencia.getInstance().getConnection();
		InputStream locStream = null;
		try{
			locStream = pReporte.getStream();
			//locStream = new FileInputStream(pReporte.getArchivo());
			JasperPrint locPrint = JasperFillManager.fillReport(locStream, 
					pParametros,locConection);
			JDialog locDialog = new JDialog(pPadre);
			JRViewer locViewer = new JRViewer(locPrint);
			locDialog.add(locViewer);
			Dimension pantalla=Toolkit.getDefaultToolkit().getScreenSize();
			pantalla.height-=30;
	        locDialog.setPreferredSize(pantalla);
			locDialog.pack();
			locDialog.setModal(true);
			locDialog.setTitle(pReporte.getTitulo());
			locDialog.setVisible(true);
		}
		finally{
			if (locStream!=null){
				locStream.close();
			}
			locConection.close();
		} 
	}
	
	public static void imprimirCollectionDataSource(Dialog pPadre, Reportes pReporte, Map<String, String> pParametros, List<? extends Object> listaDatos) throws Exception{
		InputStream locStream = null;
		try{
			//locStream = new FileInputStream(pReporte.getArchivo());
			locStream = pReporte.getStream();
			JasperPrint locPrint= JasperFillManager.fillReport(locStream, pParametros, new JRBeanCollectionDataSource(listaDatos));
			JDialog locDialog = new JDialog(pPadre);
			JRViewer locViewer = new JRViewer(locPrint);
			locDialog.add(locViewer);
			Dimension pantalla=Toolkit.getDefaultToolkit().getScreenSize();
			pantalla.height-=30;
	        locDialog.setPreferredSize(pantalla);
			locDialog.pack();
			locDialog.setModal(true);
			locDialog.setTitle(pReporte.getTitulo());
			locDialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("error");
		}
		
		finally{
			if (locStream!=null){
				locStream.close();
			}
		}
	}
	
	

	/**
	 * Genera el reporte sin necesidad de llamar al viewer, devuelve true si logró imprimir
	 * @param pPadre
	 * @param pReporte
	 * @param pParametros
	 * @param listaDatos
	 * @return
	 * @throws Exception
	 */
	public static boolean impresionSinViewer(Reportes pReporte, Map<String, String> pParametros, List<? extends Object> listaDatos) throws Exception{
		FileInputStream locStream = null;
		try{
			//locStream = new FileInputStream(pReporte.getArchivo());
			JasperPrint locPrint= JasperFillManager.fillReport(pReporte.getStream(), pParametros, new JRBeanCollectionDataSource(listaDatos));
			return JasperPrintManager.printReport(locPrint, true);
		}
		finally{
			if (locStream!=null){
				locStream.close();
			}
		}
	}
	
	
	
}
