package TDAArbol;

/**
 * Modela la excepción lanzada en operaciones de acceso/modificación de árboles vacíos.
 * @author Nicolás González
 *
 */
@SuppressWarnings("serial")
public class EmptyTreeException extends Exception {

	/**
	 * Inicializa la excepción indicando el origen del error.
	 * @param string Especifica información adicional acerca de la excepción.
	 */
	public EmptyTreeException(String string) {
		super(string);
	}
}
