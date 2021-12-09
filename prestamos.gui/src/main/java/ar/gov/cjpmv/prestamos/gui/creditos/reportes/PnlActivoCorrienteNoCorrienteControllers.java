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

import com.toedter.calendar.JDateChooser;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.TipoCreditoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionCuota;
import ar.gov.cjpmv.prestamos.core.persistence.enums.SituacionPeriodo;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.core.utiles.UtilFecha;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

public class PnlActivoCorrienteNoCorrienteControllers {
	
	private static String[] tiposOrdenamiento = {"Legajo","Apellido","Número"};
	
	private AdminCredito adminCredito;
	private PnlDchaPorPeriodo vista;
	private ComboBoxModel modeloTipoCredito;
	private ComboBoxModel modeloViaCobro;
	private TipoCreditoDAO tipoCreditoDAO;
	private TipoCredito tipoCredito;
	private ViaCobro viaCobro;
	private Date fechaHasta;
	private CreditoDAOImpl creditoDAO;
	private String ordenamiento = tiposOrdenamiento[0];
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private Integer cantidadCreditos;
	private String periodoHasta;
	private Date fechaCteYNoCte;
	private BigDecimal totalCapitalAdeudadoCorriente;
	private BigDecimal totalCapitalAdeudadoNoCorriente;
	private BigDecimal totalCapitalAdeudado;


	public PnlActivoCorrienteNoCorrienteControllers(AdminCredito adminCredito, Reportes pReporte){
		super();
		this.adminCredito = adminCredito;
		this.tipoCreditoDAO= new TipoCreditoDAO();
		this.creditoDAO= new CreditoDAOImpl();
		vista= new PnlDchaPorPeriodo();
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
		this.vista.getPnlActivoCorrienteNoCorriente().getCbxViaCobro().setModel(this.modeloViaCobro);

		List<TipoCredito> listaTiposCredito= new ArrayList<TipoCredito>();
		listaTiposCredito.add(0, null);
		listaTiposCredito.addAll(tipoCreditoDAO.findListaTipoCredito(null));
		this.modeloTipoCredito = new DefaultComboBoxModel(listaTiposCredito.toArray());
		
		this.vista.getPnlActivoCorrienteNoCorriente().getCbxTipoCredito().setModel(this.modeloTipoCredito);
		ComboBoxModel ordenamiento = new DefaultComboBoxModel(tiposOrdenamiento);
		ordenamiento.setSelectedItem(this.ordenamiento);
		this.vista.getPnlActivoCorrienteNoCorriente().getCbxOrden().setModel(ordenamiento);
	}

