package ar.gov.cjpmv.prestamos.gui.creditos.datos.cheques.model;

import ar.gov.cjpmv.prestamos.core.persistence.prestamo.Cheque;

public class ChequeModel {
	
	private Cheque cheque;
	private boolean imprimir;
	private boolean personaSeleccionada;
	
	public ChequeModel(Cheque cheque){
		this.cheque = cheque;
	}

	public boolean isImprimir() {
		return imprimir;
	}

	public void setImprimir(boolean imprimir) {
		this.imprimir = imprimir;
	}

	public Cheque getCheque() {
		return cheque;
	}

	public boolean isPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(boolean personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cheque == null) ? 0 : cheque.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChequeModel other = (ChequeModel) obj;
		if (cheque == null) {
			if (other.cheque != null)
				return false;
		} else if (!cheque.equals(other.cheque))
			return false;
		return true;
	}	
}
