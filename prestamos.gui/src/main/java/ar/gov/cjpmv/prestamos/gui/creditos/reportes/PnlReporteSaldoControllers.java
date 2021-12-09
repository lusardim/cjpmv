package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.TipoCreditoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionCuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

public class PnlReporteSaldoControllers {
	
	// No hay que cambiar el orden, se pueden agregar
	private static String[] tiposOrdenamiento = {"Legajo","Apellido","Número"};
	
	private AdminCredito adminCredito;
	private PnlDchaReporteSaldo vista;
	private ComboBoxModel modeloTipoCredito;
	private ComboBoxModel modeloViaCobro;
	private TipoCreditoDAO tipoCreditoDAO;
	private TipoCredito tipoCredito;
	private ViaCobro viaCobro;
	private Date fecha;
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private NumberFormat formateadorDecimal= NumberFormat.getNumberInstance();
	private Reportes tipoReporte;
	private boolean soloSeguros;
	
	private BigDecimal MTCapital;
	private BigDecimal MTInteres;
	private BigDecimal MTOtrosConceptos;
	private BigDecimal MTSaldo;
	private Integer cantidadCreditos;
	private String ordenamiento = tiposOrdenamiento[0];
	

	public PnlReporteSaldoControllers(AdminCredito adminCredito, Reportes pReporte) {
		super();
		
		this.adminCredito = adminCredito;
		this.tipoReporte= pReporte;
		this.tipoCreditoDAO= new TipoCreditoDAO();
		vista= new PnlDchaReporteSaldo();
		this.inicializarEventos();
		this.inicializarVista();
		this.formateadorDecimal.setMinimumFractionDigits(2);
		this.formateadorDecimal.setMaximumFractionDigits(2);
		
	}

	private void inicializarVista() {
		//FIXME QUITAR DESPUES DE ARREGLAR EL SEGURO
		//Sacar despues de acomodar el seguro de vida
		this.vista.getPnlReporteSaldoCredito().getCkbSoloCreditos().setVisible(true);
		
		Calendar c1 = Calendar.getInstance();
		Date fecha= c1.getTime();
		if(this.tipoReporte.equals(Reportes.SALDO_TIPO_CREDITO)) {
			this.vista.getLblTituloPnlDcha().setText("Reporte: Saldo por Tipo de Crédito");
			this.vista.getPnlReporteSaldoCredito().getLblViaCobro().setVisible(false);
			this.vista.getPnlReporteSaldoCredito().getCbxViaCobro().setVisible(false);
			this.vista.getPnlReporteSaldoCredito().getCboOrden().setVisible(false);
			this.vista.getPnlReporteSaldoCredito().getLblOrden().setVisible(false);
			this.vista.getPnlReporteSaldoCredito().getCkbSoloCreditos().setVisible(false);
		}
		else if (this.tipoReporte.equals(Reportes.SALDO_DETALLADO_TIPO_CREDITO)) {
			this.vista.getLblTituloPnlDcha().setText("Reporte: Saldo Detallado por Tipo de Crédito");
			CreditoDAOImpl creditoDAO= new CreditoDAOImpl();
			List<ViaCobro> listaViaCobro= new ArrayList<ViaCobro>();
			listaViaCobro.add(0, null);
			listaViaCobro.addAll(creditoDAO.getListaViasCobro());
			this.modeloViaCobro= new DefaultComboBoxModel(listaViaCobro.toArray());
			this.vista.getPnlReporteSaldoCredito().getCbxViaCobro().setModel(this.modeloViaCobro);
		}
		this.vista.getPnlReporteSaldoCredito().getDtcFecha().setDate(fecha);
		this.vista.getBtnCancelar().setVisible(false);
		this.vista.getBtnImprimir().setVisible(false);
		this.vista.getBtnAceptarGuardar().setText("Finalizar");
		this.modeloTipoCredito = new DefaultComboBoxModel(tipoCreditoDAO.findListaTipoCredito(null).toArray());
		this.vista.getPnlReporteSaldoCredito().getCbxTipoCredito().setModel(this.modeloTipoCredito);
		
		ComboBoxModel ordenamiento = new DefaultComboBoxModel(tiposOrdenamiento);
		ordenamiento.setSelectedItem(this.ordenamiento);
		this.vista.getPnlReporteSaldoCredito()
		  .getCboOrden()
		  .setModel(ordenamiento);
	}


