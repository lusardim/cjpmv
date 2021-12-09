package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ConceptoFijo extends ConceptoHaberes {
	
	private static final long serialVersionUID = 5444728022309183466L;

	@Column(columnDefinition="decimal(10,2)")
	private BigDecimal valor;
	private boolean sobreescribirValor;
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public boolean isSobreescribirValor() {
		return sobreescribirValor;
	}
	public void setSobreescribirValor(boolean sobreescribirValor) {
		this.sobreescribirValor = sobreescribirValor;
	}
	
	@Override
	public BigDecimal getMonto(ReciboSueldo recibo) {
		if (sobreescribirValor) {
			return valor;
		}
		else {
			//en este caso no aparece en las plantillas, tiene que estar asociado a un valor
			int anio = recibo.getLiquidacion().getAnio();
			int mes = recibo.getLiquidacion().getMes();
			BigDecimal valor = getServicio()
					.getValorFijoParaLiquidacionAnterior(anio, mes, this, recibo.getPersona());
			if (valor == null) {
				valor = new BigDecimal(0);
			}
			return valor.setScale(2, RoundingMode.HALF_UP);
		}
	}
}
