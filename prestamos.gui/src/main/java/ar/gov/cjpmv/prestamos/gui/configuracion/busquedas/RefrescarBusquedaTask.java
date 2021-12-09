package ar.gov.cjpmv.prestamos.gui.configuracion.busquedas;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class RefrescarBusquedaTask extends Task<Void, Void>{

		private AbstractBusquedaController controlador;
		
		
		public RefrescarBusquedaTask(AbstractBusquedaController controlador) {
			super(Application.getInstance());
			this.controlador = controlador;
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			this.controlador.getModelo().refrescarBusqueda();
			return null;
		}
		
		@Override
		protected void finished() {
			super.finished();
			
		}
}