	private void inicializarEventos() {
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finalizar();
			}
		});
		
		this.vista.getPnlReporteSaldoCredito().getBtnImprimir().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				imprimir();
			}
		});
		
	}
	
	private void imprimir() {
		this.actualizarModelo();
		try{
			if((this.tipoCredito==null)||(this.fecha==null)){
				int codigo= 91;
				String campo="";
				throw new LogicaException(codigo, campo);
			}
			if(this.tipoReporte.equals(Reportes.SALDO_TIPO_CREDITO)){
				this.generarReporteSimple();
			}
			else{
				this.generarReporteDetallado();
			}
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this.vista, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	
	private void generarReporteSimple() {
		try{
			List<ReporteSaldo> listaSaldoReporte = this.formatearReporteSaldo();
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), Reportes.SALDO_TIPO_CREDITO, this.setearParametros(), listaSaldoReporte);
		
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void generarReporteDetallado() {
		try{
			BigDecimal cero = new BigDecimal(0);
			MTCapital=cero;
			MTInteres=cero;
			MTOtrosConceptos=cero;
			MTSaldo=cero;
			cantidadCreditos=0;
			List<ReporteSaldoDetallado> listaSaldoReporteDetallado = this.formatearReporteSaldoDetallado();
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), Reportes.SALDO_DETALLADO_TIPO_CREDITO, this.setearParametrosDetallado(), listaSaldoReporteDetallado);
		
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	

	public Map<String, String> setearParametros(){
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("tipoCredito", this.tipoCredito.getNombre().toUpperCase());
		parametros.put("fechaCalculo",formateadorFecha.format(this.fecha));
		return parametros;
	}

	public Map<String, String> setearParametrosDetallado(){
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("tipoCredito", this.tipoCredito.getNombre().toUpperCase());
		parametros.put("fechaCalculo",formateadorFecha.format(this.fecha));
		parametros.put("totalCapital", this.formateadorMonetario.format(MTCapital));
		parametros.put("totalInteres", this.formateadorMonetario.format(MTInteres));
		parametros.put("totalOtrosConceptos", this.formateadorMonetario.format(MTOtrosConceptos));
		parametros.put("totalMonto", this.formateadorMonetario.format(MTSaldo));
		parametros.put("totalCreditos", this.cantidadCreditos.toString());
		String locViaCobro=null;
		if(this.viaCobro!=null){
			locViaCobro="Vía de Cobro: "+this.viaCobro.getNombre();
		}
		parametros.put("viaCobro", locViaCobro);
		return parametros;
	}


	private List<ReporteSaldo> formatearReporteSaldo() {
		CreditoDAOImpl creditoDAO= new CreditoDAOImpl();
		
		List<ViaCobro> listaViaCobro=creditoDAO.getListaViasCobro();
		List<ReporteSaldo> locReporte = new ArrayList<ReporteSaldo>();
		
		Integer cantidadTotalCreditos=0;
		BigDecimal sumaSaldoTotal=new BigDecimal(0);
			
		for(ViaCobro cadaViaCobro: listaViaCobro){
			//Recupero Creditos por Tipo y Via Cobro
			
			List<Credito> listaCreditos;
			listaCreditos = creditoDAO.getCreditosPorTipoYViaEnCurso(this.tipoCredito, cadaViaCobro, false, this.fecha);
			
			String viaCobro=cadaViaCobro.getNombre().toUpperCase();
			Integer cantidadCreditos=0;
			BigDecimal saldoTotalAdeudado = new BigDecimal(0);
			if (listaCreditos != null) {
				for(Credito cadaCredito: listaCreditos){
					if(cadaCredito.getCuotasAdeudadas(this.fecha)!=0){
						cantidadCreditos+= 1;
						for(Cuota cadaCuota: cadaCredito.getListaCuotas()){
							if(cadaCuota.getSituacion(this.fecha).equals(SituacionCuota.VENCIDA)){
								saldoTotalAdeudado = saldoTotalAdeudado.add(cadaCuota.getTotal());
							}
							else{
								if(cadaCuota.getSituacion(this.fecha).equals(SituacionCuota.NO_VENCIDA)){
									saldoTotalAdeudado = saldoTotalAdeudado.add(cadaCuota.getCapital());
								}	
							}
						}
					}
				}	
				cantidadTotalCreditos+=cantidadCreditos;
				sumaSaldoTotal = sumaSaldoTotal.add(saldoTotalAdeudado);
				locReporte.add(new ReporteSaldo(viaCobro, cantidadCreditos.toString(), formateadorMonetario.format(saldoTotalAdeudado)));
			}
		}
	
		locReporte.add(new ReporteSaldo("TOTAL", cantidadTotalCreditos.toString(), formateadorMonetario.format(sumaSaldoTotal)));
		return locReporte;
	
	}
	
	
	private void ordenarLista(List<Credito> listaCreditos) {
		ListaDetalleCreditosComparatorFactory factory = new ListaDetalleCreditosComparatorFactory();
		Collections.sort(listaCreditos,factory.create(this.ordenamiento));
	}

	private List<ReporteSaldoDetallado> formatearReporteSaldoDetallado() {
		List<ReporteSaldoDetallado> locReporte = new ArrayList<ReporteSaldoDetallado>();
		CreditoDAOImpl creditoDAO= new CreditoDAOImpl();

		List<Credito> listaCreditos=creditoDAO.getCreditosPorTipoYViaEnCurso(this.tipoCredito, this.viaCobro, this.soloSeguros, this.fecha);

	

		if (listaCreditos != null) {
			if (this.ordenamiento != null) {
				ordenarLista(listaCreditos);
			}
			
			for(Credito cadaCredito: listaCreditos){
				if(cadaCredito.getCuotasAdeudadas(this.fecha)!=0){
					BigDecimal cero = new BigDecimal(0);
					BigDecimal saldoTotalAdeudadoCredito=cero;
					BigDecimal capitalAdeudado=cero;
					BigDecimal interesAdeudado=cero;
					BigDecimal otrosConceptosAdeudado=cero;
					String numeroCredito= cadaCredito.getNumeroCredito().toString();
					String legajo= null;
					if (cadaCredito.getCuentaCorriente().getPersona() instanceof PersonaFisica) {
						PersonaFisica solicitante= (PersonaFisica)cadaCredito.getCuentaCorriente().getPersona();
						legajo= solicitante.getLegajo().toString();
					}
					String solicitante= cadaCredito.getCuentaCorriente().getPersona().getNombreYApellido();
					String montoTotal=this.formateadorMonetario.format(cadaCredito.getMontoTotal());
					String cantidadCuotas= cadaCredito.getCantidadCuotas().toString();
					String cuotasAdeudadas= Integer.toString(cadaCredito.getCuotasAdeudadas(this.fecha));
	
			
						for(Cuota cadaCuota: cadaCredito.getListaCuotas()){
							
							if(cadaCuota.getSituacion(this.fecha).equals(SituacionCuota.VENCIDA)){
						
								saldoTotalAdeudadoCredito = saldoTotalAdeudadoCredito.add(cadaCuota.getTotal());
								interesAdeudado = interesAdeudado.add(cadaCuota.getInteres());
								otrosConceptosAdeudado = otrosConceptosAdeudado.add(cadaCuota.getOtrosConceptos());
								capitalAdeudado = capitalAdeudado.add(cadaCuota.getCapital());
							}
							else{
								if(cadaCuota.getSituacion(this.fecha).equals(SituacionCuota.NO_VENCIDA)){
									saldoTotalAdeudadoCredito = saldoTotalAdeudadoCredito.add(cadaCuota.getCapital());
									capitalAdeudado = capitalAdeudado.add(cadaCuota.getCapital());
								}	
							}
						}
					
						//TOTALES RESUMEN ULTIMA FILA DEL REPORTE
						MTSaldo = MTSaldo.add(saldoTotalAdeudadoCredito);
						MTCapital = MTCapital.add(capitalAdeudado);
						MTInteres = MTInteres.add(interesAdeudado);
						MTOtrosConceptos = MTOtrosConceptos.add(otrosConceptosAdeudado);
						cantidadCreditos= listaCreditos.size();
						String saldoAdeudado= this.formateadorMonetario.format(saldoTotalAdeudadoCredito);
						String capitalAdeud= this.formateadorMonetario.format(capitalAdeudado);
						String interesAdeud= this.formateadorMonetario.format(interesAdeudado);
						String otrosConceptosAdeud= this.formateadorMonetario.format(otrosConceptosAdeudado);
						locReporte.add(new ReporteSaldoDetallado(numeroCredito, solicitante, montoTotal, cantidadCuotas, cuotasAdeudadas, saldoAdeudado, capitalAdeud, interesAdeud, otrosConceptosAdeud, legajo));
				}
			}
		}
		return locReporte;
	}

	private void actualizarModelo() {
		if (this.tipoReporte.equals(Reportes.SALDO_DETALLADO_TIPO_CREDITO)){
			this.viaCobro= (ViaCobro) this.vista.getPnlReporteSaldoCredito().getCbxViaCobro().getSelectedItem();
			this.soloSeguros=this.vista.getPnlReporteSaldoCredito().getCkbSoloCreditos().isSelected();
		}
		this.tipoCredito= (TipoCredito) this.vista.getPnlReporteSaldoCredito().getCbxTipoCredito().getSelectedItem();
		this.fecha= this.vista.getPnlReporteSaldoCredito().getDtcFecha().getDate();
		this.ordenamiento = (String)this.vista.getPnlReporteSaldoCredito().getCboOrden().getSelectedItem();
	}


	private void finalizar() {
		this.adminCredito.mostrarPnlEstadoCuenta();	
	}
	
	

	public PnlDchaReporteSaldo getVista(){
		return this.vista;
	}

	
	//MUYY CHANCHO, PERO ES RÁPIDO DE ARMAR
	private class ListaDetalleCreditosComparatorFactory {

		public Comparator<Credito> create(String tipoOrden) {
			
			if (tipoOrden.equals(tiposOrdenamiento[1])) {
				return new Comparator<Credito>() {
					@Override
					public int compare(Credito o1, Credito o2) {
						Persona p1 = o1.getCuentaCorriente().getPersona();
						Persona p2 = o2.getCuentaCorriente().getPersona();
						if (p1 instanceof PersonaFisica && p2 instanceof PersonaFisica) {
							return ((PersonaFisica)p1).getApellido().compareTo(((PersonaFisica)p2).getApellido());
						}
						return 0;
					}
				};
			}
			else if (tiposOrdenamiento[2].equals(tipoOrden)) {
				return new Comparator<Credito>() {
					@Override
					public int compare(Credito o1, Credito o2) {
						return o1.getNumeroCredito().compareTo(o2.getNumeroCredito());
					}
				};
			}
			
			//Legajo
			Comparator<Credito> comparator = new Comparator<Credito>() {
				@Override
				public int compare(Credito o1, Credito o2) {
					Persona p1 = o1.getCuentaCorriente().getPersona();
					Persona p2 = o2.getCuentaCorriente().getPersona();
					if (p1 instanceof PersonaFisica && p2 instanceof PersonaFisica) {
						return ((PersonaFisica)p1).getLegajo().compareTo(((PersonaFisica)p2).getLegajo());
					}
					return 0;
				}
			};

			return comparator;
		}
	}
	
}
