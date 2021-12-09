package ar.gov.cjpmv.prestamos.core.business.sueldos;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ar.gov.cjpmv.prestamos.core.persistence.sueldos.ConceptoPrefijado;

public class AdministracionConceptoHaberesTest {
	
	private AdministracionConceptoHaberes administracionConceptoHaberes;
	private ConceptoHaberesDAO dao = mock(ConceptoHaberesDAO.class);

	public AdministracionConceptoHaberesTest() {
		administracionConceptoHaberes = new AdministracionConceptoHaberes();
		administracionConceptoHaberes.setConceptoHaberesDAO(dao);
		for (ConceptoPrefijado cadaConcepto : ConceptoPrefijado.getConceptosPrefijados()) {
			when(dao.getConceptoPorCodigo(cadaConcepto.getCodigo())).thenReturn(cadaConcepto);
		}
	}
	
	
}
