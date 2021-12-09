package ar.gov.cjpmv.prestamos.gui.sueldos;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import ar.gov.cjpmv.prestamos.gui.Principal;
import ar.gov.cjpmv.prestamos.gui.PrincipalView;

public class CargandoLiquidacionesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5721146810272833934L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public CargandoLiquidacionesDialog() {
		Image imagen = ((PrincipalView)Principal.getApplication().getMainFrame()).getIconImage();
		this.setIconImage(imagen);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setTitle("Por favor espere...");
		setBounds(100, 100, 412, 116);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		
		JLabel lblCargandoLiquidacionesPor = new JLabel("Cargando liquidaciones, por favor espere...");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCargandoLiquidacionesPor)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(12)
					.addComponent(lblCargandoLiquidacionesPor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
