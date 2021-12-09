package ar.gov.cjpmv.prestamos.gui.sueldos.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.DAOFactory;
import ar.gov.cjpmv.prestamos.core.business.dao.CategoriaDAO;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.LineaRecibo;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;
import ar.gov.cjpmv.prestamos.gui.sueldos.PnlAMDetalleLiquidacionHaberes;

public class TblDetalleReciboHaberesModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1562814346242082939L;

	//FIXME sacar el dao de aca. super oink oink 
	private CategoriaDAO categoriaDAO = DAOFactory.getDefecto().getCategoriaDAO();
	
	private List<LineaRecibo> listaLineaRecibo;
	private PnlAMDetalleLiquidacionHaberes vista;
	
	private BigDecimal haberes;
	private BigDecimal descuento;
	private BigDecimal neto;
	
	
	public TblDetalleReciboHaberesModel(PnlAMDetalleLiquidacionHaberes pVista, Set<LineaRecibo> set) {
		super();
		this.listaLineaRecibo = new ArrayList<LineaRecibo>(set);
		this.vista= pVista;
	}

	@Override
	public int getRowCount() {
		int valor= (this.listaLineaRecibo!=null && !this.listaLineaRecibo.isEmpty())?this.listaLineaRecibo.size():0;
		return valor;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "Código";break;
			case 1: nombre = "Descripción"; break;
			case 2: nombre = "Cantidad"; break;
			case 3: nombre = "Monto Unitario"; break;
			case 4: nombre = "Monto Total"; break;
		}
		return nombre;
	}
	
	
	public void calcularTotales() {
		this.haberes= new BigDecimal("0.00").setScale(2,RoundingMode.HALF_UP);
		this.descuento= new BigDecimal("0.00").setScale(2,RoundingMode.HALF_UP);
		this.neto= new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
		if(listaLineaRecibo!=null && !listaLineaRecibo.isEmpty()){
			listaLineaRecibo.get(0).getReciboSueldo().recalcularSegunConceptos();
		}
		
		for(LineaRecibo cadaLinea:listaLineaRecibo){
			if(!cadaLinea.getConcepto().getTipoCodigo().equals(TipoCodigo.DESCUENTO)){
				this.haberes=this.haberes.add(cadaLinea.getTotal());
			}
			else{
				this.descuento= this.descuento.add(cadaLinea.getTotal());
			}
		}
		this.neto= this.neto.add(this.haberes);
		this.neto= this.neto.subtract(this.descuento);
	}
	
	
	

	@Override
	public Object getValueAt(int row, int column) {
		LineaRecibo locLineaRecibo= this.listaLineaRecibo.get(row);
		switch(column){
			case 0: {
				Integer codigo= locLineaRecibo.getConcepto().getCodigo();
				return codigo.toString();
			}
			case 1: {
				if (locLineaRecibo.getConcepto().equals(ConceptoPrefijado.SUELDO_BASICO)) {
					Categoria categoria = categoriaDAO.getCategoria(locLineaRecibo.getReciboSueldo().getPersona());
					if (categoria != null) {
						return categoria.toString();
					}
				}
				return locLineaRecibo.getConcepto().getDescripcion();
			}
			case 2:{
				if(locLineaRecibo.getCantidad()!=null){
					return locLineaRecibo.getCantidad().toString();
				}
				else{
					return "";
				}
			}
			case 3:{
				return locLineaRecibo.getMonto().toString();
			}
			case 4:{
				return locLineaRecibo.getTotal().toString(); 
			}
		}
		fireTableDataChanged(); 
		return null;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {		
		LineaRecibo lineaRecibo= (LineaRecibo)listaLineaRecibo.get(rowIndex);
		if(columnIndex==2){
			return true;
		}
		else if (columnIndex==3){
			return true;
			/*
			   if(lineaRecibo.getConcepto() instanceof ConceptoFijo){
				   ConceptoFijo locConcepto= (ConceptoFijo) lineaRecibo.getConcepto();
				   if(!locConcepto.isSobreescribirValor()){
					   return true;
				   }
				   else {
					   return false;
				   }
			   }
			   else{
				   return false;
			   }
			*/
		}
		return false;
	}

	@Override 
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { 
	   LineaRecibo lineaRecibo= (LineaRecibo)listaLineaRecibo.get(rowIndex);
	   switch(columnIndex){
		   case 2:{

           	String valorOriginal= null;
           	Integer locCantidad= this.listaLineaRecibo.get(rowIndex).getCantidad();
           
           	if(locCantidad!=null){
           		valorOriginal= locCantidad.toString();
          
           	}
           	
           	if(!aValue.equals(valorOriginal)){
	            if( aValue!=null && !aValue.toString().trim().equals("") ){
	            	try{
	            		Integer intCantidad= Integer.parseInt(aValue.toString()); 
	            		lineaRecibo.setCantidad(intCantidad); 
	            		this.calcularTotales();
	            		this.vista.actualizarVistaTotales();
	               	}
	            	catch(NumberFormatException e){
	            		String mensaje="Error en la columna cantidad. Ingrese un número entero positivo.";
	            		String titulo="";
	            		JOptionPane.showMessageDialog(this.vista, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
	            	}
	            }
           	}
           	
           	break;
		 }
		  case 3:{
		    	String valorOriginal= null;
	           	BigDecimal locMonto= this.listaLineaRecibo.get(rowIndex).getMonto();
	           
	           	if(locMonto!=null){
	           		valorOriginal= locMonto.toString();
	           	}
	           	
	           	if(!aValue.equals(valorOriginal)){
		            if( aValue!=null && !aValue.toString().trim().equals("") ){
		            	try{
		            		BigDecimal monto= new BigDecimal(aValue.toString()).setScale(2, RoundingMode.HALF_UP); 
		            		lineaRecibo.setMonto(monto); 
		            		this.calcularTotales();
		            		this.vista.actualizarVistaTotales();
		               	}
		            	catch(NumberFormatException e){
		            		String mensaje="Error en la columna monto. Ingrese un valor númerico con formato 0.00.";
		            		String titulo="";
		            		JOptionPane.showMessageDialog(this.vista, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
		            	}
		            }
	           	} 
			   
		   }
		  break;
	  }
	  fireTableDataChanged(); 
	   
	}
	
	
	public LineaRecibo getLineaRecibo(int fila) {
		return this.listaLineaRecibo.get(fila);
	}

	
	public BigDecimal getHaberes() {
		return this.haberes;
	}

	public BigDecimal getDescuento() {
		return this.descuento;
	}

	public BigDecimal getNeto() {
		return this.neto;
	}



	
	
	
}
