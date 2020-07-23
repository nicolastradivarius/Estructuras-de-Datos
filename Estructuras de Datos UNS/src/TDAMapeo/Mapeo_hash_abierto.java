package TDAMapeo;

import TDALista.*;

/**
 * Modela la implementación de la interface {@link Map} a través de una tabla hash abierta.
 * @author Nicolás González
 * @param <K> La clave de los elementos componentes en cada celda de la tabla.
 * @param <V> El valor correspondiente a la clave mencionada anteriormente.
 */
public class Mapeo_hash_abierto<K,V> implements Map<K,V> {

	//Estructura utilizada para representar internamente la tabla hash: Arreglo de listas de Entradas (pares ordenados). Comienza con un tamaño inicial definido y se redimensiona cuando es necesario. 
	protected PositionList<Entrada<K,V>> [] listhash;
	//Tamaño inicial del arreglo anteriormente mencionado. Puede modificarse
	protected int tamanyoInicial = 13;
	private int tamanyo;
	//Factor de carga definido para esta implementación. Utilizado para mantener la uniformidad de las entradas en la tabla. 
	private static final float fc = 0.8f;

	/**
	 * <B>Función Hash</B>. Es la función encargada de designar el slot (o bucket) correspondiente del arreglo a cada
	 * entrada con su respectiva clave.
	 * @param clave La clave a ser procesada por la función hash.
	 * @return El procesamiento de la función, que será utilizado como destinatario dentro del arreglo.
	 */
	protected int hash(K clave) {
		return Math.abs(clave.hashCode() % listhash.length);
	}

	/**
	 * Crea un arreglo de listas con el tamaño inicial de slots, donde cada slot contiene una lista vacía luego de ser creada.
	 */
	@SuppressWarnings("unchecked")
	public Mapeo_hash_abierto() {
		listhash = (PositionList<Entrada<K,V>> []) new PositionList[tamanyoInicial];

		for (int i=0; i < tamanyoInicial; i++) {
			listhash[i] = new ListaDoblementeEnlazada<Entrada<K,V>>();
		}
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
		if (key == null)
			throw new InvalidKeyException();

		int bucket = hash(key);
		V valorAsociado = null;

		for (Entrada<K,V> e : listhash[bucket]){

			if (e.getKey().equals(key)) {
				valorAsociado = e.getValue();
				break;
			}
		}
		
		return valorAsociado;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		//Si la tabla está desproporcionada (listas con gran número de entradas y listas con pocas entradas) entonces se redimensiona la tabla
		if ((tamanyo / listhash.length) >= fc)
			redimensionar();

		int bucket = hash(key);
		boolean seguir = true;
		PositionList<Entrada<K, V>> listaApuntada = listhash[bucket];
		Position<Entrada<K, V>> pos = null;
		V valorAnterior = null;

		try {
			if (!listaApuntada.isEmpty()) {
				pos = listaApuntada.first();

				//Busca si hay una entrada con clave repetida en la lista del bucket para, en ese caso, remplazar su valor.
				while (pos != null && seguir) {
					if (pos.element().getKey().equals(key)) {
						valorAnterior = pos.element().getValue();
						pos.element().setValue(value);
						seguir = false;
					}

					pos = (pos == listaApuntada.last()) ? null : listaApuntada.next(pos);
				}
			}

			//Si no encontró una clave repetida, entonces crea una nueva con la clave y el valor pasados por parámetro.
			if (seguir) {
				listaApuntada.addLast(new Entrada<K,V>(key, value));
				tamanyo++;
			}

		}
		catch (EmptyListException | InvalidPositionException | BoundaryViolationException err) {
			err.printStackTrace();
		}

		return valorAnterior;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		boolean seguir = true;
		int bucket = hash(key);
		Position<Entrada<K,V>> pos = null;
		V valorViejo = null;

		try {

			if (!(listhash[bucket].isEmpty())) {
				pos = listhash[bucket].first();

				//Busca la entrada con la clave parametrizada para removerla posteriormente
				while (pos != null && seguir) {
					if (pos.element().getKey().equals(key)) {
						valorViejo = pos.element().getValue();
						listhash[bucket].remove(pos);
						tamanyo--;
						seguir = false;
					}
					if (seguir)
						pos = (pos == listhash[bucket].last()) ? null : listhash[bucket].next(pos);
				}
			}
		}
		catch (EmptyListException | InvalidPositionException | BoundaryViolationException err) {
			err.printStackTrace();
		}

		return valorViejo;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> listaKeys = new ListaDoblementeEnlazada<K>();

		for (int h=0; h < listhash.length; h++) {

			for (Entrada<K,V> e : listhash[h]) {
				listaKeys.addLast(e.getKey());
			}
		}

		return listaKeys;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> listaValues = new ListaDoblementeEnlazada<V>();

		for (int h=0; h < listhash.length; h++) {

			for (Entrada<K,V> e : listhash[h]) {
				listaValues.addLast(e.getValue());
			}
		}

		return listaValues;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> listaEntries = new ListaDoblementeEnlazada<Entry<K,V>>();

		for (int h=0; h < listhash.length; h++) {

			for (Entrada<K,V> e : listhash[h]) {
				listaEntries.addLast(e);
			}
		}

		return listaEntries;
	}

