package ar.gov.cjpmv.prestamos.gui.creditos.reportes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.CobroPorBancoDAO;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroPorBanco;
import ar.gov.cjpmv.prestamos.gui.creditos.controllers.AdminCredito;
import ar.gov.cjpmv.prestamos.gui.reportes.GestorImpresion;
import ar.gov.cjpmv.prestamos.gui.reportes.enums.Reportes;
import ar.gov.cjpmv.prestamos.gui.utiles.Utiles;


public class CobroPorBancoController {
	
	private Date fechaDesde;
	private Date fechaHasta;
	private DateFormat formateadorFecha= DateFormat.getDateInstance(DateFormat.SHORT);
	private NumberFormat formateadorMonetario = NumberFormat.getCurrencyInstance();
	
	private PnlDchaCobroBancario vista;
	private AdminCredito adminCredito;
	private CobroPorBancoDAO cobroBancoDAO;
	
	public CobroPorBancoController(AdminCredito adminCredito) {
		super();
		this.adminCredito = adminCredito;
		vista= new PnlDchaCobroBancario();
		this.inicializarEventos();
		this.inicializarVista();
	}

	private void inicializarVista() {
		this.vista.getBtnAceptarGuardar().setVisible(false);
		this.vista.getBtnCancelar().setVisible(false);
		this.vista.getBtnImprimir().setVisible(false);
	}

	private void inicializarEventos() {
		this.vista.getCobrosBancarios().getBtnImprimir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imprimir();
			}
		});
	}
	
	public PnlDchaCobroBancario getVista(){
		 return this.vista;
	}

	private void imprimir() {
		try{
			this.actualizarModelo();
			this.cobroBancoDAO= new CobroPorBancoDAO();
			if(this.cobroBancoDAO.findCobros(this.fechaDesde, this.fechaHasta)!=null){
				List<CobroPorBanco> listaCobros= this.cobroBancoDAO.findCobros(this.fechaDesde, this.fechaHasta);
	
				List<ReporteCobroPorBanco> lista = this.formatearReporte(listaCobros);
				GestorImpresion.imprimirCollectionDataSource(this.adminCredito.getVista(), Reportes.REPORTE_COBROS_POR_BANCO, this.setearParametros(), lista);
			
			}
			else{
				int codigo=112;
				String campo="";
				throw new LogicaException(codigo, campo);
			}
		}
		catch(LogicaException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, e.getMessage(), e.getTitulo(), JOptionPane.ERROR_MESSAGE);
		} 
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.vista, "No se ha podido generar el listado.", "Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private List<ReporteCobroPorBanco> formatearReporte(List<CobroPorBanco> listaCobros) throws Exception {
		
		List<ReporteCobroPorBanco> lista= new ArrayList<ReporteCobroPorBanco>();
		
		for(CobroPorBanco cadaCobro: listaCobros){
			ReporteCobroPorBanco cobro= new ReporteCobroPorBanco();
			Integer numeroBoleta= cadaCobro.getNumeroBoleta();
			cobro.setNumeroBoleta(numeroBoleta.toString());
			cobro.setNumeroCuenta(cadaCobro.getCuenta().getNumero());
			cobro.setBanco(cadaCobro.getCuenta().getBanco().getNombre());
			cobro.setFecha(formateadorFecha.format(cadaCobro.getFecha()));
			cobro.setMonto(formateadorMonetario.format(cadaCobro.getMonto()));
			cobro.setSolicitante(cadaCobro.getCuentaCorriente().getPersona().getNombreYApellido());
			lista.add(cobro);

		}
		return lista;
	}
		


	private Map<String, String> setearParametros() {
		Map<String, String> parametros= new HashMap<String, String>();
		parametros.put("fechaDesde", this.fechaDesde.toString());
		parametros.put("fechaHasta", this.fechaHasta.toString());
		return parametros;	
	}
	

	private void actualizarModelo() throws LogicaException {
		this.fechaDesde= this.vista.getCobrosBancarios().getDtcFechaDesde().getDate();
		this.fechaHasta= this.vista.getCobrosBancarios().getDtcFechaHasta().getDate();
		if((this.fechaDesde==null)||(this.fechaHasta==null)){
			int codigo= 111;
			String campo= "";
			throw new LogicaException(codigo, campo);
		}
		else{
			if(Utiles.fechaDesdeMayorQueHasta(this.fechaDesde, this.fechaHasta)){
				int codigo= 113;
				String campo= "";
				throw new LogicaException(codigo, campo);	
			}
			
		}
	}

}
