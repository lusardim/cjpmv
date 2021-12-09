package ar.gov.cjpmv.prestamos.actualizador;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Antiguedad;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PermanenciaCategoria;

public class Parche2 {

	private EntityManager manager = GestorPersitencia.getInstance().getEntityManager();
	
	public static void main(String[] args) throws Exception {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}

		Parche2 parche = new Parche2();
		JFrame frame = new JFrame();

		JFileChooser fileChooser = new JFileChooser();
		int valor = fileChooser.showSaveDialog(frame);
		if (valor == JFileChooser.APPROVE_OPTION) {
			File archivo = fileChooser.getSelectedFile();
			PrintWriter writer = new PrintWriter(archivo);
			writer.println("TABLA ANTIGUEDAD");
			writer.println(parche.getAntiguedadPorCategoria());
			writer.println("");
			writer.println("");
			writer.println("TABLA CATEGORIA");
			writer.println(parche.getPermanenciaPorCategoria());
			writer.close();
		}
		System.exit(0);
	}
	
	@SuppressWarnings("unchecked")
	public String getAntiguedadPorCategoria() {
		StringBuilder string  = new StringBuilder();
		List<Categoria> categorias = manager.createQuery("from Categoria where tipoPersona='PASIVO'").getResultList();
		List<Antiguedad> antiguedades = manager.createQuery("from Antiguedad").getResultList();
		
		string.append("INSERT INTO  montoantiguedadporcategoria(id, categoria_id, antiguedad_id, monto)\n ");
		string.append("VALUES ");
		
		int indice = 73;
		int cantidad = categorias.size() * antiguedades.size();
		System.out.println("antiguedades por categoria : "+cantidad);
		for (int i = 0 ; i < categorias.size() ; i++) {
			Categoria categoria = categorias.get(i);
			for (Antiguedad antiguedad : antiguedades) {
				indice++;
				BigDecimal total = antiguedad
						.getPorcentaje()
						.multiply(categoria.getTotal())
						.setScale(2, RoundingMode.HALF_UP);
				string.append("("+ indice+ ", "+categoria.getId()+" , "+antiguedad.getId()+","+ total+")");
				if (indice - 73 < cantidad) {
					string.append(",\n");
				}
				else {
					string.append(";");
				}
			}
		}
		return string.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getPermanenciaPorCategoria() {
		StringBuilder cadena = new StringBuilder();
		EntityManager manager = GestorPersitencia.getInstance().getEntityManager();
		List<Categoria> categorias = manager.createQuery("from Categoria where tipoPersona='PASIVO'").getResultList();
		List<PermanenciaCategoria> permanencias = manager.createQuery("from PermanenciaCategoria").getResultList();
		
		cadena.append("INSERT INTO montopermanenciaporcategoria(id, permanencia_id, categoria_id, monto) \n" +
				"VALUES ");
		int indice = 48;
		int cantidad = categorias.size() * permanencias.size();
		
		for (int i = 0 ; i < categorias.size() ; i++) {
			Categoria categoria = categorias.get(i);
			BigDecimal diferenciaCategoriaSuperior;
			if ( i < categorias.size() - 1) {
				diferenciaCategoriaSuperior = categorias.get(i+1).getTotal().subtract(categoria.getTotal());
			}
			else {
				diferenciaCategoriaSuperior = new BigDecimal(0);
			}
			
			for (PermanenciaCategoria permanencia : permanencias) {
				indice++;
				BigDecimal total = permanencia
						.getPorcentaje()
						.multiply(diferenciaCategoriaSuperior)
						.setScale(2, RoundingMode.HALF_UP);
				cadena.append(" ("+indice+","+permanencia.getId()+","+ categoria.getId()+","+total+ ") ");
				if (indice - 48 < cantidad) {
					cadena.append(",\n");
				}
				else {
					cadena.append(";");
				}
			}
		}
		return cadena.toString();
	}
}
