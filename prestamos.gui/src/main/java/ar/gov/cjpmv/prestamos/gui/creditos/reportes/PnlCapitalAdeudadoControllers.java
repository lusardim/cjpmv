package ar.gov.cjpmv.prestamos.gui.creditos.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.GestorPersitencia;
import ar.gov.cjpmv.prestamos.core.business.dao.CreditoDAO;
import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.TipoCredito;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;

public class PnlCapitalAdeudadoControllers {


	private AdminCredito adminCredito;
	private PnlDchaCapitalAdeudado vista;
	private Date fechaHasta;
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);




	public PnlCapitalAdeudadoControllers(AdminCredito adminCredito){
		super();
		this.adminCredito = adminCredito;
		vista= new PnlDchaCapitalAdeudado();
		this.inicializarEventos();
		this.inicializarVista();
		
	}


	
	private void inicializarEventos() {
		this.vista.getPnlCapitalAdeudado().getBtnImprimir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				imprimirReporte();
			}

		});
		
	}

	public PnlDchaCapitalAdeudado getVista(){
		return this.vista;
	}
	
	

	private void imprimirReporte() {
		try{
			this.actualizarModelo();
			this.generarReporte();
			
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
			List<ConceptoCobranzas> listaConceptos= this.generarLista();			
			GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), Reportes.SALDO_CAPITAL_POR_TIPO_CREDITO, this.setearParametros(), listaConceptos);	
		
	}



	private Map<String, String> setearParametros() {
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("fechaHasta", this.formateadorFecha.format(this.fechaHasta));
		return parametros;
	}



	private List<ConceptoCobranzas> generarLista() {
		List<ConceptoCobranzas> listaConceptos= new ArrayList<ConceptoCobranzas>();
		CreditoDAO dao = DAOFactory.getDefecto().getCreditoDAO();
		List<TipoCredito> listaTipos = GestorPersitencia.getInstance().getEntityManager().createQuery("from TipoCredito").getResultList();
		BigDecimal total = new BigDecimal("0.00"); 
		for (TipoCredito cadaTipo : listaTipos) {
			BigDecimal capital = dao.getSaldoCapital(cadaTipo, fechaHasta);
			String cadenaCapital="";
			if(capital!=null){
				cadenaCapital=formateadorMonetario.format(capital);
				total = total.add(capital);
			}
			listaConceptos.add(new ConceptoCobranzas(cadaTipo.getNombre(), cadenaCapital));
		}
		String locTotal= formateadorMonetario.format(total);
		listaConceptos.add(new ConceptoCobranzas("Total",locTotal));
		return listaConceptos;
	}



	private void actualizarModelo() throws LogicaException {
		if(this.vista.getPnlCapitalAdeudado().getDtcFechaHasta().getDate()!=null){
			this.fechaHasta= this.vista.getPnlCapitalAdeudado().getDtcFechaHasta().getDate();
		}
		else{
			String campo="";
			int numero=109;
			throw new LogicaException(numero, campo);
		}
	}



	private void inicializarVista() {
		this.vista.getBtnAceptarGuardar().setVisible(false);
		this.vista.getBtnCancelar().setVisible(false);
		this.vista.getBtnImprimir().setVisible(false);
		
		Date locFechaHoy= Calendar.getInstance().getTime();
		this.vista.getPnlCapitalAdeudado().getDtcFechaHasta().setDate(locFechaHoy);
		
		
	}
	
}