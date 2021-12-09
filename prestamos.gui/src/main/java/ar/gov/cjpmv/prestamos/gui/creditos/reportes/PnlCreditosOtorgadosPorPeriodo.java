package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.ConceptoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAO;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.TipoCreditoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.core.utiles.UtilFecha;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

public class PnlCreditosOtorgadosPorPeriodo {

	private static String[] tiposOrdenamiento = {"Legajo","Apellido","Número"};
	private AdminCredito adminCredito;
	private PnlDchaOtorgadosPorPeriodo vista;
	private Reportes tipoReporte;
	private CreditoDAO creditoDAO;
	private ComboBoxModel modeloTipoCredito;
	private ComboBoxModel modeloViaCobro;
	private TipoCreditoDAO tipoCreditoDAO;
	private TipoCredito tipoCredito;
	private ViaCobro viaCobro;
	private Date fechaDesde;
	private Date fechaHasta;
	private String ordenamiento = tiposOrdenamiento[0];
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private Integer totalCreditos;
	private BigDecimal totalCapital;
	private BigDecimal totalFondoQuebranto;
	private BigDecimal totalMontoSellado;
	private BigDecimal totalOtrasRetenciones;
	
	public PnlCreditosOtorgadosPorPeriodo(AdminCredito adminCredito,
			Reportes pReporte) {
		this.adminCredito= adminCredito;
		this.vista= new PnlDchaOtorgadosPorPeriodo();
		this.tipoReporte= pReporte;
		this.creditoDAO= new CreditoDAOImpl();
		this.tipoCreditoDAO= new TipoCreditoDAO();
		this.totalCreditos=0;
		BigDecimal cero = new BigDecimal(0).setScale(2);
		this.totalCapital= cero;
		this.totalFondoQuebranto= cero;
		this.totalMontoSellado= cero;
		this.totalOtrasRetenciones= cero;
		this.inicializarEventos();
		this.inicializarVista();
	}

	private void inicializarVista() {
		this.vista.getBtnCancelar().setVisible(false);
		this.vista.getBtnImprimir().setVisible(false);
		this.vista.getBtnAceptarGuardar().setText("Finalizar");
		List<ViaCobro> listaViaCobro= new ArrayList<ViaCobro>();
		listaViaCobro.add(0, null);
		listaViaCobro.addAll(creditoDAO.getListaViasCobro());
		this.modeloViaCobro= new DefaultComboBoxModel(listaViaCobro.toArray());
		this.vista.getPnlCreditosOtorgados().getCbxViaCobro().setModel(this.modeloViaCobro);

		List<TipoCredito> listaTiposCredito= new ArrayList<TipoCredito>();
		listaTiposCredito.add(0, null);
		listaTiposCredito.addAll(tipoCreditoDAO.findListaTipoCredito(null));
		this.modeloTipoCredito = new DefaultComboBoxModel(listaTiposCredito.toArray());
		
		this.vista.getPnlCreditosOtorgados().getCbxTipoCredito().setModel(this.modeloTipoCredito);
		ComboBoxModel ordenamiento = new DefaultComboBoxModel(tiposOrdenamiento);
		ordenamiento.setSelectedItem(this.ordenamiento);
		this.vista.getPnlCreditosOtorgados().getCbxOrden().setModel(ordenamiento);
	}

