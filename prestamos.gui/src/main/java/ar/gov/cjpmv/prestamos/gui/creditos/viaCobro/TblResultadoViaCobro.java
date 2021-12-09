package ar.gov.cjpmv.prestamos.gui.creditos.viaCobro;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.PersonaJuridica;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.gui.creditos.importacion.ArchivoDetalleLiquidacionModel;

public class TblResultadoViaCobro extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Credito> listaCredito;
	

	public TblResultadoViaCobro(
			List<Credito> pListaCredito) {
		super();
		this.listaCredito = pListaCredito;
	}
	
	
	
	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}
	
	
	/*
	 Numero credito
estado 
Cuil/cuit
Legajo
Estado
Nombre y Apellido
Via de cobro
	 * */

	

	@Override
	public String getColumnName(int column) {
		String nombre=null;
		switch(column){
			case 0: nombre = "N° Crédito (Estado)";break;
			case 1: nombre = "CUIL/CUIT"; break;
			case 2: nombre = "Legajo"; break;
			case 3: nombre = "Estado"; break;
			case 4: nombre = "Nombre y Apellido"; break;
			case 5: nombre = "Vía de Cobro"; break;
		}
		return nombre;
	}
	
	
	@Override
	public int getRowCount() {
		if (this.listaCredito==null){
			return 0;
		}
		return this.listaCredito.size();
	}
	
	
	

	@Override
	public Class getColumnClass(int column)
	{
	      if (column == 0) return Object.class;
	      if (column == 1) return Object.class;
	      if (column == 2) return Object.class;
	      if (column == 3) return Object.class;
	      if (column == 4) return Object.class;
	      if (column == 5) return Object.class;
	      return Object.class;
	}
	
	
	@Override
	public Object getValueAt(int row, int column) {
		Credito locCredito= this.listaCredito.get(row);	
		switch(column){
			
		
			case 0: return locCredito.getNumeroCredito()+" ("+locCredito.getEstado()+")";
			case 1: return locCredito.getCuentaCorriente().getPersona().getCui();
			case 2: {
				if(locCredito.getCuentaCorriente().getPersona()instanceof PersonaFisica) {
					PersonaFisica locPersonaFisica= (PersonaFisica)locCredito.getCuentaCorriente().getPersona();
					return locPersonaFisica.getLegajo();
				}
				else{
					return "";
				}
			}
			case 3: {
				if(locCredito.getCuentaCorriente().getPersona()instanceof PersonaFisica) {
					PersonaFisica locPersonaFisica= (PersonaFisica)locCredito.getCuentaCorriente().getPersona();
					return locPersonaFisica.getEstado();
				}
				else{
					PersonaJuridica locPersonaJuridica= (PersonaJuridica)locCredito.getCuentaCorriente().getPersona();
					return locPersonaJuridica.getEstado();
				}
			}
			case 4: return locCredito.getCuentaCorriente().getPersona().getNombreYApellido();
			case 5: return locCredito.getViaCobro().getNombre();
		}
		return null;
	}
	

	
	public Credito getCredito(int fila){
		return this.listaCredito.get(fila);
	}


}
