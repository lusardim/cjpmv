/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlAMDetalleLiquidacionHaberes.java
 *
 * Created on 24/12/2011, 16:12:00
 */
package ar.gov.cjpmv.prestamos.gui.sueldos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.TipoBeneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Beneficio;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Empleo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LineaRecibo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.MementoListaLineaRecibo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ReciboSueldo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.TipoLiquidacion;
import ar.gov.cjpmv.prestamos.gui.AdministracionFactory;
import ar.gov.cjpmv.prestamos.gui.sueldos.model.TblDetallePlantillaModel;
import ar.gov.cjpmv.prestamos.gui.sueldos.model.TblDetalleReciboHaberesModel;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;

/**
 *
 * @author daiana
 */
public class PnlAMDetalleLiquidacionHaberes extends javax.swing.JPanel {

	private JDialog ventana;
	private ReciboSueldo reciboHaberes;
	private MementoListaLineaRecibo mementoLineaRecibo;
	private PnlAMLiquidacionHaberes pnlAMLiquidacionHaberes;
	
	private String apellido;
	private String nombres;
	private Long legajo;
	private Categoria categoria;
	private BigDecimal valorLiqBen;
	private TipoBeneficio tipoBeneficio;
	private AdministracionFactory adminFactory;
	private TblDetalleReciboHaberesModel modeloDetalleRecibo;
	private List<ConceptoHaberes> listaConceptoHaberes;
	
    /** Creates new form PnlAMDetalleLiquidacionHaberes */
	
