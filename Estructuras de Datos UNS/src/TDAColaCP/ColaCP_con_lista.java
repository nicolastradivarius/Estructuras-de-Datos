package TDAColaCP;

import java.util.Comparator;

import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;

public class ColaCP_con_lista<K extends Comparable<K>, V> implements PriorityQueue<K,V> {

	protected PositionList<Entrada<K,V>> lista_entradas;
	protected Comparator<K> keyComparator;

	public ColaCP_con_lista(Comparator<K> comparador_claves) {
		lista_entradas = new ListaDoblementeEnlazada<Entrada<K,V>>();
		keyComparator = comparador_claves;
	}

	public ColaCP_con_lista() {
		lista_entradas = new ListaDoblementeEnlazada<Entrada<K,V>>();
		keyComparator = new DefaultComparator<K>();
	}

	@Override
	public int size() {
		return lista_entradas.size();
	}

	@Override
	public boolean isEmpty() {
		return lista_entradas.size() == 0;
	}

	@Override
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		Entry<K,V> toReturn = null;

		try {
			if (!lista_entradas.isEmpty()) {
				toReturn = lista_entradas.first().element();
			}
			else
				throw new EmptyPriorityQueueException();
		}
		catch (TDALista.EmptyListException err) {
			err.printStackTrace();
		}

		return toReturn;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entrada<K,V> nuevo = new Entrada<>(key, value);
		Position<Entrada<K, V>> posicion_insercion;

		try {
			posicion_insercion = lista_entradas.isEmpty() ? null : lista_entradas.last();

			while (posicion_insercion != null && keyComparator.compare(key, posicion_insercion.element().getKey()) < 0)
				posicion_insercion = (posicion_insercion == lista_entradas.first()) ? null : lista_entradas.prev(posicion_insercion);

			if (posicion_insercion == null)
				lista_entradas.addFirst(nuevo);
			else
				lista_entradas.addAfter(posicion_insercion, nuevo);
		}
		catch (TDALista.EmptyListException | TDALista.InvalidPositionException | TDALista.BoundaryViolationException err) {
			err.printStackTrace();
		}

		return nuevo;
	}

	private void checkKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();
	}

	@Override
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		Entrada<K, V> toReturn = null;

		try {
			if (!lista_entradas.isEmpty()) {
				toReturn = lista_entradas.remove(lista_entradas.first());
			}
			else
				throw new EmptyPriorityQueueException();
		}
		catch (TDALista.EmptyListException | TDALista.InvalidPositionException err) {
			err.printStackTrace();
		}

		return toReturn;
	}

}