	private void inicializarEventos() {
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finalizar();
			}
		});
		this.vista.getPnlCreditosOtorgados().getBtnImprimir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imprimir();
			}
		});
	}

	private void finalizar() {
		this.adminCredito.mostrarPnlEstadoCuenta();	
	}

	private void imprimir() {
		this.actualizarModelo();
		try{
			if((this.fechaDesde==null)||(this.fechaHasta==null)){
				int codigo= 100;
				String campo="";
				throw new LogicaException(codigo, campo);
			}
			if(this.errorFechas()){
				int codigo= 101;
				String campo="";
				throw new LogicaException(codigo, campo);
			}
			this.generarReporte();
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this.vista, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean errorFechas() {
		if(UtilFecha.comparaDia(this.fechaDesde, this.fechaHasta)){
			return true;
		}
		else{
			if(this.fechaHasta.before(this.fechaDesde)){
				return true;
			}
		}
		return false;
	}

	private void generarReporte() {
		this.totalCreditos=0;
		BigDecimal cero = new BigDecimal(0).setScale(2);
		this.totalCapital= cero;
		this.totalFondoQuebranto= cero;
		this.totalMontoSellado= cero;
		this.totalOtrasRetenciones= cero;
		try{
			List<ReporteCreditosOtorgadosPorPeriodo> listaCreditos= this.generarLista();			
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), this.tipoReporte, this.setearParametros(), listaCreditos);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void ordenarLista(List<Credito> listaCreditos) {
		ListaDetalleCreditosComparatorFactory factory = new ListaDetalleCreditosComparatorFactory();
		Collections.sort(listaCreditos,factory.create(this.ordenamiento));
	}

	//TODO SACAR ESTO AFUEGA
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
						if((((PersonaFisica)p1).getLegajo()!=null)&&(((PersonaFisica)p2).getLegajo()!=null)){
							return ((PersonaFisica)p1).getLegajo().compareTo(((PersonaFisica)p2).getLegajo());
						}
					}
					return 0;
				}
			};

			return comparator;
		}
	}

	private Map<String, String> setearParametros() {
		Map<String, String> parametros= new HashMap<String, String>();
		String locTipoCredito= null;
		String locViaCobro= null;
		if(this.tipoCredito!=null){
			locTipoCredito=this.tipoCredito.getNombre().toUpperCase();
		}
		if(this.viaCobro!=null){
			locViaCobro="Vía de Cobro: "+this.viaCobro.getNombre();
		}
		parametros.put("viaCobro", locViaCobro);
		parametros.put("tipoCredito", locTipoCredito);
		String locPeriodo= "Desde: "+this.formateadorFecha.format(this.fechaDesde)+" - Hasta: "+this.formateadorFecha.format(this.fechaHasta);
		parametros.put("periodo", locPeriodo);
		parametros.put("totalCapital", formateadorMonetario.format(this.totalCapital));
		parametros.put("totalCreditos", this.totalCreditos.toString());
		parametros.put("totalFondoQuebranto", formateadorMonetario.format(this.totalFondoQuebranto));
		parametros.put("totalMontoSellado", formateadorMonetario.format(this.totalMontoSellado));
		parametros.put("totalOtrasRetenciones", formateadorMonetario.format(this.totalOtrasRetenciones));
		return parametros;
	}

	private List<ReporteCreditosOtorgadosPorPeriodo> generarLista() {
		
		List<ReporteCreditosOtorgadosPorPeriodo> locListaCreditos= new ArrayList<ReporteCreditosOtorgadosPorPeriodo>();
		
		
		List<Credito> listaCreditosOtorgados= this.creditoDAO.findCreditosOtorgados(this.fechaDesde, this.fechaHasta, this.tipoCredito, this.viaCobro);
		
		if(listaCreditosOtorgados!=null){
		

			this.totalCreditos= listaCreditosOtorgados.size();
		
			if (this.ordenamiento != null) {
				ordenarLista(listaCreditosOtorgados);
			}
			
			ConceptoDAO locConceptoDAO= new ConceptoDAO();
			Concepto conceptoSellado= locConceptoDAO.findRetenciones("IMPUESTOS A SELLOS");
			Concepto conceptoFondoQuebranto= locConceptoDAO.findRetenciones("FONDO DE QUEBRANTO");

			for(Credito cadaCredito: listaCreditosOtorgados){
				String numeroCredito= cadaCredito.getNumeroCredito().toString();
				String legajo="";
				if(cadaCredito.getCuentaCorriente().getPersona() instanceof PersonaFisica){
					PersonaFisica locPersona=(PersonaFisica)cadaCredito.getCuentaCorriente().getPersona();
					if(locPersona.getLegajo()!=null){
						legajo= locPersona.getLegajo().toString();
					}
				}
				String solicitante= cadaCredito.getCuentaCorriente().getPersona().getNombreYApellido();
				String fechaOtorgamiento= formateadorFecha.format(cadaCredito.getFechaInicio());
				String capitalOtorgado= formateadorMonetario.format(cadaCredito.getMontoTotal());
				BigDecimal cero = new BigDecimal(0).setScale(2);
				BigDecimal montoOtrasRetenciones=cero;
				BigDecimal montoSellado=cero;
				BigDecimal montoFondoQuebranto=cero;
				
				this.totalCapital = this.totalCapital.add(cadaCredito.getMontoTotal());
				
				for(DetalleCredito cadaDetalle: cadaCredito.getDetalleCredito()){
					if(cadaDetalle.getFuente()==null){
						if(!cadaDetalle.getNombre().equals("Redondeo de cuota")){
							montoOtrasRetenciones = montoOtrasRetenciones.add(cadaDetalle.getValor());
						}
					}
					else {
						if(cadaDetalle.getFuente().equals(conceptoFondoQuebranto)){
							montoFondoQuebranto = cadaDetalle.getValor();
						}
						else{
							if(cadaDetalle.getFuente().equals(conceptoSellado)){
								montoSellado = cadaDetalle.getValor();
							}
						}						
					}
				}
				
				this.totalFondoQuebranto = totalFondoQuebranto.add(montoFondoQuebranto);
				this.totalMontoSellado = totalMontoSellado.add(montoSellado);
				this.totalOtrasRetenciones = totalOtrasRetenciones.add(montoOtrasRetenciones);

				String cadenaMontoSellado= formateadorMonetario.format(montoSellado); 
				String montoFondo= formateadorMonetario.format(montoFondoQuebranto);
				String cadenaMontoOtrosConceptos= formateadorMonetario.format(montoOtrasRetenciones);
				
				locListaCreditos.add( 
						new ReporteCreditosOtorgadosPorPeriodo(
								numeroCredito, 
								legajo, 
								solicitante, 
								fechaOtorgamiento, 
								capitalOtorgado, 
								cadenaMontoSellado, 
								montoFondo, 
								cadenaMontoOtrosConceptos
							)
						);

			}
			
		}
		return locListaCreditos;
	}

	private void actualizarModelo() {
		this.viaCobro= (ViaCobro) this.vista.getPnlCreditosOtorgados().getCbxViaCobro().getSelectedItem();
		this.tipoCredito= (TipoCredito) this.vista.getPnlCreditosOtorgados().getCbxTipoCredito().getSelectedItem();
		this.fechaHasta= this.vista.getPnlCreditosOtorgados().getDtcFechaHasta().getDate();
		this.fechaDesde= this.vista.getPnlCreditosOtorgados().getDtcFechaDesde().getDate();
		this.ordenamiento = (String)this.vista.getPnlCreditosOtorgados().getCbxOrden().getSelectedItem();
	}

	public PnlDchaOtorgadosPorPeriodo getVista() {
		return this.vista;
	}
}
