package ar.gov.cjpmv.prestamos.gui.reportes;

public class ImpresionCheque {
	private String montoNumero;
	private String dia;
	private String mes;
	private String anio;
	private String nombrePersona;
	private String montoLetra;
	private String montoLetraContinuacion;
	private String diaDiferido;
	private String mesDiferido;
	private String anioDiferido;
	private String banco;
	
	
	public ImpresionCheque(String montoNumero, String dia, String mes, String anio,
			String nombrePersona, String montoLetra,
			String montoLetraContinuacion, String diaDiferido,
			String mesDiferido, String anioDiferido, String banco) {
		super();
		this.montoNumero = montoNumero;
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
		this.nombrePersona = nombrePersona;
		this.montoLetra = montoLetra;
		this.montoLetraContinuacion = montoLetraContinuacion;
		this.diaDiferido = diaDiferido;
		this.mesDiferido = mesDiferido;
		this.anioDiferido = anioDiferido;
		this.setBanco(banco);
	}
	
	public String getMontoNumero() {
		return montoNumero;
	}
	public void setMontoNumero(String montoNumero) {
		this.montoNumero = montoNumero;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	public String getMontoLetra() {
		return montoLetra;
	}
	public void setMontoLetra(String montoLetra) {
		this.montoLetra = montoLetra;
	}
	public String getMontoLetraContinuacion() {
		return montoLetraContinuacion;
	}
	public void setMontoLetraContinuacion(String montoLetraContinuacion) {
		this.montoLetraContinuacion = montoLetraContinuacion;
	}
	public String getDiaDiferido() {
		return diaDiferido;
	}
	public void setDiaDiferido(String diaDiferido) {
		this.diaDiferido = diaDiferido;
	}
	public String getMesDiferido() {
		return mesDiferido;
	}
	public void setMesDiferido(String mesDiferido) {
		this.mesDiferido = mesDiferido;
	}
	public String getAnioDiferido() {
		return anioDiferido;
	}
	public void setAnioDiferido(String anioDiferido) {
		this.anioDiferido = anioDiferido;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getBanco() {
		return banco;
	}
	
	
	
	

}
