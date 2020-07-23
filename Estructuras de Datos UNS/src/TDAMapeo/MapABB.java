package TDAMapeo;

import ABB.*;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class MapABB<K extends Comparable<K>, V> implements Map<K,V> {

	public static void main(String args[]) {
		Map<Integer, String> map1 = new MapABB<Integer, String>();

		try {
			map1.put(1, "www");
			map1.put(3, "iopi");
			map1.put(5, "eee");
			map1.put(7, "wwww");
			map1.put(2, "nicolas");
			map1.put(9, "sky");
			map1.put(1, "rreer");
			
			map1.entries();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	protected ABB<EntradaComparable<K,V>> entradas;
	protected int tamanyo;

	public MapABB() {
		entradas = new ABB<EntradaComparable<K,V>>();
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
		NodoABB<EntradaComparable<K, V>> nodo;
		EntradaComparable<K,V> entrada;
		V valorAsociado = null;

		check_key(key);
		entrada = new EntradaComparable<K,V>(key, null);
		nodo = entradas.buscar(entrada);

		if (nodo.getRotulo() != null) {
			valorAsociado = nodo.getRotulo().getValue();
		}

		return valorAsociado;
	}
	@Override
	public V put(K key, V value) throws InvalidKeyException {
		EntradaComparable<K,V> entrada;
		NodoABB<EntradaComparable<K, V>> nodo;
		V a_retornar = null;

		check_key(key);		
		entrada = new EntradaComparable<K,V>(key, value);
		nodo = entradas.buscar(entrada);

		//Si nodo es un nodo dummy entonces la entrada que queremos insertar no existe dentro del ABB, por
		//ende no existe en el mapeo. En consecuencia, insertamos una nueva entrada
		if (nodo.getRotulo() == null) {
			entradas.expandir(nodo, entrada);
			tamanyo++;
		}

		//Caso contrario, la entrada ya existe en el mapeo (ABB) y debemos modificar su valor
		else {
			a_retornar = nodo.getRotulo().getValue();
			nodo.getRotulo().setValue(value);
		}

		return a_retornar;
	}
	@Override
	public V remove(K key) throws InvalidKeyException {
		EntradaComparable<K,V> entrada;
		NodoABB<EntradaComparable<K, V>> nodo;
		V a_retornar = null;

		check_key(key);	
		entrada = new EntradaComparable<K,V>(key, null);
		nodo = entradas.buscar(entrada);

		//Si nodo es un nodo dummy entonces la entrada que queremos eliminar no existe dentro del ABB, por
		//ende no existe en el mapeo. 
		if (nodo.getRotulo() != null) {
			a_retornar = nodo.getRotulo().getValue();
			entradas.eliminar(nodo);
			tamanyo--;
		}
		
		return a_retornar;
	}
	@Override
	public Iterable<K> keys() {
		PositionList<K> lista_de_claves = new ListaDoblementeEnlazada<K>();
		
		NodoABB<EntradaComparable<K,V>> raiz = entradas.raiz();
		
		if (raiz.getRotulo() != null) {
			recorrido_inorder_claves(raiz, lista_de_claves);
		}
		
		return lista_de_claves;
	}
	@Override
	public Iterable<V> values() {
		PositionList<V> lista_de_valores = new ListaDoblementeEnlazada<V>();
		
		NodoABB<EntradaComparable<K,V>> raiz = entradas.raiz();
		
		if (raiz.getRotulo() != null) {
			recorrido_inorder_valores(raiz, lista_de_valores);
		}
		
		return lista_de_valores;
	}
	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> lista_entradas = new ListaDoblementeEnlazada<Entry<K,V>>();
		
		NodoABB<EntradaComparable<K,V>> raiz = entradas.raiz();
		
		if (raiz.getRotulo() != null) {
			recorrido_inorder_entries(raiz, lista_entradas);
		}
		
		return lista_entradas;
	}

	private void check_key(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();
	}

	private void recorrido_inorder_claves(NodoABB<EntradaComparable<K, V>> p, PositionList<K> lista_de_claves) {
		if (p.getLeft().getRotulo() != null) {
			recorrido_inorder_claves(p.getLeft(), lista_de_claves);
		}
		lista_de_claves.addLast(p.getRotulo().getKey());
		if (p.getRight().getRotulo() != null) {
			recorrido_inorder_claves(p.getRight(), lista_de_claves);
		}
	}

	private void recorrido_inorder_valores(NodoABB<EntradaComparable<K, V>> p, PositionList<V> lista_de_valores) {
		if (p.getLeft().getRotulo() != null) {
			recorrido_inorder_valores(p.getLeft(), lista_de_valores);
		}
		lista_de_valores.addLast(p.getRotulo().getValue());
		if (p.getRight().getRotulo() != null) {
			recorrido_inorder_valores(p.getRight(), lista_de_valores);
		}		
	}

	private void recorrido_inorder_entries(NodoABB<EntradaComparable<K, V>> p, PositionList<Entry<K,V>> lista_entradas) {
		if (p.getLeft().getRotulo() != null) {
			recorrido_inorder_entries(p.getLeft(), lista_entradas);
		}
		lista_entradas.addLast(p.getRotulo());
		if (p.getRight().getRotulo() != null) {
			recorrido_inorder_entries(p.getRight(), lista_entradas);
		}
	}
}
