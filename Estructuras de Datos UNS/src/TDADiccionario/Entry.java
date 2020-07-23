package TDADiccionario;

/**
 * 	Interface Entry (Entrada) con clave y valor.
 * @author Nicolás González
 * @param <K> Clave
 * @param <V> Valor
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
	 * Modifica el valor contenido en la entrada.
	 * @param value Valor que va a ser almacenado en la entrada.
	 */
	public void setValue(V value);
}