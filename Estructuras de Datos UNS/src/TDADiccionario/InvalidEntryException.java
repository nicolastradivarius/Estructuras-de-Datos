package TDADiccionario;

/**
 * Modela la excepción lanzada al momento de operar con entradas inválidas, esto es, entradas nulas, entradas con valor nulo, etc.
 * @author Nicolás González
 *
 */
@SuppressWarnings("serial")
public class InvalidEntryException extends Exception {
	
	/**
	 * Inicializa la excepción con los detalles indicados por la operación que la lanza.
	 * @param msg Razón del lanzamiento de la excepción
	 */
	public InvalidEntryException(String msg) {
		super(msg);
	}

}
