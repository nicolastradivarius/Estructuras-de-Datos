package TDAArbol;

/**
 * Modela la excepción lanzada en operaciones que no pueden ser realizadas por el TDA Árbol.
 * @author Nicolás González
 */
@SuppressWarnings("serial")
public class InvalidOperationException extends Exception {

	/**
	 * Inicializa la excepción indicando el origen del error.
	 * @param string Especifica información adicional acerca de la excepción.
	 */
	public InvalidOperationException(String string) {
		super(string);
	}
}
