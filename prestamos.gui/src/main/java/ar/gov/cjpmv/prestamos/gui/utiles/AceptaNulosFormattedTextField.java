package ar.gov.cjpmv.prestamos.gui.utiles;

import java.text.Format;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class AceptaNulosFormattedTextField extends JFormattedTextField {
	
	private String texto;
	
	public AceptaNulosFormattedTextField() {
		super();
	}
	
	public AceptaNulosFormattedTextField(Format pFormato){
		super(pFormato);
	}
	public AceptaNulosFormattedTextField(MaskFormatter pFormato) {
		super(pFormato);
		this.texto = this.getText();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 388530429865053861L;

	
	@Override
	public void commitEdit() throws ParseException {
		if (this.getText().length() == 0){
			this.setValue(null);
		}
		else if (this.texto!=null){
			if (this.getText().equals(this.texto)){
				this.setValue(null);
			}
			else{
				super.commitEdit();
			}
		}
		else{
			super.commitEdit();
		}
		//TODO PARA MI PRINCESITA DE CUATIVANTES MIRADAS
		//TE REGALO UN MÈTODO REPLETO DE AMOR, PASIÓN Y LOCURA
		//PARA QUE LO USES COMO MÁS QUIERAS 
		//TI ADOROU MUCHO TODO FIXME! mi pulpito ENCANTADOR
	//	Utiles.nuloSiVacioConCammelCase(pCadena)	
		
	}
	/*@Override
	protected void processFocusEvent(FocusEvent e) {
		if (e.getID() == FocusEvent.FOCUS_LOST){
			if (this.getText().length() == 0){
				this.setValue(null);
			}
			else if (this.texto!=null){
				if (this.getText().equals(this.texto)){
					this.setValue(null);
				}
			}
		}
		super.processFocusEvent(e);
	}*/
}
