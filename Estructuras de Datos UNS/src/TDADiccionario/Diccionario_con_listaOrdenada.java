package TDADiccionario;

import java.util.Comparator;

import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class Diccionario_con_listaOrdenada<K extends Comparable<K>, V> implements Dictionary<K, V> {

	protected PositionList<Entrada<K,V>> dictionaryList;
	protected Comparator<K> keyComp;

	public Diccionario_con_listaOrdenada() {
		dictionaryList = new ListaDoblementeEnlazada<Entrada<K,V>>();
		keyComp = new DefaultComparator<K>();
	}

	public Diccionario_con_listaOrdenada(Comparator<K> comparador) {
		dictionaryList = new ListaDoblementeEnlazada<Entrada<K,V>>();
		keyComp = comparador;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Entry<K, V> find(K key) throws InvalidKeyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		PositionList<Entry<K, V>> lista = new ListaDoblementeEnlazada<>();
		try {
			TDALista.Position<Entrada<K,V>> entrada = this.dictionaryList.isEmpty() ? null : dictionaryList.first();

			while (entrada != null) {
				if (entrada.element().getKey().equals(key)) {
					lista.addLast(entrada.element());
				}
				if (entrada.element().getKey().compareTo(key) < 0)
					entrada = null;

				entrada = (entrada == dictionaryList.last()) ? null : dictionaryList.next(entrada);
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}

		return lista;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		// TODO Auto-generated method stub
		return null;
	}

}
