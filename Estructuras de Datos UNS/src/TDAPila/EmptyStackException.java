package TDAPila;

/**
 * Modela la excepción lanzada en casos de acceso/operación con pilas que están vacías.
 * @author Nicolás González
 */
@SuppressWarnings("serial")
public class EmptyStackException extends Exception {

	/**
	 * Inicializa la excepción indicando el origen del error.
	 * @param msg Especifica información adicional acerca de la excepción.
	 */
	public EmptyStackException(String msg) {
		super(msg);
	}
}