package ar.gov.cjpmv.prestamos.gui.creditos.controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroPorBanco;
import ar.gov.cjpmv.prestamos.gui.comunes.PnlDchaBusquedaView;
import ar.gov.cjpmv.prestamos.gui.creditos.AdminCreditoView;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlAMCreditoFinalizacionView;
import ar.gov.cjpmv.prestamos.gui.creditos.PnlAMCreditoView;
import ar.gov.cjpmv.prestamos.gui.creditos.cheques.PnlAdministracionChequesController;
import ar.gov.cjpmv.prestamos.gui.creditos.cobros.porHaberesCaja.PnlCobroPorHaberesCaja;
import ar.gov.cjpmv.prestamos.gui.creditos.cobros.porHaberesCaja.PnlCobroPorHaberesCajaControllers;
import ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.PnlCobroPorBanco;
import ar.gov.cjpmv.prestamos.gui.creditos.cobros.porbanco.controllers.AdminCobroPorBancoControllers;
import ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.PnlCreditosFinalizacion;
import ar.gov.cjpmv.prestamos.gui.creditos.exportacion.PnlCuotasExportacionMunicipalidad;
import ar.gov.cjpmv.prestamos.gui.creditos.exportacion.PnlExportacionMuniController;
import ar.gov.cjpmv.prestamos.gui.creditos.garantias.PnlAfectarDesafectarGarantes;
import ar.gov.cjpmv.prestamos.gui.creditos.garantias.PnlAfectarGarantesControllers;
import ar.gov.cjpmv.prestamos.gui.creditos.importacion.PnlImportacionMunicipalidad;
import ar.gov.cjpmv.prestamos.gui.creditos.importacion.PnlImportacionMunicipalidadController;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.CobroPorBancoController;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlActivoCorrienteNoCorrienteControllers;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlCapitalAdeudado;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlCapitalAdeudadoControllers;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlCobranzas;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlCobranzasControllers;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlCreditosOtorgadosPorPeriodo;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlDchaCobroBancario;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlDchaFormularioCredito;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlDchaOtorgadosPorPeriodo;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlDchaPorPeriodo;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlDchaReporteSaldo;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlFormularioCreditoControllers;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlFormulariosCredito;
import ar.gov.cjpmv.prestamos.gui.creditos.reportes.PnlReporteSaldoControllers;
import ar.gov.cjpmv.prestamos.gui.creditos.viaCobro.PnlViaCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.viaCobro.PnlViaCobroControllers;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

public class AdminCredito {
	private AdminCreditoView vista;
	private AMCreditoController amCreditoController;
	private PnlConsultaEstadoCuentaController consultaEstadoCuentaController;
	private AdminCobroPorBancoControllers adminCobroPorBancoController;
	private PnlExportacionMuniController pnlExportacionMuniController;
	private PnlImportacionMunicipalidadController pnlImportacionMunicipalidadController;
	private PnlCobroPorHaberesCajaControllers pnlCobroPorHaberesCaja;
	private PnlAfectarGarantesControllers pnlAfectarGarantesControllers;
	private PnlAdministracionChequesController pnlAdministrarAdministracionChequesController;
	private PnlViaCobroControllers pnlViaCobroControllers;
	private PnlReporteSaldoControllers pnlReporteSaldoController;
	private AMCreditoControllerPorFinalizacion creditoFinalizacionController;
	private PnlActivoCorrienteNoCorrienteControllers pnlActivoCorrienteNoCorrienteControllers;
	private PnlFormularioCreditoControllers pnlFormularioCreditoControllers;
	private PnlCreditosOtorgadosPorPeriodo pnlCreditosOtorgadoasPorPeriodo;
	private PnlCobranzasControllers pnlCobranzasControllers;
	private PnlCapitalAdeudadoControllers pnlCapitalAdeudadoControllers;
	private CobroPorBancoController cobroPorBancoController;
	
	public AdminCredito(JFrame pPadre) {
		vista = new AdminCreditoView(pPadre, false);
		this.inicializarVista();
		this.inicializarEventos();
	}

