package TDADiccionario;

import TDALista.*;

/**
 * Modela la implementación de la interface {@link Dictionary} a través de una tabla hash cerrada. La misma está implementada como un
 * arreglo de entradas con tamaño tal que éste es un número primo.
 * @author Nicolás González
 *
 * @param <K> Tipo de elemento de la primer parte del par ordenado
 * @param <V> Tipo de elemento de la segunda parte del par ordenado.
 */
public class Diccionario_hash_cerrado<K,V> implements Dictionary<K,V> {

	//Factor de carga
	private static final float fc = 0.9f;
	protected Entrada<K,V> [] entryhash;
	//Tiene que ser un número primo
	protected int tamanyoInicial = 13;
	protected final Entrada<K,V> bucket_disponible = new Entrada<K,V>(null, null);
	protected int cant_entradas;

	/**
	 *<B>Función Hash</B>. Es la función encargada de designar el slot (o bucket) correspondiente del arreglo a cada
	 * entrada con su respectiva clave.
	 * @param clave Clave a ser procesada por la función.
	 * @return Slot correspondiente a la evaluación de la función hash con la clave parametrizada.
	 */
	protected int hash(K clave) {
		return Math.abs(clave.hashCode() % entryhash.length);
	}

	/**
	 * Crea un diccionario vacío.
	 */
	@SuppressWarnings("unchecked")
	public Diccionario_hash_cerrado() {
		entryhash = (Entrada<K,V> []) new Entrada[tamanyoInicial];
		cant_entradas = 0;
	}

	@Override
	public int size() {
		return cant_entradas;
	}

	@Override
	public boolean isEmpty() {
		return cant_entradas == 0;
	}

	@Override
	public Entry<K, V> find(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		boolean encontre = false;
		int bucket_inicial = hash(key);
		int indice_actual = bucket_inicial;
		Entry<K,V> buscada = null;

		do {

			if (entryhash[indice_actual] != bucket_disponible) {
				if (entryhash[indice_actual] != null) {
					if (entryhash[indice_actual].getKey().equals(key)) {
						buscada = entryhash[indice_actual];
						encontre = true;
					}
				}
				else {
					/*En realidad, no es cierto que se haya "encontrado" a la entrada buscada; ésta asignación funciona 
					como condición de corte pues encontrar un bucket nulo implica que la entrada buscada no existe.*/
					encontre = true;
				}
			}

			indice_actual = (indice_actual + 1) % entryhash.length;

		} while (!encontre && bucket_inicial != indice_actual);

		return buscada;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		PositionList<Entry<K,V>> entryIterable = new ListaDoblementeEnlazada<Entry<K,V>>();

		boolean seguir = true;
		int bucket_inicial = hash(key);
		int indice_actual = bucket_inicial;

		do {

			if (entryhash[indice_actual] != bucket_disponible) {
				if (entryhash[indice_actual] != null) {
					if (entryhash[indice_actual].getKey().equals(key)) {
						entryIterable.addLast(entryhash[indice_actual]);
					}
				}
				else {
					seguir = false;
				}
			}

			indice_actual = (indice_actual + 1) % entryhash.length;

		} while (seguir && bucket_inicial != indice_actual);

		return entryIterable;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		Entrada<K,V> entradaNueva = new Entrada<K,V>(key, value);

		if ((cant_entradas / entryhash.length) >= fc)
			redimensionar();

		int bucketAsoc = hash(key);
		insertar_linealmente(entradaNueva, bucketAsoc);
		cant_entradas++;

		return entradaNueva;
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if (e == null)
			throw new InvalidEntryException("Entrada inválida");
		if (e.getKey() == null)
			throw new InvalidEntryException("Entrada con clave invalida");

		boolean removi = false;
		int bucket_inicial = hash(e.getKey());
		int indice_actual = bucket_inicial;
		Entry<K, V> removida = null;

		do {

			if (entryhash[indice_actual] != bucket_disponible) {
				if (entryhash[indice_actual] != null) {
					if (entryhash[indice_actual].getKey().equals(e.getKey()) && entryhash[indice_actual].getValue().equals(e.getValue())) {
						removida = entryhash[indice_actual];
						entryhash[indice_actual] = bucket_disponible;
						removi = true;
						cant_entradas--;
					}
				}
				else //Si aparece un null entonces se puede asegurar que la entrada buscada no existe, pues de existir estaría en dicho slot 
					throw new InvalidEntryException("La entrada no se encuentra en la tabla");
			}

			indice_actual = (indice_actual + 1) % entryhash.length;

		} while (!removi && bucket_inicial != indice_actual);

		//Si se recorrió toda la tabla y la entrada no fue removida, entonces dicha entrada no está en la tabla.  
		if (indice_actual == bucket_inicial && !removi)
			throw new InvalidEntryException("La entrada no se encuentra en la tabla");

		return removida;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> lista_entradas = new ListaDoblementeEnlazada<Entry<K,V>>();

		for (int i=0; i < entryhash.length; i++) {
			if (entryhash[i] != bucket_disponible) {
				if (entryhash[i] != null) {
					lista_entradas.addLast(entryhash[i]);
				}
			}
		}

		return lista_entradas;
	}

	/**
	 * Redimensiona el tamaño del arreglo de entradas cuando éste supera el factor de carga. El nuevo tamaño es el próximo número primo al doble del tamaño original.
	 */
	@SuppressWarnings("unchecked")
	private void redimensionar() {
		int nuevaDimension = proximo_primo(cant_entradas*2);
		int bucket = 0;
		Entrada<K, V>[] entryhashAnterior = entryhash;
		entryhash = (Entrada<K,V> []) new Entrada[nuevaDimension];

		for (int j=0; j < entryhash.length; j++) {
			entryhash[j] = null;
		}

		//Para cada bucket de la tabla original (...)
		for (int i=0; i < entryhashAnterior.length; i++) {
			Entrada<K,V> entradaAnterior = entryhashAnterior[i];

			//(...) inserta las entradas de los buckets en la nueva tabla redimensionada.
			if (entradaAnterior != null && entradaAnterior != bucket_disponible) {
				bucket = hash(entradaAnterior.getKey());
				insertar_linealmente(entradaAnterior, bucket);
			}
		}
	}

	/**
	 * Inserta las entradas en el diccionario siguiendo la <b>política de resolución lineal de colisiones</b>.
	 * @param entradaAInsertar Entrada que va a ser insertada en el diccionario.
	 * @param bucket Slot de la tabla donde se va a insertar la entrada, a menos que se produzca una colisión.
	 */
	private void insertar_linealmente(Entrada<K, V> entradaAInsertar, int bucket) {

		int bucket_inicial = bucket;
		int indice_actual = bucket;
		boolean inserte = false;

		do {

			//La entrada se inserta en el próximo slot disponible/nulo si hubiera. 
			if (entryhash[indice_actual] == bucket_disponible | entryhash[indice_actual] == null) {
				entryhash[indice_actual] = entradaAInsertar;
				inserte = true;
			}

			indice_actual = (indice_actual + 1) % entryhash.length;

		} while (!inserte && bucket_inicial != indice_actual);

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
