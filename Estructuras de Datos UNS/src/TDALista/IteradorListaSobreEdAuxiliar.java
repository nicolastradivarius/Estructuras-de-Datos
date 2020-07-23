package TDALista;

import java.util.Iterator;
import java.util.NoSuchElementException;
import TDACola.*;

/**
 * Clase que modela un iterador de lista. <br>
 * El iterador se modela internamente con una <i>ED Auxiliar</i> a la lista, esto es, se realiza una clonación de los elementos de la lista sobre una ED Auxiliar.<br>
 * Provee la funcionalidad de un iterador, realizando toda la operatoria sobre la ED Auxiliar. <br>
 * <b>Requiere:</b> <i>TDACola</i>.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 * @param <E> Tipo de dato de los elementos de la lista a iterar.
 */
public class IteradorListaSobreEdAuxiliar<E> implements Iterator<E>{
	private Queue<E> cola;

	/**
	 * Clona en una estructura auxiliar todos los elementos de lista.
	 * @param lista Contiene los elementos a ser iterados.
	 */
	public IteradorListaSobreEdAuxiliar(PositionList<E> lista) {
		//La estructura auxiliar utilizada para la iteración en este caso se implementará mediante una cola con enlaces.
		cola = new Cola_con_arreglo_circular<E>();
		try {
			Position<E> cursor = lista.isEmpty() ? null : lista.first();
			while(cursor != null) {
				cola.enqueue(cursor.element());
				cursor = (cursor == lista.last()) ? null : lista.next(cursor);
			}
		}
		catch(EmptyListException e) { 
			cola = new Cola_con_arreglo_circular<E>();
		}
		catch(InvalidPositionException e1) {
			cola = new Cola_con_arreglo_circular<E>();
		} 
		catch(BoundaryViolationException e2) {
			cola = new Cola_con_arreglo_circular<E>();
		}
	}  

	/**
	 * Verifica que la iteración tenga más elementos (o dicho de otro modo, <code>hasNext()</code> es <i>true</i> si la operación <code> next()</code> devuelve un elemento
	 * en lugar de lanzar una excepción).
	 * @return <i>true</i> si el iterador sigue teniendo elementos por recorrer.
	 */
	public boolean hasNext() {
		return !(cola.isEmpty());
	}

	/**
	 * Devuelve el elemento siguiente del iterador modelado sobre la estructura auxiliar.
	 * @return El elemento siguiente en la iteración.
	 * @throws NoSuchElementException si la iteración no tiene más elementos.
	 */
	public E next() throws NoSuchElementException{
		E toReturn = null;
		if (cola.isEmpty()) throw new NoSuchElementException("ERROR: No hay elementos para iterar.");

		try {
			toReturn = cola.dequeue();
		}
		catch (EmptyQueueException e) {
			e.getMessage();
		}	
		return toReturn;
	}
}