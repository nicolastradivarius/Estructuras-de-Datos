package TDAMapeo;

import ABB.AVL;
import ABB.NodoAVL;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class Mapeo_con_AVL<K extends Comparable<K>, V> implements Map<K, V> {

	protected AVL<EntradaComparable<K,V>> entradas;
	protected int tamanyo;

	public Mapeo_con_AVL() {
		entradas = new AVL<EntradaComparable<K,V>>(new DefaultComparator<EntradaComparable<K, V>>());
		tamanyo = 0;
	}

	@Override
	public int size() {
		return tamanyo;
	}

	@Override
	public boolean isEmpty() {
		return tamanyo == 0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		NodoAVL<EntradaComparable<K,V>> nodo;
		EntradaComparable<K,V> entrada;
		V valorAsociado = null;

		checkKey(key);
		entrada = new EntradaComparable<>(key, null);
		nodo = entradas.buscar(entrada);

		if (nodo.getRotulo() != null) {
			valorAsociado = nodo.getRotulo().getValue();
		}

		return valorAsociado;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		EntradaComparable<K,V> entrada;
		NodoAVL<EntradaComparable<K,V>> nodo;
		V a_retornar = null;

		checkKey(key);
		entrada = new EntradaComparable<K,V> (key, value);

		nodo = entradas.buscar(entrada);

		if (nodo.getRotulo() != null) {
			a_retornar = nodo.getRotulo().getValue();
		}

		entradas.insert(entrada);
		tamanyo++;

		return a_retornar;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		EntradaComparable<K,V> entrada;
		NodoAVL<EntradaComparable<K,V>> nodo;
		V a_retornar = null;

		checkKey(key);
		entrada = new EntradaComparable<>(key, null);
		nodo = entradas.buscar(entrada);

		if (nodo.getRotulo() != null) {
			a_retornar = nodo.getRotulo().getValue();
			entradas.remover(nodo.getRotulo());
			tamanyo--;
		}

		return a_retornar;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> lista_claves = new ListaDoblementeEnlazada<K>();

		NodoAVL<EntradaComparable<K,V>> raiz = entradas.getRaiz();

		if (raiz.getRotulo() != null) {
			recorrido_inorder_claves(raiz, lista_claves);
		}

		return lista_claves;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> lista_valores = new ListaDoblementeEnlazada<V>();

		NodoAVL<EntradaComparable<K,V>> raiz = entradas.getRaiz();

		if (raiz.getRotulo() != null) {
			recorrido_inorder_valores(raiz, lista_valores);
		}

		return lista_valores;
	}


	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> lista_entradas = new ListaDoblementeEnlazada<Entry<K,V>>();

		NodoAVL<EntradaComparable<K,V>> raiz = entradas.getRaiz();

		if (raiz.getRotulo() != null) {
			recorrido_inorder_entries(raiz, lista_entradas);
		}

		return lista_entradas;
	}

	private void checkKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();
	
	}

	private void recorrido_inorder_claves(NodoAVL<EntradaComparable<K, V>> p, PositionList<K> lista_claves) {
		if (p.getHijoizq().getRotulo() != null) {
			recorrido_inorder_claves(p.getHijoizq(), lista_claves);
		}
		lista_claves.addLast(p.getRotulo().getKey());
		if (p.getHijoder().getRotulo() != null) {
			recorrido_inorder_claves(p.getHijoder(), lista_claves);
		}
	}

	private void recorrido_inorder_valores(NodoAVL<EntradaComparable<K, V>> p, PositionList<V> lista_valores) {
		if (p.getHijoizq().getRotulo() != null) {
			recorrido_inorder_valores(p.getHijoizq(), lista_valores);
		}
		lista_valores.addLast(p.getRotulo().getValue());
		if (p.getHijoder().getRotulo() != null) {
			recorrido_inorder_valores(p.getHijoder(), lista_valores);
		}
	}

	private void recorrido_inorder_entries(NodoAVL<EntradaComparable<K, V>> p,
			PositionList<Entry<K, V>> lista_entradas) {
		if (p.getHijoizq().getRotulo() != null) {
			recorrido_inorder_entries(p.getHijoizq(), lista_entradas);
		}
		lista_entradas.addLast(p.getRotulo());
		if (p.getHijoder().getRotulo() != null) {
			recorrido_inorder_entries(p.getHijoder(), lista_entradas);
		}
	}

}
