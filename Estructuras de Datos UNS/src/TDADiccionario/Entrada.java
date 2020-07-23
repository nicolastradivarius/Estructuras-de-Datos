package TDADiccionario;

/**
 * Modela la implementación de la interface {@link Entry} a través de un par ordenado (Clave, Valor).
 * @author Nicolás González
 * @param <K> Clave de la entrada
 * @param <V> Valor de la entrada
 */
public class Entrada<K, V> implements Entry<K, V> {

	protected K key;
	protected V value;

	/**
	 * Crea una entrada con clave y valor pasados por parámetro.
	 * @param key Clave que va a almacenar la entrada creada.
	 * @param value Valor que va a almacenar la entrada creada.
	 */
	public Entrada(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public void setValue(V value) {
		this.value = value;
	}
}
