package ar.gov.cjpmv.prestamos.core.persistence.sueldos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ar.gov.cjpmv.prestamos.core.persistence.PersonaFisica;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.enums.TipoCodigo;

@Entity
public class ConceptoPrefijado extends ConceptoHaberes {

	public static final ConceptoPrefijado SUELDO_BASICO = new ConceptoPrefijado(1l, 1, "Básico", TipoCodigo.REMUNERATIVO);
	public static final ConceptoPrefijado PERMANENCIA = new ConceptoPrefijado(2l, 2, "Permanencia en categoria", TipoCodigo.REMUNERATIVO);
	public static final ConceptoPrefijado ANTIGUEDAD = new ConceptoPrefijado(3l, 3, "Antigüedad en categoria", TipoCodigo.REMUNERATIVO);
	public static final ConceptoPrefijado JUBILACION = new ConceptoPrefijado(4l, 4, "Nominal - Jublación", TipoCodigo.REMUNERATIVO);
	public static final ConceptoPrefijado PENSION = new ConceptoPrefijado(5l, 5, "Nominal - Pensión", TipoCodigo.REMUNERATIVO);
	
	private static final long serialVersionUID = 3019889804220598956L;
	@Transient
	private TipoLiquidacion tipoLiquidacion;

	private ConceptoPrefijado(
			Long id, 
			int codigo, 
			String descripcion,
			TipoCodigo tipoCodigo) 
	{
		super();
		this.setId(id);
		this.setCodigo(codigo);
		this.setDescripcion(descripcion);
		this.setTipoCodigo(tipoCodigo);
	}

	protected ConceptoPrefijado() {}
	
	public static List<ConceptoPrefijado> getConceptosPrefijados() {
		return Arrays.asList(new ConceptoPrefijado[]{SUELDO_BASICO, PERMANENCIA, ANTIGUEDAD});
	}
	
	/**
	 * Obtiene un concepto prefijado por código o nulo en caso de no existir
	 * @param codigo
	 */
	public static ConceptoPrefijado getConcepto(int codigo) {
		for (ConceptoPrefijado concepto : getConceptosPrefijados()) {
			if (concepto.getCodigo() == codigo) {
				return concepto;
			}
		}
		return null;
	}

	@Override
	public BigDecimal getMonto(ReciboSueldo recibo) {
		if (getCodigo() < 1 || getCodigo() > 5) {
			throw new IllegalArgumentException("El concepto prefijado no tiene un código válido");
		}
		this.tipoLiquidacion = recibo.getLiquidacion().getTipo();
		
		PersonaFisica persona = recibo.getPersona();
		BigDecimal valor = null;
		switch (getCodigo()) {
			case 1: {
				valor = getServicio().getBasico(persona, tipoLiquidacion);
				break;
			}
			case 2: {
				valor = getServicio().getPermanencia(persona, tipoLiquidacion);
				break;
			}
			case 3: {
				valor = getServicio().getAntiguedad(persona, tipoLiquidacion);
				break;
			}
			case 4: {
				valor = calcularJubilacion(persona);
				break;
			}
			case 5: {
				valor = calcularPension(persona);
				break;
			}
			
		}
		if (valor != null) {
			valor = valor.setScale(2, RoundingMode.HALF_UP);
		}
		return valor;
	}

	private BigDecimal calcularPension(PersonaFisica persona) {
		Pension pension = getServicio().getPension(persona);
		BigDecimal porcentajePension = getServicio().getPorcentajePension();
		
		if (pension == null) {
			throw new RuntimeException( 
					"La persona "+persona.getNombreYApellido()+
					" legajo: "+persona.getLegajo()+
					" No posee pensión");
		}
		BigDecimal jubilacion = calcularJubilacion(pension.getCausante());
		if (jubilacion == null) {
			return null;
		}
		BigDecimal valor = (pension.getValor() != null)?pension.getValor():new BigDecimal(1);
		return jubilacion.multiply(valor)
						 .multiply(porcentajePension);
	}

	private BigDecimal calcularJubilacion(PersonaFisica persona) {
		BigDecimal basico = getBasico(persona);
		if (basico == null) {
			return null;
		}
		BigDecimal permanencia = getServicio().getPermanencia(persona, tipoLiquidacion);
		BigDecimal antiguedad = getServicio().getAntiguedad(persona, tipoLiquidacion);
		BigDecimal porcentajeSobreLiquidacion = getServicio().getPorcentajeJubilacionSobreLiquidacion(persona);
		BigDecimal porcentajeJubilacion = getServicio().getPorcentajeJubilacion();
		
		BigDecimal cero = new BigDecimal("0.00");
		
		if (permanencia == null) {
			permanencia = cero;
		}

		if (antiguedad == null) {
			antiguedad = cero;
		}
		
		if (porcentajeSobreLiquidacion == null) {
			porcentajeSobreLiquidacion = new BigDecimal("1.00");
		}
		
		return basico
				.add(permanencia)
				.add(antiguedad)
				.multiply(porcentajeSobreLiquidacion)
				.multiply(porcentajeJubilacion);
	}

	private BigDecimal getBasico(PersonaFisica persona) {
		return getServicio().getBasico(persona, this.tipoLiquidacion);
	}

}