	private void inicializarEventos() {
		this.vista.getBtnAceptarGuardar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finalizar();
			}
		});
		
		this.vista.getPnlActivoCorrienteNoCorriente().getBtnImprimir().addActionListener(new ActionListener() {
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
			if(this.fechaHasta==null){
				int codigo= 93;
				String campo="";
				throw new LogicaException(codigo, campo);
			}
			this.generarReporte();
		}
		catch(LogicaException e){
			JOptionPane.showMessageDialog(this.vista, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
		}
	}
	

	private void generarReporte() {
		try{
			List<ReporteSaldoActivoCorrienteNoCorriente> listaSaldosActivoCorrienteNoCorriente = this.formatearReporteSaldoActvos();
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), Reportes.SALDO_POR_PERIODO, this.setearParametros(), listaSaldosActivoCorrienteNoCorriente);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
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
			
		this.periodoHasta=" Hasta: "+this.formateadorFecha.format(this.fechaHasta);
			
		parametros.put("periodoDesdeHasta", this.periodoHasta);
		parametros.put("cantidadCreditos", this.cantidadCreditos.toString());
		parametros.put("totalCapitalAdeudadoCorriente", this.formateadorMonetario.format(this.totalCapitalAdeudadoCorriente));
		parametros.put("totalCapitalAdeudadoNoCorriente", this.formateadorMonetario.format(this.totalCapitalAdeudadoNoCorriente));
		parametros.put("totalCapitalAdeudado", this.formateadorMonetario.format(this.totalCapitalAdeudado));

		return parametros;
	}
	
	

	private List<ReporteSaldoActivoCorrienteNoCorriente> formatearReporteSaldoActvos() {
		List<ReporteSaldoActivoCorrienteNoCorriente> listaActivoCteNoCte= new ArrayList<ReporteSaldoActivoCorrienteNoCorriente>();
		List<Credito> listaCredito= this.creditoDAO.getCreditosPorTipoYViaEnCurso(this.tipoCredito, this.viaCobro, false, this.fechaHasta);
		
		BigDecimal cero = new BigDecimal(0).setScale(2);
		this.cantidadCreditos= 0;
		this.totalCapitalAdeudadoCorriente= cero;
		this.totalCapitalAdeudadoNoCorriente= cero;
		this.totalCapitalAdeudado= cero;
	
		if(listaCredito != null) {
			
			if (this.ordenamiento != null) {
				ordenarLista(listaCredito);
			}
			
			for(Credito cadaCredito: listaCredito) {
				if(cadaCredito.getCuotasAdeudadas(this.fechaHasta) != 0) {
					
					String numeroCredito= cadaCredito.getNumeroCredito().toString();
					String legajo= null;
					if (cadaCredito.getCuentaCorriente().getPersona() instanceof PersonaFisica) {
						PersonaFisica solicitante= (PersonaFisica)cadaCredito.getCuentaCorriente().getPersona();
						if(solicitante.getLegajo()!=null){
							legajo= solicitante.getLegajo().toString();
						}
						else {
							legajo="";
						}
					}
					String solicitante= cadaCredito.getCuentaCorriente().getPersona().getNombreYApellido();
					
				
					BigDecimal capitalAdeudadoCorriente = cero;
					BigDecimal capitalAdeudadoNoCorriente = cero;
					BigDecimal capitalAdeudadoTotal = cero;
					
							
					
					for(Cuota cadaCuota: cadaCredito.getListaCuotas()){
						
						if(!cadaCuota.getSituacion(this.fechaHasta).equals(SituacionCuota.CANCELADA)){
							SituacionPeriodo locSituacionPeriodo= cadaCuota.getSituacionPeriodo(this.fechaCteYNoCte);
							switch(locSituacionPeriodo){
								case CAPITAL_CORRIENTE: {
									capitalAdeudadoCorriente = capitalAdeudadoCorriente.add(cadaCuota.getCapital());
									break;
								}
								case CAPITAL_NO_CORRIENTE: {
									capitalAdeudadoNoCorriente = capitalAdeudadoNoCorriente.add(cadaCuota.getCapital());
									break;
								}
							}
						}
					}
					capitalAdeudadoTotal= capitalAdeudadoTotal.add(capitalAdeudadoCorriente).add(capitalAdeudadoNoCorriente);
					
					String tCapitalAdeudadoCorriente= this.formateadorMonetario.format(capitalAdeudadoCorriente);
					String tCapitalAdeudadoNoCorriente= this.formateadorMonetario.format(capitalAdeudadoNoCorriente);
					String tCapitalAdeudadoTotal= this.formateadorMonetario.format(capitalAdeudadoTotal);	
					listaActivoCteNoCte.add(new ReporteSaldoActivoCorrienteNoCorriente(numeroCredito, legajo, solicitante, tCapitalAdeudadoCorriente, tCapitalAdeudadoNoCorriente, tCapitalAdeudadoTotal));
					this.cantidadCreditos+=1;
					
					this.totalCapitalAdeudadoCorriente = totalCapitalAdeudadoCorriente.add(capitalAdeudadoCorriente); 
					this.totalCapitalAdeudadoNoCorriente = totalCapitalAdeudadoNoCorriente.add(capitalAdeudadoNoCorriente);
					this.totalCapitalAdeudado = this.totalCapitalAdeudado.add(capitalAdeudadoTotal);				
				}
			
			}
			
			
		}	
		return listaActivoCteNoCte;
	}
	
	
	private void ordenarLista(List<Credito> listaCreditos) {
		ListaDetalleCreditosComparatorFactory factory = new ListaDetalleCreditosComparatorFactory();
		Collections.sort(listaCreditos,factory.create(this.ordenamiento));
	}
	
	
	private void actualizarModelo(){
		this.viaCobro= (ViaCobro) this.vista.getPnlActivoCorrienteNoCorriente().getCbxViaCobro().getSelectedItem();
		this.tipoCredito= (TipoCredito) this.vista.getPnlActivoCorrienteNoCorriente().getCbxTipoCredito().getSelectedItem();
		this.fechaHasta= this.vista.getPnlActivoCorrienteNoCorriente().getDtcFechaHasta().getDate();
		Calendar locFecha= this.vista.getPnlActivoCorrienteNoCorriente().getDtcFechaHasta().getCalendar();
		locFecha.add(Calendar.YEAR, 1);
		this.fechaCteYNoCte= locFecha.getTime();
	
		this.ordenamiento = (String)this.vista.getPnlActivoCorrienteNoCorriente().getCbxOrden().getSelectedItem();
	
	}

	public PnlDchaPorPeriodo getVista(){
		return this.vista;
	}
	
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
			
			//TODO SACAR EN UNA CLASE APARTE
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
	
	
	
}
