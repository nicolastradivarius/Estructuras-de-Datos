package TDAPila;

/**
 * Modela la excepción lanzada en operaciones de acceso a pilas con índices negativos
 * @author Nicolás González
 *
 */
@SuppressWarnings("serial")
public class NegativeStackIndexException extends Exception {

	/**
	 * Inicializa la excepción con los detalles pasados por parámetro.
	 * @param msg Información adicional sobre el error.
	 */
	public NegativeStackIndexException(String msg) {
		super(msg);
	}
}
