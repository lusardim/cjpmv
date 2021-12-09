package ar.gov.cjpmv.prestamos.actualizador;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;

public class lalala extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lalala frame = new lalala();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public lalala() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		table = new JTable();
		setModel();
		contentPane.add(table, BorderLayout.CENTER);
	}

	private void setModel() {
		EntityManager em = GestorPersitencia.getInstance().getEntityManager();
		try {
			@SuppressWarnings("unchecked")
			List<Categoria> cat = em.createQuery("from Categoria c").getResultList();
			String[][] categorias = new String[cat.size()][3];
			for (int i = 0 ; i < cat.size() ; i++) {
				Categoria categoria = cat.get(i);
				categorias[i][0] = categoria.toString();
				categorias[i][1] = categoria.getTipoPersona().getDescripcion();
				categorias[i][2] = categoria.getTotal().toString();
			}
			table.setModel(new DefaultTableModel(categorias, new String[]{"categoria", "tipo persona", "monto"}));
		}
		finally {
			em.close();
		}
		
		
	}

}
