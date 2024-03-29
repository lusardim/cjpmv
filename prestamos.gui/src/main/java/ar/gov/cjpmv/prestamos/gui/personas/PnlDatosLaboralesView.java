/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlDatosLaboralesView.java
 *
 * Created on 30/01/2010, 20:13:10
 */

package ar.gov.cjpmv.prestamos.gui.personas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataListener;


import ar.gov.cjpmv.prestamos.core.business.dao.CategoriaDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.sueldos.AntiguedadDAO;
import ar.gov.cjpmv.prestamos.core.business.sueldos.PermanenciaCategoriaDAO;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionRevista;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Antiguedad;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.PermanenciaCategoria;
import ar.gov.cjpmv.prestamos.gui.AdministracionFactory;
import ar.gov.cjpmv.prestamos.gui.personas.controllers.BusquedaPersonaJuridicaController;
import ar.gov.cjpmv.prestamos.gui.personas.model.AMPersonaFisicaModel;
import ar.gov.cjpmv.prestamos.gui.personas.model.SituacionRevistaListCellRenderer;
import ar.gov.cjpmv.prestamos.gui.personas.model.TblEmpleosModel;
import ar.gov.cjpmv.prestamos.gui.utiles.AceptaNulosFormattedTextField;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;


/**
 * 
 * @author pulpol
 */
public class PnlDatosLaboralesView extends javax.swing.JPanel {


