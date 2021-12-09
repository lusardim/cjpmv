/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gov.cjpmv.prestamos.gui.creditos.cheques;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoCheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;

/**
 *
 * @author pulpol
 */
public class AdministracionChequeTblModel extends AbstractTableModel{
    
	private static final String COLUMNAS[] = {
		"Número",
		"Cuenta bancaria",
		"Solicitante",
		"Concepto",
		"Monto",
		"Estado"
	};
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7625772027691316405L;
	private String apellido;
    private Integer numeroCheque;
    private List<Cheque> cheques=new ArrayList<Cheque>();

    @Override
    public int getRowCount() {
    	return cheques.size();
    }

    @Override
    public int getColumnCount() {
    	return COLUMNAS.length;
    }

    @Override
    public String getColumnName(int column) {
    	return COLUMNAS[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Cheque cheque = cheques.get(rowIndex);
    	switch(columnIndex){
    		case 0: return cheque.getNumero();
    		case 1: return cheque.getCuenta().toString();
    		case 2: return cheque.getNombrePersona();
    		case 3: return cheque.getConcepto();
    		case 4: return cheque.getMonto();
    		case 5: return cheque.getEstadoCheque();
    	}
    	return null;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(Integer numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public List<Cheque> getCheques() {
        return cheques;
    }

    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
        this.fireTableDataChanged();
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
    	switch(columnIndex){
    		case 0 : return Integer.class;
    		case 4 : return Float.class;
    		case 5 : return EstadoCheque.class;
    	}
    	return super.getColumnClass(columnIndex);
    }

	public Cheque getCheque(int filaSeleccionada) {
		return this.cheques.get(filaSeleccionada);		
	}
    
}
