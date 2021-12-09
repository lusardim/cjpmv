package ar.gov.cjpmv.prestamos.gui.reportes.enums;

import java.io.File;
import java.io.InputStream;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;

public enum Reportes {

	PERSONAS_PASIVAS("reportes/personasFisicasPasivas.jasper", "Nómina de personas pasivas"),
	PERSONAS_EMPLEADOS("reportes/personasFisicasActivasEmpleados.jasper","Nómina de empleados"),
	PERSONAS_PASIVASJOC("reportes/personasFisicasPasivasJOC.jasper", "Nómina de personas pasivas: JOC"),
	PERSONAS_PASIVASJI("reportes/personasFisicasPasivasJI.jasper", "Nómina de personas pasivas: JI"),
	PERSONAS_PASIVASJEA("reportes/personasFisicasPasivasJEA.jasper", "Nómina de personas pasivas: JEA"),
	PERSONAS_PASIVASJA("reportes/personasFisicasPasivasJA.jasper", "Nómina de personas pasivas: JA"),
	PERSONAS_PASIVASP("reportes/personasFisicasPasivasP.jasper", "Nómina de personas pasivas: PENSIÓN"),
	ESTADO_CUENTA("reportes/estadoCuenta.jasper", "Estado de Cuenta"),
	FORMULARIO_F01("reportes/formulariof01.jasper", "Formulario F01"),
	FORMULARIO_F02("reportes/formulariof02.jasper", "Formulario F02"),
	RECIBO_CREDITO("reportes/recibo.jasper", "Recibo"),
	CHEQUE_BERSA("reportes/chequeBersa.jasper", "Cheque BERSA"),
	CHEQUE_SANTANDER_RIO("reportes/chequeSantanderRio.jasper", "Cheque SANTANDER RIO"),
	OTORGAMIENTO_CREDITO("reportes/otorgamientoCredito.jasper", "Otorgamiento de Crédito"),
	CONCEPTO_NO_APLIC_CUOTAS("reportes/detalleConceptoCredito.jasper", "Detalle de conceptos no aplicados a cuotas"),
	DETALLE_CUOTAS("reportes/detalleCuotas.jasper", "Detalle de Cuotas"),
	COBRO_POR_BANCO("reportes/cobroDepositoBancario.jasper", "Cobro por Depósito Bancario"),
	PLANILLA_LIQUIDACION_MUNICIPALIDAD("reportes/planillaLiquidacionMunicipalidad.jasper", "Liquidacion de cuotas de créditos a Municipalidad"),
	PLANILLA_COBRO_HABERES_CAJA("reportes/planillaLiquidacionCaja.jasper", "Cobro de cuotas por Liquidación de Haberes a la Caja"),
	SALDO_TIPO_CREDITO("reportes/saldoTipoCredito.jasper", "Informe sobre Saldos según Tipo de Crédito"),
	SALDO_DETALLADO_TIPO_CREDITO("reportes/saldoDetalladoTipoCredito.jasper", "Informe detallado sobre Saldos según Tipo de Crédito"),
	SALDO_POR_PERIODO("reportes/saldoPorPeriodo.jasper", "Informe detallado sobre Saldos por Periodo"),
	CREDITOS_OTORGADOS_POR_PERIODO("reportes/creditosOtorgadosPorPeriodo.jasper", "Informe sobre Créditos otorgados por Período"),
	COBRANZAS_POR_PERIODO_Y_VIA("reportes/cobranzas.jasper", "Informe sobre cobranzas por período y via"),
	SALDO_CAPITAL_POR_TIPO_CREDITO("reportes/capitalAdeudado.jasper", "Informe sobre Capital Adeudado por Tipo de Crédito"),
	REPORTE_COBROS_POR_BANCO("reportes/reporteCobrosPorBanco.jasper", "Cobros por Banco"),
	REPORTE_RECIBO_HABERES("reportes/reciboHaberes.jasper", "Recibo de Haberes");
	
	
	
	private File archivo;
	private String titulo;
	private String archivoReporte;
	
	private Reportes(String pArchivoReportes,String pNombreReporte){
		this.archivoReporte = pArchivoReportes;
		this.archivo = new File(pArchivoReportes);
		/*this.archivo = new File("reportes/"+pArchivoReportes);*/
		this.titulo = pNombreReporte;
	}
	
	public File getArchivo() throws LogicaException {
		if (!archivo.exists()){
			throw new LogicaException(51,null);
		}
		return archivo;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public InputStream getStream(){
		return this.getClass().getResourceAsStream("/"+this.archivoReporte);
	}
}
