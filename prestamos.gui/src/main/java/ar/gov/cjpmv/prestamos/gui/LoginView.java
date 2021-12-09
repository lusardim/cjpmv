/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Login.java
 *
 * Created on 28/12/2009, 12:58:08
 */

package ar.gov.cjpmv.prestamos.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Administrador
 */
public class LoginView extends javax.swing.JDialog {

    /** Creates new form Login */
    public LoginView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCuadroLogin = new javax.swing.JPanel();
        btnIngresar = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        pnlUsuarioContrasenia = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        pasContrasenia = new javax.swing.JPasswordField();
        lblContrasenia = new javax.swing.JLabel();
        lblBannerLogin = new javax.swing.JLabel();
        lblFo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(LoginView.class);
        setTitle(resourceMap.getString("DLogin.title")); // NOI18N
        setForeground(resourceMap.getColor("DLogin.foreground")); // NOI18N
        setName("DLogin"); // NOI18N
        setResizable(false);

        pnlCuadroLogin.setBackground(resourceMap.getColor("FondoCeleste")); // NOI18N
        pnlCuadroLogin.setName("pnlCuadroLogin"); // NOI18N

        btnIngresar.setText(resourceMap.getString("btnIngresar.text")); // NOI18N
        btnIngresar.setAlignmentX(0.5F);
        btnIngresar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIngresar.setName("btnIngresar"); // NOI18N

        lblMensaje.setFont(resourceMap.getFont("TextoMensaje")); // NOI18N
        lblMensaje.setForeground(resourceMap.getColor("lblMensaje.foreground")); // NOI18N
        lblMensaje.setText(resourceMap.getString("lblMensaje.text")); // NOI18N
        lblMensaje.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblMensaje.setName("lblMensaje"); // NOI18N

        pnlUsuarioContrasenia.setBackground(resourceMap.getColor("FondoCeleste")); // NOI18N
        pnlUsuarioContrasenia.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("pnlUsuarioContrasenia.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("tituloBarrita"), resourceMap.getColor("pnlUsuarioContrasenia.border.titleColor"))); // NOI18N
        pnlUsuarioContrasenia.setFont(resourceMap.getFont("TituloBarrita")); // NOI18N
        pnlUsuarioContrasenia.setName("pnlUsuarioContrasenia"); // NOI18N

        lblUsuario.setFont(resourceMap.getFont("Campo")); // NOI18N
        lblUsuario.setForeground(resourceMap.getColor("lblUsuario.foreground")); // NOI18N
        lblUsuario.setText(resourceMap.getString("lblUsuario.text")); // NOI18N
        lblUsuario.setName("lblUsuario"); // NOI18N

        txtUsuario.setFont(resourceMap.getFont("ContenidoCampo")); // NOI18N
        txtUsuario.setForeground(resourceMap.getColor("ColorContenidoCampo")); // NOI18N
        txtUsuario.setName("txtUsuario"); // NOI18N
        txtUsuario.setSelectionColor(resourceMap.getColor("txtUsuario.selectionColor")); // NOI18N

        pasContrasenia.setFont(resourceMap.getFont("ContenidoCampo")); // NOI18N
        pasContrasenia.setForeground(resourceMap.getColor("ColorContenidoCampo")); // NOI18N
        pasContrasenia.setName("pasContrasenia"); // NOI18N

        lblContrasenia.setFont(resourceMap.getFont("Campo")); // NOI18N
        lblContrasenia.setForeground(resourceMap.getColor("lblContrasenia.foreground")); // NOI18N
        lblContrasenia.setText(resourceMap.getString("lblContrasenia.text")); // NOI18N
        lblContrasenia.setName("lblContrasenia"); // NOI18N

        javax.swing.GroupLayout pnlUsuarioContraseniaLayout = new javax.swing.GroupLayout(pnlUsuarioContrasenia);
        pnlUsuarioContrasenia.setLayout(pnlUsuarioContraseniaLayout);
        pnlUsuarioContraseniaLayout.setHorizontalGroup(
            pnlUsuarioContraseniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUsuarioContraseniaLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addGroup(pnlUsuarioContraseniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContrasenia)
                    .addComponent(lblUsuario))
                .addGap(26, 26, 26)
                .addGroup(pnlUsuarioContraseniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsuario)
                    .addComponent(pasContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );
        pnlUsuarioContraseniaLayout.setVerticalGroup(
            pnlUsuarioContraseniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioContraseniaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUsuarioContraseniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUsuarioContraseniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pasContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContrasenia))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        lblBannerLogin.setIcon(resourceMap.getIcon("lblBannerLogin.icon")); // NOI18N
        lblBannerLogin.setName("lblBannerLogin"); // NOI18N

        lblFo.setIcon(resourceMap.getIcon("lblFo.icon")); // NOI18N
        lblFo.setName("lblFo"); // NOI18N

        javax.swing.GroupLayout pnlCuadroLoginLayout = new javax.swing.GroupLayout(pnlCuadroLogin);
        pnlCuadroLogin.setLayout(pnlCuadroLoginLayout);
        pnlCuadroLoginLayout.setHorizontalGroup(
            pnlCuadroLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuadroLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCuadroLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCuadroLoginLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblMensaje)
                        .addContainerGap())
                    .addGroup(pnlCuadroLoginLayout.createSequentialGroup()
                        .addComponent(pnlUsuarioContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23))))
            .addGroup(pnlCuadroLoginLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(btnIngresar)
                .addContainerGap(192, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCuadroLoginLayout.createSequentialGroup()
                .addGroup(pnlCuadroLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBannerLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCuadroLoginLayout.setVerticalGroup(
            pnlCuadroLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuadroLoginLayout.createSequentialGroup()
                .addComponent(lblBannerLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlUsuarioContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnIngresar, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(lblFo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCuadroLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCuadroLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView dialog = new LoginView(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JLabel lblBannerLogin;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JLabel lblFo;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField pasContrasenia;
    private javax.swing.JPanel pnlCuadroLogin;
    private javax.swing.JPanel pnlUsuarioContrasenia;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
    
    
    public JButton getBtnIngresar(){
    	return this.btnIngresar;
    }
    public JLabel getLblMensaje(){
    	return this.lblMensaje;
    }
    public JTextField getTxtUsuario(){
    	return this.txtUsuario;
    }
    public JPasswordField getPasContrasenia(){
    	return this.pasContrasenia;
    }

}