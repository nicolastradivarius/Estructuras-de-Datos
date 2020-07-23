package TDADiccionario;

import java.util.Iterator;

import TDALista.*;

public class Diccionario_con_lista<K,V> implements Dictionary<K,V> {

	protected PositionList<Entry<K, V>> dictionarylist;

	public Diccionario_con_lista() {
		dictionarylist = new ListaDoblementeEnlazada<Entry<K,V>>();
	}
	@Override
	public int size() {
		return dictionarylist.size();
	}

	@Override
	public boolean isEmpty() {
		return dictionarylist.isEmpty();
	}

	@Override
	public Entry<K, V> find(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		Iterator<Entry<K,V>> it = dictionarylist.iterator();
		boolean seguirBuscando = true;
		Entry<K,V> e = null;
		Entry<K,V> toReturn = null;

		while (it.hasNext() && seguirBuscando) {
			e = it.next();
			if (e.getKey().equals(key)) {
				seguirBuscando = false;
				toReturn = e;
			}
		}

		return toReturn;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		Iterator<Entry<K, V>> it = dictionarylist.iterator();
		PositionList<Entry<K,V>> listaDeKeys = new ListaDoblementeEnlazada<Entry<K,V>>();
		Entry<K,V> e = null;

		while (it.hasNext()) {
			e = it.next();

			if (e.getKey().equals(key)) {
				listaDeKeys.addLast(e);
			}
		}

		return listaDeKeys;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		Entry<K,V> nuevaEntrada = new Entrada<K,V>(key, value);
		dictionarylist.addLast(nuevaEntrada);

		return nuevaEntrada;
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if (e == null)
			throw new InvalidEntryException("Entrada invalida");

		Entry<K,V> aux = null;
		boolean encontreEntrada = false;

		try {
			for (Position<Entry<K,V>> p : dictionarylist.positions()) {

				if (p.element() == e) {
					aux = p.element();
					dictionarylist.remove(p);
					encontreEntrada = true;
				}
			}

			if (!encontreEntrada)
				throw new InvalidEntryException("La entrada no se encuentra en el diccionario");
		}
		catch (InvalidPositionException err) {
			err.printStackTrace();
		}
		
		return aux;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		return dictionarylist;
	}
}