	public AMCreditoControllerPorFinalizacion getCreditoFinalizacionController() {
		return creditoFinalizacionController;
	}

	public void setCreditoFinalizacionController(
			AMCreditoControllerPorFinalizacion creditoFinalizacionController) {
		this.creditoFinalizacionController = creditoFinalizacionController;
	}
 
	private void inicializarEventos() {
		this.vista.getPnlBotoneraCreditosView().getXhNuevoCredito().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mostrarAgregarCredito();
			}
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhCheques().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mostrarAdministracionCheques();
			}
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhPorDepositoBancario().addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
                 mostrarPorDepositoBancario();
             }
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhLiquidacionMunicipalidad().addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
                 mostrarLiquidacionMunicipalidad();
             }
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhViaCobro().addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
                mostrarViaCobro();
            }
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhPorMunicipalidad().addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e) {
                 mostrarImportacionMunicipalidad();
             }
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhPorLiquidacionDeHaberes().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
                mostrarCobroPorLiquidacionHaberes();
            }
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhEstadoCuenta().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
                mostrarPnlEstadoCuenta();
            }
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhAfectarGarantes().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
                mostrarAfectarDesafectarGarantes();
            }
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhReportesSaldoTipoCredito().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
                mostrarReporteSaldoPorTipoCredito(Reportes.SALDO_TIPO_CREDITO);
            }
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhDeudoresMorosos().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				mostrarReporteSaldoPorTipoCredito(Reportes.SALDO_DETALLADO_TIPO_CREDITO);
			}
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhSaldoPorPeriodo().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				mostrarReporteSaldoPorPeriodo(Reportes.SALDO_POR_PERIODO);
			}
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhNuevoCreditoPorCancelacion().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarCreditoPorFinalizacion();
			}
		}); 
		
		this.vista.getPnlBotoneraCreditosView().getXhFormularioCredito().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				mostrarFormularioCredito();
			}
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhCreditosOtorgadosPorPeriodo().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				mostrarCreditosOtorgagadosPorPeriodo(Reportes.CREDITOS_OTORGADOS_POR_PERIODO);
			}

		});
		
		this.vista.getPnlBotoneraCreditosView().getXhCobranzas().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				mostrarCobranzas();
			}
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhCapital().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				mostrarCapitalAdeudado();
			}
		});
		
		this.vista.getPnlBotoneraCreditosView().getXhCobroPorBanco().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				mostrarCobrosPorBanco();
			}

		});
		
	}
	
	private void mostrarCreditoPorFinalizacion() {
		AMCreditoControllerPorFinalizacion locController = this.getAMCreditoControllerPorFinalizacion();
		
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlAMCreditoFinalizacionView)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}
	


	private AMCreditoControllerPorFinalizacion getAMCreditoControllerPorFinalizacion() {
		if (creditoFinalizacionController == null) {
			creditoFinalizacionController = new AMCreditoControllerPorFinalizacion(this);
		}
		return this.creditoFinalizacionController;
	}

	public void mostrarPnlEstadoCuenta(){
		this.vista.getSpPrincipal()
			.setRightComponent(getConsultaEstadoCuentaController().getVista());
	}
	
	//TODO ver como hacer para no obtener derecho el SpPrincipal.getRightComponent
	// así podemos meterle un ScrollPane intermedio 
	private void mostrarAgregarCredito() {
		AMCreditoController locController = this.getAMCreditoController();
		if (! ( this.vista.getSpPrincipal().getRightComponent().getClass().equals(PnlAMCreditoView.class))){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}
	
	

	private void mostrarCobranzas() {
		PnlCobranzasControllers locController = this.getPnlCobranzasControllers();
		
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlCobranzas)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
		
	}
	

	private void mostrarCobrosPorBanco() {
		CobroPorBancoController locController = this.getCobroPorBanco();
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlDchaCobroBancario)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
		
	}


	private void mostrarCapitalAdeudado() {
		PnlCapitalAdeudadoControllers locController = this.getPnlCapitalControllers();
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlCapitalAdeudado)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}


	private void mostrarAdministracionCheques() {
		PnlDchaBusquedaView pnlDerecha = new PnlDchaBusquedaView();
		PnlAdministracionChequesController locController = new PnlAdministracionChequesController(vista);
		pnlDerecha.setPnlBusquedaDcha(locController.getVista());
		pnlDerecha.getLblTituloPnlDcha().setText("Administración de cheques");
		this.vista.getSpPrincipal().setRightComponent(pnlDerecha);
		this.vista.repaint();
	}
	
	private void mostrarPorDepositoBancario() {
		AdminCobroPorBancoControllers locController = this.getAdminCobroPorBancoControllers();
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlCobroPorBanco)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}
	
	

	private void mostrarFormularioCredito() {
		PnlFormularioCreditoControllers locController = this.getPnlFormularioCreditoControllers();
		this.vista.getSpPrincipal().setRightComponent(null);
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlDchaFormularioCredito)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}
	

	private void mostrarCreditosOtorgagadosPorPeriodo(Reportes pReporte) {
		PnlCreditosOtorgadosPorPeriodo locController= this.getPnlCreditosOtorgadosPorPeriodoControllers(pReporte);
		this.vista.getSpPrincipal().setRightComponent(null);
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlDchaOtorgadosPorPeriodo)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}
	


	private void mostrarReporteSaldoPorTipoCredito(Reportes pReporte) {
		PnlReporteSaldoControllers locController = this.getPnlReportesSaldoControllers(pReporte);
		this.vista.getSpPrincipal().setRightComponent(null);
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlDchaReporteSaldo)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}
	
	

	private void mostrarReporteSaldoPorPeriodo(Reportes pReporte) {
		PnlActivoCorrienteNoCorrienteControllers locController= this.getPnlActivoCorrienteNoCorrienteControllers(pReporte);
		this.vista.getSpPrincipal().setRightComponent(null);
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlDchaPorPeriodo)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
		
	}

	private void mostrarViaCobro() {
		PnlViaCobroControllers locController = this.getPnlViaCobroControllers();
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlViaCobro)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}
	
	private void mostrarLiquidacionMunicipalidad() {
		PnlExportacionMuniController locController = this.getPnlExportacionMuniControllers();
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlCuotasExportacionMunicipalidad)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}
	
	

	private void mostrarAfectarDesafectarGarantes() {
		PnlAfectarGarantesControllers locController = this.getPnlAfectarGarantesController();
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlAfectarDesafectarGarantes)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
		
	}

	private void mostrarImportacionMunicipalidad() {
		PnlImportacionMunicipalidadController locController = this.getPnlImportacionMunicipalidadController();
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlImportacionMunicipalidad)){
			this.vista.getSpPrincipal().setRightComponent(locController.getVista());
			this.vista.repaint();
		}
	}
	
	private void mostrarCobroPorLiquidacionHaberes() {
		PnlCobroPorHaberesCajaControllers locControllers = this.getPnlCobroPorHaberesController();
		if (! ( this.vista.getSpPrincipal().getRightComponent() instanceof PnlCobroPorHaberesCaja)){
			this.vista.getSpPrincipal().setRightComponent(locControllers.getVista());
			this.vista.repaint();
		}
		
	}
	
	private AMCreditoController getAMCreditoController() {
		if (this.amCreditoController == null ){
			this.amCreditoController =  new AMCreditoController(this);
		}
		return this.amCreditoController;
	}
	
	private AdminCobroPorBancoControllers getAdminCobroPorBancoControllers() {
	//	if (this.adminCobroPorBancoController == null ){
		this.adminCobroPorBancoController =  new AdminCobroPorBancoControllers(this);
	//	}
		return this.adminCobroPorBancoController;
	}
	
	private PnlViaCobroControllers getPnlViaCobroControllers() {
		this.pnlViaCobroControllers =  new PnlViaCobroControllers(this);
		return this.pnlViaCobroControllers;
	}
	
	private PnlCreditosOtorgadosPorPeriodo getPnlCreditosOtorgadosPorPeriodoControllers(Reportes pReporte) {
		this.pnlCreditosOtorgadoasPorPeriodo = new PnlCreditosOtorgadosPorPeriodo(this, pReporte);
		return this.pnlCreditosOtorgadoasPorPeriodo;
	}

	private PnlCapitalAdeudadoControllers getPnlCapitalControllers() {
		this.pnlCapitalAdeudadoControllers= new PnlCapitalAdeudadoControllers(this);
		return this.pnlCapitalAdeudadoControllers;
	}

	private CobroPorBancoController getCobroPorBanco() {
		this.cobroPorBancoController= new CobroPorBancoController(this);
		return this.cobroPorBancoController;
	}

	
	private PnlCobranzasControllers getPnlCobranzasControllers() {
		this.pnlCobranzasControllers = new PnlCobranzasControllers(this);
		return this.pnlCobranzasControllers;
	}
	
	private PnlActivoCorrienteNoCorrienteControllers getPnlActivoCorrienteNoCorrienteControllers(Reportes pReporte){
		this.pnlActivoCorrienteNoCorrienteControllers = new PnlActivoCorrienteNoCorrienteControllers(this, pReporte);
		return this.pnlActivoCorrienteNoCorrienteControllers;
	}
	
	private PnlReporteSaldoControllers getPnlReportesSaldoControllers(Reportes pReporte) {
		this.pnlReporteSaldoController =  new PnlReporteSaldoControllers(this, pReporte);
		return this.pnlReporteSaldoController;
	}
	
	private PnlFormularioCreditoControllers getPnlFormularioCreditoControllers() {
		this.pnlFormularioCreditoControllers =  new PnlFormularioCreditoControllers(this);
		return this.pnlFormularioCreditoControllers;
	}
	
	private PnlExportacionMuniController getPnlExportacionMuniControllers() {
		this.pnlExportacionMuniController =  new PnlExportacionMuniController(this);
		return this.pnlExportacionMuniController;
	}
	
	private PnlImportacionMunicipalidadController getPnlImportacionMunicipalidadController(){
		this.pnlImportacionMunicipalidadController =  new PnlImportacionMunicipalidadController(this);
		return this.pnlImportacionMunicipalidadController;
	}
	
	private PnlAfectarGarantesControllers getPnlAfectarGarantesController(){
		this.pnlAfectarGarantesControllers =  new PnlAfectarGarantesControllers(this);
		return this.pnlAfectarGarantesControllers;
	}
	
		
	private PnlCobroPorHaberesCajaControllers getPnlCobroPorHaberesController(){
		this.pnlCobroPorHaberesCaja=  new PnlCobroPorHaberesCajaControllers(this);
		return this.pnlCobroPorHaberesCaja;
	}
	
	
	public PnlConsultaEstadoCuentaController getConsultaEstadoCuentaController() {
		if (consultaEstadoCuentaController == null){
			consultaEstadoCuentaController = new PnlConsultaEstadoCuentaController();
		}
		return consultaEstadoCuentaController;
	}
	private void inicializarVista() {
		mostrarPnlEstadoCuenta();
	}

	void setAmCreditoController(AMCreditoController amCreditoController) {
		this.amCreditoController = amCreditoController;
	}
	
	public void show() {
		Dimension pantalla=Toolkit.getDefaultToolkit().getScreenSize();
		pantalla.height-=30;
        this.vista.setPreferredSize(pantalla);
        this.vista.pack();
        this.vista.setVisible(true);
	}
	
	public AdminCreditoView getVista() {
		return vista;
	}
	
	public static void main(String[] args){
		JFrame locFrame = new JFrame();
		AdminCredito locAdminCredito = new AdminCredito(locFrame);
		locAdminCredito.vista.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		locAdminCredito.show();
	}

}
