package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAOImpl;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.ViaCobro;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

public class PnlCobranzasControllers {

	private AdminCredito adminCredito;
	private PnlDchaCobranzas vista;
	private ComboBoxModel modeloViaCobro;
	private ViaCobro viaCobro;
	private Integer locAnio;
	private Integer locMes;
	private Date fechaDesde;
	private Date fechaHasta;
	private GeneradorReporteTotalesPorViaCobro generadorCobranzas;
	private List<ConceptoCobranzas> listaConceptoCobranzas;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private DateFormat formateadorFecha;


	
	public PnlCobranzasControllers(AdminCredito adminCredito){
		super();
		this.adminCredito = adminCredito;
		vista= new PnlDchaCobranzas();
		this.inicializarEventos();
		this.inicializarVista();
		this.formateadorFecha= new SimpleDateFormat("MMMMM 'de' yyyy");
	}


	private void inicializarVista() {
		this.vista.getBtnAceptarGuardar().setVisible(false);
		this.vista.getBtnCancelar().setVisible(false);
		this.vista.getBtnImprimir().setVisible(false);
		CreditoDAOImpl creditoDAO = new CreditoDAOImpl();
		List<ViaCobro> listaViaCobro = creditoDAO.getListaViasCobro();
		eliminarIncobrables(listaViaCobro);
		
		this.modeloViaCobro= new DefaultComboBoxModel(listaViaCobro.toArray());
		this.vista.getPnlCobranzas().getCbxViaCobro().setModel(this.modeloViaCobro);
	}

	private void eliminarIncobrables(List<ViaCobro> listaViaCobro) {
		Iterator<ViaCobro> viaCobroIterator = listaViaCobro.iterator();
		while (viaCobroIterator.hasNext()) {
			ViaCobro via = viaCobroIterator.next();
			if (via.getNombre().equalsIgnoreCase("incobrable")) {
				viaCobroIterator.remove();
			}
		}
	}

