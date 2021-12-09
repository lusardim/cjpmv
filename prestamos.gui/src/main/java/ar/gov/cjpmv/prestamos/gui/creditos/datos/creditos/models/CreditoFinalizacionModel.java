package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JOptionPane;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CobroPorCancelacionCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;

public class CreditoFinalizacionModel extends CreditoModel{

	private List<Credito> listaCreditosSeleccionados;
	private BigDecimal valorTotalFinalizacion = new BigDecimal(0);
	
	private DetalleCredito detalleCreditoFinalizacion;
	
	public CreditoFinalizacionModel(Credito pCredito) {
		super(pCredito);
	}

	@Override
	public void guardar() throws LogicaException {
		if (this.getCredito().getId() == null){
			//El monto de entrega se modifica porque se llama a aplicar conceptos una vez
			//que la tabla se modifica, por eso lo guardamos y lo volvemos a setear
			BigDecimal montoEntrega = getCredito().getMontoEntrega();
			this.getModeloConceptosParticulares().eliminar(this.detalleCreditoFinalizacion);
			getCredito().setMontoEntrega(montoEntrega);
			
			this.facade.guardarCreditoPorFinalizacion(
					this.getCredito(),
					getCuentaCorriente(),
					this.listaCreditosSeleccionados,
					detalleCreditoFinalizacion);
		}
	}
	
	public void setListaSeleccionados(List<Credito> listaSeleccionados) {
		this.listaCreditosSeleccionados = listaSeleccionados;
		
		BigDecimal valorAnterior = this.getMontoEntrega();
		if (this.valorTotalFinalizacion.floatValue() != 0) {
			valorAnterior = valorAnterior.add(valorTotalFinalizacion);
		}
		
		this.valorTotalFinalizacion = new BigDecimal(0);
		for (Credito credito : this.listaCreditosSeleccionados){
			valorTotalFinalizacion = valorTotalFinalizacion.add(credito.getSaldoAdeudado());
		}
		
		this.getCredito().setMontoEntrega(valorAnterior.subtract(valorTotalFinalizacion));
	}

	public void setMontoCancelacion(BigDecimal montoCancelacion) {
		if (detalleCreditoFinalizacion != null) {
			this.getModeloConceptosParticulares().eliminar(detalleCreditoFinalizacion);
		}
		detalleCreditoFinalizacion = new DetalleCredito();
		detalleCreditoFinalizacion.setCredito(getCredito());
		detalleCreditoFinalizacion.setEmiteCheque(false);
		detalleCreditoFinalizacion.setNombre("Cancelación de créditos anteriores");
		detalleCreditoFinalizacion.setValor(montoCancelacion);
		this.getModeloConceptosParticulares().add(detalleCreditoFinalizacion, true);
		aplicarConceptos();
	}
}
