package TDAMapeo;

/**
 * 	Interface Entry (Entrada) con clave y valor.
 * @author Nicolás González
 * @see Map
 * @param <K> Clave de la entrada
 * @param <V> Valor de la entrada
 */
public interface Entry<K, V> {

	/**
	 * Devuelve la clave de la entrada.
	 * @return La clave de la entrada.
	 */
	public K getKey();
	/**
	 * Devuelve el valor de la entrada.
	 * @return El valor de la entrada.
	 */
	public V getValue();
	
	/**
	 * Reemplaza el valor de la entrada por el valor pasado por parámetro.
	 * @param value Valor que va a reemplazar al valor anterior de la entrada.
	 */
	public void setValue(V value);
}