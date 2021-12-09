package ar.gov.cjpmv.prestamos.gui.creditos.datos.creditos.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import ar.gov.cjpmv.prestamos.core.business.exceptions.LogicaException;
import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema;
import ar.gov.cjpmv.prestamos.core.business.prestamos.Sistema.TipoSistema;
import ar.gov.cjpmv.prestamos.core.facades.CreditoFacade;
import ar.gov.cjpmv.prestamos.core.persistence.ConfiguracionSistema;
import ar.gov.cjpmv.prestamos.core.persistence.Persona;
import ar.gov.cjpmv.prestamos.core.persistence.enums.TipoFormulario;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Concepto;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Credito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.CuentaCorriente;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cuota;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.DetalleCredito;
import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Garantia;
import ar.gov.cjpmv.prestamos.gui.comunes.MontoCellRenderer;

public class CreditoModel {
	
	public static enum EventoCreditoModel{
		CAMBIO_CUENTA_CORRIENTE, LIMPIAR_DATOS
	}
	
	private PropertyChangeSupport propertyChangeSupport=new PropertyChangeSupport(this);
	
	protected CreditoFacade facade;
	
	private Credito credito;
	private CuentaCorriente cuentaCorriente;
	
	private Sistema sistema;
	private ComboBoxModel modeloTipoCredito;
	private ComboBoxModel modeloViaCobro;
	private ComboBoxModel modeloSistema;
	private TipoFormulario tipoFormulario= TipoFormulario.F01;
	
	private TblCuotasModel modeloTablaCuotas;

	private TblConceptoCreditoModel modeloConceptosCredito;
	private TblConceptosParticularesModel modeloConceptosParticulares;
	
	private Date fechaPrimerVencimiento;
	
	public CreditoModel(Credito pCredito) {
		this.facade = new CreditoFacade();
		this.credito = pCredito;
		this.setFechaPrimerVencimiento(Calendar.getInstance().getTime());
		this.inicializarModeloTipoCredito();
		this.inicializarModeloViaCobro();
		this.incializarModeloSistema();
		this.inicializarModelosTablas();
		
	}

	private void inicializarModelosTablas() {
		this.modeloTablaCuotas = new TblCuotasModel();
		this.modeloConceptosCredito = new TblConceptoCreditoModel();
		this.modeloConceptosParticulares = new TblConceptosParticularesModel(this.credito);
	}

	private void incializarModeloSistema() {
		this.modeloSistema = new DefaultComboBoxModel(TipoSistema.values());
	}

	private void inicializarModeloViaCobro() {
		this.modeloViaCobro = new DefaultComboBoxModel(this.facade.getListaViasCobro().toArray());
	}

	private void inicializarModeloTipoCredito() {
		this.modeloTipoCredito = new DefaultComboBoxModel(facade.getListaTiposCreditos().toArray());
	}