	private void inicializarEventos() {
		this.vista.getPnlCobranzas().getBtnImprimir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				imprimirReporte();
			}
		});
		
	}

	public PnlDchaCobranzas getVista(){
		return this.vista;
	}
	

	private void imprimirReporte() {
		try{
			this.actualizarModelo();
			
//			if (this.viaCobro.equals(ViaCobro.municipalidad)&&(this.locAnio==2010)){
//				if(this.locMes==Calendar.NOVEMBER){
//					this.generarReporteTruchoNov();
//				}
//				else if (this.locMes==Calendar.DECEMBER) {
//						this.generarReporteTruchoDic();
//				}
//			}
//			else {
				this.generadorCobranzas= new GeneradorReporteTotalesPorViaCobro(this.viaCobro, this.fechaDesde, this.fechaHasta);
				if(!this.generadorCobranzas.getListaTotalesPorTipoCredito().isEmpty()){
					this.generarReporte();
				}
				else{
					int codigo= 105;
					String campo="";
					throw new LogicaException(codigo, campo);
				}
//			}
		}
		catch(LogicaException e) {
			JOptionPane.showMessageDialog(this.vista, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}


	private void generarReporte() throws Exception {
		this.listaConceptoCobranzas= new ArrayList<ConceptoCobranzas>();
		Map<String,BigDecimal> totales = this.generadorCobranzas.getListaTotalesPorTipoCredito();
		for (Entry<String, BigDecimal> cadaTotal : totales.entrySet()) {
			String nombre= cadaTotal.getKey();
			String monto= formateadorMonetario.format(cadaTotal.getValue());
			this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombre, monto));
		}
		String nombreSubtotal= "SUBTOTAL";
		String montoSubtotal= formateadorMonetario.format(this.generadorCobranzas.getSubTotal());
		this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombreSubtotal, montoSubtotal));
		Iterator locIt = this.generadorCobranzas.getListaTotalesConceptos().entrySet().iterator();
		while (locIt.hasNext()) {
			Map.Entry e = (Map.Entry)locIt.next();
			String nombre= (String) e.getKey();
			String monto= formateadorMonetario.format(e.getValue());
			this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombre, monto));
		}
		String nombreTotal= "TOTAL";
		String montoTotal= formateadorMonetario.format(this.generadorCobranzas.getTotal());
		this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombreTotal, montoTotal));
		GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(),Reportes.COBRANZAS_POR_PERIODO_Y_VIA , this.setearParametros(), listaConceptoCobranzas);
	}

	@Deprecated
	private void generarReporteTruchoNov() throws Exception {
		this.listaConceptoCobranzas= new ArrayList<ConceptoCobranzas>();
		Map<String,BigDecimal> totales= new HashMap<String, BigDecimal>();
		totales.put("Personal",new BigDecimal("76635.60"));
		totales.put("Hipotecario",new BigDecimal("9889.03"));
		totales.put("Asistencial",new BigDecimal("5696.82"));
		totales.put("56 viviendas",new BigDecimal("760.31"));

		for (Entry<String, BigDecimal> cadaTotal : totales.entrySet()) {
			String nombre= cadaTotal.getKey();
			String monto= formateadorMonetario.format(cadaTotal.getValue());
			this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombre, monto));
		}
		String nombreSubtotal= "SUBTOTAL";
		String montoSubtotal= "$ 92981,76";
		this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombreSubtotal, montoSubtotal));
		
		this.listaConceptoCobranzas.add(new ConceptoCobranzas("Interés", "$ 45071,68"));
		this.listaConceptoCobranzas.add(new ConceptoCobranzas("Seguro", "$ 1721,70"));
		
		String nombreTotal= "TOTAL";
		String montoTotal= "$ 139775,15";
		this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombreTotal, montoTotal));
		GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(),Reportes.COBRANZAS_POR_PERIODO_Y_VIA , this.setearParametros(), listaConceptoCobranzas);
	
	}

	@Deprecated
	private void generarReporteTruchoDic() throws Exception {
		this.listaConceptoCobranzas= new ArrayList<ConceptoCobranzas>();
		Map<String,BigDecimal> totales= new HashMap<String, BigDecimal>();
		totales.put("Personal",new BigDecimal("69010.95"));
		totales.put("Hipotecario",new BigDecimal("9344.59"));
		totales.put("Asistencial",new BigDecimal("6283.93"));
		totales.put("56 viviendas",new BigDecimal("761.02"));

		for (Entry<String, BigDecimal> cadaTotal : totales.entrySet()) {
			String nombre= cadaTotal.getKey();
			String monto= formateadorMonetario.format(cadaTotal.getValue());
			this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombre, monto));
		}
		String nombreSubtotal= "SUBTOTAL";
		String montoSubtotal= "$ 85400,49";
		this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombreSubtotal, montoSubtotal));
		
		this.listaConceptoCobranzas.add(new ConceptoCobranzas("Interés", "$ 42097,41"));
		this.listaConceptoCobranzas.add(new ConceptoCobranzas("Seguro", "$ 1567,10"));
		
		String nombreTotal= "TOTAL";
		String montoTotal= "$ 129065,00";
		this.listaConceptoCobranzas.add(new ConceptoCobranzas(nombreTotal, montoTotal));
		GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(),Reportes.COBRANZAS_POR_PERIODO_Y_VIA , this.setearParametros(), listaConceptoCobranzas);
	
	}


	private Map<String, String> setearParametros() {
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("periodo", this.formateadorFecha.format(this.fechaDesde).toUpperCase());
		parametros.put("viaCobro", this.viaCobro.getNombre().toUpperCase());
		return parametros;
	}


	private void actualizarModelo() {
		this.viaCobro= (ViaCobro) this.vista.getPnlCobranzas().getCbxViaCobro().getSelectedItem();
		this.locAnio= this.vista.getPnlCobranzas().getYcAnio().getYear();
		this.locMes = this.vista.getPnlCobranzas().getMchMes().getMonth();
		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.YEAR, locAnio);
		calendario.set(Calendar.MONTH,locMes);	
		calendario.set(Calendar.DAY_OF_MONTH, calendario.getActualMinimum(Calendar.DAY_OF_MONTH));
		this.fechaDesde= calendario.getTime();
		calendario.set(Calendar.DAY_OF_MONTH, calendario.getActualMaximum(Calendar.DAY_OF_MONTH));
		this.fechaHasta= calendario.getTime();
	}
	
	
}