    public PnlAMDetalleLiquidacionHaberes(PnlAMLiquidacionHaberes pnlAMLiquidacionHaberes, JDialog pVentana, ReciboSueldo pReciboHaberes) {
        initComponents();
        this.reciboHaberes= pReciboHaberes;
        this.pnlAMLiquidacionHaberes= pnlAMLiquidacionHaberes;
        this.mementoLineaRecibo= new MementoListaLineaRecibo(this.reciboHaberes.getLineasRecibo()); 
        
        this.adminFactory= AdministracionFactory.getInstance();
        this.ventana= pVentana;
      
        this.ventana.add(this);
        this.ventana.pack();
        this.inicializarEventos();
        this.inicializarModelo();
        this.inicializarVista();
    }


	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        pnlListaConcepto1 = new ar.gov.cjpmv.prestamos.gui.sueldos.PnlListaConcepto();
        pnlDetalleLiquidacion1 = new ar.gov.cjpmv.prestamos.gui.sueldos.PnlDetalleLiquidacion();
        xtsTitulo = new org.jdesktop.swingx.JXTitledSeparator();
        lblTituloPnlDcha = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ar.gov.cjpmv.prestamos.gui.Principal.class).getContext().getResourceMap(PnlAMDetalleLiquidacionHaberes.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        btnGuardar.setIcon(resourceMap.getIcon("btnGuardar.icon")); // NOI18N
        btnGuardar.setText(resourceMap.getString("btnGuardar.text")); // NOI18N
        btnGuardar.setMaximumSize(new java.awt.Dimension(121, 23));
        btnGuardar.setName("btnGuardar"); // NOI18N
        btnGuardar.setPreferredSize(new java.awt.Dimension(101, 23));

        btnCancelar.setIcon(resourceMap.getIcon("btnCancelar.icon")); // NOI18N
        btnCancelar.setText(resourceMap.getString("btnCancelar.text")); // NOI18N
        btnCancelar.setName("btnCancelar"); // NOI18N

        jSplitPane1.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("jSplitPane1.border.lineColor"), 1, true)); // NOI18N
        jSplitPane1.setMinimumSize(new java.awt.Dimension(657, 114));
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        pnlListaConcepto1.setMinimumSize(new java.awt.Dimension(350, 104));
        pnlListaConcepto1.setName("pnlListaConcepto1"); // NOI18N
        jSplitPane1.setLeftComponent(pnlListaConcepto1);

        pnlDetalleLiquidacion1.setName("pnlDetalleLiquidacion1"); // NOI18N
        jSplitPane1.setRightComponent(pnlDetalleLiquidacion1);

        xtsTitulo.setForeground(resourceMap.getColor("xtsTitulo.foreground")); // NOI18N
        xtsTitulo.setFont(resourceMap.getFont("xtsTitulo.font")); // NOI18N
        xtsTitulo.setName("xtsTitulo"); // NOI18N
        xtsTitulo.setTitle(resourceMap.getString("xtsTitulo.title")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
                            .addComponent(xtsTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xtsTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblTituloPnlDcha.setFont(resourceMap.getFont("lblTituloPnlDcha.font")); // NOI18N
        lblTituloPnlDcha.setForeground(resourceMap.getColor("lblTituloPnlDcha.foreground")); // NOI18N
        lblTituloPnlDcha.setIcon(resourceMap.getIcon("lblTituloPnlDcha.icon")); // NOI18N
        lblTituloPnlDcha.setText(resourceMap.getString("lblTituloPnlDcha.text")); // NOI18N
        lblTituloPnlDcha.setName("lblTituloPnlDcha"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTituloPnlDcha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPnlDcha)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblTituloPnlDcha;
    private ar.gov.cjpmv.prestamos.gui.sueldos.PnlDetalleLiquidacion pnlDetalleLiquidacion1;
    private ar.gov.cjpmv.prestamos.gui.sueldos.PnlListaConcepto pnlListaConcepto1;
    private org.jdesktop.swingx.JXTitledSeparator xtsTitulo;
    // End of variables declaration//GEN-END:variables
	private Categoria cadaLinea;
    
    


	private void inicializarEventos() {
		this.pnlListaConcepto1.getLtConcepto().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (pnlListaConcepto1.getLtConcepto()!=null){
					if ((!pnlListaConcepto1.getLtConcepto().isSelectionEmpty()) && (e.getClickCount()==2)){
						aniadirConceptos();
					}
					else if ((!pnlListaConcepto1.getLtConcepto().isSelectionEmpty()) && (e.getClickCount()==1)){
						pnlListaConcepto1.getBtnAgregar().setEnabled(true);
					}
				}
			}
		});

		this.pnlListaConcepto1.getLtConcepto().addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if (pnlListaConcepto1.getLtConcepto()!=null){
					if((!pnlListaConcepto1.getLtConcepto().isSelectionEmpty()) && e.getKeyCode() == KeyEvent.VK_ENTER){
						aniadirConceptos();
					}
				}
			}
		});
		
		this.pnlListaConcepto1.getBtnAgregar().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				aniadirConceptos();
			}	
		});
		
		this.btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});			
	
		this.pnlDetalleLiquidacion1.getLblQuitar().addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseClicked(MouseEvent e) {
				quitarConcepto();
				
			}
		});
		
		this.btnCancelar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();	
			}
		});

		this.pnlDetalleLiquidacion1.getTblDetalle().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0){
				if(isSelectedRowConcepto()){
			    	pnlDetalleLiquidacion1.getLblQuitar().setEnabled(true);
				}
			}
		});
	}
    
    
	private boolean isSelectedRowConcepto(){
		return this.pnlDetalleLiquidacion1.getTblDetalle().getSelectedRow() != -1;
	}
	
	public void actualizarVistaTotales(){
	  	this.pnlDetalleLiquidacion1.getTxtTotalDescuento().setText(this.modeloDetalleRecibo.getDescuento().toString());
    	this.pnlDetalleLiquidacion1.getTxtTotalHaberes().setText(this.modeloDetalleRecibo.getHaberes().toString());
    	this.pnlDetalleLiquidacion1.getTxtTotalNeto().setText(this.modeloDetalleRecibo.getNeto().toString());
	}
	

    private void inicializarVista() {
    	
    	this.pnlDetalleLiquidacion1.getTxtApellido().setText(Utiles.vacioSiNulo(this.apellido));
    	if(this.categoria!=null){
    		this.pnlDetalleLiquidacion1.getTxtCategoria().setText(Utiles.vacioSiNulo(this.categoria.toString()));
    	}
    	else{
    		this.pnlDetalleLiquidacion1.getTxtCategoria().setText("");
    	}
    	this.pnlDetalleLiquidacion1.getTxtLegajo().setText(Utiles.vacioSiNulo(this.legajo.toString()));
    	this.pnlDetalleLiquidacion1.getTxtNombres().setText(Utiles.vacioSiNulo(this.nombres));

    	if(this.tipoBeneficio!=null){
    		this.pnlDetalleLiquidacion1.getTxtValorLiq().setVisible(true);
    		this.pnlDetalleLiquidacion1.getLblValorLiq().setVisible(true);
    	 	this.pnlDetalleLiquidacion1.getTxtValorLiq().setText(Utiles.vacioSiNulo(this.valorLiqBen));
    	 
    	}
    	else{
    		this.pnlDetalleLiquidacion1.getLblValorLiq().setVisible(false);
    		this.pnlDetalleLiquidacion1.getTxtValorLiq().setVisible(false);
    	}
  
    	this.pnlDetalleLiquidacion1.getTxtTotalDescuento().setText(this.modeloDetalleRecibo.getDescuento().toString());
    	this.pnlDetalleLiquidacion1.getTxtTotalHaberes().setText(this.modeloDetalleRecibo.getHaberes().toString());
    	this.pnlDetalleLiquidacion1.getTxtTotalNeto().setText(this.modeloDetalleRecibo.getNeto().toString());
    	
    	this.pnlDetalleLiquidacion1.getTxtApellido().setEnabled(false);
    	this.pnlDetalleLiquidacion1.getTxtCategoria().setEnabled(false);
    	this.pnlDetalleLiquidacion1.getTxtLegajo().setEnabled(false);
    	this.pnlDetalleLiquidacion1.getTxtNombres().setEnabled(false);
    	this.pnlDetalleLiquidacion1.getTxtValorLiq().setEnabled(false);
    	this.pnlDetalleLiquidacion1.getTxtTotalDescuento().setEnabled(false);
    	this.pnlDetalleLiquidacion1.getTxtTotalHaberes().setEnabled(false);
    	this.pnlDetalleLiquidacion1.getTxtTotalNeto().setEnabled(false);
    	
    	this.pnlDetalleLiquidacion1.getTblDetalle().setModel(this.modeloDetalleRecibo);
    	this.pnlDetalleLiquidacion1.getTblDetalle().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	this.pnlDetalleLiquidacion1.getLblQuitar().setEnabled(false);
    	
    	if(this.listaConceptoHaberes!=null){
			this.pnlListaConcepto1.getLtConcepto().setListData(this.listaConceptoHaberes.toArray());
		}
		this.pnlListaConcepto1.getBtnAgregar().setEnabled(false);
	}

	private void inicializarModelo() {
		this.apellido= this.reciboHaberes.getPersona().getApellido();
		this.nombres= this.reciboHaberes.getPersona().getNombre();
		
		
		this.legajo= this.reciboHaberes.getPersona().getLegajo();
		
		this.listaConceptoHaberes= this.adminFactory.getAdministracionConceptoHaberes().getConceptos();		
		
		TipoLiquidacion tipo= this.reciboHaberes.getLiquidacion().getTipo();
		
		if(tipo.equals(TipoLiquidacion.ACTIVO_NORMAL) || tipo.equals(TipoLiquidacion.ACTIVO_SAC)){
			
		
			this.valorLiqBen=null;
			ConceptoHaberes concNomJub= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(4);
			ConceptoHaberes concNomPens= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(5);
			this.listaConceptoHaberes.remove(concNomJub);
			this.listaConceptoHaberes.remove(concNomPens);
			
			if(	this.reciboHaberes.getPersona().getListaEmpleos()!=null && !this.reciboHaberes.getLineasRecibo().isEmpty()){
				Empleo locEmpleo=  this.reciboHaberes.getPersona().getListaEmpleos().get(0);
				this.categoria= locEmpleo.getTipoCategoria();
			}
			else{
				this.categoria= null;
			}
		}
		else if(tipo.equals(TipoLiquidacion.JUBILACION_NORMAL) || tipo.equals(TipoLiquidacion.JUBILACION_SAC)){
			

			List<Beneficio> listaBeneficio = this.reciboHaberes.getPersona().getListaBeneficios();
			for(Beneficio cadaBeneficio: listaBeneficio){
				if(!cadaBeneficio.getTipoBeneficio().equals(TipoBeneficio.PENSION)){
					this.valorLiqBen=cadaBeneficio.getValor();
					this.tipoBeneficio= cadaBeneficio.getTipoBeneficio();
					if(	this.reciboHaberes.getPersona().getListaEmpleos()!=null && !this.reciboHaberes.getLineasRecibo().isEmpty()){
						Empleo locEmpleo=  this.reciboHaberes.getPersona().getListaEmpleos().get(0);
						this.categoria= locEmpleo.getTipoCategoria();
					}
					else{
						this.categoria= null;
					}
				}
			}
			ConceptoHaberes concBasico= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(1);
			ConceptoHaberes concNomPens= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(5);
			ConceptoHaberes concPermanencia= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(2);
			ConceptoHaberes concAntiguedad= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(3);
			this.listaConceptoHaberes.remove(concBasico);
			this.listaConceptoHaberes.remove(concNomPens);
			this.listaConceptoHaberes.remove(concAntiguedad);
			this.listaConceptoHaberes.remove(concPermanencia);
		}
		else if(tipo.equals(TipoLiquidacion.PENSION_NORMAL) || (tipo.equals(TipoLiquidacion.PENSION_SAC))){
		
			List<Beneficio> listaBeneficio = this.reciboHaberes.getPersona().getListaBeneficios();
	
			for(Beneficio cadaBeneficio: listaBeneficio){
				if(cadaBeneficio.getTipoBeneficio().equals(TipoBeneficio.PENSION)){
					this.valorLiqBen=cadaBeneficio.getValor();
					this.tipoBeneficio= cadaBeneficio.getTipoBeneficio();
					this.categoria= null;
				}
			}
	
			ConceptoHaberes concBasico= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(1);
			ConceptoHaberes concNomJub= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(4);
			ConceptoHaberes concPermanencia= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(2);
			ConceptoHaberes concAntiguedad= this.adminFactory.getAdministracionConceptoHaberes().getConceptoPorCodigo(3);
			this.listaConceptoHaberes.remove(concBasico);
			this.listaConceptoHaberes.remove(concNomJub);
			this.listaConceptoHaberes.remove(concAntiguedad);
			this.listaConceptoHaberes.remove(concPermanencia);
		}
		
		this.modeloDetalleRecibo= new TblDetalleReciboHaberesModel(this, this.reciboHaberes.getLineasRecibo());
		this.modeloDetalleRecibo.calcularTotales();
		

	
		
		for(LineaRecibo cadaLinea: this.reciboHaberes.getLineasRecibo()){
			ConceptoHaberes locConcepto= cadaLinea.getConcepto();
			if(this.listaConceptoHaberes.contains(locConcepto)){
				this.listaConceptoHaberes.remove(locConcepto);
			}
		}
		
	}
	
	private void aniadirConceptos() {
		if(!this.pnlListaConcepto1.getLtConcepto().isSelectionEmpty()){
			Object[] listaSeleccionados = this.pnlListaConcepto1.getLtConcepto().getSelectedValues();
			for(Object cadaObjeto: listaSeleccionados){
				this.listaConceptoHaberes.remove(cadaObjeto);
				ConceptoHaberes locConcepto= (ConceptoHaberes) cadaObjeto;
				LineaRecibo locLinea= new LineaRecibo();
				locLinea.setCantidad(1);
				locLinea.setConcepto(locConcepto);
				this.reciboHaberes.add(locLinea);
			}
			
			this.modeloDetalleRecibo= new TblDetalleReciboHaberesModel(this, this.reciboHaberes.getLineasRecibo());
			this.pnlDetalleLiquidacion1.getTblDetalle().setModel(this.modeloDetalleRecibo);
			this.modeloDetalleRecibo.calcularTotales();
		   	this.pnlDetalleLiquidacion1.getTxtTotalDescuento().setText(this.modeloDetalleRecibo.getDescuento().toString());
	    	this.pnlDetalleLiquidacion1.getTxtTotalHaberes().setText(this.modeloDetalleRecibo.getHaberes().toString());
	    	this.pnlDetalleLiquidacion1.getTxtTotalNeto().setText(this.modeloDetalleRecibo.getNeto().toString());
	    	this.pnlListaConcepto1.getLtConcepto().setListData(this.listaConceptoHaberes.toArray());
		
		}
		else{
			String titulo= "Error";
			String mensaje="Debe seleccionar algún concepto de haberes para agregar.";
			JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
		}
	
	}
	

	private void quitarConcepto() {
		int lineaSeleccionada = this.pnlDetalleLiquidacion1.getTblDetalle().getSelectedRow();
		LineaRecibo linea= this.modeloDetalleRecibo.getLineaRecibo(lineaSeleccionada);
		this.reciboHaberes.remove(linea);
		this.listaConceptoHaberes.add(linea.getConcepto());
		this.pnlListaConcepto1.getLtConcepto().setListData(this.listaConceptoHaberes.toArray());
		this.modeloDetalleRecibo= new TblDetalleReciboHaberesModel(this, this.reciboHaberes.getLineasRecibo());
		this.modeloDetalleRecibo.calcularTotales();
	 

		this.pnlDetalleLiquidacion1.getTblDetalle().setModel(this.modeloDetalleRecibo);
		this.pnlDetalleLiquidacion1.getLblQuitar().setEnabled(false);
	  	this.pnlDetalleLiquidacion1.getTxtTotalDescuento().setText(this.modeloDetalleRecibo.getDescuento().toString());
    	this.pnlDetalleLiquidacion1.getTxtTotalHaberes().setText(this.modeloDetalleRecibo.getHaberes().toString());
    	this.pnlDetalleLiquidacion1.getTxtTotalNeto().setText(this.modeloDetalleRecibo.getNeto().toString());
    	
	   	this.pnlDetalleLiquidacion1.getTxtTotalDescuento().setText(this.modeloDetalleRecibo.getDescuento().toString());
    	this.pnlDetalleLiquidacion1.getTxtTotalHaberes().setText(this.modeloDetalleRecibo.getHaberes().toString());
    	this.pnlDetalleLiquidacion1.getTxtTotalNeto().setText(this.modeloDetalleRecibo.getNeto().toString());
    	
	}

	private void guardar() {
		try{
			this.validarModelo();
			String mensaje="Los datos se han guardado correctamente";
			String titulo="Edición de Recibo de Haberes";
			JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
			ventana.dispose();
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
		}
	}
	

	private void cancelar() {
		String locMensaje="¿Realmente desea cancelar la operación?";
		String locTitulo="Confirmación";
		int confirmacion=JOptionPane.showConfirmDialog(this,locMensaje, locTitulo, JOptionPane.YES_OPTION);
		if(confirmacion==JOptionPane.YES_OPTION){
			this.reciboHaberes.restoreFromMemento(mementoLineaRecibo);
			ventana.dispose();
		}
	}
	
	private void validarModelo() throws LogicaException {
		this.modeloDetalleRecibo.calcularTotales();
		BigDecimal valor = new BigDecimal("0.00");
		if(this.reciboHaberes.getLineasRecibo().isEmpty()){
			int codigo= 36;
			String campo="Error al guardar el recibo de haberes. Debe ingresar al menos un concepto en el detalle.";
			throw new LogicaException(codigo, campo);
		}
		else{
			for(LineaRecibo cadaLinea:this.reciboHaberes.getLineasRecibo()){
				if(cadaLinea.getMonto()==null){
					int codigo= 139;
					String campo="monto en el concepto "+cadaLinea.getConcepto().getCodigo()+" - "+cadaLinea.getConcepto().getDescripcion();
					throw new LogicaException(codigo, campo);
				}
				else if(cadaLinea.getMonto().compareTo(valor)<=0){
					int codigo= 36;
					String campo="Error al guardar el recibo de haberes. El concepto "+cadaLinea.getConcepto().getCodigo()+" - "+cadaLinea.getConcepto().getDescripcion()+
							"\ndebe tener un monto superior a cero.";
					throw new LogicaException(codigo, campo);
				}
			}
		}
		
		if(this.modeloDetalleRecibo.getNeto().compareTo(valor)<0){
			int codigo= 36;
			String campo="Error al guardar el recibo de haberes. El total neto debe ser superior a cero.";
			throw new LogicaException(codigo, campo);
		}
		
	}



}