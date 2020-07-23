package TDACola;

/**
 * Modela la excepción lanzada en casos de acceso/operación con colas vacías.
 * @author Nicolás González
 */
@SuppressWarnings("serial")
public class EmptyQueueException extends Exception {

	/**
	 * Inicializa la excepción indicando el origen del error.
	 * @param msg Especifica información adicional acerca de la excepción.
	 */
	public EmptyQueueException(String msg) {
		super(msg);
	}
}