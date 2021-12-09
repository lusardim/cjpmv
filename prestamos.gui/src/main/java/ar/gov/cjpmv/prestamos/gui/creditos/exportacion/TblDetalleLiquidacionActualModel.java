package ar.gov.cjpmv.prestamos.gui.creditos.exportacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleLiquidacion;

public class TblDetalleLiquidacionActualModel extends TblDetalleLiquidacionModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5459810782485207969L;

	private Map<DetalleLiquidacion,DetalleLiquidacion> cambiosRealizados = new HashMap<DetalleLiquidacion,DetalleLiquidacion>();
	
	public DetalleLiquidacion getDetalleLiquidacionPorCuentaCorriente(CuentaCorriente pCuentaCorriente){
		for (DetalleLiquidacion cadaDetalle : getLista()){
			if (cadaDetalle.getCuentaCorrienteAfectada().equals(pCuentaCorriente)){
				return cadaDetalle;
			}
		}
		return null;
	}
	
	public void agregarDetalleLiquidacion(DetalleLiquidacion pDetalle) {
		int nuevoLugar = getLista().size();
		getLista().add(pDetalle);
		cambiosRealizados.put(pDetalle,pDetalle);
		fireTableRowsInserted(nuevoLugar, nuevoLugar);
	}
	
	public void pisarDetalleLiquidacion(DetalleLiquidacion pDetalleAnterior, DetalleLiquidacion pDetalleNuevo){
		cambiosRealizados.put(pDetalleAnterior, pDetalleNuevo);
	}

	public void eliminarORestaurarDetalle(DetalleLiquidacion detalleNuevo) {
		DetalleLiquidacion detalleViejo = getDetalleViejo(detalleNuevo);
		cambiosRealizados.remove(detalleViejo);
		//Significa que el detalle fué agregado, no actualizado
		if (detalleViejo.equals(detalleNuevo)){
			getLista().remove(detalleNuevo);
		}
		fireTableDataChanged();
	}

	private DetalleLiquidacion getDetalleViejo(DetalleLiquidacion detalleNuevo) {
		for (Entry<DetalleLiquidacion,DetalleLiquidacion> cadaEntrada:	cambiosRealizados.entrySet())
		{
			if (cadaEntrada.getValue().equals(detalleNuevo)){
				return cadaEntrada.getKey();
			}
		}
		return null;
	}

	@Override
	protected Object getValueAt(DetalleLiquidacion detalle, int row, int column) {
		DetalleLiquidacion detalleNuevo = cambiosRealizados.get(detalle);
		if (detalleNuevo!=null){
			return super.getValueAt(detalleNuevo,row, column);
		}
		return super.getValueAt(detalle, row, column);
	}

	public DetalleLiquidacion[] getListaActual() {
		return (DetalleLiquidacion[])cambiosRealizados.values().toArray();
	}
	
	public Map<DetalleLiquidacion, DetalleLiquidacion> getCambiosRealizados() {
		return cambiosRealizados;
	}
}
