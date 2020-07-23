package TDAArbol;

/**
 * Modela las posiciones que conforman un árbol
 * @author Nicolás González
 * @param <E> Tipo de elemento de la interface Position
 */
public interface Position<E> {

	/**
	 * Devuelve el elemento contenido en la posición de un árbol.
	 * @return El elemento contenido en la posición de un árbol.
	 */
	public E element();
}