	public Credito getCredito() {
		if (this.credito==null){
			this.credito = new Credito();
		}
		return credito;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public ComboBoxModel getModeloTipoCredito() {
		return modeloTipoCredito;
	}

	public ComboBoxModel getModeloViaCobro() {
		return modeloViaCobro;
	}

	public ComboBoxModel getModeloSistema() {
		return modeloSistema;
	}
	
	public boolean isGenerarCuotasHabilitado(){
		Credito locCredito = this.getCredito();
		boolean habilitado = false;
		habilitado = locCredito.getCantidadCuotas()!=null && locCredito.getCantidadCuotas()>0;
		habilitado = habilitado && locCredito.getTasa()!=null && locCredito.getTasa().floatValue()>=0;
		habilitado = habilitado && locCredito.getFechaInicio()!=null;
		habilitado = habilitado && locCredito.getMontoTotal() != null && locCredito.getMontoTotal().floatValue()>0;
		habilitado = habilitado && this.sistema != null;
		return habilitado;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setTipoSistema(TipoSistema sistema) throws LogicaException {
		if (sistema !=null){
			this.sistema = Sistema.getSistema(sistema);
		}
		else{
			this.sistema = null;
		}
	}

	/**
	 * Genera la lista de cuotas y actualiza la tabla
	 * @throws LogicaException 
	 */
	public void generarCuotas() throws LogicaException {
		List<Cuota> locListaCuotas = this.sistema.calcularCuotas(this.credito,getFechaPrimerVencimiento());
		this.credito.setListaCuotas(locListaCuotas);
		this.modeloTablaCuotas.setListaCuotas(locListaCuotas);
		this.credito.actualizarMontoAPagar();
	}

	/**
	 * Aplica todos los conceptos que están relacionados con las cuotas y con el crédito AMBOS SON APLICADOS AL TOTAL 
	 * @param locListaCuotas
	 */
	public void aplicarConceptos() {
		//Si las cuotas son nulas significa que nunca fué calculado nada.
		if ((this.credito.getListaCuotas()!=null) && (!this.credito.getListaCuotas().isEmpty())){

			List<Concepto> locListaConceptosCuotas = this.modeloConceptosCredito.getListaConceptosAplicadosCuotas();
			
			BigDecimal capitalRestante = this.credito.getMontoTotal();
			BigDecimal diferenciaAcumuladaRedondeoConceptos = new BigDecimal(0);
			
			for (Cuota cadaCuota : this.credito.getListaCuotas()){
				cadaCuota.getListaDetallesCreditos().clear();
				cadaCuota.setOtrosConceptos(new BigDecimal(0));
				//formula de capital restante (para el calculo de otros conceptos de las cuotas) 
				capitalRestante = capitalRestante.subtract(cadaCuota.getCapital());
				
				BigDecimal total = new BigDecimal(0);
				
				for (Concepto cadaConcepto :  locListaConceptosCuotas){
					//Los detalles de las cuotas se aplican sobre el capital restante
					DetalleCredito valor = this.crearDetalleCredito(cadaConcepto, capitalRestante);
					valor.setCuota(cadaCuota);
					cadaCuota.add(valor);
					total = total.add(valor.getValor());
				}
				
				if (this.credito.getListaCuotas().indexOf(cadaCuota) == this.credito.getListaCuotas().size()-1){
					DetalleCredito ultimoRedondeo = this.aplicarConceptoRedondeo(diferenciaAcumuladaRedondeoConceptos);
					cadaCuota.add(ultimoRedondeo);
				}
				else{
					DetalleCredito redondeo = this.aplicarConceptoRedondeo(total);
					cadaCuota.add(redondeo);
					diferenciaAcumuladaRedondeoConceptos = diferenciaAcumuladaRedondeoConceptos.add(redondeo.getValor());	
				}
			}
			this.credito.getDetalleCredito().clear();
			
			for (Concepto cadaConcepto :  this.modeloConceptosCredito.getListaConceptosAplicadosCredito()){
				DetalleCredito valor = this.crearDetalleCredito(cadaConcepto, this.credito.getMontoTotal());
				valor.setCredito(this.credito);
				this.credito.getDetalleCredito().add(valor);
			}
		
			this.credito.getDetalleCredito().addAll(this.modeloConceptosParticulares.getListaDetallesCreditos());
		}
		this.credito.actualizarMontoAPagar();
		this.modeloTablaCuotas.fireTableDataChanged();
	}
	
	/**
	 * Crea un detalle de credito de rendondeo para cada una de las cuotas 
	 * @param diferenciaRedondeo
	 * @return 
	 */
	private DetalleCredito aplicarConceptoRedondeo(BigDecimal diferenciaRedondeo) {
		DetalleCredito locDetalle = new DetalleCredito();
		locDetalle.setNombre("Redondeo de cuota");
		locDetalle.setCredito(this.credito);

		BigDecimal diferencia = diferenciaRedondeo.setScale(0,RoundingMode.HALF_UP).subtract(diferenciaRedondeo);
		locDetalle.setValor(diferencia);
		return locDetalle;
	}

	/**
	 * Crea el detalle de crédito a partir del concepto y del valor al cual es aplicado.
	 * @param pConcepto
	 * @param montoTotal
	 * @return
	 */
	private DetalleCredito crearDetalleCredito(Concepto pConcepto, BigDecimal montoTotal) {
		DetalleCredito locDetalle = new DetalleCredito();
		BigDecimal valor;
		if (pConcepto.getPorcentual()){
			valor = montoTotal.multiply(pConcepto.getValor())
					.divide(new BigDecimal(100),RoundingMode.HALF_UP);
		}
		else{
			valor = pConcepto.getValor();
		}
		locDetalle.setValor(valor);
		locDetalle.setFuente(pConcepto);
		locDetalle.setNombre(pConcepto.getDescripcion());
		locDetalle.setEmiteCheque(pConcepto.getEmiteCheque());
		return locDetalle;
	}

	public TblCuotasModel getModeloTablaCuotas() {
		return modeloTablaCuotas;
	}

	public TblConceptoCreditoModel getModeloConceptosCredito() {
		return modeloConceptosCredito;
	}

	public TblConceptosParticularesModel getModeloConceptosParticulares() {
		return this.modeloConceptosParticulares;
	}
	
	public void guardar() throws LogicaException{
		//TODO VALIDAR GARANTIAS
		if (this.getCredito().getId() == null){
			this.facade.guardar(this.credito);
		}
	}

	public void limpiarDatos(){
		propertyChangeSupport.firePropertyChange(EventoCreditoModel.LIMPIAR_DATOS.toString(),null,null);

		this.credito = new Credito();
		this.modeloConceptosCredito.setListaConceptos(new ArrayList<Concepto>());
		this.modeloConceptosParticulares.limpiar();
		this.modeloSistema.setSelectedItem(null);
		this.modeloTipoCredito.setSelectedItem(null);
		this.modeloViaCobro.setSelectedItem(null);
		this.modeloTablaCuotas.setListaCuotas(new ArrayList<Cuota>());
		this.setValoresDefecto();

	}
	
	/**
	 * Busca y setea los valores por defecto en el crédito
	 * @throws LogicaException 
	 */
	public void setValoresDefecto() {
		try{
			ConfiguracionSistema locConfiguracionSistema =  this.facade.getConfiguracionSistema();
			this.sistema = Sistema.getSistema(locConfiguracionSistema.getSistemaPorDefecto());
			this.credito.setTasa(locConfiguracionSistema.getInteresPorDefecto());
			this.credito.setNumeroCredito(this.facade.getUltimoNumeroCredito()+1);
		}
		catch(LogicaException e){
			e.printStackTrace();
			//si la configuración no está cargada, que cargue vacio en todo
		}
	}

	public void setPersona(Persona personaSeleccionada){
		try{
			if (personaSeleccionada == null){
				throw new LogicaException(41, "Cuenta corriente");
			}
			this.cuentaCorriente = this.facade.getCuentaCorriente(personaSeleccionada);
			if (!this.cuentaCorriente.getListaCredito().contains(this.credito)){
				this.cuentaCorriente.getListaCredito().add(this.credito);
			}
			this.credito.setCuentaCorriente(this.cuentaCorriente);
			this.propertyChangeSupport.firePropertyChange(EventoCreditoModel.CAMBIO_CUENTA_CORRIENTE.toString(),null,this.cuentaCorriente);
		}
		catch(LogicaException e){
			this.cuentaCorriente = null;
			this.credito.setCuentaCorriente(null);
			this.propertyChangeSupport.firePropertyChange(EventoCreditoModel.LIMPIAR_DATOS.toString(),null,null);
		}
	}

	public CuentaCorriente getCuentaCorriente() {
		return cuentaCorriente;
	}

	@Deprecated
	public void mostrarModelo() {
		System.out.println("Modelo credito "+this.cuentaCorriente.getPersona());
		System.out.println("Cantidad de cuotas "+this.credito.getCantidadCuotas());
		System.out.println("lista de cuotas");
		for (int i = 0; i<this.credito.getListaCuotas().size(); i++){
			Cuota cadaCuota = (Cuota)this.credito.getListaCuotas().get(i);
			System.out.println(i+" - "+cadaCuota.getTotal());
		}
		
		System.out.println("detalle\t\tclase");
		System.out.println(this.credito.getListaGarantias().size());
		for (Garantia cadaGarantia : this.credito.getListaGarantias()){
			System.out.print(cadaGarantia.getDetalle());
			System.out.println("\t\tclase "+cadaGarantia.getClass());
		}
	}
	
	/**
	 * Genera la lista de cheques
	 * @return
	 */
	public List<Cheque> generarListaCheques(){
		if (this.credito.getCuentaCorriente()!=null){
			List<Cheque> locListaCheques = this.facade.generarCheques(this.credito);
			//TODO ELIMINAR	
			for (Cheque cadaCheque : locListaCheques){
				System.out.println(cadaCheque.getNumero()+"   "+cadaCheque.getMonto());
			}
			if (locListaCheques!=null){
				this.credito.getListaCheques().clear();
				this.credito.getListaCheques().addAll(locListaCheques);
			}
			return locListaCheques;
		}
		return null;
	}
	
	
	public Persona getPersona(){
		if (this.credito.getCuentaCorriente()==null){
			return null;
		}
		return this.credito.getCuentaCorriente().getPersona();
	}

	/**
	 * Valida que esté todo ok para la primer etapa, ántes del cheque
	 * @throws LogicaException
	 */
	public void validar() throws LogicaException {
		this.facade.validarCredito(this.getCredito());
		this.facade.validarCheques(this.getCredito());
	}

	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public BigDecimal getMontoEntrega() {
		return getCredito().getMontoEntrega();
	}

	public Date getFechaPrimerVencimiento() {
		return fechaPrimerVencimiento;
	}

	public void setFechaPrimerVencimiento(Date fechaPrimerVencimiento) {
		this.fechaPrimerVencimiento = fechaPrimerVencimiento;
	}

	public void setModeloConceptosParticulares(
			TblConceptosParticularesModel modeloConceptosParticulares) {
		this.modeloConceptosParticulares = modeloConceptosParticulares;
	}

}
