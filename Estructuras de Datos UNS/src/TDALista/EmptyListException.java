package TDALista;

/**
 * Modela la excepción lanzada al momento de intentar acceder/operar con una lista vacía/sin posiciones.
 * @author Nicolás González
 *
 */
@SuppressWarnings("serial")
public class EmptyListException extends Exception {

	/**
	 * Inicializa la excepción con el mensaje predeterminado <i>&quotERROR: Lista vacía.&quot</i>
	 */
	public EmptyListException() {
		super("ERROR: Lista vacía.");
	}
}
