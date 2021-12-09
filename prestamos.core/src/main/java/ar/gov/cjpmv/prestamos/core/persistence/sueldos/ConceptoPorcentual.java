package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.enums.EstadoPersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;

@Entity
public class ConceptoPorcentual extends ConceptoHaberes {

	private static final long serialVersionUID = -3192090138619842549L;
	@Basic(optional=false)
	@Column(columnDefinition="decimal(12,2)")
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private TipoCodigo sobreTotalTipo;
	@ManyToOne
	private Categoria sobreCategoria;
	
	public BigDecimal getValor() {
		if (valor == null) {
			valor = new BigDecimal(1);
		}
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public TipoCodigo getSobreTotalTipo() {
		return sobreTotalTipo;
	}
	public void setSobreTotalTipo(TipoCodigo sobreTotalTipo) {
		if (sobreTotalTipo != null) {
			sobreCategoria = null;
		}
		this.sobreTotalTipo = sobreTotalTipo;
	}
	public Categoria getSobreCategoria() {
		return sobreCategoria;
	}
	public void setSobreCategoria(Categoria sobreCategoria) {
		if (sobreCategoria != null) {
			sobreTotalTipo = null;
		}
		this.sobreCategoria = sobreCategoria;
	}
	
	@Override
	public BigDecimal getMonto(ReciboSueldo recibo) {
		BigDecimal total; 
		if (sobreTotalTipo != null) {
			total = new BigDecimal("0.00");
			for (LineaRecibo linea : recibo.getLineasRecibo()) {
				if (linea.getConcepto().getTipoCodigo().equals(sobreTotalTipo)) {
					total = total.add(linea.getTotal());
				}
			}
		}
		else { 
			Categoria categoria = getCategoria(recibo);
			if (categoria == null) {
				return null;
			}
			total = categoria.getTotal();
		}
		return total.multiply(getValor()).setScale(2, RoundingMode.HALF_UP);
	}
	
	private Categoria getCategoria(ReciboSueldo recibo) {
		//Significa que está asociado a la categoría de revista
		if (sobreCategoria == null && sobreTotalTipo == null) {
			return getCategoriaRevista(recibo);
		}
		
		if (recibo.getLiquidacion().getTipo().isActivo() && 
				sobreCategoria.getTipoPersona().equals(EstadoPersonaFisica.ACTIVO)) {
			return this.sobreCategoria;
		}

		return getServicio().getEquivalenciaCategoria(
				this.sobreCategoria, 
				recibo.getLiquidacion().getTipo());
	}

	private Categoria getCategoriaRevista(ReciboSueldo recibo) {
		TipoLiquidacion tipo = recibo.getLiquidacion().getTipo();
		PersonaFisica personaFisica;
		if (tipo.isPension()) {
			Pension pension = getServicio().getPension(recibo.getPersona());
			personaFisica = pension.getCausante();
		}
		else {
			personaFisica = recibo.getPersona();
		}
		return getServicio().getCategoria(personaFisica, tipo);
	}
}
