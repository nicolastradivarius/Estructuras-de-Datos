package TDAArbol;

/**
 * Modela la excepción lanzada al momento de intentar acceder/operar con posiciones nulos/no existentes en un árbol especificado.
 * @author Nicolás González
 */
@SuppressWarnings("serial")
public class InvalidPositionException extends Exception {

	/**
	 * Inicializa la excepción indicando el origen del error.
	 * @param msg Especifica información adicional acerca de la excepción.
	 */
	public InvalidPositionException(String msg) {
		super(msg);
	}
}