	private AdministracionFactory adminFactory;
	private JDialog padre;
	private List<Empleo> listaEmpleo;
	private BusquedaPersonaJuridicaController busquedaPersonaJuridica;
	private Empleo empleo;
	private AntiguedadDAO antiguedadDAO;
	private PermanenciaCategoriaDAO permanenciaDAO;
	private List<Antiguedad>  listaAntiguedad;
	private List<PermanenciaCategoria> listaPermanenciaCategorias;
	private ComboBoxModel cbxAntiguedadModel;
	private ComboBoxModel cbxPermanenciaModel;
	private PersonaFisica persona;
	private CategoriaDAO categoriaDAO;
    private Antiguedad antiguedad;
    private PermanenciaCategoria permanencia;
    private Categoria categoria;
    private Date fechaInicio;
    private Date fechaFin;
    private PersonaJuridica personaJuridica;
    private SituacionRevista situacion;
    private String funcion;
    
    
	
	
	/** Creates new form PnlDatosLaboralesView */
	public PnlDatosLaboralesView() {
		initComponents();
	}
	
	
	public void inicializarPanel(JDialog pPadre, AMPersonaFisicaModel modelo){
		this.padre= pPadre;
		this.persona= modelo.getPersona();
	
		this.permanenciaDAO= new PermanenciaCategoriaDAO();
		this.antiguedadDAO= new AntiguedadDAO();
		this.categoriaDAO= new CategoriaDAO();
		
		this.adminFactory= AdministracionFactory.getInstance();

		this.inicializarModelo();
		this.inicializarEventos();
		this.actualizarVista();
	}
	


	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        xtsTitulo = new org.jdesktop.swingx.JXTitledSeparator();
        javax.swing.JPanel pnlListaEmpleos = new javax.swing.JPanel();
        lblOrganismo = new javax.swing.JLabel();
        lblSituacion = new javax.swing.JLabel();
        lblFechaInicio = new javax.swing.JLabel();
        txtPersonaJuridica = new javax.swing.JTextField();
        btnBuscarPersonaJuridica = new javax.swing.JButton();
        dtcFechaInicio = new com.toedter.calendar.JDateChooser();
        cbxSituacion = new javax.swing.JComboBox();
        lblCategoria = new javax.swing.JLabel();
        lblFechaFin = new javax.swing.JLabel();
        lblFuncion = new javax.swing.JLabel();
        txtFuncion = new javax.swing.JTextField();
        dtcFechaFin = new com.toedter.calendar.JDateChooser();
        cbxCategoria = new javax.swing.JComboBox();
        lblCategoria1 = new javax.swing.JLabel();
        cbxPermanencia = new javax.swing.JComboBox();
        lblCategoria2 = new javax.swing.JLabel();
        cbxAntiguedad = new javax.swing.JComboBox();
        btnBorrarDatosLaborales = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlDatosLaboralesView.class);
        setBackground(resourceMap.getColor("ColorBlanco")); // NOI18N
        setName("Form"); // NOI18N

        xtsTitulo.setForeground(resourceMap.getColor("xtsTitulo.foreground")); // NOI18N
        xtsTitulo.setFont(resourceMap.getFont("TituloBarrita")); // NOI18N
        xtsTitulo.setName("xtsTitulo"); // NOI18N
        xtsTitulo.setTitle(resourceMap.getString("xtsTitulo.title")); // NOI18N

        pnlListaEmpleos.setBackground(resourceMap.getColor("pnlListaEmpleos.background")); // NOI18N
        pnlListaEmpleos.setName("pnlListaEmpleos"); // NOI18N

        lblOrganismo.setFont(resourceMap.getFont("lblOrganismo.font")); // NOI18N
        lblOrganismo.setForeground(resourceMap.getColor("lblOrganismo.foreground")); // NOI18N
        lblOrganismo.setText(resourceMap.getString("lblOrganismo.text")); // NOI18N
        lblOrganismo.setName("lblOrganismo"); // NOI18N

        lblSituacion.setFont(resourceMap.getFont("lblSituacion.font")); // NOI18N
        lblSituacion.setForeground(resourceMap.getColor("lblSituacion.foreground")); // NOI18N
        lblSituacion.setText(resourceMap.getString("lblSituacion.text")); // NOI18N
        lblSituacion.setName("lblSituacion"); // NOI18N

        lblFechaInicio.setFont(resourceMap.getFont("lblFechaInicio.font")); // NOI18N
        lblFechaInicio.setForeground(resourceMap.getColor("lblFechaInicio.foreground")); // NOI18N
        lblFechaInicio.setText(resourceMap.getString("lblFechaInicio.text")); // NOI18N
        lblFechaInicio.setName("lblFechaInicio"); // NOI18N

        txtPersonaJuridica.setFont(resourceMap.getFont("txtPersonaJuridica.font")); // NOI18N
        txtPersonaJuridica.setForeground(resourceMap.getColor("txtPersonaJuridica.foreground")); // NOI18N
        txtPersonaJuridica.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("txtPersonaJuridica.border.lineColor"), 1, true)); // NOI18N
        txtPersonaJuridica.setName("txtPersonaJuridica"); // NOI18N

        btnBuscarPersonaJuridica.setIcon(resourceMap.getIcon("btnBuscarPersonaJuridica.icon")); // NOI18N
        btnBuscarPersonaJuridica.setName("btnBuscarPersonaJuridica"); // NOI18N

        dtcFechaInicio.setForeground(resourceMap.getColor("dtcFechaInicio.foreground")); // NOI18N
        dtcFechaInicio.setFont(resourceMap.getFont("dtcFechaInicio.font")); // NOI18N
        dtcFechaInicio.setName("dtcFechaInicio"); // NOI18N

        cbxSituacion.setFont(resourceMap.getFont("cbxSituacion.font")); // NOI18N
        cbxSituacion.setForeground(resourceMap.getColor("cbxSituacion.foreground")); // NOI18N
        cbxSituacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Planta Permanente", "Contrato de Servicio", "Contrato de Obra", "Temporario", "Otro" }));
        cbxSituacion.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("cbxSituacion.border.lineColor"), 1, true)); // NOI18N
        cbxSituacion.setName("cbxSituacion"); // NOI18N

        lblCategoria.setFont(resourceMap.getFont("lblCategoria.font")); // NOI18N
        lblCategoria.setForeground(resourceMap.getColor("lblCategoria.foreground")); // NOI18N
        lblCategoria.setText(resourceMap.getString("lblCategoria.text")); // NOI18N
        lblCategoria.setName("lblCategoria"); // NOI18N

        lblFechaFin.setFont(resourceMap.getFont("lblFechaFin.font")); // NOI18N
        lblFechaFin.setForeground(resourceMap.getColor("lblFechaFin.foreground")); // NOI18N
        lblFechaFin.setText(resourceMap.getString("lblFechaFin.text")); // NOI18N
        lblFechaFin.setName("lblFechaFin"); // NOI18N

        lblFuncion.setFont(resourceMap.getFont("lblFuncion.font")); // NOI18N
        lblFuncion.setForeground(resourceMap.getColor("lblFuncion.foreground")); // NOI18N
        lblFuncion.setText(resourceMap.getString("lblFuncion.text")); // NOI18N
        lblFuncion.setName("lblFuncion"); // NOI18N

        txtFuncion.setFont(resourceMap.getFont("txtFuncion.font")); // NOI18N
        txtFuncion.setForeground(resourceMap.getColor("txtFuncion.foreground")); // NOI18N
        txtFuncion.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("txtFuncion.border.lineColor"), 1, true)); // NOI18N
        txtFuncion.setName("txtFuncion"); // NOI18N

        dtcFechaFin.setForeground(resourceMap.getColor("dtcFechaFin.foreground")); // NOI18N
        dtcFechaFin.setFont(resourceMap.getFont("dtcFechaFin.font")); // NOI18N
        dtcFechaFin.setName("dtcFechaFin"); // NOI18N

        cbxCategoria.setFont(resourceMap.getFont("cbxCategoria.font")); // NOI18N
        cbxCategoria.setForeground(resourceMap.getColor("cbxCategoria.foreground")); // NOI18N
        cbxCategoria.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("cbxCategoria.border.lineColor"), 1, true)); // NOI18N
        cbxCategoria.setName("cbxCategoria"); // NOI18N

        lblCategoria1.setFont(resourceMap.getFont("lblCategoria1.font")); // NOI18N
        lblCategoria1.setForeground(resourceMap.getColor("lblCategoria1.foreground")); // NOI18N
        lblCategoria1.setText(resourceMap.getString("lblCategoria1.text")); // NOI18N
        lblCategoria1.setName("lblCategoria1"); // NOI18N

        cbxPermanencia.setFont(resourceMap.getFont("cbxPermanencia.font")); // NOI18N
        cbxPermanencia.setForeground(resourceMap.getColor("cbxPermanencia.foreground")); // NOI18N
        cbxPermanencia.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("cbxPermanencia.border.lineColor"), 1, true)); // NOI18N
        cbxPermanencia.setName("cbxPermanencia"); // NOI18N

        lblCategoria2.setFont(resourceMap.getFont("lblCategoria2.font")); // NOI18N
        lblCategoria2.setForeground(resourceMap.getColor("lblCategoria2.foreground")); // NOI18N
        lblCategoria2.setText(resourceMap.getString("lblCategoria2.text")); // NOI18N
        lblCategoria2.setName("lblCategoria2"); // NOI18N

        cbxAntiguedad.setFont(resourceMap.getFont("cbxAntiguedad.font")); // NOI18N
        cbxAntiguedad.setForeground(resourceMap.getColor("cbxAntiguedad.foreground")); // NOI18N
        cbxAntiguedad.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("cbxAntiguedad.border.lineColor"), 1, true)); // NOI18N
        cbxAntiguedad.setName("cbxAntiguedad"); // NOI18N

        btnBorrarDatosLaborales.setText(resourceMap.getString("btnBorrarDatosLaborales.text")); // NOI18N
        btnBorrarDatosLaborales.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarDatosLaborales.setName("btnBorrarDatosLaborales"); // NOI18N

        javax.swing.GroupLayout pnlListaEmpleosLayout = new javax.swing.GroupLayout(pnlListaEmpleos);
        pnlListaEmpleos.setLayout(pnlListaEmpleosLayout);
        pnlListaEmpleosLayout.setHorizontalGroup(
            pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListaEmpleosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategoria2)
                    .addGroup(pnlListaEmpleosLayout.createSequentialGroup()
                        .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaInicio)
                            .addComponent(lblFechaFin)
                            .addComponent(lblSituacion))
                        .addGap(38, 38, 38)
                        .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxSituacion, 0, 255, Short.MAX_VALUE)
                            .addComponent(dtcFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dtcFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addComponent(btnBorrarDatosLaborales, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(pnlListaEmpleosLayout.createSequentialGroup()
                        .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCategoria)
                            .addComponent(lblCategoria1)
                            .addComponent(lblOrganismo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPersonaJuridica)
                            .addComponent(txtFuncion)
                            .addComponent(cbxCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxPermanencia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxAntiguedad, 0, 255, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarPersonaJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(151, 151, 151))
        );
        pnlListaEmpleosLayout.setVerticalGroup(
            pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListaEmpleosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBuscarPersonaJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPersonaJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblOrganismo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListaEmpleosLayout.createSequentialGroup()
                        .addComponent(txtFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(pnlListaEmpleosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblFuncion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCategoria)
                        .addGap(8, 8, 8)))
                .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategoria1)
                    .addComponent(cbxPermanencia, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlListaEmpleosLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblCategoria2))
                    .addGroup(pnlListaEmpleosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxAntiguedad, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSituacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFechaInicio))
                .addGroup(pnlListaEmpleosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlListaEmpleosLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(dtcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlListaEmpleosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaFin)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBorrarDatosLaborales)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlListaEmpleos, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(xtsTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(432, 432, 432))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xtsTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(pnlListaEmpleos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrarDatosLaborales;
    private javax.swing.JButton btnBuscarPersonaJuridica;
    private javax.swing.JComboBox cbxAntiguedad;
    private javax.swing.JComboBox cbxCategoria;
    private javax.swing.JComboBox cbxPermanencia;
    private javax.swing.JComboBox cbxSituacion;
    private com.toedter.calendar.JDateChooser dtcFechaFin;
    private com.toedter.calendar.JDateChooser dtcFechaInicio;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCategoria1;
    private javax.swing.JLabel lblCategoria2;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblFuncion;
    private javax.swing.JLabel lblOrganismo;
    private javax.swing.JLabel lblSituacion;
    private javax.swing.JTextField txtFuncion;
    private javax.swing.JTextField txtPersonaJuridica;
    private org.jdesktop.swingx.JXTitledSeparator xtsTitulo;
    // End of variables declaration//GEN-END:variables
	
   
    


	private void inicializarModelo() {
		
		if(!this.persona.getListaEmpleos().isEmpty()){
			this.empleo= this.persona.getListaEmpleos().get(0);

			if(this.empleo.getAntiguedad()!=null){
				this.antiguedad= this.empleo.getAntiguedad();
			}
			if(this.empleo.getTipoCategoria()!=null){
				if(this.persona.getEstado().equals(EstadoPersonaFisica.PASIVO)){
					this.categoria= this.buscarEquivalenciaCategoria(this.empleo.getTipoCategoria());
				}
				else{
					this.categoria= this.empleo.getTipoCategoria();
				}
			
			
			}
			if(this.empleo.getPermanencia()!=null){
				this.permanencia= this.empleo.getPermanencia();
			}
			if(this.empleo.getSituacionRevista()!=null){
				this.situacion= this.empleo.getSituacionRevista();
			}
			if(this.empleo.getEmpresa()!=null){
				this.personaJuridica= this.empleo.getEmpresa();
			}
			if(this.empleo.getFechaInicio()!=null){
				this.fechaInicio= this.empleo.getFechaInicio();
			}
			if(this.empleo.getFechaFin()!=null){
				this.fechaFin= this.empleo.getFechaFin();
			}
			if(this.empleo.getFuncion()!=null){
				this.funcion= this.empleo.getFuncion();
			}
			
		}
	
		
		
	
		this.listaAntiguedad= new ArrayList<Antiguedad>();
		this.listaAntiguedad.add(0, null);
		this.listaAntiguedad.addAll(this.antiguedadDAO.getListaAntiguedad());
		
		this.listaPermanenciaCategorias= new ArrayList<PermanenciaCategoria>();
		this.listaPermanenciaCategorias.add(0, null);
		this.listaPermanenciaCategorias.addAll(this.permanenciaDAO.getListaPermanencia());
		
		
		this.cbxAntiguedadModel = new DefaultComboBoxModel(this.listaAntiguedad.toArray());
		this.cbxPermanenciaModel= new DefaultComboBoxModel(this.listaPermanenciaCategorias.toArray());
		this.cbxAntiguedad.setModel(this.cbxAntiguedadModel);
		this.cbxPermanencia.setModel(this.cbxPermanenciaModel);
	
		DefaultComboBoxModel locComboBoxModel = new DefaultComboBoxModel();
		locComboBoxModel.addElement(null);
		for (SituacionRevista cadaSituacion : SituacionRevista.values()){
			locComboBoxModel.addElement(cadaSituacion);
		}
		this.cbxSituacion.setModel(locComboBoxModel);
		this.cbxSituacion.setRenderer(new SituacionRevistaListCellRenderer());
	
		DefaultComboBoxModel locCategoriaComboBoxModel = new DefaultComboBoxModel();
		locCategoriaComboBoxModel.addElement(null);
		for (Categoria cadaCategoria : this.categoriaDAO.findListaCategoria()){
			locCategoriaComboBoxModel.addElement(cadaCategoria);
		}
		this.cbxCategoria.setModel(locCategoriaComboBoxModel);
		
	}

	
	
	private Categoria buscarEquivalenciaCategoria(Categoria tipoCategoria) {
		return this.categoriaDAO.findEquivalenciaCategoria(tipoCategoria, EstadoPersonaFisica.ACTIVO);
	}


	private void actualizarVista() {
		this.txtPersonaJuridica.setEditable(false);
	
		this.cbxAntiguedad.setModel(this.cbxAntiguedadModel);
		this.cbxPermanencia.setModel(this.cbxPermanenciaModel);
					
		if(this.antiguedad!=null){
			this.cbxAntiguedad.setSelectedItem(this.antiguedad);
		}
		else{
			this.cbxAntiguedad.setSelectedIndex(0);
		}
			
		if(this.categoria!=null){
			this.cbxCategoria.setSelectedItem(this.categoria);
			
		}
		else{
			this.cbxCategoria.setSelectedIndex(0);
		}
			
		if(this.permanencia!=null){
			this.cbxPermanencia.setSelectedItem(this.permanencia);
		}
		else{
			this.cbxPermanencia.setSelectedIndex(0);
		}

		if(this.situacion!=null){
			this.cbxSituacion.setSelectedItem(this.situacion);
		}
		else{
			this.cbxSituacion.setSelectedIndex(0);
		}
		
		if(this.fechaInicio!=null){
			this.dtcFechaInicio.setDate(this.fechaInicio);
		}
		else{
			this.dtcFechaInicio.setDate(null);
		}
		
		if(this.fechaFin!=null){
			this.dtcFechaFin.setDate(this.fechaFin);
		}
		else{
			this.dtcFechaFin.setDate(null);
		}
		
		if(this.funcion!=null){
			this.txtFuncion.setText(this.funcion);
		}
		else{
			this.txtFuncion.setText("");
		}
		
		if(this.personaJuridica!=null){
			this.txtPersonaJuridica.setText(this.personaJuridica.getRazonSocial());
		}
		else{
			this.txtPersonaJuridica.setText("");
		}
		
	}


	private void inicializarEventos() {
		this.btnBuscarPersonaJuridica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarPersonaJuridica();
			}
		});
		
		this.btnBorrarDatosLaborales.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borrarEmpleo();
			}
		});
		
	}

	

	private void borrarEmpleo() {
		boolean empleoLiquidado= false;
	
		if(this.empleo!=null){
			empleoLiquidado= this.adminFactory.getAdministracionLiquidacionHaberes().isEmpleoLiquidado(this.empleo);
		}
	 
		if(!empleoLiquidado){
			this.antiguedad= null;
			this.permanencia= null;
			this.situacion= null;
			this.categoria= null;
			this.fechaInicio= null;
			this.fechaFin= null;
			this.funcion= null;
			this.personaJuridica= null;
			this.actualizarVista();
		}
		else{
			JOptionPane.showMessageDialog(this, "Error. El empleo no se puede eliminar ya que ha sido liquidado.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

	public void actualizarModelo() throws LogicaException {

			this.antiguedad= (Antiguedad) this.cbxAntiguedad.getSelectedItem();
			this.permanencia= (PermanenciaCategoria) this.cbxPermanencia.getSelectedItem();
			this.situacion= (SituacionRevista) this.cbxSituacion.getSelectedItem();
			this.categoria= (Categoria) this.cbxCategoria.getSelectedItem();
			this.fechaInicio= this.dtcFechaInicio.getDate();
			this.fechaFin= this.dtcFechaFin.getDate();
			this.funcion= Utiles.nuloSiVacio(this.txtFuncion.getText());
			
			
			if(this.antiguedad==null && this.permanencia==null && this.situacion==null && this.categoria==null && this.fechaInicio==null && this.fechaFin==null && this.funcion==null && this.personaJuridica==null){
				this.empleo= null;
				this.listaEmpleo= null;
			}
			else{
				
				
				
				if(this.personaJuridica==null){
					int codigo= 135;
					throw new LogicaException(codigo);
				}
				
				if(this.situacion==null){
					int codigo=36;
					String campo= "Error al guardar. Debe seleccionar la situación de revista para el empleo.";
					throw new LogicaException(codigo, campo);
				}
				if(this.situacion.equals(SituacionRevista.PLANTA_PERMANENTE) && this.categoria==null){
					int codigo=136;
					throw new LogicaException(codigo);
				}
				if(this.fechaInicio==null){
					int codigo= 36;
					String campo="Error al intentar guardar. Debe ingresar la fecha de inicio de empleo en datos laborales.";
					throw new LogicaException(codigo, campo);
				}
				
				if(this.empleo==null){
					this.empleo= new Empleo();
				}
				this.empleo.setAntiguedad(this.antiguedad);
				this.empleo.setEmpresa(this.personaJuridica);
				this.empleo.setFechaFin(this.fechaFin);
				this.empleo.setFechaInicio(this.fechaInicio);
				this.empleo.setFuncion(this.funcion);
				this.empleo.setPermanencia(this.permanencia);
				this.empleo.setSituacionRevista(this.situacion);
				this.empleo.setTipoCategoria(this.categoria);
				if(this.listaEmpleo==null){
					this.listaEmpleo= new ArrayList<Empleo>();
				}
				else{
					this.listaEmpleo.clear();
				}
				this.listaEmpleo.add(this.empleo);
			}

	}


	public List<Empleo> getListaEmpleo() {
		return this.listaEmpleo;
	}


	

	
	

	private void buscarPersonaJuridica() {
		if (this.busquedaPersonaJuridica==null){
			this.busquedaPersonaJuridica = new BusquedaPersonaJuridicaController(this.padre);
		}
		this.busquedaPersonaJuridica.setVisible(true);
		
		this.personaJuridica=this.busquedaPersonaJuridica.getPersonaSeleccionada();
	
		if (this.personaJuridica!=null){
			this.txtPersonaJuridica.setText(this.personaJuridica.getRazonSocial().toString());
		}
		else{
			this.txtPersonaJuridica.setText("");
		}
		
	}



	

}
