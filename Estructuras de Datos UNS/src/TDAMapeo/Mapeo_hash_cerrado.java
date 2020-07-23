package TDAMapeo;

import TDALista.*;

public class Mapeo_hash_cerrado<K,V> implements Map<K,V>  {

	public static void main(String args[]) {
		Map<Integer, Character> m1 = new Mapeo_hash_cerrado<Integer, Character>();

		try {
			m1.put(0, 'c');
		
			System.out.println(m1.toString());
			System.out.println("tamaño: " + m1.size());
		}
		catch (InvalidKeyException err) {
			err.printStackTrace();
		}
	}

	public String toString() {
		String salida = "{" + "\n";
		int i=0;
		for (Entry<K,V> e : entryhash) {
			if (e == bucket_disponible)
				salida += i + ": " + "disponible" + "\n";
			else if (e == null)
				salida += i + ": " + "null" + "\n";
			else
				salida += i + ": " +  e.toString() + "\n";
			i++;
		}
		salida += "}";
		return salida;
	}
	private static final float fc = 0.9f;
	protected Entry<K,V> [] entryhash;
	protected int tamanyoInicial = 13;
	private int tamanyo;
	protected final Entrada<K,V> bucket_disponible = new Entrada<K,V>(null, null);

	protected int hash(K clave) {
		return Math.abs(clave.hashCode() % entryhash.length);
	}

	@SuppressWarnings("unchecked")
	public Mapeo_hash_cerrado() {
		entryhash = (Entrada<K,V> []) new Entrada[tamanyoInicial];
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

		V buscado = null;
		boolean encontre = false;
		int bucket_asociado = hash(key);
		int indice_actual = bucket_asociado;
		
		do {
			if (entryhash[indice_actual] != bucket_disponible) {
				
				if (entryhash[indice_actual] != null) {
					
					if (entryhash[indice_actual].getKey().equals(key)) {
						buscado = entryhash[indice_actual].getValue();
						encontre = true;
					}
				}
				else {
					encontre = true;
				}
			}
			
			indice_actual = (indice_actual + 1) % entryhash.length;
		}
		while (!encontre && indice_actual != bucket_asociado);

		return buscado;

	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();

		Entrada<K,V> entradaNueva = new Entrada<K,V>(key, value);
		if ((tamanyo / entryhash.length) >= fc)
			redimensionar();

		int bucketAsoc = hash(key);
		V retorno = insertar_linealmente(entradaNueva, bucketAsoc);

		return retorno;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();
	
		V valorViejo = null;
		int bucket_asociado = hash(key);
		int bucket_real = buscar_entrada(key, bucket_asociado);
		
		if (bucket_real != -1) {
			valorViejo = entryhash[bucket_real].getValue();
			entryhash[bucket_real] = bucket_disponible;
			tamanyo--;
		}
		
		return valorViejo;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> iterable = new ListaDoblementeEnlazada<K>();
	
		for (int i=0; i < entryhash.length; i++) {
			if (entryhash[i] != null && entryhash[i] != bucket_disponible) {
				iterable.addLast(entryhash[i].getKey());
			}
		}
		return iterable;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> iterable = new ListaDoblementeEnlazada<V>();
	
		for (int i=0; i < entryhash.length; i++) {
			if (entryhash[i] != null && entryhash[i] != bucket_disponible) {
				iterable.addLast(entryhash[i].getValue());
			}
		}
		return iterable;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> iterable = new ListaDoblementeEnlazada<Entry<K,V>>();
	
		for (int i=0; i < entryhash.length; i++) {
			if (entryhash[i] != null && entryhash[i] != bucket_disponible) {
				iterable.addLast(entryhash[i]);
			}
		}
		return iterable;
	}

	@SuppressWarnings("unchecked")
	private void redimensionar() {
		int nuevaDimension = proximo_primo(tamanyo*2);
		Entry<K, V>[] entryhashAnterior = entryhash;
		entryhash = (Entrada<K,V> []) new Entrada[nuevaDimension];

		for (int j=0; j < entryhash.length; j++) {
			entryhash[j] = null;
		}
		tamanyo = 0;

		for (int i=0; i < entryhashAnterior.length; i++) {
			Entry<K,V> entradaAnterior = entryhashAnterior[i];
			if (entradaAnterior != null && entradaAnterior != bucket_disponible) {
				int bucket = hash(entradaAnterior.getKey());
				insertar_linealmente(entradaAnterior, bucket);
			}
		}
	}

	private V insertar_linealmente(Entry<K, V> entradaAnterior, int bucket) {

		V valorViejo = null;
		int bucket_inicial = bucket;
		int primer_bucket_disponible = -1;
		int indice_actual = bucket;
		boolean inserte = false;

		do {
			if (entryhash[indice_actual] != bucket_disponible) {
				if (entryhash[indice_actual] != null) {
					
					if (entryhash[indice_actual].getKey().equals(entradaAnterior.getKey())) {
						valorViejo = entryhash[indice_actual].getValue();
						entryhash[indice_actual].setValue(entradaAnterior.getValue());
						inserte = true;
					}
				}
				else {
					primer_bucket_disponible = (primer_bucket_disponible == -1) ? indice_actual : primer_bucket_disponible;
					entryhash[primer_bucket_disponible] = entradaAnterior;
					inserte = true;
					tamanyo++;
				}
			}
			else {
				primer_bucket_disponible = (primer_bucket_disponible == -1) ? indice_actual : primer_bucket_disponible;
			}

			indice_actual = (indice_actual + 1) % entryhash.length;
		} while (!inserte && bucket_inicial != indice_actual );

		if (!inserte){
			entryhash[primer_bucket_disponible] = entradaAnterior;
			tamanyo++;
		}

		return valorViejo;
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

	private int buscar_entrada(K keyBuscada, int bucket) {
		int bucket_buscado = -1;
		int indice_actual = bucket;
		boolean encontre = false;
		do {
			if (entryhash[indice_actual] != bucket_disponible) {
				
				if (entryhash[indice_actual] != null) {
					
					if (entryhash[indice_actual].getKey().equals(keyBuscada)) {
						bucket_buscado = indice_actual;
						encontre = true;
					}
				}
				else {
					encontre = true;
				}
			}
			
			indice_actual = (indice_actual + 1) % entryhash.length;
		}
		while (!encontre && indice_actual != bucket);
		
		return bucket_buscado;
	}

}
