package TDALista;

/**
 * Modela las posiciones que conforman una lista.
 * @author Nicolás González
 * @see PositionList
 * @param <E> Tipo de elemento de la interface Position
 */
public interface Position<E> {

	/**
	 * Devuelve el elemento contenido en la posición de una lista.
	 * @return El elemento contenido en la posición de una lista.
	 */
	public E element();
}
