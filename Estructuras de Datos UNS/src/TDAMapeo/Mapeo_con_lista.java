package TDAMapeo;

import java.util.Iterator;
import TDALista.*;

/**
 * Modela la implementación de la interface Map a través de una lista enlazada (simple o doble).
 * @author Nicolás González
 *
 * @param <K> Clave.
 * @param <V> Valor.
 */
public class Mapeo_con_lista<K, V> implements Map<K, V> {

	protected PositionList<Entry<K, V>> maplist;

	/**
	 * Crea una lista enlazada de entradas vacía.
	 */
	public Mapeo_con_lista() {
		maplist = new ListaDoblementeEnlazada<Entry<K, V>>();
	}

	@Override
	public int size() {
		return maplist.size();
	}

	@Override
	public boolean isEmpty() {
		return maplist.isEmpty();
	}

	@Override
	public V get(K key) throws InvalidKeyException {

		if (key == null)
			throw new InvalidKeyException();

		V valueReturn = null;
		boolean seguir = true;
		Iterator<Entry<K, V>> it = maplist.iterator();
		Entry<K, V> p = null;

		while (it.hasNext() && seguir) {
			p = it.next();

			if (p.getKey().equals(key)) {
				valueReturn = p.getValue();
				seguir = false;
			}
		}
		return valueReturn;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {

		if (key == null)
			throw new InvalidKeyException();

		boolean seguir = true;	
		V aux = null;
		Iterator<Entry<K, V>> it = maplist.iterator();
		Entry<K, V> p = null;

		while (it.hasNext() && seguir) {
			p = it.next();
			if (p.getKey().equals(key)) {
				aux = p.getValue();
				p.setValue(value);
				seguir = false;
			}
		}

		if (seguir) {
			maplist.addLast(new Entrada<K, V>(key, value));
		}

		return aux;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		Iterator<Position<Entry<K, V>>> it = maplist.positions().iterator();
		Position<Entry<K, V>> p = null;
		boolean seguir = true;
		V aux = null;
		try {
			while (it.hasNext() && seguir) {
				p = it.next();
				if (p.element().getKey().equals(key)) {
					aux = p.element().getValue();
					maplist.remove(p);
					seguir = false;
				}
			}
		}
		catch (InvalidPositionException e) {
			e.printStackTrace();
		}

		return aux;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> listaDeValores = new ListaDoblementeEnlazada<K>();
		for (Entry<K,V> e : maplist) {
			listaDeValores.addLast(e.getKey());
		}
		
		return listaDeValores;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> listaDeValores = new ListaDoblementeEnlazada<V>();
		for (Entry<K,V> e : maplist) {
			listaDeValores.addLast(e.getValue());
		}
		
		return listaDeValores;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		return maplist;
	}

	public String toString() {
		String salida = "{ ";
		Iterator<Entry<K, V>> it = maplist.iterator();
		Entry<K, V> e = null;
		while (it.hasNext()) {
			e = it.next();
			salida += e.toString() + ", ";
		}

		salida += "}";
		return salida;
	}
}