	/*¨*
	 * Redimensiona el tamaño del arreglo cuando éste supera el factor de carga definido.
	 */
	@SuppressWarnings("unchecked")
	private void redimensionar() {
		int nueva_dimension = proximo_primo(tamanyo*2);
		PositionList<Entrada<K, V>>[] listahashAnterior = listhash;
		PositionList<Entrada<K, V>> listaAnterior = null;
		Position<Entrada<K, V>> entryAnterior = null;
		listhash = (PositionList<Entrada<K,V>> []) new PositionList[nueva_dimension];

		//Inicializa cada bucket del nuevo arreglo con una lista vacía.
		for (int i=0; i < listhash.length; i++) {
			listhash[i] = new ListaDoblementeEnlazada<Entrada<K,V>>();
		}

		try {
			//Para cada bucket de la tabla original (...)
			for (int i=0; i < listahashAnterior.length; i++) {
				listaAnterior = listahashAnterior[i];

				// (...) inserta los elementos de las listas en la nueva tabla redimensionada.
				while (! (listaAnterior.isEmpty())) {
					entryAnterior = listaAnterior.first();
					Entrada<K, V> entryAReacomodar = listaAnterior.remove(entryAnterior);
					int bucket = hash(entryAReacomodar.getKey());
					listhash[bucket].addLast(entryAReacomodar);
				}
			}
		}
		catch (EmptyListException | InvalidPositionException err) {
			err.printStackTrace();
		}
	}

	/**
	 * Devuelve el siguiente número primo positivo a un número n parametrizado.
	 * Adaptado de: https://www.geeksforgeeks.org/program-to-find-the-next-prime-number/
	 * @param n Número a ser considerado para calcular el número primo siguiente.
	 * @return El número primo positivo siguiente al número n.
	 */
	private static int proximo_primo(int n) {
		boolean encontre = false;
		int primo = n;

		//Caso base
		if (n <= 1)  
			primo = 2;

		// Se repite continuamente hasta que esPrimo retorna verdadero para un número mayor que n.  
		while (!encontre) {
			primo++;
			if (esPrimo(primo)) 
				encontre = true;
		}

		return primo;
	}

	/**
	 * Determina si el número pasado por parámetro es primo.
	 * Adaptado de: https://www.geeksforgeeks.org/program-to-find-the-next-prime-number/
	 * @param n Número a ser evaluado
	 * @return Verdadero si el número es primo, falso en caso contrario.
	 */
	private static boolean esPrimo(int n) {
		boolean esprimo = true;

		//casos base
		if (n <= 1) 
			esprimo = false;
		if (n <= 3) 
			esprimo = true;

		// Este chequeo se hace para que podamos omitir cinco números intermedios en el bucle inferior 
		if (n % 2 == 0 || n % 3 == 0)
			esprimo = false;  

		//comprueba que el número n sea efectivamente un número primo
		for (int i = 5; i * i <= n; i = i + 6) {
			if (n % i == 0 || n % (i + 2) == 0)  
				esprimo = false;
		}

		return esprimo;  
	}
}
