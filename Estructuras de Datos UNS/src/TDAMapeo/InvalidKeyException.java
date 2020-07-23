package TDAMapeo;

/**
 * Modela la excepción lanzada en métodos de acceso/operación con entradas cuya clave es inválida.
 * @author Nicolás González.
 *
 */
@SuppressWarnings("serial")
public class InvalidKeyException extends Exception {

	/**
	 * Inicializa la excepción con el mensaje "Clave inválida".
	 */
	public InvalidKeyException() {
		super("Clave inválida");
	}
}
