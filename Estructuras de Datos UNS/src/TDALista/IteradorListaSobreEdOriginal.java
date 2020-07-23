package TDALista;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase que modela un iterador de lista. <br>
 * El iterador se modela internamente con <i>punteros</i> a los elementos de lista original.<br>
 * Provee la funcionalidad de un iterador, realizando toda la operatoria sobre la ED original. <br>
 * <b>No asegura</b> un correcto funcionamiento si durante el proceso de iteración la lista es modificada.</br>
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 * @param <E> Tipo de dato de los elementos de la lista a iterar.
 */
public class IteradorListaSobreEdOriginal<E> implements Iterator<E>{

	private PositionList<E> lista;
	private Position<E> cursor;

	/**
	 * Inicializa la ED interna considerando inicialmente el primer elemento de la lista.
	 * @param l Contiene los elementos a ser iterados.
	 */
	public IteradorListaSobreEdOriginal(PositionList<E> l) {
		try {
			lista = l;
			if (lista != null && !lista.isEmpty())
				cursor = l.first();
		}catch (EmptyListException e) {
			cursor = null;
		}
	}

	/**
	 * Verifica que la iteración tenga más elementos (o dicho de otro modo, <code>hasNext()</code> es <i>true</i> si la operación <code> next()</code> devuelve un elemento
	 * en lugar de lanzar una excepción).
	 * @return <i>true</i> si el iterador sigue teniendo elementos por recorrer.
	 */
	public boolean hasNext() {
		return cursor != null;		
	}

	/**
	 * Devuelve el elemento siguiente del iterador modelado sobre la lista original.
	 * @return El elemento siguiente en la iteración.
	 * @throws NoSuchElementException si la iteración no tiene más elementos.
	 */
	public E next() throws NoSuchElementException{ 
		if ( cursor == null ) throw new NoSuchElementException ("Error: No hay elementos para iterar.");

		E toReturn = cursor.element(); // Salvo el elemento corriente
		try {
			cursor = (cursor == lista.last()) ? null : lista.next(cursor);

		} catch (EmptyListException e) {
			e.getMessage();
		} catch (InvalidPositionException e) {
			e.getMessage();
		} catch (BoundaryViolationException e) {
			e.getMessage();
		} 
		return toReturn; 
	}
}
