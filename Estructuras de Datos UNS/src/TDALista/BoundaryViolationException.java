package TDALista;

/**
 * Modela la excepción lanzada en los métodos de acceso cuando se intenta acceder/operar en las posiciones inicial y/o final.
 * @author Nicolás González
 *	@see PositionList
 */
@SuppressWarnings("serial")
public class BoundaryViolationException extends Exception {

	/**
	 * Inicializa la excepción indicando el origen del error.
	 * @param msg Especifica información adicional acerca de la excepción.
	 */
	public BoundaryViolationException(String msg) {
		super(msg);
	}
}
