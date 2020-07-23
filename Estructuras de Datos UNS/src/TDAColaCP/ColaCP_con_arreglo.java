package TDAColaCP;

import java.util.Comparator;

public class ColaCP_con_arreglo<K extends Comparable<K> ,V> implements PriorityQueue<K,V> {

	public static void main(String args[]) {
		PriorityQueue<Integer, String>  pq1 = new ColaCP_con_arreglo<>();
		try {
			pq1.insert(1, "nico");
			pq1.insert(3, "sky");
			pq1.insert(2, "boca");
			
			System.out.println(pq1.toString());
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public String toString() {
		String salida = "[";
		
		for (int i=0; i<arreglo_entradas.length; i++) {
			salida += arreglo_entradas[i] + ", ";
		}
		
		salida += "]";
		return salida;
	}

	protected Entrada<K,V> [] arreglo_entradas;
	protected int size;
	protected Comparator<K> key_comparator;

	@SuppressWarnings("unchecked")
	public ColaCP_con_arreglo(int maxElems, Comparator<K> comparador_de_claves) {
		arreglo_entradas = (Entrada<K,V> []) new Entrada[maxElems];
		size = 0;
		key_comparator = comparador_de_claves;
	}

	@SuppressWarnings("unchecked")
	public ColaCP_con_arreglo(int maxElems) {
		arreglo_entradas = (Entrada<K,V> []) new Entrada[maxElems];
		size = 0;
		key_comparator = new DefaultComparator<K>();
	}

	@SuppressWarnings("unchecked")
	public ColaCP_con_arreglo() {
		arreglo_entradas = (Entrada<K,V> []) new Entrada[10];
		size = 0;
		key_comparator = new DefaultComparator<K>();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if (size == 0)
			throw new EmptyPriorityQueueException();

		return arreglo_entradas[0];
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {

		checkKey(key);
		//para siempre asegurar espacio al momento de acomodar
		if (size / (arreglo_entradas.length-1) > 0.9f) {
			redimensionar();
		}

		Entrada<K,V> nuevo = new Entrada<>(key, value);
		Entrada<K,V> caminante = (size == 0) ? null : arreglo_entradas[size-1];
		int i = (size == 0) ? 0 : size-1;
		while (caminante != null && this.key_comparator.compare(key, caminante.getKey()) < 0) {
			if (i == 0)
				caminante = null;
			else {
				i--;
				caminante = arreglo_entradas[i];
			}
		}

		if (caminante == null) {
			moverlos_hacia_derecha(0);
			arreglo_entradas[0] = nuevo;
		}
		else {
			moverlos_hacia_derecha(i+1);
			arreglo_entradas[i+1] = nuevo;
		}

		size++;

		return nuevo;
	}


	private void moverlos_hacia_derecha(int posicion) {
		for (int i=size-1; i >= posicion; i--) {
			arreglo_entradas[i+1] = arreglo_entradas[i];
		}

		arreglo_entradas[posicion] = null;
	}

	@SuppressWarnings("unchecked")
	private void redimensionar() {
		Entrada<K,V> [] arreglo_entradas_original = arreglo_entradas;

		arreglo_entradas = (Entrada<K,V> []) new Entrada[arreglo_entradas.length + 20];

		for (int i=0; i<arreglo_entradas_original.length; i++) {
			arreglo_entradas[i] = arreglo_entradas_original[i];
		}

		arreglo_entradas_original = null;

	}

	@Override
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if (size == 0)
			throw new EmptyPriorityQueueException();

		Entrada<K,V> toReturn = arreglo_entradas[0];
		arreglo_entradas[0] = null;

		moverlos_hacia_izquierda(0);

		size--;

		return toReturn;
	}

	private void moverlos_hacia_izquierda(int posicion) {
		for (int i=posicion; i < size; i++) {
			arreglo_entradas[i] = arreglo_entradas[i+1];
		}

		arreglo_entradas[size] = null;		
	}

	private void checkKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();
	}

}
