package TDADiccionario;

import TDALista.*;

public class Diccionario_hash_abierto<K,V> implements Dictionary<K,V> {

	public static void main(String args[]) {
		Dictionary<Character, String> d1 = new Diccionario_hash_abierto<Character, String>();

		try {
			d1.insert('c', "character");
			d1.insert('c', "clone");
			d1.insert('c', "codepile");
			
			System.out.println(d1.toString());
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public String toString() {
		String salida = "{" + "\n";

		for (int i=0; i < listhash.length; i++) {
			salida += i + ": " + listhash[i].toString() + "\n";
		}
		salida+= "}";
		return salida;
	}
	//Estructura utilizada para representar internamente la tabla hash: Arreglo de listas de Entradas (pares). 
	//Comienza con un tamaño inicial de 13 slots y se redimensiona cuando es necesario. 
	protected PositionList<Entry<K,V>> [] listhash;
	protected int tamanyoInicial = 13;
	//Factor de carga
	private static final float fc = 0.9f;

	/**
	 * Crea un arreglo de listas con 13 slots, donde cada slot contiene una lista vacía luego de ser creada.
	 */
	@SuppressWarnings("unchecked")
	public Diccionario_hash_abierto() {
		listhash = (PositionList<Entry<K,V>> []) new PositionList[tamanyoInicial];

		for (int i=0; i < tamanyoInicial; i++) {
			listhash[i] = new ListaDoblementeEnlazada<Entry<K,V>>();
		}
	}

	/**
	 * <B>Función Hash</B>. Es la función encargada de designar el slot (o bucket) correspondiente del arreglo a cada
	 * entrada con su respectiva clave.
	 * @param clave La clave a ser procesada por la función hash.
	 * @return El procesamiento de la función, que será utilizado como destinatario dentro del arreglo.
	 */
	protected int hash(K clave) {
		return Math.abs(clave.hashCode() % listhash.length);
	}

	@Override
	public int size() {
		//Se utiliza un método privado
		return cantidad_entradas();
	}

	@Override
	public boolean isEmpty() {
		return cantidad_entradas() == 0;
	}

	@Override
	public Entry<K, V> find(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		int bucket = hash(key);
		Entry<K, V> aux = null;

		for (Entry<K, V> e : listhash[bucket]){

			if (e.getKey().equals(key)) {
				aux = e;
				break;
			}
		}

		return aux;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		int bucket = hash(key);
		PositionList<Entry<K,V>> listaKeys = new ListaDoblementeEnlazada<Entry<K,V>>();

		for (Entry<K, V> e : listhash[bucket]){

			if (e.getKey().equals(key)) {
				listaKeys.addLast(e);
			}
		}

		return listaKeys;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		//Si la cantidad de entradas dividido la longitud del arreglo es mayor al factor de carga definido en el encabezado
		//de la clase, significa que alguna lista tiene más elementos que el resto (está desproporcionada)
		if ((cantidad_entradas() / listhash.length) >= fc)
			redimensionar();

		int bucket = hash(key);
		PositionList<Entry<K, V>> listaApuntada = listhash[bucket];
		Entry<K,V> nueva = new Entrada<K, V>(key, value);
		
		listaApuntada.addLast(nueva);

		return nueva;
	}

	@SuppressWarnings("unchecked")
	private void redimensionar() {
		int bucket = 0;
		int nueva_dimension = proximo_primo(cantidad_entradas()*2);
		PositionList<Entry<K, V>>[] listahashAnterior = listhash;
		PositionList<Entry<K, V>> listaAnterior = null;
		Position<Entry<K, V>> primeraEntrada = null;
		Entry<K, V> entryAReacomodar = null;
		listhash = (PositionList<Entry<K, V>>[]) new PositionList[nueva_dimension];

		//Inicializa cáda bucket del nuevo arreglo con una lista vacía.
		for (int i=0; i < listhash.length; i++) {
			listhash[i] = new ListaDoblementeEnlazada<Entry<K,V>>();
		}

		try {
			//Para cada bucket de la tabla...
			for (int i=0; i < listahashAnterior.length; i++) {
				listaAnterior = listahashAnterior[i];
				
				while (! (listaAnterior.isEmpty())) {
					primeraEntrada = listaAnterior.first();
					entryAReacomodar = listaAnterior.remove(primeraEntrada);
					bucket = hash(entryAReacomodar.getKey());
					listhash[bucket].addLast(entryAReacomodar);
				}
			}
		}
		catch (EmptyListException | InvalidPositionException err) {
			err.printStackTrace();
		}		
	}

	private int proximo_primo(int n) {
		boolean encontre = false;
		int primo = n;

		while (!encontre) {
			primo++;

			if (esPrimo(primo)) {
				encontre = true;
			}
		}

		return primo;
	}

	private boolean esPrimo(int n) {
		boolean esprimo = true;

		if (n % 2 == 0 || n % 3 == 0)
			esprimo = false;  

		for (int i = 5; i * i <= n; i = i + 6)  {
			if (n % i == 0 || n % (i + 2) == 0)  
				esprimo = false;
		}

		return esprimo;  
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if (e == null)
			throw new InvalidEntryException("Entrada invalida");

		boolean encontreEntrada = false;
		int backetAsociado = hash(e.getKey());
		Position<Entry<K,V>> puntero = null;
		Entry<K, V> aux = null;

		try {

			if (!(listhash[backetAsociado].isEmpty())) {
				puntero = listhash[backetAsociado].first();

				while (puntero != null && !encontreEntrada) {
					if (puntero.element().getKey().equals(e.getKey()) && puntero.element().getValue().equals(e.getValue())) {
						encontreEntrada = true;
						aux = puntero.element();
						listhash[backetAsociado].remove(puntero);
					}

					if (!encontreEntrada)
						puntero = (puntero == listhash[backetAsociado].last()) ? null : listhash[backetAsociado].next(puntero);
				}
			}
		}
		catch (EmptyListException | InvalidPositionException | BoundaryViolationException err) {
			err.printStackTrace();
		}

		if (!encontreEntrada)
			throw new InvalidEntryException("Entrada no encontrada");

		return aux;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> listaEntries = new ListaDoblementeEnlazada<Entry<K,V>>();

		for (int h=0; h < listhash.length; h++) {

			for (Entry<K, V> e : listhash[h]) {
				listaEntries.addLast(e);
			}
		}

		return listaEntries;
	}

	/**
	 * Computa la cantidad de entradas en cada lista de cada bucket.
	 * @return La cantidad de entradas del arreglo.
	 */
	private int cantidad_entradas() {
		int cant_entradas = 0;

		for (int i=0; i < listhash.length; i++) {
			cant_entradas += listhash[i].size();
		}

		return cant_entradas;
	}
}
