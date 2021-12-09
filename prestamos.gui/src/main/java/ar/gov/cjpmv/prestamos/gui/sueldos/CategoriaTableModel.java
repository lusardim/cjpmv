package ar.gov.cjpmv.prestamos.gui.sueldos;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.sueldos.AdministracionLiquidacionHaberes;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.Categoria;
import ar.gov.cjpmv.prestamos.gui.AdministracionFactory;

public class CategoriaTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1616093982361091933L;
	private AdministracionLiquidacionHaberes adminLiquidacionHaberes = AdministracionFactory.getInstance().getAdministracionLiquidacionHaberes();
	
	private List<Categoria> categorias;
	
	public CategoriaTableModel(EstadoPersonaFisica estado) {
		if (estado == null) {
			throw new NullPointerException("Debe especificarse el tipo de categoría que se desea");
		}
		actualizarListaCategoria(estado);
	}

	private void actualizarListaCategoria(EstadoPersonaFisica estado) {
		this.categorias = adminLiquidacionHaberes.getListaCategorias(estado);
	}

	@Override
	public int getColumnCount() {
		return 3;
	}
	
	@Override
	public int getRowCount() {
		if (categorias != null) {
			return categorias.size();
		}
		return 0;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
			case 0: return false;
			case 1: return true;
			case 2: return true;
		}
		return false;
	}

	@Override
	public String getColumnName(int column) {
		switch(column) {
			case 0: return "Categoria";
			case 1: return "Porcentaje sobre categoria";
			case 2: return "Monto";
		}
		return null;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		Categoria categoria = this.categorias.get(row);
		switch (column) {
			case 0: return categoria.toString();
			case 1: return categoria.getPorcentaje();
			case 2: return categoria.getTotal();
		}
		return null;
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Categoria categoria = this.categorias.get(row);
		BigDecimal valor = new BigDecimal(aValue.toString());
		switch (column) {
			case 1: categoria.setPorcentaje(valor); break;
			case 2: categoria.setMonto(valor); break;
		}
		
		if (categoria.getNumero().equals(1)) {
			fireTableDataChanged();
		}
		else {
			fireTableRowsUpdated(row, row);
		}
	}
	
	public void guardar() throws LogicaException {
		this.adminLiquidacionHaberes.guardar(categorias);
	}
	
	
}
